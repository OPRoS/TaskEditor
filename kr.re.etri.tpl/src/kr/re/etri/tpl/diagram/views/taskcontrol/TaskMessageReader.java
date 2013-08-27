package kr.re.etri.tpl.diagram.views.taskcontrol;

import java.util.ArrayList;
import java.util.List;

import kr.re.etri.tpl.diagram.views.taskcontrol.TaskControlManager.COMMAND;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class TaskMessageReader extends NonResponseMessageReader {

	private TaskControllerView m_view;
	private boolean onlyTPL = true;
	
	private static TaskMessageReader instance;
	
	public static TaskMessageReader getDefault() {
		if (instance == null)
			instance = new TaskMessageReader(null);
		return instance;
	}

	public TaskMessageReader(TaskControllerView view) {
		this.m_view = view;
	}
	
	private Shell getShell() {
		if (m_view != null) {
			return m_view.getSite().getShell();
		}
		return null;
	}
	
	private Display getDisplay() {
		Shell shell = getShell();
		if (shell != null && !shell.isDisposed()) {
			return shell.getDisplay();
		}
		return null;
	}
	
	@Override
	public void update(String message) {
		super.update(message);
		if (!isValid()) {
			return;
		}
		
		TaskControlManager tManager = TaskControlManager.getDefault();
		
		if (message.length() == 0 || message.startsWith("TPL-R>")) {
			return;
		}
		
		String command = tManager.poll();
		
		if (command == null || command.equals("")) {
			return;
		}
		
		// KJH 20110307 s, 엔진 변경되어 메시지 가공방법 변경
		if (command.equals(COMMAND.DEPLOY) || command.equals(COMMAND.UNDEPLOY) ||
				command.equals(COMMAND.RUN) || command.equals(COMMAND.STOP) ||
				command.equals(COMMAND.LIST)) {
			List<Task> taskList = new ArrayList<Task>();
			
			taskList.addAll(tManager.getTaskArray());
			
			String[] msgs = message.split("\r\n");
			for (int i = 0; i < msgs.length; i++) {
				if (msgs[i] == null) {
					continue;
				}
				
				
				if (msgs[i].contains("<Loaded Task List>")) {
					i++;
					
					taskList.clear();
					
					while (!msgs[i].contains("--------------------")) {
						String[] tokens = msgs[i].split(",");
						for (int j=0; j<tokens.length; j++) {
							String token = tokens[j].trim();
							if (!token.startsWith("Task"))
								continue;

							int index = token.indexOf(":");
							if (index < 0)
								continue;

							String taskName  = token.substring(index + 1).trim();
							if (taskName.length() > 0) {
								boolean contain = false;
								for (Task task : taskList) {
									if (task.getName().equals(taskName)) {
										contain = true;
										break;
									}
								}
								
								if (!contain) {
									taskList.add(new Task(taskName));
								}
							}
						}
						
						i++;
					}
				}
				else if (msgs[i].contains("<Running Task List>")) {
					i++;
					
					for (Task task : taskList) {
						task.setRunning(false);
					}

					while (!msgs[i].contains("--------------------")) {
						String[] tokens = msgs[i].split(",");
						for (int j=0; j<tokens.length; j++) {
							String token = tokens[j].trim();
							if (!token.trim().startsWith("Task"))
								continue;

							int index = token.indexOf(":");
							if (index < 0)
								continue;

							String taskName  = token.substring(index + 1).trim();
							if (taskName.length() > 0) {
								for (Task task : taskList) {
									if (task.getName().equals(taskName)) {
										task.setRunning(true);
										break;
									}
								}
							}
						}
						
						i++;
					}
				} else if (msgs[i].contains("<Loaded Behavior List>")) {
					i++;

					while (!msgs[i].contains("--------------------")) {
						i++;
					}
				}
			}
			
			tManager.setTaskArray(taskList);
		}	// KJH 20110307 e, 엔진 변경되어 메시지 가공방법 변경
		else if (command.equals(COMMAND.DIRALL)) {
			int cut = message.lastIndexOf("00000007TPL-R>");
			if (cut > 0) {
				message = message.substring(0, cut);
			}
			
			TreeItem2 root = new TreeItem2("");
			root.addChild(createDir(root, message));
			tManager.setRoot(root);
		}
		else if (command.equals(COMMAND.GET_DEPLOYED_TASKS)) {	// KJH 20110923
			List<Task> taskList = new ArrayList<Task>();
			String[] tokens = message.split("[\\[\\],\r\n]", 0);
			for (String token : tokens) {
				token = token.trim();
				if (token.length() > 0) {
					Task task = new Task(token);
					taskList.add(task);
				}
			}
			tManager.setTaskArray(taskList);
		}
		else if (command.equals(COMMAND.GET_RUNNING_TASKS)) {	// KJH 20110923
			List<Task> taskList = new ArrayList<Task>();
			taskList.addAll(tManager.getTaskArray());
			String[] tokens = message.split("[\\[\\],\r\n]");
			for (String token : tokens) {
				token = token.trim();
				for (Task task : taskList) {
					if (task.getName().equals(token)) {
						task.setRunning(true);
					}
				}
			}
			tManager.setTaskArray(taskList);
		}
		
		
		Display display = getDisplay();
		if (display != null && !display.isDisposed()) {
			display.asyncExec(new Runnable() {
				@Override
				public void run() {
					TaskControlManager.getDefault().update();
				}
			});
		}
	}
	
	
	private TreeItem2 createDir(TreeItem2 parent, String str) {
		parent = new TreeItem2(parent, "/");
		parent.setContainer(true);
		
		while (str.length() > 0) {
			int idx1 = str.indexOf(',');
			int idx2 = str.indexOf('{');
			int idx3 = str.indexOf('}');
			
			idx1 = idx1 == -1 ? Integer.MAX_VALUE : idx1;
			idx2 = idx2 == -1 ? Integer.MAX_VALUE : idx2;
			idx3 = idx3 == -1 ? Integer.MAX_VALUE : idx3;
			
			int min = Math.min(str.length(), Math.min(idx1, Math.min(idx2, idx3)));
			String f = str.substring(0, min);
			String s = str.substring(min);

			TreeItem2 child = null;
			boolean isContainer = false;

			if (f.startsWith("[") && f.endsWith("]")) {
				f = f.substring(1, f.length() - 1);
				isContainer = true;
			}

			if (f.length() > 0) {
				if (isContainer) {
					child = new TreeItem2(f);
					child.setContainer(isContainer);
					parent.addChild(child);
				}
				else {
					int itail = f.lastIndexOf('.');
					if (itail == -1) {
						itail = f.length();
					}
					String tail = f.substring(itail);
					if (tail.equalsIgnoreCase(".tpl")) {
						child = new TreeItem2(f);
						parent.addChild(child);
					}
					else if (!onlyTPL) {
						child = new TreeItem2(f);
						parent.addChild(child);
					}
				}
			}

			str = s.length() > 0 ? s.substring(1) : "";
			
			if (min == idx2) {
				parent = child;
			}
			else if (min == idx3) {
				parent = parent.getParent();
			}
		}
		
		return parent;
	}

	public void setView(TaskControllerView view) {
		this.m_view = view;
	}
	
	public void updateView() {
		Display display = getDisplay();
		if (display != null && !display.isDisposed()) {
			display.asyncExec(new Runnable() {
				@Override
				public void run() {
					TaskControlManager.getDefault().update();
				}
			});
		}
	}
}
