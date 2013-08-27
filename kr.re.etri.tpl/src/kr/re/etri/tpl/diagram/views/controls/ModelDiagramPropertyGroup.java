package kr.re.etri.tpl.diagram.views.controls;

import java.util.ArrayList;
import java.util.List;

import kr.re.etri.tpl.TaskModelPlugin;
import kr.re.etri.tpl.diagram.ModelManager;
import kr.re.etri.tpl.diagram.dialogs.FileSelectDialog;
import kr.re.etri.tpl.diagram.editors.TPLDiagramEditor;
import kr.re.etri.tpl.diagram.properties.sources.ModelDiagramPropertySource;
import kr.re.etri.tpl.diagram.util.ItemState;
import kr.re.etri.tpl.diagram.util.MarkerLogger;
import kr.re.etri.tpl.diagram.util.PropertyContainer;
import kr.re.etri.tpl.diagram.util.TPLUtil;
import kr.re.etri.tpl.grammar.ParserUtil;
import kr.re.etri.tpl.taskmodel.ActionElement;
import kr.re.etri.tpl.taskmodel.EnumElement;
import kr.re.etri.tpl.taskmodel.EnumItemElement;
import kr.re.etri.tpl.taskmodel.IncludedElement;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.ModelDiagram;
import kr.re.etri.tpl.taskmodel.TaskElement;
import kr.re.etri.tpl.taskmodel.TaskModelFactory;
import kr.re.etri.tpl.taskmodel.TaskModelPackage;

import org.antlr.runtime.tree.Tree;
import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.viewers.TableViewer;
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
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

public class ModelDiagramPropertyGroup extends ItemElementPropertyGroup {
	private static Logger logger = Logger.getLogger(ModelDiagramPropertyGroup.class);
	private final float DEFAULT_INCLUDE_LIST_WIDTH_ASPECT = 0.8f;
	private final int MARGIN = 5;
	private final String SCRIPT_FOLDER_NAME="scripts";

	Label includesLabel;
	TableViewer includesViewer; 
	Button includesBrowseBtn;
	Button includeRemoveBtn;
	Button includeOrderUpBtn;
	Button includeOrderDownBtn;
	Composite parent, includesPane;

	public ModelDiagramPropertyGroup(Composite parent, int style) {
		super(parent, style);
		this.parent = parent;

	}

	protected void createFormArea() {
		super.createFormArea();

		Rectangle descLabelRt, descTextRt;
		Rectangle includesLabelRt, includesListRt, includesBrowseBtnRt, includeRemoveBtnRt;

		descLabelRt = descLabel.getBounds();
		descTextRt = descText.getBounds();

		includesLabel = new Label(this, SWT.NONE);
		includesLabel.setText("Includes");
		includesLabelRt = new Rectangle(descLabelRt.x,
				descTextRt.y + descTextRt.height + MARGIN,
				80, 20);
		includesLabel.setBounds(includesLabelRt);

		includesPane = new Composite(this, SWT.NONE);

		includesPane.setLayout(new FillLayout());

		Rectangle includesPaneRt = new Rectangle(descTextRt.x, includesLabelRt.y,
				descTextRt.width, 110);
		includesPane.setBounds(includesPaneRt);
		FormLayout layout = new FormLayout();
		includesPane.setLayout(layout);

		includesBrowseBtn = new Button(includesPane, SWT.PUSH);
		includesBrowseBtn.setText("Browse");

		FormData data = new FormData();
		data.right = new FormAttachment(100,0);
		data.top = new FormAttachment(0,0);
		data.width = 60;
		includesBrowseBtn.setLayoutData(data);

		includeRemoveBtn = new Button(includesPane, SWT.PUSH);
		includeRemoveBtn.setText("Remove");

		data = new FormData();
		data.right = new FormAttachment(100,0);
		data.top = new FormAttachment(includesBrowseBtn,5);
		data.width = 60;
		includeRemoveBtn.setLayoutData(data);

		Composite includesOrderBtnPane =  new Composite(includesPane, SWT.NONE);
		FillLayout fillLayout = new FillLayout(SWT.VERTICAL);
		fillLayout.spacing = 6;
		includesOrderBtnPane.setLayout(fillLayout);

		data = new FormData();
		data.top = new FormAttachment(0,0);
		data.right= new FormAttachment(includesBrowseBtn,-5);
		data.bottom = new FormAttachment(100,0);
		includesOrderBtnPane.setLayoutData(data);

		includeOrderUpBtn = new Button(includesOrderBtnPane, SWT.ARROW|SWT.UP);
		includeOrderUpBtn.addSelectionListener(new SelectionListener(){
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
			@Override
			public void widgetSelected(SelectionEvent e) {
				//TODO 순서 바꾸기
			}
		});
		
		includeOrderDownBtn = new Button(includesOrderBtnPane, SWT.ARROW|SWT.DOWN);
		includeOrderDownBtn.addSelectionListener(new SelectionListener(){
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Include 리스트 순서 바꾸기
			}
		});

