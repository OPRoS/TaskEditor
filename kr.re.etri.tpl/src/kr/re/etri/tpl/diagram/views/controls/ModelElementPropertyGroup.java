package kr.re.etri.tpl.diagram.views.controls;

import java.util.List;

import kr.re.etri.tpl.grammar.RTMType;
import kr.re.etri.tpl.taskmodel.Constant;
import kr.re.etri.tpl.taskmodel.Direction;
import kr.re.etri.tpl.taskmodel.Function;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.ModelElement;
import kr.re.etri.tpl.taskmodel.Parameter;
import kr.re.etri.tpl.taskmodel.Symbol;
import kr.re.etri.tpl.taskmodel.TaskModelFactory;
import kr.re.etri.tpl.taskmodel.TaskModelPackage;
import kr.re.etri.tpl.diagram.ModelManager;
import kr.re.etri.tpl.diagram.properties.sources.ConstantPropertySource;
import kr.re.etri.tpl.diagram.properties.sources.ModelElementPropertySource;
import kr.re.etri.tpl.diagram.properties.sources.SymbolPropertySource;
import kr.re.etri.tpl.diagram.util.PropertyContainer;

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
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

public class ModelElementPropertyGroup extends ItemElementPropertyGroup {

	private Label labelParam;
	private TableViewer symbolTable, functionTable, constantTable, modelTable;
	private Button symbolUpBtn, functionUpBtn, constantUpBtn, modelUpBtn;
	private Button symbolDnBtn, functionDnBtn, constantDnBtn, modelDnBtn;
	private Button symbolAddBtn, functionAddBtn, constantAddBtn, modelAddBtn;
	private Button symbolRemBtn, functionRemBtn, constantRemBtn, modelRemBtn;
	private Composite elementPane;
	private Composite symbolButtonPane, functionButtonPane, constantButtonPane, modelButtonPane;
	private TabFolder elmtTabFolder;
	private TabItem selectedTabItem;
	private long selectedItem = 0;
	
	private Adapter childModitor;
	public ModelElementPropertyGroup(Composite parent, int style) {
		super(parent, style);
	}

	protected void createFormArea() {
		super.createFormArea();

		Rectangle rect1, rect2;
		Rectangle descLabelRt, descTextRt;

		TabItem functionTab, symbolTab, constantTab, modelTab;

		descLabelRt = descLabel.getBounds();
		descTextRt = descText.getBounds();

		labelParam = new Label(this, SWT.NONE);
		labelParam.setText("Element");
		rect1 = new Rectangle(descLabelRt.x, descTextRt.y+descTextRt.height+5,
				80, 20);
		labelParam.setBounds(rect1);
		rect2 = rect1;

		elementPane = new Composite(this, SWT.NONE);

		Rectangle elementPaneRt = new Rectangle(descTextRt.x, rect2.y, descTextRt.width, 170);

		elementPane.setBounds(elementPaneRt);
		FormLayout layout = new FormLayout();
		elementPane.setLayout(layout);

		elmtTabFolder = new TabFolder(elementPane, SWT.SMOOTH);
		FormData data = new FormData();
		data.left = new FormAttachment(0,0);
		data.right = new FormAttachment(100,0);
		data.top = new FormAttachment(0,0);
		data.bottom = new FormAttachment(100,0);

		elmtTabFolder.setLayoutData(data);

		functionTab = new TabItem(elmtTabFolder, SWT.NONE);
		functionTab.setText("Function");
		functionTab.setControl(getFunctionTabControl(elmtTabFolder));

		symbolTab = new TabItem(elmtTabFolder, SWT.NONE);
		symbolTab.setText("Symbol");
		symbolTab.setControl(getSymbolTabControl(elmtTabFolder));

		constantTab = new TabItem(elmtTabFolder, SWT.NONE);
		constantTab.setText("Constant");
		constantTab.setControl(getConstantTabControl(elmtTabFolder));

		modelTab = new TabItem(elmtTabFolder, SWT.NONE);
		modelTab.setText("Model");
		modelTab.setControl(getModelTabControl(elmtTabFolder));

		elmtTabFolder.setSelection(symbolTab);

		elmtTabFolder.addSelectionListener(new SelectionListener(){

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {


			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				selectedTabItem = (TabItem)e.item;
			}

		});
		rect2 = rect1;

		int width = elementPaneRt.x + elementPaneRt.width + 5;
		int height = elementPaneRt.y + elementPaneRt.height + 5;


		setMinSize(width, height);
	}


