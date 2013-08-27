package kr.re.etri.tpl.diagram.editparts.behavior;

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

import org.apache.log4j.Logger;
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
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.views.properties.IPropertySource;

/**
 * ModelDiagram 의 child 모델들과 Figure 들을 control 하는 
 * EditPart 클래스들의 상위 클래스이다.
 * 모델의 Property 변경에 대한 처리를 PropertyChangeListener interface 를 구현하여
 * 반영할 수도 있지만 이 클래스와 Subclass 들은 EMF 의 Adapter interface 를 구현하여
 * notifyChagned(Notification) 메소드를 이용하여 반영한다.
 * 
 * @author sfline
 */
public abstract class BItemElementEditPart extends AbstractGraphicalEditPart
	implements Adapter, IPropertyChangeListener, ISelectionChangedListener {
	private static Logger logger =Logger.getLogger(BItemElementEditPart.class);
	/** 속성이 변경된 Target 모델 */
	private Notifier target;


	/**
	 * EditPart 에 대한 Policy 를 적용한다.
	 * @see org.eclipse.gef.editparts.AbstractEditPart#createEditPolicies()
	 * 
	 * @Override
	 */
	protected void createEditPolicies() {
	}
	
	/**
	 * 모델에 대한 Figure 를 생성하여 반환한다.
	 * @return 모델에 대한 Figure
	 * 
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#createFigure()
	 */
	protected IFigure createFigure() {
		IFigure figure;
		figure = createFigureForModel();
		logger.debug("createFigure : "+figure);
		return figure;
	}
	
	/**
	 * 모델에 대한 Figure 를 생성하여 생성한다.
	 * subclass 는 이 메소드를 재정의 하여야한다.
	 * @return 모델에 대한 Figure
	 */
	protected IFigure createFigureForModel() {
		// if Shapes gets extended the conditions above must be updated
		throw new IllegalArgumentException();
	}
	
	/**
	 * EditPart 가 활성화되면 모델로 부터 Notify 를 받도록 한다.
	 */
	public void activate() {
		if (!isActive()) {
			hookIntoModel(getCastedModel());
			super.activate();
		}
	}

	/**
	 * EditPart 가 비활성화되면 모델로 부터 Notify 를 받지 않도록 한다.
	 */
	public void deactivate() {
		if (isActive()) {
			unhookFromModel(getCastedModel());
			super.deactivate();
		}
	}

	/**
	 * 모델의 속성 값이 변경되는 경우 Notify 를 받도록 Listener 로 등록한다.
	 * @param model 모델
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
			
			getViewer().addSelectionChangedListener(this);
		}
	}
	
	/**
	 * 모델의 Notify 를 받지 않도록 Listener 등록을 해제한다.
	 * @param model 모델
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
	 * EditParet Registry 에 모델에 대한 EditPart 를 등록한다.
	 * 추가적인 방법으로 EditPart 를 등록하기 위해서 메소드를 확장한다.
	 * 
	 * @see org.eclipse.gef.EditPartViewer#getEditPartRegistry().
	 * @see org.eclipse.gef.editparts.AbstractEditPart#registerModel()
	 * 
	 * @Override
	 */
	protected void registerModel() {
		super.registerModel();
		
		// 상위 클래스에서는 참조 모델에 대한 EditPart만 등록하므로
		// 실제 모델에 대한 EditPart를 등록하여 준다.
		Object model = getModel();
		if(model instanceof ReferElement) {
			getViewer().getEditPartRegistry().put(((ReferElement)model).getRealModel(), this);
		}
	}

	/**
	 * EditPart 에서 관리할 모델을 설정한다.
	 * @param model EditPart 에서 관리할 모델
	 */
	public void setModel(Object model) {
		super.setModel(model);
	}

	/**
	 * Object 타입의 모델을 EditPart 의 모델 타입으로 캐스팅한다.
	 * @return ItemElement 모델
	 */
	public ItemElement getCastedModel() {
		return (ItemElement) getModel();
	}

	/**
	 * 모델 편집 시 Problem Marker 을 기록할 Logger 를 제공한다.
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
	 * EditPart 가 관리하는 모델의 최상위 모델을 반환한다.
	 * @return 최상위 모델
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
	 * 모델의 자식 모델들을 반환한다.
	 * @see org.eclipse.gef.editparts.AbstractEditPart#getModelChildren()
	 * 
	 * @Override
	 */
	protected List getModelChildren() {
		return Collections.EMPTY_LIST;
	}

	/**
	 * child 모델에 대한 EditPart 를 반환한다.
	 * @param model child 모델 
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
	 * 모델의 Property 가 변경되면 호출된다.
	 * @param notification 모델의 변경 정보
	 */
	public void notifyChanged(Notification notification) {
		int type = notification.getEventType();
		int featureId = notification.getFeatureID(TaskModelPackage.class);
		
		// KJH 20110414 s,
		if (type == Notification.SET) {
			switch (featureId) {
			case TaskModelPackage.ITEM_ELEMENT__DESCRIPTION:
			case TaskModelPackage.ITEM_ELEMENT__ITEM_STATE:
			case TaskModelPackage.ITEM_ELEMENT__NAME:
			case TaskModelPackage.ITEM_ELEMENT__PARENT:
			case TaskModelPackage.ITEM_ELEMENT__VISIBLE:
				refreshVisuals();
				break;
			}
		}	// KJH 20110414 e,
		if(type == Notification.ADD || type == Notification.REMOVE) {
			switch(featureId) {
			case TaskModelPackage.MODEL_DIAGRAM__ITEMS:
				refreshChildren();
				break;
			}
		}
	}

	/**
	 * Parameter 로 주어진 클래스(또는 인터페이스)에 대한 Adapter 를 제공한다.
	 * @param key Adapter 요청 타입
	 * @return Parameter 로 주어진 클래스(또는 인터페이스)에 대한 Adapter
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
	 * EditPart 에 대한 Notifier 를 반환한다.
	 * @return Notifier
	 */
	public Notifier getTarget() {
		return target;
	}
	
	/**
	 * EditPart 에 대한 Notifier 를 설정한다.
	 * @param newTarget Notifier
	 */
	public void setTarget(Notifier newTarget) {
		target = newTarget;
	}
	
	/**
	 * 주어진 객체에 대한 Adapter 여부
	 * @param type Adapter 요청 타입
	 */
	public boolean isAdapterForType(Object type) {
		return (getModel().getClass() == type);
	}

	/**
	 * Property 가 변경되면 호출된다
	 * @param event Property 변경 Event
	 */
	public void propertyChange(PropertyChangeEvent event) {
	}
	
	/**
	 * child 모델의 Figure 선택이 변경되면 호출된다.
	 * @param event Figure 선택 변경 Event 
	 */
	public void selectionChanged(SelectionChangedEvent event) {
	}

}