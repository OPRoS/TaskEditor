
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
 * DiagramEditor 의 Outline View 에서 사용되는 TreeEditPart 들의 상위 클래스이다.
 *
 * 모델의 Property 변경에 대한 처리를 PropertyChangeListener interface 를 구현하여
 * 반영할 수도 있지만 이 클래스와 Subclass 들은 EMF 의 Adapter interface 를 구현하여
 * notifyChagned(Notification) 메소드를 이용하여 반영한다.
 * 
 * @author sfline
 */
public class ItemElementTreeEditPart extends AbstractTreeEditPart implements Adapter {
	/** Tree 노드에 표시할 Icon 의 기본 이미지 */
	private Image baseImage;
	/** baseImage 를 바탕으로 Tree Node 의 수정된 Icon 이미지 */
	private Image image;
	/** Read-Only 모델인 경우 사용될 Color */
	protected static Color readOnlyColor = new Color(null, 255, 128, 64);
	/** 속성이 변경된 Target 모델 */
	private Notifier target;
	
	/**
	 * 모델에 대한 TreeEditPart 를 생성한다.
	 * @param model a non-null Shapes instance
	 */
	public ItemElementTreeEditPart(ItemElement model) {
		super(model);
	}
	
	/**
	 * 모델의 속성 값이 변경되는 경우 Notify 를 받도록 Listener 로 등록한다.
	 * @param model 모델
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
	 * 모델의 Notify 를 받지 않도록 Listener 등록을 해제한다.
	 * @param model 모델
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
	 * EditPart 에 대한 Policy 를 적용한다.
	 * @see org.eclipse.gef.editparts.AbstractTreeEditPart#createEditPolicies()
	 * 
	 * @Override
	 */
	protected void createEditPolicies() {
	}
	
	/**
	 * Object 타입의 모델을 EditPart 의 모델 타입으로 캐스팅한다.
	 * @return ItemElement 모델
	 */
	private ItemElement getCastedModel() {
		return (ItemElement) getModel();
	}

	/**
	 * 주어진 child 에 대한 EditPart 를 반환한다.
	 * @param child 모델 instance
	 * @return child 에 대한 EditPart, null 이 될 수 있다.
	 */
	protected EditPart getEditPartForChild(Object child) {
		return (EditPart) getViewer().getEditPartRegistry().get(child);
	}

	/**
	 * EditPart 모델의 자식 모델들을 반환한다. 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#getModelChildren()
	 * 
	 * @Override
	 */
	protected List getModelChildren() {
		return Collections.EMPTY_LIST;
	}

	/**
	 * Tree Node 의 기본 Icon 을 반환한다.
	 * @return Tree Node 의 Icon
	 */
	protected Image getBaseImage() {
		return baseImage;
	}
	
	/**
	 * Tree Node 의 기본 Icon 을 설정한다.
	 * @param image Tree Node 의 Icon
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
	 * Tree Node 의 Icon 을 반환한다.
	 * baseImage 을 기본으로 Model 의 상태에 따라 Icon 을 새로 생성한다. 
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
	 * baseImage 를 기본으로 모델의 상태에 따라 Image 를 새로 생성한다. 
	 * @param shape 모델
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
	 * Tree Node 에 표시될 이름을 제공한다.
	 * @see org.eclipse.gef.editparts.AbstractTreeEditPart#getText()
	 * 
	 * @Override
	 */
	protected String getText() {
		return getCastedModel().getName();
	}

	/**
	 * 모델의 Property 가 변경되면 호출된다.
	 * @param notification 모델의 변경 정보
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
		return getModel() == type;
	}

	/**
	 * 모델 리스트를 일정 방법으로 다시 정렬한다.
	 * @param itemList 모델 리스트
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
	 * 모델 리스트를 일정 방법으로 다시 정렬한다.
	 * @param itemList 모델 리스트
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