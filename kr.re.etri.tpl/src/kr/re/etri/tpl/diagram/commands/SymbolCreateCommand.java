package kr.re.etri.tpl.diagram.commands;

import kr.re.etri.tpl.diagram.util.ItemState;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.ModelElement;
import kr.re.etri.tpl.taskmodel.Symbol;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.requests.CreateRequest;

/**
 * ModelElement 에 새로운 Symbol 을 생성하는 command 클래스이다.
 * 이 command 는 Redo/Undo 가 가능하다.
 * @author sfline
 */
public class SymbolCreateCommand extends ItemElementCreateCommand {
	
	/**
	 * ModelElement 에 새로운 Symbol 을 생성하는 command 를 생성한다.
	 * @param parentModel 새로운 자식 모델이 추가될 부모 모델
	 * @param req     새로운 모델을 생성할 request
	 * @throws IllegalArgumentException 임의 parameter 가 null 이거나 request가
	 * 							새로운 모델 instance 를 제공하지 않으면 발생한다.
	 */
	public SymbolCreateCommand(ItemElement parentModel, CreateRequest req) {
		super(parentModel, req);

		setLabel("symbol creation");
	}
	
	/**
	 * 이 command 를 실행한다.
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	public void execute() {
		Point locPt = request.getLocation();

		// request 로부터 새로운 Symbol 모델을 획득한다.
		// Symbol 모델은 palette-tool에서 제공하지 않으며, 메뉴 실행으로 인하여
		// factory 로 부터 새성된다.
		newModel = (Symbol) request.getNewObject();
		int itemState = newModel.getItemState();
		if(itemState == 0) {
			if(!newModel.isIncluded()) {
				newModel.setName(getNewName("New_Symbol"));
				itemState |= ItemState.ITEM_CREATED;
				newModel.setItemState(itemState);
			}
		}

		redo();
	}
	
	/**
	 * 새로운 모델(Symbol)를 ModelElement 에 추가한다.
	 * 이 method 는 Redo method 호출 시 실행된다.
	 * @Override
	 */
	protected void addToParent(ItemElement newItem) {
		
		// Symbol Parent는 ModelElement만이 가능함.
		if(parentModel instanceof ModelElement) {
			modelAdded = ((ModelElement)parentModel).getSymbols().add((Symbol)newItem);
			if(modelAdded)	newItem.setParent(parentModel);	// KJH 20100524
		}
	}
	
	/**
	 * 새로운 모델(Symbol)를 ModelElement 로부터 삭제한다.
	 * 이 method 는 Undo method 호출 시 실행된다.
	 * @Override
	 */
	protected void removeFromParent(ItemElement newItem) {
		
		// Symbol Parent는 ModelElement만이 가능함.
		if(parentModel instanceof ModelElement) {
			((ModelElement)parentModel).getSymbols().remove((Symbol)newItem);
		}
	}
}
