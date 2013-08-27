package kr.re.etri.tpl.script.utils;

import java.io.File;

import kr.re.etri.tpl.diagram.editors.TPLDiagramEditor;
import kr.re.etri.tpl.diagram.views.ItemPropertySheetPage;
import kr.re.etri.tpl.diagram.views.ModelNavigatorViewPart;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.views.properties.IPropertySheetPage;

public class DiagramResourceUtil {
	private static final Logger logger = Logger.getLogger(DiagramResourceUtil.class);
	public static File getProjectDir(){
		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		IWorkbenchPage page = window.getActivePage();
		if(page == null){
			logger.warn("Page is null.");
			return null;
		}
		IEditorPart editor = page.getActiveEditor();
		
		if(editor == null){
			logger.warn("Editor is null.");
			return null;
		}
		
		if(!(editor instanceof TPLDiagramEditor)){
			logger.info("Editor is not TPLDiagramEditor.");
			return null;
		}
		
		IEditorInput editorInput = editor.getEditorInput();
		if(!(editorInput instanceof IFileEditorInput)){
			logger.warn("EditorInput is not instance of IFileEditorInput.");
			return null;
		}
		IFileEditorInput fEditorInput = (IFileEditorInput)editorInput;
		IFile f = fEditorInput.getFile();
		IProject prj = f.getProject();
		
		IPath path = prj.getLocation();
		
		File prjDir = path.toFile();
		if(!prjDir.isDirectory()){
			logger.warn("Project directory is not Directory.");
			return null;
		}
		logger.info("Found project directory. path = "+prjDir.getPath());
		return prjDir;
	}	
	
	public static IProject getProject(){
		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		IWorkbenchPage page = window.getActivePage();
		if(page == null){
			logger.warn("Page is null.");
			return null;
		}
		IEditorPart editor = page.getActiveEditor();
		
		if(editor == null){
			logger.warn("Editor is null.");
			return null;
		}
		
		if(!(editor instanceof TPLDiagramEditor)){
			logger.info("Editor is not TPLDiagramEditor.");
			return null;
		}
		
		IEditorInput editorInput = editor.getEditorInput();
		if(!(editorInput instanceof IFileEditorInput)){
			logger.warn("EditorInput is not instance of IFileEditorInput.");
			return null;
		}
		IFileEditorInput fEditorInput = (IFileEditorInput)editorInput;
		IFile f = fEditorInput.getFile();
		IProject prj = f.getProject();
		return prj;
	}
	
	public static void projectNavigatorViewRefresh(){
//		IWorkbench wb = PlatformUI.getWorkbench();
//		IWorkbenchWindow wbw = wb.getActiveWorkbenchWindow();
//		IWorkbenchPage wp = wbw.getActivePage();
//		
//		IViewPart vp = wp.findView(ProjectNavigatorView.VIEW_ID);
//		if(vp != null) {
//			CommonViewer cv = ((CommonNavigator)vp).getCommonViewer();
//			cv.getTree().update();	
//			((CommonNavigator)vp).getViewSite().getShell().redraw();
//			logger.debug("Refresh....");
//			cv.refresh();
//		}
		
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		try {
			root.refreshLocal(IResource.DEPTH_INFINITE, null);
		} catch (CoreException e) {
			logger.error("",e);
		}
	}
	public static void ModelsViewRefresh(){
		IWorkbench wb = PlatformUI.getWorkbench();
		IWorkbenchWindow wbw = wb.getActiveWorkbenchWindow();
		IWorkbenchPage wp = wbw.getActivePage();

		
		
		IViewPart vp = wp.findView(ModelNavigatorViewPart.viewId);
		IViewSite site = vp.getViewSite();
		vp.dispose();
		
		try {
			vp.init(site);
		} catch (PartInitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		((ModelNavigatorViewPart)vp).getCurrentPage().getControl().update();
	
//		if(vp != null) {
//			vp.getViewSite().getShell().redraw();
//			CommonViewer cv = ((CommonNavigator)vp).getCommonViewer();
//			cv.getTree().update();	
//			((CommonNavigator)vp).getViewSite().getShell().redraw();
//			logger.debug("Refresh....");
//			cv.refresh();
//		}
		
//		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
//		try {
//			root.refreshLocal(IResource.DEPTH_INFINITE, null);
//		} catch (CoreException e) {
//			logger.error("",e);
//		}
	}
	
	
	

}
