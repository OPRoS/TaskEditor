
package kr.re.etri.tpl.diagram.editparts.behavior;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import kr.re.etri.tpl.diagram.editparts.behavior.anchor.TPLConnectionAnchor;
import kr.re.etri.tpl.diagram.editparts.behavior.policies.BGraphicalNodeEditPolicy2;
import kr.re.etri.tpl.diagram.properties.sources.LinkedElementPropertySource;
import kr.re.etri.tpl.taskmodel.ConnectionElement;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.LinkedElement;
import kr.re.etri.tpl.taskmodel.ReferElement;
import kr.re.etri.tpl.taskmodel.TaskModelPackage;

import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.ui.views.properties.IPropertySource;

/**
 * LinkedElement 모델과 Figure 를 Control 하는 클래스이다.
 * 
 * @see BItemElementEditPart
 * 
 * @author sfline
 */
public abstract class BLinkedElementEditPart extends BShapeElementEditPart 
implements NodeEditPart {
	/** Figure 와 연결선의 연결점 */
	protected ConnectionAnchor anchor;

	/**
	 * LinkedElement 모델의 EditPart 를 생성한다.
	 */
	public BLinkedElementEditPart() {
		super();
	}

	/**
	 * EditPart 에 대한 Policy 를 적용한다.
	 * @see org.eclipse.gef.editparts.AbstractEditPart#createEditPolicies()
	 * 
	 * @Override
	 */
	protected void createEditPolicies() {
		super.createEditPolicies();

		// allow the creation of connections and 
		// and the reconnection of connections between ShapeElement instances
		installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE, new BGraphicalNodeEditPolicy2());
//		installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE, new GraphicalNodeEditPolicy() {
//			/* (non-Javadoc)
//			 * @see org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy#getConnectionCompleteCommand(org.eclipse.gef.requests.CreateConnectionRequest)
//			 */
//			protected Command getConnectionCompleteCommand(CreateConnectionRequest request) {
//				ConnectionCreateCommand2 cmd;
//				cmd = (ConnectionCreateCommand2) request.getStartCommand();
//				cmd.setTarget((ReferElement) getHost().getModel());
//				return cmd;
//			}
//			/* (non-Javadoc)
//			 * @see org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy#getConnectionCreateCommand(org.eclipse.gef.requests.CreateConnectionRequest)
//			 */
//			protected Command getConnectionCreateCommand(CreateConnectionRequest request) {
//				ReferElement source = (ReferElement) getHost().getModel();
//				ConnectionCreateCommand2 cmd;
//				cmd = (ConnectionCreateCommand2)request.getStartCommand();
//				if(cmd == null) {
//					cmd = new ConnectionCreateCommand2();
//				}
//				ConnectionElement conn = cmd.getConnection();
//				if(conn == null) {
//					conn = ((ConnectionElement) request.getNewObjectType());
//				}
//				conn.setSourceEndPoint(LineEndPoint.NONE);
//				cmd.setConnection(conn);
//				cmd.setSource(source);
//
//				request.setStartCommand(cmd);
//				return cmd;
//			}
//			/* (non-Javadoc)
//			 * @see org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy#getReconnectSourceCommand(org.eclipse.gef.requests.ReconnectRequest)
//			 */
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
//				conn.setSourceEndPoint(LineEndPoint.NONE);
//				ConnectionReconnectCommand cmd = new ConnectionReconnectCommand(conn);
//				cmd.setNewSource(newSource);
//				return cmd;
//			}
//			/* (non-Javadoc)
//			 * @see org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy#getReconnectTargetCommand(org.eclipse.gef.requests.ReconnectRequest)
//			 */
//			protected Command getReconnectTargetCommand(ReconnectRequest request) {
//				ConnectionElement conn = (ConnectionElement) request.getConnectionEditPart().getModel();
//
//				// Include된 모델의 Connection은 편집 불가하므로
//				ReferElement refElmt = (ReferElement)conn.getSource();
//				ItemElement realItem = refElmt.getRealModel();
//				if(realItem.isIncluded()) {
//					return null;
//				}
//
//				ReferElement newTarget = (ReferElement) getHost().getModel();
//				ConnectionReconnectCommand cmd = new ConnectionReconnectCommand(conn);
//				cmd.setNewTarget(newTarget);
//				return cmd;
//			}
//		});

	}
	
	/**
	 * 모델에 대한 Figure 를 생성하여 생성한다.
	 * @return 모델에 대한 Figure
	 */
	protected IFigure createFigureForModel() {

		IFigure figure;

		if (getModel() instanceof LinkedElement) {
			figure = new RectangleFigure();
			return figure;
		}

		figure = super.createFigureForModel();
		return figure;
	}

	/**
	 * 주어지는 모델로부터 Notify 를 받도록 Listener 로 등록한다.
	 * @param item Notifier
	 */
	protected void addNotifyListener(ItemElement item) {
		if(item == null) {
			return;
		}
		
		item.eAdapters().add(this);
		
		if(item instanceof ReferElement) {
			item = ((ReferElement)item).getRealModel();
			item.eAdapters().add(this);
		}
	}

	/**
	 * 주어진 모델로 부터 Notify 를 받지 않도록 해제한다.
	 * @param item Notifier
	 */
	protected void removeNotifyListener(ItemElement item) {
		if(item == null) {
			return;
		}
		
		item.eAdapters().remove(this);
		
		if(item instanceof ReferElement) {
			item = ((ReferElement)item).getRealModel();
			item.eAdapters().remove(this);
		}
	}

	/**
	 * Object 타입의 모델을 EditPart 의 모델 타입으로 캐스팅한다.
	 * @return LinkedElement 모델의 참조 모델(ReferElement)
	 * 
	 * @Override
	 */
	public LinkedElement getCastedModel() {
		return (LinkedElement)super.getCastedModel();
	}
	
	/**
	 * 모델의 자식 모델들을 반환한다.
	 * @see org.eclipse.gef.editparts.AbstractEditPart#getModelChildren()
	 * 
	 * @Override
	 */
	protected List getModelChildren() {
		return Collections.EMPTY_LIST;
	}

	/**
	 * 모델의 속성 값을 다시 화면에 다시 표시한다.
	 * 
	 * @Override
	 */
	protected void refreshVisuals() {
		LinkedElement linkedShape = getCastedModel();
		IFigure figure = getFigure();

		// notify parent container of changed position & location
		// if this line is removed, the XYLayoutManager used by the parent container 
		// (the Figure of the ShapesDiagramEditPart), will not know the bounds of this figure
		// and will not draw it correctly.
		
		Rectangle bounds = new Rectangle(
				linkedShape.getX(),
				linkedShape.getY(),
				linkedShape.getWidth(),
				linkedShape.getHeight());
		((GraphicalEditPart) getParent()).setLayoutConstraint(this, figure, bounds);
	}
	
	/**
	 * 모델의 source ConnectionEditPart을 다시 화면에 다시 표시한다.
	 * 
	 * @Override
	 */
	protected void refreshSourceConnections() {
		int lastOrder = 0;
		List<ItemElement> list = new ArrayList<ItemElement>();
		for (ItemElement item : getOrderConnections()) {
			int order = -1;
			try {
				// KJH 20111005 s, modify
				if (item instanceof ConnectionElement) {
					order = Integer.parseInt(item.getName());
				}
				else if (item instanceof ReferElement) {
					for (ConnectionElement conn : ((ReferElement)item).getSourceConnections()) {
						order = Integer.parseInt(conn.getName());
						break;
					}
				}	// KJH 20111005 e, modify
			} catch (NumberFormatException e) {}
			
			if (order == -1) {
				list.add(item);
			}
			if (order > lastOrder) {
				lastOrder = order;
			}
		}
		
		for (ItemElement item : list) {
			// KJH 20111005 s, modify
			if (item instanceof ConnectionElement) {
				lastOrder++;
				item.setName(Integer.toString(lastOrder));
			}
			else if (item instanceof ReferElement) {
				lastOrder++;
				for (ConnectionElement conn : ((ReferElement)item).getSourceConnections()) {
					conn.setName(Integer.toString(lastOrder));
				}
			}	// KJH 20111005 e, modify
		}
		
		super.refreshSourceConnections();
	}

	/**
	 * 모델의 Property 가 변경되면 호출된다.
	 * @param notification 모델의 변경 정보
	 * 
	 * @Override
	 */
	public void notifyChanged(Notification notification) {
		int type = notification.getEventType();
		int featureId = notification.getFeatureID(TaskModelPackage.class);
		switch(type) {
		case Notification.ADD:
			switch(featureId) {
			case TaskModelPackage.LINKED_ELEMENT__SOURCE_CONNECTIONS:
				addNotifyListener((ItemElement)notification.getNewValue());
				refreshSourceConnections();
				refreshVisuals();
				break;
			case TaskModelPackage.LINKED_ELEMENT__TARGET_CONNECTIONS:
				refreshTargetConnections();
				refreshVisuals();
				break;
			case TaskModelPackage.REFER_ELEMENT__ITEMS:
				refreshChildren();
				break;
			default:
				super.notifyChanged(notification);
				break;
			}
			break;
		case Notification.REMOVE:
			switch(featureId) {
			case TaskModelPackage.LINKED_ELEMENT__SOURCE_CONNECTIONS:
				removeNotifyListener((ItemElement)notification.getOldValue());
				refreshSourceConnections();
				refreshVisuals();
				break;
			case TaskModelPackage.LINKED_ELEMENT__TARGET_CONNECTIONS:
				refreshTargetConnections();
				refreshVisuals();
				break;
			case TaskModelPackage.REFER_ELEMENT__ITEMS:
				refreshChildren();
				break;
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

	/**
	 * 속성 중 Connection Property 가 변경되면 호출된다.
	 * @param notification 모델의 변경 정보
	 */
	protected void connectionElemeentNotifyChanged(Notification notification){
		int type = notification.getEventType();
		int featureId = notification.getFeatureID(TaskModelPackage.class);
		switch(type) {
		case Notification.SET:
		case Notification.ADD:
		case Notification.ADD_MANY:
		case Notification.REMOVE:
		case Notification.REMOVE_MANY:
			switch(featureId) {
			case TaskModelPackage.CONNECTION_ELEMENT__CONDITION:
				break;
			case TaskModelPackage.LINE_ELEMENT__LINE_STYLE:
			case TaskModelPackage.LINE_ELEMENT__SOURCE_END_POINT:
			case TaskModelPackage.LINE_ELEMENT__TARGET_END_POINT:
			case TaskModelPackage.LINE_ELEMENT__BEND_POINTS:
				break;
			default:
//				super.notifyChanged(notification);
				break;
			}
			refreshVisuals();
			break;
		}
	}

	/**
	 * Parameter 로 주어진 클래스(또는 인터페이스)에 대한 Adapter 를 제공한다.
	 * @param key Adapter 요청 타입
	 * @return Parameter 로 주어진 클래스(또는 인터페이스)에 대한 Adapter
	 */
	public Object getAdapter(Class key) {
		if (IPropertySource.class == key) {
			Object model = getModel();
			if(model instanceof LinkedElement) {
				return new LinkedElementPropertySource((LinkedElement)model);
			}
		}
		return super.getAdapter(key);
	}

	/**
	 * 연결점을 제공한다.
	 * @return
	 */
	protected ConnectionAnchor getConnectionAnchor() {
		if (anchor == null) {
			if (getModel() instanceof LinkedElement){
				anchor = new TPLConnectionAnchor(getFigure());
			}
			else
				// if Shapes gets extended the conditions above must be updated
				throw new IllegalArgumentException("unexpected model");
		}
		return anchor;
	}

	/**
	 * 모델을 Source 로 연결한 Connection 들을 반환한다.
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#getModelSourceConnections()
	 */
	protected List getModelSourceConnections() {
		LinkedElement elmt = getCastedModel();
		return elmt.getSourceConnections();
	}

	/**
	 * 모델을 Target 으로 연결한 Connection 들을 반환한다.
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#getModelTargetConnections()
	 */
	protected List getModelTargetConnections() {
		LinkedElement elmt = getCastedModel();
		return elmt.getTargetConnections();
	}

	/**
	 * 모델을 Source 로 연결한 연결점을 반환한다.
	 * @see org.eclipse.gef.NodeEditPart#getSourceConnectionAnchor(org.eclipse.gef.ConnectionEditPart)
	 */
	public ConnectionAnchor getSourceConnectionAnchor(ConnectionEditPart connection) {
		return getConnectionAnchor();
	}
	
	/**
	 * 모델을 Source 로 연결한 연결점을 반환한다.
	 * @see org.eclipse.gef.NodeEditPart#getSourceConnectionAnchor(org.eclipse.gef.Request)
	 */
	public ConnectionAnchor getSourceConnectionAnchor(Request request) {
		return getConnectionAnchor();
	}
	
	/**
	 * 모델을 Target 으로 연결한 연결점을 반환한다.
	 * (non-Javadoc)
	 * @see org.eclipse.gef.NodeEditPart#getTargetConnectionAnchor(org.eclipse.gef.ConnectionEditPart)
	 */
	public ConnectionAnchor getTargetConnectionAnchor(ConnectionEditPart connection) {
		return getConnectionAnchor();
	}
	
	/**
	 * 모델을 Target 으로 연결한 연결점을 반환한다.
	 * @see org.eclipse.gef.NodeEditPart#getTargetConnectionAnchor(org.eclipse.gef.Request)
	 */
	public ConnectionAnchor getTargetConnectionAnchor(Request request) {
		return getConnectionAnchor();
	}

	/**
	 * Property 가 변경되면 호출된다
	 * @param event Property 변경 Event
	 * 
	 * @Override
	 */
	public void propertyChange(PropertyChangeEvent event) {
		super.propertyChange(event);
	}
	
	// KJH 20110530 s, 우선순위 지정대상 connections 구하는 함수
	public List<ItemElement> getOrderConnections() {
		List<ItemElement> list = new ArrayList<ItemElement>();
		list.addAll(getModelSourceConnections());
		sortListByOrder(list);
		return list;
	}
	
	protected void sortListByOrder(List<ItemElement> list) {
		Collections.sort(list, new Comparator<ItemElement>() {
			@Override
			public int compare(ItemElement o1, ItemElement o2) {
				String s1 = o1.getName();
				String s2 = o2.getName();
				int pri1= Integer.MAX_VALUE;
				int pri2= Integer.MAX_VALUE;
				
				// KJH 20111005 s,
				if (o1 instanceof LinkedElement) {
					for (ConnectionElement conn : ((LinkedElement)o1).getSourceConnections()) {
						s1 = conn.getName();
						break;
					}
				}
				if (o2 instanceof LinkedElement) {
					for (ConnectionElement conn : ((LinkedElement)o2).getSourceConnections()) {
						s2 = conn.getName();
						break;
					}
				}	// KJH 20111005 e,
				
				if(s1 != null ){
					try{
						pri1 = Integer.parseInt(s1);
					}catch(NumberFormatException e){
					}
				}
				if(s2 != null){
					try{
						pri2 = Integer.parseInt(s2);
					}catch(NumberFormatException e){
					}
				}
				
				return pri1 - pri2;
			}

		});
	}
	// KJH 20110530 e, 우선순위 지정대상 connections 구하는 함수
}