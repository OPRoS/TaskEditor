
package kr.re.etri.tpl.diagram.editparts.behavior.policies;

import java.util.List;

import kr.re.etri.tpl.diagram.commands.ShapeModelDeleteCommand;
import kr.re.etri.tpl.diagram.commands.WithElementDeleteCommand;
import kr.re.etri.tpl.taskmodel.ExpandTransElement;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.ReferAttribute;
import kr.re.etri.tpl.taskmodel.ReferElement;
import kr.re.etri.tpl.taskmodel.StateElement;
import kr.re.etri.tpl.taskmodel.SubDiagram;
import kr.re.etri.tpl.taskmodel.WithElement;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

/**
 * 이 클래스는 Container 로부터 Shape instance 의 삭제 가능하도록 하는 Policy 클래스이다.
 * 
 * @author sfline
 */
public class BComponentEditPolicy extends ComponentEditPolicy {

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
			// KJH 20100827 s, external behavior doesn't remove child
			if (((ReferElement)parent).getAttribute() == ReferAttribute.EXTERNAL) {
				return null;
			}
			
			Command command = null;
			// KJH 20110518 s, state의 bifucates 삭제
			ReferElement refItem = (ReferElement)child;
			ItemElement realModel = refItem.getRealModel(); 
			if (realModel instanceof StateElement) {
				List<ItemElement> list = refItem.getItems();
				for (int i=0; i<list.size(); i++) {
					ReferElement item = (ReferElement)list.get(i);
					if (item.getRealModel() instanceof ExpandTransElement) {
						Command cmd = new ShapeModelDeleteCommand(refItem, (ReferElement) item); 
						command = (command != null) ? command.chain(cmd) : cmd;
					}
				}
				if (command != null)
					return command.chain(new ShapeModelDeleteCommand((ReferElement) parent, (ReferElement) child));
			}// KJH 20110518 e, state의 bifucates 삭제
			// KJH 20110527 s, with 삭제 시 라인 재정비하기 위한 command
			if (realModel instanceof WithElement) {
				return new WithElementDeleteCommand((ReferElement) parent, (ReferElement) child);
			}	// KJH 20110527 s, with 삭제 시 라인 재정비하기 위한 command
			
			return new ShapeModelDeleteCommand((ReferElement) parent, (ReferElement) child);
		}

		return super.createDeleteCommand(deleteRequest);
	}

}