package kr.re.etri.tpl.diagram.commands;

import kr.re.etri.tpl.diagram.util.ItemState;
import kr.re.etri.tpl.taskmodel.ActionElement;
import kr.re.etri.tpl.taskmodel.BehaviorElement;
import kr.re.etri.tpl.taskmodel.Function;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.Parameter;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.requests.CreateRequest;

/**
 * �θ� �𵨿� Parameter �� �߰��ϴ� command Ŭ�����̴�.
 * Parameter �� �߰��� ���ִ� �θ� ����
 * ActionElement, TaskElement, Function
 * �� ���� �ִ�.
 * �� command �� Undo/Redo �� �����ϴ�.
 * @author sfline
 */
public class ParameterCreateCommand extends ItemElementCreateCommand {
	
	/**
	 * �� command �� �θ� �𵨿� ���ο� Parameter �� �߰��Ѵ�.
	 * @param parentModel ���ο� Parameter �� �߰��� �θ� ��
	 * @param req     ���ο� Parameter ���� ��û
	 * @throws IllegalArgumentException ���� parameter �� null �̰ų� request��
	 * 							���ο� Parameter instance �� �������� ������ �߻��Ѵ�.
	 */
	public ParameterCreateCommand(ItemElement parentModel, CreateRequest req) {
		super(parentModel, req);

		setLabel("add parameter");
	}
	
	/**
	 * �� command �� �����Ѵ�.
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	public void execute() {
		Point locPt = request.getLocation();

		// request �κ��� ���ο� Parameter ���� ȹ���Ѵ�.
		// Parameter ���� palette-tool���� �������� ������, �޴� �������� ���Ͽ�
		// factory �� ���� �����ȴ�.
		newModel = (Parameter) request.getNewObject();
		int itemState = newModel.getItemState();
		if(itemState == 0) {
			if(!newModel.isIncluded()) {
				newModel.setName(getNewName("New_Param"));
				itemState |= ItemState.ITEM_CREATED;
				newModel.setItemState(itemState);
			}
		}

		redo();
	}

	/**
	 * ���ο� ��(Parameter)�� �θ� �𵨿� �߰��Ѵ�.
	 * �� method �� Redo method ȣ�� �� ����ȴ�.
	 */
	protected void addToParent(ItemElement newItem) {
		
		// Parameter Parent�� ActionElement, TaskElement, Function���� ������.
		if(parentModel instanceof ActionElement) {
			modelAdded = ((ActionElement)parentModel).getParams().add((Parameter)newItem);
			if(modelAdded)	newItem.setParent(parentModel);	// KJH 20100524
		}
		else if(parentModel instanceof BehaviorElement) {
			modelAdded = ((BehaviorElement)parentModel).getParams().add((Parameter)newItem);
			if(modelAdded)	newItem.setParent(parentModel);	// KJH 20100524
		}
		else if(parentModel instanceof Function) {
			modelAdded = ((Function)parentModel).getParams().add((Parameter)newItem);
			if(modelAdded)	newItem.setParent(parentModel);	// KJH 20100524
		}
	}

	/**
	 * ���ο� ��(Parameter)�� �θ� �𵨷κ��� �����Ѵ�.
	 * �� method �� Undo method ȣ�� �� ����ȴ�.
	 */
	protected void removeFromParent(ItemElement newItem) {
		
		// Parameter Parent�� ActionElement, TaskElement, Function���� ������.
		if(parentModel instanceof ActionElement) {
			((ActionElement)parentModel).getParams().remove((Parameter)newItem);
		}
		else if(parentModel instanceof BehaviorElement) {
			((BehaviorElement)parentModel).getParams().remove((Parameter)newItem);
		}
		else if(parentModel instanceof Function) {
			((Function)parentModel).getParams().remove((Parameter)newItem);
		}
	}
}
