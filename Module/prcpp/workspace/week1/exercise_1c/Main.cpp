#include <iostream>

#define SIZE_OF_TYPE(type) #type << " " << sizeof(type)

using namespace std;

int main(int argc, char *argv[]) {
    cout << SIZE_OF_TYPE(bool) << endl;
    cout << SIZE_OF_TYPE(char) << endl;
    cout << SIZE_OF_TYPE(short) << endl;
    cout << SIZE_OF_TYPE(int) << endl;
    cout << SIZE_OF_TYPE(long) << endl;
    cout << SIZE_OF_TYPE(long long) << endl;
    cout << SIZE_OF_TYPE(float) << endl;
    cout << SIZE_OF_TYPE(double) << endl;
    cout << SIZE_OF_TYPE(long double) << endl;
    return 0;
}
