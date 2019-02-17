#include <stdio.h>
#include <stdlib.h>
#include <signal.h>
#include <setjmp.h>

jmp_buf position;

void signal_handler(int signum) {
    printf("Invalid input. Please type in other numbers for the division\n");
    longjmp(position, 1);
}

int main(void) {
    signal(SIGFPE, signal_handler);

    if(setjmp(position) == 0) {
        printf("Please provide two numbers for the division:\n");
    }

    double a, b, c;
    scanf("%lf %lf", &a, &b);
    c = a / b;
    printf("%f / %f = %f\n", a, b, c);

    return EXIT_SUCCESS;
}
