#include <iostream>

using namespace std;

void doPointers() {
    int i = 'a'; // Variabler Wert
    const int ic = i; // Konstanter Wert
    const int *pic = &ic; // Pointer auf konstanter Wert
    //int * const cpi = &ic; // Konstanter Pointer auf variabler [aber eigentlich konstanter] Wert --> Fehler
    const int *const cpic = &ic; // Konstanter Pointer auf konstanter Wert
}

void doReferences() {
    //int &i = 'a'; // Referenz auf variabler Wert --> Fehler
    int i = 'a';
    const int ic = i; // Konstanter Wert
    //const int &ric = &ic; // Referenz auf Konstante aber mit Dereferenzierung (Pointer wird zurückgegeben) --> Fehler
    //int &const rpi = &ic; // Konstante Referenz auf variabler [aber eigentlich konstanter] Wert und mit Dereferenzierung (Pointer wird zurückgegeben) --> Fehler
    //const int &const cpic = &ic; // Konstante Referenz auf konstanzer Wert aber mit Dereferenzierung (Pointer wird zurückgegeben) --> Fehler
}

int main(int argc, char *argv[]) {
    doPointers();
    doReferences();
    return 0;
}
