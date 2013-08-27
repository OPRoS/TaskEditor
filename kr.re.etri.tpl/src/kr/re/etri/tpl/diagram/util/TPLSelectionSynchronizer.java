package kr.re.etri.tpl.diagram.util;

import kr.re.etri.tpl.script.editors.TPLScriptEditor;
import kr.re.etri.tpl.taskmodel.ModelDiagram;
import kr.re.etri.tpl.taskmodel.ReferElement;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.ui.parts.SelectionSynchronizer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.EditorManager;
import org.eclipse.ui.internal.PartPane;
import org.eclipse.ui.internal.PartSite;
import org.eclipse.ui.internal.WorkbenchPage;

public class TPLSelectionSynchronizer extends SelectionSynchronizer {

	// KJH 20100401 이전에 선택된 EditPart를 저장
	private ModelDiagram modelDiagram;	// KJH 20110623
	
	public TPLSelectionSynchronizer(ModelDiagram modelDiagram) {
		super();
		this.modelDiagram = modelDiagram;
	}
	
	/**
	 * Maps the given editpart from one viewer to an editpart in another viewer. It returns
	 * <code>null</code> if there is no corresponding part. This method can be overridden
	 * to provide custom mapping.
	 * @param viewer the viewer being mapped to
	 * @param editPart a part from another viewer
	 * @return <code>null</code> or a corresponding editpart
	 */
	protected EditPart convert(EditPartViewer viewer, EditPart editPart) {
		Object model = editPart.getModel();
		if(model instanceof ReferElement) {
			model = ((ReferElement)model).getRealModel();
		}
		Object temp = viewer.getEditPartRegistry().get(model);
		EditPart newPart = null;
		if (temp != null) {
			newPart = (EditPart)temp;
			if (newPart.isSelectable() == false) {	// KJH 20111007 s, invisible editpart
				return null;
			}
		}
		
		return newPart;
	}

	@Override
	public void selectionChanged(SelectionChangedEvent event) {
		super.selectionChanged(event);
		
		IStructuredSelection selection = (IStructuredSelection)event.getSelection();
		Object firstElement = selection.getFirstElement();
		if (firstElement instanceof EditPart) {
			Object model = ((EditPart)firstElement).getModel();
			if (model instanceof ReferElement) {
				model = ((ReferElement)model).getRealModel();
			}
			
			// KJH 20100401 s, ScriptEditor의 커서 변경을 위한 코드
			if (modelDiagram != null && modelDiagram.getScript() != null) {
				IWorkspaceRoot workRoot = ResourcesPlugin.getWorkspace().getRoot();
				IResource resource = workRoot.findMember(modelDiagram.getScript());

				IWorkbench wb = PlatformUI.getWorkbench();
				IWorkbenchWindow wbw = wb.getActiveWorkbenchWindow();
				IWorkbenchPage wp = wbw.getActivePage();
				EditorManager editorManager = ((WorkbenchPage)wp).getEditorManager();

				IEditorPart curEditor = editorManager.getVisibleEditor();
				PartPane curPane = ((PartSite)curEditor.getEditorSite()).getPane();

				IEditorReference[] ers = wp.getEditorReferences();
				for(IEditorReference er : ers){
					IEditorPart ep = er.getEditor(true);
					if(ep instanceof TPLScriptEditor){

						PartPane selPane = ((PartSite)ep.getEditorSite()).getPane();
						if (curPane.getContainer() == selPane.getContainer()) {
							continue;
						}

						IFile file = ((TPLScriptEditor)ep).getInputFile();
						if(file != null && file.equals(resource)) {
							editorManager.setVisibleEditor(er, false);
							((TPLScriptEditor)ep).selectScript(model);
							break;
						}
					}
				}
			}
			// KJH 20100401 e, ScriptEditor의 커서 변경을 위한 코드
		}
	}

}
