package kr.re.etri.tpl.diagram.editparts.behavior;

import java.util.ArrayList;
import java.util.List;

import kr.re.etri.tpl.diagram.DiagramConfiguration;
import kr.re.etri.tpl.diagram.UniShortestConnectionRouter;
import kr.re.etri.tpl.diagram.celledit.LabelCellEditorLocator;
import kr.re.etri.tpl.diagram.celledit.LabelDirectEditManager;
import kr.re.etri.tpl.diagram.editparts.behavior.policies.BComponentEditPolicy;
import kr.re.etri.tpl.diagram.editparts.behavior.policies.BConnectorElementXYLayoutEditPolicy;
import kr.re.etri.tpl.diagram.editparts.behavior.policies.BGraphicalNodeEditPolicy2;
import kr.re.etri.tpl.diagram.editparts.behavior.policies.BLabelDirectEditPolicy;
import kr.re.etri.tpl.diagram.figures.ConnectorElementFigure;
import kr.re.etri.tpl.diagram.properties.sources.ConnectorElementPropertySource;
import kr.re.etri.tpl.diagram.util.ItemState;
import kr.re.etri.tpl.diagram.util.MarkerLogger;
import kr.re.etri.tpl.diagram.util.TPLErrorLogger;
import kr.re.etri.tpl.grammar.ParserUtil;
import kr.re.etri.tpl.taskmodel.ConnectionElement;
import kr.re.etri.tpl.taskmodel.ConnectorElement;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.ReferAttribute;
import kr.re.etri.tpl.taskmodel.ReferElement;
import kr.re.etri.tpl.taskmodel.StructBlockElement;
import kr.re.etri.tpl.taskmodel.TaskModelPackage;

import org.antlr.runtime.RecognitionException;
import org.apache.log4j.Logger;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gef.requests.LocationRequest;
import org.eclipse.gef.requests.ReconnectRequest;
import org.eclipse.gef.tools.DirectEditManager;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.IPropertySourceProvider;

public class BConnectorElementEditPart extends BLinkedElementEditPart {
	private static Logger logger =
		 Logger.getLogger(BConnectorElementEditPart.class.getName());
//	private Dimension m_corner;	// KJH 20110209,
	/** 이름 Label 의 text 를 수정할 수 있도록 CellEditor 관리 */
	protected DirectEditManager manager;
	/** Figure 간 Connection 경로를 계산할 Router */
	private UniShortestConnectionRouter connectionRetuter;

	/**
	 * ConnectorElement 모델의 참조 모델(ReferElement) 대한 EditPart 를 생성한다.
	 * @param router 경로 계산 Router
	 */
	public BConnectorElementEditPart(UniShortestConnectionRouter router) {
		super();
		connectionRetuter = router;
//		m_corner = new Dimension(10, 10);
	}
	
