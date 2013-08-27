
package kr.re.etri.tpl.diagram.outline;

import java.util.Collections;
import java.util.List;

import kr.re.etri.tpl.IconStrings;
import kr.re.etri.tpl.TaskModelPlugin;
import kr.re.etri.tpl.diagram.commands.ConnectionDeleteCommand;
import kr.re.etri.tpl.diagram.outline.policies.ShapeComponentTreeEditPolicy;
import kr.re.etri.tpl.diagram.properties.sources.ConnectionPropertySource;
import kr.re.etri.tpl.taskmodel.ConnectionElement;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.ReferElement;
import kr.re.etri.tpl.taskmodel.RelationShip;
import kr.re.etri.tpl.taskmodel.TaskModelPackage;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ConnectionEditPolicy;
import org.eclipse.gef.requests.GroupRequest;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Pattern;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.IPropertySourceProvider;

/**
 * ConnectionElement �� ���� TreeEditPart ũ�����̴�.
 * 
 * @see kr.re.etri.tpl.diagram.outline.ItemElementTreeEditPart
 * 
 * @author sfline
 */
public class ConnectionElementTreeEditPart extends ItemElementTreeEditPart {
	
	/**
	 * ConnectionElement �𵨿� ���� TreeEditPart �� �����Ѵ�.
	 * @param model ConnectionElement instance
	 */
	public ConnectionElementTreeEditPart(ConnectionElement model) {
		super(model);
	}
	
	/**
	 * Parameter �� �־��� Ŭ����(�Ǵ� �������̽�)�� ���� Adapter �� �����Ѵ�.
	 * @param key Adapter ��û Ÿ��
	 */
	public Object getAdapter(Class key) {
		if (IPropertySource.class == key) {
			Object obj = getModel();
			if(obj instanceof ConnectionElement) {
				return new ConnectionPropertySource((ConnectionElement)obj);
			}
		}
		else if (IPropertySourceProvider.class == key) {
			final Object model = getModel();
			if(model instanceof ConnectionElement) {
				return new IPropertySourceProvider() {
					public IPropertySource getPropertySource(Object object) {
						return new ConnectionPropertySource((ConnectionElement)model);		
					}
				};
			}
		}
		return super.getAdapter(key);
	}

	/**
	 * EditPart �� ���� Policy �� �����Ѵ�.
	 * @see org.eclipse.gef.editparts.AbstractTreeEditPart#createEditPolicies()
	 * @Override
	 */
	protected void createEditPolicies() {
		// allow removal of the associated model element
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new ShapeComponentTreeEditPolicy());
		installEditPolicy(EditPolicy.CONNECTION_ROLE, new ConnectionEditPolicy() {
			protected Command getDeleteCommand(GroupRequest request) {
				Object child = getHost().getModel();
				// Include�� ���� Connection�� ���� �Ұ��ϹǷ�
				if(child instanceof ConnectionElement) {
					ReferElement refItem = (ReferElement)((ConnectionElement)child).getSource();
					ItemElement realItem = refItem.getRealModel();
					if(realItem.isIncluded()) {
						return null;
					}
				}

				return new ConnectionDeleteCommand(getCastedModel());
			}
		});
	}
	
	/**
	 * Object Ÿ���� ���� EditPart �� �� Ÿ������ ĳ�����Ѵ�.
	 * @return ConnectionElement ��
	 */
	protected ConnectionElement getCastedModel() {
		return (ConnectionElement) getModel();
	}

	/**
	 * EditPart ���� �ڽ� �𵨵��� ��ȯ�Ѵ�. 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#getModelChildren()
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
			if(((ConnectionElement)shape).getRelationship() == RelationShip.TASK_CALL) {
				baseImage = TaskModelPlugin.getImageDescriptor(IconStrings.EXPAND_16).createImage();
			}
			else if(((ConnectionElement)shape).getRelationship() == RelationShip.TRANSITION) {
				baseImage = TaskModelPlugin.getImageDescriptor(IconStrings.TRANSITION_16).createImage();
			}
			else if(((ConnectionElement)shape).getRelationship() == RelationShip.ACTION_CALL) {
				baseImage = TaskModelPlugin.getImageDescriptor("icons/dotopenarrow16.gif").createImage();
			}
			setBaseImage(baseImage);
		}

		Rectangle boundingRect = baseImage.getBounds();
		Image image = new Image(null, boundingRect.width, boundingRect.height);
		if(shape instanceof ConnectionElement) {
			GC imageGc = new GC(image);

			Pattern bgPattern = new Pattern(image.getDevice(), boundingRect.x, boundingRect.y,
					boundingRect.width, boundingRect.height, 
					ColorConstants.white, 0x00, ColorConstants.white, 0x00);

			imageGc.setBackgroundPattern(bgPattern);

			imageGc.drawImage(baseImage, 0, 0);

			ItemElement source = ((ConnectionElement)shape).getSource2();
			if(source.isIncluded()) {
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
	 * Figure �� ǥ�õ� Text �� ��ȯ�Ѵ�.
	 * @return igure �� ǥ�õ� Text
	 */
	protected String getText() {
		ConnectionElement conn;
		conn = getCastedModel();

		ReferElement refItem = (ReferElement)conn.getTarget();
		ItemElement target = refItem != null ? refItem.getRealModel() : null;
		String name = target != null ? target.getName() : "?";
		if(conn.getRelationship() == RelationShip.TASK_CALL) {
			return String.format("Expand (%s)", name);	// KJH 20110214, Call for Behavior -> Expand
		}
		else if(conn.getRelationship() == RelationShip.TRANSITION) {
			return String.format("Moveto (%s)", name);
		}
		else if(conn.getRelationship() == RelationShip.ACTION_CALL) {
			return String.format("Call for Action (%s)", name);
		}
		return getCastedModel().getName();
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
			{
				break;
			}
			case Notification.ADD:
			{
				switch(featureId) {
					case TaskModelPackage.CONNECTION_ELEMENT__SOURCE:
						break;
				}
				break;
			}
			case Notification.REMOVE:
			{
				switch(featureId) {
					case TaskModelPackage.CONNECTION_ELEMENT__SOURCE:
						break;
				}
				break;
			}
		}

		refreshVisuals();
	}
}