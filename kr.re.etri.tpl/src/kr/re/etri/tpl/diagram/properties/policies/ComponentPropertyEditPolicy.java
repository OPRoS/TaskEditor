
package kr.re.etri.tpl.diagram.properties.policies;

import kr.re.etri.tpl.diagram.commands.ShapeModelDeleteCommand;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.ModelDiagram;
import kr.re.etri.tpl.taskmodel.ReferElement;
import kr.re.etri.tpl.taskmodel.ShapeElement;
import kr.re.etri.tpl.taskmodel.StateElement;
import kr.re.etri.tpl.taskmodel.SubDiagram;
import kr.re.etri.tpl.taskmodel.BehaviorElement;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

/**
 * 이 클래스는 Container 로부터 Shape instance 의 삭제 가능하도록 하는 Policy 클래스이다.
 * 이 Policy 클래스는 Properties View 에서 모델을 삭제하기 위하여 사용된다.
 * 
 * @see org.eclipse.gef.editpolicies.ComponentEditPolicy
 * 
 * @author sfline
 */
public class ComponentPropertyEditPolicy extends ComponentEditPolicy {

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
		if (parent instanceof ModelDiagram && child instanceof ShapeElement) {
			return new ShapeModelDeleteCommand((ModelDiagram) parent, (ShapeElement) child);
		}
		if (parent instanceof BehaviorElement && child instanceof ReferElement) {
			return new ShapeModelDeleteCommand((SubDiagram) parent, (ReferElement) child);
		}
		else if (parent instanceof BehaviorElement && child instanceof StateElement) {
			return new ShapeModelDeleteCommand((BehaviorElement) parent, (StateElement) child);
		}
		return super.createDeleteCommand(deleteRequest);
	}
}