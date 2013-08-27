
package kr.re.etri.tpl.diagram.properties.sources;

import java.util.ArrayList;
import java.util.List;

import kr.re.etri.tpl.diagram.util.PropertyContainer;
import kr.re.etri.tpl.taskmodel.StateAction;
import kr.re.etri.tpl.taskmodel.TaskModelPackage;

import org.eclipse.emf.common.util.EList;
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
public class StateActionPropertySource extends BlockElementPropertySource {
	/**
	 * StateAction �� Statements �Ӽ� id
	 * TaskModelPackage.STATE_ACTION__STATEMENTS
	 */
	private static final String ID_STATEMENTS = "Statements";
	/** �Ӽ��� category */
	private static final String category = "StateAction";

	/**
	 * �־��� model�� ���� PropertySoruce �� �����Ѵ�.
	 * @param model ��
	 */
	public StateActionPropertySource(StateAction model) {
		super(model);
	}

	/**
	 * Object Ÿ���� ���� ���� PropertySource �� �����ϴ� �� Ÿ������ ĳ�����Ѵ�.
	 * @return ��
	 * 
	 * @Override
	 */
	private StateAction getShape() {
		return (StateAction) getModel();
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
//				TaskModelPackage.STATE_ACTION__STATEMENTS,
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
		if(TaskModelPackage.STATE_ACTION__STATEMENTS == propId.intValue()) {
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

		if(TaskModelPackage.STATE_ACTION__STATEMENTS == propId.intValue()) {
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
		StateAction item = getShape();

		if(TaskModelPackage.STATE_ACTION__STATEMENTS == propId.intValue()) {
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
		StateAction item = getShape();

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
//			case PropertyContainer.ADD:
//				switch(featureId) {
//				case TaskModelPackage.STATE_ACTION__STATEMENTS:
//					if(property.getNewValue() != null) {
//						item.getStatements().add(property.getNewValue().toString());
//					}
//					break;
//				default:
//					super.setPropertyValue(id, value);
//					break;
//				}
//				break;
//			case PropertyContainer.ADDALL:
//				switch(featureId) {
//				case TaskModelPackage.STATE_ACTION__STATEMENTS:
//					if(property.getNewValue() != null) {
//						item.getStatements().addAll((List<String>)property.getNewValue());
//					}
//					break;
//				default:
//					super.setPropertyValue(id, value);
//					break;
//				}
//				break;
//			case PropertyContainer.REMOVE:
//				switch(featureId) {
//				case TaskModelPackage.STATE_ACTION__STATEMENTS:
//					if(property.getNewValue() != null) {
//						item.getStatements().remove(property.getOldValue().toString());
//					}
//					break;
//				default:
//					super.setPropertyValue(id, value);
//					break;
//				}
//				break;
//			case PropertyContainer.REMOVEALL:
//				switch(featureId) {
//				case TaskModelPackage.STATE_ACTION__STATEMENTS:
//					if(property.getNewValue() != null) {
//						item.getStatements().removeAll((List<String>)property.getOldValue());
//					}
//					break;
//				default:
//					super.setPropertyValue(id, value);
//					break;
//				}
//				break;
//			case PropertyContainer.REPLACEALL:
//				switch(featureId) {
//				case TaskModelPackage.STATE_ACTION__STATEMENTS:
//					if(property.getOldValue() != null) {
//						item.getStatements().removeAll((List<String>)property.getOldValue());
//					}
//					if(property.getNewValue() != null) {
//						item.getStatements().addAll((List<String>)property.getNewValue());
//					}
//					break;
//				default:
//					super.setPropertyValue(id, value);
//					break;
//				}
//				break;
			case PropertyContainer.ADD:
			case PropertyContainer.ADDALL:
				if (property.getNewValue() instanceof List) {
					newValues.addAll((List)property.getNewValue());
				} else if (property.getNewValue() instanceof String) {
					newValues.add((String) property.getNewValue());
				}
				
				switch(featureId) {
				case TaskModelPackage.STATE_ACTION__STATEMENTS:
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
				case TaskModelPackage.STATE_ACTION__STATEMENTS:
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
				case TaskModelPackage.STATE_ACTION__STATEMENTS:
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
