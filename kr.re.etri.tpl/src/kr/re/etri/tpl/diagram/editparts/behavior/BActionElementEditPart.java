
package kr.re.etri.tpl.diagram.editparts.behavior;

import java.util.Collections;
import java.util.List;

import kr.re.etri.tpl.diagram.DiagramConfiguration;
import kr.re.etri.tpl.diagram.celledit.LabelCellEditorLocator;
import kr.re.etri.tpl.diagram.celledit.LabelDirectEditManager;
import kr.re.etri.tpl.diagram.editparts.behavior.policies.BComponentEditPolicy;
import kr.re.etri.tpl.diagram.editparts.behavior.policies.BGraphicalNodeEditPolicy2;
import kr.re.etri.tpl.diagram.editparts.behavior.policies.BLabelDirectEditPolicy;
import kr.re.etri.tpl.diagram.figures.ActionElementFigure;
import kr.re.etri.tpl.diagram.properties.sources.ActionElementPropertySource;
import kr.re.etri.tpl.diagram.util.ItemState;
import kr.re.etri.tpl.taskmodel.ActionElement;
import kr.re.etri.tpl.taskmodel.ConnectionElement;
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
import org.eclipse.gef.requests.DirectEditRequest;
import org.eclipse.gef.tools.DirectEditManager;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.IPropertySourceProvider;

/**
 * ActionElement 모델과 Figure 을 control 하는 EditPart 클래스이다.
 * 
 * @see BItemElementEditPart
 * 
 * @author sfline
 */
public class BActionElementEditPart extends BLinkedElementEditPart
	implements IPropertyChangeListener {

	/** 이름 Label 의 text 를 수정할 수 있도록 CellEditor 관리 */
	protected DirectEditManager manager;

	/**
	 * ActionElement 모델의 참조 모델(ReferElement) 대한 EditPart 를 생성한다.
	 */
	public BActionElementEditPart() {
		super();
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
	 * @return ActionElement 모델
	 */
	public ActionElement getRealModel() {
		ReferElement refItem = getCastedModel();
		return (ActionElement)refItem.getRealModel();
	}

	/**
	 * EditPart 에 대한 Policy 를 적용한다.
	 * @see org.eclipse.gef.editparts.AbstractEditPart#createEditPolicies()
	 * 
	 * @Override
	 */
	protected void createEditPolicies() {
		// allow removal of the associated model element
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new BComponentEditPolicy());
		installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE, new BLabelDirectEditPolicy());
		// allow the creation of connections and 
		// and the reconnection of connections between ActionElement instances
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
//				// Action에서 Connection이 시작하는 것은 없음
//				return null;
//
////				LinkedElement source = (LinkedElement) getHost().getModel();
////				ConnectionElement conn = ((ConnectionElement) request.getNewObjectType());
////				ConnectionCreateCommand cmd = 
////					new ConnectionCreateCommand(source, conn, conn.getRelationship());
////				request.setStartCommand(cmd);
////				return cmd;
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
	 * 
	 * @Override
	 */
	protected IFigure createFigureForModel() {

		ActionElement action = this.getRealModel();

		DiagramConfiguration dgmCfg = DiagramConfiguration.getInstance();
			
		figure = new ActionElementFigure();
//		figure.setBorder(new MarginBorder(2));
//		figure.setLayoutManager(new XYLayout());

		FontData []fd = new FontData[]{new FontData("Candara", 10, SWT.NORMAL)};
		Font font = new Font(null, fd);
		figure.setFont(font);

		figure.setBackgroundColor(dgmCfg.getItemBGColor(TaskModelPackage.ACTION_ELEMENT, DiagramConfiguration.NONE));
		((ActionElementFigure)figure).setMinimumSize(new Dimension(100, 25));
		figure.setOpaque(true); // non-transparent figure

		String name = action.getName();
		if(name == null|| name.isEmpty()) {
			action.setName("New_Action");
		}
		((ActionElementFigure)figure).setName(action.getName());

		figure.setVisible(action.isVisible());

		
		return figure;
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
	 * Parameter 로 주어진 클래스(또는 인터페이스)에 대한 Adapter 를 제공한다.
	 * @param key Adapter 요청 타입
	 * @return Parameter 로 주어진 클래스(또는 인터페이스)에 대한 Adapter
	 */
	public Object getAdapter(Class key) {
		if (IPropertySource.class == key) {
			ActionElement model = getRealModel();
			if(model instanceof ActionElement) {
				return new ActionElementPropertySource(model);
			}
		}
		else if (IPropertySourceProvider.class == key) {
			final Object model = getModel();
			if(model instanceof ActionElement) {
				return new IPropertySourceProvider() {
					public IPropertySource getPropertySource(Object object) {
						return new ActionElementPropertySource((ActionElement)model);		
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
		ActionElement action = getRealModel();
		ActionElementFigure figure = (ActionElementFigure)getFigure();
		
		int itemState = action.getItemState();
		if(action.isIncluded()) {
			itemState |= ItemState.ITEM_INCLUDED;
		}
		figure.setModelState(itemState);

		figure.setName(action.getName());

		ReferElement refItem = getCastedModel();
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
	}
	
	/**
	 * 모델의 Property 가 변경되면 호출된다.
	 * @param notification 모델의 변경 정보
	 * 
	 * @Override
	 */
	public void notifyChanged(Notification notification) {
		Object notifier = notification.getNotifier();
		if (notifier instanceof ConnectionElement) {
			connectionElemeentNotifyChanged(notification);
			return;
		}
		if (notifier instanceof ReferElement) {
			super.notifyChanged(notification);
			return;
		}

		int type = notification.getEventType();
		int featureId = notification.getFeatureID(TaskModelPackage.class);
		switch(type) {
		case Notification.SET:
			switch(featureId) {
			case TaskModelPackage.ACTION_ELEMENT__NAME:
				ReferElement refItem = getCastedModel();
				EList<ConnectionElement> connList = refItem.getTargetConnections();
				for(ConnectionElement conn : connList) {
					conn.setTarget(refItem);
				}
				refreshVisuals();
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
	 * Property 가 변경되면 호출된다
	 * @param event Property 변경 Event
	 * 
	 * @Override
	 */
	public void propertyChange(PropertyChangeEvent event) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 주어진 Request 를 수행한다. 이 method 는 EditPart 에 일반적인
	 * 메시지를 보내기 위해 사용된다.
	 * 
	 * @see org.eclipse.gef.EditPart#performRequest(org.eclipse.gef.Request)
	 */
	public void performRequest(Request request) {
		// DirectEdit 요청 처리
		if (request.getType() == RequestConstants.REQ_DIRECT_EDIT) {
		}
		// Figure 에 대한 마우스 Double Click 요청 처리
		else if (request.getType() == RequestConstants.REQ_OPEN) {
			ActionElement action = getRealModel();
			if(action.isIncluded()) {
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
	 * Request 요청이 이름 Label 영역인가를 확인한다.
	 * @param requestLoc Request 의 좌표
	 * @return Name Label 영역 포함 여부
	 */
	private boolean directEditHitTest(Point requestLoc) {
		Label label = ((ActionElementFigure)getFigure()).getLabelFigure();
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
			Label label = ((ActionElementFigure)getFigure()).getLabelFigure();
			manager =
				new LabelDirectEditManager(
					this,
					TextCellEditor.class,
					new LabelCellEditorLocator(label), label);
		}
		manager.show();
	}
}