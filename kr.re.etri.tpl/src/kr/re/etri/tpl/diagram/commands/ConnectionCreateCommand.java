package kr.re.etri.tpl.diagram.commands;

import kr.re.etri.tpl.taskmodel.ActionElement;
import kr.re.etri.tpl.taskmodel.BehaviorElement;
import kr.re.etri.tpl.taskmodel.ConnectionElement;
import kr.re.etri.tpl.taskmodel.ConnectorElement;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.LinkedElement;
import kr.re.etri.tpl.taskmodel.ReferElement;
import kr.re.etri.tpl.taskmodel.RelationShip;
import kr.re.etri.tpl.taskmodel.StateElement;
import kr.re.etri.tpl.taskmodel.TaskElement;
import kr.re.etri.tpl.taskmodel.WithElement;

import org.apache.log4j.Logger;
import org.eclipse.gef.commands.Command;

/**
 * GEF ��尣�� ������ �����ϴ� command �̴�.
 * �� ����� Undo/Redo�� �����ϴ�.
 * 
 * <p>
 * �� command �� GraphicalNodeEditPolicy�� �������ִ� ���鿡 ���ȴ�.
 * �� command �� ����ϱ� ���ؼ��� ������ ���� �ܰ谡 �ʿ��Ѵ�.
 * </p>
 * <ol>
 * <li>GraphicalNodeEditPolicy�� ���� Ŭ������ �����Ѵ�.</li>
 * <li>ConnectionCreateCommand�� ���� new instance�� �����ϱ� ���Ͽ� 
 * <tt>getConnectionCreateCommand(...)</tt> method�� Override �ϰ�
 * CreateConnectionRequest�� �����Ѵ�.</li>
 * <li>CreateConnectionRequest�κ��� ConnectionCreateCommand ��� ���Ͽ�
 * <tt>getConnectionCompleteCommand(...)</tt>  method�� Override�Ѵ�.
 * setTarget(...)�޼ҵ� ȣ��� connection�� Target endpoint�� �����ϰ�
 * command  instance�� return�Ѵ�.</li>
 * </ol>
 * @see kr.re.etri.tpl.diagram.editparts.behavior.BLinkedElementEditPart#createEditPolicies()
 * @see kr.re.etri.tpl.diagram.editparts.task.TLinkedElementEditPart#createEditPolicies()
 * @see org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy
 * @author sfline
 */
public class ConnectionCreateCommand extends Command {
	private static Logger logger = Logger.getLogger(ConnectionCreateCommand.class);
	/** connection instance. */
	protected ConnectionElement connection;
	/** connection�� Start endpoint */
	protected ReferElement source;
	/** connection�� Target endpoint*/
	protected ReferElement target;
	/** connection�� Start endpoint */
	protected ReferElement source2;
	/** connection�� Target endpoint*/
	protected ReferElement target2;
	
	/**
	 *	GEF Node���� connection�� �����ϱ� ���� ����� �����Ѵ�.
	 */
	public ConnectionCreateCommand() {
		setLabel("connection creation");
	}
	
	/**
	 * command �� ���� ���� ���θ� �����Ѵ�. 
	 * @see org.eclipse.gef.commands.Command#canExecute()
	 */
	public boolean canExecute() {
		return checkConnection(source, target);
	}
	
