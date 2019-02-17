#include <iostream>

using namespace std;

int *fillArray(int array[], int length) {
    for (int i = 0; i < length; i++) {
        array[i]++;
    }
    return array;
}

int main(int argc, char *argv[]) {
    int data[] = {0, 1, 2, 3, 4};
    int length = sizeof(data) / sizeof(int);
    int *dataptr = fillArray(data, length);

    for (int &i : data) {
        cout << "For: " << i << endl;
    }

    for (int i = 0; i < length; i++) {
        cout << "Array: " << dataptr[i] << endl;
    }

    return 0;
}
