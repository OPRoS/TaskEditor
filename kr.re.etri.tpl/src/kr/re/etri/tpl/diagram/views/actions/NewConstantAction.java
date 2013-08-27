package kr.re.etri.tpl.diagram.views.actions;

import kr.re.etri.tpl.grammar.RTMType;
import kr.re.etri.tpl.taskmodel.Constant;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.diagram.ModelManager;
import kr.re.etri.tpl.diagram.commands.ConstantCreateCommand;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gef.requests.CreationFactory;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;

public class NewConstantAction extends Action {
	
	private CommandStack commandStack;
	private ItemElement rootModel;
	private ItemElement parentModel;

	public final static String actionId = "kr.re.etri.tpl.views.SymbolNavigatorViewPage.NewConstantAction";
	
	public NewConstantAction(ItemElement rootModel, CommandStack cmdStack) {
		this("&New Constant", rootModel, cmdStack);
	}

	public NewConstantAction(String text, ItemElement rootModel, CommandStack cmdStack) {
		super(text);
		
		this.commandStack = cmdStack;
		this.rootModel = rootModel;
	}

	public NewConstantAction(String text, ImageDescriptor image) {
		super(text, image);
	}

	public NewConstantAction(String text, int style) {
		super(text, style);
	}

	public String getId() {
		return actionId;
	}

	public void setParent(ItemElement parentModel) {
		this.parentModel = parentModel;
	}
	
	public ItemElement getParent() {
		if(this.parentModel == null) {
			return this.rootModel;
		}
		
		return this.parentModel;
	}
	
	public void setCommandStack(CommandStack commandStack) {
		this.commandStack = commandStack;
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
			public Object getNewObject() {
				Constant constModel = ModelManager.getFactory().createConstant();
				constModel.setName("New_Constant");
				constModel.setType(RTMType.INT.toString());
				
				return constModel;
			}
			public Object getObjectType() { return null; }
		});
		
		ConstantCreateCommand cmd = new ConstantCreateCommand(getParent(), request);
		CommandStack cmdStack = getCommandStack();

		cmdStack.execute(cmd);
	}
}