	protected boolean checkConnection(ReferElement sourceRef, ReferElement targetRef) {
		// ���� GEF ��尣�� ���� source -> source �� ������� ���ο� ���� 
//		if (source.equals(target)) {
//			return false;
//		}

		// �ߺ��� GEF ��װ��� ���� source -> target �� ������� ���ο� ���� 
//		if(source instanceof LinkedElement) {
//			LinkedElement elmt = (LinkedElement)source;
//			for (Iterator iter = elmt.getSourceConnections().iterator(); iter.hasNext();) {
//				Connection conn = (Connection) iter.next();
//				if (conn.getTarget().equals(target)) {
//					return false;
//				}
//			}
//		}

		ItemElement srcReal, tarReal = null;
		srcReal = sourceRef.getRealModel();
		if(targetRef != null) {
			tarReal = targetRef.getRealModel();
		}
		
		// Source ���� TaskElement �̰�
		if(srcReal instanceof BehaviorElement) {
			// KJH 20101130, behavior���� �ڽ��� call �� �� ����
			if (srcReal.equals(tarReal)) {
				return false;
			}
			// Relation �� TASK_CALL �� ��츸 Target �𵨷� TaskElement �� �� �� �ִ�.
			if( (tarReal instanceof BehaviorElement) &&
				(RelationShip.TASK_CALL == connection.getRelationship()) ) {
				return true;
			}
			// Relation �� INCLUDE �� ��츸  Target �𵨷� StateElement �� �� �� �ִ�.  
			else if( (tarReal instanceof StateElement) &&
				(RelationShip.INCLUDE == connection.getRelationship()) ) {
				return true;
			}
			// Relation �� ACTION_CALL �� ��츶 Target �𵨷� ActionElement �� �� �� �ִ�.
			else if( (tarReal instanceof ActionElement) &&
				(RelationShip.ACTION_CALL == connection.getRelationship()) ) {
					return true;
			}
			// KJH 20101130, TASK_CALL�� ��츸 behavior -> connector
			else if ((tarReal instanceof ConnectorElement) &&
					(RelationShip.TASK_CALL == connection.getRelationship())) {
				return true;
			}
		}
		// Source ���� StateElement �̰� 
		else if(srcReal instanceof StateElement) {
			// Relation �� TASK_CALL �� ��� ���� Target ���� TaskElement �� �� �� �ִ�.
			if( (tarReal instanceof BehaviorElement) &&
				(RelationShip.TASK_CALL == connection.getRelationship()) ) {
				return true;
			}
			// Relation �� TRANSITION �� ��� ���� Target ���� StateElement �� �� �� �ִ�.
			else if( (tarReal instanceof StateElement) &&
				(RelationShip.TRANSITION == connection.getRelationship()) ) {
					return true;
			}
			// Relation �� ACTION_CALL �� ��� ���� Target ���� ActonElement �� �� �� �ִ�.
			else if( (tarReal instanceof ActionElement) &&
				(RelationShip.ACTION_CALL == connection.getRelationship()) ) {
					return true;
			}
			// KJH 20101130, TRANSITION�� ��츸 state -> connector
			else if ((tarReal instanceof ConnectorElement) &&
					(RelationShip.TASK_CALL == connection.getRelationship())) {
				return true;
			}
		}
		// KJH 20101130 s, connector�� ���� ����
		else if (srcReal instanceof ConnectorElement) {
			if (srcReal.equals(tarReal)) {
				return false;
			}
			if ((tarReal instanceof BehaviorElement) &&
					(RelationShip.TASK_CALL == connection.getRelationship())) {
				return true;
			}
			else if ((tarReal instanceof ConnectorElement) &&
					(RelationShip.TASK_CALL == connection.getRelationship())) {
				return true;
			}
		}	// KJH 20101130 e, connector�� ���� ����
		// KJH 20110506 s, with�� ���� ����
		else if (srcReal instanceof WithElement) {
			if (srcReal.equals(tarReal)) {
				return false;
			}
			if ((tarReal instanceof BehaviorElement) &&
					(RelationShip.TASK_CALL == connection.getRelationship())) {
				return true;
			}
			if ((tarReal instanceof ConnectorElement) &&
					(RelationShip.TASK_CALL == connection.getRelationship())) {
				return true;
			}
			if ((tarReal instanceof WithElement) &&
					(RelationShip.TRANSITION == connection.getRelationship())) {
				return true;
			}
		}// KJH 20110506 e, with�� ���� ����
		// KJH 20110509 s, task�� ���� ����
		else if (srcReal instanceof TaskElement) {
			if (srcReal.equals(tarReal)) {
				return false;
			}
			if ((tarReal instanceof BehaviorElement) &&
					(RelationShip.TASK_CALL == connection.getRelationship())) {
				return true;
			}
			if ((tarReal instanceof ConnectorElement) &&
					(RelationShip.TASK_CALL == connection.getRelationship())) {
				return true;
			}
		}// KJH 20110509 e, task�� ���� ����
		return false;
	}

