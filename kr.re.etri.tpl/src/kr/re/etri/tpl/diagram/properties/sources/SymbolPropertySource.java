
package kr.re.etri.tpl.diagram.properties.sources;

import java.util.List;

import kr.re.etri.tpl.diagram.util.PropertyContainer;
import kr.re.etri.tpl.taskmodel.ActionElement;
import kr.re.etri.tpl.taskmodel.Direction;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.ModelElement;
import kr.re.etri.tpl.taskmodel.Parameter;
import kr.re.etri.tpl.taskmodel.Symbol;
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
public class SymbolPropertySource extends ItemElementPropertySource {
	/**
	 * Symbol 의 Value 속성 id
	 * TaskModelPackage.SYMBOL__VALUE
	 */
	private static final String ID_VALUE = "Value";

	private static final String category = "Symbol";

	/**
	 * 주어진 model에 대한 PropertySoruce 를 생성한다.
	 * @param model 모델
	 */
	public SymbolPropertySource(Symbol model) {
		super(model);
	}

	/**
	 * Object 타입의 모델을 현재 PropertySource 가 관리하는 모델 타입으로 캐스팅한다.
	 * @return 모델
	 * 
	 * @Override
	 */
	private Symbol getShape() {
		return (Symbol) getModel();
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
				TaskModelPackage.SYMBOL__VALUE,
				ID_VALUE);
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
		if(TaskModelPackage.SYMBOL__VALUE == propId.intValue()) {
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
		Symbol item = getShape();

		if(TaskModelPackage.SYMBOL__VALUE == propId.intValue()) {
			String value = item.getValue();
			if(value == null) {
				return "";
			}
			return item.getValue();
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

			ItemElement item = getShape();

			int type = property.getType();
			int featureId = property.getFeatureId();


			switch(type) {
			case PropertyContainer.SET:

				switch(featureId) {
				case TaskModelPackage.SYMBOL__DIRECTION:
					Symbol symbol = (Symbol)item;
					if(property.isUndo){
						symbol.setDirection((Direction)property.getOldValue());
					}else{
						symbol.setDirection((Direction)property.getNewValue());
					}
					break;
				case TaskModelPackage.SYMBOL__TYPE:
					symbol = (Symbol)item;
					if(property.isUndo){
						symbol.setType((String)property.getOldValue());
					}else{
						symbol.setType((String)property.getNewValue());
					}
					break;
				default:
					super.setPropertyValue(id, value);
					break;
				}
				break;
			case PropertyContainer.ADD:
				switch(featureId) {
				case TaskModelPackage.MODEL_ELEMENT__SYMBOLS:
					ModelElement model = (ModelElement)item;
					if(property.isUndo) {
						model.getSymbols().remove(property.getNewValue());
					}
					else {
						Symbol symbol = (Symbol)property.getNewValue();
						symbol.setParent(model);
						model.getSymbols().add(symbol);
					}
					break;
				default:
					super.setPropertyValue(id, value);
					break;
				}
				break;
			case PropertyContainer.REMOVE:
				switch(featureId) {
				case TaskModelPackage.MODEL_ELEMENT__SYMBOLS:

					ModelElement model = (ModelElement)item;

					if(property.isUndo) {
						Symbol symbol = (Symbol)property.getOldValue();
						symbol.setParent(item);
						model.getSymbols().add(symbol);
					}
					else {
						model.getSymbols().remove(property.getOldValue());
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
