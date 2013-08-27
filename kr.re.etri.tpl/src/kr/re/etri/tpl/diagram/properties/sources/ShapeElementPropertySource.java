
package kr.re.etri.tpl.diagram.properties.sources;

import java.util.List;

import kr.re.etri.tpl.taskmodel.ShapeElement;
import kr.re.etri.tpl.taskmodel.StateAction;
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
public class ShapeElementPropertySource extends ItemElementPropertySource {
	/**
	 * ShapeElement �� X �Ӽ� id
	 * TaskModelPackage.SHAPE_ELEMENTL__X
	 */
	private static final String ID_X = "X";
	/**
	 * ShapeElement �� Y �Ӽ� id
	 * TaskModelPackage.SHAPE_ELEMENTL__Y
	 */
	private static final String ID_Y = "Y";
	/**
	 * ShapeElement �� Width �Ӽ� id
	 * TaskModelPackage.SHAPE_ELEMENTL__WIDTH
	 */
	private static final String ID_WIDTH = "Width";
	/**
	 * ShapeElement �� Height �Ӽ� id
	 * TaskModelPackage.SHAPE_ELEMENTL__HEIGHT
	 */
	private static final String ID_HEIGHT = "Height";
	/**
	 * ShapeElement �� Figure �Ӽ� id
	 * TaskModelPackage.SHAPE_MODEL__FIGURE
	 */
	private static final String ID_FIGURE = "Figure";
	/**
	 * ShapeElement �� Border �Ӽ� id
	 * TaskModelPackage.SHAPE_MODEL__BORDER
	 */
	private static final String ID_BORDER = "Border";
	/**
	 * ShapeElement �� Collapsed �Ӽ� id
	 * TaskModelPackage.SHAPE_ELEMENT__COLLAPSED
	 */
	private static final String ID_COLLAPSED = "Collapsed";

	private static final String category = "Graphic";
	
	/**
	 * �־��� model�� ���� PropertySoruce �� �����Ѵ�.
	 * @param model ��
	 */
	public ShapeElementPropertySource(ShapeElement model) {
		super(model);
	}

	/**
	 * Object Ÿ���� ���� ���� PropertySource �� �����ϴ� �� Ÿ������ ĳ�����Ѵ�.
	 * @return ��
	 * 
	 * @Override
	 */
	private ShapeElement getShape() {
		return (ShapeElement) getModel();
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
				TaskModelPackage.SHAPE_ELEMENT__X,
				ID_X);
		propDescr.setCategory(category);
		propDescr.setAlwaysIncompatible(true);
		descriptors.add(propDescr);
		
		propDescr = new TextPropertyDescriptor(
				TaskModelPackage.SHAPE_ELEMENT__Y,
				ID_Y);
		propDescr.setCategory(category);
		propDescr.setAlwaysIncompatible(true);
		descriptors.add(propDescr);
		
		propDescr = new TextPropertyDescriptor(
				TaskModelPackage.SHAPE_ELEMENT__WIDTH,
				ID_WIDTH);
		propDescr.setCategory(category);
		propDescr.setAlwaysIncompatible(true);
		descriptors.add(propDescr);

		propDescr = new TextPropertyDescriptor(
				TaskModelPackage.SHAPE_ELEMENT__HEIGHT,
				ID_HEIGHT);
		propDescr.setCategory(category);
		propDescr.setAlwaysIncompatible(true);
		descriptors.add(propDescr);
		propDescr = new TextPropertyDescriptor(
				TaskModelPackage.SHAPE_ELEMENT__COLLAPSED,
				ID_COLLAPSED);
		propDescr.setCategory(category);
		propDescr.setAlwaysIncompatible(true);
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
		if(TaskModelPackage.SHAPE_ELEMENT__X == propId.intValue()) {
			return true;
		}
		else if(TaskModelPackage.SHAPE_ELEMENT__Y == propId.intValue()) {
			return true;
		}
		else if(TaskModelPackage.SHAPE_ELEMENT__WIDTH == propId.intValue()) {
			return true;
		}
		else if(TaskModelPackage.SHAPE_ELEMENT__HEIGHT == propId.intValue()) {
			return true;
		}
		else if(TaskModelPackage.SHAPE_ELEMENT__COLLAPSED == propId.intValue()) {
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

		if(TaskModelPackage.SHAPE_ELEMENT__X == propId.intValue()) {
			return false;
		}
		else if(TaskModelPackage.SHAPE_ELEMENT__Y == propId.intValue()) {
			return false;
		}
		else if(TaskModelPackage.SHAPE_ELEMENT__WIDTH == propId.intValue()) {
			return false;
		}
		else if(TaskModelPackage.SHAPE_ELEMENT__HEIGHT == propId.intValue()) {
			return false;
		}
		else if(TaskModelPackage.SHAPE_ELEMENT__COLLAPSED == propId.intValue()) {
			return false;
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
		ShapeElement item = getShape();

		if(TaskModelPackage.SHAPE_ELEMENT__X == propId.intValue()) {
			return Integer.toString(item.getX());
		}
		else if(TaskModelPackage.SHAPE_ELEMENT__Y == propId.intValue()) {
			return Integer.toString(item.getY());
		}
		else if(TaskModelPackage.SHAPE_ELEMENT__WIDTH == propId.intValue()) {
			return Integer.toString(item.getWidth());
		}
		else if(TaskModelPackage.SHAPE_ELEMENT__HEIGHT == propId.intValue()) {
			return Integer.toString(item.getHeight());
		}
		else if(TaskModelPackage.SHAPE_ELEMENT__COLLAPSED == propId.intValue()) {
			return Boolean.toString(item.isCollapsed());
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

		ShapeElement item = getShape();

		if (TaskModelPackage.SHAPE_ELEMENT__X == propId.intValue()) {
			item.setX(Integer.parseInt(value.toString()));
			return;
		} else if (TaskModelPackage.SHAPE_ELEMENT__Y == propId.intValue()) {
			item.setY(Integer.parseInt(value.toString()));
			return;
		} else if (TaskModelPackage.SHAPE_ELEMENT__WIDTH == propId.intValue()) {
			if(! (value instanceof StateAction)){
				item.setWidth(Integer.parseInt(value.toString()));
				return;
			}			
		} else if(TaskModelPackage.SHAPE_ELEMENT__HEIGHT == propId.intValue()) {
			item.setHeight(Integer.parseInt(value.toString()));
			return;
		} else if(TaskModelPackage.SHAPE_ELEMENT__COLLAPSED == propId.intValue()) {
			item.setCollapsed(Boolean.parseBoolean(value.toString()));
			return;
		}

		super.setPropertyValue(id, value);
	}
}
