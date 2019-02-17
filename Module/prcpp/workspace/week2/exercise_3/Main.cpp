#include <iostream>

using namespace std;

void swapByPointer(int *a, int *b) {
    int tmp = *a;
    *a = *b;
    *b = tmp;
}

void swapByReference(int &a, int &b) {
    int tmp = a;
    a = b;
    b = tmp;
}

int main(int argc, char *argv[]) {
    int a = 5, b = 10;
    swapByPointer(&a, &b);
    cout << a << " " << b << endl;

    a = 5, b = 10;
    swapByReference(a, b);
    cout << a << " " << b << endl;

    return 0;
}
