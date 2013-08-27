package kr.re.etri.tpl.diagram.views.taskcontrol.actions;

public abstract class DownloadRunnable implements Runnable {

	private String content;
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getContent() {
		return content;
	}
}
