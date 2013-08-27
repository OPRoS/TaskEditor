package kr.re.etri.tpl.diagram.wizards.profile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.List;

import kr.re.etri.tpl.diagram.util.component.ProfileStrings;
import kr.re.etri.tpl.diagram.wizards.profile.dnd.TableViewerDragAdapter;
import kr.re.etri.tpl.diagram.wizards.profile.dnd.TableViewerDropAdapter;
import kr.re.etri.tpl.diagram.wizards.profile.dnd.TreeViewerDragAdapter;
import kr.re.etri.tpl.diagram.wizards.profile.dnd.TreeViewerDropAdapter;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.ui.dialogs.ContainerCheckedTreeViewer;
import org.jdom.Attribute;
import org.jdom.Comment;
import org.jdom.Content;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Text;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

public class ExportComponentProfilePage2 extends WizardPage {

	private Tree tree;
	
	private ContainerCheckedTreeViewer treeViewer;
	
	private Table table;
	
	private TableViewer tableViewer;
	
	private IStructuredSelection selection;
	
	private Element input;
	
	private Element properties;
	
	private final String DEFAULT_PROFILE_XML = "default_profile.xml";
	private final String USER_PROFILE_XML = System.getProperty("user.dir") + "\\user_profile.xml";
	
	public final String ELEMENT_COLUMN = "Element";
	public final String METADATA_COLUMN = "Metadata";
	public final String NAME_COLUMN = "Name";
	public final String TEXT_COLUMN = "Text";
	public final String COMMENT_COLUMN = "Comment";
	private String[] columnNames = new String[] {
			ELEMENT_COLUMN,
			METADATA_COLUMN,
	};
	private String[] columnNames2 = new String[] {
			NAME_COLUMN,
			TEXT_COLUMN,
			COMMENT_COLUMN,
	};

	
	protected ExportComponentProfilePage2(IStructuredSelection selection) {
		super("Export Resource To Component Profile");
		setDescription("Enter the metadata to be added to the component profile");
		setTitle("Export Resource To Component Profile");
		this.selection = selection;
	}

	@Override
	public void createControl(Composite parent) {
		Composite container = createClientArea(parent);

		setControl(container);
	}

