package kr.re.etri.tpl.grammar;

import java.util.HashMap;
import java.util.Stack;

public class SymbolTable {
	private Stack<StackNode> symbolStack = new Stack<StackNode>();

	protected class StackNode {
		public HashMap<String, String> table;
		public int error;
		
		public StackNode() {
			this.table = new HashMap<String, String>();
			this.error = 0;
		}
	}

	public SymbolTable() {
		addScope();
	}

	public void addScope() {
		StackNode node = new StackNode();
		symbolStack.push(node);
	}

	public void removeScope() {
		symbolStack.pop();
	}

	public boolean hasSymbol(String symbol) {
		int size = symbolStack.size() - 1;
		if(size < 0) {
			return false;
		}
		for(int idx = size; idx >= 0; idx -= 1) {
			StackNode node = symbolStack.get(idx);
			if(node.table.containsKey(symbol)) {
				return true;
			}
		}

		return false;
	}

	public boolean hasCurrentScope(String symbol) {
		int size = symbolStack.size() - 1;
		if(size < 0) {
			return false;
		}

		StackNode node = symbolStack.get(size);
		if(node.table.containsKey(symbol)) {
			return true;
		}

		return false;
	}

	public void addSymbol(String symbol) {
		int size = symbolStack.size() - 1;
		if(size < 0) {
			addScope();
		}
		
		size = symbolStack.size() - 1;
		
		StackNode node = symbolStack.get(size);
		if(node.table.containsKey(symbol)) {
			return;
		}

		node.table.put(symbol, symbol);
	}

	public void increaseError() {
		int size = symbolStack.size() - 1;
		if(size < 0) {
			addScope();
		}
		
		size = symbolStack.size() - 1;
		
		StackNode node = symbolStack.get(size);
		node.error += 1;
	}
	
	public int getCurrentErrorCount() {
		int size = symbolStack.size() - 1;
		if(size < 0) {
			return 0;
		}

		size = symbolStack.size() - 1;
		StackNode node = symbolStack.get(size);
		return node.error;
	}
	
	public int getTotalErrorCount() {
		int size = symbolStack.size() - 1;
		if(size < 0) {
			return 0;
		}

		int error = 0;
		for(int idx = size; idx >= 0; idx -= 1) {
			StackNode node = symbolStack.get(idx);
			error += node.error;
		}
		
		return error;
	}
}
