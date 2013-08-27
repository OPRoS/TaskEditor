
package kr.re.etri.tpl.diagram.editparts.task.policies;

import kr.re.etri.tpl.diagram.commands.ShapeModelDeleteCommand;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.ReferElement;
import kr.re.etri.tpl.taskmodel.StateElement;
import kr.re.etri.tpl.taskmodel.SubDiagram;
import kr.re.etri.tpl.taskmodel.BehaviorElement;

import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

/**
 * 이 클래스는 Container 로부터 Shape instance 의 삭제 가능하도록 하는 Policy 클래스이다.
 * 
 *  @author sfline
 */
public class TBehaviorElementComponentEditPolicy extends ComponentEditPolicy {

	/**
	 * Factors the incoming Request into ORPHANs and DELETEs.
	 * @see org.eclipse.gef.EditPolicy#getCommand(Request)
	 */
	public Command getCommand(Request request) {
		return super.getCommand(request);
	}

	/**
	 * request 에 대한 삭제 command 를 생성한다.
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
		else if (parent instanceof BehaviorElement && child instanceof ReferElement) {
			return new ShapeModelDeleteCommand((BehaviorElement) parent, (ReferElement) child);
		}
		else if (parent instanceof BehaviorElement && child instanceof StateElement) {
			return new ShapeModelDeleteCommand((BehaviorElement) parent, (StateElement) child);
		}
		return super.createDeleteCommand(deleteRequest);
	}
}