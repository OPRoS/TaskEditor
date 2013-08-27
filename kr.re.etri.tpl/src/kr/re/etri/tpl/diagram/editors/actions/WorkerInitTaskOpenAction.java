package kr.re.etri.tpl.diagram.editors.actions;

import kr.re.etri.tpl.diagram.dialogs.WorkerInitTaskDialog;
import kr.re.etri.tpl.taskmodel.ModelDiagram;
import kr.re.etri.tpl.taskmodel.BehaviorElement;

import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

public class WorkerInitTaskOpenAction extends Action {

	public static final String actionId = "kr.re.etri.tpl.diagram.editors.actions.WorkerInitTaskOpenAction";
	
	private DefaultEditDomain editDomain;
	
	private ModelDiagram modelDiagram;
	
	private BehaviorElement taskElement;
	
	public WorkerInitTaskOpenAction(DefaultEditDomain editDomain) {
		super("&set init task");
		this.editDomain = editDomain;
	}

	public WorkerInitTaskOpenAction(String text, DefaultEditDomain editDomain) {
		super(text);
		
		this.editDomain = editDomain;
	}

	public WorkerInitTaskOpenAction(DefaultEditDomain editDomain, String text, ImageDescriptor image) {
		super(text, image);
		
		this.editDomain = editDomain;
	}

	public WorkerInitTaskOpenAction(DefaultEditDomain editDomain, String text, int style) {
		super(text, style);
		
		this.editDomain = editDomain;
	}
	
	public void setModelDiagram(ModelDiagram modelDiagram) {
		this.modelDiagram = modelDiagram;
	}
	
	public void setTaskElement(BehaviorElement taskElement) {
		this.taskElement = taskElement;
	}

	public String getId() {
		return actionId;
	}
	
	public void run() {
		WorkerInitTaskDialog dialog;
		String title = "Conditions for transition.";
		String message = "Please enter the transition conditions.";

		Shell shell = PlatformUI.getWorkbench().getDisplay().getActiveShell();
		dialog = new WorkerInitTaskDialog(shell, title, message);
		dialog.setEditDomain(this.editDomain);
		dialog.setModelDiagram(modelDiagram);
		dialog.setTaskElement(taskElement);
		dialog.open();
	}
}
