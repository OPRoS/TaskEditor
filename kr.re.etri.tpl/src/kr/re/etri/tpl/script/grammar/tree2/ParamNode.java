package kr.re.etri.tpl.script.grammar.tree2;


public class ParamNode extends ScriptNode implements
		IParamNode {
	String dataType;

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	@Override
	public String getFullName() {
		return (new StringBuffer().append(dataType).append(" ").append(getName())).toString();
	}

}
