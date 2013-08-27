package kr.re.etri.tpl.diagram;

import java.util.HashMap;
import java.util.Map;

import kr.re.etri.tpl.diagram.outline.ActionElementTreeEditPart;
import kr.re.etri.tpl.diagram.outline.BehaviorElementTreeEditPart;
import kr.re.etri.tpl.diagram.outline.ConnectionElementTreeEditPart;
import kr.re.etri.tpl.diagram.outline.ConnectorElementTreeEditPart;
import kr.re.etri.tpl.diagram.outline.DiagramTreeEditPart;
import kr.re.etri.tpl.diagram.outline.ExpandTransElementTreeEditPart;
import kr.re.etri.tpl.diagram.outline.StateActionTreeEditPart;
import kr.re.etri.tpl.diagram.outline.StateElementTreeEditPart;
import kr.re.etri.tpl.diagram.outline.StructBlockTreeEditPart;
import kr.re.etri.tpl.diagram.outline.TaskElementTreeEditPart;
import kr.re.etri.tpl.diagram.outline.WithElementTreeEditPart;
import kr.re.etri.tpl.diagram.views.actionparts.ANavObjectEditPart;
import kr.re.etri.tpl.diagram.views.actionparts.ANavParameterEditPart;
import kr.re.etri.tpl.taskmodel.ActionElement;
import kr.re.etri.tpl.taskmodel.BehaviorElement;
import kr.re.etri.tpl.taskmodel.ConnectionElement;
import kr.re.etri.tpl.taskmodel.ConnectorElement;
import kr.re.etri.tpl.taskmodel.ExpandTransElement;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.ModelDiagram;
import kr.re.etri.tpl.taskmodel.Parameter;
import kr.re.etri.tpl.taskmodel.StateAction;
import kr.re.etri.tpl.taskmodel.StateElement;
import kr.re.etri.tpl.taskmodel.StructBlockElement;
import kr.re.etri.tpl.taskmodel.TaskElement;
import kr.re.etri.tpl.taskmodel.WithElement;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;

/**
 * 모델에 대한 TreeEditPart 를 생성할 수있는 Factory 클래스이다.
 * TreeEditPart 는 Outline View 에 사용된다.
 * 
 * @author sfline
 */
public class ShapeModelTreeEditPartFactory implements EditPartFactory {
	/** */
	protected Map constraints = new HashMap();

	/**
	 * 주어진 모델에 대한 TreeEditPart 를 생성하여 제공한다.
	 * @param context EditPart 의 부모 EditPart
	 * @param itemElement 모델
	 * @return 모델에 대한 EditPart
	 * 
	 * @see org.eclipse.gef.EditPartFactory#createEditPart(org.eclipse.gef.EditPart, java.lang.Object)
	 */
	public EditPart createEditPart(EditPart context, Object model) {
		// get EditPart for model element
		EditPart part = getPartForElement(model);
		if(part == null) {
			return null;
		}
		// store model element in EditPart
		part.setModel(model);
		return part;
	}

	/**
	 * 모델 타입에 대한 TreeEditPart 를 생성한다.
	 * @param itemElement 모델
	 * @return 모델 타입에 대한 EditPart
	 * @throws RuntimeException if no match was found (programming error)
	 */
	public EditPart getPartForElement(Object itemElement) {
		EditPart editPart = (EditPart)constraints.get(itemElement);
		if(editPart != null) {
			return editPart;
		}

		if (itemElement instanceof ModelDiagram) {
			editPart = new DiagramTreeEditPart((ModelDiagram)itemElement);
		}
		else if (itemElement instanceof TaskElement) {
			editPart = new TaskElementTreeEditPart((TaskElement) itemElement);
		}
		else if (itemElement instanceof BehaviorElement) {
			editPart = new BehaviorElementTreeEditPart((BehaviorElement) itemElement);
		}
		else if (itemElement instanceof StateElement) {
			editPart = new StateElementTreeEditPart((StateElement) itemElement);
		}
		else if (itemElement instanceof StateAction) {
			editPart = new StateActionTreeEditPart((StateAction) itemElement);
		}
		else if (itemElement instanceof ActionElement) {
			editPart = new ActionElementTreeEditPart((ActionElement) itemElement);
		}
		else if (itemElement instanceof Parameter) {
			editPart = new ANavParameterEditPart((Parameter)itemElement);
		}
		else if (itemElement instanceof ConnectionElement) {
			editPart = new ConnectionElementTreeEditPart((ConnectionElement) itemElement);
		}
		else if (itemElement instanceof ConnectorElement) {
			editPart = new ConnectorElementTreeEditPart((ItemElement) itemElement);
		}
		// KJH 20110210 s, StructBlock
		else if (itemElement instanceof StructBlockElement) {
			editPart = new StructBlockTreeEditPart((StructBlockElement) itemElement);
		}	// KJH 20110210 e, StructBlock
		// KJH 20110503 s, WithElement
		else if (itemElement instanceof WithElement) {
			editPart = new WithElementTreeEditPart((WithElement) itemElement);
		}	// KJH 20110503 e, WithElement
		// KJH 20110512 s, ExpandTransElement
		else if (itemElement instanceof ExpandTransElement) {
			editPart = new ExpandTransElementTreeEditPart((ExpandTransElement) itemElement);
		}// KJH 20110512 e, ExpandTransElement
		else {
			editPart = new ANavObjectEditPart(itemElement);
		}

		if(editPart != null) {
			constraints.put(itemElement, editPart);
		}

		return editPart; // will not show an entry for the corresponding model instance
	}
}
