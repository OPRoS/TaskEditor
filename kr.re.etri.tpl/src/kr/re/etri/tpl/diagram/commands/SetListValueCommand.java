package kr.re.etri.tpl.diagram.commands;

import kr.re.etri.tpl.diagram.util.PropertyContainer;

import org.eclipse.gef.commands.Command;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.IPropertySource2;

/**
 * List 데이터를 SET, ADD_MAY, REMOVE_MANY 하기 위한 command 클래스이다.
 * 이 command 는 Undo/Redo 가 가능하다.
 * @author sfline
 *
 */
public class SetListValueCommand extends Command {
	/** property 을 제공하는 IPropertySource */
	protected Object propertyValue;
	/** property 이름 */
	protected Object propertyName;
	/** 변경 전 Property 값 */
	protected Object undoValue;
	/** Undo 시 Property 값 reset 여부 flag */
	protected boolean resetOnUndo;
	/** property 를 변경할 IPropertySource */
	protected IPropertySource target;

	/**
	 * List 데이터를 변경할 command 를 생성한다.
	 */
	public SetListValueCommand() {
	}

	/**
	 * List 데이터를 변경할 command 를 생성한다.
	 * @param label command label
	 */
	public SetListValueCommand(String label) {
		super(label);

//		super(MessageFormat.format(GEFMessages.SetPropertyValueCommand_Label,
//				new Object[]{label}).trim());
	}

	/**
	 * 이 command 의 실행 가능 여부를 반환한다.
	 */
	public boolean canExecute() {
		return true;
	}
	
	/**
	 * 이 command 를 실행한다.
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
	 * 이 command 가 실행되어 Property 를 수정 할 target 을 반환한다.
	 * @return 실행 대상 IPropertySource
	 */
	public IPropertySource getTarget() {
		return target;
	}
	
	/**
	 * 이 command 가 실행되어 Property 를 수정 할 target 을 설정한다. 
	 * @param aTarget
	 */
	public void setTarget(IPropertySource aTarget) {
		target = aTarget;
	}
	
	/**
	 * 이 command 를 Redo 한다.
	 */
	public void redo() {
		if(propertyValue instanceof PropertyContainer) {
			PropertyContainer property = (PropertyContainer)propertyValue;
			property.setUndo(false);
		}
		
		execute();
	}
	
	/**
	 * 이 command 가 실행되어 수정될 Property 의 이름을 설정한다.
	 * @param pName Property 이름
	 */
	public void setPropertyId(Object pName) {
		propertyName = pName;
	}
	
	/**
	 * 이 command 가 실행되어 수정될 Proeprty 의 값을 설정한다.
	 * @param val Property Value
	 */
	public void setPropertyValue(Object val) {
		propertyValue = val;
	}
	
	/**
	 * 이 command 를 Undo 한다.
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
