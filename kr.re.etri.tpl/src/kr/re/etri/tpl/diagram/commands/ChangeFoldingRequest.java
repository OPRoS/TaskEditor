package kr.re.etri.tpl.diagram.commands;

import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.requests.ChangeBoundsRequest;

/**
 * Model ���̾�׷��� Task ���̾�׷��� ��ġ��/�ݱ�����
 * ��û Ŭ�����̴�.
 * @author sfline
 *
 */
public class ChangeFoldingRequest extends ChangeBoundsRequest {

	/**
	 * ��ġ��/�ݱ� ��û
	 * collapsed�� true�� �ݱ�, �׷��� ������ ��ġ�⸦ �����Ѵ�.
	 */
	private boolean collapsed;
	
	/**
	 * ��ġ��/�ݱ� ���� �������� �����Ѵ�.
	 * @param collapsed true �� �ݱ�, �׷��� ������ ��ġ�⸦ �����Ѵ�.
	 */
	public ChangeFoldingRequest(boolean collapsed) {
		this.collapsed = collapsed;
	}

	/**
	 * ��û Ÿ�԰� �Բ� ��ġ��/�ݱ� ���� �������� �����Ѵ�.
	 *
	 * @param type ��û�� ���� Ÿ������ GEF ����� ũ�⸦ �����ϴ� ���̹Ƿ�
	 * RequestConstants.REQ_RESIZE�� �����Ǿ�� �Ѵ�.
	 */
	public ChangeFoldingRequest(boolean collapsed, Object type) {
		super(type);

		this.collapsed = collapsed;
	}

	/**
	 * ��ġ��/�ݱ� ��û ���¸� �����Ѵ�.
	 * @return
	 */
	public boolean isCollapsed() {
		return this.collapsed;
	}
	
	/**
	 * ��ġ��/�ݱ� ��û ���¸� �����Ѵ�.
	 * @param collapsed
	 */
	public void setCollapsed(boolean collapsed) {
		this.collapsed = collapsed;
	}
}
