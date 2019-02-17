#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>

int main(void) {
    int p[2];

    char data[] = "Hello world!\n";
    char buffer[] = {0};

    if (pipe(p) == -1) {
        perror("Unable to create the pipe");
        return EXIT_FAILURE;
    }

    int pid = fork();
    if (pid == -1) {
        perror("Unable to fork the process");
        return EXIT_FAILURE;
    }

    if (pid == 0) {
        close(p[0]);
        write(p[1], data, strlen(data));
    } else {
        close(p[1]);
        read(p[0], buffer, sizeof(buffer));
        printf("Pipe value is: %s\n", buffer);
    }

    return EXIT_SUCCESS;
}
