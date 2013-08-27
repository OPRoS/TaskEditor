package kr.re.etri.tpl.diagram.editparts.behavior.policies;

import java.util.List;

import kr.re.etri.tpl.diagram.commands.ChangeFoldingRequest;
import kr.re.etri.tpl.diagram.commands.ExpandTransElementCreateCommand;
import kr.re.etri.tpl.diagram.commands.ItemElementFoldingCommand;
import kr.re.etri.tpl.diagram.commands.MoveChildCommand;
import kr.re.etri.tpl.diagram.commands.ShapeModelSetConstraintCommand;
import kr.re.etri.tpl.diagram.commands.StateElementCreateCommand;
import kr.re.etri.tpl.diagram.editparts.behavior.BBehaviorElementEditPart;
import kr.re.etri.tpl.diagram.editparts.behavior.BExpandTransElementEditPart;
import kr.re.etri.tpl.diagram.editparts.behavior.BLinkedElementEditPart;
import kr.re.etri.tpl.diagram.editparts.behavior.BStateElementEditPart;
import kr.re.etri.tpl.taskmodel.BehaviorElement;
import kr.re.etri.tpl.taskmodel.ExpandTransElement;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.ReferAttribute;
import kr.re.etri.tpl.taskmodel.ReferElement;
import kr.re.etri.tpl.taskmodel.ShapeElement;
import kr.re.etri.tpl.taskmodel.StateAttribute;
import kr.re.etri.tpl.taskmodel.StateElement;

import org.apache.log4j.Logger;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.XYLayoutEditPolicy;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gef.requests.GroupRequest;

/**
 * 이 클래스는 TaskElement 의 child 모델들을 constraint 를 이용하여
 * 배치하는 EditPolicy 클래스이다.
 * 
 * @author sfline
 *
 */
public class BBehaviorElementXYLayoutEditPolicy extends XYLayoutEditPolicy {
	private static Logger logger = Logger.getLogger(BBehaviorElementXYLayoutEditPolicy.class);
	/** TaskElement 의 EditPart */
	private BLinkedElementEditPart editPart;
	
	/**
	 * TaskElement 에 대한 EditPolicy 를 생성한다.
	 * @param editPart
	 */
	public BBehaviorElementXYLayoutEditPolicy(BLinkedElementEditPart editPart) {
		this.editPart = editPart;
	}

	/**
	 * Request 에 대한 Command 를 반환한다.
	 */
	public Command getCommand(Request request) {
		if(request instanceof GroupRequest){
			List list = ((GroupRequest)request).getEditParts();
			logger.debug("Size : "+list.size());
			for(Object o : list){
				logger.debug(o);
			}
		}
		if(request instanceof ChangeFoldingRequest) {
			// return a command that can move and/or resize a ShapeElement
			Dimension size = editPart.getFigure().getMinimumSize();
			if(RequestConstants.REQ_RESIZE.equals(request.getType())) {
				ReferElement refItem = (ReferElement)editPart.getCastedModel();
				if(refItem.isCollapsed() == ((ChangeFoldingRequest)request).isCollapsed()) {
					return null;
				}
			}
//			if(((ItemElement) child.getModel()).isVisiable() == false) {
//				return null;
//			}

			return new ItemElementFoldingCommand((ReferElement)editPart.getCastedModel(), (ChangeFoldingRequest)request);
		}

		return super.getCommand(request);
	}

