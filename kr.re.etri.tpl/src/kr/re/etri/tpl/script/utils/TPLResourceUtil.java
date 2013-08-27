package kr.re.etri.tpl.script.utils;

import java.io.File;

import kr.re.etri.tpl.script.editors.TPLScriptEditor;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

public class TPLResourceUtil {
	private static final Logger logger = Logger.getLogger(TPLResourceUtil.class);
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
		
		if(!(editor instanceof TPLScriptEditor)){
			logger.info("Editor is not TPLEditor.");
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
	
	public static TPLScriptEditor getTPLEditor(){
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
		
		if(!(editor instanceof TPLScriptEditor)){
			logger.info("Editor is not TPLEditor.");
			return null;
		}
		
		return (TPLScriptEditor)editor;
	}
}
