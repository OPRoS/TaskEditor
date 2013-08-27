package kr.re.etri.tpl.diagram.providers;

import kr.re.etri.tpl.taskmodel.ActionElement;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.ModelDiagram;
import kr.re.etri.tpl.taskmodel.StateAction;
import kr.re.etri.tpl.taskmodel.StateElement;
import kr.re.etri.tpl.taskmodel.BehaviorElement;
import kr.re.etri.tpl.taskmodel.TaskElement;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class SymbolNavigatorContentProvider implements
		IStructuredContentProvider, ITreeContentProvider {

	private Object rootElement;
	
	public SymbolNavigatorContentProvider()
	{
	}
	@Override
	public Object[] getElements(Object inputElement) {
		if(rootElement == null) {
			rootElement = inputElement;
		}
		return getChildren(inputElement);
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// TODO Auto-generated method stub

	}

	@Override
	public Object[] getChildren(Object parentElement) {
		if(parentElement == null) {
			return null;
		}

		if(parentElement instanceof ModelDiagram) {
			return ((ModelDiagram)parentElement).getItems().toArray();
		}
		else if(parentElement instanceof BehaviorElement) {
			return ((BehaviorElement)parentElement).getStates().toArray();
		}
		else if(parentElement instanceof StateElement) {
//			return ((StateElement)parentElement).getItems().toArray();
		}
		else if(parentElement instanceof StateAction) {
//			return ((ModelDiagram)parentElement).getItems().toArray();
		}
		else if(parentElement instanceof ActionElement) {
//			return ((ModelDiagram)parentElement).getItems().toArray();
		}

		return null;
	}

	@Override
	public Object getParent(Object element) {
		if(element == null) {
			return null;
		}

		if(element instanceof ItemElement) {
			return ((ItemElement)element).getParent();
		}
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		if(element == null) {
			return false;
		}
		
		if(element instanceof ModelDiagram) {
			return ((ModelDiagram)element).getItems().size() > 0;
		}
		else if(element instanceof BehaviorElement) {
			return ((BehaviorElement)element).getStates().size() > 0;
		}

		return false;
	}
}
