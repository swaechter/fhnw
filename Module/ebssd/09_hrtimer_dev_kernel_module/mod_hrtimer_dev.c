#include <linux/module.h>
#include <linux/hrtimer.h>
#include <linux/kthread.h>
#include <linux/delay.h>
#include <linux/uaccess.h>
#include <linux/fs.h>
#include <linux/wait.h>
#include <linux/device.h>

MODULE_AUTHOR("M. Tim Jones (IBM)");
MODULE_AUTHOR("Matthias Meier <matthias.meier@fhnw.ch>");
MODULE_LICENSE("GPL");

#define INTERVAL_BETWEEN_CALLBACKS (100 * 1000000LL) //100ms (scaled in ns)
#define NR_ITERATIONS 20

static DECLARE_WAIT_QUEUE_HEAD(wq);

int major = 0;

static char mydata[100];

static int triggered = 0;

static struct class *mydev_class;

static struct hrtimer hr_timer;
static ktime_t ktime_interval;
static s64 starttime_ns;

static enum hrtimer_restart my_hrtimer_callback( struct hrtimer *timer )
{
    static int n=0;
    static int min=1000000000, max=0, sum=0;
    int latency;
    s64 now_ns = ktime_to_ns(ktime_get());
    hrtimer_forward(&hr_timer, hr_timer._softexpires, ktime_interval);
    //next call relative to expired timestamp
    // calculate some statistics values...
    n++;
    latency = now_ns - starttime_ns - n * INTERVAL_BETWEEN_CALLBACKS;
    sum += latency/1000;
    if (min>latency) min = latency;
    if (max<latency) max = latency;
    printk("mod_hrtimer: my_hrtimer_callback called after %dus.\n", (int) (now_ns - starttime_ns)/1000 );

    if (n < NR_ITERATIONS)
        return HRTIMER_RESTART;
    else {
        snprintf(mydata, sizeof(mydata), "mod_hrtimer: my_hrtimer_callback: statistics latences over %d hrtimer callbacks: min=%dus, max=%dus, mean=%dus\n", n, min/1000, max/1000, sum/n);
        printk(mydata);
        triggered = 1;
        wake_up_interruptible_sync(&wq);
        n=0,min=1000000000, max=0, sum=0;
        return HRTIMER_NORESTART;
    }
}

static int my_open(struct inode *inode, struct file *filp) {
    return 0;
    // SUCCESS zurueckmelden
}

static int my_release(struct inode *inode, struct file *filp) {
    return 0;
    // SUCCESS zurückmelden
}

static ssize_t my_read(struct file *filp, char *buf, size_t count, loff_t *f_pos) {
    //get the current time as high resolution timestamp, convert it to ns
    starttime_ns = ktime_to_ns(ktime_get());
    //activate the high resolution timer including callback function...
    hrtimer_start( &hr_timer, ktime_interval, HRTIMER_MODE_REL );

    // wait for measurement to be completed
    wait_event_interruptible(wq, triggered);
    triggered = 0;

    if (strnlen(mydata,sizeof(mydata)) < count)
        // mehr Daten angefordert als in mydata[] vorhanden?
        count = strnlen(mydata,sizeof(mydata));
    // wenn ja, count entsprechend dezimieren
    __copy_to_user (buf, mydata, count);
    // Daten aus Kernel- in Userspace kopieren
    mydata[0] = 0;
    // und lokale Daten „loeschen“ womit naechstes read() EOF
    return count;
    // Zurueckmelden wieviele Bytes effektiv geliefert werden
}
static ssize_t my_write(struct file *filp, const char *buf, size_t count, loff_t *f_pos) {
    if (sizeof(mydata)<count)
        // mydata[] Platz fuer gelieferte Daten?
        count = sizeof(mydata);
    // wenn nicht entsprehchend begrenzen!
    __copy_from_user(mydata, buf, count);
    // Daten aus User- in Kernel-space kopieren
    return count;
    // Zurückmelden wieviele Bytes effektiv konsumiert wurden
}


static struct file_operations my_fops = {
    .owner = THIS_MODULE,
    .read = my_read,
    .write = my_write,
    .open = my_open,
    .release = my_release,
};

static int init_module_hrtimer(void)
{
    major = register_chrdev(0, "mod_hrtimer_dev", &my_fops);

    mydev_class = class_create(THIS_MODULE, "mod_hrtimer_dev");
    device_create(mydev_class, NULL, MKDEV(major, 0), NULL, "mod_hrtimer_dev");

    if (major < 0) {
        printk("mod_hr_timer: error, cannot register the character device\n");
        return major;
    }
    printk("initialized with major device number: %d\n", major);

    printk("mod_hrtimer: installing module...\n");
    //define a ktime variable with the interval time defined on top of this file
    ktime_interval = ktime_set( 0, INTERVAL_BETWEEN_CALLBACKS );
    //init a high resolution timer named 'hr_timer'
    hrtimer_init( &hr_timer, CLOCK_MONOTONIC, HRTIMER_MODE_REL );
    //set the callback function for this hr_timer
    hr_timer.function = &my_hrtimer_callback;
    //get the current time as high resolution timestamp, convert it to ns
    starttime_ns = ktime_to_ns(ktime_get());
    //activate the high resolution timer including callback function...
    hrtimer_start( &hr_timer, ktime_interval, HRTIMER_MODE_REL );
    printk( "mod_hrtimer: started timer callback function to fire every %lldns (current jiffies=%ld, HZ=%d)\n",
    INTERVAL_BETWEEN_CALLBACKS, jiffies, HZ );

    return 0;
}

static void cleanup_module_hrtimer(void)
{
    int ret;

    device_destroy(mydev_class, MKDEV(major,0));
    class_unregister(mydev_class);
    class_destroy(mydev_class);
    unregister_chrdev(major, "mod_hrtimer_dev");
    
    ret = hrtimer_cancel( &hr_timer );
    if (ret)
        printk("mod_hrtimer: The timer was still in use...\n");
    printk("mod_hrtimer: HR Timer module uninstalling\n");
}

module_init(init_module_hrtimer);
module_exit(cleanup_module_hrtimer);
