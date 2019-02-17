#include <iostream>

using namespace std;

int *mySmartFunc() {
    int value = 42;
    return &value;
}

int main(int argc, char *argv[]) {
    int *value = mySmartFunc();
    cout << *value << endl;
    return 0;
}
