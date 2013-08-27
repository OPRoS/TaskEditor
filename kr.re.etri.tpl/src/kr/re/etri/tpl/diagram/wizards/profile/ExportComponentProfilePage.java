package kr.re.etri.tpl.diagram.wizards.profile;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import kr.re.etri.tpl.IRTMDefines;
import kr.re.etri.tpl.diagram.editors.DiagramResourceManager;
import kr.re.etri.tpl.diagram.util.TreeItem;
import kr.re.etri.tpl.diagram.wizards.profile.dialogs.ExportResourceSelectionDialog;
import kr.re.etri.tpl.script.grammar.tree2.IModelNode;
import kr.re.etri.tpl.script.grammar.tree2.IScriptNode;
import kr.re.etri.tpl.script.grammar.tree2.ScriptManager;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.ModelDiagram;
import kr.re.etri.tpl.taskmodel.ModelElement;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.viewers.AbstractTreeViewer;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.ui.dialogs.ContainerCheckedTreeViewer;
import org.eclipse.ui.dialogs.ContainerSelectionDialog;

public class ExportComponentProfilePage extends WizardPage {

	private Tree tree;
	
	private ContainerCheckedTreeViewer treeViewer;
	
	private Text containerText;
	
	private IStructuredSelection selection;
	
	private TreeItem input;
	
	public final String RESOURCE_COLUMN = "Resource";
	public final String COMPONENT_COLUMN = "Component";
	private String[] columnNames = new String[] {
			RESOURCE_COLUMN,
			COMPONENT_COLUMN,
	};

	protected class ExportInfo {
		private Object sourceObject;
		private String targetname;
		private int level;
		
		public Object getSourceObject() {
			return sourceObject;
		}
		public void setSourceObject(Object sourceObject) {
			this.sourceObject = sourceObject;
		}
		public String getTargetname() {
			return targetname;
		}
		public void setTargetname(String targetname) {
			this.targetname = targetname;
		}
		public int getLevel() {
			return level;
		}
		public void setLevel(int level) {
			this.level = level;
		}
	}
	
	protected ExportComponentProfilePage(IStructuredSelection selection) {
		super("Export Resource To Component Profile");
		setDescription("Select one or more resource to export");
		setTitle("Export Resource To Component Profile");
		this.selection = selection;
	}
	
	public String getContainerName() {
		return containerText.getText();
	}
	
	public Object[] getResults() {
		return ((ContainerCheckedTreeViewer)treeViewer).getCheckedElements();
	}
	
	@Override
	public void createControl(Composite parent) {
		Composite container = createClientArea(parent);
		
		initialize();
		dialogChanged();
		setControl(container);
	}

