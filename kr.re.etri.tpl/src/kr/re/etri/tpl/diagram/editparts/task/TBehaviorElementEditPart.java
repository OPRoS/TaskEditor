
package kr.re.etri.tpl.diagram.editparts.task;

import java.util.List;

import kr.re.etri.tpl.diagram.DiagramConfiguration;
import kr.re.etri.tpl.diagram.ShortestConnectionRouter;
import kr.re.etri.tpl.diagram.celledit.LabelCellEditorLocator;
import kr.re.etri.tpl.diagram.celledit.LabelDirectEditManager;
import kr.re.etri.tpl.diagram.commands.ConnectionCreateCommand;
import kr.re.etri.tpl.diagram.commands.ConnectionReconnectCommand;
import kr.re.etri.tpl.diagram.editparts.task.policies.TBehaviorElementComponentEditPolicy;
import kr.re.etri.tpl.diagram.editparts.task.policies.TBehaviorElementXYLayoutEditPolicy;
import kr.re.etri.tpl.diagram.editparts.task.policies.TLabelDirectEditPolicy;
import kr.re.etri.tpl.diagram.figures.BehaviorElementFigure;
import kr.re.etri.tpl.diagram.properties.sources.BehaviorElementPropertySource;
import kr.re.etri.tpl.diagram.util.ItemState;
import kr.re.etri.tpl.taskmodel.BehaviorElement;
import kr.re.etri.tpl.taskmodel.ConnectionElement;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.LineEndPoint;
import kr.re.etri.tpl.taskmodel.ReferElement;
import kr.re.etri.tpl.taskmodel.TaskModelPackage;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gef.requests.DirectEditRequest;
import org.eclipse.gef.requests.ReconnectRequest;
import org.eclipse.gef.tools.DirectEditManager;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.IPropertySourceProvider;

/**
 * TaskElement �𵨰� Figure �� control �ϴ� EditPart Ŭ�����̴�.
 * 
 * @see TItemElementEditPart
 * 
 * @author sfline
 */
public class TBehaviorElementEditPart extends TLinkedElementEditPart {

	/** �̸� Label �� text �� ������ �� �ֵ��� CellEditor ���� */
	protected DirectEditManager manager;
	/** Figure �� corner */
	protected Dimension corner;
	/** Figure �� Connection ��θ� ����� Router */
	private ShortestConnectionRouter connectionRetuter;
	
	/**
	 * TaskElement ���� ���� ��(ReferElement) ���� EditPart �� �����Ѵ�.
	 * @param router ��� ��� Router
	 */
	public TBehaviorElementEditPart(ShortestConnectionRouter router) {
		super();
		
		corner = new Dimension(12, 12);
		connectionRetuter = router;
	}

	/**
	 * Object Ÿ���� ���� EditPart �� �� Ÿ������ ĳ�����Ѵ�.
	 * @return ActionElement ���� ���� ��(ReferElement)
	 * 
	 * @Override
	 */
	public ReferElement getCastedModel() {
		return (ReferElement)super.getCastedModel();
	}

	/**
	 * ���� ��(ReferElement)�� ���� ���� ��ȯ�Ѵ�.
	 * @return ActionElement ��
	 */
	public BehaviorElement getRealModel() {
		ReferElement refItem = getCastedModel();
		return (BehaviorElement)refItem.getRealModel();
	}
	
	/**
	 * Figure �� �ּ� ũ�⸦ ��ȯ�Ѵ�.
	 * 
	 * @return Figure �� �ּ� ũ��
	 */
	public Dimension getMinimumSize() {
		return this.getFigure().getMinimumSize();
	}
	
	/**
	 * child Figure �� �׷��� Container Figure �� ��ȯ�Ѵ�.
	 * @param child Figure �� �׷��� Container Figure
	 */
	public IFigure getContentPane() {
		BehaviorElementFigure figure = (BehaviorElementFigure)getFigure();
		return figure.getContentPane();
	}
	
