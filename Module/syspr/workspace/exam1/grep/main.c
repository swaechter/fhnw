#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>

int main(void) {
    printf("Parent process started\n");

    int pid = fork();
    if (pid == -1) {
        perror("Unable to fork the process");
        return EXIT_FAILURE;
    }

    if (pid == 0) {
        execl("/bin/grep", "grep", (char *) 0);
    } else {
        printf("Going to terminate the parent process.\n");
        wait(0);
    }

    return EXIT_SUCCESS;
}
