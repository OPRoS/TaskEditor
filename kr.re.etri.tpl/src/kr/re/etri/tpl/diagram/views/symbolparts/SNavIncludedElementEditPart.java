
package kr.re.etri.tpl.diagram.views.symbolparts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import kr.re.etri.tpl.TaskModelPlugin;
import kr.re.etri.tpl.diagram.outline.policies.ShapeComponentTreeEditPolicy;
import kr.re.etri.tpl.diagram.properties.sources.IncludedElementPropertySource;
import kr.re.etri.tpl.taskmodel.EnumElement;
import kr.re.etri.tpl.taskmodel.IncludedElement;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.ModelElement;
import kr.re.etri.tpl.taskmodel.Symbol;

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
 * TreeEditPart used for ActionElement instances (more specific for EllipticalShape and
 * RectangularShape instances). This is used in the Outline View of the ShapesEditor.
 * <p>This edit part must implement the PropertyChangeListener interface, 
 * so it can be notified of property changes in the corresponding model element.
 * </p>
 * 
 * @author Elias Volanakis
 */
public class SNavIncludedElementEditPart extends SNavItemElementEditPart {
	
	/**
	 * Create a new instance of this edit part using the given model element.
	 * @param model a non-null Shapes instance
	 */
	public SNavIncludedElementEditPart(IncludedElement model) {
		super(model);
	}
	
	public Object getAdapter(Class key) {
		if (IPropertySource.class == key) {
			Object obj = getModel();
			if(obj instanceof IncludedElement) {
				return new IncludedElementPropertySource((IncludedElement)obj);
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
	public IncludedElement getCastedModel() {
		return (IncludedElement) getModel();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.editparts.AbstractEditPart#getModelChildren()
	 */
	protected List getModelChildren() {
		IncludedElement incElmt = getCastedModel();
		
		if(incElmt == null) {
			return Collections.EMPTY_LIST;
		}

		ArrayList<ItemElement> list = new ArrayList<ItemElement>();

		List<ItemElement> itemList = incElmt.getItems();
		for(ItemElement item : itemList) {
			if(item instanceof IncludedElement) {
				list.add(item);
			}
		}
		for(ItemElement item : itemList) {
			if(item instanceof EnumElement) {
				list.add(item);
			}
		}
		for(ItemElement item : itemList) {
			if(item instanceof ModelElement) {
				list.add(item);
			}
		}

		return list;
//		return Collections.EMPTY_LIST;
	}

	@Override
	protected Image createImageForModel(ItemElement shape) {
		Image baseImage;
		
		baseImage = getBaseImage();
		if(baseImage == null) {
			baseImage = TaskModelPlugin.getImageDescriptor("icons/club/#I.png").createImage();
			setBaseImage(baseImage);
		}
		Rectangle boundingRect = baseImage.getBounds();
//		Image image = new Image(null, boundingRect.width, boundingRect.height);
//		if(shape instanceof IncludedElement) {
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
		IncludedElement incItem = getCastedModel();
		if(incItem == null) {
			return "";
		}
		
		return incItem.getIncludePath() == null ? "" : incItem.getIncludePath();
	}

	public void notifyChanged(Notification notification) {
		// this will cause an invocation of getImage() and getText(), see below
		refreshVisuals();
	}
}