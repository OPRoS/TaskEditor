
package kr.re.etri.tpl.diagram.editparts.task;

import java.util.Collections;
import java.util.List;

import kr.re.etri.tpl.TaskModelPlugin;
import kr.re.etri.tpl.diagram.editparts.behavior.BItemElementEditPart;
import kr.re.etri.tpl.diagram.editparts.task.policies.TComponentEditPolicy;
import kr.re.etri.tpl.diagram.properties.sources.StateActionPropertySource;
import kr.re.etri.tpl.taskmodel.ReferElement;
import kr.re.etri.tpl.taskmodel.StateAction;
import kr.re.etri.tpl.taskmodel.StateActionType;
import kr.re.etri.tpl.taskmodel.TaskModelPackage;

import org.apache.log4j.Logger;
import org.eclipse.draw2d.Ellipse;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.ImageFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.gef.EditPolicy;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.views.properties.IPropertySource;

/**
 * StateAction �𵨰� Figure �� control �ϴ� EditPart Ŭ�����̴�.
 * 
 * @see TItemElementEditPart
 * 
 * @author sfline
 */
public class TStateActionEditPart extends TItemElementEditPart
	implements IPropertyChangeListener {
	private static Logger logger = Logger.getLogger(TStateActionEditPart.class);
	private Image entryImage,exitImage,doImage ;

	/**
	 * StateAction �𵨿� ���� EditPart �� �����Ѵ�.
	 */
	public TStateActionEditPart() {
		super();
		ImageDescriptor imageDescriptor = TaskModelPlugin.getImageDescriptor("icons/stateactionentry.gif");
		entryImage = imageDescriptor.createImage();
		imageDescriptor = TaskModelPlugin.getImageDescriptor("icons/stateactiondo.gif");
		doImage = imageDescriptor.createImage();
		imageDescriptor = TaskModelPlugin.getImageDescriptor("icons/stateactionexit.gif");
		exitImage = imageDescriptor.createImage();	
	}

	/**
	 * Object Ÿ���� ���� EditPart �� �� Ÿ������ ĳ�����Ѵ�.
	 * @return StateAction ���� ���� ��(ReferElement)
	 * 
	 * @Override
	 */
	public StateAction getCastedModel() {
		return (StateAction)super.getCastedModel();
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
//	protected IFigure createFigureForModel() {
//
//		IFigure figure;
//		 if (getModel() instanceof StateAction) {
//			figure = new Ellipse();
//			figure.setOpaque(true); // non-transparent figure
//			return figure;
//		}
//		
//		figure = super.createFigureForModel();
//		return figure;
//	}
//	
	protected IFigure createFigureForModel() {	
	logger.debug("Creating StateAction.");
	Object o = getModel();	
	ReferElement refer = (ReferElement)this.getParent().getModel();
	
	if (o instanceof StateAction) {
		StateAction sa = (StateAction)o;
		logger.debug("Parent "+sa.getParent());
		if(sa.getStateActionType() ==StateActionType.ENTRY){
			figure = new ImageFigure(entryImage);
			figure.setOpaque(true);
			Rectangle rec = evaluateLoc(refer);
			logger.debug("Rectangle : x = "+rec.x+", y = "+rec.y+", width = "+rec.width+", height = "+rec.height);
			figure.setBounds(rec);
			
			figure.setVisible(true);
			return figure;
		}else if(sa.getStateActionType() ==StateActionType.STAY){
			figure = new ImageFigure(doImage);
			figure.setOpaque(true);
			figure.setVisible(refer.isVisible());
			return figure;				
		}else if(sa.getStateActionType() ==StateActionType.EXIT){
			figure = new ImageFigure(exitImage);
			figure.setOpaque(true);
			figure.setVisible(refer.isVisible());
			return figure;
		}
		else{
			logger.error("Unsupported StateActionType : "+sa.getStateActionType());
		}
	}
	
	figure = super.createFigureForModel();
	return figure;
}
private Rectangle evaluateLoc(ReferElement referE){
	int width = entryImage.getBounds().width;
	int height = entryImage.getBounds().height;
	int x = referE.getX()+referE.getWidth()/2 -width*2;
	int y = referE.getY()+referE.getHeight()*3/4;
	
	return new Rectangle(x,y, width, height);	
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
		if (IPropertySource.class == key) {
			Object model = getModel();
			return new StateActionPropertySource((StateAction)model);
		}
		return super.getAdapter(key);
	}
	
	/**
	 * ���� �Ӽ� ���� �ٽ� ȭ�鿡 �ٽ� ǥ���Ѵ�.
	 * 
	 * @Override
	 */
	protected void refreshVisuals() {
		StateAction model = getCastedModel();
		IFigure figure = getFigure();

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
			case TaskModelPackage.STATE_ACTION__NAME:
			case TaskModelPackage.STATE_ACTION__DESCRIPTION:
			case TaskModelPackage.STATE_ACTION__STATEMENTS:
			case TaskModelPackage.STATE_ACTION__VISIBLE:
				refreshVisuals();
				break;
			}
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