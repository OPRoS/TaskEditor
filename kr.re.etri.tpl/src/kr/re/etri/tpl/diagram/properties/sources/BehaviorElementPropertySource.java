package kr.re.etri.tpl.diagram.properties.sources;

import java.util.ArrayList;
import java.util.List;

import kr.re.etri.tpl.diagram.util.PropertyContainer;
import kr.re.etri.tpl.taskmodel.BehaviorElement;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.Parameter;
import kr.re.etri.tpl.taskmodel.StateElement;
import kr.re.etri.tpl.taskmodel.StructBlockElement;
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
public class BehaviorElementPropertySource extends BlockElementPropertySource {
	/**
	 * TaskElement �� Parameters �Ӽ� id
	 * TaskModelPackage.BEHAVIOR_ELEMENT__PARAMS
	 */
	private static final String ID_PARAMS = "Parameters";
	/**
	 * TaskElement �� Construct �Ӽ� id
	 * TaskModelPackage.BEHAVIOR_ELEMENT_CONSTRUCT
	 */
	private static final String ID_CONSTRUCT = "Construct";	// KJH 20101202
	/**
	 * TaskElement �� Destruct �Ӽ� id
	 * TaskModelPackage.BEHAVIOR_ELEMENT_DESTRUCT
	 */
	private static final String ID_DESTRUCT = "Destruct";	// KJH 20101202
	/** �Ӽ��� category */
	private static final String category = "Task";

	/**
	 * �־��� model�� ���� PropertySoruce �� �����Ѵ�.
	 * @param model ��
	 */
	public BehaviorElementPropertySource(BehaviorElement model) {
		super(model);
	}

	/**
	 * Object Ÿ���� ���� ���� PropertySource �� �����ϴ� �� Ÿ������ ĳ�����Ѵ�.
	 * @return ��
	 * 
	 * @Override
	 */
	private BehaviorElement getShape() {
		return (BehaviorElement) getModel();
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
		propDescr = new TextPropertyDescriptor(TaskModelPackage.TASK_ELEMENT__PARAMS, ID_PARAMS);
		propDescr.setCategory(category);
		descriptors.add(propDescr);
		
		// KJH 20101202 s,
		propDescr = new TextPropertyDescriptor(TaskModelPackage.TASK_ELEMENT__CONSTRUCT, ID_CONSTRUCT);
		propDescr.setCategory(category);
		descriptors.add(propDescr);
		
		propDescr = new TextPropertyDescriptor(TaskModelPackage.TASK_ELEMENT__DESTRUCT, ID_DESTRUCT);
		propDescr.setCategory(category);
		descriptors.add(propDescr);
		// KJH 20101202 e,
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
		if(TaskModelPackage.TASK_ELEMENT__PARAMS == propId.intValue()) {
			return true;
		}
		// KJH 20101202 s,
		if (TaskModelPackage.TASK_ELEMENT__CONSTRUCT == propId.intValue() ||
				TaskModelPackage.TASK_ELEMENT__DESTRUCT == propId.intValue()) {
			return true;
		}	// KJH 20101202 e,

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
		BehaviorElement item = getShape();

		if(TaskModelPackage.TASK_ELEMENT__PARAMS == propId.intValue()) {
			EList list = item.getParams();
			if(list == null) {
				return new ArrayList();
			}
			return list;
		}
		// KJH 20101202 s,
		if(TaskModelPackage.TASK_ELEMENT__CONSTRUCT == propId.intValue()) {
			StructBlockElement value = item.getConstruct();
			return value;
		}
		if(TaskModelPackage.TASK_ELEMENT__DESTRUCT == propId.intValue()) {
			StructBlockElement value = item.getDestruct();
			return value;
		}	// KJH 20101202 e,

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
		BehaviorElement item = getShape();

		if(value instanceof PropertyContainer) {
			PropertyContainer propety = (PropertyContainer)value;
	
			int type = propety.getType();
			int featureId = propety.getFeatureId();
			List<ItemElement> valueList = new ArrayList<ItemElement>();
			switch(type) {
			case PropertyContainer.SET:
				switch(featureId) {
				default:
					super.setPropertyValue(id, value);
					break;
				}
				break;
			case PropertyContainer.ADD:
				switch(featureId) {
				case TaskModelPackage.TASK_ELEMENT__PARAMS:
					if(propety.isUndo) {
						item.getParams().remove(propety.getNewValue());
					}
					else {
						Parameter param = (Parameter)propety.getNewValue();
						param.setParent(item);
						item.getParams().add(param);
					}
					break;
				case TaskModelPackage.TASK_ELEMENT__STATES:
					if(propety.isUndo) {
						item.getStates().remove(propety.getNewValue());	// KJH 20101202, getParams->getStates
					}
					else {
						StateElement state = (StateElement)propety.getNewValue();
						state.setParent(item);
						item.getStates().add(state);
					}
					break;
				default:
					super.setPropertyValue(id, value);
					break;
				}
				break;
			case PropertyContainer.REMOVE:
				switch(featureId) {
				case TaskModelPackage.TASK_ELEMENT__PARAMS:
					if(propety.isUndo) {
						Parameter param = (Parameter)propety.getOldValue();
						param.setParent(item);
						item.getParams().add(param);
					}
					else {
						item.getParams().remove(propety.getOldValue());
					}
					break;
				case TaskModelPackage.TASK_ELEMENT__STATES:
					if(propety.isUndo) {
						StateElement state = (StateElement)propety.getOldValue();
						state.setParent(item);
						item.getStates().add(state);
					}
					else {
						item.getParams().remove(propety.getOldValue());
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
		
		if (TaskModelPackage.TASK_ELEMENT__CONSTRUCT == propId.intValue()) {
			item.setConstruct((StructBlockElement)value);
			return;
		}
		if (TaskModelPackage.TASK_ELEMENT__DESTRUCT == propId.intValue()) {
			item.setDestruct((StructBlockElement)value);
			return;
		}

		super.setPropertyValue(id, value);
	}
	
}
