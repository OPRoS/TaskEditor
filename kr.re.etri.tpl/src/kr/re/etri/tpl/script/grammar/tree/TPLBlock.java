package kr.re.etri.tpl.script.grammar.tree;

public class TPLBlock extends TPLTree implements ITPLBlock {

	private int blockStart;
	private int blockEnd;
	
	public int getBlockEnd() {
		return blockEnd;
	}

	public int getBlockStart() {
		return blockStart;
	}

	public void setBlockEnd(int blockEnd) {
		this.blockEnd = blockEnd;
	}

	public void setBlockStart(int blockStart) {
		this.blockStart = blockStart;
	}

}
