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
 * Graphical Node 에 대한 Constraint 를 설정하는 command 클래스이다.
 * 이 command 는 Graphical Node 의 resize, move 를 수행한다.
 * 이 command 는 Undo/Redo 가 가능하다.
 * @author sfline
 */
public class ShapeModelSetConstraintCommand extends Command {
	private static Logger logger = Logger.getLogger(ShapeModelSetConstraintCommand.class);
	/** Node 의 새로운 위치와 크기 */
	private final Rectangle newBounds;
	/** Node 의 이전 위치와 크기 */
	private Rectangle oldBounds;
	/** 위치와 크기 변경 요청 Request */
	private final ChangeBoundsRequest request;
	/** 위치와 크기가 변경될 대상 Node */
	private final ShapeElement shape;
	/** 최소 크기 */
	private final Dimension minSize;	// KJH 20110420, add

	/**
	 * Graphical Node의 크기, 위치를 변경할 command 를 생성한다. 
	 * @param shape	크기, 위치가 변경될 Node
	 * @param req		변경 요청 Request
	 * @param newBounds 새로운 위치와 크기
	 * @throws IllegalArgumentException Parameter가 null 이면 발생한다.
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
	 * Graphical Node의 크기, 위치를 변경할 command 를 생성한다. 
	 * @param shape	크기, 위치가 변경될 Node
	 * @param req		변경 요청 Request
	 * @param newBounds 새로운 위치와 크기
	 * @param minSize	최소 크기
	 * @throws IllegalArgumentException Parameter가 null 이면 발생한다.
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
	 * 이 command 의 실행 가능 여부를 반환한다.
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
	 * 이 comman 를 실행한다.
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
	 * 이 command 를 Redo 한다.
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
	 * 이 command 를 Undo 한다.
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	public void undo() {
		shape.setX(oldBounds.x);
		shape.setY(oldBounds.y);
		shape.setHeight(oldBounds.height);
		shape.setWidth(oldBounds.width);
	}
}
