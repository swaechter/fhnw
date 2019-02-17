#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>

int main(int argc, char* argv[]) {
    if(argc != 2) {
        printf("Usage: week6_myls <Source>\n");
        return EXIT_FAILURE;
    }
    int pid = fork();
    if (pid == -1) {
        perror("Unable to fork the process");
        exit(EXIT_FAILURE);
    }

    if (pid == 0) {
        execlp("ls", "ls", "-al", argv[1]);
    } else {
        wait(0);
    }
    return EXIT_SUCCESS;
}
