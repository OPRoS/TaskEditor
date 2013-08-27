package kr.re.etri.tpl.script.editors.outline;

import kr.re.etri.tpl.IconStrings;
import kr.re.etri.tpl.TaskModelPlugin;
import kr.re.etri.tpl.grammar.RFSMParser;
import kr.re.etri.tpl.script.grammar.tree2.IConnectorNode;
import kr.re.etri.tpl.script.grammar.tree2.IFuncNode;
import kr.re.etri.tpl.script.grammar.tree2.IScriptNode;
import kr.re.etri.tpl.script.grammar.tree2.IScriptRootNode;
import kr.re.etri.tpl.script.grammar.tree2.IStateNode;
import kr.re.etri.tpl.script.grammar.tree2.IVarNode;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

public class OutlineLabelProvider2 extends LabelProvider {

	@Override
	public Image getImage(Object element) {	// diagram/outline/ÀÇ °¢ TreeEditPart.createImageForModel(ItemElement)
		if (element instanceof IScriptNode) {
			IScriptNode elem = (IScriptNode)element;
			if(RFSMParser.BEHA == elem.getType()){
				return TaskModelPlugin.getImageDescriptor(IconStrings.BEHAVIOR_16).createImage();
			}
			if(RFSMParser.STATE == elem.getType()){
				if (IScriptNode.INITIAL.equals(((IStateNode)elem).getModifier())) {
					return TaskModelPlugin.getImageDescriptor(IconStrings.INITSTATE_16).createImage();
				}
				if (IScriptNode.GOAL.equals(((IStateNode)elem).getModifier())) {
					return TaskModelPlugin.getImageDescriptor(IconStrings.GOALSTATE_16).createImage();
				}
				return TaskModelPlugin.getImageDescriptor(IconStrings.STATE_16).createImage();
			}
			if(RFSMParser.INCL == elem.getType() || RFSMParser.RTDL == elem.getType()){
				if (elem instanceof IScriptRootNode && ((IScriptRootNode)elem).isGlobal()) {
					return TaskModelPlugin.getImageDescriptor("/icons/club/#Ig.png").createImage();
				}
				return TaskModelPlugin.getImageDescriptor("/icons/club/#I.png").createImage();
			}
			if(RFSMParser.ENUM == elem.getType()){
				return TaskModelPlugin.getImageDescriptor("/icons/club/enum.png").createImage();
			}
			if(RFSMParser.MODEL == elem.getType()){
				return TaskModelPlugin.getImageDescriptor("/icons/club/model.png").createImage();
			}
			if(RFSMParser.WORKER2 == elem.getType()){
				return TaskModelPlugin.getImageDescriptor(IconStrings.TASK_16).createImage();
			}
			if(RFSMParser.ACTION == elem.getType()){
				if(IScriptNode.ENTRY.equals(elem.getName()))
					return TaskModelPlugin.getImageDescriptor(IconStrings.ENTRY_16).createImage();
				if(IScriptNode.STAY.equals(elem.getName()))
					return TaskModelPlugin.getImageDescriptor(IconStrings.STAY_16).createImage();
				if(IScriptNode.EXIT.equals(elem.getName()))
					return TaskModelPlugin.getImageDescriptor(IconStrings.EXIT_16).createImage();
			}
			if (RFSMParser.GOTO == elem.getType()) {
				return TaskModelPlugin.getImageDescriptor(IconStrings.TRANSITION_16).createImage();
			}
			if (RFSMParser.IVK == elem.getType()) {
				return TaskModelPlugin.getImageDescriptor(IconStrings.EXPAND_16).createImage();
			}
			if(RFSMParser.ELEM == elem.getType()){
				return TaskModelPlugin.getImageDescriptor("icons/club/untitle.png").createImage();
			}
			if(RFSMParser.VAR == elem.getType()){
				if (IScriptNode.IN.equals(((IVarNode)elem).getVMod())) {
					return TaskModelPlugin.getImageDescriptor("icons/club/input.png").createImage();
				}
				if (IScriptNode.OUT.equals(((IVarNode)elem).getVMod())) {
					return TaskModelPlugin.getImageDescriptor("icons/club/output.png").createImage();
				}
			}
			if(RFSMParser.FUNC == elem.getType()){
				if (((IFuncNode)elem).isAction() == true) {
					return TaskModelPlugin.getImageDescriptor("icons/club/action.png").createImage();
				}
				return TaskModelPlugin.getImageDescriptor("icons/club/function.png").createImage();
			}
			if(RFSMParser.PARM == elem.getType()){
				return TaskModelPlugin.getImageDescriptor("icons/club/parameter.png").createImage();
			}
			if (RFSMParser.CNT == elem.getType()) {	// KJH 20110208 s, connector icon
				if (IScriptNode.CONEXER.equals(((IConnectorNode)elem).getConMod())) {
					return TaskModelPlugin.getImageDescriptor(IconStrings.CONEXER_16).createImage();
				}
				if (IScriptNode.SEQEXER.equals(((IConnectorNode)elem).getConMod())) {
					return TaskModelPlugin.getImageDescriptor(IconStrings.SEQEXER_16).createImage();
				}
			}	// KJH 20110208 e, connector icon
			// KJH 20110210 s, constr, destr icon
			if (RFSMParser.CONSTR == elem.getType()) {
				return TaskModelPlugin.getImageDescriptor(IconStrings.CONSTR_16).createImage();
			}
			if (RFSMParser.DEST == elem.getType()) {
				return TaskModelPlugin.getImageDescriptor(IconStrings.DESTR_16).createImage();
			}
			// KJH 20110210 e, constr, destr icon
			if (RFSMParser.RUN == elem.getType()) {
				return TaskModelPlugin.getImageDescriptor(IconStrings.WITH_16).createImage();
			}
		}
		return null;
	}

	@Override
	public String getText(Object element) {
		if (element instanceof IScriptNode) {
			IScriptNode elem = (IScriptNode) element;
			String textToShow = elem.getFullName();

			if(textToShow.equals(""))
				textToShow = "(empty)";
			
			return textToShow;
		}
		return null;
	}

}
