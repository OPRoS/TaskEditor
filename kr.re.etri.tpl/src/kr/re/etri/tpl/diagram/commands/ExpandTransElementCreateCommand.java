package kr.re.etri.tpl.diagram.commands;

import kr.re.etri.tpl.diagram.util.ItemState;
import kr.re.etri.tpl.taskmodel.ExpandTransElement;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.ReferElement;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.requests.CreateRequest;

public class ExpandTransElementCreateCommand extends ShapeElementCreateCommand {
	private ItemElement attachModel;	// KJH 20110517

	public ExpandTransElementCreateCommand(ItemElement attachModel,
			CreateRequest req, Rectangle constraint) {
		super(attachModel, req, constraint);
		this.attachModel = attachModel;
		setLabel("expand&trans creation");
	}

	@Override
	public void execute() {
		if (request.getNewObject() instanceof ExpandTransElement) {
			newShape = (ItemElement)request.getNewObject();
		}
		else if (request.getNewObject() instanceof ReferElement) {
			refItem = (ReferElement)request.getNewObject();
			newShape = refItem.getRealModel();
		}
		
		int itemState = newShape.getItemState();
		if (itemState == 0) {
			if(!newShape.isIncluded()) {
				// KJH 20110530 s, name: state의 sourceConnections + state의 expand&trans 개수
//				int order = 1;
//				
//				ReferElement refParent = null;
//				ItemElement realParent = null;
//				
//				if (parentModel instanceof ReferElement) {
//					refParent = (ReferElement)parentModel;
//					realParent = refParent.getRealModel();
//				} else {
//					realParent = parentModel;
//				}
//				
//				if (refParent != null) {
//					order += refParent.getSourceConnections().size();
//				} else {
//					for (ReferElement ref : realParent.getReferences()) {
//						order += ref.getSourceConnections().size();
//					}
//				}
//				
//				if (realParent instanceof StateElement) {
//					order += ((StateElement)realParent).getBifurcates().size();
//				}
				// KJH 20110530 e, name: state의 sourceConnections + state의 expand&trans 개수

//				newShape.setName(Integer.toString(order));
				newShape.setName("expand&moveto");
				itemState |= ItemState.ITEM_CREATED;
				newShape.setItemState(itemState);
			}
		}
		
		makeShapeRefer(15, 15);	// KJH 20101130 s, 크기변경
		
		redo();
	}

	@Override
	public void redo() {
		super.redo();
		
//		ReferElement refAttach = null;
//		ItemElement realAttach = null;
//		if (attachModel instanceof ReferElement) {
//			refAttach = (ReferElement)attachModel;
//			realAttach = refAttach.getRealModel();
//		} else {
//			realAttach = attachModel;
//			refAttach = realAttach.getReferences().get(0);
//		}
//		
//		if (realAttach instanceof StateElement) {
//			StateElement state = (StateElement)realAttach;
//			state.getBifurcates().add((ExpandTransElement)newShape);
//			((ExpandTransElement)newShape).setSource(refAttach);
//		}
	}

	@Override
	public void undo() {
//		ReferElement refAttach = null;
//		ItemElement realAttach = null;
//		if (attachModel instanceof ReferElement) {
//			refAttach = (ReferElement)attachModel;
//			realAttach = refAttach.getRealModel();
//		} else {
//			realAttach = attachModel;
//			refAttach = realAttach.getReferences().get(0);
//		}
//		
//		if (realAttach instanceof StateElement) {
//			StateElement state = (StateElement)realAttach;
//			state.getBifurcates().remove((ExpandTransElement)newShape);
//			((ExpandTransElement)newShape).setSource(null);
//		}
		
		super.undo();
	}

}
