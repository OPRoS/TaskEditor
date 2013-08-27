
package kr.re.etri.tpl.diagram.editparts.behavior;

import java.util.List;

import kr.re.etri.tpl.diagram.editparts.behavior.policies.BComponentEditPolicy;
import kr.re.etri.tpl.diagram.properties.sources.ShapeElementPropertySource;
import kr.re.etri.tpl.taskmodel.ShapeElement;
import kr.re.etri.tpl.taskmodel.TaskModelPackage;

import org.apache.log4j.Logger;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.ScrollPane;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.ui.views.properties.IPropertySource;

/**
 * ShapeElement 모델과 Figure 을 control 하는 EditPart 클래스이다.
 * 
 * @see BItemElementEditPart
 * 
 * @author sfline
 */
public class BShapeElementEditPart extends BItemElementEditPart {
	private static Logger logger = Logger.getLogger(BShapeElementEditPart.class);

	/**
	 * ShapeElement 모델에 대한 EditPart 를 생성한다.
	 */
	public BShapeElementEditPart() {
		super();
	}

	/**
	 * Object 타입의 모델을 EditPart 의 모델 타입으로 캐스팅한다.
	 * @return ShapeElement 모델의 참조 모델(ReferElement)
	 * 
	 * @Override
	 */
	public ShapeElement getCastedModel() {
		return (ShapeElement)super.getCastedModel();
	}

	/**
	 * EditPart 에 대한 Policy 를 적용한다.
	 * @see org.eclipse.gef.editparts.AbstractEditPart#createEditPolicies()
	 * 
	 * @Override
	 */
	protected void createEditPolicies() {
		// allow removal of the associated model element
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new BComponentEditPolicy());
	}
	
	/**
	 * 모델에 대한 Figure 를 생성하여 생성한다.
	 * @return 모델에 대한 Figure
	 * 
	 * @Override
	 */
	protected IFigure createFigureForModel() {

		IFigure figure;
		if (getModel() instanceof ShapeElement) {
			figure = new ScrollPane();

			figure.setBorder(new MarginBorder(2));
			figure.setLayoutManager(new XYLayout());
			figure.setBackgroundColor(new Color(null, 245, 245, 150));
			figure.setOpaque(true); // non-transparent figure
			return figure;
		}
		
		figure = super.createFigureForModel();
		return figure;
	}
	
	/**
	 * 모델의 자식 모델들을 반환한다.
	 * @see org.eclipse.gef.editparts.AbstractEditPart#getModelChildren()
	 * 
	 * @Override
	 */
	protected List getModelChildren() {
		return super.getModelChildren();
	}

	/**
	 * Parameter 로 주어진 클래스(또는 인터페이스)에 대한 Adapter 를 제공한다.
	 * @param key Adapter 요청 타입
	 * @return Parameter 로 주어진 클래스(또는 인터페이스)에 대한 Adapter
	 */
	public Object getAdapter(Class key) {
		if (IPropertySource.class == key) {
			Object model = getModel();
			if(model instanceof ShapeElement) {
				return new ShapeElementPropertySource((ShapeElement)model);
			}
		}
		return super.getAdapter(key);
	}
	
	/**
	 * 모델의 속성 값을 다시 화면에 다시 표시한다.
	 * 
	 * @Override
	 */
	protected void refreshVisuals() {
		ShapeElement model = getCastedModel();
		IFigure figure = getFigure();

		// notify parent container of changed position & location
		// if this line is removed, the XYLayoutManager used by the parent container 
		// (the Figure of the ShapesDiagramEditPart), will not know the bounds of this figure
		// and will not draw it correctly.
		Rectangle bounds = new Rectangle(
				model.getX(),
				model.getY(),
				model.getWidth(),
				model.getHeight());
		((GraphicalEditPart) getParent()).setLayoutConstraint(this, figure, bounds);

//		if(figure instanceof RoundedRectangle) {
//			int cx = ((RoundRectanguleShape)linkedShape).getCx();
//			int cy = ((RoundRectanguleShape)linkedShape).getCy();
//			((RoundedRectangle)figure).setCornerDimensions(new Dimension(cx, cy));
//			((RoundedRectangle)figure).repaint();
//		}
	}
	
	/**
	 * 모델의 Property 가 변경되면 호출된다.
	 * @param notification 모델의 변경 정보
	 * 
	 * @Override
	 */
	public void notifyChanged(Notification notification) {
		int type = notification.getEventType();
		int featureId = notification.getFeatureID(TaskModelPackage.class);
		switch(type) {
		case Notification.SET:
			switch(featureId) {
			case TaskModelPackage.SHAPE_ELEMENT__X:
			case TaskModelPackage.SHAPE_ELEMENT__Y:
			case TaskModelPackage.SHAPE_ELEMENT__WIDTH:
			case TaskModelPackage.SHAPE_ELEMENT__HEIGHT:
				addNotify();
//				refreshChildren();
				break;
			case TaskModelPackage.SHAPE_ELEMENT__COLLAPSED:
				addNotify();	// KJH 20110603,
//				refreshVisuals();
				break;
			default:
				super.notifyChanged(notification);
				break;
			}
			break;
		default:
			super.notifyChanged(notification);
			break;
		}
	}

	/**
	 * Property 가 변경되면 호출된다
	 * @param event Property 변경 Event
	 * 
	 * @Override
	 */
	public void propertyChange(PropertyChangeEvent event) {
		super.propertyChange(event);
	}

}