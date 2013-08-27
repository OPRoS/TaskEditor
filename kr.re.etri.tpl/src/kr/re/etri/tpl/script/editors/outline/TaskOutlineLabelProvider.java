package kr.re.etri.tpl.script.editors.outline;

import kr.re.etri.tpl.TaskModelPlugin;
import kr.re.etri.tpl.script.grammar.tree.ITPLTree;
import kr.re.etri.tpl.script.grammar.tree.TPLTree;
import kr.re.etri.tpl.script.grammar.tree.TranNode;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;

public class TaskOutlineLabelProvider implements ILabelProvider {

	public TaskOutlineLabelProvider() {
		super();
	}

	@Override
	public Image getImage(Object element) {
		if (element instanceof TPLTree) {
			TPLTree elem = (TPLTree) element;
			if(elem.getType().equals(ITPLTree.BEHAVIOR)){
				return TaskModelPlugin.getImageDescriptor("/icons/club/behavior.png").createImage();
			}
			else if(elem.getType().equals(ITPLTree.STATE)){
				return TaskModelPlugin.getImageDescriptor("/icons/club/state.png").createImage();
			}
			else if(elem.getType().equals(ITPLTree.INCLUDE)){
				return TaskModelPlugin.getImageDescriptor("/icons/club/#I.png").createImage();
			}
			else if(elem.getType().equals(ITPLTree.ENUM)){
				return TaskModelPlugin.getImageDescriptor("/icons/club/enum.png").createImage();
			}
			else if(elem.getType().equals(ITPLTree.MODEL)){
				return TaskModelPlugin.getImageDescriptor("/icons/club/model.png").createImage();
			}
			else if(elem.getType().equals(ITPLTree.WORKER)){
				return TaskModelPlugin.getImageDescriptor("/icons/club/task_for_TaskView02.png").createImage();
			}
			else if(elem.getType().equals(ITPLTree.TASK)){
				return TaskModelPlugin.getImageDescriptor("/icons/club/b.png").createImage();
			}
		}
		return null;
	}

	@Override
	public String getText(Object element) {
		if (element instanceof TPLTree) {
			TPLTree elem = (TPLTree) element;
		
			String textToShow = elem.getName();
//			if(elem instanceof TranNode){
//				textToShow += " ("+((TranNode)elem).getRelationShip()+")";
//			}
			return textToShow;
		}
		return null;
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
