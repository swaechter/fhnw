#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/stat.h>

#define include 1

int main(int argc, char *argv[]) {

    if (argc != 2) {
        printf("Usage: week5_myrmdir <Target>\n");
        return EXIT_FAILURE;
    }

    struct stat stats = {0};

    if (stat(argv[1], &stats) == -1) {
        printf("The target directory does not exist\n");
        return EXIT_FAILURE;
    }

    if (rmdir(argv[1])) {
        perror("Failed to delete the target directory");
        return EXIT_FAILURE;
    }

    return EXIT_SUCCESS;
}
