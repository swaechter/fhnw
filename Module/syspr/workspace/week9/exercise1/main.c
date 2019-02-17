#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>

#define ENABLE_LOGGING 1

#define MAX_VALUE 100

#define IS_PRIME is_prime(i)

bool is_prime(int num) {
    if (num <= 1) return 0;
    if (num % 2 == 0 && num > 2) return 0;
    for (int i = 3; i < num / 2; i += 2) {
        if (num % i == 0)
            return 0;
    }
    return 1;
}

int main(void) {
    int i = 0;
    for (i; i < MAX_VALUE; i++) {
        if (IS_PRIME) {
            if (ENABLE_LOGGING == 1) {
                printf("Prime number: %d\n", i);
            }
        }
    }
    return EXIT_SUCCESS;
}
