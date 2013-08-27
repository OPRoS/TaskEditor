package kr.re.etri.tpl.script.debug.variables;

import kr.re.etri.tpl.script.debug.thread.ThreadNode;

/**
 * 
 * @author Kim jiho
 *
 */
public class Variable {
	private String msgid;		// �޽��� �Ϸù�ȣ
	private String thread;		// ������ �ĺ���
	private String task;		// �����(����͸�)�� ���Ͽ� ������ �½�ũ
	private String time;		// ��� �޽��� �ۼ� �ð� hh:mm:ss 
	private String target;		// ���� ���� ���� �½�ũ�� ���� ��ġ
	private String file;		// ���� ���� ���� ���ǵ� �����̸�
	private String line;		// ���ǵ� ���Ͽ����� ���ι�ȣ
	private String name;		// �Է� �ɺ�(in)�� �̸�
	private String type;		// �Է½ɺ��� ������ Ÿ�� �Ǵ� �Լ��� ����Ÿ��[typ::= ('int'|'float'|'bool'|'string')]
	private String value;		// �Է½ɺ��� ������ ��
	private String dbgline;		// ���� ����� �������� ���ι�ȣ

	private ThreadNode threadNode;
	
	public Variable() {
	}
	
	public Variable(String msgid) {
		this.msgid = msgid;
	}
	
	public Variable(String msgid, String thread, String task, String time, String target,
			String file, String line, String name, String type, String value, String dbgline) {
		this.msgid = msgid;
		this.thread = thread;
		this.task = task;
		this.time = time;
		this.target = target;
		this.file = file;
		this.line = line;
		this.name = name;
		this.type = type;
		this.value = value;
		this.dbgline = dbgline;
	}

	public String getMsgid() {
		return msgid;
	}

	public void setMsgid(String msgid) {
		this.msgid = msgid;
	}

	public String getThread() {
		return thread;
	}

	public void setThread(String thread) {
		this.thread = thread;
	}

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDbgline() {
		return dbgline;
	}

	public void setDbgline(String dbgline) {
		this.dbgline = dbgline;
	}

	public ThreadNode getThreadNode() {
		return threadNode;
	}

	public void setThreadNode(ThreadNode threadNode) {
		this.threadNode = threadNode;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Variable) {
			Variable var = (Variable)obj;
			return (this.name.equals(var.name) &&
					this.line.equals(var.line) &&
					this.file.equals(var.file));
		}
		return super.equals(obj);
	}

}
