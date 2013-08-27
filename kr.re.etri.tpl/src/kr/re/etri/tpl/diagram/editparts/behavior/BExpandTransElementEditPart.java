package kr.re.etri.tpl.diagram.editparts.behavior;

import java.util.ArrayList;
import java.util.List;

import kr.re.etri.tpl.diagram.DiagramConfiguration;
import kr.re.etri.tpl.diagram.commands.ConnectionCreateCommand2;
import kr.re.etri.tpl.diagram.commands.ConnectionReconnectCommand;
import kr.re.etri.tpl.diagram.editparts.behavior.policies.BBehaviorElementXYLayoutEditPolicy;
import kr.re.etri.tpl.diagram.editparts.behavior.policies.BComponentEditPolicy;
import kr.re.etri.tpl.diagram.editparts.behavior.policies.BGraphicalNodeEditPolicy2;
import kr.re.etri.tpl.diagram.figures.ExpandTransElementFigure;
import kr.re.etri.tpl.diagram.properties.sources.ExpandTransElementPropertySource;
import kr.re.etri.tpl.taskmodel.ConnectionElement;
import kr.re.etri.tpl.taskmodel.ExpandTransElement;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.LinkedElement;
import kr.re.etri.tpl.taskmodel.ReferElement;
import kr.re.etri.tpl.taskmodel.TaskModelPackage;

import org.apache.log4j.Logger;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.EllipseAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gef.requests.ReconnectRequest;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.IPropertySourceProvider;

public class BExpandTransElementEditPart extends BLinkedElementEditPart {
private static Logger logger = Logger.getLogger(BExpandTransElementEditPart.class);
	
	public BExpandTransElementEditPart() {
		super();
	}
	
	@Override
	protected void createEditPolicies() {
		// allow removal of the associated model element
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new BComponentEditPolicy());

//		installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE, new BLabelDirectEditPolicy());
		
		installEditPolicy(EditPolicy.LAYOUT_ROLE,  new BBehaviorElementXYLayoutEditPolicy(this));
		
		// allow the creation of connections and
		// and the reconnection of connections between StateElement instances
		installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE, new BGraphicalNodeEditPolicy2() {

			@Override
			protected Command getConnectionCreateCommand(
					CreateConnectionRequest request) {
				ReferElement model = getCastedModel();
				ConnectionCreateCommand2 cmd = (ConnectionCreateCommand2) super.getConnectionCreateCommand(request);
				
				// KJH 20110531 s, connection order (ExpandTransElement)
				if (getHost().getParent() instanceof BLinkedElementEditPart) {
					BLinkedElementEditPart parentEditPart = (BLinkedElementEditPart)getHost().getParent();
					List<ItemElement> orderConnections = parentEditPart.getOrderConnections();
					
					if (orderConnections.contains(model)) {
						int order = orderConnections.indexOf(model) + 1;
						cmd.setOrder(order);
//						model.setName(Integer.toString(order));	// KJH 20111005, del
					} else {
						int order = orderConnections.size() + 1;
						cmd.setOrder(order);
					}
				}
				// KJH 20110531 e, connection order (ExpandTransElement)
				
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
				
				// KJH 20111005 s, connection order
				if (getHost() instanceof BLinkedElementEditPart) {
					int order = -1;
					BLinkedElementEditPart editPart = (BLinkedElementEditPart)getHost();
					for (ItemElement item : editPart.getOrderConnections()) {
						if (item instanceof ConnectionElement) {
							order = Integer.parseInt(item.getName());
							break;
						}
					}
					
					if (order == -1) {
						order = 1;
						if (getHost().getParent() instanceof BLinkedElementEditPart) {
							editPart = (BLinkedElementEditPart)getHost().getParent();
//							order = editPart.getOrderConnections().size() + 1;
							
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
						}
					}
					
					cmd.setOrder(order);
				}	// KJH 20111005 s, connection order
				
				return cmd;
			}
		});
	}
	
	@Override
	protected IFigure createFigureForModel() {
		ExpandTransElement et = getRealModel();
		
		ExpandTransElementFigure figure = new ExpandTransElementFigure();
		
		figure.setLineWidth(1);
		
		DiagramConfiguration dgmCfg = DiagramConfiguration.getInstance();
		figure.setForegroundColor(dgmCfg.getItemFGColor(TaskModelPackage.EXPAND_TRANS_ELEMENT,
				DiagramConfiguration.VALID));
		figure.setBackgroundColor(dgmCfg.getItemLineColor(TaskModelPackage.EXPAND_TRANS_ELEMENT,
				DiagramConfiguration.VALID));
		
		figure.setVisible(getCastedModel().isVisible());
		
		return figure;
	}
	
	@Override
	public Object getAdapter(Class key) {
		if (IPropertySource.class == key) {
			Object model = getModel();
			if(model instanceof ExpandTransElement) {
				return new ExpandTransElementPropertySource((ExpandTransElement)model);
			}
		}
		else if (IPropertySourceProvider.class == key) {
			final Object model = getModel();
			if(model instanceof ExpandTransElement) {
				return new IPropertySourceProvider() {
					public IPropertySource getPropertySource(Object object) {
						return new ExpandTransElementPropertySource((ExpandTransElement)model);
					}
				};
			}
		}
		return super.getAdapter(key);
	}

	/**
	 * 모델의 속성 값을 다시 화면에 다시 표시한다.
	 * 
	 * @Override
	 */
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
	
	@Override
	public ReferElement getCastedModel() {
		return (ReferElement)super.getCastedModel();
	}
	
	/**
	 * 참조 모델(ReferElement)의 실제 모델을 반환한다.
	 * @return WithElement 모델
	 */
	public ExpandTransElement getRealModel() {
		ReferElement refItem = getCastedModel();
		return (ExpandTransElement)refItem.getRealModel();
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
			return;
		}
		
		int type = notification.getEventType();
		int featureId = notification.getFeatureID(TaskModelPackage.class);
		logger.debug(featureId);
		switch(type) {
		case Notification.SET:
			switch(featureId) {
			default:
				break;
			}
			break;
		default:
			super.notifyChanged(notification);
			break;
		}
	}

	// KJH 20110520 s, connection anchor
	@Override
	protected ConnectionAnchor getConnectionAnchor() {
		if (anchor == null) {
			if (getModel() instanceof LinkedElement){
				anchor = new EllipseAnchor(getFigure());
			}
			else
				// if Shapes gets extended the conditions above must be updated
				throw new IllegalArgumentException("unexpected model");
		}
		return anchor;
	}	// KJH 20110520 s, connection anchor
	
	@Override
	public boolean isSelectable() {	// KJH 20110614
		return getCastedModel().isVisible() && !getCastedModel().isCollapsed();
	}
	
}
