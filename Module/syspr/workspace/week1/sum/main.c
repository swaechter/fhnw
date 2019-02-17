#include <stdio.h>

int numbers[101] = {0};

void display_sum_a() {
    int sum = 0;
    for (int i = 0; i <= 100; i++) {
        sum += i;
    }
    printf("Sum from 1 to 100 (Method A): %d\n", sum);
}

void display_sum_b() {
    for (int i = 0; i <= 100; i++) {
        numbers[i] = i;
    }

    int sum = 0;
    for (int i = 0; i <= 100; i++) {
        sum += numbers[i];
    }
    printf("Sum from 1 to 100 (Method B): %d\n", sum);
}

int main() {
    display_sum_a();
    display_sum_b();
    return 0;
}
