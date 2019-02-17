
// FreewayView.cpp : implementation of the CFreewayView class
//

#include "stdafx.h"
// SHARED_HANDLERS can be defined in an ATL project implementing preview, thumbnail
// and search filter handlers and allows sharing of document code with that project.
#ifndef SHARED_HANDLERS
#include "FreewayApp.h"
#endif

#include "FreewayDoc.h"
#include "FreewayView.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#endif


// CFreewayView

IMPLEMENT_DYNCREATE(CFreewayView, CView)

BEGIN_MESSAGE_MAP(CFreewayView, CView)
	// Standard printing commands
	ON_COMMAND(ID_FILE_PRINT, &CView::OnFilePrint)
	ON_COMMAND(ID_FILE_PRINT_DIRECT, &CView::OnFilePrint)
	ON_COMMAND(ID_FILE_PRINT_PREVIEW, &CFreewayView::OnFilePrintPreview)
	ON_WM_CONTEXTMENU()
	ON_WM_RBUTTONUP()
	ON_UPDATE_COMMAND_UI(ID_SIM_RUN, &CFreewayView::OnUpdateSimRun)
	ON_COMMAND(ID_SIM_RUN, &CFreewayView::OnSimRun)
	ON_UPDATE_COMMAND_UI(ID_SIM_PAUSE, &CFreewayView::OnUpdateSimPause)
	ON_COMMAND(ID_SIM_PAUSE, &CFreewayView::OnSimPause)
	ON_COMMAND(ID_SIM_STEP, &CFreewayView::OnSimStep)
	ON_WM_TIMER()
	ON_COMMAND(ID_SIM_RESET, &CFreewayView::OnSimReset)
END_MESSAGE_MAP()

// CFreewayView construction/destruction

CFreewayView::CFreewayView() : m_initDone(false), m_simulation(), m_timer(0)
{
	// TODO: add construction code here
}

CFreewayView::~CFreewayView() {
}

BOOL CFreewayView::PreCreateWindow(CREATESTRUCT& cs)
{
	// TODO: Modify the Window class or styles here by modifying
	//  the CREATESTRUCT cs

	return CView::PreCreateWindow(cs);
}

void CFreewayView::OnInitialUpdate() {
	CView::OnInitialUpdate();

	theApp.GetMainFrame()->m_view = this;

	CFreewayDoc* pDoc = GetDocument();
	m_simulation.init(
		Freeway(
			Meters(pDoc->m_freewayLength), 
			pDoc->m_sectorLength, 
			pDoc->m_allowedMaxSpeed,
			pDoc->m_roadWorksStart,
			pDoc->m_roadWorksLength,
			pDoc->m_reducedMaxSpeed), 
		pDoc->m_stepDuration, 
		pDoc->m_carsPerSecond, 
		pDoc->m_speedFactorsSigma, 
		pDoc->m_carLength, 
		pDoc->m_freeSight);

	OnSimPause();

	theApp.GetMainFrame()->m_wndProperties.SendMessage(WM_COMMAND, MAKELONG(ID_PROPERTIES_FW, 0));
	theApp.GetMainFrame()->m_wndProperties.SendMessage(WM_COMMAND, MAKELONG(ID_PROPERTIES_SIM, 0));

	m_initDone = true;
}

// CFreewayView drawing

