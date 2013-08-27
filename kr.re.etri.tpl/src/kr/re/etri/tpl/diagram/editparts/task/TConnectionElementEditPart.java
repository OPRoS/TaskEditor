
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
 * ConnectionElement 모델과 Figure 를 Controlg 하는 클래스이다.
 * ConnectionElement 는 모델간 Relation 을 관리한다.
 * 모델의 Property 변경에 대한 처리를 PropertyChangeListener interface 를 구현하여
 * 반영할 수도 있지만 이 클래스는 EMF 의 Adapter interface 를 구현한 nested class 인
 * ConnectionAdapter 의 notifyChagned(Notification) 메소드를 이용하여 반영한다.
 * 
 * @author sfline
 */
public class TConnectionElementEditPart extends AbstractConnectionEditPart
	implements IPropertyChangeListener {

	/** 이름 Label 의 text 를 수정할 수 있도록 CellEditor 관리 */
	protected DirectEditManager manager;

	/** EMF Adapter */
	private ConnectionAdapter connectionAdapter = new ConnectionAdapter(this);

	/**
	 * EditPart 가 활성화되면 모델로 부터 Notify 를 받도록 한다.
	 */
	public void activate() {
		if (!isActive()) {
			hookIntoModel(getCastedModel());
			super.activate();
		}
	}

	/**
	 * EditPart 가 비활성화되면 모델로 부터 Notify 를 받지 않도록 한다.
	 */
	public void deactivate() {
		if (isActive()) {
			unhookFromModel(getCastedModel());
			super.deactivate();
		}
	}

	/**
	 * 모델의 속성 값이 변경되는 경우 Notify 를 받도록 Listener 로 등록한다.
	 * @param model 모델
	 */
	private void hookIntoModel(EObject model) {
		if(model == null) {
			return;
		}
		model.eAdapters().add(connectionAdapter);
	}

	/**
	 * 모델의 Notify 를 받지 않도록 Listener 등록을 해제한다.
	 * @param model 모델
	 */
	private void unhookFromModel(EObject model) {
		if(model == null) {
			return;
		}
		model.eAdapters().remove(connectionAdapter);
	}

	/**
	 * Parameter 로 주어진 클래스(또는 인터페이스)에 대한 Adapter 를 제공한다.
	 * @param key Adapter 요청 타입
	 * @return Parameter 로 주어진 클래스(또는 인터페이스)에 대한 Adapter
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
	 * EditPart 에 대한 Policy 를 적용한다.
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
				// Include된 모델의 Connection은 삭제 불가하므로
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
	 * 모델에 대한 Figure 를 생성하여 반환한다.
	 * @return 모델에 대한 Figure
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
	 * Object 타입의 모델을 EditPart 의 모델 타입으로 캐스팅한다.
	 * @return ConnectionElement 모델
	 */
	public ConnectionElement getCastedModel() {
		return (ConnectionElement) getModel();
	}

	/**
	 * Property 가 변경되면 호출된다
	 * @param event Property 변경 Event
	 */
	public void propertyChange(PropertyChangeEvent event) {
	}

	/**
	 * 모델 편집 시 Problem Marker 을 기록할 Logger 를 제공한다.
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
	 * EditPart 가 관리하는 모델의 최상위 모델을 반환한다.
	 * @return 최상위 모델
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
	 * 주어진 Request 를 수행한다. 이 method 는 EditPart 에 일반적인
	 * 메시지를 보내기 위해 사용된다.
	 * 
	 * @see org.eclipse.gef.EditPart#performRequest(org.eclipse.gef.Request)
	 */
	public void performRequest(Request request) {
		if (request.getType() == RequestConstants.REQ_DIRECT_EDIT) {
//			직접 입력하지 못하도록 함.
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

					// Statement목록을 모두 새로 입력된 Statement로 변경하기위한 Command를 실행한다.
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

					// 최상위 모델을 검색하여 설정한다.
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
	}

	/**
	 * Request 요청이 이름 Label 영역인가를 확인한다.
	 * @param requestLoc Request 의 좌표
	 * @return Name Label 영역 포함 여부
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
	 * Direct Edit 를 수행한다.
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
	 * 모델에 대한 Adapter 이다. 
	 * @author sfline
	 *
	 */
	private class ConnectionAdapter implements Adapter {
		/** 속성이 변경된 Target 모델 */
		private Notifier target;
		/** 모델을 control 하는 EditPart */
		private TConnectionElementEditPart editPart;

		/**
		 * 모델에 대한 Adapter 를 생성한다.
		 * @param part
		 */
		public ConnectionAdapter(TConnectionElementEditPart part) {
			this.editPart = part;
		}

		/**
		 * 모델의 속성 값을 다시 화면에 다시 표시한다.
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
		 * 모델의 Property 가 변경되면 호출된다.
		 * @param notification 모델의 변경 정보
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
		 * EditPart 에 대한 Notifier 를 설정한다.
		 * @param newTarget Notifier
		 * 
		 * @see org.eclipse.emf.common.notify.Adapter#setTarget(org.eclipse.emf.common.notify.Notifier)
		 */
		public void setTarget(Notifier newTarget) {
			target = newTarget;
		}

		/**
		 * 주어진 객체에 대한 Adapter 여부
		 * @param type Adapter 요청 타입
		 *
		 * @see org.eclipse.emf.common.notify.Adapter#isAdapterForType(java.lang.Object)
		 */
		public boolean isAdapterForType(Object type) {
			return (getModel().getClass() == type);
		}

		/**
		 * EditPart 에 대한 Notifier 를 반환한다.
		 * @return Notifier
		 * 
		 * @see org.eclipse.emf.common.notify.Adapter#getTarget()
		 */
		public Notifier getTarget() {
			return target;
		}

	}
}