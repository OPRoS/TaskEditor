
package kr.re.etri.tpl.diagram.views.symbolparts;

import java.util.ArrayList;
import java.util.List;

import kr.re.etri.tpl.IconStrings;
import kr.re.etri.tpl.TaskModelPlugin;
import kr.re.etri.tpl.diagram.outline.policies.ShapeComponentTreeEditPolicy;
import kr.re.etri.tpl.diagram.properties.sources.ModelElementPropertySource;
import kr.re.etri.tpl.taskmodel.ActionElement;
import kr.re.etri.tpl.taskmodel.Constant;
import kr.re.etri.tpl.taskmodel.Function;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.ModelElement;
import kr.re.etri.tpl.taskmodel.Symbol;
import kr.re.etri.tpl.taskmodel.TaskModelPackage;

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
 * TreeEditPart used for ModelElement instances (more specific for EllipticalShape and
 * RectangularShape instances). This is used in the Outline View of the ShapesEditor.
 * <p>This edit part must implement the PropertyChangeListener interface, 
 * so it can be notified of property changes in the corresponding model element.
 * </p>
 * 
 * @author Elias Volanakis
 */
public class SNavModelElementEditPart extends SNavItemElementEditPart {
	
	/**
	 * Create a new instance of this edit part using the given model element.
	 * @param model a non-null Shapes instance
	 */
	public SNavModelElementEditPart(ModelElement model) {
		super(model);
	}
	
