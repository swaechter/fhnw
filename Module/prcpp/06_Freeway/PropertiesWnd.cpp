
#include "stdafx.h"

#include "PropertiesWnd.h"
#include "Resource.h"
#include "MainFrm.h"
#include "FreewayApp.h"

#ifdef _DEBUG
#undef THIS_FILE
static char THIS_FILE[]=__FILE__;
#define new DEBUG_NEW
#endif

/////////////////////////////////////////////////////////////////////////////
// CResourceViewBar

CPropertiesWnd::CPropertiesWnd()
{
}

CPropertiesWnd::~CPropertiesWnd()
{
}

BEGIN_MESSAGE_MAP(CPropertiesWnd, CDockablePane)
	ON_WM_CREATE()
	ON_WM_SIZE()
	ON_COMMAND(ID_EXPAND_ALL, OnExpandAllProperties)
	ON_UPDATE_COMMAND_UI(ID_EXPAND_ALL, OnUpdateExpandAllProperties)
	ON_COMMAND(ID_SORTPROPERTIES, OnSortProperties)
	ON_UPDATE_COMMAND_UI(ID_SORTPROPERTIES, OnUpdateSortProperties)
	ON_COMMAND(ID_PROPERTIES1, OnProperties1)
	ON_UPDATE_COMMAND_UI(ID_PROPERTIES1, OnUpdateProperties1)
	ON_COMMAND(ID_PROPERTIES2, OnProperties2)
	ON_UPDATE_COMMAND_UI(ID_PROPERTIES2, OnUpdateProperties2)
	ON_COMMAND(ID_PROPERTIES_FW, &CPropertiesWnd::OnFreewayProperties)
	ON_COMMAND(ID_PROPERTIES_SIM, &CPropertiesWnd::OnSimulationProperties)
	ON_COMMAND(ID_PROPERTIES_STAT, &CPropertiesWnd::OnStatisticsProperties)
	ON_WM_SETFOCUS()
	ON_WM_SETTINGCHANGE()
	ON_REGISTERED_MESSAGE(AFX_WM_PROPERTY_CHANGED, OnPropertyChanged)
END_MESSAGE_MAP()

/////////////////////////////////////////////////////////////////////////////
// CResourceViewBar message handlers

void CPropertiesWnd::AdjustLayout()
{
	if (GetSafeHwnd () == NULL || (AfxGetMainWnd() != NULL && AfxGetMainWnd()->IsIconic()))
	{
		return;
	}

	CRect rectClient;
	GetClientRect(rectClient);

	int cyTlb = m_wndToolBar.CalcFixedLayout(FALSE, TRUE).cy;

	m_wndToolBar.SetWindowPos(NULL, rectClient.left, rectClient.top, rectClient.Width(), cyTlb, SWP_NOACTIVATE | SWP_NOZORDER);
	m_wndPropList.SetWindowPos(NULL, rectClient.left, rectClient.top + cyTlb, rectClient.Width(), rectClient.Height() - cyTlb, SWP_NOACTIVATE | SWP_NOZORDER);
}

int CPropertiesWnd::OnCreate(LPCREATESTRUCT lpCreateStruct)
{
	if (CDockablePane::OnCreate(lpCreateStruct) == -1)
		return -1;

	CRect rectDummy;
	rectDummy.SetRectEmpty();

	if (!m_wndPropList.Create(WS_VISIBLE | WS_CHILD, rectDummy, this, 2))
	{
		TRACE0("Failed to create Properties Grid \n");
		return -1;      // fail to create
	}

	InitPropList();

	m_wndToolBar.Create(this, AFX_DEFAULT_TOOLBAR_STYLE, IDR_PROPERTIES);
	m_wndToolBar.LoadToolBar(IDR_PROPERTIES, 0, 0, TRUE /* Is locked */);
	m_wndToolBar.CleanUpLockedImages();
	m_wndToolBar.LoadBitmap(theApp.m_bHiColorIcons ? IDB_PROPERTIES_HC : IDR_PROPERTIES, 0, 0, TRUE /* Locked */);

	m_wndToolBar.SetPaneStyle(m_wndToolBar.GetPaneStyle() | CBRS_TOOLTIPS | CBRS_FLYBY);
	m_wndToolBar.SetPaneStyle(m_wndToolBar.GetPaneStyle() & ~(CBRS_GRIPPER | CBRS_SIZE_DYNAMIC | CBRS_BORDER_TOP | CBRS_BORDER_BOTTOM | CBRS_BORDER_LEFT | CBRS_BORDER_RIGHT));
	m_wndToolBar.SetOwner(this);

	// All commands will be routed via this control , not via the parent frame:
	m_wndToolBar.SetRouteCommandsViaFrame(FALSE);

	AdjustLayout();
	return 0;
}

void CPropertiesWnd::OnSize(UINT nType, int cx, int cy)
{
	CDockablePane::OnSize(nType, cx, cy);
	AdjustLayout();
}

void CPropertiesWnd::OnExpandAllProperties()
{
	m_wndPropList.ExpandAll();
}

void CPropertiesWnd::OnUpdateExpandAllProperties(CCmdUI* /* pCmdUI */)
{
}

