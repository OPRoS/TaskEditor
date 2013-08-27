package kr.re.etri.tpl.diagram.commands;

import kr.re.etri.tpl.diagram.util.ItemState;
import kr.re.etri.tpl.taskmodel.Function;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.ModelElement;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.requests.CreateRequest;

/**
 * ModelElement �� Function �� �߰��ϴ� command Ŭ�����̴�.
 * �� command �� Undo/Redo �� �����ϴ�.
 * @author sfline
 */
public class FunctionCreateCommand extends ItemElementCreateCommand {
	
	/**
	 * �� command �� ModelElement�� ���ο� Function �� �߰��Ѵ�.
	 * @param parentModel ���ο� Function �� �߰��� �θ� ��
	 * @param req     ���ο� Function ���� ��û
	 * @throws IllegalArgumentException ���� parameter �� null �̰ų� request��
	 * 							���ο� Constant instance �� �������� ������ �߻��Ѵ�.
	 */
	public FunctionCreateCommand(ItemElement parentModel, CreateRequest req) {
		super(parentModel, req);

		setLabel("function creation");
	}
	
	/**
	 * �� command �� �����Ѵ�.
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	public void execute() {
		Point locPt = request.getLocation();

		// request �κ��� ���ο� Function ���� ȹ���Ѵ�.
		// Constant ���� palette-tool���� �������� ������, �޴� �������� ���Ͽ�
		// factory �� ���� �����ȴ�.
		newModel = (Function) request.getNewObject();
		int itemState = newModel.getItemState();
		if(itemState == 0) {
			if(!newModel.isIncluded()) {
				newModel.setName(getNewName("New_Function"));
				itemState |= ItemState.ITEM_CREATED;
				newModel.setItemState(itemState);
			}
		}

		redo();
	}
	
	/**
	 * ���ο� ��(Function)�� ModelElement �� �߰��Ѵ�.
	 * �� method �� Redo method ȣ�� �� ����ȴ�.
	 * @param newItem �θ� �𵨿� �߰��� �ڽ� ��
	 */
	protected void addToParent(ItemElement newItem) {
		
		// Function Parent�� ModelElement���� ������.
		if(parentModel instanceof ModelElement) {
			modelAdded = ((ModelElement)parentModel).getFunctions().add((Function)newItem);
			if(modelAdded)	newItem.setParent(parentModel);	// KJH 20100524
		}
	}
	
	/**
	 * ���ο� ��(Function)�� ModelElement �κ��� �����Ѵ�.
	 * �� method �� Undo method ȣ�� �� ����ȴ�.
	 * @param newItem �θ� �𵨿��� ������ �ڽ� ��
	 */
	protected void removeFromParent(ItemElement newItem) {
		
		// Function Parent�� ModelElement���� ������.
		if(parentModel instanceof ModelElement) {
			((ModelElement)parentModel).getFunctions().remove((Function)newItem);
		}
	}
}
