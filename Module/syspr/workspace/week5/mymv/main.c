#include <stdio.h>
#include <stdlib.h>
#include <sys/stat.h>

int main(int argc, char *argv[]) {

    if (argc != 3) {
        printf("Usage: week5_mymv <Source> <Target>\n");
        return EXIT_FAILURE;
    }

    struct stat sourcestats = {0};
    struct stat targetstats = {0};

    if (stat(argv[1], &sourcestats) == -1) {
        printf("The source file does not exists\n");
        return EXIT_FAILURE;
    }

    if (stat(argv[1], &targetstats) == 1) {
        printf("The target file does already exist\n");
        return EXIT_FAILURE;
    }

    if (!S_ISREG(sourcestats.st_mode)) {
        printf("The source path is not a regular file\n");
        return EXIT_FAILURE;
    }

    if (rename(argv[1], argv[2])) {
        perror("Unable to rename the file");
        return EXIT_FAILURE;
    }

    return EXIT_SUCCESS;
}
