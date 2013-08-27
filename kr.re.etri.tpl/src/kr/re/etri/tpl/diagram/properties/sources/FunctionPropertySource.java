
package kr.re.etri.tpl.diagram.properties.sources;

import java.util.Collections;
import java.util.List;

import kr.re.etri.tpl.diagram.util.PropertyContainer;
import kr.re.etri.tpl.taskmodel.Function;
import kr.re.etri.tpl.taskmodel.Parameter;
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
public class FunctionPropertySource extends ItemElementPropertySource {
	/**
	 * ItemElement �� Parameters �Ӽ� id
	 * TaskModelPackage.FUNCTION__PARAMS
	 */
	private static final String ID_PARAMS = "Parameters";
	/**
	 * ItemElement �� Type �Ӽ� id
	 * TaskModelPackage.FUNCTION__TYPE
	 */
	private static final String ID_TYPE = "Type";
	/** �Ӽ��� category */
	private static final String category = "Function";

	/**
	 * �־��� model�� ���� PropertySoruce �� �����Ѵ�.
	 * @param model ��
	 */
	public FunctionPropertySource(Function model) {
		super(model);
	}

	/**
	 * Object Ÿ���� ���� ���� PropertySource �� �����ϴ� �� Ÿ������ ĳ�����Ѵ�.
	 * @return ��
	 * 
	 * @Override
	 */
	private Function getShape() {
		return (Function) getModel();
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
				TaskModelPackage.FUNCTION__PARAMS,
				ID_PARAMS);
		propDescr.setCategory(category);
		descriptors.add(propDescr);
		
		propDescr = new TextPropertyDescriptor(
				TaskModelPackage.FUNCTION__TYPE,
				ID_TYPE);
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
		if(TaskModelPackage.FUNCTION__PARAMS == propId.intValue()) {
			return true;
		}
		else if(TaskModelPackage.FUNCTION__TYPE == propId.intValue()) {
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
		Function item = getShape();

		if(TaskModelPackage.FUNCTION__PARAMS == propId.intValue()) {
			EList list = item.getParams();
			if(list == null) {
				return Collections.EMPTY_LIST;
			}
			return list;
		}
		else if(TaskModelPackage.FUNCTION__TYPE == propId.intValue()) {
			return item.getType();
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
		Function item = getShape();

		if(value instanceof PropertyContainer) {
			PropertyContainer propety = (PropertyContainer)value;
	
			int type = propety.getType();
			int featureId = propety.getFeatureId();
			switch(type) {
			case PropertyContainer.SET:
				switch(featureId) {
				case TaskModelPackage.FUNCTION__TYPE:
					if (propety.isUndo) {
						item.setType((String)propety.getOldValue());
					}
					else {
						item.setType((String)propety.getNewValue());
					}
					break;
				default:
					super.setPropertyValue(id, value);
					break;
				}
				break;
			case PropertyContainer.ADD:
				switch(featureId) {
					case TaskModelPackage.FUNCTION__PARAMS:
						if(propety.isUndo) {
							item.getParams().remove(propety.getNewValue());
						}
						else {
							Parameter param = (Parameter)propety.getNewValue();
							param.setParent(item);
							item.getParams().add(param);
						}
						break;
					default:
						super.setPropertyValue(id, value);
						break;
					}
				break;
			case PropertyContainer.REMOVE:
				switch(featureId) {
				case TaskModelPackage.FUNCTION__PARAMS:
					if(propety.isUndo) {
						Parameter param = (Parameter)propety.getOldValue();
						param.setParent(item);
						item.getParams().add(param);
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
			}

			return;
		}
		
		super.setPropertyValue(id, value);

	}
}
