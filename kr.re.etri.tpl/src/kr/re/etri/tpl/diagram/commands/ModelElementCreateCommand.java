package kr.re.etri.tpl.diagram.commands;

import kr.re.etri.tpl.diagram.util.ItemState;
import kr.re.etri.tpl.taskmodel.IncludedElement;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.ModelDiagram;
import kr.re.etri.tpl.taskmodel.ModelElement;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.requests.CreateRequest;

/**
 * �θ� �𵨿� ModelElement �� �����ϴ� command Ŭ�����̴�.
 * �� command �� Redo/Undo �� �����ϴ�.
 * @author sfline
 */
public class ModelElementCreateCommand extends ItemElementCreateCommand {
	
	/**
	 * �θ� ��(ModelDiagram, IncludedElement)�� ���ο� ModelElement�� �߰��ϴ�
	 * command �� �����Ѵ�.
	 * @param parentModel ���ο� ModelElement �� �߰��� �θ� ��
	 * @param req     ���ο� ModelElement ���� ��û
	 * @throws IllegalArgumentException ���� parameter �� null �̰ų� request��
	 * 							���ο� ModelElement instance �� �������� ������ �߻��Ѵ�.
	 */
	public ModelElementCreateCommand(ItemElement parentModel, CreateRequest req) {
		super(parentModel, req);

		setLabel("model creation");
	}
	
	/**
	 * �� command �� �����Ѵ�.
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	public void execute() {
		Point locPt = request.getLocation();

		// request �κ��� ���ο� ModelElement ���� ȹ���Ѵ�.
		// Constant ���� palette-tool���� �������� ������, �޴� �������� ���Ͽ�
		// factory �� ���� �����ȴ�.
		newModel = (ModelElement) request.getNewObject();
		int itemState = newModel.getItemState();
		if(itemState == 0) {
			if(!newModel.isIncluded()) {
				newModel.setName(getNewName("New_Model"));
				itemState |= ItemState.ITEM_CREATED;
				newModel.setItemState(itemState);
			}
		}

		redo();
	}
	
	/**
	 * ���ο� ��(ModelElement)�� �θ� �𵨿� �߰��Ѵ�.
	 * �� method �� Redo method ȣ�� �� ����ȴ�.
	 */
	protected void addToParent(ItemElement newItem) {
		
		// Model�� Parent�� ModelDiagram, IncludedElement, ModelElement���� ������.
		if(parentModel instanceof ModelDiagram) {
			modelAdded = ((ModelDiagram)parentModel).getItems().add(newItem);
			if(modelAdded)	newItem.setParent(parentModel);	// KJH 20100524
		}
		else if(parentModel instanceof IncludedElement) {
			modelAdded = ((IncludedElement)parentModel).getItems().add(newItem);
			if(modelAdded)	newItem.setParent(parentModel);	// KJH 20100524
		}
		else if(parentModel instanceof ModelElement) {
			modelAdded = ((ModelElement)parentModel).getModels().add((ModelElement)newItem);
			if(modelAdded)	newItem.setParent(parentModel);	// KJH 20100524
		}
	}
	
	/**
	 * ���ο� ��(ModelElement)�� �θ� �𵨷κ��� �����Ѵ�.
	 * �� method �� Undo method ȣ�� �� ����ȴ�.
	 */
	protected void removeFromParent(ItemElement newItem) {
		
		// Model�� Parent�� ModelDiagram, IncludedElement, ModelElement���� ������.
		if(parentModel instanceof ModelDiagram) {
			((ModelDiagram)parentModel).getItems().remove(newItem);
		}
		else if(parentModel instanceof IncludedElement) {
			((IncludedElement)parentModel).getItems().remove(newItem);
		}
		else if(parentModel instanceof ModelElement) {
			((ModelElement)parentModel).getModels().remove((ModelElement)newItem);
		}
	}
}
