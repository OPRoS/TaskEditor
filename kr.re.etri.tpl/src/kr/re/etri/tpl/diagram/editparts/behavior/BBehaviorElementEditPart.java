
package kr.re.etri.tpl.diagram.editparts.behavior;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import kr.re.etri.tpl.diagram.DiagramConfiguration;
import kr.re.etri.tpl.diagram.UniShortestConnectionRouter;
import kr.re.etri.tpl.diagram.celledit.LabelCellEditorLocator;
import kr.re.etri.tpl.diagram.celledit.LabelDirectEditManager;
import kr.re.etri.tpl.diagram.editparts.behavior.policies.BBehaviorElementXYLayoutEditPolicy;
import kr.re.etri.tpl.diagram.editparts.behavior.policies.BComponentEditPolicy;
import kr.re.etri.tpl.diagram.editparts.behavior.policies.BGraphicalNodeEditPolicy2;
import kr.re.etri.tpl.diagram.editparts.behavior.policies.BLabelDirectEditPolicy;
import kr.re.etri.tpl.diagram.figures.BehaviorElementFigure;
import kr.re.etri.tpl.diagram.properties.sources.BehaviorElementPropertySource;
import kr.re.etri.tpl.diagram.util.ItemState;
import kr.re.etri.tpl.script.editors.TPLScriptEditor;
import kr.re.etri.tpl.script.grammar.tree2.IScriptNode;
import kr.re.etri.tpl.script.grammar.tree2.IScriptRootNode;
import kr.re.etri.tpl.script.utils.SDConverter;
import kr.re.etri.tpl.taskmodel.BehaviorElement;
import kr.re.etri.tpl.taskmodel.ConnectionElement;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.ModelDiagram;
import kr.re.etri.tpl.taskmodel.ReferAttribute;
import kr.re.etri.tpl.taskmodel.ReferElement;
import kr.re.etri.tpl.taskmodel.StructBlockElement;
import kr.re.etri.tpl.taskmodel.TaskModelPackage;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
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
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.IPropertySourceProvider;


/**
 * TaskElement 모델과 Figure 을 control 하는 EditPart 클래스이다.
 * 
 * @see BItemElementEditPart
 * 
 * @author sfline
 */
public class BBehaviorElementEditPart extends BLinkedElementEditPart {
	private static Logger logger =
		 Logger.getLogger(BBehaviorElementEditPart.class.getName());
	/** 이름 Label 의 text 를 수정할 수 있도록 CellEditor 관리 */
	protected DirectEditManager manager;
	/** Figure 의 corner */
	protected Dimension corner;
	/** Figure 간 Connection 경로를 계산할 Router */
	private UniShortestConnectionRouter connectionRetuter;
	
	/**
	 * TaskElement 모델의 참조 모델(ReferElement) 대한 EditPart 를 생성한다.
	 * @param router 경로 계산 Router
	 */
	public BBehaviorElementEditPart(UniShortestConnectionRouter router) {
		super();
		corner = new Dimension(12, 12);
		connectionRetuter = router;
	}
	
