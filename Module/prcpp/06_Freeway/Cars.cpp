#include "stdafx.h"
#include "Cars.h"
#include "Simulation.h"
#include <cassert>

// get car in front
list<unique_ptr<Car>>::const_iterator Cars::carInFront(list<unique_ptr<Car>>::const_iterator cit, size_t lane) const {
	cit++;
	while (cit != m_cars.end() && cit->get()->getLane() != lane) cit++;
	return cit;
}

// get car behind
list<unique_ptr<Car>>::const_iterator Cars::carBehind(list<unique_ptr<Car>>::const_iterator cit, size_t lane) const {
	if (cit == m_cars.cbegin()) return m_cars.cend();
	cit--;
	while (cit != m_cars.cbegin() && cit->get()->getLane() != lane) cit--;
	if (cit == m_cars.cbegin() && cit->get()->getLane() != lane) return m_cars.cend();
	return cit;
}

// add one car to simulation
void Cars::addCar(unique_ptr<Car>& pCar) {
	assert(pCar);

	Car& car = *pCar;
	Meters pos = car.getPos();

	auto cit = m_cars.cbegin();
	while (cit != m_cars.cend() && cit->get()->getPos() < pos) cit++;

	Meters pos2 = pos;

	if (cit != m_cars.cend()) {
		if (cit->get()->getLane() == car.getLane()) {
			pos2 = cit->get()->getPos() - m_sim.m_carLength;
		} else {
			auto cit2 = carInFront(cit, car.getLane());
			if (cit2 != m_cars.cend()) pos2 = cit2->get()->getPos() - m_sim.m_carLength;
		}
	}
	if (pos2 < pos) pos = pos2;
	if (pos > Meters(0)) {
		// set final car position
		car.setPos(pos);

		// insert car before cit
		m_cars.insert(cit, move(pCar));
	} else {
		// park unplaced car
		m_sim.m_parking.parkCar(move(pCar));
	}
	car.start(m_sim.m_time);
	m_sim.m_startedCars++;
}

// move one car in simulation
void Cars::moveCar(list<unique_ptr<Car>>::iterator& it) {
	Car& car = *it->get();

	// compute new car position
	if (driveCar(it)) {
		// car has been parked
		it = m_cars.erase(it);

		m_sim.m_stoppedCars++;
		m_sim.m_totalTime += (m_sim.m_time + m_sim.m_simulationStep - car.getStartTime());
	} else {
		// car has been driven to (new) position
		const Meters& pos = car.getPos();
		unique_ptr<Car> pCar = move(*it);

		auto it2 = m_cars.erase(it); // remove car from list
		it = it2;
		// prevents from endless looping
		if (it != m_cars.begin()) {
			it--;
		} else {
			it = m_cars.end();
		}

		// update car list
		while (it2 != m_cars.end() && it2->get()->getPos() < pos) it2++;
		m_cars.insert(it2, move(pCar));
		if (it != m_cars.end()) {
			it++;
		} else {
			it = m_cars.begin();
		}
	}
}

bool Cars::freeSight(list<unique_ptr<Car>>::iterator it, Meters minDist, size_t lane, bool forward, Meters& dist) const {
	dist = minDist;
	auto cit2 = (forward) ? carInFront(it, lane) : carBehind(it, lane);
	if (cit2 == m_cars.cend()) return true;

	const Car& car2 = *cit2->get();
	dist = car2.getPos() - it->get()->getPos();
	if (forward) {
		dist = dist - m_sim.m_carLength;
	} else {
		dist = -dist - m_sim.m_carLength;
	}
	return dist >= minDist;
}

bool Cars::driveCar(list<unique_ptr<Car>>::iterator it) {
	Car& car = *it->get();
	Position position = car.getPosition();
	Mps speed = m_fw.carSpeed(car);
	Meters maxDist = speed*m_sim.m_simulationStep;
	auto cit2 = carInFront(it, position.m_lane);

	if (cit2 == m_cars.cend()) {
		// no car in front
		// TODO : compute new position and sector and test if end of freeway (= parking) has been reached
		// return true if car has been parked
		Meters targetPos = position.m_pos + maxDist;
		size_t sectorNr = m_fw.getSectorNr(targetPos);
		if (sectorNr >= m_fw.nSectors()) {
			m_sim.m_parking.parkCar(move(*it));
			return true;
		}
	} else {
		// car in front
		// TODO : test if car in front is close and if a change of lane is possible
		Meters dist;
		if (!freeSight(it, maxDist, position.m_lane, true, dist)) {
			if (position.m_lane == 0) {
				// Spur wechseln
				Position position1(1, position.m_pos);
				Meters freeDist, freeDistBehind;
				if (freeSight(it, maxDist, 1, true, freeDist) && freeSight(it, speed * m_sim.m_freeSight, 1, false, freeDistBehind) && !m_fw.roadWorksAhead(position1, speed)) {
					// Change lan
					car.changeLane(1);
					car.addDist(maxDist);
					return false;
				}
			}
			maxDist = dist;
		}
	}

	// possible option: move maxDist on the same lane

	// check for road work
	if (m_fw.roadWorksAhead(position, speed, maxDist + m_sim.m_carLength)) {
		// try to change lane
		size_t lane1 = 1 - position.m_lane;
		Position position1(lane1, position.m_pos);
		Meters freeDist, freeDistBehind;
		if (freeSight(it, m_sim.m_carLength, lane1, true, freeDist) && freeSight(it, m_sim.m_carLength, lane1, false, freeDistBehind)) {
			// change lane
			car.addDist(min(freeDist, maxDist));
			car.changeLane(lane1);
		} else {
			// keep lane and reduce speed
			car.addDist(min(maxDist, m_fw.distToRoadWorks(position) - m_sim.m_carLength));
		}
		return false;
	}

	// unsolicited lane change
	if (position.m_lane == 1) {
		// try to change lane
		Position position0(0, position.m_pos);
		Meters freeDist, freeDistBehind;
		if (freeSight(it, maxDist, 0, true, freeDist) &&
			freeSight(it, speed*m_sim.m_freeSight, 0, false, freeDistBehind) &&
			!m_fw.roadWorksAhead(position0, speed)) {
			// change lane
			car.addDist(min(freeDist, maxDist));
			car.changeLane(0);
			return false;
		}
	}

	// move maxDist on the same lane
	car.addDist(maxDist);
	return false;
}

