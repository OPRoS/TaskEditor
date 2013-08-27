
package kr.re.etri.tpl.diagram.properties.sources;

import java.util.List;

import kr.re.etri.tpl.diagram.util.PropertyContainer;
import kr.re.etri.tpl.taskmodel.IncludedElement;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.ModelDiagram;
import kr.re.etri.tpl.taskmodel.TaskModelPackage;

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
public class ModelDiagramPropertySource extends ItemElementPropertySource {

	/**
	 * �־��� model�� ���� PropertySoruce �� �����Ѵ�.
	 * @param model ��
	 */
	public ModelDiagramPropertySource(ModelDiagram model) {
		super(model);
	}

	/**
	 * Object Ÿ���� ���� ���� PropertySource �� �����ϴ� �� Ÿ������ ĳ�����Ѵ�.
	 * @return ��
	 * 
	 * @Override
	 */
	private ModelDiagram getShape() {
		return (ModelDiagram) getModel();
	}

	/**
	 * �𵨿� ���� Property �鿡 ���� Descriptor �� �����Ͽ� �����Ѵ�.
	 * @param descriptors PropertyDescriptor ���
	 * 
	 * @Override
	 */
	public void createPropertyDescriptors(List descriptors) {
		super.createPropertyDescriptors(descriptors);
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
		ModelDiagram item = getShape();

		switch(propId.intValue()) {
		case TaskModelPackage.MODEL_DIAGRAM__INCLUDE_ITEMS:
			return item.getIncludeItems();
			
		case TaskModelPackage.MODEL_DIAGRAM__ITEMS:
			return item.getItems();
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
		ModelDiagram modelDiagram = getShape();

		if(value instanceof PropertyContainer) {
			PropertyContainer propety = (PropertyContainer)value;

			List itemList;
			ItemElement newItem, oldItem;
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
				newItem = (ItemElement)propety.getNewValue();
				switch(featureId) {
				case TaskModelPackage.MODEL_DIAGRAM__INCLUDE_ITEMS:
					if (propety.isUndo) {
						newItem.setParent(null);
						modelDiagram.getIncludeItems().remove(propety.getNewValue());
					}
					else {
						if(newItem instanceof IncludedElement) {
							newItem.setParent(modelDiagram);
							modelDiagram.getIncludeItems().add((IncludedElement)newItem);
						}
					}
					break;
				case TaskModelPackage.MODEL_DIAGRAM__ITEMS:
					if (propety.isUndo) {
						newItem.setParent(null);
						modelDiagram.getItems().remove(propety.newValue);
					}
					else {
						newItem.setParent(modelDiagram);
						modelDiagram.getItems().add(newItem);
					}
					break;
				default:
						super.setPropertyValue(id, value);
						break;
				}
				break;
			case PropertyContainer.REMOVE:
				oldItem = (ItemElement)propety.getOldValue();
				switch(featureId) {
				case TaskModelPackage.MODEL_DIAGRAM__INCLUDE_ITEMS:
					if (propety.isUndo) {
						if (oldItem instanceof IncludedElement) {
							oldItem.setParent(modelDiagram);
							modelDiagram.getIncludeItems().add((IncludedElement)oldItem);
						}
					}
					else {
						oldItem.setParent(null);
						modelDiagram.getIncludeItems().remove(propety.getOldValue());
					}
					break;
				case TaskModelPackage.MODEL_DIAGRAM__ITEMS:
					if (propety.isUndo) {
						oldItem.setParent(modelDiagram);
						modelDiagram.getItems().add(oldItem);
					}
					else {
						oldItem.setParent(null);
						modelDiagram.getItems().remove(propety.getOldValue());
					}
					break;
				default:
					super.setPropertyValue(id, value);
					break;
				}
				break;
			case PropertyContainer.ADDALL:
				itemList = (List)propety.getNewValue();
				switch(featureId) {
				case TaskModelPackage.MODEL_DIAGRAM__INCLUDE_ITEMS:
					if (propety.isUndo) {
						for(int idx = 0; idx < itemList.size(); idx += 1) {
							newItem = (ItemElement)itemList.get(idx);
							newItem.setParent(null);
							modelDiagram.getIncludeItems().remove(newItem);
						}
					}
					else {
						for(int idx = 0; idx < itemList.size(); idx += 1) {
							newItem = (ItemElement)itemList.get(idx);
							if (newItem instanceof IncludedElement) {
								newItem.setParent(modelDiagram);
								modelDiagram.getIncludeItems().add((IncludedElement)newItem);
							}
						}
					}
					break;
				case TaskModelPackage.MODEL_DIAGRAM__ITEMS:
					if (propety.isUndo) {
						for(int idx = 0; idx < itemList.size(); idx += 1) {
							newItem = (ItemElement)itemList.get(idx);
							newItem.setParent(null);
							modelDiagram.getItems().remove(newItem);
						}
					}
					else {
						for(int idx = 0; idx < itemList.size(); idx += 1) {
							newItem = (ItemElement)itemList.get(idx);
							newItem.setParent(modelDiagram);
							modelDiagram.getItems().add(newItem);
						}
					}
					break;
				default:
						super.setPropertyValue(id, value);
						break;
				}
				break;
			case PropertyContainer.REMOVEALL:
				itemList = (List)propety.getOldValue();
				switch(featureId) {
				case TaskModelPackage.MODEL_DIAGRAM__INCLUDE_ITEMS:
					if (propety.isUndo) {
						for(int idx = 0; idx < itemList.size(); idx += 1) {
							oldItem = (ItemElement)itemList.get(idx);
							if (oldItem instanceof IncludedElement) {
								oldItem.setParent(modelDiagram);
								modelDiagram.getIncludeItems().add((IncludedElement)oldItem);
							}
						}
					}
					else {
						for(int idx = 0; idx < itemList.size(); idx += 1) {
							oldItem = (ItemElement)itemList.get(idx);
							oldItem.setParent(null);
							modelDiagram.getIncludeItems().remove(oldItem);
						}
					}
					break;
				case TaskModelPackage.MODEL_DIAGRAM__ITEMS:
					if (propety.isUndo) {
						for(int idx = 0; idx < itemList.size(); idx += 1) {
							oldItem = (ItemElement)itemList.get(idx);
							oldItem.setParent(modelDiagram);
							modelDiagram.getItems().add(oldItem);
						}
					}
					else {
						for(int idx = 0; idx < itemList.size(); idx += 1) {
							oldItem = (ItemElement)itemList.get(idx);
							oldItem.setParent(null);
							modelDiagram.getItems().remove(oldItem);
						}						
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
