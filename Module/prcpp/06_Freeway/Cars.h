#pragma once

#include <list>
#include <memory>
#include "Car.h"
#include "Freeway.h"

using namespace std;

class Simulation;

class Cars {
	Simulation& m_sim;
	Freeway& m_fw;
	list<unique_ptr<Car>> m_cars;

public:
	Cars(Simulation& sim, Freeway& fw) : m_sim(sim), m_fw(fw) {}

	auto begin() -> decltype(m_cars.begin()) { return m_cars.begin(); }
	auto end() -> decltype(m_cars.end()) { return m_cars.end(); }
	auto size() -> decltype(m_cars.size()) { return m_cars.size(); }
	auto clear() -> decltype(m_cars.clear()) { return m_cars.clear(); }

	void addCar(unique_ptr<Car>& pCar);
	void moveCar(list<unique_ptr<Car>>::iterator& it);

private:
	list<unique_ptr<Car>>::const_iterator carInFront(list<unique_ptr<Car>>::const_iterator cit, size_t lane) const;
	list<unique_ptr<Car>>::const_iterator carBehind(list<unique_ptr<Car>>::const_iterator cit, size_t lane) const;
	bool freeSight(list<unique_ptr<Car>>::iterator it, Meters minDist, size_t lane, bool forward, Meters& dist) const;
	
	bool driveCar(list<unique_ptr<Car>>::iterator it);
};