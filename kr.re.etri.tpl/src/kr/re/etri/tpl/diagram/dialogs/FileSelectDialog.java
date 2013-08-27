package kr.re.etri.tpl.diagram.dialogs;

import java.util.ArrayList;
import java.util.List;

import kr.re.etri.tpl.IRTMDefines;
import kr.re.etri.tpl.IconStrings;
import kr.re.etri.tpl.TaskModelPlugin;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;

public class FileSelectDialog extends TitleAreaDialog{
	
	public static final String I_INCLUDE_LABEL = "Include";
	
	private static Image RTSIMAGE;
	
	private Object rootElement;
	private final List<IFile> selectedItems;
	private TreeViewer treeViewer;
	
	private Button includeBtn, cancelBtn;
	
	public FileSelectDialog(Shell shell, List<IFile> result){
		super(shell);
		setShellStyle(SWT.SHELL_TRIM | SWT.RESIZE | SWT.APPLICATION_MODAL | getDefaultOrientation());
		this.selectedItems = result;
		
		RTSIMAGE = TaskModelPlugin.getImageDescriptor(IconStrings.RTS_16).createImage();
	}
	
	protected Control createContents(Composite parent){
		Control contents = super.createContents(parent);

		Shell shell = getShell();
		Point size = new Point(500, 400);
		Point location = getInitialLocation(size);
		shell.setBounds(getConstrainedShellBounds(new Rectangle(location.x,
				location.y, size.x, size.y)));
		
		setTitle("Select Script File");
		setMessage("Select the file(s) to include");
		
		return contents;
		
	}
	@Override
	public Control createDialogArea(Composite parent) {
		
		Composite composite = (Composite)super.createDialogArea(parent);
		
		composite.setLayout(new GridLayout(1, false));
		GridData gridData = new GridData(GridData.FILL_BOTH);
		composite.setLayoutData(gridData);
		
		treeViewer = new TreeViewer(composite, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER );
		treeViewer.getTree().setLayoutData(gridData);
		
		treeViewer.setContentProvider(new FileTreeContentProvider());
		treeViewer.setLabelProvider(new FileTreeLabelProvider());
		treeViewer.setAutoExpandLevel(2);
		treeViewer.setInput(getProject());
		
		treeViewer.addSelectionChangedListener(new ISelectionChangedListener(){

			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				if( event.getSelection().isEmpty())
					return;
				if( event.getSelection() instanceof TreeSelection){
					TreeSelection selection = (TreeSelection)event.getSelection();
					selectedItems.clear();
					
					for(Object res : selection.toList()) {
						if(res instanceof IFile && !selectedItems.contains(res)) {
							selectedItems.add((IFile)res);
							includeBtn.setEnabled(true);
						}else
							includeBtn.setEnabled(false);
					}
				}
			}
		});
		
		treeViewer.addDoubleClickListener(new IDoubleClickListener(){

			@Override
			public void doubleClick(DoubleClickEvent event) {
				buttonPressed(IDialogConstants.OK_ID);
			}
			
		});

		return composite;
	}
	
	class FileTreeContentProvider implements ITreeContentProvider{

		@Override
		public Object[] getChildren(Object parentElement) {
			ArrayList<IResource> filteredResources =null;
			IResource[] resources =null;
			if( parentElement instanceof IContainer ){
				try {
					resources = ((IContainer)parentElement).members();
					filteredResources = new ArrayList<IResource>();
					for( int i=0, n=resources.length; i<n; i++){
						if( resources[i] instanceof IContainer && isScriptFileIncluded(resources[i])){
							filteredResources.add(resources[i]);
						}else if( resources[i] instanceof IFile && 
								IRTMDefines.TASK_SCRIPT_EXTENSION_NAME.equalsIgnoreCase(resources[i].getFileExtension())){
							filteredResources.add(resources[i]);
						}else
							continue;
						
					}
					return filteredResources.toArray(new IResource[filteredResources.size()]);
//				return resources;
					
				} catch (CoreException e) { }
				
			}
			return null;
				
		}

		@Override
		public Object getParent(Object element) {
			return ((IResource)element).getParent();
		}

		@Override
		public boolean hasChildren(Object element) {
			
			IResource[] resources = (IResource[])getChildren(element);
			return resources==null?false:resources.length>0;
		}
		
		@Override
		public Object[] getElements(Object inputElement) {
			
			if(rootElement == null) {
				rootElement = inputElement;
				return new Object[]{inputElement};
			}
			
			return getChildren(inputElement);
		}

		@Override
		public void dispose() {} 		
		
		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {}
		
		private boolean isScriptFileIncluded(IResource source){
			
			if(!(source instanceof IContainer)){
				return false;
			}
			IResource[] resources;
			try {
				resources = ((IContainer)source).members();
			} catch (CoreException e) {
				return false;
			}
			
			for( int i=0,n=resources.length;i<n;i++){
//				System.out.println("Container: "+ resources[i]);
				if(resources[i] instanceof IContainer){
					
					if(isScriptFileIncluded(resources[i])){
						return true;
					}
				}
				// 파일일 경우 스크립트인지 검사
				else if( resources[i] instanceof IFile){
					//스크립트 파일이 아닐경우 계속 진행
					if(IRTMDefines.TASK_SCRIPT_EXTENSION_NAME.equalsIgnoreCase(((IFile)resources[i]).getFileExtension()) == false) {
						continue;
					}
					// 스크립트 파일이 있을 경우 true리턴
					else{
						return true;
					}
				}
			}
			return false;
		}
		
	}
	
	class FileTreeLabelProvider extends LabelProvider{

		@Override
		public Image getImage(Object element) {
			if (element instanceof IFile) {
				return RTSIMAGE;
			}
			
			if( element instanceof IContainer) {
				return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_FOLDER);
			}
			
			return null;
		}

		@Override
		public String getText(Object element) {
			return ((IResource)element).getName();
		}

	}
	
	private IProject getProject(){
			
		IWorkbench wb = PlatformUI.getWorkbench();
		IWorkbenchWindow wbw = wb.getActiveWorkbenchWindow();
		IWorkbenchPage wbp = wbw.getActivePage();
		IEditorPart ep = wbp.getActiveEditor();
		IEditorInput ei = ep.getEditorInput();
		
		if(ei instanceof FileEditorInput) {
			IFile contentFile = ((FileEditorInput)ei).getFile();
			return contentFile.getProject();
		}
		
		return null;
	}

	protected void createButtonsForButtonBar(Composite parent) {
		
		includeBtn = createButton(parent, IDialogConstants.OK_ID, I_INCLUDE_LABEL, false);
		includeBtn.setEnabled(false);
		cancelBtn = createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL,
				false);

	}

	protected void buttonPressed(int buttonId){
		if(buttonId== IDialogConstants.CANCEL_ID){
			selectedItems.clear();
		}
		this.setReturnCode(buttonId);
		close();
	}
}