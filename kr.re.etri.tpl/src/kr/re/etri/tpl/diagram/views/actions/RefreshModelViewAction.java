package kr.re.etri.tpl.diagram.views.actions;

import java.util.List;
import java.util.Map;

import kr.re.etri.tpl.TaskModelPlugin;
import kr.re.etri.tpl.diagram.util.MarkerLogger;
import kr.re.etri.tpl.diagram.views.ModelNavigatorViewPage;
import kr.re.etri.tpl.diagram.views.ModelNavigatorViewPart;
import kr.re.etri.tpl.diagram.views.symbolparts.SNavIncludedElementEditPart;
import kr.re.etri.tpl.grammar.ParserUtil;
import kr.re.etri.tpl.script.utils.DiagramResourceUtil;
import kr.re.etri.tpl.taskmodel.IncludedElement;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.ModelDiagram;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.action.Action;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.views.ViewsPlugin;

public class RefreshModelViewAction extends Action{
	public static String ID=RefreshModelViewAction.class.getName();
	private static Logger logger = Logger.getLogger(RefreshModelViewAction.class);
	private ModelDiagram m_rootModel;
	private CommandStack m_commandStack;
	public RefreshModelViewAction(String text, ModelDiagram rootModel, CommandStack cmdStack) {
		super(text);
		m_rootModel = rootModel;
		m_commandStack = cmdStack;
	}
	public String getId() {
		return ID;
	}
	public void run() {
		List<IncludedElement> includes = m_rootModel.getIncludeItems();
		
		for(IncludedElement next : includes){
			String path = next.getIncludePath();
			int index  = path.indexOf("/");
			path = path.substring(index);			
			IProject prj = DiagramResourceUtil.getProject();
			IFile file = prj.getFile(path);
			
			if(file.exists()){
				refreshModel(file,next);				
			}
			else{
				logger.debug("파일 없음");
			}			
		}
	}
	
	private void refreshModel(IFile file, IncludedElement includeElement){	
		MarkerLogger problemLogger = new MarkerLogger(file, MarkerLogger.TYPE_SCRIPT);

		ModelDiagram modelDiagram=null;
		try {
			modelDiagram = ParserUtil.parseModel(file, problemLogger);
		} catch (Exception e1) {
			logger.error(e1.getMessage(), e1);
			return;
		}
	
		if(modelDiagram == null ){
			logger.info("ModelDiagram is null."+file);
			return;
		}

		if(problemLogger.hasError()){
			IMarker[] markers = null;
			try {
				markers = file.findMarkers(MarkerLogger.TYPE_SCRIPT, true, IResource.DEPTH_ZERO);
			} catch (CoreException e) {
				logger.error(e.getMessage(), e);
				return;
			}
			StringBuffer buffer = new StringBuffer();
			for(int i =0 ; i< markers.length;i++ ){
				int severity = markers[i].getAttribute(IMarker.SEVERITY,IMarker.SEVERITY_WARNING);
				if(severity == IMarker.SEVERITY_ERROR){
					String message = markers[i].getAttribute(IMarker.MESSAGE, "");
					buffer.append(message);
				}
			}
			
			Status status = new Status(IStatus.ERROR, TaskModelPlugin.PLUGIN_ID, 0,"The model script has Syntax Errors", new Exception(buffer.toString()));						
			return;
		}
		List<ItemElement> incChildren = includeElement.getItems();
		
		incChildren.clear();
				
		List<ItemElement> children = modelDiagram.getItems();
		for(ItemElement child : children) {
			child.setParent(includeElement);
		}
		incChildren.addAll(children);
		IWorkbench wb = PlatformUI.getWorkbench();
		IWorkbenchWindow wbw = wb.getActiveWorkbenchWindow();
		IWorkbenchPage wp = wbw.getActivePage();
	
		IViewPart vp = wp.findView(ModelNavigatorViewPart.viewId);
		
		ModelNavigatorViewPage page =(ModelNavigatorViewPage) ViewsPlugin.getAdapter(vp, ModelNavigatorViewPage.class, false);		
		
		EditPartViewer viewer = page.getEditPartViewer();
	
		Map reg = viewer.getEditPartRegistry();
		Object o = reg.get(includeElement);
		
		if(!(o instanceof SNavIncludedElementEditPart)){
			logger.debug("Is NOT SNavIncludedElementEditPart");
			return;
		}
		
		SNavIncludedElementEditPart editpart = (SNavIncludedElementEditPart)o;
		
		editpart.refresh();		
	}
}
