package kr.re.etri.tpl.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;


/**
 * 스크립트 파일을 입력으로 받아 모델을 생성하는 메뉴 실행 클래스이다.
 * 이 메뉴 기능은 2차년도 수행 계획에 포함되어있어 기능 구현되어
 * 있지 않음.
 * @author sfline
 *
 */
public class ReverseEngineeringActionDelegate implements
		IWorkbenchWindowActionDelegate {

	/**
	 * 메뉴 ID
	 */
	public final static String actionId = "kr.re.etri.tpl.actions.reverseengineeringactiondelegate";
	
	/**
	 * 워크 벤치 윈도우
	 */
	private IWorkbenchWindow window;

	/**
	 * 생성자
	 */
	public ReverseEngineeringActionDelegate() {
	}

	/**
	 * dispose시 자원을 해제하여야 한다.
	 */
//	@Override
	public void dispose() {
	}

	/**
	 * 메뉴 실행시 초기화를 수행한다.
	 * @param window 워크벤치 윈도우
	 * @Override
	 */
	public void init(IWorkbenchWindow window) {
		this.window = window;
	}

	/**
	 * 선택된 메뉴 기능을 수행한다.
	 * @Override
	 */
	public void run(IAction action) {
	}

	/**
	 * 메뉴 활성화를 변경한다.
	 * @param action 활성화 여부를 확인할 메뉴 Action
	 * @param selection 메뉴 Action의 선택 여부
	 * @Override
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		action.setEnabled(false);
	}
}
