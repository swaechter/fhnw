#ifndef OP_H
#define OP_H

struct Addition {
	template<typename T>
	static T apply(T left, T right) {
		return left + right;
	}
};

struct Substraction {
	template<typename T>
	static T apply(T left, T right) {
		return left - right;
	}
};

struct Multiplication {
	template<typename T>
	static T apply(T left, T right) {
		return left * right;
	}
};

struct Division {
	template<typename T>
	static T apply(T left, T right) {
		return left / right;
	}
};

#endif // OP_H
