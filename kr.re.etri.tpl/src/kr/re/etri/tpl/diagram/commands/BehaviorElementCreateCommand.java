package kr.re.etri.tpl.diagram.commands;

import kr.re.etri.tpl.diagram.util.ItemState;
import kr.re.etri.tpl.taskmodel.BehaviorElement;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.ReferElement;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.requests.CreateRequest;

/**
 * 부모 모델 요소에 새로운 TaskElement 를 생성하는 command 클래스이다.
 * 이 command 는 Undo/Redo 가 가능하다.
 * @author sfline
 */
public class BehaviorElementCreateCommand extends ShapeElementCreateCommand {
	
	/**
	 * ModelDiagram 에 새로운 TaskElement 를 생성하는 command 를 생성한다.
	 * @param parent 새로운 TaskElement 가 추가될 부모 모델
	 * @param req     새로운 TaskElement 생성 요청
	 * @throws IllegalArgumentException 임의 parameter 가 null 이거나 request 가
	 * 							새로운 TaskElement instance 를 제공하지 않으면 발생한다.
	 */
	public BehaviorElementCreateCommand(ItemElement parentModel, CreateRequest req, Rectangle constraint) {
		super(parentModel, req, constraint);

		setLabel("behavior creation");
	}
	
	/**
	 * 이 Command 를 실행한다.
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	public void execute() {
	
		// request 로부터 새로 생성된 TaskElement 를 획득한다.
		// 새로운 Instance 는 pallet 나 메뉴 선택으로 인하여 TaskModelFactory 로
		// 생성된다.
		// KJH 20100722 s, ReferElement도 넘어올 수 있도록 수정
		if (request.getNewObject() instanceof BehaviorElement) {
			newShape = (BehaviorElement) request.getNewObject();
		}
		else {
			refItem = (ReferElement) request.getNewObject();
			newShape = refItem.getRealModel();
		}
		// KJH 20100722 e, ReferElement도 넘어올 수 있도록 수정
		int itemState = newShape.getItemState();
		if(itemState == 0) {
			if(!newShape.isIncluded()) {
				// KJH 20100720, UpdateDiagram에 의해 생성될 때 스크립트에 적힌 이름이 쓰이게 하기위해
				if(newShape.getName() == null || newShape.getName().equals("")) {
					newShape.setName(getNewName("New_Behavior"));
				}
				itemState |= ItemState.ITEM_CREATED;
				newShape.setItemState(itemState);
			}
		}

		// KJH 20100825, palette의 getNewObject()에서 실행함
//		if (canUndo() && BehaviorAttribute.REFERENCE == ((BehaviorElement)newShape).getAttribute()) {
//			IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow(); 
//			Shell shell = window.getShell();
//			IEditorInput editorInput = window.getActivePage().getActiveEditor().getEditorInput();
//			BehaviorSelectionDialog dialog = new BehaviorSelectionDialog(shell, editorInput);
//			if (dialog.open() != IDialogConstants.OK_ID)
//				return;
//		}

		Dimension size = request.getSize();
		if(size != null) {
			makeShapeRefer(size.width, size.height);
		}
		else {
			makeShapeRefer(120, 50);	// KJH 20110420, height:24->28
		}
		
		redo();

	}
	
}
