package kr.re.etri.tpl.diagram.views.taskcontrol;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import kr.re.etri.tpl.IRTMDefines;
import kr.re.etri.tpl.MessageListener;
import kr.re.etri.tpl.script.debug.ConsoleMessageReader;
import kr.re.etri.tpl.script.debug.DebugMessageClient;
import kr.re.etri.tpl.script.debug.EOTMessageReader;
import kr.re.etri.tpl.script.debug.IConnectionListener;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;

public class TaskControlManager implements IConnectionListener {

	protected interface COMMAND {
		public static final String PWD = "pwd";
		public static final String DIR = "dir";
		public static final String DIRALL = "dirAll";
		public static final String DEPLOY = "deploy";
		public static final String UNDEPLOY = "undeploy";
		public static final String RUN = "run";
		public static final String STOP = "stop";
		public static final String EXIT = "exit";
		public static final String CHDIR = "chdir";
		public static final String UPLOAD = "upload";
		public static final String DOWNLOAD = "download";
		public static final String EOJ = "EOJ:";
		public static final String MKDIR = "mkdir";
		public static final String EXIST = "exist";
		public static final String RMDIR = "rmdir";
		public static final String RM = "rm";
		public static final String RENAME = "rename";
		public static final String LIST = "list";
		public static final String RESULTPRINT = "resultprint";	// KJH 20101220
		public static final String GET_DEPLOYED_TASKS = "getDeployedTasks";	// KJH 20110923
		public static final String GET_RUNNING_TASKS = "getRunningTasks";	// KJH 20110923
	}
	
	protected interface RESPONSE {
		public static final String OK = "Ok";
		public static final String ERROR = "Error";
		public static final String YES = "Yes";
		public static final String NO = "No";
	}
	

	// socket

	private static TaskControlManager manager;

	private TreeItem2 root;

	private List<ITaskControlListener> listeners;
	
	private boolean isOnlyTpl;

	private List<Task> taskList;
	
	// KJH 20101111 s, taskList & function
	private DebugMessageClient client;
	
	private Queue<String> commands;
	
	protected TaskControlManager() {
		listeners = new ArrayList<ITaskControlListener>();
		isOnlyTpl = true;
		taskList = new ArrayList<Task>();
		commands = new LinkedList<String>();
	}
	
	public static TaskControlManager getDefault() {
		if (manager == null)
			manager = new TaskControlManager();
		return manager;
	}
	
	public void dispose() {
		if (isConnected()) {
			try {
				disconnect();
			} catch (IOException e) {
			}
		}
		
		if (listeners != null) {
			listeners.clear();
		}
	}

	public TreeItem2 getRoot() {
		if (root == null) {
			root = new TreeItem2("");
		}
		return root;
	}
	
	public void setRoot(TreeItem2 root) {
		this.root = root;
	}


	public void deploy(String path) {	// KJH 20111125, TreeItem2->String
		String msg = String.format("%s %s\r\n", COMMAND.DEPLOY, path);
		if (send(msg)) {
			push(COMMAND.DEPLOY);
		}
	}
	
	public void undeploy(String task) {
		String msg = String.format("%s %s\r\n", COMMAND.UNDEPLOY, task);
		if (send(msg)) {
			push(COMMAND.UNDEPLOY);
		}
	}

	public void run(String path) {
		String msg = String.format("%s %s\r\n", COMMAND.RUN, path);
		if (send(msg)) {
			push(COMMAND.RUN);
		}
	}
	
	public void stop(String path) {
		String msg = String.format("%s %s\r\n", COMMAND.STOP, path);
		if (send(msg)) {
			push(COMMAND.STOP);
		}
	}
	
	/*
	 * 
	 */
	
	public boolean connect(String host, int port) throws IOException {
		client = DebugMessageClient.open(host, port);
		if (client != null && client.isConnected()) {
			client.sendMessage("on size\r\n");

//			for (MessageListener listener : messageListeners) {
//				client.addMessageListener(listener);
//			}
//			client.addMessageListener(new ConsoleMessageReader());
			
			new Thread(client).start();
			return true;
		}
		return false;
	}

	public void disconnect() throws IOException {
		// KJH 20110225,
		if (client != null && client.isConnected()) {
//			root = null;
//			if (taskList != null) {
//				taskList.clear();
//			}
//
//			if (commands != null) {
//				commands.clear();
//			}

			client.stop();
			client.sendMessage("\r\n");
//			client.sendMessage("exit\r\n");

//			update();
		}
	}

