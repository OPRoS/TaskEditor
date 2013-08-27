
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
 * 모델별 PropertyEditPart 를 생성할 수있는 Factory 클래스이다.
 * @author sfline
 */
public class PropertyEditPartFactory implements EditPartFactory {
	/** 모델 속성이 표시될 control 의 container */
	Composite control;
	
	/**
	 * PropertyEditPart 를 생성한다.
	 * @param control 모델 속성이 표시될 control 의 container
	 */
	public PropertyEditPartFactory(Composite control) {
		this.control = control;
	}
	
	/**
	 * 주어진 모델에 대한 EditPart 를 생성하여 제공한다.
	 * @param context EditPart 의 부모 EditPart
	 * @param itemElement 모델
	 * @return 모델에 대한 EditPart
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
	 * 모델 타입에 대한 EditPart 를 생성한다.
	 * @param itemElement 모델
	 * @return 모델 타입에 대한 EditPart
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