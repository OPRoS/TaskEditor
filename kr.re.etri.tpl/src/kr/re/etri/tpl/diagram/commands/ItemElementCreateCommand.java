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
 * 이 command 클래스는 자식 모델 요소를 부모 모델 요소에 추가하는 command 들의
 * 상위 클래스이다.
 * 이 command 의 subclass 들은 Undo/Redo 가 가능하도록 하기위해여
 * addToParent(), removeFromParent() 메소드를 구현하여야 한다.
 * @author sfline
 */
public abstract class ItemElementCreateCommand extends Command {
	/** 자식 모델이 추가될 부모 모델 */
	protected final ItemElement parentModel;
	/** 새로운 자식 모델 */ 
	protected ItemElement newModel;
	/** 모델 추가 request */
	protected final CreateRequest request;
	/** 부모 모델에 추가 성공 여부 */
	protected boolean modelAdded;

	/**
	 * 자식 모델을 부모 모델에 추가하는 command 를 생성한다.
	 * @param parentModel 새로운 자식 모델이 추가될 부모 모델
	 * @param req     새로운 모델을 생성할 request
	 * @throws IllegalArgumentException 임의 parameter 가 null 이거나 request가
	 * 							새로운 모델 instance 를 제공하지 않으면 발생한다.
	 */
	public ItemElementCreateCommand(ItemElement parentModel, CreateRequest req) {
		if (parentModel == null || req == null || req.getNewObject() == null) {
			throw new IllegalArgumentException();
		}
		this.parentModel = parentModel;
		this.request = req;
	}
	
	/**
	 * 이 command 에 대하여 Undo 가능 여부를 제공한다.
	 * 자식 모델이 부모 모델에 추가되었다면 Undo 가 가능하여야 한다.
	 * @see org.eclipse.gef.commands.Command#canUndo()
	 */
	public boolean canUndo() {
		return modelAdded;
	}

	/**
	 * 이 command 를 실행한다. subclass 들은 이 메소드를 구현하여야 한다.
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
	 * 새로운 모델를 부모 모델에 추가한다.
	 * 이 method 는 Redo method 호출 시 실행된다.
	 * subclass 들은 이 메소드를 구현하여야 한다.
	 * @param newItem 부모 모델에 추가할 자식 모델
	 */
	protected abstract void addToParent(ItemElement newItem);

	/**
	 * 이 command 를 다시 Redo 한다.
	 * @see org.eclipse.gef.commands.Command#redo()
	 */
	public void redo() {

		if(newModel.isIncluded() == false) {
			addToParent(newModel);
		}
	}
	
	/**
	 * 새로운 모델를 부모 모델로부터 삭제한다.
	 * 이 method 는 Undo method 호출 시 실행된다.
	 * subclass 들은 이 메소드를 구현하여야 한다.
	 * @param newItem 부모 모델에서 삭제할 자식 모델
	 */
	protected abstract void removeFromParent(ItemElement newItem);

	/**
	 * 이 command 수행을 Undo 한다.
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
