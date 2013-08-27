
package kr.re.etri.tpl.diagram.views.actionparts;

import java.util.HashMap;
import java.util.Map;

import kr.re.etri.tpl.taskmodel.ActionElement;
import kr.re.etri.tpl.taskmodel.EnumElement;
import kr.re.etri.tpl.taskmodel.IncludedElement;
import kr.re.etri.tpl.taskmodel.ModelDiagram;
import kr.re.etri.tpl.taskmodel.Parameter;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;

/**
 * Factory that maps model elements to edit parts.
 * @author Elias Volanakis
 */
public class ActionNavigatorEditPartFactory implements EditPartFactory {
	
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
			editPart = new ANavDiagramEditPart((ModelDiagram)itemElement);
		}
		else if (itemElement instanceof IncludedElement) {
			editPart = new ANavIncludedElementEditPart((IncludedElement)itemElement);
		}
		else if (itemElement instanceof ActionElement) {
			editPart = new ANavActionElementEditPart((ActionElement)itemElement);
		}
		else if (itemElement instanceof Parameter) {
			editPart = new ANavParameterEditPart((Parameter)itemElement);
		}
		else if (itemElement instanceof EnumElement) {
			editPart = new ANavParameterEditPart((Parameter)itemElement);
		}
		else {
			editPart = new ANavObjectEditPart(itemElement);
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