package kr.re.etri.tpl.diagram.properties;

import kr.re.etri.tpl.diagram.properties.sources.ConnectionPropertySource;
import kr.re.etri.tpl.taskmodel.ConnectionElement;

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
import org.eclipse.gef.editpolicies.RootComponentEditPolicy;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.IPropertySource;

/**
 * ConnectionElement �� ���� Properties View �� EditPart Ŭ�����̴�.
 * @author sfline
 *
 */
public class ConnectionPropertyEditPart extends AbstractGraphicalEditPart implements Adapter {
	/** �Ӽ��� ����� Target �� */
	private Notifier target;
	/** �Ӽ� ���� ǥ���ϴ� ȭ�� ����� Container */
	private Composite control;

	/**
	 * Connection ���� Property �� ǥ���ϴ� EditPart �� �����Ѵ�.
	 * @param control �Ӽ� ���� ǥ���ϴ� ȭ�� ���
	 */
	public ConnectionPropertyEditPart(Composite control) {
		this.control = control;
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
	 * ���� Property �� ����Ǹ� ȣ��ȴ�.
	 * @param notification ���� ���� ����
	 */
	public void notifyChanged(Notification notification) {
		refreshVisuals(); // this will cause an invocation of getImage() and getText(), see below
	}

	/**
	 * Parameter �� �־��� Ŭ����(�Ǵ� �������̽�)�� ���� Adapter �� �����Ѵ�.
	 * @param key Adapter ��û Ÿ��
	 */
	public Object getAdapter(Class key) {
		if (IPropertySource.class == key) {
			Object model = getModel();
			if(model instanceof ConnectionElement) {
				return new ConnectionPropertySource((ConnectionElement)model);
			}
		}
		return super.getAdapter(key);
	}

	/**
	 * EditPart �� ���� Policy �� �����Ѵ�.
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#createEditPolicies()
	 */
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new RootComponentEditPolicy());
	}

	/**createFigure
	 * EditPart �� ���� Figure �� �����Ͽ� ��ȯ�Ѵ�.
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#createFigure()
	 */
	protected IFigure createFigure() {
		Figure f = new FreeformLayer();
		f.setBorder(new MarginBorder(3));
		f.setLayoutManager(new FreeformLayout());
		return f;
	}
}
