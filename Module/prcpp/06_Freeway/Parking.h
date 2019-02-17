#pragma once

#include <forward_list>
#include <memory>
#include "Car.h"

using namespace std;

class Parking {
	forward_list<unique_ptr<Car>> m_freeList;

public:
	void parkCar(unique_ptr<Car>&& c) {
		m_freeList.push_front(move(c));
	}

	/*void parkCar(unique_ptr<Car>& c) {
		m_freeList.push_front(c);
	}*/

	unique_ptr<Car> getCar() {
		if (m_freeList.empty()) {
			// Keine verfügbaren Autos im Parking
			return make_unique<Car>();
		} else {
			unique_ptr<Car> c = move(m_freeList.front());
			m_freeList.pop_front();
			return c;
		}
	}

};
