package kr.re.etri.tpl.script.debug;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import kr.re.etri.tpl.script.editors.TPLScriptEditor;

import org.eclipse.debug.ui.actions.IToggleBreakpointsTarget;
import org.eclipse.debug.ui.actions.IToggleBreakpointsTargetFactory;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchPart;

public class TPLToggleBreakpointsTargetFactory implements
		IToggleBreakpointsTargetFactory {
	
	public static String ID = "kr.re.etri.tpl.script.debug.TPLToggleBreakpointsTargetFactory";

	public TPLToggleBreakpointsTargetFactory() {
	}

	@Override
	public IToggleBreakpointsTarget createToggleTarget(String targetID) {
		return new TPLLineBreakpointAdapter();
//		return new ToggleBreakpointAdapter();
	}

	@Override
	public String getDefaultToggleTarget(IWorkbenchPart part,
			ISelection selection) {
		return null;
	}

	@Override
	public String getToggleTargetDescription(String targetID) {
		return "TPL";
	}

	@Override
	public String getToggleTargetName(String targetID) {
		return "TPL";
	}

	@Override
	public Set getToggleTargets(IWorkbenchPart part, ISelection selection) {
		if (getEditor(part) != null) {
			Set targets = new HashSet();
			targets.add(ID);
			return targets;
		}
		return Collections.EMPTY_SET;
	}
	
	private TPLScriptEditor getEditor(IWorkbenchPart part) {
		if (part instanceof TPLScriptEditor) {
			return (TPLScriptEditor)part;
		}
		return null;
	}

}
