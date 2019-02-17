#include <iostream>

using namespace std;

typedef unsigned int COLORREF;

typedef unsigned char BYTE;

struct DebugeImage {
    int channels;
    int width;
    int height;
    int pitch;
    BYTE *data;
};

int main(int argc, char *argv[]) {
    const int radius = 20;
    const int size = 51;
    const int halfsize = (size / 2) - 1;
    const int squareradius = radius * radius;

    COLORREF colorrefs[size][size];
    for (int y = 0; y < size; y++) {
        for (int x = 0; x < size; x++) {
            int squareddistance = (x - halfsize) * (x - halfsize) + (y - halfsize) * (y - halfsize);
            colorrefs[x][y] = (squareddistance > squareradius) ? 0xFFFFFF00 : 0xFFFF0000;

        }
    }

    DebugeImage image;
    image.channels = sizeof(COLORREF);
    image.width = size;
    image.height = size;
    image.pitch = size * image.channels;
    image.data = reinterpret_cast<BYTE *>(&colorrefs);

    return 0;
}
