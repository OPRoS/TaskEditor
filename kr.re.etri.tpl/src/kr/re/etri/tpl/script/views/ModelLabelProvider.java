package kr.re.etri.tpl.script.views;

import kr.re.etri.tpl.TaskModelPlugin;
import kr.re.etri.tpl.script.grammar.tree.FuncNode;
import kr.re.etri.tpl.script.grammar.tree.ITPLTree;
import kr.re.etri.tpl.script.grammar.tree.TPLTree;
import kr.re.etri.tpl.script.grammar.tree.VarNode;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;

public class ModelLabelProvider implements ILabelProvider {

	public ModelLabelProvider() {
		super();
	}

	public Image getImage(Object element) {
		if (element instanceof TPLTree) {
			TPLTree elem = (TPLTree) element;
			if(elem.getType().equals(ITPLTree.MODEL)){
				return TaskModelPlugin.getImageDescriptor("/icons/model.gif").createImage();
			} else if(elem.getType().equals(ITPLTree.INCLUDE)){
				return TaskModelPlugin.getImageDescriptor("/icons/include.gif").createImage();
			}else if(elem.getType() == ITPLTree.VAR){
				return TaskModelPlugin.getImageDescriptor("/icons/var.gif").createImage();
			}
			else if(elem.getType() == ITPLTree.FUNC){
				FuncNode node = (FuncNode)elem;
				if(node.getAction() == null){
					return TaskModelPlugin.getImageDescriptor("/icons/function.gif").createImage();
				}
				return TaskModelPlugin.getImageDescriptor("/icons/action.gif").createImage();
			}
		}
		return null;
	}

	public String getText(Object element) {
		if (element instanceof TPLTree) {
			TPLTree elem = (TPLTree) element;
			if(elem.getType() == ITPLTree.INCLUDE){
				return elem.getName()+" <"+elem.getType()+">";
			}
			else if(elem.getType() == ITPLTree.MODEL){
				return elem.getName()+" <"+elem.getType()+">";
			}
			else if(elem.getType() == ITPLTree.VAR){
				VarNode node = (VarNode)elem;
				return node.getVType()+" "+node.getName()+" <"+node.getVMod()+">";
			}
			else if(elem.getType() == ITPLTree.FUNC){
				FuncNode node = (FuncNode)elem;
				String action=node.getAction();
				if(action == null){
					action="function";
				}
				return node.getRtn()+" "+node.getName()+" <"+action+">";
			}
		}
		return null;
	}

	public void addListener(ILabelProviderListener listener) {
	}

	public void dispose() {
	}

	public boolean isLabelProperty(Object element, String property) {
		return false;
	}

	public void removeListener(ILabelProviderListener listener) {
	}

}