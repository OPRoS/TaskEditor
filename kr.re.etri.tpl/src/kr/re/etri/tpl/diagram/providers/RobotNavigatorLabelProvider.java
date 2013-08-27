package kr.re.etri.tpl.diagram.providers;

import kr.re.etri.tpl.diagram.util.TreeItem;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

public class RobotNavigatorLabelProvider extends LabelProvider {

	public String getText(Object obj) {
		return obj.toString();
	}
	public Image getImage(Object obj) {
		String imageKey = null;
		if (obj instanceof TreeItem) {
			if(((TreeItem) obj).hasChildren()) {
				imageKey = ISharedImages.IMG_OBJ_FOLDER;
			}
			else {
				imageKey = ISharedImages.IMG_OBJ_ELEMENT;
			}
		}

		if(imageKey == null) {
			return null;
		}

		return PlatformUI.getWorkbench().getSharedImages().getImage(imageKey);
	}
}
