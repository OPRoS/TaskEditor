
package kr.re.etri.tpl.diagram;

import kr.re.etri.tpl.diagram.properties.ConnectionPropertyEditPart;
import kr.re.etri.tpl.diagram.properties.DiagramPropertyEditPart;
import kr.re.etri.tpl.diagram.properties.ShapeModelPropertyEditPart;
import kr.re.etri.tpl.taskmodel.ConnectionElement;
import kr.re.etri.tpl.taskmodel.ModelDiagram;
import kr.re.etri.tpl.taskmodel.ShapeElement;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;
import org.eclipse.swt.widgets.Composite;

/**
 * �𵨺� PropertyEditPart �� ������ ���ִ� Factory Ŭ�����̴�.
 * @author sfline
 */
public class PropertyEditPartFactory implements EditPartFactory {
	/** �� �Ӽ��� ǥ�õ� control �� container */
	Composite control;
	
	/**
	 * PropertyEditPart �� �����Ѵ�.
	 * @param control �� �Ӽ��� ǥ�õ� control �� container
	 */
	public PropertyEditPartFactory(Composite control) {
		this.control = control;
	}
	
	/**
	 * �־��� �𵨿� ���� EditPart �� �����Ͽ� �����Ѵ�.
	 * @param context EditPart �� �θ� EditPart
	 * @param itemElement ��
	 * @return �𵨿� ���� EditPart
	 * 
	 * @see org.eclipse.gef.EditPartFactory#createEditPart(org.eclipse.gef.EditPart, java.lang.Object)
	 */
	public EditPart createEditPart(EditPart context, Object itemElement) {
		// get EditPart for model element
		EditPart part = getPartForElement(itemElement);
		// store model element in EditPart
		part.setModel(itemElement);
		return part;
	}
	
	/**
	 * �� Ÿ�Կ� ���� EditPart �� �����Ѵ�.
	 * @param itemElement ��
	 * @return �� Ÿ�Կ� ���� EditPart
	 * @throws RuntimeException if no match was found (programming error)
	 */
	private EditPart getPartForElement(Object itemElement) {
		if (itemElement instanceof ModelDiagram) {
			return new DiagramPropertyEditPart(this.control);
		}
		if (itemElement instanceof ShapeElement) {
			return new ShapeModelPropertyEditPart(this.control);
		}
		if (itemElement instanceof ConnectionElement) {
			return new ConnectionPropertyEditPart(this.control);
		}
		throw new RuntimeException(
				"Can't create part for model element: "
				+ ((itemElement != null) ? itemElement.getClass().getName() : "null"));
	}
	
}