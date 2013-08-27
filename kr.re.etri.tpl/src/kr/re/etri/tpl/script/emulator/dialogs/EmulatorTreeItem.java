package kr.re.etri.tpl.script.emulator.dialogs;

import java.util.ArrayList;
import java.util.List;

import kr.re.etri.tpl.grammar.RTMType;


public class EmulatorTreeItem {
	
	public final static int NONE = 0;
	public final static int MODEL = 1;
	public final static int FUNCTION = 2;
	public final static int SYMBOL = 3;
	
	private String name;
	private String type;
	private EType etype;
	private String value;
	private String min;
	private String max;
	private String enumm;
	
	private int itemType;
	private List<EmulatorTreeItem> children;
	private EmulatorTreeItem parent;
	
	public EmulatorTreeItem(String name) {
		this(null, name, NONE);
	}
	
	public EmulatorTreeItem(String name, int itemType) {
		this(null, name, itemType);
	}
	
	public EmulatorTreeItem(EmulatorTreeItem parent, String name) {
		this(parent, name, NONE);
	}
	
	public EmulatorTreeItem(EmulatorTreeItem parent, String name, int itemType) {
		this.name = name;
		this.itemType = itemType;
		
		this.type = "";
		this.etype = EType.VALUE;
		this.value = "";
		this.min = "";
		this.max = "";
		this.enumm = "";
		
		this.children = new ArrayList<EmulatorTreeItem>();
		this.parent = parent;
		if (parent != null) {
			parent.addChild(this);
		}
	}

	public int getItemType() {
		return itemType;
	}

	public void setItemType(int itemType) {
		this.itemType = itemType;
	}
	
	public String getFullName() {
		String fullName = "";
		if (parent != null && parent.getItemType() != NONE) {
			fullName = parent.getFullName();
			if (fullName.length() > 0) {
				fullName += ".";
			}
		}
		return fullName + name;
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

	public String getEtype() {
		return etype.getName();
	}

	public void setEtype(String etype) {
		this.etype = EType.get(etype);
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getMin() {
		return min;
	}

	public void setMin(String min) {
		this.min = min;
	}

	public String getMax() {
		return max;
	}

	public void setMax(String max) {
		this.max = max;
	}

	public String getEnumm() {
		return enumm;
	}

	public void setEnumm(String enumm) {
		this.enumm = enumm;
	}
	
	public int getChildrenCount() {
		return children.size();
	}

	public EmulatorTreeItem[] getChildren() {
		return children.toArray(new EmulatorTreeItem[children.size()]);
	}
	
	public EmulatorTreeItem getChild(int i) {
		return children.get(i);
	}

	public void addChild(EmulatorTreeItem child) {
		this.children.add(child);
	}
	
	public boolean removeChild(EmulatorTreeItem child) {
		return this.children.remove(child);
	}
	
	public boolean hasChildren() {
		return children.size() > 0;
	}
	
	public boolean contains(EmulatorTreeItem child) {
		return children.contains(child);
	}
	
	public EmulatorTreeItem getParent() {
		return parent;
	}

	public void setParent(EmulatorTreeItem parent) {
		this.parent = parent;
	}

	public boolean validate() {
		if (name.length() <= 0) {
			return false;
		}
		
		if (EType.VALUE == etype) {
			if (value.length() <= 0) {
				return false;
			}
		}
		else if (EType.UNIFURM == etype || EType.GAUSSIAN == etype) {
			if (min.length() <= 0 || max.length() <= 0) {
				return false;
			}
		}
		else if (EType.ENUM == etype) {
			if (enumm.length() <= 0) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("@target[").append(getFullName()).append("]");
		
		if (EType.VALUE == etype) {
			sb.append("@etype[value]");
			sb.append("@value[").append(value).append("]");
		}
		else if (EType.UNIFURM == etype) {
			RTMType rtmType = RTMType.get(type);
			if (RTMType.INT == rtmType ||
					RTMType.SHORT == rtmType ||
					RTMType.LONG == rtmType) {
				sb.append("@etype[randomD_U]");
				sb.append("@min[").append(min).append("]");
				sb.append("@max[").append(max).append("]");
			}
			else if (RTMType.DOUBLE == rtmType ||
					RTMType.FLOAT == rtmType) {
				sb.append("@etype[randomF_U]");
				sb.append("@min[").append(min).append("]");
				sb.append("@max[").append(max).append("]");
			}
		}
		else if (EType.GAUSSIAN == etype) {
			RTMType rtmType = RTMType.get(type);
			if (RTMType.INT == rtmType ||
					RTMType.SHORT == rtmType ||
					RTMType.LONG == rtmType) {
				sb.append("@etype[randomD_G]");
				sb.append("@min[").append(min).append("]");
				sb.append("@max[").append(max).append("]");
			}
			else if (RTMType.DOUBLE == rtmType ||
					RTMType.FLOAT == rtmType) {
				sb.append("@etype[randomF_G]");
				sb.append("@min[").append(min).append("]");
				sb.append("@max[").append(max).append("]");
			}
		}
		else if (EType.ENUM == etype) {
			sb.append("@etype[enum]");
			sb.append("@enumlist[").append(enumm).append("]");
		}
		return sb.toString();
	}
}
