
// FreewayDoc.h : interface of the CFreewayDoc class
//


#pragma once

#include "Units.h"

class CFreewayDoc : public CDocument
{
protected: // create from serialization only
	CFreewayDoc();
	DECLARE_DYNCREATE(CFreewayDoc)

// Attributes
public:
	Seconds m_stepDuration;
	Seconds m_freeSight; // > m_stepDuration
	Kilometers m_freewayLength;
	Meters m_roadWorksStart, m_roadWorksLength;
	Meters m_sectorLength;
	Meters m_carLength;
	Kmh m_allowedMaxSpeed, m_reducedMaxSpeed;
	double m_carsPerSecond;
	double m_speedFactorsSigma;

// Operations
public:

// Overrides
public:
	virtual BOOL OnNewDocument();
	virtual void Serialize(CArchive& ar);
#ifdef SHARED_HANDLERS
	virtual void InitializeSearchContent();
	virtual void OnDrawThumbnail(CDC& dc, LPRECT lprcBounds);
#endif // SHARED_HANDLERS

// Implementation
public:
	virtual ~CFreewayDoc();
#ifdef _DEBUG
	virtual void AssertValid() const;
	virtual void Dump(CDumpContext& dc) const;
#endif

protected:

// Generated message map functions
protected:
	DECLARE_MESSAGE_MAP()

#ifdef SHARED_HANDLERS
	// Helper function that sets search content for a Search Handler
	void SetSearchContent(const CString& value);
#endif // SHARED_HANDLERS
};
