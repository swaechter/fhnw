#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>
#include <sys/stat.h>

int main(void) {
    char path[] = "pipe.tmp";
    char data[] = "Hello world!\n";
    char buffer[] = {0};

    // Alternativ: mknod(path, 010777, 0);
    if (mkfifo(path, 010777)) {
        perror("Unable to create the named pipe");
        return EXIT_FAILURE;
    }

    int pid = fork();
    if (pid == -1) {
        perror("Unable to fork the process");
        return EXIT_FAILURE;
    }

    if (pid == 0) {
        int file = open(path, O_RDONLY);
        if (file == -1) {
            perror("Unable to read from the named pipe");
            return EXIT_FAILURE;
        }
        read(file, buffer, sizeof(buffer));
        printf("Output from the pipe: %s\n", buffer);
        close(file);
    } else {
        int file = open(path, O_WRONLY);
        if(file == -1) {
            perror("Unable to write to the named pipe");
            return EXIT_FAILURE;
        }
        write(file, data, sizeof(data));
        close(file);
    }

    return EXIT_SUCCESS;
}
