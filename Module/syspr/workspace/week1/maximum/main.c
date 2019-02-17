#include <stdio.h>

#define MAXIMUM 5

int main() {
    int i;
    float j = MAXIMUM;
    float *p;

    for (i = MAXIMUM; i >= 0; i--) {
        j = j / i;
        printf("i = %d, j = %f\n", i, j);
    }
    *p = i;
    printf("i=%f\n", p);
}
