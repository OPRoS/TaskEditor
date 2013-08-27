package kr.re.etri.tpl.diagram.commands;

import java.util.ArrayList;
import java.util.List;

import kr.re.etri.tpl.diagram.ModelManager;
import kr.re.etri.tpl.taskmodel.BehaviorElement;
import kr.re.etri.tpl.taskmodel.ConnectorElement;
import kr.re.etri.tpl.taskmodel.EnumElement;
import kr.re.etri.tpl.taskmodel.ExpandTransElement;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.ModelDiagram;
import kr.re.etri.tpl.taskmodel.ReferElement;
import kr.re.etri.tpl.taskmodel.StateElement;
import kr.re.etri.tpl.taskmodel.SubDiagram;
import kr.re.etri.tpl.taskmodel.TaskElement;
import kr.re.etri.tpl.taskmodel.TaskModelFactory;
import kr.re.etri.tpl.taskmodel.WithElement;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.CreateRequest;

/**
 * ����� ���� �� ��Ҹ� ModelDiagram �� �߰��� command Ŭ�����̴�.
 * �� command �� Undo/Redo �� �����ϴ�.
 * GEF EditPart�� Graphical Root ���� ModelDiagram �� ������ �ִ�
 * Subdiagram ���̴�.
 * Graphical Root �𵨿� �׷��� �� �ִ� Figure�� ���� ���� TaskElement,
 * StateElement, ActionElement�� Connetion �� �ִ�.
 * TaskElement, StateElement, ActionElement �� �ٸ� Graphical Root ����
 * �ڽ����� Figure�� �����Ͽ��� �ϴ� ��찡 �����Ƿ� ������(ReferElement)��
 * �����Ͽ� ���� �ٸ� Graphical Root �𵨿� ǥ�õǷΰ� �Ѵ�.
 * @author sfline
 */
public abstract class ShapeElementCreateCommand extends Command {
	/** �ڽ� ���� �߰��� �θ� �� */
	protected final ItemElement parentModel;
	/** �θ� �𵨿� �߰��� �ڽ� �𵨿� ���� ���� �� */
	protected ReferElement refItem;
	/** �θ� �� ��ҿ� �߰��� �ڽ� �� */ 
	protected ItemElement newShape;
	/** �θ� �𵨿� ���ο� �ڽ� ���� �߰��� Request */
	protected final CreateRequest request;
	/** ���ο� �ڽ� ����� �߰� ���� ���� */
	protected boolean shapeAdded;
	
	protected Rectangle m_constraint;
	
	/**
	 * ModelDiagram �� ���ο� �ڽ� ���� �߰��ϴ� command �� �����Ѵ�.
	 * @param parentModel the ModelDiagram that will hold the new element
	 * @param req     ���ο� �ڽ� �� ���� ��û
	 * @throws IllegalArgumentException ���� parameter �� null �̰ų� request��
	 * 							���ο� �ڽ� �� instance �� �������� ������ �߻��Ѵ�.
	 */
	public ShapeElementCreateCommand(ItemElement parentModel, CreateRequest req, Rectangle constraint) {
		if (parentModel == null || req == null || req.getNewObject() == null) {
			throw new IllegalArgumentException();
		}
		m_constraint = constraint;
		this.parentModel = parentModel;
		this.request = req;
		setLabel("shape creation");
	}

	/**
	 * �� command �� ���Ͽ� Undo ���� ���θ� �����Ѵ�.
	 * �ڽ� ���� �θ� ��(ModelDiagram)�� �߰��Ǿ��ٸ� Undo �� �����Ͽ��� �Ѵ�.
	 * @see org.eclipse.gef.commands.Command#canUndo()
	 */
	public boolean canUndo() {
		return shapeAdded;
	}
	
