#include <iostream>

using namespace std;

bool strcmp(char *data1, char *data2) {
    int a = 0;
    while (data1[a] != '\0') {
        a++;
    }

    int b = 0;
    while (data2[b] != '\0') {
        b++;
    }

    return a == b;
}

int main(int argc, char *argv[]) {
    char string0[] = {'J', 'a', 'a', 'v', 's', 's', '\0'};
    char string1[] = {'J', 'a', 'a', 'v', 's', '\0'};
    char string2[] = "Jaavs";
    char string3[] = {'J', 'a', 'a'};

    cout << string0 << " == " << string1 << " = " << strcmp(string0, string1) << endl;
    cout << string2 << " == " << string3 << " = " << strcmp(string2, string3) << endl;
    cout << string1 << " == " << string2 << " = " << strcmp(string1, string2) << endl;

    return 0;
}
