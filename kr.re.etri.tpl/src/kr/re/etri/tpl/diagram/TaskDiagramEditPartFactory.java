
package kr.re.etri.tpl.diagram;

import java.util.HashMap;
import java.util.Map;

import kr.re.etri.tpl.diagram.editparts.task.TActionElementEditPart;
import kr.re.etri.tpl.diagram.editparts.task.TBehaviorDiagramEditPart;
import kr.re.etri.tpl.diagram.editparts.task.TBehaviorElementEditPart;
import kr.re.etri.tpl.diagram.editparts.task.TConnectionElementEditPart;
import kr.re.etri.tpl.diagram.editparts.task.TObjectEditPart;
import kr.re.etri.tpl.diagram.editparts.task.TStateActionEditPart;
import kr.re.etri.tpl.diagram.editparts.task.TStateElementEditPart;
import kr.re.etri.tpl.diagram.editparts.task.TTaskElementEditPart;
import kr.re.etri.tpl.taskmodel.ActionElement;
import kr.re.etri.tpl.taskmodel.BehaviorElement;
import kr.re.etri.tpl.taskmodel.ConnectionElement;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.ReferElement;
import kr.re.etri.tpl.taskmodel.StateAction;
import kr.re.etri.tpl.taskmodel.StateElement;
import kr.re.etri.tpl.taskmodel.SubDiagram;
import kr.re.etri.tpl.taskmodel.TaskElement;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;

/**
 * Factory that maps model elements to edit parts.
 * @author Elias Volanakis
 */
public class TaskDiagramEditPartFactory implements EditPartFactory {
	
	private ShortestConnectionRouter connectionRouter;
	protected Map constraints = new HashMap();
	
	public TaskDiagramEditPartFactory(ShortestConnectionRouter router) {
		connectionRouter = router;
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
			editPart = new TBehaviorDiagramEditPart();
		}
		else if (itemElement instanceof ReferElement) {
			ItemElement realItem = ((ReferElement)itemElement).getRealModel();
			if (realItem instanceof BehaviorElement) {
				editPart = new TBehaviorElementEditPart(connectionRouter);
			}
			else if (realItem instanceof StateElement) {
				editPart = new TStateElementEditPart();
			}
			else if (realItem instanceof StateAction) {
				editPart = new TStateActionEditPart();
			}
			else if (realItem instanceof ActionElement) {
				editPart = new TActionElementEditPart();
			}
			else if (realItem instanceof ConnectionElement) {
				editPart = new TConnectionElementEditPart();
			}
			else {
				editPart = new TObjectEditPart();
			}
		}
		else if (itemElement instanceof TaskElement) {
			editPart = new TTaskElementEditPart();
		}
		else if (itemElement instanceof BehaviorElement) {
			editPart = new TBehaviorElementEditPart(connectionRouter);
		}
		else if (itemElement instanceof StateElement) {
			editPart = new TStateElementEditPart();
		}
		else if (itemElement instanceof StateAction) {
			editPart = new TStateActionEditPart();
		}
		else if (itemElement instanceof ActionElement) {
			editPart = new TActionElementEditPart();
		}
		else if (itemElement instanceof ConnectionElement) {
			editPart = new TConnectionElementEditPart();
		}
		else {
			editPart = new TObjectEditPart();
		}

		if(editPart == null) {
/*			
			throw new RuntimeException(
					"Can't create part for model element: "
					+ ((itemElement != null) ? itemElement.getClass().getName() : "null"));
*/
			return null;
		}
		
		constraints.put(itemElement, editPart);

		return editPart;
	}

}