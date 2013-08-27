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
 * 모양을 갖는 모델 요소를 ModelDiagram 에 추가할 command 클래스이다.
 * 이 command 는 Undo/Redo 가 가능하다.
 * GEF EditPart의 Graphical Root 모델은 ModelDiagram 이 가지고 있는
 * Subdiagram 모델이다.
 * Graphical Root 모델에 그려질 수 있는 Figure를 갖는 모델은 TaskElement,
 * StateElement, ActionElement와 Connetion 이 있다.
 * TaskElement, StateElement, ActionElement 은 다른 Graphical Root 모델의
 * 자식으로 Figure를 제공하여야 하는 경우가 있으므로 참조모델(ReferElement)를
 * 생성하여 각각 다른 Graphical Root 모델에 표시되로고 한다.
 * @author sfline
 */
public abstract class ShapeElementCreateCommand extends Command {
	/** 자식 모델이 추가될 부모 모델 */
	protected final ItemElement parentModel;
	/** 부모 모델에 추가될 자식 모델에 대한 참조 모델 */
	protected ReferElement refItem;
	/** 부모 모델 요소에 추가될 자식 모델 */ 
	protected ItemElement newShape;
	/** 부모 모델에 새로운 자식 모델을 추가할 Request */
	protected final CreateRequest request;
	/** 새로운 자식 노드의 추가 성공 여부 */
	protected boolean shapeAdded;
	
	protected Rectangle m_constraint;
	
	/**
	 * ModelDiagram 에 새로운 자식 모델을 추가하는 command 를 생성한다.
	 * @param parentModel the ModelDiagram that will hold the new element
	 * @param req     새로운 자식 모델 생성 요청
	 * @throws IllegalArgumentException 임의 parameter 가 null 이거나 request가
	 * 							새로운 자식 모델 instance 를 제공하지 않으면 발생한다.
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
	 * 이 command 에 대하여 Undo 가능 여부를 제공한다.
	 * 자식 모델이 부모 모델(ModelDiagram)에 추가되었다면 Undo 가 가능하여야 한다.
	 * @see org.eclipse.gef.commands.Command#canUndo()
	 */
	public boolean canUndo() {
		return shapeAdded;
	}
	
	/**
	 * 이 command 를 실행한다.
	 * subclass 는 이 메소드를 구현하여야 한다.
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	public abstract void execute();
	
	/**
	 * 자식 모델에 대한 참조 모델을 생성한다.
	 * @param defaultWidth 참조 모델의 넓이
	 * @param defaultHeight 참조 모델의 높이
	 */
	protected void makeShapeRefer(int defaultWidth, int defaultHeight) {
//		Point locPt = request.getLocation();
		Point locPt = m_constraint.getLocation();
		if((parentModel instanceof ReferElement)||(parentModel instanceof SubDiagram)) {
			TaskModelFactory factory = ModelManager.getFactory();
			
			// KJH 20100722 s, ReferElement도 넘어올 수 있도록 수정, ReferElement로 넘어온 경우 다시 생성하지 않음
			if (refItem == null) {
				refItem = factory.createReferElement();
				refItem.setRealModel(newShape);
				newShape.getReferences().add(refItem);
//				refItem.setCollapsed(true);	// KJH 20110421, collapse기능 없는 모델은 초기값을 true로 함 
			}
			// KJH 20100722 e, ReferElement도 넘어올 수 있도록 수정, ReferElement로 넘어온 경우 다시 생성하지 않음
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

			// KJH 20110413 s, defaultWidth/defaultHeight는 호출당시 결정되도록 수정
			// Get desired location and size from the request
//			if(request.getSize() != null) { // we'll use default size if it's null
//				refItem.setHeight(request.getSize().height);
//				refItem.setWidth(request.getSize().width);
//			}
//			else {
				refItem.setWidth(defaultWidth);
				refItem.setHeight(defaultHeight);
//			}
			// KJH 20110413 e, defaultWidth/defaultHeight는 호출당시 결정되도록 수정
		}
	}
	
	/**
	 * 자식 모델의 최상위 Root 모델을 찾아 반환한다.
	 * @param child 자식 모델
	 * @return 자식 모델의 최상위 모델
	 */
	protected ItemElement getRootModel(ItemElement child) {
		ItemElement parent = child.getParent();
		if(parent == null) {
			return child;
		}
		
		return getRootModel(parent);
	}
	
	/**
	 * 이 command 를 Redo 한다.
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

		// Include 모델은 추가하지 않는다.
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
	 * 이 command 를 Undo 한다.
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

		// Include된 Item은 추가하지 않았음으로 삭제하지 안는다.
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
