#include <stdio.h>

void display_numbers_a() {
    printf("Odd numbers from 1 to 99 (Method A):\n");
    for (int i = 1; i < 100; i++) {
        if (i % 2 == 1) {
            printf("%d\n", i);
        }
    }
}

void display_numbers_b() {
    printf("Odd numbers from 1 to 99 (Method B):\n");
    int i = 1;
    while (i < 100) {
        if (i % 2 == 1) {
            printf("%d\n", i);
        }
        i++;
    }
}

void display_numbers_c() {
    printf("Odd numbers from 1 to 99 (Method C):\n");
    int i = 1;
    do {
        if (i % 2 == 1) {
            printf("%d\n", i);
        }
        i++;
    } while (i < 100);
}

int main() {
    display_numbers_a();
    display_numbers_b();
    display_numbers_c();
    return 0;
}