	public boolean isConnected () {
		return (client != null && client.isConnected());
	}
	
	/*
	 * 
	 */

	protected boolean send(String message){
		if (client != null && client.isConnected()) {
			try {
				client.sendMessage(message);
				return true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	protected boolean send(ByteBuffer buf) {
		if (client != null && client.isConnected()) {
			try {
				client.sendMessage(buf);
				return true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	
	public void dirall() {
		if (send(String.format("%s\r\n", COMMAND.DIRALL))) {
			push(COMMAND.DIRALL);
		}
	}

	// KJH 20101110 s,
	public void mkdir(TreeItem2 parent, String path) {
		String fullPath = parent.getPath();	// KJH 20101220
		if (!fullPath.endsWith("/"))
			fullPath += "/";
		fullPath += path;
		
		String msg = String.format("%s %s\r\n", COMMAND.MKDIR, fullPath);
		if (send(msg)) {
			push(COMMAND.MKDIR);
		}
	}	// KJH 20101110 e,
	
	// KJH 20101105 s,
	public void mkdir(String fullPath) {
		String[] paths = fullPath.split("/");
		String nextPath = "";
		for (String path : paths) {
			nextPath += ("/" + path);
			String msg = String.format("%s %s\r\n", COMMAND.MKDIR, nextPath);
			send(msg);
		}
	}
	// KJH 20101105 e,
	
	// KJH 20101108 s, rmdir
	public void rmdir(TreeItem2 item) {
		if (item.isContainer()) {	// directory
			Object[] children = item.getChildren();
			if (children != null) {
				for (Object child : children) {
					rmdir ((TreeItem2)child);
				}
			}
			
			String msg = String.format("%s %s\r\n", COMMAND.RMDIR, item.getPath());	// KJH 20101220, getPath()
			if (send(msg)) {
				push(COMMAND.RMDIR);
			}
		} else {
			String msg = String.format("%s %s\r\n", COMMAND.RM, item.getPath());	// KJH 20101220, getPath()
			if (send(msg)) {
				push(COMMAND.RM);
			}
		}
		
	}	// KJH 20101108 e, rmdir
	
	// KJH 20101105 s, rename
	public void rename(TreeItem2 item, String after) {
		String before = item.getText();
		String path = "/";
		TreeItem2 parent = item.getParent();
		while (parent != null && !parent.getText().equals("/")) {
			path += ("/" + parent.getText());
		}
		
		String msg = String.format("%s %s %s\r\n", COMMAND.RENAME, path + before, path + after);
		send(msg);
	}	// KJH 20101105 e, rename
	
	// KJH 20110729 s, move
	public void move(TreeItem2 item, Object target) {
		if (target instanceof TreeItem2 == false) {
			return;
		}
		
		TreeItem2 targetItem = (TreeItem2)target;
		if (targetItem.isContainer() == false) {
			return;
		}
		
		if (item.isContainer()) {
			for (Object child : item.getChildren()) {
				if (child instanceof TreeItem2) {
					move((TreeItem2)child, target);
				}
			}
		}
		else {
			String beforePath = item.getPath();
			String fileName = item.getText();
			String afterPath = targetItem.getPath();
			if (afterPath.endsWith("/") == false) {
				afterPath += "/";
			}
				
			
			String msg = String.format("%s %s %s%s\r\n", COMMAND.RENAME, beforePath, afterPath, fileName);
			if (send(msg)) {
				push(COMMAND.RENAME);
			}
		}
	}
	
	// KJH 20101105 s, exist
	public boolean exist(String path) {
		if (path.startsWith("/")) {
			path = "/" + path;
		}
		
		String msg = String.format("%s %s\r\n", COMMAND.EXIST, path);
		send(msg);
		
		return false;
	}	// KJH 20101105 e, exist
	
	/**
	 * 
	 * @param file	Directory or File
	 * @param target	TreeItem2
	 * @return
	 */
	public boolean upload(File file, Object target) {
		if (!(target instanceof TreeItem2)) {
			return false;
		}
		
		boolean result = true;
		TreeItem2 item = (TreeItem2)target;
		String fileName = file.getName();

		if (file.isDirectory()) {
			mkdir(item, fileName);
			
			File[] subs = file.listFiles();
			
			for (File sub : subs) {
				if (!upload(sub, new TreeItem2(item, fileName))) {
					result = false;
				}
			}
		}
		else if (file.isFile()) {
			String uploadPath = ((TreeItem2)target).getPath();
			if (uploadPath.startsWith("/")) {
				uploadPath = uploadPath.substring(1);
			}
			if (uploadPath.endsWith("/")) {
				uploadPath.substring(0, uploadPath.length() - 1);
			}
			
			send(COMMAND.UPLOAD + "\r\n");
			result = upload(file, uploadPath);
			send(COMMAND.EOJ);
		}

		return result;
	}
	
	public boolean upload(IResource resource, Object target) {
		if (!(target instanceof TreeItem2)) {
			return false;
		}
		
		boolean result = true;
		TreeItem2 item = (TreeItem2)target;
		String fileName = resource.getName();

		if (resource instanceof IFolder) {
			mkdir(item, fileName);
			
			IResource[] members;
			try {
				members = ((IFolder) resource).members();
			} catch (CoreException e) {
				return false;
			}
			
			for (IResource member : members) {
				if (!upload(member, new TreeItem2(item, fileName))) {
					result = false;
				}
			}
		}
		else if (resource instanceof IFile) {
			String uploadPath = ((TreeItem2)target).getPath();
			if (uploadPath.startsWith("/")) {
				uploadPath = uploadPath.substring(1);
			}
			if (uploadPath.endsWith("/")) {
				uploadPath.substring(0, uploadPath.length() - 1);
			}
			
			File file = resource.getLocation().toFile();
			send(COMMAND.UPLOAD + "\r\n");
			result = upload(file, uploadPath);
			send(COMMAND.EOJ);
		}
		
		return result;
	}
	
	public boolean upload(File[] files, Object target) {
		if (files == null || files.length == 0) {
			return false;
		}

		if (!(target instanceof TreeItem2)) {
			return false;
		}
		
		boolean result = true;
		String uploadPath = ((TreeItem2)target).getPath();
		if (uploadPath.startsWith("/")) {
			uploadPath = uploadPath.substring(1);
		}
		if (uploadPath.endsWith("/")) {
			uploadPath.substring(0, uploadPath.length() - 1);
		}

		send(COMMAND.UPLOAD + "\r\n");

		for (int i=0; i<files.length; i++) {
			File file = files[i];
			if (file == null || !file.exists())
				continue;
			
			if (!upload(file, uploadPath)) {
				result = false;
				break;
			}
		}
		send(COMMAND.EOJ);
		return result;
	}
	
	public boolean upload(String content, String path) {
		final int capacity = 1024;
		int nSize = content.length();
		
		if (path.startsWith("/")) {
			path = path.substring(1);
		}
		String msg = String.format("fname%%%s%%%s:", path, nSize);
		
		send(COMMAND.UPLOAD + "\r\n");
		send(msg);
		
		BufferedInputStream bis = new BufferedInputStream(
				new ByteArrayInputStream(content.getBytes()));
		
		ByteBuffer buffer = ByteBuffer.allocate(capacity);
		int nRead = 0;
		
		try {
			while ((nRead = bis.read(buffer.array(), 0, capacity)) > 0) {
				buffer.get(buffer.array(), 0, nRead);
				buffer.flip();
				send(buffer);
				buffer.clear();
				if (nRead < capacity) {
					break;
				}
			}
		} catch (IOException e) {
			return false;
		} finally {
			try {
				bis.close();
			} catch (IOException e) {
			}
		}
		
		send(COMMAND.EOJ);
		
		return true;
	}
	
	private boolean upload(File file, String path) {
		boolean result = true;
		final int capacity = 1024;
		String name = file.getName();
		long nFileSize = file.length();
		String msg = String.format("fname%%%s/%s%%%s:", path, name, nFileSize);
		
		send(msg);

		FileInputStream fis = null;
		BufferedInputStream bis = null;
		try {
			fis = new FileInputStream(file);
			bis = new BufferedInputStream(fis);

			ByteBuffer buffer = ByteBuffer.allocate(capacity);

			int nRead = 0;
			while ((nRead = bis.read(buffer.array(), 0, capacity)) > 0) {
				buffer.get(buffer.array(), 0, nRead);
				buffer.flip();
				send(buffer);
				buffer.clear();
				if (nRead < capacity) {
					break;
				}
			}
		} catch (FileNotFoundException e) {
			result = false;
		} catch (IOException e) {
			result = false;
		} finally {
			try {
				bis.close();
				fis.close();
			} catch (IOException e) {
			}
		}
		return result;
	}
	
	/*
	 * download, KJH 20110810
	 */
	public boolean download(String[] paths) {
		boolean result = false;
		for (String path : paths) {
			result = download(path);
		}
		return result;
	}
	
	public boolean download(String path) {
		if (path == null || path.length() == 0) {
			return false;
		}
		
		if (path.endsWith(IRTMDefines.TASK_SCRIPT_EXTENSION_NAME) == false) {
			return false;
		}
		
		String msg = String.format("%s %s\r\n", COMMAND.DOWNLOAD, path);
		if (send(msg)) {
			push(COMMAND.DOWNLOAD);
		}
		
		return true;
	}
	
	/*
	 * task
	 */
	// KJH 20101105 s,
	public boolean isOnlytpl() {
		return isOnlyTpl;
	}	// KJH 20101105 e,
	
	public boolean isDeployed() {
		return taskList.size() > 0;
	}
	
	// KJH 20101111 s, taskList & function
	public void setTaskArray(List<Task> tasks) {
		taskList.clear();
		taskList.addAll(tasks);
	}
	
	public List<Task> getTaskArray() {
		return taskList;
	}
	
	public Task[] getRunTasks() {
		List<Task> tasks = new ArrayList<Task>();
		for (Task task : taskList) {
			if (task.isRunning()) {
				tasks.add(task);
			}
		}
		return tasks.toArray(new Task[tasks.size()]);
	}
	
	public boolean isRunningTask(String text) {
		if (text == null || text.equals(""))
			return false;
		
		for (Task task : taskList) {
			if (task.getName().equals(text) && task.isRunning()) {
				return true;
			}
		}
		
		return false;
	}
	
	protected boolean isExistTask(boolean isRun) {
		return isExistTask(isRun, null);
	}
	protected boolean isExistTask(String text) {
		return isExistTask(true, text) || isExistTask(false, text);
	}
	protected boolean isExistTask(boolean isRun, String text) {
		for (Task task : taskList) {
			if (task.isRunning() == isRun) {
				if (text == null || text.equals("") || task.getName().equals(text)) {
					return true;
				}
			}
		}
		return false;
	}
	
	/*
	 * 
	 */
//	public String getPath(TreeItem2 item) {
//		if (item.getData() instanceof String) {
//			return (String)item.getData();
//		}
//		
//		StringBuilder fullPath = new StringBuilder();
//		TreeItem2 parent = (TreeItem2)item;
//		while (parent != null && !parent.getText().equals("/")) {
//			fullPath.insert(0, "/").insert(0, parent.getText());
//			parent = (TreeItem2)parent.getParent();
//		}
//		
//		return fullPath.indexOf("/") == 0 ? fullPath.substring(1) : fullPath.toString();
//	}


	/*
	 * Listeners
	 */
	public void addListener(ITaskControlListener listener) {
		if (listener != null) {
			listeners.add(listener);
		}
	}

	public void removeListener(Object listener) {
		if (listener != null) {
			listeners.remove(listener);
		}
	}

	public void update() {
		for (ITaskControlListener listener : listeners) {
			listener.update();
		}
	}
	
	public void addMessageListener(MessageListener listener) {
		if (client != null) {
			client.addMessageListener(listener);
		}
	}
	

	public String poll() {
		return commands.poll();
	}
	
	public void push(String command) {
		commands.add(command);
	}

	@Override
	public void changedConnection(DebugMessageClient client) {
		this.client = client;
		if (client != null && client.isConnected()) {
			client.addMessageListener(new EOTMessageReader());
			client.addMessageListener(TaskMessageReader.getDefault());
			client.addMessageListener(ConsoleMessageReader.getDefault());
			client.addMessageListener(DownloadMessageReader.getDefault());	// KJH 20110810
			
			dirall();
			
			getDeployedTasks();
			getRunningTasks();
		}
		else {
			if (taskList != null) {
				taskList.clear();
			}
			if (commands != null) {
				commands.clear();
			}
			root = null;

			ConsoleMessageReader.getDefault().dispose();	// KJH 20110411, console √ ±‚»≠

			update();
		}
	}
	
	private void getDeployedTasks() {
		if (send(String.format("%s\r\n", COMMAND.GET_DEPLOYED_TASKS))) {
			push(COMMAND.GET_DEPLOYED_TASKS);
		}
	}
	
	private void getRunningTasks() {
		if (send(String.format("%s\r\n", COMMAND.GET_RUNNING_TASKS))) {
			push(COMMAND.GET_RUNNING_TASKS);
		}
	}
}
