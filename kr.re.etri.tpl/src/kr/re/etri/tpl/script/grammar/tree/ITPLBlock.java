package kr.re.etri.tpl.script.grammar.tree;

public interface ITPLBlock {
	public int getBlockStart();
	public int getBlockEnd();
	
	public void setBlockStart(int blockStart);
	public void setBlockEnd(int blockEnd);
}
