package kr.re.etri.tpl.script.editors.outline;

import kr.re.etri.tpl.TaskModelPlugin;
import kr.re.etri.tpl.script.grammar.tree.FuncNode;
import kr.re.etri.tpl.script.grammar.tree.ITPLTree;
import kr.re.etri.tpl.script.grammar.tree.ParamNode;
import kr.re.etri.tpl.script.grammar.tree.TPLTree;
import kr.re.etri.tpl.script.grammar.tree.TranNode;
import kr.re.etri.tpl.script.grammar.tree.VarNode;
import kr.re.etri.tpl.taskmodel.Function;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;

public class ModelOutlineLabelProvider implements ILabelProvider {

	public ModelOutlineLabelProvider() {
		super();
	}

	@Override
	public Image getImage(Object element) {
		if (element instanceof TPLTree) {
			TPLTree elem = (TPLTree) element;
			if(elem.getType().equals(ITPLTree.INCLUDE)){
				return TaskModelPlugin.getImageDescriptor("/icons/club/#I.png").createImage();
			}
			else if(elem.getType().equals(ITPLTree.ENUM)){
				return TaskModelPlugin.getImageDescriptor("/icons/club/enum.png").createImage();
			}
			else if(elem.getType().equals(ITPLTree.MODEL)){
				return TaskModelPlugin.getImageDescriptor("/icons/club/model.png").createImage();
			}
			else if(elem.getType().equals(ITPLTree.ELEM)){
				return TaskModelPlugin.getImageDescriptor("icons/club/untitle.png").createImage();
			}
			else if(elem.getType().equals(ITPLTree.VAR)){
				if(elem instanceof VarNode){
					String vMod = ((VarNode)elem).getVMod();
					
					if("in".equals(vMod))
						return TaskModelPlugin.getImageDescriptor("icons/club/input.png").createImage();
					else if("out".equals(vMod))
						return TaskModelPlugin.getImageDescriptor("icons/club/output.png").createImage();
					else
						return TaskModelPlugin.getImageDescriptor("icons/club/enum.png").createImage();
				}
			}
			else if(elem.getType().equals(ITPLTree.FUNC)){
				if(elem instanceof FuncNode){
					String type = ((FuncNode)elem).getAction();
					if(ITPLTree.ACTION.equals(type))
						return TaskModelPlugin.getImageDescriptor("icons/club/action.png").createImage();
				}
				return TaskModelPlugin.getImageDescriptor("icons/club/function.png").createImage();
			}
			else if(elem.getType().equals(ITPLTree.PARAM)){
				return TaskModelPlugin.getImageDescriptor("icons/club/parameter.png").createImage();
			}
		}
		return null;
	}

	@Override
	public String getText(Object element) {
		if (element instanceof TPLTree) {
			TPLTree elem = (TPLTree) element;
		
			String textToShow = elem.getName();
			if(elem instanceof VarNode){
				VarNode vNode = (VarNode)elem;
				textToShow = vNode.getVMod() +" "+ vNode.getVType() +" "+ vNode.getName();
			}
			else if(elem instanceof FuncNode){
				FuncNode fNode = (FuncNode)elem;
				textToShow = fNode.getRtn() +" "+ fNode.getName();
			}
			else if(elem instanceof ParamNode){
				ParamNode pNode = (ParamNode)elem;
				textToShow = pNode.getDataType() +" "+ pNode.getName();
			}
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
