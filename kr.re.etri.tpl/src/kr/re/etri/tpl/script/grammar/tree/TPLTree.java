package kr.re.etri.tpl.script.grammar.tree;

import java.util.ArrayList;
import java.util.List;

public class TPLTree implements ITPLTree{
	private List<ITPLTree> children ;
	private String type;
	private String name;
	private ITPLTree parent;
	private int line;
	private int charPosition;
	private boolean isRoot;
	
	public TPLTree(){
		children = new ArrayList<ITPLTree>();
		isRoot = false;
	}
	public List<ITPLTree> getChildren(){
		return  children;
	}
	public ITPLTree getParent(){
		return parent;
	}
	public String getName(){
		return name;
	}
	public String getType(){
		return type;
	}
	public boolean hasChildren(){
		return (children.size()== 0 ? false : true);
	}
	public int getChildCount(){
		return children.size();
	}
	public int getLine(){
		return line;
	}
	
	public int getCharPosition(){
		return charPosition;
	}
	
	public void setLine(int i){
		line = i;
	}
	public void setCharPosition(int i){
		charPosition = i;
	}
	
	public void setParent(ITPLTree parent){
		this.parent = parent;
	}
	public void addChild(ITPLTree child){
		children.add(child);
	}
	
	public void setType(String type){
		this.type = type;
	}
	public void setName(String name){
		this.name = name;
	}
	public void removeChild(ITPLTree o){
		children.remove(o);
	}
	
	public boolean isRoot(){
		return isRoot;
	}
	public void setIsRoot(boolean isRoot){
		this.isRoot = isRoot;
	}
	
	public int hashCode(){
		StringBuffer buffer = new StringBuffer(getName());
		ITPLTree parent = getParent();
		while(!parent.isRoot()){
			buffer.append(parent.getName());
			parent = parent.getParent();
		}
		
		return buffer.toString().hashCode();
	}
}
