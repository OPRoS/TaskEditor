
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
 * 모델로 정의하지 않은 Object 에 대한 TreeEditPart 클래스이다.
 *
 * 모델의 Property 변경에 대한 처리를 PropertyChangeListener interface 를 구현하여
 * 반영할 수도 있지만 이 클래스와 Subclass 들은 EMF 의 Adapter interface 를 구현하여
 * notifyChagned(Notification) 메소드를 이용하여 반영한다.
 * 
 * @author sfline
 */
public class ObjectTreeEditPart extends AbstractTreeEditPart implements Adapter {
	/** 속성이 변경된 Target 모델 */
	private Notifier target;

	/**
	 * Object 에 대한 TreeEditPart 를 생성한다.
	 * @param model a non-null Shapes instance
	 */
	public ObjectTreeEditPart(Object model) {
		super(model);
	}
	
	/**
	 * EditPart 가 활성화되면 모델로 부터 Notify 를 받도록 한다.
	 */
	public void activate() {
		if (!isActive()) {
			super.activate();
		}
	}
	
	/**
	 * EditPart 가 비활성화되면 모델로 부터 Notify 를 받지 않도록 한다.
	 */
	public void deactivate() {
		if (isActive()) {
			super.deactivate();
		}
	}

	/**
	 * Parameter 로 주어진 클래스(또는 인터페이스)에 대한 Adapter 를 제공한다.
	 * @param key Adapter 요청 타입
	 */
	public Object getAdapter(Class key) {
		return super.getAdapter(key);
	}

	/**
	 * EditPart 모델의 자식 모델들을 반환한다. 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#getModelChildren()
	 * 
	 * @Override
	 */
	protected List getModelChildren() {
		return Collections.EMPTY_LIST;
	}

	/**
	 * Tree Node 의 Icon 을 반환한다.
	 * baseImage 을 기본으로 Model 의 상태에 따라 Icon 을 새로 생성한다. 
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
	 * Tree Node 에 표시될 이름을 제공한다.
	 * @see org.eclipse.gef.editparts.AbstractTreeEditPart#getText()
	 * 
	 * @Override
	 */
	protected String getText() {
		Object object =  getModel();
		return object.toString();
	}

	/**
	 * 모델의 Property 가 변경되면 호출된다.
	 * @param notification 모델의 변경 정보
	 */
	public void notifyChanged(Notification notification) {
		// this will cause an invocation of getImage() and getText(), see below
		this.refreshChildren();
		this.refreshVisuals();
	}
	
	/**
	 * EditPart 에 대한 Notifier 를 반환한다.
	 */
	public Notifier getTarget() {
		return target;
	}
	
	/**
	 * EditPart 에 대한 Notifier 를 설정한다.
	 */
	public void setTarget(Notifier newTarget) {
		target = newTarget;
	}
	
	/**
	 * 주어진 객체에 대한 Adapter 여부
	 * @param type Adapter 요청 타입
	 */
	public boolean isAdapterForType(Object type) {
		return getModel() == type ? true : getModel().equals(type) ? true : false;
	}
}