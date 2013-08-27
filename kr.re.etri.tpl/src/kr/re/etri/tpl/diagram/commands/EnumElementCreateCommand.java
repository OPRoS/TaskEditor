package kr.re.etri.tpl.diagram.commands;

import kr.re.etri.tpl.diagram.util.ItemState;
import kr.re.etri.tpl.taskmodel.EnumElement;
import kr.re.etri.tpl.taskmodel.IncludedElement;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.ModelDiagram;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.requests.CreateRequest;

/**
 * EnumElement �� �θ� ��(ModelDiagram, IncludedElement) �� �߰��ϴ� command Ŭ�����̴�.
 * �� command �� Undo/Redo �� �����ϴ�.
 * @author sfline
 */
public class EnumElementCreateCommand extends ItemElementCreateCommand {

	/**
	 * �θ� ��(ModelDiagram, IncludedElement) �� ���ο� EnumElement �� �߰��ϴ� command �� �����Ѵ�.
	 * @param parentModel ���ο� EnumElement �� �߰��� �θ� ��
	 * @param req     ���ο� EnumElement ���� ��û
	 * @throws IllegalArgumentException ���� parameter �� null �̰ų� request��
	 * 							���ο� EnumElement instance �� �������� ������ �߻��Ѵ�.
	 */
	public EnumElementCreateCommand(ItemElement parentModel, CreateRequest req) {
		super(parentModel, req);

		setLabel("enum creation");
	}
	
	/**
	 * �� command �� �����Ѵ�.
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	public void execute() {
		Point locPt = request.getLocation();

		// request �κ��� ���ο� EnumElement ���� ȹ���Ѵ�.
		// Constant ���� palette-tool���� �������� ������, �޴� �������� ���Ͽ�
		// factory �� ���� �����ȴ�.
		newModel = (EnumElement) request.getNewObject();
		int itemState = newModel.getItemState();
		if(itemState == 0) {
			if(!newModel.isIncluded()) {
				newModel.setName(getNewName("New_Enum"));
				itemState |= ItemState.ITEM_CREATED;
				newModel.setItemState(itemState);
			}
		}

		redo();
	}
	
	/**
	 * ���ο� ��(EnumElement)�� ModelElement �� �߰��Ѵ�.
	 * �� method �� Redo method ȣ�� �� ����ȴ�.
	 */
	protected void addToParent(ItemElement newItem) {
		
		// Enum�� Parent�� ModelDiagram, IncludedElement���� ������.
		if(parentModel instanceof ModelDiagram) {
			modelAdded = ((ModelDiagram)parentModel).getItems().add(newItem);
			if(modelAdded)	newItem.setParent(parentModel);	// KJH 20100524
		}
		else if(parentModel instanceof IncludedElement) {
			modelAdded = ((IncludedElement)parentModel).getItems().add(newItem);
			if(modelAdded)	newItem.setParent(parentModel);	// KJH 20100524
		}
	}
	
	/**
	 * ���ο� ��(EnumElement)�� �θ� �𵨷κ��� �����Ѵ�.
	 * �� method �� Undo method ȣ�� �� ����ȴ�.
	 */
	protected void removeFromParent(ItemElement newItem) {
		
		// Enum�� Parent�� ModelDiagram, IncludedElement���� ������.
		if(parentModel instanceof ModelDiagram) {
			((ModelDiagram)parentModel).getItems().remove(newItem);
		}
		else if(parentModel instanceof IncludedElement) {
			((IncludedElement)parentModel).getItems().remove(newItem);
		}
	}
}
