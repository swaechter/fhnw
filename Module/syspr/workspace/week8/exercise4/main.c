#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <signal.h>

void signal_handler(int signum) {
    printf("Alarm triggered\n");
}

int main(void) {
    signal(SIGALRM, signal_handler);
    alarm(1);
    sleep(2);
    return EXIT_SUCCESS;
}
