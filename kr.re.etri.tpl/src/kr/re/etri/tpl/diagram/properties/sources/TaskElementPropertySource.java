
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
public class TaskElementPropertySource extends ItemElementPropertySource {
	/**
	 * WorkerElement �� Initial Task �Ӽ� id
	 * TaskModelPackage.TASK_ELEMENT__INITIAL_TASK
	 */
	private static final String ID_INITIALTASK = "Initial Task";
	// KJH 20110209 s, add
	/**
	 * WorkerElement �� Initial Task �Ӽ� id
	 * TaskModelPackage.TASK_ELEMENT__INITIALIZE
	 */
	private static final String ID_INITIALIZE = "Initialize";
	/**
	 * WorkerElement �� Initial Task �Ӽ� id
	 * TaskModelPackage.TASK_ELEMENT__FINALIZE
	 */
	private static final String ID_FINALIZE = "Finalize";
	// KJH 20110209 e, add
	/** �Ӽ��� category */
	private static final String category = "Worker";

	/**
	 * �־��� model�� ���� PropertySoruce �� �����Ѵ�.
	 * @param model ��
	 */
	/**
	 * Object Ÿ���� ���� ���� PropertySource �� �����ϴ� �� Ÿ������ ĳ�����Ѵ�.
	 * @return ��
	 * 
	 * @Override
	 */
	public TaskElementPropertySource(TaskElement model) {
		super(model);
	}

	/**
	 * Object Ÿ���� ���� ���� PropertySource �� �����ϴ� �� Ÿ������ ĳ�����Ѵ�.
	 * @return ��
	 * 
	 * @Override
	 */
	private TaskElement getShape() {
		return (TaskElement) getModel();
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
