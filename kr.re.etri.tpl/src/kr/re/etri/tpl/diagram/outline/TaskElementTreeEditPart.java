
package kr.re.etri.tpl.diagram.outline;

import java.util.ArrayList;
import java.util.List;

import kr.re.etri.tpl.IconStrings;
import kr.re.etri.tpl.TaskModelPlugin;
import kr.re.etri.tpl.diagram.outline.policies.ShapeComponentTreeEditPolicy;
import kr.re.etri.tpl.diagram.properties.sources.TaskElementPropertySource;
import kr.re.etri.tpl.taskmodel.ConnectionElement;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.ReferElement;
import kr.re.etri.tpl.taskmodel.TaskElement;
import kr.re.etri.tpl.taskmodel.TaskModelPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.gef.EditPolicy;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.IPropertySourceProvider;

/**
 * WorkerElement �� ���� TreeEditPart Ŭ�����̴�.
 * 
 * @see kr.re.etri.tpl.diagram.outline.ItemElementTreeEditPart
 * 
 * @author sfline
 */
public class TaskElementTreeEditPart extends ItemElementTreeEditPart {
	
	/**
	 * WorkerElement �𵨿� ���� TreeEditPart �� �����Ѵ�.
	 * @param model ActionElement instance
	 */
	public TaskElementTreeEditPart(TaskElement model) {
		super(model);
	}
	
	/**
	 * Parameter �� �־��� Ŭ����(�Ǵ� �������̽�)�� ���� Adapter �� �����Ѵ�.
	 * @param key Adapter ��û Ÿ��
	 */
	public Object getAdapter(Class key) {
		if (IPropertySource.class == key) {
			Object obj = getModel();
			if(obj instanceof TaskElement) {
				return new TaskElementPropertySource((TaskElement)obj);
			}
		}
		else if (IPropertySourceProvider.class == key) {
			final Object model = getModel();
			if(model instanceof TaskElement) {
				return new IPropertySourceProvider() {
					public IPropertySource getPropertySource(Object object) {
						return new TaskElementPropertySource((TaskElement)model);		
					}
				};
			}
		}
		return super.getAdapter(key);
	}

	/**
	 * EditPart �� ���� Policy �� �����Ѵ�.
	 * @see org.eclipse.gef.editparts.AbstractTreeEditPart#createEditPolicies()
	 * 
	 * @Override
	 */
	protected void createEditPolicies() {
		// allow removal of the associated model element
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new ShapeComponentTreeEditPolicy());
	}

	/**
	 * Object Ÿ���� ���� EditPart �� �� Ÿ������ ĳ�����Ѵ�.
	 * @return WorkerElement ��
	 * 
	 * @Override
	 */
	protected TaskElement getCastedModel() {
		return (TaskElement) getModel();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.editparts.AbstractEditPart#getModelChildren()
	 */
	protected List getModelChildren() {
		TaskElement task = getCastedModel();
		List<ItemElement> list = new ArrayList<ItemElement>();
		
		ItemElement struct = task.getInitialize();
		if (struct != null) {
			list.add(struct);
		}
		struct = task.getFinalize();
		if (struct != null) {
			list.add(struct);
		}
		
		list.addAll(task.getItems());

		EList<ReferElement> refList = task.getReferences();
		for(ReferElement ref : refList) {
			ItemElement parent = ref;
			while (parent instanceof ReferElement) {
				EList<ConnectionElement> connList = ((ReferElement)parent).getSourceConnections();
				for (ConnectionElement conn : connList) {
					if (ref.equals(conn.getSource2())) {
						list.add(conn);
					}
				}
				parent = parent.getParent();
			}
		}
		
		return list;
	}

	/**
	 * EditPart ���� �ڽ� �𵨵��� ��ȯ�Ѵ�. 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#getModelChildren()
	 * 
	 * @Override
	 */
	protected Image getImage() {
		return createImageForModel(getCastedModel());
	}

	/**
	 * baseImage �� �⺻���� ���� ���¿� ���� Image �� ���� �����Ѵ�. 
	 * @param shape ��
	 * @return Image
	 * 
	 * @Override
	 */
	protected Image createImageForModel(TaskElement shape) {
		Image image = null;
		if(shape instanceof TaskElement) {
			image = TaskModelPlugin.getImageDescriptor(IconStrings.TASK_16).createImage();
		}

		return image;
	}

	/**
	 * ���� Property �� ����Ǹ� ȣ��ȴ�.
	 * @param notification ���� ���� ����
	 */
	public void notifyChanged(Notification notification) {
		int type = notification.getEventType();
		int featureId = notification.getFeatureID(TaskModelPackage.class);
		
		switch(type) {
		case Notification.SET:
			refreshVisuals();
			break;
		case Notification.ADD:
			switch(featureId) {
			case TaskModelPackage.WORKER_ELEMENT__ITEMS:
//				addChild(createChild(notification.getNewValue()), -1);
				this.refreshChildren();
				break;
			default:
				super.notifyChanged(notification);
				break;
			}
			break;
		case Notification.REMOVE:
			switch(featureId) {
			case TaskModelPackage.WORKER_ELEMENT__ITEMS:
//				removeChild(getEditPartForChild(notification.getOldValue()));
				this.refreshChildren();
				break;
			default:
				super.notifyChanged(notification);
				break;
			}
			break;
		}
	}
}