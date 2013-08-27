package kr.re.etri.tpl.diagram.editparts.task.policies;

import kr.re.etri.tpl.diagram.commands.BehaviorElementFoldingCommand;
import kr.re.etri.tpl.diagram.commands.ChangeFoldingRequest;
import kr.re.etri.tpl.diagram.commands.ShapeModelSetConstraintCommand;
import kr.re.etri.tpl.diagram.commands.StateElementCreateCommand;
import kr.re.etri.tpl.diagram.editparts.task.TBehaviorElementEditPart;
import kr.re.etri.tpl.diagram.editparts.task.TStateElementEditPart;
import kr.re.etri.tpl.taskmodel.ReferElement;
import kr.re.etri.tpl.taskmodel.ShapeElement;
import kr.re.etri.tpl.taskmodel.StateAttribute;
import kr.re.etri.tpl.taskmodel.StateElement;
import kr.re.etri.tpl.taskmodel.BehaviorElement;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.XYLayoutEditPolicy;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.requests.CreateRequest;

/**
 * 이 클래스는 TaskElement 의 child 모델들을 constraint 를 이용하여
 * 배치하는 EditPolicy 클래스이다.
 * 
 * @author sfline
 *
 */
public class TBehaviorElementXYLayoutEditPolicy extends XYLayoutEditPolicy {
	/** TaskElement 의 EditPart */
	private TBehaviorElementEditPart editPart;
	
	/**
	 * TaskElement 에 대한 EditPart 를 생성한다.
	 * @param editPart
	 */
	public TBehaviorElementXYLayoutEditPolicy(TBehaviorElementEditPart editPart) {
		this.editPart = editPart;
	}

	/**
	 * Request 에 대한 Command 를 반환한다.
	 */
	public Command getCommand(Request request) {
		if(request instanceof ChangeFoldingRequest) {
			// return a command that can move and/or resize a ShapeElement
			Dimension size = editPart.getFigure().getMinimumSize();
			if(RequestConstants.REQ_RESIZE.equals(request.getType())) {
				ReferElement refItem = editPart.getCastedModel();
				if(refItem.isCollapsed() == ((ChangeFoldingRequest)request).isCollapsed()) {
					return null;
				}
			}
//			if(((ItemElement) child.getModel()).isVisiable() == false) {
//				return null;
//			}

			return new BehaviorElementFoldingCommand(editPart.getCastedModel(), (ChangeFoldingRequest)request);
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
		if (child instanceof TStateElementEditPart && constraint instanceof Rectangle) {
			
			IFigure contentFigure = editPart.getContentPane();
			Rectangle clientRect = contentFigure.getClientArea();
			clientRect = new Rectangle(0, 0, clientRect.width, clientRect.height);
			if(clientRect.contains((Rectangle)constraint) == false) {
				return null;
			}

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

		if(childClass instanceof StateElement) {
			ReferElement refItem = editPart.getCastedModel();
			BehaviorElement task = editPart.getRealModel();
			
			if(task.isIncluded()) {
				return null;
			}
			
			if(refItem.isCollapsed()) {
				return null;
			}

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
			Rectangle bounds = (Rectangle)getConstraintFor(request);
//			Rectangle r = figure.getBounds();
			if(clientRect.contains(loc)) {
				// return a command that can add a ShapeElement to a ModelDiagram 
				return new StateElementCreateCommand(editPart.getCastedModel(), request ,bounds);
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
		// not used in this example
		return null;
	}

	protected Command getOrphanChildrenCommand(Request request) {
		return null;
	}
}
