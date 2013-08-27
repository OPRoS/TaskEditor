package kr.re.etri.tpl.diagram.editparts.behavior.policies;

import java.util.List;

import kr.re.etri.tpl.diagram.commands.ConnectionCreateCommand2;
import kr.re.etri.tpl.diagram.commands.ConnectionReconnectCommand;
import kr.re.etri.tpl.diagram.editparts.behavior.BLinkedElementEditPart;
import kr.re.etri.tpl.taskmodel.ConnectionElement;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.ReferElement;

import org.apache.log4j.Logger;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gef.requests.ReconnectRequest;

public class BGraphicalNodeEditPolicy2 extends GraphicalNodeEditPolicy {
	private static Logger logger = Logger.getLogger(BGraphicalNodeEditPolicy2.class);
	
	@Override
	protected Command getConnectionCompleteCommand(
			CreateConnectionRequest request) {
		ReferElement target = (ReferElement) getHost().getModel();
		ConnectionCreateCommand2 cmd = (ConnectionCreateCommand2) request.getStartCommand();
		
		ReferElement source = (ReferElement)cmd.getSource();
		
		for(ItemElement parent = source.getParent(); parent != null; parent = parent.getParent()) {
			if(target == parent) {
				return null;
			}
		}
		for(ItemElement parent = target.getParent(); parent != null; parent = parent.getParent()) {
			if(source == parent) {
				return null;
			}
		}
		
		cmd.setTarget(target);
		cmd.setTargetFigure(getHostFigure());
		
		return cmd;
	}

	@Override
	protected Command getConnectionCreateCommand(CreateConnectionRequest request) {
		ReferElement source = (ReferElement) getHost().getModel();
		logger.debug(source);
		
		ConnectionCreateCommand2 cmd;
		cmd = (ConnectionCreateCommand2)request.getStartCommand();
		if(cmd == null) {
			cmd = new ConnectionCreateCommand2();
		}
		ConnectionElement conn = cmd.getConnection();
		if(conn == null) {
			conn = ((ConnectionElement) request.getNewObjectType());
		}
		
		cmd.setSourceFigure(getHostFigure());	// KJH 20110511
		cmd.setConnection(conn);
		cmd.setSource(source);
		
		// KJH 20110530 s, connection's order
		if (getHost() instanceof BLinkedElementEditPart) {
			BLinkedElementEditPart editPart = (BLinkedElementEditPart)getHost();
			
			cmd.setOrder(editPart.getOrderConnections().size() + 1);
		}	// KJH 20110530 s, connection's order
		
		request.setStartCommand(cmd);
		return cmd;
	}

	@Override
	protected Command getReconnectSourceCommand(ReconnectRequest request) {
		ConnectionElement conn = (ConnectionElement) request.getConnectionEditPart().getModel();

		// Include된 모델의 Connection은 편집 불가하므로
		ReferElement newSource = (ReferElement) getHost().getModel();
		ItemElement realItem = newSource.getRealModel();
		if(realItem.isIncluded()) {
			return null;
		}
		
		if (conn.getSource() == newSource) {
			return null;
		}

		ConnectionReconnectCommand cmd = new ConnectionReconnectCommand(conn);
		cmd.setNewSource(newSource);
		
		// KJH 20110531 s, connection order
		if (getHost() instanceof BLinkedElementEditPart) {
			BLinkedElementEditPart editPart = (BLinkedElementEditPart)getHost();
			
			// KJH 20111005 s, order
			int order = 1;
			for (ItemElement item : editPart.getOrderConnections()) {
				if (item.equals(conn)) {
					continue;
				}
				if (item instanceof ReferElement) {
					EList<ConnectionElement> conns = ((ReferElement)item).getSourceConnections();
					if (conns.size() == 1 && conns.contains(conn)) {
						continue;
					}
				}
				order++;
			}
			cmd.setOrder(order);
			// KJH 20111005 e, order
//			cmd.setOrder(editPart.getOrderConnections().size() + 1);
		}	// KJH 20110531 s, connection order
		
		return cmd;
	}

	@Override
	protected Command getReconnectTargetCommand(ReconnectRequest request) {
		ConnectionElement conn = (ConnectionElement) request.getConnectionEditPart().getModel();

		// Include된 모델의 Connection은 편집 불가하므로
		ReferElement refElmt = (ReferElement)conn.getSource();
		ItemElement realItem = refElmt.getRealModel();
		if(realItem.isIncluded()) {
			return null;
		}
		
		ReferElement newTarget = (ReferElement) getHost().getModel();
		ConnectionReconnectCommand cmd = new ConnectionReconnectCommand(conn);
		cmd.setNewTarget(newTarget);
		return cmd;
	}

	@Override
	protected ConnectionAnchor getSourceConnectionAnchor(
			CreateConnectionRequest request) {
		return super.getSourceConnectionAnchor(request);
	}

	@Override
	protected ConnectionAnchor getTargetConnectionAnchor(
			CreateConnectionRequest request) {
		return super.getTargetConnectionAnchor(request);
	}

}
