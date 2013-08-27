package kr.re.etri.tpl.diagram.commands;

import kr.re.etri.tpl.diagram.util.ItemState;
import kr.re.etri.tpl.taskmodel.ActionElement;
import kr.re.etri.tpl.taskmodel.ItemElement;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.requests.CreateRequest;

/**
 * �𵨿� ActionElement�� �����Ͽ� �߰��Ѵ�. ������ ActionElement��
 * Diagram�� �߰��ȴ�.
 * 
 * �� ����� Undo/Redo �� �����Ѵ�.
 * @author sfline
 */
public class ActionElementCreateCommand extends ShapeElementCreateCommand {

	/**
	 * ModelDiagram �� ���ο� ActionElement �� �����ϴ� command �� �����Ѵ�.
	 * @param parent ���ο� ActionElement �� �߰��� �θ� ��
	 * @param req     ���ο� ActionElement ���� ��û
	 * @throws IllegalArgumentException ���� parameter �� null �̰ų� request ��
	 * 							���ο� ActionElement instance �� �������� ������ �߻��Ѵ�.
	 */
	public ActionElementCreateCommand(ItemElement parentModel, CreateRequest req, Rectangle constraint) {
		super(parentModel, req, constraint);

		// Undo/Redo�� ���� Context �޴��� Text�� �����Ѵ�.
		setLabel("action creation");
	}
	
	/**
	 * �� Command �� �����Ѵ�.
	 */
	public void execute() {

		// request �κ��� ���� ������ ActionElement �� ȹ���Ѵ�.
		// ���ο� Instance �� pallette �� �޴� �������� ���Ͽ� TaskModelFactory ��
		// �����ȴ�.
		newShape = (ActionElement) request.getNewObject();
		int itemState = newShape.getItemState();
		if(itemState == 0) {
			if(!newShape.isIncluded()) {
				newShape.setName("New_Action");
				itemState |= ItemState.ITEM_CREATED;
				newShape.setItemState(itemState);
			}
		}

		// size data�� �ִٸ� �ش� data�� �̿� GEF ��带 �����Ѵ�.
		// �׷��� ������ ����Ʈ ũ��� �����Ѵ�.
		Dimension size = request.getSize();
		if(size != null) {
			makeShapeRefer(size.width, size.height);
		}
		else {
			makeShapeRefer(80, 40);
		}

		// ActionElement�� ModelDiagram�� �߰��Ѵ�.
		redo();
	}
}
