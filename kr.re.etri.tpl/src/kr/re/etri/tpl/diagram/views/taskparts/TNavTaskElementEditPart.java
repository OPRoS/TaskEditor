
package kr.re.etri.tpl.diagram.views.taskparts;

import java.util.ArrayList;
import java.util.List;

import kr.re.etri.tpl.IconStrings;
import kr.re.etri.tpl.TaskModelPlugin;
import kr.re.etri.tpl.diagram.outline.policies.ShapeComponentTreeEditPolicy;
import kr.re.etri.tpl.diagram.properties.sources.TaskElementPropertySource;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.TaskElement;
import kr.re.etri.tpl.taskmodel.TaskModelPackage;

import org.apache.log4j.Logger;
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
 * TreeEditPart used for WorkerElement instances (more specific for EllipticalShape and
 * RectangularShape instances). This is used in the Outline View of the ShapesEditor.
 * <p>This edit part must implement the PropertyChangeListener interface, 
 * so it can be notified of property changes in the corresponding model element.
 * </p>
 * 
 * @author Elias Volanakis
 */
public class TNavTaskElementEditPart extends TNavItemElementEditPart {
	private static Logger logger = Logger.getLogger(TNavTaskElementEditPart.class);

	/**
	 * Create a new instance of this edit part using the given model element.
	 * @param model a non-null Shapes instance
	 */
	public TNavTaskElementEditPart(TaskElement model) {
		super(model);
	}
	
	public Object getAdapter(Class key) {
		if (IPropertySource.class == key) {
			Object obj = getModel();
			if(obj instanceof TaskElement) {
				return new TaskElementPropertySource((TaskElement)obj);
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
	
	protected TaskElement getCastedModel() {
		return (TaskElement) getModel();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.editparts.AbstractEditPart#getModelChildren()
	 */
	protected List getModelChildren() {
		TaskElement worker;
		worker = getCastedModel();
		
		ArrayList<String> list = new ArrayList<String>();
		String initTask = worker.getInitialTask() != null ?
				worker.getInitialTask().getName() : "";
		if(initTask != null && initTask.length() > 0) {
			list.add(initTask);
		}
		return list;
	}

	@Override
	protected Image createImageForModel(ItemElement shape) {
		Image baseImage;
		
		baseImage = getBaseImage();
		if(baseImage == null) {
			baseImage = TaskModelPlugin.getImageDescriptor(IconStrings.TASK_16).createImage();
			setBaseImage(baseImage);
		}

		Rectangle boundingRect = baseImage.getBounds();
		Image image = new Image(null, boundingRect.width, boundingRect.height);
		if(shape instanceof TaskElement) {
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
			refreshChildren();
			refreshVisuals();
			break;
		case Notification.ADD:
			switch(featureId)
			{
			case TaskModelPackage.WORKER_ELEMENT__INITIAL_TASK:
				addChild(createChild(notification.getNewValue()), -1);
				refreshChildren();
				break;
			}
			break;
		case Notification.REMOVE:
			switch(featureId)
			{
			case TaskModelPackage.WORKER_ELEMENT__INITIAL_TASK:
				removeChild(getEditPartForChild(notification.getOldValue()));
				refreshChildren();
				break;
			}
			break;
		}
	}
}