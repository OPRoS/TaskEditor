
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
 * ModelDiagram �� ���� TreeEditPart Ŭ�����̴�.
 * 
 * @see kr.re.etri.tpl.diagram.outline.ItemElementTreeEditPart
 * 
 * @author sfline
 */
public class DiagramTreeEditPart extends ItemElementTreeEditPart {
	
	/**
	 * ModelDiagram �𵨿� ���� TreeEditPart �� �����Ѵ�.
	 * @param model ActionElement instance
	 */
	public DiagramTreeEditPart(ModelDiagram model) {
		super(model);
	}

	/**
	 * ���� �Ӽ� ���� ����Ǵ� ��� Notify �� �޵��� Listener �� ����Ѵ�.
	 * @param model ��
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
	 * ���� Notify �� ���� �ʵ��� Listener ����� �����Ѵ�.
	 * @param model ��
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
	 * EditPart �� ���� Policy �� �����Ѵ�.
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
	 * Object Ÿ���� ���� EditPart �� �� Ÿ������ ĳ�����Ѵ�.
	 * @return ��
	 * 
	 * @Override
	 */
	public ModelDiagram getCastedModel() {
		return (ModelDiagram) getModel();
	}
	
	/**
	 * EditPart ���� �ڽ� �𵨵��� ��ȯ�Ѵ�. 
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
			if (item instanceof TaskElement) {	// KJH 20110706, Outline�� Task �߰�
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
	 * EditPart �� �ڽ� EditPart ���� ��ȯ�Ѵ�. 
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
	 * ���� Property �� ����Ǹ� ȣ��ȴ�.
	 * @param notification ���� ���� ����
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
	 * ModelDiagram �� Subdiagram ���� Property �� ����Ǹ� ȣ��ȴ�.
	 * @param notification ���� ���� ����
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
