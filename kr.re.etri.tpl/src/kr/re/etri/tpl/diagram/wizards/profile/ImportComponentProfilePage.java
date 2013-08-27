package kr.re.etri.tpl.diagram.wizards.profile;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import kr.re.etri.tpl.diagram.util.TreeItem;
import kr.re.etri.tpl.diagram.util.component.ProfileStrings;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.TreeViewer;
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
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.ui.dialogs.ContainerSelectionDialog;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

public class ImportComponentProfilePage extends WizardPage {

	private TreeItem input;
	
	private Tree profileTree;
	
	private Text containerText;

	private IStructuredSelection selection;
	
	private IProject selectedProject;
	
	private TreeViewer treeViewer;

	public final String COMPONENT_COLUMN = "Component";
	public final String RESOURCE_COLUMN = "Resource";
	public final String TPL_COLUMN = "TPL";
	public final String TPD_COLUMN = "TPD";
	private String[] columnNames = new String[] {
			COMPONENT_COLUMN,
			RESOURCE_COLUMN,
//			TPL_COLUMN,
//			TPD_COLUMN,
	};

	protected class ImportInfo {
		private File sourcefile;
		private String targetname;
		private boolean createTpd;
		private boolean createTpl;
		private Element element;
		private boolean isComment;
		
		public ImportInfo() {
			sourcefile = null;
			targetname = "";
			createTpd = false;
			createTpl = false;
			element = null;
			isComment = false;
		}
		
		public File getSourcefile() {
			return sourcefile;
		}
		public void setSourcefile(File sourcefile) {
			this.sourcefile = sourcefile;
		}
		public String getTargetname() {
			return targetname;
		}
		public void setTargetname(String targetname) {
			this.targetname = targetname;
		}
		public boolean isCreateTpd() {
			return createTpd;
		}
		public void setCreateTpd(boolean check) {
			this.createTpd = check;
		}
		public boolean isCreateTpl() {
			return createTpl;
		}
		public void setCreateTpl(boolean check) {
			this.createTpl = check;
		}
		public Element getElement() {
			return element;
		}
		public void setElement(Element element) {
			this.element = element;
		}
		public boolean isComment() {
			return isComment;
		}
		public void setComment(boolean isComment) {
			this.isComment = isComment;
		}
	}
	
	protected ImportComponentProfilePage(IStructuredSelection selection) {
		super("Import Component Profile(XML) Files");
		setDescription("Select a file or a directory to search for Component Profile");
		setTitle("Import Component Profile(XML) Files");
		this.selection = selection;
	}
	
	public IProject getProject() {
		return selectedProject;
	}

	public String getContainerName() {
		return containerText.getText();
	}
	
