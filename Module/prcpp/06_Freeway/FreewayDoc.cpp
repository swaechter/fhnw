
// FreewayDoc.cpp : implementation of the CFreewayDoc class
//

#include "stdafx.h"
// SHARED_HANDLERS can be defined in an ATL project implementing preview, thumbnail
// and search filter handlers and allows sharing of document code with that project.
#ifndef SHARED_HANDLERS
#include "FreewayApp.h"
#endif

#include "FreewayDoc.h"

#include <propkey.h>

#ifdef _DEBUG
#define new DEBUG_NEW
#endif

// CFreewayDoc

IMPLEMENT_DYNCREATE(CFreewayDoc, CDocument)

BEGIN_MESSAGE_MAP(CFreewayDoc, CDocument)
END_MESSAGE_MAP()


// CFreewayDoc construction/destruction

CFreewayDoc::CFreewayDoc()
{
	m_stepDuration = Seconds(0.1);
	m_freeSight = Seconds(2);
	ASSERT(m_freeSight > m_stepDuration);
	m_freewayLength = Kilometers(1);
	m_sectorLength = Meters(100);
	m_carLength = Meters(5);
	m_allowedMaxSpeed = Kmh(120);
	m_reducedMaxSpeed = Kmh(80);
	m_carsPerSecond = 2;
	m_speedFactorsSigma = 0.3;
	m_roadWorksStart = Meters(500);
	m_roadWorksLength = Meters(190);
}

CFreewayDoc::~CFreewayDoc()
{
}

BOOL CFreewayDoc::OnNewDocument()
{
	if (!CDocument::OnNewDocument())
		return FALSE;

	// TODO: add reinitialization code here
	// (SDI documents will reuse this document)

	return TRUE;
}




// CFreewayDoc serialization

void CFreewayDoc::Serialize(CArchive& ar)
{
	if (ar.IsStoring())
	{
		// TODO: add storing code here
	}
	else
	{
		// TODO: add loading code here
	}
}

#ifdef SHARED_HANDLERS

// Support for thumbnails
void CFreewayDoc::OnDrawThumbnail(CDC& dc, LPRECT lprcBounds)
{
	// Modify this code to draw the document's data
	dc.FillSolidRect(lprcBounds, RGB(255, 255, 255));

	CString strText = _T("TODO: implement thumbnail drawing here");
	LOGFONT lf;

	CFont* pDefaultGUIFont = CFont::FromHandle((HFONT) GetStockObject(DEFAULT_GUI_FONT));
	pDefaultGUIFont->GetLogFont(&lf);
	lf.lfHeight = 36;

	CFont fontDraw;
	fontDraw.CreateFontIndirect(&lf);

	CFont* pOldFont = dc.SelectObject(&fontDraw);
	dc.DrawText(strText, lprcBounds, DT_CENTER | DT_WORDBREAK);
	dc.SelectObject(pOldFont);
}

// Support for Search Handlers
void CFreewayDoc::InitializeSearchContent()
{
	CString strSearchContent;
	// Set search contents from document's data. 
	// The content parts should be separated by ";"

	// For example:  strSearchContent = _T("point;rectangle;circle;ole object;");
	SetSearchContent(strSearchContent);
}

void CFreewayDoc::SetSearchContent(const CString& value)
{
	if (value.IsEmpty())
	{
		RemoveChunk(PKEY_Search_Contents.fmtid, PKEY_Search_Contents.pid);
	}
	else
	{
		CMFCFilterChunkValueImpl *pChunk = NULL;
		ATLTRY(pChunk = new CMFCFilterChunkValueImpl);
		if (pChunk != NULL)
		{
			pChunk->SetTextValue(PKEY_Search_Contents, value, CHUNK_TEXT);
			SetChunkValue(pChunk);
		}
	}
}

#endif // SHARED_HANDLERS

// CFreewayDoc diagnostics

#ifdef _DEBUG
void CFreewayDoc::AssertValid() const
{
	CDocument::AssertValid();
}

void CFreewayDoc::Dump(CDumpContext& dc) const
{
	CDocument::Dump(dc);
}
#endif //_DEBUG


// CFreewayDoc commands
