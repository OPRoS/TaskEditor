package kr.re.etri.tpl.diagram.commands;

import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.requests.ChangeBoundsRequest;

/**
 * Model 다이어그램의 Task 다이어그램의 펼치지/닫기위한
 * 요청 클래스이다.
 * @author sfline
 *
 */
public class ChangeFoldingRequest extends ChangeBoundsRequest {

	/**
	 * 펼치기/닫기 요청
	 * collapsed가 true면 닫기, 그렇지 않으면 펼치기를 수행한다.
	 */
	private boolean collapsed;
	
	/**
	 * 펼치기/닫기 선택 사항으로 생성한다.
	 * @param collapsed true 면 닫기, 그렇지 않으면 펼치기를 수행한다.
	 */
	public ChangeFoldingRequest(boolean collapsed) {
		this.collapsed = collapsed;
	}

	/**
	 * 요청 타입과 함께 펼치기/닫기 선택 사항으로 생성한다.
	 *
	 * @param type 요청에 대한 타입으로 GEF 노드의 크기를 변경하는 것이므로
	 * RequestConstants.REQ_RESIZE가 설정되어야 한다.
	 */
	public ChangeFoldingRequest(boolean collapsed, Object type) {
		super(type);

		this.collapsed = collapsed;
	}

	/**
	 * 펼치지/닫기 요청 상태를 제공한다.
	 * @return
	 */
	public boolean isCollapsed() {
		return this.collapsed;
	}
	
	/**
	 * 펼치기/닫기 요청 상태를 설정한다.
	 * @param collapsed
	 */
	public void setCollapsed(boolean collapsed) {
		this.collapsed = collapsed;
	}
}
