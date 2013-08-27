
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
 * WorkerElement 에 대한 TreeEditPart 클래스이다.
 * 
 * @see kr.re.etri.tpl.diagram.outline.ItemElementTreeEditPart
 * 
 * @author sfline
 */
public class TaskElementTreeEditPart extends ItemElementTreeEditPart {
	
	/**
	 * WorkerElement 모델에 대한 TreeEditPart 를 생성한다.
	 * @param model ActionElement instance
	 */
	public TaskElementTreeEditPart(TaskElement model) {
		super(model);
	}
	
	/**
	 * Parameter 로 주어진 클래스(또는 인터페이스)에 대한 Adapter 를 제공한다.
	 * @param key Adapter 요청 타입
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
	 * EditPart 에 대한 Policy 를 적용한다.
	 * @see org.eclipse.gef.editparts.AbstractTreeEditPart#createEditPolicies()
	 * 
	 * @Override
	 */
	protected void createEditPolicies() {
		// allow removal of the associated model element
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new ShapeComponentTreeEditPolicy());
	}

	/**
	 * Object 타입의 모델을 EditPart 의 모델 타입으로 캐스팅한다.
	 * @return WorkerElement 모델
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
	 * EditPart 모델의 자식 모델들을 반환한다. 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#getModelChildren()
	 * 
	 * @Override
	 */
	protected Image getImage() {
		return createImageForModel(getCastedModel());
	}

	/**
	 * baseImage 를 기본으로 모델의 상태에 따라 Image 를 새로 생성한다. 
	 * @param shape 모델
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
	 * 모델의 Property 가 변경되면 호출된다.
	 * @param notification 모델의 변경 정보
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