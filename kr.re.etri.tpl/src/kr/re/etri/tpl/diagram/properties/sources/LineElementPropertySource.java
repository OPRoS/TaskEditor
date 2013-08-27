
package kr.re.etri.tpl.diagram.properties.sources;

import java.util.List;

import kr.re.etri.tpl.taskmodel.LineElement;
import kr.re.etri.tpl.taskmodel.LineEndPoint;
import kr.re.etri.tpl.taskmodel.LineStyle;
import kr.re.etri.tpl.taskmodel.TaskModelPackage;

import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;

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
public class LineElementPropertySource extends ItemElementPropertySource {
	/**
	 * LineElement 의 Line Style 속성 id
	 * TaskModelPackage.LINE_MODEL__LINE_STYLE
	 */
	private static final String ID_STYLE = "Line Style";
	/**
	 * LineElement 의 width 속성 id
	 * TaskModelPackage.LINE_MODEL__WIDTH
	 */
	private static final String ID_WIDTH = "width";
	/**
	 * LineElement 의 Source Endpoint 속성 id
	 * TaskModelPackage.LINE_MODEL__SOURCE_ARROW
	 */
	private static final String ID_SOURCEARROW = "Source Endpoint";
	/**
	 * LineElement 의 Target Endpoint 속성 id
	 * TaskModelPackage.LINE_MODEL__TARGET_ARROW
	 */
	private static final String ID_TARGETARROW = "Target Endpoint";
	/** 속성의 category */
	private static final String category = "Line";
	
	/**
	 * 주어진 model에 대한 PropertySoruce 를 생성한다.
	 * @param model 모델
	 */
	public LineElementPropertySource(LineElement model) {
		super(model);
	}

	/**
	 * Object 타입의 모델을 현재 PropertySource 가 관리하는 모델 타입으로 캐스팅한다.
	 * @return 모델
	 * 
	 * @Override
	 */
	private LineElement getShape() {
		return (LineElement) getModel();
	}

