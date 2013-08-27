
package kr.re.etri.tpl.diagram.properties.sources;

import java.util.ArrayList;
import java.util.List;

import kr.re.etri.tpl.diagram.util.PropertyContainer;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.ReferElement;
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
public class ItemElementPropertySource extends AbstractPropertySource {
	/**
	 * ItemElement �� name �Ӽ� id
	 * TaskModelPackage.MODEL_ELEMENT__NAME
	 */
	private static final String ID_NAME = "name";
	/**
	 * ItemElement �� description �Ӽ� id
	 * TaskModelPackage.LINE_MODEL__DESCRIPTION
	 */
	private static final String ID_DESCRIPTION = "Description";
	/** �Ӽ��� category */
	private static final String category = "Item";

	/**
	 * �־��� model�� ���� PropertySoruce �� �����Ѵ�.
	 * @param model ��
	 */
	public ItemElementPropertySource(ItemElement model) {
		super(model);
	}

	/**
	 * Object Ÿ���� ���� ���� PropertySource �� �����ϴ� �� Ÿ������ ĳ�����Ѵ�.
	 * @return ��
	 * 
	 * @Override
	 */
	private ItemElement getShape() {
		return (ItemElement) getModel();
	}

	/**
	 * �𵨿� ���� Property �鿡 ���� Descriptor �� �����Ͽ� �����Ѵ�.
	 * @param descriptors PropertyDescriptor ���
	 */
	public void createPropertyDescriptors(List descriptors) {
		PropertyDescriptor propDescr;

		propDescr = new TextPropertyDescriptor(
				TaskModelPackage.ITEM_ELEMENT__NAME,
				ID_NAME);
		propDescr.setCategory(category);
		propDescr.setAlwaysIncompatible(true);
		descriptors.add(propDescr);
		
		propDescr = new TextPropertyDescriptor(
				TaskModelPackage.ITEM_ELEMENT__DESCRIPTION,
				ID_DESCRIPTION);
		propDescr.setCategory(category);
		propDescr.setAlwaysIncompatible(true);
		descriptors.add(propDescr);
	}

	/**
	 * �־��� id �� �ش��ϴ� Property ���� set �� �������� ���θ� ��ȯ�Ѵ�.
	 * @param Property Id
	 * @see org.eclipse.ui.views.properties.IPropertySource2#isPropertySet(Object)
	 * 
	 */
	public boolean isPropertySet(Object id) {
		if(super.isPropertySet(id)) {
			return true;
		}
		
		Integer propId = (Integer) id;
		if(TaskModelPackage.ITEM_ELEMENT__NAME == propId.intValue()) {
			return true;
		}
		else if(TaskModelPackage.ITEM_ELEMENT__DESCRIPTION == propId.intValue()) {
			return true;
		}

		return false;
	}
	
	/**
	 * �־��� id �� �ش��ϴ� Property �� reset �� �������� ���θ� ��ȯ�Ѵ�.
	 * @param Property Id
	 * 
	 * @see org.eclipse.ui.views.properties.IPropertySource2#isPropertyResettable(Object)
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
	 */
	public Object getPropertyValue(Object id) {
		Integer intValue = (Integer) id;
		
		ItemElement item = getShape();

		if(TaskModelPackage.ITEM_ELEMENT__NAME == intValue.intValue()) {
			if(item.getName() == null)
				return "";
			return item.getName();
		}
		if(TaskModelPackage.ITEM_ELEMENT__DESCRIPTION == intValue.intValue()) {
			if(item.getDescription() == null)
				return "";
			return item.getDescription();
		}
		// KJH 20100903 s, reference set
		if (TaskModelPackage.ITEM_ELEMENT__REFERENCES == intValue.intValue()) {
			if (item.getReferences() == null)
				return new ArrayList();
			return item.getReferences();
		}

		return null;
	}


	/**
	 * �־��� id �� �ش��ϴ� Property ���� �����Ѵ�.
	 * @param Property Id
	 * 
	 * @see org.eclipse.ui.views.properties.IPropertySource2#setPropertyValue(Object, Object)
	 */
	public void setPropertyValue(Object id, Object value) {
		Integer propId = (Integer) id;
		ItemElement item = getShape();

		if(value instanceof PropertyContainer) {
			PropertyContainer property = (PropertyContainer)value;
	
			int type = property.getType();
			int featureId = property.getFeatureId();
			switch(type) {
			case PropertyContainer.SET:
				switch(featureId) {
				case TaskModelPackage.ITEM_ELEMENT__NAME:
					if(property.isUndo) {
						item.setName((String)property.getOldValue());
					}
					else {
						item.setName((String)property.getNewValue());
					}
					break;
				case TaskModelPackage.ITEM_ELEMENT__DESCRIPTION:
					if(property.isUndo) {
						item.setDescription((String)property.getOldValue());
					}
					else {
						item.setDescription((String)property.getNewValue());
					}
					break;
				}
				break;
			case PropertyContainer.ADD:
				switch(featureId) {
				// KJH 20100903 s, reference set
				case TaskModelPackage.ITEM_ELEMENT__REFERENCES:
					if (property.isUndo) {
						item.getReferences().remove(property.getNewValue());
					}
					else {
						ReferElement refer = (ReferElement)property.getNewValue();
						refer.setRealModel(item);
						item.getReferences().add(refer);
					}
					break;
				}
				break;
			case PropertyContainer.REMOVE:
				switch(featureId) {
				// KJH 20100903 s, reference set
				case TaskModelPackage.ITEM_ELEMENT__REFERENCES:
					if (property.isUndo) {
						ReferElement refer = (ReferElement)property.getNewValue();
						refer.setRealModel(item);
						item.getReferences().add(refer);
					}
					else {
						item.getReferences().remove(property.getNewValue());
					}
					break;
				}
				break;
			}

			return;
		}

		if(TaskModelPackage.ITEM_ELEMENT__NAME == propId.intValue()) {
			item.setName((String)value);
		}
		if(TaskModelPackage.ITEM_ELEMENT__DESCRIPTION == propId.intValue()) {
			item.setDescription((String)value);
		}
	}
	
}
