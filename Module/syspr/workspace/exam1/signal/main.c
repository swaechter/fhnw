#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <signal.h>

void signal_handler(int signum) {
    printf("Signal: %d\n", signum);
}

int main(void) {
    signal(SIGINT, &signal_handler);

    printf("Going to sleep for the first time\n");
    sleep(10); // Press Ctrl + C to trigger the signal handler

    return EXIT_SUCCESS;
}