		int style = SWT.SELECTED | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER | SWT.FULL_SELECTION;
		includesViewer = new TableViewer(includesPane, style);
		Table includeTable = (Table)includesViewer.getControl();
		data = new FormData();
		data.left = new FormAttachment(0,0);
		data.top = new FormAttachment(0,0);
		data.bottom = new FormAttachment(100,0);
		data.right = new FormAttachment(includesOrderBtnPane,0);
		includeTable.setLayoutData(data);

		TableColumn tableColumn;
		tableColumn = new TableColumn(includeTable, SWT.NONE);
		tableColumn.setText("경로");
		tableColumn.setWidth(250);
		includeTable.setLinesVisible(true);
		includeTable.setHeaderVisible(true);

		includesBrowseBtn.addSelectionListener(new SelectionListener(){

			public void widgetDefaultSelected(SelectionEvent e) {}

			public void widgetSelected(SelectionEvent e){
				IWorkbench wb = PlatformUI.getWorkbench();
				IWorkbenchWindow wbw = wb.getActiveWorkbenchWindow();
				IWorkbenchPage wbp = wbw.getActivePage();
				IEditorPart ep = wbp.getActiveEditor();
				if(!(ep instanceof TPLDiagramEditor)) {
					return;
				}
				
				java.util.List<IFile> selectedResources = new ArrayList<IFile>();
				FileSelectDialog dialog = new FileSelectDialog(getShell(), selectedResources);
				/*int result = */dialog.open();

				/*if(result == IDialogConstants.OK_ID){ }*/

				// 중복 파일 검사
				ModelDiagramPropertySource propSrc = new ModelDiagramPropertySource((ModelDiagram)getModel());
				EList<IncludedElement> incList = (EList<IncludedElement>)propSrc.getPropertyValue(TaskModelPackage.MODEL_DIAGRAM__INCLUDE_ITEMS);
				
				ArrayList<IFile> existList = new ArrayList<IFile>();
				for(IncludedElement incElmt : incList) {
					for(IFile file: selectedResources){
						String path = file.getFullPath().toString();
						logger.debug(path);
						if(path.startsWith("/")){
							path =path.substring(1);
						}
						if(isExists(incElmt, path)) {
							existList.add(file);
						}
					}
				}

				selectedResources.removeAll(existList);

				TaskModelFactory factory = ModelManager.getFactory();
				java.util.List<IncludedElement> itemList = new ArrayList<IncludedElement>();

				try {
//					TPLDiagramEditor editPart = (TPLDiagramEditor)ep;
					if(!(ep instanceof TPLDiagramEditor)) {
						return;
					}
					
					for(IFile file: selectedResources){
						IncludedElement incElmt;
//						incElmt = parseScript(file);
						MarkerLogger problemLogger = new MarkerLogger(file, MarkerLogger.TYPE_SCRIPT);

						ModelDiagram modelDiagram;
						modelDiagram = ParserUtil.parseModel(file, problemLogger);
					
						if(modelDiagram == null ){
							logger.info("ModelDiagram is null.");
							return;
						}
		
						if(problemLogger.hasError()){
							IMarker[] markers=file.findMarkers(MarkerLogger.TYPE_SCRIPT, true, IResource.DEPTH_ZERO);
							StringBuffer buffer = new StringBuffer();
							for(int i =0 ; i< markers.length;i++ ){
								int severity = markers[i].getAttribute(IMarker.SEVERITY,IMarker.SEVERITY_WARNING);
								if(severity == IMarker.SEVERITY_ERROR){
									String message = markers[i].getAttribute(IMarker.MESSAGE, "");
									buffer.append(message);
								}
							}
							
							Status status = new Status(IStatus.ERROR, TaskModelPlugin.PLUGIN_ID, 0,"The model script has Syntax Errors", new Exception(buffer.toString()));

							ErrorDialog.openError(getShell(), "Model Script Error","" , status);							
							return;
						}
				
						incElmt = TPLUtil.convertTo(modelDiagram);
						
						if(incElmt != null) {
							String path = file.getFullPath().toString();
							logger.debug(path);
							if(path.startsWith("/")){
								path =path.substring(1);
							}
							logger.debug(path);
							incElmt.setIncludePath(path);

							Table includeTable = (Table)includesViewer.getControl();
							TableItem tableItem = new TableItem(includeTable, SWT.NONE);
							
							tableItem.setText(new String[]{path});
							tableItem.setData(incElmt);
							itemList.add(incElmt);
						}
					}
					if(itemList.size() > 0) {
						PropertyContainer prop;
						prop = new PropertyContainer(PropertyContainer.ADDALL, TaskModelPackage.MODEL_DIAGRAM__INCLUDE_ITEMS, itemList, null);
						setValue(prop, "");
					}
				}
				catch(Exception ex) {
					ex.printStackTrace();
				}
			}
		});

