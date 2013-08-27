
package kr.re.etri.tpl.diagram.properties.sources;

import java.util.List;

import kr.re.etri.tpl.diagram.util.PropertyContainer;
import kr.re.etri.tpl.taskmodel.Constant;
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
public class ConstantPropertySource extends ItemElementPropertySource {
	/**
	 * ItemElement �� Type �Ӽ� id
	 * TaskModelPackage.CONSTANT__TYPE
	 */
	private static final String ID_TYPE = "Type";
	/**
	 * ItemElement �� InitialValue �Ӽ� id
	 * TaskModelPackage.CONSTANT__INIT_VALUE
	 */
	private static final String ID_INITVALUE = "InitialValue";
	/** �Ӽ��� category */
	private static final String category = "Constant";

	/**
	 * �־��� model�� ���� PropertySoruce �� �����Ѵ�.
	 * @param model ��
	 */
	public ConstantPropertySource(Constant model) {
		super(model);
	}

	/**
	 * Object Ÿ���� ���� ���� PropertySource �� �����ϴ� �� Ÿ������ ĳ�����Ѵ�.
	 * @return ��
	 * 
	 * @Override
	 */
	private Constant getShape() {
		return (Constant) getModel();
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
		if(TaskModelPackage.CONSTANT__TYPE == propId.intValue()) {
			return true;
		}

		if(TaskModelPackage.CONSTANT__INIT_VALUE == propId.intValue()) {
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
