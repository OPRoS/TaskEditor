
package kr.re.etri.tpl.diagram.editparts.task;

import java.util.Collections;
import java.util.List;

import kr.re.etri.tpl.grammar.ParserUtil;
import kr.re.etri.tpl.taskmodel.ConnectionElement;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.LineEndPoint;
import kr.re.etri.tpl.taskmodel.LinkedElement;
import kr.re.etri.tpl.taskmodel.ReferElement;
import kr.re.etri.tpl.taskmodel.RelationShip;
import kr.re.etri.tpl.taskmodel.StateAction;
import kr.re.etri.tpl.taskmodel.StateAttribute;
import kr.re.etri.tpl.taskmodel.StateElement;
import kr.re.etri.tpl.taskmodel.TaskModelPackage;
import kr.re.etri.tpl.diagram.DiagramConfiguration;
import kr.re.etri.tpl.diagram.celledit.LabelCellEditorLocator;
import kr.re.etri.tpl.diagram.celledit.LabelDirectEditManager;
import kr.re.etri.tpl.diagram.commands.ConnectionCreateCommand;
import kr.re.etri.tpl.diagram.commands.ConnectionReconnectCommand;
import kr.re.etri.tpl.diagram.editparts.behavior.BItemElementEditPart;
import kr.re.etri.tpl.diagram.editparts.task.policies.TComponentEditPolicy;
import kr.re.etri.tpl.diagram.editparts.task.policies.TLabelDirectEditPolicy;
import kr.re.etri.tpl.diagram.figures.StateElementFigure;
import kr.re.etri.tpl.diagram.properties.sources.StateElementPropertySource;
import kr.re.etri.tpl.diagram.util.MarkerLogger;
import kr.re.etri.tpl.diagram.util.TPLErrorLogger;

import org.antlr.runtime.RecognitionException;
import org.apache.log4j.Logger;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
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
 * StateElement 모델과 Figure 을 control 하는 EditPart 클래스이다.
 * 
 * @see TItemElementEditPart
 * 
 * @author sfline
 */
public class TStateElementEditPart extends TLinkedElementEditPart {
	private static Logger logger = Logger.getLogger(TStateElementEditPart.class);

	/** 이름 Label 의 text 를 수정할 수 있도록 CellEditor 관리 */
	protected DirectEditManager manager;

	/**
	 * StateElement 모델의 참조 모델(ReferElement) 대한 EditPart 를 생성한다.
	 */
	public TStateElementEditPart() {
		super();
	}

	/**
	 * EditPart 가 활성화되면 모델로 부터 Notify 를 받도록 한다.
	 * 모델의 child 모델 또는 Connection 의 속성이 변경되어도 Notify 를 받도록
	 * 한다.
	 */
	protected void hookIntoModel(EObject model) {
		super.hookIntoModel(model);

		ReferElement refItem = getCastedModel();
		List<ConnectionElement> conList = refItem.getSourceConnections();
		for(ConnectionElement con : conList) {
			addNotifyListener(con);
		}

		ItemElement realItem = getRealModel();
		if(realItem instanceof StateElement) {
			StateAction stayAction = ((StateElement)realItem).getStay();
			if(stayAction != null) {
				stayAction.eAdapters().add(this);
			}
		}
	}
	
