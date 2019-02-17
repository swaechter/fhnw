#ifndef MYSET_H
#define MYSET_H

#include <memory>
#include <cstddef>

using namespace std;

class Set {

private:

	shared_ptr<int> m_values;

protected:

	size_t m_size;

protected:

	Set(size_t size); // Protected size constructor

	virtual int *begin() const; // Get first element

public:
	int &operator[](const size_t index); // Get the item at the given index

protected:
	virtual Set merge(const Set &set) const; // Merge two sets

	Set difference(const Set &set) const; // Difference two sets

	Set difference(Set &&set) const; // Difference two sets via move

	Set intersection(const Set &set) const; // Intersect two sets

	Set intersection(Set &&set) const; // Intersect two sets via move

public:

	int operator[](size_t index) const {
		return *(begin() + index);
	}

	Set(); // Public default constructor

	Set(const Set &set); // Public default copy constructor

	Set(const int *values, const size_t size); // Default type conversion constructor

	virtual ~Set(); // Default destructor

	bool contains(int element) const; // Check if an element exists

	bool containsAll(const Set &set) const; // Check if a set exists

	bool isEmpty() const; // Check if the list is empty

	size_t size() const; // Get the size

	bool operator==(const Set &set) const; // Compare two sets

	static Set merge(const Set &set1, const Set &set2); // Statically merge two sets

	static Set difference(const Set &set1, const Set &set2); // Statically differentiate two sets

	static Set intersection(const Set &set1, const Set &set2); // Statically intersect two sets

	static Set difference(Set &&set1, const Set &set2); // Statically differentiate two sets via move

	static Set difference(Set &&set1, Set &&set2); // Statically differentiate two sets via move

	static Set intersection(const Set &set1, Set &&set2); // Statically intersect two sets via move

	static Set intersection(Set &&set1, const Set &set2); // Statically intersect two sets via move

	static Set intersection(Set &&set1, Set &&set2); // Statically intersect two sets via move

	friend ostream &operator<<(ostream &stream, const Set &set); // Dump the set
};

#endif // MYSET_H
