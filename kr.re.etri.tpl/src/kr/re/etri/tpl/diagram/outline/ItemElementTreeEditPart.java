
package kr.re.etri.tpl.diagram.outline;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import kr.re.etri.tpl.IconStrings;
import kr.re.etri.tpl.TaskModelPlugin;
import kr.re.etri.tpl.diagram.properties.sources.ItemElementPropertySource;
import kr.re.etri.tpl.taskmodel.ActionElement;
import kr.re.etri.tpl.taskmodel.BehaviorElement;
import kr.re.etri.tpl.taskmodel.ConnectorElement;
import kr.re.etri.tpl.taskmodel.IncludedElement;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.ReferElement;
import kr.re.etri.tpl.taskmodel.StateElement;
import kr.re.etri.tpl.taskmodel.TaskElement;
import kr.re.etri.tpl.taskmodel.TaskModelPackage;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.editparts.AbstractTreeEditPart;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Pattern;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.ui.views.properties.IPropertySource;

/**
 * DiagramEditor �� Outline View ���� ���Ǵ� TreeEditPart ���� ���� Ŭ�����̴�.
 *
 * ���� Property ���濡 ���� ó���� PropertyChangeListener interface �� �����Ͽ�
 * �ݿ��� ���� ������ �� Ŭ������ Subclass ���� EMF �� Adapter interface �� �����Ͽ�
 * notifyChagned(Notification) �޼ҵ带 �̿��Ͽ� �ݿ��Ѵ�.
 * 
 * @author sfline
 */
public class ItemElementTreeEditPart extends AbstractTreeEditPart implements Adapter {
	/** Tree ��忡 ǥ���� Icon �� �⺻ �̹��� */
	private Image baseImage;
	/** baseImage �� �������� Tree Node �� ������ Icon �̹��� */
	private Image image;
	/** Read-Only ���� ��� ���� Color */
	protected static Color readOnlyColor = new Color(null, 255, 128, 64);
	/** �Ӽ��� ����� Target �� */
	private Notifier target;
	
	/**
	 * �𵨿� ���� TreeEditPart �� �����Ѵ�.
	 * @param model a non-null Shapes instance
	 */
	public ItemElementTreeEditPart(ItemElement model) {
		super(model);
	}
	
	/**
	 * ���� �Ӽ� ���� ����Ǵ� ��� Notify �� �޵��� Listener �� ����Ѵ�.
	 * @param model ��
	 */
	protected void hookIntoModel(EObject model) {
		if(model != null) {
			model.eAdapters().add(this);
			EList<ReferElement> refList = ((ItemElement)model).getReferences();
			for(Iterator<ReferElement> iter = refList.iterator(); iter.hasNext(); ) {
				ReferElement refItem = iter.next();
				refItem.eAdapters().add(this);
			}
		}
	}
	
	/**
	 * ���� Notify �� ���� �ʵ��� Listener ����� �����Ѵ�.
	 * @param model ��
	 */
	protected void unhookFromModel(EObject model) {
		if(model != null) {
			model.eAdapters().remove(this);
			EList<ReferElement> refList = ((ItemElement)model).getReferences();
			for(Iterator<ReferElement> iter = refList.iterator(); iter.hasNext(); ) {
				ReferElement refItem = iter.next();
				refItem.eAdapters().remove(this);
			}
		}
	}
	
	/**
	 * EditPart �� Ȱ��ȭ�Ǹ� �𵨷� ���� Notify �� �޵��� �Ѵ�.
	 */
	public void activate() {
		if (!isActive()) {
			hookIntoModel(getCastedModel());
			super.activate();
		}
	}
	
	/**
	 * EditPart �� ��Ȱ��ȭ�Ǹ� �𵨷� ���� Notify �� ���� �ʵ��� �Ѵ�.
	 */
	public void deactivate() {
		if (isActive()) {
			unhookFromModel(getCastedModel());
			super.deactivate();
		}
	}

