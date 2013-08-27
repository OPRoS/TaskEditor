
package kr.re.etri.tpl.diagram.editparts.behavior;

import java.util.ArrayList;
import java.util.List;

import kr.re.etri.tpl.diagram.DiagramConfiguration;
import kr.re.etri.tpl.diagram.celledit.LabelCellEditorLocator;
import kr.re.etri.tpl.diagram.celledit.LabelDirectEditManager;
import kr.re.etri.tpl.diagram.editparts.behavior.policies.BBehaviorElementXYLayoutEditPolicy;
import kr.re.etri.tpl.diagram.editparts.behavior.policies.BComponentEditPolicy;
import kr.re.etri.tpl.diagram.editparts.behavior.policies.BGraphicalNodeEditPolicy2;
import kr.re.etri.tpl.diagram.editparts.behavior.policies.BLabelDirectEditPolicy;
import kr.re.etri.tpl.diagram.figures.StateElementFigure;
import kr.re.etri.tpl.diagram.properties.sources.StateElementPropertySource;
import kr.re.etri.tpl.diagram.util.MarkerLogger;
import kr.re.etri.tpl.diagram.util.TPLErrorLogger;
import kr.re.etri.tpl.grammar.ParserUtil;
import kr.re.etri.tpl.taskmodel.ConnectionElement;
import kr.re.etri.tpl.taskmodel.ExpandTransElement;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.ReferAttribute;
import kr.re.etri.tpl.taskmodel.ReferElement;
import kr.re.etri.tpl.taskmodel.RelationShip;
import kr.re.etri.tpl.taskmodel.StateAction;
import kr.re.etri.tpl.taskmodel.StateAttribute;
import kr.re.etri.tpl.taskmodel.StateElement;
import kr.re.etri.tpl.taskmodel.TaskModelPackage;

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
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gef.requests.LocationRequest;
import org.eclipse.gef.tools.DirectEditManager;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.IPropertySourceProvider;

/**
 * StateElement �𵨰� Figure �� control �ϴ� EditPart Ŭ�����̴�.
 * 
 * @see BItemElementEditPart
 * 
 * @author sfline
 */
public class BStateElementEditPart extends BLinkedElementEditPart {
	private static Logger logger = Logger.getLogger(BStateElementEditPart.class);
	private Dimension m_corner;
	/** �̸� Label �� text �� ������ �� �ֵ��� CellEditor ���� */
	protected DirectEditManager manager;

