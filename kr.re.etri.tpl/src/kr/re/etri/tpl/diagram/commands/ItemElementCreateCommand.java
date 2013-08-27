package kr.re.etri.tpl.diagram.commands;

import java.util.ArrayList;
import java.util.List;

import kr.re.etri.tpl.diagram.ModelManager;
import kr.re.etri.tpl.diagram.util.ItemState;
import kr.re.etri.tpl.taskmodel.ConnectorElement;
import kr.re.etri.tpl.taskmodel.EnumElement;
import kr.re.etri.tpl.taskmodel.Function;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.ModelDiagram;
import kr.re.etri.tpl.taskmodel.ModelElement;
import kr.re.etri.tpl.taskmodel.ReferElement;
import kr.re.etri.tpl.taskmodel.StateElement;
import kr.re.etri.tpl.taskmodel.SubDiagram;
import kr.re.etri.tpl.taskmodel.BehaviorElement;
import kr.re.etri.tpl.taskmodel.TaskModelFactory;
import kr.re.etri.tpl.taskmodel.TaskElement;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.CreateRequest;

/**
 * �� command Ŭ������ �ڽ� �� ��Ҹ� �θ� �� ��ҿ� �߰��ϴ� command ����
 * ���� Ŭ�����̴�.
 * �� command �� subclass ���� Undo/Redo �� �����ϵ��� �ϱ����ؿ�
 * addToParent(), removeFromParent() �޼ҵ带 �����Ͽ��� �Ѵ�.
 * @author sfline
 */
public abstract class ItemElementCreateCommand extends Command {
	/** �ڽ� ���� �߰��� �θ� �� */
	protected final ItemElement parentModel;
	/** ���ο� �ڽ� �� */ 
	protected ItemElement newModel;
	/** �� �߰� request */
	protected final CreateRequest request;
	/** �θ� �𵨿� �߰� ���� ���� */
	protected boolean modelAdded;

	/**
	 * �ڽ� ���� �θ� �𵨿� �߰��ϴ� command �� �����Ѵ�.
	 * @param parentModel ���ο� �ڽ� ���� �߰��� �θ� ��
	 * @param req     ���ο� ���� ������ request
	 * @throws IllegalArgumentException ���� parameter �� null �̰ų� request��
	 * 							���ο� �� instance �� �������� ������ �߻��Ѵ�.
	 */
	public ItemElementCreateCommand(ItemElement parentModel, CreateRequest req) {
		if (parentModel == null || req == null || req.getNewObject() == null) {
			throw new IllegalArgumentException();
		}
		this.parentModel = parentModel;
		this.request = req;
	}
	
	/**
	 * �� command �� ���Ͽ� Undo ���� ���θ� �����Ѵ�.
	 * �ڽ� ���� �θ� �𵨿� �߰��Ǿ��ٸ� Undo �� �����Ͽ��� �Ѵ�.
	 * @see org.eclipse.gef.commands.Command#canUndo()
	 */
	public boolean canUndo() {
		return modelAdded;
	}

	/**
	 * �� command �� �����Ѵ�. subclass ���� �� �޼ҵ带 �����Ͽ��� �Ѵ�.
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	public abstract void execute();
	
	protected ItemElement getRootModel(ItemElement child) {
		ItemElement parent = child.getParent();
		if(parent == null) {
			return child;
		}
		
		return getRootModel(parent);
	}
	
	/**
	 * ���ο� �𵨸� �θ� �𵨿� �߰��Ѵ�.
	 * �� method �� Redo method ȣ�� �� ����ȴ�.
	 * subclass ���� �� �޼ҵ带 �����Ͽ��� �Ѵ�.
	 * @param newItem �θ� �𵨿� �߰��� �ڽ� ��
	 */
	protected abstract void addToParent(ItemElement newItem);

	/**
	 * �� command �� �ٽ� Redo �Ѵ�.
	 * @see org.eclipse.gef.commands.Command#redo()
	 */
	public void redo() {

		if(newModel.isIncluded() == false) {
			addToParent(newModel);
		}
	}
	
	/**
	 * ���ο� �𵨸� �θ� �𵨷κ��� �����Ѵ�.
	 * �� method �� Undo method ȣ�� �� ����ȴ�.
	 * subclass ���� �� �޼ҵ带 �����Ͽ��� �Ѵ�.
	 * @param newItem �θ� �𵨿��� ������ �ڽ� ��
	 */
	protected abstract void removeFromParent(ItemElement newItem);

	/**
	 * �� command ������ Undo �Ѵ�.
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	public void undo() {
		if(newModel.isIncluded() == false) {
			removeFromParent(newModel);
		}
	}
	
	/**
	 * @author KJH 20110826
	 * @param name
	 * @return
	 */
	protected String getNewName(String name) {
		List<ItemElement> items = new ArrayList<ItemElement>();
		ItemElement realParent = parentModel;
		
		if (realParent instanceof ModelDiagram) {
			items.addAll(((ModelDiagram)realParent).getItems());
		}
		else if (realParent instanceof TaskElement) {
			items.addAll(((TaskElement)realParent).getItems());
		}
		else if (realParent instanceof EnumElement) {
			items.addAll(((EnumElement)realParent).getEnumItem());
		}
		else if (realParent instanceof ModelElement) {
			items.addAll(((ModelElement)realParent).getFunctions());
			items.addAll(((ModelElement)realParent).getSymbols());
			items.addAll(((ModelElement)realParent).getModels());
//			items.addAll(((ModelElement)realParent).getConstants());
		}
		else if (realParent instanceof Function) {
			items.addAll(((Function)realParent).getParams());
		}
		
		int index = 1;
		while (true) {
			boolean bCheck = true;
			for (int i = 0; i < items.size(); i++) {
				ItemElement item = items.get(i);
				if (item.getClass().equals(newModel.getClass())) {
					if (item.getName() != null && item.getName().equals(name)) {
						name += Integer.toString(index);
						index++;
						bCheck = false;
						break;
					}
				}
			}
			
			if (bCheck) {
				break;
			}
		}
		
		return name;
	}
}
