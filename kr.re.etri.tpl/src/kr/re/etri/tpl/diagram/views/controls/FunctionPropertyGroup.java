package kr.re.etri.tpl.diagram.views.controls;

import java.util.List;

import kr.re.etri.tpl.diagram.ModelManager;
import kr.re.etri.tpl.diagram.properties.sources.ParameterPropertySource;
import kr.re.etri.tpl.diagram.util.PropertyContainer;
import kr.re.etri.tpl.grammar.RTMType;
import kr.re.etri.tpl.taskmodel.Function;
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
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

public class FunctionPropertyGroup extends ItemElementPropertyGroup {

	private Combo typeCombo;

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

	public FunctionPropertyGroup(Composite parent, int style) {
		super(parent, style);
		// TODO Auto-generated constructor stub
	}

	protected void createFormArea() {
		Rectangle nameLabelRt, nameTextRt;
		Rectangle descLabelRt, descTextRt;
		Rectangle typeComboRt;
		Rectangle rect1, rect2;

		nameLabel = new Label(this, SWT.NONE);
		nameLabel.setText("Name");
		nameLabelRt = new Rectangle(0, 3, 80, 20);
		nameLabel.setBounds(nameLabelRt);

		typeCombo = new Combo(this, SWT.BORDER);
		typeComboRt = new Rectangle(
				nameLabelRt.x + nameLabelRt.width + 10,
				nameLabelRt.y,
				80,
				20);
		typeCombo.setBounds(typeComboRt);

		nameText = new Text(this, SWT.BORDER);
		nameTextRt = new Rectangle(
				typeComboRt.x + typeComboRt.width + 3,
				nameLabelRt.y,
				150,
				20);
		nameText.setBounds(nameTextRt);

		descLabel = new Label(this, SWT.NONE);
		descLabel.setText("Description");
		descLabelRt = new Rectangle(
				nameLabelRt.x,
				nameLabelRt.y + nameLabelRt.height + 5,
				80,
				20);
		descLabel.setBounds(descLabelRt);

		descText = new Text(this, SWT.BORDER|SWT.MULTI|SWT.V_SCROLL);
		descTextRt = new Rectangle(descLabelRt.x + descLabelRt.width + 10,
				descLabelRt.y,
				150,
				60);
		descText.setBounds(descTextRt);

//-----------------------------------------------------------------------------
		
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
				item_.setText(RTMType.getInputTypes());
				TaskModelFactory factory = ModelManager.getFactory();
				Parameter param = factory.createParameter();
				param.setType(RTMType.INT.getName());
				param.setName("param");
				item_.setData(param);

				PropertyContainer prop;
				prop = new PropertyContainer(PropertyContainer.ADD, TaskModelPackage.FUNCTION__PARAMS, param);
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
					prop = new PropertyContainer(PropertyContainer.REMOVE, TaskModelPackage.FUNCTION__PARAMS, null, param);
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
						selectedItem = (((long)TaskModelPackage.FUNCTION__PARAMS) << 32) | ((long)(selIdx_ - 1));
						
						Function funcItem = (Function)getModel();
						funcItem.getParams().move(selIdx_, selIdx_-1);
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
						selectedItem = (((long)TaskModelPackage.FUNCTION__PARAMS) << 32) | ((long)(selIdx_ + 1));
						
						Function funcItem = (Function)getModel();
						funcItem.getParams().move(selIdx_, selIdx_+1);
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
		paramTable.setCellModifier(new PropertyCellModifier(paramTable));
		paramTable.setCellEditors(editors);

		rect2 = rect1;

		int width = paramPaneRt.x + paramPaneRt.width + 5;
		int height = paramPaneRt.y + paramPaneRt.height + 5;
		setMinSize(width, height);
	}

