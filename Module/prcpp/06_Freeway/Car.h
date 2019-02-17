#pragma once

#include "Units.h"

const int NColors = 6;
const COLORREF colors[NColors] = { 
	RGB(255, 0, 0), 
	RGB(0, 255, 0), 
	RGB(0, 0, 255), 
	RGB(255, 255, 0), 
	RGB(0, 255, 255), 
	RGB(255, 0, 255) 
};

struct Position {
	size_t m_lane;
	Meters m_pos;

	Position(size_t lane = 0, Meters pos = 0) : m_lane(lane), m_pos(pos) {}
	
	bool operator<(const Position& position) const { return m_pos < position.m_pos || m_pos == position.m_pos && m_lane < position.m_lane; }
};

class Car {

	COLORREF m_color;
	Position m_position;
	double m_speedFactor; // Individual speed adaption
	Seconds m_start;

	static int s_colorIdx; // Farbindex für das nächste Auto in der Simulation

public:

	Car() {
		m_color = colors[s_colorIdx++];
		if (s_colorIdx == NColors) {
			s_colorIdx = 0;
		}
	}

	COLORREF getColor() const {
		return m_color;
	}

	const Position& getPosition() const {
		return m_position;
	}

	size_t getLane() const {
		return m_position.m_lane;
	}

	Meters getPos() const {
		return m_position.m_pos;
	}

	double getSpeedFactor() const {
		return m_speedFactor;
	}

	const Seconds& getStartTime() const {
		return m_start;
	}

	void setPos(const Meters& pos) {
		m_position.m_pos = pos;
	}

	void setPosition(const Position& position) {
		m_position = position;
	}

	void addDist(const Meters& dist) {
		m_position.m_pos += dist;
	}

	void changeLane(size_t lane) {
		m_position.m_lane = lane;
	}

	void setSpeedFactor(double f) {
		m_speedFactor = max(0.75, f);
	}

	void start(const Seconds& time) {
		m_start = time;
	}
};
