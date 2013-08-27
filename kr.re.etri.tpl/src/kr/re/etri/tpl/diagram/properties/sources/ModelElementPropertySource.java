
package kr.re.etri.tpl.diagram.properties.sources;

import java.util.List;

import kr.re.etri.tpl.diagram.util.PropertyContainer;
import kr.re.etri.tpl.taskmodel.Constant;
import kr.re.etri.tpl.taskmodel.Function;
import kr.re.etri.tpl.taskmodel.ModelElement;
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
public class ModelElementPropertySource extends ItemElementPropertySource {
	/**
	 * ModelElement 의 Symbol 속성 id
	 * TaskModelPackage.MODEL_ELEMENT__SYMBOLS
	 */
	private static final String ID_SYMBOL= "Symbol";
	/**
	 * ModelElement 의 Constant 속성 id
	 * TaskModelPackage.MODEL_ELEMENT__CONSTANTS
	 */
	private static final String ID_CONSTATNT= "Constant";
	/**
	 * ModelElement 의 Function 속성 id
	 * TaskModelPackage.MODEL_ELEMENT__FUNCTIONS
	 */
	private static final String ID_FUNCTION= "Function";
	/**
	 * ModelElement 의 Model 속성 id
	 * TaskModelPackage.MODEL_ELEMENT__MODELS
	 */
	private static final String ID_MODEL= "Model";
	/** 속성의 category */
	private static final String category = "Symbols";

	/**
	 * 주어진 model에 대한 PropertySoruce 를 생성한다.
	 * @param model 모델
	 */
	public ModelElementPropertySource(ModelElement model) {
		super(model);
	}

	/**
	 * Object 타입의 모델을 현재 PropertySource 가 관리하는 모델 타입으로 캐스팅한다.
	 * @return 모델
	 * 
	 * @Override
	 */
	private ModelElement getShape() {
		return (ModelElement) getModel();
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
				TaskModelPackage.MODEL_ELEMENT__SYMBOLS,
				ID_SYMBOL);
		propDescr.setCategory(category);
		propDescr.setAlwaysIncompatible(true);
		descriptors.add(propDescr);

		propDescr = new TextPropertyDescriptor(
				TaskModelPackage.MODEL_ELEMENT__CONSTANTS,
				ID_CONSTATNT);
		propDescr.setCategory(category);
		propDescr.setAlwaysIncompatible(true);
		descriptors.add(propDescr);

		propDescr = new TextPropertyDescriptor(
				TaskModelPackage.MODEL_ELEMENT__FUNCTIONS,
				ID_FUNCTION);
		propDescr.setCategory(category);
		propDescr.setAlwaysIncompatible(true);
		descriptors.add(propDescr);

		propDescr = new TextPropertyDescriptor(
				TaskModelPackage.MODEL_ELEMENT__MODELS,
				ID_MODEL);
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
		if(TaskModelPackage.MODEL_ELEMENT__SYMBOLS == propId.intValue()) {
			return true;
		}
		if(TaskModelPackage.MODEL_ELEMENT__CONSTANTS == propId.intValue()) {
			return true;
		}
		if(TaskModelPackage.MODEL_ELEMENT__FUNCTIONS == propId.intValue()) {
			return true;
		}
		if(TaskModelPackage.MODEL_ELEMENT__MODELS == propId.intValue()) {
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

		if(TaskModelPackage.MODEL_ELEMENT__SYMBOLS == propId.intValue()) {
			return false;
		}
		if(TaskModelPackage.MODEL_ELEMENT__CONSTANTS == propId.intValue()) {
			return false;
		}
		if(TaskModelPackage.MODEL_ELEMENT__FUNCTIONS == propId.intValue()) {
			return false;
		}
		if(TaskModelPackage.MODEL_ELEMENT__MODELS == propId.intValue()) {
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
		ModelElement item = getShape();

		if(TaskModelPackage.MODEL_ELEMENT__SYMBOLS == propId.intValue()) {
			return item.getSymbols();
		}
		if(TaskModelPackage.MODEL_ELEMENT__CONSTANTS == propId.intValue()) {
			return item.getConstants();
		}
		if(TaskModelPackage.MODEL_ELEMENT__FUNCTIONS == propId.intValue()) {
			return item.getFunctions();
		}
		if(TaskModelPackage.MODEL_ELEMENT__MODELS == propId.intValue()) {
			return item.getModels();
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

		ModelElement item = getShape();

		if(value instanceof PropertyContainer) {
			PropertyContainer propety = (PropertyContainer)value;

			int type = propety.getType();
			int featureId = propety.getFeatureId();
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
				case TaskModelPackage.MODEL_ELEMENT__SYMBOLS:
					if(propety.isUndo) {
						item.getSymbols().remove(propety.getNewValue());
					}
					else {
						Symbol symbolItem = (Symbol)propety.getNewValue();
						symbolItem.setParent(item);
						item.getSymbols().add(symbolItem);
					}
					break;
				case TaskModelPackage.MODEL_ELEMENT__CONSTANTS:
					if(propety.isUndo) {
						item.getConstants().remove(propety.getNewValue());
					}
					else {
						Constant constItem = (Constant)propety.getNewValue();
						constItem.setParent(item);
						item.getConstants().add(constItem);
					}
					break;
				case TaskModelPackage.MODEL_ELEMENT__FUNCTIONS:
					if(propety.isUndo) {
						item.getFunctions().remove(propety.getNewValue());
					}
					else {
						Function funcItem = (Function)propety.getNewValue();
						funcItem.setParent(item);
						item.getFunctions().add(funcItem);
					}
					break;
				case TaskModelPackage.MODEL_ELEMENT__MODELS:
					if(propety.isUndo) {
						item.getModels().remove(propety.getNewValue());
					}
					else {
						ModelElement modelItem = (ModelElement)propety.getNewValue();
						modelItem.setParent(item);
						item.getModels().add(modelItem);
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
					if(propety.isUndo) {
						Symbol symbolItem = (Symbol)propety.getOldValue();
						symbolItem.setParent(item);
						item.getSymbols().add(symbolItem);
					}
					else {
						item.getSymbols().remove(propety.getOldValue());
					}
					break;
				case TaskModelPackage.MODEL_ELEMENT__CONSTANTS:
					if(propety.isUndo) {
						Constant constItem = (Constant)propety.getOldValue();
						constItem.setParent(item);
						item.getConstants().add(constItem);
					}
					else {
						item.getConstants().remove(propety.getOldValue());
					}
					break;
				case TaskModelPackage.MODEL_ELEMENT__FUNCTIONS:
					if(propety.isUndo) {
						Function funcItem = (Function)propety.getOldValue();
						funcItem.setParent(item);
						item.getFunctions().add(funcItem);
					}
					else {
						item.getFunctions().remove(propety.getOldValue());
					}
					break;
				case TaskModelPackage.MODEL_ELEMENT__MODELS:
					if(propety.isUndo) {
						ModelElement modelItem = (ModelElement)propety.getOldValue();
						modelItem.setParent(item);
						item.getModels().add(modelItem);
					}
					else {
						item.getModels().remove(propety.getOldValue());
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
