package kr.re.etri.tpl.diagram.views.controls;

import kr.re.etri.tpl.diagram.views.symbolparts.SNavDiagramEditPart;
import kr.re.etri.tpl.diagram.views.taskparts.TNavDiagramEditPart;
import kr.re.etri.tpl.taskmodel.ActionElement;
import kr.re.etri.tpl.taskmodel.BehaviorElement;
import kr.re.etri.tpl.taskmodel.ConnectionElement;
import kr.re.etri.tpl.taskmodel.ConnectorElement;
import kr.re.etri.tpl.taskmodel.Constant;
import kr.re.etri.tpl.taskmodel.EnumElement;
import kr.re.etri.tpl.taskmodel.Function;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.ModelDiagram;
import kr.re.etri.tpl.taskmodel.ModelElement;
import kr.re.etri.tpl.taskmodel.Parameter;
import kr.re.etri.tpl.taskmodel.ReferElement;
import kr.re.etri.tpl.taskmodel.StateAction;
import kr.re.etri.tpl.taskmodel.StateElement;
import kr.re.etri.tpl.taskmodel.StructBlockElement;
import kr.re.etri.tpl.taskmodel.SubDiagram;
import kr.re.etri.tpl.taskmodel.Symbol;
import kr.re.etri.tpl.taskmodel.TaskElement;
import kr.re.etri.tpl.taskmodel.WithElement;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ScrollBar;
import org.eclipse.ui.views.properties.IPropertySource;

public class BehaviorPropertyForm extends Composite {
	private static Logger logger =
		 Logger.getLogger(BehaviorPropertyForm.class);
	ModelDiagramPropertyGroup modelDiagramGroup;

	private ItemElementPropertyGroup itemElemtGroup;
	private TaskPropertyGroup workerElemtGroup;
	private BehaviorPropertyGroup taskElmtGroup;
	private StatePropertyGroup stateElmtGroup;
	private StateActionPropertyGroup stateActionGroup;
	private ActionPropertyGroup actonElmtGroup;
	private EnumElementPropertyGroup enumElmtGroup;
	private ModelElementPropertyGroup modelElmtGroup;
	private SymbolPropertyGroup symbolElmtGroup;
	private ConstantPropertyGroup constElmtGroup;
	private FunctionPropertyGroup functionElmtGroup;
	private ParameterPropertyGroup paramGroup;
	private ConnectionPropertyGroup connElemtGroup;
	private ConnectorPropertyGroup connectorGroup;	// KJH 20101201
	private StructBlockPropertyGroup structGroup;	// KJH 20110209
	private WithPropertyGroup withGroup;	// KJH 20110527, with
	
	private CommandStack commandStack;
	private IPropertySource propertySource;
	private Adapter modelMonitor;
	private ItemElementPropertyGroup propertyGroup;
	private SelectionListener vBarListener;

	private final static Point ZEROSIZE = new Point(0, 0);

	Object input;
	ItemElement model;

	Listener listener;
	int[] events = new int[] {
			SWT.Dispose,
			SWT.Resize,
			SWT.FocusIn,
			SWT.FocusOut,
			SWT.Activate,
			SWT.Deactivate};

	public BehaviorPropertyForm(Composite parent, int style) {
		super(parent, style | SWT.V_SCROLL);

		listener = new Listener() {
			public void handleEvent(Event e) {
				switch (e.type) {
					case SWT.Dispose: onDispose(); break;
					case SWT.Resize: onResize(); break;
					case SWT.Activate: onActivate(); break;
					case SWT.Deactivate: onDeactivate(); break;
				}
			}
		};

		for (int i = 0; i < events.length; i++) {
			addListener(events[i], listener);
		}

		this.vBarListener = new SelectionListener() {

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				ScrollBar vBar = (ScrollBar)e.getSource();
				int pos = vBar.getSelection();
				Rectangle bound = propertyGroup.getBounds();
				bound.y = -pos;
				propertyGroup.setBounds(bound);
			}
		};