	/**
	 * Parameter �� �־��� Ŭ����(�Ǵ� �������̽�)�� ���� Adapter �� �����Ѵ�.
	 * @param key Adapter ��û Ÿ��
	 * @return Parameter �� �־��� Ŭ����(�Ǵ� �������̽�)�� ���� Adapter
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
	 * EditPart �� ���� Policy �� �����Ѵ�.
	 * @see org.eclipse.gef.editparts.AbstractTreeEditPart#createEditPolicies()
	 * 
	 * @Override
	 */
	protected void createEditPolicies() {
	}
	
	/**
	 * Object Ÿ���� ���� EditPart �� �� Ÿ������ ĳ�����Ѵ�.
	 * @return ItemElement ��
	 */
	private ItemElement getCastedModel() {
		return (ItemElement) getModel();
	}

	/**
	 * �־��� child �� ���� EditPart �� ��ȯ�Ѵ�.
	 * @param child �� instance
	 * @return child �� ���� EditPart, null �� �� �� �ִ�.
	 */
	protected EditPart getEditPartForChild(Object child) {
		return (EditPart) getViewer().getEditPartRegistry().get(child);
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
	 * Tree Node �� �⺻ Icon �� ��ȯ�Ѵ�.
	 * @return Tree Node �� Icon
	 */
	protected Image getBaseImage() {
		return baseImage;
	}
	
	/**
	 * Tree Node �� �⺻ Icon �� �����Ѵ�.
	 * @param image Tree Node �� Icon
	 */
	protected void setBaseImage(Image image) {
		if(baseImage != null) {
			if(baseImage.isDisposed() == false) {
				baseImage.dispose();
			}
		}

		baseImage = image;
	}

	/**
	 * Tree Node �� Icon �� ��ȯ�Ѵ�.
	 * baseImage �� �⺻���� Model �� ���¿� ���� Icon �� ���� �����Ѵ�. 
	 * @see org.eclipse.gef.editparts.AbstractTreeEditPart#getImage()
	 * 
	 * @Override
	 */
	protected Image getImage() {
		if(image == null || image.isDisposed()) {
			image = createImageForModel(getCastedModel());
		}

		return image;
	}

	/**
	 * baseImage �� �⺻���� ���� ���¿� ���� Image �� ���� �����Ѵ�. 
	 * @param shape ��
	 * @return Image
	 */
	protected Image createImageForModel(ItemElement shape) {
		Image baseImage;
		
		baseImage = getBaseImage();
		if(baseImage == null) {
			baseImage = TaskModelPlugin.getImageDescriptor(IconStrings.PARAM_16).createImage();
			setBaseImage(baseImage);
		}

		Rectangle boundingRect = baseImage.getBounds();
		Image image = new Image(null, boundingRect.width, boundingRect.height);
		if(shape instanceof ItemElement) {
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

//				imageGc.setForeground(image.getDevice().getSystemColor(SWT.COLOR_BLUE));
				imageGc.setForeground(readOnlyColor);
				imageGc.drawString("R", 1, 5);

//				imageGc.setLineWidth(1);
//
//				Color bgColor = new Color(null, 255, 96, 0);
//				imageGc.setForeground(bgColor);
//				imageGc.drawLine(13, 1, 1, 13);
//				imageGc.drawLine(13, 2, 2, 13);
//				bgColor.dispose();
//
//				bgColor = new Color(null, 255, 162, 53);
//				imageGc.setForeground(bgColor);
//				imageGc.drawLine(14, 2, 2, 14);
//				bgColor.dispose();
			}

			imageGc.setBackgroundPattern(null);

			bgPattern.dispose();
			imageGc.dispose();
		}

		return image;
	}

	/**
	 * Tree Node �� ǥ�õ� �̸��� �����Ѵ�.
	 * @see org.eclipse.gef.editparts.AbstractTreeEditPart#getText()
	 * 
	 * @Override
	 */
	protected String getText() {
		return getCastedModel().getName();
	}

