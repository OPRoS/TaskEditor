package kr.re.etri.tpl.diagram.commands;

import kr.re.etri.tpl.diagram.util.PropertyContainer;

import org.eclipse.gef.commands.Command;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.IPropertySource2;

/**
 * List �����͸� SET, ADD_MAY, REMOVE_MANY �ϱ� ���� command Ŭ�����̴�.
 * �� command �� Undo/Redo �� �����ϴ�.
 * @author sfline
 *
 */
public class SetListValueCommand extends Command {
	/** property �� �����ϴ� IPropertySource */
	protected Object propertyValue;
	/** property �̸� */
	protected Object propertyName;
	/** ���� �� Property �� */
	protected Object undoValue;
	/** Undo �� Property �� reset ���� flag */
	protected boolean resetOnUndo;
	/** property �� ������ IPropertySource */
	protected IPropertySource target;

	/**
	 * List �����͸� ������ command �� �����Ѵ�.
	 */
	public SetListValueCommand() {
	}

	/**
	 * List �����͸� ������ command �� �����Ѵ�.
	 * @param label command label
	 */
	public SetListValueCommand(String label) {
		super(label);

//		super(MessageFormat.format(GEFMessages.SetPropertyValueCommand_Label,
//				new Object[]{label}).trim());
	}

	/**
	 * �� command �� ���� ���� ���θ� ��ȯ�Ѵ�.
	 */
	public boolean canExecute() {
		return true;
	}
	
	/**
	 * �� command �� �����Ѵ�.
	 */
	public void execute() {
		/*
		 * Fix for Bug# 54250
		 * IPropertySource.isPropertySet(String) returns false both when there is no default 
		 * value, and when there is a default value and the property is set to that value.
		 * To correctly determine if a reset should be done during undo, we compare the
		 * return value of isPropertySet(String) before and after setPropertyValue(...) is
		 * invoked.  If they are different (it must have been false before and true after --
		 * it cannot be the other way around), then that means we need to reset.
		 */
		boolean wasPropertySet = getTarget().isPropertySet(propertyName);
		undoValue = getTarget().getPropertyValue(propertyName);
		if (undoValue instanceof IPropertySource)
			undoValue = ((IPropertySource)undoValue).getEditableValue();
		if (propertyValue instanceof IPropertySource)
			propertyValue = ((IPropertySource)propertyValue).getEditableValue();
		getTarget().setPropertyValue(propertyName, propertyValue);
		if (getTarget() instanceof IPropertySource2)
			resetOnUndo = !wasPropertySet
					&& ((IPropertySource2)getTarget()).isPropertyResettable(propertyName);
		else
			resetOnUndo = !wasPropertySet && getTarget().isPropertySet(propertyName);
		if (resetOnUndo)
			undoValue = null;
	}
	
	/**
	 * �� command �� ����Ǿ� Property �� ���� �� target �� ��ȯ�Ѵ�.
	 * @return ���� ��� IPropertySource
	 */
	public IPropertySource getTarget() {
		return target;
	}
	
	/**
	 * �� command �� ����Ǿ� Property �� ���� �� target �� �����Ѵ�. 
	 * @param aTarget
	 */
	public void setTarget(IPropertySource aTarget) {
		target = aTarget;
	}
	
	/**
	 * �� command �� Redo �Ѵ�.
	 */
	public void redo() {
		if(propertyValue instanceof PropertyContainer) {
			PropertyContainer property = (PropertyContainer)propertyValue;
			property.setUndo(false);
		}
		
		execute();
	}
	
	/**
	 * �� command �� ����Ǿ� ������ Property �� �̸��� �����Ѵ�.
	 * @param pName Property �̸�
	 */
	public void setPropertyId(Object pName) {
		propertyName = pName;
	}
	
	/**
	 * �� command �� ����Ǿ� ������ Proeprty �� ���� �����Ѵ�.
	 * @param val Property Value
	 */
	public void setPropertyValue(Object val) {
		propertyValue = val;
	}
	
	/**
	 * �� command �� Undo �Ѵ�.
	 */
	public void undo() {
		if(propertyValue instanceof PropertyContainer) {
			PropertyContainer property = (PropertyContainer)propertyValue;
			property.setUndo(true);
			getTarget().setPropertyValue(propertyName, property);
		}
		else {
			if (resetOnUndo)
				getTarget().resetPropertyValue(propertyName);
			else
				getTarget().setPropertyValue(propertyName, undoValue);
		}
	}
}
