package kr.re.etri.tpl.diagram.views.taskcontrol;

import java.io.File;

import kr.re.etri.tpl.IRTMDefines;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.ViewerDropAdapter;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.dnd.TransferData;

public class TaskControllerDropAdapter extends ViewerDropAdapter {

	private static final Transfer[] SUPPORTED_DROP_TRANSFERS = new Transfer[] {
		LocalSelectionTransfer.getTransfer(), FileTransfer.getInstance(),
//		TPLLocalSelectionTransfer.getTransfer()	// KJH 20101105, new transfer
	};
	
	protected TaskControllerDropAdapter(StructuredViewer viewer) {
		super(viewer);
		setFeedbackEnabled(false);
	}

	public Transfer[] getSupportedDropTransfers() {
		return SUPPORTED_DROP_TRANSFERS;
	}

	public TaskControllerTreeViewer getViewer() {
		return (TaskControllerTreeViewer)super.getViewer();
	}
	
	@Override
	public boolean performDrop(Object data) {
		TaskControlManager manager = TaskControlManager.getDefault();
		if (!manager.isConnected()) {
			return false;
		}

		DropTargetEvent event = getCurrentEvent();
		boolean result = false;
		if (LocalSelectionTransfer.getTransfer().isSupportedType(event.currentDataType)) {
			if (data instanceof IStructuredSelection) {
				IStructuredSelection selection = (IStructuredSelection)data;
				Object[] elements = selection.toArray();
				
				for (Object element : elements) {
					if (element instanceof IResource) {
						if (manager.upload((IResource)element, getCurrentTarget())) {
							result = true;
						}
					}
					else if (element instanceof TreeItem2) {
						manager.move((TreeItem2)element, getCurrentTarget());
						result = true;
					}
				}
			}
		}
		else if (FileTransfer.getInstance().isSupportedType(event.currentDataType)) {
			if (data instanceof String[]) {
				String[] paths = (String[])data;
				for (String path : paths) {
					File file = new File(path);
					if (manager.upload(file, getCurrentTarget())) {
						result = true;
					}
				}
			}
		}
//		else if (TPLLocalSelectionTransfer.getTransfer().isSupportedType(event.currentDataType)) {
//			if (data instanceof IStructuredSelection) {
//				IStructuredSelection selection = (IStructuredSelection)data;
//				Object[] elements = selection.toArray();
//			}
//		}
		
		if (result) {
			getViewer().update();
		}
		
		return result;
	}

	@Override
	public boolean validateDrop(Object target, int operation,
			TransferData transferType) {
		
		if (!(target instanceof TreeItem2)) {
			return false;
		}
		
		TreeItem2 targetItem = (TreeItem2)target;
		if (!targetItem.isContainer()) {
			return false;
		}
		
		if (LocalSelectionTransfer.getTransfer().isSupportedType(transferType)) {
			IStructuredSelection selection = (IStructuredSelection)LocalSelectionTransfer.getTransfer().getSelection();
			Object[] elements = selection.toArray();
			// KJH 20110308 s,
			for (Object element : elements) {
				if (element instanceof IResource) {
					if (validateScript(element)) {
						return true;
					}
				}
				else if (element instanceof TreeItem2) {
					TreeItem2 item = (TreeItem2)element;
					TreeItem2 parent = item.getParent();
					if (targetItem == item) {
						continue;
					} else if (targetItem == parent) {
						continue;
					}
					
					// target이 선택된 item의 하위노드에 포함되면 false
					parent = targetItem.getParent();
					while (parent != null && parent != item) {
						parent = parent.getParent();
					}
					
					if (parent == null) {
						return true;
					}
				}
			}	// KJH 20110308 e,
		}
		else if (FileTransfer.getInstance().isSupportedType(transferType)) {
			String[] paths = (String[])FileTransfer.getInstance().nativeToJava(transferType);
			// KJH 20110308 s,
			for (String path : paths) {
				File file = new File(path);
				if (file.isDirectory()) {
					return true;
				}
				else if (file.isFile() && path.endsWith(IRTMDefines.TASK_SCRIPT_EXTENSION_NAME)) {
					return true;
				}
			}	// KJH 20110308 e,
		}
//		else if (TPLLocalSelectionTransfer.getTransfer().isSupportedType(transferType)) {
//			IStructuredSelection selection = (IStructuredSelection)TPLLocalSelectionTransfer
//					.getTransfer().getSelection();
//			Object[] elements = selection.toArray();
//			for (Object element : elements) {
//				if (element instanceof TreeItem2) {
//					TreeItem2 item = (TreeItem2)element;
//					TreeItem2 parent = item.getParent();
//					if (targetItem == item) {
//						continue;
//					} else if (targetItem == parent) {
//						continue;
//					}
//					
//					// target이 선택된 item의 하위노드에 포함되면 false
//					parent = targetItem.getParent();
//					while (parent != null && parent != item) {
//						parent = parent.getParent();
//					}
//					
//					if (parent == null) {
//						return true;
//					}
//				}
//			}
//		}
		
		return false;
	}

	@Override
	public void dragEnter(DropTargetEvent event) {
		if (LocalSelectionTransfer.getTransfer().isSupportedType(event.currentDataType)) {
			if (event.detail == DND.DROP_MOVE || event.detail == DND.DROP_DEFAULT) {
				if ((event.operations & DND.DROP_COPY) != 0) {
					event.detail = DND.DROP_COPY;
				} else {
					event.detail = DND.DROP_NONE;
				}
			}
		}
		else if (FileTransfer.getInstance().isSupportedType(event.currentDataType)) {
			if (event.detail == DND.DROP_MOVE || event.detail == DND.DROP_DEFAULT) {
				if ((event.operations & DND.DROP_COPY) != 0) {
					event.detail = DND.DROP_COPY;
				} else {
					event.detail = DND.DROP_NONE;
				}
			}
		}
//		else if (TPLLocalSelectionTransfer.getTransfer().isSupportedType(event.currentDataType)) {
//			if (event.detail == DND.DROP_MOVE || event.detail == DND.DROP_DEFAULT) {
//				if ((event.operations & DND.DROP_COPY) != 0) {
//					event.detail = DND.DROP_COPY;
//				} else {
//					event.detail = DND.DROP_NONE;
//				}
//			}
//		}
	}

	protected boolean validateScript(Object resource) {
		boolean result = false;
		
		if (resource instanceof IFile) {
			IFile ifile = (IFile)resource;
			if (IRTMDefines.TASK_SCRIPT_EXTENSION_NAME.equalsIgnoreCase(ifile.getFileExtension())) {
				return true;
			}
		}
		else if (resource instanceof IFolder) {
			try {
				for (IResource member : ((IFolder) resource).members()) {
					if (validateScript(member)) {
						return true;
					}
				}
			} catch (CoreException e) {
				result = false;
			}
		}
		return result;
	}

}
