package kr.re.etri.tpl.diagram.commands;

import kr.re.etri.tpl.diagram.util.ItemState;
import kr.re.etri.tpl.taskmodel.Function;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.ModelElement;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.requests.CreateRequest;

/**
 * ModelElement 에 Function 를 추가하는 command 클래스이다.
 * 이 command 는 Undo/Redo 가 가능하다.
 * @author sfline
 */
public class FunctionCreateCommand extends ItemElementCreateCommand {
	
	/**
	 * 이 command 는 ModelElement에 새로운 Function 를 추가한다.
	 * @param parentModel 새로운 Function 가 추가될 부모 모델
	 * @param req     새로운 Function 생성 요청
	 * @throws IllegalArgumentException 임의 parameter 가 null 이거나 request가
	 * 							새로운 Constant instance 를 제공하지 않으면 발생한다.
	 */
	public FunctionCreateCommand(ItemElement parentModel, CreateRequest req) {
		super(parentModel, req);

		setLabel("function creation");
	}
	
	/**
	 * 이 command 를 실행한다.
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	public void execute() {
		Point locPt = request.getLocation();

		// request 로부터 새로운 Function 모델을 획득한다.
		// Constant 모델은 palette-tool에서 제공하지 않으며, 메뉴 실행으로 인하여
		// factory 로 부터 새성된다.
		newModel = (Function) request.getNewObject();
		int itemState = newModel.getItemState();
		if(itemState == 0) {
			if(!newModel.isIncluded()) {
				newModel.setName(getNewName("New_Function"));
				itemState |= ItemState.ITEM_CREATED;
				newModel.setItemState(itemState);
			}
		}

		redo();
	}
	
	/**
	 * 새로운 모델(Function)를 ModelElement 에 추가한다.
	 * 이 method 는 Redo method 호출 시 실행된다.
	 * @param newItem 부모 모델에 추가할 자식 모델
	 */
	protected void addToParent(ItemElement newItem) {
		
		// Function Parent는 ModelElement만이 가능함.
		if(parentModel instanceof ModelElement) {
			modelAdded = ((ModelElement)parentModel).getFunctions().add((Function)newItem);
			if(modelAdded)	newItem.setParent(parentModel);	// KJH 20100524
		}
	}
	
	/**
	 * 새로운 모델(Function)를 ModelElement 로부터 삭제한다.
	 * 이 method 는 Undo method 호출 시 실행된다.
	 * @param newItem 부모 모델에서 삭제할 자식 모델
	 */
	protected void removeFromParent(ItemElement newItem) {
		
		// Function Parent는 ModelElement만이 가능함.
		if(parentModel instanceof ModelElement) {
			((ModelElement)parentModel).getFunctions().remove((Function)newItem);
		}
	}
}
