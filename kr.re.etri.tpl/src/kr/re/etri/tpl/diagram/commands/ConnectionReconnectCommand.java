package kr.re.etri.tpl.diagram.commands;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import kr.re.etri.tpl.taskmodel.ConnectionElement;
import kr.re.etri.tpl.taskmodel.ExpandTransElement;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.LinkedElement;
import kr.re.etri.tpl.taskmodel.ReferElement;
import kr.re.etri.tpl.taskmodel.StateElement;

/**
 * start point 또는 end point에 대하여 다시 연결하는 command 클래스이다.
 * 이 명령은 Undo/Redo가 가능하다.
 * 
 * <p>
 * 이 command 는 GraphicalNodeEditPolicy를 가지고있는 노드들에 사용된다.
 * </p>
 * @see kr.re.etri.tpl.diagram.editparts.behavior.BLinkedElementEditPart#createEditPolicies()
 * @see kr.re.etri.tpl.diagram.editparts.task.TLinkedElementEditPart#createEditPolicies()
 * @see org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy
 * @see #setNewSource(LinkedElement)
 * @see #setNewTarget(LinkedElement)
 * @author sfline
 */
public class ConnectionReconnectCommand extends ConnectionCreateCommand2 {

	/** 재 연결할 connection instance. */
//	private ConnectionElement connection;
	/** 원래의 source endpoint. */
	private final ReferElement oldSource;
	/** 원래의 source endpoint 백업용 */
	private final ReferElement oldSource2;
	/** 원래의 target endpoint. */
	private final ReferElement oldTarget;
	/** 원래의 target endpoint 백업용 */
	private final ReferElement oldTarget2;
	/** 원래의 connection name */
	private String oldName;
	/** oldSource에서 order 변경되어야할 connections */
	private Map<ConnectionElement, String> connNames = new HashMap<ConnectionElement, String>();
	
	/**
	 * 다른 source endpoint 또는 target endpoint로 재연결할 command를 생성한다.
	 * @param conn 재연결할 connection instance
	 * @throws IllegalArgumentException conn 이 null 이면 발생한다.
	 */
	public ConnectionReconnectCommand(ConnectionElement conn) {
		if (conn == null) {
			throw new IllegalArgumentException();
		}
		this.connection = conn;
		this.oldSource = (ReferElement)conn.getSource();
		this.oldSource2 = (ReferElement)conn.getSource2();
		this.oldTarget = (ReferElement)conn.getTarget();
		this.oldTarget2 = (ReferElement)conn.getTarget2();
	}

	/**
	 * 이 command 가 실행 가능한지 여부를 반환한다.
	 * @see org.eclipse.gef.commands.Command#canExecute()
	 */
	public boolean canExecute() {
		if (source != null) {
			return checkSourceReconnection();
		}
		else if (target != null) {
			return checkTargetReconnection();
		}

		return false;
	}
	
	/**
	 * source endpoint 에 대하여 connection 가능 여부를 확인한다.
	 */
	private boolean checkSourceReconnection() {
		if( !(source instanceof LinkedElement)) {
			return false;
		}

		if(source != null) {
			if(checkConnection(source, oldTarget) != false) {
				return true;
			}
		}

		return false;
	}
	
	/**
	 * target endpoint 에 대하여 connection 가능 여부를 확인한다. 
	 */
	private boolean checkTargetReconnection() {
		if( !(target instanceof LinkedElement)) {
			return false;
		}

		if(target != null) {
			if(checkConnection(oldSource, target) != false) {
				return true;
			}
		}

		return false;
	}

	/**
	 * connection에 대한 새로운 source endpoint 를 설정한다.
	 * 새로운 source endpoint 와 새로운 target endpoint 는 동시에 변경 될 수 없다.
	 * @param connectionSource 새 source endpoint
	 * @throws IllegalArgumentException connectionSource 이 null 이면 발생한다.
	 */
	public void setNewSource(ReferElement connectionSource) {
		if (connectionSource == null) {
			throw new IllegalArgumentException();
		}

		setLabel("move connection startpoint");
		source = connectionSource;
		target = null;
	}

