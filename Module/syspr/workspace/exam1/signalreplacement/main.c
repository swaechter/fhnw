#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <signal.h>

static int timedout;

void (*oldsignal)();

void signal_handler(int signum) {
    timedout = 1;
    signal(SIGALRM, signal_handler);
}

int main(void) {
    oldsignal = signal(SIGALRM, signal_handler);

    timedout = 0;

    alarm(3);
    printf("Time critical section\n");
    sleep(4);

    alarm(0);

    if (timedout == 1) {
        printf("Time overrun\n");
    } else {
        signal(SIGALRM, oldsignal);
        printf("Just in time");
    }

    return EXIT_SUCCESS;
}
