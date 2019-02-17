#include <iostream>

using namespace std;

int main(int argc, char *argv[]) {
    //double &ref = 42; // Fehler
    const double &ref = 42; // Ok
    return 0;
}
