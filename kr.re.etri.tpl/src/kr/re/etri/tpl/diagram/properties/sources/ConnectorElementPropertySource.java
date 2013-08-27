package kr.re.etri.tpl.diagram.properties.sources;

import java.util.ArrayList;
import java.util.List;

import kr.re.etri.tpl.diagram.util.PropertyContainer;
import kr.re.etri.tpl.taskmodel.ConnectorElement;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.JoinType;
import kr.re.etri.tpl.taskmodel.Parameter;
import kr.re.etri.tpl.taskmodel.StateElement;
import kr.re.etri.tpl.taskmodel.StructBlockElement;
import kr.re.etri.tpl.taskmodel.TaskModelPackage;
import kr.re.etri.tpl.taskmodel.WithElement;

import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

/*
 * KJH 20101130, create
 */
public class ConnectorElementPropertySource extends BlockElementPropertySource {

	/**
	 * ConnectorElement 의 Join Type 속성 id
	 * TaskModelPackage.CONNECTOR_ELEMENT_JOIN_TYPE
	 */
	private static final String ID_JOIN_TYPE = "JoinType";
	/**
	 * ConnectorElement 의 Construct 속성 id
	 * TaskModelPackage.CONNECTOR_ELEMENT_CONSTRUCT
	 */
	private static final String ID_CONSTRUCT = "Construct";
	/**
	 * ConnectorElement 의 Destruct 속성 id
	 * TaskModelPackage.CONNECTOR_ELEMENT_DESTRUCT
	 */
	private static final String ID_DESTRUCT = "Destruct";
	/** 속성의 category */
	private static final String category = "Connector";
	
	/**
	 * 주어진 model에 대한 PropertySoruce 를 생성한다.
	 * @param model 모델
	 */
	public ConnectorElementPropertySource(ConnectorElement model) {
		super(model);
	}

	/**
	 * Object 타입의 모델을 현재 PropertySource 가 관리하는 모델 타입으로 캐스팅한다.
	 * @return 모델
	 * 
	 * @Override
	 */
	private ConnectorElement getShape() {
		return (ConnectorElement) getModel();
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
		
		// KJH 20101203 s, join type
		propDescr = new ComboBoxPropertyDescriptor(TaskModelPackage.CONNECTOR_ELEMENT__JOIN_TYPE,
				ID_JOIN_TYPE, JoinType.getJoinTypes());
		propDescr.setCategory(category);
		descriptors.add(propDescr);
		// KJH 20101203 e, join type
		
		propDescr = new TextPropertyDescriptor(TaskModelPackage.CONNECTOR_ELEMENT__CONSTRUCT, ID_CONSTRUCT);
		propDescr.setCategory(category);
		descriptors.add(propDescr);
		
		propDescr = new TextPropertyDescriptor(TaskModelPackage.CONNECTOR_ELEMENT__DESTRUCT, ID_DESTRUCT);
		propDescr.setCategory(category);
		descriptors.add(propDescr);
	}

	@Override
	public boolean isPropertyResettable(Object id) {
		return super.isPropertyResettable(id);
	}

	@Override
	public boolean isPropertySet(Object id) {
		Integer propId = (Integer)id;
		if (	TaskModelPackage.CONNECTOR_ELEMENT__JOIN_TYPE == propId.intValue() ||	// KJH 20101203, join type
				TaskModelPackage.CONNECTOR_ELEMENT__CONSTRUCT == propId.intValue() ||
				TaskModelPackage.CONNECTOR_ELEMENT__DESTRUCT == propId.intValue()) {	// KJH 20110209, del conexer
			return true;
		}
		if (TaskModelPackage.CONNECTOR_ELEMENT__PARAMS == propId.intValue()) {
			return true;
		}
		
		return super.isPropertySet(id);
	}

	@Override
	public void resetPropertyValue(Object id) {
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
		ConnectorElement item = getShape();

		if(TaskModelPackage.CONNECTOR_ELEMENT__CONSTRUCT == propId.intValue()) {
			StructBlockElement value = item.getConstruct();
			if (value == null)
				return "";
			return value;
		}
		if(TaskModelPackage.CONNECTOR_ELEMENT__DESTRUCT == propId.intValue()) {
			StructBlockElement value = item.getDestruct();
			if (value == null)
				return "";
			return value;
		}
		// KJH 20101203 s, join type
		if(TaskModelPackage.CONNECTOR_ELEMENT__JOIN_TYPE == propId.intValue()) {
			return item.getJoinType();
		}	// KJH 20101203 e, join type

		return super.getPropertyValue(id);
	}

	@Override
	public void setPropertyValue(Object id, Object value) {
		Integer propId = (Integer)id;
		ConnectorElement item = getShape();
		
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
				case TaskModelPackage.CONNECTOR_ELEMENT__PARAMS:
					if(propety.isUndo) {
						item.getParams().remove(propety.getNewValue());
					}
					else {
						Parameter param = (Parameter)propety.getNewValue();
						param.setParent(item);
						item.getParams().add(param);
					}
					break;
				case TaskModelPackage.CONNECTOR_ELEMENT__WITHS:
					if(propety.isUndo) {
						item.getWiths().remove(propety.getNewValue());	// KJH 20101202, getParams->getStates
					}
					else {
						WithElement with = (WithElement)propety.getNewValue();
						with.setParent(item);
						item.getWiths().add(with);
					}
					break;
				default:
					super.setPropertyValue(id, value);
					break;
				}
				break;
			case PropertyContainer.REMOVE:
				switch(featureId) {
				case TaskModelPackage.CONNECTOR_ELEMENT__PARAMS:
					if(propety.isUndo) {
						Parameter param = (Parameter)propety.getOldValue();
						param.setParent(item);
						item.getParams().add(param);
					}
					else {
						item.getParams().remove(propety.getOldValue());
					}
					break;
				case TaskModelPackage.CONNECTOR_ELEMENT__WITHS:
					if(propety.isUndo) {
						WithElement with = (WithElement)propety.getOldValue();
						with.setParent(item);
						item.getWiths().add(with);
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
		
		if (TaskModelPackage.CONNECTOR_ELEMENT__CONSTRUCT == propId.intValue()) {
			item.setConstruct((StructBlockElement)value);
			return;
		}
		if (TaskModelPackage.CONNECTOR_ELEMENT__DESTRUCT == propId.intValue()) {
			item.setDestruct((StructBlockElement)value);
			return;
		}
		if (TaskModelPackage.CONNECTOR_ELEMENT__JOIN_TYPE == propId.intValue()) {
			item.setJoinType((JoinType)value);
			return;
		}

		super.setPropertyValue(id, value);
	}

}
