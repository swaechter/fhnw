#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>

int main(void) {
    int pid = fork();
    if (pid == -1) {
        perror("Unable to fork the process");
        exit(EXIT_FAILURE);
    }

    if (pid == 0) {
        printf("Type: Child | Process ID: %d | Parent process ID: %d\n", getpid(), getppid());
    } else {
        printf("Type: Parent | Process ID: %d | Parent process ID: %d\n", getpid(), getppid());
        wait(0);
    }
    return EXIT_SUCCESS;
}
