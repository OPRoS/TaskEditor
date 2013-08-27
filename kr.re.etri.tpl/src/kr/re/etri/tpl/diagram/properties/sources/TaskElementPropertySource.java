
package kr.re.etri.tpl.diagram.properties.sources;

import java.util.List;

import kr.re.etri.tpl.diagram.util.PropertyContainer;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.StructBlockElement;
import kr.re.etri.tpl.taskmodel.TaskElement;
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
public class TaskElementPropertySource extends ItemElementPropertySource {
	/**
	 * WorkerElement 의 Initial Task 속성 id
	 * TaskModelPackage.TASK_ELEMENT__INITIAL_TASK
	 */
	private static final String ID_INITIALTASK = "Initial Task";
	// KJH 20110209 s, add
	/**
	 * WorkerElement 의 Initial Task 속성 id
	 * TaskModelPackage.TASK_ELEMENT__INITIALIZE
	 */
	private static final String ID_INITIALIZE = "Initialize";
	/**
	 * WorkerElement 의 Initial Task 속성 id
	 * TaskModelPackage.TASK_ELEMENT__FINALIZE
	 */
	private static final String ID_FINALIZE = "Finalize";
	// KJH 20110209 e, add
	/** 속성의 category */
	private static final String category = "Worker";

	/**
	 * 주어진 model에 대한 PropertySoruce 를 생성한다.
	 * @param model 모델
	 */
	/**
	 * Object 타입의 모델을 현재 PropertySource 가 관리하는 모델 타입으로 캐스팅한다.
	 * @return 모델
	 * 
	 * @Override
	 */
	public TaskElementPropertySource(TaskElement model) {
		super(model);
	}

	/**
	 * Object 타입의 모델을 현재 PropertySource 가 관리하는 모델 타입으로 캐스팅한다.
	 * @return 모델
	 * 
	 * @Override
	 */
	private TaskElement getShape() {
		return (TaskElement) getModel();
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
				TaskModelPackage.WORKER_ELEMENT__INITIAL_TASK,
				ID_INITIALTASK);
		propDescr.setCategory(category);
		descriptors.add(propDescr);
		
		// KJH 20110209 s, add
		propDescr = new TextPropertyDescriptor(
				TaskModelPackage.WORKER_ELEMENT__INITIALIZE,
				ID_INITIALIZE);
		propDescr.setCategory(category);
		descriptors.add(propDescr);
		
		propDescr = new TextPropertyDescriptor(
				TaskModelPackage.WORKER_ELEMENT__FINALIZE,
				ID_FINALIZE);
		propDescr.setCategory(category);
		descriptors.add(propDescr);
		// KJH 20110209 e, add
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
		if(TaskModelPackage.WORKER_ELEMENT__INITIAL_TASK == propId.intValue()) {
			return true;
		}
		// KJH 20110209 s, add
		else if(TaskModelPackage.WORKER_ELEMENT__INITIALIZE == propId.intValue()) {
			return true;
		}
		else if(TaskModelPackage.WORKER_ELEMENT__FINALIZE == propId.intValue()) {
			return true;
		}	// KJH 20110209 e, add

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
		TaskElement item = getShape();

		if(TaskModelPackage.WORKER_ELEMENT__INITIAL_TASK == propId.intValue()) {
			// KJH 20110419, String->BehaviorElement
			ItemElement value = item.getInitialTask();
			return value;
		}
		// KJH 20110209 s, add
		else if(TaskModelPackage.WORKER_ELEMENT__INITIALIZE == propId.intValue()) {
			StructBlockElement value = item.getInitialize();
			return value;
		}
		else if(TaskModelPackage.WORKER_ELEMENT__FINALIZE == propId.intValue()) {
			StructBlockElement value = item.getFinalize();
			return value;
		}	// KJH 20110209 e, add

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
		TaskElement item = getShape();

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
			}
			
			return;
		}
		// KJH 20110209 s, add
		if (TaskModelPackage.WORKER_ELEMENT__INITIALIZE == propId.intValue()) {
			item.setInitialize((StructBlockElement)value);
			return;
		}
		else if (TaskModelPackage.WORKER_ELEMENT__FINALIZE == propId.intValue()) {
			item.setFinalize((StructBlockElement)value);
			return;
		}
		// KJH 20110209 e, add

		super.setPropertyValue(id, value);
	}
	
}
