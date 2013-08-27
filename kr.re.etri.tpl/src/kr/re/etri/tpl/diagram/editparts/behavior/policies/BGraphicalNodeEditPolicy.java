package kr.re.etri.tpl.diagram.editparts.behavior.policies;

import java.util.Iterator;
import java.util.List;

import kr.re.etri.tpl.diagram.ModelManager;
import kr.re.etri.tpl.diagram.commands.ConnectionCreateCommand;
import kr.re.etri.tpl.diagram.commands.ConnectionReconnectCommand;
import kr.re.etri.tpl.diagram.commands.ExpandTransElementCreateCommand;
import kr.re.etri.tpl.diagram.commands.WithElementCreateCommand;
import kr.re.etri.tpl.diagram.figures.ConnectorElementFigure;
import kr.re.etri.tpl.taskmodel.ConnectionElement;
import kr.re.etri.tpl.taskmodel.ConnectorElement;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.LineEndPoint;
import kr.re.etri.tpl.taskmodel.LineStyle;
import kr.re.etri.tpl.taskmodel.ReferElement;
import kr.re.etri.tpl.taskmodel.RelationShip;
import kr.re.etri.tpl.taskmodel.StateElement;

import org.apache.log4j.Logger;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gef.requests.CreationFactory;
import org.eclipse.gef.requests.ReconnectRequest;

public class BGraphicalNodeEditPolicy extends GraphicalNodeEditPolicy {
	private static Logger logger = Logger.getLogger(BBehaviorElementXYLayoutEditPolicy.class);
	
	public BGraphicalNodeEditPolicy() {
		super();
	}
	
	@Override
	protected Command getConnectionCompleteCommand(CreateConnectionRequest request) {
		logger.debug(getHost());
		ReferElement target = (ReferElement) getHost().getModel();
		
		ConnectionCreateCommand cmd = null;
		Command command = request.getStartCommand();
		if (command instanceof CompoundCommand) {
			Iterator iter = ((CompoundCommand)command).getCommands().iterator();
			while (iter.hasNext()) {
				Object obj = iter.next();
				if (obj instanceof ConnectionCreateCommand) {
					cmd = (ConnectionCreateCommand)obj;
					break;
				}
			}
		}
		else if (command instanceof ConnectionCreateCommand) {
			cmd = (ConnectionCreateCommand)command;
		}
		
		if (cmd == null) {
			return null;
		}
		
		ReferElement source = (ReferElement)cmd.getSource();
		ItemElement realItem = source.getRealModel();
		
		// parent와 child가 연결 불가능 하도록 하는 부분
		for(ItemElement parent = source; parent != null; parent = parent.getParent()) {
			if(target == parent) {
				return null;
			}
		}
		for(ItemElement parent = target; parent != null; parent = parent.getParent()) {
			if(source == parent) {
				return null;
			}
		}
		
		if (realItem instanceof StateElement) {
			if (source.getParent() != target.getParent()) {
				return null;
			}
		}
		
		cmd.setTarget(target);

		command = (command != null) ? command : cmd;
		return command;
	}

