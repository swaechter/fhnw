#include <stdio.h>
#include <stdlib.h>
#include <signal.h>
#include <unistd.h>
#include <sys/wait.h>

void old_signal_handler(int signum) {
    printf("Hello from the old signal handler of process ID %d\n", getpid());
}

void new_signal_handler(int signum) {
    printf("Hello from the new signal handler of process ID %d\n", getpid());
}

int main(void) {
    signal(SIGALRM, old_signal_handler);

    int pid = fork();
    if(pid == 0) {
        alarm(1); // Old handler
        sleep(2);
        sleep(2 + 2 + 2 + 1); // Wait until the parent has his new signal handler
        alarm(1); // Still old handler
        sleep(2);
        execlp("ls", "ls", "/home");
    } else {
        alarm(1); // Old handler
        sleep(2);
        signal(SIGALRM, new_signal_handler);
        alarm(1); // New handler
        sleep(2);
        wait(0);
    }
    return EXIT_SUCCESS;
}
