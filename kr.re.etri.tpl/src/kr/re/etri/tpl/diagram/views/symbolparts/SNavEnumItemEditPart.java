
package kr.re.etri.tpl.diagram.views.symbolparts;

import java.util.Collections;
import java.util.List;

import kr.re.etri.tpl.IconStrings;
import kr.re.etri.tpl.TaskModelPlugin;
import kr.re.etri.tpl.diagram.outline.policies.ShapeComponentTreeEditPolicy;
import kr.re.etri.tpl.diagram.properties.sources.EnumItemPropertySource;
import kr.re.etri.tpl.taskmodel.EnumItemElement;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.Symbol;
import kr.re.etri.tpl.taskmodel.TaskModelPackage;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.gef.EditPolicy;
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
 * TreeEditPart used for EnumItemElement instances (more specific for EllipticalShape and
 * RectangularShape instances). This is used in the Outline View of the ShapesEditor.
 * <p>This edit part must implement the PropertyChangeListener interface, 
 * so it can be notified of property changes in the corresponding model element.
 * </p>
 * 
 * @author Elias Volanakis
 */
public class SNavEnumItemEditPart extends SNavItemElementEditPart {

	protected static Color darkColor = new Color(null, 120, 120, 120);
	protected static Color lightColor = new Color(null, 185, 185, 185);
	/**
	 * Create a new instance of this edit part using the given model element.
	 * @param model a non-null Shapes instance
	 */
	public SNavEnumItemEditPart(EnumItemElement model) {
		super(model);
	}
	
	public Object getAdapter(Class key) {
		if (IPropertySource.class == key) {
			Object obj = getModel();
			if(obj instanceof EnumItemElement) {
				return new EnumItemPropertySource((EnumItemElement)obj);
			}
		}
		return super.getAdapter(key);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.editparts.AbstractTreeEditPart#createEditPolicies()
	 */
	protected void createEditPolicies() {
		// allow removal of the associated model element
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new ShapeComponentTreeEditPolicy());
	}
	
	@Override
	public EnumItemElement getCastedModel() {
		return (EnumItemElement) getModel();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.editparts.AbstractEditPart#getModelChildren()
	 */
	protected List getModelChildren() {
		return Collections.EMPTY_LIST;
	}

	@Override
	protected Image createImageForModel(ItemElement shape) {
		Image baseImage;
		
		baseImage = getBaseImage();
		if(baseImage == null) {
//			baseImage = TaskModelPlugin.getImageDescriptor("icons/blank.gif").createImage();
			baseImage = TaskModelPlugin.getImageDescriptor(IconStrings.ENUMITEM_16).createImage();
			setBaseImage(baseImage);
		}

		Rectangle boundingRect = baseImage.getBounds();
//		Image image = new Image(null, boundingRect.width, boundingRect.height);
//		if(shape instanceof EnumItemElement) {
//			GC imageGc = new GC(image);
//
//			Pattern bgPattern = new Pattern(image.getDevice(), boundingRect.x, boundingRect.y,
//					boundingRect.width, boundingRect.height, 
//					ColorConstants.white, 0x00, ColorConstants.white, 0x00);
//
//			imageGc.setBackgroundPattern(bgPattern);
//
//			imageGc.drawImage(baseImage, 0, 0);
//			
//			imageGc.setLineWidth(1);
//			imageGc.setForeground(lightColor);
//			imageGc.drawLine(4, 7, 12, 7);
//			imageGc.setForeground(darkColor);
//			imageGc.drawLine(4, 8, 12, 8);
//			imageGc.setForeground(lightColor);
//			imageGc.drawLine(4, 9, 12, 9);
//
//			if(shape.isIncluded()) {
//				FontData []fd = new FontData[]{new FontData("Candara", 8, SWT.BOLD)};
//				Font font = new Font(null, fd);
//				imageGc.setFont(font);
//
//				imageGc.setForeground(readOnlyColor);
//				imageGc.drawString("R", 1, 5);
//			}
//
//			imageGc.setBackgroundPattern(null);
//
//			bgPattern.dispose();
//			imageGc.dispose();
//		}

		return baseImage;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.editparts.AbstractTreeEditPart#getText()
	 */
	protected String getText() {
		return getCastedModel().getName();
	}

	public void notifyChanged(Notification notification) {
		int type = notification.getEventType();
		int featureId = notification.getFeatureID(TaskModelPackage.class);
		
		switch(type) {
		case Notification.SET:
			refreshVisuals();
			break;
		case Notification.ADD:
			switch(featureId)
			{
			case TaskModelPackage.ACTION_ELEMENT__PARAMS:
				addChild(createChild(notification.getNewValue()), -1);
				break;
			}
			break;
		case Notification.REMOVE:
			switch(featureId)
			{
			case TaskModelPackage.ACTION_ELEMENT__PARAMS:
				removeChild(getEditPartForChild(notification.getOldValue()));
				break;
			}
			break;
		}
	}
}