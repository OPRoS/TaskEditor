package kr.re.etri.tpl.diagram.commands;

import javax.swing.text.html.HTMLDocument.RunElement;

import kr.re.etri.tpl.diagram.ModelManager;
import kr.re.etri.tpl.taskmodel.ActionElement;
import kr.re.etri.tpl.taskmodel.BehaviorElement;
import kr.re.etri.tpl.taskmodel.ConnectionElement;
import kr.re.etri.tpl.taskmodel.ConnectorElement;
import kr.re.etri.tpl.taskmodel.ExpandTransElement;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.LineEndPoint;
import kr.re.etri.tpl.taskmodel.LineStyle;
import kr.re.etri.tpl.taskmodel.LinkedElement;
import kr.re.etri.tpl.taskmodel.ReferElement;
import kr.re.etri.tpl.taskmodel.RelationShip;
import kr.re.etri.tpl.taskmodel.StateElement;
import kr.re.etri.tpl.taskmodel.TaskElement;
import kr.re.etri.tpl.taskmodel.WithElement;

import org.apache.log4j.Logger;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.commands.Command;

public class ConnectionCreateCommand2 extends Command {
	private static Logger logger = Logger.getLogger(ConnectionCreateCommand2.class);
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
	/**	connection�� Order(name) */
	protected int order;	// KJH 20110531
	
	private ReferElement srcParent;
	private ReferElement tarParent;
	private IFigure srcFigure;
	private IFigure tarFigure;
	
	private boolean isChildAdded;
	private boolean isExecuted;

	public ConnectionCreateCommand2() {
		setLabel("connection creation222");
		this.order = -1;	// KJH 20110531
	}
	
	public ConnectionCreateCommand2(IFigure figure) {
		setLabel("connection creation222");
		this.srcFigure = figure;
		this.order = -1;	// KJH 20110531
	}

	@Override
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
		
		// Source ���� BehaviorElement �̰�
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
		// KJH 201100520 s, expand&tans�� ���� ����
		else if (srcReal instanceof ExpandTransElement) {
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
			if ((tarReal instanceof StateElement) &&
					(RelationShip.TRANSITION == connection.getRelationship())) {
				return true;
			}
		}// KJH 201100520 e, expand&tans�� ���� ����
		return false;
	}

	@Override
	public boolean canUndo() {
		return isExecuted;
	}

	@Override
	public void execute() {
		ItemElement realItem = source.getRealModel();
		if (source2 == null)	source2 = source;
		if (target2 == null)	target2 = target;
		
		if (realItem.isIncluded()) {
			return;
		}
		
		isExecuted = true;
		
		// create a new connection between source and target
		if( !(source instanceof LinkedElement) || !(target instanceof LinkedElement)) {
			return;
		}

		// KJH 20110531 s, connections order
		if (order > -1) {
			connection.setName(Integer.toString(order));
		}	// KJH 20110531 e, connections order

		connection.setSource(source);
		connection.setSource2(source2);
		connection.setTarget(target);
		connection.setTarget2(target2);
		connection.setVisible(true);
		source.getSourceConnections().add(connection);
		target.getTargetConnections().add(connection);
		
		connection.setSourceEndPoint(getSourceEndPoint(source));
		connection.setLineStyle(getLineStyle(source, target));
	}

	@Override
	public void redo() {
		if (srcParent != null) {
			ItemElement realParent = srcParent.getRealModel();
			ItemElement realItem = source.getRealModel();
			
			if (realParent instanceof ConnectorElement &&
					realItem instanceof WithElement) {
				if (isChildAdded) {
					((ConnectorElement)realParent).getWiths().add((WithElement)realItem);
					srcParent.getItems().add(source);
				}
			}
		}
		
		if (tarParent != null) {
			ItemElement realParent = tarParent.getRealModel();
			ItemElement realItem = target.getRealModel();
		}
		
		source.getSourceConnections().add(connection);
		target.getTargetConnections().add(connection);
	}

	@Override
	public void undo() {
		source.getSourceConnections().remove(connection);
		target.getTargetConnections().remove(connection);

		if (srcParent != null) {
			ItemElement realParent = srcParent.getRealModel();
			ItemElement realItem = source.getRealModel();

			if (realParent instanceof ConnectorElement &&
					realItem instanceof RunElement) {
				if (isChildAdded) {
					((ConnectorElement)realParent).getWiths().remove((RunElement)realItem);
					srcParent.getItems().remove(source);
				}
			}
		}
		
		if (tarParent != null) {
			ItemElement realParent = tarParent.getRealModel();
			ItemElement realItem = target.getRealModel();
		}
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

	// KJH 20110511 s,
	public IFigure getSourceFigure() {
		return srcFigure;
	}

	public void setSourceFigure(IFigure figure) {
		this.srcFigure = figure;
	}
	
	public IFigure getTargetFigure() {
		return tarFigure;
	}

	public void setTargetFigure(IFigure figure) {
		this.tarFigure = figure;
	}
	// KJH 20110511 e,
	
	/**
	 * connection�� order�� �����Ѵ�.
	 * @param order
	 */
	public void setOrder(int order) {
		this.order = order;	// KJH 20110531
	}

	/**
	 * connection�� order�� �����Ѵ�.
	 * @return
	 */
	public int getOrder() {
		return order;	// KJH 20110531
	}
	
	protected LineEndPoint getSourceEndPoint(ItemElement element) {
		if (element instanceof ReferElement) {
			element = ((ReferElement)element).getRealModel();
		}
		
		if (element instanceof BehaviorElement) {
			return LineEndPoint.OPENED_CIRCLE;
		}
		if (element instanceof ConnectorElement) {
			return LineEndPoint.OPENED_CIRCLE;
		}
		if (element instanceof TaskElement) {
			return LineEndPoint.OPENED_CIRCLE;
		}
		return LineEndPoint.NONE;
	}
	
	private LineStyle getLineStyle(ItemElement source, ItemElement target) {
		if (source instanceof ReferElement) {
			source = ((ReferElement)source).getRealModel();
		}
		if (target instanceof ReferElement) {
			target = ((ReferElement)target).getRealModel();
		}
		
		if (source instanceof RunElement && target instanceof RunElement) {
			return LineStyle.LINE_DASH;
		}
		
		return LineStyle.LINE_SOLID;
	}

	private ReferElement addChildModel(ReferElement refItem, ItemElement child, Rectangle constraint) {
		boolean add = false;
		
		ItemElement parent = refItem.getRealModel();
		ReferElement childRef = ModelManager.getFactory().createReferElement();
		childRef.setWidth(constraint.width);
		childRef.setWidth2(constraint.width);
		childRef.setHeight(constraint.height);
		childRef.setHeight2(constraint.height);
		childRef.setX(constraint.x);
		childRef.setX2(constraint.x);
		childRef.setY(constraint.y);
		childRef.setY2(constraint.y);
		
		if (parent instanceof StateElement) {
			StateElement state = (StateElement)parent;
			if (child instanceof ExpandTransElement) {
				state.getBifurcates().add((ExpandTransElement)child);
				add = true;
			}
		}
		if (parent instanceof ConnectorElement) {
			ConnectorElement connector = (ConnectorElement)parent;
			if (child instanceof RunElement) {
				connector.getWiths().add((WithElement)child);
				add = true;
			}
		}
		
		if (add) {
			childRef.setRealModel(child);
			childRef.setParent(refItem);
			refItem.getItems().add(childRef);
			child.setParent(parent);
			return childRef;
		}
		
		return null;
	}
}
