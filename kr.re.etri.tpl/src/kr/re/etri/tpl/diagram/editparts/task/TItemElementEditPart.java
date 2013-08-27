package kr.re.etri.tpl.diagram.editparts.task;

import java.util.Collections;
import java.util.List;

import kr.re.etri.tpl.diagram.editors.TPLDiagramEditor;
import kr.re.etri.tpl.diagram.properties.sources.ItemElementPropertySource;
import kr.re.etri.tpl.diagram.util.MarkerLogger;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.ModelDiagram;
import kr.re.etri.tpl.taskmodel.ReferElement;
import kr.re.etri.tpl.taskmodel.SubDiagram;
import kr.re.etri.tpl.taskmodel.TaskModelPackage;

import org.eclipse.draw2d.IFigure;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.views.properties.IPropertySource;

/**
 * ModelDiagram �� child �𵨵�� Figure ���� control �ϴ� 
 * EditPart Ŭ�������� ���� Ŭ�����̴�.
 * ���� Property ���濡 ���� ó���� PropertyChangeListener interface �� �����Ͽ�
 * �ݿ��� ���� ������ �� Ŭ������ Subclass ���� EMF �� Adapter interface �� �����Ͽ�
 * notifyChagned(Notification) �޼ҵ带 �̿��Ͽ� �ݿ��Ѵ�.
 * 
 * @author sfline
 */
