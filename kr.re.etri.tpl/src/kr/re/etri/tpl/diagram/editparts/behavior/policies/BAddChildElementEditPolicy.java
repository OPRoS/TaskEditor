package kr.re.etri.tpl.diagram.editparts.behavior.policies;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.AbstractEditPolicy;
import org.eclipse.gef.requests.CreateConnectionRequest;

public class BAddChildElementEditPolicy extends AbstractEditPolicy {
	String ADD_CHILD_ROLE = "AddChildEditPolicy";

	@Override
	public Command getCommand(Request request) {
		if (REQ_CONNECTION_END.equals(request.getType())) {
			return createAddChildElementCommand(request);
		}
		return super.getCommand(request);
	}

	protected Command createAddChildElementCommand(Request request) {
		if (request instanceof CreateConnectionRequest) {
			CreateConnectionRequest req = (CreateConnectionRequest)request;
			EditPart srcEditPart = req.getSourceEditPart();
		}
		return null;
	}
}
