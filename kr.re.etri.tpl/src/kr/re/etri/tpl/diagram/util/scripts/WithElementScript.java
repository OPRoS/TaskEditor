package kr.re.etri.tpl.diagram.util.scripts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import kr.re.etri.tpl.diagram.listener.IErrorListener;
import kr.re.etri.tpl.diagram.util.TPLException;
import kr.re.etri.tpl.diagram.util.TPLUtil;
import kr.re.etri.tpl.taskmodel.BehaviorElement;
import kr.re.etri.tpl.taskmodel.ConnectionElement;
import kr.re.etri.tpl.taskmodel.ConnectorElement;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.ReferElement;
import kr.re.etri.tpl.taskmodel.RelationShip;
import kr.re.etri.tpl.taskmodel.WithElement;

import org.eclipse.emf.common.util.EList;

public class WithElementScript extends ScriptGenerator {

	public WithElementScript(WithElement model, int indent, IErrorListener logger) {
		super(model, indent, logger);
	}

	protected WithElement getCastedModel() {
		return (WithElement)getModel();
	}
	
	private class ComparatorImpl implements Comparator<ConnectionElement>{

		@Override
		public int compare(ConnectionElement o1, ConnectionElement o2) {
			String s1 = o1.getName();
			String s2 = o2.getName();
			int pri1= Integer.MAX_VALUE;
			int pri2= Integer.MAX_VALUE;
			if(s1 != null ){
				try{
					pri1 = Integer.parseInt(s1);
				}catch(NumberFormatException e){	
				}
			}
			if(s2 != null){
				try{
					pri2 = Integer.parseInt(s2);
				}catch(NumberFormatException e){
				}
			}
			return pri1-pri2;
		}
		
	}
	
	@Override
	public void validate() throws TPLException {
		if (getCastedModel() == null) {
			return;
		}
		
		EList<ReferElement> refList = getCastedModel().getReferences();
		boolean isConnected = false;
		for (ReferElement refItem : refList) {
			EList<ConnectionElement> connList = refItem.getSourceConnections();
			for (ConnectionElement conn : connList) {
				ReferElement refTarget = (ReferElement)conn.getTarget();
				if(RelationShip.TASK_CALL == conn.getRelationship()) {
					EList<String> strList = conn.getCondition();
					StringBuilder sb = new StringBuilder();

					for(String str : strList) {
						sb.append(str.trim());
					}
					if(sb.length() == 0) {
						// KJH 20110623, WithElement에서 조건이 없을 경우, if문은 생략
						if (refTarget.getRealModel() instanceof BehaviorElement) {
							getLogger().warning(String.format("Run %s 로부터 Behavior %s로의 호출 조건이 설정되지 않았습니다.",
									getCastedModel().getName(), refTarget.getName()), -1, -1, -1);
						}
						else if (refTarget.getRealModel() instanceof ConnectorElement) {
							getLogger().warning(String.format("Run %s 로부터 Connector %s로의 호출 조건이 설정되지 않았습니다.",
									getCastedModel().getName(), refTarget.getName()), -1, -1, -1);
						}
					}
				}
				isConnected = true;
			}
		}
		
		if (!isConnected) {
			getLogger().warning(String.format("Run %s 에서 아무런 호출도 지정되지 않았습니다.", getCastedModel().getName()), -1, -1, -1);
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		EList<ReferElement> refList = getCastedModel().getReferences();
		EList<String> stmtList = getCastedModel().getStatements();
		
		List<ConnectionElement> connList = new ArrayList<ConnectionElement>();
		for (ReferElement refItem : refList) {
			for (ConnectionElement conn : refItem.getSourceConnections()) {
				if (RelationShip.TASK_CALL == conn.getRelationship()) {
					connList.add(conn);
				}
			}
		}
		
		ConnectionElement[] arr = connList.toArray(new ConnectionElement[0]);
		Arrays.sort(arr, new ComparatorImpl());

		if (arr.length == 0 && stmtList.size() == 0) {
			return "";
		}
		
		boolean beforeAppended = false;

		// KJH 20110722 s, stmt_with 변경에 따른 수정
//		sb.append(TPLUtil.getWs(getIndent())).append("with:\r\n");
		sb.append(TPLUtil.getWs(getIndent())).append("run");
		sb.append(" @cycle<").append(getCastedModel().getCycle()).append(">");
		sb.append(" {\r\n");

		for (String stmt : stmtList) {
			sb.append(TPLUtil.getWs(getIndent() + 1)).append(stmt).append("\r\n");
			beforeAppended = true;
		}

		if (beforeAppended) {
			sb.append("\r\n");
		}

		for (int i=0; i<arr.length; i++) {
			ConnectionElement conn = arr[i];
			ReferElement targetItem = (ReferElement)conn.getTarget2();
			ItemElement realItem = targetItem.getRealModel();

			int idx = 0;
			List<String> strList = conn.getCondition();
			StringBuilder tmpCond = new StringBuilder();
			for(String str : strList) {
				if(idx > 0) {
					tmpCond.append("\r\n").append(TPLUtil.getWs(getIndent()+2));
				}
				tmpCond.append(str);

				idx++;
			}

			sb.append(TPLUtil.getWs(getIndent()+1));

			if (i == 0) {
				if ((arr.length > 1) || (tmpCond.toString().trim().length() > 0)) {
					sb.append("if (").append(tmpCond).append(")\t");
				}
			}
			else {
				if ((i == arr.length - 1) && (tmpCond.toString().trim().length() == 0)) {
					sb.append("else\t");
				}
				else {
					sb.append("else if (").append(tmpCond).append(")\t");
				}
			}

			if (realItem instanceof BehaviorElement) {
				sb.append("expand ").append(realItem.getName()).append("();\r\n");
			}
			else if (realItem instanceof ConnectorElement) {
				sb.append("expand ").append(realItem.getName()).append("();\r\n");
			}
			else {
				sb.append("moveto ").append(realItem.getName()).append(";\r\n");
			}
		}
		
		sb.append(TPLUtil.getWs(getIndent())).append("}\r\n");
		
		return sb.toString();
	}

}
