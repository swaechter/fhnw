
// Freeway.h : main header file for the Freeway application
//
#pragma once

#ifndef __AFXWIN_H__
	#error "include 'stdafx.h' before including this file for PCH"
#endif

#include "resource.h"       // main symbols
#include "MainFrm.h"

// CFreewayApp:
// See Freeway.cpp for the implementation of this class
//

class CFreewayApp : public CWinAppEx
{
public:
	CFreewayApp();

	CMainFrame* GetMainFrame() const { return static_cast<CMainFrame*>(AfxGetMainWnd()); }

// Overrides
public:
	virtual BOOL InitInstance();

// Implementation
	BOOL  m_bHiColorIcons;

	virtual void PreLoadState();
	virtual void LoadCustomState();
	virtual void SaveCustomState();

	afx_msg void OnAppAbout();
	DECLARE_MESSAGE_MAP()
};

extern CFreewayApp theApp;
