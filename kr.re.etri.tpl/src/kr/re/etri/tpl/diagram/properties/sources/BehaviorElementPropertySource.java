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
public class BehaviorElementPropertySource extends BlockElementPropertySource {
	/**
	 * TaskElement 의 Parameters 속성 id
	 * TaskModelPackage.BEHAVIOR_ELEMENT__PARAMS
	 */
	private static final String ID_PARAMS = "Parameters";
	/**
	 * TaskElement 의 Construct 속성 id
	 * TaskModelPackage.BEHAVIOR_ELEMENT_CONSTRUCT
	 */
	private static final String ID_CONSTRUCT = "Construct";	// KJH 20101202
	/**
	 * TaskElement 의 Destruct 속성 id
	 * TaskModelPackage.BEHAVIOR_ELEMENT_DESTRUCT
	 */
	private static final String ID_DESTRUCT = "Destruct";	// KJH 20101202
	/** 속성의 category */
	private static final String category = "Task";

	/**
	 * 주어진 model에 대한 PropertySoruce 를 생성한다.
	 * @param model 모델
	 */
	public BehaviorElementPropertySource(BehaviorElement model) {
		super(model);
	}

	/**
	 * Object 타입의 모델을 현재 PropertySource 가 관리하는 모델 타입으로 캐스팅한다.
	 * @return 모델
	 * 
	 * @Override
	 */
	private BehaviorElement getShape() {
		return (BehaviorElement) getModel();
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
