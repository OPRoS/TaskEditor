
package kr.re.etri.tpl.diagram.views.symbolparts;

import java.util.List;

import kr.re.etri.tpl.IconStrings;
import kr.re.etri.tpl.TaskModelPlugin;
import kr.re.etri.tpl.diagram.outline.policies.ShapeComponentTreeEditPolicy;
import kr.re.etri.tpl.diagram.properties.sources.FunctionPropertySource;
import kr.re.etri.tpl.taskmodel.ActionElement;
import kr.re.etri.tpl.taskmodel.Function;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.Parameter;
import kr.re.etri.tpl.taskmodel.TaskModelPackage;

import org.apache.log4j.Logger;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
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
 * TreeEditPart used for Function instances (more specific for EllipticalShape and
 * RectangularShape instances). This is used in the Outline View of the ShapesEditor.
 * <p>This edit part must implement the PropertyChangeListener interface, 
 * so it can be notified of property changes in the corresponding model element.
 * </p>
 * 
 * @author Elias Volanakis
 */
public class SNavFunctionEditPart extends SNavItemElementEditPart {
	private static Logger logger = Logger.getLogger(SNavFunctionEditPart.class);
	/**
	 * Create a new instance of this edit part using the given model element.
	 * @param model a non-null Shapes instance
	 */
	public SNavFunctionEditPart(Function model) {
		super(model);
	}
	
	public Object getAdapter(Class key) {
		if (IPropertySource.class == key) {
			Object obj = getModel();
			if(obj instanceof Function) {
				return new FunctionPropertySource((Function)obj);
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
	protected void hookIntoModel(EObject model) {
		super.hookIntoModel(model);
		
		if(getModel() instanceof ActionElement) {
			ActionElement action = (ActionElement)getModel();
			List<Parameter> paramList = action.getParams();
			for(Parameter param : paramList) {
				param.eAdapters().add(this);
			}
		}
	}

	@Override
	protected void unhookFromModel(EObject model) {
		super.unhookFromModel(model);
		
		if(getModel() instanceof ActionElement) {
			ActionElement action = (ActionElement)getModel();
			List<Parameter> paramList = action.getParams();
			for(Parameter param : paramList) {
				param.eAdapters().remove(this);
			}
		}
	}

	@Override
	public Function getCastedModel() {
		return (Function) getModel();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.editparts.AbstractEditPart#getModelChildren()
	 */
	protected List getModelChildren() {
		Function func;
		func = getCastedModel();
		EList<Parameter> list = func.getParams();
		return list;
	}
	
	private static final String ACTION = "action";
	@Override
	protected Image createImageForModel(ItemElement shape) {
		Image baseImage;
		
		baseImage = getBaseImage();
		if(baseImage == null) {
//			baseImage = TaskModelPlugin.getImageDescriptor("icons/symbol.gif").createImage();
			baseImage = TaskModelPlugin.getImageDescriptor(IconStrings.FUNCTION_16).createImage();
			if(shape instanceof Function) {
				String type =((Function)shape).getType();
				if(type.startsWith(ACTION)){
					baseImage = TaskModelPlugin.getImageDescriptor(IconStrings.FACTION_16).createImage();
				}
			}
			setBaseImage(baseImage);
		}

		Rectangle boundingRect = baseImage.getBounds();
//		Image image = new Image(null, boundingRect.width, boundingRect.height);
//		if(shape instanceof Function) {
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
		Function funcItem =  getCastedModel();
		return String.format("%s %s", funcItem.getType(), funcItem.getName());
	}

	public void notifyChanged(Notification notification) {

		Object notifier = notification.getNotifier();
		if(notifier instanceof Parameter) {
			parameterNotifyChanged(notification);
			return;
		}

		int type = notification.getEventType();
		int featureId = notification.getFeatureID(TaskModelPackage.class);

		switch(type) {
		case Notification.SET:
			refreshVisuals();
			break;
		case Notification.ADD:
			switch(featureId)
			{
			case TaskModelPackage.FUNCTION__PARAMS:
//				addChild(createChild(notification.getNewValue()), -1);
				refreshChildren();
				refreshVisuals();
				break;
			}
			break;
		case Notification.REMOVE:
			switch(featureId)
			{
			case TaskModelPackage.FUNCTION__PARAMS:
//				removeChild(getEditPartForChild(notification.getOldValue()));
				refreshChildren();
				refreshVisuals();
				break;
			}
			break;
		case Notification.MOVE:
			refreshChildren();
			break;
		default:
			super.notifyChanged(notification);
			break;
		}
	}

	public void parameterNotifyChanged(Notification notification) {

		int type = notification.getEventType();
		int featureId = notification.getFeatureID(TaskModelPackage.class);
		switch(type) {
		case Notification.SET:
			switch(featureId) {
			case TaskModelPackage.PARAMETER__TYPE:
				refreshChildren();
				refreshVisuals();
				break;
			default:
				super.notifyChanged(notification);
				break;
			}
			break;
		case Notification.ADD:
			switch(featureId) {
			default:
				super.notifyChanged(notification);
				break;
			}
			break;
		case Notification.ADD_MANY:
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
		case Notification.REMOVE_MANY:
			switch(featureId) {
			default:
				super.notifyChanged(notification);
				break;
			}
			break;
		}
	}
}