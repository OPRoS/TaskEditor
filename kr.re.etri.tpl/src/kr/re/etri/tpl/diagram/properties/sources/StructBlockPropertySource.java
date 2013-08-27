package kr.re.etri.tpl.diagram.properties.sources;

import java.util.List;

import kr.re.etri.tpl.taskmodel.StructBlockElement;
import kr.re.etri.tpl.taskmodel.TaskModelPackage;

import org.eclipse.emf.common.util.EList;
import org.eclipse.ui.views.properties.PropertyDescriptor;

// KJH 20110209, new
public class StructBlockPropertySource extends BlockElementPropertySource {
	/**
	 * StructBlockElement �� Statements �Ӽ� id
	 * TaskModelPackage.STRUCT_BLOCK_ELEMENT__STATEMENTS
	 */
	private static final String ID_STATEMENTS = "Statements";
	/** �Ӽ��� category */
	private static final String category = "StructBlock";

	/**
	 * �־��� model�� ���� PropertySoruce �� �����Ѵ�.
	 * @param model ��
	 */
	public StructBlockPropertySource(StructBlockElement model) {
		super(model);
	}

	/**
	 * Object Ÿ���� ���� ���� PropertySource �� �����ϴ� �� Ÿ������ ĳ�����Ѵ�.
	 * @return ��
	 * 
	 * @Override
	 */
	private StructBlockElement getShape() {
		return (StructBlockElement) getModel();
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

//		propDescr = new TextPropertyDescriptor(
//				TaskModelPackage.STRUCT_BLOCK_ELEMENT__STATEMENTS,
//				ID_STATEMENTS);
//		propDescr.setCategory(category);
//		propDescr.setAlwaysIncompatible(true);
//
//		descriptors.add(propDescr);
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
		if(TaskModelPackage.STRUCT_BLOCK_ELEMENT__STATEMENTS == propId.intValue()) {
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

		if(TaskModelPackage.STRUCT_BLOCK_ELEMENT__STATEMENTS == propId.intValue()) {
			return true;
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
		StructBlockElement item = getShape();

		if(TaskModelPackage.STRUCT_BLOCK_ELEMENT__STATEMENTS == propId.intValue()) {
			EList<String> value = item.getStatements();
			return value;
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
		StructBlockElement item = getShape();

//		if(value instanceof PropertyContainer) {
//			PropertyContainer property = (PropertyContainer)value;
//
//			int type = property.getType();
//			int featureId = property.getFeatureId();
//			
//			List<String> newValues = new ArrayList<String>();
//			List<String> oldValues = new ArrayList<String>();
//			if (property.isUndo) {
//				if (property.getNewValue() instanceof List<?>) {
//					oldValues.addAll((List<String>)property.getNewValue());
//				} else if (property.getNewValue() instanceof String) {
//					oldValues.add(property.getNewValue().toString());
//				}
//				if (property.getOldValue() instanceof List<?>) {
//					newValues.addAll((List<String>)property.getOldValue());
//				} else if (property.getOldValue() instanceof String) {
//					newValues.add(property.getOldValue().toString());
//				}
//			} else {
//				if (property.getNewValue() instanceof List<?>) {
//					newValues.addAll((List<String>)property.getNewValue());
//				} else if (property.getNewValue() instanceof String) {
//					newValues.add(property.getNewValue().toString());
//				}
//				if (property.getOldValue() instanceof List<?>) {
//					oldValues.addAll((List<String>)property.getOldValue());
//				} else if (property.getOldValue() instanceof String) {
//					oldValues.add(property.getOldValue().toString());
//				}
//			}
//			
//			switch(type) {
//			case PropertyContainer.SET:
//				switch(featureId) {
//				default:
//					super.setPropertyValue(id, value);
//					break;
//				}
//				break;
//			case PropertyContainer.ADD:
//			case PropertyContainer.ADDALL:
//				switch(featureId) {
//				case TaskModelPackage.STRUCT_BLOCK_ELEMENT__STATEMENTS:
//					item.getStatements().addAll(newValues);
//					break;
//				default:
//					super.setPropertyValue(id, value);
//					break;
//				}
//				break;
//			case PropertyContainer.REMOVE:
//			case PropertyContainer.REMOVEALL:
//				switch(featureId) {
//				case TaskModelPackage.STRUCT_BLOCK_ELEMENT__STATEMENTS:
//					item.getStatements().remove(oldValues);
//					break;
//				default:
//					super.setPropertyValue(id, value);
//					break;
//				}
//				break;
//			case PropertyContainer.REPLACEALL:
//				switch(featureId) {
//				case TaskModelPackage.STRUCT_BLOCK_ELEMENT__STATEMENTS:
//					item.getStatements().removeAll(oldValues);
//					item.getStatements().addAll(newValues);
//					break;
//				default:
//					super.setPropertyValue(id, value);
//					break;
//				}
//				break;
//			}
//			
//			return;
//		}

		super.setPropertyValue(id, value);
	}
}
