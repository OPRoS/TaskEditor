
package kr.re.etri.tpl.diagram.editparts.task;

import java.util.ArrayList;
import java.util.List;

import kr.re.etri.tpl.grammar.ParserUtil;
import kr.re.etri.tpl.taskmodel.ConnectionElement;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.LineEndPoint;
import kr.re.etri.tpl.taskmodel.LineStyle;
import kr.re.etri.tpl.taskmodel.ModelDiagram;
import kr.re.etri.tpl.taskmodel.ReferElement;
import kr.re.etri.tpl.taskmodel.RelationShip;
import kr.re.etri.tpl.taskmodel.StateAction;
import kr.re.etri.tpl.taskmodel.StateElement;
import kr.re.etri.tpl.taskmodel.BehaviorElement;
import kr.re.etri.tpl.taskmodel.TaskModelPackage;
import kr.re.etri.tpl.diagram.DiagramConfiguration;
import kr.re.etri.tpl.diagram.celledit.LabelCellEditorLocator;
import kr.re.etri.tpl.diagram.celledit.LabelDirectEditManager;
import kr.re.etri.tpl.diagram.commands.ConnectionDeleteCommand;
import kr.re.etri.tpl.diagram.commands.SetListValueCommand;
import kr.re.etri.tpl.diagram.dialogs.ActionStatementEditorDialog;
import kr.re.etri.tpl.diagram.dialogs.TransitionEditorDialog;
import kr.re.etri.tpl.diagram.editors.TPLDiagramEditor;
import kr.re.etri.tpl.diagram.editparts.task.policies.TLabelDirectEditPolicy;
import kr.re.etri.tpl.diagram.factories.DecoratioinFactory;
import kr.re.etri.tpl.diagram.figures.ConnectionElementFigure;
import kr.re.etri.tpl.diagram.properties.sources.ConnectionPropertySource;
import kr.re.etri.tpl.diagram.properties.sources.StateActionPropertySource;
import kr.re.etri.tpl.diagram.util.MarkerLogger;
import kr.re.etri.tpl.diagram.util.PropertyContainer;
import kr.re.etri.tpl.diagram.util.TPLErrorLogger;
import kr.re.etri.tpl.diagram.util.TPLProblemLogger;
import kr.re.etri.tpl.diagram.util.TPLUtil;

import org.antlr.runtime.RecognitionException;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.RotatableDecoration;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.editparts.AbstractConnectionEditPart;
import org.eclipse.gef.editpolicies.ConnectionEditPolicy;
import org.eclipse.gef.editpolicies.ConnectionEndpointEditPolicy;
import org.eclipse.gef.requests.GroupRequest;
import org.eclipse.gef.tools.DirectEditManager;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.IPropertySourceProvider;

/**
 * ConnectionElement �𵨰� Figure �� Controlg �ϴ� Ŭ�����̴�.
 * ConnectionElement �� �𵨰� Relation �� �����Ѵ�.
 * ���� Property ���濡 ���� ó���� PropertyChangeListener interface �� �����Ͽ�
 * �ݿ��� ���� ������ �� Ŭ������ EMF �� Adapter interface �� ������ nested class ��
 * ConnectionAdapter �� notifyChagned(Notification) �޼ҵ带 �̿��Ͽ� �ݿ��Ѵ�.
 * 
 * @author sfline
 */