void CPropertiesWnd::OnSortProperties()
{
	m_wndPropList.SetAlphabeticMode(!m_wndPropList.IsAlphabeticMode());
}

void CPropertiesWnd::OnUpdateSortProperties(CCmdUI* pCmdUI)
{
	pCmdUI->SetCheck(m_wndPropList.IsAlphabeticMode());
}

void CPropertiesWnd::OnFreewayProperties() {
	auto pDoc = theApp.GetMainFrame()->m_view->GetDocument();
	CMFCPropertyGridProperty *pGroup = m_wndPropList.GetProperty(0);

	pGroup->GetSubItem(0)->SetValue((_variant_t)pDoc->m_freewayLength.m_val);
	pGroup->GetSubItem(1)->SetValue((_variant_t)pDoc->m_sectorLength.m_val);
	pGroup->GetSubItem(2)->SetValue((_variant_t)pDoc->m_carLength.m_val);
	pGroup->GetSubItem(3)->SetValue((_variant_t)(int)pDoc->m_allowedMaxSpeed.m_val);
	pGroup->GetSubItem(4)->SetValue((_variant_t)pDoc->m_roadWorksStart.m_val);
	pGroup->GetSubItem(5)->SetValue((_variant_t)pDoc->m_roadWorksLength.m_val);
	pGroup->GetSubItem(6)->SetValue((_variant_t)(int)pDoc->m_reducedMaxSpeed.m_val);
}

void CPropertiesWnd::OnSimulationProperties() {
	auto pDoc = theApp.GetMainFrame()->m_view->GetDocument();
	CMFCPropertyGridProperty *pGroup = m_wndPropList.GetProperty(1);

	pGroup->GetSubItem(0)->SetValue((_variant_t)pDoc->m_stepDuration.count());
	pGroup->GetSubItem(1)->SetValue((_variant_t)pDoc->m_carsPerSecond);
	pGroup->GetSubItem(2)->SetValue((_variant_t)pDoc->m_freeSight.count());
	pGroup->GetSubItem(3)->SetValue((_variant_t)pDoc->m_speedFactorsSigma);
}

void CPropertiesWnd::OnStatisticsProperties() {
	auto pView = theApp.GetMainFrame()->m_view;
	CMFCPropertyGridProperty *pGroup = m_wndPropList.GetProperty(2);

	pGroup->GetSubItem(0)->SetValue((_variant_t)pView->GetStartedCars());
	pGroup->GetSubItem(1)->SetValue((_variant_t)pView->GetStoppedCars());
	pGroup->GetSubItem(2)->SetValue((_variant_t)pView->GetMeanSpeed());
}

void CPropertiesWnd::OnProperties1()
{
	// TODO: Add your command handler code here
}

void CPropertiesWnd::OnUpdateProperties1(CCmdUI* /*pCmdUI*/)
{
	// TODO: Add your command update UI handler code here
}

void CPropertiesWnd::OnProperties2()
{
	// TODO: Add your command handler code here
}

void CPropertiesWnd::OnUpdateProperties2(CCmdUI* /*pCmdUI*/)
{
	// TODO: Add your command update UI handler code here
}