void CFreewayView::OnDraw(CDC* pDC)
{
	CFreewayDoc* pDoc = GetDocument();
	ASSERT_VALID(pDoc);
	if (!pDoc || !m_initDone)
		return;

	CRect rect;
	GetClientRect(&rect);

	// [pixel]
	const CSize carBoxSize(28, 8);
	const CSize carMargin(4, 0);
	const CSize carSize = carBoxSize - carMargin -carMargin;
	const double pixelPerMeter = carBoxSize.cx/m_simulation.m_carLength.m_val;
	const int roadMargin = 10;
	const int laneMargin = 2;
	const int laneHeight = carBoxSize.cy + 2*laneMargin;
	const int sectorWidth = (int)(m_simulation.SectorLen().m_val*pixelPerMeter);
	const int roadHeight = (1 + laneHeight)*m_simulation.nLanes() + 2*roadMargin + 1;
	// [Sectors]
	const int w = rect.Width()/sectorWidth; if (w == 0) return;
	const int h = (m_simulation.nSectors() + w - 1)/w;
	// [m]
	const Meters wDist = w*m_simulation.SectorLen();
	// [pixel]
	const int laneWidth = w*sectorWidth + carBoxSize.cx;
	// colors
	const COLORREF roadColor = RGB(180, 180, 180);
	const COLORREF siteColor = RGB(60, 60, 60);
	const COLORREF marginColor = RGB(255, 255, 255);

	CPen border(PS_SOLID, 1, RGB(0, 0, 0));
	CPen middle(PS_DASH, 1, RGB(0, 0, 0));
	CBrush roadBrush(roadColor);
	LOGBRUSH logBrush;
	logBrush.lbStyle = BS_SOLID;
	logBrush.lbColor = roadColor;
	CPen lane(PS_SOLID | PS_GEOMETRIC | PS_ENDCAP_FLAT, laneHeight, &logBrush);
	logBrush.lbColor = siteColor;
	CPen construction(PS_SOLID | PS_GEOMETRIC | PS_ENDCAP_FLAT, laneHeight, &logBrush);
	logBrush.lbColor = marginColor;
	CPen noRoad(PS_SOLID | PS_GEOMETRIC | PS_ENDCAP_FLAT, laneHeight, &logBrush);
	int y = roadMargin;
	int SectorOffset = 0;
	SectorInfo si(0, 0);

	// draw road
	pDC->SetBkColor(roadColor);
	for (int i = 0; i < h; i++) {
		pDC->SelectObject(&border);
		pDC->MoveTo(0, y);
		pDC->LineTo(laneWidth, y);
		y++;
		for (int l = m_simulation.nLanes() - 1; l >= 0; l--) {
			si.m_lane = l;
			pDC->SelectObject(&lane);
			int y2 = y + laneHeight/2;
			pDC->MoveTo(0, y2);
			pDC->LineTo(laneWidth, y2);
			for (int j = 0; j < w; j++) {
				si.m_sectorNr = SectorOffset + j;
				if (si.m_sectorNr < m_simulation.nSectors()) {
					if (m_simulation.isClosed(si)) {
						pDC->SelectObject(&construction);
						pDC->MoveTo(j*sectorWidth, y2);
						if (j == w - 1) {
							pDC->LineTo(laneWidth, y2);
						} else {
							pDC->LineTo((j+1)*sectorWidth, y2);
						}
					}
				} else {
					pDC->SelectObject(noRoad);
					pDC->MoveTo(j*sectorWidth + carBoxSize.cx, y2);
					pDC->LineTo(laneWidth, y2);
				}
			}
			y += laneHeight;
			if (l > 0) {
				pDC->SelectObject(&middle);
				pDC->MoveTo(0, y);
				pDC->LineTo(laneWidth, y);
				y++;
			}
		}
		pDC->SelectObject(&border);
		pDC->MoveTo(0, y);
		pDC->LineTo(laneWidth, y);
		y += 2*roadMargin + 1;
		SectorOffset += w;
	}

	// draw cars
	auto& it = m_simulation.m_cars.begin();
	while (it != m_simulation.m_cars.end()) {
		const Car& car = *it->get();
		const Position& position = car.getPosition();
		const div_t qr = div((int)(position.m_pos.m_val*pixelPerMeter), (int)(wDist.m_val*pixelPerMeter));
		const int y = qr.quot*roadHeight + roadMargin + (m_simulation.nLanes() - car.getLane() - 1)*(laneHeight + 1) + 1 + laneMargin;
		const CRect carRect(CPoint(qr.rem, y) + carMargin, carSize);

		CBrush carBrush(car.getColor());
		pDC->FillRect(carRect, &carBrush);

		it++;
	}
}


// CFreewayView printing


void CFreewayView::OnFilePrintPreview()
{
#ifndef SHARED_HANDLERS
	AFXPrintPreview(this);
#endif
}

BOOL CFreewayView::OnPreparePrinting(CPrintInfo* pInfo)
{
	// default preparation
	return DoPreparePrinting(pInfo);
}

void CFreewayView::OnBeginPrinting(CDC* /*pDC*/, CPrintInfo* /*pInfo*/)
{
	// TODO: add extra initialization before printing
}

void CFreewayView::OnEndPrinting(CDC* /*pDC*/, CPrintInfo* /*pInfo*/)
{
	// TODO: add cleanup after printing
}

void CFreewayView::OnRButtonUp(UINT /* nFlags */, CPoint point)
{
	ClientToScreen(&point);
	OnContextMenu(this, point);
}

void CFreewayView::OnContextMenu(CWnd* /* pWnd */, CPoint point)
{
#ifndef SHARED_HANDLERS
	theApp.GetContextMenuManager()->ShowPopupMenu(IDR_POPUP_EDIT, point.x, point.y, this, TRUE);
#endif
}


// CFreewayView diagnostics

#ifdef _DEBUG
void CFreewayView::AssertValid() const
{
	CView::AssertValid();
}

void CFreewayView::Dump(CDumpContext& dc) const
{
	CView::Dump(dc);
}

CFreewayDoc* CFreewayView::GetDocument() const // non-debug version is inline
{
	ASSERT(m_pDocument->IsKindOf(RUNTIME_CLASS(CFreewayDoc)));
	return (CFreewayDoc*)m_pDocument;
}
#endif //_DEBUG


// CFreewayView message handlers
void CFreewayView::OnUpdateSimRun(CCmdUI *pCmdUI) {
	pCmdUI->SetCheck(m_simulation.isRunning());
}

void CFreewayView::OnSimRun() {
	m_timer = SetTimer(1, m_simulation.simulationStepInMillis(), NULL);
	m_simulation.restart();
}

void CFreewayView::OnUpdateSimPause(CCmdUI *pCmdUI) {
	pCmdUI->SetCheck(!m_simulation.isRunning());
}

void CFreewayView::OnSimPause() {
	if (m_timer) KillTimer(m_timer);
	m_timer = 0;
	m_simulation.pause();
}

void CFreewayView::OnSimStep() {
	OnSimPause();
	m_simulation.step();
	theApp.GetMainFrame()->m_wndProperties.SendMessage(WM_COMMAND, MAKELONG(ID_PROPERTIES_STAT, 0));
	Invalidate(FALSE);	// don't erase background
}

void CFreewayView::OnTimer(UINT_PTR nIDEvent) {
	m_simulation.step();
	theApp.GetMainFrame()->m_wndProperties.SendMessage(WM_COMMAND, MAKELONG(ID_PROPERTIES_STAT, 0));
	Invalidate(FALSE);	// don't erase background

	CView::OnTimer(nIDEvent);
}

void CFreewayView::OnSimReset() {
	m_simulation.reset();
}
