
package kr.re.etri.tpl.diagram.views.symbolparts;

import java.util.HashMap;
import java.util.Map;

import kr.re.etri.tpl.taskmodel.ActionElement;
import kr.re.etri.tpl.taskmodel.Constant;
import kr.re.etri.tpl.taskmodel.EnumElement;
import kr.re.etri.tpl.taskmodel.EnumItemElement;
import kr.re.etri.tpl.taskmodel.Function;
import kr.re.etri.tpl.taskmodel.IncludedElement;
import kr.re.etri.tpl.taskmodel.ModelDiagram;
import kr.re.etri.tpl.taskmodel.ModelElement;
import kr.re.etri.tpl.taskmodel.Parameter;
import kr.re.etri.tpl.taskmodel.Symbol;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;

/**
 * Factory that maps model elements to edit parts.
 * @author Elias Volanakis
 */
public class SymbolNavigatorEditPartFactory implements EditPartFactory {
	
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
			editPart = new SNavDiagramEditPart((ModelDiagram)itemElement);
		}
		else if (itemElement instanceof IncludedElement) {
			editPart = new SNavIncludedElementEditPart((IncludedElement)itemElement);
		}
		else if (itemElement instanceof EnumElement) {
			editPart = new SNavEnumElementEditPart((EnumElement)itemElement);
		}
		else if (itemElement instanceof ModelElement) {
			editPart = new SNavModelElementEditPart((ModelElement)itemElement);
		}
		else if (itemElement instanceof ActionElement) {
			editPart = new SNavActionElementEditPart((ActionElement)itemElement);
		}
		else if (itemElement instanceof Constant) {
			editPart = new SNavConstantEditPart((Constant)itemElement);
		}
		else if (itemElement instanceof Symbol) {
			editPart = new SNavSymbolEditPart((Symbol)itemElement);
		}
		else if (itemElement instanceof Function) {
			editPart = new SNavFunctionEditPart((Function)itemElement);
		}
		else if (itemElement instanceof EnumItemElement) {
			editPart = new SNavEnumItemEditPart((EnumItemElement)itemElement);
		}
		else if (itemElement instanceof Parameter) {
			editPart = new SNavParameterEditPart((Parameter)itemElement);
		}
		else {
			editPart = new SNavObjectEditPart(itemElement);
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