	/**
	 * EditPart �� ���� Policy �� �����Ѵ�.
	 * @see org.eclipse.gef.editparts.AbstractEditPart#createEditPolicies()
	 * 
	 * @Override
	 */
	protected void createEditPolicies() {
		// allow removal of the associated model element
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new TBehaviorElementComponentEditPolicy());
		installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE, new TLabelDirectEditPolicy());
		// handles constraint changes (e.g. moving and/or resizing) of model elements
		// and creation of new model elements
		installEditPolicy(EditPolicy.LAYOUT_ROLE,  new TBehaviorElementXYLayoutEditPolicy(this));
		// allow the creation of connections and 
		// and the reconnection of connections between ShapeModel instances
		installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE, new GraphicalNodeEditPolicy() {
			/* (non-Javadoc)
			 * @see org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy#getConnectionCompleteCommand(org.eclipse.gef.requests.CreateConnectionRequest)
			 */
			protected Command getConnectionCompleteCommand(CreateConnectionRequest request) {
				ConnectionCreateCommand cmd;
				cmd = (ConnectionCreateCommand) request.getStartCommand();
				cmd.setTarget((ReferElement) getHost().getModel());
				return cmd;
			}
			/* (non-Javadoc)
			 * @see org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy#getConnectionCreateCommand(org.eclipse.gef.requests.CreateConnectionRequest)
			 */
			protected Command getConnectionCreateCommand(CreateConnectionRequest request) {
				ReferElement source = (ReferElement) getHost().getModel();
				ConnectionCreateCommand cmd;
				cmd = (ConnectionCreateCommand)request.getStartCommand();
				if(cmd == null) {
					cmd = new ConnectionCreateCommand();
				}
				ConnectionElement conn = cmd.getConnection();
				if(conn == null) {
					conn = ((ConnectionElement) request.getNewObjectType());
				}
				conn.setSourceEndPoint(LineEndPoint.OPENED_CIRCLE);
				cmd.setConnection(conn);
				cmd.setSource(source);

				request.setStartCommand(cmd);
				return cmd;
			}
			/* (non-Javadoc)
			 * @see org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy#getReconnectSourceCommand(org.eclipse.gef.requests.ReconnectRequest)
			 */
			protected Command getReconnectSourceCommand(ReconnectRequest request) {
				ConnectionElement conn = (ConnectionElement) request.getConnectionEditPart().getModel();

				// Include�� ���� Connection�� ���� �Ұ��ϹǷ�
				ReferElement newSource = (ReferElement) getHost().getModel();
				ItemElement realItem = newSource.getRealModel();
				if(realItem.isIncluded()) {
					return null;
				}

				conn.setSourceEndPoint(LineEndPoint.OPENED_CIRCLE);
				ConnectionReconnectCommand cmd = new ConnectionReconnectCommand(conn);
				cmd.setNewSource(newSource);
				return cmd;
			}
			/* (non-Javadoc)
			 * @see org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy#getReconnectTargetCommand(org.eclipse.gef.requests.ReconnectRequest)
			 */
			protected Command getReconnectTargetCommand(ReconnectRequest request) {
				ConnectionElement conn = (ConnectionElement) request.getConnectionEditPart().getModel();

				// Include�� ���� Connection�� ���� �Ұ��ϹǷ�
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
		});
	}

	/**
	 * �𵨿� ���� Figure �� �����Ͽ� �����Ѵ�.
	 * @return �𵨿� ���� Figure
	 * 
	 * @Override
	 */
	protected IFigure createFigureForModel() {

		BehaviorElement task = this.getRealModel();
		ReferElement refItem = getCastedModel();

		BehaviorElementFigure figure;
		DiagramConfiguration dgmCfg = DiagramConfiguration.getInstance();

		figure = new BehaviorElementFigure(this);
//		figure = new ScrollPane();
//		figure.setBorder(new SchemeBorder(SchemeBorder.SCHEMES.ETCHED));
		
		connectionRetuter.addContainer(figure.getContentPane());

		FontData []fd = new FontData[]{new FontData("Candara", 10, SWT.BOLD)};
		Font font = new Font(null, fd);
		figure.setFont(font);

		figure.setForegroundColor(dgmCfg.getItemFGColor(TaskModelPackage.TASK_ELEMENT, DiagramConfiguration.VALID));
		figure.setBackgroundColor(dgmCfg.getItemBGColor(TaskModelPackage.TASK_ELEMENT, DiagramConfiguration.VALID));
//		((BehaviorElementFigure)figure).setCornerDimensions(corner);
		((BehaviorElementFigure)figure).setMinimumSize(new Dimension(100, 25));
		figure.setOpaque(true); // non-transparent figure
		((BehaviorElementFigure)figure).setLineWidth(2);

		String name = task.getName();
		if(name == null|| name.isEmpty()) {
			task.setName("New_Behavior");
		}

		((BehaviorElementFigure)figure).setName(task.getName());

		return figure;
	}
	
	/**
	 * ���� �ڽ� �𵨵��� ��ȯ�Ѵ�.
	 * @see org.eclipse.gef.editparts.AbstractEditPart#getModelChildren()
	 * 
	 * @Override
	 */
	protected List getModelChildren() {
		ReferElement refItem = getCastedModel();
		List list = refItem.getItems();
		
		return list;
//		return Collections.EMPTY_LIST;
	}

	/**
	 * Parameter �� �־��� Ŭ����(�Ǵ� �������̽�)�� ���� Adapter �� �����Ѵ�.
	 * @param key Adapter ��û Ÿ��
	 * @return Parameter �� �־��� Ŭ����(�Ǵ� �������̽�)�� ���� Adapter
	 */
	public Object getAdapter(Class key) {
		if (IPropertySource.class == key) {
			Object model = getRealModel();
			if(model instanceof BehaviorElement) {
				return new BehaviorElementPropertySource((BehaviorElement)model);
			}
		}
		else if (IPropertySourceProvider.class == key) {
			final Object model = getRealModel();
			if(model instanceof BehaviorElement) {
				return new IPropertySourceProvider() {
					public IPropertySource getPropertySource(Object object) {
						return new BehaviorElementPropertySource((BehaviorElement)model);		
					}
				};
			}
		}

		return super.getAdapter(key);
	}
	
	/**
	 * ���� �Ӽ� ���� �ٽ� ȭ�鿡 �ٽ� ǥ���Ѵ�.
	 * 
	 * @Override
	 */
	protected void refreshVisuals() {
		ReferElement refItem = getCastedModel();
		BehaviorElement task = getRealModel();
		
		BehaviorElementFigure figure = (BehaviorElementFigure)getFigure();
		
		int itemState = task.getItemState();
		if(task.isIncluded()) {
			itemState |= ItemState.ITEM_INCLUDED;
		}
		figure.setModelState(itemState);

		figure.updateFigure();

		// notify parent container of changed position & location
		// if this line is removed, the XYLayoutManager used by the parent container 
		// (the Figure of the ShapesDiagramEditPart), will not know the bounds of this figure
		// and will not draw it correctly.
		Rectangle bounds = new Rectangle(
				refItem.getX(),
				refItem.getY(),
				refItem.getWidth(),
				refItem.getHeight());
		((GraphicalEditPart) getParent()).setLayoutConstraint(this, figure, bounds);

//		if(figure instanceof RoundedRectangle) {
//			int cx = 8;
//			int cy = 8;
//			((RoundedRectangle)figure).setCornerDimensions(new Dimension(cx, cy));
//			((RoundedRectangle)figure).repaint();
//		}
	}
	
	/**
	 * ���� ���� Property �� ����Ǹ� ȣ��ȴ�.
	 * @param notification ���� ���� ����
	 * 
	 * @Override
	 */
	public void notifyChanged(Notification notification) {

		Object notifier = notification.getNotifier();
		if(notifier instanceof BehaviorElement) {
			taskElementNotifyChanged(notification);
			return;
		}

		int type = notification.getEventType();
		int featureId = notification.getFeatureID(TaskModelPackage.class);
		switch(type) {
		case Notification.SET:
			switch(featureId) {
			case TaskModelPackage.ITEM_ELEMENT__NAME:
				ReferElement refItem = getCastedModel();
				EList<ConnectionElement> connList = refItem.getTargetConnections();
				for(ConnectionElement conn : connList) {
					conn.setTarget(refItem);
				}
				break;
			default:
				super.notifyChanged(notification);
				break;
			}
			refreshVisuals();
			break;
		case Notification.ADD:
		case Notification.ADD_MANY:
		case Notification.REMOVE:
		case Notification.REMOVE_MANY:
			switch(featureId) {
			case TaskModelPackage.LINKED_ELEMENT__SOURCE_CONNECTIONS:
				refreshSourceConnections();
				break;
			case TaskModelPackage.LINKED_ELEMENT__TARGET_CONNECTIONS:
				refreshTargetConnections();
				break;
			case TaskModelPackage.REFER_ELEMENT__ITEMS:
				refreshChildren();
				break;
			default:
				super.notifyChanged(notification);
				break;
			}
			break;
		}
	}

	/**
	 * ���� Property �� ����Ǹ� ȣ��ȴ�.
	 * @param notification ���� ���� ����
	 * 
	 * @Override
	 */
	public void taskElementNotifyChanged(Notification notification) {
		int type = notification.getEventType();
		int featureId = notification.getFeatureID(TaskModelPackage.class);
		switch(type) {
		case Notification.SET:
			switch(featureId) {
			default:
				super.notifyChanged(notification);
				break;
			}
			break;
		case Notification.ADD:
		case Notification.ADD_MANY:
		case Notification.REMOVE:
		case Notification.REMOVE_MANY:
			switch(featureId) {
			case TaskModelPackage.TASK_ELEMENT__STATES:
				refreshChildren();
				break;
			default:
				super.notifyChanged(notification);
				break;
			}
			break;
		}
	}

	/**
	 * Property �� ����Ǹ� ȣ��ȴ�
	 * @param event Property ���� Event
	 * 
	 * @Override
	 */
	public void propertyChange(PropertyChangeEvent event) {
		super.propertyChange(event);
	}

	/**
	 * �־��� Request �� �����Ѵ�. �� method �� EditPart �� �Ϲ�����
	 * �޽����� ������ ���� ���ȴ�.
	 * 
	 * @see org.eclipse.gef.EditPart#performRequest(org.eclipse.gef.Request)
	 */
	public void performRequest(Request request) {
		if (request.getType() == RequestConstants.REQ_DIRECT_EDIT) {
		}
		else if (request.getType() == RequestConstants.REQ_OPEN) {
			BehaviorElement task = getRealModel();
			if(task.isIncluded()) {
				return;
			}
			if (request instanceof DirectEditRequest
				&& !directEditHitTest(((DirectEditRequest) request)
					.getLocation()
					.getCopy()))
				return;
			performDirectEdit();
		}
	}

	/**
	 * Request ��û�� �̸� Label �����ΰ��� Ȯ���Ѵ�.
	 * @param requestLoc Request �� ��ǥ
	 * @return Name Label ���� ���� ����
	 */
	private boolean directEditHitTest(Point requestLoc) {
		Label label = ((BehaviorElementFigure)getFigure()).getLabelFigure();
		label.translateToRelative(requestLoc);
		if (label.containsPoint(requestLoc))
			return true;
		return false;
	}

	/**
	 * Direct Edit �� �����Ѵ�.
	 */
	protected void performDirectEdit() {
		if (manager == null) {
			Label label = ((BehaviorElementFigure)getFigure()).getLabelFigure();
			manager =
				new LabelDirectEditManager(
					this,
					TextCellEditor.class,
					new LabelCellEditorLocator(label), label);
		}
		manager.show();
	}
}