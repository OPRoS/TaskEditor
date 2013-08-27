package kr.re.etri.tpl.actions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import kr.re.etri.tpl.TaskScriptNature;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

/**
 * Project Explorer에 선택된 프로젝트에 RTM Nature를 추가/삭제
 * 를 수행하는 메뉴 실행 클래스이다.
 * @author sfline
 *
 */
public class ToggleProjectNatureActionDelegate implements
		IWorkbenchWindowActionDelegate {

	/**
	 * Action의 Text를 추가로 변경하기 위한 메뉴 Text 정의.
	 */
	private static final String ACTION_TEXT_ADD = "Add RTM project Nature";
	/**
	 * Action의 Text를 삭제로 변경하기 위한 메뉴 Text 정의.
	 */
	private static final String ACTION_TEXT_REMOVE = "Remove RTM project Nature";
	/**
	 * Project Explorer에서 선택된 프로젝트
	 */
	private final Set<IProject> projects = new HashSet<IProject>();

	/**
	 * Nature 추가/삭제 여부
	 */
	private boolean checked = true;
	/**
	 * Nature 연결 여부 확인 시 Nature가 연결된 프로젝트 목록
	 */
	ArrayList<IProject> hasProject = new ArrayList<IProject>();
	/**
	 * Nature 연결 여부 확인 시 Nature가 연결되지 않은 프로젝트 목록
	 */
	ArrayList<IProject> nonProject = new ArrayList<IProject>();

	/**
	 * dispose시 자원을 해제하여야 한다.
	 * @Override
	 */
	public void dispose() {
		// TODO Auto-generated method stub

	}

	/**
	 * 메뉴 실행시 초기화를 수행한다.
	 * @param window 워크벤치 윈도우
	 * @Override
	 */
	public void init(IWorkbenchWindow window) {
		// TODO Auto-generated method stub

	}

	/**
	 * 선택된 메뉴 기능을 수행한다.
	 * @Override
	 */
	public void run(IAction action) {
		IProject project = null;
		Iterator<IProject> iter = projects.iterator();

		while(iter.hasNext()) {
			project = (IProject)iter.next();
			if(project.isOpen() == false) {
				continue;
			}

			if(checked == true) {
				TaskScriptNature.removeNatureFromProject(project);
			}
			else {
				TaskScriptNature.addNatureToProject(project);
			}
		}

		updateProjects();

		if(checked == true) {
			action.setText(ACTION_TEXT_REMOVE);
		}
		else {
			action.setText(ACTION_TEXT_ADD);
		}
	}

	/**
	 * 메뉴 활성화를 변경한다.
	 * @param action 활성화 여부를 확인할 메뉴 Action
	 * @param selection 메뉴 Action의 선택 여부
	 * @Override
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		updateSelectedProjects(selection);

		// 선택된 Project 중 Nature가 적용되었는지 여부를 확인한다.
		IProject project = null;
		if(projects.size() == 0) {
			action.setEnabled(false);
			action.setChecked(false);
			return;
		}

		// Project에 Nature가  연결되었는지 확인힌다.
		updateProjects();

		// 메뉴를 활성화 시킨다.
		action.setEnabled(true);
//		action.setChecked(false);

		// 선택된 모든 Project에 RTM project Nature가 연결되었다면
		if(checked == true) {
			action.setText(ACTION_TEXT_REMOVE);
		}
		else {
			action.setText(ACTION_TEXT_ADD);
		}
	}

	/**
	 * 프로젝트 선택 정보를 Update한다.
	 * @param selection Project Explorer의 선택 정보
	 */
	private void updateSelectedProjects(ISelection selection) {
		projects.clear();

		if((selection instanceof IStructuredSelection) == false) {
			return;
		}
		
		for(Iterator iter = ((IStructuredSelection)selection).iterator();iter.hasNext();) {
			Object elem = iter.next();
			
			if((elem instanceof IResource) == false) {
				if((elem instanceof IAdaptable) == false) {
					continue;
				}
				elem = ((IAdaptable)elem).getAdapter(IResource.class);
				if((elem instanceof IResource) == false) {
					continue;
				}
			}
			if((elem instanceof IProject) == false) {
				elem = ((IResource)elem).getProject();
				if((elem instanceof IProject) == false) {
					continue;
				}
			}

			// 현재 열린 프로젝트만 Nature 적용 여부를 변경한다.
			if(((IProject)elem).isOpen() == false) {
				continue;
			}
			
			if(projects.contains((IProject)elem) == false) {
				projects.add((IProject)elem);
			}
		}
	}
	
	/**
	 * 선택된 프로젝트들이 RTM project Nature가 연결되었는지 확인한다.
	 */
	private void updateProjects() {
		Iterator<IProject> iter = projects.iterator();
		
		checked = true;
		hasProject.clear();
		nonProject.clear();

		while(iter.hasNext()) {
			IProject project = iter.next();

			try {
				if(project.hasNature(TaskScriptNature.natureId)) {
					hasProject.add(project);
				}
				else {
					nonProject.add(project);
				}

				// 선택된 프로젝트중 하나라도 Nature가 등록되지 않은 프로젝트가 있다면
				checked &= project.isOpen() & project.hasNature(TaskScriptNature.natureId);
			} catch (CoreException e) {
//				checked = false;
			}
		}
	}
}
