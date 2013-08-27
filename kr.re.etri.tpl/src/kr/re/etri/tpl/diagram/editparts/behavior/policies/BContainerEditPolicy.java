package kr.re.etri.tpl.diagram.editparts.behavior.policies;

import kr.re.etri.tpl.taskmodel.ReferElement;

import org.apache.log4j.Logger;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ContainerEditPolicy;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gef.requests.GroupRequest;

public class BContainerEditPolicy extends ContainerEditPolicy {
	private static Logger logger = Logger.getLogger(BContainerEditPolicy.class);
	
	@Override
	protected Command getCreateCommand(CreateRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Command getAddCommand(GroupRequest request) {
		logger.debug(getHost());
		
		ReferElement refItem = (ReferElement)getHost().getModel();
		return super.getAddCommand(request);
	}

	@Override
	protected Command getCloneCommand(ChangeBoundsRequest request) {
		// TODO Auto-generated method stub
		return super.getCloneCommand(request);
	}

	@Override
	protected Command getOrphanChildrenCommand(GroupRequest request) {
		// TODO Auto-generated method stub
		return super.getOrphanChildrenCommand(request);
	}

}
