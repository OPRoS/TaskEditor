package kr.re.etri.tpl.diagram.dialogs;

import java.util.ArrayList;
import java.util.List;

import kr.re.etri.tpl.IRTMDefines;
import kr.re.etri.tpl.IconStrings;
import kr.re.etri.tpl.TaskModelPlugin;
import kr.re.etri.tpl.diagram.editors.TPLDiagramEditor;
import kr.re.etri.tpl.diagram.util.MarkerLogger;
import kr.re.etri.tpl.diagram.util.TPLUtil;
import kr.re.etri.tpl.grammar.ParserUtil;
import kr.re.etri.tpl.taskmodel.BehaviorElement;
import kr.re.etri.tpl.taskmodel.IncludedElement;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.ModelDiagram;
import kr.re.etri.tpl.taskmodel.ReferAttribute;
import kr.re.etri.tpl.taskmodel.ReferElement;
import kr.re.etri.tpl.taskmodel.SubDiagram;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.AbstractTreeViewer;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

public class BehaviorSelectionDialog extends TitleAreaDialog {

	private ModelDiagram rootDiagram;
	private IEditorInput editorInput;
	private TreeViewer treeViewer;
	private ItemElement currentBehavior;
	private IncludedElement incElement;	// KJH 20101015
	private TreeNode rootElement;
	
	// KJH 20101012, 현재 선택된 파일
//	private IFile currentFile;
	
	// KJH 20100913, 현재 선택된 노드
	private TreeNode selected;
	// KJH 20100913, 현재 다이어그램에 이미 있는 참조 Behavior
	private List<BehaviorElement> behaviorList = new ArrayList<BehaviorElement>();
	
	class TreeNode {
		private List<TreeNode> children = new ArrayList<TreeNode>();
		private TreeNode parent;
		private Object data;
		
		public TreeNode() {
			parent = null;
			data = null;
		}
		public TreeNode[] getChildren() {
			return children.toArray(new TreeNode[children.size()]);
		}
		public int getChildrenCount() {
			return children.size();
		}
		public void addChild(TreeNode child) {
			children.add(child);
		}
		public void removeChild(TreeNode child) {
			children.remove(child);
		}
		public TreeNode getParent() {
			return parent;
		}
		public void setParent(TreeNode parent) {
			this.parent = parent;
		}
		public Object getData() {
			return data;
		}
		public void setData(Object data) {
			this.data = data;
		}
		@Override
		public String toString() {
			if (data instanceof IResource) {
				return ((IResource)data).getName();
			}
			else if (data instanceof ItemElement) {
				return ((ItemElement)data).getName();
			}
			return "";
		}
	}
	
	class BehaviorTreeContentProvider implements ITreeContentProvider {

		@Override
		public Object[] getChildren(Object element) {
			if (element instanceof TreeNode) {
				return ((TreeNode)element).getChildren();
			}
			return null;
		}

		@Override
		public Object getParent(Object element) {
			if (element instanceof TreeNode)
				return ((TreeNode)element).getParent();
			return null;
		}

		@Override
		public boolean hasChildren(Object element) {
			if (element instanceof TreeNode) {
				return ((TreeNode)element).getChildrenCount() > 0;
			}
			return false;
		}

		@Override
		public Object[] getElements(Object inputElement) {
			if (rootElement == null)
				rootElement = (TreeNode)inputElement;
			return getChildren(inputElement);
		}

