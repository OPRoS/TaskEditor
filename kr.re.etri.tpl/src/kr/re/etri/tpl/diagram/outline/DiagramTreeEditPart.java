
package kr.re.etri.tpl.diagram.outline;

import java.util.ArrayList;
import java.util.List;

import kr.re.etri.tpl.taskmodel.ActionElement;
import kr.re.etri.tpl.taskmodel.BehaviorElement;
import kr.re.etri.tpl.taskmodel.ConnectorElement;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.ModelDiagram;
import kr.re.etri.tpl.taskmodel.ReferElement;
import kr.re.etri.tpl.taskmodel.SubDiagram;
import kr.re.etri.tpl.taskmodel.TaskElement;
import kr.re.etri.tpl.taskmodel.TaskModelPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.RootEditPart;
import org.eclipse.gef.editpolicies.RootComponentEditPolicy;

/**
 * ModelDiagram 에 대한 TreeEditPart 클래스이다.
 * 
 * @see kr.re.etri.tpl.diagram.outline.ItemElementTreeEditPart
 * 
 * @author sfline
 */
public class DiagramTreeEditPart extends ItemElementTreeEditPart {
	
	/**
	 * ModelDiagram 모델에 대한 TreeEditPart 를 생성한다.
	 * @param model ActionElement instance
	 */
	public DiagramTreeEditPart(ModelDiagram model) {
		super(model);
	}

	/**
	 * 모델의 속성 값이 변경되는 경우 Notify 를 받도록 Listener 로 등록한다.
	 * @param model 모델
	 * 
	 * @Override
	 */
	protected void hookIntoModel(EObject model) {
		super.hookIntoModel(model);

		ItemElement realItem = getCastedModel();
		if(realItem instanceof ModelDiagram) {
			SubDiagram subDiagram = ((ModelDiagram)realItem).getSubDiagram();
			if(subDiagram != null) {
				subDiagram.eAdapters().add(this);
			}
		}
	}
	
	/**
	 * 모델의 Notify 를 받지 않도록 Listener 등록을 해제한다.
	 * @param model 모델
	 * 
	 * @Override
	 */
	protected void unhookFromModel(EObject model) {
		super.unhookFromModel(model);

		ItemElement realItem = getCastedModel();
		if(realItem instanceof ModelDiagram) {
			SubDiagram subDiagram = ((ModelDiagram)realItem).getSubDiagram();
			if(subDiagram != null) {
				subDiagram.eAdapters().remove(this);
			}
		}
	}

	/**
	 * EditPart 에 대한 Policy 를 적용한다.
	 * @see org.eclipse.gef.editparts.AbstractTreeEditPart#createEditPolicies()
	 * 
	 * @Override
	 */
	protected void createEditPolicies() {
		// If this editpart is the root content of the viewer, then disallow removal
		if (getParent() instanceof RootEditPart) {
			installEditPolicy(EditPolicy.COMPONENT_ROLE, new RootComponentEditPolicy());
		}
	}

	/**
	 * Object 타입의 모델을 EditPart 의 모델 타입으로 캐스팅한다.
	 * @return 모델
	 * 
	 * @Override
	 */
	public ModelDiagram getCastedModel() {
		return (ModelDiagram) getModel();
	}
	
	/**
	 * EditPart 모델의 자식 모델들을 반환한다. 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#getModelChildren()
	 * 
	 * @Override
	 */
	protected List getModelChildren() {
		EList<ItemElement> itemList = getCastedModel().getItems(); // a list of shapes

		ArrayList<ItemElement> childList = new ArrayList<ItemElement>();

		childList.addAll(itemList);
		
		ModelDiagram modelDiagram = getCastedModel();
		SubDiagram subDiagram = modelDiagram.getSubDiagram();
		List<ReferElement> refList = subDiagram.getItems();
		for(ReferElement refItem : refList) {
			ItemElement realItem = refItem.getRealModel();
			if(realItem.isIncluded()) {
				childList.add(realItem);
			}
		}

		sortList(childList);

		ArrayList<ItemElement> list = new ArrayList<ItemElement>();
		
		for(ItemElement item : childList) {
			if (item instanceof TaskElement) {	// KJH 20110706, Outline에 Task 추가
				list.add(item);
			}
			if(item instanceof BehaviorElement) {
				list.add(item);
			}
			else if(item instanceof ActionElement) {
				list.add(item);
			}
			else if (item instanceof ConnectorElement) {	// KJH 20101201 s, add connector
				list.add(item);
			}												// KJH 20101201 e, add connector
		}

		return list;
	}

