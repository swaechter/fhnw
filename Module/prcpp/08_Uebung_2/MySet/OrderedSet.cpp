#include "OrderedSet.h"

#include <iostream>
#include <algorithm>
#include <cstring>

// Protected size constructor
OrderedSet::OrderedSet(size_t size) : Set(size), m_start(0) {
	cout << "OrderedSet::OrderedSet(size)" << endl;
}

// Public default constructor
OrderedSet::OrderedSet() : Set(0), m_start(0) {
	cout << "OrderedSet::OrderedSet()" << endl;
}

// Public default copy constructor
OrderedSet::OrderedSet(const OrderedSet &set) : Set(set), m_start(set.m_start) {
	cout << "OrderedSet::OrderedSet(set)" << endl;
}

// Default type conversion constructor
OrderedSet::OrderedSet(const int *values, const size_t size) : Set(values, size), m_start(0) {
	sort(begin(), begin() + Set::size());
	cout << "OrderedSet::OrderedSet(values,size)" << endl;
}

// Default destructor
OrderedSet::~OrderedSet() {
	cout << "OrderedSet::~OrderedSet" << endl;
}

// Get first element
int *OrderedSet::begin() const {
	return Set::begin() + m_start;
}

// Merge two sets
Set OrderedSet::merge(const Set &set) const {
	if (isEmpty()) {
		return set;
	}

	if (set.isEmpty()) {
		return *this;
	}

	const OrderedSet *castedset = dynamic_cast<const OrderedSet *>(&set);
	if (castedset != nullptr) {
		OrderedSet result(size() + set.size());

		size_t size1 = 0;
		size_t size2 = 0;
		int next = 0;

		while (size1 < size() || size2 < set.size()) {
			if (size1 < size() && (size2 == set.size() || (*this)[size1] < set[size2])) {
				next = (*this)[size1++];
			} else {
				next = set[size2++];
			}

			if (result.size() == 0 || result[result.size() - 1] != next) {
				result[result.size()] = next;
				result.m_size++;
			}
		}
		return result;
	} else {
		return Set::merge(set);
	}
}

// Get an ordered set with smaller values
OrderedSet OrderedSet::getSmaller(int x) const {
	OrderedSet result = OrderedSet(*this);
	result.m_start = 0;
	result.m_size = 0;

	const int *const dataptr = begin();
	while (result.size() < size() && dataptr[result.size()] < x) {
		result.m_size++; // TODO: Find a better solution which respects encapsulation
	}
	return result;
}

// Get an ordered set with larger values
OrderedSet OrderedSet::getLarger(int x) const {
	OrderedSet result = OrderedSet(*this);
	result.m_size = size(); // TODO: Find a better solution which respects encapsulation

	const int *const dataptr = begin();
	while (result.size() > 0 && dataptr[result.size() - 1] > x) {
		result.m_size--; // TODO: Find a better solution which respects encapsulation
	}
	result.m_start = size() - result.size();
	return result;
}

// Statically merge two sets
Set OrderedSet::merge(const OrderedSet &set1, const Set &set2) {
	return set1.merge(set2);
}