	/**
	 * command �� Undo ���� ���θ� �����Ѵ�. 
	 * @see org.eclipse.gef.commands.Command#canUndo()
	 */
	public boolean canUndo() {
		return super.canUndo();
	}

	/**
	 * command�� �����Ѵ�.
	 * command�� �����ϸ� Source �𵨰� Target �� ���� ���ο� connection�� �����ȴ�.
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	public void execute() {
		// create a new connection between source and target
		if( !(source instanceof LinkedElement) || !(target instanceof LinkedElement)) {
			return;
		}

		connection.setSource(source);
		connection.setSource2(source2 != null ? source2 : source);
		connection.setTarget(target);
		connection.setTarget2(target2 != null ? source2 : source);
		connection.setVisible(true);
		source.getSourceConnections().add(connection);
		target.getTargetConnections().add(connection);

		return;
	}
	
	/**
	 * Source �𵨰� Target �� ���� connection �� �ٽ� �����Ѵ�.
	 * @see org.eclipse.gef.commands.Command#redo()
	 */
	public void redo() {
		source.getSourceConnections().add(connection);
		target.getTargetConnections().add(connection);
	}

	/**
	 * Source �𵨰� Target �� ���� connection �� �����Ѵ�.
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	public void undo() {
		source.getSourceConnections().remove(connection);
		target.getTargetConnections().remove(connection);
	}

	/**
	 * connection �� target endpoint �� �����Ѵ�.
	 * @param target Target �𵨷� null�� �ƴ� LinkedElement instance �̾�� �Ѵ�.
	 * @throws IllegalArgumentException if target is null
	 */
	public void setTarget(ReferElement target) {
		if (target == null) {
			throw new IllegalArgumentException();
		}
		this.target = target;
	}
	
	/**
	 * connection �� Target �� �����Ѵ�.
	 * @return
	 */
	public LinkedElement getTarget() {
		return this.target;
	}
	
	/**
	 * connection �� source endpoint �� �����Ѵ�.
	 * @param source Source �𵨷� null�� �ƴ� LinkedElement instance �̾�� �Ѵ�.
	 */
	public void setSource(ReferElement source) {
		if (source == null) {
			throw new IllegalArgumentException();
		}
		this.source = source;
		logger.debug(source);
	}
	
	/**
	 * connection �� Source �� �����Ѵ�.
	 * @return
	 */
	public LinkedElement getSource() {
		return this.source;
	}

	/**
	 * connection �� �����Ѵ�.
	 * @param conn
	 */
	public void setConnection(ConnectionElement conn) {
		if (conn == null) {
			throw new IllegalArgumentException();
		}
		connection = conn;
	}
	
	/**
	 * connection �� �����Ѵ�.
	 * @return
	 */
	public ConnectionElement getConnection() {
		return connection;
	}
	
	// KJH 20110513 s, add
	public void setTarget2(ReferElement target2) {
		if (target2 == null) {
			throw new IllegalArgumentException();
		}
		this.target2 = target2;
	}
	
	public LinkedElement getTarget2() {
		return this.target2;
	}
	
	public void setSource2(ReferElement source2) {
		if (source2 == null) {
			throw new IllegalArgumentException();
		}
		this.source2 = source2;
	}
	
	public LinkedElement getSource2() {
		return this.source2;
	}
	// KJH 20110513 e, add

}
