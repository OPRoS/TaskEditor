package kr.re.etri.tpl.script.editors.outline;

import kr.re.etri.tpl.TaskModelPlugin;
import kr.re.etri.tpl.script.grammar.tree.FuncNode;
import kr.re.etri.tpl.script.grammar.tree.ITPLTree;
import kr.re.etri.tpl.script.grammar.tree.ParamNode;
import kr.re.etri.tpl.script.grammar.tree.TPLTree;
import kr.re.etri.tpl.script.grammar.tree.VarNode;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;

public class OutlineLabelProvider implements ILabelProvider {

	public OutlineLabelProvider() {
		super();
	}

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
			// KJH 20100517 s, 수정
			else if(elem.getType().equals(ITPLTree.WORKER)){
				return TaskModelPlugin.getImageDescriptor("/icons/club/task_for_TaskView02.png").createImage();
			}
			else if(elem.getType().equals(ITPLTree.TASK)){
				return TaskModelPlugin.getImageDescriptor("/icons/club/b.png").createImage();
			}
			// KJH 20100517 e, 수정
			// KJH 20100331 s, action image
			else if(elem.getType().equals(ITPLTree.STATEELEM)){
				if(elem.getName().equals("entry"))
					return TaskModelPlugin.getImageDescriptor("/icons/club/entry.png").createImage();
				else if(elem.getName().equals("stay"))
					return TaskModelPlugin.getImageDescriptor("/icons/club/do.png").createImage();
				if(elem.getName().equals("exit"))
					return TaskModelPlugin.getImageDescriptor("/icons/club/exit.png").createImage();
			}
			// KJH 20100331 e, action image
			
			// KJH 20100415 s, transition image
			else if(elem.getType().equals(ITPLTree.GOTO)){
				return TaskModelPlugin.getImageDescriptor("icons/club/transition.png").createImage();
			}
			else if(elem.getType().equals(ITPLTree.EXPAND)){
				return TaskModelPlugin.getImageDescriptor("icons/club/call_behavior.png").createImage();
			}
			// KJH 20100415 e, transition image
			
			// KJH 20100514 s, enum/model 하위 노드에 대한 image
			else if(elem.getType().equals(ITPLTree.ELEM)){
				return TaskModelPlugin.getImageDescriptor("icons/club/untitle.png").createImage();
			}
			else if(elem.getType().equals(ITPLTree.SYMBOL)){
				return TaskModelPlugin.getImageDescriptor("icons/club/input.png").createImage();
			}
			// KJH 20100514 e, enum/model 하위 노드에 대한 image
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

	public String getText(Object element) {
		if (element instanceof TPLTree) {
			TPLTree elem = (TPLTree) element;
		
			String textToShow = elem.getName();
//			if(elem instanceof TranNode){
//				TranNode tNode = (TranNode)elem;
//				if(tNode.getType() == ITPLTree.EXPAND)
//					textToShow = "Call for Behavior ("+ tNode.getName() +")";
//				else if(tNode.getType() == ITPLTree.GOTO)
//					textToShow = "Transition ("+ tNode.getName() +")";
//			}
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