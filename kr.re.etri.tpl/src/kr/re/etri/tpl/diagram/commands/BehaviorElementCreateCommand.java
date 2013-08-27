package kr.re.etri.tpl.diagram.commands;

import kr.re.etri.tpl.diagram.util.ItemState;
import kr.re.etri.tpl.taskmodel.BehaviorElement;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.ReferElement;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.requests.CreateRequest;

/**
 * �θ� �� ��ҿ� ���ο� TaskElement �� �����ϴ� command Ŭ�����̴�.
 * �� command �� Undo/Redo �� �����ϴ�.
 * @author sfline
 */
public class BehaviorElementCreateCommand extends ShapeElementCreateCommand {
	
	/**
	 * ModelDiagram �� ���ο� TaskElement �� �����ϴ� command �� �����Ѵ�.
	 * @param parent ���ο� TaskElement �� �߰��� �θ� ��
	 * @param req     ���ο� TaskElement ���� ��û
	 * @throws IllegalArgumentException ���� parameter �� null �̰ų� request ��
	 * 							���ο� TaskElement instance �� �������� ������ �߻��Ѵ�.
	 */
	public BehaviorElementCreateCommand(ItemElement parentModel, CreateRequest req, Rectangle constraint) {
		super(parentModel, req, constraint);

		setLabel("behavior creation");
	}
	
	/**
	 * �� Command �� �����Ѵ�.
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	public void execute() {
	
		// request �κ��� ���� ������ TaskElement �� ȹ���Ѵ�.
		// ���ο� Instance �� pallet �� �޴� �������� ���Ͽ� TaskModelFactory ��
		// �����ȴ�.
		// KJH 20100722 s, ReferElement�� �Ѿ�� �� �ֵ��� ����
		if (request.getNewObject() instanceof BehaviorElement) {
			newShape = (BehaviorElement) request.getNewObject();
		}
		else {
			refItem = (ReferElement) request.getNewObject();
			newShape = refItem.getRealModel();
		}
		// KJH 20100722 e, ReferElement�� �Ѿ�� �� �ֵ��� ����
		int itemState = newShape.getItemState();
		if(itemState == 0) {
			if(!newShape.isIncluded()) {
				// KJH 20100720, UpdateDiagram�� ���� ������ �� ��ũ��Ʈ�� ���� �̸��� ���̰� �ϱ�����
				if(newShape.getName() == null || newShape.getName().equals("")) {
					newShape.setName(getNewName("New_Behavior"));
				}
				itemState |= ItemState.ITEM_CREATED;
				newShape.setItemState(itemState);
			}
		}

		// KJH 20100825, palette�� getNewObject()���� ������
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
