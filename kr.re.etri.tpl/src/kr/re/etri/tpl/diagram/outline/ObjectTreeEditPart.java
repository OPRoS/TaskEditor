
package kr.re.etri.tpl.diagram.outline;

import java.util.Collections;
import java.util.List;

import kr.re.etri.tpl.IconStrings;
import kr.re.etri.tpl.TaskModelPlugin;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.gef.editparts.AbstractTreeEditPart;
import org.eclipse.swt.graphics.Image;

/**
 * �𵨷� �������� ���� Object �� ���� TreeEditPart Ŭ�����̴�.
 *
 * ���� Property ���濡 ���� ó���� PropertyChangeListener interface �� �����Ͽ�
 * �ݿ��� ���� ������ �� Ŭ������ Subclass ���� EMF �� Adapter interface �� �����Ͽ�
 * notifyChagned(Notification) �޼ҵ带 �̿��Ͽ� �ݿ��Ѵ�.
 * 
 * @author sfline
 */
public class ObjectTreeEditPart extends AbstractTreeEditPart implements Adapter {
	/** �Ӽ��� ����� Target �� */
	private Notifier target;

	/**
	 * Object �� ���� TreeEditPart �� �����Ѵ�.
	 * @param model a non-null Shapes instance
	 */
	public ObjectTreeEditPart(Object model) {
		super(model);
	}
	
	/**
	 * EditPart �� Ȱ��ȭ�Ǹ� �𵨷� ���� Notify �� �޵��� �Ѵ�.
	 */
	public void activate() {
		if (!isActive()) {
			super.activate();
		}
	}
	
	/**
	 * EditPart �� ��Ȱ��ȭ�Ǹ� �𵨷� ���� Notify �� ���� �ʵ��� �Ѵ�.
	 */
	public void deactivate() {
		if (isActive()) {
			super.deactivate();
		}
	}

	/**
	 * Parameter �� �־��� Ŭ����(�Ǵ� �������̽�)�� ���� Adapter �� �����Ѵ�.
	 * @param key Adapter ��û Ÿ��
	 */
	public Object getAdapter(Class key) {
		return super.getAdapter(key);
	}

	/**
	 * EditPart ���� �ڽ� �𵨵��� ��ȯ�Ѵ�. 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#getModelChildren()
	 * 
	 * @Override
	 */
	protected List getModelChildren() {
		return Collections.EMPTY_LIST;
	}

	/**
	 * Tree Node �� Icon �� ��ȯ�Ѵ�.
	 * baseImage �� �⺻���� Model �� ���¿� ���� Icon �� ���� �����Ѵ�. 
	 * @see org.eclipse.gef.editparts.AbstractTreeEditPart#getImage()
	 * 
	 * @Override
	 */
	protected Image getImage() {
		Image image = null;
		image = TaskModelPlugin.getImageDescriptor(IconStrings.PARAM_16).createImage();

		return image;
	}

	/**
	 * Tree Node �� ǥ�õ� �̸��� �����Ѵ�.
	 * @see org.eclipse.gef.editparts.AbstractTreeEditPart#getText()
	 * 
	 * @Override
	 */
	protected String getText() {
		Object object =  getModel();
		return object.toString();
	}

	/**
	 * ���� Property �� ����Ǹ� ȣ��ȴ�.
	 * @param notification ���� ���� ����
	 */
	public void notifyChanged(Notification notification) {
		// this will cause an invocation of getImage() and getText(), see below
		this.refreshChildren();
		this.refreshVisuals();
	}
	
	/**
	 * EditPart �� ���� Notifier �� ��ȯ�Ѵ�.
	 */
	public Notifier getTarget() {
		return target;
	}
	
	/**
	 * EditPart �� ���� Notifier �� �����Ѵ�.
	 */
	public void setTarget(Notifier newTarget) {
		target = newTarget;
	}
	
	/**
	 * �־��� ��ü�� ���� Adapter ����
	 * @param type Adapter ��û Ÿ��
	 */
	public boolean isAdapterForType(Object type) {
		return getModel() == type ? true : getModel().equals(type) ? true : false;
	}
}