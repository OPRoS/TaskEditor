package kr.re.etri.tpl.diagram.views.actions;

import kr.re.etri.tpl.diagram.editors.TPLDiagramEditor;
import kr.re.etri.tpl.taskmodel.TaskElement;

import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

public class OpenWorkerAction extends Action {
	private DefaultEditDomain editDomain;
	private CommandStack commandStack;
	private TaskElement parentWorker;

	public final static String actionId = "kr.re.etri.tpl.views.WorkerNavigatorViewPage.OpenWorkerAction";
	
	public OpenWorkerAction(DefaultEditDomain editDomain, CommandStack cmdStack) {
		this("&Open Diagram", editDomain, cmdStack);
	}

	public OpenWorkerAction(String text, DefaultEditDomain editDomain, CommandStack cmdStack) {
		super(text);

		this.commandStack = cmdStack;
		this.editDomain = editDomain;
	}

	public OpenWorkerAction(String text, ImageDescriptor image) {
		super(text, image);
	}

	public OpenWorkerAction(String text, int style) {
		super(text, style);
	}

	public String getId() {
		return actionId;
	}

	public CommandStack getCommandStack() {
		return commandStack;
	}

	public void setWorker(TaskElement worker) {
		parentWorker = worker;
	}
	/**
	 * The default implementation of this <code>IAction</code> method does
	 * nothing. Subclasses should override this method if they do not need
	 * information from the triggering event, or override
	 * <code>runWithEvent(Event)</code> if they do.
	 */
	public void run() {
		IEditorPart editPart = editDomain.getEditorPart();
//		IEditorPart editPart = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		if(editPart instanceof TPLDiagramEditor) {
			TPLDiagramEditor rtmEditor = (TPLDiagramEditor)editPart;
			try {
				if(parentWorker != null) {
					rtmEditor.addWorkerDiagramPage(parentWorker, commandStack);
				}
				parentWorker = null;
			} catch (PartInitException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
