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
 * GEF 노드간의 연결을 생성하는 command 이다.
 * 이 명령은 Undo/Redo가 가능하다.
 * 
 * <p>
 * 이 command 는 GraphicalNodeEditPolicy를 가지고있는 노드들에 사용된다.
 * 이 command 를 사용하기 위해서는 다음과 같은 단계가 필요한다.
 * </p>
 * <ol>
 * <li>GraphicalNodeEditPolicy의 서브 클래스를 생성한다.</li>
 * <li>ConnectionCreateCommand에 대한 new instance를 생성하기 위하여 
 * <tt>getConnectionCreateCommand(...)</tt> method를 Override 하고
 * CreateConnectionRequest에 설정한다.</li>
 * <li>CreateConnectionRequest로부터 ConnectionCreateCommand 얻기 위하여
 * <tt>getConnectionCompleteCommand(...)</tt>  method를 Override한다.
 * setTarget(...)메소드 호출로 connection의 Target endpoint를 설정하고
 * command  instance를 return한다.</li>
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
	/** connection의 Start endpoint */
	protected ReferElement source;
	/** connection의 Target endpoint*/
	protected ReferElement target;
	/** connection의 Start endpoint */
	protected ReferElement source2;
	/** connection의 Target endpoint*/
	protected ReferElement target2;
	
	/**
	 *	GEF Node간의 connection을 생성하기 위한 명령을 생성한다.
	 */
	public ConnectionCreateCommand() {
		setLabel("connection creation");
	}
	
	/**
	 * command 의 실행 가능 여부를 제공한다. 
	 * @see org.eclipse.gef.commands.Command#canExecute()
	 */
	public boolean canExecute() {
		return checkConnection(source, target);
	}
	
	protected boolean checkConnection(ReferElement sourceRef, ReferElement targetRef) {
		// 같은 GEF 노드간의 연결 source -> source 을 허용할지 여부에 따라 
//		if (source.equals(target)) {
//			return false;
//		}

		// 중복된 GEF 노그간의 연결 source -> target 을 허용할지 여부에 따라 
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
		
		// Source 모델이 TaskElement 이고
		if(srcReal instanceof BehaviorElement) {
			// KJH 20101130, behavior에서 자신을 call 할 수 없음
			if (srcReal.equals(tarReal)) {
				return false;
			}
			// Relation 이 TASK_CALL 인 경우만 Target 모델로 TaskElement 가 될 수 있다.
			if( (tarReal instanceof BehaviorElement) &&
				(RelationShip.TASK_CALL == connection.getRelationship()) ) {
				return true;
			}
			// Relation 이 INCLUDE 인 경우만  Target 모델로 StateElement 가 될 수 있다.  
			else if( (tarReal instanceof StateElement) &&
				(RelationShip.INCLUDE == connection.getRelationship()) ) {
				return true;
			}
			// Relation 이 ACTION_CALL 인 경우마 Target 모델로 ActionElement 가 될 수 있다.
			else if( (tarReal instanceof ActionElement) &&
				(RelationShip.ACTION_CALL == connection.getRelationship()) ) {
					return true;
			}
			// KJH 20101130, TASK_CALL인 경우만 behavior -> connector
			else if ((tarReal instanceof ConnectorElement) &&
					(RelationShip.TASK_CALL == connection.getRelationship())) {
				return true;
			}
		}
		// Source 모델이 StateElement 이고 
		else if(srcReal instanceof StateElement) {
			// Relation 이 TASK_CALL 인 경우 만이 Target 모델이 TaskElement 가 될 수 있다.
			if( (tarReal instanceof BehaviorElement) &&
				(RelationShip.TASK_CALL == connection.getRelationship()) ) {
				return true;
			}
			// Relation 이 TRANSITION 인 경우 만이 Target 모델이 StateElement 가 될 수 있다.
			else if( (tarReal instanceof StateElement) &&
				(RelationShip.TRANSITION == connection.getRelationship()) ) {
					return true;
			}
			// Relation 이 ACTION_CALL 인 경우 만이 Target 모델이 ActonElement 가 될 수 있다.
			else if( (tarReal instanceof ActionElement) &&
				(RelationShip.ACTION_CALL == connection.getRelationship()) ) {
					return true;
			}
			// KJH 20101130, TRANSITION인 경우만 state -> connector
			else if ((tarReal instanceof ConnectorElement) &&
					(RelationShip.TASK_CALL == connection.getRelationship())) {
				return true;
			}
		}
		// KJH 20101130 s, connector에 대한 설정
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
		}	// KJH 20101130 e, connector에 대한 설정
		// KJH 20110506 s, with에 대한 설정
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
		}// KJH 20110506 e, with에 대한 설정
		// KJH 20110509 s, task에 대한 설정
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
		}// KJH 20110509 e, task에 대한 설정
		return false;
	}

	/**
	 * command 의 Undo 가능 여부를 제공한다. 
	 * @see org.eclipse.gef.commands.Command#canUndo()
	 */
	public boolean canUndo() {
		return super.canUndo();
	}

	/**
	 * command를 실행한다.
	 * command를 실행하면 Source 모델과 Target 모델 간의 새로운 connection이 설정된다.
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
	 * Source 모델과 Target 모델 간의 connection 을 다시 설정한다.
	 * @see org.eclipse.gef.commands.Command#redo()
	 */
	public void redo() {
		source.getSourceConnections().add(connection);
		target.getTargetConnections().add(connection);
	}

	/**
	 * Source 모델과 Target 모델 간의 connection 을 삭제한다.
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	public void undo() {
		source.getSourceConnections().remove(connection);
		target.getTargetConnections().remove(connection);
	}

	/**
	 * connection 의 target endpoint 를 설정한다.
	 * @param target Target 모델로 null이 아닌 LinkedElement instance 이어야 한다.
	 * @throws IllegalArgumentException if target is null
	 */
	public void setTarget(ReferElement target) {
		if (target == null) {
			throw new IllegalArgumentException();
		}
		this.target = target;
	}
	
	/**
	 * connection 의 Target 을 제공한다.
	 * @return
	 */
	public LinkedElement getTarget() {
		return this.target;
	}
	
	/**
	 * connection 의 source endpoint 를 설정한다.
	 * @param source Source 모델로 null이 아닌 LinkedElement instance 이어야 한다.
	 */
	public void setSource(ReferElement source) {
		if (source == null) {
			throw new IllegalArgumentException();
		}
		this.source = source;
		logger.debug(source);
	}
	
	/**
	 * connection 의 Source 를 제공한다.
	 * @return
	 */
	public LinkedElement getSource() {
		return this.source;
	}

	/**
	 * connection 을 설정한다.
	 * @param conn
	 */
	public void setConnection(ConnectionElement conn) {
		if (conn == null) {
			throw new IllegalArgumentException();
		}
		connection = conn;
	}
	
	/**
	 * connection 을 제공한다.
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
