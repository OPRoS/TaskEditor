
package kr.re.etri.tpl.diagram.properties.sources;

import java.util.List;

import kr.re.etri.tpl.taskmodel.IncludedElement;
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
public class IncludedElementPropertySource extends ItemElementPropertySource {
	/**
	 * IncludedElement �� Path �Ӽ� id
	 * TaskModelPackage.INCLUDED_ELEMENT__INCLUDE_PATH
	 */
	private static final String ID_PATH = "Path";
	/**
	 * IncludedElement �� Items �Ӽ� id
	 * TaskModelPackage.INCLUDED_ELEMENT__ITEMS
	 */
	private static final String ID_ITEMS = "Items";
	/** �Ӽ��� category */
	private static final String category = "Included";

	/**
	 * �־��� model�� ���� PropertySoruce �� �����Ѵ�.
	 * @param model ��
	 */
	public IncludedElementPropertySource(IncludedElement model) {
		super(model);
	}

	/**
	 * Object Ÿ���� ���� ���� PropertySource �� �����ϴ� �� Ÿ������ ĳ�����Ѵ�.
	 * @return ��
	 * 
	 * @Override
	 */
	private IncludedElement getShape() {
		return (IncludedElement) getModel();
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
		if(TaskModelPackage.INCLUDED_ELEMENT__INCLUDE_PATH == propId.intValue()) {
			return true;
		}
		if(TaskModelPackage.INCLUDED_ELEMENT__ITEMS == propId.intValue()) {
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

		if(TaskModelPackage.INCLUDED_ELEMENT__INCLUDE_PATH == propId.intValue()) {
			return false;
		}

		if(TaskModelPackage.INCLUDED_ELEMENT__ITEMS == propId.intValue()) {
			return false;
		}

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
