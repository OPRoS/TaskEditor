package kr.re.etri.tpl.script.debug;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import kr.re.etri.tpl.DebugPerspectiveFactory;
import kr.re.etri.tpl.MessageListener;
import kr.re.etri.tpl.diagram.views.taskcontrol.TaskControlManager;
import kr.re.etri.tpl.script.debug.dialog.MonitoringDialog;
import kr.re.etri.tpl.script.debug.dialog.TaskSelectionDialog;
import kr.re.etri.tpl.script.debug.thread.ThreadMessageReader;
import kr.re.etri.tpl.script.debug.thread.ThreadNode;
import kr.re.etri.tpl.script.debug.thread.VariableMessageReader;
import kr.re.etri.tpl.script.debug.variables.Variable;
import kr.re.etri.tpl.script.editors.TPLScriptEditor;
import kr.re.etri.tpl.script.grammar.tree2.IScriptNode;
import kr.re.etri.tpl.script.grammar.tree2.IScriptRootNode;
import kr.re.etri.tpl.script.grammar.tree2.ScriptManager;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.IBreakpointManager;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.FileEditorInput;

public class DebugManager implements IConnectionListener {
	
	private static Logger logger = Logger.getLogger(DebugManager.class);
	private static DebugManager instance;
	
	private boolean isEnabled;	// KJH 20110310
	private boolean isRunning;
	private boolean isSuspend;
	private boolean isMonitoring;
	
	private Map<String, ThreadNode> threads;
	private ThreadNode threadRoot;
	private ThreadNode selectedThread;
	
	private DebugMessageClient client;
	
	private IProject curProject;
	
	private List<IPostStateChangedListender> postListeners;	// KJH 20110224, 
	
	private int msgCount = 1;	// msgid
	private String initThread = "0";	// thread
	private String m_tname;
	
	private Map<Object, MonitoringDialog> dialogMap;	// KJH 20110302, monitoring dialogs
	private List<Boolean> positions;	// KJH 20110302, dialog positions
	
	private List<PostRunnable> runnables;	// KJH 20110311, runnable list
	
	protected DebugManager() {
		isEnabled = true;	// KJH 20110310
		isRunning = false;
		isSuspend = false;
		
		m_tname = "";
		
		threads = new HashMap<String, ThreadNode>();
		threadRoot = new ThreadNode("0", "root");
		threads.put("0", threadRoot);	// KJH, root Thread
		postListeners = new ArrayList<IPostStateChangedListender>();	// KJH 20110224,
		
		dialogMap = new HashMap<Object, MonitoringDialog>();
		positions = new ArrayList<Boolean>();
		
		runnables = new ArrayList<PostRunnable>();
	}

	public static DebugManager getDefault() {
		if (instance == null)
			instance = new DebugManager();
		return instance;
	}
	
	public int getMsgCount() {
		return msgCount;
	}
	
	public void setMsgCount(int msgCount) {
		this.msgCount = msgCount;
	}
	
	public boolean isConnected() {
		return client != null && client.isConnected();
	}
	
	public boolean isEnabled() {
		return isEnabled && isConnected();
	}

	public boolean isRunning() {
		return isRunning;
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}

	public boolean isSuspend() {
		return isSuspend;
	}

	public void setSuspend(boolean isSuspend, Shell shell) {
		this.isSuspend = isSuspend;
		for (ThreadNode thread : threads.values()) {
			thread.setRunning(!isSuspend);
			updateThread(thread, shell);
		}
		ThreadMessageReader.getDefault().updateView();
	}
	
	public boolean isMonitoring() {
		return isMonitoring;
	}
	
	public IProject getCurProject() {
		return curProject;
	}
	
	/*
	 *  Message Listeners
	 */
	public void addMessageListener(MessageListener listener) {
		if (client != null) {
			client.addMessageListener(listener);
		}
	}
	
	/*
	 * Update Listeners
	 */
	public void addPostStateChangedListener(IPostStateChangedListender listener) {
		postListeners.add(listener);
	}
	
	public void removePostStateChangedListener(IPostStateChangedListender listener) {
		postListeners.remove(listener);
	}
	
