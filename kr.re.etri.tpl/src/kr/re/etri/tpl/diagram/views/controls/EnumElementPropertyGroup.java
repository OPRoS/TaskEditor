package kr.re.etri.tpl.diagram.views.controls;

import java.util.List;

import kr.re.etri.tpl.diagram.ModelManager;
import kr.re.etri.tpl.diagram.properties.sources.EnumItemPropertySource;
import kr.re.etri.tpl.diagram.util.PropertyContainer;
import kr.re.etri.tpl.taskmodel.ActionElement;
import kr.re.etri.tpl.taskmodel.EnumElement;
import kr.re.etri.tpl.taskmodel.EnumItemElement;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.TaskModelFactory;
import kr.re.etri.tpl.taskmodel.TaskModelPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.viewers.CellEditor;
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

public class EnumElementPropertyGroup extends LinkedElementPropertyGroup {

	private Label labelParam;
	private TableViewer elementTable;
	private Button orderUpBtn;
	private Button orderDnBtn;
	private Button elementAddBtn;
	private Button elementRemBtn;
	private Composite elementPane;
	private Composite buttonPane;
	private long selectedItem = 0;
	
	public EnumElementPropertyGroup(Composite parent, int style) {
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
		labelParam.setText("Element");
		rect1 = new Rectangle(descLabelRt.x, descTextRt.y+descTextRt.height+5,
				80, 20);
		labelParam.setBounds(rect1);
		rect2 = rect1;

		elementPane = new Composite(this, SWT.NONE);

		Rectangle elementPaneRt = new Rectangle(descTextRt.x, rect2.y, descTextRt.width, 110); 
		elementPane.setBounds(elementPaneRt);
		FormLayout layout = new FormLayout();
		elementPane.setLayout(layout);

		buttonPane = new Composite(elementPane, SWT.NONE);
		FormData data = new FormData();
		data.right = new FormAttachment(100,0);
		data.top = new FormAttachment(0,0);
		data.width = 60;
		buttonPane.setLayoutData(data);
		FillLayout fillLayout = new FillLayout(SWT.VERTICAL);
		fillLayout.spacing = 5;
		buttonPane.setLayout(fillLayout);

		elementAddBtn = new Button(buttonPane, SWT.PUSH);
		elementAddBtn.setText("Add");
		elementAddBtn.addListener(SWT.Selection, new Listener()
		{
			@Override
			public void handleEvent(Event event)
			{
				TableItem item_ = new TableItem(elementTable.getTable(), SWT.NONE);
				item_.setText(new String[]{""});
				TaskModelFactory factory = ModelManager.getFactory();
				EnumItemElement enumItem = factory.createEnumItemElement();
				enumItem.setName("New_Element");
				item_.setData(enumItem);

				PropertyContainer prop;
				prop = new PropertyContainer(PropertyContainer.ADD, TaskModelPackage.ENUM_ELEMENT__ENUM_ITEM, enumItem);
				setValue(prop, "");

//				prop = new PropertyContainer(PropertyContainer.SET, TaskModelPackage.PARAMETER__TYPE, "boolean", param.getType());
////				setValue(prop);
//				ParameterPropertySource paramSrc = new ParameterPropertySource(param);
//				paramSrc.setPropertyValue(TaskModelPackage.PARAMETER__TYPE, prop);
			}
		});

		elementRemBtn = new Button(buttonPane, SWT.PUSH);
		elementRemBtn.setText("Remove");
		elementRemBtn.addListener(SWT.Selection, new Listener()
		{
			@Override
			public void handleEvent(Event event)
			{
				StructuredSelection sel = (StructuredSelection)elementTable.getSelection();
				PropertyContainer prop;

				List seList = sel.toList();
				for(int i = 0; i < seList.size(); i += 1) {
					Object enumItem = seList.get(i);
					prop = new PropertyContainer(PropertyContainer.REMOVE, TaskModelPackage.ENUM_ELEMENT__ENUM_ITEM, null, enumItem);
					setListValue(prop, "remove element");
				}
				
				elementTable.remove(sel.toArray());
			}
		});

		orderUpBtn = new Button(buttonPane, SWT.ARROW|SWT.UP);
		orderUpBtn.addListener(SWT.Selection, new Listener()
		{
			@Override
			public void handleEvent(Event event)
			{
				StructuredSelection selection_ = (StructuredSelection)elementTable.getSelection();
				
				if(selection_.size() == 1)
				{
					Table table_ = elementTable.getTable();
					int selIdx_ = table_.getSelectionIndex();
					
					if(selIdx_ > 0)
					{
						selectedItem = (((long)TaskModelPackage.ENUM_ELEMENT__ENUM_ITEM) << 32) | ((long)(selIdx_ - 1));
						
						EnumElement enumModel = (EnumElement)getModel();
						enumModel.getEnumItem().move(selIdx_, selIdx_-1);
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
				StructuredSelection selection_ = (StructuredSelection)elementTable.getSelection();
				
				if(selection_.size() == 1)
				{
					Table table_ = elementTable.getTable();
					int selIdx_ = table_.getSelectionIndex();
					int total_ = table_.getItemCount()-1;
					
					if(selIdx_ < total_)
					{
						selectedItem = (((long)TaskModelPackage.ENUM_ELEMENT__ENUM_ITEM) << 32) | ((long)(selIdx_ + 1));

						EnumElement enumModel = (EnumElement)getModel();
						enumModel.getEnumItem().move(selIdx_, selIdx_+1);
					}
				}
			}
		});
	
		int style = SWT.SELECTED | SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER | SWT.FULL_SELECTION;
		elementTable = new TableViewer(elementPane, style);
		Table table = (Table)elementTable.getControl();
		data = new FormData();
		data.left = new FormAttachment(0,0);
		data.top = new FormAttachment(0,0);
		data.bottom = new FormAttachment(100,0);
		data.right = new FormAttachment(buttonPane,-5);
		table.setLayoutData(data);
		
		String[] columnNames = new String[] {"Element"};
		int[] columnWidths = new int[] {200};
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
		
		CellEditor[] editors = new CellEditor[1];
		editors[0] = new TextCellEditor(table);
		
		elementTable.setColumnProperties(new String[]{"Element"});
		elementTable.setCellModifier(new ActionPropertyCellModifier(elementTable));
		elementTable.setCellEditors(editors);

		rect2 = rect1;
		
		int width = elementPaneRt.x + elementPaneRt.width + 5;
		int height = elementPaneRt.y + elementPaneRt.height + 5;
		setMinSize(width, height);
	}

	protected void onDispose() {
		super.onDispose();

		labelParam.dispose();
	}

	protected void onResize() {
		super.onResize();
		
		Point size = getSize();
		Rectangle elementTableRt;

		elementTableRt = elementPane.getBounds();
		elementTableRt.width = size.x - elementTableRt.x;
		elementPane.setBounds(elementTableRt);
	}

	protected void hookControl() {
		super.hookControl();

	}

	public void setModel(Object model) {
		super.setModel(model);

		if(model instanceof EnumElement) {
			EnumElement enumModel = (EnumElement)model;
			Table table = (Table)elementTable.getControl();
			table.removeAll();

			EList<EnumItemElement> elementList = enumModel.getEnumItem();
			for(EnumItemElement enumItem : elementList) {
				TableItem item_ = new TableItem(elementTable.getTable(), SWT.NONE);
				item_.setText(new String[]{enumItem.getName()});
				item_.setData(enumItem);
			}
			if((selectedItem >> 32) == TaskModelPackage.ENUM_ELEMENT__ENUM_ITEM) {
				int selIdx = (int)(selectedItem & 0xFFFFFFFFL);
				table.setSelection(selIdx);
			}

			boolean enabled = ((ItemElement)model).isIncluded() == false;
			orderUpBtn.setEnabled(enabled);
			orderDnBtn.setEnabled(enabled);
			elementAddBtn.setEnabled(enabled);
			elementRemBtn.setEnabled(enabled);
		}
	}

	public void notifyChanged(Notification notification) {
		int type = notification.getEventType();
		int featureId = notification.getFeatureID(TaskModelPackage.class);
		switch(type) {
		case Notification.SET:
			switch(featureId) {
			default:
				super.notifyChanged(notification);
				break;
			}
			break;
		case Notification.ADD:
			switch(featureId) {
			case TaskModelPackage.ENUM_ELEMENT__ENUM_ITEM:
				setModel(getModel());
				break;
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
			case TaskModelPackage.ENUM_ELEMENT__ENUM_ITEM:
				setModel(getModel());
				break;
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
		case Notification.MOVE:
			switch(featureId) {
			case TaskModelPackage.ENUM_ELEMENT__ENUM_ITEM:
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
			if(null != element && element instanceof EnumItemElement)
			{
				EnumItemElement enumItem = (EnumItemElement)element;

				if("Element".equals(property))
				{
					return enumItem.getName();
				}
			}
			
			return null;
		}

		@Override
		public void modify(Object element, String property, Object value)
		{
			if(element instanceof TableItem)
			{
				EnumItemElement enumItem = (EnumItemElement)((TableItem)element).getData();
				PropertyContainer prop;
				
				if("Element".equals(property))
				{
					String itemName = value.toString().trim();
					if(itemName.length() > 0) {
						if(itemName.equals(enumItem.getName())) {
							return;
						}
						EnumItemPropertySource propSrc = new EnumItemPropertySource(enumItem);
						prop = new PropertyContainer(PropertyContainer.SET, TaskModelPackage.ENUM_ITEM_ELEMENT__NAME, itemName, enumItem.getName() );
						setValue(prop, propSrc, "");
					}
					else {
						prop = new PropertyContainer(PropertyContainer.REMOVE, TaskModelPackage.ENUM_ELEMENT__ENUM_ITEM, null, enumItem );
						setListValue(prop, "remove element");
					}
				}
				
				setModel(getModel());
			}
		}
	}
}
