package kr.re.etri.tpl.grammar;

import java.util.ArrayList;
import java.util.Stack;

public class ConditionStack {
	private Stack<StackNode> symbolStack = new Stack<StackNode>();

	protected class StackNode {
		public ArrayList<String> condList;

		public StackNode() {
			this.condList = new ArrayList<String>();
		}
	}

	public ConditionStack() {
	}

	public void increaseLevel() {
		StackNode node = new StackNode();
		symbolStack.push(node);
	}

	public void decreaseLevel() {
		symbolStack.pop();
	}

	public void addCondition(String condition) {
		int size = symbolStack.size() - 1;
		if(size < 0) {
			increaseLevel();
		}
		
		size = symbolStack.size() - 1;
		
		StackNode node = symbolStack.get(size);
		if(node.condList.contains(condition)) {
			return;
		}

		node.condList.add(condition);
	}
	
	public String getCurrentCondtion() {
		int size = symbolStack.size() - 1;
		if(size < 0) {
			return "";
		}

		size = symbolStack.size() - 1;
		StackNode node = symbolStack.get(size);
		StringBuilder sb_ = new StringBuilder();
		for(String cond : node.condList) {
			sb_.append(cond);
		}
		return sb_.toString();
	}

	public String getCondition() {
		int pos = symbolStack.size() - 1;
		if(pos < 0) {
			return "";
		}

		StringBuilder sb_ = new StringBuilder();
		for(int idx = pos; idx >= 0; idx -= 1) {
			StackNode node = symbolStack.get(idx);
			if(idx < pos) {
				sb_.append(" && "); //.append("\r\n");
			}

			int condIdx = 0;
			for(String cond : node.condList) {
				if(condIdx > 0)sb_.append(" ?? ");
				
				if(pos > 0) sb_.append('(');
				sb_.append(cond);
				if(pos > 0) sb_.append(')');
				
				condIdx += 1;
			}
		}
		
		return sb_.toString();
	}
	
	// KJH 20110928
	public String[] getCurrentConditions() {
		int size = symbolStack.size() - 1;
		if(size < 0) {
			return new String[0];
		}

		size = symbolStack.size() - 1;
		StackNode node = symbolStack.get(size);
		return node.condList.toArray(new String[node.condList.size()]);
	}
}
