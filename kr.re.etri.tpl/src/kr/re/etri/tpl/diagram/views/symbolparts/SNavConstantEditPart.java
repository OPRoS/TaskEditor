
package kr.re.etri.tpl.diagram.views.symbolparts;

import java.util.Collections;
import java.util.List;

import kr.re.etri.tpl.TaskModelPlugin;
import kr.re.etri.tpl.diagram.outline.policies.ShapeComponentTreeEditPolicy;
import kr.re.etri.tpl.diagram.properties.sources.ConstantPropertySource;
import kr.re.etri.tpl.taskmodel.Constant;
import kr.re.etri.tpl.taskmodel.ItemElement;
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
 * TreeEditPart used for Constant instances (more specific for EllipticalShape and
 * RectangularShape instances). This is used in the Outline View of the ShapesEditor.
 * <p>This edit part must implement the PropertyChangeListener interface, 
 * so it can be notified of property changes in the corresponding model element.
 * </p>
 * 
 * @author Elias Volanakis
 */
public class SNavConstantEditPart extends SNavItemElementEditPart {

	/**
	 * Create a new instance of this edit part using the given model element.
	 * @param model a non-null Shapes instance
	 */
	public SNavConstantEditPart(Constant model) {
		super(model);
	}
	
	public Object getAdapter(Class key) {
		if (IPropertySource.class == key) {
			Object obj = getModel();
			if(obj instanceof Constant) {
				return new ConstantPropertySource((Constant)obj);
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
	public Constant getCastedModel() {
		return (Constant) getModel();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.editparts.AbstractEditPart#getModelChildren()
	 */
	protected List getModelChildren() {
		Constant sym;
		sym = getCastedModel();

		return Collections.EMPTY_LIST;
	}

	@Override
	protected Image createImageForModel(ItemElement shape) {
		Image baseImage;
		
		baseImage = getBaseImage();
		if(baseImage == null) {
			baseImage = TaskModelPlugin.getImageDescriptor("icons/club/untitle.png").createImage();
			setBaseImage(baseImage);
		}

		Rectangle boundingRect = baseImage.getBounds();
//		Image image = new Image(null, boundingRect.width, boundingRect.height);
//		if(shape instanceof Constant) {
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
		StringBuilder sb = new StringBuilder();
		Constant constModel = getCastedModel();

		sb.append(constModel.getType()).append(" ");
		sb.append(constModel.getName());
		return sb.toString();
	}

	public void notifyChanged(Notification notification) {
		int type = notification.getEventType();
		int featureId = notification.getFeatureID(TaskModelPackage.class);
		
		switch(type) {
		case Notification.SET:
			switch(featureId) {
			case TaskModelPackage.CONSTANT__INIT_VALUE:
				break;
			default:
				super.notifyChanged(notification);
				break;
			}
			refreshVisuals();
			break;
		case Notification.ADD:
			switch(featureId)
			{
			default:
				super.notifyChanged(notification);
				break;
			}
			refreshChildren();
			break;
		case Notification.REMOVE:
			switch(featureId)
			{
			default:
				super.notifyChanged(notification);
				break;
			}
			refreshChildren();
			break;
		}
	}
}