	private Control getSymbolTabControl(TabFolder tabFolder){

		Composite symbolTabComposite = new Composite(tabFolder, SWT.NONE);
		symbolTabComposite.setLayout(new FormLayout());
		FormData data = new FormData();
		data.left = new FormAttachment(0,0);
		data.right = new FormAttachment(100,0);
		data.top = new FormAttachment(0,0);
		data.bottom = new FormAttachment(100,0);
		symbolTabComposite.setLayoutData(data);

		getSymbolButtonPane(symbolTabComposite);

		int style = SWT.SELECTED | SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER | SWT.FULL_SELECTION;

		symbolTable = new TableViewer(symbolTabComposite, style);
		Table table = (Table)symbolTable.getControl();
		data = new FormData();
		data.left = new FormAttachment(0,5);
		data.top = new FormAttachment(0,5);
		data.bottom = new FormAttachment(100, -5);
		data.right = new FormAttachment(symbolButtonPane,-5);
		table.setLayoutData(data);

		String[] columnNames = new String[] {"Direction", "Type", "Name" };
		int[] columnWidths = new int[] {70, 70, 120 };
		int[] columnAlignments = new int[] {SWT.LEFT, SWT.LEFT, SWT.LEFT};

		for (int i = 0; i < columnNames.length; i++) {
			TableColumn tableColumn = new TableColumn(table, columnAlignments[i]);
			tableColumn.setText(columnNames[i]);
			tableColumn.setWidth(columnWidths[i]);
		}

		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		CellEditor[] editors = new CellEditor[3];
		int length = Direction.values().length;
		String[] items = new String[length];
		for (int i = 0; i < length; i++) {
			items[i] = Direction.get(i).toString();
		}
		editors[0] = new ComboBoxCellEditor(table, items);
		// KJH 20100916, model\symbol\type
		editors[1] = new ComboBoxCellEditor(table, RTMType.getInputTypes());
//		editors[1] = new TextCellEditor(table);
		editors[2] = new TextCellEditor(table);

		symbolTable.setColumnProperties( columnNames );
		symbolTable.setCellModifier(new ActionPropertyCellModifier(symbolTable));
		symbolTable.setCellEditors(editors);


		return symbolTabComposite;
	}

	private void getSymbolButtonPane(Composite parentComposite){

		symbolButtonPane = new Composite(parentComposite, SWT.NONE);
		FormData data = new FormData();
		data.right = new FormAttachment(100,-5);
		data.top = new FormAttachment(0,5);
		//data.bottom = new FormAttachment(,-5);
		data.width = 60;
		data.height = 110;
		symbolButtonPane.setLayoutData(data);

		FillLayout fillLayout = new FillLayout(SWT.VERTICAL);
		fillLayout.spacing = 5;
		symbolButtonPane.setLayout(fillLayout);

		symbolAddBtn = new Button(symbolButtonPane, SWT.PUSH);
		symbolAddBtn.setText("Add");
		symbolAddBtn.addListener(SWT.Selection, new Listener()
		{
			@Override
			public void handleEvent(Event event)
			{
				TableItem item_ = new TableItem(symbolTable.getTable(), SWT.NONE);
				item_.setText(new String[]{"in", RTMType.INT.getName() ,"New_Symbol"});
				TaskModelFactory factory = ModelManager.getFactory();
				//ModelElement modelElem = factory.createModelElement();
				Symbol symbol = factory.createSymbol();
				symbol.setName("New_Symbol");
				symbol.setDirection(Direction.IN);
				symbol.setType(RTMType.INT.getName());
				item_.setData(symbol);

				PropertyContainer prop;
				prop = new PropertyContainer(PropertyContainer.ADD, TaskModelPackage.MODEL_ELEMENT__SYMBOLS, symbol);
				setListValue(prop, "add symbol");

			}
		});

		symbolRemBtn = new Button(symbolButtonPane, SWT.PUSH);
		symbolRemBtn.setText("Remove");
		symbolRemBtn.addListener(SWT.Selection, new Listener()
		{
			@Override
			public void handleEvent(Event event)
			{
				StructuredSelection sel = (StructuredSelection)symbolTable.getSelection();
				PropertyContainer prop;

				List seList = sel.toList();
				for(int i = 0; i < seList.size(); i += 1) {
					Object symbol = seList.get(i);
					prop = new PropertyContainer(PropertyContainer.REMOVE, TaskModelPackage.MODEL_ELEMENT__SYMBOLS, null, symbol);
					setListValue(prop, "remove symbol");
				}

				symbolTable.remove(sel.toArray());
			}
		});

		symbolUpBtn = new Button(symbolButtonPane, SWT.ARROW|SWT.UP);
		symbolUpBtn.addListener(SWT.Selection, new Listener()
		{
			@Override
			public void handleEvent(Event event)
			{
				StructuredSelection selection_ = (StructuredSelection)symbolTable.getSelection();

				if(selection_.size() == 1)
				{
					Table table_ = symbolTable.getTable();
					int selIdx_ = table_.getSelectionIndex();

					if(selIdx_ > 0)
					{
						selectedItem = (((long)TaskModelPackage.MODEL_ELEMENT__SYMBOLS) << 32) | ((long)(selIdx_ - 1)); 

						ModelElement modelItem = (ModelElement)getModel();
						modelItem.getSymbols().move(selIdx_, selIdx_-1);
					}
				}
			}
		});

		symbolDnBtn = new Button(symbolButtonPane, SWT.ARROW|SWT.DOWN);
		symbolDnBtn.addListener(SWT.Selection, new Listener()
		{
			@Override
			public void handleEvent(Event event)
			{
				StructuredSelection selection_ = (StructuredSelection)symbolTable.getSelection();

				if(selection_.size() == 1)
				{
					Table table_ = symbolTable.getTable();
					int selIdx_ = table_.getSelectionIndex();
					int total_ = table_.getItemCount()-1;

					if(selIdx_ < total_)
					{
						selectedItem = (((long)TaskModelPackage.MODEL_ELEMENT__SYMBOLS) << 32) | ((long)(selIdx_ + 1));

						ModelElement modelItem = (ModelElement)getModel();
						modelItem.getSymbols().move(selIdx_, selIdx_+1);
					}
				}
			}
		});

	}