		includeRemoveBtn.addSelectionListener(new SelectionListener(){

			public void widgetDefaultSelected(SelectionEvent e) {}

			public void widgetSelected(SelectionEvent e){
				Table includeTable = (Table)includesViewer.getControl();
				int selIdx[] = includeTable.getSelectionIndices();
				if(selIdx == null || selIdx.length == 0) {
					return;
				}				
//				for(int i=0; i < selIdx.length ; i++){
//					logger.debug(selIdx[i]);
//				}
				List<IncludedElement> remList = new ArrayList<IncludedElement>();
				ModelDiagram model = (ModelDiagram)getModel();
				List<IncludedElement> itemList = model.getIncludeItems();
				for(int i = 0; i < selIdx.length; i++) {
					TableItem tableItem = includeTable.getItem(selIdx[i]);
					String path = tableItem.getText();
					if(path == null || path.equals("")) {
						continue;
					}
					for(IncludedElement item: itemList) {
						if(path.equals(item.getIncludePath())) {
							if(remList.contains(item) == false) {
								remList.add(item);
							}
						}
					}
				}
				
				if (remList.size() > 0) {
					PropertyContainer prop;
					prop = new PropertyContainer(PropertyContainer.REMOVEALL, TaskModelPackage.MODEL_DIAGRAM__INCLUDE_ITEMS, null, remList);
					setValue(prop, "");
				}
			}
		});

