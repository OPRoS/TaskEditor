
package kr.re.etri.tpl.diagram.properties.sources;

import java.util.List;

import kr.re.etri.tpl.taskmodel.StateAction;
import kr.re.etri.tpl.taskmodel.StateAttribute;
import kr.re.etri.tpl.taskmodel.StateElement;
import kr.re.etri.tpl.taskmodel.TaskModelPackage;

import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

/**
 * 모델의 Properties 를 제공한다. 
 * DiagramEditor 또는 View 에서 선택된 모델에 대하여 Properties View 에
 * 모델의 Propertis 를 제공한다.
 * 
 * @see org.eclipse.ui.views.properties.IPropertySource
 * @see org.eclipse.ui.views.properties.IPropertySource2
 * 
 * @author sfline
 *
 */
public class StateElementPropertySource extends BlockElementPropertySource {
	/**
	 * StateElement 의 Attribute 속성 id
	 * TaskModelPackage.STATE_ELEMENT__ATTRIBUTE
	 */
	private static final String ID_ATTRIBUTE = "Attribute";
	/**
	 * StateElement 의 Entry 속성 id
	 * TaskModelPackage.STATE_ELEMENT__ENTRY
	 */
	private static final String ID_ENTRY = "Entry";
	/**
	 * StateElement 의 Do 속성 id
	 * TaskModelPackage.STATE_ELEMENT__DO
	 */
	private static final String ID_STAY = "Stay";
	/**
	 * StateElement 의 Exit 속성 id
	 * TaskModelPackage.STATE_ELEMENT__EXIT
	 */
	private static final String ID_EXIT = "Exit";
	/** 속성의 category */
	private static final String category = "State";

	/**
	 * 주어진 model에 대한 PropertySoruce 를 생성한다.
	 * @param model 모델
	 */
	public StateElementPropertySource(StateElement model) {
		super(model);
	}

	/**
	 * Object 타입의 모델을 현재 PropertySource 가 관리하는 모델 타입으로 캐스팅한다.
	 * @return 모델
	 * 
	 * @Override
	 */
	private StateElement getShape() {
		return (StateElement) getModel();
	}

	/**
	 * 모델에 대한 Property 들에 대한 Descriptor 를 생성하여 제공한다.
	 * @param descriptors PropertyDescriptor 목록
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
	 * 주어진 id 에 해당하는 Property 값을 set 이 가능한지 여부를 반환한다.
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
	 * 주어진 id 에 해당하는 Property 가 reset 이 가능한지 여부를 반환한다.
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
	 * 주어진 id 에 해당하는 Property 값을 reset 한다.
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
	 * 주어진 id 에 해당하는 Property 값을 반환한다.
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
	 * 주어진 id 에 해당하는 Property 값을 변경한다.
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
