
package kr.re.etri.tpl.diagram.editparts.behavior;

import java.util.ArrayList;
import java.util.List;

import kr.re.etri.tpl.diagram.DiagramConfiguration;
import kr.re.etri.tpl.diagram.UniShortestConnectionRouter;
import kr.re.etri.tpl.diagram.celledit.LabelCellEditorLocator;
import kr.re.etri.tpl.diagram.celledit.LabelDirectEditManager;
import kr.re.etri.tpl.diagram.editparts.behavior.policies.BComponentEditPolicy;
import kr.re.etri.tpl.diagram.editparts.behavior.policies.BGraphicalNodeEditPolicy2;
import kr.re.etri.tpl.diagram.editparts.behavior.policies.BLabelDirectEditPolicy;
import kr.re.etri.tpl.diagram.editparts.behavior.policies.BTaskElementXYLayoutEditPolicy;
import kr.re.etri.tpl.diagram.figures.TaskElementFigure;
import kr.re.etri.tpl.diagram.properties.sources.TaskElementPropertySource;
import kr.re.etri.tpl.diagram.util.ItemState;
import kr.re.etri.tpl.taskmodel.ConnectionElement;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.ReferAttribute;
import kr.re.etri.tpl.taskmodel.ReferElement;
import kr.re.etri.tpl.taskmodel.StructBlockElement;
import kr.re.etri.tpl.taskmodel.TaskElement;
import kr.re.etri.tpl.taskmodel.TaskModelPackage;

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
import org.eclipse.gef.requests.DirectEditRequest;
import org.eclipse.gef.requests.LocationRequest;
import org.eclipse.gef.requests.ReconnectRequest;
import org.eclipse.gef.tools.DirectEditManager;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.IPropertySourceProvider;

/**
 * WorkerElement 모델과 Figure 을 control 하는 EditPart 클래스이다.
 * 
 * @see BItemElementEditPart
 * 
 * @author sfline
 */
public class BTaskElementEditPart extends BLinkedElementEditPart {
	private static Logger logger = Logger.getLogger(BTaskElementEditPart.class);
	/** 이름 Label 의 text 를 수정할 수 있도록 CellEditor 관리 */
	protected DirectEditManager manager;
	/** Figure 간 Connection 경로를 계산할 Router */
	private UniShortestConnectionRouter connectionRetuter;

	/**
	 * WorkerElement 모델의 참조 모델(ReferElement) 대한 EditPart 를 생성한다.
	 */
	public BTaskElementEditPart(UniShortestConnectionRouter router) {
		super();
		connectionRetuter = router;
	}
	
