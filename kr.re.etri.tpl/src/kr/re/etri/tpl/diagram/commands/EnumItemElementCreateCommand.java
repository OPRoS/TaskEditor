package kr.re.etri.tpl.diagram.commands;

import kr.re.etri.tpl.diagram.util.ItemState;
import kr.re.etri.tpl.taskmodel.EnumElement;
import kr.re.etri.tpl.taskmodel.EnumItemElement;
import kr.re.etri.tpl.taskmodel.ItemElement;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.requests.CreateRequest;

/**
 * ���ο� EnumItemElement �� EnumElement �� �߰��ϴ� command Ŭ�����̴�.
 * �� command �� Undo/Redo �� �����ϴ�.
 * @author sfline
 */
public class EnumItemElementCreateCommand extends ItemElementCreateCommand {
	
	/**
	 * �� command �� EnumElement �� ���ο� EnumItemElement �� �߰��Ѵ�.
	 * @param parentModel ���ο� EnumItemElement �� �߰��� �θ� ��(EnumElement)
	 * @param req     ���ο� EnumItemElement ���� ��û
	 * @throws IllegalArgumentException ���� parameter �� null �̰ų� request��
	 * 							���ο� EnumItemElement instance �� �������� ������ �߻��Ѵ�.
	 */
	public EnumItemElementCreateCommand(ItemElement parentModel, CreateRequest req) {
		super(parentModel, req);

		setLabel("enum element creation");
	}

	/**
	 * �� command �� �����Ѵ�.
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	public void execute() {
		Point locPt = request.getLocation();

		// request �κ��� ���ο� EnumItemElement ���� ȹ���Ѵ�.
		// Constant ���� palette-tool���� �������� ������, �޴� �������� ���Ͽ�
		// factory �� ���� �����ȴ�.
		newModel = (EnumItemElement) request.getNewObject();
		int itemState = newModel.getItemState();
		if(itemState == 0) {
			if(!newModel.isIncluded()) {
				newModel.setName(getNewName("New_Element"));
				itemState |= ItemState.ITEM_CREATED;
				newModel.setItemState(itemState);
			}
		}

		redo();
	}
	
	/**
	 * ���ο� ��(EnumItemElement)�� EnumElement �� �߰��Ѵ�.
	 * �� method �� Redo method ȣ�� �� ����ȴ�.
	 */
	protected void addToParent(ItemElement newItem) {
		
		// EnumItemElement Parent�� EnumElement ������.
		if(parentModel instanceof EnumElement) {
			modelAdded = ((EnumElement)parentModel).getEnumItem().add((EnumItemElement)newItem);
			if(modelAdded)	newItem.setParent(parentModel);	// KJH 20100524
		}
	}
	
	/**
	 * ���ο� ��(EnumItemElement)�� EnumElement �κ��� �����Ѵ�.
	 * �� method �� Undo method ȣ�� �� ����ȴ�.
	 */
	protected void removeFromParent(ItemElement newItem) {
		
		// EnumItemElement Parent�� EnumElement ������.
		if(parentModel instanceof EnumElement) {
			((EnumElement)parentModel).getEnumItem().remove((EnumItemElement)newItem);
		}
	}
}