	/**
	 * ���� Property �� ����Ǹ� ȣ��ȴ�.
	 * @param notification ���� ���� ����
	 */
	public void notifyChanged(Notification notification) {
		int type = notification.getEventType();
		int featureId = notification.getFeatureID(TaskModelPackage.class);
		ItemElement refItem;

		switch(type) {
		case Notification.SET:
			refreshVisuals();
			break;
		case Notification.ADD:
/*			switch(featureId) {
			case TaskModelPackage.SUB_DIAGRAM__ITEMS:
			case TaskModelPackage.ITEM_ELEMENT__REFERENCES:
			case TaskModelPackage.CONNECTION_ELEMENT__SOURCE:
			case TaskModelPackage.CONNECTION_ELEMENT__TARGET:
			case TaskModelPackage.LINKED_ELEMENT__SOURCE_CONNECTIONS:
			case TaskModelPackage.LINKED_ELEMENT__TARGET_CONNECTIONS:
				refItem = (ItemElement)notification.getNewValue();
				refItem.eAdapters().add(this);
//				addChild(createChild(notification.getNewValue()), -1);
				this.refreshChildren();
				break;
			}
*/			this.refreshChildren();
			break;
		case Notification.REMOVE:
/*			switch(featureId) {
			case TaskModelPackage.SUB_DIAGRAM__ITEMS:
			case TaskModelPackage.ITEM_ELEMENT__REFERENCES:
			case TaskModelPackage.CONNECTION_ELEMENT__SOURCE:
			case TaskModelPackage.CONNECTION_ELEMENT__TARGET:
			case TaskModelPackage.LINKED_ELEMENT__SOURCE_CONNECTIONS:
			case TaskModelPackage.LINKED_ELEMENT__TARGET_CONNECTIONS:
				refItem = (ItemElement)notification.getOldValue();
				refItem.eAdapters().remove(this);
//				removeChild(getEditPartForChild(notification.getOldValue()));
				this.refreshChildren();
				break;
			}
*/			this.refreshChildren();
			break;
		}
	}
	
	/**
	 * EditPart �� ���� Notifier �� ��ȯ�Ѵ�.
	 * @return Notifier
	 */
	public Notifier getTarget() {
		return target;
	}
	
	/**
	 * EditPart �� ���� Notifier �� �����Ѵ�.
	 * @param newTarget Notifier
	 */
	public void setTarget(Notifier newTarget) {
		target = newTarget;
	}
	
	/**
	 * �־��� ��ü�� ���� Adapter ����
	 * @param type Adapter ��û Ÿ��
	 */
	public boolean isAdapterForType(Object type) {
		return getModel() == type;
	}

	/**
	 * �� ����Ʈ�� ���� ������� �ٽ� �����Ѵ�.
	 * @param itemList �� ����Ʈ
	 */
	public void sortList(EList<ItemElement> itemList) {
		ECollections.sort(itemList, new Comparator<ItemElement>() {
			@Override
			public int compare(ItemElement o1, ItemElement o2) {
				if(o1 instanceof ReferElement) {
					o1 = ((ReferElement)o1).getRealModel();
				}
				if(o2 instanceof ReferElement) {
					o2 = ((ReferElement)o2).getRealModel();
				}

				if(o1 instanceof IncludedElement) {
					if(o2 instanceof TaskElement) return -1;
					if(o2 instanceof BehaviorElement) return -1;
					if(o2 instanceof StateElement) return -1;
					if(o2 instanceof ActionElement) return -1;
				}
				if(o1 instanceof TaskElement) {
					if(o2 instanceof IncludedElement) return 1;
					if(o2 instanceof BehaviorElement) return -1;
					if(o2 instanceof StateElement) return -1;
					if(o2 instanceof ActionElement) return -1;
				}
				if(o1 instanceof BehaviorElement) {
					if(o2 instanceof IncludedElement) return 1;
					if(o2 instanceof TaskElement) return 1;
					if(o2 instanceof StateElement) return -1;
					if(o2 instanceof ActionElement) return -1;
				}
				if(o1 instanceof ActionElement) {
					if(o2 instanceof IncludedElement) return 1;
					if(o2 instanceof TaskElement) return 1;
					if(o2 instanceof BehaviorElement) return 1;
					if(o2 instanceof StateElement) return -1;
				}
				if(o1 instanceof StateElement) {
					if(o2 instanceof IncludedElement) return 1;
					if(o2 instanceof TaskElement) return 1;
					if(o2 instanceof BehaviorElement) return 1;
					if(o2 instanceof ActionElement) return 1;
				}
				
				if(o1.getName() == null) {
					return -1;
				}
				return o1.getName().compareTo(o2.getName());
			}
		});
	}

