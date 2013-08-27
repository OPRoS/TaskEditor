package kr.re.etri.tpl.diagram.commands;

import java.util.List;

import kr.re.etri.tpl.diagram.BehaviorDiagramEditPartFactory;
import kr.re.etri.tpl.diagram.ModelManager;
import kr.re.etri.tpl.diagram.editparts.behavior.BBehaviorElementEditPart;
import kr.re.etri.tpl.diagram.editparts.behavior.BExpandTransElementEditPart;
import kr.re.etri.tpl.diagram.editparts.behavior.BStateElementEditPart;
import kr.re.etri.tpl.taskmodel.BehaviorElement;
import kr.re.etri.tpl.taskmodel.ExpandTransElement;
import kr.re.etri.tpl.taskmodel.ModelDiagram;
import kr.re.etri.tpl.taskmodel.ReferElement;
import kr.re.etri.tpl.taskmodel.ShapeElement;

import org.apache.log4j.Logger;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.ChangeBoundsRequest;

/**
 * Graphical Node �� ���� Constraint �� �����ϴ� command Ŭ�����̴�.
 * �� command �� Graphical Node �� resize, move �� �����Ѵ�.
 * �� command �� Undo/Redo �� �����ϴ�.
 * @author sfline
 */
public class ShapeModelSetConstraintCommand extends Command {
	private static Logger logger = Logger.getLogger(ShapeModelSetConstraintCommand.class);
	/** Node �� ���ο� ��ġ�� ũ�� */
	private final Rectangle newBounds;
	/** Node �� ���� ��ġ�� ũ�� */
	private Rectangle oldBounds;
	/** ��ġ�� ũ�� ���� ��û Request */
	private final ChangeBoundsRequest request;
	/** ��ġ�� ũ�Ⱑ ����� ��� Node */
	private final ShapeElement shape;
	/** �ּ� ũ�� */
	private final Dimension minSize;	// KJH 20110420, add

	/**
	 * Graphical Node�� ũ��, ��ġ�� ������ command �� �����Ѵ�. 
	 * @param shape	ũ��, ��ġ�� ����� Node
	 * @param req		���� ��û Request
	 * @param newBounds ���ο� ��ġ�� ũ��
	 * @throws IllegalArgumentException Parameter�� null �̸� �߻��Ѵ�.
	 */
	public ShapeModelSetConstraintCommand(ShapeElement shape, ChangeBoundsRequest req, 
			Rectangle newBounds) {
		if (shape == null || req == null || newBounds == null) {
			throw new IllegalArgumentException();
		}
		this.shape = shape;
		this.request = req;
		this.newBounds = newBounds.getCopy();
		this.minSize = null;	// KJH 20110420, add
		setLabel(String.format("%s move/resize", shape.getName()));
	}
	
	/**
	 * Graphical Node�� ũ��, ��ġ�� ������ command �� �����Ѵ�. 
	 * @param shape	ũ��, ��ġ�� ����� Node
	 * @param req		���� ��û Request
	 * @param newBounds ���ο� ��ġ�� ũ��
	 * @param minSize	�ּ� ũ��
	 * @throws IllegalArgumentException Parameter�� null �̸� �߻��Ѵ�.
	 * @author KJH 20110420
	 */
	public ShapeModelSetConstraintCommand(ShapeElement shape, ChangeBoundsRequest req, 
			Rectangle newBounds, Dimension minSize) {
		if (shape == null || req == null || newBounds == null) {
			throw new IllegalArgumentException();
		}
		this.shape = shape;
		this.request = req;
		this.newBounds = newBounds.getCopy();
		this.minSize = minSize.getCopy();	// KJH 20110420, add
		setLabel(String.format("%s move/resize", shape.getName()));
	}
	
	/**
	 * �� command �� ���� ���� ���θ� ��ȯ�Ѵ�.
	 * @see org.eclipse.gef.commands.Command#canExecute()
	 */
	public boolean canExecute() {
		logger.debug("canExecute");
		Object type = request.getType();
		List list = request.getEditParts();
		if(list.size() ==1){
			Object o =list.get(0);
			logger.debug(o);
			logger.debug(type);
			if(o instanceof BStateElementEditPart){
				return (RequestConstants.REQ_MOVE.equals(type)
						|| RequestConstants.REQ_MOVE_CHILDREN.equals(type));
			}
			if(o instanceof BBehaviorElementEditPart){
				BBehaviorElementEditPart bEditPart = (BBehaviorElementEditPart)o;
				ReferElement refItem = (ReferElement)bEditPart.getModel();
				if(refItem.isCollapsed()){
					return (RequestConstants.REQ_MOVE.equals(type)
							|| RequestConstants.REQ_MOVE_CHILDREN.equals(type)
							|| RequestConstants.REQ_RESIZE_CHILDREN.equals(type));
				}
			}
			// KJH 20110520 s, expand&trans
			if (o instanceof BExpandTransElementEditPart) {
				return (RequestConstants.REQ_MOVE.equals(type)
						|| RequestConstants.REQ_MOVE_CHILDREN.equals(type));
			}
			
		}		
		logger.debug(type);
		// make sure the Request is of a type we support:
		return (RequestConstants.REQ_MOVE.equals(type)
				|| RequestConstants.REQ_MOVE_CHILDREN.equals(type) 
				|| RequestConstants.REQ_RESIZE.equals(type)
				|| RequestConstants.REQ_RESIZE_CHILDREN.equals(type));
	}
	
	/**
	 * �� comman �� �����Ѵ�.
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	public void execute() {
		oldBounds = new Rectangle(
				shape.getX(), 
				shape.getY(), 
				shape.getWidth(), 
				shape.getHeight());
		
		redo();
	}
	
	/**
	 * �� command �� Redo �Ѵ�.
	 * @see org.eclipse.gef.commands.Command#redo()
	 */
	public void redo() {
		// KJH 20110420 s, add
		if (minSize != null) {
			if (newBounds.width < minSize.width) {
				newBounds.width = minSize.width;
				if (newBounds.x + newBounds.width > oldBounds.x + oldBounds.width) {
					newBounds.x = oldBounds.x + oldBounds.width - newBounds.width;
				}
			}
			if (newBounds.height < minSize.height) {
				newBounds.height = minSize.height;
				if (newBounds.y + newBounds.height > oldBounds.y + oldBounds.height) {
					newBounds.y = oldBounds.y + oldBounds.height - newBounds.height;
				}
			}
		}// KJH 20110420 e, add
		
		shape.setX(newBounds.x);
		shape.setY(newBounds.y);
		shape.setHeight(newBounds.height);
		shape.setWidth(newBounds.width);
	}
	
	/**
	 * �� command �� Undo �Ѵ�.
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	public void undo() {
		shape.setX(oldBounds.x);
		shape.setY(oldBounds.y);
		shape.setHeight(oldBounds.height);
		shape.setWidth(oldBounds.width);
	}
}
