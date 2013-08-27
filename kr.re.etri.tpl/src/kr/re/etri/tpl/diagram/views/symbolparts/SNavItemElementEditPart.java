
package kr.re.etri.tpl.diagram.views.symbolparts;

import java.util.Collections;
import java.util.List;

import kr.re.etri.tpl.TaskModelPlugin;
import kr.re.etri.tpl.diagram.properties.sources.ItemElementPropertySource;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.Parameter;

import org.apache.log4j.Logger;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
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
 * TreeEditPart used for ItemElement instances (more specific for EllipticalShape and
 * RectangularShape instances). This is used in the Outline View of the ShapesEditor.
 * <p>This edit part must implement the PropertyChangeListener interface, 
 * so it can be notified of property changes in the corresponding model element.
 * </p>
 * 
 * @author Elias Volanakis
 */
public class SNavItemElementEditPart extends AbstractTreeEditPart implements Adapter {
	private static Logger logger = Logger.getLogger(SNavItemElementEditPart.class);
	private Image baseImage;
	private Image image;
	protected static Color readOnlyColor = new Color(null, 255, 128, 64);
	private Notifier target;
	
	/**
	 * Create a new instance of this edit part using the given model element.
	 * @param model a non-null Shapes instance
	 */
	public SNavItemElementEditPart(ItemElement model) {
		super(model);
	}
	
	protected void hookIntoModel(EObject model) {
		if(model != null) {
			model.eAdapters().add(this);
		}
	}
	
	protected void unhookFromModel(EObject model) {
		if(model != null) {
			model.eAdapters().remove(this);
		}
	}
	
	/**
	 * Upon activation, attach to the model element as a property change listener.
	 */
	public void activate() {
		if (!isActive()) {
			hookIntoModel(getCastedModel());
			super.activate();
		}
	}
	
	/**
	 * Upon deactivation, detach from the model element as a property change listener.
	 */
	public void deactivate() {
		if (isActive()) {
			unhookFromModel(getCastedModel());
			super.deactivate();
		}
	}

	public Object getAdapter(Class key) {
		if (IPropertySource.class == key) {
			Object model = getCastedModel();
			if(model instanceof ItemElement) {
				return new ItemElementPropertySource((ItemElement)model);
			}
		}
		return super.getAdapter(key);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.gef.editparts.AbstractTreeEditPart#createEditPolicies()
	 */
	protected void createEditPolicies() {
	}

	public ItemElement getCastedModel() {
		return (ItemElement) getModel();
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.gef.editparts.AbstractEditPart#getModelChildren()
	 */
	protected List getModelChildren() {
		return Collections.EMPTY_LIST;
	}

	/**
	 * Convenience method that returns the EditPart corresponding to a given child.
	 * @param child a model element instance
	 * @return the corresponding EditPart or null
	 */
	protected EditPart getEditPartForChild(Object child) {
		return (EditPart) getViewer().getEditPartRegistry().get(child);
	}

	protected Image getBaseImage() {
		return baseImage;
	}
	
	protected void setBaseImage(Image image) {
		if(baseImage != null) {
			if(baseImage.isDisposed() == false) {
				baseImage.dispose();
			}
		}

		baseImage = image;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.editparts.AbstractTreeEditPart#getImage()
	 */
	protected Image getImage() {
		// KJH 20100630, baseImage == image 일때,
		//               setBaseImage()에서 baseImage를 dispose 에러발생
		//               조건에 'image.isDisposed() 추가
		if(image == null || image.isDisposed()) {
			image = createImageForModel(getCastedModel());
		}

		return image;
	}
	
	protected Image createImageForModel(ItemElement shape) {
		Image baseImage;
		
		baseImage = getBaseImage();
		if(baseImage == null) {
			baseImage = TaskModelPlugin.getImageDescriptor("icons/param.gif").createImage();
			setBaseImage(baseImage);
		}

		Rectangle boundingRect = baseImage.getBounds();
//		Image image = new Image(null, boundingRect.width, boundingRect.height);
//		if(shape instanceof ItemElement) {
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

//				imageGc.setForeground(image.getDevice().getSystemColor(SWT.COLOR_BLUE));
//				imageGc.setForeground(readOnlyColor);
//				imageGc.drawString("R", 1, 5);

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
		// this will cause an invocation of getImage() and getText(), see below
		refreshVisuals();
	}
	
	public Notifier getTarget() {
		return target;
	}
	
	public void setTarget(Notifier newTarget) {
		target = newTarget;
	}
	
	public boolean isAdapterForType(Object type) {
		return getModel() == type;
	}
}