		int width = includesPaneRt.x + includesPaneRt.width + 5;
		int height = includesPaneRt.y + includesPaneRt.height + 5;
		setMinSize(width, height);
	}

	private boolean isExists(IncludedElement includeElmt, String path) {
		if(path == null || path.length() == 0) {
			return false;
		}

		if(path.equals(includeElmt.getIncludePath())) {
			return true;
		}

		EList<ItemElement> itemList = includeElmt.getItems();
		for(ItemElement item : itemList) {
			if(item instanceof IncludedElement) {
				if(isExists((IncludedElement)item, path)) {
					return true;
				}
			}
		}

		return false;
	}

	private IncludedElement parseScript(IFile file) {
		TaskModelFactory factory = ModelManager.getFactory();
		IncludedElement incElmt = factory.createIncludedElement();
		incElmt.setItemState(ItemState.ITEM_CREATED);
		incElmt.setName("");
		incElmt.setDescription("");
		incElmt.setIncludePath(file.getFullPath().toString());

		ActionElement actionElmt = (ActionElement)ModelManager.createElement(TaskModelPackage.Literals.ACTION_ELEMENT, incElmt);
		actionElmt.setName("doWork");
		incElmt.getItems().add(actionElmt);

		EnumItemElement enumItem;
		EnumElement enumElmt = (EnumElement)ModelManager.createElement(TaskModelPackage.Literals.ENUM_ELEMENT, incElmt);
		enumElmt.setName("EnumWork");

		enumItem = (EnumItemElement)ModelManager.createElement(TaskModelPackage.Literals.ENUM_ITEM_ELEMENT, enumElmt);
		enumItem.setName("Typing");
		enumItem.setValue(0);
		enumElmt.getEnumItem().add(enumItem);
		enumItem = (EnumItemElement)ModelManager.createElement(TaskModelPackage.Literals.ENUM_ITEM_ELEMENT, enumElmt);
		enumItem.setName("Talking");
		enumItem.setValue(1);
		enumElmt.getEnumItem().add(enumItem);
		incElmt.getItems().add(enumElmt);

		TaskElement workerElmt = (TaskElement)ModelManager.createElement(TaskModelPackage.Literals.TASK_ELEMENT, incElmt);
		workerElmt.setName("Soccer");
		workerElmt.setDescription("Play");
		incElmt.getItems().add(workerElmt);

		return incElmt;
	}


	protected void onDispose() {
		super.onDispose();

		includesLabel.dispose();

	}

	protected void onResize() {
		super.onResize();

		Point size = getSize();

		Rectangle includesPaneRt, includesBrowseBtnRt;

		includesPaneRt = includesPane.getBounds();

		includesPaneRt.width = size.x - includesPaneRt.x;
		includesPane.setBounds(includesPaneRt);



	}

	protected void hookControl() {
		super.hookControl();

	}

	public ModelDiagram getParsedModel(java.util.List<IFile> fileList){
		for(IFile file : fileList){
			MarkerLogger problemLogger = new MarkerLogger(file, MarkerLogger.TYPE_SCRIPT);
			return ParserUtil.parseModel(file, problemLogger);
		}
		return null;
	}

	private void printTree(int level, Tree tree) {

		Tree child = null;
		String format = String.format("%%0$%dc%%s(%d)", level * 2 + 1, level);
		System.out.println(String.format(format, ' ', tree.toString()));
		int childNum = tree.getChildCount();
		for (int i = 0; i < childNum; i++) {
			child = (Tree) tree.getChild(i);
			printTree(level + 1, child);
		}
	}

	public void setModel(Object model) {
		super.setModel(model);

		if(model instanceof ModelDiagram) {
			ModelDiagram diagram = (ModelDiagram)model;
			java.util.List<IncludedElement> incList = diagram.getIncludeItems();

			Table includeTable = (Table)includesViewer.getControl();
			includeTable.removeAll();
			for(IncludedElement item : incList) {
				TableItem tableItem = new TableItem(includeTable, SWT.NONE);
				tableItem.setText(item.getIncludePath());
				tableItem.setData(item);
			}
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
			case TaskModelPackage.MODEL_DIAGRAM__INCLUDE_ITEMS:
				setModel(getModel());
				break;
			default:
				super.notifyChanged(notification);
				break;
			}
			break;
		case Notification.ADD_MANY:
			switch(featureId) {
			case TaskModelPackage.MODEL_DIAGRAM__INCLUDE_ITEMS:
				setModel(getModel());
				break;
			default:
				super.notifyChanged(notification);
				break;
			}
			break;
		case Notification.REMOVE:
			switch(featureId) {
			case TaskModelPackage.MODEL_DIAGRAM__INCLUDE_ITEMS:
				setModel(getModel());
				break;
			default:
				super.notifyChanged(notification);
				break;
			}
			break;
		case Notification.REMOVE_MANY:
			switch(featureId) {
			case TaskModelPackage.MODEL_DIAGRAM__INCLUDE_ITEMS:
				setModel(getModel());
				break;
			default:
				super.notifyChanged(notification);
				break;
			}
			break;
		}
	}
}
