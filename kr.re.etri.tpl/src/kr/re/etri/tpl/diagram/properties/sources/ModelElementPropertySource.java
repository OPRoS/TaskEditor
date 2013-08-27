
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
public class ModelElementPropertySource extends ItemElementPropertySource {
	/**
	 * ModelElement �� Symbol �Ӽ� id
	 * TaskModelPackage.MODEL_ELEMENT__SYMBOLS
	 */
	private static final String ID_SYMBOL= "Symbol";
	/**
	 * ModelElement �� Constant �Ӽ� id
	 * TaskModelPackage.MODEL_ELEMENT__CONSTANTS
	 */
	private static final String ID_CONSTATNT= "Constant";
	/**
	 * ModelElement �� Function �Ӽ� id
	 * TaskModelPackage.MODEL_ELEMENT__FUNCTIONS
	 */
	private static final String ID_FUNCTION= "Function";
	/**
	 * ModelElement �� Model �Ӽ� id
	 * TaskModelPackage.MODEL_ELEMENT__MODELS
	 */
	private static final String ID_MODEL= "Model";
	/** �Ӽ��� category */
	private static final String category = "Symbols";

	/**
	 * �־��� model�� ���� PropertySoruce �� �����Ѵ�.
	 * @param model ��
	 */
	public ModelElementPropertySource(ModelElement model) {
		super(model);
	}

	/**
	 * Object Ÿ���� ���� ���� PropertySource �� �����ϴ� �� Ÿ������ ĳ�����Ѵ�.
	 * @return ��
	 * 
	 * @Override
	 */
	private ModelElement getShape() {
		return (ModelElement) getModel();
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
