#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <pthread.h>
#include <stdarg.h>
#include <sys/wait.h>

#define BUFFER_SIZE 40

static pthread_mutex_t printf_mutex;

int sync_printf(const char *format, ...) {
    va_list args;
    va_start(args, format);
    pthread_mutex_lock(&printf_mutex);
    vprintf(format, args);
    pthread_mutex_unlock(&printf_mutex);
    va_end(args);
}

void clear_buffer(char *buffer) {
    memset(buffer, 0, BUFFER_SIZE);
}

void prepare_buffer(char *buffer, int counter) {
    clear_buffer(buffer);
    sprintf(buffer, "Hello %d", counter);
}

int main(void) {
    int p1[2];
    int p2[2];

    int counter1 = 0;
    int counter2 = 0;
    int maxcounter = 5;

    pthread_mutex_init(&printf_mutex, NULL);

    if (pipe(p1)) {
        perror("Unable to create the first pipe");
        return EXIT_FAILURE;
    }

    if (pipe(p2)) {
        perror("Unable to create the second pipe");
        return EXIT_FAILURE;
    }

    int pid1 = fork();
    if (pid1 == 0) {
        char buffer[BUFFER_SIZE] = {0};
        close(p1[0]);
        close(p2[1]);
        while (counter1 != maxcounter) {
            prepare_buffer(buffer, counter1);
            sync_printf("1 -> 2: Send data: %s\n", buffer);
            write(p1[1], buffer, sizeof(buffer));

            clear_buffer(buffer);
            read(p2[0], buffer, sizeof(buffer));
            sync_printf("1 -> 2: Read data: %s\n", buffer);

            counter1++;
        }
        return EXIT_SUCCESS;
    }

    int pid2 = fork();
    if (pid2 == 0) {
        char buffer[BUFFER_SIZE] = {0};
        close(p1[1]);
        close(p2[0]);
        while (counter2 != maxcounter) {
            clear_buffer(buffer);
            read(p1[0], buffer, sizeof(buffer));
            sync_printf("2 -> 1: Read data: %s\n", buffer);

            prepare_buffer(buffer, counter2);
            sync_printf("2 -> 1: Send data: %s\n", buffer);
            write(p2[1], buffer, sizeof(buffer));

            counter2++;
        }
        return EXIT_SUCCESS;
    }

    wait(0);
    return EXIT_SUCCESS;
}
