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
 * ShapeElement 에 대한 Properties View 의 EditPart 클래스이다.
 * @author sfline
 *
 */
public class ShapeModelPropertyEditPart extends AbstractGraphicalEditPart implements Adapter {
	/** 속성이 변경된 Target 모델 */
	private Notifier target;
	/** 속성 값을 표시하는 화면 요소의 Container */
	private Composite control;

	/**
	 * Connection 모델의 Property 를 표시하는 EditPart 를 생성한다.
	 * @param control 속성 값을 표시하는 화면 요소
	 */
	public ShapeModelPropertyEditPart(Composite control) {
		this.control = control;
	}

	/**
	 * EditPart 에 대한 Policy 를 적용한다.
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#createEditPolicies()
	 */
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new ComponentPropertyEditPolicy());
	}

	/**
	 * EditPart 에 대한 Notifier 를 반환한다.
	 */
	public Notifier getTarget() {
		return target;
	}

	/**
	 * 주어진 객체에 대한 Adapter 여부
	 * @param type Adapter 요청 타입
	 */
	public boolean isAdapterForType(Object type) {
		return getModel() == type;
	}
	
	/**
	 * 모델의 Property 가 변경되면 호출된다.
	 * @param notification 모델의 변경 정보
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
	 * EditPart 에 대한 Notifier 를 설정한다.
	 * @param newTarget Notifier
	 */
	public void setTarget(Notifier newTarget) {
		target = newTarget;
	}
	
	/**
	 * 모델의 속성 값이 변경되는 경우 Notify 를 받도록 Listener 로 등록한다.
	 * @param model 모델
	 */
	private void hookIntoModel(EObject model) {
		if(model != null) {
			model.eAdapters().add(this);
		}
	}
	
	/**
	 * 모델의 Notify 를 받지 않도록 Listener 등록을 해제한다.
	 * @param model 모델
	 */
	private void unhookFromModel(EObject model) {
		if(model != null) {
			model.eAdapters().remove(this);
		}
	}
	
	/**
	 * EditPart 가 활성화되면 모델로 부터 Notify 를 받도록 한다.
	 */
	public void activate() {
		if (!isActive()) {
			hookIntoModel((EObject)getModel());
			super.activate();
		}
	}

	/**
	 * EditPart 가 비활성화되면 모델로 부터 Notify 를 받지 않도록 한다.
	 */
	public void deactivate() {
		if (isActive()) {
			unhookFromModel((EObject)getModel());
			super.deactivate();
		}
	}

	/**
	 * Parameter 로 주어진 클래스(또는 인터페이스)에 대한 Adapter 를 제공한다.
	 * @param key Adapter 요청 타입
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
	 * EditPart 에 대한 Figure 를 생성하여 반환한다.
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#createFigure()
	 */
	protected IFigure createFigure() {
		Figure f = new FreeformLayer();
		f.setBorder(new MarginBorder(10));
		f.setLayoutManager(new FreeformLayout());
		return f;
	}
}
