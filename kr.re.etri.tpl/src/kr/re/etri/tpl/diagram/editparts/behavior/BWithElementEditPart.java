package kr.re.etri.tpl.diagram.editparts.behavior;

import java.util.ArrayList;
import java.util.List;

import javax.swing.text.html.HTMLDocument.RunElement;

import kr.re.etri.tpl.diagram.DiagramConfiguration;
import kr.re.etri.tpl.diagram.editparts.behavior.policies.BComponentEditPolicy;
import kr.re.etri.tpl.diagram.editparts.behavior.policies.BConnectorElementXYLayoutEditPolicy;
import kr.re.etri.tpl.diagram.editparts.behavior.policies.BGraphicalNodeEditPolicy2;
import kr.re.etri.tpl.diagram.figures.WithElementFigure;
import kr.re.etri.tpl.diagram.properties.sources.WithElementPropertySource;
import kr.re.etri.tpl.taskmodel.ConnectionElement;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.ReferElement;
import kr.re.etri.tpl.taskmodel.RelationShip;
import kr.re.etri.tpl.taskmodel.TaskModelPackage;
import kr.re.etri.tpl.taskmodel.WithElement;

import org.apache.log4j.Logger;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gef.requests.ReconnectRequest;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.IPropertySourceProvider;

public class BWithElementEditPart extends BLinkedElementEditPart {
	private static Logger logger = Logger.getLogger(BWithElementEditPart.class);
	
	public BWithElementEditPart() {
		super();
	}

	@Override
	protected void createEditPolicies() {
		// allow removal of the associated model element
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new BComponentEditPolicy());

//		installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE, new BLabelDirectEditPolicy());
		
		installEditPolicy(EditPolicy.LAYOUT_ROLE,  new BConnectorElementXYLayoutEditPolicy());
		
		// allow the creation of connections and
		// and the reconnection of connections between StateElement instances
		installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE, new BGraphicalNodeEditPolicy2() {
			@Override
			protected Command getConnectionCompleteCommand(
					CreateConnectionRequest request) {
				return super.getConnectionCompleteCommand(request);
			}

			@Override
			protected Command getConnectionCreateCommand(
					CreateConnectionRequest request) {
				ConnectionElement conn = (ConnectionElement) request.getNewObjectType();
				if (RelationShip.TRANSITION == conn.getRelationship()) {
					return null;
				}
				return super.getConnectionCreateCommand(request);
			}

			@Override
			protected Command getReconnectSourceCommand(ReconnectRequest request) {
				ConnectionElement conn = (ConnectionElement) request.getConnectionEditPart().getModel();
				if (RelationShip.TRANSITION == conn.getRelationship()) {
					return null;
				}
				return super.getReconnectSourceCommand(request);
			}

			@Override
			protected Command getReconnectTargetCommand(ReconnectRequest request) {
				ConnectionElement conn = (ConnectionElement) request.getConnectionEditPart().getModel();
				if (RelationShip.TRANSITION == conn.getRelationship()) {
					return null;
				}
				return super.getReconnectTargetCommand(request);
			}
			
		});
