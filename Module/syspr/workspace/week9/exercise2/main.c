#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>

int mystrlen(char *string) {
    int i = 0;
    int counter = 0;
    for (i; string[i] != 0; i++) {
        counter++;
    }
    return counter;
}

int mystrcpy(char *outputstring, char *inputstring, int length) {
    int i = 0;
    int reallength = mystrlen(inputstring);
    if (reallength > length) {
        reallength = length;
    }
    for (i; i < reallength; i++) {
        outputstring[i] = inputstring[i];
    }
    return 0;
}

int mystrcmp(char *firststring, char *secondstring) {
    int i = 0;
    int length = mystrlen(firststring);
    for (i; i < length; i++) {
        int diff = firststring[i] - secondstring[i];
        if (diff != 0) {
            return diff;
        }
    }
    return 0;
}

int main(void) {

    char data[] = {"Hello world!"};
    char newdata[sizeof(data)] = {0};

    printf("mystrlen: %d | strlen: %d\n", mystrlen(data), (int) strlen(data));

    mystrcpy(newdata, data, sizeof(data));
    printf("mystrcpy: %s\n", newdata);

    printf("mystrcmp: %d\n", mystrcmp(data, newdata));
    return EXIT_SUCCESS;
}