public abstract class TItemElementEditPart extends AbstractGraphicalEditPart
	implements Adapter, IPropertyChangeListener {
	/** �Ӽ��� ����� Target �� */
	private Notifier target;

	/**
	 * EditPart �� ���� Policy �� �����Ѵ�.
	 * @see org.eclipse.gef.editparts.AbstractEditPart#createEditPolicies()
	 * 
	 * @Override
	 */
	protected void createEditPolicies() {
	}
	
	/**
	 * �𵨿� ���� Figure �� �����Ͽ� ��ȯ�Ѵ�.
	 * @return �𵨿� ���� Figure
	 * 
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#createFigure()
	 */
	protected IFigure createFigure() {
		IFigure figure;
		figure = createFigureForModel();
		return figure;
	}
	
	/**
	 * �𵨿� ���� Figure �� �����Ͽ� �����Ѵ�.
	 * subclass �� �� �޼ҵ带 ������ �Ͽ����Ѵ�.
	 * @return �𵨿� ���� Figure
	 */
	protected IFigure createFigureForModel() {
		// if Shapes gets extended the conditions above must be updated
		throw new IllegalArgumentException();
	}
	
	/**
	 * EditPart �� Ȱ��ȭ�Ǹ� �𵨷� ���� Notify �� �޵��� �Ѵ�.
	 */
	public void activate() {
		if (!isActive()) {
			hookIntoModel(getCastedModel());
			super.activate();
		}
	}

	/**
	 * EditPart �� ��Ȱ��ȭ�Ǹ� �𵨷� ���� Notify �� ���� �ʵ��� �Ѵ�.
	 */
	public void deactivate() {
		if (isActive()) {
			unhookFromModel(getCastedModel());
			super.deactivate();
		}
	}

	/**
	 * ���� �Ӽ� ���� ����Ǵ� ��� Notify �� �޵��� Listener �� ����Ѵ�.
	 * @param model ��
	 */
	protected void hookIntoModel(EObject model) {
		if(model != null) {
			model.eAdapters().add(this);
			if(model instanceof ReferElement) {
				ItemElement realItem = ((ReferElement)model).getRealModel();
				realItem.eAdapters().add(this);
			}
			else if(model instanceof SubDiagram) {
				ItemElement realItem = ((SubDiagram)model).getParent();
				realItem.eAdapters().add(this);
			}
		}
	}
	
	/**
	 * ���� Notify �� ���� �ʵ��� Listener ����� �����Ѵ�.
	 * @param model ��
	 */
	protected void unhookFromModel(EObject model) {
		if(model != null) {
			model.eAdapters().remove(this);
			if(model instanceof ReferElement) {
				ItemElement realItem = ((ReferElement)model).getRealModel();
				realItem.eAdapters().remove(this);
			}
			else if(model instanceof SubDiagram) {
				ItemElement realItem = ((SubDiagram)model).getParent();
				realItem.eAdapters().remove(this);
			}
		}
	}

	/**
	 * EditParet Registry �� �𵨿� ���� EditPart �� ����Ѵ�.
	 * �߰����� ������� EditPart �� ����ϱ� ���ؼ� �޼ҵ带 Ȯ���Ѵ�.
	 * 
	 * @see org.eclipse.gef.EditPartViewer#getEditPartRegistry().
	 * @see org.eclipse.gef.editparts.AbstractEditPart#registerModel()
	 * 
	 * @Override
	 */
	protected void registerModel() {
		super.registerModel();
		
		// ���� Ŭ���������� ���� �𵨿� ���� EditPart�� ����ϹǷ�
		// ���� �𵨿� ���� EditPart�� ����Ͽ� �ش�.
		Object model = getModel();
		if(model instanceof ReferElement) {
			getViewer().getEditPartRegistry().put(((ReferElement)model).getRealModel(), this);
		}
	}

	/**
	 * Object Ÿ���� ���� EditPart �� �� Ÿ������ ĳ�����Ѵ�.
	 * @return ItemElement ��
	 */
	public ItemElement getCastedModel() {
		return (ItemElement) getModel();
	}

	/**
	 * �� ���� �� Problem Marker �� ����� Logger �� �����Ѵ�.
	 * @return Problem Marker Logger
	 */
	public MarkerLogger getLogger() {
		MarkerLogger problemLogger = null;
		
		EditPartViewer partViewer = this.getViewer();
		if(partViewer == null) {
			return null;
		}
		DefaultEditDomain editDomain = (DefaultEditDomain)partViewer.getEditDomain();
		if(editDomain == null) {
			return null;
		}
		IEditorPart editPart = editDomain.getEditorPart();
		if(editPart instanceof TPLDiagramEditor) {
			problemLogger = ((TPLDiagramEditor)editPart).getLogger();
		}

		return problemLogger;
	}
	
	/**
	 * EditPart �� �����ϴ� ���� �ֻ��� ���� ��ȯ�Ѵ�.
	 * @return �ֻ��� ��
	 */
	public ModelDiagram getRootModel() {
		EditPartViewer partViewer = this.getViewer();
		if(partViewer == null) {
			return null;
		}
		DefaultEditDomain editDomain = (DefaultEditDomain)partViewer.getEditDomain();
		if(editDomain == null) {
			return null;
		}
		
		IEditorPart editPart = editDomain.getEditorPart();
		if((editPart instanceof TPLDiagramEditor) == false) {
			return null;
		}
		
		ModelDiagram model;
		model = (ModelDiagram)((TPLDiagramEditor)editPart).getModel();
		
		return model;
	}

	/**
	 * ���� �ڽ� �𵨵��� ��ȯ�Ѵ�.
	 * @see org.eclipse.gef.editparts.AbstractEditPart#getModelChildren()
	 * 
	 * @Override
	 */
	protected List getModelChildren() {
		return Collections.EMPTY_LIST;
	}

	/**
	 * child �𵨿� ���� EditPart �� ��ȯ�Ѵ�.
	 * @param model child �� 
	 */
	protected EditPart getEditPartForChild(Object model) {
		int i;
		EditPart editPart;
		List children = getChildren();

		for (i = 0; i < children.size(); i++) {
			editPart = (EditPart)children.get(i);
			if(model == editPart.getModel()) {
				return editPart;
			}
		}
		
		return null;
	}

	/**
	 * ���� Property �� ����Ǹ� ȣ��ȴ�.
	 * @param notification ���� ���� ����
	 */
	public void notifyChanged(Notification notification) {
		int type = notification.getEventType();
		int featureId = notification.getFeatureID(TaskModelPackage.class);
		if(type == Notification.ADD || type == Notification.REMOVE) {
			switch(featureId) {
			case TaskModelPackage.MODEL_DIAGRAM__ITEMS:
				refreshChildren();
				break;
			}
		}
	}

	/**
	 * Parameter �� �־��� Ŭ����(�Ǵ� �������̽�)�� ���� Adapter �� �����Ѵ�.
	 * @param key Adapter ��û Ÿ��
	 * @return Parameter �� �־��� Ŭ����(�Ǵ� �������̽�)�� ���� Adapter
	 */
	public Object getAdapter(Class key) {
		if (IPropertySource.class == key) {
			Object model = getCastedModel();
			if(model instanceof ItemElement) {
				return new ItemElementPropertySource((ItemElement)model);
			}
		}
		return super.getAdapter(key);
	}

	/**
	 * EditPart �� ���� Notifier �� ��ȯ�Ѵ�.
	 * @return Notifier
	 */
	public Notifier getTarget() {
		return target;
	}
	
	/**
	 * EditPart �� ���� Notifier �� �����Ѵ�.
	 * @param newTarget Notifier
	 */
	public void setTarget(Notifier newTarget) {
		target = newTarget;
	}
	
	/**
	 * �־��� ��ü�� ���� Adapter ����
	 * @param type Adapter ��û Ÿ��
	 */
	public boolean isAdapterForType(Object type) {
		return (getModel().getClass() == type);
	}
	
	/**
	 * Property �� ����Ǹ� ȣ��ȴ�
	 * @param event Property ���� Event
	 */
	public void propertyChange(PropertyChangeEvent event) {
		// TODO Auto-generated method stub
		
	}
}