	private Control getFunctionTabControl(TabFolder tabFolder){

		Composite functionTabComposite = new Composite(tabFolder, SWT.NONE);
		functionTabComposite.setLayout(new FormLayout());
		FormData data = new FormData();
		data.left = new FormAttachment(0,0);
		data.right = new FormAttachment(100,0);
		data.top = new FormAttachment(0,0);
		data.bottom = new FormAttachment(100,0);
		functionTabComposite.setLayoutData(data);

		getFunctionButtonPane(functionTabComposite);

		int style = SWT.SELECTED | SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER | SWT.FULL_SELECTION;

		functionTable = new TableViewer(functionTabComposite, style);
		Table table = (Table)functionTable.getControl();
		data = new FormData();
		data.left = new FormAttachment(0,5);
		data.top = new FormAttachment(0,5);
		data.bottom = new FormAttachment(100, -5);
		data.right = new FormAttachment(functionButtonPane,-5);
		table.setLayoutData(data);

		String[] columnNames = new String[] {"Signature" };
		int[] columnWidths = new int[] { 200 };
		int[] columnAlignments = new int[] {SWT.LEFT};

		for (int i = 0; i < columnNames.length; i++) {
			TableColumn tableColumn = new TableColumn(table, columnAlignments[i]);
			tableColumn.setText(columnNames[i]);
			tableColumn.setWidth(columnWidths[i]);
		}

		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		functionTable.setColumnProperties( columnNames );
//		functionTable.setCellModifier(new ActionPropertyCellModifier(functionTable));
//		CellEditor[] editors = new CellEditor[]{new TextCellEditor(table)};
//		functionTable.setCellEditors(editors);

		return functionTabComposite;
	}


	private void getFunctionButtonPane(Composite parentComposite){

		functionButtonPane = new Composite(parentComposite, SWT.NONE);
		FormData data = new FormData();
		data.right = new FormAttachment(100,-5);
		data.top = new FormAttachment(0,5);
		//data.bottom = new FormAttachment(,-5);
		data.width = 60;
		data.height = 110;
		functionButtonPane.setLayoutData(data);

		FillLayout fillLayout = new FillLayout(SWT.VERTICAL);
		fillLayout.spacing = 5;
		functionButtonPane.setLayout(fillLayout);

		functionAddBtn = new Button(functionButtonPane, SWT.PUSH);
		functionAddBtn.setText("Add");
		functionAddBtn.addListener(SWT.Selection, new Listener()
		{
			@Override
			public void handleEvent(Event event)
			{
				TaskModelFactory factory = ModelManager.getFactory();
				//ModelElement modelElem = factory.createModelElement();
				Function function = factory.createFunction();
				function.setType(RTMType.VOID.getName());
				function.setName("New_Function");

				PropertyContainer prop;
				prop = new PropertyContainer(PropertyContainer.ADD, TaskModelPackage.MODEL_ELEMENT__FUNCTIONS, function);
				setListValue(prop, "add function");
			}
		});

		functionRemBtn = new Button(functionButtonPane, SWT.PUSH);
		functionRemBtn.setText("Remove");
		functionRemBtn.addListener(SWT.Selection, new Listener()
		{
			@Override
			public void handleEvent(Event event)
			{
				StructuredSelection sel = (StructuredSelection)functionTable.getSelection();
				PropertyContainer prop;

				List seList = sel.toList();
				for(int i = 0; i < seList.size(); i += 1) {
					Object function = seList.get(i);
					prop = new PropertyContainer(PropertyContainer.REMOVE, TaskModelPackage.MODEL_ELEMENT__FUNCTIONS, null, function);
					setListValue(prop, "remove function");
				}

				functionTable.remove(sel.toArray());
			}
		});

		functionUpBtn = new Button(functionButtonPane, SWT.ARROW|SWT.UP);
		functionUpBtn.addListener(SWT.Selection, new Listener()
		{
			@Override
			public void handleEvent(Event event)
			{
				StructuredSelection selection_ = (StructuredSelection)functionTable.getSelection();

				if(selection_.size() == 1)
				{
					Table table_ = functionTable.getTable();
					int selIdx_ = table_.getSelectionIndex();

					if(selIdx_ > 0)
					{
						selectedItem = (((long)TaskModelPackage.MODEL_ELEMENT__FUNCTIONS) << 32) | ((long)(selIdx_ - 1));

						ModelElement modelItem = (ModelElement)getModel();
						modelItem.getFunctions().move(selIdx_, selIdx_-1);
					}
				}
			}
		});

		functionDnBtn = new Button(functionButtonPane, SWT.ARROW|SWT.DOWN);
		functionDnBtn.addListener(SWT.Selection, new Listener()
		{
			@Override
			public void handleEvent(Event event)
			{
				StructuredSelection selection_ = (StructuredSelection)functionTable.getSelection();

				if(selection_.size() == 1)
				{
					Table table_ = functionTable.getTable();
					int selIdx_ = table_.getSelectionIndex();
					int total_ = table_.getItemCount()-1;

					if(selIdx_ < total_)
					{
						selectedItem = (((long)TaskModelPackage.MODEL_ELEMENT__FUNCTIONS) << 32) | ((long)(selIdx_ + 1));

						ModelElement modelItem = (ModelElement)getModel();
						modelItem.getFunctions().move(selIdx_, selIdx_+1);
					}
				}
			}
		});

	}

