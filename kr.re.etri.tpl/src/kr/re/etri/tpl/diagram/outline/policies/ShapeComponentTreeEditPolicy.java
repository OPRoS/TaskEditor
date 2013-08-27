
package kr.re.etri.tpl.diagram.outline.policies;

import java.util.Iterator;

import kr.re.etri.tpl.diagram.commands.ConnectionDeleteCommand;
import kr.re.etri.tpl.diagram.commands.ShapeModelDeleteCommand;
import kr.re.etri.tpl.diagram.commands.WithElementDeleteCommand;
import kr.re.etri.tpl.taskmodel.ConnectionElement;
import kr.re.etri.tpl.taskmodel.ExpandTransElement;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.ReferElement;
import kr.re.etri.tpl.taskmodel.StateElement;
import kr.re.etri.tpl.taskmodel.WithElement;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

/**
 * 이 클래스는 Container 로부터 Shape instance 의 삭제 가능하도록 하는 Policy 클래스이다.
 * 이 Policy 클래스는 Outline View 에서 모델을 삭제하기 위하여 사용된다.
 * 
 * @see org.eclipse.gef.editpolicies.ComponentEditPolicy
 * 
 * @author sfline
 */
public class ShapeComponentTreeEditPolicy extends ComponentEditPolicy {

	/**
	 * request 에 대한 삭제 command 를 생성한다.
	 * @see org.eclipse.gef.editpolicies.ComponentEditPolicy#createDeleteCommand(org.eclipse.gef.requests.GroupRequest)
	 * 
	 * @Override
	 */
	protected Command createDeleteCommand(GroupRequest deleteRequest) {
		Object parent = getHost().getParent().getModel();
		Object child = getHost().getModel();
		// Include된 모델의 Connection은 삭제 불가하므로
		if(parent instanceof ItemElement) {
			if(((ItemElement)parent).isIncluded()) {
				return null;
			}
		}
		if (child instanceof ConnectionElement) {
			ReferElement refItem = (ReferElement)((ConnectionElement)child).getSource();
			ItemElement realItem = refItem.getRealModel();
			if(realItem.isIncluded()) {
				return null;
			}
			return new ConnectionDeleteCommand((ConnectionElement) child);
		}
		else if (parent instanceof ItemElement && child instanceof ItemElement) {
			ItemElement c = (ItemElement)child;
			if (c instanceof ReferElement) {
				c = ((ReferElement)c).getRealModel();
			}
			// KJH 20110527 s, withelement
			if (c instanceof WithElement) {
				return new WithElementDeleteCommand((ItemElement) parent, (ItemElement) child);
			}	// KJH 20110527 e, withelement
			if (c instanceof StateElement) {
				Command command = null;
				for (Iterator<ExpandTransElement> iter = ((StateElement)c).getBifurcates().iterator(); iter.hasNext(); ) {
					ExpandTransElement expandTrans = iter.next();
					Command cmd = new ShapeModelDeleteCommand((ItemElement)c, (ItemElement)expandTrans);
					command = (command != null) ? command.chain(cmd) : cmd;
				}
				if (command != null) {
					return command.chain(new ShapeModelDeleteCommand((ItemElement) parent, (ItemElement) child));
				}
			}
			
			return new ShapeModelDeleteCommand((ItemElement) parent, (ItemElement) child);
		}

		return super.createDeleteCommand(deleteRequest);
	}
}