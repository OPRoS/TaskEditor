
package kr.re.etri.tpl.diagram.editparts.task;

import java.util.List;

import kr.re.etri.tpl.diagram.editparts.behavior.BItemElementEditPart;
import kr.re.etri.tpl.diagram.editparts.task.policies.TComponentEditPolicy;
import kr.re.etri.tpl.diagram.properties.sources.ShapeElementPropertySource;
import kr.re.etri.tpl.taskmodel.ShapeElement;
import kr.re.etri.tpl.taskmodel.TaskModelPackage;

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
 * ShapeElement �𵨰� Figure �� control �ϴ� EditPart Ŭ�����̴�.
 * 
 * @see TItemElementEditPart
 * 
 * @author sfline
 */
public class TShapeElementEditPart extends TItemElementEditPart {

	/**
	 * ShapeElement �𵨿� ���� EditPart �� �����Ѵ�.
	 */
	public TShapeElementEditPart() {
		super();
	}

	/**
	 * Object Ÿ���� ���� EditPart �� �� Ÿ������ ĳ�����Ѵ�.
	 * @return ShapeElement ���� ���� ��(ReferElement)
	 * 
	 * @Override
	 */
	public ShapeElement getCastedModel() {
		return (ShapeElement)super.getCastedModel();
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
	 * ���� �ڽ� �𵨵��� ��ȯ�Ѵ�.
	 * @see org.eclipse.gef.editparts.AbstractEditPart#getModelChildren()
	 * 
	 * @Override
	 */
	protected List getModelChildren() {
		return super.getModelChildren();
	}

	/**
	 * Parameter �� �־��� Ŭ����(�Ǵ� �������̽�)�� ���� Adapter �� �����Ѵ�.
	 * @param key Adapter ��û Ÿ��
	 * @return Parameter �� �־��� Ŭ����(�Ǵ� �������̽�)�� ���� Adapter
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
	 * ���� �Ӽ� ���� �ٽ� ȭ�鿡 �ٽ� ǥ���Ѵ�.
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
			switch(featureId) {
			case TaskModelPackage.SHAPE_ELEMENT__X:
			case TaskModelPackage.SHAPE_ELEMENT__Y:
			case TaskModelPackage.SHAPE_ELEMENT__WIDTH:
			case TaskModelPackage.SHAPE_ELEMENT__HEIGHT:
			case TaskModelPackage.SHAPE_ELEMENT__COLLAPSED:
				refreshVisuals();
				break;
			}
			break;
		case Notification.ADD:
		case Notification.REMOVE:
			switch(featureId) {
			case TaskModelPackage.LINKED_ELEMENT__SOURCE_CONNECTIONS:
				refreshSourceConnections();
				break;
			case TaskModelPackage.LINKED_ELEMENT__TARGET_CONNECTIONS:
				refreshTargetConnections();
				break;
			case TaskModelPackage.TASK_ELEMENT__STATES:
				refreshChildren();
				break;
			}
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