	/**
	 * StateElement ���� ���� ��(ReferElement) ���� EditPart �� �����Ѵ�.
	 */
	public BStateElementEditPart() {
		super();
		m_corner = new Dimension(5, 5);
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
		if(realItem instanceof StateElement) {
			StateAction stayAction = ((StateElement)realItem).getStay();
			if(stayAction != null) {
				stayAction.eAdapters().add(this);
			}
			StateAction entryAction = ((StateElement)realItem).getEntry();
			if(entryAction != null) {
				entryAction.eAdapters().add(this);
			}
			StateAction exitAction = ((StateElement)realItem).getExit();
			if(exitAction != null) {
				exitAction.eAdapters().add(this);
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
		if(realItem instanceof StateElement) {
			StateAction entryAction = ((StateElement)realItem).getEntry();
			if(entryAction != null) {
				entryAction.eAdapters().remove(this);
			}
			StateAction exitAction = ((StateElement)realItem).getExit();
			if(exitAction != null) {
				exitAction.eAdapters().remove(this);
			}
			StateAction stayAction = ((StateElement)realItem).getStay();
			if(stayAction != null) {
				stayAction.eAdapters().remove(this);
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
	 * @return StateElement ��
	 */
	public StateElement getRealModel() {
		ReferElement refItem = getCastedModel();
		return (StateElement)refItem.getRealModel();
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
	 * EditPart �� ���� Policy �� �����Ѵ�.
	 * @see org.eclipse.gef.editparts.AbstractEditPart#createEditPolicies()
	 * 
	 * @Override
	 */
	protected void createEditPolicies() {
		// allow removal of the associated model element
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new BComponentEditPolicy());
		
		installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE, new BLabelDirectEditPolicy());
		
		installEditPolicy(EditPolicy.LAYOUT_ROLE,  new BBehaviorElementXYLayoutEditPolicy(this));
		
		// allow the creation of connections and
		// and the reconnection of connections between StateElement instances
		installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE, new BGraphicalNodeEditPolicy2() {

			@Override
			protected Command getConnectionCompleteCommand(
					CreateConnectionRequest request) {
				ReferElement target = (ReferElement) getHost().getModel();
				ReferElement source = (ReferElement) request.getSourceEditPart().getModel();
				ItemElement srcReal = source.getRealModel();
				if (srcReal instanceof StateElement) {
					if (source.getParent() != target.getParent())
						return null;
				}
				// KJH 20110520 s, expand&trans -> state ����, ���� behavior�� ���Ͽ�
				if (srcReal instanceof ExpandTransElement) {
					if (source.getParent().getParent() != target.getParent()) {
						return null;
					}
				}	// KJH 20110520 e, expand&trans -> state ����, ���� behavior�� ���Ͽ�
				return super.getConnectionCompleteCommand(request);
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

		ReferElement refItem = getCastedModel();
		StateElement state = this.getRealModel();

		DiagramConfiguration dgmCfg = DiagramConfiguration.getInstance();

		StateElementFigure figure = new StateElementFigure(state.getAttribute());

		figure.setFont(dgmCfg.getItemFont(TaskModelPackage.STATE_ELEMENT, DiagramConfiguration.VALID));

		figure.setForegroundColor(dgmCfg.getItemFGColor(TaskModelPackage.STATE_ELEMENT, DiagramConfiguration.VALID));
		figure.setBackgroundColor(dgmCfg.getItemBGColor(TaskModelPackage.STATE_ELEMENT, DiagramConfiguration.VALID));
		figure.setCornerDimensions(m_corner);
		figure.setMinimumSize(new Dimension(100, 25));
		figure.setLineWidth(2);
	
		figure.setOpaque(true); // non-transparent figure
//		((StateElementFigure)figure).setLineWidth(3);
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
		figure.setName(state.getName());
		
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
		StateElement model= getRealModel();
		StateAction action;

		List list = new ArrayList();
		action = model.getEntry();
		if(action != null) {
			list.add(action);
		}
		action = model.getStay();
		if(action != null) {
			list.add(action);
		}
		action = model.getExit();
		if(action != null) {
			list.add(action);
		}
		
		list.addAll(refItem.getItems());	// KJH 20110512, bifurcates

		return list;

	//	return Collections.EMPTY_LIST;
	}

	/**
	 * Parameter �� �־��� Ŭ����(�Ǵ� �������̽�)�� ���� Adapter �� �����Ѵ�.
	 * @param key Adapter ��û Ÿ��
	 * @return Parameter �� �־��� Ŭ����(�Ǵ� �������̽�)�� ���� Adapter
	 */
	public Object getAdapter(Class key) {
		if (IPropertySource.class == key) {
			Object model = getRealModel();
			if(model instanceof StateElement) {
				return new StateElementPropertySource((StateElement)model);
			}
		}
		else if (IPropertySourceProvider.class == key) {
			final Object model = getRealModel();
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
	 * @author KJH 20110518
	 */
	@Override
	protected void addChildVisual(EditPart childEditPart, int index) {
		IFigure child = ((GraphicalEditPart)childEditPart).getFigure();
		if (childEditPart instanceof BExpandTransElementEditPart) {
			((GraphicalEditPart)getParent()).getContentPane().add(child);
		}
		else {
			getContentPane().add(child, index);
		}
	}

	/**
	 * @author KJH 20110518
	 */
	@Override
	protected void removeChildVisual(EditPart childEditPart) {
		IFigure child = ((GraphicalEditPart)childEditPart).getFigure();
		if (childEditPart instanceof BExpandTransElementEditPart) {
			((GraphicalEditPart)getParent()).getContentPane().remove(child);
		}
		else {
			getContentPane().remove(child);
		}
	}

	/**
	 * ȭ�鿡 ǥ���� ������ �ִ��� Ȯ���Ѵ�.
	 * @return true �� ȭ�鿡 ǥ���� ������ �ִ�. �׷��� ������ ����.
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
	 * ���� �Ӽ� ���� �ٽ� ȭ�鿡 �ٽ� ǥ���Ѵ�.
	 * 
	 * @Override
	 */
	protected void refreshVisuals() {
		StateElement model = getRealModel();
		ReferElement refItem = getCastedModel();

		StateElementFigure figure = (StateElementFigure)getFigure();

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
		if(notifier instanceof StateAction) {
			logger.debug("notifier instanceof StateAction");
//			this.stateActionNotifyChanged(notification);
			return;
		}

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
			case TaskModelPackage.STATE_ELEMENT__NAME:
				logger.debug("ITEM_ELEMENT__NAME");
				ReferElement refItem = getCastedModel();
				EList<ConnectionElement> connList = refItem.getTargetConnections();
				for(ConnectionElement conn : connList) {
					conn.setTarget(refItem);
				}
				refreshVisuals();
				break;
			case TaskModelPackage.STATE_ELEMENT__ENTRY:
				logger.debug("SET : STATE_ELEMENT__ENTRY");
				this.refreshChildren();
				break;
			case TaskModelPackage.STATE_ELEMENT__STAY:
				logger.debug("SET : STATE_ELEMENT__DO");
				this.refreshChildren();
				break;
			case TaskModelPackage.STATE_ELEMENT__EXIT:
				logger.debug("SET : STATE_ELEMENT__EXIT");
				this.refreshChildren();
				break;
			default:
				super.notifyChanged(notification);
				break;
			}
			break;
		case Notification.ADD:
			switch(featureId) {
			case TaskModelPackage.STATE_ELEMENT__STAY:
				refreshChildren();
//				StateAction stateAction = (StateAction)notification.getNewValue();
//				stateAction.eAdapters().add(this);
				break;
			case TaskModelPackage.STATE_ELEMENT__BIFURCATES:
				refreshChildren();
				break;
			default:
				super.notifyChanged(notification);
				break;
			}
			break;
		case Notification.REMOVE:
			switch(featureId) {
			case TaskModelPackage.STATE_ELEMENT__STAY:
				refreshChildren();
//				StateAction stateAction = (StateAction)notification.getNewValue();
//				stateAction.eAdapters().remove(this);
				break;
			case TaskModelPackage.STATE_ELEMENT__BIFURCATES:
				refreshChildren();
				break;
			default:
				super.notifyChanged(notification);
				break;
			}
			break;
		}
	}

	/**
	 * child �� �� StateAction ���� Property �� ����Ǹ� ȣ��ȴ�.
	 * @param notification ���� ���� ����
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
	 * Property �� ����Ǹ� ȣ��ȴ�
	 * @param event Property ���� Event
	 * 
	 * @Override
	 */
	public void propertyChange(PropertyChangeEvent event) {
		logger.debug(event.getProperty());
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
			// KJH 20101201 s, direct edit
			if (refItem.getAttribute() == ReferAttribute.EXTERNAL) {
				return;
			}
			if (request instanceof LocationRequest &&
					directEditHitTest(((LocationRequest)request).getLocation().getCopy())) {
				performDirectEdit();
			}
			// KJH 20101201 e, direct edit
		}
		else if (request.getType() == RequestConstants.REQ_OPEN) {
			ReferElement refItem = getCastedModel();
			StateElement state = getRealModel();
			
			if (refItem.getAttribute() == ReferAttribute.EXTERNAL) {
				return;
			}
			if(state.isIncluded()) {
				return;
			}
			// KJH 20101201 s, direct edit
			if (request instanceof LocationRequest
					&& directEditHitTest(((LocationRequest) request).getLocation().getCopy())) {
				performDirectEdit();
			}
			// KJH 20101201 e, direct edit
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

					// Statement����� ��� ���� �Էµ� Statement�� �����ϱ����� Command�� �����Ѵ�.
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
	 * Request ��û�� �̸� Label �����ΰ��� Ȯ���Ѵ�.
	 * @param requestLoc Request �� ��ǥ
	 * @return Name Label ���� ���� ����
	 */
	private boolean directEditHitTest(Point requestLoc) {
		Label label = ((StateElementFigure)getFigure()).getLabelFigure();
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
	 * Connection ���� Relation �� call �� �ִ°� �˻��Ѵ�.
	 * @return Relation �� 1�� �̻��� Call �� ������ true, �׷��� ������ false
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
	
	/**
	 * child ���� Figure ������ ����Ǹ� ȣ��ȴ�.
	 * @param event Figure ���� ���� Event 
	 */
	public void selectionChanged(SelectionChangedEvent event) {
		super.selectionChanged(event);
	}
	
	// KJH 20110530 s, �켱���� ������� connections ���ϴ� �Լ�
	@Override
	public List<ItemElement> getOrderConnections() {
		List<ItemElement> list = new ArrayList<ItemElement>();
		
		list.addAll(getModelSourceConnections());
		
		ReferElement refItem = getCastedModel();
		for (ItemElement item : refItem.getItems()) {
			if (item instanceof ReferElement) {
				ReferElement refChild = (ReferElement)item;
				if (refChild.getRealModel() instanceof ExpandTransElement == false) {
					continue;
				}
				if ((refChild.getSourceConnections() != null) &&
						(refChild.getSourceConnections().size() > 0)) {
					list.add(refChild);
				}
			}
		}
		
		sortListByOrder(list);
		
		return list;
	}
	// KJH 20110530 e, �켱���� ������� connections ���ϴ� �Լ�
	
	@Override
	public boolean isSelectable() {	// KJH 20110614
		return getCastedModel().isVisible() && !getCastedModel().isCollapsed();
	}
	
}