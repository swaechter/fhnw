#pragma once

#include <ratio>
#include <chrono>

// duration
typedef std::chrono::duration<double> Seconds;
typedef std::chrono::duration<double, std::ratio<60>> Minutes;
typedef std::chrono::duration<double, std::ratio<60*60>> Hours;

template<typename Scale> struct Length {
	int m_val;

	Length(int val = 0) : m_val(val) {}
	template<typename S> Length(const Length<S>& other)
		: m_val(other.m_val*(S::num*Scale::den)/(S::den*Scale::num)) {}

	Length<Scale> operator-() const { return Length<Scale>(-m_val); }
	template<typename S> int operator/(const Length<S>& other) const {
		return m_val/Length<Scale>(other).m_val;
	}
	Length<Scale> operator/(int d) const {
		return Length<Scale>(m_val/d);
	}
	Length<Scale> operator*(int d) const {
		return Length<Scale>(m_val*d);
	}
	friend Length<Scale> operator*(int d, Length<Scale> len) {
		return Length<Scale>(d*len.m_val);
	}
	template<typename S> Length<Scale> operator+(const Length<S>& other) const {
		return Length(m_val + Length<Scale>(other).m_val);
	}
	template<typename S> Length<Scale> operator-(const Length<S>& other) const {
		return Length(m_val - Length<Scale>(other).m_val);
	}
	template<typename S> bool operator==(const Length<S>& other) const {
		return m_val == Length<Scale>(other).m_val;
	}
	template<typename S> bool operator<(const Length<S>& other) const {
		return m_val < Length<Scale>(other).m_val;
	}
	template<typename S> bool operator>(const Length<S>& other) const {
		return m_val > Length<Scale>(other).m_val;
	}
	template<typename S> bool operator<=(const Length<S>& other) const {
		return m_val <= Length<Scale>(other).m_val;
	}
	template<typename S> bool operator>=(const Length<S>& other) const {
		return m_val >= Length<Scale>(other).m_val;
	}
	template<typename S> Length<Scale>& operator+=(const Length<S>& other) {
		m_val += Length<Scale>(other).m_val;
		return *this;
	}
	template<typename S> Length<Scale>& operator-=(const Length<S>& other) {
		m_val -= Length<Scale>(other).m_val;
		return *this;
	}
};

// distance
typedef Length<std::kilo> Kilometers;
typedef Length<std::ratio<1>> Meters;

template<typename Scale> struct Speed {
	double m_val;

	Speed(double val = 0) : m_val(val) {}
	template<typename S> Speed(const Speed<S>& other)
		: m_val(other.m_val*(S::num*Scale::den)/(S::den*Scale::num)) {}
	template<typename S> Speed<Scale> operator+(const Speed<S>& other) const {
		return Speed(m_val + Speed<Scale>(other).m_val);
	}
	template<typename S> Speed<Scale> operator-(const Speed<S>& other) const {
		return Speed(m_val - Speed<Scale>(other).m_val);
	}
	template<typename S> friend Seconds operator/(Length<S> len, Speed<Scale> v);
	template<typename S> friend Meters operator*(std::chrono::duration<double, S> t, Speed<Scale> v);
	template<typename S> friend Meters operator*(Speed<Scale> v, std::chrono::duration<double, S> t);
	Speed<Scale> operator*(double d) const {
		return Speed<Scale>(m_val*d);
	}
	friend Speed<Scale> operator*(double d, Speed<Scale> speed) {
		return Speed<Scale>(d*speed.m_val);
	}
	template<typename S> bool operator==(const Speed<S>& other) const {
		return m_val == Speed<Scale>(other).m_val;
	}
	template<typename S> bool operator<(const Speed<S>& other) const {
		return m_val < Speed<Scale>(other).m_val;
	}
	template<typename S> bool operator>(const Speed<S>& other) const {
		return m_val > Speed<Scale>(other).m_val;
	}
};

// speed
typedef Speed<std::ratio<1000, 3600>> Kmh;
typedef Speed<std::ratio<1>> Mps; // meter per second

template<typename S1, typename S2> Seconds operator/(Length<S1> len, Speed<S2> v) {
	const Meters& m(len);
	const Mps& mps(v);
	return Seconds(m.m_val/v.m_val);
}

template<typename S1, typename S2> Meters operator*(std::chrono::duration<double, S1> t, Speed<S2> v) {
	const Seconds& s(t);
	const Mps& mps(v);
	return Meters((int)round(s.count()*v.m_val));
}

template<typename S1, typename S2> Meters operator*(Speed<S1> v, std::chrono::duration<double, S2> t) {
	const Seconds& s(t);
	const Mps& mps(v);
	return Meters((int)round(s.count()*v.m_val));
}

template<typename S1, typename S2> Mps operator/(Length<S1> len, std::chrono::duration<double, S2> t) {
	const Meters& m(len);
	const Seconds& s(t);
	return Mps(m.m_val/s.count());
}
