package kr.re.etri.tpl.script.debug.thread;

import java.util.ArrayList;
import java.util.List;

import kr.re.etri.tpl.TaskModelPlugin;
import kr.re.etri.tpl.preferences.TPLPreferenceConstants;
import kr.re.etri.tpl.script.debug.variables.Variable;

/**
 * 
 * @author Kim jiho
 *
 */
public class ThreadNode {

	private boolean isRoot;
	
	private boolean isRunning;	// KJH 20110228
	
	private String thid;
	private String name;
	
	private String dbgline;	// KJH 20110314, variable 없을 경우 정지점에 대한 정보
	private String file;	// KJH 20110314, variable 없을 경우 정지점에 대한 정보
	
	private String threadline;	// KJH 20111129
	private String expandedFile;	// KJH 20111130

	private List<ThreadNode> children;
	private ThreadNode parent;
	
	private List<Variable> variables;
	
	public ThreadNode() {
		this("", "");
	}
	
	public ThreadNode(String id, String name) {
		this.thid = id;
		this.name = name;
		children = new ArrayList<ThreadNode>();
		variables = new ArrayList<Variable>();
		this.isRoot = false;
		this.isRunning = true;
	}
	

	public boolean isRoot() {
		return isRoot;
	}

	public void setRoot(boolean isRoot) {
		this.isRoot = isRoot;
	}
	
	public boolean isRunning() {
		return isRunning;
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}
	
	public String getThid() {
		return thid;
	}

	public void setThid(String thid) {
		this.thid = thid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDbgline() {
		// KJH 20111129 s, variable에서 dbgline 구하기
		if (dbgline == null || dbgline.equals("")) {
			for (Variable var : variables) {
				String result = var.getDbgline();
				if (result != null && !result.equals("")) {
					return result;
				}
			}
		}	// KJH 20111129 e, variable에서 dbgline 구하기
		return dbgline;
	}

	public void setDbgline(String dbgline) {
		this.dbgline = dbgline;
	}

	public String getFile() {
		// KJH 20111129 s, variable에서 file 구하기
		if (file == null || file.equals("")) {
			for (Variable var : variables) {
				String result = var.getFile();
				if (result != null && !result.equals("")) {
					return result;
				}
			}
		}	// KJH 20111129 e, variable에서 file 구하기
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}
	
	public String getThreadLine() {
		return threadline;
	}
	
	public void setThreadLine(String threadline) {
		this.threadline = threadline;
	}
	
	public String getExpandedFile() {
		return expandedFile;
	}
	
	public void setExpandedFile(String expandedFile) {
		this.expandedFile = expandedFile;
	}
	
	/**
	 * @return Identify
	 * 
	 * @author KJH 20111124
	 */
	public String getIdentify() {
		String type = TaskModelPlugin.getDefault()
				.getPreferenceStore()
				.getString(TPLPreferenceConstants.MONITORING_TYPE);
		
		if (TPLPreferenceConstants.MONITORING_TYPE1.equals(type)) {
			return String.format("@name[%s]@file[%s]@threadLine[%s]",
					getName(),
					getExpandedFile(),
					getThreadLine());
		}
		else if (TPLPreferenceConstants.MONITORING_TYPE2.equals(type)) {
			return String.format("@name[%s]@file[%s]@threadLine[%s]@file[%s]@dbgline[%s]",
					getName(),
					getExpandedFile(),
					getThreadLine(),
					getFile(),
					getDbgline());
		}
		return thid;
	}

	/*
	 * 
	 */
	public void addChild(ThreadNode child) {
		children.add(child);
		child.setParent(this);
	}
	
	public void addChild(int index, ThreadNode child) {
		children.add(index, child);
		child.setParent(this);
	}

	public void removeChild(ThreadNode child) {
		child.setParent(null);
//		child.removeAllChildren();
		child.removeAllVariables();
		children.remove(child);
	}
	
	public void removeAllChildren(){
		for (ThreadNode child : children) {
			child.setParent(null);
			child.removeAllChildren();
		}
		children.clear();
	}

	public ThreadNode[] getChildren() {
		return (ThreadNode[]) children.toArray(new ThreadNode[children.size()]);
	}

	public boolean hasChildren() {
		return children.size() > 0;
	}
	
	public int getChildrenCount() {
		return children.size();
	}


	public ThreadNode getParent() {
		return parent;
	}

	public void setParent(ThreadNode parent) {
		this.parent = parent;
	}
	

	public Variable[] getVariables() {
		return variables.toArray(new Variable[variables.size()]);
	}

	public void setVariables(List<Variable> variables) {
		this.variables = variables;
	}
	
	public void addVariable(Variable variable) {
		for (Variable var : variables) {
			if (var.equals(variable)) {
				variables.remove(var);
				var.setThreadNode(null);
				break;
			}
		}
		variables.add(variable);
		variable.setThreadNode(this);
	}
	
	public void removeVariable(Variable variable) {
		variable.setThreadNode(null);
		variables.remove(variable);
	}
	
	public void removeAllVariables() {
		for (Variable variable : variables) {
			variable.setThreadNode(null);
		}
		variables.clear();
	}

}
