package kr.re.etri.tpl.diagram.views.taskcontrol.actions;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import kr.re.etri.tpl.IRTMDefines;
import kr.re.etri.tpl.diagram.views.taskcontrol.DownloadMessageReader;
import kr.re.etri.tpl.diagram.views.taskcontrol.TaskControlManager;
import kr.re.etri.tpl.diagram.views.taskcontrol.TreeItem2;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.swt.widgets.Shell;

public class CopyFilesAndFoldersOperation {
	/**
	 * The parent shell used to show any dialogs.
	 */
	private Shell messageShell;
	
	public CopyFilesAndFoldersOperation(Shell shell) {
		messageShell = shell;
	}
	
	public void copyFilesAndFolders(TreeItem2[] items, IContainer destination, IProgressMonitor monitor) {
		Assert.isTrue(destination != null && destination.exists());		
		Assert.isNotNull(items);
		
		if (messageShell == null || messageShell.isDisposed()) {
			return;
		}

		for (TreeItem2 item : items) {
			if (item.isContainer()) {
				copyFolder(item, destination, monitor);
			}
			else {
				copyFile(item, destination, monitor);
			}
		}
	}
	
	public void copyFolder(TreeItem2 item, IContainer destination, IProgressMonitor monitor) {
		Assert.isTrue(item.isContainer());
		
		String name = item.getText();
		IFolder folder = destination.getFolder(new Path(name));
		if (!folder.exists()) {
			try {
				folder.create(true, true, monitor);
			} catch (CoreException e) {
				e.printStackTrace();
				return;
			}
		}
		
		for (Object child : item.getChildren()) {
			if (child instanceof TreeItem2) {
				TreeItem2 childItem = (TreeItem2)child;
				if (childItem.isContainer()) {
					copyFolder(childItem, folder, monitor);
				}
				else {
					copyFile(childItem, folder, monitor);
				}
			}
		}
	}
	
	public void copyFile(TreeItem2 item, final IContainer destination, IProgressMonitor monitor) {
		Assert.isTrue(!item.isContainer());

		String name = item.getText();
		String path = item.getPath();
		
		if (!name.endsWith(IRTMDefines.TASK_SCRIPT_EXTENSION_NAME)) {
			return;
		}
		
//		if (path.startsWith("/")) {
//			path = path.substring(1);
//		}

		final IFile file = destination.getFile(new Path(name));

		DownloadRunnable runnable = new DownloadRunnable() {
			@Override
			public void run() {
				String content = getContent();
				try {
					InputStream stream = new ByteArrayInputStream(content.getBytes());
					if (file.exists()) {
						file.setContents(stream, true, true, null);
					}
					else {
						file.create(stream, true, null);
					}
					destination.refreshLocal(IResource.DEPTH_INFINITE, null);
				} catch (CoreException e) {
					e.printStackTrace();
				}
			}
		};

		DownloadMessageReader.getDefault().setDisplay(messageShell.getDisplay());
		DownloadMessageReader.getDefault().addRunnable(runnable);
		TaskControlManager.getDefault().download(path);
	}
}
