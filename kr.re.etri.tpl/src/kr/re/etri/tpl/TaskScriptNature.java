package kr.re.etri.tpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import kr.re.etri.tpl.diagram.builder.ScriptParsingBuilder;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

/**
 * RTM project Nature ���� Ŭ�����̴�.
 * ������Ʈ�� RTM project Nature�� �߰� �Ǵ� ���� �� Nature��
 * ���õ� Builder�� ������Ʈ�� ���� �Ǵ� �����Ѵ�.
 * Project�� Nature�� ����Ǹ� Builder�� �����Ѵ�.
 * @author sfline
 *
 */
public class TaskScriptNature implements IProjectNature {

	public final static String natureId = "kr.re.etri.tpl.TaskScriptNature";
	
	private IProject project;
	
	@Override
	public void configure() throws CoreException {
		ScriptParsingBuilder.addBuilderToProject(project);

		// �ʿ�� �����İ� �߰��� �� ��� ��ũ��Ʈ ������ ������ �˻��Ѵ�.
		Job job = new Job("Task Script Parsing") {

			@Override
			protected IStatus run(IProgressMonitor monitor) {
				try {
//					project.build(IncrementalProjectBuilder.INCREMENTAL_BUILD, monitor);
					project.build(IncrementalProjectBuilder.INCREMENTAL_BUILD,
							ScriptParsingBuilder.builderId,
							new HashMap(),
							monitor);	// KJH 20110607
				} catch (CoreException e) {
					e.printStackTrace();
				}

				return Status.OK_STATUS;
			}
		};
		job.schedule();
	}

	@Override
	public void deconfigure() throws CoreException {
		ScriptParsingBuilder.removeBuilderFromProject(project);
	}

	@Override
	public IProject getProject() {
		return project;
	}

	@Override
	public void setProject(IProject project) {
		this.project = project;
	}
	
	/**
	 * Project�� Nature�� �����Ѵ�.
	 * @param project Nature�� ������ ������Ʈ
	 */
	public static int addNatureToProject(IProject project) {
		if(project == null) {
			return -1;
		}
		
		if(project.isOpen() == false) {
			return -1;
		}
/*		
		try {
			if(project.hasNature(TaskScriptNature.natureId)) {
				return;
			}
		} catch (CoreException e) {
			e.printStackTrace();
		}
*/
		IProjectDescription description;
		try {
			description = project.getDescription();
		}
		catch (CoreException e) {
			return -1;
		}

		List<String> natureIds = new ArrayList<String>();
		natureIds.addAll(Arrays.asList(description.getNatureIds()));

		int natureIndex = natureIds.indexOf(TaskScriptNature.natureId);
		if(natureIndex >= 0) {
			return 1;
		}

		natureIds.add(TaskScriptNature.natureId);

		description.setNatureIds((String[])natureIds.toArray(new String[natureIds.size()]));

		try {
			project.setDescription(description, null);
		}
		catch (CoreException e) {
			return -1;
		}
		
		return 0;
	}
	
	/**
	 * Project�� Nature ������ �����Ѵ�.
	 * @param project Nature�� ������ ������Ʈ
	 */
	public static int removeNatureFromProject(IProject project) {
		if(project == null) {
			return -1;
		}
		
		if(project.isOpen() == false) {
			return -1;
		}
/*
		try {
			if(project.hasNature(TaskScriptNature.natureId) == false) {
				return;
			}
		} catch (CoreException e) {
			e.printStackTrace();
		}
*/
		IProjectDescription description;
		try {
			description = project.getDescription();
		}
		catch (CoreException e) {
			return -1;
		}

		List<String> natureIds = new ArrayList<String>();
		natureIds.addAll(Arrays.asList(description.getNatureIds()));

		int natureIndex = natureIds.indexOf(TaskScriptNature.natureId);
		if(natureIndex == -1) {
			return 1;
		}

		natureIds.remove(natureIndex);

		description.setNatureIds((String[])natureIds.toArray(new String[natureIds.size()]));

		try {
			project.setDescription(description, null);
		}
		catch (CoreException e) {
			return -1;
		}
		
		return 0;
	}
}
