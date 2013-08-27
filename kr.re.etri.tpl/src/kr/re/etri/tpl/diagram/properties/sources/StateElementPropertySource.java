
package kr.re.etri.tpl.diagram.properties.sources;

import java.util.List;

import kr.re.etri.tpl.taskmodel.StateAction;
import kr.re.etri.tpl.taskmodel.StateAttribute;
import kr.re.etri.tpl.taskmodel.StateElement;
import kr.re.etri.tpl.taskmodel.TaskModelPackage;

import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

/**
 * ���� Properties �� �����Ѵ�. 
 * DiagramEditor �Ǵ� View ���� ���õ� �𵨿� ���Ͽ� Properties View ��
 * ���� Propertis �� �����Ѵ�.
 * 
 * @see org.eclipse.ui.views.properties.IPropertySource
 * @see org.eclipse.ui.views.properties.IPropertySource2
 * 
 * @author sfline
 *
 */
public class StateElementPropertySource extends BlockElementPropertySource {
	/**
	 * StateElement �� Attribute �Ӽ� id
	 * TaskModelPackage.STATE_ELEMENT__ATTRIBUTE
	 */
	private static final String ID_ATTRIBUTE = "Attribute";
	/**
	 * StateElement �� Entry �Ӽ� id
	 * TaskModelPackage.STATE_ELEMENT__ENTRY
	 */
	private static final String ID_ENTRY = "Entry";
	/**
	 * StateElement �� Do �Ӽ� id
	 * TaskModelPackage.STATE_ELEMENT__DO
	 */
	private static final String ID_STAY = "Stay";
	/**
	 * StateElement �� Exit �Ӽ� id
	 * TaskModelPackage.STATE_ELEMENT__EXIT
	 */
	private static final String ID_EXIT = "Exit";
	/** �Ӽ��� category */
	private static final String category = "State";

	/**
	 * �־��� model�� ���� PropertySoruce �� �����Ѵ�.
	 * @param model ��
	 */
	public StateElementPropertySource(StateElement model) {
		super(model);
	}

	/**
	 * Object Ÿ���� ���� ���� PropertySource �� �����ϴ� �� Ÿ������ ĳ�����Ѵ�.
	 * @return ��
	 * 
	 * @Override
	 */
	private StateElement getShape() {
		return (StateElement) getModel();
	}

	/**
	 * �𵨿� ���� Property �鿡 ���� Descriptor �� �����Ͽ� �����Ѵ�.
	 * @param descriptors PropertyDescriptor ���
	 * 
	 * @Override
	 */
	public void createPropertyDescriptors(List descriptors) {
		super.createPropertyDescriptors(descriptors);

		PropertyDescriptor propDescr;

		propDescr = new TextPropertyDescriptor(
				TaskModelPackage.STATE_ELEMENT__ATTRIBUTE,
				ID_ATTRIBUTE);
		propDescr.setCategory(category);
		descriptors.add(propDescr);
		
		propDescr = new TextPropertyDescriptor(
				TaskModelPackage.STATE_ELEMENT__ENTRY,
				ID_ENTRY);
		propDescr.setCategory(category);
		descriptors.add(propDescr);
		
		propDescr = new TextPropertyDescriptor(
				TaskModelPackage.STATE_ELEMENT__STAY,
				ID_STAY);
		propDescr.setCategory(category);
		descriptors.add(propDescr);
		
		propDescr = new TextPropertyDescriptor(
				TaskModelPackage.STATE_ELEMENT__EXIT,
				ID_EXIT);
		propDescr.setCategory(category);
		descriptors.add(propDescr);
	}