	protected Composite createClientArea(Composite parent) {
		Composite basePanel = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		basePanel.setLayout(layout);
		
		Composite container = new Composite(basePanel, SWT.NONE);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		container.setLayoutData(gridData);

		layout = new GridLayout();
		container.setLayout(layout);
		layout.numColumns = 3;
		layout.verticalSpacing = 9;
		
		Label label = new Label(container, SWT.NONE);
		gridData = new GridData();
		gridData.widthHint = 60;
		gridData.verticalAlignment = GridData.BEGINNING;
		label.setLayoutData(gridData);
		label.setText("&Meta Data:");
		
		tree = createTree(container);
		treeViewer = createTreeViewer(tree);
		treeViewer.setLabelProvider(new ExportTreeViewerLabelProvider2());
		treeViewer.setContentProvider(new ExportTreeViewerContentProvider2());
		treeViewer.setCellEditors(getTreeViewerCellEditors(tree));
		treeViewer.setCellModifier(getTreeViewerCellModifier());
		treeViewer.setInput(getInput());
		treeViewer.expandAll();
		treeViewer.setCheckedElements(getInput().getChildren().toArray());
		hookViewerContextMenu(treeViewer);
		hookTreeViewerDragAndDrop(treeViewer);
		
		Composite buttonPanel = new Composite(container, SWT.NONE);
		gridData = new GridData();
		gridData.widthHint = 68;
		gridData.verticalAlignment = GridData.BEGINNING;
		buttonPanel.setLayoutData(gridData);
		
		FormLayout formLayout = new FormLayout();
		buttonPanel.setLayout(formLayout);
		
		FormData formData;
		Button otherBtn, button;
//		button = new Button(buttonPanel, SWT.PUSH);
//		formData = new FormData();
//		formData.left = new FormAttachment(0, 0);
//		formData.top = new FormAttachment(0, 0);
//		formData.width = 68;
//		button.setLayoutData(formData);
//		button.setText("Add &Elem");
//		button.addSelectionListener(new SelectionAdapter() {
//			public void widgetSelected(SelectionEvent e) {
//				handleAddElementButton();
//			}
//		});
//		otherBtn = button;
//
//		button = new Button(buttonPanel, SWT.PUSH);
//		formData = new FormData();
//		formData.left = new FormAttachment(0, 0);
//		formData.top = new FormAttachment(otherBtn, 10);
//		formData.width = 68;
//		button.setLayoutData(formData);
//		button.setText("&Add Attr");
//		button.addSelectionListener(new SelectionAdapter() {
//			public void widgetSelected(SelectionEvent e) {
//				handleAddAttributeButton();
//			}
//		});
//		otherBtn = button;
//
//		button = new Button(buttonPanel, SWT.PUSH);
//		formData = new FormData();
//		formData.left = new FormAttachment(0, 0);
//		formData.top = new FormAttachment(otherBtn, 10);
//		formData.width = 68;
//		button.setLayoutData(formData);
//		button.setText("Re&move");
//		button.addSelectionListener(new SelectionAdapter() {
//			public void widgetSelected(SelectionEvent e) {
//				handleRemoveButton();
//			}
//		});
//		otherBtn = button;
		
		button = new Button(buttonPanel, SWT.PUSH);
		formData = new FormData();
		formData.left = new FormAttachment(0, 0);
//		formData.top = new FormAttachment(otherBtn, 20);
		formData.top = new FormAttachment(0, 0);
		formData.width = 68;
		button.setLayoutData(formData);
		button.setText("&Save");
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				handleSaveButton();
			}
		});
		otherBtn = button;

		button = new Button(buttonPanel, SWT.PUSH);
		formData = new FormData();
		formData.left = new FormAttachment(0, 0);
		formData.top = new FormAttachment(otherBtn, 10);
		formData.width = 68;
		button.setLayoutData(formData);
		button.setText("De&fault");
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				File file = new File(USER_PROFILE_XML);
				if (file.exists()) {
					file.delete();
				}
				input = createDefaultElement();
				treeViewer.setInput(getInput());
				treeViewer.expandAll();
				treeViewer.setCheckedElements(getInput().getChildren().toArray());
			}
		});
		otherBtn = button;
		
		Composite propPanel = new Composite(basePanel, SWT.NONE);
		gridData = new GridData(GridData.FILL_BOTH);
		propPanel.setLayoutData(gridData);
		
		layout = new GridLayout();
		propPanel.setLayout(layout);
		layout.numColumns = 3;
		layout.verticalSpacing = 9;
		
		label = new Label(propPanel, SWT.NONE);
		gridData = new GridData();
		gridData.widthHint = 60;
		gridData.verticalAlignment = GridData.BEGINNING;
		label.setLayoutData(gridData);
		label.setText("&Properties:");
		
		// properties table
		table = createTable(propPanel);
		tableViewer = createTableViewer(table);
		tableViewer.setLabelProvider(new ExportTableViewerLabelProvider2());
		tableViewer.setContentProvider(new ExportTableViewerContentProvider2());
		tableViewer.setCellEditors(getTableViewerCellEditors(table));
		tableViewer.setCellModifier(getTableViewerCellModifier());
		tableViewer.setInput(getProperties());
		hookTableViewerDragAndDrop(tableViewer);

		Composite buttonPanel2 = new Composite(propPanel, SWT.NONE);
		gridData = new GridData();
		gridData.widthHint = 68;
		gridData.verticalAlignment = GridData.BEGINNING;
		buttonPanel2.setLayoutData(gridData);
		
		formLayout = new FormLayout();
		buttonPanel2.setLayout(formLayout);
		
		button = new Button(buttonPanel2, SWT.PUSH);
		formData = new FormData();
		formData.left = new FormAttachment(0, 0);
		formData.top = new FormAttachment(0, 0);
		formData.width = 68;
		button.setLayoutData(formData);
		button.setText("A&dd");
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				handleAddPropertyButton();
			}
		});
		otherBtn = button;

		button = new Button(buttonPanel2, SWT.PUSH);
		formData = new FormData();
		formData.left = new FormAttachment(0, 0);
		formData.top = new FormAttachment(otherBtn, 10);
		formData.width = 68;
		button.setLayoutData(formData);
		button.setText("&Remove");
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				handleRemovePropertyButton();
			}
		});
		otherBtn = button;
		
		return basePanel;
	}
	
	private void hookViewerContextMenu(final StructuredViewer viewer) {
		Control control = viewer.getControl();
		MenuManager menuMgr = new MenuManager("#PopupMenu"); //$NON-NLS-1$
		menuMgr.setRemoveAllWhenShown(true);
		Menu menu = menuMgr.createContextMenu(control);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				IStructuredSelection ss = (IStructuredSelection)viewer.getSelection();
				Object first = ss.getFirstElement();
				
				if (first instanceof Element){
					manager.add(new AddElementAction());
					manager.add(new AddAttributeAction());
					manager.add(new RemoveAction());
				}
				else if (first instanceof Attribute) {
					manager.add(new RemoveAction());
				}
			}
		});
		control.setMenu(menu);
	}
	
	private void hookTreeViewerDragAndDrop(StructuredViewer viewer) {
		int ops = DND.DROP_MOVE;
		TreeViewerDragAdapter dragListener = new TreeViewerDragAdapter(viewer);
		viewer.addDragSupport(ops, dragListener.getSupportedDragTransfers(), dragListener);
		
		TreeViewerDropAdapter dropListener = new TreeViewerDropAdapter(viewer); 
		viewer.addDropSupport(ops, dropListener.getSupportedDropTransfers(), dropListener);
	}
	
	private void hookTableViewerDragAndDrop(StructuredViewer viewer) {
		int ops = DND.DROP_MOVE;
		TableViewerDragAdapter dragListener = new TableViewerDragAdapter(viewer);
		viewer.addDragSupport(ops, dragListener.getSupportedDragTransfers(), dragListener);
		
		TableViewerDropAdapter dropListener = new TableViewerDropAdapter(viewer); 
		viewer.addDropSupport(ops, dropListener.getSupportedDropTransfers(), dropListener);
	}
	
	private Tree createTree(Composite parent) {
		int style = SWT.MULTI | SWT.BORDER | SWT.H_SCROLL |
				SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.CHECK;
		Tree tree = new Tree(parent, style);
		
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.heightHint = 200;
		tree.setLayoutData(gridData);
		
		tree.setLinesVisible(true);
		tree.setHeaderVisible(true);

		TreeColumn column = new TreeColumn(tree, SWT.LEFT);
		column.setText(ELEMENT_COLUMN);
		column.setWidth(200);

		column = new TreeColumn(tree, SWT.LEFT);
		column.setText("Text");
		column.setWidth(180);
		
		return tree;
	}
	
	private ContainerCheckedTreeViewer createTreeViewer(Tree tree) {
		ContainerCheckedTreeViewer treeViewer = new ContainerCheckedTreeViewer(tree);
		treeViewer.setUseHashlookup(true);
		treeViewer.setColumnProperties(columnNames);
		return treeViewer;
	}
	
	private CellEditor[] getTreeViewerCellEditors(Composite parent) {
		return new CellEditor[] {
				new TextCellEditor(parent),
				new TextCellEditor(parent),
		};
	}
	
	private ICellModifier getTreeViewerCellModifier() {
		return new ICellModifier() {
			
			@Override
			public void modify(Object element, String property, Object value) {
				if (element instanceof org.eclipse.swt.widgets.TreeItem) {
					Object data = ((org.eclipse.swt.widgets.TreeItem)element).getData();
					if (property.equals(ELEMENT_COLUMN)) {
						if (data instanceof Element) {
							((Element)data).setName((String)value);
						}
						if (data instanceof Attribute) {
							((Attribute)data).setName((String)value);
						}
					}
					else if (property.equals(METADATA_COLUMN)) {
						if (data instanceof Element) {
							((Element)data).setText((String)value);
						}
						else if (data instanceof Attribute) {
							((Attribute)data).setValue((String)value);
						}
					}
					
					treeViewer.refresh();
				}
			}
			
			@Override
			public Object getValue(Object element, String property) {
				if (property.equals(ELEMENT_COLUMN)) {
					if (element instanceof Element) {
						return ((Element)element).getName();
					}
					if (element instanceof Attribute) {
						return ((Attribute)element).getName();
					}
				}
				if (property.equals(METADATA_COLUMN)) {
					if (element instanceof Element) {
						return ((Element)element).getText();
					}
					if (element instanceof Attribute) {
						return ((Attribute)element).getValue();
					}
				}
				return null;
			}
			
			@Override
			public boolean canModify(Object element, String property) {
				if (property.equals(ELEMENT_COLUMN)) {
					return true;
				}
				if (property.equals(METADATA_COLUMN)) {
					if (element instanceof Element &&
							((Element)element).getChildren().size() > 0) {
						return false;
					}
					return true;
				}
				return false;
			}
		};
	}
	
	private Table createTable(Composite parent) {
		int style = SWT.MULTI | SWT.BORDER | SWT.H_SCROLL |
				SWT.V_SCROLL | SWT.FULL_SELECTION;
		Table table = new Table(parent, style);
		
		GridData gridData = new GridData(GridData.FILL_BOTH);
		table.setLayoutData(gridData);
		
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		TableColumn column = new TableColumn(table, SWT.CENTER, 0);
		column.setText(NAME_COLUMN);
		column.setWidth(100);

		column = new TableColumn(table, SWT.LEFT, 1);
		column.setText(TEXT_COLUMN);
		column.setWidth(100);

		column = new TableColumn(table, SWT.LEFT, 2);
		column.setText(COMMENT_COLUMN);
		column.setWidth(180);

		return table;
	}
	
	private TableViewer createTableViewer(Table table) {
		TableViewer tableViewer = new TableViewer(table);
		tableViewer.setUseHashlookup(true);
		tableViewer.setColumnProperties(columnNames2);
		return tableViewer;
	}
	
	private CellEditor[] getTableViewerCellEditors(Composite parent) {
		return new CellEditor[] {
				new TextCellEditor(parent),
				new TextCellEditor(parent),
				new TextCellEditor(parent),
		};
	}
	
	private ICellModifier getTableViewerCellModifier() {
		return new ICellModifier() {
			
			@Override
			public void modify(Object element, String property, Object value) {
				if (element instanceof org.eclipse.swt.widgets.TableItem) {
					Object data = ((org.eclipse.swt.widgets.TableItem)element).getData();
					if (data instanceof Element) {
						Element ele = (Element)data;
						if (property.equals(NAME_COLUMN)) {
							ele.setAttribute(ProfileStrings.NAME, (String)value);
						}
						else if (property.equals(TEXT_COLUMN)) {
							ele.setText((String)value);
						}
						else if (property.equals(COMMENT_COLUMN)) {
							String comment = (String)value;
							Element parent = ele.getParentElement();
							int index = parent.indexOf(ele);
							if (index > -1) {
								int i = index + 1;
								for (; i < parent.getContentSize(); i++) {
									Content content = parent.getContent(i);
									if (content instanceof Comment) {
										((Comment) content).setText(comment);
										i = -1;
										break;
									}
									else if (content instanceof Element) {
										i = index + 1;
										break;
									}
								}
								
								if (i == -1) {
									;
								} else if (i < parent.getContentSize()) {
									parent.addContent(i, new Comment(comment));
								} else {
									parent.addContent(new Comment(comment));
								}
							}
						}
					}
					tableViewer.refresh();
				}
			}
			
			@Override
			public Object getValue(Object element, String property) {
				if (property.equals(NAME_COLUMN)) {
					if (element instanceof Element) {
						return ((Element)element).getAttributeValue(ProfileStrings.NAME);
					}
				}
				else if (property.equals(TEXT_COLUMN)) {
					if (element instanceof Element) {
						return ((Element)element).getTextTrim();
					}
				}
				else if (property.equals(COMMENT_COLUMN)) {
					if (element instanceof Element) {
						Element parent = ((Element)element).getParentElement();
						int index = parent.indexOf((Element)element);
						if (index > -1) {
							for (int i = index + 1; i < parent.getContentSize(); i++) {
								Content content = parent.getContent(i);
								if (content instanceof Comment) {
									return ((Comment) content).getText();
								}
								else if (content instanceof Element) {
									break;
								}
							}
						}
						return "";
					}
				}

				return null;
			}
			
			@Override
			public boolean canModify(Object element, String property) {
				if (property.equals(NAME_COLUMN)) {
					return true;
				}
				else if (property.equals(TEXT_COLUMN)) {
					return true;
				}
				else if (property.equals(COMMENT_COLUMN)) {
					return true;
				}
				return false;
			}
		};
	}
	
	public Element getResult() {
		Element result = getInput();
		Element props = getProperties();
		if (result.getContent().contains(props) == false) {
			result.addContent(getProperties());
		}
		return result;
	}
	
	private Element getInput() {
		if (input == null) {
			input = createDefaultElement();
		}
		return input;
	}

	private Element createDefaultElement() {
		SAXBuilder builder = new SAXBuilder();
		Document document = null;
		Element defaultRoot = new Element(ProfileStrings.COMPONENT_PROFILE);
		try {
			InputStream stream = null;
			File file = new File(USER_PROFILE_XML);
			if (file.exists()) {
				stream = new FileInputStream(file);
			}
			if (stream == null) {
				stream = ExportComponentProfilePage2.class.getResourceAsStream(DEFAULT_PROFILE_XML);
			}
			
			document = builder.build(stream);
		} catch (JDOMException e1) {
			return defaultRoot;
		} catch (IOException e1) {
			return defaultRoot;
		}
		
		if (document == null) {
			return defaultRoot;
		}
		
		Element root = document.getRootElement();
		if (root != null && root.getName().equals(ProfileStrings.COMPONENT_PROFILE)) {
			Element props = root.getChild(ProfileStrings.PROPERTIES);
			if (props != null) {
				root.removeContent(props);	// prop의 Parent를 제거해야 함
				properties = props;
			}
			return root;
		}
		return defaultRoot;
	}
	
	private Element getProperties() {
		if (properties == null) {
			properties = new Element(ProfileStrings.PROPERTIES);
		}
		return properties;
	}
	
	private void handleAddElementButton() {
		IStructuredSelection selection = (IStructuredSelection)treeViewer.getSelection();
		Object firstElement = selection.getFirstElement();
		if (firstElement instanceof Element) {
			Element element = (Element)firstElement;
			int count = 0;
			String newName = "newElement";
			while (element.getChild(newName) != null) {
				count++;
				newName = "newElement" + count;
			}
			
			for (int i=0; i<element.getContentSize(); i++) {
				Content content = element.getContent(i);
				if (content instanceof Text) {
					element.removeContent(content);
				}
			}
			element.addContent(new Element(newName));
		}
		treeViewer.refresh();
	}
	
	private void handleAddAttributeButton() {
		IStructuredSelection selection = (IStructuredSelection)treeViewer.getSelection();
		Object firstElement = selection.getFirstElement();
		if (firstElement instanceof Element) {
			Element element = (Element)firstElement;
			int count = 0;
			String newName = "newAttribute";
			while (element.getChild(newName) != null) {
				count++;
				newName = "newAttribute" + count;
			}
			element.setAttribute(newName, "");
		}
		treeViewer.refresh();
	}
	
	private void handleRemoveButton() {
		IStructuredSelection selection = (IStructuredSelection)treeViewer.getSelection();
		Iterator iter = selection.iterator();
		while (iter.hasNext()) {
			Object obj = iter.next();
			if (obj instanceof Element) {
				Element parent = ((Element)obj).getParentElement();
				if (parent != null) {
					parent.removeContent((Element)obj);
				}
			}
			else if (obj instanceof Attribute) {
				Element parent = ((Attribute)obj).getParent();
				if (parent != null) {
					parent.removeAttribute((Attribute)obj);
				}
			}
		}
		treeViewer.refresh();
	}
	
	private void handleAddPropertyButton() {
		IStructuredSelection selection = (IStructuredSelection)tableViewer.getSelection();
		Object firstElement = selection.getFirstElement();
		if (firstElement == null || firstElement instanceof Element) {
			Element properties = (firstElement == null) ? getProperties()
					: ((Element)firstElement).getParentElement();
			List children = properties.getChildren(ProfileStrings.PROPERTY);
			int index = children.indexOf(firstElement);
			
			int count = 0;
			String newName = "newProperty";
			boolean isContained = true;
			while (isContained) {
				isContained = false;
				for (Object obj : children) {
					Attribute attr = null;
					if (obj instanceof Element) {
						attr = ((Element)obj).getAttribute(ProfileStrings.NAME);
					}
					else if (obj instanceof Attribute) {
						attr = (Attribute)obj;
					}
					
					if (attr != null && newName.equals(attr.getValue())) {
						isContained = true;
						break;
					}
				}
				
				if (isContained) {
					count++;
					newName = "newProperty" + count;
				}
			}

			Element newElement = new Element(ProfileStrings.PROPERTY);
			newElement.setAttribute(ProfileStrings.NAME, newName);
			newElement.setText("");
			properties.addContent(newElement);
		}
		tableViewer.refresh();
	}
	
	private void handleRemovePropertyButton() {
		IStructuredSelection selection = (IStructuredSelection)tableViewer.getSelection();
		Iterator iter = selection.iterator();
		while (iter.hasNext()) {
			Object obj = iter.next();
			if (obj instanceof Element) {
				Element parent = ((Element)obj).getParentElement();
				if (parent != null) {
					parent.removeContent((Element)obj);
				}
			}
			else if (obj instanceof Attribute) {
				Element parent = ((Attribute)obj).getParent();
				if (parent != null) {
					parent.removeAttribute((Attribute)obj);
				}
			}
		}
		tableViewer.refresh();
	}
	
	private void handleSaveButton() {
		IRunnableWithProgress op = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor) throws InvocationTargetException {
				try {
					monitor.beginTask("Save user profile...", 1);
					XMLOutputter outp = new XMLOutputter();
					Format form = outp.getFormat();
					form.setEncoding("UTF-8");
					form.setLineSeparator("\r\n");
					form.setIndent("	");
					form.setTextMode(Format.TextMode.TRIM);
					outp.setFormat(form);
					
					Element root = getResult();
					if (root.getParent() != null) {
						// RootElement의 Parent(Document)를 제거해 줘야 함
						root.getParent().removeContent(root);
					}

					Document doc = new Document(root);
					FileWriter writer;
					writer = new FileWriter(USER_PROFILE_XML);
					outp.output(doc, writer);
					writer.close();
					monitor.worked(1);
				} catch (IOException e) {
					throw new InvocationTargetException(e);
				} finally {
					monitor.done();
				}
			}
		};
		try {
			getContainer().run(true, false, op);
		} catch (InterruptedException e) {
			return;
		} catch (InvocationTargetException e) {
			Throwable realException = e.getTargetException();
			MessageDialog.openError(getShell(), "Error", realException.getMessage());
			return;
		}
	}
	
	
	/**
	 * AddElement
	 */
	class AddElementAction extends Action {
		public AddElementAction() {
			super("AddElement");
		}
		
		public void run() {
			handleAddElementButton();
		}
	}
	
	/**
	 * AddAttribute
	 */
	class AddAttributeAction extends Action {
		public AddAttributeAction() {
			super("AddAttribute");
		}
		
		public void run() {
			handleAddAttributeButton();
		}
	}
	
	/**
	 * RemoveElement, RemoveAttribute
	 */
	class RemoveAction extends Action {
		public RemoveAction() {
			super("Remove");
		}
		
		public void run() {
			handleRemoveButton();
		}
	}
}