	/**
	 * connection에 대한 새로운 target endpoint 를 설정한다.
	 * 새로운 source endpoint 와 새로운 target endpoint 는 동시에 변경 될 수 없다.
	 * @param connectionTarget 새 target endpoint
	 * @throws IllegalArgumentException connectionTarget 이 null 이면 발생한다.
	 */
	public void setNewTarget(ReferElement connectionTarget) {
		if (connectionTarget == null) {
			throw new IllegalArgumentException();
		}
		setLabel("move connection endpoint");
		source = null;
		target = connectionTarget;
	}
	
	/**
	 * 이 명령을 실행한다.
	 * 재연결 시 원본 source endpoint 와 target endpoint 를 백업한다.
	 * 백업된 원본 endpoint들은 TaskElement 다이어그램이 폴딩될 수 있기 때문이다.
	 * 상위 클래스의 redo() 메소드에서 호출한다.
	 */
	public void execute() {
//		if (source != null && source.equals(oldSource)) {
//			return;
//		}
//		if (target != null && target.equals(oldTarget)) {
//			return;
//		}
		
		// KJH 20110531 s, renumber source connections
		if (source != null) {
			try {
				oldName = connection.getName();
				int oldOrder = Integer.parseInt(oldName);

				for (ConnectionElement conn : oldSource2.getSourceConnections()) {
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
				
				for (ItemElement item : oldSource2.getItems()) {
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
								
								if (sourcePort.equals(source)) {
									this.order = newOrder - 1; 
								}
							}
						} catch (NumberFormatException e) {}
					}
				}
				
				// KJH 20111005 s, expand&trans의 마지막 connection이 reconnect될 때 
				if (oldSource2.getRealModel() instanceof ExpandTransElement &&
						oldSource2.getSourceConnections().size() == 1) {
					ReferElement oldParent = (ReferElement)oldSource2.getParent();
					if (oldParent.getRealModel() instanceof StateElement) {
						for (ConnectionElement conn : oldParent.getSourceConnections()) {
							try {
								int newOrder = Integer.parseInt(conn.getName());
								
								if (newOrder > 1 && newOrder <= this.order) {
									connNames.put(conn, Integer.toString(newOrder - 1));
								}
							} catch (NumberFormatException e) {}
						}
					}
				}
				// KJH 20111005 e,
			} catch (NumberFormatException e) {}
		}
		// KJH 20110531 e, renumber source connections
		
		redo();
	}
	
	/**
	 * 이 명령에 대한 Redo 를 수행한다.
	 */
	public void redo() {
		if (source != null) {
			oldSource.getSourceConnections().remove(connection);
			target = oldTarget;
			target2 = oldTarget2;
		}
		else if (target != null) {
			oldTarget.getTargetConnections().remove(connection);
			source = oldSource;
			source2 = oldSource2;
		}
		else {
			throw new IllegalStateException("Should not happen");
		}
		
		// KJH 20110531 s, change connection name
		Iterator<ConnectionElement> connIter = connNames.keySet().iterator();
		while (connIter.hasNext()) {
			ConnectionElement conn = connIter.next();
			String newName = connNames.get(conn);
			connNames.put(conn, conn.getName());
			conn.setName(newName);
		}	// KJH 20110531 e, change connection name
		
		super.execute();
	}
	
	/**
	 * 이 명령에 대한 Undo 를 수행한다.
	 * 원본 source endpoint 또는 target endpoint 로 연결을 복원한다.
	 */
	public void undo() {
		if (source != null) {
			source.getSourceConnections().remove(connection);
			connection.setSource(oldSource);
			connection.setSource2(oldSource2);
			connection.setSourceEndPoint(getSourceEndPoint(oldSource));

			oldSource.getSourceConnections().add(connection);
		}
		else if (target != null) {
			target.getSourceConnections().remove(connection);
			connection.setTarget(oldTarget);
			connection.setTarget2(oldTarget2);
			oldTarget.getSourceConnections().add(connection);
		}
		
		// KJH 20110531 s, change connection name
		Iterator<ConnectionElement> connIter = connNames.keySet().iterator();
		while (connIter.hasNext()) {
			ConnectionElement conn = connIter.next();
			String newName = connNames.get(conn);
			connNames.put(conn, conn.getName());
			conn.setName(newName);
		}	// KJH 20110531 e, change connection name
		
		// KJH 20110601, undo connection name
		connection.setName(oldName);
	}
}
