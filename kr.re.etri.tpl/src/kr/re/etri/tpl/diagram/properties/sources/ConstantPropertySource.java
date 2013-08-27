
package kr.re.etri.tpl.diagram.properties.sources;

import java.util.List;

import kr.re.etri.tpl.diagram.util.PropertyContainer;
import kr.re.etri.tpl.taskmodel.Constant;
import kr.re.etri.tpl.taskmodel.TaskModelPackage;

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
public class ConstantPropertySource extends ItemElementPropertySource {
	/**
	 * ItemElement 의 Type 속성 id
	 * TaskModelPackage.CONSTANT__TYPE
	 */
	private static final String ID_TYPE = "Type";
	/**
	 * ItemElement 의 InitialValue 속성 id
	 * TaskModelPackage.CONSTANT__INIT_VALUE
	 */
	private static final String ID_INITVALUE = "InitialValue";
	/** 속성의 category */
	private static final String category = "Constant";

	/**
	 * 주어진 model에 대한 PropertySoruce 를 생성한다.
	 * @param model 모델
	 */
	public ConstantPropertySource(Constant model) {
		super(model);
	}

	/**
	 * Object 타입의 모델을 현재 PropertySource 가 관리하는 모델 타입으로 캐스팅한다.
	 * @return 모델
	 * 
	 * @Override
	 */
	private Constant getShape() {
		return (Constant) getModel();
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

		propDescr = new TextPropertyDescriptor(
				TaskModelPackage.CONSTANT__TYPE,
				ID_TYPE);
		propDescr.setCategory(category);
		descriptors.add(propDescr);

		propDescr = new TextPropertyDescriptor(
				TaskModelPackage.CONSTANT__INIT_VALUE,
				ID_INITVALUE);
		propDescr.setCategory(category);
		descriptors.add(propDescr);
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
		if(TaskModelPackage.CONSTANT__TYPE == propId.intValue()) {
			return true;
		}

		if(TaskModelPackage.CONSTANT__INIT_VALUE == propId.intValue()) {
			return true;
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
		Constant item = getShape();

		if(TaskModelPackage.CONSTANT__TYPE == propId.intValue()) {
			String value = item.getType();
			if(value == null) {
				return "";
			}
			return value;
		}

		if(TaskModelPackage.CONSTANT__INIT_VALUE == propId.intValue()) {
			String value = item.getInitValue();
			if(value == null) {
				return "";
			}
			return value;
		}

		return super.getPropertyValue(id);
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

		if(value instanceof PropertyContainer) {
			PropertyContainer property = (PropertyContainer)value;

			Constant constItem = getShape();

			int type = property.getType();
			int featureId = property.getFeatureId();


			switch(type) {
			case PropertyContainer.SET:

				switch(featureId) {
				case TaskModelPackage.CONSTANT__TYPE:
					if(property.isUndo){
						constItem.setType((String)property.getOldValue());
					}else{
						constItem.setType((String)property.getNewValue());
					}
					break;
				case TaskModelPackage.CONSTANT__INIT_VALUE:
					if(property.isUndo){
						constItem.setInitValue((String)property.getOldValue());
					}else{
						constItem.setInitValue((String)property.getNewValue());
					}
					break;
				default:
					super.setPropertyValue(id, value);
					break;
				}
				break;
			case PropertyContainer.ADD:
			case PropertyContainer.ADDALL:
			case PropertyContainer.REMOVE:
			case PropertyContainer.REMOVEALL:
			case PropertyContainer.MOVE:
				switch(featureId) {
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
