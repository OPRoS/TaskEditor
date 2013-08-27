package kr.re.etri.tpl.diagram.views.taskcontrol;

public class Task {
	private boolean isRunning;
	private String name;

	public Task() {

	}
	public Task(String name) {
		this.name = name;
		this.isRunning = false;
	}

	public String getName() {
		return name;
	}
	public boolean isRunning() {
		return isRunning;
	}
	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}
	@Override
	public String toString() {
		return name;
	}


}
