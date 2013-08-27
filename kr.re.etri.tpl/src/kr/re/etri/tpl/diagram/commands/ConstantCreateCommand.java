package kr.re.etri.tpl.diagram.commands;

import kr.re.etri.tpl.diagram.util.ItemState;
import kr.re.etri.tpl.taskmodel.Constant;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.ModelElement;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.requests.CreateRequest;

/**
 * ModelElement �� Constant �� �߰��ϴ� command Ŭ�����̴�.
 * �� command �� Undo/Redo �� �����ϴ�.
 * @author sfline
 */
public class ConstantCreateCommand extends ItemElementCreateCommand {
	
	/**
	 * �� command �� ModelElement �� ���ο� Constant �� �߰��Ѵ�.
	 * @param parentModel ���ο� Constant �� �߰��� �θ� ��
	 * @param req     ���ο� Constant ���� ��û
	 * @throws IllegalArgumentException ���� parameter �� null �̰ų� request��
	 * 							���ο� Constant instance �� �������� ������ �߻��Ѵ�.
	 */
	public ConstantCreateCommand(ItemElement parentModel, CreateRequest req) {
		super(parentModel, req);

		setLabel("const creation");
	}
	
	/**
	 * �� command �� �����Ѵ�.
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	public void execute() {
		Point locPt = request.getLocation();

		// request �κ��� ���ο� Constant ���� ȹ���Ѵ�.
		// Constant ���� palette-tool���� �������� ������, �޴� �������� ���Ͽ�
		// factory �� ���� �����ȴ�.
		newModel = (Constant) request.getNewObject();
		int itemState = newModel.getItemState();
		if(itemState == 0) {
			if(!newModel.isIncluded()) {
				newModel.setName(getNewName("New_Constant"));
				itemState |= ItemState.ITEM_CREATED;
				newModel.setItemState(itemState);
			}
		}

		redo();
	}
	
	/**
	 * ���ο� ��(Constant)�� ModelElement �� �߰��Ѵ�.
	 * �� method �� Redo method ȣ�� �� ����ȴ�.
	 * @Override
	 */
	protected void addToParent(ItemElement newItem) {
		
		// Constant Parent�� ModelElement���� ������.
		if(parentModel instanceof ModelElement) {
			modelAdded = ((ModelElement)parentModel).getConstants().add((Constant)newItem);
			if(modelAdded)	newItem.setParent(parentModel);	// KJH 20100524
		}
	}

	/**
	 * ���ο� ��(Constant)�� ModelElement �κ��� �����Ѵ�.
	 * �� method �� Undo method ȣ�� �� ����ȴ�.
	 * @Override
	 */
	protected void removeFromParent(ItemElement newItem) {
		
		// Constant Parent�� ModelElement���� ������.
		if(parentModel instanceof ModelElement) {
			((ModelElement)parentModel).getConstants().remove((Constant)newItem);
		}
	}
}
