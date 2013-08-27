package kr.re.etri.tpl.script.debug.variables;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class DebugVariableContentProvider implements IStructuredContentProvider {

	@Override
	public Object[] getElements(Object inputElement) {
		return (Object[]) inputElement;
	}

	@Override
	public void dispose() {
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	}

}