	/**
	 * �� command �� �����Ѵ�.
	 * subclass �� �� �޼ҵ带 �����Ͽ��� �Ѵ�.
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	public abstract void execute();
	
	/**
	 * �ڽ� �𵨿� ���� ���� ���� �����Ѵ�.
	 * @param defaultWidth ���� ���� ����
	 * @param defaultHeight ���� ���� ����
	 */
	protected void makeShapeRefer(int defaultWidth, int defaultHeight) {
//		Point locPt = request.getLocation();
		Point locPt = m_constraint.getLocation();
		if((parentModel instanceof ReferElement)||(parentModel instanceof SubDiagram)) {
			TaskModelFactory factory = ModelManager.getFactory();
			
			// KJH 20100722 s, ReferElement�� �Ѿ�� �� �ֵ��� ����, ReferElement�� �Ѿ�� ��� �ٽ� �������� ����
			if (refItem == null) {
				refItem = factory.createReferElement();
				refItem.setRealModel(newShape);
				newShape.getReferences().add(refItem);
//				refItem.setCollapsed(true);	// KJH 20110421, collapse��� ���� ���� �ʱⰪ�� true�� �� 
			}
			// KJH 20100722 e, ReferElement�� �Ѿ�� �� �ֵ��� ����, ReferElement�� �Ѿ�� ��� �ٽ� �������� ����
			if(newShape instanceof BehaviorElement) {
				refItem.setCollapsed(true);
				if(defaultWidth > 120) {
					refItem.setWidth2(120);	// KJH 20110413, defaultWidth->120
					refItem.setCollapsed(false);
				}
				else {
					refItem.setWidth2(300);
				}
				if(defaultHeight > 50) {
					refItem.setHeight2(50);	// KJH 20110413, defaultHeight->28
					refItem.setCollapsed(false);
				}
				else {
					refItem.setHeight2(200);
				}
			}
			// KJH 20110502 s, connector
			else if(newShape instanceof ConnectorElement) {
				refItem.setCollapsed(true);
				if(defaultWidth > 120) {
					refItem.setWidth2(120);	// KJH 20110413, defaultWidth->120
					refItem.setCollapsed(false);
				}
				else {
					refItem.setWidth2(200);
				}
				if(defaultHeight > 50) {
					refItem.setHeight2(50);	// KJH 20110413, defaultHeight->28
					refItem.setCollapsed(false);
				}
				else {
					refItem.setHeight2(150);
				}
			}// KJH 20110502 e, connector
			else if (newShape instanceof TaskElement) {
				// KJH 20110728 s,
				refItem.setCollapsed(true);
				if(defaultWidth > 120) {
					refItem.setWidth2(120);
					refItem.setCollapsed(false);
				}
				else {
					refItem.setWidth2(150);
				}
				if(defaultHeight > 50) {
					refItem.setHeight2(50);
					refItem.setCollapsed(false);
				}
				else {
					refItem.setHeight2(100);
				}
				// KJH 20110728 e,	
			}
			else {
				// KJH 20110513 s,
				refItem.setCollapsed(false);
				refItem.setWidth2(defaultWidth);
				refItem.setHeight2(defaultHeight);
				// KJH 20110513 e,
			}

//			IFigure contentFigure = new Figure();
//			contentFigure.translateToAbsolute(new Rectangle(10, 10, 10, 10));
			if(parentModel instanceof ReferElement) {
				Point tmpTp = Point.SINGLETON;
				tmpTp.x = locPt.x;
				tmpTp.y = locPt.y;
				tmpTp.performTranslate(-((ReferElement)parentModel).getX(), -((ReferElement)parentModel).getY());
				refItem.setX(tmpTp.x);
				refItem.setY(tmpTp.y);
				// KJH 20110513 s,
				refItem.setX2(tmpTp.x);
				refItem.setY2(tmpTp.y);
				// KJH 20110513 e,
			}
			else {
				refItem.setX(locPt.x);
				refItem.setY(locPt.y);
				// KJH 20110513 s,
				refItem.setX2(locPt.x);
				refItem.setY2(locPt.y);
				// KJH 20110513 e,
			}

			// KJH 20110413 s, defaultWidth/defaultHeight�� ȣ���� �����ǵ��� ����
			// Get desired location and size from the request
//			if(request.getSize() != null) { // we'll use default size if it's null
//				refItem.setHeight(request.getSize().height);
//				refItem.setWidth(request.getSize().width);
//			}
//			else {
				refItem.setWidth(defaultWidth);
				refItem.setHeight(defaultHeight);
//			}
			// KJH 20110413 e, defaultWidth/defaultHeight�� ȣ���� �����ǵ��� ����
		}
	}
	
	/**
	 * �ڽ� ���� �ֻ��� Root ���� ã�� ��ȯ�Ѵ�.
	 * @param child �ڽ� ��
	 * @return �ڽ� ���� �ֻ��� ��
	 */
	protected ItemElement getRootModel(ItemElement child) {
		ItemElement parent = child.getParent();
		if(parent == null) {
			return child;
		}
		
		return getRootModel(parent);
	}
	
