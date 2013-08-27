package kr.re.etri.tpl.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;


/**
 * ��ũ��Ʈ ������ �Է����� �޾� ���� �����ϴ� �޴� ���� Ŭ�����̴�.
 * �� �޴� ����� 2���⵵ ���� ��ȹ�� ���ԵǾ��־� ��� �����Ǿ�
 * ���� ����.
 * @author sfline
 *
 */
public class ReverseEngineeringActionDelegate implements
		IWorkbenchWindowActionDelegate {

	/**
	 * �޴� ID
	 */
	public final static String actionId = "kr.re.etri.tpl.actions.reverseengineeringactiondelegate";
	
	/**
	 * ��ũ ��ġ ������
	 */
	private IWorkbenchWindow window;

	/**
	 * ������
	 */
	public ReverseEngineeringActionDelegate() {
	}

	/**
	 * dispose�� �ڿ��� �����Ͽ��� �Ѵ�.
	 */
//	@Override
	public void dispose() {
	}

	/**
	 * �޴� ����� �ʱ�ȭ�� �����Ѵ�.
	 * @param window ��ũ��ġ ������
	 * @Override
	 */
	public void init(IWorkbenchWindow window) {
		this.window = window;
	}

	/**
	 * ���õ� �޴� ����� �����Ѵ�.
	 * @Override
	 */
	public void run(IAction action) {
	}

	/**
	 * �޴� Ȱ��ȭ�� �����Ѵ�.
	 * @param action Ȱ��ȭ ���θ� Ȯ���� �޴� Action
	 * @param selection �޴� Action�� ���� ����
	 * @Override
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		action.setEnabled(false);
	}
}
