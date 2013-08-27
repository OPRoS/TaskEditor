package kr.re.etri.tpl.diagram.views.taskcontrol;

import org.eclipse.jface.viewers.ViewerComparator;

public class TaskControllerComparator extends ViewerComparator {
	@Override
	public int category(Object element) {
		if (element instanceof TreeItem2) {
			TreeItem2 item = (TreeItem2)element;
			if (item.isContainer()) {
				return 0;
			} else {
				return 1;
			}
		}
		return super.category(element);
	}
}