	/**
	 * �� command �� Redo �Ѵ�.
	 * @see org.eclipse.gef.commands.Command#redo()
	 */
	public void redo() {
		ItemElement realParent, rootModel;
		if(parentModel instanceof ModelDiagram) {
			realParent = (ModelDiagram)parentModel;
			rootModel = realParent;
			if(!newShape.isIncluded()) {
				newShape.setParent(realParent);
			}
		}
		else if(parentModel instanceof ReferElement) {
			realParent = ((ReferElement)parentModel).getRealModel();
			rootModel = realParent;
			if(!newShape.isIncluded()) {
				newShape.setParent(realParent);
			}
			refItem.setParent(parentModel);
		}
		else if(parentModel instanceof SubDiagram) {
			realParent = ((SubDiagram)parentModel).getParent();
			rootModel = getRootModel(realParent);
			if(!newShape.isIncluded()) {
				newShape.setParent(rootModel);
			}
			refItem.setParent(parentModel);
		}
		else if(parentModel instanceof TaskElement) {
			realParent = (TaskElement)parentModel;
			rootModel = getRootModel(realParent);
			if(!newShape.isIncluded()) {
				newShape.setParent(realParent);
			}
		}
		else if(parentModel instanceof BehaviorElement) {
			realParent = (BehaviorElement)parentModel;
			rootModel = realParent;
			if(!newShape.isIncluded()) {
				newShape.setParent(realParent);
			}
		}
		// KJH 20110502 s, connector\with
		else if(parentModel instanceof ConnectorElement) {
			realParent = (ConnectorElement)parentModel;
			rootModel = realParent;
			if(!newShape.isIncluded()) {
				newShape.setParent(realParent);
			}
		}// KJH 20110502 e, connector\with
		else {//????
			realParent = parentModel;
			rootModel = realParent;
			if(!newShape.isIncluded()) {
				newShape.setParent(parentModel);
			}
		}

		// Include ���� �߰����� �ʴ´�.
		int itemState = newShape.getItemState();
		if(newShape.isIncluded() == false) {
			if(rootModel instanceof ModelDiagram) {
				shapeAdded = ((ModelDiagram)rootModel).getItems().add(newShape);
				if(realParent instanceof TaskElement) {
					((TaskElement)realParent).getItems().add(newShape);
				}
			}
			else if(rootModel instanceof ReferElement) {
				shapeAdded = ((ReferElement)rootModel).getItems().add(newShape);
			}
			else if(rootModel instanceof TaskElement) {
				shapeAdded = ((TaskElement)rootModel).getItems().add(newShape);
			}
			else if(rootModel instanceof BehaviorElement) {
				if (newShape instanceof StateElement) {
					shapeAdded = ((BehaviorElement)rootModel).getStates().add((StateElement)newShape);
				}
				else if (newShape instanceof ExpandTransElement) {
					shapeAdded = ((BehaviorElement)rootModel).getBifurcates().add((ExpandTransElement)newShape);
				}
			}
			// KJH 20110503 s, connector\with
			else if (rootModel instanceof ConnectorElement) {
				shapeAdded = ((ConnectorElement)rootModel).getWiths().add((WithElement)newShape);
			}	// KJH 20110503 e, connector\with
			// KJH 20110518 s, expand&trans
			else if (rootModel instanceof StateElement) {
				shapeAdded = ((StateElement)rootModel).getBifurcates().add((ExpandTransElement)newShape);
			}
		}

		if(refItem != null) {
			if(parentModel instanceof ModelDiagram) {
				shapeAdded = ((ModelDiagram)parentModel).getItems().add(refItem);
			}
			else if(parentModel instanceof SubDiagram) {
				shapeAdded = ((SubDiagram)parentModel).getItems().add(refItem);
			}
			else if(parentModel instanceof ReferElement) {
				shapeAdded = ((ReferElement)parentModel).getItems().add(refItem);
			}
			else if(parentModel instanceof TaskElement) {
				shapeAdded = ((TaskElement)parentModel).getItems().add(refItem);
			}
		}
	}

