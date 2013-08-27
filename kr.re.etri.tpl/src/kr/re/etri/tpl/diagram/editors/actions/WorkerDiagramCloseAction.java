package kr.re.etri.tpl.diagram.editors.actions;

import kr.re.etri.tpl.diagram.editors.TPLDiagramEditor;

import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.EditDomain;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;

public class WorkerDiagramCloseAction extends Action {

	public static final String actionId = "kr.re.etri.tpl.editors.actions.WorkerDiagramCloseAction";
	
	private DefaultEditDomain editDomain;
	
	public WorkerDiagramCloseAction(DefaultEditDomain editDomain) {
		super("&Close Diagram");

		this.editDomain = editDomain;
	}

	public WorkerDiagramCloseAction(DefaultEditDomain editDomain, String text) {
		super(text);
		
		this.editDomain = editDomain;
	}

	public WorkerDiagramCloseAction(DefaultEditDomain editDomain, String text, ImageDescriptor image) {
		super(text, image);
		
		this.editDomain = editDomain;
	}

	public WorkerDiagramCloseAction(DefaultEditDomain editDomain, String text, int style) {
		super(text, style);
		
		this.editDomain = editDomain;
	}
	
	public String getId() {
		return actionId;
	}
	
	public void run() {
		IEditorPart editPart = ((DefaultEditDomain)editDomain).getEditorPart();
		IEditorPart activePart = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		
		if(editPart == activePart) {
			
		}
		else if(activePart instanceof TPLDiagramEditor) {
			TPLDiagramEditor rtmEditor = (TPLDiagramEditor)activePart;

			rtmEditor.removePage(editPart);
		}
		else {
			editPart.getEditorSite().getPage().close();
		}
	}
}
