#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>

int main(int argc, char *argv[]) {

    if (argc != 3) {
        printf("Usage: week9_exercise4 <Source> <Destination>\n");
        return EXIT_SUCCESS;
    }

    int pid1 = fork();
    if (pid1 == -1) {
        perror("Unable to fork the application");
        return EXIT_FAILURE;
    }

    if (pid1 == 0) {
        execlp("cp", "cp", argv[1], argv[2]);
        return EXIT_FAILURE;
    } else {
        int status = 0;
        waitpid(pid1, &status, 0);
        printf("Exit code of the child process: %d\n", status);
    }
}
