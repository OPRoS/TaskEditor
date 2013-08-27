package kr.re.etri.tpl.diagram.editparts.task.policies;

import kr.re.etri.tpl.diagram.commands.ActionElementCreateCommand;
import kr.re.etri.tpl.diagram.commands.BehaviorElementCreateCommand;
import kr.re.etri.tpl.diagram.commands.ShapeModelSetConstraintCommand;
import kr.re.etri.tpl.diagram.editparts.task.TActionElementEditPart;
import kr.re.etri.tpl.diagram.editparts.task.TBehaviorDiagramEditPart;
import kr.re.etri.tpl.diagram.editparts.task.TBehaviorElementEditPart;
import kr.re.etri.tpl.taskmodel.ActionElement;
import kr.re.etri.tpl.taskmodel.ShapeElement;
import kr.re.etri.tpl.taskmodel.BehaviorElement;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.XYLayoutEditPolicy;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.requests.CreateRequest;

/**
 * 이 클래스는 Container 에서 Shape instance 의 constraint 를 이용하여
 * 배치하는 EditPolicy 클래스이다.
 * 
 * @author sfline
 *
 */
public class TXYLayoutEditPolicy extends XYLayoutEditPolicy {
	/** TaskDiagram 의 EditPart */
	private TBehaviorDiagramEditPart editPart;

	/**
	 * TaskElement 에 대한 EditPolicy 를 생성한다.
	 * @param editPart
	 */
	public TXYLayoutEditPolicy(TBehaviorDiagramEditPart editPart) {
		this.editPart = editPart;
	}

	/**
	 * Constraint 를 갖는 child EditPart 를 추가하는 Command 를 생성한다. 
	 * @see org.eclipse.gef.editpolicies.ConstrainedLayoutEditPolicy#createAddCommand(org.eclipse.gef.EditPart, java.lang.Object)
	 * 
	 * @Override
	 */
	protected Command createAddCommand(EditPart child, Object constraint) {
		// not used in this example
		return null;
	}

	/**
	 * request 타입에 child 에 constraint 를 적용하는 Command 를 생성한다.
	 * @see org.eclipse.gef.editpolicies.ConstrainedLayoutEditPolicy#createChangeConstraintCommand(org.eclipse.gef.requests.ChangeBoundsRequest, org.eclipse.gef.EditPart, java.lang.Object)
	 * 
	 * @Override
	 */
	protected Command createChangeConstraintCommand(ChangeBoundsRequest request,
			EditPart child, Object constraint) {
		if (child instanceof TBehaviorElementEditPart && constraint instanceof Rectangle) {
			// return a command that can move and/or resize a ShapeElement
			Dimension size = ((TBehaviorElementEditPart)child).getFigure().getMinimumSize();
			if(((Rectangle)constraint).width < size.width || ((Rectangle)constraint).height < size.height) {
				return null;
			}
/*
			Rectangle bounds = ((TaskElementFigure)((TaskElementEditPart)child).getFigure()).getChildRectangle();
			if(bounds == null) {
				if(((Rectangle)constraint).width < size.width || ((Rectangle)constraint).height < size.height) {
					return null;
				}
			}
			else {
				if(RequestConstants.REQ_RESIZE_CHILDREN.equals(request.getType())) {
					if(request.getResizeDirection() == PositionConstants.EAST ||
						request.getResizeDirection() == PositionConstants.NORTH_EAST ||
						request.getResizeDirection() == PositionConstants.SOUTH_EAST) {
						if((((Rectangle)constraint).x+((Rectangle)constraint).width) <= (bounds.x + bounds.width)) {
							return null;
						}
					}
					if(request.getResizeDirection() == PositionConstants.WEST ||
							request.getResizeDirection() == PositionConstants.NORTH_WEST ||
							request.getResizeDirection() == PositionConstants.SOUTH_WEST) {
							if(((Rectangle)constraint).x >= bounds.x) {
								return null;
							}
						}
					if(request.getResizeDirection() == PositionConstants.SOUTH ||
							request.getResizeDirection() == PositionConstants.SOUTH_WEST ||
							request.getResizeDirection() == PositionConstants.SOUTH_EAST) {
							if((((Rectangle)constraint).y+((Rectangle)constraint).height) <= (bounds.y + bounds.height)) {
								return null;
							}
						}
					if(request.getResizeDirection() == PositionConstants.NORTH ||
							request.getResizeDirection() == PositionConstants.NORTH_WEST ||
							request.getResizeDirection() == PositionConstants.NORTH_EAST) {
							if(((Rectangle)constraint).y >= bounds.y) {
								return null;
							}
						}
				}
			}
*/
//			if(((ItemElement) child.getModel()).isVisiable() == false) {
//				return null;
//			}

			return new ShapeModelSetConstraintCommand(
					(ShapeElement) child.getModel(), request, (Rectangle) constraint);
		}
		if (child instanceof TActionElementEditPart && constraint instanceof Rectangle) {
			// return a command that can move and/or resize a ShapeElement
			return new ShapeModelSetConstraintCommand(
					(ShapeElement) child.getModel(), request, (Rectangle) constraint);
		}
		return super.createChangeConstraintCommand(request, child, constraint);
	}

	/**
	 * child 의 constraint 를 변경할 Command 를 생성한다.
	 * @see org.eclipse.gef.editpolicies.ConstrainedLayoutEditPolicy#createChangeConstraintCommand(org.eclipse.gef.EditPart, java.lang.Object)
	 * 
	 * @Override
	 */
	protected Command createChangeConstraintCommand(EditPart child,
			Object constraint) {
		// not used in this example
		return null;
	}

	/**
	 * request 에 따라 child 를 생성하는 command 를 생성한다.
	 * @see org.eclipse.gef.editpolicies.LayoutEditPolicy#getCreateCommand(org.eclipse.gef.requests.CreateRequest)
	 * 
	 * @Override
	 */
	protected Command getCreateCommand(CreateRequest request) {
		Object childClass = request.getNewObject();

		if(editPart.isChild(childClass) != false) {
			return null;
		}
		Rectangle bounds = (Rectangle)getConstraintFor(request);
		if(childClass instanceof BehaviorElement) {
			// return a command that can add a TaskElement to a ModelDiagram 
			return new BehaviorElementCreateCommand(editPart.getCastedModel(), request, bounds);
		}
//		else if(childClass instanceof StateElement) {
//			// return a command that can add a StateElement to a ModelDiagram or TaskElement
//			return new StateElementCreateCommand(editPart.getCastedModel(), request);
//		}
		else if(childClass instanceof ActionElement) {
			// return a command that can add a ActionElement to a ModelDiagram 
			return new ActionElementCreateCommand(editPart.getCastedModel(), request, bounds);
		}

		return null;
	}
}
