
package kr.re.etri.tpl.diagram.outline.policies;

import kr.re.etri.tpl.diagram.commands.ShapeModelDeleteCommand;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.ModelDiagram;
import kr.re.etri.tpl.taskmodel.ShapeElement;
import kr.re.etri.tpl.taskmodel.StateElement;
import kr.re.etri.tpl.taskmodel.BehaviorElement;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

/**
 * �� Ŭ������ Container �κ��� Shape instance �� ���� �����ϵ��� �ϴ� Policy Ŭ�����̴�.
 * �� Policy Ŭ������ Outline View ���� ���� �����ϱ� ���Ͽ� ���ȴ�.
 * 
 * @see org.eclipse.gef.editpolicies.ComponentEditPolicy
 * 
 * @author sfline
 */
public class BehaviorElementTreeEditPolicy extends ComponentEditPolicy {

	/**
	 * request �� ���� ���� command �� �����Ѵ�.
	 * @see org.eclipse.gef.editpolicies.ComponentEditPolicy#createDeleteCommand(org.eclipse.gef.requests.GroupRequest)
	 * 
	 * @Override
	 */
	protected Command createDeleteCommand(GroupRequest deleteRequest) {
		Object parent = getHost().getParent().getModel();
		Object child = getHost().getModel();
		if(parent instanceof ItemElement) {
			if(((ItemElement)parent).isIncluded()) {
				return null;
			}
		}
		if (parent instanceof ModelDiagram && child instanceof ShapeElement) {
			return new ShapeModelDeleteCommand((ModelDiagram) parent, (ShapeElement) child);
		}
		else if (parent instanceof BehaviorElement && child instanceof StateElement) {
			return new ShapeModelDeleteCommand((BehaviorElement) parent, (StateElement) child);
		}
		return super.createDeleteCommand(deleteRequest);
	}
}