package kr.re.etri.tpl.diagram.outline;

import java.util.Collections;
import java.util.List;

import kr.re.etri.tpl.IconStrings;
import kr.re.etri.tpl.TaskModelPlugin;
import kr.re.etri.tpl.diagram.outline.policies.ShapeComponentTreeEditPolicy;
import kr.re.etri.tpl.diagram.properties.sources.StructBlockPropertySource;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.StateAction;
import kr.re.etri.tpl.taskmodel.StructBlockElement;
import kr.re.etri.tpl.taskmodel.StructType;
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

// KJH 20110210, new
public class StructBlockTreeEditPart extends ItemElementTreeEditPart {
	/**
	 * StructBlockElement 모델에 대한 TreeEditPart 를 생성한다.
	 * @param model StructBlockElement instance
	 */
	public StructBlockTreeEditPart(StructBlockElement model) {
		super(model);
	}
	
	/**
	 * Parameter 로 주어진 클래스(또는 인터페이스)에 대한 Adapter 를 제공한다.
	 * @param key Adapter 요청 타입
	 */
	public Object getAdapter(Class key) {
		if (IPropertySource.class == key) {
			Object obj = getModel();
			if(obj instanceof StructBlockElement) {
				return new StructBlockPropertySource((StructBlockElement)obj);
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
	 * @return StateAction 모델
	 * 
	 * @Override
	 */
	protected StateAction getCastedModel() {
		return (StateAction) getModel();
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
			if(((StructBlockElement)shape).getStructType() == StructType.CONSTRUCT) {	// KJH 20110421, add initialize
				baseImage = TaskModelPlugin.getImageDescriptor(IconStrings.CONSTR_16).createImage();
			}
			else if(((StructBlockElement)shape).getStructType() == StructType.DESTRUCT) {	// KJH 20110421, add finalize
				baseImage = TaskModelPlugin.getImageDescriptor(IconStrings.DESTR_16).createImage();
			}

			setBaseImage(baseImage);
		}

		Rectangle boundingRect = baseImage.getBounds();
		Image image = new Image(null, boundingRect.width, boundingRect.height);
		if(shape instanceof StructBlockElement) {
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
			refreshVisuals();
			break;
		case Notification.ADD:
			switch(featureId) {
			case TaskModelPackage.STRUCT_BLOCK_ELEMENT__STATEMENTS:
				break;
			default:
				super.notifyChanged(notification);
				break;
			}
			break;
		case Notification.REMOVE:
			switch(featureId) {
			case TaskModelPackage.STRUCT_BLOCK_ELEMENT__STATEMENTS:
				break;
			default:
				super.notifyChanged(notification);
				break;
			}
			break;
		}
	}
}
