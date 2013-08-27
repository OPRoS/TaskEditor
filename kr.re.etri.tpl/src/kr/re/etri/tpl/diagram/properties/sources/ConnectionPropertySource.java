
package kr.re.etri.tpl.diagram.properties.sources;

import java.util.List;

import kr.re.etri.tpl.diagram.util.PropertyContainer;
import kr.re.etri.tpl.taskmodel.ConnectionElement;
import kr.re.etri.tpl.taskmodel.LinkedElement;
import kr.re.etri.tpl.taskmodel.RelationShip;
import kr.re.etri.tpl.taskmodel.TaskModelPackage;

import org.eclipse.emf.common.util.EList;
import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
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
public class ConnectionPropertySource extends LineElementPropertySource {
	private static final String ID_PRIORITY ="Priority";
	/**
	 * ItemElement �� Source �Ӽ� id
	 * TaskModelPackage.CONNECTION__SOURCE
	 */
	private static final String ID_SOURCE = "Source";
	/**
	 * ItemElement �� Target �Ӽ� id
	 * TaskModelPackage.CONNECTION__TARGET
	 */
	private static final String ID_TARGET = "Target";
	/**
	 * ItemElement �� Relation Ship �Ӽ� id
	 * TaskModelPackage.LINE_MODEL__LINE_STYLE
	 */
	private static final String ID_RELATIONSHIP = "Relation Ship";
	/**
	 * ItemElement �� Condition �Ӽ� id
	 * TaskModelPackage.LINE_MODEL__WIDTH
	 */
	private static final String ID_CONDITION = "Condition";
	/** �Ӽ��� category */
	private static final String category = "Relation";

	/**
	 * �־��� model�� ���� PropertySoruce �� �����Ѵ�.
	 * @param model ��
	 */
	public ConnectionPropertySource(ConnectionElement model) {
		super(model);
	}

	/**
	 * Object Ÿ���� ���� ���� PropertySource �� �����ϴ� �� Ÿ������ ĳ�����Ѵ�.
	 * @return ��
	 * 
	 * @Override
	 */
	private ConnectionElement getShape() {
		return (ConnectionElement) getModel();
	}

