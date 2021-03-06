
package kr.re.etri.tpl.diagram.properties.sources;

import java.util.List;

import kr.re.etri.tpl.taskmodel.IncludedElement;
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
public class IncludedElementPropertySource extends ItemElementPropertySource {
	/**
	 * IncludedElement 의 Path 속성 id
	 * TaskModelPackage.INCLUDED_ELEMENT__INCLUDE_PATH
	 */
	private static final String ID_PATH = "Path";
	/**
	 * IncludedElement 의 Items 속성 id
	 * TaskModelPackage.INCLUDED_ELEMENT__ITEMS
	 */
	private static final String ID_ITEMS = "Items";
	/** 속성의 category */
	private static final String category = "Included";

	/**
	 * 주어진 model에 대한 PropertySoruce 를 생성한다.
	 * @param model 모델
	 */
	public IncludedElementPropertySource(IncludedElement model) {
		super(model);
	}

	/**
	 * Object 타입의 모델을 현재 PropertySource 가 관리하는 모델 타입으로 캐스팅한다.
	 * @return 모델
	 * 
	 * @Override
	 */
	private IncludedElement getShape() {
		return (IncludedElement) getModel();
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
				TaskModelPackage.INCLUDED_ELEMENT__INCLUDE_PATH,
				ID_PATH);
		propDescr.setCategory(category);
		propDescr.setAlwaysIncompatible(true);
		descriptors.add(propDescr);

		propDescr = new TextPropertyDescriptor(
				TaskModelPackage.INCLUDED_ELEMENT__ITEMS,
				ID_ITEMS);
		propDescr.setCategory(category);
		propDescr.setAlwaysIncompatible(true);
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
		if(TaskModelPackage.INCLUDED_ELEMENT__INCLUDE_PATH == propId.intValue()) {
			return true;
		}
		if(TaskModelPackage.INCLUDED_ELEMENT__ITEMS == propId.intValue()) {
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

		if(TaskModelPackage.INCLUDED_ELEMENT__INCLUDE_PATH == propId.intValue()) {
			return false;
		}

		if(TaskModelPackage.INCLUDED_ELEMENT__ITEMS == propId.intValue()) {
			return false;
		}

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
		IncludedElement item = getShape();

		if(TaskModelPackage.INCLUDED_ELEMENT__INCLUDE_PATH == propId.intValue()) {
			return item.getIncludePath();
		}

		if(TaskModelPackage.INCLUDED_ELEMENT__ITEMS == propId.intValue()) {
			return item.getItems();
		}

		return  super.getPropertyValue(id);
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

		IncludedElement item = getShape();

		if (TaskModelPackage.INCLUDED_ELEMENT__INCLUDE_PATH == propId.intValue()) {
			item.setIncludePath(value.toString());
			return;
		}

		if (TaskModelPackage.INCLUDED_ELEMENT__ITEMS == propId.intValue()) {
//			item.setItems(value);
			return;
		}
		super.setPropertyValue(id, value);
	}
	
}