	/**
	 * �� ����Ʈ�� ���� ������� �ٽ� �����Ѵ�.
	 * @param itemList �� ����Ʈ
	 */
	public void sortList(List<ItemElement> itemList) {
		Collections.sort(itemList, new Comparator<ItemElement>() {
			@Override
			public int compare(ItemElement o1, ItemElement o2) {
				if(o1 instanceof ReferElement) {
					o1 = ((ReferElement)o1).getRealModel();
				}
				if(o2 instanceof ReferElement) {
					o2 = ((ReferElement)o2).getRealModel();
				}

				if(o1 instanceof IncludedElement) {
					if(o2 instanceof TaskElement) return -1;
					if(o2 instanceof BehaviorElement) return -1;
					if(o2 instanceof StateElement) return -1;
					if(o2 instanceof ActionElement) return -1;
					if(o2 instanceof ConnectorElement) return -1;	// KJH 20101201, sort connector
				}
				if(o1 instanceof TaskElement) {
					if(o2 instanceof IncludedElement) return 1;
					if(o2 instanceof BehaviorElement) return -1;
					if(o2 instanceof StateElement) return -1;
					if(o2 instanceof ActionElement) return -1;
					if(o2 instanceof ConnectorElement) return -1;	// KJH 20101201, sort connector
				}
				if(o1 instanceof BehaviorElement) {
					if(o2 instanceof IncludedElement) return 1;
					if(o2 instanceof TaskElement) return 1;
					if(o2 instanceof StateElement) return -1;
					if(o2 instanceof ActionElement) return -1;
					if(o2 instanceof ConnectorElement) return -1;	// KJH 20101201, sort connector
				}
				if(o1 instanceof ActionElement) {
					if(o2 instanceof IncludedElement) return 1;
					if(o2 instanceof TaskElement) return 1;
					if(o2 instanceof BehaviorElement) return 1;
					if(o2 instanceof StateElement) return -1;
					if(o2 instanceof ConnectorElement) return -1;	// KJH 20101201, sort connector
				}
				if(o1 instanceof StateElement) {
					if(o2 instanceof IncludedElement) return 1;
					if(o2 instanceof TaskElement) return 1;
					if(o2 instanceof BehaviorElement) return 1;
					if(o2 instanceof ActionElement) return 1;
					if(o2 instanceof ConnectorElement) return -1;	// KJH 20101201, sort connector
				}
				if(o1 instanceof ConnectorElement) {	// KJH 20101201 s, sort connector
					if(o2 instanceof IncludedElement) return 1;
					if(o2 instanceof TaskElement) return 1;
					if(o2 instanceof BehaviorElement) return 1;
					if(o2 instanceof ActionElement) return 1;
					if(o2 instanceof StateElement) return 1;
				}										// KJH 20101201 e, sort connector
				
				if(o1.getName() == null) {
					return -1;
				}
				return o1.getName().compareTo(o2.getName());
			}
		});
	}
}