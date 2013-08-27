
package kr.re.etri.tpl.diagram.views.actionparts;

import java.util.ArrayList;
import java.util.List;

import kr.re.etri.tpl.taskmodel.ActionElement;
import kr.re.etri.tpl.taskmodel.EnumElement;
import kr.re.etri.tpl.taskmodel.IncludedElement;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.ModelDiagram;
import kr.re.etri.tpl.taskmodel.ModelElement;
import kr.re.etri.tpl.taskmodel.TaskModelPackage;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.RootEditPart;
import org.eclipse.gef.editpolicies.RootComponentEditPolicy;

/**
 * TreeEditPart for a ShapesDiagram instance.
 * This is used in the Outline View of the ShapesEditor.
 * <p>This edit part must implement the PropertyChangeListener interface, 
 * so it can be notified of property changes in the corresponding model element.
 * </p>
 * 
 * @author Elias Volanakis
 */
public class ANavDiagramEditPart extends ANavItemElementEditPart implements Adapter {
	
	private Notifier target;
	
	/** 
	 * Create a new instance of this edit part using the given model element.
	 * @param model a non-null ShapesDiagram instance
	 */
	public ANavDiagramEditPart(ModelDiagram model) {
		super(model);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.examples.shapes.parts.ShapeTreeEditPart#createEditPolicies()
	 */
	protected void createEditPolicies() {
		// If this editpart is the root content of the viewer, then disallow removal
		if (getParent() instanceof RootEditPart) {
			installEditPolicy(EditPolicy.COMPONENT_ROLE, new RootComponentEditPolicy());
		}
	}

	private ModelDiagram getCastedModel() {
		return (ModelDiagram) getModel();
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.gef.editparts.AbstractEditPart#getModelChildren()
	 */
	protected List getModelChildren() {
		List<ItemElement> list = new ArrayList<ItemElement>();

//		List<IncludedElement> incItemList = getCastedModel().getIncludeItems();
//		for(IncludedElement incItem : incItemList) {
//			list.addAll(getActionChildren(incItem));
//		}
//		List<ItemElement> itemList = getCastedModel().getItems();
//		for(ItemElement item : itemList) {
//			if(item instanceof IncludedElement) {
//				list.addAll(getActionChildren((IncludedElement)item));
//			}
//			else if(item instanceof ActionElement) {
//				list.add(item);
//			}
//		}

		List<IncludedElement> incItemList = getCastedModel().getIncludeItems();
		list.addAll(incItemList);

		List<ItemElement> itemList = getCastedModel().getItems();
		for(ItemElement item : itemList) {
			if(item instanceof IncludedElement) {
				list.add(item);
			}
		}
		for(ItemElement item : itemList) {
			if(item instanceof ActionElement) {
				list.add(item);
			}
		}

		return list;
	}

	@Override
	public List getChildren() {
		List list = super.getChildren();

		return list;
	}
	
	protected List<ItemElement> getActionChildren(IncludedElement parentItem) {
		ArrayList<ItemElement> actionList = new ArrayList<ItemElement>();

		List<ItemElement> itemList = parentItem.getItems();
		for(ItemElement item : itemList) {
			if(item instanceof IncludedElement) {
				actionList.addAll(getActionChildren((IncludedElement)item));
			}
			else if(item instanceof ActionElement) {
				actionList.add(item);
			}
		}

		return actionList;
	}

	private void addChildren(Object newObject) {
//		super.addChild(createChild(newObject), -1);
		if(newObject instanceof IncludedElement) {
			List<ItemElement> itemList = ((IncludedElement)newObject).getItems();
			for(ItemElement item : itemList) {
				if(item instanceof IncludedElement) {
					addChildren(item);
				}
				else if(item instanceof ActionElement) {
					super.addChild(createChild(item), -1);
				}
			}
		}
		else if(newObject instanceof ActionElement) {
			super.addChild(createChild(newObject), -1);
		}
	}

	private void removeChildren(Object oldObject) {
		EditPart editPart = getEditPartForChild(oldObject);
		if(editPart == null) {
			return;
		}
		
		removeChild(editPart);
	}

	public void notifyChanged(Notification notification) {
		int type = notification.getEventType();
		int featureId = notification.getFeatureID(TaskModelPackage.class);
		switch(type) {
		case Notification.SET:
			refreshVisuals();
			break;
		case Notification.ADD:
			switch(featureId)
			{
			case TaskModelPackage.MODEL_DIAGRAM__ITEMS:
//				addChild(createChild(notification.getNewValue()), -1);
//				break;
			case TaskModelPackage.MODEL_DIAGRAM__INCLUDE_ITEMS:
//				addChildren(notification.getNewValue());
				break;
			}
			refreshChildren();
			break;
		case Notification.REMOVE:
			switch(featureId)
			{
			case TaskModelPackage.MODEL_DIAGRAM__ITEMS:
			case TaskModelPackage.MODEL_DIAGRAM__INCLUDE_ITEMS:
//				removeChildren(notification.getOldValue());
				break;
			}
			refreshChildren();
			break;
		case Notification.ADD_MANY:
		case Notification.REMOVE_MANY:
			refreshChildren();
			break;
		}
	}
}
