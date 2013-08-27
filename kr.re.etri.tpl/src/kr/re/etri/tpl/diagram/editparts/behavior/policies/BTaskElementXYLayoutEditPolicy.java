package kr.re.etri.tpl.diagram.editparts.behavior.policies;

import java.util.List;

import kr.re.etri.tpl.diagram.commands.ChangeFoldingRequest;
import kr.re.etri.tpl.diagram.commands.ItemElementFoldingCommand;
import kr.re.etri.tpl.diagram.commands.ShapeModelSetConstraintCommand;
import kr.re.etri.tpl.diagram.commands.WithElementCreateCommand;
import kr.re.etri.tpl.diagram.editparts.behavior.BTaskElementEditPart;
import kr.re.etri.tpl.diagram.editparts.behavior.BWithElementEditPart;
import kr.re.etri.tpl.taskmodel.ReferElement;
import kr.re.etri.tpl.taskmodel.ShapeElement;
import kr.re.etri.tpl.taskmodel.WithElement;

import org.apache.log4j.Logger;
import org.eclipse.draw2d.IFigure;
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

public class BTaskElementXYLayoutEditPolicy extends XYLayoutEditPolicy {
	private static Logger logger = Logger.getLogger(BTaskElementXYLayoutEditPolicy.class);
	
	/**
	 * Request 에 대한 Command 를 반환한다.
	 * 
	 * @Override
	 */
	public Command getCommand(Request request) {
		if (request instanceof GroupRequest) {
			List list = ((GroupRequest)request).getEditParts();
			logger.debug("Size : "+list.size());
			for(Object o : list){
				logger.debug(o);
			}
		}
		if (request instanceof ChangeFoldingRequest) {
			ChangeFoldingRequest req = (ChangeFoldingRequest)request;
			ReferElement refItem = (ReferElement)getHost().getModel();
			
			if(RequestConstants.REQ_RESIZE.equals(request.getType())) {
				if(refItem.isCollapsed() == req.isCollapsed()) {
					return null;
				}
			}
			
			return new ItemElementFoldingCommand(refItem, req);
		}
		return super.getCommand(request);
	}
	
	@Override
	protected Command createChangeConstraintCommand(EditPart child,
			Object constraint) {
		return null;
	}

	/**
	 * request 타입에 child 에 constraint 를 적용하는 Command 를 생성한다.
	 * @see org.eclipse.gef.editpolicies.ConstrainedLayoutEditPolicy#createChangeConstraintCommand(org.eclipse.gef.requests.ChangeBoundsRequest, org.eclipse.gef.EditPart, java.lang.Object)
	 * 
	 * @Override
	 */
	@Override
	protected Command createChangeConstraintCommand(
			ChangeBoundsRequest request, EditPart child, Object constraint) {
		logger.debug(request);
		
		if (child instanceof BWithElementEditPart && constraint instanceof Rectangle) {
			IFigure contentFigure = getGraphicalEditPart().getContentPane();
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

	@Override
	protected Command getCreateCommand(CreateRequest request) {
		logger.debug(request);
		Object childClass = request.getNewObject();
		GraphicalEditPart editPart = getGraphicalEditPart();
		if (editPart instanceof BTaskElementEditPart == false) {
			return null;
		}
		
		ReferElement refParent = ((BTaskElementEditPart)editPart).getCastedModel();
		if (refParent.getItems().size() > 0) {
			return null;
		}
		
		if (childClass instanceof WithElement) {
			logger.debug(editPart);
			ReferElement refItem = (ReferElement)getHost().getModel();
			
			if (refItem.isIncluded()) {
				return null;
			}
			if (refItem.isCollapsed()) {
				return null;
			}
			
			IFigure contentFigure = editPart.getContentPane();
			Rectangle clientRect = contentFigure.getClientArea().getCopy();
			contentFigure.translateToAbsolute(clientRect);
			
			Point loc = new Point(request.getLocation());
			if(clientRect.contains(loc)) {
				IFigure figure = editPart.getFigure();
				Rectangle r = figure.getBounds();
				Rectangle bounds = (Rectangle)getConstraintFor(request);
				bounds.x += r.x;
				bounds.y += r.y;
				return new WithElementCreateCommand(refItem, request, bounds);
			}
		}
		return null;
	}

	/**
	 * @Override
	 * @author KJH 20110420
	 */
	protected EditPolicy createChildEditPolicy(EditPart child) {
		return new BResizableEditPolicy();
	}
	
	protected GraphicalEditPart getGraphicalEditPart() {
		return (GraphicalEditPart)getHost();
	}
}