	public Object getAdapter(Class key) {
		if (IPropertySource.class == key) {
			Object obj = getModel();
			if(obj instanceof ModelElement) {
				return new ModelElementPropertySource((ModelElement)obj);
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
		
		if(getModel() instanceof ModelElement) {
			ModelElement modelElmt = (ModelElement)getModel();
			List<Symbol> symList = modelElmt.getSymbols();
			for(Symbol symItem : symList) {
				symItem.eAdapters().add(this);
			}
			List<Constant> constList = modelElmt.getConstants();
			for(Constant constItem : constList) {
				constItem.eAdapters().add(this);
			}
			List<Function> funcList = modelElmt.getFunctions();
			for(Function funcItem : funcList) {
				funcItem.eAdapters().add(this);
			}
			List<ModelElement> modleList = modelElmt.getModels();
			for(ModelElement modelItem : modleList) {
				modelItem.eAdapters().add(this);
			}
		}
	}

	@Override
	protected void unhookFromModel(EObject model) {
		super.unhookFromModel(model);
		
		if(getModel() instanceof ModelElement) {
			ModelElement modelElmt = (ModelElement)getModel();
			List<Symbol> symList = modelElmt.getSymbols();
			for(Symbol symItem : symList) {
				symItem.eAdapters().remove(this);
			}
			List<Constant> constList = modelElmt.getConstants();
			for(Constant constItem : constList) {
				constItem.eAdapters().remove(this);
			}
			List<Function> funcList = modelElmt.getFunctions();
			for(Function funcItem : funcList) {
				funcItem.eAdapters().remove(this);
			}
			List<ModelElement> modleList = modelElmt.getModels();
			for(ModelElement modelItem : modleList) {
				modelItem.eAdapters().remove(this);
			}
		}
	}

	@Override
	public ModelElement getCastedModel() {
		return (ModelElement) getModel();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.editparts.AbstractEditPart#getModelChildren()
	 */
	protected List getModelChildren() {
		ModelElement model;
		model = getCastedModel();
		EList<Symbol> symList = model.getSymbols();
		EList<Constant> constList = model.getConstants();
		EList<ModelElement> modelList = model.getModels();
		EList<Function> funcList = model.getFunctions();

		ArrayList<ItemElement> list = new ArrayList<ItemElement>();
		list.addAll(symList);
		list.addAll(constList);
		list.addAll(modelList);
		list.addAll(funcList);
		
		return list;
	}

	@Override
	protected Image createImageForModel(ItemElement shape) {
		Image baseImage;
		
		baseImage = getBaseImage();
		if(baseImage == null) {
//			baseImage = TaskModelPlugin.getImageDescriptor("icons/m_b.gif").createImage();
			baseImage = TaskModelPlugin.getImageDescriptor(IconStrings.MODEL_16).createImage();
			setBaseImage(baseImage);
		}

		Rectangle boundingRect = baseImage.getBounds();
//		Image image = new Image(null, boundingRect.width, boundingRect.height);
//		if(shape instanceof ModelElement) {
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
		return getCastedModel().getName();
	}

	public void notifyChanged(Notification notification) {

		Object notifier = notification.getNotifier();
		if(notifier instanceof Symbol) {
			symbolNotifyChanged(notification);
			return;
		}
		if(notifier instanceof Constant) {
			constantNotifyChanged(notification);
			return;
		}
		if(notifier instanceof Function) {
			functionNotifyChanged(notification);
			return;
		}
		if((notifier instanceof ModelElement) && (notifier != getModel())) {
			modelNotifyChanged(notification);
			return;
		}

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
			case TaskModelPackage.MODEL_ELEMENT__CONSTANTS:
			case TaskModelPackage.MODEL_ELEMENT__SYMBOLS:
			case TaskModelPackage.MODEL_ELEMENT__FUNCTIONS:
			case TaskModelPackage.MODEL_ELEMENT__MODELS:
//				addChild(createChild(notification.getNewValue()), -1);
				refreshChildren();
				break;
			}
			break;
		case Notification.REMOVE:
			switch(featureId)
			{
			case TaskModelPackage.MODEL_ELEMENT__CONSTANTS:
			case TaskModelPackage.MODEL_ELEMENT__SYMBOLS:
			case TaskModelPackage.MODEL_ELEMENT__FUNCTIONS:
			case TaskModelPackage.MODEL_ELEMENT__MODELS:
//				removeChild(getEditPartForChild(notification.getOldValue()));
				refreshChildren();
				break;
			}
			break;
		case Notification.ADD_MANY:
		case Notification.REMOVE_MANY:
		case Notification.MOVE:
			refreshChildren();
			break;
		default:
			super.notifyChanged(notification);
			break;
		}
	}

	public void symbolNotifyChanged(Notification notification) {

		int type = notification.getEventType();
		int featureId = notification.getFeatureID(TaskModelPackage.class);
		switch(type) {
		case Notification.SET:
			switch(featureId) {
			case TaskModelPackage.SYMBOL__DIRECTION:
			case TaskModelPackage.SYMBOL__TYPE:
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

	public void constantNotifyChanged(Notification notification) {

		int type = notification.getEventType();
		int featureId = notification.getFeatureID(TaskModelPackage.class);
		switch(type) {
		case Notification.SET:
			switch(featureId) {
			case TaskModelPackage.CONSTANT__TYPE:
			case TaskModelPackage.CONSTANT__INIT_VALUE:
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

	public void functionNotifyChanged(Notification notification) {

		int type = notification.getEventType();
		int featureId = notification.getFeatureID(TaskModelPackage.class);
		switch(type) {
		case Notification.SET:
			switch(featureId) {
			case TaskModelPackage.FUNCTION__TYPE:
			case TaskModelPackage.FUNCTION__PARAMS:
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
			case TaskModelPackage.FUNCTION__PARAMS:
				refreshChildren();
				break;
			default:
				super.notifyChanged(notification);
				break;
			}
			break;
		case Notification.ADD_MANY:
			switch(featureId) {
			case TaskModelPackage.FUNCTION__PARAMS:
				refreshChildren();
				break;
			default:
				super.notifyChanged(notification);
				break;
			}
			break;
		case Notification.REMOVE:
			switch(featureId) {
			case TaskModelPackage.FUNCTION__PARAMS:
				refreshChildren();
				break;
			default:
				super.notifyChanged(notification);
				break;
			}
			break;
		case Notification.REMOVE_MANY:
			switch(featureId) {
			case TaskModelPackage.FUNCTION__PARAMS:
				refreshChildren();
				break;
			default:
				super.notifyChanged(notification);
				break;
			}
			break;
		}
	}

	public void modelNotifyChanged(Notification notification) {

		int type = notification.getEventType();
		int featureId = notification.getFeatureID(TaskModelPackage.class);
		switch(type) {
		case Notification.SET:
			switch(featureId) {
			case TaskModelPackage.MODEL_ELEMENT__SYMBOLS:
			case TaskModelPackage.MODEL_ELEMENT__CONSTANTS:
			case TaskModelPackage.MODEL_ELEMENT__FUNCTIONS:
			case TaskModelPackage.MODEL_ELEMENT__MODELS:
				refreshChildren();
				break;
			default:
				super.notifyChanged(notification);
				break;
			}
			break;
		case Notification.ADD:
			switch(featureId) {
			case TaskModelPackage.MODEL_ELEMENT__SYMBOLS:
			case TaskModelPackage.MODEL_ELEMENT__CONSTANTS:
			case TaskModelPackage.MODEL_ELEMENT__FUNCTIONS:
			case TaskModelPackage.MODEL_ELEMENT__MODELS:
				refreshChildren();
				break;
			default:
				super.notifyChanged(notification);
				break;
			}
			break;
		case Notification.ADD_MANY:
			switch(featureId) {
			case TaskModelPackage.MODEL_ELEMENT__SYMBOLS:
			case TaskModelPackage.MODEL_ELEMENT__CONSTANTS:
			case TaskModelPackage.MODEL_ELEMENT__FUNCTIONS:
			case TaskModelPackage.MODEL_ELEMENT__MODELS:
				refreshChildren();
				break;
			default:
				super.notifyChanged(notification);
				break;
			}
			break;
		case Notification.REMOVE:
			switch(featureId) {
			case TaskModelPackage.MODEL_ELEMENT__SYMBOLS:
			case TaskModelPackage.MODEL_ELEMENT__CONSTANTS:
			case TaskModelPackage.MODEL_ELEMENT__FUNCTIONS:
			case TaskModelPackage.MODEL_ELEMENT__MODELS:
				refreshChildren();
				break;
			default:
				super.notifyChanged(notification);
				break;
			}
			break;
		case Notification.REMOVE_MANY:
			switch(featureId) {
			case TaskModelPackage.MODEL_ELEMENT__SYMBOLS:
			case TaskModelPackage.MODEL_ELEMENT__CONSTANTS:
			case TaskModelPackage.MODEL_ELEMENT__FUNCTIONS:
			case TaskModelPackage.MODEL_ELEMENT__MODELS:
				refreshChildren();
				break;
			default:
				super.notifyChanged(notification);
				break;
			}
			break;
		}
	}
}