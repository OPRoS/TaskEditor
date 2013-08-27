package kr.re.etri.tpl.diagram.util.scripts;

import kr.re.etri.tpl.diagram.listener.IErrorListener;
import kr.re.etri.tpl.diagram.util.TPLException;
import kr.re.etri.tpl.diagram.util.TPLUtil;
import kr.re.etri.tpl.taskmodel.BehaviorElement;
import kr.re.etri.tpl.taskmodel.ConnectionElement;
import kr.re.etri.tpl.taskmodel.ConnectorElement;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.ReferElement;
import kr.re.etri.tpl.taskmodel.RelationShip;
import kr.re.etri.tpl.taskmodel.TaskElement;
import kr.re.etri.tpl.taskmodel.WithElement;

import org.eclipse.emf.common.util.EList;

public class TaskElementScript extends ScriptGenerator {

	public TaskElementScript(TaskElement model, int indent, IErrorListener logger) {
		super(model, indent, logger);
	}
	
	protected TaskElement getCastedModel() {
		return (TaskElement)getModel();
	}
	
	public void validate() throws TPLException {
		EList<ReferElement> refList = getCastedModel().getReferences();
		for (ReferElement refItem : refList) {
			EList<ConnectionElement> conList = refItem.getSourceConnections();
			for(ConnectionElement con : conList) {
				ReferElement refSource2 = (ReferElement)con.getSource2();
				ReferElement refTarget = (ReferElement)con.getTarget();
				if (!(refSource2.getRealModel() instanceof WithElement)) {
					if (RelationShip.TASK_CALL == con.getRelationship()) {
						// KJH 20110526 s, behavior/connector 분류
						if (refTarget.getRealModel() instanceof BehaviorElement) {
							getLogger().error(String.format("Behavior %s 호출이 지정되지 않았습니다.", refTarget.getName()), -1, -1, -1);
						}
						else if (refTarget.getRealModel() instanceof ConnectorElement) {
							getLogger().error(String.format("Connector %s 호출이 지정되지 않았습니다.", refTarget.getName()), -1, -1, -1);
						}	// KJH 20110526 e, behavior/connector 분류
					}
				}
			}
		}
		
		EList<ItemElement> withList = getCastedModel().getItems();
		for (ItemElement with : withList) {
			if (with instanceof WithElement) {
				WithElementScript withScript = new WithElementScript((WithElement)with, getIndent() + 2, getLogger());
				withScript.validate();
			}
		}
		
		// KJH 20110526 s, construct&destruct validate
		StructBlockScript structScript = new StructBlockScript(getCastedModel(),
				getCastedModel().getInitialize(), getIndent() + 1, getLogger());
		structScript.validate();

		structScript = new StructBlockScript(getCastedModel(),
				getCastedModel().getFinalize(), getIndent() + 1, getLogger());
		structScript.validate();
		// KJH 20110526 e, construct&destruct validate
	}

	@Override
	public String toString() {
		if(getCastedModel() == null) {
			return "";
		}

		StringBuilder sb = new StringBuilder();

		// KJH 20101203 s, modified grammar
		sb.append(TPLUtil.getWs(getIndent())).append("task ")
											 .append(getCastedModel().getName())
											 .append(" {\r\n");
		
		// KJH 20110526 s, statements
		EList<String> stmtList = getCastedModel().getStatements();
		if (stmtList != null && stmtList.size() > 0) {
			for (String str : getCastedModel().getStatements()) {
				sb.append(TPLUtil.getWs(getIndent() + 1)).append(str).append("\r\n");
			}
			sb.append("\r\n");
		}	// KJH 20110526 e, statements
		
		// KJH 20110526 s, construct & destruct
		// construct
		StructBlockScript structScript = new StructBlockScript(getCastedModel(),
				getCastedModel().getInitialize(), getIndent() + 1, getLogger());
		String tmp;
		sb.append(tmp = structScript.toString());
		if (tmp.length() > 0) {
			sb.append("\r\n");
		}
		// construct_
		
		// destruct
		structScript = new StructBlockScript(getCastedModel(),
				getCastedModel().getFinalize(), getIndent() + 1, getLogger());
		sb.append(tmp = structScript.toString());
		if (tmp.length() > 0) {
			sb.append("\r\n");
		}	// destruct_
		// KJH 20110526 e, construct & destruct
		
		// run
		// KJH 20110813 s
		EList<ItemElement> items = getCastedModel().getItems();
		for (ItemElement item : items) {
			if (item instanceof WithElement) {
				WithElementScript withScript = new WithElementScript((WithElement)item, getIndent() + 1, getLogger());
				sb.append(withScript.toString());
			}
		}	// KJH 20110813 e
		
		// KJH 20110816 s, empty run block
		if (items.size() == 0) {
			sb.append(TPLUtil.getWs(getIndent() + 1)).append("run @cycle<0> {\r\n");
			sb.append(TPLUtil.getWs(getIndent() + 1)).append("}\r\n");
		}	// KJH 20110816 e, empty run block
		
		sb.append(TPLUtil.getWs(getIndent())).append("};\r\n");	// end task
		// KJH 20101203 e, modified grammar
		
		return sb.toString();
	}
}
