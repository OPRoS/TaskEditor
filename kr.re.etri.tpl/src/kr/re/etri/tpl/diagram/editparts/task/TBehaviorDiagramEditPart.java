package kr.re.etri.tpl.diagram.editparts.task;

import java.util.ArrayList;
import java.util.List;

import kr.re.etri.tpl.diagram.DiagramConfiguration;
import kr.re.etri.tpl.diagram.editparts.behavior.BItemElementEditPart;
import kr.re.etri.tpl.diagram.editparts.task.policies.TXYLayoutEditPolicy;
import kr.re.etri.tpl.diagram.properties.sources.ModelDiagramPropertySource;
import kr.re.etri.tpl.diagram.properties.sources.TaskElementPropertySource;
import kr.re.etri.tpl.taskmodel.ActionElement;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.ModelDiagram;
import kr.re.etri.tpl.taskmodel.ReferElement;
import kr.re.etri.tpl.taskmodel.SubDiagram;
import kr.re.etri.tpl.taskmodel.BehaviorElement;
import kr.re.etri.tpl.taskmodel.TaskModelPackage;
import kr.re.etri.tpl.taskmodel.TaskElement;

import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.FreeformLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editpolicies.RootComponentEditPolicy;
import org.eclipse.gef.editpolicies.SelectionEditPolicy;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.IPropertySourceProvider;

/**
 * TaskDiagram �𵨰� Figure �� control �ϴ� EditPart Ŭ�����̴�.
 * �� EditPart ���� �����ϴ� Figure �� ���̾�׷� Editor �̴�.
 * 
 * @see TItemElementEditPart
 * 
 * @author sfline
 */
public class TBehaviorDiagramEditPart extends TItemElementEditPart {

	/**
	 * EditPart �� ���� Policy �� �����Ѵ�.
	 * @see org.eclipse.gef.editparts.AbstractEditPart#createEditPolicies()
	 * 
	 * @Override
	 */
	protected void createEditPolicies() {
		super.createEditPolicies();

		// disallows the removal of this edit part from its parent
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new RootComponentEditPolicy());
		// handles constraint changes (e.g. moving and/or resizing) of model elements
		// and creation of new model elements
		installEditPolicy(EditPolicy.LAYOUT_ROLE,  new TXYLayoutEditPolicy(this));
		
		installEditPolicy(EditPolicy.SELECTION_FEEDBACK_ROLE, new SelectionEditPolicy(){

			@Override
			protected void hideSelection() {
				// TODO Auto-generated method stub
				
			}

			@Override
			protected void showSelection() {
				// TODO Auto-generated method stub
				
			} 
			
		});
	}
	
	/**
	 * �𵨿� ���� Figure �� �����Ͽ� �����Ѵ�.
	 * @return �𵨿� ���� Figure
	 * 
	 * @Override
	 */
	protected IFigure createFigureForModel()
	{
		IFigure figure;
		figure = new FreeformLayer();
		figure.setBorder(new MarginBorder(3));
		figure.setLayoutManager(new FreeformLayout());
		
		DiagramConfiguration dgmCfg = DiagramConfiguration.getInstance();
		figure.setBackgroundColor(dgmCfg.getItemBGColor(TaskModelPackage.MODEL_DIAGRAM, DiagramConfiguration.NONE));
		
		return figure;
	}

	/**
	 * Object Ÿ���� ���� EditPart �� �� Ÿ������ ĳ�����Ѵ�.
	 * @return ModelDiagram ���� ���� ��(SubDiagram)
	 * 
	 * @Override
	 */
	public SubDiagram getCastedModel() {
		return (SubDiagram) getModel();
	}

	/**
	 * ���� ��(ReferElement)�� ���� ���� ��ȯ�Ѵ�.
	 * @return ItemElement ��
	 */
	public ItemElement getRealModel() {
		SubDiagram refItem = getCastedModel();
		return (ItemElement)refItem.getParent();
	}
	
	/**
	 * �־��� Object �� child �ΰ��� Ȯ���Ѵ�.
	 * @param child child ���θ� Ȯ���� ���
	 * @return
	 */
	public boolean isChild(Object child) {
		EditPart editPart;
		List children = getChildren();

		for (int i = 0; i < children.size(); i++) {
			editPart = (EditPart)children.get(i);
			ItemElement childModel = (ItemElement)editPart.getModel();
			if(child == childModel) {
				return true;
			}
			else if(childModel instanceof ReferElement) {
				ItemElement realItem = (ItemElement)((ReferElement)childModel).getRealModel();
				if(child == realItem) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * ���� �ڽ� �𵨵��� ��ȯ�Ѵ�.
	 * @see org.eclipse.gef.editparts.AbstractEditPart#getModelChildren()
	 * 
	 * @Override
	 */
	protected List getModelChildren() {
		EList<ReferElement> itemList = getCastedModel().getItems(); // return a list of elements
		ArrayList<ItemElement> childList = new ArrayList<ItemElement>();
		for(ReferElement refItem : itemList) {
			ItemElement item = refItem.getRealModel();
			if(item instanceof BehaviorElement) {
				childList.add(refItem);
			}
			else if(item instanceof ActionElement) {
				childList.add(refItem);
			}
		}

		return childList;
	}
	
	/**
	 * ���� Property �� ����Ǹ� ȣ��ȴ�.
	 * @param notification ���� ���� ����
	 * 
	 * @Override
	 */
	public void notifyChanged(Notification notification) {
		int type = notification.getEventType();
		int featureId = notification.getFeatureID(TaskModelPackage.class);
		switch(type) {
		case Notification.SET:
			refreshChildren();
			break;
			
		case Notification.ADD:
			switch(featureId) {
			case TaskModelPackage.SUB_DIAGRAM__ITEMS:
				refreshChildren();
				break;
			case TaskModelPackage.MODEL_DIAGRAM__INCLUDE_ITEMS:
				refreshChildren();
				break;
			}
			break;
		case Notification.REMOVE:
			switch(featureId) {
			case TaskModelPackage.SUB_DIAGRAM__ITEMS:
				refreshChildren();
				break;
			case TaskModelPackage.MODEL_DIAGRAM__INCLUDE_ITEMS:
				refreshChildren();
				break;
			}
			break;
		}
	}

	/**
	 * Parameter �� �־��� Ŭ����(�Ǵ� �������̽�)�� ���� Adapter �� �����Ѵ�.
	 * @param key Adapter ��û Ÿ��
	 * @return Parameter �� �־��� Ŭ����(�Ǵ� �������̽�)�� ���� Adapter
	 */
	public Object getAdapter(Class key) {
		if (IPropertySource.class == key) {
			Object model = getRealModel();
			if(model instanceof ModelDiagram) {
				return new ModelDiagramPropertySource((ModelDiagram)model);
			}
			else if(model instanceof TaskElement) {
				return new TaskElementPropertySource((TaskElement)model);
			}
		}
		if (IPropertySourceProvider.class == key) {
			final Object model = getModel();
			if(model instanceof ModelDiagram) {
				return new IPropertySourceProvider() {
					public IPropertySource getPropertySource(Object object) {
						return new ModelDiagramPropertySource((ModelDiagram)model);		
					}
				};
			}
		}
		return super.getAdapter(key);
	}

	/**
	 * Property �� ����Ǹ� ȣ��ȴ�
	 * @param event Property ���� Event
	 * 
	 * @Override
	 */
	public void propertyChange(PropertyChangeEvent event) {
		super.propertyChange(event);
	}
}