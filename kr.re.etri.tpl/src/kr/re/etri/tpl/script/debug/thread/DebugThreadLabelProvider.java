package kr.re.etri.tpl.script.debug.thread;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

public class DebugThreadLabelProvider extends LabelProvider {

	@Override
	public Image getImage(Object element) {
		String imageKey = ISharedImages.IMG_OBJ_ELEMENT;			
		return PlatformUI.getWorkbench().getSharedImages().getImage(imageKey);
	}

	@Override
	public String getText(Object element) {
		if (element instanceof ThreadNode) {
			ThreadNode tn = (ThreadNode)element;
//			Variable[] vars = tn.getVariables();
//			if (vars.length > 0) {
//				return String.format("%s[%s]_%s::%s", tn.getName(), tn.getThid(),
//						vars[0].getDbgline(), vars[0].getFile());
//			}
//			else {
//				return String.format("%s[%s]", tn.getName(), tn.getThid());
//			}
			return String.format("%s[%s](%s)", tn.getName(), tn.getThid(), tn.isRunning() ? "Running" : "Suspend");
			
		}
		return super.getText(element);
	}

}
