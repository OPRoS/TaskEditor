package kr.re.etri.tpl.diagram.views.controls;

import java.util.List;

import kr.re.etri.tpl.diagram.ModelManager;
import kr.re.etri.tpl.diagram.properties.sources.ParameterPropertySource;
import kr.re.etri.tpl.diagram.util.PropertyContainer;
import kr.re.etri.tpl.grammar.RTMType;
import kr.re.etri.tpl.taskmodel.ActionElement;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.Parameter;
import kr.re.etri.tpl.taskmodel.TaskModelFactory;
import kr.re.etri.tpl.taskmodel.TaskModelPackage;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

public class ActionPropertyGroup extends LinkedElementPropertyGroup {

	private Label labelParam;
	private TableViewer paramTable;
	private Button orderUpBtn;
	private Button orderDnBtn;
	private Button paramAddBtn;
	private Button paramRemBtn;
	private Composite paramPane;
	private Composite buttonPane;
	
	private Adapter paramMonitor;

	private long selectedItem = 0;
	
	public ActionPropertyGroup(Composite parent, int style) {
		super(parent, style);
		// TODO Auto-generated constructor stub
	}

	protected void createFormArea() {
		super.createFormArea();

		Rectangle rect1, rect2;
		Rectangle descLabelRt, descTextRt;

		descLabelRt = descLabel.getBounds();
		descTextRt = descText.getBounds();

		labelParam = new Label(this, SWT.NONE);
		labelParam.setText("Parameter");
		rect1 = new Rectangle(descLabelRt.x, descTextRt.y+descTextRt.height+5,
				80, 20);
		labelParam.setBounds(rect1);
		rect2 = rect1;

		paramPane = new Composite(this, SWT.NONE);

		Rectangle paramPaneRt = new Rectangle(descTextRt.x, rect2.y, descTextRt.width, 110);
		paramPane.setBounds(paramPaneRt);
		FormLayout layout = new FormLayout();
		paramPane.setLayout(layout);

		buttonPane = new Composite(paramPane, SWT.NONE);
		FormData data = new FormData();
		data.right = new FormAttachment(100,0);
		data.top = new FormAttachment(0,0);
		data.width = 60;
		buttonPane.setLayoutData(data);
		FillLayout fillLayout = new FillLayout(SWT.VERTICAL);
		fillLayout.spacing = 5;
		buttonPane.setLayout(fillLayout);

		paramAddBtn = new Button(buttonPane, SWT.PUSH);
		paramAddBtn.setText("Add");
		paramAddBtn.addListener(SWT.Selection, new Listener()
		{
			@Override
			public void handleEvent(Event event)
			{
				TableItem item_ = new TableItem(paramTable.getTable(), SWT.NONE);
				item_.setText(new String[]{RTMType.INT.toString(), "param"});
				TaskModelFactory factory = ModelManager.getFactory();
				Parameter param = factory.createParameter();
				param.setType(RTMType.INT.toString());
				param.setName("param");
				item_.setData(param);

				PropertyContainer prop;
				prop = new PropertyContainer(PropertyContainer.ADD, TaskModelPackage.ACTION_ELEMENT__PARAMS, param);
				setListValue(prop, "add parameter");

//				prop = new PropertyContainer(PropertyContainer.SET, TaskModelPackage.PARAMETER__TYPE, "boolean", param.getType());
////				setValue(prop);
//				ParameterPropertySource paramSrc = new ParameterPropertySource(param);
//				paramSrc.setPropertyValue(TaskModelPackage.PARAMETER__TYPE, prop);
			}
		});

		paramRemBtn = new Button(buttonPane, SWT.PUSH);
		paramRemBtn.setText("Remove");
		paramRemBtn.addListener(SWT.Selection, new Listener()
		{
			@Override
			public void handleEvent(Event event)
			{
				StructuredSelection sel = (StructuredSelection)paramTable.getSelection();
				PropertyContainer prop;

				List seList = sel.toList();
				for(int i = 0; i < seList.size(); i += 1) {
					Object param = seList.get(i);
					prop = new PropertyContainer(PropertyContainer.REMOVE, TaskModelPackage.ACTION_ELEMENT__PARAMS, null, param);
					setListValue(prop, "remove parameter");
				}

				paramTable.remove(sel.toArray());
			}
		});

		orderUpBtn = new Button(buttonPane, SWT.ARROW|SWT.UP);
		orderUpBtn.addListener(SWT.Selection, new Listener()
		{
			@Override
			public void handleEvent(Event event)
			{
				StructuredSelection selection_ = (StructuredSelection)paramTable.getSelection();

				if(selection_.size() == 1)
				{
					Table table_ = paramTable.getTable();
					int selIdx_ = table_.getSelectionIndex();

					if(selIdx_ > 0)
					{
						selectedItem = (((long)TaskModelPackage.ACTION_ELEMENT__PARAMS) << 32) | ((long)(selIdx_ - 1));

						ActionElement action = (ActionElement)getModel();
						action.getParams().move(selIdx_, selIdx_-1);
					}
				}
			}
		});

		orderDnBtn = new Button(buttonPane, SWT.ARROW|SWT.DOWN);
		orderDnBtn.addListener(SWT.Selection, new Listener()
		{
			@Override
			public void handleEvent(Event event)
			{
				StructuredSelection selection_ = (StructuredSelection)paramTable.getSelection();

				if(selection_.size() == 1)
				{
					Table table_ = paramTable.getTable();
					int selIdx_ = table_.getSelectionIndex();
					int total_ = table_.getItemCount()-1;

					if(selIdx_ < total_)
					{
						selectedItem = (((long)TaskModelPackage.ACTION_ELEMENT__PARAMS) << 32) | ((long)(selIdx_ + 1));

						ActionElement action = (ActionElement)getModel();
						action.getParams().move(selIdx_, selIdx_+1);
					}
				}
			}
		});

		int style = SWT.SELECTED | SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER | SWT.FULL_SELECTION;
		paramTable = new TableViewer(paramPane, style);
		Table table = (Table)paramTable.getControl();
		data = new FormData();
		data.left = new FormAttachment(0,0);
		data.top = new FormAttachment(0,0);
		data.bottom = new FormAttachment(100,0);
		data.right = new FormAttachment(buttonPane,-5);
		table.setLayoutData(data);

		String[] columnNames = new String[] {"Type", "Name"};
		int[] columnWidths = new int[] {100, 150};
		int[] columnAlignments = new int[] {SWT.RIGHT, SWT.RIGHT};

		for (int i = 0; i < columnNames.length; i++) {
			TableColumn tableColumn = new TableColumn(table, columnAlignments[i]);
			tableColumn.setText(columnNames[i]);
			tableColumn.setWidth(columnWidths[i]);
		}
//
//		for (int i = 0; i < table.getColumnCount(); i++)
//		{
//			table.getColumn(i).pack();
//		}

		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		CellEditor[] editors = new CellEditor[2];
		editors[0] = new ComboBoxCellEditor(table, RTMType.getInputTypes());
		editors[1] = new TextCellEditor(table);

		paramTable.setColumnProperties(new String[]{"Type", "Name"});
		paramTable.setCellModifier(new ActionPropertyCellModifier(paramTable));
		paramTable.setCellEditors(editors);

		rect2 = rect1;

		int width = paramPaneRt.x + paramPaneRt.width + 5;
		int height = paramPaneRt.y + paramPaneRt.height + 5;
		setMinSize(width, height);
	}

