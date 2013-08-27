package kr.re.etri.tpl.script.grammar.tree2;

import org.eclipse.jface.viewers.IElementComparer;

public class ElementComparer implements IElementComparer {

	@Override
	public boolean equals(Object a, Object b) {
		if(a.equals(b))
			return true;
		
		if(a instanceof IScriptNode && b instanceof IScriptNode){
		
			IScriptNode node1 = (IScriptNode)a;
			IScriptNode node2 = (IScriptNode)b;

			if(node1.getName() != null && node1.getName().equals(node2.getName())){
				if(node1.getParent() != null && node2.getParent() != null)
					return equals(node1.getParent(), node2.getParent());
			}
			// KJH 20100720
//			if(node1.getParent() == null && node2.getParent() == null)
//				return true;
		}
		
		return false;
	}

	@Override
	public int hashCode(Object element) {
		return ((IScriptNode)element).hashCode();
	}

}
