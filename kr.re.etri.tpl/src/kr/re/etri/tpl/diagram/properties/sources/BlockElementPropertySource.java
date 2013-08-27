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
	 * BlockElement 의 Statements 속성 id
	 * TaskModelPackage.BLOCK_ELEMENT_STATEMENTS
	 */
	private static final String ID_STATEMENTS = "Statements";	// KJH 20101202
	/** 속성의 category */
	private static final String category = "Block";

	/**
	 * 주어진 model에 대한 PropertySoruce 를 생성한다.
	 * @param model 모델
	 */
	public BlockElementPropertySource(ItemElement model) {
		super(model);
	}

	/**
	 * Object 타입의 모델을 현재 PropertySource 가 관리하는 모델 타입으로 캐스팅한다.
	 * @return 모델
	 * 
	 * @Override
	 */
	private BlockElement getShape() {
		return (BlockElement) getModel();
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
		// KJH 20110602 s, statements
		propDescr = new TextPropertyDescriptor(
				TaskModelPackage.BLOCK_ELEMENT__STATEMENTS + TaskModelPackage.ITEM_ELEMENT_FEATURE_COUNT,
				ID_STATEMENTS);
		propDescr.setCategory(category);
		descriptors.add(propDescr);
		// KJH 20110602 e, statemenst
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
		if (TaskModelPackage.BLOCK_ELEMENT__STATEMENTS + TaskModelPackage.ITEM_ELEMENT_FEATURE_COUNT == propId.intValue()) {
			return true;	// KJH 20110602, add
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
