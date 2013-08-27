package kr.re.etri.tpl.diagram.views.taskcontrol.dialog;

import kr.re.etri.tpl.TaskModelPlugin;
import kr.re.etri.tpl.diagram.views.taskcontrol.TaskControlManager;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.views.markers.internal.TaskMarker;

public class TaskLabelProvider implements ITableLabelProvider {
	TaskControlManager manager = TaskControlManager.getDefault();
	
	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		if (columnIndex == 0) {
			String text = element.toString();
			if (manager.isRunningTask(text)) {
				return TaskModelPlugin.getImageDescriptor("/icons/club/task_run.png").createImage();
			}
			return TaskModelPlugin.getImageDescriptor("/icons/club/task_stop.png").createImage();
		}
		return null;
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		if (columnIndex == 1) {
			return element.toString();
		}
		return "";
	}

	@Override
	public void addListener(ILabelProviderListener listener) {
	}

	@Override
	public void dispose() {
	}

	@Override
	public boolean isLabelProperty(Object element, String property) {
		return false;
	}

	@Override
	public void removeListener(ILabelProviderListener listener) {
	}

}
