package kr.re.etri.tpl.diagram.views.taskcontrol;

import kr.re.etri.tpl.diagram.util.TreeItem;

public class TreeItem2 extends TreeItem {

	private boolean isContainer;
	
	public TreeItem2(String text) {
		this(text, false);
	}
	
	public TreeItem2(String text, Object data) {
		super(text, data);
	}
	
	public TreeItem2(String text, boolean isContainer) {
		super(text);
		this.isContainer = isContainer;
	}
	
	public TreeItem2(TreeItem2 parent, String text) {
		super(parent, text);
	}
	
	public TreeItem2 getParent() {
		return (TreeItem2)super.getParent();
	}
	
	public TreeItem2 addChild(String text) {
		return (TreeItem2)super.addChild(text);
	}
	
	public TreeItem2 addChild(String text, Object data) {
		return (TreeItem2)super.addChild(text, data);
	}

	public boolean isContainer() {
		return isContainer;
	}

	public void setContainer(boolean isContainer) {
		this.isContainer = isContainer;
	}
	
	// KJH 20101220 s
	public String getPath() {
		StringBuilder sb = new StringBuilder();
		
		TreeItem2 parent = this;
		while (parent != null) {
			String text = parent.getText();
			if (text.equals("") || text.equals("/")) {
				break;
			}
			sb.insert(0, text).insert(0, "/");
			parent = parent.getParent();
		}
		
		if (sb.indexOf("/") != 0) {
			sb.insert(0, "/");
		}
		
		return sb.toString();
	}
	// KJH 20101220 e

}
