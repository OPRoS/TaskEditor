package kr.re.etri.tpl.diagram.commands;

import kr.re.etri.tpl.diagram.util.ItemState;
import kr.re.etri.tpl.taskmodel.ConnectorElement;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.ReferElement;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.requests.CreateRequest;

public class ConnectorElementCreateCommand extends ShapeElementCreateCommand {

	public ConnectorElementCreateCommand(ItemElement parentModel,
			CreateRequest req, Rectangle constraint) {
		super(parentModel, req, constraint);
		setLabel("connector creation");
	}

	@Override
	public void execute() {
		if (request.getNewObject() instanceof ConnectorElement) {
			newShape = (ConnectorElement)request.getNewObject();
		}
		else if (request.getNewObject() instanceof ReferElement) {
			refItem = (ReferElement)request.getNewObject();
			newShape = refItem.getRealModel();
		}
		
		int itemState = newShape.getItemState();
		if (itemState == 0) {
			if(!newShape.isIncluded()) {
				if(newShape.getName() == null || newShape.getName().equals("")) {
					newShape.setName(getNewName("New_Connector"));
				}
				itemState |= ItemState.ITEM_CREATED;
				newShape.setItemState(itemState);
			}
		}

		Dimension size = request.getSize();
		if(size != null) {
			makeShapeRefer(size.width, size.height);
		}
		else {
			makeShapeRefer(120, 50);	// KJH 20101130 s, 크기변경
		}
		
		redo();
	}

}
