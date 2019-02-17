#include "PointArray.h"

PointArray::PointArray() {
    m_size = 0;
    m_capacity = 0;
    m_points = nullptr;
}

PointArray::PointArray(const Point pts[], const size_t size) {
    m_size = size;
    m_capacity = size;
    m_points = new Point[size];
    for (size_t i = 0; i < size; i++) {
        m_points[i] = pts[i];
    }
}

PointArray::PointArray(const PointArray &pv) {
    m_size = pv.m_size;
    m_capacity = pv.m_size;
    m_points = new Point[m_size];
    for (size_t i = 0; i < m_size; i++) {
        m_points[i] = pv.m_points[i];
    }
}

PointArray::~PointArray() {
    delete[] m_points;
}

void PointArray::clear() {
    resize(0);
}

int PointArray::getSize() const {
    return (int) m_size;
}

void PointArray::print() const {
    cout << "[";
    for (int i = 0; i < m_size; i++) {
        cout << "(" << m_points[i].getX() << ", " << m_points[i].getY() << ")";
        cout << ((i != getSize() - 1) ? ", " : "");
    }
    cout << "]" << endl;
}

void PointArray::pushBack(const Point &p) {
    if (m_size == m_capacity) {
        resize(3 * m_size / 2 + 1);
    }
    m_points[m_size] = p;
    m_size++;
}

void PointArray::insert(const size_t pos, const Point &p) {
    if (m_size == m_capacity) {
        resize(3 * m_size / 2 + 1);
    }
    for (size_t i = m_size; i > pos; i--) {
        m_points[i] = m_points[i - 1];
    }
    m_points[pos] = p;
    m_size++;
}

void PointArray::remove(const size_t pos) {
    if (pos >= m_size) {
        return;
    }

    for (size_t i = pos; i < m_size - 1; i++) {
        m_points[i] = m_points[i + 1];
    }
    m_size--;
}

bool PointArray::get(const size_t pos, Point &p) const {
    if (pos < m_size) {
        p = m_points[pos];
        return true;
    } else {
        return false;
    }
}

Point *PointArray::at(const size_t pos) {
    return (pos < m_size) ? m_points + pos : nullptr;
}

const Point *PointArray::at(const size_t pos) const {
    return (pos < m_size) ? m_points + pos : nullptr;
}

void PointArray::resize(size_t capacity) {
    m_capacity = capacity;
    if (m_capacity < m_size) {
        m_size = m_capacity;
    }

    Point *points = new Point[m_capacity];
    for (size_t i = 0; i < m_size; i++) {
        points[i] = m_points[i];
    }
    delete[] m_points;
    m_points = points;
}
