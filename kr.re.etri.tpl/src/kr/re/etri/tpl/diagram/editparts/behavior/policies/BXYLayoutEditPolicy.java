package kr.re.etri.tpl.diagram.editparts.behavior.policies;

import kr.re.etri.tpl.diagram.commands.ActionElementCreateCommand;
import kr.re.etri.tpl.diagram.commands.BehaviorElementCreateCommand;
import kr.re.etri.tpl.diagram.commands.ChangeFoldingRequest;
import kr.re.etri.tpl.diagram.commands.ConnectorElementCreateCommand;
import kr.re.etri.tpl.diagram.commands.ReferElementCreateCommand;
import kr.re.etri.tpl.diagram.commands.ShapeModelSetConstraintCommand;
import kr.re.etri.tpl.diagram.commands.TaskElementCreateCommand;
import kr.re.etri.tpl.diagram.editparts.behavior.BActionElementEditPart;
import kr.re.etri.tpl.diagram.editparts.behavior.BBehaviorDiagramEditPart;
import kr.re.etri.tpl.diagram.editparts.behavior.BBehaviorElementEditPart;
import kr.re.etri.tpl.diagram.editparts.behavior.BConnectorElementEditPart;
import kr.re.etri.tpl.diagram.editparts.behavior.BTaskElementEditPart;
import kr.re.etri.tpl.taskmodel.ActionElement;
import kr.re.etri.tpl.taskmodel.BehaviorElement;
import kr.re.etri.tpl.taskmodel.ConnectorElement;
import kr.re.etri.tpl.taskmodel.ReferElement;
import kr.re.etri.tpl.taskmodel.ShapeElement;
import kr.re.etri.tpl.taskmodel.TaskElement;

import org.apache.log4j.Logger;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
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
public class BXYLayoutEditPolicy extends XYLayoutEditPolicy {
	private static Logger logger = Logger.getLogger(BXYLayoutEditPolicy.class);
	/** TaskDiagram 의 EditPart */
	private BBehaviorDiagramEditPart editPart;

	/**
	 * TaskElement 에 대한 EditPolicy 를 생성한다.
	 * @param editPart
	 */
	public BXYLayoutEditPolicy(BBehaviorDiagramEditPart editPart) {
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
		
		if (child instanceof BBehaviorElementEditPart && constraint instanceof Rectangle) {
			// return a command that can move and/or resize a ShapeElement
			Dimension size = ((BBehaviorElementEditPart)child).getFigure().getMinimumSize();
			// KJH 20110420 s, del
//			if(((Rectangle)constraint).width < size.width || ((Rectangle)constraint).height < size.height) {
//				return null;
//			}// KJH 20110420 e, del
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
			
			// KJH 20110420 s, add
			if (request instanceof ChangeFoldingRequest) {
				ChangeFoldingRequest req = (ChangeFoldingRequest)request;
				if (!req.isCollapsed()) {
				}
			} else {
				ReferElement refItem = ((BBehaviorElementEditPart)child).getCastedModel();
				if (refItem.isCollapsed()) {
				}
			}// KJH 20110420 e, add

			return new ShapeModelSetConstraintCommand(
					(ShapeElement) child.getModel(), request, (Rectangle) constraint, size);
		}
		if (child instanceof BActionElementEditPart && constraint instanceof Rectangle) {
			// return a command that can move and/or resize a ShapeElement
			Dimension size = ((BActionElementEditPart)child).getFigure().getMinimumSize();
			return new ShapeModelSetConstraintCommand(
					(ShapeElement) child.getModel(), request, (Rectangle) constraint, size);
		}
		// KJH 20101126 s, connector
		if (child instanceof BConnectorElementEditPart && constraint instanceof Rectangle) {
			Dimension size = ((BConnectorElementEditPart)child).getFigure().getMinimumSize();
			return new ShapeModelSetConstraintCommand(
					(ShapeElement)child.getModel(), request, (Rectangle)constraint, size);
		}	// KJH 20101126 e, connector
		// KJH 20110413 s, task
		if (child instanceof BTaskElementEditPart && constraint instanceof Rectangle) {
			Dimension size = ((BTaskElementEditPart)child).getFigure().getMinimumSize();
			return new ShapeModelSetConstraintCommand(
					(ShapeElement)child.getModel(), request, (Rectangle)constraint, size);
		} // KJH 20110413 e, task
		
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
		logger.debug("size"+request.getSize());
		Rectangle bounds = (Rectangle)getConstraintFor(request);

		logger.debug("bounds"+bounds);
		if(childClass instanceof BehaviorElement) {
			// return a command that can add a TaskElement to a ModelDiagram 
			logger.debug(editPart.getCastedModel());
			return new BehaviorElementCreateCommand(editPart.getCastedModel(), request, bounds);
		}
		// KJH 20100826,
		else if(childClass instanceof ReferElement) {
			return new ReferElementCreateCommand(editPart.getCastedModel(), request, bounds);
		}
//		else if(childClass instanceof StateElement) {
//			// return a command that can add a StateElement to a ModelDiagram or TaskElement
//			return new StateElementCreateCommand(editPart.getCastedModel(), request);
//		}
		else if(childClass instanceof ActionElement) {
			// return a command that can add a ActionElement to a ModelDiagram 
			return new ActionElementCreateCommand(editPart.getCastedModel(), request, bounds);
		}
		// KJH 20101126 s, connector
		else if (childClass instanceof ConnectorElement) {
			return new ConnectorElementCreateCommand(editPart.getCastedModel(), request, bounds);
		}	// KJH 20101126 e, connector
		// KJH 20110412 s, task
		else if (childClass instanceof TaskElement) {
			return new TaskElementCreateCommand(editPart.getCastedModel(), request, bounds);
		} // KJH 20110412 e, task
		
		return null;
	}

	/**
	 * @Override
	 * @author KJH 20110420
	 */
	protected EditPolicy createChildEditPolicy(EditPart child) {
		return new BResizableEditPolicy();
	}
}
