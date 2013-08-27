
package kr.re.etri.tpl.diagram.properties.sources;

import java.util.List;

import kr.re.etri.tpl.diagram.util.PropertyContainer;
import kr.re.etri.tpl.taskmodel.IncludedElement;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.ModelDiagram;
import kr.re.etri.tpl.taskmodel.TaskModelPackage;

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
public class ModelDiagramPropertySource extends ItemElementPropertySource {

	/**
	 * 주어진 model에 대한 PropertySoruce 를 생성한다.
	 * @param model 모델
	 */
	public ModelDiagramPropertySource(ModelDiagram model) {
		super(model);
	}

	/**
	 * Object 타입의 모델을 현재 PropertySource 가 관리하는 모델 타입으로 캐스팅한다.
	 * @return 모델
	 * 
	 * @Override
	 */
	private ModelDiagram getShape() {
		return (ModelDiagram) getModel();
	}

	/**
	 * 모델에 대한 Property 들에 대한 Descriptor 를 생성하여 제공한다.
	 * @param descriptors PropertyDescriptor 목록
	 * 
	 * @Override
	 */
	public void createPropertyDescriptors(List descriptors) {
		super.createPropertyDescriptors(descriptors);
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