		@Override
		public void dispose() {
		}

		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}
	}

	class BehaviorTreeLabelProvider extends LabelProvider {

		@Override
		public Image getImage(Object element) {
			if (element instanceof TreeNode) {
				Object data = ((TreeNode)element).getData();

				if (data instanceof BehaviorElement)
					return TaskModelPlugin.getImageDescriptor(IconStrings.BEHAVIOR_16).createImage();

				String imageKey = ISharedImages.IMG_OBJ_ELEMENT;

				if (data instanceof IContainer)
					imageKey = ISharedImages.IMG_OBJ_FOLDER;
				return PlatformUI.getWorkbench().getSharedImages().getImage(imageKey);
			}
			return null;
		}

		@Override
		public String getText(Object element) {
			if (element instanceof TreeNode)
				return ((TreeNode)element).toString();
			return null;
		}
	}
	
	public ItemElement getValue() {
		return currentBehavior;
	}

	public void setValue(ItemElement element) {
		this.currentBehavior = element;
	}
	
	// KJH 20101015 s,
	public IncludedElement getIncludedElement() {
		return incElement;
	}
	
	public void setIncludedElement(IncludedElement incElement) {
		this.incElement = incElement;
	}
	// KJH 20101015 e,
	
	public BehaviorSelectionDialog(Shell parentShell, IEditorPart editor) {
		super(parentShell);
		this.editorInput = editor.getEditorInput();
		if (editor instanceof TPLDiagramEditor) {
			this.rootDiagram = (ModelDiagram)((TPLDiagramEditor)editor).getModel();
		}
	}

	@Override
	protected Control createContents(Composite parent) {
		Control contents = super.createContents(parent);

		Shell shell = getShell();
		Point size = new Point(500, 400);
		Point location = getInitialLocation(size);
		shell.setBounds(getConstrainedShellBounds(new Rectangle(location.x,
				location.y, size.x, size.y)));
		
		setTitle("Select Behavior");
		setMessage("Select behavior to refer");
		
		return contents;
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite)super.createDialogArea(parent);
		
		composite.setLayout(new GridLayout(1, false));
		GridData gridData = new GridData(GridData.FILL_BOTH);
		composite.setLayoutData(gridData);
		
		treeViewer = new TreeViewer(composite, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER );
		treeViewer.getTree().setLayoutData(gridData);
		
		treeViewer.setContentProvider(new BehaviorTreeContentProvider());
		treeViewer.setLabelProvider(new BehaviorTreeLabelProvider());
		treeViewer.setAutoExpandLevel(AbstractTreeViewer.ALL_LEVELS);
		treeViewer.setInput(getInput());
		
		// KJH 20100913, init selection
		if (selected != null) {
			ISelection selection = new StructuredSelection(selected);
			treeViewer.setSelection(selection);
		}
		
		treeViewer.setComparator(new ViewerComparator() {

			@Override
			public int category(Object element) {
				if (element instanceof TreeNode) {
					Object data = ((TreeNode)element).getData();
					if (data instanceof IContainer)
						return 0;
					else if (data instanceof IFile)
						return 1;
					else if (data instanceof ItemElement)
						return 2;
				}
				return super.category(element);
			}
			
			@Override
			public int compare(Viewer viewer, Object e1, Object e2) {
				return super.compare(viewer, e1, e2);
			}

			@Override
			public boolean isSorterProperty(Object element, String property) {
				return true;
			}

		});

		treeViewer.addSelectionChangedListener(new ISelectionChangedListener(){

			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				if( event.getSelection().isEmpty())
					return;
				
				setValue(null);
				
				if( event.getSelection() instanceof TreeSelection){
					TreeSelection selection = (TreeSelection)event.getSelection();
					if (selection.getFirstElement() instanceof TreeNode) {
						TreeNode selectedNode = (TreeNode)selection.getFirstElement();
						if (selectedNode.getData() instanceof ItemElement) {
							// KJH 20101018 s, 
							ItemElement item = (ItemElement)selectedNode.getData();
							ItemElement parent = item.getParent();
							while (parent != null &&
									parent instanceof ModelDiagram == false &&
									parent instanceof IncludedElement == false) {
								parent = parent.getParent();
							}

							String path = "";
							TreeNode parentTree = selectedNode.getParent();
							if (parentTree.getData() instanceof IFile) {
								IFile file = (IFile)parentTree.getData();
								path = file.getFullPath().toString();
								if (path.startsWith("/")) {
									path = path.substring(1);
								}
							}

							if (parent instanceof ModelDiagram) {
								parent = TPLUtil.convertTo((ModelDiagram)parent);
								((IncludedElement)parent).setIncludePath(path);
							}

							if (parent instanceof IncludedElement) {
								setIncludedElement((IncludedElement)parent);
								setValue(item);
							}
							// KJH 20101018 e, 
						}
					}
				}
			}
		});
		
		treeViewer.addDoubleClickListener(new IDoubleClickListener(){

			@Override
			public void doubleClick(DoubleClickEvent event) {
				if (getValue() instanceof BehaviorElement) {
					okPressed();
				}
			}
			
		});
		
		return composite;
	}

	@Override
	protected void buttonPressed(int buttonId) {
		if (buttonId == IDialogConstants.CANCEL_ID)	currentBehavior = null;
		super.buttonPressed(buttonId);
	}

	protected IFile getInputFile() {
		if (editorInput instanceof IFileEditorInput) {
			return ((IFileEditorInput)editorInput).getFile();
		}
		return null;
	}
	
	private Object getInput() {
		TreeNode treeNode = new TreeNode();
		
		IFile inputFile = getInputFile();
		if (inputFile != null) {
			// 이미 추가되어 있는 참조 behavior노드들 구하기
			SubDiagram subDiagram = rootDiagram.getSubDiagram();
			for (ReferElement refItem : subDiagram.getItems()) {
				if (refItem.getAttribute() == ReferAttribute.EXTERNAL) {
					ItemElement realModel = refItem.getRealModel();
					if (realModel instanceof BehaviorElement &&
							realModel.equals(currentBehavior) == false) {
						behaviorList.add((BehaviorElement)realModel);
					}
				}
			}
			
//			String script = rootDiagram.getScript();
//			if (script != null) {
//				DiagramResourceManager.setResource(script, rootDiagram.eResource());
//			}

			IProject project = inputFile.getProject();
			treeNode.setData(project);
			
			getTreeRoutine(treeNode, project);
		}		

		return treeNode;
	}
	
	// KJH 20101004, script 위주로 처리되도록 수정
	private void getTreeRoutine(TreeNode parent, IContainer container) {
		IResource[] members;
		try {
			members = container.members();
		} catch(CoreException e) {
			return;
		}

		for (IResource resource : members) {
			if (resource instanceof IFile) {
				IFile file = (IFile)resource;
				
				// 현재 다이어그램과 같은 파일이면 통과
				if (file.equals(getInputFile()))
					continue;
				
				ModelDiagram modelDiagram = null;

				if (IRTMDefines.TASK_SCRIPT_EXTENSION_NAME.equalsIgnoreCase(file.getFileExtension())) {
					String script = rootDiagram.getScript();
					if (script != null && script.equals(file.getFullPath().toString())) {
						continue;
					}

					MarkerLogger problemLogger = new MarkerLogger(file, MarkerLogger.TYPE_SCRIPT);
					try {
						modelDiagram = ParserUtil.parseModel(file, problemLogger);
					} catch (Exception e) {
						e.printStackTrace();
						return;
					}
				}
//				else if (IRTMDefines.TASK_DIAGRAM_EXTENSION_NAME.equals(file.getFileExtension())) {
//					Resource xmlResource = DiagramResourceManager.getResource(file);
//					if (xmlResource != null) {
//						EList<EObject> contents = xmlResource.getContents();
//						if (contents != null && contents.size() > 0) {
//							if (contents.get(0) instanceof ModelDiagram) {
//								modelDiagram = (ModelDiagram)contents.get(0);
//								String script = modelDiagram.getScript();
//								if (script != null) {
//									IProject project = file.getProject();
//									IResource finded = project.findMember(script);
//									if (finded instanceof IFile) {
//										modelDiagram = null;
//									}
//								}
//							}
//						}
//					}
//				}

				if (modelDiagram != null) {
					TreeNode treeNode = new TreeNode();
					treeNode.setData(file);

					String path = file.getFullPath().toString();
					if (path.startsWith("/")) {
						path = path.substring(1);
					}
					IncludedElement inclElement = TPLUtil.convertTo(modelDiagram);
					inclElement.setIncludePath(path);
					
					for (ItemElement item : inclElement.getItems()) {
						if (item instanceof BehaviorElement && behaviorList.contains(item) == false) {
							TreeNode treeNode2 = new TreeNode();
							treeNode2.setData(item);
							treeNode2.setParent(treeNode);
							treeNode.addChild(treeNode2);
							
							if (item.equals(currentBehavior)) {
								selected = treeNode2;
							}
						}
					}
					
					if (treeNode.getChildrenCount() > 0) {
						parent.addChild(treeNode);
					}
				}

			}
			else if (resource instanceof IContainer) {
				TreeNode treeNode = new TreeNode();
				treeNode.setData(resource);
				getTreeRoutine(treeNode, (IContainer)resource);

				if (treeNode.getChildrenCount() > 0) {
					treeNode.setParent(parent);
					parent.addChild(treeNode);
				}
			}
		}
	}
	
	private boolean isExistBehavior(BehaviorElement bhv) {
		for (BehaviorElement behavior : behaviorList) {
			if (isEqual(behavior, bhv)) {
				return true;
			}
		}
		
		return false;
	}
	
	private boolean isEqual(ItemElement b1, ItemElement b2) {
		if (b1 == null || b2 == null) {
			return false;
		}
		if (b1.getClass().equals(b2.getClass()) == false) {
			return false;
		}
		
		String s1 = b1.getName();
		String s2 = b2.getName();
		if (s1 == null || s1.equals(s2) == false) {
			return false;
		}
		
		ItemElement p1 = b1.getParent();
		ItemElement p2 = b2.getParent();
		if (p1 instanceof IncludedElement && p2 instanceof IncludedElement) {
			s1 = ((IncludedElement)p1).getIncludePath();
			s2 = ((IncludedElement)p2).getIncludePath();
			if (s1 == null || s1.equals(s2) == false) {
				return false;
			}
		}
		
		return true;
	}
}
