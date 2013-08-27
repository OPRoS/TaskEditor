package kr.re.etri.tpl.diagram.outline;

import java.util.ArrayList;
import java.util.List;

import kr.re.etri.tpl.IconStrings;
import kr.re.etri.tpl.TaskModelPlugin;
import kr.re.etri.tpl.diagram.outline.policies.ShapeComponentTreeEditPolicy;
import kr.re.etri.tpl.diagram.properties.sources.WithElementPropertySource;
import kr.re.etri.tpl.taskmodel.ConnectionElement;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.ReferElement;
import kr.re.etri.tpl.taskmodel.RelationShip;
import kr.re.etri.tpl.taskmodel.TaskModelPackage;
import kr.re.etri.tpl.taskmodel.WithElement;

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

public class WithElementTreeEditPart extends ItemElementTreeEditPart {

	public WithElementTreeEditPart(ItemElement model) {
		super(model);
	}

	/* (non-Javadoc)
	 * @see kr.re.etri.tpl.diagram.outline.ItemElementTreeEditPart#getAdapter(java.lang.Class)
	 */
	@Override
	public Object getAdapter(Class key) {
		if (IPropertySource.class == key) {
			Object obj = getModel();
			if (obj instanceof WithElement) {
				return new WithElementPropertySource((WithElement)obj);
			}
		}
		else if (IPropertySourceProvider.class == key) {
			final Object obj = getModel();
			if (obj instanceof WithElement) {
				return new IPropertySourceProvider() {
					@Override
					public IPropertySource getPropertySource(Object object) {
						return new WithElementPropertySource((WithElement)obj);
					}
				};
			}
		}
		return super.getAdapter(key);
	}
	
	/* (non-Javadoc)
	 * @see kr.re.etri.tpl.diagram.outline.ItemElementTreeEditPart#createEditPolicies()
	 */
	@Override
	protected void createEditPolicies() {
		// allow removal of the associated model element
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new ShapeComponentTreeEditPolicy());
	}

	/**
	 * Object 타입의 모델을 EditPart 의 모델 타입으로 캐스팅한다.
	 * @return TaskElement 모델
	 */
	protected WithElement getCastedModel() {
		return (WithElement) getModel();
	}
	
	/* (non-Javadoc)
	 * @see kr.re.etri.tpl.diagram.outline.ItemElementTreeEditPart#getModelChildren()
	 */
	@Override
	protected List getModelChildren() {
		WithElement with = getCastedModel();
		List<ItemElement> list = new ArrayList<ItemElement>();

		EList<ReferElement> refList = with.getReferences();
		for(ReferElement ref : refList) {
			ItemElement parent = ref;
			while (parent instanceof ReferElement) {
				EList<ConnectionElement> connList = ((ReferElement)parent).getSourceConnections();
				for (ConnectionElement conn : connList) {
					if (RelationShip.TASK_CALL == conn.getRelationship() &&
							ref.equals(conn.getSource2())) {
						list.add(conn);
					}
				}
				parent = parent.getParent();
			}
		}
		
		return list;
	}
	
	/**
	 * Figure 에 표시될 Text 를 반환한다.
	 * @return igure 에 표시될 Text
	 */
	protected String getText() {
		String text = getCastedModel().getName();
		return (text != null) ? text : "run";
	}

	/* (non-Javadoc)
	 * @see kr.re.etri.tpl.diagram.outline.ItemElementTreeEditPart#createImageForModel(kr.re.etri.tpl.taskmodel.ItemElement)
	 */
	@Override
	protected Image createImageForModel(ItemElement shape) {
		Image baseImage;
		
		baseImage = getBaseImage();
		if (baseImage == null) {
			baseImage = TaskModelPlugin.getImageDescriptor(IconStrings.WITH_16).createImage();
			setBaseImage(baseImage);
		}
		
		Rectangle boundingRect = baseImage.getBounds();
		Image image = new Image(null, boundingRect.width, boundingRect.height);
		if(shape instanceof WithElement) {
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
	
	/* (non-Javadoc)
	 * @see kr.re.etri.tpl.diagram.outline.ItemElementTreeEditPart#notifyChanged(org.eclipse.emf.common.notify.Notification)
	 */
	@Override
	public void notifyChanged(Notification notification) {
		
		// KJH 20110209 s,
		Object notifier = notification.getNotifier();
		
		if(notifier instanceof ReferElement) {
			super.notifyChanged(notification);
//			connectionElemeentNotifyChanged(notification);
			return;
		}	// KJH 20110209 e,

		
		int type = notification.getEventType();
		int featureId = notification.getFeatureID(TaskModelPackage.class);
		
		switch(type) {
		case Notification.SET:
			refreshVisuals();
			break;
		case Notification.ADD:
			switch(featureId) {
			default:
				super.notifyChanged(notification);
				break;
			}
			break;
		case Notification.REMOVE:
			switch(featureId) {
			default:
				super.notifyChanged(notification);
				break;
			}
			break;
		}
	}
}
