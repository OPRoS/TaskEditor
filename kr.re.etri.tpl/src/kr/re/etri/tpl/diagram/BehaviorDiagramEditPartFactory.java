
package kr.re.etri.tpl.diagram;

import java.util.HashMap;
import java.util.Map;

import kr.re.etri.tpl.diagram.editparts.behavior.BActionElementEditPart;
import kr.re.etri.tpl.diagram.editparts.behavior.BBehaviorDiagramEditPart;
import kr.re.etri.tpl.diagram.editparts.behavior.BBehaviorElementEditPart;
import kr.re.etri.tpl.diagram.editparts.behavior.BConnectionElementEditPart;
import kr.re.etri.tpl.diagram.editparts.behavior.BConnectorElementEditPart;
import kr.re.etri.tpl.diagram.editparts.behavior.BExpandTransElementEditPart;
import kr.re.etri.tpl.diagram.editparts.behavior.BObjectEditPart;
import kr.re.etri.tpl.diagram.editparts.behavior.BWithElementEditPart;
import kr.re.etri.tpl.diagram.editparts.behavior.BStateActionEditPart;
import kr.re.etri.tpl.diagram.editparts.behavior.BStateElementEditPart;
import kr.re.etri.tpl.diagram.editparts.behavior.BStructBlockEditPart;
import kr.re.etri.tpl.diagram.editparts.behavior.BTaskElementEditPart;
import kr.re.etri.tpl.taskmodel.ActionElement;
import kr.re.etri.tpl.taskmodel.BehaviorElement;
import kr.re.etri.tpl.taskmodel.ConnectionElement;
import kr.re.etri.tpl.taskmodel.ConnectorElement;
import kr.re.etri.tpl.taskmodel.ExpandTransElement;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.ReferElement;
import kr.re.etri.tpl.taskmodel.StateAction;
import kr.re.etri.tpl.taskmodel.StateElement;
import kr.re.etri.tpl.taskmodel.StructBlockElement;
import kr.re.etri.tpl.taskmodel.SubDiagram;
import kr.re.etri.tpl.taskmodel.TaskElement;
import kr.re.etri.tpl.taskmodel.WithElement;

import org.apache.log4j.Logger;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;


/**
 * Factory that maps model elements to edit parts.
 * @author Elias Volanakis
 */
public class BehaviorDiagramEditPartFactory implements EditPartFactory {
	private static Logger logger = Logger.getLogger(BehaviorDiagramEditPartFactory.class);
	
	private UniShortestConnectionRouter connectionRouter;
	protected Map constraints = new HashMap();
	
	public BehaviorDiagramEditPartFactory(UniShortestConnectionRouter router) {
		connectionRouter = router;
	}
	public BehaviorDiagramEditPartFactory() {

	}
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gef.EditPartFactory#createEditPart(org.eclipse.gef.EditPart, java.lang.Object)
	 */
	public EditPart createEditPart(EditPart context, Object itemElement) {
		// get EditPart for model element
		EditPart part = getPartForElement(itemElement);
		if(part == null) {
			return null;
		}
		// store model element in EditPart
		part.setModel(itemElement);
		return part;
	}

	/**
	 * Maps an object to an EditPart. 
	 * @throws RuntimeException if no match was found (programming error)
	 */
	public EditPart getPartForElement(Object itemElement) {
		EditPart editPart = (EditPart)constraints.get(itemElement);
		if(editPart != null) {
			return editPart;
		}

		if (itemElement instanceof SubDiagram) {
			logger.debug("BehaviorDiagram");
			editPart = new BBehaviorDiagramEditPart();
		}
		else if (itemElement instanceof ReferElement) {
			ItemElement realItem = ((ReferElement)itemElement).getRealModel();
			if (realItem instanceof BehaviorElement) {
				logger.debug("BehaviorElement");
				editPart = new BBehaviorElementEditPart(connectionRouter);
//				editPart = new TDgmTaskElementEditPart();
			}
			else if (realItem instanceof StateElement) {
				logger.debug("StateElement");
				editPart = new BStateElementEditPart();
			}
			else if (realItem instanceof StateAction) {
				logger.debug("StateAction");
				editPart = new BStateActionEditPart();
			}
			else if (realItem instanceof ActionElement) {
				logger.debug("ActionElement");
				editPart = new BActionElementEditPart();
			}
			else if (realItem instanceof ConnectionElement) {
				logger.debug("ConnectionElement");
				editPart = new BConnectionElementEditPart();
			}
			else if (realItem instanceof ConnectorElement){	// KJH 20101125, connector
				logger.debug("ConnectorElement");
				editPart = new BConnectorElementEditPart(connectionRouter);
			}
			else if (realItem instanceof StructBlockElement) {	// KJH 20110209, structblock
				logger.debug("StructBlock");
				editPart = new BStructBlockEditPart();
			}
			else if (realItem instanceof TaskElement) {	// KJH 20110413, task
				logger.debug("TaskElement");
				editPart = new BTaskElementEditPart(connectionRouter);
			}
			// KJH 20110503 s, with
			else if (realItem instanceof WithElement) {
				logger.debug("WithElement");
				editPart = new BWithElementEditPart();
			}// KJH 20110503 s, with
			// KJH 20110512 s, ExpandTransElement
			else if (realItem instanceof ExpandTransElement) {
				editPart = new BExpandTransElementEditPart();
			}// KJH 20110512 e, ExpandTransElement
			else {
				editPart = new BObjectEditPart();
			}
		}
		else if (itemElement instanceof TaskElement) {
			editPart = new BTaskElementEditPart(connectionRouter);
		}
		else if (itemElement instanceof BehaviorElement) {
			editPart = new BBehaviorElementEditPart(connectionRouter);
//			editPart = new TDgmTaskElementEditPart();
		}
		else if (itemElement instanceof StateElement) {
			editPart = new BStateElementEditPart();
		}
		else if (itemElement instanceof StateAction) {
			logger.debug("StateAction2222");
			editPart = new BStateActionEditPart();
		}
		else if (itemElement instanceof ActionElement) {
			editPart = new BActionElementEditPart();
		}
		else if (itemElement instanceof ConnectionElement) {
			editPart = new BConnectionElementEditPart();
		}
		else if (itemElement instanceof ConnectorElement){	// KJH 20101125, connector
			logger.debug("ConnectorElement");
			editPart = new BConnectorElementEditPart(connectionRouter);
		}
		else if (itemElement instanceof StructBlockElement) {	// KJH 20110209 s, structblock
			logger.debug("StructBlock2222");
			editPart = new BStructBlockEditPart();
		}	// KJH 20110209 e, structblock
		// KJH 20110503 s, with
		else if (itemElement instanceof WithElement) {
			logger.debug("WithElement");
			editPart = new BWithElementEditPart();
		}// KJH 20110503 s, with
		// KJH 20110512 s, ExpandTransElement
		else if (itemElement instanceof ExpandTransElement) {
			editPart = new BExpandTransElementEditPart();
		}// KJH 20110512 e, ExpandTransElement
		else {
			editPart = new BObjectEditPart();
		}

		if(editPart == null) {
			return null;
		}
		
		constraints.put(itemElement, editPart);

		return editPart;
	}

}