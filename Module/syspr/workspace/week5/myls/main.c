#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <dirent.h>

int display_directory(char *path) {

    DIR *directory = opendir(path);
    if (directory == NULL) {
        perror("Unable to access the directory");
        return EXIT_FAILURE;
    }

    struct dirent *entries;

    while ((entries = readdir(directory))) {

        char filepath[PATH_MAX] = {0};
        sprintf(filepath, "%s/%s", path, entries->d_name);

        printf("%s\n", filepath);

        if (strcmp(entries->d_name, ".") == 0 || strcmp(entries->d_name, "..") == 0) {
            continue;
        }

        if (entries->d_type == DT_DIR) {
            display_directory(filepath);
        }
    }

    closedir(directory);

    return EXIT_SUCCESS;
}

int main(int argc, char *argv[]) {

    if (argc != 2) {
        printf("Usage: week5_myls <Source>\n");
        return EXIT_FAILURE;
    }

    char path[PATH_MAX] = {0};
    realpath(argv[1], path);

    if (display_directory(path)) {
        perror("An error occurred");
    }

    return EXIT_SUCCESS;
}
