package kr.re.etri.tpl.diagram.views.actions;

import kr.re.etri.tpl.diagram.ModelManager;
import kr.re.etri.tpl.diagram.commands.ActionElementCreateCommand;
import kr.re.etri.tpl.taskmodel.ModelDiagram;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gef.requests.CreationFactory;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;

public class NewActionAction extends Action {
	
	private CommandStack commandStack;
	private ModelDiagram parentModel;

	public final static String actionId = "kr.re.etri.tpl.views.ActionNavigatorViewPage.NewActionAction";
	
	public NewActionAction(ModelDiagram model, CommandStack cmdStack) {
		this("&New Action", model, cmdStack);
	}

	public NewActionAction(String text, ModelDiagram model, CommandStack cmdStack) {
		super(text);
		
		this.commandStack = cmdStack;
		this.parentModel = model;
	}

	public NewActionAction(String text, ImageDescriptor image) {
		super(text, image);
	}

	public NewActionAction(String text, int style) {
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
			public Object getNewObject() { return ModelManager.getFactory().createActionElement(); }
			public Object getObjectType() { return null; }
		});
		Rectangle constraint = new Rectangle(10, 10, 100, 70);
		ActionElementCreateCommand cmd = new ActionElementCreateCommand(parentModel, request,constraint);
		CommandStack cmdStack = getCommandStack();

		cmdStack.execute(cmd);
	}
}