	protected Composite createClientArea(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		GridData gridData = new GridData(GridData.FILL_BOTH);
		container.setLayoutData(gridData);

		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		layout.numColumns = 3;
		layout.verticalSpacing = 9;
		
		Label label = new Label(container, SWT.NONE);
		gridData = new GridData();
		gridData.widthHint = 60;
		gridData.verticalAlignment = GridData.BEGINNING;
		label.setLayoutData(gridData);
		label.setText("&Profiles:");
		
		tree = createTree(container);
		treeViewer = createTreeViewer(tree);
		treeViewer.setLabelProvider(new ExportTreeViewerLabelProvider());
		treeViewer.setContentProvider(new ExportTreeViewerContentProvider());
		treeViewer.setCellEditors(getTreeViewerCellEditors());
		treeViewer.setCellModifier(getTreeViewerCellModifier());
		treeViewer.setColumnProperties(columnNames);
		treeViewer.setInput(getInput());

		Composite buttonPanel = new Composite(container, SWT.NONE);
		gridData = new GridData();
		gridData.widthHint = 68;
		gridData.verticalAlignment = GridData.BEGINNING;
		buttonPanel.setLayoutData(gridData);
		
		FormLayout formLayout = new FormLayout();
		buttonPanel.setLayout(formLayout);
		
		FormData formData;
		Button otherBtn, button;
		button = new Button(buttonPanel, SWT.PUSH);
		formData = new FormData();
		formData.left = new FormAttachment(0, 0);
		formData.top = new FormAttachment(0, 0);
		formData.width = 68;
		button.setLayoutData(formData);
		button.setText("B&rowse...");
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				handleResourceBrowse();
				dialogChanged();
			}
		});
		otherBtn = button;

		button = new Button(buttonPanel, SWT.PUSH);
		formData = new FormData();
		formData.left = new FormAttachment(0, 0);
		formData.top = new FormAttachment(otherBtn, 20);
		formData.width = 68;
		button.setLayoutData(formData);
		button.setText("Re&move");
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				IStructuredSelection selection = (IStructuredSelection)treeViewer.getSelection();
				Iterator iter = selection.iterator();
				while (iter.hasNext()) {
					TreeItem item = (TreeItem)iter.next();
					if (item.getParent() != null) {
						item.getParent().removeChild(item);
					}
				}
				treeViewer.refresh();
				treeViewer.setChecked(getInput(), true);
			}
		});
		otherBtn = button;
		
		// container
		label = new Label(container, SWT.NONE);
		label.setText("&Container:");

		containerText = new Text(container, SWT.BORDER | SWT.SINGLE);
		containerText.setEditable(false);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		containerText.setLayoutData(gd);
		containerText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});

		button = new Button(container, SWT.PUSH);
		button.setText("Bro&wse...");
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				handleFolderBrowse();
			}
		});
		
		return container;
	}
	
	private Tree createTree(Composite parent) {
		int style = SWT.MULTI | SWT.BORDER | SWT.H_SCROLL |
				SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.CHECK;
		Tree tree = new Tree(parent, style);
		
		GridData gridData = new GridData(GridData.FILL_BOTH);
		tree.setLayoutData(gridData);
		
		tree.setLinesVisible(true);
		tree.setHeaderVisible(true);

		TreeColumn column = new TreeColumn(tree, SWT.LEFT);
		column.setText(RESOURCE_COLUMN);
		column.setWidth(200);

		column = new TreeColumn(tree, SWT.LEFT);
		column.setText(COMPONENT_COLUMN);
		column.setWidth(200);
		
		return tree;
	}
	
	private ContainerCheckedTreeViewer createTreeViewer(Tree tree) {
		ContainerCheckedTreeViewer treeViewer = new ContainerCheckedTreeViewer(tree);
		treeViewer.setUseHashlookup(true);
		treeViewer.setColumnProperties(columnNames);
		return treeViewer;
	}
	
	private CellEditor[] getTreeViewerCellEditors() {
		return new CellEditor[] {
				null,
				new TextCellEditor(tree),
		};
	}
	
	private ICellModifier getTreeViewerCellModifier() {
		return new ICellModifier() {
			
			@Override
			public void modify(Object element, String property, Object value) {
				if (element instanceof org.eclipse.swt.widgets.TreeItem) {
					TreeItem treeItem = (TreeItem)((org.eclipse.swt.widgets.TreeItem)element).getData();
					if (property.equals(RESOURCE_COLUMN)) {
					}
					else if (property.equals(COMPONENT_COLUMN)) {
					}
					
					treeViewer.refresh();
					treeViewer.setChecked(getInput(), true);
				}
			}
			
			@Override
			public Object getValue(Object element, String property) {
				if (element instanceof TreeItem) {
					Object data = ((TreeItem)element).getData();
					if (property.equals(RESOURCE_COLUMN)) {
					}
					else if (property.equals(COMPONENT_COLUMN)) {
					}
				}
				return null;
			}
			
			@Override
			public boolean canModify(Object element, String property) {
				if (property.equals(COMPONENT_COLUMN)) {
					return true;
				}
				return false;
			}
		};
	}
	
	private TreeItem getInput() {
		if (input == null) {
			input = new TreeItem("");
		}
		return input;
	}

	/**
	 * Tests if the current workbench selection is a suitable container to use.
	 */
	private void initialize() {
		if (selection != null && selection.isEmpty() == false
				&& selection instanceof IStructuredSelection) {
			IStructuredSelection ssel = (IStructuredSelection) selection;
			
			Iterator iter = ssel.iterator();
			while (iter.hasNext()) {
				Object obj = iter.next();
				if (obj instanceof IFile) {
					if (IRTMDefines.TASK_SCRIPT_EXTENSION_NAME
							.equalsIgnoreCase(((IFile)obj).getFileExtension())) {
						createTreeItem(getInput(), (IFile)obj);
					}
				}
				else if (obj instanceof IContainer) {
					createTreeItem(getInput(), (IContainer)obj);
				}
			}
			treeViewer.expandAll();
			treeViewer.setCheckedElements(getInput().getChildren());
			treeViewer.refresh();
		}
	}

	/**
	 * Ensures that both text fields are set.
	 */
	private void dialogChanged() {
		if (getContainerName().length() == 0) {
			updateStatus("Target directory must be specified");
			return;
		}
		if (getInput().getChildren().length == 0) {
			updateStatus("Resource must be chosen one or more");
			return;
		}

		updateStatus(null);
	}
	
	private void handleResourceBrowse() {
		IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
		if (workspaceRoot.getProjects().length == 0) {
			return;
		}

		ExportResourceSelectionDialog dialog;
		dialog = new ExportResourceSelectionDialog(getShell(), "");
		for (IResource project : workspaceRoot.getProjects()) {
			dialog.addRoot(project);
		}

		if (dialog.open() == ContainerSelectionDialog.OK) {
			Object[] result = dialog.getResult();
			List<TreeItem> addedList = new ArrayList<TreeItem>();
			if (result.length > 0) {
				for(int i = 0; i < result.length; i++) {
					if((result[i] instanceof IFile) == false) {
						continue;
					}
					
					IFile file = (IFile)result[i];
					TreeItem addedItem = createTreeItem(getInput(), file);
					if (addedItem != null) {
						addedList.add(addedItem);
					}
				}
			}
			treeViewer.refresh();
			for (TreeItem item : addedList) {
				treeViewer.setChecked(item, true);
				treeViewer.expandToLevel(item, AbstractTreeViewer.ALL_LEVELS);
			}
		}
	}
	
	private void handleFolderBrowse() {
		DirectoryDialog dialog = new DirectoryDialog(getShell(), SWT.SHEET);
		dialog.setFilterPath(System.getProperty("user.dir"));
		dialog.setText("Directory selection");
		dialog.setMessage("Choose a directory that contains component profile(.xml)");
		String folderName = dialog.open();
		
		if (folderName != null) {
			containerText.setText(folderName);
		}
	}
	
	private void createTreeItem(TreeItem parent, IContainer container) {
		try {
			for (IResource resource : container.members()) {
				if (resource instanceof IFile) {
					createTreeItem(parent, (IFile)resource);
				}
				else if (resource instanceof IContainer) {
					createTreeItem(parent, (IContainer)resource);
				}
			}
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}
	
	private TreeItem createTreeItem(TreeItem parent, IFile file) {
		if (isContainedFile(parent, file)) {
			return null;
		}
		
		String extension = file.getFileExtension();

		if(IRTMDefines.TASK_SCRIPT_EXTENSION_NAME.equalsIgnoreCase(extension)) {
			TreeItem treeItem = parent.addChild(file.getFullPath().toString());
			treeItem.setData(file);
			
			IScriptNode scriptNode = ScriptManager.getInstance().getTree(file, null);
			for (IScriptNode childNode : scriptNode.getChildren()) {
				if (childNode instanceof IModelNode) {
					TreeItem childItem = treeItem.addChild(childNode.getName());
					ExportInfo exportInfo = new ExportInfo();
					exportInfo.setSourceObject(childNode);
					exportInfo.setTargetname(childNode.getName() + ".xml");
					exportInfo.setLevel(1);
					childItem.setData(exportInfo);
					
					for (IModelNode model : ((IModelNode)childNode).getModels()) {
						TreeItem cchildItem = childItem.addChild(model.getName());
						exportInfo = new ExportInfo();
						exportInfo.setSourceObject(model);
						exportInfo.setTargetname(model.getName() + ".xml");
						exportInfo.setLevel(2);
						cchildItem.setData(exportInfo);
					}
				}
			}
			
			return treeItem;
		}
		else if(IRTMDefines.TASK_DIAGRAM_EXTENSION_NAME.equalsIgnoreCase(extension)) {
			TreeItem treeItem = parent.addChild(file.getFullPath().toString());
			treeItem.setData(file);
			
			Resource resource = DiagramResourceManager.getResource(file);
			if (resource == null) {
				return null;
			}
			EList<EObject> contents = resource.getContents();
			if (contents != null && contents.size() > 0 &&
					contents.get(0)  instanceof ModelDiagram) {
				ModelDiagram diagram = (ModelDiagram)contents.get(0);

				for (ItemElement item : diagram.getItems()) {
					if (item instanceof ModelElement == false) {
						continue;
					}
					
					ModelElement model = (ModelElement)item;
					TreeItem childItem = treeItem.addChild(model.getName());
					ExportInfo exportInfo = new ExportInfo();
					exportInfo.setSourceObject(model);
					exportInfo.setTargetname(model.getName() + ".xml");
					exportInfo.setLevel(1);
					childItem.setData(exportInfo);
					
					for (ModelElement subModel : model.getModels()) {
						TreeItem cchildItem = childItem.addChild(subModel.getName());
						exportInfo = new ExportInfo();
						exportInfo.setSourceObject(subModel);
						exportInfo.setTargetname(subModel + ".xml");
						exportInfo.setLevel(2);
						cchildItem.setData(exportInfo);
					}
				}
			}
			return treeItem;
		}
		return null;
	}
	
	/**
	 * 이미 추가된 Resource인지 검사
	 * 
	 * @param parent
	 * @param file
	 * @return
	 * 
	 * @author KJH 20110704
	 */
	private boolean isContainedFile(TreeItem parent, IFile file) {
		for (Object obj : parent.getChildren()) {
			Object objData = ((TreeItem)obj).getData();
			if (objData instanceof ExportInfo) {
				Object f = ((ExportInfo)objData).getSourceObject();
				if (file.equals(f)) {
					return true;
				}
			}
		}
		return false;
	}

	private void updateStatus(String message) {
		setErrorMessage(message);
		setPageComplete(message == null);
	}


}
