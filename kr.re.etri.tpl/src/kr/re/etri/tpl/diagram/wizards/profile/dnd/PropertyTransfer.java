package kr.re.etri.tpl.diagram.wizards.profile.dnd;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.util.Policy;
import org.eclipse.swt.dnd.TransferData;

public class PropertyTransfer extends LocalSelectionTransfer {
	private static PropertyTransfer instance = new PropertyTransfer();
	private static final String TYPE_NAME = "property-transfer-format";
	private static final int TYPEID = registerType(TYPE_NAME);

	/**
	 * Returns the singleton gadget transfer instance.
	 */
	public static PropertyTransfer getTransfer() {
		return instance;
	}
	/**
	 * Avoid explicit instantiation
	 */
	private PropertyTransfer() {
	}
	/*
	 * Method declared on Transfer.
	 */
	protected int[] getTypeIds() {
		return new int[] { TYPEID };
	}
	/*
	 * Method declared on Transfer.
	 */
	protected String[] getTypeNames() {
		return new String[] { TYPE_NAME };
	}
	/*
	 * Method declared on Transfer.
	 */
	public void javaToNative(Object object, TransferData transferData) {
		byte[] bytes = TYPE_NAME.getBytes();
		if (bytes != null)
			super.javaToNative(bytes, transferData);
	}
	/*
	 * Method declared on Transfer.
	 */
	public Object nativeToJava(TransferData transferData) {
		Object result = super.nativeToJava(transferData);
		if (isInvalidNativeType(result)) {
			Policy.getLog().log(new Status(
					IStatus.ERROR,
					Policy.JFACE,
					IStatus.ERROR,
					JFaceResources.getString("PropertyTransfer.errorMessage"), null)); //$NON-NLS-1$
		}
		return super.getSelection();
	}
	
	private boolean isInvalidNativeType(Object result) {
		return !(result instanceof byte[])
		|| !TYPE_NAME.equals(new String((byte[]) result));
	}
}
