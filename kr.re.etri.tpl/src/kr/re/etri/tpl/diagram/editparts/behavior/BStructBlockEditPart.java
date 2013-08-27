package kr.re.etri.tpl.diagram.editparts.behavior;

import java.util.Collections;
import java.util.List;

import kr.re.etri.tpl.IconStrings;
import kr.re.etri.tpl.TaskModelPlugin;
import kr.re.etri.tpl.diagram.DiagramConfiguration;
import kr.re.etri.tpl.diagram.properties.sources.StructBlockPropertySource;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.ReferElement;
import kr.re.etri.tpl.taskmodel.StructBlockElement;
import kr.re.etri.tpl.taskmodel.StructType;
import kr.re.etri.tpl.taskmodel.TaskModelPackage;

import org.apache.log4j.Logger;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.ImageFigure;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.views.properties.IPropertySource;

// KJH 20110209, new
public class BStructBlockEditPart extends BItemElementEditPart implements
		IPropertyChangeListener {
	private static Logger logger = Logger.getLogger(BStructBlockEditPart.class);
	private Image consImage, desImage;

	/**
	 * StructBlockElement �𵨿� ���� EditPart �� �����Ѵ�.
	 */
	public BStructBlockEditPart() {
		super();
		ImageDescriptor imageDescriptor = TaskModelPlugin.getImageDescriptor(IconStrings.CONSTR_16);
		consImage = imageDescriptor.createImage();
		imageDescriptor = TaskModelPlugin.getImageDescriptor(IconStrings.DESTR_16);
		desImage = imageDescriptor.createImage();
	}

	/**
	 * Object Ÿ���� ���� EditPart �� �� Ÿ������ ĳ�����Ѵ�.
	 * @return StructBlockElement ���� ���� ��(ReferElement)
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
		logger.debug("Creating StructBlockElement.");
		IFigure figure;
		Object o = getModel();
		if (o instanceof StructBlockElement) {
			StructBlockElement sb = (StructBlockElement)o;
			logger.debug("Parent "+sb.getParent());
			if(sb.getStructType() == StructType.CONSTRUCT){	// KJH 20110418, task\initialize
				figure = new ImageFigure(consImage);
				figure.setOpaque(false);	// KJH 20110421, true->false
				int width =  consImage.getBounds().width;
				int hieght = consImage.getBounds().height;
				figure.setMinimumSize(new Dimension(width, hieght));
				figure.setVisible(sb.isVisible());
				return figure;
			}else if(sb.getStructType() == StructType.DESTRUCT){		// KJH 20110418, task\finalize
				figure = new ImageFigure(desImage);
				figure.setOpaque(false);	// KJH 20110421, true->false
				int width =  desImage.getBounds().width;
				int hieght = desImage.getBounds().height;
				figure.setMinimumSize(new Dimension(width, hieght));
				figure.setVisible(sb.isVisible());
				return figure;
			}
			else{
				logger.error("Unsupported StateActionType : "+sb.getStructType());
			}
		}
		
		figure = super.createFigureForModel();
		return figure;
	}
	
	/**
	 * 
	 * @param referE parent
	 * @param i index
	 * @return
	 */
	private Rectangle evaluateStartLoc(ReferElement referE, int i){
		ItemElement realItem = referE.getRealModel();
		
		int width = consImage.getBounds().width;
		int height = consImage.getBounds().height;
		int x;
		int y ;
		
		int iconCount, firstPos;
//		if (realItem instanceof TaskElement) {
//			iconCount = 3;
//			firstPos = -5;
//		} else {
			if (i == 2)	i = 1;
			iconCount = 2;
			firstPos = -3;
//		}
		
		int parentHeight, parentWidth;
		if (referE.getHeight() == 0) {
			parentHeight = referE.getHeight2();
			parentWidth = referE.getWidth2();
		} else {
			parentHeight = referE.getHeight();
			parentWidth = referE.getWidth();
		}
		
		if (referE.isCollapsed()) {
			x = (parentWidth / 2) + (width * (i*4+firstPos) / 2);
			y = (parentHeight - 18 - height) / 2 + 18;
		} else {
			x = parentWidth - ((width + 4) * (iconCount - i)) - 26;
			y = 4;
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
			return new StructBlockPropertySource((StructBlockElement)model);
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
		
		if(!(o instanceof StructBlockElement)){
			return ;
		}
		StructBlockElement sb =(StructBlockElement)o;
		IFigure figure= getFigure();		
	
		DiagramConfiguration dgmCfg = DiagramConfiguration.getInstance();
		figure.setForegroundColor(dgmCfg.getItemFGColor(TaskModelPackage.STRUCT_BLOCK_ELEMENT, DiagramConfiguration.NONE));
		figure.setBorder(new MarginBorder(3));

		int i = 0;
		if (sb.getStructType() == StructType.RUN) {
			i = 1;
		}
		if (sb.getStructType() == StructType.DESTRUCT) {
			i = 2;
		}
		
		EditPart parent = getParent();
		ReferElement refer = (ReferElement)parent.getModel();
		Rectangle rec = evaluateStartLoc(refer, i);
		
		logger.debug("x = "+rec.x+", y = "+rec.y+", width = "+rec.width+", height = "+rec.height);
		((GraphicalEditPart)parent).setLayoutConstraint(this, figure, rec);
		
		figure.setVisible(sb.isVisible());
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
		int type = notification.getEventType();
		int featureId = notification.getFeatureID(TaskModelPackage.class);
		switch(type) {
		case Notification.SET:
			switch(featureId) {
			case TaskModelPackage.STRUCT_BLOCK_ELEMENT__NAME:
			case TaskModelPackage.STRUCT_BLOCK_ELEMENT__DESCRIPTION:
			case TaskModelPackage.STRUCT_BLOCK_ELEMENT__STATEMENTS:
			case TaskModelPackage.STRUCT_BLOCK_ELEMENT__VISIBLE:
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
		return getCastedModel().isVisible();
	}
	
}
