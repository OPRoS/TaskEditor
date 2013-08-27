package kr.re.etri.tpl.diagram.util.scripts;

import java.util.Hashtable;

import kr.re.etri.tpl.diagram.listener.IErrorListener;
import kr.re.etri.tpl.diagram.util.TPLException;
import kr.re.etri.tpl.diagram.util.TPLUtil;
import kr.re.etri.tpl.taskmodel.BehaviorElement;
import kr.re.etri.tpl.taskmodel.ConnectionElement;
import kr.re.etri.tpl.taskmodel.ConnectorElement;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.Parameter;
import kr.re.etri.tpl.taskmodel.ReferElement;
import kr.re.etri.tpl.taskmodel.RelationShip;
import kr.re.etri.tpl.taskmodel.StateElement;

import org.eclipse.emf.common.util.EList;

public class BehaviorElementScript extends ScriptGenerator {

	public BehaviorElementScript(BehaviorElement model, int indent, IErrorListener logger) {
		super(model, indent, logger);
	}
	
	protected BehaviorElement getCastedModel() {
		return (BehaviorElement)getModel();
	}
	
	public void validate() throws TPLException {
		if(getCastedModel().getInitialState() == null) {
			getLogger().warning(String.format("Behavior %s 에 Initial State가 없습니다.", getCastedModel().getName()), -1, -1, -1);
		}

		Hashtable<String, ItemElement> nameMap = new Hashtable<String, ItemElement>();
		EList<Parameter> itemList = getCastedModel().getParams();
		for(int idx = 0; idx < itemList.size(); idx += 1) {
			Parameter item = itemList.get(idx);
			if(item.getType() == null) {
				getLogger().error(String.format("Behavior %s 의 parameter 타입을 지정하지 않았습니다.", getCastedModel().getName()), -1, -1, -1);
			}
			if(item.getName() == null) {
				getLogger().error(String.format("Behavior %s 의 parameter 이름을 지정하지 않았습니다.", getCastedModel().getName()), -1, -1, -1);
			}
			
			if(nameMap.containsKey(item.getName())) {
				getLogger().warning(String.format("Behavior %s 의 parameter 이름(%s)이 중복됩니다.", getCastedModel().getName(), item.getName()), -1, -1, -1);
			}
			nameMap.put(item.getName(), item);
		}

		EList<ReferElement> refList = getCastedModel().getReferences();
		for(ReferElement refItem : refList) {
			EList<ConnectionElement> conList = refItem.getSourceConnections();
			for(ConnectionElement con : conList) {
				ReferElement refSource2 = (ReferElement)con.getSource2();
				ReferElement refTarget = (ReferElement)con.getTarget();
				if(!(refSource2.getRealModel() instanceof StateElement)) {
					if(con.getRelationship() == RelationShip.TASK_CALL) {
						// KJH 20110526 s, behavior/connector 분류
						if (refTarget.getRealModel() instanceof BehaviorElement) {
							getLogger().error(String.format("Behavior %s 호출이 지정되지 않았습니다.", refTarget.getName()), -1, -1, -1);
						}
						else if (refTarget.getRealModel() instanceof ConnectorElement) {
							getLogger().error(String.format("Connector %s 호출이 지정되지 않았습니다.", refTarget.getName()), -1, -1, -1);
						}	// KJH 20110526 e, behavior/connector 분류
					}
					else if(con.getRelationship() == RelationShip.ACTION_CALL) {
						getLogger().error(String.format("Action %s 호출이 지정되지 않았습니다.", refTarget.getName()), -1, -1, -1);
					}
				}
			}
		}

		nameMap.clear();
		EList<StateElement> stateList = getCastedModel().getStates();
		for(int idx = 0; idx < stateList.size(); idx += 1) {
			StateElement stateItem = stateList.get(idx);

			if(nameMap.containsKey(stateItem.getName())) {
				getLogger().warning(String.format("State %s 의 이름이 중복되었습니다.", stateItem.getName()), -1, -1, -1);
			}
			nameMap.put(stateItem.getName(), stateItem);
		}

		StateElement initState = getCastedModel().getInitialState();
		if(initState != null) {
			StateElementScript stateScript = new StateElementScript((StateElement)initState, getIndent() + 1, getLogger());
			stateScript.validate();
		}

		for(int idx = 0; idx < stateList.size(); idx += 1) {
			StateElement stateItem = stateList.get(idx);
			if(initState == stateItem) {
				continue;
			}

			StateElementScript stateScript = new StateElementScript(stateItem, getIndent() + 1, getLogger());
			stateScript.validate();
		}
		
		// KJH 20110526 s, construct&destruct validate
		StructBlockScript structScript = new StructBlockScript(getCastedModel(),
				getCastedModel().getConstruct(), getIndent() + 1, getLogger());
		structScript.validate();

		structScript = new StructBlockScript(getCastedModel(),
				getCastedModel().getDestruct(), getIndent() + 1, getLogger());
		structScript.validate();
		// KJH 20110526 e, construct&destruct validate
	}

	@Override
	public String toString() {
		if(getCastedModel() == null) {
			return "";
		}

		EList<StateElement> stateList = getCastedModel().getStates();
		EList<Parameter> paramList = getCastedModel().getParams();
		EList<String> stmtList = getCastedModel().getStatements();

		StringBuilder sb = new StringBuilder();
		sb.append(TPLUtil.getWs(getIndent())).append("behavior ")
											 .append(getCastedModel().getName())
											 .append("(");
		for(int idx = 0; idx < paramList.size(); idx += 1) {
			Parameter item = paramList.get(idx);
			if(idx > 0) {
				sb.append(",");
			}
			sb.append(" ").append(item.getType());
			sb.append(" ").append(item.getName());
		}
		sb.append(")").append(" {\r\n");
		
		// KJH 20110526 s, statements
		if (stmtList != null && stmtList.size() > 0) {
			for (String str : getCastedModel().getStatements()) {
				sb.append(TPLUtil.getWs(getIndent() + 1)).append(str).append("\r\n");
			}
			sb.append("\r\n");
		}	// KJH 20110526 e, statements
		
		// KJH 20110526 s, construct & destruct
		// construct
		StructBlockScript structScript = new StructBlockScript(getCastedModel(),
				getCastedModel().getConstruct(), getIndent() + 1, getLogger());
		String tmp;
		sb.append(tmp = structScript.toString());
		if (tmp.length() > 0) {
			sb.append("\r\n");
		}
		// construct_
		
		// destruct
		structScript = new StructBlockScript(getCastedModel(),
				getCastedModel().getDestruct(), getIndent() + 1, getLogger());
		sb.append(tmp = structScript.toString());
		if (tmp.length() > 0) {
			sb.append("\r\n");
		}	// destruct_
		// KJH 20110526 e, construct & destruct
		
		StateElement initState = getCastedModel().getInitialState();
		if(initState != null) {
			StateElementScript stateScript = new StateElementScript(initState, getIndent() + 1, getLogger());
			sb.append(stateScript.toString());
			sb.append("\r\n");
		}

		for(int idx = 0; idx < stateList.size(); idx += 1) {
			StateElement stateItem = stateList.get(idx);
			if(initState == stateItem) {
				continue;
			}

			StateElementScript stateScript = new StateElementScript(stateItem, getIndent() + 1, getLogger());
			sb.append(stateScript.toString());
			sb.append("\r\n");
		}

		sb.append(TPLUtil.getWs(getIndent())).append("};").append("\r\n");

		return sb.toString();
	}
}
