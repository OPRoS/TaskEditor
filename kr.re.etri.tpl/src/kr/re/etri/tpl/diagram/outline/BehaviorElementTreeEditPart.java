
package kr.re.etri.tpl.diagram.outline;

import java.util.ArrayList;
import java.util.List;

import kr.re.etri.tpl.IconStrings;
import kr.re.etri.tpl.TaskModelPlugin;
import kr.re.etri.tpl.diagram.outline.policies.ShapeComponentTreeEditPolicy;
import kr.re.etri.tpl.diagram.properties.sources.BehaviorElementPropertySource;
import kr.re.etri.tpl.taskmodel.ConnectionElement;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.ModelDiagram;
import kr.re.etri.tpl.taskmodel.ReferElement;
import kr.re.etri.tpl.taskmodel.BehaviorElement;
import kr.re.etri.tpl.taskmodel.TaskModelPackage;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.gef.EditPolicy;
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
 * TaskElement �� ���� TreeEditPart Ŭ�����̴�.
 * 
 * @see kr.re.etri.tpl.diagram.outline.ItemElementTreeEditPart
 * 
 * @author sfline
 */
public class BehaviorElementTreeEditPart extends ItemElementTreeEditPart {
	
	/**
	 * TaskElement �𵨿� ���� TreeEditPart �� �����Ѵ�.
	 * @param model ActionElement instance
	 */
	public BehaviorElementTreeEditPart(BehaviorElement model) {
		super(model);
	}
	
	/**
	 * Parameter �� �־��� Ŭ����(�Ǵ� �������̽�)�� ���� Adapter �� �����Ѵ�.
	 * @param key Adapter ��û Ÿ��
	 */
	public Object getAdapter(Class key) {
		if (IPropertySource.class == key) {
			Object obj = getModel();
			if(obj instanceof BehaviorElement) {
				return new BehaviorElementPropertySource((BehaviorElement)obj);
			}
		}
		else if (IPropertySourceProvider.class == key) {
			final Object model = getModel();
			if(model instanceof BehaviorElement) {
				return new IPropertySourceProvider() {
					public IPropertySource getPropertySource(Object object) {
						return new BehaviorElementPropertySource((BehaviorElement)model);		
					}
				};
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
	 * @return TaskElement ��
	 * 
	 * @Override
	 */
	protected BehaviorElement getCastedModel() {
		return (BehaviorElement) getModel();
	}

	/**
	 * EditPart ���� �ڽ� �𵨵��� ��ȯ�Ѵ�. 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#getModelChildren()
	 * 
	 * @Override
	 */
	protected List getModelChildren() {
		BehaviorElement model = getCastedModel();
		
		ArrayList<ItemElement> list = new ArrayList<ItemElement>();
		
		ItemElement struct = model.getConstruct();
		if (struct != null) {
			list.add(struct);
		}
		struct = model.getDestruct();
		if (struct != null) {
			list.add(struct);
		}
		
		list.addAll(model.getStates());

		EList<ReferElement> refList = model.getReferences();
		for(ReferElement ref : refList) {
			ItemElement parent = ref;
			while (parent instanceof ReferElement) {
				EList<ConnectionElement> connList = ((ReferElement)parent).getSourceConnections();
				for (ConnectionElement conn : connList) {
					if (ref.equals(conn.getSource2())) {
						list.add(conn);
					}
				}
				parent = parent.getParent();
			}
		}

		return list;
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
			baseImage = TaskModelPlugin.getImageDescriptor(IconStrings.BEHAVIOR_16).createImage();
			setBaseImage(baseImage);
		}

		Rectangle boundingRect = baseImage.getBounds();
		Image image = new Image(null, boundingRect.width, boundingRect.height);
		if(shape instanceof BehaviorElement) {
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
			// Include�� Item�� getCastedModel().getParent()��
			// ModelDiagram�� �ƴϹǷ� EditPart�� Parent�κ��� ModelDiagram��
			// ��� �����Ѵ�.
			ModelDiagram modelDiagram = ((DiagramTreeEditPart)this.getParent()).getCastedModel();
			sortList(modelDiagram.getItems());
			refreshVisuals();
			break;
		case Notification.ADD:
			switch(featureId) {
			case TaskModelPackage.TASK_ELEMENT__STATES:
//				addChild(createChild(notification.getNewValue()), -1);
				this.refreshChildren();
				break;
			default:
				super.notifyChanged(notification);
				break;
			}
			break;
		case Notification.REMOVE:
			switch(featureId) {
			case TaskModelPackage.TASK_ELEMENT__STATES:
//				removeChild(getEditPartForChild(notification.getOldValue()));
				this.refreshChildren();
				break;
			default:
				super.notifyChanged(notification);
				break;
			}
			break;
		}
	}
}