	private synchronized void fireStateChange() {
		for (IPostStateChangedListender listener : postListeners) {
			listener.stateChanged();
		}
	}
	
	/*
	 * ack message
	 */
	public synchronized void putAckMessage(int reqid, final String error) {
		
		List<PostRunnable> removeList = new ArrayList<PostRunnable>();
		
		for (PostRunnable runnable : runnables) {
			if (runnable.run(reqid, error)) {
				removeList.add(runnable);
			}
		}
		
		runnables.removeAll(removeList);
	}
	
	/*
	 * Data etc.
	 */

	private boolean send(String msg) {
		if (client != null && client.isConnected()) {
			try {
				client.sendMessage(msg);
				ConsoleMessageReader.getDefault().getConsoleStream().print(msg);
				return true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	private IScriptNode getTask() {
		IWorkbench wb = PlatformUI.getWorkbench();
		IWorkbenchWindow ww = wb.getActiveWorkbenchWindow();
//		IWorkbenchPage page = ww.getActivePage();
//		IWorkbenchPart part = page.getActivePart();

		IResource resource = null;
		
//		if (part instanceof CommonNavigator) {
//			CommonViewer viewer = ((CommonNavigator)part).getCommonViewer();
//			if (viewer.getSelection() instanceof IStructuredSelection) {
//				IStructuredSelection selection = (IStructuredSelection)viewer.getSelection();
//				resource = (IResource)selection.getFirstElement();
//			}
//		}
//		else if (part instanceof TPLScriptEditor){
//			resource = ((TPLScriptEditor)part).getInputFile();
//		}
//		else {
//			part = page.getActiveEditor();
//			if (part instanceof TPLScriptEditor) {
//				resource = ((TPLScriptEditor)part).getInputFile();
//			}
//			else {
//				return null;
//			}
//		}

		Map<IProject, IScriptNode[]> taskMap = new LinkedHashMap<IProject, IScriptNode[]>();
		List<IScriptNode> taskList = new ArrayList<IScriptNode>();
		
		if (resource == null) {
			IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
			IProject[] projects = root.getProjects();
			for (int j=0; j<projects.length; j++) {
				List<IScriptNode> list = ScriptManager.getInstance().getTasks(projects[j]);

				if (list.size() > 0) {
					taskMap.put(projects[j], list.toArray(new IScriptNode[list.size()]));
					taskList.addAll(list);
				}
				
				curProject = projects[j];	// KJH 20110315, 
			}
		}
//		else {
//			IProject project = resource.getProject();
//			List<IScriptNode> list = ScriptManager.getInstance().getTasks(project);
//			
//			if (list.size() > 0) {
//				taskMap.put(project, list.toArray(new IScriptNode[list.size()]));
//				taskList.addAll(list);
//			}
//			
//			curProject = project;	// KJH 20110225,
//		}
		
		
		if (taskList.size() > 0) {
			TaskSelectionDialog dlg = new TaskSelectionDialog(ww.getShell(), taskMap);
			if (dlg.open() == IDialogConstants.OK_ID) {
				curProject = dlg.getProject();
				return dlg.getTask();
			}
		}
		else {
			openError(ww.getShell(), "There is nothing available for debug tasks.");
		}
		
		return null;
	}
	
	private void registryBatch(String task, IWorkbenchWindow window) {
		IBreakpointManager manager = DebugPlugin.getDefault().getBreakpointManager();
		IBreakpoint[] breakpoints = manager.getBreakpoints();
		if (breakpoints != null) {
			for (int i=0; i<breakpoints.length; i++) {
				try {
					if (!breakpoints[i].isEnabled()) {	// KJH 20110302, breakpoint enable/disable
						continue;
					}
				} catch (CoreException e) {
				}
				
				IMarker marker = breakpoints[i].getMarker();
				IResource resource = marker.getResource();
				
				if (curProject == null || !curProject.equals(resource.getProject())) {
					continue;	// KJH 20110315, curProject와 다른 프로젝트의 breakpoint는 전송하지 않음
				}
				
				int line = -1;
				String file = "";
				if (marker != null) {
					line = marker.getAttribute(IMarker.LINE_NUMBER, -1);
					file = resource.getFullPath().removeFirstSegments(1).toString();
					
				}

				IScriptNode info = null;
				if (breakpoints[i] instanceof TPLBreakpoint) {
					info = ((TPLBreakpoint)breakpoints[i]).getInfo();
				}
				
				String target = info != null ? info.getFullPath() : "";
				registry(task, file, target, line);
			}
		}
	}
	
	public void registry(String task, String file, String target, int line) {
		if (client == null || !client.isConnected()) {
			return;
		}
		
		if (task == null) {
			task = m_tname;
		}
		if (!file.startsWith("/")) {
			file = "/" + file;
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append("REQ-start;register;")
		  .append("@msgid[").append(Integer.toString(msgCount)).append("]")
		  .append("@task[").append(task).append("]")
		  .append("@file[").append(file).append("]")
		  .append("@target[").append(target).append("]")
		  .append("@line[").append(Integer.toString(line)).append("]")
		  .append(";REQ-end\r\n");
		send(sb.toString());
		msgCount++;
	}
	
	public void remove(String task, String file, String target, int line) {
		if (client == null || !client.isConnected()) {
			return;
		}
		
		if (!file.startsWith("/")) {
			file = "/" + file;
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append("REQ-start;remove;")
		  .append("@msgid[").append(Integer.toString(msgCount)).append("]")
		  .append("@task[").append(task).append("]")
		  .append("@file[").append(file).append("]")
		  .append("@target[").append(target).append("]")
		  .append("@line[").append(Integer.toString(line)).append("]")
		  .append(";REQ-end\r\n");
		send(sb.toString());
		msgCount++;
	}
	
	public void start(boolean step, boolean monitoring, IWorkbenchWindow window) {
		final Shell shell = window.getShell();
		if (client == null || !client.isConnected()) {
			fireStateChange();
			MessageDialog.openInformation(shell, "Warning", "Task Controller is not connected");
			return;
		}
		
		this.isMonitoring = monitoring;
		
		String thread = "";
		
		if (!isRunning) {
			IScriptNode task = getTask();
			if (task == null) {
				return;
			}
			
			// KJH 20110830 s, 처음 run 시에만 perspective변경
			IWorkbenchWindow activeWindow = window != null ? window : PlatformUI.getWorkbench().getActiveWorkbenchWindow();
			IWorkbenchPage activePage = activeWindow.getActivePage();
			if (activePage != null) {
				IPerspectiveDescriptor curPerspe = activePage.getPerspective();
				if (!curPerspe.getId().equals(DebugPerspectiveFactory.persp_id)) {
					IPerspectiveDescriptor desc = activeWindow.getWorkbench()
					.getPerspectiveRegistry().findPerspectiveWithId(DebugPerspectiveFactory.persp_id);

					if (desc != null) {
						boolean res = MessageDialog.openQuestion(activeWindow.getShell(),
								"Confirm Perspective Switch",
								"Do you want to open Debug perspective now?");
						
						if (res) {
							activePage.setPerspective(desc);
						}
					}
				}
			}	// KJH 20110830 e, 처음 run 시에만 perspective변경
			
			m_tname = task.getName();

			String m_fname = "";
			IScriptNode parent = task.getParent();
			if (parent instanceof IScriptRootNode) {
				m_fname = parent.getName();
//				m_fname = m_fname.substring(m_fname.indexOf('/'));
				if (!m_fname.startsWith("/")) {
					m_fname = "/" + m_fname;
				}
			}
			
			if (m_tname.equals("") || m_fname.equals("")) {
				return;
			}
			
//			send("deploy " + m_fname + "\r\n");	// 초기 deploy해야 debugging이 가능함
			TaskControlManager.getDefault().deploy(m_fname);	// KJH 20111125
			registryBatch(m_tname, window);
			thread = initThread;
		}
		else {
			thread = selectedThread != null ? selectedThread.getThid() : "";
			if (thread.equals("")) {
				return;
			}
		}
		
		if (client == null || !client.isConnected()) {
			fireStateChange();
			return;
		}
		
		final int reqId = msgCount;
		final String fThread = thread;
		PostRunnable r = new PostRunnable() {
			@Override
			public boolean run(final int id, final String value) {
				if (reqId == id) {
					if (value != null && value.equals("0")) {
						isRunning = true;
						isSuspend = false;
						isEnabled = true;

						ThreadNode t = getThread(fThread);
						if (t != null) {
							t.setRunning(true);
							ThreadMessageReader.getDefault().updateView();
						}
						fireStateChange();
					} else {
						openError(shell, value);	// KJH 20110315,
					}
					return true;
				}
				return false;
			}
		};
		runnables.add(r);
		
		StringBuilder sb = new StringBuilder();
		sb.append("REQ-start;start;")
		  .append("@msgid[").append(Integer.toString(msgCount)).append("]")
		  .append("@thread[").append(thread).append("]")
		  .append("@stepbystep[").append(Boolean.toString(step)).append("]")
		  .append("@task[").append(m_tname).append("]")
		  .append("@isMonitoring[").append(Boolean.toString(monitoring)).append("]")
		  .append(";REQ-end\r\n");
		send(sb.toString());
		msgCount++;

		// KJH 20110310 s, action 즉각 반영되도록 추가
		isEnabled = false;
		fireStateChange();
		// KJH 20110310 e, action 즉각 반영되도록 추가
	}
	
	public void suspend(IWorkbenchWindow window) {
		if (client == null || !client.isConnected()) {
			return;
		}
		
		final int reqId = msgCount;
		final Shell shell = window.getShell();
		PostRunnable r = new PostRunnable() {
			@Override
			public boolean run(final int id, final String value) {
				if (reqId == id) {
					if (value != null && value.equals("0")) {
						isEnabled = true;
						setSuspend(true, shell);
						fireStateChange();
					} else {
						openError(shell, value);	// KJH 20110315,
					}
					return true;
				}
				return false;
			}
		};
		runnables.add(r);
		
		StringBuilder sb = new StringBuilder();
		sb.append("REQ-start;suspend;")
		  .append("@msgid[").append(Integer.toString(msgCount)).append("]")
		  .append("@task[").append(m_tname).append("]")
		  .append(";REQ-end\r\n");
		send(sb.toString());
		msgCount++;
		
		// KJH 20110310 s, action 즉각 반영되도록 추가
		isEnabled = false;
		fireStateChange();
		// KJH 20110310 e, action 즉각 반영되도록 추가
	}
	
	public void resume(IWorkbenchWindow window) {
		if (client == null || !client.isConnected()) {
			return;
		}
		
		final int reqId = msgCount;
		final Shell shell = window.getShell();
		PostRunnable r = new PostRunnable() {
			@Override
			public boolean run(final int id, final String value) {
				if (reqId == id) {
					if (value != null && value.equals("0")) {
						isEnabled = true;
						setSuspend(false, shell);
						fireStateChange();
					} else {
						openError(shell, value);	// KJH 20110315,
					}
					return true;
				}
				return false;
			}
		};
		runnables.add(r);

		StringBuilder sb = new StringBuilder();
		sb.append("REQ-start;resume;")
		  .append("@msgid[").append(Integer.toString(msgCount)).append("]")
		  .append("@task[").append(m_tname).append("]")
		  .append(";REQ-end\r\n");
		send(sb.toString());
		msgCount++;

		// KJH 20110310 s, action 즉각 반영되도록 추가
		isEnabled = false;
		fireStateChange();
		// KJH 20110310 e, action 즉각 반영되도록 추가
	}
	
	public void stop(IWorkbenchWindow window) {
		if (client == null || !client.isConnected()) {
			return;
		}
		
		final int reqId = msgCount;
		final Shell shell = window.getShell();
		PostRunnable r = new PostRunnable() {
			@Override
			public boolean run(final int id, final String value) {
				if (reqId == id) {
					if (value != null && value.equals("0")) {
//						TaskControlManager.getDefault().undeploy(m_tname);	// KJH 20111129, move to EOTMessageReader

						reset();
					} else {
						openError(shell, value);	// KJH 20110315,
					}
					return true;
				}
				return false;
			}
		};
		runnables.add(r);

		StringBuilder sb = new StringBuilder();
		sb.append("REQ-start;stop;")
		.append("@msgid[").append(Integer.toString(msgCount)).append("]")
		.append("@task[").append(m_tname).append("]")
		.append(";REQ-end\r\n");
		send(sb.toString());
		TaskControlManager.getDefault().push("stop");	// KJH 20111125
		msgCount++;

		// KJH 20110310 s, action 즉각 반영되도록 추가
		isEnabled = false;
		fireStateChange();
		// KJH 20110310 e, action 즉각 반영되도록 추가
	}

	/*
	 * 
	 */
	
	public ThreadNode getThread(String thid) {
		ThreadNode thread = threads.get(thid);
		
		if (thread == null && threadRoot != null) {
			thread = getThread(thid, threadRoot);
		}
		
		return thread;
	}
	
	protected ThreadNode getThread(String thid, ThreadNode parent) {
		if (parent.getThid().equals(thid))
			return parent;
		
		ThreadNode thread;
		for (ThreadNode child : parent.getChildren()) {
			thread = getThread(thid, child);
			if (thread != null)
				return thread;
		}
		
		return null;
	}
	
	public void addThread(ThreadNode thread, Shell parentShell) {
		threads.put(thread.getThid(), thread);
		
		if (isMonitoring()) {	// 20110228 s, monitoring dialog
			if (parentShell != null) {
				int position = 0;
				for (Boolean pos : positions) {
					if (pos == Boolean.FALSE) {
						break;
					}
					position++;
				}
				if (positions.size() == position) {
					positions.add(Boolean.TRUE);
				} else {
					positions.set(position, Boolean.TRUE);
				}
				
				// KJH 20111125 s, 다이얼로그의  식별자 변경
				final MonitoringDialog dialog = dialogMap.get(thread.getIdentify());
				if (dialog != null) {	// 기존 dialog가 있을 경우
					parentShell.getDisplay().asyncExec(new Runnable() {
						@Override
						public void run() {
							dialog.open();
						}
					});
				}
				else {	// 없을 경우, 새 dialog 생성
					final MonitoringDialog newDialog = new MonitoringDialog(parentShell, thread, position);
					dialogMap.put(thread.getIdentify(), newDialog);
					parentShell.getDisplay().asyncExec(new Runnable() {
						@Override
						public void run() {
							newDialog.open();
						}
						
					});
				}
				// KJH 20111125 e, 다이얼로그의  식별자 변경
			}
		}	// 20110228 s, monitoring dialog
	}
	
	public void removeThread(ThreadNode thread, Shell parentShell) {
		threads.remove(thread.getThid());
		
		if (isMonitoring()) {	// 20110228 s, monitoring dialog
			// KJH 20111125 s, del
//			final MonitoringDialog dialog = dialogMap.remove(thread);
//			int position = dialog.getPosition();
//			
//			positions.set(position, Boolean.FALSE);
//			
//			if (dialog != null) {
//				parentShell.getDisplay().asyncExec(new Runnable() {
//					@Override
//					public void run() {
//						dialog.close();
//					}
//				});
//			}
			// KJH 20111125 e, del
		}	// 20110228 s, monitoring dialog
	}
	
	public void updateThread(ThreadNode thread, Shell parentShell) {
//		final MonitoringDialog dialog = dialogMap.get(thread);
		final MonitoringDialog dialog = dialogMap.get(thread.getIdentify()); // KJH 20111125
		if (dialog != null) {
			dialog.setThread(thread);	// KJH 20111130
			parentShell.getDisplay().asyncExec(new Runnable() {
				@Override
				public void run() {
					dialog.update();
				}
			});
		}
	}
	
	public void toggleDialogVisible(ThreadNode thread) {
		MonitoringDialog dialog = dialogMap.get(thread.getIdentify());
		if (dialog != null) {
			boolean isVisible = dialog.getShell().getVisible();
			dialog.getShell().setVisible(!isVisible);
		}
	}

	public ThreadNode getThreadRoot() {
		return threadRoot;
	}

	public void setThreadRoot(ThreadNode threadRoot) {
		this.threadRoot = threadRoot;
	}

	public ThreadNode getSelectedThread() {
		return selectedThread != null ? selectedThread : threadRoot;
	}

	public void setSelectedThread(ThreadNode selectedThread) {
		this.selectedThread = selectedThread;
		fireStateChange();
	}
	
	public void selectAndReveal(IWorkbenchPage page, ThreadNode curThread, boolean open) {
		if (curThread == null) {
			curThread = selectedThread;
		}
		
		if (page == null || curThread == null) {
			return;
		}

		if (isMonitoring) {
			return;
		}
		
		String fname = "";
		int line = 0;
		Variable[] vars = curThread.getVariables();
		if (vars.length > 0) {
			Variable var = vars[vars.length - 1];
			fname = var.getFile();
			try {
				line = Integer.parseInt(var.getDbgline());
			} catch (NumberFormatException e) {
				logger.warn(e.getMessage(), e);
				return;
			}
		} else {	// KJH 20110314 s, thread에 variable정보가 없는 경우에 대한 처리
			fname = curThread.getFile();
			try {
				line = Integer.parseInt(curThread.getDbgline());
			} catch (NumberFormatException e) {
				logger.warn(e.getMessage(), e);
				return;
			}
		}	// KJH 20110314 e, thread에 variable정보가 없는 경우에 대한 처리
		
		if (fname.equals("")) {
			return;
		}
		
		IResource resource = curProject != null ? curProject.findMember(fname) : null;
		
		if (resource instanceof IFile) {
			IFile file = (IFile)resource;
			try {
				IEditorPart editor = open ? IDE.openEditor(page, file, true) : page.findEditor(new FileEditorInput(file));
				if (editor instanceof TPLScriptEditor) {
					((TPLScriptEditor) editor).selectLine(line);
				}
			} catch (PartInitException e) {
				logger.warn("Cannot Open Editor");
			}
		}
		
	}
	// KJH 20110225 e, last breakpoint

	@Override
	public void changedConnection(DebugMessageClient client) {
		this.client = client;
		if (client != null && client.isConnected()) {
			client.addMessageListener(ThreadMessageReader.getDefault());
			client.addMessageListener(VariableMessageReader.getDefault());
			client.addMessageListener(new AckMessageReader());
			fireStateChange();
		} else {
			reset();
		}
	}
	
	// KJH 20110315 s, Error Message & action reset
	private synchronized void openError(final Shell shell, final String value) {
		if (shell != null && !shell.isDisposed()) {
			shell.getDisplay().asyncExec(new Runnable() {
				@Override
				public void run() {
					reset();
					MessageDialog.openError(shell, "Error", value);
				}
			});
		}
	}	// KJH 20110315 e, Error Message & action reset
	
	public void reset() {	// KJH 20111129, reset()함수로 정리
		if (m_tname != null && !m_tname.equals("")) {
			TaskControlManager.getDefault().undeploy(m_tname);
		}
		
		threadRoot.removeAllChildren();
		threadRoot.removeAllVariables();
		
		threads.clear();

		msgCount = 1;
		isEnabled = true;
		isSuspend = false;
		isRunning = false;
		isMonitoring = false;
		selectedThread = null;
		curProject = null;
		m_tname = "";
		
		// KJH 20110307 s, 서버 강제 종료 시 thread/variable view 및 thread dialog 초기화
		for (final MonitoringDialog dialog : dialogMap.values()) {
			Shell shell = dialog.getShell();
			Display display = null;
			
			if (shell != null && !shell.isDisposed()) {
				display = shell.getDisplay();
			}
			
			if (display != null && !display.isDisposed()) {
				display.asyncExec(new Runnable() {
					@Override
					public void run() {
						dialog.close();
					}
				});
			}
		}
		dialogMap.clear();
		positions.clear();
		
		fireStateChange();
		ThreadMessageReader.getDefault().updateView();
		// KJH 20110307 e, 서버 강제 종료 시 thread/variable view 및 thread dialog 초기화
	}
}