	private Control getConstantTabControl(TabFolder tabFolder){

		Composite constantTabComposite = new Composite(tabFolder, SWT.NONE);
		constantTabComposite.setLayout(new FormLayout());
		FormData data = new FormData();
		data.left = new FormAttachment(0,0);
		data.right = new FormAttachment(100,0);
		data.top = new FormAttachment(0,0);
		data.bottom = new FormAttachment(100,0);
		constantTabComposite.setLayoutData(data);

		getConstantButtonPane(constantTabComposite);

		int style = SWT.SELECTED | SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER | SWT.FULL_SELECTION;

		constantTable = new TableViewer(constantTabComposite, style);
		Table table = (Table)constantTable.getControl();
		data = new FormData();
		data.left = new FormAttachment(0,5);
		data.top = new FormAttachment(0,5);
		data.bottom = new FormAttachment(100, -5);
		data.right = new FormAttachment(constantButtonPane,-5);
		table.setLayoutData(data);

		String[] columnNames = new String[] {"Type", "Name", "Initialize" };
		int[] columnWidths = new int[] {80, 100, 300 };
		int[] columnAlignments = new int[] {SWT.LEFT, SWT.LEFT, SWT.LEFT};

		for (int i = 0; i < columnNames.length; i++) {
			TableColumn tableColumn = new TableColumn(table, columnAlignments[i]);
			tableColumn.setText(columnNames[i]);
			tableColumn.setWidth(columnWidths[i]);
		}

		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		CellEditor[] editors = new CellEditor[3];
		// KJH 20100916, model\const\type
		editors[0] = new ComboBoxCellEditor(table, RTMType.getInputTypes());
//		editors[0] = new TextCellEditor(table);
		editors[1] = new TextCellEditor(table);
		editors[2] = new TextCellEditor(table);

		constantTable.setColumnProperties( columnNames );
		constantTable.setCellModifier(new ActionPropertyCellModifier(constantTable));
		constantTable.setCellEditors(editors);


		return constantTabComposite;
	}


	private void getConstantButtonPane(Composite parentComposite){

		constantButtonPane = new Composite(parentComposite, SWT.NONE);
		FormData data = new FormData();
		data.right = new FormAttachment(100,-5);
		data.top = new FormAttachment(0,5);
		//data.bottom = new FormAttachment(,-5);
		data.width = 60;
		data.height = 110;
		constantButtonPane.setLayoutData(data);

		FillLayout fillLayout = new FillLayout(SWT.VERTICAL);
		fillLayout.spacing = 5;
		constantButtonPane.setLayout(fillLayout);

		constantAddBtn = new Button(constantButtonPane, SWT.PUSH);
		constantAddBtn.setText("Add");
		constantAddBtn.addListener(SWT.Selection, new Listener()
		{
			@Override
			public void handleEvent(Event event)
			{
				TableItem item_ = new TableItem(constantTable.getTable(), SWT.NONE);
				item_.setText(new String[]{"in", RTMType.INT.getName() ,"New_Constant"});
				TaskModelFactory factory = ModelManager.getFactory();
				//ModelElement modelElem = factory.createModelElement();
				Constant constant = factory.createConstant();
				constant.setType(RTMType.INT.getName());
				constant.setName("New_Constant");
				constant.setInitValue("0");

				item_.setData(constant);

				PropertyContainer prop;
				prop = new PropertyContainer(PropertyContainer.ADD, TaskModelPackage.MODEL_ELEMENT__CONSTANTS, constant);
				setListValue(prop, "add constant");

			}
		});

		constantRemBtn = new Button(constantButtonPane, SWT.PUSH);
		constantRemBtn.setText("Remove");
		constantRemBtn.addListener(SWT.Selection, new Listener()
		{
			@Override
			public void handleEvent(Event event)
			{
				StructuredSelection sel = (StructuredSelection)constantTable.getSelection();
				PropertyContainer prop;

				List seList = sel.toList();
				for(int i = 0; i < seList.size(); i += 1) {
					Object constant = seList.get(i);
					prop = new PropertyContainer(PropertyContainer.REMOVE, TaskModelPackage.MODEL_ELEMENT__CONSTANTS, null, constant);
					setListValue(prop, "remove constant");
				}

				constantTable.remove(sel.toArray());
			}
		});

		constantUpBtn = new Button(constantButtonPane, SWT.ARROW|SWT.UP);
		constantUpBtn.addListener(SWT.Selection, new Listener()
		{
			@Override
			public void handleEvent(Event event)
			{
				StructuredSelection selection_ = (StructuredSelection)constantTable.getSelection();

				if(selection_.size() == 1)
				{
					Table table_ = constantTable.getTable();
					int selIdx_ = table_.getSelectionIndex();

					if(selIdx_ > 0)
					{
						selectedItem = (((long)TaskModelPackage.MODEL_ELEMENT__CONSTANTS) << 32) | ((long)(selIdx_ - 1));

						ModelElement modelItem = (ModelElement)getModel();
						modelItem.getConstants().move(selIdx_, selIdx_-1);
					}
				}
			}
		});

		constantDnBtn = new Button(constantButtonPane, SWT.ARROW|SWT.DOWN);
		constantDnBtn.addListener(SWT.Selection, new Listener()
		{
			@Override
			public void handleEvent(Event event)
			{
				StructuredSelection selection_ = (StructuredSelection)constantTable.getSelection();

				if(selection_.size() == 1)
				{
					Table table_ = constantTable.getTable();
					int selIdx_ = table_.getSelectionIndex();
					int total_ = table_.getItemCount()-1;

					if(selIdx_ < total_)
					{
						selectedItem = (((long)TaskModelPackage.MODEL_ELEMENT__CONSTANTS) << 32) | ((long)(selIdx_ + 1));

						ModelElement modelItem = (ModelElement)getModel();
						modelItem.getConstants().move(selIdx_, selIdx_+1);
					}
				}
			}
		});

	}

