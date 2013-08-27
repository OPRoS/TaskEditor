package kr.re.etri.tpl.diagram.commands;

import kr.re.etri.tpl.diagram.util.ItemState;
import kr.re.etri.tpl.taskmodel.ActionElement;
import kr.re.etri.tpl.taskmodel.ItemElement;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.requests.CreateRequest;

/**
 * 모델에 ActionElement를 생성하여 추가한다. 생성된 ActionElement는
 * Diagram에 추가된다.
 * 
 * 이 명령은 Undo/Redo 가 가능한다.
 * @author sfline
 */
public class ActionElementCreateCommand extends ShapeElementCreateCommand {

	/**
	 * ModelDiagram 에 새로운 ActionElement 를 생성하는 command 를 생성한다.
	 * @param parent 새로운 ActionElement 가 추가될 부모 모델
	 * @param req     새로운 ActionElement 생성 요청
	 * @throws IllegalArgumentException 임의 parameter 가 null 이거나 request 가
	 * 							새로운 ActionElement instance 를 제공하지 않으면 발생한다.
	 */
	public ActionElementCreateCommand(ItemElement parentModel, CreateRequest req, Rectangle constraint) {
		super(parentModel, req, constraint);

		// Undo/Redo를 위한 Context 메뉴의 Text를 설정한다.
		setLabel("action creation");
	}
	
	/**
	 * 이 Command 를 실행한다.
	 */
	public void execute() {

		// request 로부터 새로 생성된 ActionElement 를 획득한다.
		// 새로운 Instance 는 pallette 나 메뉴 선택으로 인하여 TaskModelFactory 로
		// 생성된다.
		newShape = (ActionElement) request.getNewObject();
		int itemState = newShape.getItemState();
		if(itemState == 0) {
			if(!newShape.isIncluded()) {
				newShape.setName("New_Action");
				itemState |= ItemState.ITEM_CREATED;
				newShape.setItemState(itemState);
			}
		}

		// size data가 있다면 해당 data를 이용 GEF 노드를 생성한다.
		// 그렇지 않으면 디폴트 크기로 생성한다.
		Dimension size = request.getSize();
		if(size != null) {
			makeShapeRefer(size.width, size.height);
		}
		else {
			makeShapeRefer(80, 40);
		}

		// ActionElement을 ModelDiagram에 추가한다.
		redo();
	}
}
