
package kr.re.etri.tpl.diagram.views.symbolparts;

import java.util.Collections;
import java.util.List;

import kr.re.etri.tpl.IconStrings;
import kr.re.etri.tpl.TaskModelPlugin;
import kr.re.etri.tpl.diagram.outline.policies.ShapeComponentTreeEditPolicy;
import kr.re.etri.tpl.diagram.properties.sources.SymbolPropertySource;
import kr.re.etri.tpl.taskmodel.Direction;
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
 * TreeEditPart used for Symbol instances (more specific for EllipticalShape and
 * RectangularShape instances). This is used in the Outline View of the ShapesEditor.
 * <p>This edit part must implement the PropertyChangeListener interface, 
 * so it can be notified of property changes in the corresponding model element.
 * </p>
 * 
 * @author Elias Volanakis
 */
public class SNavSymbolEditPart extends SNavItemElementEditPart {

	/**
	 * Create a new instance of this edit part using the given model element.
	 * @param model a non-null Shapes instance
	 */
	public SNavSymbolEditPart(Symbol model) {
		super(model);
	}
	
	public Object getAdapter(Class key) {
		if (IPropertySource.class == key) {
			Object obj = getModel();
			if(obj instanceof Symbol) {
				return new SymbolPropertySource((Symbol)obj);
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
	public Symbol getCastedModel() {
		return (Symbol) getModel();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.editparts.AbstractEditPart#getModelChildren()
	 */
	protected List getModelChildren() {
		Symbol sym;
		sym = getCastedModel();

		return Collections.EMPTY_LIST;
	}

	@Override
	protected Image createImageForModel(ItemElement shape) {
		Image baseImage;
		
		baseImage = getBaseImage();
		if(baseImage == null) {
			if(shape instanceof Symbol) {
				if(Direction.IN == ((Symbol)shape).getDirection()) {
					baseImage = TaskModelPlugin.getImageDescriptor(IconStrings.INVAR_16).createImage();
				}
				else if(Direction.OUT == ((Symbol)shape).getDirection()){
					baseImage = TaskModelPlugin.getImageDescriptor(IconStrings.OUTVAR_16).createImage();
				}
				else {
					baseImage = TaskModelPlugin.getImageDescriptor(IconStrings.ENUM_16).createImage();
				}
			}
			setBaseImage(baseImage);
		}

		Rectangle boundingRect = baseImage.getBounds();
//		Image image = new Image(null, boundingRect.width, boundingRect.height);
//		if(shape instanceof Symbol) {
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
		Direction dir = getCastedModel().getDirection();
		sb.append(dir.getName()).append(" ");
		sb.append(getCastedModel().getType()).append(" ");
		sb.append(getCastedModel().getName());
		return sb.toString();
	}

	public void notifyChanged(Notification notification) {
		int type = notification.getEventType();
		int featureId = notification.getFeatureID(TaskModelPackage.class);
		
		switch(type) {
		case Notification.SET:
			// KJH 20100630 s, symbol in/out 변경시 createImageForModel()과 동일한 이미지로 수정
			switch(featureId) {
			case TaskModelPackage.SYMBOL__DIRECTION:
				Symbol symbol = getCastedModel();
				Image image;
				if(Direction.IN == symbol.getDirection()) {
//					image = TaskModelPlugin.getImageDescriptor("icons/in.gif").createImage();
					image = TaskModelPlugin.getImageDescriptor("icons/club/input.png").createImage();
				}
				else if(Direction.OUT == symbol.getDirection()){
//					image = TaskModelPlugin.getImageDescriptor("icons/out.gif").createImage();
					image = TaskModelPlugin.getImageDescriptor("icons/club/output.png").createImage();
				}
				else {
//					image = TaskModelPlugin.getImageDescriptor("icons/symbol.gif").createImage();
					image = TaskModelPlugin.getImageDescriptor("icons/club/enum.png").createImage();
				}

				setBaseImage(image);

				break;
			}
			// KJH 20100630 e, symbol in/out 변경시 createImageForModel()과 동일한 이미지로 수정
			
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
//		refreshVisuals();
	}
}