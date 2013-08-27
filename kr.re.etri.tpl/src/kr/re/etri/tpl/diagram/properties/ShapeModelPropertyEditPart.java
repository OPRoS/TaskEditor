package kr.re.etri.tpl.diagram.properties;

import kr.re.etri.tpl.diagram.properties.policies.ComponentPropertyEditPolicy;
import kr.re.etri.tpl.diagram.properties.sources.ActionElementPropertySource;
import kr.re.etri.tpl.diagram.properties.sources.BehaviorElementPropertySource;
import kr.re.etri.tpl.diagram.properties.sources.ConnectorElementPropertySource;
import kr.re.etri.tpl.diagram.properties.sources.StateElementPropertySource;
import kr.re.etri.tpl.diagram.properties.sources.StructBlockPropertySource;
import kr.re.etri.tpl.diagram.properties.sources.TaskElementPropertySource;
import kr.re.etri.tpl.diagram.properties.sources.WithElementPropertySource;
import kr.re.etri.tpl.taskmodel.ActionElement;
import kr.re.etri.tpl.taskmodel.BehaviorElement;
import kr.re.etri.tpl.taskmodel.ConnectorElement;
import kr.re.etri.tpl.taskmodel.StateElement;
import kr.re.etri.tpl.taskmodel.StructBlockElement;
import kr.re.etri.tpl.taskmodel.TaskElement;
import kr.re.etri.tpl.taskmodel.TaskModelPackage;
import kr.re.etri.tpl.taskmodel.WithElement;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.FreeformLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.IPropertySource;

/**
 * ShapeElement �� ���� Properties View �� EditPart Ŭ�����̴�.
 * @author sfline
 *
 */
public class ShapeModelPropertyEditPart extends AbstractGraphicalEditPart implements Adapter {
	/** �Ӽ��� ����� Target �� */
	private Notifier target;
	/** �Ӽ� ���� ǥ���ϴ� ȭ�� ����� Container */
	private Composite control;

	/**
	 * Connection ���� Property �� ǥ���ϴ� EditPart �� �����Ѵ�.
	 * @param control �Ӽ� ���� ǥ���ϴ� ȭ�� ���
	 */
	public ShapeModelPropertyEditPart(Composite control) {
		this.control = control;
	}

	/**
	 * EditPart �� ���� Policy �� �����Ѵ�.
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#createEditPolicies()
	 */
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new ComponentPropertyEditPolicy());
	}

	/**
	 * EditPart �� ���� Notifier �� ��ȯ�Ѵ�.
	 */
	public Notifier getTarget() {
		return target;
	}

	/**
	 * �־��� ��ü�� ���� Adapter ����
	 * @param type Adapter ��û Ÿ��
	 */
	public boolean isAdapterForType(Object type) {
		return getModel() == type;
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
			switch(featureId) {
			case TaskModelPackage.MODEL_ELEMENT__NAME:
			case TaskModelPackage.MODEL_ELEMENT__DESCRIPTION:
//				((TaskPropertyForm)this.control).setValue(featureId, getAdapter(IPropertySource.class));
				break;
			case TaskModelPackage.SHAPE_ELEMENT__X:
			case TaskModelPackage.SHAPE_ELEMENT__Y:
			case TaskModelPackage.SHAPE_ELEMENT__WIDTH:
			case TaskModelPackage.SHAPE_ELEMENT__HEIGHT:
			case TaskModelPackage.SHAPE_ELEMENT__COLLAPSED:
//				((TaskPropertyForm)this.control).setValue(featureId, getAdapter(IPropertySource.class));
				break;
			}
			break;
		}
	}

	/**
	 * EditPart �� ���� Notifier �� �����Ѵ�.
	 * @param newTarget Notifier
	 */
	public void setTarget(Notifier newTarget) {
		target = newTarget;
	}
	
	/**
	 * ���� �Ӽ� ���� ����Ǵ� ��� Notify �� �޵��� Listener �� ����Ѵ�.
	 * @param model ��
	 */
	private void hookIntoModel(EObject model) {
		if(model != null) {
			model.eAdapters().add(this);
		}
	}
	
	/**
	 * ���� Notify �� ���� �ʵ��� Listener ����� �����Ѵ�.
	 * @param model ��
	 */
	private void unhookFromModel(EObject model) {
		if(model != null) {
			model.eAdapters().remove(this);
		}
	}
	
	/**
	 * EditPart �� Ȱ��ȭ�Ǹ� �𵨷� ���� Notify �� �޵��� �Ѵ�.
	 */
	public void activate() {
		if (!isActive()) {
			hookIntoModel((EObject)getModel());
			super.activate();
		}
	}

	/**
	 * EditPart �� ��Ȱ��ȭ�Ǹ� �𵨷� ���� Notify �� ���� �ʵ��� �Ѵ�.
	 */
	public void deactivate() {
		if (isActive()) {
			unhookFromModel((EObject)getModel());
			super.deactivate();
		}
	}

	/**
	 * Parameter �� �־��� Ŭ����(�Ǵ� �������̽�)�� ���� Adapter �� �����Ѵ�.
	 * @param key Adapter ��û Ÿ��
	 */
	public Object getAdapter(Class key) {
		if (IPropertySource.class == key) {
			Object model = getModel();
			if(model instanceof TaskElement) {
				return new TaskElementPropertySource((TaskElement)model);
			}
			else if(model instanceof BehaviorElement) {
				return new BehaviorElementPropertySource((BehaviorElement)model);
			}
			else if(model instanceof StateElement) {
				return new StateElementPropertySource((StateElement)model);
			}
			else if(model instanceof ActionElement) {
				return new ActionElementPropertySource((ActionElement)model);
			}
			// KJH 20110527 s, add
			else if (model instanceof ConnectorElement) {
				return new ConnectorElementPropertySource((ConnectorElement)model);
			}
			else if (model instanceof WithElement) {
				return new WithElementPropertySource((WithElement)model);
			}
			else if (model instanceof StructBlockElement) {
				return new StructBlockPropertySource((StructBlockElement)model);
			}
			// KJH 20110527 e, add

		}
		return super.getAdapter(key);
	}

	/**createFigure
	 * EditPart �� ���� Figure �� �����Ͽ� ��ȯ�Ѵ�.
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#createFigure()
	 */
	protected IFigure createFigure() {
		Figure f = new FreeformLayer();
		f.setBorder(new MarginBorder(10));
		f.setLayoutManager(new FreeformLayout());
		return f;
	}
}