	private Control getModelTabControl(TabFolder tabFolder){

		Composite modelTabComposite = new Composite(tabFolder, SWT.NONE);
		modelTabComposite.setLayout(new FormLayout());
		FormData data = new FormData();
		data.left = new FormAttachment(0,0);
		data.right = new FormAttachment(100,0);
		data.top = new FormAttachment(0,0);
		data.bottom = new FormAttachment(100,0);
		modelTabComposite.setLayoutData(data);

		getModelButtonPane(modelTabComposite);

		int style = SWT.SELECTED | SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER | SWT.FULL_SELECTION;

		modelTable = new TableViewer(modelTabComposite, style);
		Table table = (Table)modelTable.getControl();
		data = new FormData();
		data.left = new FormAttachment(0,5);
		data.top = new FormAttachment(0,5);
		data.bottom = new FormAttachment(100, -5);
		data.right = new FormAttachment(modelButtonPane,-5);
		table.setLayoutData(data);

		String[] columnNames = new String[] {"Name"};
		int[] columnWidths = new int[] {120 };
		int[] columnAlignments = new int[] {SWT.LEFT};

		for (int i = 0; i < columnNames.length; i++) {
			TableColumn tableColumn = new TableColumn(table, columnAlignments[i]);
			tableColumn.setText(columnNames[i]);
			tableColumn.setWidth(columnWidths[i]);
		}

		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		CellEditor[] editors = new CellEditor[]{ new TextCellEditor(table)	};

		modelTable.setColumnProperties( columnNames );
		modelTable.setCellModifier(new ActionPropertyCellModifier(modelTable));
		modelTable.setCellEditors(editors);


		return modelTabComposite;
	}


