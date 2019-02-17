#include "Point.h"

Point::Point(int x, int y) {
    m_x = x;
    m_y = y;
}

int Point::getX() const {
    return m_x;
}

int Point::getY() const {
    return m_y;
}

void Point::setX(const int x) {
    m_x = x;
}

void Point::setY(const int y) {
    m_y = y;
}
