#include <iostream>

using namespace std;

int main(int argc, char *argv[]) {
    char msg[10];
    char *p;

    //msg = "Bonjour"; // const char[8] kann nicht char[10] zugewiesen werden
    p = "Bonjour";

    //msg = p; // char* kann nicht char[10] zugewiesen werden
    p = msg;

    p[0] = 'H', p[1] = 'i', p[2] = '\0';

    cout << "Content of array p[] is: " << p << endl;
    return 0;
}
