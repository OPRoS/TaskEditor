package kr.re.etri.tpl.script.grammar.tree;

import java.util.List;

public interface ITPLTree {
	public static final String WORKER ="worker";
	public static final String INCLUDE = "include";
	public static final String ENUM ="enum";
	public static final String MODEL ="model";
	public static final String TASK = "task";
	public static final String STATE ="state"; 
	public static final String VAR ="var";
	public static final String FUNC="func";
	public static final String GOTO ="goto";
	public static final String BEHAVIOR="behavior";
	//KJH 20100416 s, add
	public static final String TRANS="transition";
	//KJH 20100514 s, add
	public static final String STATEELEM="stateelem";
	public static final String ELEM="element";
	public static final String SYMBOL="symbol";
	public static final String CONST="constant";
	public static final String PARAM="param";
	public static final String ACTION="action";
	// KJH 20100526 s, add
	public static final String EXPAND="EXPAND";
	
	public List<ITPLTree> getChildren();
	public ITPLTree getParent();
	public String getName();
	public String getType();
	public boolean hasChildren();
	public int getChildCount();
	public int getLine();	
	public int getCharPosition();
	
	public void addChild(ITPLTree node);
	public void removeChild(ITPLTree node);
	public void setName(String name);
	public void setType(String type);
	public void setParent(ITPLTree node);
	public void setLine(int line);
	public void setCharPosition(int charPosition);
	
	// KJH 20100608 add
	public boolean isRoot();
	public void setIsRoot(boolean isRoot);
	public int hashCode();
}