	/**
	 * �� command �� Undo �Ѵ�.
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	public void undo() {
		ItemElement realParent, rootModel;
		if(parentModel instanceof ModelDiagram) {
			realParent = (ModelDiagram)parentModel;
			rootModel = realParent;
			if(!newShape.isIncluded()) {
				newShape.setParent(null);
			}
		}
		else if(parentModel instanceof ReferElement) {
			realParent = ((ReferElement)parentModel).getRealModel();
			rootModel = realParent;
			newShape.setParent(null);
			refItem.setParent(null);
		}
		else if(parentModel instanceof SubDiagram) {
			realParent = ((SubDiagram)parentModel).getParent();
			rootModel = getRootModel(realParent);
			if(!newShape.isIncluded()) {
				newShape.setParent(null);
			}
			refItem.setParent(null);
		}
		else if(parentModel instanceof TaskElement) {
			realParent = ((TaskElement)parentModel);
			rootModel = getRootModel(realParent);
			if(!newShape.isIncluded()) {
				newShape.setParent(null);
			}
		}
		else if(parentModel instanceof BehaviorElement) {
			realParent = ((BehaviorElement)parentModel);
			rootModel = realParent;
			if(!newShape.isIncluded()) {
				newShape.setParent(null);
			}
		}
		else {//????
			realParent = parentModel;
			rootModel = realParent;
			if(!newShape.isIncluded()) {
				newShape.setParent(parentModel);
			}
		}

		// Include�� Item�� �߰����� �ʾ������� �������� �ȴ´�.
		int itemState = newShape.getItemState();
		if(newShape.isIncluded() == false) {
			if(rootModel instanceof ModelDiagram) {
				((ModelDiagram)rootModel).getItems().remove(newShape);
				if(realParent instanceof TaskElement) {
					((TaskElement)realParent).getItems().remove(newShape);
				}
			}
			else if(rootModel instanceof ReferElement) {
				((ReferElement)rootModel).getItems().remove(newShape);
			}
			else if(rootModel instanceof TaskElement) {
				((TaskElement)rootModel).getItems().remove(newShape);
			}
			else if(rootModel instanceof BehaviorElement) {
				((BehaviorElement)rootModel).getStates().remove(newShape);
			}
		}

		if(refItem != null) {
			if(parentModel instanceof ModelDiagram) {
				((ModelDiagram)parentModel).getItems().remove(refItem);
			}
			else if(parentModel instanceof SubDiagram) {
				((SubDiagram)parentModel).getItems().remove(refItem);
			}
			else if(parentModel instanceof ReferElement) {
				((ReferElement)parentModel).getItems().remove(refItem);
			}
			else if(parentModel instanceof TaskElement) {
				((TaskElement)parentModel).getItems().remove(refItem);
			}
			else if(parentModel instanceof BehaviorElement) {
				((BehaviorElement)parentModel).getStates().remove(refItem);
			}
		}
	}
	
	protected String getNewName(String name) {
		if (name == null)	name = "";
		
		List<ItemElement> items = new ArrayList<ItemElement>();
		ItemElement realParent;
		
		if (parentModel instanceof ModelDiagram) {
			realParent = parentModel;
		}
		else if (parentModel instanceof SubDiagram) {
			realParent = ((SubDiagram)parentModel).getParent();
		}
		else if (parentModel instanceof ReferElement) {
			realParent = ((ReferElement)parentModel).getRealModel();
		}
		else {
			realParent = parentModel;
		}
		
		if (realParent instanceof ModelDiagram) {
			items.addAll(((ModelDiagram)realParent).getItems());
		}
		else if (realParent instanceof BehaviorElement) {
			items.addAll(((BehaviorElement)realParent).getStates());
		}
		else if (realParent instanceof ConnectorElement) {
			items.addAll(((ConnectorElement)realParent).getWiths());
		}
		else if (realParent instanceof TaskElement) {
			items.addAll(((TaskElement)realParent).getItems());
		}
		
		int index = 1;
		String newName = name;
		while (true) {
			boolean bCheck = true;
			for (int i = 0; i < items.size(); i++) {
				ItemElement item = items.get(i);
				if (item.getClass().equals(newShape.getClass()) == false) {
					continue;
				}
				if (newName.equals(item.getName())) {
					newName = String.format("%s%d", name, index);
					index++;
					bCheck = false;
					break;
				}
			}
			
			if (bCheck) {
				break;
			}
		}
		
		return newName;
	}
}
