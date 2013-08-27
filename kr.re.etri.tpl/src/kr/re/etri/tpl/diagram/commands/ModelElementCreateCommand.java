package kr.re.etri.tpl.diagram.commands;

import kr.re.etri.tpl.diagram.util.ItemState;
import kr.re.etri.tpl.taskmodel.IncludedElement;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.ModelDiagram;
import kr.re.etri.tpl.taskmodel.ModelElement;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.requests.CreateRequest;

/**
 * 부모 모델에 ModelElement 를 생성하는 command 클래스이다.
 * 이 command 는 Redo/Undo 가 가능하다.
 * @author sfline
 */
public class ModelElementCreateCommand extends ItemElementCreateCommand {
	
	/**
	 * 부모 모델(ModelDiagram, IncludedElement)에 새로운 ModelElement를 추가하는
	 * command 를 생성한다.
	 * @param parentModel 새로운 ModelElement 가 추가될 부모 모델
	 * @param req     새로운 ModelElement 생성 요청
	 * @throws IllegalArgumentException 임의 parameter 가 null 이거나 request가
	 * 							새로운 ModelElement instance 를 제공하지 않으면 발생한다.
	 */
	public ModelElementCreateCommand(ItemElement parentModel, CreateRequest req) {
		super(parentModel, req);

		setLabel("model creation");
	}
	
	/**
	 * 이 command 를 수행한다.
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	public void execute() {
		Point locPt = request.getLocation();

		// request 로부터 새로운 ModelElement 모델을 획득한다.
		// Constant 모델은 palette-tool에서 제공하지 않으며, 메뉴 실행으로 인하여
		// factory 로 부터 새성된다.
		newModel = (ModelElement) request.getNewObject();
		int itemState = newModel.getItemState();
		if(itemState == 0) {
			if(!newModel.isIncluded()) {
				newModel.setName(getNewName("New_Model"));
				itemState |= ItemState.ITEM_CREATED;
				newModel.setItemState(itemState);
			}
		}

		redo();
	}
	
	/**
	 * 새로운 모델(ModelElement)를 부모 모델에 추가한다.
	 * 이 method 는 Redo method 호출 시 실행된다.
	 */
	protected void addToParent(ItemElement newItem) {
		
		// Model의 Parent는 ModelDiagram, IncludedElement, ModelElement만이 가능함.
		if(parentModel instanceof ModelDiagram) {
			modelAdded = ((ModelDiagram)parentModel).getItems().add(newItem);
			if(modelAdded)	newItem.setParent(parentModel);	// KJH 20100524
		}
		else if(parentModel instanceof IncludedElement) {
			modelAdded = ((IncludedElement)parentModel).getItems().add(newItem);
			if(modelAdded)	newItem.setParent(parentModel);	// KJH 20100524
		}
		else if(parentModel instanceof ModelElement) {
			modelAdded = ((ModelElement)parentModel).getModels().add((ModelElement)newItem);
			if(modelAdded)	newItem.setParent(parentModel);	// KJH 20100524
		}
	}
	
	/**
	 * 새로운 모델(ModelElement)를 부모 모델로부터 삭제한다.
	 * 이 method 는 Undo method 호출 시 실행된다.
	 */
	protected void removeFromParent(ItemElement newItem) {
		
		// Model의 Parent는 ModelDiagram, IncludedElement, ModelElement만이 가능함.
		if(parentModel instanceof ModelDiagram) {
			((ModelDiagram)parentModel).getItems().remove(newItem);
		}
		else if(parentModel instanceof IncludedElement) {
			((IncludedElement)parentModel).getItems().remove(newItem);
		}
		else if(parentModel instanceof ModelElement) {
			((ModelElement)parentModel).getModels().remove((ModelElement)newItem);
		}
	}
}
