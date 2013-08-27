package kr.re.etri.tpl.diagram.outline;

import java.util.ArrayList;
import java.util.List;

import kr.re.etri.tpl.IconStrings;
import kr.re.etri.tpl.TaskModelPlugin;
import kr.re.etri.tpl.diagram.outline.policies.ShapeComponentTreeEditPolicy;
import kr.re.etri.tpl.diagram.properties.sources.ConnectorElementPropertySource;
import kr.re.etri.tpl.taskmodel.ConnectionElement;
import kr.re.etri.tpl.taskmodel.ConnectorElement;
import kr.re.etri.tpl.taskmodel.ConnectorType;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.ReferElement;
import kr.re.etri.tpl.taskmodel.StructBlockElement;
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
 * ConnectorElement 에 대한 TreeEditPart 클래스이다.
 * 
 * @see kr.re.etri.tpl.diagram.outline.ItemElementTreeEditPart
 * 
 * @author KJH
 */
public class ConnectorElementTreeEditPart extends ItemElementTreeEditPart {

	/**
	 * ConnectorElement 모델에 대한 TreeEditPart 를 생성한다.
	 * @param model ConnectorElement instance
	 */
	public ConnectorElementTreeEditPart(ItemElement model) {
		super(model);
	}

	/* (non-Javadoc)
	 * @see kr.re.etri.tpl.diagram.outline.ItemElementTreeEditPart#getAdapter(java.lang.Class)
	 */
	@Override
	public Object getAdapter(Class key) {
		if (IPropertySource.class == key) {
			Object obj = getModel();
			if (obj instanceof ConnectorElement) {
				return new ConnectorElementPropertySource((ConnectorElement)obj);
			}
		}
		else if (IPropertySourceProvider.class == key) {
			final Object obj = getModel();
			if (obj instanceof ConnectorElement) {
				return new IPropertySourceProvider() {
					@Override
					public IPropertySource getPropertySource(Object object) {
						return new ConnectorElementPropertySource((ConnectorElement)obj);
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
	protected ConnectorElement getCastedModel() {
		return (ConnectorElement) getModel();
	}

	/* (non-Javadoc)
	 * @see kr.re.etri.tpl.diagram.outline.ItemElementTreeEditPart#getModelChildren()
	 */
	@Override
	protected List getModelChildren() {
		// KJH 20110210 s, fix
		ConnectorElement connector = getCastedModel();
		List<ItemElement> list = new ArrayList<ItemElement>();
		
		ItemElement struct = connector.getConstruct();
		if (struct != null) {
			list.add(struct);
		}
		struct = connector.getDestruct();
		if (struct != null) {
			list.add(struct);
		}
		
		list.addAll(connector.getWiths());

		EList<ReferElement> refList = connector.getReferences();
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
		// KJH 20110210 e, fix
	}

	/* (non-Javadoc)
	 * @see kr.re.etri.tpl.diagram.outline.ItemElementTreeEditPart#createImageForModel(kr.re.etri.tpl.taskmodel.ItemElement)
	 */
	@Override
	protected Image createImageForModel(ItemElement shape) {
		Image baseImage;
		
		baseImage = getBaseImage();
		if (baseImage == null) {
			// KJH 20110503 s, conexer/seqexer 아이콘 변경
			ConnectorElement connector = getCastedModel();
			if (ConnectorType.CONEXER == connector.getConType()) {
				baseImage = TaskModelPlugin.getImageDescriptor(IconStrings.CONEXER_16).createImage();
			}
			else {
				baseImage = TaskModelPlugin.getImageDescriptor(IconStrings.SEQEXER_16).createImage();
			}	// KJH 20110503 e, conexer/seqexer 아이콘 변경
			setBaseImage(baseImage);
		}
		
		Rectangle boundingRect = baseImage.getBounds();
		Image image = new Image(null, boundingRect.width, boundingRect.height);
		if(shape instanceof ConnectorElement) {
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
		if(notifier instanceof StructBlockElement) {
			structBlockNotifyChanged(notification);
			return;
		}
		
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
			case TaskModelPackage.CONNECTOR_ELEMENT__CONSTRUCT:
			case TaskModelPackage.CONNECTOR_ELEMENT__DESTRUCT:
				break;
			default:
				super.notifyChanged(notification);
				break;
			}
			break;
		case Notification.REMOVE:
			switch(featureId) {
			case TaskModelPackage.CONNECTOR_ELEMENT__CONSTRUCT:
			case TaskModelPackage.CONNECTOR_ELEMENT__DESTRUCT:
				break;
			default:
				super.notifyChanged(notification);
				break;
			}
			break;
		}
	}

	// KJH 20110209 s,
	/**
	 * child 모델 중 StructBlockElement 모델의 Property 가 변경되면 호출된다.
	 * @param notification 모델의 변경 정보
	 */
	protected void structBlockNotifyChanged(Notification notification){
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
	}	// KJH 20110209 e,
	
	// KJH 20110209 s, add
	/**
	 * 속성 중 Connection Property 가 변경되면 호출된다.
	 * @param notification 모델의 변경 정보
	 */
	protected void connectionElemeentNotifyChanged(Notification notification){
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
	}	// KJH 20110209 e, add
}
