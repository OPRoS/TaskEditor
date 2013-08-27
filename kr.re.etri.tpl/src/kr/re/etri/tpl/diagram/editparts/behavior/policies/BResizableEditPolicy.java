package kr.re.etri.tpl.diagram.editparts.behavior.policies;

import kr.re.etri.tpl.diagram.commands.ChangeFoldingRequest;

import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ResizableEditPolicy;
import org.eclipse.gef.requests.ChangeBoundsRequest;

public class BResizableEditPolicy extends ResizableEditPolicy implements
		EditPolicy {
	
	@Override
	protected Command getResizeCommand(ChangeBoundsRequest request) {
		if (request instanceof ChangeFoldingRequest) {
			ChangeFoldingRequest req = new ChangeFoldingRequest(
					((ChangeFoldingRequest)request).isCollapsed(),
					REQ_RESIZE_CHILDREN);

			req.setEditParts(getHost());
			req.setMoveDelta(request.getMoveDelta());
			req.setSizeDelta(request.getSizeDelta());
			req.setLocation(request.getLocation());
			req.setExtendedData(request.getExtendedData());
			req.setResizeDirection(request.getResizeDirection());
			if (getHost().getParent() != null) {
				return getHost().getParent().getCommand(req);
			}
		}
		
		return super.getResizeCommand(request);
	}

}
