/* Simon Wächter */
/* Testvorlage ebssd 19hs */
/* Derived from: https://www.kernel.org/doc/Documentation/filesystems/sysfs.txt */
 
// ===== Includes =====
#include <linux/module.h>
#include <linux/init.h>
#include <linux/device.h>
#include <linux/kthread.h>
#include <linux/delay.h>

// ===== Forward Declarations =====
static int threadfunc(void *data);
static ssize_t show_myvalue(struct device *dev, struct device_attribute *attr, char *buf);
static ssize_t set_myvalue(struct device *dev, struct device_attribute *attr, const char *buf, size_t len);
static int mytest_init(void);
static void mytest_exit(void);

// ===== Kernel Information =====
MODULE_LICENSE("GPL");
MODULE_AUTHOR("Simon Wächter");

module_init(mytest_init);
module_exit(mytest_exit);

// ===== Datenstrukturen =====

// Bisherige Deklarationen
static  char mybuf[100] = "bla";
static DEVICE_ATTR(myvalue, S_IWUSR | S_IRUGO, show_myvalue, set_myvalue);  /* define a pseudo file 'myvalue' which can be read and write */
static struct class *cls;

// Teilaufgabe 1d
static int triggered = 0;
static DECLARE_WAIT_QUEUE_HEAD(wq);

// Teilaufgabe 3a
static s64 starttime_ns;
static s64 endtime_before_kthread_run;
static s64 endtime_after_waitqueue;

// ====== Funktionen =====

// Teilaufgabe 1b
static int threadfunc(void *data)
{
    int sleep_duration;

    // Teilaufgabe 3: Zeit bis vor dem/zum Starten des Kernel Threads messen
    endtime_before_kthread_run = ktime_to_ns(ktime_get());

    // Teilaufgabe 2
    sleep_duration = (int) data;
    printk("Sleep duration: %d\n", sleep_duration);
    
    // Teilaufgabe 1c
    mdelay(sleep_duration);
    
    // Teilaufgabe deblockieren - Lesen kann jetzt erfolgen
    triggered = 1;
    wake_up_interruptible_sync(&wq);
    
//     // Teilaufgabe 3: Zeit bis nach dem Warten/Zürckkommen der Waitqueue messen
//     endtime_after_waitqueue = ktime_to_ns(ktime_get());

    return 0;
}

static ssize_t show_myvalue(struct device *dev, struct device_attribute *attr, char *buf)        /* function called when reading 'myvalue' e.g. by 'cat' --> read */
{
    int delta1, delta2;
    
    printk("show_myvalue init\n");

    // Teilaufgabe 1f - Warten bis Kernelthread fertig gesleept hat & entblockt
    wait_event_interruptible(wq, triggered);
    triggered = 0;
    
    // Teilaufgabe 3: Zeit bis nach dem Warten/Zürckkommen der Waitqueue messen
    endtime_after_waitqueue = ktime_to_ns(ktime_get());

    // Teilaufgabe 3c
    delta1 = (int) (endtime_before_kthread_run - starttime_ns) / 1000;
    delta2 = (int) (endtime_after_waitqueue - starttime_ns) / 1000;

    // Test um zu schauen, ob schon etwas vorher underflowt oder so - zuerst casten und dann gleich dividieren bevor wir substrahieren
//     delta1 = (((int) endtime_before_kthread_run) / 1000) - (((int) starttime_ns) / 1000);
//     delta2 = (((int) endtime_after_waitqueue) / 1000) - (((int) starttime_ns) / 1000);
    
    /*
     * NOTE / BUG: Obwohl ich bei einer sleep_time von z.B. 5555 genau auch 5.555 Sekunden warten muss, lautet die Ausgabe leider anderst:
     * 
     * buildroot:~# echo 5555 > /sys/class/myclass/mydevice/myvalue ; time cat /sys/class/myclass/mydevice/myvalue 
     * First delta time 9, second delta time 1266651 --> Bei grösseren Werten kann er auch overflowen und negativ werden.....
     *
     * real    0m 5.55s
     * user    0m 0.00s
     * sys     0m 0.01s
         * 
     * Heisst also, da habe ich irgendwo noch einen Fehler :(
     * Ein zweites cat von myvalue blockiert komplett - warum? Der triggered Wert wurde ja nach dem Freigeben via Kernel Thread deblockiert.... + 1. cat geht ja auch :(
     */
    snprintf(mybuf, sizeof(mybuf), "First delta time %d us, second delta time %d us\n", delta1, delta2);
    printk(mybuf);

    printk("show_myvalue exit\n");

    return sprintf(buf,"%s\n", mybuf);        /* copy mybuf to buf and return with return value set to nr of characters sent */
}

static ssize_t set_myvalue(struct device *dev, struct device_attribute *attr, const char *buf, size_t len)        /* function called when writing to 'myvalue' e.g. by 'echo' --> write */
{
    int sleep_duration = 0;

    printk("set_myvalue init\n");

    // Teilaufgabe 2b
    // Eigentlich sollten sie auch snprintf verwenden, dann wäre aber Aufgabenstellung 3 klar ;)
    sprintf(mybuf, "%s", buf); // sprintf(mybuf, sizeof(mybuf), "%s", buf); 
    sscanf(mybuf, "%d", &sleep_duration);   

    // Teilaufgabe 3: Zeit ab Start von Wert setzen/Vor Starten des Kernel Threads
    starttime_ns = ktime_to_ns(ktime_get());

    // Teilaufgabe 1
    kthread_run(&threadfunc, (void *) sleep_duration, "threadfunc");
    
    printk("set_myvalue exit\n");

    return len;
}

static int mytest_init(void)
{
    struct device *mydev;   

    printk("mytest_init started\n");
    cls=class_create(THIS_MODULE, "myclass");              /* create sysfs class 'myclass' */
    mydev = device_create(cls, 0, 0, NULL, "mydevice");    /* create device directory 'mydevice' under this class */
    if(sysfs_create_file(&(mydev->kobj), &dev_attr_myvalue.attr))    /* create the device attribute 'myvalue' under this device */
         return -1;
    
    printk("mytest_init ended\n");
    return 0;
}


static void mytest_exit(void)
{
    printk("mytest_exit started\n");
    device_destroy(cls, 0);
    class_destroy(cls);
    printk("mytest_exit ended\n");
}
