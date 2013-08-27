package kr.re.etri.tpl.diagram.properties.sources;

import java.util.ArrayList;
import java.util.List;

import kr.re.etri.tpl.diagram.util.PropertyContainer;
import kr.re.etri.tpl.taskmodel.BlockElement;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.TaskModelPackage;

import org.eclipse.emf.common.util.EList;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

public class BlockElementPropertySource extends ItemElementPropertySource {

	/**
	 * BlockElement �� Statements �Ӽ� id
	 * TaskModelPackage.BLOCK_ELEMENT_STATEMENTS
	 */
	private static final String ID_STATEMENTS = "Statements";	// KJH 20101202
	/** �Ӽ��� category */
	private static final String category = "Block";

	/**
	 * �־��� model�� ���� PropertySoruce �� �����Ѵ�.
	 * @param model ��
	 */
	public BlockElementPropertySource(ItemElement model) {
		super(model);
	}

	/**
	 * Object Ÿ���� ���� ���� PropertySource �� �����ϴ� �� Ÿ������ ĳ�����Ѵ�.
	 * @return ��
	 * 
	 * @Override
	 */
	private BlockElement getShape() {
		return (BlockElement) getModel();
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
		// KJH 20110602 s, statements
		propDescr = new TextPropertyDescriptor(
				TaskModelPackage.BLOCK_ELEMENT__STATEMENTS + TaskModelPackage.ITEM_ELEMENT_FEATURE_COUNT,
				ID_STATEMENTS);
		propDescr.setCategory(category);
		descriptors.add(propDescr);
		// KJH 20110602 e, statemenst
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
		if (TaskModelPackage.BLOCK_ELEMENT__STATEMENTS + TaskModelPackage.ITEM_ELEMENT_FEATURE_COUNT == propId.intValue()) {
			return true;	// KJH 20110602, add
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
		BlockElement item = getShape();

		if(TaskModelPackage.BLOCK_ELEMENT__STATEMENTS+TaskModelPackage.ITEM_ELEMENT_FEATURE_COUNT == propId.intValue()) {
			EList list = item.getStatements();
			if(list == null) {
				return new ArrayList();
			}
			return list;
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
		BlockElement item = getShape();

		if(value instanceof PropertyContainer) {
			PropertyContainer property = (PropertyContainer)value;
	
			int type = property.getType();
			int featureId = property.getFeatureId();
			List<String> newValues = new ArrayList<String>();
			List<String> oldValues = new ArrayList<String>();
			switch(type) {
			case PropertyContainer.SET:
				switch(featureId) {
				default:
					super.setPropertyValue(id, value);
					break;
				}
				break;
			case PropertyContainer.ADD:
			case PropertyContainer.ADDALL:
				if (property.getNewValue() instanceof List) {
					newValues.addAll((List)property.getNewValue());
				} else if (property.getNewValue() instanceof String) {
					newValues.add((String) property.getNewValue());
				}
				
				switch(featureId) {
				case TaskModelPackage.BLOCK_ELEMENT__STATEMENTS + TaskModelPackage.ITEM_ELEMENT_FEATURE_COUNT:	// KJH 20110602 s, statements
					if (property.isUndo) {
						item.getStatements().removeAll(newValues);
					}
					else {
						item.getStatements().addAll(newValues);
					}
					break;
				default:
					super.setPropertyValue(id, value);
					break;
				}
				break;
			case PropertyContainer.REMOVE:
			case PropertyContainer.REMOVEALL:
				if (property.getOldValue() instanceof List) {
					oldValues.addAll((List)property.getOldValue());
				} else if (property.getOldValue() instanceof String) {
					oldValues.add((String) property.getOldValue());
				}
				
				switch(featureId) {
				case TaskModelPackage.BLOCK_ELEMENT__STATEMENTS + TaskModelPackage.ITEM_ELEMENT_FEATURE_COUNT:	// KJH 20110602 s, statements
					if (property.isUndo) {
						item.getStatements().addAll(oldValues);
					}
					else {
						item.getStatements().removeAll(oldValues);
					}
					break;
				default:
					super.setPropertyValue(id, value);
					break;
				}
				break;
			case PropertyContainer.REPLACEALL:
				if (property.getNewValue() instanceof List) {
					newValues.addAll((List)property.getNewValue());
				} else if (property.getNewValue() instanceof String) {
					newValues.add((String) property.getNewValue());
				}
				
				if (property.getOldValue() instanceof List) {
					oldValues.addAll((List)property.getOldValue());
				} else if (property.getOldValue() instanceof String) {
					oldValues.add((String) property.getOldValue());
				}
				
				switch(featureId) {
				case TaskModelPackage.BLOCK_ELEMENT__STATEMENTS + TaskModelPackage.ITEM_ELEMENT_FEATURE_COUNT:	// KJH 20110602 s, statements
					if (property.isUndo) {
						item.getStatements().removeAll(newValues);
						item.getStatements().addAll(oldValues);
					}
					else {
						item.getStatements().removeAll(oldValues);
						item.getStatements().addAll(newValues);
					}
					break;
				default:
					super.setPropertyValue(id, value);
					break;
				}
				break;
			default:
				super.setPropertyValue(id, value);
				break;
			}

			return;
		}

		super.setPropertyValue(id, value);
	}
}
