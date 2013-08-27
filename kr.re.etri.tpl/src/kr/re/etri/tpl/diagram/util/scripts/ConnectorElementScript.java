package kr.re.etri.tpl.diagram.util.scripts;

import kr.re.etri.tpl.diagram.listener.IErrorListener;
import kr.re.etri.tpl.diagram.util.TPLException;
import kr.re.etri.tpl.diagram.util.TPLUtil;
import kr.re.etri.tpl.taskmodel.BehaviorElement;
import kr.re.etri.tpl.taskmodel.ConnectionElement;
import kr.re.etri.tpl.taskmodel.ConnectorElement;
import kr.re.etri.tpl.taskmodel.ConnectorType;
import kr.re.etri.tpl.taskmodel.ReferElement;
import kr.re.etri.tpl.taskmodel.RelationShip;
import kr.re.etri.tpl.taskmodel.WithElement;

import org.eclipse.emf.common.util.EList;

/*
 * KJH 20101203
 */
public class ConnectorElementScript extends ScriptGenerator {

	public ConnectorElementScript(Object model, int indent, IErrorListener logger) {
		super(model, indent, logger);
	}
	
	public ConnectorElement getCastedModel() {
		return (ConnectorElement)getModel();
	}

	@Override
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
					else if (RelationShip.ACTION_CALL == con.getRelationship()) {
						getLogger().error(String.format("Action %s 호출이 지정되지 않았습니다.", refTarget.getName()), -1, -1, -1);
					}
				}
			}
		}
		
		EList<WithElement> withList = getCastedModel().getWiths();
		for (WithElement with : withList) {
			WithElementScript withScript = new WithElementScript(with, getIndent() + 2, getLogger());
			withScript.validate();
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
		StringBuilder sb = new StringBuilder();

		EList<String> stmtList = getCastedModel().getStatements();

		// name
		sb.append(TPLUtil.getWs(getIndent())).append("connector ")
											 .append(getCastedModel().getName())
											 .append("() {\r\n");
		
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
		
		// conexer or seqexer
		sb.append(TPLUtil.getWs(getIndent() + 1)).append(getCastedModel().getConType().toString()).append(" {\r\n");
		// connection
		EList<WithElement> withList = getCastedModel().getWiths();
		WithElement[] arr = withList.toArray(new WithElement[0]);
//		Arrays.sort(arr, new ComparatorImpl());
		
		for (int i=0; i<arr.length; i++) {
			WithElementScript withScript = new WithElementScript(arr[i], getIndent() + 2, getLogger());
			sb.append(withScript.toString());
		}
		
		sb.append(TPLUtil.getWs(getIndent() + 1)).append("}");
		if (ConnectorType.CONEXER == getCastedModel().getConType()) {
			sb.append(" ").append(getCastedModel().getJoinType().toString());	// join-type
		}
		sb.append(";\r\n");
		// KJH 20110209 e, fix
		
		// end
		sb.append(TPLUtil.getWs(getIndent())).append("};\r\n");
		
		return sb.toString();
	}

}
