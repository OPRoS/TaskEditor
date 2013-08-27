
package kr.re.etri.tpl.diagram.editparts.task;

import java.util.Collections;
import java.util.List;

import kr.re.etri.tpl.diagram.editparts.task.policies.TComponentEditPolicy;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.TaskModelPackage;

import org.eclipse.draw2d.Ellipse;
import org.eclipse.draw2d.IFigure;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.gef.EditPolicy;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;

/**
 * �𵨷� �������� ���� Object �� ���� EditPart Ŭ�����̴�.
 * 
 * @author sfline
 */
public class TObjectEditPart extends TItemElementEditPart
	implements IPropertyChangeListener {

	/**
	 * Object ���� EditPart �� �����Ѵ�.
	 */
	public TObjectEditPart() {
		super();
	}

	/**
	 * Object Ÿ���� ���� EditPart �� �� Ÿ������ ĳ�����Ѵ�.
	 * @return Object �� ItemElement �̸� ItemElement �׷��� ������ null
	 */
	public ItemElement getCastedModel() {
		if(getModel() instanceof ItemElement) {
			return (ItemElement)getModel();
		}
		return null;
	}

	/**
	 * EditPart �� ���� Policy �� �����Ѵ�.
	 * @see org.eclipse.gef.editparts.AbstractEditPart#createEditPolicies()
	 * 
	 * @Override
	 */
	protected void createEditPolicies() {
		// allow removal of the associated model element
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new TComponentEditPolicy());
	}
	
	/**
	 * �𵨿� ���� Figure �� �����Ͽ� �����Ѵ�.
	 * @return �𵨿� ���� Figure
	 * 
	 * @Override
	 */
	protected IFigure createFigureForModel() {
		IFigure figure;
		figure = new Ellipse();
		figure.setOpaque(true); // non-transparent figure
		return figure;

//		return super.createFigureForModel();
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
	 * Parameter �� �־��� Ŭ����(�Ǵ� �������̽�)�� ���� Adapter �� �����Ѵ�.
	 * @param key Adapter ��û Ÿ��
	 * @return Parameter �� �־��� Ŭ����(�Ǵ� �������̽�)�� ���� Adapter
	 */
	public Object getAdapter(Class key) {
		return super.getAdapter(key);
	}
	
	/**
	 * ���� �Ӽ� ���� �ٽ� ȭ�鿡 �ٽ� ǥ���Ѵ�.
	 * 
	 * @Override
	 */
	protected void refreshVisuals() {

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
			refreshVisuals();
			break;
		case Notification.ADD:
		case Notification.REMOVE:
			refreshChildren();
			break;
		}
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