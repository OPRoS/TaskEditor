package kr.re.etri.tpl.script.grammar.tree2;

import java.util.List;

import org.eclipse.core.runtime.IAdaptable;

public interface IScriptNode extends IAdaptable{
	
	public static final String INFO = "kr.re.etri.tpl.script.id";
	
	public static final String ROOT = "";
	public static final String EXPAND = "Expand";
	public static final String TRANS = "Transition";
	public static final String INITIAL = "initial";
	public static final String GOAL = "goal";
	public static final String ENTRY = "entry";
	public static final String STAY = "stay";
	public static final String EXIT = "exit";
	public static final String RUN = "run";
	public static final String IN = "in";
	public static final String OUT = "out";
	public static final String CONEXER = "conexer";
	public static final String SEQEXER = "seqexer";
	
	public String getFullName();
	
	public String getName();
	public void setName(String name);
	public int getType();
	public void setType(int type);
	public String getDesc();
	public void setDesc(String desc);
	public IScriptNode getParent();
	public void setParent(IScriptNode parent);
	public List<IScriptNode> getChildren();
	public int getChildrenCount();
	public IScriptNode getChild(int index);
	public void addChild(IScriptNode child);
	public void addChild(int index, IScriptNode child);
	public void removeChild(IScriptNode child);
	public void removeChild(int index);
	public boolean contains(IScriptNode child);
	public void dispose();
	
	public int getStart();
	public void setStart(int start);
	public int getEnd();
	public void setEnd(int end);
	public int getLength();
	public void setLength(int length);
	public int getNameStart();
	public void setNameStart(int nameStart);
	public int getNameEnd();
	public void setNameEnd(int nameEnd);
	public int getNameLength();
	public void setNameLength(int nameLength);
	
	public boolean isIncluded();
	
	public IScriptNode getChildByOffset(int offset);
	public IScriptNode getNextChildByOffset(int offset);	// KJH 20110214,
	public String getFullPath();	// KJH 2011021116,
	
	public List<IScriptNode> getParents();	// KJH 20110614
	public IScriptNode getBlockNode();		// KJH 20110614
	
	public void avalidate();	// KJH 20110616
	public void invalidate();	// KJH 20110616
	public void revalidate();	// KJH 20110616
	
	public void setValid(boolean valid);		// KJH 20110616
	public boolean isValid();	// KJH 20110616
}
