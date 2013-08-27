
package kr.re.etri.tpl.diagram.editparts.task.policies;

import kr.re.etri.tpl.diagram.commands.ShapeModelDeleteCommand;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.ReferElement;
import kr.re.etri.tpl.taskmodel.SubDiagram;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

/**
 * �� Ŭ������ Container �κ��� Shape instance �� ���� �����ϵ��� �ϴ� Policy Ŭ�����̴�.
 * 
 * @author sfline
 */
public class TComponentEditPolicy extends ComponentEditPolicy {

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
		if (parent instanceof SubDiagram && child instanceof ReferElement) {
			return new ShapeModelDeleteCommand((SubDiagram) parent, (ReferElement) child);
		}
		else if (parent instanceof ReferElement && child instanceof ReferElement) {
			return new ShapeModelDeleteCommand((ReferElement) parent, (ReferElement) child);
		}

		return super.createDeleteCommand(deleteRequest);
	}
}