#include <stdio.h>
#include <unistd.h>
#include <time.h>
#include <stdlib.h>
#include <stdbool.h>
#include <string.h>
#include <fcntl.h>
#include <time.h>
#include <poll.h>

void check_error(bool condition, char *message);
void init_led();
void init_button();
void deinit_led();
void deinit_button();
void set_led_status(bool status);
int get_button_status();
void init_interrupt();

int led_file_descriptor;
int button_file_descriptor;

int main ()
{
    // Alles initialisieren
    init_led();
    init_button();
    init_interrupt();

    // Random Number Generator initialisieren
    srand(time(NULL));

    // Zufallszahl würfeln
    int seconds_to_sleep = (rand() % 3) + 1;
    printf("Going to sleep...get ready!\n");
    sleep(seconds_to_sleep);
    
    // LED aktivieren
    set_led_status(true); 

    // Startzeit nehmen
    clock_t begin_time = clock();

    // Pollen bis wir die Taste drücken
//     while(get_button_status() == 1) {
//         printf("Button still not pressed!\n");
//     }
    
    // Neu: poll verwenden um auf Software-Interrupt zu warten
    struct pollfd poll_button;
    poll_button.fd = button_file_descriptor;
    poll_button.events = POLLPRI;

    char buffer = '1';
    lseek(button_file_descriptor, 0, SEEK_SET);
    read(button_file_descriptor,(void*)&buffer, sizeof(buffer));
    poll(&poll_button, 1, 10e5);
    // Problem: CPU Time via clock stimmt nicht mehr, da poll() die Kontrolle an den Scheduler abgibt & so keine Prozesszeit konsumiert --> Ach was solls :D

    // LED deaktivieren
    set_led_status(false);

    // Endzeit nehmen
    clock_t end_time = clock();
    double time_spent = (double)(end_time - begin_time) / CLOCKS_PER_SEC;
    printf("Button was pressed after how many seconds: %f\n", time_spent);

    // Disable everything
    deinit_led();
    deinit_button();
    return 0;
}

void check_error(bool condition, char *message)
{
    if(condition) {
        perror(message);
    }
}

void init_led()
{
    // GPIO Port aktivieren
    int file_descriptor = open("/sys/class/gpio/export", O_WRONLY);
    check_error(file_descriptor == -1, "Unable to enable the GPIO port");
    
    const void *data1 = "200";
    write(file_descriptor, data1, strlen(data1));
    close(file_descriptor);

    // Datenrichtung schalten: Schreiben
    file_descriptor = open("/sys/class/gpio/gpio200/direction", O_WRONLY);
    check_error(file_descriptor == -1, "Unable to change LED GPIO direction");

    const void *data2 = "out";
    write(file_descriptor, data2, strlen(data2));
    close(file_descriptor);
    
    // LED GPIO Port öffnen
    led_file_descriptor = open("/sys/class/gpio/gpio200/value", O_WRONLY);
    check_error(led_file_descriptor == -1, "Unable to open the LED GPIO port");
    
    printf("LED initialized\n");
}

void init_button()
{
    // GPIO Port aktivieren
    int file_descriptor = open("/sys/class/gpio/export", O_WRONLY);
    check_error(file_descriptor == -1, "Unable to enable the GPIO port");
    
    const void *data1 = "191";
    write(file_descriptor, data1, strlen(data1));
    close(file_descriptor);
    
    // Datenrichtung schalten: Lesen
    file_descriptor = open("/sys/class/gpio/gpio191/direction", O_WRONLY);
    check_error(file_descriptor == -1, "Unable to change button GPIO direction");

    const void *data2 = "in";
    write(file_descriptor, data2, strlen(data2));
    close(file_descriptor);
    
    // Button GPIO Port öffnen
    button_file_descriptor = open("/sys/class/gpio/gpio191/value", O_RDWR);
    check_error(button_file_descriptor == -1, "Unable to read the button GPIO port");
    
    printf("Button initialized\n");
}

void deinit_led()
{
    // GPIO Port deaktivieren
    int file_descriptor = open("/sys/class/gpio/unexport", O_WRONLY);
    check_error(file_descriptor == -1, "Unable to disable the GPIO port");
    
    const void *data1 = "200";
    write(file_descriptor, data1, strlen(data1));
    close(file_descriptor);

    // LED file descriptor schliessen
    close(led_file_descriptor);
    
    printf("LED deinitialized\n");
}

void deinit_button()
{
    // GPIO Port deaktivieren
    int file_descriptor = open("/sys/class/gpio/unexport", O_WRONLY);
    check_error(file_descriptor == -1, "Unable to disable the GPIO port");
    
    const void *data1 = "191";
    write(file_descriptor, data1, strlen(data1));
    close(file_descriptor);

    // Button file descriptor schliessen
    close(button_file_descriptor);

    printf("Button deinitialized\n");
}

void set_led_status(bool status)
{
    if(status) {
        write(led_file_descriptor, "1\n", strlen("1\n"));
    } else {
        write(led_file_descriptor, "0\n", strlen("0\n"));
    }
}

int get_button_status()
{
    char data[1];

    lseek(button_file_descriptor, 0, SEEK_SET);
    read(button_file_descriptor, data, 1);

    if(data[0] == '1') {
        return 1;
    } else {
        return 0;
    }
}

void init_interrupt()
{
    int file_descriptor = open("/sys/class/gpio/gpio191/edge", O_RDWR);
    check_error(file_descriptor == -1, "Unable to open poll GPIO port");

    const void *data = "falling";
    write(file_descriptor, data, strlen(data));
    close(file_descriptor);

    printf("Interrupt initialized\n");
//     if (init->fd_edge<0)
//     {perror("Error open gpio191/edge (interrupt): ");
//         exit(EXIT_FAILURE);
//     }
//     if(write(init->fd_edge,(void*)INT_TYPE,sizeof(INT_TYPE))<0)
//     {perror("Error write gpio191/edge (interrupt): ");
//         exit(EXIT_FAILURE);
//     }
//     poll_bouton.fd = init->fd_valuein;
//     poll_bouton.events =POLLPRI;
}