	/**
	 * 모델에 대한 Property 들에 대한 Descriptor 를 생성하여 제공한다.
	 * @param descriptors PropertyDescriptor 목록
	 * 
	 * @Override
	 */
	public void createPropertyDescriptors(List descriptors) {
		super.createPropertyDescriptors(descriptors);

		int idx, itemLen;
		String []values;

		PropertyDescriptor propDescr;

		idx = 0;
		itemLen = LineStyle.values().length;
		values = new String[itemLen];
		for(LineStyle lineStyle : LineStyle.VALUES) {
			values[idx++] = lineStyle.getName();
		}

		propDescr = new ComboBoxPropertyDescriptor(
				TaskModelPackage.LINE_ELEMENT__LINE_STYLE,
				ID_STYLE,
				values);
		propDescr.setCategory(category);
		descriptors.add(propDescr);

		idx = 0;
		itemLen = LineEndPoint.values().length;
		values = new String[itemLen];
		for(LineEndPoint arrowType : LineEndPoint.VALUES) {
			values[idx++] = arrowType.getName();
		}

		propDescr = new ComboBoxPropertyDescriptor(
				TaskModelPackage.LINE_ELEMENT__SOURCE_END_POINT,
				ID_SOURCEARROW,
				values);
		propDescr.setCategory(category);
		descriptors.add(propDescr);

		propDescr = new ComboBoxPropertyDescriptor(
				TaskModelPackage.LINE_ELEMENT__TARGET_END_POINT,
				ID_TARGETARROW,
				values);
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
		if(TaskModelPackage.LINE_ELEMENT__LINE_STYLE == propId.intValue()) {
			return true;
		}
		else if(TaskModelPackage.LINE_ELEMENT__SOURCE_END_POINT == propId.intValue()) {
			return true;
		}
		else if(TaskModelPackage.LINE_ELEMENT__TARGET_END_POINT == propId.intValue()) {
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
		Integer intValue = (Integer) id;
		LineElement item = getShape();

		if(TaskModelPackage.LINE_ELEMENT__LINE_STYLE == intValue.intValue()) {
			LineStyle lineStyle = item.getLineStyle();
			return new Integer(lineStyle.getValue() - 1);
		}
		else if(TaskModelPackage.LINE_ELEMENT__SOURCE_END_POINT == intValue.intValue()) {
			LineEndPoint arrowType = item.getSourceEndPoint();
			return new Integer(arrowType.getValue());
		}
		if(TaskModelPackage.LINE_ELEMENT__TARGET_END_POINT == intValue.intValue()) {
			LineEndPoint arrowType = item.getTargetEndPoint();
			return new Integer(arrowType.getValue());
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
//		if(value == null) return;

		LineElement item = getShape();
		Integer propId = (Integer) id;

		if(TaskModelPackage.LINE_ELEMENT__LINE_STYLE == propId.intValue()) {
			Integer intValue = (Integer) value;
			switch(intValue.intValue() + 1) {
			case LineStyle.LINE_SOLID_VALUE:
				item.setLineStyle(LineStyle.LINE_SOLID);
				break;
			case LineStyle.LINE_DASH_VALUE:
				item.setLineStyle(LineStyle.LINE_DASH);
				break;
			case LineStyle.LINE_DOT_VALUE:
				item.setLineStyle(LineStyle.LINE_DOT);
				break;
			case LineStyle.LINE_DASHDOT_VALUE:
				item.setLineStyle(LineStyle.LINE_DASHDOT);
				break;
			case LineStyle.LINE_DASHDOTDOT_VALUE:
				item.setLineStyle(LineStyle.LINE_DASHDOTDOT);
				break;
			case LineStyle.LINE_CUSTOM_VALUE:
				item.setLineStyle(LineStyle.LINE_CUSTOM);
				break;
			default:
				item.setLineStyle(LineStyle.LINE_SOLID);
				break;
			}

			return;
		}
		else if(TaskModelPackage.LINE_ELEMENT__SOURCE_END_POINT == propId.intValue()) {
			Integer intValue = (Integer) value;
			switch(intValue.intValue()) {
			case LineEndPoint.NONE_VALUE:
				item.setSourceEndPoint(LineEndPoint.NONE);
				break;
			case LineEndPoint.OPENED_ARROW_VALUE:
				item.setSourceEndPoint(LineEndPoint.OPENED_ARROW);
				break;
			case LineEndPoint.OPENED_TRIANGLE_VALUE:
				item.setSourceEndPoint(LineEndPoint.OPENED_TRIANGLE);
				break;
			case LineEndPoint.OPENED_SQUARE_VALUE:
				item.setSourceEndPoint(LineEndPoint.OPENED_SQUARE);
				break;
			case LineEndPoint.OPENED_CIRCLE_VALUE:
				item.setSourceEndPoint(LineEndPoint.OPENED_CIRCLE);
				break;
			case LineEndPoint.CLOSED_TRIANGLE_VALUE:
				item.setSourceEndPoint(LineEndPoint.CLOSED_TRIANGLE);
				break;
			case LineEndPoint.CLOSED_SQUARE_VALUE:
				item.setSourceEndPoint(LineEndPoint.CLOSED_SQUARE);
				break;
			case LineEndPoint.CLOSED_CIRCLE_VALUE:
				item.setSourceEndPoint(LineEndPoint.CLOSED_CIRCLE);
				break;
			default:
				item.setSourceEndPoint(LineEndPoint.NONE);
				break;
			}

			return;
		}
		else if(TaskModelPackage.LINE_ELEMENT__TARGET_END_POINT == propId.intValue()) {
			Integer intValue = (Integer) value;
			switch(intValue.intValue()) {
			case LineEndPoint.NONE_VALUE:
				item.setTargetEndPoint(LineEndPoint.NONE);
				break;
			case LineEndPoint.OPENED_ARROW_VALUE:
				item.setTargetEndPoint(LineEndPoint.OPENED_ARROW);
				break;
			case LineEndPoint.OPENED_TRIANGLE_VALUE:
				item.setTargetEndPoint(LineEndPoint.OPENED_TRIANGLE);
				break;
			case LineEndPoint.OPENED_SQUARE_VALUE:
				item.setTargetEndPoint(LineEndPoint.OPENED_SQUARE);
				break;
			case LineEndPoint.OPENED_CIRCLE_VALUE:
				item.setTargetEndPoint(LineEndPoint.OPENED_CIRCLE);
				break;
			case LineEndPoint.CLOSED_TRIANGLE_VALUE:
				item.setTargetEndPoint(LineEndPoint.CLOSED_TRIANGLE);
				break;
			case LineEndPoint.CLOSED_SQUARE_VALUE:
				item.setTargetEndPoint(LineEndPoint.CLOSED_SQUARE);
				break;
			case LineEndPoint.CLOSED_CIRCLE_VALUE:
				item.setTargetEndPoint(LineEndPoint.CLOSED_CIRCLE);
				break;
			default:
				item.setTargetEndPoint(LineEndPoint.NONE);
				break;
			}

			return;
		}

		super.setPropertyValue(id, value);
	}
}
