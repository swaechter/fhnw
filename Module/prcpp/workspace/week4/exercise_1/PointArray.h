#ifndef WORKSPACE_POINTARRAY_H
#define WORKSPACE_POINTARRAY_H

#include <cstddef>
#include <iostream>

#include "Point.h"

using namespace std;

class PointArray {

private:
    size_t m_size, m_capacity;
    Point *m_points;

public:
    PointArray();

    PointArray(const Point pts[], const size_t size);

    PointArray(const PointArray &pv);

    ~PointArray();

    void clear();

    int getSize() const;

    void print() const;

    void pushBack(const Point &p);

    void insert(const size_t pos, const Point &p);

    void remove(const size_t pos);

    bool get(const size_t pos, Point &p) const;

    Point *at(const size_t pos);

    const Point *at(const size_t pos) const;

private:
    void resize(size_t capacity);
};

#endif //WORKSPACE_POINTARRAY_H
