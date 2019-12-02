#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>

void init_led();
void init_button();
void deinit_led();
void deinit_button();

int main ()
{
    init_led();
    init_button();

    for(int i = 0; i < 5; i++)
    {
        // Wert auf GPIO schreiben: LED an
        system("echo '1' >/sys/class/gpio/gpio200/value");

        // Tasterwert von GPIO lesen: Taster gedrückt oder nicht
        system("cat /sys/class/gpio/gpio191/value");
        usleep(100000);

        // Wert auf GPIO schreiben: LED aus
        system("echo '0' >/sys/class/gpio/gpio200/value");

        // Tastenwert von GPIO lesen: Taster gedrückt oder nicht
        system("cat /sys/class/gpio/gpio191/value");
        usleep(100000);
    }

    deinit_led();
    deinit_button();
    return 0;
}


void init_led()
{
    // GPIO Port aktivieren
    system("echo 200 >/sys/class/gpio/export");

    // Datenrichtung schalten: Schreiben
    system("echo 'out' >/sys/class/gpio/gpio200/direction");
}

void init_button()
{
    // GPIO Port aktivieren
    system("echo 191 >/sys/class/gpio/export");

    // Datenrichtung schalten: Lesen
    system("echo 'in' >/sys/class/gpio/gpio191/direction");
}

void deinit_led()
{
    // GPIO Port deaktivieren
    system("echo 200 >/sys/class/gpio/unexport");
}

void deinit_button()
{
    // GPIO Port deaktivieren
    system("echo 191 >/sys/class/gpio/unexport");
}