	protected void onDispose() {
		super.onDispose();

		labelParam.dispose();
		typeCombo.dispose();
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


		typeCombo.addFocusListener(new FocusListener(){

			@Override
			public void focusGained(FocusEvent e) {
			}

			@Override
			public void focusLost(FocusEvent e) {
				if(!(getModel() instanceof Function)) {
					return;
				}

				Function item = (Function)getModel();
				if(typeCombo.getText().equals(item.getType()) == false) {

					PropertyContainer prop;
					prop = new PropertyContainer(PropertyContainer.SET, TaskModelPackage.FUNCTION__TYPE, typeCombo.getText(), item.getType());
					setValue(prop, "set function type");

					int featureId = TaskModelPackage.FUNCTION__TYPE;
					
					RTMType type = RTMType.get(typeCombo.getText().trim());
					if(type == null) {
						typeCombo.setText(item.getType());
						return;
					}

					setValue(featureId, type.getName(), "");
				}
			}
		});

		typeCombo.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
			}
			
		});
	}

	@Override
	protected void hookIntoModel(EObject model) {
		super.hookIntoModel(model);
		
		if(getModel() instanceof Function) {
			Function funcItem = (Function)getModel();
			List<Parameter> paramList = funcItem.getParams();
			for(Parameter param : paramList) {
				param.eAdapters().add(paramMonitor);
			}
		}
	}

	@Override
	protected void unhookFromModel(EObject model) {
		super.unhookFromModel(model);
		
		if(getModel() instanceof Function) {
			Function funcItem = (Function)getModel();
			List<Parameter> paramList = funcItem.getParams();
			for(Parameter param : paramList) {
				param.eAdapters().remove(paramMonitor);
			}
		}
	}

	public void setModel(Object model) {
		super.setModel(model);

		if(model instanceof Function) {
			Function funcItem = (Function)model;

			boolean enabled = funcItem.isIncluded() == false;

			typeCombo.removeAll();
			typeCombo.setItems(RTMType.getReturnTypes());
			typeCombo.setText(funcItem.getType());
			typeCombo.setEnabled(enabled);

			Table table = (Table)paramTable.getControl();
			table.removeAll();

			EList<Parameter> paramList = funcItem.getParams();
			for(Parameter param : paramList) {
				TableItem item_ = new TableItem(paramTable.getTable(), SWT.NONE);
				item_.setText(new String[]{param.getType(), param.getName()});
				item_.setData(param);
			}
			if((selectedItem >> 32) == TaskModelPackage.FUNCTION__PARAMS) {
				int selIdx = (int)(selectedItem & 0xFFFFFFFFL);
				table.setSelection(selIdx);
			}

			table.setEnabled(enabled);

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
			case TaskModelPackage.FUNCTION__TYPE:
				setModel(getModel());
				break;
			case TaskModelPackage.FUNCTION__PARAMS:
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
			case TaskModelPackage.FUNCTION__TYPE:
				setModel(getModel());
				break;
			case TaskModelPackage.FUNCTION__PARAMS:
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
			case TaskModelPackage.FUNCTION__TYPE:
				setModel(getModel());
				break;
			case TaskModelPackage.FUNCTION__PARAMS:
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
			case TaskModelPackage.FUNCTION__TYPE:
				setModel(getModel());
				break;
			case TaskModelPackage.FUNCTION__PARAMS:
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
			case TaskModelPackage.FUNCTION__TYPE:
				setModel(getModel());
				break;
			case TaskModelPackage.FUNCTION__PARAMS:
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
			case TaskModelPackage.FUNCTION__PARAMS:
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

	class PropertyCellModifier implements ICellModifier
	{
		private Viewer viewer;

		public PropertyCellModifier(Viewer viewer)
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
					if(type == null) {
						return RTMType.INT.getName();
					}
					return  type.getValue() - 1;	// KJH 20101015, type combo 값이 한칸씩 앞의값이 되는 문제 수정
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
					RTMType type = RTMType.get((Integer)value + 1);
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
						prop = new PropertyContainer(PropertyContainer.REMOVE, TaskModelPackage.FUNCTION__PARAMS, null, param_ );
						setListValue(prop, "remove parameter");
					}
				}

				setModel(getModel());
			}
		}
	}
}
