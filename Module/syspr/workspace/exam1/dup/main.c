#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>
#include <sys/stat.h>

int display_file(int filehandle) {
    if (filehandle == -1) {
        perror("Unable to use the invalid file handle");
        return EXIT_FAILURE;
    }

    char buffer[1024 * 1024 * 2] = {0};

    read(filehandle, buffer, sizeof(buffer));
    printf("Buffer from file handle %d is: %s\n", filehandle, buffer);
    return EXIT_SUCCESS;
}

int main(void) {
    char path[] = "/etc/passwd";

    int file1 = open(path, O_RDONLY);
    if(display_file(file1)) {
        perror("Unable to read the file with the first file handle");
        return EXIT_FAILURE;
    }

    int file2 = dup(file1);
    close(file1);
    if(display_file(file2)) {
        perror("Unable to read the file with the second file handle");
        return EXIT_FAILURE;
    }
    return EXIT_SUCCESS;
}