	public BConnectorElementEditPart() {
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
		if(realItem instanceof ConnectorElement) {
			StructBlockElement consBlock = ((ConnectorElement)realItem).getConstruct();
			if(consBlock != null) {
				consBlock.eAdapters().add(this);
			}
			StructBlockElement desBlock = ((ConnectorElement)realItem).getDestruct();
			if(desBlock != null) {
				desBlock.eAdapters().add(this);
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
		if(realItem instanceof ConnectorElement) {
			StructBlockElement consBlock = ((ConnectorElement)realItem).getConstruct();
			if(consBlock != null) {
				consBlock.eAdapters().remove(this);
			}
			StructBlockElement desBlock = ((ConnectorElement)realItem).getDestruct();
			if(desBlock != null) {
				desBlock.eAdapters().remove(this);
			}
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
	 * @return ActionElement 모델
	 */
	public ConnectorElement getRealModel() {
		ReferElement refItem = getCastedModel();
		return (ConnectorElement)refItem.getRealModel();
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
	 * child Figure 가 그려질 Container Figure 를 반환한다.
	 * @param child Figure 가 그려질 Container Figure
	 */
	public IFigure getContentPane() {
		ConnectorElementFigure figure = (ConnectorElementFigure)getFigure();
		return figure.getContentPane();
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
		// handles constraint changes (e.g. moving and/or resizing) of model elements
		// and creation of new model elements
		installEditPolicy(EditPolicy.LAYOUT_ROLE,  new BConnectorElementXYLayoutEditPolicy());
		// allow the creation of connections and
		// and the reconnection of connections between ShapeModel instances
		installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE, new BGraphicalNodeEditPolicy2() {
			@Override
			protected Command getConnectionCreateCommand(
					CreateConnectionRequest request) {
				return null;
			}

			@Override
			protected Command getReconnectSourceCommand(ReconnectRequest request) {
				return null;
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

		ConnectorElement connector = this.getRealModel();

		ConnectorElementFigure figure;
		DiagramConfiguration dgmCfg = DiagramConfiguration.getInstance();

		figure = new ConnectorElementFigure(this);
		
		connectionRetuter.addContainer(figure.getContentPane());	// KJH 20110209,

		figure.setFont(dgmCfg.getItemFont(TaskModelPackage.CONNECTOR_ELEMENT, DiagramConfiguration.VALID));

		figure.setForegroundColor(dgmCfg.getItemFGColor(TaskModelPackage.CONNECTOR_ELEMENT, DiagramConfiguration.VALID));
		figure.setBackgroundColor(dgmCfg.getItemBGColor(TaskModelPackage.CONNECTOR_ELEMENT, DiagramConfiguration.VALID));
	
		figure.setMinimumSize(new Dimension(100, 50));
		figure.setOpaque(true); // non-transparent figure
		figure.setLineWidth(2);

		String name = connector.getName();
		if(name == null|| name.isEmpty()) {
			connector.setName("New_Connector");
		}
		figure.setName(connector.getName());
		
		figure.setVisible(getCastedModel().isVisible());

		return figure;
	}

	/**
	 * 모델의 자식 모델들을 반환한다.
	 * @see org.eclipse.gef.editparts.AbstractEditPart#getModelChildren()
	 * 
	 * @Override
	 */
	protected List getModelChildren() {
		// KJH 20110209 s, fix
		ReferElement refItem = getCastedModel();
		ConnectorElement connector = getRealModel();
		List list = new ArrayList();
		
		list.addAll(refItem.getItems());
		
		StructBlockElement struct;
		struct = connector.getConstruct();
		if (struct != null) {
			list.add(struct);
		}
		
		struct = connector.getDestruct();
		if (struct != null) {
			list.add(struct);
		}

		return list;
	}

	/**
	 * Parameter 로 주어진 클래스(또는 인터페이스)에 대한 Adapter 를 제공한다.
	 * @param key Adapter 요청 타입
	 * @return Parameter 로 주어진 클래스(또는 인터페이스)에 대한 Adapter
	 */
	public Object getAdapter(Class key) {
		if (IPropertySource.class == key) {
			Object model = getRealModel();
			if(model instanceof ConnectorElement) {
				return new ConnectorElementPropertySource((ConnectorElement)model);
			}
		}
		else if (IPropertySourceProvider.class == key) {
			final Object model = getRealModel();
			if(model instanceof ConnectorElement) {
				return new IPropertySourceProvider() {
					public IPropertySource getPropertySource(Object object) {
						return new ConnectorElementPropertySource((ConnectorElement)model);
					}
				};
			}
		}

		return super.getAdapter(key);
	}

	/**
	 * @Override
	 * @author KJH 20110421
	 */
	protected void addChildVisual(EditPart childEditPart, int index) {
		IFigure child = ((GraphicalEditPart)childEditPart).getFigure();
		if (childEditPart instanceof BStructBlockEditPart) {
			getFigure().add(child);	// construct, destruct가 가장 상위에 오도록 하기 위해
		} else {
			IFigure contentPane = getContentPane();
			index = contentPane.getChildren().size();
			contentPane.add(child, index);
		}
	}

	/**
	 * @Override
	 * @author KJH 20110425
	 */
	protected void removeChildVisual(EditPart childEditPart) {
		IFigure child = ((GraphicalEditPart)childEditPart).getFigure();
		if (childEditPart instanceof BStructBlockEditPart) {
			getFigure().remove(child);
		} else {
			getContentPane().remove(child);
		}
	}

	/**
	 * 모델의 속성 값을 다시 화면에 다시 표시한다.
	 * 
	 * @Override
	 */
	protected void refreshVisuals() {
		ReferElement refItem = getCastedModel();
		ConnectorElement model = getRealModel();
		ConnectorElementFigure figure = (ConnectorElementFigure)getFigure();
		
		int itemState = model.getItemState();
		if(model.isIncluded()) {
			itemState |= ItemState.ITEM_INCLUDED;
		}
		figure.setModelState(itemState);

		figure.updateFigure();
		
		figure.setName(model.getName());
		
		Rectangle bounds = new Rectangle(
				refItem.getX(),
				refItem.getY(),
				refItem.getWidth(),
				refItem.getHeight());
		((GraphicalEditPart)getParent()).setLayoutConstraint(this, figure, bounds);
		
		figure.setVisible(getCastedModel().isVisible());
		figure.repaint();
	}

	/**
	 * 참조 모델의 Property 가 변경되면 호출된다.
	 * @param notification 모델의 변경 정보
	 * 
	 * @Override
	 */
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

		switch (type) {
		case Notification.SET:
			switch (featureId) {
			case TaskModelPackage.CONNECTOR_ELEMENT__CONSTRUCT:
				logger.debug("SET : CONNECTOR_ELEMENT__CONSTRUCT");
				this.refreshChildren();
				break;
			case TaskModelPackage.CONNECTOR_ELEMENT__DESTRUCT:
				logger.debug("SET : CONNECTOR_ELEMENT__DESTRUCT");
				this.refreshChildren();
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
			case TaskModelPackage.CONNECTOR_ELEMENT__WITHS:
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
	 * child 모델 중 StructBlockElement 모델의 Property 가 변경되면 호출된다.
	 * @param notification 모델의 변경 정보
	 */
	protected void structBlockNotifyChanged(Notification notification){
		int type = notification.getEventType();
		int featureId = notification.getFeatureID(TaskModelPackage.class);

		switch(type) {
		case Notification.SET:
			switch(featureId) {
			case TaskModelPackage.STRUCT_BLOCK_ELEMENT__STATEMENTS:
				StructBlockElement structBlock = (StructBlockElement)notification.getNotifier();
				List<String> strList = structBlock.getStatements();
				StringBuilder sb = new StringBuilder();
				for(String str : strList) {
					sb.append(str).append("\r\n");
				}

				try {
					MarkerLogger logger = getLogger();
					logger.clearProblem();

					ParserUtil parser = new ParserUtil();
					parser.checkStructBlock(getRootModel(), sb.toString(), logger);
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
			case TaskModelPackage.STRUCT_BLOCK_ELEMENT__STATEMENTS:
				StructBlockElement structBlock = (StructBlockElement)notification.getNotifier();
				List<String> strList = structBlock.getStatements();
				StringBuilder sb = new StringBuilder();
				for(String str : strList) {
					sb.append(str).append("\r\n");
				}

				try {
					MarkerLogger logger = getLogger();
					logger.clearProblem();

					ParserUtil parser = new ParserUtil();
					parser.checkStructBlock(getRootModel(), sb.toString(), logger);
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
			ReferElement refItem = getCastedModel();
			ConnectorElement connector = getRealModel();
			
			if (refItem.getAttribute() == ReferAttribute.EXTERNAL) {
				return;
			}
			if (request instanceof LocationRequest &&
					directEditHitTest(((LocationRequest)request).getLocation().getCopy())) {
				performDirectEdit();
			}
		}
		else if (request.getType() == RequestConstants.REQ_OPEN) {
			ReferElement refItem = getCastedModel();
			ConnectorElement connector = getRealModel();
			
			if (refItem.getAttribute() == ReferAttribute.EXTERNAL) {
				return;
			}
			
			if (connector.isIncluded()) {
				return;
			}
			
			if (request instanceof LocationRequest &&
					directEditHitTest(((LocationRequest)request).getLocation().getCopy())) {
				performDirectEdit();
			}
		}
	}

	/**
	 * Request 요청이 이름 Label 영역인가를 확인한다.
	 * @param requestLoc Request 의 좌표
	 * @return Name Label 영역 포함 여부
	 */
	private boolean directEditHitTest(Point requestLoc) {
		Label label = ((ConnectorElementFigure)getFigure()).getLabelFigure();
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
			Label label = ((ConnectorElementFigure)getFigure()).getLabelFigure();
			manager =
				new LabelDirectEditManager(
					this,
					TextCellEditor.class,
					new LabelCellEditorLocator(label), label);
		}
		manager.show();
	}

	/**
	 * child 모델의 Figure 선택이 변경되면 호출된다.
	 * @param event Figure 선택 변경 Event 
	 */
	public void selectionChanged(SelectionChangedEvent event) {
		super.selectionChanged(event);
	}

	@Override
	public boolean isSelectable() {	// KJH 20110614
		return getCastedModel().isVisible();
	}
	
}
