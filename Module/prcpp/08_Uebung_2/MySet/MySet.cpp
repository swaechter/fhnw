#include "MySet.h"

#include <iostream>
#include <cstring>

// Protected size constructor
Set::Set(size_t size) : m_size(0), m_values(new int[size], default_delete<int[]>()) {
	cout << "Set::Set(size)" << endl;
}

// Public default constructor
Set::Set() : m_size(0) {
	cout << "Set::Set()" << endl;
}

// Public default copy constructor
Set::Set(const Set &set) : m_size(set.m_size), m_values(set.m_values) {
	cout << "Set::Set(set)" << endl;
}

// Default type conversion constructor
Set::Set(const int *values, const size_t size) : Set(size) {
	int *const dataptr = m_values.get();
	for (size_t i = 0; i < size; i++) {
		if (!contains(values[i])) {
			dataptr[m_size++] = values[i];
		}
	}
	cout << "Set::Set(values, size)" << endl;
}

// Default destructor
Set::~Set() {
	cout << "Set::~Set()" << endl;
}

// Get first element
int *Set::begin() const {
	return m_values.get(); // Nullpointer value included
}

// Get the item at the given index
int &Set::operator[](size_t index) {
	return *(begin() + index);
}

// Merge two sets
Set Set::merge(const Set &set) const {
	Set result(m_size + set.m_size);
	memcpy(result.begin(), begin(), m_size * sizeof(int));
	result.m_size = m_size;

	const int *const otherdataptr = set.begin();
	for (size_t i = 0; i < set.m_size; ++i) {
		if (!contains(otherdataptr[i])) {
			result[result.m_size++] = otherdataptr[i];
		}
	}
	return result;
}

// Difference two sets
Set Set::difference(const Set &set) const {
	Set result(set.m_size);

	const int *const otherdataptr = set.begin();
	for (size_t i = 0; i < set.m_size; ++i) {
		if (!contains(otherdataptr[i])) {
			result[result.m_size++] = otherdataptr[i];
		}
	}
	return result;
}

// Difference two sets via move
Set Set::difference(Set &&set) const {
	if (set.m_values.unique()) {
		size_t index = 0;
		const int *const otherdataptr = set.begin();
		for (size_t i = 0; i < set.size(); ++i) {
			if (!contains(otherdataptr[i])) {
				set[index++] = otherdataptr[i];
			} else {
				set[i] = 0;
			}
		}
		set.m_size = index;
		return move(set);
	} else {
		return difference(set);
	}
}

// Intersect two sets
Set Set::intersection(const Set &set) const {
	Set result(set.m_size);

	const int *const otherdataptr = set.begin();
	for (size_t i = 0; i < set.m_size; ++i) {
		if (contains(otherdataptr[i])) {
			result[result.m_size++] = otherdataptr[i];
		}
	}
	return result;
}

// Intersect two sets via move
Set Set::intersection(Set &&set) const {
	if (set.m_values.unique()) {
		size_t index = 0;
		const int *const otherdataptr = set.begin();
		for (size_t i = 0; i < set.size(); ++i) {
			if (contains(otherdataptr[i])) {
				set[index++] = otherdataptr[i];
			} else {
				set[i] = 0;
			}
		}
		set.m_size = index;
		return move(set);
	} else {
		return intersection(set);
	}
}

// Check if an element exists
bool Set::contains(int element) const {
	int index = 0;
	const int *const dataptr = begin();
	while (index < size() && dataptr[index] != element) {
		index++;
	}

	return index != size();
}

// Check if a set exists
bool Set::containsAll(const Set &set) const {
	if (isEmpty() && !set.isEmpty()) {
		return false;
	}

	int index = 0;
	const int *const dataptr = begin();
	while (index < set.size() && contains(dataptr[index])) {
		index++;
	}

	return index == set.size();
}

// Check if the list is empty
bool Set::isEmpty() const {
	return m_size == 0;
}

// Get the size
size_t Set::size() const {
	return m_size;
}

// Compare two sets
bool Set::operator==(const Set &set) const {
	return containsAll(set) && set.containsAll(*this);
}

// Statically merge two sets
Set Set::merge(const Set &set1, const Set &set2) {
	return set1.merge(set2);
}

// Statically differentiate two sets
Set Set::difference(const Set &set1, const Set &set2) {
	return set2.difference(set1);
}

// Statically intersect two sets
Set Set::intersection(const Set &set1, const Set &set2) {
	return set1.intersection(set2);
}

// Statically differentiate two sets via move
Set Set::difference(Set &&set1, const Set &set2) {
	return set2.difference(move(set1));
}

// Statically differentiate two sets via move
Set Set::difference(Set &&set1, Set &&set2) {
	return set2.difference(move(set1));
}

// Statically intersect two sets via move
Set Set::intersection(const Set &set1, Set &&set2) {
	return set1.intersection(move(set2));
}

// Statically intersect two sets via move
Set Set::intersection(Set &&set1, const Set &set2) {
	return set2.intersection(move(set1));
}

// Statically intersect two sets via move
Set Set::intersection(Set &&set1, Set &&set2) {
	return set1.intersection(move(set2));
}

// Dump the set
ostream &operator<<(ostream &stream, const Set &set) {
	const int *const dataptr = set.begin();
	stream << "{";
	if (!set.isEmpty()) {
		stream << dataptr[0];
	}

	for (size_t i = 1; i < set.size(); ++i) {
		stream << ", " << dataptr[i];
	}
	stream << "}";
	return stream;
}
