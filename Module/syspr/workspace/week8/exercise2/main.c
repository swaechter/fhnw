#include <stdio.h>
#include <stdlib.h>
#include <signal.h>

void signal_handler(int signum) {
    printf("Division by zero was detected\n");
    exit(EXIT_FAILURE);
}

int main(void) {
    signal(SIGFPE, signal_handler);
    int value = (2 - 2);
    int result = 0 / value;
    printf("Invalid division of 0 / 0 = %d\n", result);
    return EXIT_SUCCESS;
}