void CPropertiesWnd::InitPropList() {
	m_wndPropList.EnableHeaderCtrl(FALSE);
	m_wndPropList.EnableDescriptionArea();
	m_wndPropList.SetVSDotNetLook();
	m_wndPropList.MarkModifiedProperties();

	CMFCPropertyGridProperty* prop = nullptr;
	CMFCPropertyGridProperty* pGroup1 = new CMFCPropertyGridProperty(_T("Freeway"));

	prop = new CMFCPropertyGridProperty(_T("Length [km]"), (_variant_t)0, _T("Specifies the length in km"));
	prop->AllowEdit(FALSE);
	pGroup1->AddSubItem(prop);
	prop = new CMFCPropertyGridProperty(_T("Sector Length [m]"), (_variant_t)0, _T("Specifies the length of a freeway sector"));
	prop->AllowEdit(FALSE);
	pGroup1->AddSubItem(prop);
	prop = new CMFCPropertyGridProperty(_T("Car Length [m]"), (_variant_t)0, _T("Specifies the length of a car"));
	prop->AllowEdit(FALSE);
	pGroup1->AddSubItem(prop);
	prop = new CMFCPropertyGridProperty(_T("Allowed Speed [km/h]"), (_variant_t)0, _T("Specifies the normal speed"));
	prop->AllowEdit(FALSE);
	pGroup1->AddSubItem(prop);
	prop = new CMFCPropertyGridProperty(_T("Road Works Start [m]"), (_variant_t)0, _T("Specifies the position of road works"));
	prop->AllowEdit(FALSE);
	pGroup1->AddSubItem(prop);
	prop = new CMFCPropertyGridProperty(_T("Road Works Length [m]"), (_variant_t)0, _T("Specifies the length of road works area"));
	pGroup1->AddSubItem(prop);
	prop->AllowEdit(FALSE);
	prop = new CMFCPropertyGridProperty(_T("Reduced Speed [km/h]"), (_variant_t)0, _T("Specifies the reduced speed in road works areas"));
	pGroup1->AddSubItem(prop);
	prop->AllowEdit(FALSE);

	m_wndPropList.AddProperty(pGroup1);


	CMFCPropertyGridProperty* pGroup2 = new CMFCPropertyGridProperty(_T("Simulation"));

	pGroup2->AddSubItem(new CMFCPropertyGridProperty(_T("Step Duration [s]"), (_variant_t)0.0, _T("Specifies the duration of a simulation step")));
	pGroup2->AddSubItem(new CMFCPropertyGridProperty(_T("Cars per Second"), (_variant_t)0.0, _T("Specifies the initial traffic")));
	pGroup2->AddSubItem(new CMFCPropertyGridProperty(_T("Free Sight Durance [s]"), (_variant_t)0.0, _T("Specifies the durance for free sight")));
	pGroup2->AddSubItem(new CMFCPropertyGridProperty(_T("Speed Factor Deviation"), (_variant_t)0.0, _T("Specifies the standard deviation of the speed factor")));

	m_wndPropList.AddProperty(pGroup2);


	CMFCPropertyGridProperty* pGroup3 = new CMFCPropertyGridProperty(_T("Statistics"));
	prop = new CMFCPropertyGridProperty(_T("Started Cars"), (_variant_t)0, _T("Specifies the number of started cars"));
	prop->Enable(FALSE);
	pGroup3->AddSubItem(prop);
	prop = new CMFCPropertyGridProperty(_T("Stopped Cars"), (_variant_t)0, _T("Specifies the number of stopped cars"));
	prop->Enable(FALSE);
	pGroup3->AddSubItem(prop);
	prop = new CMFCPropertyGridProperty(_T("Mean Speed [km/h]"), (_variant_t)0.0, _T("Specifies the mean speed of stopped cars"));
	prop->Enable(FALSE);
	pGroup3->AddSubItem(prop);

	m_wndPropList.AddProperty(pGroup3);
}

void CPropertiesWnd::OnSetFocus(CWnd* pOldWnd)
{
	CDockablePane::OnSetFocus(pOldWnd);
	m_wndPropList.SetFocus();
}

void CPropertiesWnd::OnSettingChange(UINT uFlags, LPCTSTR lpszSection)
{
	CDockablePane::OnSettingChange(uFlags, lpszSection);
	SetPropListFont();
}

/////////////////////////////////////////////////////////////////////////////
void CPropertiesWnd::SetPropListFont() {
	::DeleteObject(m_fntPropList.Detach());

	LOGFONT lf;
	afxGlobalData.fontRegular.GetLogFont(&lf);

	NONCLIENTMETRICS info;
	info.cbSize = sizeof(info);

	afxGlobalData.GetNonClientMetrics(info);

	lf.lfHeight = info.lfMenuFont.lfHeight;
	lf.lfWeight = info.lfMenuFont.lfWeight;
	lf.lfItalic = info.lfMenuFont.lfItalic;

	m_fntPropList.CreateFontIndirect(&lf);

	m_wndPropList.SetFont(&m_fntPropList);
}

/////////////////////////////////////////////////////////////////////////////
// Called by the framework when the value of a property is changed. 
// wparam: The control ID of the property list. 
// lparam: A pointer to the property (CMFCPropertyGridProperty) that changed. 
LRESULT CPropertiesWnd::OnPropertyChanged(WPARAM /*wparam*/, LPARAM lparam) {
	CMFCPropertyGridProperty *pProp = reinterpret_cast<CMFCPropertyGridProperty *>(lparam);
	auto pView = theApp.GetMainFrame()->m_view;
	if (!pProp || !pView) return 0;

	auto parent = pProp->GetParent();
	CString s(parent->GetName());

	if (s == L"Simulation") {
		if (pProp == parent->GetSubItem(0)) {
			//pGroup2->AddSubItem(new CMFCPropertyGridProperty(_T("Step Duration [s]"), (_variant_t)0.0, _T("Specifies the duration of a simulation step")));
			pView->SetStepDuration(Seconds(pProp->GetValue().dblVal));
		}
		if (pProp == parent->GetSubItem(1)) {
			//pGroup2->AddSubItem(new CMFCPropertyGridProperty(_T("Cars per Second"), (_variant_t)0.0, _T("Specifies the initial traffic")));
			pView->SetCarsPerSecond(pProp->GetValue().dblVal);
		}
		if (pProp == parent->GetSubItem(2)) {
			//pGroup2->AddSubItem(new CMFCPropertyGridProperty(_T("Free Sight Durance [s]"), (_variant_t)0.0, _T("Specifies the durance for free sight")));
			pView->SetFreeSightDurance(Seconds(pProp->GetValue().dblVal));
		}
		if (pProp == parent->GetSubItem(3)) {
			//pGroup2->AddSubItem(new CMFCPropertyGridProperty(_T("Speed Factor Deviation"), (_variant_t)0.0, _T("Specifies the standard deviation of the speed factor")));
			pView->SetSpeedFactorDev(pProp->GetValue().dblVal);
		}
	}

	return 0;	// not used
}
