#pragma once

#include <random>
#include "Units.h"
#include "Freeway.h"
#include "Parking.h"
#include "Cars.h"

using namespace std;

class Simulation {
	friend class CFreewayView;
	friend class Cars;

	Parking m_parking;
	Cars m_cars;
	Seconds m_simulationStep, m_time, m_freeSight;
	Freeway m_fw;
	default_random_engine m_engine;
	exponential_distribution<double> m_arrivals;
	normal_distribution<double> m_speedFactors;
	Meters m_carLength;
	double m_carsPerSecond;
	bool m_running;

	// statistics
	int m_startedCars, m_stoppedCars;
	Seconds m_totalTime; // of stopped cars

public:
	Simulation();
	~Simulation() {}
	void init(Freeway&& fw, Seconds simulationStep, double carsPerSecond, double speedFactorsSigma, Meters carLength, Seconds freeSight);
	void step();
	void pause() { m_running = false; }
	void restart() { m_running = true; }
	void reset() { m_cars.clear(); m_startedCars = m_stoppedCars = 0; m_totalTime = Seconds(0); }

	void setStepDuration(Seconds s) { m_simulationStep = s; }
	void setCarsPerSecond(double d) { m_arrivals = exponential_distribution<double>(d/m_fw.nLanes()); }
	void setFreeSightDurance(Seconds s) { m_freeSight = s; }
	void setSpeedFactorDev(double d) { m_speedFactors = normal_distribution<double>(1, d); }

	bool isRunning() const { return m_running; }
	int simulationStepInMillis() const { return (int)std::chrono::duration<double, milli>(m_simulationStep).count(); }

	// Freeway
	size_t nSectors() const { return m_fw.nSectors(); }
	size_t nLanes() const { return m_fw.nLanes(); }
	bool isClosed(const SectorInfo& si) const { return m_fw.isClosed(si); }
	Meters SectorLen() { return m_fw.m_sectorLen;  }

private:
};