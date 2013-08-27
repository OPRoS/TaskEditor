
package kr.re.etri.tpl.diagram.properties.sources;

import java.util.List;

import kr.re.etri.tpl.taskmodel.ShapeElement;
import kr.re.etri.tpl.taskmodel.StateAction;
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
public class ShapeElementPropertySource extends ItemElementPropertySource {
	/**
	 * ShapeElement 의 X 속성 id
	 * TaskModelPackage.SHAPE_ELEMENTL__X
	 */
	private static final String ID_X = "X";
	/**
	 * ShapeElement 의 Y 속성 id
	 * TaskModelPackage.SHAPE_ELEMENTL__Y
	 */
	private static final String ID_Y = "Y";
	/**
	 * ShapeElement 의 Width 속성 id
	 * TaskModelPackage.SHAPE_ELEMENTL__WIDTH
	 */
	private static final String ID_WIDTH = "Width";
	/**
	 * ShapeElement 의 Height 속성 id
	 * TaskModelPackage.SHAPE_ELEMENTL__HEIGHT
	 */
	private static final String ID_HEIGHT = "Height";
	/**
	 * ShapeElement 의 Figure 속성 id
	 * TaskModelPackage.SHAPE_MODEL__FIGURE
	 */
	private static final String ID_FIGURE = "Figure";
	/**
	 * ShapeElement 의 Border 속성 id
	 * TaskModelPackage.SHAPE_MODEL__BORDER
	 */
	private static final String ID_BORDER = "Border";
	/**
	 * ShapeElement 의 Collapsed 속성 id
	 * TaskModelPackage.SHAPE_ELEMENT__COLLAPSED
	 */
	private static final String ID_COLLAPSED = "Collapsed";

	private static final String category = "Graphic";
	
	/**
	 * 주어진 model에 대한 PropertySoruce 를 생성한다.
	 * @param model 모델
	 */
	public ShapeElementPropertySource(ShapeElement model) {
		super(model);
	}

	/**
	 * Object 타입의 모델을 현재 PropertySource 가 관리하는 모델 타입으로 캐스팅한다.
	 * @return 모델
	 * 
	 * @Override
	 */
	private ShapeElement getShape() {
		return (ShapeElement) getModel();
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