	public Object[] getResults() {
		return input.getChildren();
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
		
		profileTree = createTree(container);
		treeViewer = createTreeViewer(profileTree);
		treeViewer.setLabelProvider(new ImportTreeViewerLabelProvider());
		treeViewer.setContentProvider(new ImportTreeViewerContentProvider());
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
//				handleFolderBrowse();
				handleFileBrowse();
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
				dialogChanged();
			}
		});
		otherBtn = button;
		
		// container
		label = new Label(container, SWT.NONE);
		label.setText("&Container:");

		containerText = new Text(container, SWT.BORDER | SWT.SINGLE);
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
				handleBrowse();
			}
		});

		return container;
	}
	
	private Tree createTree(Composite parent) {
		int style = SWT.MULTI | SWT.BORDER | SWT.H_SCROLL |
				SWT.V_SCROLL | SWT.FULL_SELECTION;
		Tree tree = new Tree(parent, style);
		
		GridData gridData = new GridData(GridData.FILL_BOTH);
		tree.setLayoutData(gridData);
		
		tree.setLinesVisible(true);
		tree.setHeaderVisible(true);

		TreeColumn column = new TreeColumn(tree, SWT.LEFT);
		column.setText(COMPONENT_COLUMN);
		column.setWidth(150);
		
		column = new TreeColumn(tree, SWT.LEFT);
		column.setText(RESOURCE_COLUMN);
		column.setWidth(150);
		
//		column = new TreeColumn(tree, SWT.CENTER);
//		column.setText(TPL_COLUMN);
//		column.setWidth(40);
//		
//		column = new TreeColumn(tree, SWT.CENTER);
//		column.setText(TPD_COLUMN);
//		column.setWidth(40);

		return tree;
	}
	
	private TreeViewer createTreeViewer(Tree tree) {
		TreeViewer treeViewer = new TreeViewer(tree);
		treeViewer.setUseHashlookup(true);
		treeViewer.setColumnProperties(columnNames);
		return treeViewer;
	}
	
	private CellEditor[] getTreeViewerCellEditors() {
		return new CellEditor[] {
				null,
				new TextCellEditor(profileTree),
//				new CheckboxCellEditor(profileTree),
//				new CheckboxCellEditor(profileTree),
		};
	}
	
	private ICellModifier getTreeViewerCellModifier() {
		return new ICellModifier() {
			
			@Override
			public void modify(Object element, String property, Object value) {
				if (element instanceof org.eclipse.swt.widgets.TreeItem) {
					TreeItem treeItem = (TreeItem)((org.eclipse.swt.widgets.TreeItem)element).getData();
					ImportInfo importInfo = (ImportInfo)treeItem.getData();
					if (property.equals(COMPONENT_COLUMN)) {
						importInfo.setSourcefile((File)value);
					}
					else if (property.equals(RESOURCE_COLUMN)) {
						importInfo.setTargetname((String)value);
					}
					else if (property.equals(TPD_COLUMN)) {
						boolean newValue = (Boolean)value;
						importInfo.setCreateTpd(newValue);
						if (newValue) {
							treeItem = treeItem.getParent();
							while (treeItem != null) {
								importInfo = (ImportInfo)treeItem.getData();
								if (importInfo != null) {
									importInfo.setCreateTpd(newValue);
								}
								treeItem = treeItem.getParent();
							}
						}
						else {
							for (Object obj : treeItem.getChildren()) {
								importInfo = (ImportInfo)((TreeItem)obj).getData();
								if (importInfo != null) {
									importInfo.setCreateTpd(newValue);
								}
							}
						}
					}
					else if (property.equals(TPL_COLUMN)) {
						boolean newValue = (Boolean)value;
						importInfo.setCreateTpl(newValue);
						if (newValue) {
							treeItem = treeItem.getParent();
							while (treeItem != null) {
								importInfo = (ImportInfo)treeItem.getData();
								if (importInfo != null) {
									importInfo.setCreateTpl(newValue);
								}
								treeItem = treeItem.getParent();
							}
						}
						else {
							for (Object obj : treeItem.getChildren()) {
								importInfo = (ImportInfo)((TreeItem)obj).getData();
								if (importInfo != null) {
									importInfo.setCreateTpl(newValue);
								}
							}
						}
					}
					
					treeViewer.refresh();
				}
			}
			
			@Override
			public Object getValue(Object element, String property) {
				if (element instanceof TreeItem) {
					ImportInfo importInfo = (ImportInfo)((TreeItem)element).getData();
					if (property.equals(COMPONENT_COLUMN)) {
						return importInfo.getSourcefile();
					}
					else if (property.equals(RESOURCE_COLUMN)) {
						return importInfo.getTargetname();
					}
					else if (property.equals(TPD_COLUMN)) {
						return importInfo.isCreateTpd();
					}
					else if (property.equals(TPL_COLUMN)) {
						return importInfo.isCreateTpl();
					}
				}
				return null;
			}
			
			@Override
			public boolean canModify(Object element, String property) {
				if (property.equals(RESOURCE_COLUMN)) {
					return true;
				}
				else if (property.equals(TPD_COLUMN)) {
					return true;
				}
				else if (property.equals(TPL_COLUMN)) {
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
			
			if (ssel.size() > 1)
				return;
			Object obj = ssel.getFirstElement();
			if (obj instanceof IResource) {
				IContainer container;
				if (obj instanceof IContainer) {
					container = (IContainer) obj;
				}
				else {
					container = ((IResource) obj).getParent();
				}
				
				selectedProject = ((IResource)obj).getProject();
				containerText.setText(container.getFullPath().toString());
			}
		}
	}

	/**
	 * Ensures that both text fields are set.
	 */
	private void dialogChanged() {
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IResource resource = root.findMember(new Path(getContainerName()));

		if (getContainerName().length() == 0) {
			updateStatus("File container must be specified");
			return;
		}
		if (resource == null
				|| (resource.getType() & (IResource.PROJECT | IResource.FOLDER)) == 0) {
			updateStatus("File container must exist");
			return;
		}
		if (!resource.isAccessible()) {
			updateStatus("Project must be writable");
			return;
		}
		if (getInput().getChildren().length == 0) {
			updateStatus("Compoment profile must be chosen one or more");
			return;
		}

		updateStatus(null);
	}

	/**
	 * Uses the standard container selection dialog to choose the new value for
	 * the container field.
	 */
	private void handleBrowse() {
		IWorkspaceRoot wsRoot = ResourcesPlugin.getWorkspace().getRoot();
		ContainerSelectionDialog dialog = new ContainerSelectionDialog(
				getShell(), wsRoot, false,
				"Select new file container");
		if (dialog.open() == ContainerSelectionDialog.OK) {
			Object[] result = dialog.getResult();
			if (result.length == 1) {
				
				IResource res = wsRoot.findMember((Path) result[0]);
				selectedProject = res.getProject();;
				containerText.setText(((Path) result[0]).toString());
			}
		}
	}

	/**
	 * Uses the standard container selection dialog to choose the new value for
	 * the container field.
	 */
	private void handleFolderBrowse() {
		DirectoryDialog dialog = new DirectoryDialog(getShell(), SWT.SHEET);
		dialog.setFilterPath(System.getProperty("user.dir"));
		dialog.setText("Directory selection");
		dialog.setMessage("Choose a directory that contains component profile(.xml)");
		String folderName = dialog.open();
		
		if (folderName != null) {
			File selectedFolder = new File(folderName);
			if (selectedFolder.exists() && selectedFolder.isDirectory()) {
				for (File memberFile : selectedFolder.listFiles()) {
					if (memberFile.exists() == false || memberFile.isFile() == false) {
						continue;
					}
					if (isContainedFile(getInput(), memberFile)) {
						continue;
					}
					
					String fileName = memberFile.getName();
					int idx = fileName.lastIndexOf('.');
					String fileExt = (idx > -1) ? fileName.substring(idx+1) : "";
					String targetName = (idx > -1) ? fileName.substring(0, idx) : fileName;
					if (fileExt.equalsIgnoreCase("xml") == false) {
						continue;
					}
					
					Element root = getRootElement(memberFile);
					if (root == null ||
							root.getName().equals(ProfileStrings.COMPONENT_PROFILE) == false) {
						continue;
					}
					
					String modelName = root.getChildText(ProfileStrings.NAME);
					
					ImportInfo importInfo = new ImportInfo();
					importInfo.setSourcefile(memberFile);
					importInfo.setTargetname(targetName);
					importInfo.setCreateTpd(false);
					importInfo.setCreateTpl(true);
					importInfo.setElement(root);
					TreeItem child = new TreeItem(modelName, importInfo);
					child.setParent(getInput());
					getInput().addChild(child);
					
					// service_port
					Element ports = root.getChild(ProfileStrings.PORTS);
					if (ports != null) {
						for (Object obj : ports.getChildren(ProfileStrings.SERVICE_PORT)) {
							if (obj instanceof Element == false) {
								continue;
							}
							
							Element servicePort = (Element)obj;
							String servicePortUsage = servicePort.getChildText(ProfileStrings.USAGE);
							if (ProfileStrings.PROVIDED.equals(servicePortUsage) == false) {
								continue;
							}
							
							String servicePortRef = servicePort.getChildText(ProfileStrings.REFERENCE);
							idx = servicePortRef.lastIndexOf('.');
							String servicePortName = (idx > -1) ? servicePortRef.substring(0, idx) : servicePortRef;
							
							if (servicePortRef.startsWith(File.separator) == false) {
								servicePortRef = folderName + File.separator + servicePortRef;
							}
							else {
								servicePortRef = folderName + servicePortRef;
							}
							
							File serviceFile = new File(servicePortRef);
							if (serviceFile.exists() == false || serviceFile.isFile() == false) {
								continue;
							}
							
							Element serviceRoot = getRootElement(serviceFile);
							if (serviceRoot == null ||
									serviceRoot.getName().equals(ProfileStrings.SERVICE_PORT_TYPE_PROFILE) == false) {
								continue;
							}
							
							modelName = servicePort.getChildText(ProfileStrings.NAME);
							
							importInfo = new ImportInfo();
							importInfo.setSourcefile(serviceFile);
							importInfo.setTargetname(servicePortName);
							importInfo.setCreateTpd(false);
							importInfo.setCreateTpl(true);
							importInfo.setElement(serviceRoot);
							TreeItem cchild = new TreeItem(modelName, importInfo);
							cchild.setParent(child);
							child.addChild(cchild);
						}
					}
				}
			}
			treeViewer.refresh();
		}
	}
	
	private void handleFileBrowse() {
		FileDialog dialog = new FileDialog(getShell(), SWT.OPEN);
		dialog.setFilterNames(new String[] {"Xml files (*.xml)", "All files (*.*)"});
		dialog.setFilterExtensions(new String[] {"*.xml", "*.*"});
		dialog.setFilterPath(System.getProperty("user.dir"));
		
		String fileName = dialog.open();
		TreeItem item = createProfileTreeItem(fileName, null);
		if (item != null) {
			getInput().addChild(item);
			item.setParent(getInput());
			treeViewer.refresh();
		}
	}
	
	private TreeItem createProfileTreeItem(String fileName, String typeName) {
		TreeItem item = null;
		
		File selectedFile = new File(fileName);
		if (selectedFile.exists() == false || selectedFile.isFile() == false) {
			return null;
		}
		
		if (isContainedFile(getInput(), selectedFile)) {
			return null;
		}
		
		Element root = getRootElement(selectedFile);
		if (root == null) {
			return null;
		}
		
		String folderName = selectedFile.getParent();
		String rootName = root.getName();
		String profName = root.getChildText(ProfileStrings.NAME);

		if (ProfileStrings.APPLICATION_PROFILE.equals(rootName) ||
				ProfileStrings.COMPOSITE_COMPONENT_PROFILE.equals(rootName)) {
			ImportInfo importInfo = new ImportInfo();
			importInfo.setSourcefile(selectedFile);
			importInfo.setTargetname(profName);
			importInfo.setCreateTpd(false);
			importInfo.setCreateTpl(true);
			importInfo.setElement(root);
			item = new TreeItem(profName, importInfo);
			
			if (folderName.endsWith(File.separator)) {
				folderName += (profName + File.separator);
			}
			else {
				folderName += (File.separator + profName + File.separator);
			}
			
			Element subComponents = root.getChild(ProfileStrings.SUBCOMPONENTS);
			if (subComponents == null) {
				return item;
			}
			
			List subComponentList = subComponents.getChildren(ProfileStrings.SUBCOMPONENT);
			if (subComponentList == null) {
				return item;
			}
			
			for (int i = 0; i < subComponentList.size(); i++) {
				if (subComponentList.get(i) instanceof Element) {
					Element subComponent = (Element)subComponentList.get(i);
					String subName = subComponent.getChildText(ProfileStrings.NAME);
					String reference = subComponent.getChildText(ProfileStrings.REFERENCE);
					reference = folderName + reference;
					
					TreeItem cItem = createProfileTreeItem(reference, null);
					if (cItem != null) {
						item.addChild(cItem);
						cItem.setParent(item);
					}
					else {
						importInfo = new ImportInfo();
						importInfo.setSourcefile(new File(reference));
						importInfo.setTargetname(subName);
						importInfo.setCreateTpd(false);
						importInfo.setCreateTpl(true);
						importInfo.setElement(null);
						cItem = new TreeItem(subName, importInfo);
						item.addChild(cItem);
						cItem.setParent(item);
					}
				}
			}
		}
		else if (ProfileStrings.COMPONENT_PROFILE.equals(rootName)) {
			ImportInfo importInfo = new ImportInfo();
			importInfo.setSourcefile(selectedFile);
			importInfo.setTargetname(profName);
			importInfo.setCreateTpd(false);
			importInfo.setCreateTpl(true);
			importInfo.setElement(root);
			item = new TreeItem(profName, importInfo);
			
			Element ports = root.getChild(ProfileStrings.PORTS);
			if (ports == null) {
				return item;
			}
			
			List portList = ports.getChildren(ProfileStrings.SERVICE_PORT);
			if (portList == null) {
				return item;
			}
			
			for (int i = 0; i < portList.size(); i++) {
				if (portList.get(i) instanceof Element == false) {
					continue;
				}

				Element servicePort = (Element)portList.get(i);
				String servicePortName = servicePort.getChildText(ProfileStrings.NAME);
				String servicePortType = servicePort.getChildText(ProfileStrings.TYPE);
				String servicePortRef = servicePort.getChildText(ProfileStrings.REFERENCE);
				String servicePortUsage = servicePort.getChildText(ProfileStrings.USAGE);
				if (ProfileStrings.PROVIDED.equals(servicePortUsage) == false) {
					continue;
				}

				if (folderName.endsWith(File.separator)) {
					servicePortRef = folderName + servicePortRef;
				}
				else {
					servicePortRef = folderName + File.separator + servicePortRef;
				}

				TreeItem cItem = createProfileTreeItem(servicePortRef, servicePortType);
				if (cItem != null) {
					cItem.setText(servicePortName);
					item.addChild(cItem);
					cItem.setParent(item);
				}
				else {
					importInfo = new ImportInfo();
					importInfo.setSourcefile(new File(servicePortRef));
					importInfo.setTargetname(servicePortName);
					importInfo.setCreateTpd(false);
					importInfo.setCreateTpl(true);
					importInfo.setElement(null);
					cItem = new TreeItem(servicePortName, importInfo);
					item.addChild(cItem);
					cItem.setParent(item);
				}
			}
		}
		else if (ProfileStrings.SERVICE_PORT_TYPE_PROFILE.equals(rootName)) {
			// KJH 20111007, service_port_type_profile에 복수의 service_port_type이 올 수 있도록 수정
			List children = root.getChildren(ProfileStrings.SERVICE_PORT_TYPE);
			if (children == null) {
				return null; 
			}
			
			for (int i = 0; i < children.size(); i++) {
				if (children.get(i) instanceof Element == false) {
					continue;
				}

				Element child = (Element)children.get(i);
				profName = child.getChildText(ProfileStrings.TYPE_NAME);
				
				if (profName != null && profName.equals(typeName)) {
					ImportInfo importInfo = new ImportInfo();
					importInfo.setSourcefile(selectedFile);
					importInfo.setTargetname(profName);
					importInfo.setCreateTpd(false);
					importInfo.setCreateTpl(true);
					importInfo.setElement(root);
					item = new TreeItem(profName, importInfo);
					break;
				}
			}
		}
		
		return item;
	}
	
	/**
	 * 이미 추가된 Component Profile인지 검사
	 * 
	 * @param parent
	 * @param file
	 * @return
	 * 
	 * @author KJH 20110704
	 */
	private boolean isContainedFile(TreeItem parent, File file) {
		for (Object obj : parent.getChildren()) {
			Object objData = ((TreeItem)obj).getData();
			if (objData instanceof ImportInfo) {
				File f = ((ImportInfo)objData).getSourcefile();
				if (file.equals(f)) {
					return true;
				}
			}
		}
		return false;
	}

	private Element getRootElement(File file) {
		SAXBuilder builder = new SAXBuilder();
		Document document = null;
		try {
			document = builder.build(file);
		} catch (JDOMException e1) {
			return null;
		} catch (IOException e1) {
			return null;
		}
		
		if (document == null) {
			return null;
		}
		
		return document.getRootElement();
	}
	
	private void updateStatus(String message) {
		setErrorMessage(message);
		setPageComplete(message == null);
	}

}
