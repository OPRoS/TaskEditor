
package kr.re.etri.tpl.diagram.outline;

import java.util.Collections;
import java.util.List;

import kr.re.etri.tpl.IconStrings;
import kr.re.etri.tpl.TaskModelPlugin;
import kr.re.etri.tpl.diagram.outline.policies.ShapeComponentTreeEditPolicy;
import kr.re.etri.tpl.diagram.properties.sources.StateActionPropertySource;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.StateAction;
import kr.re.etri.tpl.taskmodel.StateActionType;
import kr.re.etri.tpl.taskmodel.TaskModelPackage;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.gef.EditPolicy;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Pattern;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.ui.views.properties.IPropertySource;

/**
 * StateAction �� ���� TreeEditPart Ŭ�����̴�.
 *
 * ���� Property ���濡 ���� ó���� PropertyChangeListener interface �� �����Ͽ�
 * �ݿ��� ���� ������ �� Ŭ������ Subclass ���� EMF �� Adapter interface �� �����Ͽ�
 * notifyChagned(Notification) �޼ҵ带 �̿��Ͽ� �ݿ��Ѵ�.
 * 
 * @author sfline
 */
public class StateActionTreeEditPart extends ItemElementTreeEditPart {
	
	/**
	 * StateAction �𵨿� ���� TreeEditPart �� �����Ѵ�.
	 * @param model ActionElement instance
	 */
	public StateActionTreeEditPart(StateAction model) {
		super(model);
	}
	
	/**
	 * Parameter �� �־��� Ŭ����(�Ǵ� �������̽�)�� ���� Adapter �� �����Ѵ�.
	 * @param key Adapter ��û Ÿ��
	 */
	public Object getAdapter(Class key) {
		if (IPropertySource.class == key) {
			Object obj = getModel();
			if(obj instanceof StateAction) {
				return new StateActionPropertySource((StateAction)obj);
			}
		}
		return super.getAdapter(key);
	}

	/**
	 * EditPart �� ���� Policy �� �����Ѵ�.
	 * @see org.eclipse.gef.editparts.AbstractTreeEditPart#createEditPolicies()
	 * 
	 * @Override
	 */
	protected void createEditPolicies() {
		// allow removal of the associated model element
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new ShapeComponentTreeEditPolicy());
	}
	
	/**
	 * Object Ÿ���� ���� EditPart �� �� Ÿ������ ĳ�����Ѵ�.
	 * @return StateAction ��
	 * 
	 * @Override
	 */
	protected StateAction getCastedModel() {
		return (StateAction) getModel();
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
	 * baseImage �� �⺻���� ���� ���¿� ���� Image �� ���� �����Ѵ�. 
	 * @param shape ��
	 * @return Image
	 * 
	 * @Override
	 */
	protected Image createImageForModel(ItemElement shape) {
		Image baseImage;
		
		baseImage = getBaseImage();
		if(baseImage == null) {
			if(((StateAction)shape).getStateActionType() == StateActionType.ENTRY) {
				baseImage = TaskModelPlugin.getImageDescriptor(IconStrings.ENTRY_16).createImage();
			}
			else if(((StateAction)shape).getStateActionType() == StateActionType.STAY) {
				baseImage = TaskModelPlugin.getImageDescriptor(IconStrings.STAY_16).createImage();
			}
			else if(((StateAction)shape).getStateActionType() == StateActionType.EXIT) {
				baseImage = TaskModelPlugin.getImageDescriptor(IconStrings.EXIT_16).createImage();
			}

			setBaseImage(baseImage);
		}

		Rectangle boundingRect = baseImage.getBounds();
		Image image = new Image(null, boundingRect.width, boundingRect.height);
		if(shape instanceof StateAction) {
			GC imageGc = new GC(image);

			Pattern bgPattern = new Pattern(image.getDevice(), boundingRect.x, boundingRect.y,
					boundingRect.width, boundingRect.height, 
					ColorConstants.white, 0x00, ColorConstants.white, 0x00);

			imageGc.setBackgroundPattern(bgPattern);

			imageGc.drawImage(baseImage, 0, 0);

			if(shape.isIncluded()) {
				FontData []fd = new FontData[]{new FontData("Candara", 8, SWT.BOLD)};
				Font font = new Font(null, fd);
				imageGc.setFont(font);

				imageGc.setForeground(readOnlyColor);
				imageGc.drawString("R", 1, 5);
			}

			imageGc.setBackgroundPattern(null);

			bgPattern.dispose();
			imageGc.dispose();
		}

		return image;
	}

	/**
	 * ���� Property �� ����Ǹ� ȣ��ȴ�.
	 * @param notification ���� ���� ����
	 */
	public void notifyChanged(Notification notification) {
		int type = notification.getEventType();
		int featureId = notification.getFeatureID(TaskModelPackage.class);
		
		switch(type) {
		case Notification.SET:
			refreshVisuals();
			break;
		case Notification.ADD:
			switch(featureId) {
			case TaskModelPackage.STATE_ACTION__STATEMENTS:
				break;
			default:
				super.notifyChanged(notification);
				break;
			}
			break;
		case Notification.REMOVE:
			switch(featureId) {
			case TaskModelPackage.STATE_ACTION__STATEMENTS:
				break;
			default:
				super.notifyChanged(notification);
				break;
			}
			break;
		}
	}
}