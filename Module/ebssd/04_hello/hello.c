#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

int main(void) {
    char hostname[1024];
    gethostname(hostname, 1024);
    printf("%s\n", hostname);
    return EXIT_SUCCESS;
}
