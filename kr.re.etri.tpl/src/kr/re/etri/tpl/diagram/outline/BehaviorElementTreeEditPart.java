
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
 * TaskElement 에 대한 TreeEditPart 클래스이다.
 * 
 * @see kr.re.etri.tpl.diagram.outline.ItemElementTreeEditPart
 * 
 * @author sfline
 */
public class BehaviorElementTreeEditPart extends ItemElementTreeEditPart {
	
	/**
	 * TaskElement 모델에 대한 TreeEditPart 를 생성한다.
	 * @param model ActionElement instance
	 */
	public BehaviorElementTreeEditPart(BehaviorElement model) {
		super(model);
	}
	
	/**
	 * Parameter 로 주어진 클래스(또는 인터페이스)에 대한 Adapter 를 제공한다.
	 * @param key Adapter 요청 타입
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
	 * EditPart 에 대한 Policy 를 적용한다.
	 * @see org.eclipse.gef.editparts.AbstractTreeEditPart#createEditPolicies()
	 * 
	 * @Override
	 */
	protected void createEditPolicies() {
		// allow removal of the associated model element
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new ShapeComponentTreeEditPolicy());
	}

	/**
	 * Object 타입의 모델을 EditPart 의 모델 타입으로 캐스팅한다.
	 * @return TaskElement 모델
	 * 
	 * @Override
	 */
	protected BehaviorElement getCastedModel() {
		return (BehaviorElement) getModel();
	}

	/**
	 * EditPart 모델의 자식 모델들을 반환한다. 
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
	 * baseImage 를 기본으로 모델의 상태에 따라 Image 를 새로 생성한다. 
	 * @param shape 모델
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
	 * 모델의 Property 가 변경되면 호출된다.
	 * @param notification 모델의 변경 정보
	 */
	public void notifyChanged(Notification notification) {
		int type = notification.getEventType();
		int featureId = notification.getFeatureID(TaskModelPackage.class);
		
		switch(type) {
		case Notification.SET:
			// Include된 Item의 getCastedModel().getParent()는
			// ModelDiagram이 아니므로 EditPart의 Parent로부터 ModelDiagram를
			// 얻어 정렬한다.
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