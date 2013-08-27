
package kr.re.etri.tpl.diagram.views.taskparts;

import java.util.HashMap;
import java.util.Map;

import kr.re.etri.tpl.taskmodel.BehaviorElement;
import kr.re.etri.tpl.taskmodel.IncludedElement;
import kr.re.etri.tpl.taskmodel.ModelDiagram;
import kr.re.etri.tpl.taskmodel.Parameter;
import kr.re.etri.tpl.taskmodel.TaskElement;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;

/**
 * Factory that maps model elements to edit parts.
 * @author Elias Volanakis
 */
public class TaskNavigatorEditPartFactory implements EditPartFactory {
	
	protected Map constraints = new HashMap();
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

		if (itemElement instanceof ModelDiagram) {
			editPart = new TNavDiagramEditPart((ModelDiagram)itemElement);
		}
		else if (itemElement instanceof IncludedElement) {
			editPart = new TNavIncludedElementEditPart((IncludedElement)itemElement);
		}
		else if (itemElement instanceof TaskElement) {
			editPart = new TNavTaskElementEditPart((TaskElement)itemElement);
		}
		else if (itemElement instanceof BehaviorElement){
			editPart = new TNavBehaviorElementEditPart((BehaviorElement)itemElement);
		}
		else if (itemElement instanceof Parameter) {
			editPart = new TNavParameterEditPart((Parameter)itemElement);
		}
		else {
			editPart = new TNavObjectEditPart(itemElement);
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