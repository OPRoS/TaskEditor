package kr.re.etri.tpl.diagram.views.actions;

import kr.re.etri.tpl.diagram.ModelManager;
import kr.re.etri.tpl.diagram.commands.TaskElementCreateCommand;
import kr.re.etri.tpl.taskmodel.ModelDiagram;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gef.requests.CreationFactory;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;

public class NewTaskAction extends Action {
	
	private CommandStack commandStack;
	private ModelDiagram parentModel;

	public final static String actionId = "kr.re.etri.tpl.views.WorkerNavigatorViewPage.NewWorkerAction";
	
	public NewTaskAction(ModelDiagram model, CommandStack cmdStack) {
		this("&New Task", model, cmdStack);
	}

	public NewTaskAction(String text, ModelDiagram model, CommandStack cmdStack) {
		super(text);
		
		this.commandStack = cmdStack;
		this.parentModel = model;
	}

	public NewTaskAction(String text, ImageDescriptor image) {
		super(text, image);
	}

	public NewTaskAction(String text, int style) {
		super(text, style);
	}

	public String getId() {
		return actionId;
	}

	public CommandStack getCommandStack() {
		return commandStack;
	}
	/**
	 * The default implementation of this <code>IAction</code> method does
	 * nothing. Subclasses should override this method if they do not need
	 * information from the triggering event, or override
	 * <code>runWithEvent(Event)</code> if they do.
	 */
	public void run() {
		CreateRequest request = new CreateRequest();
		request.setLocation(new Point(10, 10));
		request.setFactory(new CreationFactory() {
			public Object getNewObject() { return ModelManager.getFactory().createTaskElement(); }
			public Object getObjectType() { return null; }
		});
		Rectangle constraint = new Rectangle(10, 10, 100, 70);
		TaskElementCreateCommand cmd = new TaskElementCreateCommand(parentModel, request, constraint);
		CommandStack cmdStack = getCommandStack();

		cmdStack.execute(cmd);
	}
}
