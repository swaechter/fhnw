#include <iostream>
#include <cmath>

using namespace std;

void sinus(int a, double b) {
    double bb = b / 6.28;
    for (int y = a; y >= -a; y--) {
        for (int x = 0; x < b; x++) {
            cout << ((int) round(a * sin(x / bb)) == y ? '*' : ' ');
        }
        cout << endl;
    }
}

int main(int argc, char *argv[]) {
    sinus(50, 100);
    return 0;
}