	protected void onDispose() {
		super.onDispose();

		labelParam.dispose();
	}

	protected void onResize() {
		super.onResize();

		Point size = getSize();
		Rectangle paramTableRt;

		paramTableRt = paramPane.getBounds();
		paramTableRt.width = size.x - paramTableRt.x;
		paramPane.setBounds(paramTableRt);
	}

	protected void hookControl() {
		super.hookControl();

		paramMonitor = new Adapter(){

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
				int type = notification.getEventType();
				int featureId = notification.getFeatureID(TaskModelPackage.class);
				switch(type) {
				case Notification.SET:
				case Notification.ADD:
				case Notification.ADD_MANY:
				case Notification.REMOVE:
				case Notification.REMOVE_MANY:
					setModel(getModel());
					break;
				}
			}

			@Override
			public void setTarget(Notifier newTarget) {
				// TODO Auto-generated method stub
				
			}
		};
	}

	@Override
	protected void hookIntoModel(EObject model) {
		super.hookIntoModel(model);
		
		if(getModel() instanceof ActionElement) {
			ActionElement action = (ActionElement)getModel();
			List<Parameter> paramList = action.getParams();
			for(Parameter param : paramList) {
				param.eAdapters().add(paramMonitor);
			}
		}
	}

	@Override
	protected void unhookFromModel(EObject model) {
		super.unhookFromModel(model);
		
		if(getModel() instanceof ActionElement) {
			ActionElement action = (ActionElement)getModel();
			List<Parameter> paramList = action.getParams();
			for(Parameter param : paramList) {
				param.eAdapters().remove(paramMonitor);
			}
		}
	}

	public void setModel(Object model) {
		super.setModel(model);

		if(model instanceof ActionElement) {
			ActionElement action = (ActionElement)model;
			Table table = (Table)paramTable.getControl();
			table.removeAll();

			EList<Parameter> paramList = action.getParams();
			for(Parameter param : paramList) {
				TableItem item_ = new TableItem(paramTable.getTable(), SWT.NONE);
				item_.setText(new String[]{param.getType(), param.getName()});
				item_.setData(param);
			}
			if((selectedItem >> 32) == TaskModelPackage.ACTION_ELEMENT__PARAMS) {
				int selIdx = (int)(selectedItem & 0xFFFFFFFFL);
				table.setSelection(selIdx);
			}

			boolean enabled = ((ItemElement)model).isIncluded() == false;
			orderUpBtn.setEnabled(enabled);
			orderDnBtn.setEnabled(enabled);
			paramAddBtn.setEnabled(enabled);
			paramRemBtn.setEnabled(enabled);
		}
	}


	public void notifyChanged(Notification notification) {

		Object notifier = notification.getNotifier();
		if(notifier instanceof Parameter) {
			parameterNotifyChanged(notification);
			return;
		}

		int type = notification.getEventType();
		int featureId = notification.getFeatureID(TaskModelPackage.class);
		switch(type) {
		case Notification.SET:
			switch(featureId) {
			case TaskModelPackage.ACTION_ELEMENT__PARAMS:
				Parameter param = (Parameter)notification.getOldValue();
				param.eAdapters().remove(paramMonitor);
				
				param = (Parameter)notification.getNewValue();
				param.eAdapters().add(paramMonitor);
				
				setModel(getModel());
				break;
			default:
				super.notifyChanged(notification);
				break;
			}
			break;
		case Notification.ADD:
			switch(featureId) {
			case TaskModelPackage.ACTION_ELEMENT__PARAMS:
				Parameter param = (Parameter)notification.getNewValue();
				param.eAdapters().add(paramMonitor);
				setModel(getModel());
				break;
			default:
				super.notifyChanged(notification);
				break;
			}
			break;
		case Notification.ADD_MANY:
			switch(featureId) {
			case TaskModelPackage.ACTION_ELEMENT__PARAMS:
				List<Parameter> paramList = (List<Parameter>)notification.getNewValue();
				for(Parameter param : paramList) {
					param.eAdapters().add(paramMonitor);
				}
				setModel(getModel());
				break;
			default:
				super.notifyChanged(notification);
				break;
			}
			break;
		case Notification.REMOVE:
			switch(featureId) {
			case TaskModelPackage.ACTION_ELEMENT__PARAMS:
				Parameter param = (Parameter)notification.getOldValue();
				if(param != null) {
					param.eAdapters().remove(paramMonitor);
				}
				setModel(getModel());
				break;
			default:
				super.notifyChanged(notification);
				break;
			}
			break;
		case Notification.REMOVE_MANY:
			switch(featureId) {
			case TaskModelPackage.ACTION_ELEMENT__PARAMS:
				List<Parameter> paramList = (List<Parameter>)notification.getOldValue();
				for(Parameter param : paramList) {
					param.eAdapters().remove(paramMonitor);
				}
				setModel(getModel());
				break;
			default:
				super.notifyChanged(notification);
				break;
			}
			break;
		case Notification.MOVE:
			switch(featureId) {
			case TaskModelPackage.TASK_ELEMENT__PARAMS:
				setModel(getModel());
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

	public void parameterNotifyChanged(Notification notification) {

		int type = notification.getEventType();
		int featureId = notification.getFeatureID(TaskModelPackage.class);
		switch(type) {
		case Notification.SET:
			switch(featureId) {
			case TaskModelPackage.PARAMETER__TYPE:
				setModel(getModel());
				break;
			default:
				super.notifyChanged(notification);
				break;
			}
			break;
		case Notification.ADD:
			switch(featureId) {
			default:
				super.notifyChanged(notification);
				break;
			}
			break;
		case Notification.ADD_MANY:
			switch(featureId) {
			default:
				super.notifyChanged(notification);
				break;
			}
			break;
		case Notification.REMOVE:
			switch(featureId) {
			default:
				super.notifyChanged(notification);
				break;
			}
			break;
		case Notification.REMOVE_MANY:
			switch(featureId) {
			default:
				super.notifyChanged(notification);
				break;
			}
			break;
		}
	}

	class ActionPropertyCellModifier implements ICellModifier
	{
		private Viewer viewer;

		public ActionPropertyCellModifier(Viewer viewer)
		{
			this.viewer = viewer;
		}

		@Override
		public boolean canModify(Object element, String property)
		{
			Object model = getModel();
			if(model == null || (model instanceof ItemElement) == false) {
				return false;
			}

			if(((ItemElement)getModel()).isIncluded()) {
				return false;
			}
			return true;
		}

		@Override
		public Object getValue(Object element, String property)
		{
			if(null != element && element instanceof Parameter)
			{
				Parameter param = (Parameter)element;

				if("Type".equals(property))
				{
					RTMType type = RTMType.get(param.getType());
					return  type.getValue();
				}
				else if("Name".equals(property))
				{
					return param.getName();
				}
			}

			return null;
		}

		@Override
		public void modify(Object element, String property, Object value)
		{
			if(element instanceof TableItem)
			{
				Parameter param_ = (Parameter)((TableItem)element).getData();
				PropertyContainer prop;

				if("Type".equals(property))
				{
					RTMType type = RTMType.get((Integer)value);
					if(type == null) {
						return;
					}
					prop = new PropertyContainer(PropertyContainer.SET, TaskModelPackage.PARAMETER__TYPE, type.getName(), param_.getType());

					ParameterPropertySource paramSrc = new ParameterPropertySource(param_);
					setValue(prop, paramSrc, "");
				}
				else if("Name".equals(property))
				{
					String name = value.toString().trim();
					if(name.length() > 0) {
						ParameterPropertySource paramSrc = new ParameterPropertySource(param_);
						prop = new PropertyContainer(PropertyContainer.SET, TaskModelPackage.PARAMETER__NAME, value.toString(), param_.getName() );
						setValue(prop, paramSrc, "");
					}
					else {
						prop = new PropertyContainer(PropertyContainer.REMOVE, TaskModelPackage.ACTION_ELEMENT__PARAMS, null, param_ );
						setListValue(prop, "remove parameter");
					}
				}

				setModel(getModel());
			}
		}
	}
}