	/**
	 * Constraint 를 갖는 child EditPart 를 추가하는 Command 를 생성한다. 
	 * @see org.eclipse.gef.editpolicies.ConstrainedLayoutEditPolicy#createAddCommand(org.eclipse.gef.EditPart, java.lang.Object)
	 * 
	 * @Override
	 */
	protected Command createAddCommand(EditPart child, Object constraint) {
		if (editPart instanceof BStateElementEditPart &&
				child instanceof BExpandTransElementEditPart) {
			ReferElement refParent = (ReferElement)editPart.getCastedModel();
			ReferElement refChild = (ReferElement)child.getModel();
			ExpandTransElement realChild = (ExpandTransElement)refChild.getRealModel();
			
			if (realChild.getExpand() == refParent.getParent()) {
				return null;
			}
			
			IFigure contentFigure = editPart.getContentPane();
			Rectangle clientRect = contentFigure.getBounds();
			Rectangle childRect = (Rectangle)constraint; 
			int margin = 8;
			
			Rectangle expandRect = Rectangle.SINGLETON.setBounds(clientRect);
			expandRect.x = 0;
			expandRect.y = 0;
			expandRect.expand(new Insets(margin));
			
			int dt = childRect.y - expandRect.y;
			int dl = childRect.x - expandRect.x;
			int dr = expandRect.right() - childRect.right();
			int db = expandRect.bottom() - childRect.bottom();
			boolean isTranslated = false;
			
			if (dt < 0) {
				childRect.y = expandRect.y;
				isTranslated = true;
			}
			if (dl < 0) {
				childRect.x = expandRect.x;
				isTranslated = true;
			}
			if (dr < 0) {
				childRect.x = expandRect.right() - childRect.width;
				isTranslated = true;
			}
			if (db < 0) {
				childRect.y = expandRect.bottom() - childRect.height;
				isTranslated = true;
			}
			
			if (!isTranslated) {
				if (dt < dl && dt < dr && dt < db) {
					childRect.y = expandRect.y;
				}
				else if (dl < dr && dl < db) {
					childRect.x = expandRect.x;
				}
				else if (dr < db) {
					childRect.x = expandRect.right() - childRect.width;
				}
				else {
					childRect.y = expandRect.bottom() - childRect.height;
				}
			}

			childRect.translate(refParent.getX(), refParent.getY());
			
			return new MoveChildCommand(refParent, refChild, childRect);
		}
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
		logger.debug(request);
		
		// KJH 20100827 s, external behavior doesn't move child
		ReferElement refItem = (ReferElement)editPart.getCastedModel();
		if (refItem.getAttribute() == ReferAttribute.EXTERNAL) {
			return null;
		}	// KJH 20100827 e, external behavior doesn't move child
			
		if (child instanceof BStateElementEditPart && constraint instanceof Rectangle) {
			
			IFigure contentFigure = editPart.getContentPane();
			Rectangle clientRect = contentFigure.getClientArea();
			clientRect = new Rectangle(0, 0, clientRect.width, clientRect.height);
			if(clientRect.contains((Rectangle)constraint) == false) {
				return null;
			}
			
			// KJH 20110518 s, expand&trans
			Command command = null;
			
			List children = child.getChildren();
			for (int i=0; i<children.size(); i++) {
				if (children.get(i) instanceof BExpandTransElementEditPart) {
					BExpandTransElementEditPart editPart = (BExpandTransElementEditPart)children.get(i);
					Rectangle newBounds = new Rectangle(getCurrentConstraintFor(editPart));
					newBounds.translate(request.getMoveDelta());
					Command cmd = new ShapeModelSetConstraintCommand(editPart.getCastedModel(), request, newBounds);
					command = (command != null) ? command.chain(cmd) : cmd;
				}
			}
			
			if (command != null) {
				return command.chain(new ShapeModelSetConstraintCommand(
						(ShapeElement) child.getModel(), request, (Rectangle) constraint));
			}
			
			// KJH 20110518 e, expand&trans
			// return a command that can move and/or resize a ShapeElement
			return new ShapeModelSetConstraintCommand(
					(ShapeElement) child.getModel(), request, (Rectangle) constraint);
		}
		// KJH 20110518 s, expand&trans
		else if (child instanceof BExpandTransElementEditPart && constraint instanceof Rectangle) {
			IFigure contentFigure = editPart.getContentPane();
			Rectangle clientRect = contentFigure.getBounds();
			Rectangle childRect = (Rectangle)constraint; 
			int margin = 8;
			
			Rectangle expandRect = Rectangle.SINGLETON.setBounds(clientRect);
			expandRect.x = 0;
			expandRect.y = 0;
			expandRect.expand(new Insets(margin));
			
			int dt = childRect.y - expandRect.y;
			int dl = childRect.x - expandRect.x;
			int dr = expandRect.right() - childRect.right();
			int db = expandRect.bottom() - childRect.bottom();
			boolean isTranslated = false;
			
			if (dt < 0) {
				childRect.y = expandRect.y;
				isTranslated = true;
			}
			if (dl < 0) {
				childRect.x = expandRect.x;
				isTranslated = true;
			}
			if (dr < 0) {
				childRect.x = expandRect.right() - childRect.width;
				isTranslated = true;
			}
			if (db < 0) {
				childRect.y = expandRect.bottom() - childRect.height;
				isTranslated = true;
			}
			
			if (!isTranslated) {
				if (dt < dl && dt < dr && dt < db) {
					childRect.y = expandRect.y;
				}
				else if (dl < dr && dl < db) {
					childRect.x = expandRect.x;
				}
				else if (dr < db) {
					childRect.x = expandRect.right() - childRect.width;
				}
				else {
					childRect.y = expandRect.bottom() - childRect.height;
				}
			}

			ReferElement ref = (ReferElement)editPart.getCastedModel();
			childRect.translate(ref.getX(), ref.getY());

			// return a command that can move and/or resize a ShapeElement
			return new ShapeModelSetConstraintCommand(
					(ShapeElement) child.getModel(), request, childRect);
		}// KJH 20110518 e, expand&trans
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
		logger.debug(request);
		Object childClass = request.getNewObject();
		
		ReferElement refItem = (ReferElement)editPart.getCastedModel();
		ItemElement realItem = refItem.getRealModel();
		
		if (ReferAttribute.EXTERNAL == refItem.getAttribute() ||
				/*refItem.isCollapsed() ||*/
				realItem.isIncluded()) {
			return null;
		}

		if((editPart instanceof BBehaviorElementEditPart)
				&& (childClass instanceof StateElement)) {
			logger.debug(editPart);
			
			if (refItem.isCollapsed()) {
				return null;
			}
			
			BehaviorElement task = ((BBehaviorElementEditPart)editPart).getRealModel();

			if(StateAttribute.INITIAL == ((StateElement)childClass).getAttribute()) {
				if(task.getInitialState() != null) {
					return null;
				}
			}
//			IFigure figure = editPart.getFigure();
			IFigure contentFigure = editPart.getContentPane();
			Rectangle clientRect = contentFigure.getClientArea().getCopy();
			contentFigure.translateToAbsolute(clientRect);

			Point loc = new Point(request.getLocation());
//			Rectangle r = figure.getBounds();
			if(clientRect.contains(loc)) {
				IFigure figure = editPart.getFigure();
				Rectangle r = figure.getBounds();
				Rectangle bounds = (Rectangle)getConstraintFor(request);
				bounds.x += r.x;
				bounds.y += r.y;
				// return a command that can add a ShapeElement to a ModelDiagram 
				return new StateElementCreateCommand(editPart.getCastedModel(), request, bounds);
			}
		}
		else if ((editPart instanceof BStateElementEditPart)
				&& (childClass instanceof ExpandTransElement)) {
			logger.debug(editPart);
			StateElement state = ((BStateElementEditPart)editPart).getRealModel();
			
			if (refItem.getAttribute() == ReferAttribute.EXTERNAL) {
				return null;
			}

			if(state.isIncluded()) {
				return null;
			}
			
			if(refItem.isCollapsed()) {
				return null;
			}
			
			IFigure contentFigure = editPart.getContentPane();
			Rectangle clientRect = contentFigure.getClientArea().getCopy();
			contentFigure.translateToAbsolute(clientRect);

			Point loc = new Point(request.getLocation());
			if(clientRect.contains(loc)) {
				IFigure figure = ((GraphicalEditPart)editPart.getParent()).getFigure();
				Rectangle r = figure.getBounds();
				Rectangle bounds = (Rectangle)getConstraintFor(getCurrentConstraintFor(editPart));
//				bounds.x += r.x;
//				bounds.y += r.y;
				bounds.x *= 2;
				bounds.y *= 2;
				bounds.translate(-8, -8);
				// return a command that can add a ShapeElement to a ModelDiagram 
				return new ExpandTransElementCreateCommand(editPart.getCastedModel(), request, bounds);
			}
		}
		return null;
	}
	
	/**
	 * request 에 따라 의존성을 삭제하는 Command 를 생성한다.
	 * @see org.eclipse.gef.editpolicies.LayoutEditPolicy#getDeleteDependantCommand(org.eclipse.gef.Request)
	 * @Override
	 */
	protected Command getDeleteDependantCommand(Request request) {
		return null;
	}

	protected Command getOrphanChildrenCommand(Request request) {
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
