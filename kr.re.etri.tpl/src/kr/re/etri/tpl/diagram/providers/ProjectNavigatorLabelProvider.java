package kr.re.etri.tpl.diagram.providers;

import kr.re.etri.tpl.taskmodel.ActionElement;
import kr.re.etri.tpl.taskmodel.ConnectionElement;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.RelationShip;
import kr.re.etri.tpl.taskmodel.StateAction;
import kr.re.etri.tpl.taskmodel.StateElement;
import kr.re.etri.tpl.taskmodel.BehaviorElement;
import kr.re.etri.tpl.taskmodel.TaskElement;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.swt.graphics.Image;

public class ProjectNavigatorLabelProvider extends
	AdapterFactoryLabelProvider {

	public ProjectNavigatorLabelProvider() {
		super(ProjectNavigatorComposedAdapterFactory.getAdapterFactory());
	}

	public ProjectNavigatorLabelProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}
	public String getText(Object obj) {
		if(obj instanceof TaskElement) {
			return ((TaskElement)obj).getName();
		}
		else if(obj instanceof BehaviorElement) {
			return ((BehaviorElement)obj).getName();
		}
		else if(obj instanceof StateElement) {
			return ((StateElement)obj).getName();
		}
		else if(obj instanceof StateAction) {
			return ((StateAction)obj).getName();
		}
		else if(obj instanceof ActionElement) {
			return ((ActionElement)obj).getName();
		}
		else if(obj instanceof ConnectionElement) {
			ConnectionElement conn = (ConnectionElement)obj;
			if(conn.getRelationship() == RelationShip.TASK_CALL) {
				ItemElement itemElmt = ((ConnectionElement)obj).getTarget();
				return String.format("Call for task (%s)", itemElmt.getName());
			}
			else if(conn.getRelationship() == RelationShip.TRANSITION) {
				ItemElement itemElmt = ((ConnectionElement)obj).getTarget();
				return String.format("Transition (%s)", itemElmt.getName());
			}
			else if(conn.getRelationship() == RelationShip.ACTION_CALL) {
				ItemElement itemElmt = ((ConnectionElement)obj).getTarget();
				return String.format("Call for action (%s)", itemElmt.getName());
			}
		}

		return super.getText(obj);
	}
	public Image getImage(Object obj) {
		return super.getImage(obj);
	}
}
