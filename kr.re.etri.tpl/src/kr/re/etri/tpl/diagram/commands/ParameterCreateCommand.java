package kr.re.etri.tpl.diagram.commands;

import kr.re.etri.tpl.diagram.util.ItemState;
import kr.re.etri.tpl.taskmodel.ActionElement;
import kr.re.etri.tpl.taskmodel.BehaviorElement;
import kr.re.etri.tpl.taskmodel.Function;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.Parameter;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.requests.CreateRequest;

/**
 * 부모 모델에 Parameter 를 추가하는 command 클래스이다.
 * Parameter 가 추가될 수있는 부모 모델은
 * ActionElement, TaskElement, Function
 * 모델 등이 있다.
 * 이 command 는 Undo/Redo 가 가능하다.
 * @author sfline
 */
public class ParameterCreateCommand extends ItemElementCreateCommand {
	
	/**
	 * 이 command 는 부모 모델에 새로운 Parameter 를 추가한다.
	 * @param parentModel 새로운 Parameter 가 추가될 부모 모델
	 * @param req     새로운 Parameter 생성 요청
	 * @throws IllegalArgumentException 임의 parameter 가 null 이거나 request가
	 * 							새로운 Parameter instance 를 제공하지 않으면 발생한다.
	 */
	public ParameterCreateCommand(ItemElement parentModel, CreateRequest req) {
		super(parentModel, req);

		setLabel("add parameter");
	}
	
	/**
	 * 이 command 를 실행한다.
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	public void execute() {
		Point locPt = request.getLocation();

		// request 로부터 새로운 Parameter 모델을 획득한다.
		// Parameter 모델은 palette-tool에서 제공하지 않으며, 메뉴 실행으로 인하여
		// factory 로 부터 새성된다.
		newModel = (Parameter) request.getNewObject();
		int itemState = newModel.getItemState();
		if(itemState == 0) {
			if(!newModel.isIncluded()) {
				newModel.setName(getNewName("New_Param"));
				itemState |= ItemState.ITEM_CREATED;
				newModel.setItemState(itemState);
			}
		}

		redo();
	}

	/**
	 * 새로운 모델(Parameter)를 부모 모델에 추가한다.
	 * 이 method 는 Redo method 호출 시 실행된다.
	 */
	protected void addToParent(ItemElement newItem) {
		
		// Parameter Parent는 ActionElement, TaskElement, Function만이 가능함.
		if(parentModel instanceof ActionElement) {
			modelAdded = ((ActionElement)parentModel).getParams().add((Parameter)newItem);
			if(modelAdded)	newItem.setParent(parentModel);	// KJH 20100524
		}
		else if(parentModel instanceof BehaviorElement) {
			modelAdded = ((BehaviorElement)parentModel).getParams().add((Parameter)newItem);
			if(modelAdded)	newItem.setParent(parentModel);	// KJH 20100524
		}
		else if(parentModel instanceof Function) {
			modelAdded = ((Function)parentModel).getParams().add((Parameter)newItem);
			if(modelAdded)	newItem.setParent(parentModel);	// KJH 20100524
		}
	}

	/**
	 * 새로운 모델(Parameter)를 부모 모델로부터 삭제한다.
	 * 이 method 는 Undo method 호출 시 실행된다.
	 */
	protected void removeFromParent(ItemElement newItem) {
		
		// Parameter Parent는 ActionElement, TaskElement, Function만이 가능함.
		if(parentModel instanceof ActionElement) {
			((ActionElement)parentModel).getParams().remove((Parameter)newItem);
		}
		else if(parentModel instanceof BehaviorElement) {
			((BehaviorElement)parentModel).getParams().remove((Parameter)newItem);
		}
		else if(parentModel instanceof Function) {
			((Function)parentModel).getParams().remove((Parameter)newItem);
		}
	}
}
