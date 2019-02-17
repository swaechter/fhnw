#ifndef EXPRESSION_H
#define EXPRESSION_H

#include "Op.h"

using namespace std;

template<typename Left, typename Op, typename Right>
class Expression {

  private:

	const Left &m_left;

	const Right &m_right;

  public:

	// Typedef for the template type
	typedef typename Left::value_type value_type;

	// Default constructor
	Expression(const Left &left, const Right &right) : m_left{left}, m_right{right} {
	}

	// Get the size
	size_t size() const {
		return m_left.size();
	}

	// Type operator
	value_type operator[](const size_t index) const {
		return Op::apply<value_type>(m_left[index], m_right[index]);
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

	// Stream operator
	friend ostream &operator<<(ostream &stream, const Expression &expression) {
		cout << "[";
		if (expression.size()) {
			size_t i = 0;
			for (i = 0; i < expression.size() - 1; i++) {
				cout << expression[i] << ", ";
			}
			cout << expression[i];
		}
		cout << "]";
		return stream;
	}
};

template<typename Op, typename Right>
class Expression<double, Op, Right> {

private:

	const double &m_left;

	const Right &m_right;

public:

	// Typedef for the template type
	typedef typename Right::value_type value_type;

	// Default constructor
	Expression(const double &left, const Right &right) : m_left{ left }, m_right{ right } {
	}

	// Get the size
	size_t size() const {
		return m_right.size();
	}

	// Type operator
	value_type operator[](const size_t index) const {
		return Op::apply<value_type>(m_left, m_right[index]);
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

	// Stream operator
	friend ostream &operator<<(ostream &stream, const Expression &expression) {
		cout << "[";
		if (expression.size()) {
			size_t i = 0;
			for (i = 0; i < expression.size() - 1; i++) {
				cout << expression[i] << ", ";
			}
			cout << expression[i];
		}
		cout << "]";
		return stream;
	}
};

template<typename Left, typename Op>
class Expression<Left, Op, double> {

private:

	const Left &m_left;

	const double &m_right;

public:

	// Typedef for the template type
	typedef typename Left::value_type value_type;

	// Default constructor
	Expression(const Left &left, const double &right) : m_left{ left }, m_right{ right } {
	}

	// Get the size
	size_t size() const {
		return m_left.size();
	}

	// Type operator
	value_type operator[](const size_t index) const {
		return Op::apply<value_type>(m_left[index], m_right);
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

	// Stream operator
	friend ostream &operator<<(ostream &stream, const Expression &expression) {
		cout << "[";
		if (expression.size()) {
			size_t i = 0;
			for (i = 0; i < expression.size() - 1; i++) {
				cout << expression[i] << ", ";
			}
			cout << expression[i];
		}
		cout << "]";
		return stream;
	}
};

template<typename Left, typename Right>
Expression<Left, Addition, Right> operator+(const Left &left, const Right &right) {
	return Expression<Left, Addition, Right>(left, right);
}

template<typename Left, typename Right>
Expression<Left, Substraction, Right> operator-(const Left &left, const Right &right) {
	return Expression<Left, Substraction, Right>(left, right);
}

template<typename Left, typename Right>
Expression<Left, Multiplication, Right> operator*(const Left &left, const Right &right) {
	return Expression<Left, Multiplication, Right>(left, right);
}

template<typename Left, typename Right>
Expression<Left, Division, Right> operator/(const Left &left, const Right &right) {
	return Expression<Left, Division, Right>(left, right);
}

#endif // EXPRESSION_H
