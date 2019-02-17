#ifndef ORDEREDSET_H
#define ORDEREDSET_H

#include <memory>
#include <cstddef>

#include "MySet.h"

using namespace std;

class OrderedSet : public Set {

private:

	size_t m_start;

protected:

	OrderedSet(size_t size); // Protected size constructor

	virtual Set merge(const Set &set) const override; // Merge two sets

public:

	OrderedSet(); // Public default constructor

	OrderedSet(const OrderedSet &set); // Public default copy constructor

	OrderedSet(const int *values, const size_t size); // Default type conversion constructor

	virtual ~OrderedSet(); // Default destructor

	int *begin() const override; // Get first element

	OrderedSet getSmaller(int x) const; // Get an ordered set with smaller values

	OrderedSet getLarger(int x) const; // Get an ordered set with larger values

	static Set merge(const OrderedSet &set1, const Set &set2); // Statically merge two sets
};

#endif // ORDEREDSET_H