	/**
	 * EditPart 의 자식 EditPart 들을 반환한다. 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#getChildren()
	 * 
	 * @Override
	 */
	public List getChildren() {
		List list = super.getChildren();

/*		Collections.sort(list, new Comparator<Object>() {

			@Override
			public int compare(Object o1, Object o2) {
				if(o1 instanceof TaskElementTreeEditPart) {
					if(o2 instanceof TaskElementTreeEditPart) {
						ItemElement item1 = (ItemElement)((ItemElementTreeEditPart)o1).getModel();
						ItemElement item2 = (ItemElement)((ItemElementTreeEditPart)o2).getModel();
						return item1.getName().compareTo(item2.getName());
					}
					else if(o2 instanceof ActionElementTreeEditPart) {
						return -1;
					}
					
					return 1;
				}
				else if(o1 instanceof ActionElementTreeEditPart) {
					if(o2 instanceof TaskElementTreeEditPart) {
						return 1;
					}
					else if(o2 instanceof ActionElementTreeEditPart) {
						ItemElement item1 = (ItemElement)((ItemElementTreeEditPart)o1).getModel();
						ItemElement item2 = (ItemElement)((ItemElementTreeEditPart)o2).getModel();
						return item1.getName().compareTo(item2.getName());
					}
					
					return -1;
				}
				
				return 0;
			}
			
		});
*/
		return list;
	}

	/**
	 * 모델의 Property 가 변경되면 호출된다.
	 * @param notification 모델의 변경 정보
	 */
	public void notifyChanged(Notification notification) {

		Object notifier = notification.getNotifier();
		if(notifier instanceof SubDiagram) {
			subDiagramNotifyChanged(notification);
			return;
		}

		int type = notification.getEventType();
		int featureId = notification.getFeatureID(TaskModelPackage.class);
		
		switch(type) {
		case Notification.SET:
//			sortList(getCastedModel().getItems());
			refreshChildren();
//			refreshVisuals();
			break;
		case Notification.ADD:
			switch(featureId) {
			default:
				super.notifyChanged(notification);
				sortList(getCastedModel().getItems());
				break;
			}
			break;
		case Notification.REMOVE:
			switch(featureId) {
			default:
				super.notifyChanged(notification);
				break;
			}
			break;
		}
	}
	
	/**
	 * ModelDiagram 의 Subdiagram 모델의 Property 가 변경되면 호출된다.
	 * @param notification 모델의 변경 정보
	 */
	protected void subDiagramNotifyChanged(Notification notification) {
		int type = notification.getEventType();
		int featureId = notification.getFeatureID(TaskModelPackage.class);

		switch(type) {
		case Notification.SET:
//			sortList(getCastedModel().getItems());
			refreshChildren();
//			refreshVisuals();
			break;
		case Notification.ADD:
			switch(featureId) {
			case TaskModelPackage.SUB_DIAGRAM__ITEMS:
//				ReferElement refItem = (ReferElement)notification.getNewValue();
//				ItemElement realItem = refItem.getRealModel();
//				ModelDiagram modelDiagram = getCastedModel();
//				modelDiagram.getItems().add(realItem);
				refreshChildren();
				break;
			default:
				super.notifyChanged(notification);
				sortList(getCastedModel().getItems());
				break;
			}
			break;
		case Notification.REMOVE:
			switch(featureId) {
			case TaskModelPackage.SUB_DIAGRAM__ITEMS:
//				ReferElement refItem = (ReferElement)notification.getOldValue();
//				ItemElement realItem = refItem.getRealModel();
//				ModelDiagram modelDiagram = getCastedModel();
//				modelDiagram.getItems().remove(realItem);
				refreshChildren();
				break;
			default:
				super.notifyChanged(notification);
				break;
			}
			break;
		}
	}
}
