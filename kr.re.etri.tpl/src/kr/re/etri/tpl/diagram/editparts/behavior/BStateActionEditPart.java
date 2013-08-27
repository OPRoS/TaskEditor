
package kr.re.etri.tpl.diagram.editparts.behavior;

import java.util.Collections;
import java.util.List;

import kr.re.etri.tpl.IconStrings;
import kr.re.etri.tpl.TaskModelPlugin;
import kr.re.etri.tpl.diagram.DiagramConfiguration;
import kr.re.etri.tpl.diagram.properties.sources.StateActionPropertySource;
import kr.re.etri.tpl.taskmodel.ReferElement;
import kr.re.etri.tpl.taskmodel.StateAction;
import kr.re.etri.tpl.taskmodel.StateActionType;
import kr.re.etri.tpl.taskmodel.TaskModelPackage;

import org.apache.log4j.Logger;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.ImageFigure;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.views.properties.IPropertySource;

/**
 * StateAction �𵨰� Figure �� control �ϴ� EditPart Ŭ�����̴�.
 * 
 * @see BItemElementEditPart
 * 
 * @author sfline
 */
public class BStateActionEditPart extends BItemElementEditPart
	implements IPropertyChangeListener {
	private static Logger logger = Logger.getLogger(BStateActionEditPart.class);
	private Image entryImage,exitImage,stayImage ;

	/**
	 * StateAction �𵨿� ���� EditPart �� �����Ѵ�.
	 */
	public BStateActionEditPart() {
		super();
		ImageDescriptor imageDescriptor = TaskModelPlugin.getImageDescriptor(IconStrings.ENTRY_16);
		entryImage = imageDescriptor.createImage();
		imageDescriptor = TaskModelPlugin.getImageDescriptor(IconStrings.STAY_16);
		stayImage = imageDescriptor.createImage();
		imageDescriptor = TaskModelPlugin.getImageDescriptor(IconStrings.EXIT_16);
		exitImage = imageDescriptor.createImage();	
	}

	/**
	 * Object Ÿ���� ���� EditPart �� �� Ÿ������ ĳ�����Ѵ�.
	 * @return StateAction ���� ���� ��(ReferElement)
	 * 
	 * @Override
	 */
//	public ReferElement getCastedModel() {
//		LinkedElement ele =(LinkedElement)super.getCastedModel();
//		return (ReferElement)super.getCastedModel();
//	}

	/**
	 * EditPart �� ���� Policy �� �����Ѵ�.
	 * @see org.eclipse.gef.editparts.AbstractEditPart#createEditPolicies()
	 * 
	 * @Override
	 */
	protected void createEditPolicies() {
		// allow removal of the associated model element
//		installEditPolicy(EditPolicy.COMPONENT_ROLE, new BComponentEditPolicy());
	}
	

	/**
	 * �𵨿� ���� Figure �� �����Ͽ� �����Ѵ�.
	 * @return �𵨿� ���� Figure
	 * 
	 * @Override
	 */
	protected IFigure createFigureForModel() {
		logger.debug("Creating StateAction.");
		IFigure figure;
		Object o = getModel();
		if (o instanceof StateAction) {
			StateAction sa = (StateAction)o;
			logger.debug("Parent "+sa.getParent());
			if(sa.getStateActionType() ==StateActionType.ENTRY){
				figure = new ImageFigure(entryImage);
				figure.setOpaque(true);
				int width =  entryImage.getBounds().width;
				int hieght = entryImage.getBounds().height;
				figure.setMinimumSize(new Dimension(width, hieght));
				figure.setVisible(sa.isVisible());
				return figure;
			}else if(sa.getStateActionType() ==StateActionType.STAY){
				figure = new ImageFigure(stayImage);
				figure.setOpaque(true);
				int width =  stayImage.getBounds().width;
				int hieght = stayImage.getBounds().height;
				figure.setMinimumSize(new Dimension(width, hieght));
				figure.setVisible(sa.isVisible());
				return figure;				
			}else if(sa.getStateActionType() ==StateActionType.EXIT){
				figure = new ImageFigure(exitImage);
				figure.setOpaque(true);
				int width =  exitImage.getBounds().width;
				int hieght = exitImage.getBounds().height;
				figure.setMinimumSize(new Dimension(width, hieght));
				figure.setVisible(sa.isVisible());
				return figure;
			}
			else{
				logger.error("Unsupported StateActionType : "+sa.getStateActionType());
			}
		}
		
		figure = super.createFigureForModel();
		return figure;
	}
	private Rectangle evaluateStartLoc(ReferElement referE){
		int width = entryImage.getBounds().width;
		int height = entryImage.getBounds().height;
		int x = referE.getWidth()/2 -width*5/2;
		int y ;
		if(referE.getHeight() == 0){
			y = referE.getHeight2()/2+3;
		}
		else {
			y = referE.getHeight()/2+3;
		}
		
		
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
		logger.debug("refresh....");
		Object o = getModel();
		
		if(!(o instanceof StateAction)){
			return ;
		}
		StateAction sa =(StateAction)o;		
		IFigure figure= getFigure();
	
		DiagramConfiguration dgmCfg = DiagramConfiguration.getInstance();
		figure.setForegroundColor(dgmCfg.getItemFGColor(TaskModelPackage.STATE_ACTION, DiagramConfiguration.NONE));
		figure.setBorder(new MarginBorder(3));

		ReferElement refer = (ReferElement)this.getParent().getModel();
		Rectangle rec = evaluateStartLoc(refer);	
		if(sa.getStateActionType() == StateActionType.STAY){
			rec.setLocation(rec.x+entryImage.getBounds().width*2, rec.y);
		}
		else if(sa.getStateActionType() == StateActionType.EXIT){
			rec.setLocation(rec.x+entryImage.getBounds().width*4, rec.y);
		}
		logger.debug("x = "+rec.x+", y = "+rec.y+", width = "+rec.width+", height = "+rec.height);
		((GraphicalEditPart) this.getParent()).setLayoutConstraint(this, figure, rec);
		
		figure.setVisible(sa.isVisible());
		figure.repaint();
	//	this.setSelected(1);
	
	}
	
	/**
	 * ���� Property �� ����Ǹ� ȣ��ȴ�.
	 * @param notification ���� ���� ����
	 * 
	 * @Override
	 */
	public void notifyChanged(Notification notification) {
		logger.debug("Notification");
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
		logger.debug("PropertyChangeEvent");
		super.propertyChange(event);
	}
	
	
	public void performRequest(Request request) {
		logger.debug(request);
		if (request.getType() == RequestConstants.REQ_DIRECT_EDIT) {
		}else if (request.getType() == RequestConstants.REQ_OPEN) {
			
		}
	}
	
	@Override
	public boolean isSelectable() {	// KJH 20110614
		return getCastedModel().isVisible() && getParent().isSelectable();
	}
	
}