package kr.re.etri.tpl.actions;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;

import kr.re.etri.tpl.IRTMDefines;
import kr.re.etri.tpl.diagram.ModelManager;
import kr.re.etri.tpl.diagram.commands.UpdateDiagramCommand;
import kr.re.etri.tpl.diagram.editors.TPLDiagramEditor;
import kr.re.etri.tpl.diagram.util.MarkerLogger;
import kr.re.etri.tpl.grammar.ParserUtil;
import kr.re.etri.tpl.script.editors.TPLScriptEditor;
import kr.re.etri.tpl.taskmodel.ModelDiagram;
import kr.re.etri.tpl.taskmodel.SubDiagram;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceImpl;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;


public class UpdateDiagramActionDelegate implements IWorkbenchWindowActionDelegate {
	private static Logger logger = Logger.getLogger(UpdateDiagramActionDelegate.class);
	private IWorkbenchWindow m_window;

	@Override
	public void dispose() {
	}

	@Override
	public void init(IWorkbenchWindow window) {
		m_window = window;
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		IWorkbench wb = PlatformUI.getWorkbench();
		IWorkbenchWindow wbw = wb.getActiveWorkbenchWindow();
		IWorkbenchPage wp = wbw.getActivePage();
		if (wp == null) {
			return;
		}
		IEditorPart editPart= wp.getActiveEditor();
		
		if(editPart instanceof TPLScriptEditor) {
			action.setEnabled(true);
		}
		else {
			action.setEnabled(false);
		}
	}