	private void getModelButtonPane(Composite parentComposite){

		modelButtonPane = new Composite(parentComposite, SWT.NONE);
		FormData data = new FormData();
		data.right = new FormAttachment(100,-5);
		data.top = new FormAttachment(0,5);
		//data.bottom = new FormAttachment(,-5);
		data.width = 60;
		data.height = 110;
		modelButtonPane.setLayoutData(data);

		FillLayout fillLayout = new FillLayout(SWT.VERTICAL);
		fillLayout.spacing = 5;
		modelButtonPane.setLayout(fillLayout);

		modelAddBtn = new Button(modelButtonPane, SWT.PUSH);
		modelAddBtn.setText("Add");
		modelAddBtn.addListener(SWT.Selection, new Listener()
		{
			@Override
			public void handleEvent(Event event)
			{
				TableItem item_ = new TableItem(modelTable.getTable(), SWT.NONE);
				item_.setText(new String[]{"New_Model"});
				TaskModelFactory factory = ModelManager.getFactory();

				ModelElement model = factory.createModelElement();
				model.setName("New_Model");

				item_.setData(model);

				PropertyContainer prop;
				prop = new PropertyContainer(PropertyContainer.ADD, TaskModelPackage.MODEL_ELEMENT__MODELS, model);
				setListValue(prop, "add model");

			}
		});

		modelRemBtn = new Button(modelButtonPane, SWT.PUSH);
		modelRemBtn.setText("Remove");
		modelRemBtn.addListener(SWT.Selection, new Listener()
		{
			@Override
			public void handleEvent(Event event)
			{
				StructuredSelection sel = (StructuredSelection)modelTable.getSelection();
				PropertyContainer prop;

				List seList = sel.toList();
				for(int i = 0; i < seList.size(); i += 1) {
					Object model = seList.get(i);
					prop = new PropertyContainer(PropertyContainer.REMOVE, TaskModelPackage.MODEL_ELEMENT__MODELS, null, model);
					setListValue(prop, "remove model");
				}

				modelTable.remove(sel.toArray());
			}
		});

		modelUpBtn = new Button(modelButtonPane, SWT.ARROW|SWT.UP);
		modelUpBtn.addListener(SWT.Selection, new Listener()
		{
			@Override
			public void handleEvent(Event event)
			{
				StructuredSelection selection_ = (StructuredSelection)modelTable.getSelection();

				if(selection_.size() == 1)
				{
					Table table_ = modelTable.getTable();
					int selIdx_ = table_.getSelectionIndex();

					if(selIdx_ > 0)
					{
						ModelElement modelItem = (ModelElement)getModel();
						modelItem.getModels().move(selIdx_, selIdx_-1);
						
						selectedItem = (((long)TaskModelPackage.MODEL_ELEMENT__MODELS) << 32) | ((long)(selIdx_ - 1));
					}
				}
			}
		});

		modelDnBtn = new Button(modelButtonPane, SWT.ARROW|SWT.DOWN);
		modelDnBtn.addListener(SWT.Selection, new Listener()
		{
			@Override
			public void handleEvent(Event event)
			{
				StructuredSelection selection_ = (StructuredSelection)modelTable.getSelection();

				if(selection_.size() == 1)
				{
					Table table_ = modelTable.getTable();
					int selIdx_ = table_.getSelectionIndex();
					int total_ = table_.getItemCount()-1;

					if(selIdx_ < total_)
					{
						selectedItem = (((long)TaskModelPackage.MODEL_ELEMENT__MODELS) << 32) | ((long)(selIdx_ + 1));

						ModelElement modelItem = (ModelElement)getModel();
						modelItem.getModels().move(selIdx_, selIdx_+1);
					}
				}
			}
		});

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

		childModitor = new Adapter(){

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
		
		if(getModel() instanceof ModelElement) {
			ModelElement modelElmt = (ModelElement)getModel();
			List<Symbol> symList = modelElmt.getSymbols();
			for(Symbol symItem : symList) {
				symItem.eAdapters().add(childModitor);
			}
			List<Constant> constList = modelElmt.getConstants();
			for(Constant constItem : constList) {
				constItem.eAdapters().add(childModitor);
			}
			List<Function> funcList = modelElmt.getFunctions();
			for(Function funcItem : funcList) {
				funcItem.eAdapters().add(childModitor);
			}
			List<ModelElement> modleList = modelElmt.getModels();
			for(ModelElement modelItem : modleList) {
				modelItem.eAdapters().add(childModitor);
			}
		}
	}

	@Override
	protected void unhookFromModel(EObject model) {
		super.unhookFromModel(model);
		
		if(getModel() instanceof ModelElement) {
			ModelElement modelElmt = (ModelElement)getModel();
			List<Symbol> symList = modelElmt.getSymbols();
			for(Symbol symItem : symList) {
				symItem.eAdapters().remove(childModitor);
			}
			List<Constant> constList = modelElmt.getConstants();
			for(Constant constItem : constList) {
				constItem.eAdapters().remove(childModitor);
			}
			List<Function> funcList = modelElmt.getFunctions();
			for(Function funcItem : funcList) {
				funcItem.eAdapters().remove(childModitor);
			}
			List<ModelElement> modleList = modelElmt.getModels();
			for(ModelElement modelItem : modleList) {
				modelItem.eAdapters().remove(childModitor);
			}
		}
	}

	public void setModel(Object model) {
		super.setModel(model);

		if(model instanceof ModelElement) {

			ModelElement modelModel = (ModelElement)model;
			boolean enabled = modelModel.isIncluded() == false;

			TabItem tabItems[] = elmtTabFolder.getItems();
			for(int idx = 0; idx < tabItems.length; idx += 1) {
				if("Symbol".equals(tabItems[idx].getText())) {
					Table table = (Table)symbolTable.getControl();
					table.removeAll();

					EList<Symbol> symbolList = modelModel.getSymbols();

					for(Symbol symbolItem : symbolList) {
						TableItem item_ = new TableItem(table, SWT.NONE);
						item_.setText(	new String[]{	symbolItem.getDirection().toString(),
											symbolItem.getType(), symbolItem.getName()}	);
						item_.setData(symbolItem);
					}

					if((selectedItem >> 32) == TaskModelPackage.MODEL_ELEMENT__SYMBOLS) {
						int selIdx = (int)(selectedItem & 0xFFFFFFFFL);
						table.setSelection(selIdx);
					}
					
					symbolUpBtn.setEnabled(enabled);
					symbolDnBtn.setEnabled(enabled);
					symbolAddBtn.setEnabled(enabled);
					symbolRemBtn.setEnabled(enabled);
				}
				else if("Constant".equals(tabItems[idx].getText())) {
					Table table = (Table)constantTable.getControl();
					table.removeAll();

					EList<Constant> constList = modelModel.getConstants();

					for(Constant constItem : constList) {
						TableItem item_ = new TableItem(table, SWT.NONE);
						item_.setText(	new String[]{	constItem.getType(), constItem.getName(), constItem.getInitValue() });
						item_.setData(constItem);
					}
					if((selectedItem >> 32) == TaskModelPackage.MODEL_ELEMENT__CONSTANTS) {
						int selIdx = (int)(selectedItem & 0xFFFFFFFFL);
						table.setSelection(selIdx);
					}

					constantUpBtn.setEnabled(enabled);
					constantDnBtn.setEnabled(enabled);
					constantAddBtn.setEnabled(enabled);
					constantRemBtn.setEnabled(enabled);
				}
				else if("Function".equals(tabItems[idx].getText())) {
					Table table = (Table)functionTable.getControl();
					table.removeAll();

					StringBuilder sb = new StringBuilder();
					EList<Function> funcList = modelModel.getFunctions();
					for(Function funcItem : funcList){
						String funcType = funcItem.getType();
						String funcName = funcItem.getName();

						sb.setLength(0);
						sb.append(funcType).append(" ");
						sb.append(funcName).append(" ");
						sb.append("(");
						List<Parameter> paramList = funcItem.getParams();
						for(int pIdx = 0; pIdx < paramList.size(); pIdx += 1) {
							Parameter param = paramList.get(pIdx);
							if(pIdx > 0) {
								sb.append(", ");
							}
							sb.append(param.getType()).append(" ");
							sb.append(param.getName());
						}
						sb.append(")");

						TableItem item_ = new TableItem(table, SWT.NONE);
						item_.setText(	new String[]{ sb.toString() });
						item_.setData(funcItem);
					}
					if((selectedItem >> 32) == TaskModelPackage.MODEL_ELEMENT__FUNCTIONS) {
						int selIdx = (int)(selectedItem & 0xFFFFFFFFL);
						table.setSelection(selIdx);
					}

					functionUpBtn.setEnabled(enabled);
					functionDnBtn.setEnabled(enabled);
					functionAddBtn.setEnabled(enabled);
					functionRemBtn.setEnabled(enabled);
				}
				else if("Model".equals(tabItems[idx].getText())) {
					Table table = (Table)modelTable.getControl();
					table.removeAll();

					EList<ModelElement> modelList = modelModel.getModels();
					for(ModelElement modelItem : modelList){
						String modelName = modelItem.getName();

						TableItem item_ = new TableItem(table, SWT.NONE);
						item_.setText(	new String[]{ modelName });
						item_.setData(modelItem);
					}
					if((selectedItem >> 32) == TaskModelPackage.MODEL_ELEMENT__MODELS) {
						int selIdx = (int)(selectedItem & 0xFFFFFFFFL);
						table.setSelection(selIdx);
					}

					modelUpBtn.setEnabled(enabled);
					modelDnBtn.setEnabled(enabled);
					modelAddBtn.setEnabled(enabled);
					modelRemBtn.setEnabled(enabled);
				}
			}
		}
	}

	public void notifyChanged(Notification notification) {

		Object notifier = notification.getNotifier();
		if(notifier instanceof Symbol) {
			symbolNotifyChanged(notification);
			return;
		}
		if(notifier instanceof Constant) {
			constantNotifyChanged(notification);
			return;
		}
		if(notifier instanceof Function) {
			functionNotifyChanged(notification);
			return;
		}
		if((notifier instanceof ModelElement) && (notifier != getModel())) {
			modelNotifyChanged(notification);
			return;
		}

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
			case TaskModelPackage.MODEL_ELEMENT__SYMBOLS:
			case TaskModelPackage.MODEL_ELEMENT__CONSTANTS:
			case TaskModelPackage.MODEL_ELEMENT__FUNCTIONS:
			case TaskModelPackage.MODEL_ELEMENT__MODELS:
				setModel(getModel());
				break;
			default:
				super.notifyChanged(notification);
				break;
			}
			break;
		case Notification.ADD_MANY:
			switch(featureId) {
			case TaskModelPackage.MODEL_ELEMENT__SYMBOLS:
			case TaskModelPackage.MODEL_ELEMENT__CONSTANTS:
			case TaskModelPackage.MODEL_ELEMENT__FUNCTIONS:
			case TaskModelPackage.MODEL_ELEMENT__MODELS:
				setModel(getModel());
				break;
			default:
				super.notifyChanged(notification);
				break;
			}
			break;
		case Notification.REMOVE:
			switch(featureId) {
			case TaskModelPackage.MODEL_ELEMENT__SYMBOLS:
			case TaskModelPackage.MODEL_ELEMENT__CONSTANTS:
			case TaskModelPackage.MODEL_ELEMENT__FUNCTIONS:
			case TaskModelPackage.MODEL_ELEMENT__MODELS:
				setModel(getModel());
				break;
			default:
				super.notifyChanged(notification);
				break;
			}
			break;
		case Notification.REMOVE_MANY:
			switch(featureId) {
			case TaskModelPackage.MODEL_ELEMENT__SYMBOLS:
			case TaskModelPackage.MODEL_ELEMENT__CONSTANTS:
			case TaskModelPackage.MODEL_ELEMENT__FUNCTIONS:
			case TaskModelPackage.MODEL_ELEMENT__MODELS:
				setModel(getModel());
				break;
			default:
				super.notifyChanged(notification);
				break;
			}
			break;
		case Notification.MOVE:
			switch(featureId) {
			case TaskModelPackage.MODEL_ELEMENT__SYMBOLS:
			case TaskModelPackage.MODEL_ELEMENT__CONSTANTS:
			case TaskModelPackage.MODEL_ELEMENT__FUNCTIONS:
			case TaskModelPackage.MODEL_ELEMENT__MODELS:
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

	public void symbolNotifyChanged(Notification notification) {

		int type = notification.getEventType();
		int featureId = notification.getFeatureID(TaskModelPackage.class);
		switch(type) {
		case Notification.SET:
			switch(featureId) {
			case TaskModelPackage.SYMBOL__DIRECTION:
				setModel(getModel());
				break;
			case TaskModelPackage.SYMBOL__TYPE:
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

	public void constantNotifyChanged(Notification notification) {

		int type = notification.getEventType();
		int featureId = notification.getFeatureID(TaskModelPackage.class);
		switch(type) {
		case Notification.SET:
			switch(featureId) {
			case TaskModelPackage.CONSTANT__TYPE:
				setModel(getModel());
				break;
			case TaskModelPackage.CONSTANT__INIT_VALUE:
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

	public void functionNotifyChanged(Notification notification) {

		int type = notification.getEventType();
		int featureId = notification.getFeatureID(TaskModelPackage.class);
		switch(type) {
		case Notification.SET:
			switch(featureId) {
			case TaskModelPackage.FUNCTION__TYPE:
				setModel(getModel());
				break;
			case TaskModelPackage.FUNCTION__PARAMS:
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
		}
	}

	public void modelNotifyChanged(Notification notification) {

		int type = notification.getEventType();
		int featureId = notification.getFeatureID(TaskModelPackage.class);
		switch(type) {
		case Notification.SET:
			switch(featureId) {
			case TaskModelPackage.MODEL_ELEMENT__SYMBOLS:
				setModel(getModel());
				break;
			case TaskModelPackage.MODEL_ELEMENT__CONSTANTS:
				setModel(getModel());
				break;
			case TaskModelPackage.MODEL_ELEMENT__FUNCTIONS:
				setModel(getModel());
				break;
			case TaskModelPackage.MODEL_ELEMENT__MODELS:
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
		case Notification.MOVE:
			switch(featureId) {
			case TaskModelPackage.MODEL_ELEMENT__SYMBOLS:
			case TaskModelPackage.MODEL_ELEMENT__CONSTANTS:
			case TaskModelPackage.MODEL_ELEMENT__FUNCTIONS:
			case TaskModelPackage.MODEL_ELEMENT__MODELS:
				setModel(getModel());
				break;
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
			if(element == null) {
				return null;
			}
			
			if(element instanceof Symbol)
			{

				Symbol symbol = (Symbol)element;

				if("Direction".equals(property))
				{
					if( symbol.getDirection() == Direction.IN )
						return 0;
					else if( symbol.getDirection() == Direction.OUT )
						return 1;
				}
				if("Type".equals(property)){
					// KJH 20100916, 콤보박스 대체에 따른 수정
					return RTMType.get(symbol.getType()).getValue() - 1;
				}

				if("Name".equals(property)){
					return symbol.getName();
				}
			}
			else if(element instanceof Constant) {
				Constant constItem = (Constant)element;
				if("Type".equals(property)) {
					// KJH 20100916, 콤보박스 대체에 따른 수정
					return RTMType.get(constItem.getType()).getValue() - 1;
				}
				else if("Name".equals(property)) {
					return constItem.getName();
				}
				else if("Initialize".equals(property)) {
					return constItem.getInitValue();
				}				
			}
			else if(element instanceof Function) {
				
			}
			else if(element instanceof ModelElement)
			{

				ModelElement model = (ModelElement)element;

				if("Name".equals(property)){
					return model.getName();
				}
			}

			return null;
		}

		@Override
		public void modify(Object element, String property, Object value)
		{
			if(element instanceof TableItem)
			{
				Object item = ((TableItem)element).getData();

				if( item instanceof Symbol ){
					Symbol symbol = (Symbol) item;
					PropertyContainer prop;

					if("Direction".equals(property))
					{
						SymbolPropertySource propSrc = new SymbolPropertySource(symbol);
						prop = new PropertyContainer(PropertyContainer.SET, TaskModelPackage.SYMBOL__DIRECTION, Direction.get(((Integer)value).intValue()), symbol.getDirection());
						setValue(prop, propSrc, "");
					}
					else if("Type".equals(property)) {
						SymbolPropertySource propSrc = new SymbolPropertySource(symbol);
						// KJH 20100916, 콤보박스로 변경에 따른 수정
						prop = new PropertyContainer(PropertyContainer.SET, TaskModelPackage.SYMBOL__TYPE, RTMType.get(((Integer)value).intValue() + 1).getName(), symbol.getType());
						setValue(prop, propSrc, "");
					}
					else if("Name".equals(property)) {
						String itemName = value.toString().trim();
						if(itemName.length() > 0) {
							SymbolPropertySource propSrc = new SymbolPropertySource(symbol);
							prop = new PropertyContainer(PropertyContainer.SET, TaskModelPackage.SYMBOL__NAME, value, symbol.getName() );
							setValue(prop, propSrc, "");
						}
						else {
							prop = new PropertyContainer(PropertyContainer.REMOVE, TaskModelPackage.MODEL_ELEMENT__SYMBOLS, null, symbol );
							setListValue(prop, "remove symbol");
						}
					}
				}
				else if( item instanceof Constant ){
					Constant constItem = (Constant) item;
					PropertyContainer prop;

					if("Type".equals(property)) {
						ConstantPropertySource propSrc = new ConstantPropertySource(constItem);
						prop = new PropertyContainer(PropertyContainer.SET, TaskModelPackage.CONSTANT__TYPE, RTMType.get(((Integer)value).intValue() + 1).getName(), constItem.getType());
						setValue(prop, propSrc, "");
					}
					else if("Name".equals(property)) {
						String itemName = value.toString().trim();
						if(itemName.length() > 0) {
							ConstantPropertySource propSrc = new ConstantPropertySource(constItem);
							prop = new PropertyContainer(PropertyContainer.SET, TaskModelPackage.CONSTANT__NAME, value, constItem.getName() );
							setValue(prop, propSrc, "");
						}
						else {
							prop = new PropertyContainer(PropertyContainer.REMOVE, TaskModelPackage.MODEL_ELEMENT__CONSTANTS, null, constItem );
							setListValue(prop, "remove symbol");
						}
					}
					else if("Initialize".equals(property)) {
						ConstantPropertySource propSrc = new ConstantPropertySource(constItem);
						prop = new PropertyContainer(PropertyContainer.SET, TaskModelPackage.CONSTANT__INIT_VALUE, value, constItem.getType());
						setValue(prop, propSrc, "");
					}
				}
				else if( item instanceof ModelElement ){

					ModelElement model = (ModelElement) item;
					PropertyContainer prop;

					if("Name".equals(property)) {
						String itemName = value.toString().trim();
						if(itemName.length() > 0) {
							ModelElementPropertySource propSrc = new ModelElementPropertySource(model);
							prop = new PropertyContainer(PropertyContainer.SET, TaskModelPackage.MODEL_ELEMENT__NAME, value, model.getName() );
							setValue(prop, propSrc, "");
						}
						else {
							prop = new PropertyContainer(PropertyContainer.REMOVE, TaskModelPackage.MODEL_ELEMENT__MODELS, null, model);
							setListValue(prop, "remove model");
						}
					}
				}
				setModel(getModel());
			}
		}
	}
}
