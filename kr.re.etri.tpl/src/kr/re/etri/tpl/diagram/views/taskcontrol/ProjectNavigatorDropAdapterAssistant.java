package kr.re.etri.tpl.diagram.views.taskcontrol;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import kr.re.etri.tpl.TaskModelPlugin;
import kr.re.etri.tpl.diagram.views.taskcontrol.actions.CopyFilesAndFoldersOperation;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.TransferData;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.navigator.CommonDropAdapter;
import org.eclipse.ui.navigator.CommonDropAdapterAssistant;

public class ProjectNavigatorDropAdapterAssistant extends
		CommonDropAdapterAssistant {
	
	@Override
	public IStatus handleDrop(CommonDropAdapter aDropAdapter,
			DropTargetEvent aDropTargetEvent, Object aTarget) {
		if (aDropAdapter.getCurrentTarget() == null
				|| aDropTargetEvent.data == null) {
			return Status.CANCEL_STATUS;
		}
		
		IStatus status = null;
		TransferData currentTransfer = aDropAdapter.getCurrentTransfer();
		if (LocalSelectionTransfer.getTransfer().isSupportedType(
				currentTransfer)) {
			status = performFileDrop(aDropAdapter, getSelectedItems());
		}

		return status;
	}

	@Override
	public IStatus validateDrop(Object target, int operation,
			TransferData transferType) {
		if (!(target instanceof IResource)) {
			return new Status(IStatus.INFO, TaskModelPlugin.PLUGIN_ID, "Target must be a resource");
		}
		
		IResource resource = (IResource)target;
		if (!resource.isAccessible()) {
			return new Status(IStatus.ERROR, TaskModelPlugin.PLUGIN_ID, "Cannot drop a resource into closed project");
		}
		
		IContainer destination = getActualTarget(resource);
		if (destination.getType() == IResource.ROOT) {
			return new Status(IStatus.ERROR, TaskModelPlugin.PLUGIN_ID, "Resources cannot be siblings of projects");
		}
		
		if (LocalSelectionTransfer.getTransfer().isSupportedType(transferType)) {
			TreeItem2[] items = getSelectedItems();
			if (items.length == 0) {
				return Status.CANCEL_STATUS;
			}
		}
		
		return Status.OK_STATUS;
	}

	private IContainer getActualTarget(IResource mouseTarget) {
		/* if cursor is on a file, return the parent */
		if (mouseTarget.getType() == IResource.FILE) {
			return mouseTarget.getParent();
		}
		/* otherwise the mouseTarget is the real target */
		return (IContainer) mouseTarget;
	}
	
	private TreeItem2[] getSelectedItems() {
		ISelection selection = LocalSelectionTransfer.getTransfer().getSelection();
		if (selection instanceof IStructuredSelection) {
			return getSelectedItems((IStructuredSelection)selection);
		}
		return new TreeItem2[0];
	}
	
	private TreeItem2[] getSelectedItems(IStructuredSelection selection) {
		List<TreeItem2> selectedItems = new ArrayList<TreeItem2>();

		for (Iterator i = selection.iterator(); i.hasNext();) {
			Object o = i.next();
			if (o instanceof TreeItem2) {
				TreeItem2 item = (TreeItem2)o;
				selectedItems.add(item);
			}
		}
		
		return selectedItems.toArray(new TreeItem2[selectedItems.size()]);
	}
	
	private IStatus performFileDrop(CommonDropAdapter anAdapter, Object data) {
		MultiStatus problems = new MultiStatus(TaskModelPlugin.PLUGIN_ID, 0,
				"Problems occurred while importing resources", null);
		mergeStatus(problems,
				validateDrop(anAdapter.getCurrentTarget(), anAdapter.getCurrentOperation(),
						anAdapter.getCurrentTransfer()));
		
		final IContainer target = getActualTarget((IResource) anAdapter
				.getCurrentTarget());
		final TreeItem2[] items = (TreeItem2[]) data;
		// Run the import operation asynchronously.
		// Otherwise the drag source (e.g., Windows Explorer) will be blocked
		// while the operation executes. Fixes bug 16478.
		Display.getCurrent().asyncExec(new Runnable() {
			public void run() {
				getShell().forceActive();
				CopyFilesAndFoldersOperation operation = new CopyFilesAndFoldersOperation(
						getShell());
				operation.copyFilesAndFolders(items, target, null);
			}
		});
		return problems;
	}
	
	private void mergeStatus(MultiStatus status, IStatus toMerge) {
		if (!toMerge.isOK()) {
			status.merge(toMerge);
		}
	}

}
