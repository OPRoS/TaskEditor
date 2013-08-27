package kr.re.etri.tpl.diagram.views.taskcontrol;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.util.Policy;
import org.eclipse.swt.dnd.TransferData;

public class TPLLocalSelectionTransfer extends LocalSelectionTransfer {
	private static final String TYPE_NAME = "tpl_local-selection-transfer-format" + (new Long(System.currentTimeMillis())).toString(); //$NON-NLS-1$;

	private static final int TYPEID = registerType(TYPE_NAME);

	private static final TPLLocalSelectionTransfer INSTANCE = new TPLLocalSelectionTransfer();

	public static TPLLocalSelectionTransfer getTransfer() {
		return INSTANCE;
	}

	@Override
	protected int[] getTypeIds() {
		return new int[] {TYPEID};
	}

	@Override
	protected String[] getTypeNames() {
		return new String[] {TYPE_NAME};
	}

	private boolean isInvalidNativeType(Object result) {
		return !(result instanceof byte[])
		|| !TYPE_NAME.equals(new String((byte[]) result));
	}

	public void javaToNative(Object object, TransferData transferData) {
		byte[] check = TYPE_NAME.getBytes();
		super.javaToNative(check, transferData);
	}

	public Object nativeToJava(TransferData transferData) {
		Object result = super.nativeToJava(transferData);
		if (isInvalidNativeType(result)) {
			Policy.getLog().log(new Status(
					IStatus.ERROR,
					Policy.JFACE,
					IStatus.ERROR,
					JFaceResources.getString("LocalSelectionTransfer.errorMessage"), null)); //$NON-NLS-1$
		}
		return super.getSelection();
	}
}