	@Override
	public void run(IAction action) {
		TPLScriptEditor scriptEditor = getTPLScriptEditor();
		if(scriptEditor == null){
			logger.debug("선택된 편집기는 TPLScriptEditor가 아닙니다.");
			return;
		}
		
		IFile file = scriptEditor.getInputFile();
		if (file == null) {
			return;
		}

		IProject project = file.getProject();
		IContainer container = file.getParent();
		String scriptName = file.getFullPath().toString();
		String diagramName = file.getName();
		int idx;
		if ((idx = diagramName.lastIndexOf(".")) > -1) {
			diagramName = diagramName.substring(0, idx);
		}
		if ((idx = diagramName.lastIndexOf("_task")) > -1) {
			diagramName = diagramName.substring(0, idx);
		}
		
		MarkerLogger markerLogger = new MarkerLogger(file, MarkerLogger.TYPE_SCRIPT);
		IDocument doc = scriptEditor.getInputDocument();
		ModelDiagram newModelDiagram = null;
		if (doc != null) {
			newModelDiagram = ParserUtil.parseModel(file,
					new ByteArrayInputStream(doc.get().getBytes()), markerLogger);
		}
		else {
			newModelDiagram = ParserUtil.parseModel(file, markerLogger);
		}
		newModelDiagram.setScript(file.getFullPath().toString());
		
		TPLDiagramEditor diagramEditor = getTPLDiagramEditor(scriptName);
		if(diagramEditor != null){
			ModelDiagram modelDiagram = (ModelDiagram)diagramEditor.getModel();
			if (modelDiagram == null) {
				modelDiagram = ModelManager.getFactory().createModelDiagram();
				modelDiagram.setName(diagramName);
			}

			CommandStack commandStack = diagramEditor.getCommandStack();
			UpdateDiagramCommand command = new UpdateDiagramCommand(modelDiagram, newModelDiagram);
			commandStack.execute(command);
		}
		else{
			IFile targetFile = getTargetFile(project, scriptName);
			if (targetFile == null) {
				diagramName = String.format("%s.%s", diagramName, IRTMDefines.TASK_DIAGRAM_EXTENSION_NAME);
				if (container instanceof IProject) {
					targetFile = ((IProject)container).getFile(diagramName);
				}
				else if (container instanceof IFolder) {
					targetFile = ((IFolder)container).getFile(diagramName);
				}
			}
			ModelDiagram modelDiagram = getModelDiagram(targetFile);
			if (modelDiagram == null) {
				modelDiagram = ModelManager.getFactory().createModelDiagram();
				modelDiagram.setName(diagramName);
			}
			if (modelDiagram.getSubDiagram() == null) {
				SubDiagram subDiagram = ModelManager.getFactory().createSubDiagram();
				modelDiagram.setSubDiagram(subDiagram);
				subDiagram.setParent(modelDiagram);
			}
		
			UpdateDiagramCommand command = new UpdateDiagramCommand(modelDiagram, newModelDiagram);
			command.execute();
			
			try {
				XMLResource resource = new XMLResourceImpl();
				resource.getContents().add(modelDiagram);
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				resource.save(out, Collections.EMPTY_MAP);
				ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
				if (targetFile.exists()) {
					// KJH 20110928, 동일이름의 파일 있을 경우
					String[] dialogButtonLabels = new String[]{"확인", "취소"};
					StringBuffer sb = new StringBuffer();
					sb.append("선택된 경로에 이미 파일이 있습니다. 파일을 바꾸시겠습니까?").append("\r\n").append("\r\n");
					sb.append(targetFile.getFullPath().toString());
					MessageDialog msgDialog = new MessageDialog(m_window.getShell(), "TPL", null, sb.toString(), MessageDialog.QUESTION, dialogButtonLabels, 0);
					if(msgDialog.open() != Dialog.OK) {
						return;
					}
					
					targetFile.setContents(in, true, true, null);
				}
				else {
					targetFile.create(in, true, null);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
	}
	
	private TPLScriptEditor getTPLScriptEditor(){
		IWorkbenchPage page = m_window.getActivePage();
		IEditorPart editor = page.getActiveEditor();
		
		if(!(editor instanceof TPLScriptEditor))
			return null;
		
		return (TPLScriptEditor)editor;
	}
	
	private TPLDiagramEditor getTPLDiagramEditor(String fileName){
		IWorkbenchPage page = m_window.getActivePage();
		IEditorReference[] ers = page.getEditorReferences();
		
		for(IEditorReference er : ers){
			IEditorPart part = er.getEditor(true);
			if(!(part instanceof TPLDiagramEditor))
				continue;

			Resource resource = ((TPLDiagramEditor)part).getResource();
			EList<EObject> list = resource.getContents();
			for(EObject object : list){
				if(!(object instanceof ModelDiagram))
					continue;

				String script = ((ModelDiagram)object).getScript();
				if(fileName.equals(script))
					return (TPLDiagramEditor)part;
			}
		}
		
		return null;
	}
	
	private IFile getTargetFile(IContainer container, String scriptName) {
		if (container == null) {
			return null;
		}
		if (scriptName == null || scriptName.equals("")) {
			return null;
		}
		
		XMLResource resource = new XMLResourceImpl();
		IFile targetFile = null;
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
					targetFile = getTargetFile((IContainer)member, scriptName);
				}
			}
		} catch (CoreException e) {
			return null;
		} catch (IOException e) {
			return null;
		}
		
		return targetFile;
	}
	
	private ModelDiagram getModelDiagram(IFile file) {
		if (file == null || file.exists() == false) {
			return null;
		}
		if(!IRTMDefines.TASK_DIAGRAM_EXTENSION_NAME.equals(file.getFileExtension())) {
			return null;
		}
		
		XMLResource resource = new XMLResourceImpl();
		ModelDiagram rootModel = null;

		InputStream inputStream;
		try {
			inputStream = file.getContents(true);
			resource.load(inputStream, Collections.EMPTY_MAP);
		} catch (CoreException e) {
			logger.error(e.getMessage());
			return null;
		} catch (IOException e) {
			logger.error(e.getMessage());
			return null;
		}

		if(resource.getContents().size() > 0){
			rootModel = (ModelDiagram)resource.getContents().get(0);
		}
		
		return rootModel;
	}
	
}
