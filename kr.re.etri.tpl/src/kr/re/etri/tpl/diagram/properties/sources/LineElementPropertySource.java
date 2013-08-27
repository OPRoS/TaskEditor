
package kr.re.etri.tpl.diagram.properties.sources;

import java.util.List;

import kr.re.etri.tpl.taskmodel.LineElement;
import kr.re.etri.tpl.taskmodel.LineEndPoint;
import kr.re.etri.tpl.taskmodel.LineStyle;
import kr.re.etri.tpl.taskmodel.TaskModelPackage;

import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;

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
public class LineElementPropertySource extends ItemElementPropertySource {
	/**
	 * LineElement �� Line Style �Ӽ� id
	 * TaskModelPackage.LINE_MODEL__LINE_STYLE
	 */
	private static final String ID_STYLE = "Line Style";
	/**
	 * LineElement �� width �Ӽ� id
	 * TaskModelPackage.LINE_MODEL__WIDTH
	 */
	private static final String ID_WIDTH = "width";
	/**
	 * LineElement �� Source Endpoint �Ӽ� id
	 * TaskModelPackage.LINE_MODEL__SOURCE_ARROW
	 */
	private static final String ID_SOURCEARROW = "Source Endpoint";
	/**
	 * LineElement �� Target Endpoint �Ӽ� id
	 * TaskModelPackage.LINE_MODEL__TARGET_ARROW
	 */
	private static final String ID_TARGETARROW = "Target Endpoint";
	/** �Ӽ��� category */
	private static final String category = "Line";
	
	/**
	 * �־��� model�� ���� PropertySoruce �� �����Ѵ�.
	 * @param model ��
	 */
	public LineElementPropertySource(LineElement model) {
		super(model);
	}

	/**
	 * Object Ÿ���� ���� ���� PropertySource �� �����ϴ� �� Ÿ������ ĳ�����Ѵ�.
	 * @return ��
	 * 
	 * @Override
	 */
	private LineElement getShape() {
		return (LineElement) getModel();
	}

	/**
	 * �𵨿� ���� Property �鿡 ���� Descriptor �� �����Ͽ� �����Ѵ�.
	 * @param descriptors PropertyDescriptor ���
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
	 * �־��� id �� �ش��ϴ� Property ���� �����Ѵ�.
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
