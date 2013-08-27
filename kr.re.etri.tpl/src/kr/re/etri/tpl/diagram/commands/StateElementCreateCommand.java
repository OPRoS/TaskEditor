package kr.re.etri.tpl.diagram.commands;

import kr.re.etri.tpl.diagram.util.ItemState;
import kr.re.etri.tpl.taskmodel.BehaviorElement;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.ModelDiagram;
import kr.re.etri.tpl.taskmodel.ReferElement;
import kr.re.etri.tpl.taskmodel.StateAttribute;
import kr.re.etri.tpl.taskmodel.StateElement;
import kr.re.etri.tpl.taskmodel.SubDiagram;
import kr.re.etri.tpl.taskmodel.TaskElement;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.requests.CreateRequest;

/**
 * StateElement�� �����ϴ� command Ŭ�����̴�.
 * �� command �� Redo/Undo �� �����ϴ�.
 * @author sfline
 */
public class StateElementCreateCommand extends ShapeElementCreateCommand {
	
	/**
	 * �θ� �𵨿� StateElement �� �߰��ϴ� command �� �����Ѵ�.
	 * @param parentModel ���ο� StateElement �� �߰��� �θ� ��
	 * @param req     ���ο� StateElement ���� ��û
	 * @throws IllegalArgumentException ���� parameter �� null �̰ų� request �� 
	 * 						���ο� StateElement �� �������� ������ �߻��Ѵ�.
	 */
	public StateElementCreateCommand(ItemElement parentModel, CreateRequest req, Rectangle constraint) {
		super(parentModel, req, constraint);

		setLabel("state creation");
	}
	
	
	/**
	 * �� command �� �����Ѵ�.
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	public void execute() {

		// Obtain the new ModelElement instance from the request.
		// This causes the factory stored in the request to create a new instance.
		// The factory is supplied in the palette-tool-entry, see
		// ShapesEditorPaletteFactory#createComponentsGroup()
		// KJH 20100722 s, ReferElement�� �Ѿ�� �� �ֵ��� ����
		if (request.getNewObject() instanceof StateElement){
			newShape = (StateElement) request.getNewObject();
		}
		else if (request.getNewObject() instanceof ReferElement){
			refItem = (ReferElement) request.getNewObject();
			newShape = refItem.getRealModel();
		}
		// KJH 20100722 e, ReferElement�� �Ѿ�� �� �ֵ��� ����
		int itemState = newShape.getItemState();
		if(itemState == 0) {
			if(!newShape.isIncluded()) {
				// KJH 20100722, �̸��� ������ ��쿡�� �������� ����
				if(newShape.getName() == null || newShape.getName().equals("")) {
					if(StateAttribute.INITIAL == ((StateElement)newShape).getAttribute()) {
						newShape.setName(getNewName("Initial_State"));
					}
					else if(StateAttribute.TARGET == ((StateElement)newShape).getAttribute()) {
						newShape.setName(getNewName("Goal_State"));	// KJH 20110829, Target->Goal
					}
					else {
						newShape.setName(getNewName("New_State"));
					}
				}
				itemState |= ItemState.ITEM_CREATED;
				newShape.setItemState(itemState);
			}
		}

//		Dimension size = request.getSize();
//		if(size != null) {
//			makeShapeRefer(size.width, size.height);
//		}
//		else {
			makeShapeRefer(100, 55);
//		}
		
		redo();
	}
	
	/**
	 * �� Command �� Redo �Ѵ�.
	 * 
	 * @Override
	 */
	public void redo() {
		super.redo();
		
		ItemElement realParent;
		if(parentModel instanceof ModelDiagram) {
			realParent = (ModelDiagram)parentModel;
		}
		else if(parentModel instanceof ReferElement) {
			realParent = ((ReferElement)parentModel).getRealModel();
		}
		else if(parentModel instanceof SubDiagram) {
			realParent = ((SubDiagram)parentModel).getParent();
		}
		else if(parentModel instanceof TaskElement) {
			realParent = (TaskElement)parentModel;
		}
		else if(parentModel instanceof BehaviorElement) {
			realParent = (BehaviorElement)parentModel;
		}
		else {
			realParent = parentModel;
		}

		if(realParent instanceof BehaviorElement) {
			StateAttribute stateAttribute = ((StateElement)newShape).getAttribute();
	
			if(StateAttribute.INITIAL == stateAttribute) {
				((BehaviorElement)realParent).setInitialState((StateElement)newShape);
			}
		}
	}
	
	/**
	 * �� command �� Undo �Ѵ�.
	 * 
	 * @Override
	 */
	public void undo() {
		super.undo();
		
		ItemElement realParent;
		if(parentModel instanceof ModelDiagram) {
			realParent = (ModelDiagram)parentModel;
		}
		else if(parentModel instanceof ReferElement) {
			realParent = ((ReferElement)parentModel).getRealModel();
		}
		else if(parentModel instanceof SubDiagram) {
			realParent = ((SubDiagram)parentModel).getParent();
		}
		else if(parentModel instanceof TaskElement) {
			realParent = (TaskElement)parentModel;
		}
		else if(parentModel instanceof BehaviorElement) {
			realParent = (BehaviorElement)parentModel;
		}
		else {
			realParent = parentModel;
		}

		if(realParent instanceof BehaviorElement) {
			StateAttribute stateAttribute = ((StateElement)newShape).getAttribute();
	
			if(StateAttribute.INITIAL == stateAttribute) {
				((BehaviorElement)realParent).setInitialState(null);
			}
		}
	}
}
