#include <iostream>

#include "Point.h"
#include "PointArray.h"

using namespace std;

int main(int argc, char *argv[]) {
    Point p1(1, 2), p2(2, 3), p3(3, 4), p4(4, 5);
    Point points[] = {p1, p2, p3, p4};
    PointArray pa1;
    PointArray pa2(points, sizeof(points) / sizeof(Point));
    PointArray pa3(pa2);
    pa2.clear();

    cout << "size test" << endl;
    cout << boolalpha << (pa1.getSize() == 0) << endl;
    cout << boolalpha << (pa2.getSize() == 0) << endl;
    cout << boolalpha << (pa3.getSize() == 4) << endl;

    cout << "push-back test" << endl;
    pa3.pushBack(Point(5, 6));
    pa3.print();

    cout << "remove test" << endl;
    pa3.remove(5);
    pa3.remove(4);
    pa3.remove(0);
    pa3.remove(1);
    pa3.print();

    cout << "insert test" << endl;
    pa3.insert(0, p1);
    pa3.insert(2, p3);
    pa3.insert(4, Point(5, 6));
    pa3.print();
}
