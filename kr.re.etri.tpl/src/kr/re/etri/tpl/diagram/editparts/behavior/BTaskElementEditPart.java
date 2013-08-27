
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
 * WorkerElement �𵨰� Figure �� control �ϴ� EditPart Ŭ�����̴�.
 * 
 * @see BItemElementEditPart
 * 
 * @author sfline
 */
public class BTaskElementEditPart extends BLinkedElementEditPart {
	private static Logger logger = Logger.getLogger(BTaskElementEditPart.class);
	/** �̸� Label �� text �� ������ �� �ֵ��� CellEditor ���� */
	protected DirectEditManager manager;
	/** Figure �� Connection ��θ� ����� Router */
	private UniShortestConnectionRouter connectionRetuter;

	/**
	 * WorkerElement ���� ���� ��(ReferElement) ���� EditPart �� �����Ѵ�.
	 */
	public BTaskElementEditPart(UniShortestConnectionRouter router) {
		super();
		connectionRetuter = router;
	}
	
	public BTaskElementEditPart() {
		super();
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
	 * LinkedElement Ÿ���� ���� EditPart �� �� Ÿ������ ĳ�����Ѵ�.
	 * @return WorkerElement ���� ���� ��(ReferElement)
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
	public TaskElement getRealModel() {
		ReferElement refItem = getCastedModel();
		return (TaskElement)refItem.getRealModel();
	}

	/**
	 * child Figure �� �׷��� Container Figure �� ��ȯ�Ѵ�.
	 * @param child Figure �� �׷��� Container Figure
	 */
	public IFigure getContentPane() {
		TaskElementFigure figure = (TaskElementFigure)getFigure();
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
	 * �𵨿� ���� Figure �� �����Ͽ� �����Ѵ�.
	 * @return �𵨿� ���� Figure
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
	 * ���� �ڽ� �𵨵��� ��ȯ�Ѵ�.
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
			list.add(struct);	// KJH 20110624, run �߰�
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
	 * Parameter �� �־��� Ŭ����(�Ǵ� �������̽�)�� ���� Adapter �� �����Ѵ�.
	 * @param key Adapter ��û Ÿ��
	 * @return Parameter �� �־��� Ŭ����(�Ǵ� �������̽�)�� ���� Adapter
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
		TaskElement model = getRealModel();
		ReferElement refItem = getCastedModel();
		TaskElementFigure figure = (TaskElementFigure)getFigure();

		int itemState = model.getItemState();
		if(model.isIncluded()) {
			itemState |= ItemState.ITEM_INCLUDED;
		}
		figure.setModelState(itemState);

		figure.updateFigure();	// KJH 20110816, ��ħ �� scrollpane�� visiable�� ������Ʈ �ǵ��� 
		
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
	 * ���� Property �� ����Ǹ� ȣ��ȴ�.
	 * @param notification ���� ���� ����
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
	 * �־��� Request �� �����Ѵ�. �� method �� EditPart �� �Ϲ�����
	 * �޽����� ������ ���� ���ȴ�.
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
//				// KJH 20110816 s, Task ���� Ŭ���� ������ ����
//				try {
//					CommandStack cmdStack = editDomain.getCommandStack();
//					rtmEditor.addWorkerDiagramPage(worker, cmdStack);
//				} catch (PartInitException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				// KJH 20110816 e, Task ���� Ŭ���� ������ ����
//			}
			
			// KJH 20110829 s, ����Ŭ���� �̸�����
			if (refItem.getAttribute() == ReferAttribute.EXTERNAL) {
				return;
			}
			
			if (worker.isIncluded()) {
				return;
			}
		
			if (request instanceof LocationRequest
					&& directEditHitTest(((LocationRequest) request).getLocation().getCopy())) {
				performDirectEdit();
			}	// KJH 20110829 e, ����Ŭ���� �̸�����
		}
	}

	/**
	 * Request ��û�� �̸� Label �����ΰ��� Ȯ���Ѵ�.
	 * @param requestLoc Request �� ��ǥ
	 * @return Name Label ���� ���� ����
	 */
	private boolean directEditHitTest(Point requestLoc) {
		Label label = ((TaskElementFigure)getFigure()).getLabelFigure();
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
	 * Property �� ����Ǹ� ȣ��ȴ�
	 * @param event Property ���� Event
	 * 
	 * @Override
	 */
	public void propertyChange(PropertyChangeEvent event) {
		super.propertyChange(event);
	}
	
	/**
	 * child ���� Figure ������ ����Ǹ� ȣ��ȴ�.
	 * @param event Figure ���� ���� Event 
	 */
	public void selectionChanged(SelectionChangedEvent event) {
		super.selectionChanged(event);
	}
	
	@Override
	public boolean isSelectable() {	// KJH 20110614
		return getCastedModel().isVisible();
	}
	
}