	/**
	 * EditPart 가 비활성화되면 모델로 부터 Notify 를 받지 않도록 한다.
	 */
	protected void unhookFromModel(EObject model) {
		super.unhookFromModel(model);

		ReferElement refItem = getCastedModel();
		List<ConnectionElement> conList = refItem.getSourceConnections();
		for(ConnectionElement con : conList) {
			removeNotifyListener(con);
		}

		ItemElement realItem = getRealModel();
		if(realItem instanceof StateElement) {
			StateAction stayAction = ((StateElement)realItem).getStay();
			if(stayAction != null) {
				stayAction.eAdapters().remove(this);
			}
		}
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
	 * @return ActionElement 모델의 참조 모델(ReferElement)
	 * 
	 * @Override
	 */
	public ReferElement getCastedModel() {
		return (ReferElement)super.getCastedModel();
	}

	/**
	 * 참조 모델(ReferElement)의 실제 모델을 반환한다.
	 * @return StateElement 모델
	 */
	public StateElement getRealModel() {
		ReferElement refItem = getCastedModel();
		return (StateElement)refItem.getRealModel();
	}
	
	/**
	 * Figure 의 최소 크기를 반환한다.
	 * 
	 * @return Figure 의 최소 크기
	 */
	public Dimension getMinimumSize() {
		return this.getFigure().getMinimumSize();
	}

	/**
	 * EditPart 에 대한 Policy 를 적용한다.
	 * @see org.eclipse.gef.editparts.AbstractEditPart#createEditPolicies()
	 * 
	 * @Override
	 */
	protected void createEditPolicies() {
		// allow removal of the associated model element
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new TComponentEditPolicy());

		installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE, new TLabelDirectEditPolicy());