	/**
	 * �𵨿� ���� Property �鿡 ���� Descriptor �� �����Ͽ� �����Ѵ�.
	 * @param descriptors PropertyDescriptor ���
	 * 
	 * @Override
	 */
	public void createPropertyDescriptors(List descriptors) {
		super.createPropertyDescriptors(descriptors);

		int idx, itemLen;
		String []values;
		
		PropertyDescriptor propDescr;

		propDescr = new TextPropertyDescriptor(
				TaskModelPackage.CONNECTION_ELEMENT__SOURCE,
				ID_SOURCE);
		propDescr.setCategory(category);
		descriptors.add(propDescr);
		
		propDescr = new TextPropertyDescriptor(
				TaskModelPackage.CONNECTION_ELEMENT__TARGET,
				ID_TARGET);
		propDescr.setCategory(category);
		descriptors.add(propDescr);

		idx = 0;
		itemLen = RelationShip.values().length;
		values = new String[itemLen];
		for(RelationShip relation : RelationShip.VALUES) {
			values[idx++] = relation.getName();
		}

		propDescr = new ComboBoxPropertyDescriptor(
				TaskModelPackage.CONNECTION_ELEMENT__RELATIONSHIP,
				ID_RELATIONSHIP,
				values);
		propDescr.setCategory(category);
		descriptors.add(propDescr);

		propDescr = new TextPropertyDescriptor(
				TaskModelPackage.CONNECTION_ELEMENT__CONDITION,
				ID_CONDITION);
		propDescr.setCategory(category);
		descriptors.add(propDescr);
		
//		values = new String[20];
//		for(int i =1 ; i<21 ; i++){
//			values[i-1]=""+i;
//		}
		propDescr = new TextPropertyDescriptor(TaskModelPackage.CONNECTION_ELEMENT_FEATURE_COUNT, ID_PRIORITY);
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
		if(TaskModelPackage.CONNECTION_ELEMENT__SOURCE == propId.intValue()) {
			return true;
		}
		else if(TaskModelPackage.CONNECTION_ELEMENT__TARGET == propId.intValue()) {
			return true;
		}
		else if(TaskModelPackage.CONNECTION_ELEMENT__RELATIONSHIP == propId.intValue()) {
			return true;
		}
		else if(TaskModelPackage.CONNECTION_ELEMENT__CONDITION == propId.intValue()) {
			return true;
		}
		else if(TaskModelPackage.CONNECTION_ELEMENT_FEATURE_COUNT == propId.intValue()) {

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
		Integer intValue = (Integer) id;
		ConnectionElement conn = getShape();

		if(TaskModelPackage.CONNECTION_ELEMENT__SOURCE == intValue.intValue()) {
			LinkedElement item = conn.getSource();
			System.out.println("CONNECTION_ELEMENT__SOURCE  : "+item.getName());
			if(item.getName() == null)
				return "";
			
			return item.getName();
		}
		if(TaskModelPackage.CONNECTION_ELEMENT__TARGET == intValue.intValue()) {
			LinkedElement item = conn.getTarget();
			System.out.println("CONNECTION_ELEMENT__TARGET : "+item.getName());
			if(item.getName() == null)
				return "";
			return item.getName();
		}
		if(TaskModelPackage.CONNECTION_ELEMENT__RELATIONSHIP == intValue.intValue()) {
			System.out.println("CONNECTION_ELEMENT__RELATIONSHIP : ");
			RelationShip relation = conn.getRelationship();
			return new Integer(relation.getValue());
		}
		if(TaskModelPackage.CONNECTION_ELEMENT__CONDITION == intValue.intValue()) {
			EList<String> value = conn.getCondition();
			System.out.println("CONNECTION_ELEMENT__CONDITION : "+ value.toString());
			return value;
		}
		
//		if(TaskModelPackage.CONNECTION_ELEMENT_FEATURE_COUNT == intValue.intValue()) {
//			Integer value = conn.getPriority();
//			System.out.println("CONNECTION_ELEMENT_FEATURE_COUNT : "+ value);
//			return value;
//		}

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
		ConnectionElement conn = getShape();

		if(value instanceof PropertyContainer) {
			PropertyContainer propety = (PropertyContainer)value;

			int type = propety.getType();
			int featureId = propety.getFeatureId();
			switch(type) {
			case PropertyContainer.SET:
				switch(featureId) {
				case TaskModelPackage.CONNECTION_ELEMENT__SOURCE:
					break;
				case TaskModelPackage.CONNECTION_ELEMENT__TARGET:
					break;
				case TaskModelPackage.CONNECTION_ELEMENT__RELATIONSHIP:
					break;
				default:
					super.setPropertyValue(id, value);
					break;
				}
				break;
			case PropertyContainer.ADD:
				switch(featureId) {
				case TaskModelPackage.CONNECTION_ELEMENT__CONDITION:
					if(propety.isUndo) {
						if(propety.getNewValue() != null) {
							conn.getCondition().remove(propety.getNewValue().toString());
						}
					}
					else {
						if(propety.getNewValue() != null) {
							conn.getCondition().add(propety.getNewValue().toString());
						}
					}
					break;
				default:
					super.setPropertyValue(id, value);
					break;
				}
				break;
			case PropertyContainer.ADDALL:
				switch(featureId) {
				case TaskModelPackage.CONNECTION_ELEMENT__CONDITION:
					if(propety.isUndo) {
						if(propety.getNewValue() != null) {
							conn.getCondition().removeAll((List<String>)propety.getNewValue());
						}
					}
					else {
						if(propety.getNewValue() != null) {
							conn.getCondition().addAll((List<String>)propety.getNewValue());
						}
					}
					break;
				default:
					super.setPropertyValue(id, value);
					break;
				}
				break;
			case PropertyContainer.REMOVE:
				switch(featureId) {
				case TaskModelPackage.CONNECTION_ELEMENT__CONDITION:
					if(propety.isUndo) {
						if(propety.getOldValue() != null) {
							conn.getCondition().add(propety.getOldValue().toString());
						}
					}
					else {
						if(propety.getOldValue() != null) {
							conn.getCondition().remove(propety.getOldValue().toString());
						}
					}
					break;
				default:
					super.setPropertyValue(id, value);
					break;
				}
				break;
			case PropertyContainer.REMOVEALL:
				switch(featureId) {
				case TaskModelPackage.CONNECTION_ELEMENT__CONDITION:
					if(propety.isUndo) {
						if(propety.getOldValue() != null) {
							conn.getCondition().addAll((List<String>)propety.getOldValue());
						}
					}
					else {
						if(propety.getOldValue() != null) {
							conn.getCondition().removeAll((List<String>)propety.getOldValue());
						}
					}
					break;
				default:
					super.setPropertyValue(id, value);
					break;
				}
				break;
			case PropertyContainer.REPLACEALL:
				switch(featureId) {
				case TaskModelPackage.CONNECTION_ELEMENT__CONDITION:
					if(propety.isUndo) {
						if(propety.getNewValue() != null) {
							conn.getCondition().removeAll((List<String>)propety.getNewValue());
						}
						if(propety.getOldValue() != null) {
							conn.getCondition().addAll((List<String>)propety.getOldValue());
						}
					}
					else {
						if(propety.getOldValue() != null) {
							conn.getCondition().removeAll((List<String>)propety.getOldValue());
						}
						if(propety.getNewValue() != null) {
							conn.getCondition().addAll((List<String>)propety.getNewValue());
						}
					}
					break;
//				case TaskModelPackage.CONNECTION_ELEMENT_FEATURE_COUNT:
//					if(propety.isUndo) {
//						if(propety.getOldValue() != null) {
//							conn.setPriority((Integer)propety.getOldValue());
//						}
//					}
//					else {
//						if(propety.getNewValue() != null) {
//							conn.setPriority((Integer)propety.getNewValue());
//						}
//					}
//					break;
				default:
					super.setPropertyValue(id, value);
					break;
				}
				break;
			}
			
			return;
		}
		
		super.setPropertyValue(id, value);
	}
}
