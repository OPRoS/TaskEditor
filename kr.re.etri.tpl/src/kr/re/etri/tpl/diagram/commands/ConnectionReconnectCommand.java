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
 * start point �Ǵ� end point�� ���Ͽ� �ٽ� �����ϴ� command Ŭ�����̴�.
 * �� ����� Undo/Redo�� �����ϴ�.
 * 
 * <p>
 * �� command �� GraphicalNodeEditPolicy�� �������ִ� ���鿡 ���ȴ�.
 * </p>
 * @see kr.re.etri.tpl.diagram.editparts.behavior.BLinkedElementEditPart#createEditPolicies()
 * @see kr.re.etri.tpl.diagram.editparts.task.TLinkedElementEditPart#createEditPolicies()
 * @see org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy
 * @see #setNewSource(LinkedElement)
 * @see #setNewTarget(LinkedElement)
 * @author sfline
 */
public class ConnectionReconnectCommand extends ConnectionCreateCommand2 {

	/** �� ������ connection instance. */
//	private ConnectionElement connection;
	/** ������ source endpoint. */
	private final ReferElement oldSource;
	/** ������ source endpoint ����� */
	private final ReferElement oldSource2;
	/** ������ target endpoint. */
	private final ReferElement oldTarget;
	/** ������ target endpoint ����� */
	private final ReferElement oldTarget2;
	/** ������ connection name */
	private String oldName;
	/** oldSource���� order ����Ǿ���� connections */
	private Map<ConnectionElement, String> connNames = new HashMap<ConnectionElement, String>();
	
	/**
	 * �ٸ� source endpoint �Ǵ� target endpoint�� �翬���� command�� �����Ѵ�.
	 * @param conn �翬���� connection instance
	 * @throws IllegalArgumentException conn �� null �̸� �߻��Ѵ�.
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
	 * �� command �� ���� �������� ���θ� ��ȯ�Ѵ�.
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
	 * source endpoint �� ���Ͽ� connection ���� ���θ� Ȯ���Ѵ�.
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
	 * target endpoint �� ���Ͽ� connection ���� ���θ� Ȯ���Ѵ�. 
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
	 * connection�� ���� ���ο� source endpoint �� �����Ѵ�.
	 * ���ο� source endpoint �� ���ο� target endpoint �� ���ÿ� ���� �� �� ����.
	 * @param connectionSource �� source endpoint
	 * @throws IllegalArgumentException connectionSource �� null �̸� �߻��Ѵ�.
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
	 * connection�� ���� ���ο� target endpoint �� �����Ѵ�.
	 * ���ο� source endpoint �� ���ο� target endpoint �� ���ÿ� ���� �� �� ����.
	 * @param connectionTarget �� target endpoint
	 * @throws IllegalArgumentException connectionTarget �� null �̸� �߻��Ѵ�.
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
	 * �� ����� �����Ѵ�.
	 * �翬�� �� ���� source endpoint �� target endpoint �� ����Ѵ�.
	 * ����� ���� endpoint���� TaskElement ���̾�׷��� ������ �� �ֱ� �����̴�.
	 * ���� Ŭ������ redo() �޼ҵ忡�� ȣ���Ѵ�.
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
				
				// KJH 20111005 s, expand&trans�� ������ connection�� reconnect�� �� 
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
	 * �� ��ɿ� ���� Redo �� �����Ѵ�.
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
	 * �� ��ɿ� ���� Undo �� �����Ѵ�.
	 * ���� source endpoint �Ǵ� target endpoint �� ������ �����Ѵ�.
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
