#include <iostream>

using namespace std;

int main(int argc, char *argv[]) {
    char s[] = "Hello World";
    int uppercase = 0;
    for (char c : s) {
        if (isupper(c)) {
            ++uppercase;
        }
    }
    std::cout << uppercase << " uppercase letters in: " << s << std::endl;
    return 0;
}
