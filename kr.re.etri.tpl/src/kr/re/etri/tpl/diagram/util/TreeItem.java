package kr.re.etri.tpl.diagram.util;

import java.util.ArrayList;

public class TreeItem {

	private String m_text;
	private Object m_data;
	private TreeItem m_parent;
	private ArrayList<TreeItem> m_children;

	public TreeItem(String text) {
		m_text = text;
		m_children = new ArrayList<TreeItem>();
	}

	public TreeItem(String text, Object data) {
		m_text = text;
		m_data = data;
		m_children = new ArrayList<TreeItem>();
	}

	public TreeItem(TreeItem parent, String text) {
		m_text = text;
		m_parent = parent;
		m_children = new ArrayList<TreeItem>();
	}

	public TreeItem(TreeItem parent, String text, Object data) {
		m_text = text;
		m_data = data;
		m_parent = parent;
		m_children = new ArrayList<TreeItem>();
	}

	public TreeItem getParent()
	{
		return m_parent;
	}

	public void setText(String text) {
		m_text = text;
	}

	public String getText() {
		return m_text;
	}

	public void setData(Object data) {
		m_data = data;
	}

	public Object getData() {
		return m_data;
	}

	public Object[] getChildren() {
		return m_children.toArray();
	}

	public boolean hasChildren() {
		return (m_children.size() > 0);
	}

	public void setParent(TreeItem parent) {
		if(m_parent != null) {
			m_parent.removeChild(this);
		}

		m_parent = parent;
		m_parent.m_children.add(this);
	}

	public void addChild(TreeItem item) {
		if(item == null) {
			return;
		}

		item.setParent(this);
	}

	public TreeItem addChild(String text) {
		TreeItem child;
		child = new TreeItem(this, text);
		m_children.add(child);

		return child;
	}

	public TreeItem addChild(String text, Object data) {
		TreeItem child;
		child = new TreeItem(this, text, data);
		m_children.add(child);

		return child;
	}

	public boolean removeChild(TreeItem item) {
		if(m_children.contains(item)) {
			item.m_parent = null;
			return m_children.remove(item);
		}

		return false;
	}

	public boolean removeChild(String text, boolean all) {
		if(all) {
			ArrayList<TreeItem> remList = new ArrayList<TreeItem>();
			for(TreeItem item : m_children) {
				if(item.m_text.equals(text)) {
					item.m_parent = null;
					remList.add(item);
				}
			}

			if(remList.size() > 0) {
				m_children.removeAll(remList);

				return true;
			}

			return false;
		}

		for(TreeItem item : m_children) {
			if(item.m_text.equals(text)) {
				item.m_parent = null;
				return m_children.remove(item);
			}
		}

		return false;
	}

	public String toString() {
		return m_text;
	}
}