	public BBehaviorElementEditPart() {
		super();

		corner = new Dimension(12, 12);
//		connectionRetuter = router;
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
		if(realItem instanceof BehaviorElement) {
			StructBlockElement consBlock = ((BehaviorElement)realItem).getConstruct();
			if(consBlock != null) {
				consBlock.eAdapters().add(this);
			}
			StructBlockElement desBlock = ((BehaviorElement)realItem).getDestruct();
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
		if(realItem instanceof BehaviorElement) {
			StructBlockElement consBlock = ((BehaviorElement)realItem).getConstruct();
			if(consBlock != null) {
				consBlock.eAdapters().remove(this);
			}
			StructBlockElement desBlock = ((BehaviorElement)realItem).getDestruct();
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
	public BehaviorElement getRealModel() {
		ReferElement refItem = getCastedModel();
		return (BehaviorElement)refItem.getRealModel();
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
		BehaviorElementFigure figure = (BehaviorElementFigure)getFigure();
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
//		installEditPolicy(EditPolicy.CONTAINER_ROLE, new TDgmContainerEditPolicy());
		installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE, new BLabelDirectEditPolicy());
		// handles constraint changes (e.g. moving and/or resizing) of model elements
		// and creation of new model elements
		installEditPolicy(EditPolicy.LAYOUT_ROLE,  new BBehaviorElementXYLayoutEditPolicy(this));
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

		BehaviorElement task = this.getRealModel();
		int itemState = task.getItemState();
		if(task.isIncluded()) {
			itemState |= ItemState.ITEM_INCLUDED;
		}
		
		BehaviorElementFigure figure;
		DiagramConfiguration dgmCfg = DiagramConfiguration.getInstance();

		figure = new BehaviorElementFigure(this);
		
		connectionRetuter.addContainer(figure.getContentPane());

		figure.setFont(dgmCfg.getItemFont(TaskModelPackage.TASK_ELEMENT, itemState));

		figure.setForegroundColor(dgmCfg.getItemFGColor(TaskModelPackage.TASK_ELEMENT, itemState));
		figure.setBackgroundColor(dgmCfg.getItemBGColor(TaskModelPackage.TASK_ELEMENT, itemState));
		figure.setMinimumSize(new Dimension(100, 50));
		figure.setOpaque(true); // non-transparent figure
		figure.setLineWidth(2);

		String name = task.getName();
		if(name == null|| name.isEmpty()) {
			task.setName("New_Behavior");
		}
		figure.setName(task.getName());
		
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
		ReferElement refItem = getCastedModel();
		BehaviorElement behavior = getRealModel();
		List<ItemElement> list = new ArrayList<ItemElement>();
		
		// KJH 20110419 s, construct & destruct add
		ItemElement struct = behavior.getConstruct();
		if (struct != null) {
			list.add(struct);
		}

		struct = behavior.getDestruct();
		if (struct != null) {
			list.add(struct);
		}// KJH 20110419 e, construct & destruct add
		
		list.addAll(refItem.getItems());
		
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
		BehaviorElement task = getRealModel();

		BehaviorElementFigure figure = (BehaviorElementFigure)getFigure();

		int itemState = task.getItemState();
		if(task.isIncluded()) {
			itemState |= ItemState.ITEM_INCLUDED;
		}
		figure.setModelState(itemState);

		figure.updateFigure();
		
		figure.setName(task.getName());

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
			case TaskModelPackage.TASK_ELEMENT__NAME:
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
		case Notification.ADD:
		case Notification.ADD_MANY:
		case Notification.REMOVE:
		case Notification.REMOVE_MANY:
			switch(featureId) {
			case TaskModelPackage.TASK_ELEMENT__STATES:
				refreshChildren();
				break;
			case TaskModelPackage.TASK_ELEMENT__BIFURCATES:
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
			BehaviorElement task = getRealModel();
			
			// KJH 20101201 s,
			// KJH 20101018, external은 이름변경 불가
			if (refItem.getAttribute() == ReferAttribute.EXTERNAL) {
				return;
			}
			
			if (request instanceof LocationRequest &&
					directEditHitTest(((LocationRequest)request).getLocation().getCopy())) {
				performDirectEdit();
			}
			// KJH 20101201 e,
		}
		else if (request.getType() == RequestConstants.REQ_OPEN) {
			ReferElement refItem = getCastedModel();
			BehaviorElement behavior = getRealModel();
			
			// KJH 20100906, real model삭제되었을 때 해당 에디터 열리지 않게 하기
			if (behavior.getParent() instanceof ModelDiagram) {
				ModelDiagram diagram = (ModelDiagram)behavior.getParent();
				if (!diagram.getItems().contains(behavior)) {
					System.out.println("real model is removed");
					return;
				}
			}

			// KJH 20100827 s, 참조 behavior 더블클릭시 해당 에디터 오픈 
			if (refItem.getAttribute() == ReferAttribute.EXTERNAL) {
				// KJH 20101007, script editor open
				IScriptNode scriptNode = SDConverter.convertToScript(behavior);
				IScriptNode parentNode = scriptNode.getParent();
				while (parentNode != null && !(parentNode instanceof IScriptRootNode)) {
					parentNode = parentNode.getParent();
				}
				
				if (parentNode != null) {
					IWorkspaceRoot wsRoot = ResourcesPlugin.getWorkspace().getRoot();
					IResource resource = wsRoot.findMember(parentNode.getName());
					IWorkbenchPage wp = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
					if (resource instanceof IFile) {
						try {
							IEditorPart editor = IDE.openEditor(wp, (IFile)resource, true);
							if (editor instanceof TPLScriptEditor) {
								((TPLScriptEditor)editor).selectScript(behavior);
							}
						} catch (PartInitException e) {
							e.printStackTrace();
						}
					}
				}
			}
			else {
				if(behavior.isIncluded()) {
					return;
				}
				// KJH 20101201 s,
				if (request instanceof LocationRequest &&
					directEditHitTest(((LocationRequest)request).getLocation().getCopy())) {
					performDirectEdit();
				}
				// KJH 20101201 e,
			}
		}
	}

	/**
	 * Request 요청이 이름 Label 영역인가를 확인한다.
	 * @param requestLoc Request 의 좌표
	 * @return Name Label 영역 포함 여부
	 */
	private boolean directEditHitTest(Point requestLoc) {
		Label label = ((BehaviorElementFigure)getFigure()).getLabelFigure();
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
			Label label = ((BehaviorElementFigure)getFigure()).getLabelFigure();
			manager =
				new LabelDirectEditManager(
					this,
					TextCellEditor.class,
					new LabelCellEditorLocator(label), label);
		}
		manager.show();
	}
	
	@Override
	public void selectionChanged(SelectionChangedEvent event) {
		super.selectionChanged(event);

//		ISelection sel = event.getSelection();
//		if(sel instanceof StructuredSelection) {
//			if(((StructuredSelection)sel).getFirstElement() != this) {
//				return;
//			}
//			
//			EditPartViewer viewer = getViewer();
//			
//			// add the ShortestPathConnectionRouter
//			ScalableFreeformRootEditPart root = (ScalableFreeformRootEditPart)viewer.getRootEditPart();
//			ConnectionLayer connLayer = (ConnectionLayer)root.getLayer(LayerConstants.CONNECTION_LAYER);
//
////			BendpointConnectionRouter bendpointRouter = 
////				new BendpointConnectionRouter();
////			connLayer.setConnectionRouter(bendpointRouter);
//
//			ShortestPathConnectionRouter shortestPathRouter = 
//				new ShortestPathConnectionRouter(this.getFigure());
//			connLayer.setConnectionRouter(shortestPathRouter);
//		}
	}
	
	@Override
	public boolean isSelectable() {	// KJH 20110614
		return getCastedModel().isVisible();
	}

}