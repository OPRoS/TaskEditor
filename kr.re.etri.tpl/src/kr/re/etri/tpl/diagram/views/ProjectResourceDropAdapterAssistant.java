package kr.re.etri.tpl.diagram.views;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.TransferData;
import org.eclipse.ui.navigator.CommonDropAdapter;
import org.eclipse.ui.navigator.resources.ResourceDropAdapterAssistant;

public class ProjectResourceDropAdapterAssistant extends
		ResourceDropAdapterAssistant {

	@Override
	public IStatus handleDrop(CommonDropAdapter aDropAdapter,
			DropTargetEvent aDropTargetEvent, Object aTarget) {
		// TODO Auto-generated method stub
		return super.handleDrop(aDropAdapter, aDropTargetEvent, aTarget);
	}

	@Override
	public IStatus handlePluginTransferDrop(
			IStructuredSelection aDragSelection, Object aDropTarget) {
		// TODO Auto-generated method stub
		return super.handlePluginTransferDrop(aDragSelection, aDropTarget);
	}

	@Override
	public boolean isSupportedType(TransferData aTransferType) {
		// TODO Auto-generated method stub
		return super.isSupportedType(aTransferType);
	}

	@Override
	public IStatus validateDrop(Object target, int aDropOperation,
			TransferData transferType) {
		// TODO Auto-generated method stub
		return super.validateDrop(target, aDropOperation, transferType);
	}

	@Override
	public IStatus validatePluginTransferDrop(
			IStructuredSelection aDragSelection, Object aDropTarget) {
		// TODO Auto-generated method stub
		return super.validatePluginTransferDrop(aDragSelection, aDropTarget);
	}

}
