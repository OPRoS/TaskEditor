package kr.re.etri.tpl.diagram.views;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import kr.re.etri.tpl.IRTMDefines;
import kr.re.etri.tpl.diagram.editors.DiagramResourceManager;
import kr.re.etri.tpl.script.editors.TPLScriptEditor;
import kr.re.etri.tpl.script.grammar.tree2.ScriptManager;
import kr.re.etri.tpl.taskmodel.ModelDiagram;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceImpl;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.navigator.CommonViewer;

public class ProjectCommonViewer extends CommonViewer {

	public ProjectCommonViewer(String aViewerId, Composite aParent, int aStyle) {
		super(aViewerId, aParent, aStyle);
	}

	@Override
	public void add(Object parentElement, Object[] childElements) {
		super.add(parentElement, childElements);

//		updateElements(childElements);

		updateEditor(childElements);
	}

	@Override
	public void remove(Object[] elements) {
		super.remove(elements);
		
		// KJH 20110623 s, 삭제시 스크립트 트리 갱신
		List<IResource> projects = new ArrayList<IResource>();
		for (Object element : elements) {
			if (element instanceof IResource) {
				IResource project = ((IResource)element).getProject();
				if (project != null && !projects.contains(project)) {
					projects.add(project);
				}
			}
			if (element instanceof IFile) {
				IFile file = (IFile)element;
				Resource resource = DiagramResourceManager.getResource(file);
				if (resource.getContents().size() > 0) {
					ModelDiagram modelDiagram = (ModelDiagram)resource.getContents().get(0);
					String script = modelDiagram.getScript();
				}
				DiagramResourceManager.setResource(file, null);
			}
		}
		for (IResource project : projects) {
			ScriptManager.getInstance().getTPLList(project);
		}
		// KJH 20110623 e, 삭제시 스크립트 트리 갱신
		
		updateEditor(elements);
	}

	@Override
	public void refresh(Object element, boolean updateLabels) {
		super.refresh(element, updateLabels);
		
		if (element != null)
			updateEditor(new Object[]{element});
	}

	// KJH 20101115 s, modify
	private void updateEditor(Object[] elements){
		IWorkbench wb = PlatformUI.getWorkbench();
		IWorkbenchWindow wbw = wb.getActiveWorkbenchWindow();
		IWorkbenchPage wp = wbw.getActivePage();
		IEditorPart ep = wp.getActiveEditor();
		
		if(ep instanceof TPLScriptEditor){
			((TPLScriptEditor) ep).updateTaskView();
			((TPLScriptEditor) ep).updateModelView();
		}
	}
	// KJH 20101115 e, modify

	/**
	 * @author KJH 20111010
	 */
	private void updateElements(Object[] elements) {
		if (elements == null) {
			return;
		}
		
		for (Object obj : elements) {
			if (obj instanceof IFile) {
				IFile file = (IFile)obj;
				String fileExt = file.getFileExtension();
				String filePath = file.getFullPath().toString();
				
				if (IRTMDefines.TASK_SCRIPT_EXTENSION_NAME.equals(fileExt) == false) {
					continue;
				}
				
				IFile tpdFile = getTPDFile(file.getProject(), filePath);
				if (tpdFile == null || tpdFile.exists() == false) {
					continue;
				}
				
				Resource resource = DiagramResourceManager.getResource(tpdFile);
				if (resource != null) {
					EList<EObject> contents = resource.getContents();
					if (contents != null && contents.size() > 0) {
						EObject eObject = contents.get(0);
						if (eObject instanceof ModelDiagram) {
							ModelDiagram modelDiagram = (ModelDiagram)eObject;
							modelDiagram.setScript(filePath);
						}
					}
				}
			}
			else if (obj instanceof IFolder) {
				IFolder folder = (IFolder)obj;
				try {
					updateElements(folder.members());
				} catch (CoreException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * @author KJH 20111010
	 * @param container
	 * @param scriptName
	 * @return
	 */
	private IFile getTPDFile(IContainer container, String scriptName) {
		if (container == null) {
			return null;
		}
		if (scriptName == null || scriptName.equals("")) {
			return null;
		}
		
		XMLResource resource = new XMLResourceImpl();
		IFile tpdFile = null;
		try {
			for(IResource member : container.members()){
				if(member instanceof IFile){
					IFile file = (IFile)member;
					if(!IRTMDefines.TASK_DIAGRAM_EXTENSION_NAME.equals(file.getFileExtension())) {
						continue;
					}

					InputStream inputStream = file.getContents(true);
					resource.load(inputStream, Collections.EMPTY_MAP);

					if(resource.getContents().size() > 0){
						ModelDiagram rootModel = (ModelDiagram)resource.getContents().get(0);
						if(scriptName.equals(rootModel.getScript())) {
							return file;
						}
					}
				}
				else if(member instanceof IContainer) {
					tpdFile = getTPDFile((IContainer)member, scriptName);
				}
			}
		} catch (CoreException e) {
			return null;
		} catch (IOException e) {
			return null;
		}
		
		return tpdFile;
	}
}
