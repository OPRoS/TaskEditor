package kr.re.etri.tpl.diagram.editors;

import kr.re.etri.tpl.taskmodel.ItemElement;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

public class DiagramEditorInput implements IEditorInput {
	private ItemElement model;
	public DiagramEditorInput(ItemElement model) {
		this.model = model;
	}

	@Override
	public boolean exists() {
		return model != null;
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		return null;
	}

	@Override
	public String getName() {
		if(model != null) {
			return model.getName();
		}
		
		return "";
	}

	@Override
	public IPersistableElement getPersistable() {
		return null;
	}

	@Override
	public String getToolTipText() {
		if(model != null) {
			return model.getName();
		}
		
		return "";
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object getAdapter(Class adapter) {
		return null;
	}

	public Object getModel() {
		return model;
	}
}
