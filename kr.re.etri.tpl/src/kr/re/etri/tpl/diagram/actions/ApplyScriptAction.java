package kr.re.etri.tpl.diagram.actions;

import java.util.List;

import kr.re.etri.tpl.TaskModelPlugin;
import kr.re.etri.tpl.diagram.editors.TPLDiagramEditor;
import kr.re.etri.tpl.diagram.monitoring.controller.MonitoringControlView;

import org.apache.log4j.Logger;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

public class ApplyScriptAction extends SelectionAction {

	private static Logger logger = Logger.getLogger(ApplyScriptAction.class);
	public static final String actionId = "kr.re.etri.tpl.diagram.actions.ApplyScriptAction";	

	private IWorkbenchPart m_workbenchPart;
	public ApplyScriptAction(IWorkbenchPart part){
		super(part);
		m_workbenchPart = part;		
	}
	
	public void run() {
		IWorkbench wb = PlatformUI.getWorkbench();		
		IWorkbenchWindow wbw = wb.getActiveWorkbenchWindow();		
		IWorkbenchPage wp = wbw.getActivePage();
		IViewReference[] refers = wp.getViewReferences();
		MonitoringControlView mView = null;
		for(int i =0; i < refers.length ; i++){
			if(refers[i].getId().equals(MonitoringControlView.VIEW_ID)){
				mView = (MonitoringControlView)refers[i].getView(true);
			}
		}
		if(mView == null){
			logger.info("Monitoring Viwer is deactive.");
			return;
		}		
		
		List list = getSelectedObjects();
		
		if(list.size() <= 0 ){
			logger.info("There is not selected part.");
			return;
		}
		
		for(Object o : list){
			mView.add(o);
		}
		
	}
	
	protected void init() {
		super.init();
		setText("apply script");
		setId(actionId);
		setImageDescriptor(TaskModelPlugin.getImageDescriptor("/icons/rtm16.gif"));
	}

	@Override
	protected boolean calculateEnabled() {
		
		
		
		
		if(m_workbenchPart == null){
			return false;
		}
		
		IWorkbenchPartSite wps= m_workbenchPart.getSite();		
		if(wps == null){
			return false;
		}
		
		IWorkbenchPage wp = wps.getPage();
		if(wp == null){
			return false;
		}
		
//		WJH 100608 S 스크립터 적용 액션 추가
		TPLDiagramEditor editor = (TPLDiagramEditor)wp.getActiveEditor();

		IViewReference[] refers = wp.getViewReferences();
		MonitoringControlView mView = null;
		for(int i =0; i < refers.length ; i++){
			if(refers[i].getId().equals(MonitoringControlView.VIEW_ID)){
				mView = (MonitoringControlView)refers[i].getView(true);
			}
		}
		if(mView != null){
			return true;
		}
		return false;
	}

}