//		installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE, new GraphicalNodeEditPolicy() {
//
//			@Override
//			protected Command getConnectionCompleteCommand(
//					CreateConnectionRequest request) {
//				return null;
//			}
//
//			@Override
//			protected Command getConnectionCreateCommand(
//					CreateConnectionRequest request) {
//				ReferElement source = (ReferElement) getHost().getModel();
//				
//				ConnectionCreateCommand cmd;
//				cmd = (ConnectionCreateCommand)request.getStartCommand();
//				if(cmd == null) {
//					cmd = new ConnectionCreateCommand();
//				}
//				ConnectionElement conn = cmd.getConnection();
//				if(conn == null) {
//					conn = ((ConnectionElement) request.getNewObjectType());
//				}
//				
//				conn.setSourceEndPoint(LineEndPoint.NONE);
//				cmd.setConnection(conn);
//				cmd.setSource(source);
//
//				request.setStartCommand(cmd);
//				return cmd;
//			}
//
//			@Override
//			protected Command getReconnectSourceCommand(ReconnectRequest request) {
//				ConnectionElement conn = (ConnectionElement) request.getConnectionEditPart().getModel();
//				
//				// Include된 모델의 Connection은 편집 불가하므로
//				ReferElement newSource = (ReferElement) getHost().getModel();
//				ItemElement realItem = newSource.getRealModel();
//				if(realItem.isIncluded()) {
//					return null;
//				}
//				
//				ConnectionReconnectCommand cmd = new ConnectionReconnectCommand(conn);
//				cmd.setNewSource(newSource);
//				return cmd;
//			}
//
//			@Override
//			protected Command getReconnectTargetCommand(ReconnectRequest request) {
//				return null;
//			}
//			
//		});
	}

	@Override
	protected IFigure createFigureForModel() {
		
		WithElementFigure figure = new WithElementFigure();
		figure.setLineWidth(2);
		DiagramConfiguration dgmCfg = DiagramConfiguration.getInstance();
		figure.setForegroundColor(dgmCfg.getItemFGColor(TaskModelPackage.WITH_ELEMENT,
				DiagramConfiguration.VALID));
		figure.setBackgroundColor(dgmCfg.getItemLineColor(TaskModelPackage.WITH_ELEMENT,
				DiagramConfiguration.VALID));
		
		figure.setVisible(getCastedModel().isVisible());
		
		return figure;
	}

	@Override
	public Object getAdapter(Class key) {
		if (IPropertySource.class == key) {
			Object model = getRealModel();
			if(model instanceof RunElement) {
				return new WithElementPropertySource((WithElement)model);
			}
		}
		else if (IPropertySourceProvider.class == key) {
			final Object model = getRealModel();
			if(model instanceof WithElement) {
				return new IPropertySourceProvider() {
					public IPropertySource getPropertySource(Object object) {
						return new WithElementPropertySource((WithElement)model);
					}
				};
			}
		}
		return super.getAdapter(key);
	}

	@Override
	public ReferElement getCastedModel() {
		return (ReferElement)super.getCastedModel();
	}
	
	/**
	 * 참조 모델(ReferElement)의 실제 모델을 반환한다.
	 * @return WithElement 모델
	 */
	public WithElement getRealModel() {
		ReferElement refItem = getCastedModel();
		return (WithElement)refItem.getRealModel();
	}

	@Override
	protected List getModelChildren() {
		// TODO Auto-generated method stub
		return super.getModelChildren();
	}

	/**
	 * Figure 의 최소 크기를 반환한다.
	 * 
	 * @return Figure 의 최소 크기
	 */
	public Dimension getMinimumSize() {
		return this.getFigure().getMinimumSize();
	}

	@Override
	public void notifyChanged(Notification notification) {
		Object notifier = notification.getNotifier();

		if(notifier instanceof ConnectionElement) {
			connectionElemeentNotifyChanged(notification);
			return;
		}
		if (notifier instanceof ReferElement) {
			super.notifyChanged(notification);
		}
		
		int type = notification.getEventType();
		int featureId = notification.getFeatureID(TaskModelPackage.class);
		logger.debug(featureId);
		switch(type) {
		case Notification.SET:
			switch(featureId) {
			default:
				super.notifyChanged(notification);
				break;
			}
			break;
		default:
			super.notifyChanged(notification);
			break;
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		logger.debug(event.getProperty());
		super.propertyChange(event);
	}

	@Override
	protected void refreshVisuals() {
		IFigure figure = getFigure();
		ReferElement refItem = getCastedModel();
		
		Rectangle bounds = new Rectangle(
				refItem.getX(),
				refItem.getY(),
				refItem.getWidth(),
				refItem.getHeight());
		((GraphicalEditPart) getParent()).setLayoutConstraint(this, figure, bounds);

		figure.setVisible(getCastedModel().isVisible());
		figure.repaint();
	}
	
	// KJH 20110530 s, 우선순위 지정대상 connections 구하는 함수
	@Override
	public List<ItemElement> getOrderConnections() {
		List<ItemElement> list = new ArrayList<ItemElement>();
		
		List connList = getModelSourceConnections();
		for (int i=0; i<connList.size(); i++) {
			if (connList.get(i) instanceof ConnectionElement) {
				ConnectionElement conn = (ConnectionElement)connList.get(i);
				
				if (RelationShip.TASK_CALL == conn.getRelationship()) {
					list.add(conn);
				}
			}
		}
		
		sortListByOrder(list);
		
		return list;
	}
	// KJH 20110530 e, 우선순위 지정대상 connections 구하는 함수
	
	@Override
	public boolean isSelectable() {	// KJH 20110614
		return getCastedModel().isVisible() && !getCastedModel().isCollapsed();
	}
	
}
