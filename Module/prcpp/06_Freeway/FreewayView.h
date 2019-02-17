
// FreewayView.h : interface of the CFreewayView class
//

#pragma once

#include "Simulation.h"
#include "FreewayDoc.h"

class CFreewayView : public CView
{
protected: // create from serialization only
	CFreewayView();
	DECLARE_DYNCREATE(CFreewayView)

// Attributes
	bool m_initDone;
	Simulation m_simulation;
	UINT_PTR m_timer;

// Operations
public:
	CFreewayDoc* GetDocument() const;
	int GetStartedCars() const { return m_simulation.m_startedCars; }
	int GetStoppedCars() const { return m_simulation.m_stoppedCars; }
	double GetMeanSpeed() const { return (m_simulation.m_totalTime != Seconds(0)) ? Kmh(m_simulation.m_stoppedCars*GetDocument()->m_freewayLength/m_simulation.m_totalTime).m_val : 0; }
	
	void SetStepDuration(Seconds s) { m_simulation.setStepDuration(s); if (m_simulation.isRunning()) { OnSimPause(); OnSimRun(); }}
	void SetCarsPerSecond(double d) { m_simulation.setCarsPerSecond(d); }
	void SetFreeSightDurance(Seconds s) { m_simulation.setFreeSightDurance(s); }
	void SetSpeedFactorDev(double d) { m_simulation.setSpeedFactorDev(d); }

// Overrides
public:
	virtual void OnDraw(CDC* pDC);  // overridden to draw this view
	virtual BOOL PreCreateWindow(CREATESTRUCT& cs);
	virtual void OnInitialUpdate();
protected:
	virtual BOOL OnPreparePrinting(CPrintInfo* pInfo);
	virtual void OnBeginPrinting(CDC* pDC, CPrintInfo* pInfo);
	virtual void OnEndPrinting(CDC* pDC, CPrintInfo* pInfo);

// Implementation
public:
	virtual ~CFreewayView();
#ifdef _DEBUG
	virtual void AssertValid() const;
	virtual void Dump(CDumpContext& dc) const;
#endif

protected:

// Generated message map functions
protected:
	afx_msg void OnFilePrintPreview();
	afx_msg void OnRButtonUp(UINT nFlags, CPoint point);
	afx_msg void OnContextMenu(CWnd* pWnd, CPoint point);
	afx_msg void OnUpdateSimRun(CCmdUI *pCmdUI);
	afx_msg void OnSimRun();
	afx_msg void OnUpdateSimPause(CCmdUI *pCmdUI);
	afx_msg void OnSimPause();
	afx_msg void OnSimStep();
	afx_msg void OnTimer(UINT_PTR nIDEvent);
	afx_msg void OnSimReset();
	DECLARE_MESSAGE_MAP()
};

#ifndef _DEBUG  // debug version in FreewayView.cpp
inline CFreewayDoc* CFreewayView::GetDocument() const
   { return reinterpret_cast<CFreewayDoc*>(m_pDocument); }
#endif