public class TConnectionElementEditPart extends AbstractConnectionEditPart
	implements IPropertyChangeListener {

	/** �̸� Label �� text �� ������ �� �ֵ��� CellEditor ���� */
	protected DirectEditManager manager;

	/** EMF Adapter */
	private ConnectionAdapter connectionAdapter = new ConnectionAdapter(this);

	/**
	 * EditPart �� Ȱ��ȭ�Ǹ� �𵨷� ���� Notify �� �޵��� �Ѵ�.
	 */
	public void activate() {
		if (!isActive()) {
			hookIntoModel(getCastedModel());
			super.activate();
		}
	}

	/**
	 * EditPart �� ��Ȱ��ȭ�Ǹ� �𵨷� ���� Notify �� ���� �ʵ��� �Ѵ�.
	 */
	public void deactivate() {
		if (isActive()) {
			unhookFromModel(getCastedModel());
			super.deactivate();
		}
	}

	/**
	 * ���� �Ӽ� ���� ����Ǵ� ��� Notify �� �޵��� Listener �� ����Ѵ�.
	 * @param model ��
	 */
	private void hookIntoModel(EObject model) {
		if(model == null) {
			return;
		}
		model.eAdapters().add(connectionAdapter);
	}

	/**
	 * ���� Notify �� ���� �ʵ��� Listener ����� �����Ѵ�.
	 * @param model ��
	 */
	private void unhookFromModel(EObject model) {
		if(model == null) {
			return;
		}
		model.eAdapters().remove(connectionAdapter);
	}

	/**
	 * Parameter �� �־��� Ŭ����(�Ǵ� �������̽�)�� ���� Adapter �� �����Ѵ�.
	 * @param key Adapter ��û Ÿ��
	 * @return Parameter �� �־��� Ŭ����(�Ǵ� �������̽�)�� ���� Adapter
	 */
	public Object getAdapter(Class key) {
		if (IPropertySource.class == key) {
			Object model = getModel();
			if(model instanceof ConnectionElement) {
				return new ConnectionPropertySource((ConnectionElement)model);
			}
		}
		else if (IPropertySourceProvider.class == key) {
			final Object model = getModel();
			if(model instanceof ConnectionElement) {
				return new IPropertySourceProvider() {
					public IPropertySource getPropertySource(Object object) {
						return new ConnectionPropertySource((ConnectionElement)model);
					}
				};
			}
		}
		return super.getAdapter(key);
	}

	/**
	 * EditPart �� ���� Policy �� �����Ѵ�.
	 * @see org.eclipse.gef.editparts.AbstractEditPart#createEditPolicies()
	 * 
	 * @Override
	 */
	protected void createEditPolicies() {
		// Selection handle edit policy.
		// Makes the connection show a feedback, when selected by the user.
		installEditPolicy(EditPolicy.CONNECTION_ENDPOINTS_ROLE, new ConnectionEndpointEditPolicy());

		installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE, new TLabelDirectEditPolicy());

		// Allows the removal of the connection model element
		installEditPolicy(EditPolicy.CONNECTION_ROLE, new ConnectionEditPolicy() {
			protected Command getDeleteCommand(GroupRequest request) {
				Object child = getHost().getModel();
				// Include�� ���� Connection�� ���� �Ұ��ϹǷ�
				if(child instanceof ConnectionElement) {
					ReferElement refItem = (ReferElement)((ConnectionElement)child).getSource();
					ItemElement realItem = refItem.getRealModel();
					if(realItem.isIncluded()) {
						return null;
					}
				}

				return new ConnectionDeleteCommand(getCastedModel());
			}
		});
	}

	/**
	 * �𵨿� ���� Figure �� �����Ͽ� ��ȯ�Ѵ�.
	 * @return �𵨿� ���� Figure
	 * 
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#createFigure()
	 */
	protected IFigure createFigure() {

//		PolylineConnection connFigure = (PolylineConnection) super.createFigure();
		ConnectionElementFigure connFigure = new ConnectionElementFigure();
		
		connFigure.setLineWidth(1);
		connFigure.setTolerance(6);
		
		FontData []fd = new FontData[]{new FontData("Candara", 10, SWT.NORMAL)};
		Font font = new Font(null, fd);
		connFigure.setFont(font);

		ConnectionElement connModel = getCastedModel();

		if(connModel.getRelationship() == RelationShip.TRANSITION) {
			List<String> strList = connModel.getCondition();
			StringBuilder sb = new StringBuilder();
			for(String str : strList) {
				str = str.trim();
				if(str.length() > 0) {
					sb.append(str);
					break;
				}
			}
			connModel.setName(sb.toString());
		}
		else if(connModel.getRelationship() == RelationShip.ACTION_CALL ||
				connModel.getRelationship() == RelationShip.TASK_CALL) {
			ReferElement refItem = (ReferElement)connModel.getSource2();
			ItemElement realItem = refItem.getRealModel();
			if(realItem instanceof StateElement) {
				StateAction stateAction = ((StateElement)realItem).getStay();
				connModel.setName(stateAction.getName());
			}
		}

		if(connModel.getName() != null) {
			String name = connModel.getName().trim();
			connFigure.setName(name);
		}

		DiagramConfiguration dgmCfg = DiagramConfiguration.getInstance();
		connFigure.setForegroundColor(dgmCfg.getRelationLineColor(connModel.getRelationship().getValue()));

		RotatableDecoration dec;
		LineEndPoint arrowType;
		arrowType = connModel.getSourceEndPoint();

		switch(arrowType.getValue()) {
		case LineEndPoint.NONE_VALUE:
			connFigure.setSourceDecoration(null); // arrow at target endpoint
			break;
		case LineEndPoint.OPENED_ARROW_VALUE:
			dec = DecoratioinFactory.createArrowDecoration();
//			dec.setBackgroundColor(dgmCfg.getRelationLineColor(connModel.getRelationship().getValue()));
			connFigure.setSourceDecoration(dec); // arrow at target endpoint
			break;
		case LineEndPoint.OPENED_TRIANGLE_VALUE:
			dec = DecoratioinFactory.createOpenedArrowDecoration();
//			dec.setBackgroundColor(dgmCfg.getRelationLineColor(connModel.getRelationship().getValue()));
			connFigure.setSourceDecoration(dec); // arrow at target endpoint
			break;
		case LineEndPoint.OPENED_SQUARE_VALUE:
			dec = DecoratioinFactory.createOpenedSquareDecoration();
//			dec.setBackgroundColor(dgmCfg.getRelationLineColor(connModel.getRelationship().getValue()));
			connFigure.setSourceDecoration(dec); // arrow at target endpoint
			break;
		case LineEndPoint.OPENED_CIRCLE_VALUE:
			dec = DecoratioinFactory.createOpenedCircleDecoration();
//			dec.setBackgroundColor(dgmCfg.getRelationLineColor(connModel.getRelationship().getValue()));
			connFigure.setSourceDecoration(dec); // arrow at target endpoint
			break;
		case LineEndPoint.CLOSED_TRIANGLE_VALUE:
			dec = DecoratioinFactory.createClosedArrowDecoration();
			dec.setBackgroundColor(dgmCfg.getRelationLineColor(connModel.getRelationship().getValue()));
			connFigure.setSourceDecoration(dec); // arrow at target endpoint
			break;
		case LineEndPoint.CLOSED_SQUARE_VALUE:
			dec = DecoratioinFactory.createClosedSquareDecoration();
			dec.setBackgroundColor(dgmCfg.getRelationLineColor(connModel.getRelationship().getValue()));
			connFigure.setSourceDecoration(dec); // arrow at target endpoint
			break;
		case LineEndPoint.CLOSED_CIRCLE_VALUE:
			dec = DecoratioinFactory.createClosedCircleDecoration();
			dec.setBackgroundColor(dgmCfg.getRelationLineColor(connModel.getRelationship().getValue()));
			connFigure.setSourceDecoration(dec); // arrow at target endpoint
			break;
		}

		arrowType = connModel.getTargetEndPoint();

		switch(arrowType.getValue()) {
		case LineEndPoint.NONE_VALUE:
			connFigure.setTargetDecoration(null); // arrow at target endpoint
			break;
		case LineEndPoint.OPENED_ARROW_VALUE:
			dec = DecoratioinFactory.createArrowDecoration();
//			dec.setBackgroundColor(dgmCfg.getRelationLineColor(connModel.getRelationship().getValue()));
			connFigure.setTargetDecoration(dec); // arrow at target endpoint
			break;
		case LineEndPoint.OPENED_TRIANGLE_VALUE:
			dec = DecoratioinFactory.createOpenedArrowDecoration();
//			dec.setBackgroundColor(dgmCfg.getRelationLineColor(connModel.getRelationship().getValue()));
			connFigure.setTargetDecoration(dec); // arrow at target endpoint
			break;
		case LineEndPoint.OPENED_SQUARE_VALUE:
			dec = DecoratioinFactory.createOpenedSquareDecoration();
//			dec.setBackgroundColor(dgmCfg.getRelationLineColor(connModel.getRelationship().getValue()));
			connFigure.setTargetDecoration(dec); // arrow at target endpoint
			break;
		case LineEndPoint.OPENED_CIRCLE_VALUE:
			dec = DecoratioinFactory.createOpenedCircleDecoration();
//			dec.setBackgroundColor(dgmCfg.getRelationLineColor(connModel.getRelationship().getValue()));
			connFigure.setTargetDecoration(dec); // arrow at target endpoint
			break;
		case LineEndPoint.CLOSED_TRIANGLE_VALUE:
			dec = DecoratioinFactory.createClosedArrowDecoration();
			dec.setBackgroundColor(dgmCfg.getRelationLineColor(connModel.getRelationship().getValue()));
			connFigure.setTargetDecoration(dec); // arrow at target endpoint
			break;
		case LineEndPoint.CLOSED_SQUARE_VALUE:
			dec = DecoratioinFactory.createClosedSquareDecoration();
			dec.setBackgroundColor(dgmCfg.getRelationLineColor(connModel.getRelationship().getValue()));
			connFigure.setTargetDecoration(dec); // arrow at target endpoint
			break;
		case LineEndPoint.CLOSED_CIRCLE_VALUE:
			dec = DecoratioinFactory.createClosedCircleDecoration();
			dec.setBackgroundColor(dgmCfg.getRelationLineColor(connModel.getRelationship().getValue()));
			connFigure.setTargetDecoration(dec); // arrow at target endpoint
			break;
		}

		// this check is kind of stupid, I really should be using an enumerated
		// type for the line style that matches Graphics :/
		switch(connModel.getLineStyle().getValue()) {
		case LineStyle.LINE_SOLID_VALUE:
			connFigure.setLineStyle(Graphics.LINE_SOLID);
			break;
		case LineStyle.LINE_DASH_VALUE:
			connFigure.setLineStyle(Graphics.LINE_DASH);
			break;
		case LineStyle.LINE_DOT_VALUE:
			connFigure.setLineStyle(Graphics.LINE_DOT);
			break;
		case LineStyle.LINE_DASHDOT_VALUE:
			connFigure.setLineStyle(Graphics.LINE_DASHDOT);
			break;
		case LineStyle.LINE_DASHDOTDOT_VALUE:
			connFigure.setLineStyle(Graphics.LINE_DASHDOTDOT);
			break;
		case LineStyle.LINE_CUSTOM_VALUE:
			connFigure.setLineStyle(Graphics.LINE_CUSTOM);
			break;
		default:
			break;
		}

		connFigure.setVisible(connModel.isVisible());
		return connFigure;
	}

	/**
	 * Object Ÿ���� ���� EditPart �� �� Ÿ������ ĳ�����Ѵ�.
	 * @return ConnectionElement ��
	 */
	public ConnectionElement getCastedModel() {
		return (ConnectionElement) getModel();
	}

	/**
	 * Property �� ����Ǹ� ȣ��ȴ�
	 * @param event Property ���� Event
	 */
	public void propertyChange(PropertyChangeEvent event) {
	}

	/**
	 * �� ���� �� Problem Marker �� ����� Logger �� �����Ѵ�.
	 * @return Problem Marker Logger
	 */
	public MarkerLogger getLogger() {
		MarkerLogger problemLogger = null;
		
		EditPartViewer partViewer = this.getViewer();
		if(partViewer == null) {
			return null;
		}
		DefaultEditDomain editDomain = (DefaultEditDomain)partViewer.getEditDomain();
		if(editDomain == null) {
			return null;
		}
		IEditorPart editPart = editDomain.getEditorPart();
		if(editPart instanceof TPLDiagramEditor) {
			problemLogger = ((TPLDiagramEditor)editPart).getLogger();
		}

		return problemLogger;
	}
	
	/**
	 * EditPart �� �����ϴ� ���� �ֻ��� ���� ��ȯ�Ѵ�.
	 * @return �ֻ��� ��
	 */
	public ModelDiagram getRootModel() {
		EditPartViewer partViewer = this.getViewer();
		if(partViewer == null) {
			return null;
		}
		DefaultEditDomain editDomain = (DefaultEditDomain)partViewer.getEditDomain();
		if(editDomain == null) {
			return null;
		}
		
		IEditorPart editPart = editDomain.getEditorPart();
		if((editPart instanceof TPLDiagramEditor) == false) {
			return null;
		}
		
		ModelDiagram model;
		model = (ModelDiagram)((TPLDiagramEditor)editPart).getModel();
		
		return model;
	}

	/**
	 * �־��� Request �� �����Ѵ�. �� method �� EditPart �� �Ϲ�����
	 * �޽����� ������ ���� ���ȴ�.
	 * 
	 * @see org.eclipse.gef.EditPart#performRequest(org.eclipse.gef.Request)
	 */
	public void performRequest(Request request) {
		if (request.getType() == RequestConstants.REQ_DIRECT_EDIT) {
//			���� �Է����� ���ϵ��� ��.
//			if (request instanceof DirectEditRequest
//				&& !directEditHitTest(((DirectEditRequest) request)
//					.getLocation()
//					.getCopy()))
//				return;
//			performDirectEdit();
		}
		else if (request.getType() == RequestConstants.REQ_OPEN) {
			ConnectionElement conn = getCastedModel();
			if(conn.getRelationship() == RelationShip.TRANSITION) {
				TransitionEditorDialog dialog;
				String title = "Conditions for transition.";
				String message = "Please enter the transition conditions.";

				List<String> strList = conn.getCondition();
				StringBuilder sb = new StringBuilder();
				for(String str : strList) {
					sb.append(str).append("\r\n");
				}

				Shell shell = PlatformUI.getWorkbench().getDisplay().getActiveShell();
				dialog = new TransitionEditorDialog(shell, title, message, sb.toString());

				ReferElement refItem = (ReferElement)conn.getSource();
				ItemElement source = refItem.getRealModel();
				ModelDiagram rootModel = (ModelDiagram)TPLUtil.getRootModel((ItemElement)source);
				dialog.setRootModel(rootModel);

				if(source.isIncluded()) {
					dialog.setEditable(false);
				}

				if(dialog.open() == IDialogConstants.OK_ID) {
					int type, featureId;
					PropertyContainer propety;
					ConnectionPropertySource propertySource;

					propertySource = new ConnectionPropertySource(conn);

					// Statement����� ��� ���� �Էµ� Statement�� �����ϱ����� Command�� �����Ѵ�.
					type = PropertyContainer.REPLACEALL;
					featureId = TaskModelPackage.CONNECTION_ELEMENT__CONDITION;
					SetListValueCommand cmd = new SetListValueCommand("condition");
					cmd.setTarget(propertySource);
					cmd.setPropertyId(featureId);

					Object propValue = propertySource.getPropertyValue(featureId);
					ArrayList<String> oldValue = new ArrayList<String>();
					oldValue.addAll((List<String>)propValue);

					String []condString = dialog.getText().split("\r\n");
					ArrayList<String> newValue = new ArrayList<String>();
					for(String str : condString) {
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
			else if(conn.getRelationship() == RelationShip.TASK_CALL ||
					conn.getRelationship() == RelationShip.ACTION_CALL) {
				ReferElement refItem = (ReferElement)conn.getSource();
				ItemElement source = refItem.getRealModel();
				StateAction stateAction = null;
				if(source instanceof StateElement) {
					stateAction = ((StateElement)source).getStay();
				}
				else if(source instanceof BehaviorElement) {
					refItem = (ReferElement)conn.getSource2();
					source = refItem.getRealModel();
					if(source instanceof StateElement) {
						stateAction = ((StateElement)source).getStay();
					}
				}
				if(stateAction != null) {
					ActionStatementEditorDialog dialog;
					String title = "\"Do\" Action statement.";
					String message = "Please enter the action statement.";

					List<String> strList = stateAction.getStatements();
					StringBuilder sb = new StringBuilder();
					for(String str : strList) {
						sb.append(str).append("\r\n");
					}

					Shell shell = PlatformUI.getWorkbench().getDisplay().getActiveShell();
					dialog = new ActionStatementEditorDialog(shell, title, message, sb.toString());

					// �ֻ��� ���� �˻��Ͽ� �����Ѵ�.
					ModelDiagram rootModel = (ModelDiagram)TPLUtil.getRootModel((ItemElement)source);
					ReferElement refTarget = (ReferElement)conn.getTarget();
					dialog.setRootModel(rootModel, refTarget.getRealModel());

					if(source.isIncluded()) {
						dialog.setEditable(false);
					}

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
	}

	/**
	 * Request ��û�� �̸� Label �����ΰ��� Ȯ���Ѵ�.
	 * @param requestLoc Request �� ��ǥ
	 * @return Name Label ���� ���� ����
	 */
	private boolean directEditHitTest(Point requestLoc) {
/*
		Label label = ((ConnectionElementFigure)getFigure()).getLabelFigure();
		label.translateToRelative(requestLoc);
		if (label.containsPoint(requestLoc))
			return true;
		return false;
*/
		return true;
	}

	/**
	 * Direct Edit �� �����Ѵ�.
	 */
	protected void performDirectEdit() {
		if (manager == null) {
			Label label = ((ConnectionElementFigure)getFigure()).getLabelFigure();
			manager =
				new LabelDirectEditManager(
					this,
					TextCellEditor.class,
					new LabelCellEditorLocator(label), label);
		}
		manager.show();
	}


	/**
	 * �𵨿� ���� Adapter �̴�. 
	 * @author sfline
	 *
	 */
	private class ConnectionAdapter implements Adapter {
		/** �Ӽ��� ����� Target �� */
		private Notifier target;
		/** ���� control �ϴ� EditPart */
		private TConnectionElementEditPart editPart;

		/**
		 * �𵨿� ���� Adapter �� �����Ѵ�.
		 * @param part
		 */
		public ConnectionAdapter(TConnectionElementEditPart part) {
			this.editPart = part;
		}

		/**
		 * ���� �Ӽ� ���� �ٽ� ȭ�鿡 �ٽ� ǥ���Ѵ�.
		 * 
		 * @Override
		 */
		protected void refreshVisuals() {
			ConnectionElement connModel = getCastedModel();
			ConnectionElementFigure connFigure = (ConnectionElementFigure) getFigure();

			if(connModel.getName() != null) {
				String name = connModel.getName().trim();
				connFigure.setName(name);
			}

			DiagramConfiguration dgmCfg = DiagramConfiguration.getInstance();
			connFigure.setForegroundColor(dgmCfg.getRelationLineColor(connModel.getRelationship().getValue()));

			RotatableDecoration dec;
			LineEndPoint arrowType;

			arrowType = connModel.getSourceEndPoint();

			switch(arrowType.getValue()) {
			case LineEndPoint.NONE_VALUE:
				connFigure.setSourceDecoration(null); // arrow at target endpoint
				break;
			case LineEndPoint.OPENED_ARROW_VALUE:
				dec = DecoratioinFactory.createArrowDecoration();
//				dec.setBackgroundColor(dgmCfg.getRelationLineColor(connModel.getRelationship().getValue()));
				connFigure.setSourceDecoration(dec); // arrow at target endpoint
				break;
			case LineEndPoint.OPENED_TRIANGLE_VALUE:
				dec = DecoratioinFactory.createOpenedArrowDecoration();
//				dec.setBackgroundColor(dgmCfg.getRelationLineColor(connModel.getRelationship().getValue()));
				connFigure.setSourceDecoration(dec); // arrow at target endpoint
				break;
			case LineEndPoint.OPENED_SQUARE_VALUE:
				dec = DecoratioinFactory.createOpenedSquareDecoration();
//				dec.setBackgroundColor(dgmCfg.getRelationLineColor(connModel.getRelationship().getValue()));
				connFigure.setSourceDecoration(dec); // arrow at target endpoint
				break;
			case LineEndPoint.OPENED_CIRCLE_VALUE:
				dec = DecoratioinFactory.createOpenedCircleDecoration();
//				dec.setBackgroundColor(dgmCfg.getRelationLineColor(connModel.getRelationship().getValue()));
				connFigure.setSourceDecoration(dec); // arrow at target endpoint
				break;
			case LineEndPoint.CLOSED_TRIANGLE_VALUE:
				dec = DecoratioinFactory.createClosedArrowDecoration();
				dec.setBackgroundColor(dgmCfg.getRelationLineColor(connModel.getRelationship().getValue()));
				connFigure.setSourceDecoration(dec); // arrow at target endpoint
				break;
			case LineEndPoint.CLOSED_SQUARE_VALUE:
				dec = DecoratioinFactory.createClosedSquareDecoration();
				dec.setBackgroundColor(dgmCfg.getRelationLineColor(connModel.getRelationship().getValue()));
				connFigure.setSourceDecoration(dec); // arrow at target endpoint
				break;
			case LineEndPoint.CLOSED_CIRCLE_VALUE:
				dec = DecoratioinFactory.createClosedCircleDecoration();
				dec.setBackgroundColor(dgmCfg.getRelationLineColor(connModel.getRelationship().getValue()));
				connFigure.setSourceDecoration(dec); // arrow at target endpoint
				break;
			}

			arrowType = connModel.getTargetEndPoint();

			switch(arrowType.getValue()) {
			case LineEndPoint.NONE_VALUE:
				connFigure.setTargetDecoration(null); // arrow at target endpoint
				break;
			case LineEndPoint.OPENED_ARROW_VALUE:
				dec = DecoratioinFactory.createArrowDecoration();
//				dec.setBackgroundColor(dgmCfg.getRelationLineColor(connModel.getRelationship().getValue()));
				connFigure.setTargetDecoration(dec); // arrow at target endpoint
				break;
			case LineEndPoint.OPENED_TRIANGLE_VALUE:
				dec = DecoratioinFactory.createOpenedArrowDecoration();
//				dec.setBackgroundColor(dgmCfg.getRelationLineColor(connModel.getRelationship().getValue()));
				connFigure.setTargetDecoration(dec); // arrow at target endpoint
				break;
			case LineEndPoint.OPENED_SQUARE_VALUE:
				dec = DecoratioinFactory.createOpenedSquareDecoration();
//				dec.setBackgroundColor(dgmCfg.getRelationLineColor(connModel.getRelationship().getValue()));
				connFigure.setTargetDecoration(dec); // arrow at target endpoint
				break;
			case LineEndPoint.OPENED_CIRCLE_VALUE:
				dec = DecoratioinFactory.createOpenedCircleDecoration();
//				dec.setBackgroundColor(dgmCfg.getRelationLineColor(connModel.getRelationship().getValue()));
				connFigure.setTargetDecoration(dec); // arrow at target endpoint
				break;
			case LineEndPoint.CLOSED_TRIANGLE_VALUE:
				dec = DecoratioinFactory.createClosedArrowDecoration();
				dec.setBackgroundColor(dgmCfg.getRelationLineColor(connModel.getRelationship().getValue()));
				connFigure.setTargetDecoration(dec); // arrow at target endpoint
				break;
			case LineEndPoint.CLOSED_SQUARE_VALUE:
				dec = DecoratioinFactory.createClosedSquareDecoration();
				dec.setBackgroundColor(dgmCfg.getRelationLineColor(connModel.getRelationship().getValue()));
				connFigure.setTargetDecoration(dec); // arrow at target endpoint
				break;
			case LineEndPoint.CLOSED_CIRCLE_VALUE:
				dec = DecoratioinFactory.createClosedCircleDecoration();
				dec.setBackgroundColor(dgmCfg.getRelationLineColor(connModel.getRelationship().getValue()));
				connFigure.setTargetDecoration(dec); // arrow at target endpoint
				break;
			}

			if(connModel.getLineStyle().getValue() == LineStyle.LINE_SOLID_VALUE) {
				connFigure.setLineStyle(Graphics.LINE_SOLID);
			}
			else if(connModel.getLineStyle().getValue() == LineStyle.LINE_DASH_VALUE) {
				connFigure.setLineStyle(Graphics.LINE_DASH);
			}
			else if(connModel.getLineStyle().getValue() == LineStyle.LINE_DOT_VALUE) {
				connFigure.setLineStyle(Graphics.LINE_DOT);
			}
			else if(connModel.getLineStyle().getValue() == LineStyle.LINE_DASHDOT_VALUE) {
				connFigure.setLineStyle(Graphics.LINE_DASHDOT);
			}
			else if(connModel.getLineStyle().getValue() == LineStyle.LINE_DASHDOTDOT_VALUE) {
				connFigure.setLineStyle(Graphics.LINE_DASHDOTDOT);
			}
			else if(connModel.getLineStyle().getValue() == LineStyle.LINE_CUSTOM_VALUE) {
				connFigure.setLineStyle(Graphics.LINE_CUSTOM);
			}
			else {
				connFigure.setLineStyle(Graphics.LINE_SOLID);
			}

			connFigure.setVisible(connModel.isVisible());

		}

		/**
		 * ���� Property �� ����Ǹ� ȣ��ȴ�.
		 * @param notification ���� ���� ����
		 * 
		 * @see org.eclipse.emf.common.notify.Adapter#notifyChanged(org.eclipse.emf.common.notify.Notification)
		 * @Override
		 */
		public void notifyChanged(Notification notification) {
			int type = notification.getEventType();
			int featureId = notification.getFeatureID(TaskModelPackage.class);
			ConnectionElement conn = getCastedModel();
			switch(type) {
			case Notification.SET:
				switch(featureId) {
//				case TaskModelPackage.LINE_ELEMENT__LINE_STYLE:
//				case TaskModelPackage.LINE_ELEMENT__SOURCE_END_POINT:
//				case TaskModelPackage.LINE_ELEMENT__TARGET_END_POINT:
//				case TaskModelPackage.ITEM_ELEMENT__VISIBLE:
//				case TaskModelPackage.CONNECTION_ELEMENT__SOURCE:
//					refreshVisuals();
//					break;
//				}
				case TaskModelPackage.CONNECTION_ELEMENT__CONDITION:
					if(conn.getRelationship() == RelationShip.TRANSITION) {
						List<String> strList = conn.getCondition();
						StringBuilder sb = new StringBuilder();
						for(String str : strList) {
							str = str.trim();
							if(str.length() > 0) {
								sb.append(str);
								break;
							}
						}
						try {
							MarkerLogger logger = getLogger();
							logger.clearProblem();

							ParserUtil parser = new ParserUtil();
							parser.checkExpression(getRootModel(), sb.toString(), logger);
						} catch (RecognitionException e) {
							TPLErrorLogger.error(e.getMessage(), e);
						}
						
						conn.setName(sb.toString());
					}
					break;
				case TaskModelPackage.CONNECTION_ELEMENT__SOURCE2:
					if(conn.getRelationship() == RelationShip.TRANSITION) {
						List<String> strList = conn.getCondition();
						StringBuilder sb = new StringBuilder();
						for(String str : strList) {
							str = str.trim();
							if(str.length() > 0) {
								sb.append(str);
								break;
							}
						}
						try {
							MarkerLogger logger = getLogger();
							logger.clearProblem();

							ParserUtil parser = new ParserUtil();
							parser.checkExpression(getRootModel(), sb.toString(), logger);
						} catch (RecognitionException e) {
							TPLErrorLogger.error(e.getMessage(), e);
						}
						
						conn.setName(sb.toString());
					}
					else if(conn.getRelationship() == RelationShip.ACTION_CALL ||
							conn.getRelationship() == RelationShip.TASK_CALL) {
						ReferElement refItem = (ReferElement)conn.getSource2();
						ItemElement realItem = refItem.getRealModel();
						if(realItem instanceof StateElement) {
							StateAction stateAction = ((StateElement)realItem).getStay();
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
							
							conn.setName(stateAction.getName());
						}
					}
					break;
				}
				refreshVisuals();

				break;
			case Notification.ADD:
			case Notification.REMOVE:
			case Notification.ADD_MANY:
			case Notification.REMOVE_MANY:
				switch(featureId) {
				case TaskModelPackage.CONNECTION_ELEMENT__CONDITION:
					if(conn.getRelationship() == RelationShip.TRANSITION) {
						List<String> strList = conn.getCondition();
						StringBuilder sb = new StringBuilder();
						for(String str : strList) {
							str = str.trim();
							if(str.length() > 0) {
								sb.append(str);
								break;
							}
						}
						try {
							MarkerLogger logger = getLogger();
							logger.clearProblem();

							ParserUtil parser = new ParserUtil();
							parser.checkExpression(getRootModel(), sb.toString(), logger);
						} catch (RecognitionException e) {
							TPLErrorLogger.error(e.getMessage(), e);
						}
						
						conn.setName(sb.toString());
					}
					break;
				}
				refreshChildren();
				break;
			}
		}

		/**
		 * EditPart �� ���� Notifier �� �����Ѵ�.
		 * @param newTarget Notifier
		 * 
		 * @see org.eclipse.emf.common.notify.Adapter#setTarget(org.eclipse.emf.common.notify.Notifier)
		 */
		public void setTarget(Notifier newTarget) {
			target = newTarget;
		}

		/**
		 * �־��� ��ü�� ���� Adapter ����
		 * @param type Adapter ��û Ÿ��
		 *
		 * @see org.eclipse.emf.common.notify.Adapter#isAdapterForType(java.lang.Object)
		 */
		public boolean isAdapterForType(Object type) {
			return (getModel().getClass() == type);
		}

		/**
		 * EditPart �� ���� Notifier �� ��ȯ�Ѵ�.
		 * @return Notifier
		 * 
		 * @see org.eclipse.emf.common.notify.Adapter#getTarget()
		 */
		public Notifier getTarget() {
			return target;
		}

	}
}