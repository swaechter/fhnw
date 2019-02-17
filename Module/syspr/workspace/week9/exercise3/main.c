#include <stdio.h>
#include <stdlib.h>

int sum_by_value(int data[], int length) {
    int sum = 0;
    int *ptr = data;
    for (length; length > 0; length--) {
        sum += *ptr;
        ptr++;
    }
    return sum;
}

int sum_by_reference(int *data, int length) {
    int sum = 0;
    int *ptr = data;
    for (length; length > 0; length--) {
        sum += *ptr;
        ptr++;
    }
    return sum;
}

int main(void) {
    int value1 = 42;
    int *ptrvalue1 = &value1;
    printf("ptrvalue1: %p | *ptrvalue: %d | value1: %d\n", &ptrvalue1, *ptrvalue1, value1);

    int i = 0;
    int value2[100] = {0};
    for (i; i < 100; i++) {
        value2[i] = i;
    }
    int *ptrvalue2 = value2;
    int *ptrvalue3 = ptrvalue2 + 1;
    printf("ptrvalue2: %d | value2[0]: %d\n", *ptrvalue2, value2[0]);
    printf("ptrvalue3: %d | value2[1]: %d\n", *ptrvalue3, value2[1]);

    int data[] = {1, 2, 3};
    printf("Sum by value: %d\n", sum_by_value(data, 3));
    printf("Sum by reference: %d\n", sum_by_reference(data, 3));
    return EXIT_SUCCESS;
}
