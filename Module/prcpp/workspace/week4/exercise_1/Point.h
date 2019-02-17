#ifndef WORKSPACE_POINT_H
#define WORKSPACE_POINT_H

class Point {

private:
    int m_x, m_y;

public:
    Point(int x = 0, int y = 0);

    int getX() const;

    int getY() const;

    void setX(const int x = 0);

    void setY(const int y = 0);
};

#endif //WORKSPACE_POINT_H