	@Override
	protected Command getConnectionCreateCommand(CreateConnectionRequest request) {
		logger.debug(getHost());
		ReferElement source = (ReferElement) getHost().getModel();
		ItemElement realModel = source.getRealModel();
		
		ConnectionCreateCommand cmd = null;
		Command command = request.getStartCommand();
		System.out.println(command);
		if (command instanceof CompoundCommand) {
			Iterator iter = ((CompoundCommand)command).getCommands().iterator();
			while (iter.hasNext()) {
				Object obj = iter.next();
				if (obj instanceof ConnectionCreateCommand) {
					cmd = (ConnectionCreateCommand)obj;
					break;
				}
			}
		}
		else if (command instanceof ConnectionCreateCommand) {
			cmd = (ConnectionCreateCommand)command;
		}
		
		if (cmd == null) {
			cmd = new ConnectionCreateCommand();
			if (command != null) {
				command = command.chain(cmd);
			}
		}
		
		ConnectionElement conn = cmd.getConnection();
		if(conn == null) {
			conn = ((ConnectionElement) request.getNewObjectType());
		}
		conn.setSourceEndPoint(getSourceEndPoint());	// KJH 20110513
		cmd.setConnection(conn);
		cmd.setSource(source);

		// KJH 20110513 s, connector
		if (realModel instanceof ConnectorElement) {
			List<ItemElement> children = source.getItems();
			
			ReferElement childRef = null;
			int priority = -1;
			
			if (children != null && children.size() > 0) {
//				for (ItemElement child : children) {
//					if (child instanceof ReferElement) {
//						ItemElement childReal = ((ReferElement)child).getRealModel();
//						if (childReal instanceof WithElement) {
//							int prio = ((WithElement)childReal).getPriority();
//							
//							if (priority < prio) {
//								childRef = (ReferElement)child;
//								priority = prio;
//							}
//						}
//					}
//				}
				childRef = (ReferElement)children.get(children.size()-1);
			}
			
			boolean isConnection = false;
			if (childRef != null) {
				List<ConnectionElement> conns= source.getSourceConnections();
				for (ConnectionElement c : conns) {
					if (childRef == c.getSource2()) {
						isConnection = true;
						break;
					}
				}
				isConnection |= childRef.getSourceConnections().size() > 0;
			}
			
			if (childRef == null || isConnection) {
				
				CreateRequest req = new CreateRequest();
				req.setFactory(new CreationFactory() {
					@Override
					public Object getObjectType() {	return null;}

					@Override
					public Object getNewObject() {
						ReferElement refItem = ModelManager.getFactory().createReferElement();
						refItem.setRealModel(ModelManager.getFactory().createWithElement());
						return refItem;
					}
				});

				Rectangle constraint = ((ConnectorElementFigure)getHostFigure()).getChildRectangle();
				constraint.translate(getHostFigure().getBounds().getLocation());
				constraint.x += constraint.width;
				command = new WithElementCreateCommand(source, req, constraint).chain(cmd);

				childRef = (ReferElement)req.getNewObject();
			}
			
			if (!source.isCollapsed()) {
				cmd.setSource(childRef);
			} else {
				cmd.setSource(source);
				cmd.setSource2(childRef);
			}
			conn.setSourceEndPoint(LineEndPoint.NONE);
		}// KJH 20110513 e, connector
		// KJH 20110513 s, state
		else if (realModel instanceof StateElement) {
			if (RelationShip.TASK_CALL == conn.getRelationship()) {
				CreateRequest req = new CreateRequest();
				req.setFactory(new CreationFactory() {
					@Override
					public Object getObjectType() {	return null;}

					@Override
					public Object getNewObject() {
						ReferElement refItem = ModelManager.getFactory().createReferElement();
						refItem.setRealModel(ModelManager.getFactory().createExpandTransElement());
						return refItem;
					}
				});

				Rectangle constraint = Rectangle.SINGLETON;
				command = new ExpandTransElementCreateCommand(source, req, constraint).chain(cmd);
			}
		}// KJH 20110513 e, state
		else {
			command = null;
		}

		command = (command != null) ? command : cmd;
		request.setStartCommand(command);
		return command;
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

		conn.setSourceEndPoint(getSourceEndPoint());
		ConnectionReconnectCommand cmd = new ConnectionReconnectCommand(conn);
		cmd.setNewSource(newSource);

		Command command = null;
		// KJH 20110513 s,

		
		return cmd;
	}

	@Override
	protected Command getReconnectTargetCommand(ReconnectRequest request) {
		return null;
	}

	/**
	 * @author KJH 20110513
	 * @return
	 */
	protected LineEndPoint getSourceEndPoint() {
		return LineEndPoint.NONE;
	}
	
	/**
	 * @author KJH 20110513
	 * @param source
	 * @return
	 */
	protected LineStyle getLineStyle(ItemElement source) {
		return LineStyle.LINE_SOLID;
	}
	
}
