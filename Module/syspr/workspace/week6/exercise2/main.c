#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>

int main(void) {
    int pid = fork();
    if(pid == -1) {
        perror("Unable to fork the process");
        exit(EXIT_FAILURE);
    }

    if(pid == 0) {
        execl("/bin/ls", "/bin/ls", "-al", "/home");
    } else {
        int status = 0;
        printf("Wait for process\n");
        waitpid(pid, &status, 0);
        printf("Status of the process was: %d\n", status);
    }
    return EXIT_SUCCESS;
}