	public BTaskElementEditPart() {
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
		if(realItem instanceof TaskElement) {
			StructBlockElement consBlock = ((TaskElement)realItem).getInitialize();
			if(consBlock != null) {
				consBlock.eAdapters().add(this);
			}
			StructBlockElement desBlock = ((TaskElement)realItem).getFinalize();
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
		if(realItem instanceof TaskElement) {
			StructBlockElement consBlock = ((TaskElement)realItem).getInitialize();
			if(consBlock != null) {
				consBlock.eAdapters().remove(this);
			}
			StructBlockElement desBlock = ((TaskElement)realItem).getFinalize();
			if(desBlock != null) {
				desBlock.eAdapters().remove(this);
			}
		}
	}

	/**
	 * LinkedElement 타입의 모델을 EditPart 의 모델 타입으로 캐스팅한다.
	 * @return WorkerElement 모델의 참조 모델(ReferElement)
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
	public TaskElement getRealModel() {
		ReferElement refItem = getCastedModel();
		return (TaskElement)refItem.getRealModel();
	}

	/**
	 * child Figure 가 그려질 Container Figure 를 반환한다.
	 * @param child Figure 가 그려질 Container Figure
	 */
	public IFigure getContentPane() {
		TaskElementFigure figure = (TaskElementFigure)getFigure();
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
		// KJH 20110421, add EditPolicy
		installEditPolicy(EditPolicy.LAYOUT_ROLE,  new BTaskElementXYLayoutEditPolicy());
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

		DiagramConfiguration dgmCfg = DiagramConfiguration.getInstance();

		TaskElementFigure figure = new TaskElementFigure(this);

		connectionRetuter.addContainer(figure.getContentPane());
		
		figure.setFont(dgmCfg.getItemFont(TaskModelPackage.WORKER_ELEMENT, DiagramConfiguration.VALID));

		figure.setForegroundColor(dgmCfg.getItemFGColor(TaskModelPackage.WORKER_ELEMENT, DiagramConfiguration.VALID));
		figure.setBackgroundColor(dgmCfg.getItemBGColor(TaskModelPackage.WORKER_ELEMENT, DiagramConfiguration.VALID));
		figure.setMinimumSize(new Dimension(100, 48));
		figure.setOpaque(true); // non-transparent figure
		figure.setLineWidth(2);

		TaskElement worker = getRealModel();
		String name = worker.getName();
		if(name == null|| name.isEmpty()) {
			worker.setName("NewTask");
		}
		figure.setName(worker.getName());
		
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
		// KJH 20110418 s, model children
		ReferElement refItem = getCastedModel();
		TaskElement model = getRealModel();
		List list = new ArrayList();
		
		list.addAll(refItem.getItems());
	
		StructBlockElement struct = model.getInitialize();
		if (struct != null) {
			list.add(struct);
		}
		
		struct = model.getRun();
		if (struct != null) {
			list.add(struct);	// KJH 20110624, run 추가
		}
		
		struct = model.getFinalize();
		if (struct != null) {
			list.add(struct);
		}
		// KJH 20110418 s, model children
		
		return list;
//		return Collections.EMPTY_LIST;
	}

	/**
	 * Parameter 로 주어진 클래스(또는 인터페이스)에 대한 Adapter 를 제공한다.
	 * @param key Adapter 요청 타입
	 * @return Parameter 로 주어진 클래스(또는 인터페이스)에 대한 Adapter
	 */
	public Object getAdapter(Class key) {
		if (IPropertySource.class == key) {
			Object model = getRealModel();
			if(model instanceof TaskElement) {
				return new TaskElementPropertySource((TaskElement)model);
			}
		}
		else if (IPropertySourceProvider.class == key) {
			final Object model = getRealModel();
			if(model instanceof TaskElement) {
				return new IPropertySourceProvider() {
					public IPropertySource getPropertySource(Object object) {
						return new TaskElementPropertySource((TaskElement)model);		
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
		TaskElement model = getRealModel();
		ReferElement refItem = getCastedModel();
		TaskElementFigure figure = (TaskElementFigure)getFigure();

		int itemState = model.getItemState();
		if(model.isIncluded()) {
			itemState |= ItemState.ITEM_INCLUDED;
		}
		figure.setModelState(itemState);

		figure.updateFigure();	// KJH 20110816, 펼침 시 scrollpane의 visiable가 업데이트 되도록 
		
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

//		if(figure instanceof RoundedRectangle) {
//			int cx = ((RoundRectanguleShape)linkedShape).getCx();
//			int cy = ((RoundRectanguleShape)linkedShape).getCy();
//			((RoundedRectangle)figure).setCornerDimensions(new Dimension(cx, cy));
//			((RoundedRectangle)figure).repaint();
//		}
		
		figure.setVisible(getCastedModel().isVisible());
		figure.repaint();
	}
	
	/**
	 * 모델의 Property 가 변경되면 호출된다.
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
			case TaskModelPackage.WORKER_ELEMENT__INITIAL_TASK:
				break;
			case TaskModelPackage.WORKER_ELEMENT__INITIALIZE:
				logger.debug("SET : WORKER_ELEMENT__INITIALIZE");
				this.refreshChildren();
				break;
			case TaskModelPackage.WORKER_ELEMENT__FINALIZE:
				logger.debug("SET : WORKER_ELEMENT__FINALIZE");
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
			case TaskModelPackage.WORKER_ELEMENT__ITEMS:
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
	 * 주어진 Request 를 수행한다. 이 method 는 EditPart 에 일반적인
	 * 메시지를 보내기 위해 사용된다.
	 * 
	 * @see org.eclipse.gef.EditPart#performRequest(org.eclipse.gef.Request)
	 */
	public void performRequest(Request request) {
		if (request.getType() == RequestConstants.REQ_DIRECT_EDIT) {
			if (request instanceof DirectEditRequest
				&& !directEditHitTest(((DirectEditRequest) request)
					.getLocation()
					.getCopy()))
				return;
			performDirectEdit();
		}
		else if (request.getType() == RequestConstants.REQ_OPEN) {
			ReferElement refItem = getCastedModel();
			TaskElement worker = getRealModel();
			
//			SelectionRequest selRequest = (SelectionRequest)request;
//
//			DefaultEditDomain editDomain = (DefaultEditDomain)this.getViewer().getEditDomain();
//			IEditorPart editPart = editDomain.getEditorPart();
//			if(editPart instanceof TPLDiagramEditor) {
//				TPLDiagramEditor rtmEditor = (TPLDiagramEditor)editPart;
//				// KJH 20110816 s, Task 더블 클릭시 에디터 열림
//				try {
//					CommandStack cmdStack = editDomain.getCommandStack();
//					rtmEditor.addWorkerDiagramPage(worker, cmdStack);
//				} catch (PartInitException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				// KJH 20110816 e, Task 더블 클릭시 에디터 열림
//			}
			
			// KJH 20110829 s, 더블클릭시 이름변경
			if (refItem.getAttribute() == ReferAttribute.EXTERNAL) {
				return;
			}
			
			if (worker.isIncluded()) {
				return;
			}
		
			if (request instanceof LocationRequest
					&& directEditHitTest(((LocationRequest) request).getLocation().getCopy())) {
				performDirectEdit();
			}	// KJH 20110829 e, 더블클릭시 이름변경
		}
	}

	/**
	 * Request 요청이 이름 Label 영역인가를 확인한다.
	 * @param requestLoc Request 의 좌표
	 * @return Name Label 영역 포함 여부
	 */
	private boolean directEditHitTest(Point requestLoc) {
		Label label = ((TaskElementFigure)getFigure()).getLabelFigure();
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
			Label label = ((TaskElementFigure)getFigure()).getLabelFigure();
			manager =
				new LabelDirectEditManager(
					this,
					TextCellEditor.class,
					new LabelCellEditorLocator(label), label);
		}
		manager.show();
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