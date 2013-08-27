
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
 * TaskElement �𵨰� Figure �� control �ϴ� EditPart Ŭ�����̴�.
 * 
 * @see BItemElementEditPart
 * 
 * @author sfline
 */
public class BBehaviorElementEditPart extends BLinkedElementEditPart {
	private static Logger logger =
		 Logger.getLogger(BBehaviorElementEditPart.class.getName());
	/** �̸� Label �� text �� ������ �� �ֵ��� CellEditor ���� */
	protected DirectEditManager manager;
	/** Figure �� corner */
	protected Dimension corner;
	/** Figure �� Connection ��θ� ����� Router */
	private UniShortestConnectionRouter connectionRetuter;
	
	/**
	 * TaskElement ���� ���� ��(ReferElement) ���� EditPart �� �����Ѵ�.
	 * @param router ��� ��� Router
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
	 * EditPart �� Ȱ��ȭ�Ǹ� �𵨷� ���� Notify �� �޵��� �Ѵ�.
	 * ���� child �� �Ǵ� Connection �� �Ӽ��� ����Ǿ Notify �� �޵���
	 * �Ѵ�.
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
	 * EditPart �� ��Ȱ��ȭ�Ǹ� �𵨷� ���� Notify �� ���� �ʵ��� �Ѵ�.
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
	 * �𵨿� ���� Figure �� �����Ͽ� �����Ѵ�.
	 * @return �𵨿� ���� Figure
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
	 * ���� �ڽ� �𵨵��� ��ȯ�Ѵ�.
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
	 * @Override
	 * @author KJH 20110421
	 */
	protected void addChildVisual(EditPart childEditPart, int index) {
		IFigure child = ((GraphicalEditPart)childEditPart).getFigure();
		if (childEditPart instanceof BStructBlockEditPart) {
			getFigure().add(child);	// construct, destruct�� ���� ������ ������ �ϱ� ����
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
	 * ���� ���� Property �� ����Ǹ� ȣ��ȴ�.
	 * @param notification ���� ���� ����
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
			ReferElement refItem = getCastedModel();
			BehaviorElement task = getRealModel();
			
			// KJH 20101201 s,
			// KJH 20101018, external�� �̸����� �Ұ�
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
			
			// KJH 20100906, real model�����Ǿ��� �� �ش� ������ ������ �ʰ� �ϱ�
			if (behavior.getParent() instanceof ModelDiagram) {
				ModelDiagram diagram = (ModelDiagram)behavior.getParent();
				if (!diagram.getItems().contains(behavior)) {
					System.out.println("real model is removed");
					return;
				}
			}

			// KJH 20100827 s, ���� behavior ����Ŭ���� �ش� ������ ���� 
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