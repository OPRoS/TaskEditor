package kr.re.etri.tpl.diagram.commands;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import kr.re.etri.tpl.taskmodel.ConnectionElement;
import kr.re.etri.tpl.taskmodel.ExpandTransElement;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.LinkedElement;
import kr.re.etri.tpl.taskmodel.ReferElement;
import kr.re.etri.tpl.taskmodel.RelationShip;
import kr.re.etri.tpl.taskmodel.TaskElement;
import kr.re.etri.tpl.taskmodel.WithElement;

import org.eclipse.gef.commands.Command;

/**
 * GEF Node간 연결을 삭제하는 command 클래스이다.
 * 이 command는 Undo/Redo 가 가능하다.
 * @author sfline
 */
public class ConnectionDeleteCommand extends Command {
	
	/** 연결을 끊을 Connection instance. */
	private final ConnectionElement connection;
	/** oldSource에서 order 변경되어야할 connections */
	private Map<ConnectionElement, String> connNames = new HashMap<ConnectionElement, String>();
	
	/** 
	 * GEF Node간 연결을 삭제하는 command 를 생성한다.
	 * @param conn 연결을 끊을 connection instance
	 * @throws IllegalArgumentException conn이 null이면 발생한다. 
	 */ 
	public ConnectionDeleteCommand(ConnectionElement conn) {
		if (conn == null) {
			throw new IllegalArgumentException();
		}
		setLabel("connection deletion");
		this.connection = conn;
	}
	
	@Override
	public boolean canExecute() {
		// KJH 20110527 s, with->with, cannot delete
		ReferElement source = (ReferElement)connection.getSource();
		ReferElement target = (ReferElement)connection.getTarget();
		if ((source.getRealModel() instanceof WithElement) &&
				(target.getRealModel() instanceof WithElement) &&
				RelationShip.TRANSITION == connection.getRelationship()) {
			return false;
		}	// KJH 20110527 e, with->with, cannot delete
		
		return super.canExecute();
	}

	/**
	 * command 를 실행한다.
	 */
	public void execute() {
		ReferElement source = (ReferElement)connection.getSource();
		ReferElement target = (ReferElement)connection.getTarget();
		// KJH 20110531 s, renumber source connections
		if (source != null) {
			try {
				int oldOrder = Integer.parseInt(connection.getName());

				for (ConnectionElement conn : source.getSourceConnections()) {
					if (conn == connection) {
						continue;
					}

					try {
						int newOrder = Integer.parseInt(conn.getName());

						if (oldOrder > 0 && newOrder > oldOrder) {
							connNames.put(conn, Integer.toString(newOrder - 1));
						}
					} catch (NumberFormatException e) {}
				}
				
				for (ItemElement item : source.getItems()) {
					if (!(item instanceof ReferElement)) {
						continue;
					}

					ReferElement sourcePort = (ReferElement)item;
					if (!(sourcePort.getRealModel() instanceof ExpandTransElement)) {
						continue;
					}
					
					for (ConnectionElement conn : sourcePort.getSourceConnections()) {
						try {
							int newOrder = Integer.parseInt(conn.getName());

							if (oldOrder > 0 && newOrder > oldOrder) {
								connNames.put(conn, Integer.toString(newOrder - 1));
							}
						} catch (NumberFormatException e) {}
					}
				}
				
				// KJH 20110601 s, expand&trans 지울때 parent(State)의 connection 처리
				if (source.getRealModel() instanceof ExpandTransElement) {
					// 마지막 connection이 지워지는 경우
					if ((source.getSourceConnections().size() == 1) &&
							source.getSourceConnections().get(0) == connection) {
						ReferElement parent = (ReferElement)source.getParent();
						for (ConnectionElement conn : parent.getSourceConnections()) {
							try {
								int newOrder = Integer.parseInt(conn.getName());

								if (oldOrder > 0 && newOrder > oldOrder) {
									connNames.put(conn, Integer.toString(newOrder - 1));
								}
							} catch (NumberFormatException e) {}
						}
					}
				}	// KJH 20110601 e, expand&trans 지울때 parent(State)의 connection 처리
			} catch (NumberFormatException e) {}
		}
		// KJH 20110531 e, renumber source connections
		
		redo();
	}
	
	/**
	 * command에 대하여 Redo를 수행한다.
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	public void redo() {
		LinkedElement source = connection.getSource();
		source.getSourceConnections().remove(connection);

		LinkedElement target = connection.getTarget();
		target.getTargetConnections().remove(connection);
		
		// KJH 20110531 s, change connection name
		Iterator<ConnectionElement> connIter = connNames.keySet().iterator();
		while (connIter.hasNext()) {
			ConnectionElement conn = connIter.next();
			String newName = connNames.get(conn);
			connNames.put(conn, conn.getName());
			conn.setName(newName);
		}	// KJH 20110531 e, change connection name
	}
	
	/**
	 * command 에 대하여 Undo를 수행한다.
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	public void undo() {
		LinkedElement source = connection.getSource();
		source.getSourceConnections().add(connection);

		LinkedElement target = connection.getTarget();
		target.getTargetConnections().add(connection);
		
		// KJH 20110531 s, change connection name
		Iterator<ConnectionElement> connIter = connNames.keySet().iterator();
		while (connIter.hasNext()) {
			ConnectionElement conn = connIter.next();
			String newName = connNames.get(conn);
			connNames.put(conn, conn.getName());
			conn.setName(newName);
		}	// KJH 20110531 e, change connection name
	}
}
