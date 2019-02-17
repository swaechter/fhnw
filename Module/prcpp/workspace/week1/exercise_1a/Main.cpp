#include <iostream>

#define MAX(a, b) ((a) < (b) ? (b) : (a))

using namespace std;

int max(int a, int b) {
    return (a < b) ? b : a;
}

int main(int argc, char *argv[]) {
    int x = 5, y = 10;
    int z = MAX(x++, y++);
    cout << "Macro Max: " << z << endl;

    x = 5, y = 10;
    z = max(x++, y++);
    cout << "Function Max: " << z << endl;

    return 0;
}