		ScrollBar vBar = this.getVerticalBar();
		vBar.addSelectionListener(vBarListener);
	}

	public void setCommandStack(CommandStack commandStack) {
		this.commandStack = commandStack;
	}

	public CommandStack getCommandStack() {
		return this.commandStack;
	}

	public void setPropertySource(IPropertySource propertySource) {
		this.propertySource = propertySource;
	}

	public void createControl() {
		createFormArea();

		hookControl();
	}

	protected void createFormArea() {
		itemElemtGroup = new ItemElementPropertyGroup(this, SWT.NONE);
		itemElemtGroup.createControl();

		modelDiagramGroup = new ModelDiagramPropertyGroup(this, SWT.NONE);
		modelDiagramGroup.createControl();

		workerElemtGroup = new TaskPropertyGroup(this, SWT.NONE);
		workerElemtGroup.createControl();

		taskElmtGroup = new BehaviorPropertyGroup(this, SWT.NONE);
		taskElmtGroup.createControl();

		stateElmtGroup = new StatePropertyGroup(this, SWT.NONE);
		stateElmtGroup.createControl();

		stateActionGroup = new StateActionPropertyGroup(this, SWT.NONE);
		stateActionGroup.createControl();

		actonElmtGroup = new ActionPropertyGroup(this, SWT.NONE);
		actonElmtGroup.createControl();

		enumElmtGroup = new EnumElementPropertyGroup(this, SWT.NONE);
		enumElmtGroup.createControl();

		modelElmtGroup = new ModelElementPropertyGroup(this, SWT.NONE);
		modelElmtGroup.createControl();

		symbolElmtGroup = new SymbolPropertyGroup(this, SWT.NONE);
		symbolElmtGroup.createControl();

		constElmtGroup = new ConstantPropertyGroup(this, SWT.NONE);
		constElmtGroup.createControl();

		functionElmtGroup = new FunctionPropertyGroup(this, SWT.NONE);
		functionElmtGroup.createControl();

		paramGroup = new ParameterPropertyGroup(this, SWT.NONE);
		paramGroup.createControl();

		connElemtGroup = new ConnectionPropertyGroup(this, SWT.NONE);
		connElemtGroup.createControl();
		
		// KJH 20101201 s, connector
		connectorGroup = new ConnectorPropertyGroup(this, SWT.NONE);
		connectorGroup.createControl();
		// KJH 20101201 e, connector

		// KJH 20110209 s, structblock
		structGroup = new StructBlockPropertyGroup(this, SWT.NONE);
		structGroup.createControl();
		// KJH 20110209 e, structblock
		
		// KJH 20110527 s, with
		withGroup = new WithPropertyGroup(this, SWT.NONE);
		withGroup.createControl();
		// KJH 20110527 e, with		
	}

	private void hookControl() {
		modelMonitor = new Adapter(){

			@Override
			public Notifier getTarget() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public boolean isAdapterForType(Object type) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public void notifyChanged(Notification notification) {

			}

			@Override
			public void setTarget(Notifier newTarget) {
				// TODO Auto-generated method stub

			}
		};
	}

	private void hookIntoModel(EObject model) {
		if(model != null) {
			if(model.eAdapters().contains(modelMonitor) == false) {
				model.eAdapters().add(modelMonitor);
			}
		}
	}

	private void unhookFromModel(EObject model) {
		if(model != null) {
			model.eAdapters().remove(modelMonitor);
		}
	}

	public void setInput(Object newInput) {
		if(newInput == null || ((Object [])newInput).length == 0) {
			return;
		}

		modelDiagramGroup.setVisible(false);
		workerElemtGroup.setVisible(false);
		taskElmtGroup.setVisible(false);
		stateElmtGroup.setVisible(false);
		stateActionGroup.setVisible(false);
		actonElmtGroup.setVisible(false);
		enumElmtGroup.setVisible(false);
		modelElmtGroup.setVisible(false);
		symbolElmtGroup.setVisible(false);
		constElmtGroup.setVisible(false);
		functionElmtGroup.setVisible(false);
		paramGroup.setVisible(false);
		connElemtGroup.setVisible(false);
		connectorGroup.setVisible(false);	// KJH 20101201
		structGroup.setVisible(false);		// KJH 20110209
		withGroup.setVisible(false);	// KJH 20110527, with
		itemElemtGroup.setVisible(false);

		this.input = ((Object [])newInput)[0];

		if(this.input instanceof EditPart) {
			unhookFromModel(model);
			if(!(((EditPart)this.input).getModel() instanceof ItemElement)) {
				return;
			}
			model = (ItemElement)((EditPart)this.input).getModel();
			hookIntoModel(model);
		}
		else {
			return;
		}

		if(this.model instanceof SubDiagram) {
			model = ((SubDiagram)model).getParent();
		}
		else if(this.model instanceof ReferElement) {
			model = ((ReferElement)model).getRealModel();
		}

		if(this.model instanceof ModelDiagram) {
			if((this.input instanceof TNavDiagramEditPart )|| (this.input instanceof SNavDiagramEditPart)){
				propertyGroup = null;
				return;
			}
			propertyGroup = modelDiagramGroup;
		}
		else if(this.model instanceof TaskElement) {
			propertyGroup = workerElemtGroup;
		}
		else if(this.model instanceof BehaviorElement) {
			propertyGroup = taskElmtGroup;
		}
		else if(this.model instanceof StateElement) {
			propertyGroup = stateElmtGroup;
		}
		else if(this.model instanceof StateAction) {
			propertyGroup = stateActionGroup;
		}
		else if(this.model instanceof ActionElement) {
			propertyGroup = actonElmtGroup;
		}
		else if(this.model instanceof EnumElement) {
			propertyGroup = enumElmtGroup;
		}
		else if(this.model instanceof ModelElement){
			propertyGroup = modelElmtGroup;
		}
		else if(this.model instanceof Symbol) {
			propertyGroup = symbolElmtGroup;
		}
		else if(this.model instanceof Constant) {
			propertyGroup = constElmtGroup;
		}
		else if(this.model instanceof Function) {
			propertyGroup = functionElmtGroup;
		}
		else if(this.model instanceof Parameter) {
			propertyGroup = paramGroup;
		}
		else if(this.model instanceof ConnectionElement) {
			propertyGroup = connElemtGroup;
		}
		else if(this.model instanceof ConnectorElement) {	// KJH 20101201 s, connector
			propertyGroup = connectorGroup;
		}													// KJH 20101201 e, connector
		else if(this.model instanceof StructBlockElement) {	// KJH 20110209 s, structblock
			propertyGroup = structGroup;
		}													// KJH 20110209 e, structblock
		else if(this.model instanceof WithElement) {
			propertyGroup = withGroup;	// KJH 20110527, with
		}
		else if(this.model instanceof ItemElement) {
			propertyGroup = itemElemtGroup;
		}
		else {
			propertyGroup = null;
			return;
		}
		propertyGroup.setModel(model);

		propertyGroup.setCommandStack(commandStack);
		propertyGroup.setPropertySource(propertySource);
		propertyGroup.setVisible(true);

		Point formSize = propertyGroup.getMinSize();
		Point size = getSize();
		propertyGroup.setSize(size.x, formSize.y);

		ScrollBar vBar = getVerticalBar();
		vBar.setSelection(0);
		vBar.setMinimum(0);

		onResize();
	}

	private void onDispose() {
		for (int i = 0; i < events.length; i++) {
			removeListener(events[i], listener);
		}

		if (itemElemtGroup != null)	itemElemtGroup.dispose();
		if (modelDiagramGroup != null)	modelDiagramGroup.dispose();
		if (workerElemtGroup != null)	workerElemtGroup.dispose();
		if (taskElmtGroup != null)	taskElmtGroup.dispose();
		if (stateElmtGroup != null)	stateElmtGroup.dispose();
		if (stateActionGroup != null)	stateActionGroup.dispose();
		if (actonElmtGroup != null)	actonElmtGroup.dispose();
		if (enumElmtGroup != null)	enumElmtGroup.dispose();
		if (modelElmtGroup != null)	modelElmtGroup.dispose();
		if (constElmtGroup != null)	constElmtGroup.dispose();
		if (symbolElmtGroup != null)	symbolElmtGroup.dispose();
		if (functionElmtGroup != null)	functionElmtGroup.dispose();
		if (paramGroup != null)	paramGroup.dispose();
		if (connElemtGroup != null)	connElemtGroup.dispose();
		if (connectorGroup != null)	connectorGroup.dispose();	// KJH 20101201, connector
		if (structGroup != null)	structGroup.dispose();		// KJH 20110209, structblock
		if (withGroup != null)	withGroup.dispose();	// KJH 20110527, with
	}

	public Point getMinSize() {
		Point size;
		if(this.propertyGroup == null) {
			size = new Point(0, 0);
		}
		else {
			size = propertyGroup.getMinSize();
		}

		return size;
	}

	private void onResize() {
		Point size = getSize();

		if(propertyGroup == null) {
			return;
		}

		Point formSize = propertyGroup.getMinSize();

		ScrollBar vBar = getVerticalBar();
		vBar.setEnabled(size.y < formSize.y);
		vBar.setPageIncrement(size.y);
		vBar.setIncrement(25);

		Point barSize = vBar.getSize();
		propertyGroup.setSize(size.x-barSize.x, formSize.y);

		if(size.y < formSize.y) {
			vBar.setMaximum(formSize.y-size.y+(this.getBorderWidth()*2));
			Rectangle bound = propertyGroup.getBounds();
			int pos = vBar.getSelection();
			bound.y = -pos;
			propertyGroup.setBounds(bound);
		}
		else {
			Rectangle bound = propertyGroup.getBounds();
			bound.y = 0;
			propertyGroup.setBounds(bound);
		}
	}

	private void onActivate() {
		hookIntoModel(model);
	}

	private void onDeactivate() {
		unhookFromModel(model);
	}
}
