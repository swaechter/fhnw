#pragma once

#include <array>
#include <vector>
#include <memory>
#include "Units.h"
#include "Car.h"
#include "Parking.h"

using namespace std;

const size_t NLanes = 2;

struct SectorInfo {
	size_t m_lane, m_sectorNr;
	SectorInfo(size_t lane, size_t sectorNr) : m_lane(lane), m_sectorNr(sectorNr) {}
};

class Freeway {
	friend class Simulation;

	struct Sector {
		// instance variables
		Mps m_maxSpeed;
		bool m_closed;

		// ctor
		Sector(Kmh maxSpeed = 0) : m_maxSpeed(maxSpeed), m_closed(false) {}
	};

	Meters m_sectorLen;
	Seconds m_freeSight;
	vector<vector<Sector>> m_lanes; 

public:
	Freeway() {}
	Freeway(Meters dist, Meters SectorLen, Kmh maxSpeed, Meters roadWorksStart, Meters roadWorksLength, Kmh reducedSpeed);

	Freeway& operator=(Freeway&& fw) {
		m_sectorLen = fw.m_sectorLen;
		m_freeSight = fw.m_freeSight;
		m_lanes = move(fw.m_lanes);
		return *this;
	}

	size_t nLanes() const { return NLanes; }
	size_t nSectors() const { return m_lanes[0].size(); }
	size_t getSectorNr(Meters m) const { return m/m_sectorLen;  }
	Mps getAllowedSpeed(const SectorInfo& si) const { return m_lanes[si.m_lane][si.m_sectorNr].m_maxSpeed; }
	bool isClosed(const SectorInfo& si) const { return m_lanes[si.m_lane][si.m_sectorNr].m_closed; }
	bool roadWorksAhead(const Position& position, Mps speed, Meters minDist = 0) const;
	Mps carSpeed(const Car& car) const;
	Meters distToRoadWorks(const Position& position) const;

	void addRoadWorks(Meters start, Meters dist, size_t lane, Kmh maxSpeed);

};