	/**
	 * �־��� id �� �ش��ϴ� Property ���� set �� �������� ���θ� ��ȯ�Ѵ�.
	 * @param Property Id
	 * @see org.eclipse.ui.views.properties.IPropertySource2#isPropertySet(Object)
	 * @see ItemElementPropertySource#isPropertySet(Object)
	 * 
	 * @Override
	 */
	public boolean isPropertySet(Object id) {
		if(super.isPropertySet(id)) {
			return true;
		}
		
		Integer propId = (Integer) id;
		if(TaskModelPackage.STATE_ELEMENT__ATTRIBUTE == propId.intValue()) {
			return true;
		}
		if(TaskModelPackage.STATE_ELEMENT__ENTRY == propId.intValue()) {
			return true;
		}
		if(TaskModelPackage.STATE_ELEMENT__STAY == propId.intValue()) {
			return true;
		}
		if(TaskModelPackage.STATE_ELEMENT__EXIT == propId.intValue()) {
			return true;
		}

		return false;
	}
	
	/**
	 * �־��� id �� �ش��ϴ� Property �� reset �� �������� ���θ� ��ȯ�Ѵ�.
	 * @param Property Id
	 * 
	 * @see org.eclipse.ui.views.properties.IPropertySource2#isPropertyResettable(Object)
	 * @see ItemElementPropertySource#isPropertyResettable(Object)
	 * 
	 * @Override
	 */
	public boolean isPropertyResettable(Object id) {
		Integer propId = (Integer) id;

		return super.isPropertyResettable(id);
	}
	
	/**
	 * �־��� id �� �ش��ϴ� Property ���� reset �Ѵ�.
	 * @param Property Id
	 * 
	 * @see org.eclipse.ui.views.properties.IPropertySource2#resetPropertyValue(Object)
	 * @see ItemElementPropertySource#resetPropertyValue(Object)
	 * 
	 * @Override
	 */
	public void resetPropertyValue(Object id) {
		Integer propId = (Integer) id;

		super.resetPropertyValue(id);
	}

	/**
	 * �־��� id �� �ش��ϴ� Property ���� ��ȯ�Ѵ�.
	 * @param Property Id
	 * 
	 * @see org.eclipse.ui.views.properties.IPropertySource2#getPropertyValue(Object)
	 * @see ItemElementPropertySource#getPropertyValue(Object)
	 * 
	 * @Override
	 */
	public Object getPropertyValue(Object id) {
		Integer propId = (Integer) id;
		StateElement item = getShape();

		if(TaskModelPackage.STATE_ELEMENT__ATTRIBUTE == propId.intValue()) {
			StateAttribute attr = item.getAttribute();
			if(attr == null) {
				return "";
			}

			return attr;
		}
		if(TaskModelPackage.STATE_ELEMENT__ENTRY == propId.intValue()) {
			StateAction action = item.getEntry();
			if(action == null) {
				return "";
			}

			return action;
		}
		if(TaskModelPackage.STATE_ELEMENT__STAY == propId.intValue()) {
			StateAction action = item.getStay();
			if(action == null) {
				return "";
			}

			return action;
		}
		if(TaskModelPackage.STATE_ELEMENT__EXIT == propId.intValue()) {
			StateAction action = item.getExit();
			if(action == null) {
				return "";
			}

			return action;
		}

		return super.getPropertyValue(id);
	}

	/**
	 * �־��� id �� �ش��ϴ� Property ���� �����Ѵ�.
	 * @param Property Id
	 * 
	 * @see org.eclipse.ui.views.properties.IPropertySource2#setPropertyValue(Object, Object)
	 * @see ItemElementPropertySource#setPropertyValue(Object)
	 * 
	 * @Override
	 */
	public void setPropertyValue(Object id, Object value) {
		Integer propId = (Integer) id;
		StateElement item = getShape();

		if(TaskModelPackage.STATE_ELEMENT__ATTRIBUTE == propId.intValue()) {
			item.setAttribute((StateAttribute)value);
			return;
		}
		if(TaskModelPackage.STATE_ELEMENT__ENTRY == propId.intValue()) {
			item.setEntry((StateAction)value);
			return;
		}
		if(TaskModelPackage.STATE_ELEMENT__STAY == propId.intValue()) {
			item.setStay((StateAction)value);
			return;
		}
		if(TaskModelPackage.STATE_ELEMENT__EXIT == propId.intValue()) {
			item.setExit((StateAction)value);
			return;
		}

		super.setPropertyValue(id, value);
	}
}
