package kr.re.etri.tpl.script.grammar.tree2;

import java.util.ArrayList;
import java.util.List;

public class ScriptNode implements IScriptNode {
	protected String name;
	protected int type;
	protected String desc;
	protected IScriptNode parent;
	protected List<IScriptNode> children;

	protected int start;
	protected int end;
	protected int nameStart;
	protected int nameEnd;
	
	protected boolean valid;
	
	public ScriptNode(){
		this("", -1, null);
	}
	public ScriptNode(String name, int type) {
		this(name, type, null);
	}
	public ScriptNode(String name, int type, IScriptNode parent) {
		this.name = name;
		this.type = type;
		this.desc = "";
		this.parent = parent;
		this.start = -1;
		this.end = -1;
		this.nameStart = -1;
		this.nameEnd = -1;
		
		this.valid = false;
	}
	
	public String getFullName(){
		return getName();
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public IScriptNode getParent() {
		return parent;
	}
	public void setParent(IScriptNode parent) {
		this.parent = parent;
	}
	public List<IScriptNode> getChildren() {
		if(children == null)
			children = new ArrayList<IScriptNode>();
		return children;
	}
	public int getChildrenCount() {
		return getChildren().size();
	}
	public IScriptNode getChild(int index) {
		return getChildren().get(index);
	}
	public void addChild(IScriptNode child) {
		getChildren().add(child);
	}
	public void addChild(int index, IScriptNode child) {
		getChildren().add(index, child);
	}
	public void removeChild(IScriptNode child) {
		if(getChildren().contains(child))
			getChildren().remove(child);
	}
	public void removeChild(int index) {
		if(getChildren().size() > index)
			getChildren().remove(index);
	}
	public boolean contains(IScriptNode child) {
		return getChildren().contains(child);
	}
	
	public void dispose() {
		if(children != null) {
			for(IScriptNode child : children)
				child.dispose();
			children.clear();
			children = null;
		}
		parent = null;
	}
	
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getEnd() {
		return end;
	}
	public void setEnd(int end) {
		this.end = end;
	}
	public int getLength() {
		return end - start;
	}
	public void setLength(int length) {
		end = start + length;
	}
	public int getNameStart() {
		return nameStart;
	}
	public void setNameStart(int nameStart) {
		this.nameStart = nameStart;
	}
	public int getNameEnd() {
		return nameEnd;
	}
	public void setNameEnd(int nameEnd) {
		this.nameEnd = nameEnd;
	}
	public int getNameLength() {
		return nameEnd - nameStart;
	}
	public void setNameLength(int nameLength) {
		nameEnd = nameStart + nameLength;
	}
	
	public boolean isIncluded() {
		IScriptNode p = this.parent;
		while (p != null) {
			if (p instanceof IScriptRootNode && ((IScriptRootNode)p).isGlobal()) {
				return true;
			}
			p = p.getParent();
		}
		
		return false;
	}
	
	// KJH 20100712, offset으로 child검색
	public IScriptNode getChildByOffset(int offset){
		IScriptNode result = null;
		// root node에 대한 처리 (IncludeNode)
		if(start < 0 || end < 0){
			if(children != null){
				for(IScriptNode child : children){
					result = child.getChildByOffset(offset);
					if(result != null)
						break;
				}
			}
		}
		
		if(offset >= start && offset <= end){
			if(children != null){
				for(IScriptNode child : children){
					result = child.getChildByOffset(offset);
					if(result != null)
						break;
				}
				
				if(result == null)
					result = this;
			}
			else{	
				result = this;
			}
		}
		return result;
	}
	
	// KJH 20110214 s, offset다음 노드
	public IScriptNode getNextChildByOffset(int offset) {
		IScriptNode result = null;
		
		if(start < 0 || end < 0){
			if(children != null){
				for(IScriptNode child : children){
					result = child.getNextChildByOffset(offset);
					if(result != null)	break;
				}
			}
		}
		
		if(offset >= start && offset <= end){
			if(children != null){
				for(IScriptNode child : children){
					result = child.getNextChildByOffset(offset);
					if(result != null)	break;
				}
			}

			if (result == null && this instanceof IBreakableNode) {
				result = this;
			}
		}
		else if (offset < start) {
			if (this instanceof IBreakableNode) {	// 현재노드가 breakable하면 현재노드 반환
				result = this;
			}
			else if (children != null) {			// 그렇지 않으면 children을 탐색
				for(IScriptNode child : children){
					result = child.getNextChildByOffset(offset);
					if(result != null)	break;
				}
			}
		}
		return result;
	}
	// KJH 20110214 e, offset다음 노드
	
	// KJH 20110216 s,
	public String getFullPath() {
		StringBuilder sb = new StringBuilder();
		
		if (parent != null) {
			sb.append(parent.getFullName()).append(".");
		}
		sb.append(name);

		return sb.toString();
	}
	// KJH 20110216 e,
	
	@Override
	public Object getAdapter(Class adapter) {
		return ScriptAdapter.getInstance().getAdapter(this, adapter);
	}
	
	@Override
	public IScriptNode getBlockNode() {
		if (this instanceof IBlockNode) {
			return this;
		}
		IScriptNode parent = getParent();
		if (parent != null) {
			return parent.getBlockNode();
		}
		return null;
	}
	
	@Override
	public List<IScriptNode> getParents() {
		List<IScriptNode> parents = new ArrayList<IScriptNode>();
		IScriptNode parent = getParent();
		if (parent != null) {
			parents.addAll(parent.getParents());
		}
		if (!(this instanceof IIncludeNode)) {
			parents.add(this);
		}
		return parents;
	}
	
	@Override
	public void avalidate() {
		valid = true;
		for (IScriptNode child : getChildren()) {
			child.avalidate();
		}
	}
	
	@Override
	public void invalidate() {
		valid = false;
		for (IScriptNode child : getChildren()) {
			child.invalidate();
		}
	}
	
	@Override
	public void revalidate() {
		List<IScriptNode> removeList = new ArrayList<IScriptNode>();
		for (IScriptNode child : getChildren()) {
			child.revalidate();
			if (!child.isValid()) {
				removeList.add(child);
			}
		}
		getChildren().removeAll(removeList);
	}
	
	@Override
	public void setValid(boolean valid) {
		this.valid = valid;
	}
	
	@Override
	public boolean isValid() {
		return valid;
	}
}
