package kr.re.etri.tpl.diagram.commands;

import kr.re.etri.tpl.diagram.util.ItemState;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.ModelElement;
import kr.re.etri.tpl.taskmodel.Symbol;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.requests.CreateRequest;

/**
 * ModelElement �� ���ο� Symbol �� �����ϴ� command Ŭ�����̴�.
 * �� command �� Redo/Undo �� �����ϴ�.
 * @author sfline
 */
public class SymbolCreateCommand extends ItemElementCreateCommand {
	
	/**
	 * ModelElement �� ���ο� Symbol �� �����ϴ� command �� �����Ѵ�.
	 * @param parentModel ���ο� �ڽ� ���� �߰��� �θ� ��
	 * @param req     ���ο� ���� ������ request
	 * @throws IllegalArgumentException ���� parameter �� null �̰ų� request��
	 * 							���ο� �� instance �� �������� ������ �߻��Ѵ�.
	 */
	public SymbolCreateCommand(ItemElement parentModel, CreateRequest req) {
		super(parentModel, req);

		setLabel("symbol creation");
	}
	
	/**
	 * �� command �� �����Ѵ�.
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	public void execute() {
		Point locPt = request.getLocation();

		// request �κ��� ���ο� Symbol ���� ȹ���Ѵ�.
		// Symbol ���� palette-tool���� �������� ������, �޴� �������� ���Ͽ�
		// factory �� ���� �����ȴ�.
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
	 * ���ο� ��(Symbol)�� ModelElement �� �߰��Ѵ�.
	 * �� method �� Redo method ȣ�� �� ����ȴ�.
	 * @Override
	 */
	protected void addToParent(ItemElement newItem) {
		
		// Symbol Parent�� ModelElement���� ������.
		if(parentModel instanceof ModelElement) {
			modelAdded = ((ModelElement)parentModel).getSymbols().add((Symbol)newItem);
			if(modelAdded)	newItem.setParent(parentModel);	// KJH 20100524
		}
	}
	
	/**
	 * ���ο� ��(Symbol)�� ModelElement �κ��� �����Ѵ�.
	 * �� method �� Undo method ȣ�� �� ����ȴ�.
	 * @Override
	 */
	protected void removeFromParent(ItemElement newItem) {
		
		// Symbol Parent�� ModelElement���� ������.
		if(parentModel instanceof ModelElement) {
			((ModelElement)parentModel).getSymbols().remove((Symbol)newItem);
		}
	}
}
