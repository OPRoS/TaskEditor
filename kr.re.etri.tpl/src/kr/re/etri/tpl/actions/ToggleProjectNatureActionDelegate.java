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
 * Project Explorer�� ���õ� ������Ʈ�� RTM Nature�� �߰�/����
 * �� �����ϴ� �޴� ���� Ŭ�����̴�.
 * @author sfline
 *
 */
public class ToggleProjectNatureActionDelegate implements
		IWorkbenchWindowActionDelegate {

	/**
	 * Action�� Text�� �߰��� �����ϱ� ���� �޴� Text ����.
	 */
	private static final String ACTION_TEXT_ADD = "Add RTM project Nature";
	/**
	 * Action�� Text�� ������ �����ϱ� ���� �޴� Text ����.
	 */
	private static final String ACTION_TEXT_REMOVE = "Remove RTM project Nature";
	/**
	 * Project Explorer���� ���õ� ������Ʈ
	 */
	private final Set<IProject> projects = new HashSet<IProject>();

	/**
	 * Nature �߰�/���� ����
	 */
	private boolean checked = true;
	/**
	 * Nature ���� ���� Ȯ�� �� Nature�� ����� ������Ʈ ���
	 */
	ArrayList<IProject> hasProject = new ArrayList<IProject>();
	/**
	 * Nature ���� ���� Ȯ�� �� Nature�� ������� ���� ������Ʈ ���
	 */
	ArrayList<IProject> nonProject = new ArrayList<IProject>();

	/**
	 * dispose�� �ڿ��� �����Ͽ��� �Ѵ�.
	 * @Override
	 */
	public void dispose() {
		// TODO Auto-generated method stub

	}

	/**
	 * �޴� ����� �ʱ�ȭ�� �����Ѵ�.
	 * @param window ��ũ��ġ ������
	 * @Override
	 */
	public void init(IWorkbenchWindow window) {
		// TODO Auto-generated method stub

	}

	/**
	 * ���õ� �޴� ����� �����Ѵ�.
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
	 * �޴� Ȱ��ȭ�� �����Ѵ�.
	 * @param action Ȱ��ȭ ���θ� Ȯ���� �޴� Action
	 * @param selection �޴� Action�� ���� ����
	 * @Override
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		updateSelectedProjects(selection);

		// ���õ� Project �� Nature�� ����Ǿ����� ���θ� Ȯ���Ѵ�.
		IProject project = null;
		if(projects.size() == 0) {
			action.setEnabled(false);
			action.setChecked(false);
			return;
		}

		// Project�� Nature��  ����Ǿ����� Ȯ������.
		updateProjects();

		// �޴��� Ȱ��ȭ ��Ų��.
		action.setEnabled(true);
//		action.setChecked(false);

		// ���õ� ��� Project�� RTM project Nature�� ����Ǿ��ٸ�
		if(checked == true) {
			action.setText(ACTION_TEXT_REMOVE);
		}
		else {
			action.setText(ACTION_TEXT_ADD);
		}
	}

	/**
	 * ������Ʈ ���� ������ Update�Ѵ�.
	 * @param selection Project Explorer�� ���� ����
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

			// ���� ���� ������Ʈ�� Nature ���� ���θ� �����Ѵ�.
			if(((IProject)elem).isOpen() == false) {
				continue;
			}
			
			if(projects.contains((IProject)elem) == false) {
				projects.add((IProject)elem);
			}
		}
	}
	
	/**
	 * ���õ� ������Ʈ���� RTM project Nature�� ����Ǿ����� Ȯ���Ѵ�.
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

				// ���õ� ������Ʈ�� �ϳ��� Nature�� ��ϵ��� ���� ������Ʈ�� �ִٸ�
				checked &= project.isOpen() & project.hasNature(TaskScriptNature.natureId);
			} catch (CoreException e) {
//				checked = false;
			}
		}
	}
}
