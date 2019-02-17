#include <stdio.h>
#include <stdlib.h>

int main(int argc, char *argv[]) {

    if (argc != 3) {
        printf("Usage: week5_mycp <Source> <Target>\n");
        return EXIT_FAILURE;
    }

    FILE *source = fopen(argv[1], "r");
    if (source == NULL) {
        perror("Unable to access the source file");
        return EXIT_FAILURE;
    }

    FILE *target = fopen(argv[2], "w");
    if (target == NULL) {
        fclose(source);
        perror("Unable to access the target file");
        return EXIT_FAILURE;
    }

    char element;
    while ((element = (char) fgetc(source)) != EOF) {
        fputc(element, target);
    }

    fclose(source);
    fclose(target);

    return EXIT_SUCCESS;
}
