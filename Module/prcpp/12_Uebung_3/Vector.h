#ifndef VECTOR_H
#define VECTOR_H

#include <array>

#include "Expression.h"

#define PORTABLE_MIN(a, b)  (((a) < (b)) ? (a) : (b))

using namespace std;

template<typename T, size_t S>
class Vector: public array<T, S> {

  public:

	// Default constructor
	Vector() {
	}

	// Default constructor with initializer list
	Vector(const initializer_list<T> &data) {
		size_t s = PORTABLE_MIN(data.size(), S);
		auto it = data.begin();
		for (size_t i = 0; i < s; i++) {
			at(i) = *it++;
		}
	}

	// Constant index operator
	const T &operator[](const size_t index) const {
		return at(index);
	}

	// Non-constant index operator
	T &operator[](const size_t index) {
		return at(index);
	}

	Vector *operator*() {
		return this;
	}

	// Compare operator
	template<typename T>
	bool operator==(const T &other) {
		if (size() != other.size()) {
			return false;
		}

		size_t counter = 0;
		while (counter < size() && (*this)[counter] == other[counter]) {
			counter++;
		}
		return size() == counter;
	}

	// Assignment operator
	template<typename T>
	Vector &operator=(const T &other) {
		for (size_t i = 0; i < size(); i++) {
			(*this)[i] = other[i];
		}
		return *this;
	}

	// Stream operator
	friend ostream &operator<<(ostream &stream, const Vector &vector) {
		cout << "[";
		if (vector.size()) {
			size_t i = 0;
			for (i = 0; i < vector.size() - 1; i++) {
				cout << vector[i] << ", ";
			}
			cout << vector[i];
		}
		cout << "]";
		return stream;
	}
};

#endif // VECTOR_H
