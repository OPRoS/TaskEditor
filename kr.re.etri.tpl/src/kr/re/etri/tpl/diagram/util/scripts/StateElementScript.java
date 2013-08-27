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
import kr.re.etri.tpl.taskmodel.ExpandTransElement;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.ReferElement;
import kr.re.etri.tpl.taskmodel.RelationShip;
import kr.re.etri.tpl.taskmodel.StateAction;
import kr.re.etri.tpl.taskmodel.StateAttribute;
import kr.re.etri.tpl.taskmodel.StateElement;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.EList;

public class StateElementScript extends ScriptGenerator {
	private static Logger logger = Logger.getLogger(StateElementScript.class);
	public StateElementScript(StateElement model, int indent, IErrorListener logger) {
		super(model, indent, logger);
	}
	
	protected StateElement getCastedModel() {
		return (StateElement)getModel();
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
	
	private class ComparatorImpl2 implements Comparator<ItemElement>{

		@Override
		public int compare(ItemElement o1, ItemElement o2) {
			String s1 = o1.getName();
			String s2 = o2.getName();
			int pri1= Integer.MAX_VALUE;
			int pri2= Integer.MAX_VALUE;
			
			if (o1 instanceof ReferElement) {
				for (ConnectionElement conn : ((ReferElement)o1).getSourceConnections()) {
					s1 = conn.getName();
					break;
				}
			}
			
			if (o2 instanceof ReferElement) {
				for (ConnectionElement conn : ((ReferElement)o2).getSourceConnections()) {
					s2 = conn.getName();
					break;
				}
			}
			
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

	public void validate() throws TPLException {
		EList<ReferElement> refList = getCastedModel().getReferences();
		for(ReferElement refItem : refList) {
			EList<ConnectionElement> connEList = refItem.getSourceConnections();
			ConnectionElement[] arr = connEList.toArray(new ConnectionElement[0]);
			Arrays.sort(arr, new ComparatorImpl());
			
			
			for(int i =0; i< arr.length ; i++) {
				ConnectionElement conn = arr[i];
				ReferElement refTarget = (ReferElement)conn.getTarget();
				if(RelationShip.TASK_CALL == conn.getRelationship()) {
					EList<String> strList = conn.getCondition();
					StringBuilder sb = new StringBuilder();

					for(String str : strList) {
						sb.append(str.trim());
					}
					if(sb.length() == 0) {
						// KJH 20110526 s, behavior/connector 분류
						if (refTarget.getRealModel() instanceof BehaviorElement) {
							getLogger().warning(String.format("State %s 로부터 Behavior %s로의 호출 조건이 설정되지 않았습니다.", refItem.getName(), refTarget.getName()), -1, -1, -1);
						}
						else if (refTarget.getRealModel() instanceof ConnectorElement) {
							getLogger().warning(String.format("State %s 로부터 Connector %s로의 호출 조건이 설정되지 않았습니다.", refItem.getName(), refTarget.getName()), -1, -1, -1);
						}	// KJH 20110526 e, behavior/connector 분류
					}
				}
				else if(RelationShip.ACTION_CALL == conn.getRelationship()) {
					StateAction stayAction = getCastedModel().getStay();
					List<String> strList = stayAction.getStatements();
					StringBuilder sb = new StringBuilder();
					for(String str : strList) {
						sb.append(str.trim());
					}
					if(sb.length() == 0) {
						getLogger().warning(String.format("State %s 로부터 Action %s로의 호출 조건이 설정되지 않았습니다.", refItem.getName(), refTarget.getName()), -1, -1, -1);
					}
				}
				else if(RelationShip.TRANSITION == conn.getRelationship()) {
					List<String> strList = conn.getCondition();
					StringBuilder sb = new StringBuilder();

					for(String str : strList) {
						sb.append(str);
					}

					if(sb.toString().trim().length() == 0) {
						StateElement realItem = (StateElement)refItem.getRealModel();
						BehaviorElement taskElement = (BehaviorElement)realItem.getParent();
						if(taskElement != null) {
							getLogger().warning(String.format("Behavior %s에서 State %s 로부터 State %s 로의 transition 조건이 설정되지 않았습니다.", taskElement.getName(), refItem.getName(), refTarget.getName()), -1, -1, -1);
						}
						else {
							ItemElement realTarget = refTarget.getRealModel();
							if(realItem != realTarget) {
								getLogger().warning(String.format("State %s 로부터 State %s 로의 transition 조건이 설정되지 않았습니다.", realItem.getName(), refTarget.getName()), -1, -1, -1);
							}
						}
					}
				}
			}
		}
		
		StateElement state = getCastedModel();
		StateAction stayAction = state.getStay();
		if(stayAction == null) {
			getLogger().warning(String.format("State %s 에 Stay action이 정의되지 않았습니다.", state.getName()), -1, -1, -1);
		}
	}

	@Override
	public String toString() {
		if(getCastedModel() == null) {
			return "";
		}
		StringBuilder sb = new StringBuilder();

		if(StateAttribute.INITIAL == getCastedModel().getAttribute()) {
			sb.append(TPLUtil.getWs(getIndent())).append("initial ");
		}
		else if(StateAttribute.TARGET == getCastedModel().getAttribute()){
			// KJH 20100504, target -> goal
			sb.append(TPLUtil.getWs(getIndent())).append("goal ");
		}
		else {
			sb.append(TPLUtil.getWs(getIndent()));
		}
		sb.append("state ").append(getCastedModel().getName()).append(" {\r\n");
		
		// KJH 20110526 s, statements
		EList<String> stmtList = getCastedModel().getStatements();
		if (stmtList != null && stmtList.size() > 0) {
			for (String str : getCastedModel().getStatements()) {
				sb.append(TPLUtil.getWs(getIndent() + 1)).append(str).append("\r\n");
			}
			sb.append("\r\n");
		}	// KJH 20110526 e, statements
		
		sb.append(TPLUtil.getWs(getIndent()+1)).append("transition").append(" {\r\n");
		EList<ReferElement> refList = getCastedModel().getReferences();
		List<ItemElement> connList = new ArrayList<ItemElement>();
		for (ReferElement refItem : refList) {
			connList.addAll(refItem.getSourceConnections());
			
			// KJH 20110601 s, expand & trans
			for (ItemElement refChild : refItem.getItems()) {
				if ((refChild instanceof ReferElement) &&
						(((ReferElement)refChild).getRealModel() instanceof ExpandTransElement)) {
					connList.add((ReferElement)refChild);
				}
			}	// KJH 20110601 e, expand & trans
		}
		
		ItemElement[] arr = connList.toArray(new ItemElement[0]);
		Arrays.sort(arr, new ComparatorImpl2());
		
		for(int i = 0; i < arr.length ; i++) {
			ConnectionElement conn = null;
			ConnectionElement conn2 = null;
			
			if (arr[i] instanceof ConnectionElement) {
				conn = (ConnectionElement)arr[i];
				if(RelationShip.ACTION_CALL == conn.getRelationship() ) {
					continue;
				}
			}
			else if (arr[i] instanceof ReferElement) {
				ReferElement portRef = (ReferElement)arr[i];
				for (ConnectionElement connection : portRef.getSourceConnections()) {
					if (RelationShip.TASK_CALL == connection.getRelationship()) {
						conn = connection;
					}
					else if (RelationShip.TRANSITION == connection.getRelationship()) {
						conn2 = connection;
					}
				}
				if (conn == null) {
					conn = conn2;
				}
			}
			
			if (conn == null) {
				continue;
			}
			
			StateElement sourceReal = getCastedModel();
			ReferElement targetRef = (ReferElement)conn.getTarget();
			ItemElement targetReal = targetRef.getRealModel();
			ReferElement targetRef2 = conn2 != null ? (ReferElement)conn2.getTarget() : null;
			ItemElement targetReal2 = targetRef2 != null ? targetRef2.getRealModel() : null;
			
			int idx = 0;
			List<String> strList = conn.getCondition();
			StringBuilder tmpCond = new StringBuilder();
			for(String str : strList) {
				if(idx > 0) {
					tmpCond.append("\r\n").append(TPLUtil.getWs(getIndent()+3));
				}
				tmpCond.append(str);

				idx++;
			}

			// 조건없이도 출력되도록
//			if(tmpCond.toString().length() == 0 ) {
//				continue;
//			}

			if(i == 0 ) {
				sb.append(TPLUtil.getWs(getIndent()+2)).append("if(");
				sb.append(tmpCond);
				sb.append(") {\r\n");
			}
			else {
				// KJH 20110603 s, last order & condition is empty => else {
				if ((i == arr.length - 1) && (tmpCond.toString().trim().length() == 0)) {
					sb.append(TPLUtil.getWs(getIndent()+2)).append("else {\r\n");
				}	// KJH 20110603 e, last order & condition is empty => else {
				else {
					sb.append(TPLUtil.getWs(getIndent()+2)).append("else if(");
					sb.append(tmpCond);
					sb.append(") {\r\n");
				}
			}

			if(sourceReal== targetReal) {
				sb.append(TPLUtil.getWs(getIndent()+3)).append("goto stay;\r\n");
			}
			else {
				logger.debug(targetRef);
				if (targetReal instanceof BehaviorElement) {
					// KJH 20100504, goto -> expand
					sb.append(TPLUtil.getWs(getIndent()+3)).append("expand ")
														   .append(targetRef.getName())
														   .append("()");
					if (targetReal2 != null) {
						sb.append("~>").append(targetReal2.getName());
					}
					sb.append(";\r\n");
				}
				else if (targetReal instanceof ConnectorElement) {
					// KJH 20110526, connector에 대해 추가
					sb.append(TPLUtil.getWs(getIndent()+3)).append("expand ")
														   .append(targetRef.getName())
														   .append("()");
					if (targetReal2 != null) {
						sb.append("~>").append(targetReal2.getName());
					}
					sb.append(";\r\n");
				}
				else {
					// KJH 20100504, goto -> moveto
					sb.append(TPLUtil.getWs(getIndent()+3)).append("moveto ").append(targetRef.getName()).append(";\r\n");
				}
			}
			sb.append(TPLUtil.getWs(getIndent()+2)).append("}\r\n");
		}
		
		sb.append(TPLUtil.getWs(getIndent()+1)).append("}\r\n");//End Transition

		StateAction entryAction = getCastedModel().getEntry();
		if(entryAction != null) {
			sb.append(TPLUtil.getWs(getIndent()+1)).append("entry {\r\n");
			List<String> strList = entryAction.getStatements();
			for(String str : strList) {
				sb.append(TPLUtil.getWs(getIndent()+2)).append(str).append("\r\n");
			}
			sb.append(TPLUtil.getWs(getIndent()+1)).append("}\r\n");
		}

		StateAction stayAction = getCastedModel().getStay();
		if(stayAction != null) {
			sb.append(TPLUtil.getWs(getIndent()+1)).append("stay {\r\n");
			List<String> strList = stayAction.getStatements();
			for(String str : strList) {
				sb.append(TPLUtil.getWs(getIndent()+2)).append(str).append("\r\n");
			}
			sb.append(TPLUtil.getWs(getIndent()+1)).append("}\r\n");
		}

		StateAction exitAction = getCastedModel().getExit();
		if(exitAction != null) {
			sb.append(TPLUtil.getWs(getIndent()+1)).append("exit {\r\n");
			List<String> strList = exitAction.getStatements();
			for(String str : strList) {
				sb.append(TPLUtil.getWs(getIndent()+2)).append(str).append("\r\n");
			}

			sb.append(TPLUtil.getWs(getIndent()+1)).append("}\r\n");
		}

		sb.append(TPLUtil.getWs(getIndent())).append("}\r\n");//End State
		
		return sb.toString();
	}
	
//	private void checkParallel(ConnectionElement[] arr,Map<String, ParallelConnectionElement> parallels,List<ConnectionElement> connList){
//		if(arr == null || arr.length ==0){
//			return;
//		}
//		
//		for(int i = 0 ; i< arr.length ; i++){
//			connList.add(arr[i]);
//		}
//		for(int i =0 ; i< arr.length ; i++){
//			String name = arr[i].getName();
//			if(name == null|| name.equals("")){
//				continue;
//			}
//			List<String> strList = arr[i].getCondition();
//			StringBuilder builder = new StringBuilder();
//			for(String str : strList) {
//				builder.append(str);
//			}
//			String condition = builder.toString();
//			
//			for(int j = i+1 ; j < arr.length ; j++){
//				String name1 = arr[j].getName();
//				List<String> strList1 = arr[j].getCondition();
//				StringBuilder builder1 = new StringBuilder();				
//				for(String str : strList1) {
//					builder1.append(str);
//				}
//				String condition1 = builder1.toString();
//				
//				if(name.equals(name1)&& condition.equals(condition1)){
//					if(connList.contains(arr[i])){
//						connList.remove(arr[i]);
//					}
//					if(connList.contains(arr[j])){
//						connList.remove(arr[j]);
//					}
//					if(parallels.containsKey(name+condition)){
//						ParallelConnectionElement parallel = parallels.get(name+condition);
//						parallel.addGoTo(((ReferElement)arr[i].getTarget2()).getRealModel());
//						parallel.addGoTo(((ReferElement)arr[j].getTarget2()).getRealModel());
//					}
//					else{
//						ParallelConnectionElement parallel = new ParallelConnectionElement(name, condition);
//						parallels.put(name+condition, parallel);
//						parallel.addGoTo(((ReferElement)arr[i].getTarget2()).getRealModel());
//						parallel.addGoTo(((ReferElement)arr[j].getTarget2()).getRealModel());
//					}
//				}
//			}
//		}
//	}
//}
//
//class ParallelConnectionElement{
//	private static Logger logger = Logger.getLogger(ParallelConnectionElement.class);
//	private String m_name;
//	private String m_condition;
//	private Set<ItemElement> gotos = new HashSet<ItemElement>();
//	
//	public ParallelConnectionElement(String name , String condition){
//		m_name = name;
//		m_condition = condition;
//	}
//	
//	public String getName(){
//		return m_name;
//	}
//	
//	public String getCondition(){
//		return m_condition;
//	}
//	
//	public void addGoTo(ItemElement got){
//		gotos.add(got);
//	}
//	
//	public String toString(int indent){
//		StringBuilder builder = new StringBuilder();
//		builder.append(TPLUtil.getWs(indent+2)).append("if(");
//		builder.append(m_condition);
//		builder.append(") {\r\n");
//		builder.append(TPLUtil.getWs(indent+3)).append("parallel {\r\n");
//		for(ItemElement got : gotos){
//			builder.append(TPLUtil.getWs(indent+4)).append("with:\r\n");
////			builder.append(TPLUtil.getWs(indent+5)).append("goto ");
//			if(got instanceof BehaviorElement){
//				// KJH 20100504, goto -> expand
//				builder.append(TPLUtil.getWs(indent+5)).append("expand ");
//				builder.append(got.getName()+"();\r\n");
//			}
//			else if(got instanceof StateElement){
//				// KJH 20100504, goto -> moveto
//				builder.append(TPLUtil.getWs(indent+5)).append("moveto ");
//				builder.append(got.getName()+";\r\n");
//			}
//			else{
//				logger.error("Unexpected Type : "+got);
//			}
//		}
//		builder.append(TPLUtil.getWs(indent+3)).append("}\r\n");
//		builder.append(TPLUtil.getWs(indent+2)).append("}\r\n");
//		return builder.toString();
//	}
}


