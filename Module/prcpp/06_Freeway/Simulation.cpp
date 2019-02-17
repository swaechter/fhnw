#include "stdafx.h"
#include <cassert>
#include "Simulation.h"

int Car::s_colorIdx = 0; // class variable

Simulation::Simulation()
	: m_running(false)
	, m_engine((unsigned int)chrono::system_clock::now().time_since_epoch().count()) // time depending seed value
	, m_startedCars(0)
	, m_stoppedCars(0)
	, m_cars(*this, m_fw)
	, m_totalTime(0)
	, m_time(0)
{
}

void Simulation::init(Freeway&& fw, Seconds simulationStep, double carsPerSecond, double speedFactorsSigma, Meters carLength, Seconds freeSight) {
	m_fw = move(fw);
	m_fw.m_freeSight = freeSight;
	m_simulationStep = simulationStep;
	m_carsPerSecond = carsPerSecond;
	m_carLength = carLength;
	m_freeSight = freeSight;
	setCarsPerSecond(carsPerSecond); // exponential distributed duration between the arrival of two cars
	setSpeedFactorDev(speedFactorsSigma);
}

void Simulation::step() {
	static Seconds time2goOnLane[2] = { Seconds(0), Seconds(0) };

	// simulate all current cars
	if (m_cars.size()) {
		auto it = m_cars.end();
		while (it != m_cars.begin()) {
			it--;
			m_cars.moveCar(it);
		}
	}

	// add new cars
	for (size_t lane = 0; lane < m_fw.nLanes(); lane++) {
		time2goOnLane[lane] -= m_simulationStep;
		if (time2goOnLane[lane].count() <= 0) {
			unique_ptr<Car> pCar = m_parking.getCar();
			pCar->setSpeedFactor(m_speedFactors(m_engine));
			Mps v = m_fw.getAllowedSpeed(SectorInfo(0, 0))*pCar->getSpeedFactor();
			pCar->setPosition(Position(lane, -(v*time2goOnLane[lane])));
			m_cars.addCar(pCar);

			time2goOnLane[lane] += Seconds(m_arrivals(m_engine));
		}
	}

	m_time += m_simulationStep;
}