
package kr.re.etri.tpl.diagram.views.behaviorparts;

import java.util.Collections;
import java.util.List;

import kr.re.etri.tpl.TaskModelPlugin;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.editparts.AbstractTreeEditPart;
import org.eclipse.swt.graphics.Image;
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
public class BNavObjectEditPart extends AbstractTreeEditPart implements Adapter {
	
	private Image image;
	private Notifier target;
	
	/**
	 * Create a new instance of this edit part using the given model element.
	 * @param model a non-null Shapes instance
	 */
	public BNavObjectEditPart(Object model) {
		super(model);
	}
	
	/**
	 * Upon activation, attach to the model element as a property change listener.
	 */
	public void activate() {
		if (!isActive()) {
			super.activate();
		}
	}
	
	/**
	 * Upon deactivation, detach from the model element as a property change listener.
	 */
	public void deactivate() {
		if (isActive()) {
			super.deactivate();
		}
	}

	public Object getAdapter(Class key) {
		if (IPropertySource.class == key) {
//			Object model = getCastedModel();
//			if(model instanceof ItemElement) {
//				return new ItemElementPropertySource((ItemElement)model);
//			}
		}
		return super.getAdapter(key);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.gef.editparts.AbstractTreeEditPart#createEditPolicies()
	 */
	protected void createEditPolicies() {
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

	/* (non-Javadoc)
	 * @see org.eclipse.gef.editparts.AbstractTreeEditPart#getImage()
	 */
	protected Image getImage() {
		if(image == null) {
			image = createImageForModel(getModel());
		}

		return image;
	}

	protected Image createImageForModel(Object shape) {
		Image image = null;
		image = TaskModelPlugin.getImageDescriptor("icons/param.gif").createImage();
		return image;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.editparts.AbstractTreeEditPart#getText()
	 */
	protected String getText() {
		return getModel().toString();
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