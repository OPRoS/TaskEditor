
package kr.re.etri.tpl.diagram.editparts.task;

import java.util.List;

import kr.re.etri.tpl.diagram.DiagramConfiguration;
import kr.re.etri.tpl.diagram.celledit.LabelCellEditorLocator;
import kr.re.etri.tpl.diagram.celledit.LabelDirectEditManager;
import kr.re.etri.tpl.diagram.commands.ConnectionCreateCommand;
import kr.re.etri.tpl.diagram.commands.ConnectionReconnectCommand;
import kr.re.etri.tpl.diagram.editors.TPLDiagramEditor;
import kr.re.etri.tpl.diagram.editparts.behavior.BItemElementEditPart;
import kr.re.etri.tpl.diagram.editparts.task.policies.TComponentEditPolicy;
import kr.re.etri.tpl.diagram.editparts.task.policies.TLabelDirectEditPolicy;
import kr.re.etri.tpl.diagram.figures.TaskElementFigure;
import kr.re.etri.tpl.diagram.properties.sources.TaskElementPropertySource;
import kr.re.etri.tpl.taskmodel.ConnectionElement;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.LineEndPoint;
import kr.re.etri.tpl.taskmodel.ReferElement;
import kr.re.etri.tpl.taskmodel.TaskModelPackage;
import kr.re.etri.tpl.taskmodel.TaskElement;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gef.requests.DirectEditRequest;
import org.eclipse.gef.requests.ReconnectRequest;
import org.eclipse.gef.requests.SelectionRequest;
import org.eclipse.gef.tools.DirectEditManager;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.IPropertySourceProvider;

/**
 * WorkerElement 모델과 Figure 을 control 하는 EditPart 클래스이다.
 * 
 * @see BItemElementEditPart
 * 
 * @author sfline
 */
public class TTaskElementEditPart extends TLinkedElementEditPart {
	/** 이름 Label 의 text 를 수정할 수 있도록 CellEditor 관리 */
	protected DirectEditManager manager;

	/**
	 * WorkerElement 모델의 참조 모델(ReferElement) 대한 EditPart 를 생성한다.
	 */
	public TTaskElementEditPart() {
		super();
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
		// and the reconnection of connections between ShapeModel instances
		installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE, new GraphicalNodeEditPolicy() {
			/* (non-Javadoc)
			 * @see org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy#getConnectionCompleteCommand(org.eclipse.gef.requests.CreateConnectionRequest)
			 */
			protected Command getConnectionCompleteCommand(CreateConnectionRequest request) {
				ConnectionCreateCommand cmd;
				cmd = (ConnectionCreateCommand) request.getStartCommand();
				cmd.setTarget((ReferElement) getHost().getModel());
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

				conn.setSourceEndPoint(LineEndPoint.NONE);
				ConnectionReconnectCommand cmd = new ConnectionReconnectCommand(conn);
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
				ConnectionReconnectCommand cmd = new ConnectionReconnectCommand(conn);
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

		IFigure figure;
		if (getModel() instanceof TaskElement) {
			DiagramConfiguration dgmCfg = DiagramConfiguration.getInstance();

			figure = new RectangleFigure();

			FontData []fd = new FontData[]{new FontData("Candara", 10, SWT.NORMAL)};
			Font font = new Font(null, fd);
			figure.setFont(font);

			figure.setForegroundColor(dgmCfg.getItemFGColor(TaskModelPackage.WORKER_ELEMENT, DiagramConfiguration.VALID));
			figure.setBackgroundColor(dgmCfg.getItemBGColor(TaskModelPackage.WORKER_ELEMENT, DiagramConfiguration.VALID));
			figure.setOpaque(true); // non-transparent figure

			TaskElement worker = (TaskElement)getModel();
			String name = worker.getName();
			if(name == null|| name.isEmpty()) {
				worker.setName("NewTask");
			}

			return figure;
		}
		
		figure = super.createFigureForModel();
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
		List list = refItem.getItems();
		
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
			Object model = getModel();
			if(model instanceof TaskElement) {
				return new TaskElementPropertySource((TaskElement)model);
			}
		}
		else if (IPropertySourceProvider.class == key) {
			final Object model = getModel();
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
	 * 모델의 속성 값을 다시 화면에 다시 표시한다.
	 * 
	 * @Override
	 */
	protected void refreshVisuals() {
		TaskElement model = getRealModel();
		ReferElement refItem = getCastedModel();
		IFigure figure = getFigure();

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
		case Notification.SET:
//			switch(featureId) {
//			case TaskModelPackage.SHAPE_ELEMENT__X:
//			case TaskModelPackage.SHAPE_ELEMENT__Y:
//			case TaskModelPackage.SHAPE_ELEMENT__WIDTH:
//			case TaskModelPackage.SHAPE_ELEMENT__HEIGHT:
//			case TaskModelPackage.SHAPE_ELEMENT__COLLAPSED:
//				refreshVisuals();
//				break;
//			}
			refreshVisuals();
			break;
		case Notification.ADD:
		case Notification.REMOVE:
			switch(featureId) {
			case TaskModelPackage.LINKED_ELEMENT__SOURCE_CONNECTIONS:
				refreshSourceConnections();
				break;
			case TaskModelPackage.LINKED_ELEMENT__TARGET_CONNECTIONS:
				refreshTargetConnections();
				break;
			case TaskModelPackage.REFER_ELEMENT__ITEMS:
				refreshChildren();
				break;
			}
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
			SelectionRequest selRequest = (SelectionRequest)request;

			DefaultEditDomain editDomain = (DefaultEditDomain)this.getViewer().getEditDomain();
			IEditorPart editPart = editDomain.getEditorPart();
//			IEditorPart editPart = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
			if(editPart instanceof TPLDiagramEditor) {
				TaskElement worker = getRealModel();
				TPLDiagramEditor rtmEditor = (TPLDiagramEditor)editPart;
				try {
					CommandStack cmdStack = editDomain.getCommandStack();
					rtmEditor.addWorkerDiagramPage(worker, cmdStack);
				} catch (PartInitException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
/*
		SelectionRequest request = (SelectionRequest)req;
		IEditorPart editPart = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();

        String editorId = TaskDiagramEditor.editorId;

		try {
			IFile file = ((FileEditorInput)editPart.getEditorInput()).getFile();
	        IWorkbench wb = PlatformUI.getWorkbench();
	        IWorkbenchWindow wbw = wb.getActiveWorkbenchWindow();
	        IWorkbenchPage wbp = wbw.getActivePage();
	        wbp.openEditor(new FileEditorInput(file), editorId, true, IWorkbenchPage.MATCH_NONE);

	        IDE.setDefaultEditor(file, editorId);
		} catch (WorkbenchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
*/
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
}