		// allow the creation of connections and 
		// and the reconnection of connections between StateElement instances
		installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE, new GraphicalNodeEditPolicy() {
			/* (non-Javadoc)
			 * @see org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy#getConnectionCompleteCommand(org.eclipse.gef.requests.CreateConnectionRequest)
			 */
			protected Command getConnectionCompleteCommand(CreateConnectionRequest request) {
				ReferElement target;
				target = (ReferElement) getHost().getModel();

				ConnectionCreateCommand cmd;
				cmd = (ConnectionCreateCommand) request.getStartCommand();

				ItemElement realItem = target.getRealModel();
				if(realItem instanceof StateElement) {
					LinkedElement source = cmd.getSource();
					if(source.getParent() != target.getParent()) {
						return null;
					}
				}

				cmd.setTarget(target);
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

				if(RelationShip.TRANSITION != conn.getRelationship()) {
					ItemElement realItem = source.getRealModel();
					List<ReferElement> refList = realItem.getReferences();
					for(ReferElement refItem : refList) {
						List<ConnectionElement> connList = refItem.getSourceConnections();
						for(ConnectionElement connItem : connList) {
							if(RelationShip.ACTION_CALL == connItem.getRelationship()) {
								return null;
							}
							if(RelationShip.TASK_CALL == connItem.getRelationship()) {
								return null;
							}
						}
					}
				}
				
				conn.setSourceEndPoint(LineEndPoint.NONE);
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

				// Include된 모델의 Connection은 편집 불가하므로
				ReferElement newSource = (ReferElement) getHost().getModel();
				ItemElement realItem = newSource.getRealModel();
				if(realItem.isIncluded()) {
					return null;
				}

				// State는 Action Call 또는 Task Call은 1개만 할 수 있다.
				// TODO : 현재는 Do StateAction에서만 Action 또는 Task를  Call한다.
				// 추후 Entry, Exit StateAction 기능이 추가되는 경우 수정되어야  한다.
				realItem = newSource.getRealModel();
				List<ReferElement> refList = realItem.getReferences();
				
				if(RelationShip.TRANSITION != conn.getRelationship()) {
					int callCount = 0;
					for(ReferElement refItem : refList) {
						List<ConnectionElement> connList = refItem.getSourceConnections();
						for(ConnectionElement connItem : connList) {
							if(RelationShip.TRANSITION == connItem.getRelationship()) {
								continue;
							}
							if(conn == connItem) {
								continue;
							}
							callCount += 1;
						}
					}
	
					if(callCount > 0) {
						return null;
					}
				}

				ConnectionReconnectCommand cmd = new ConnectionReconnectCommand(conn);

				realItem = newSource.getRealModel();
				if(realItem instanceof StateElement) {
					if(RelationShip.TRANSITION == conn.getRelationship()) {
						LinkedElement target = conn.getTarget();
						if(target.getParent() != newSource.getParent()) {
							return null;
						}
					}
				}

				cmd.setNewSource(newSource);
				return cmd;
			}
			/* (non-Javadoc)
			 * @see org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy#getReconnectTargetCommand(org.eclipse.gef.requests.ReconnectRequest)
			 */
			protected Command getReconnectTargetCommand(ReconnectRequest request) {
				ConnectionElement conn = (ConnectionElement) request.getConnectionEditPart().getModel();

				// Include된 모델의 Connection은 편집 불가하므로
				ReferElement refElmt = (ReferElement)conn.getSource();
				ItemElement realItem = refElmt.getRealModel();
				if(realItem.isIncluded()) {
					return null;
				}

				ReferElement newTarget = (ReferElement) getHost().getModel();
				realItem = newTarget.getRealModel();
				List<ReferElement> refList = realItem.getReferences();

				if(RelationShip.TRANSITION != conn.getRelationship()) {
					int callCount = 0;
					for(ReferElement refItem : refList) {
						List<ConnectionElement> connList = refItem.getSourceConnections();
						for(ConnectionElement connItem : connList) {
							if(RelationShip.TRANSITION == connItem.getRelationship()) {
								continue;
							}

							callCount += 1;
						}
					}
					
					if(callCount > 1) {
						return null;
					}
				}

				ConnectionReconnectCommand cmd = new ConnectionReconnectCommand(conn);

				realItem = newTarget.getRealModel();
				if(realItem instanceof StateElement) {
					if(RelationShip.TRANSITION == conn.getRelationship()) {
						LinkedElement source = conn.getSource();
						if(source.getParent() != newTarget.getParent()) {
							return null;
						}
					}
				}

				cmd.setNewTarget(newTarget);
				return cmd;
			}
		});
	}
	
	/**
	 * 모델에 대한 Figure 를 생성하여 생성한다.
	 * @return 모델에 대한 Figure
	 * 
	 * @Override
	 */
	protected IFigure createFigureForModel() {
		
		StateElement state = this.getRealModel();

		DiagramConfiguration dgmCfg = DiagramConfiguration.getInstance();
		
		figure = new StateElementFigure(state.getAttribute());
//		figure.setBorder(new MarginBorder(2));
//		figure.setLayoutManager(new XYLayout());

		FontData []fd = new FontData[]{new FontData("Candara", 10, SWT.NORMAL)};
		Font font = new Font(null, fd);
		figure.setFont(font);

		figure.setForegroundColor(dgmCfg.getItemFGColor(TaskModelPackage.STATE_ELEMENT, DiagramConfiguration.VALID));
		figure.setBackgroundColor(dgmCfg.getItemBGColor(TaskModelPackage.STATE_ELEMENT, DiagramConfiguration.VALID));
		((StateElementFigure)figure).setMinimumSize(new Dimension(100, 25));
		figure.setOpaque(true); // non-transparent figure

		String name = state.getName();
		if(name == null|| name.isEmpty()) {
			if(StateAttribute.INITIAL == state.getAttribute()) {
				state.setName("Initial_State");
			}
			else if(StateAttribute.TARGET == state.getAttribute()) {
				state.setName("Target_State");
			}
			else {
				state.setName("New_State");
			}
		}
		((StateElementFigure)figure).setName(state.getName());

		figure.setVisible(state.isVisible());

		return figure;
	}
	
	/**
	 * 모델의 자식 모델들을 반환한다.
	 * @see org.eclipse.gef.editparts.AbstractEditPart#getModelChildren()
	 * 
	 * @Override
	 */
	protected List getModelChildren() {
/*
		StateElement model = getRealModel();
		StateAction action;

		List list = new ArrayList();
		action = model.getEntry();
		if(action != null) {
			list.add(action);
		}
		action = model.getDo();
		if(action != null) {
			list.add(action);
		}
		action = model.getExit();
		if(action != null) {
			list.add(action);
		}

		return list;
*/
		return Collections.EMPTY_LIST;
	}

	/**
	 * Parameter 로 주어진 클래스(또는 인터페이스)에 대한 Adapter 를 제공한다.
	 * @param key Adapter 요청 타입
	 * @return Parameter 로 주어진 클래스(또는 인터페이스)에 대한 Adapter
	 */
	public Object getAdapter(Class key) {
		if (IPropertySource.class == key) {
			Object model = getModel();
			if(model instanceof StateElement) {
				return new StateElementPropertySource((StateElement)model);
			}
		}
		else if (IPropertySourceProvider.class == key) {
			final Object model = getModel();
			if(model instanceof StateElement) {
				return new IPropertySourceProvider() {
					public IPropertySource getPropertySource(Object object) {
						return new StateElementPropertySource((StateElement)model);		
					}
				};
			}
		}
		return super.getAdapter(key);
	}
	
	/**
	 * 화면에 표시할 내용이 있는지 확인한다.
	 * @return true 면 화면에 표시할 내용이 있다. 그렇지 않으면 없다.
	 */
	private boolean checkVisual() {
		StateElement model = getRealModel();
		ReferElement refItem = getCastedModel();

		List<ConnectionElement> connList = refItem.getSourceConnections();
		for(ConnectionElement con : connList) {
			if(con.getRelationship() == RelationShip.TRANSITION) {
				List<String> stmtString = con.getCondition();
				StringBuilder sb = new StringBuilder();
				for(String str : stmtString) {
					sb.append(str.trim());
				}
				if(sb.length() == 0) {
					return false;
				}
			}
			else if(con.getRelationship() == RelationShip.ACTION_CALL ||
					con.getRelationship() == RelationShip.TASK_CALL) {

				StateAction stateActon = model.getStay();
				List<String> stmtString = stateActon.getStatements();

				StringBuilder sb = new StringBuilder();
				for(String str : stmtString) {
					sb.append(str.trim());
				}
				if(sb.length() == 0) {
					return false;
				}
			}
		}
		
		return true;
	}

	/**
	 * 모델의 속성 값을 다시 화면에 다시 표시한다.
	 * 
	 * @Override
	 */
	protected void refreshVisuals() {
		StateElement model = getRealModel();
		ReferElement refItem = getCastedModel();

		StateElementFigure figure = (StateElementFigure)getFigure();

	//	StateAction stateActon = model.getDo();

		DiagramConfiguration dgmCfg = DiagramConfiguration.getInstance();
		if(checkVisual()) {
			figure.setForegroundColor(dgmCfg.getItemFGColor(TaskModelPackage.STATE_ELEMENT, DiagramConfiguration.VALID));
		}
		else {
			figure.setForegroundColor(dgmCfg.getItemFGColor(TaskModelPackage.STATE_ELEMENT, DiagramConfiguration.NONE));
		}

		figure.setName(model.getName());
		
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

		figure.setVisible(model.isVisible());
	}
	
	/**
	 * 모델의 Property 가 변경되면 호출된다.
	 * @param notification 모델의 변경 정보
	 * 
	 * @Override
	 */
	public void notifyChanged(Notification notification) {
		
		Object notifier = notification.getNotifier();
		if(notifier instanceof StateAction) {
			stateActionNotifyChanged(notification);
			return;
		}

		if(notifier instanceof ConnectionElement) {
			connectionElemeentNotifyChanged(notification);
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
			case TaskModelPackage.STATE_ELEMENT__ENTRY:
				break;
			case TaskModelPackage.STATE_ELEMENT__STAY:
				break;
			case TaskModelPackage.STATE_ELEMENT__EXIT:
				break;
			default:
				super.notifyChanged(notification);
				break;
			}
			refreshVisuals();
			break;
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
				refreshVisuals();
				break;
			case TaskModelPackage.STATE_ELEMENT__STAY:
				refreshChildren();
//				StateAction stateAction = (StateAction)notification.getNewValue();
//				stateAction.eAdapters().add(this);
				break;
			default:
				super.notifyChanged(notification);
				break;
			}
			break;
		case Notification.REMOVE:
			switch(featureId) {
			case TaskModelPackage.LINKED_ELEMENT__SOURCE_CONNECTIONS:
				ConnectionElement conn = (ConnectionElement)notification.getOldValue();
				if(RelationShip.ACTION_CALL == conn.getRelationship() ||
					RelationShip.TASK_CALL == conn.getRelationship()) {
					StateElement realItem = getRealModel();
					StateAction stateAction = realItem.getStay();
					if(stateAction != null) {
						stateAction.getStatements().clear();
					}
				}

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
				refreshVisuals();
				break;
			case TaskModelPackage.STATE_ELEMENT__STAY:
				refreshChildren();
//				StateAction stateAction = (StateAction)notification.getNewValue();
//				stateAction.eAdapters().remove(this);
				break;
			default:
				super.notifyChanged(notification);
				break;
			}
			break;
		}
	}
	
	/**
	 * child 모델 중 StateAction 모델의 Property 가 변경되면 호출된다.
	 * @param notification 모델의 변경 정보
	 */
	protected void stateActionNotifyChanged(Notification notification){
		int type = notification.getEventType();
		int featureId = notification.getFeatureID(TaskModelPackage.class);
		switch(type) {
		case Notification.SET:
			switch(featureId) {
			case TaskModelPackage.STATE_ACTION__STATEMENTS:
				StateAction stateAction = (StateAction)notification.getNotifier();
				List<String> strList = stateAction.getStatements();
				StringBuilder sb = new StringBuilder();
				for(String str : strList) {
					sb.append(str).append("\r\n");
				}

				try {
					MarkerLogger logger = getLogger();
					logger.clearProblem();

					ParserUtil parser = new ParserUtil();
					parser.checkStayAction(getRootModel(), sb.toString(), logger);
				} catch (RecognitionException e) {
					TPLErrorLogger.error(e.getMessage(), e);
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
			case TaskModelPackage.STATE_ACTION__STATEMENTS:
				StateAction stateAction = (StateAction)notification.getNotifier();
				List<String> strList = stateAction.getStatements();
				StringBuilder sb = new StringBuilder();
				for(String str : strList) {
					sb.append(str).append("\r\n");
				}

				try {
					MarkerLogger logger = getLogger();
					logger.clearProblem();

					ParserUtil parser = new ParserUtil();
					parser.checkStayAction(getRootModel(), sb.toString(), logger);
				} catch (RecognitionException e) {
					TPLErrorLogger.error(e.getMessage(), e);
				}
				break;
			default:
				super.notifyChanged(notification);
				break;
			}
			refreshVisuals();
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
			switch(featureId) {
			case TaskModelPackage.CONNECTION_ELEMENT__CONDITION:

				break;
			default:
				super.notifyChanged(notification);
				break;
			}
			refreshVisuals();
			break;
		case Notification.ADD:
			switch(featureId) {
			default:
				super.notifyChanged(notification);
				break;
			}
			refreshVisuals();
			break;
		case Notification.ADD_MANY:
			switch(featureId) {
			default:
				super.notifyChanged(notification);
				break;
			}
			refreshVisuals();
			break;
		case Notification.REMOVE:
			switch(featureId) {
			default:
				super.notifyChanged(notification);
				break;
			}
			refreshVisuals();
			break;
		case Notification.REMOVE_MANY:
			switch(featureId) {
			default:
				super.notifyChanged(notification);
				break;
			}
			refreshVisuals();
			break;
		}
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

	/**
	 * 주어진 Request 를 수행한다. 이 method 는 EditPart 에 일반적인
	 * 메시지를 보내기 위해 사용된다.
	 * 
	 * @see org.eclipse.gef.EditPart#performRequest(org.eclipse.gef.Request)
	 */
	public void performRequest(Request request) {
		if (request.getType() == RequestConstants.REQ_DIRECT_EDIT) {
		}
		else if (request.getType() == RequestConstants.REQ_OPEN) {
			StateElement state = getRealModel();
			if(state.isIncluded()) {
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

/*
	public void performRequest(Request request) {
		if (request.getType() == RequestConstants.REQ_DIRECT_EDIT) {
			StateElement state = getRealModel();
			if(state.isIncluded()) {
				return;
			}
			if (request instanceof DirectEditRequest
				&& !directEditHitTest(((DirectEditRequest) request)
					.getLocation()
					.getCopy()))
				return;
			performDirectEdit();
		}
		else if (request.getType() == RequestConstants.REQ_OPEN) {
			
			boolean hasCall = checkCall();
			if(hasCall == false) {
				return;
			}

			StateElement state = getRealModel();
			StateAction stateAction = state.getDo();
			
			if(stateAction != null) {
				TextEditorDialog dialog;
				String title = "\"Do\" Action statement.";
				String message = "Please enter the action statement.";
				
				List<String> strList = stateAction.getStatements();
				StringBuilder sb = new StringBuilder();
				for(String str : strList) {
					sb.append(str).append("\r\n");
				}

				Shell shell = PlatformUI.getWorkbench().getDisplay().getActiveShell();
				dialog = new TextEditorDialog(shell, title, message, sb.toString());

				if(dialog.open() == IDialogConstants.OK_ID) {
					int type, featureId;
					PropertyContainer propety;
					StateActionPropertySource propertySource;
					
					propertySource = new StateActionPropertySource(stateAction);

					// Statement목록을 모두 새로 입력된 Statement로 변경하기위한 Command를 실행한다.
					type = PropertyContainer.REPLACEALL;
					featureId = TaskModelPackage.STATE_ACTION__STATEMENTS;
					SetListValueCommand cmd = new SetListValueCommand("Do statement");
					cmd.setTarget(propertySource);
					cmd.setPropertyId(featureId);

					Object propValue = propertySource.getPropertyValue(featureId);
					ArrayList<String> oldValue = new ArrayList<String>();
					oldValue.addAll((List<String>)propValue);

					String []stmtString = dialog.getText().split("\r\n");
					ArrayList<String> newValue = new ArrayList<String>();
					for(String str : stmtString) {
						newValue.add(str);
					}

					propety = new PropertyContainer(type, featureId, newValue, oldValue);
					cmd.setPropertyValue(propety);

					EditPartViewer viewer = this.getViewer();
					DefaultEditDomain domain = (DefaultEditDomain)viewer.getEditDomain();
					CommandStack cmdStack = domain.getCommandStack();
					cmdStack.execute(cmd);
				}
			}
		}
	}
*/
	/**
	 * Request 요청이 이름 Label 영역인가를 확인한다.
	 * @param requestLoc Request 의 좌표
	 * @return Name Label 영역 포함 여부
	 */
	private boolean directEditHitTest(Point requestLoc) {
		Label label = ((StateElementFigure)getFigure()).getLabelFigure();
		label.translateToRelative(requestLoc);
		if (label.containsPoint(requestLoc))
			return true;
		return false;
	}

	/**
	 * Direct Edit 를 수행한다.
	 */
	protected void performDirectEdit() {
		if (manager == null) {
			Label label = ((StateElementFigure)getFigure()).getLabelFigure();
			manager =
				new LabelDirectEditManager(
					this,
					TextCellEditor.class,
					new LabelCellEditorLocator(label), label);
		}
		manager.show();
	}

	/**
	 * Connection 들의 Relation 이 call 이 있는가 검사한다.
	 * @return Relation 이 1개 이상의 Call 이 있으면 true, 그렇지 않으면 false
	 */
	private boolean checkCall() {
		ReferElement refItem = getCastedModel();
		EList<ConnectionElement> conList = refItem.getSourceConnections();
		for(ConnectionElement con : conList) {
			if(con.getRelationship() == RelationShip.ACTION_CALL) {
				return true;
			}
			else if(con.getRelationship() == RelationShip.TASK_CALL) {
				return true;
			}
		}

		return false;
	}
}