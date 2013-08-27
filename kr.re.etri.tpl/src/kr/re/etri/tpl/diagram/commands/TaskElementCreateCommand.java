package kr.re.etri.tpl.diagram.commands;

import kr.re.etri.tpl.diagram.util.ItemState;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.ReferElement;
import kr.re.etri.tpl.taskmodel.TaskElement;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.requests.CreateRequest;

/**
 * ModelDiagram �� WorkerElement �� �����ϴ� command �� �����Ѵ�.
 * �� command �� Undo/Redo �� �����ϴ�.
 * @author sfline
 */
public class TaskElementCreateCommand extends ShapeElementCreateCommand {
	
	/**
	 * ModelDiagram �� ���ο� WorkerElement �� �����ϴ� command �� �����Ѵ�.
	 * @param parent ���ο� ModelDiagram �� �߰��� �θ� ��
	 * @param req     ���ο� ModelDiagram ���� ��û
	 * @throws IllegalArgumentException ���� parameter �� null �̰ų� request ��
	 * 							���ο� ModelDiagram instance �� �������� ������ �߻��Ѵ�.
	 */
	public TaskElementCreateCommand(ItemElement parentModel, CreateRequest req, Rectangle constraint) {
		super(parentModel, req, constraint);

		setLabel("worker creation");
	}
	
	/**
	 * �� Command �� �����Ѵ�.
	 */
	public void execute() {
		Point locPt = request.getLocation();

		// Obtain the new ModelElement instance from the request.
		// This causes the factory stored in the request to create a new instance.
		// The factory is supplied in the palette-tool-entry, see
		// ShapesEditorPaletteFactory#createComponentsGroup()
		// KJH 20110413 s,
		if (request.getNewObject() instanceof TaskElement) {
			newShape = (TaskElement) request.getNewObject();
		}
		else if (request.getNewObject() instanceof ReferElement) {
			refItem = (ReferElement) request.getNewObject();
			newShape = (TaskElement) refItem.getRealModel();
		} // KJH 20110413 e,
		int itemState = newShape.getItemState();
		if(itemState == 0) {
			if(!newShape.isIncluded()) {
				// KJH 20110413 s,
				if (newShape.getName() == null || newShape.getName().equals("")) {
					newShape.setName(getNewName("New_Task"));
				} // KJH 20110413 e,
				itemState |= ItemState.ITEM_CREATED;
				newShape.setItemState(itemState);
			}
		}

		Dimension size = request.getSize();
		if(size != null) {
			makeShapeRefer(size.width, size.height);
		}
		else {
			makeShapeRefer(120, 50);
		}

		redo();
	}
}
