#include <stdio.h>
#include <stdlib.h>
#include <sys/stat.h>

int main(int argc, char *argv[]) {

    if (argc != 2) {
        printf("Usage: week5_mymkdir <Target>\n");
        return EXIT_FAILURE;
    }

    struct stat stats = {0};

    if (stat(argv[1], &stats) != -1) {
        printf("The target directory already exists\n");
        return EXIT_FAILURE;
    }

    if (mkdir(argv[1], 0700)) {
        perror("Failed to create the target directory");
        return EXIT_FAILURE;
    }

    return EXIT_SUCCESS;
}
