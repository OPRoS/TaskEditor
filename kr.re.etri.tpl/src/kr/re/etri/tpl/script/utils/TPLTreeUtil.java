package kr.re.etri.tpl.script.utils;

import kr.re.etri.tpl.grammar.RFSMParser;

import org.antlr.runtime.tree.CommonErrorNode;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.Tree;

public class TPLTreeUtil {
	public static String getFirstLeafValue(Tree root){
		if(root == null){
			return "";
		}
		int size = root.getChildCount();
		if(size ==0 )
			return root.getText();
		else
			return TPLTreeUtil.getFirstLeafValue(root.getChild(0));
		
	}
	
	// KJH 20100512 s, get text
	public static String gets(Tree root){
		if(root == null || root instanceof CommonErrorNode)
			return "";
		
		StringBuffer buffer = new StringBuffer();
		int tokType = root.getType();
		
		// KJH 20100629, eob/eoe/eof/eol에 대해 추가
		if(RFSMParser.EOL == tokType||
				RFSMParser.EOF == tokType ||
				RFSMParser.EOE == tokType ||
				RFSMParser.EOB == tokType){
			;
		}
		else if(RFSMParser.STMTS == tokType){
			// KJH 20101029 s, STMTS의 child가 0-* 이므로 수정함 
			int size = root.getChildCount();
			for(int i=0; i<size; i++){
				buffer.append(gets(root.getChild(i)));
				buffer.append("\r\n");
			}
			// KJH 20101029 e, STMTS의 child가 0-* 이므로 수정함
		}
		else if(RFSMParser.STMTBLOCK == tokType){	// KJH 20100702, EOL token에 의해 값이 구해지지 않던 오류 수정
			// KJH 20101029 s, 문법 변경에 따른 수정
			buffer.append("{\r\n");
			int size = root.getChildCount();
			for(int i=0; i<size; i++){
				buffer.append(gets(root.getChild(i)));
			}
			buffer.append("\r\n}");
			// KJH 20101029 e, 문법 변경에 따른 수정
		}
		else if(RFSMParser.SEQ == tokType){
			// KJH 20101029 s, 문법 변경에 따른 수정
			buffer.append("sequential {");
			int size = root.getChildCount();
			for(int i=0; i<size; i++){
				buffer.append(gets(root.getChild(i)));
				buffer.append("\r\n");
			}
			buffer.append("\r\n}");
			// KJH 20101029 e, 문법 변경에 따른 수정
		}
		else if(RFSMParser.PAR == tokType){
			// KJH 20101029 s, 문법 변경에 따른 수정
			buffer.append("parallal {");
			int size = root.getChildCount();
			for(int i=0; i<size; i++){
				buffer.append(gets(root.getChild(i)));
				buffer.append("\r\n");
			}
			buffer.append("\r\n}");
			// KJH 20101029 e, 문법 변경에 따른 수정
		}
		else if(RFSMParser.STMTEXPR == tokType){
			int size = root.getChildCount();
			for(int i=0; i<size; i++){
				if (RFSMParser.ASMT == root.getChild(i).getType()) {
					buffer.append(gets(root.getChild(i)));
					break;
				}
			}
			buffer.append(";");
		}
		else if(RFSMParser.STMTCALL == tokType) {
			int size = root.getChildCount();
			for(int i=0; i<size; i++){
				if (RFSMParser.CALL == root.getChild(i).getType()) {
					buffer.append(gets(root.getChild(i)));
					break;
				}
			}
			buffer.append(";");
		}
		else if(RFSMParser.WAIT == tokType){
			buffer.append("SLEEP(");
			int size = root.getChildCount();
			for(int i=0; i<size; i++){
				if (RFSMParser.TIME == root.getChild(i).getType()) {
					buffer.append(gets(root.getChild(i)));
					break;
				}
			}
			buffer.append(");");
		}
		else if(RFSMParser.SYNCH == tokType){
			buffer.append("SYNCH(");
			int size = root.getChildCount();
			for(int i=0; i<size; i++){
				if (RFSMParser.ID == root.getChild(i).getType()) {
					buffer.append(gets(root.getChild(i)));
					break;
				}
			}
			buffer.append(");");
		}
		else if(RFSMParser.IF == tokType){
			int size = root.getChildCount();
			String cond = "", whent = "", whenf = "";

			for(int i=0; i<size; i++){
				Tree child = root.getChild(i);

				if(RFSMParser.COND == child.getType()){
					cond = gets(child.getChild(0));
				}
				else if(RFSMParser.WHENT == child.getType()){
					whent = gets(child.getChild(0));
				}
				else if(RFSMParser.WHENF == child.getType()){
					whenf = gets(child.getChild(0));
				}
			}
			buffer.append("if (").append(cond).append(") ").append(whent);
			if(whenf != "") {
				buffer.append("else ").append(whenf);
			}
		}
		else if(RFSMParser.GOTO == tokType){
			int size = root.getChildCount();
			
			for(int i=0; i<size; i++){
				Tree child = root.getChild(i);
				
				if(RFSMParser.STATE == child.getType()){
					buffer.append("moveto ");
					buffer.append(gets(child.getChild(0))).append(";");
					break;
				}
				else if(RFSMParser.STAY == child.getType()){
					buffer.append("goto ");
					buffer.append(gets(child.getChild(0))).append(";");
					break;
				}
			}
		}
		else if(RFSMParser.IVK == tokType){
			int size = root.getChildCount();
			String beha = "", eoe = "", eob = "";
			for(int i=0; i<size; i++){
				Tree child = root.getChild(i);
				
				if(RFSMParser.BEHA == child.getType()){
					beha = gets(child.getChild(0));
				}
				else if(RFSMParser.EOE == child.getType()){
					eoe = gets(child.getChild(0));
				}
				else if(RFSMParser.EOB == child.getType()){
					eob = gets(child.getChild(0));
				}
			}
			buffer.append("expand ").append(beha);
			if(eoe != "")
				buffer.append(" ~> ").append(eoe);
			if(eob != "")
				buffer.append(" ? ").append(eob);
			buffer.append(";");
		}
		else if(RFSMParser.CALL == tokType){
			int size = root.getChildCount();
			String cname = "", cparams = "";
			for(int i=0; i<size; i++){
				Tree child = root.getChild(i);
				
				if(RFSMParser.CNAME == child.getType()){
					cname = gets(child.getChild(0));
				}
				else if(RFSMParser.CPARAMS == child.getType()){
					cparams = gets(child);
				}
			}
			buffer.append(cname).append("(").append(cparams).append(")");
		}
		else if(RFSMParser.CPARAMS == tokType){
			int size = root.getChildCount();
			
			for(int i=0; i<size; i++){
				Tree child = root.getChild(i);

				if(RFSMParser.CPARAM == child.getType()){
					if(i>0)
						buffer.append(", ");
					buffer.append(gets(child.getChild(0)));
				}
			}
		}
		else if(RFSMParser.ASMT == tokType){
			int size = root.getChildCount();
			String asmtop="";

			for(int i=0; i<size; i++){
				Tree child = root.getChild(i);

				if(RFSMParser.COND == child.getType() ||
						RFSMParser.ADD == child.getType()){
					buffer.append(gets(child));

					if(asmtop != ""){
						buffer.append(asmtop);
						asmtop = "";
					}
				}
				else if(child.getType() == RFSMParser.OP){
					asmtop = gets(child);
				}
			}
		}
		else if(RFSMParser.COND == tokType ||
				RFSMParser.NOT == tokType ||
				RFSMParser.SIGN == tokType){
			int size = root.getChildCount();
			for(int i=0; i<size; i++){
				Tree child = root.getChild(i);

				buffer.append(gets(child));
			}
		}
		else if(RFSMParser.OR == tokType){
			int size = root.getChildCount();
			for(int i=0; i<size; i++){
				Tree child = root.getChild(i);

				if(RFSMParser.AND == child.getType()){
					if(i > 0){
						buffer.append(" || ");
					}
					buffer.append(gets(child));
				}
			}
		}
		else if(RFSMParser.AND == tokType){
			int size = root.getChildCount();
			for(int i=0; i<size; i++){
				Tree child = root.getChild(i);

				if(RFSMParser.NOT == child.getType()){
					if(i > 0){
						buffer.append(" && ");
					}
					buffer.append(gets(child));
				}
			}
		}
		else if(RFSMParser.BOOL == tokType){
			int size = root.getChildCount();
			String relop="";

			for(int i=0; i<size; i++){
				Tree child = root.getChild(i);

				if(RFSMParser.ADD == child.getType()){
					buffer.append(gets(child));

					if(relop != ""){
						buffer.append(relop);
						relop = "";
					}
				}
				else if(RFSMParser.OP == child.getType()){
					relop = gets(child);
				}
			}
		}
		else if(RFSMParser.ADD == tokType){
			int size = root.getChildCount();
			String addop = "";

			for(int i=0; i<size; i++){
				Tree child = root.getChild(i);

				if(RFSMParser.MUL == child.getType()){
					buffer.append(gets(child));

					if(addop != ""){
						buffer.append(addop);
						addop = "";
					}
				}
				else if(RFSMParser.OP == child.getType()){
					addop = gets(child);
				}
			}
		}
		else if(RFSMParser.MUL == tokType){
			int size = root.getChildCount();
			String mulop = "";

			for(int i=0; i<size; i++){
				Tree child = root.getChild(i);

				if(RFSMParser.SIGN == child.getType()){
					buffer.append(gets(child));

					if(mulop != ""){
						buffer.append(mulop);
						mulop = "";
					}
				}
				else if(RFSMParser.OP == child.getType()){
					mulop = gets(child);
				}
			}
		}
		else if(RFSMParser.EXPRPAR == tokType) {
			buffer.append("(").append(gets(root.getChild(0))).append(")");
		}
		else if (RFSMParser.LVAR == tokType) {	// KJH 20110926
			int size = root.getChildCount();
			String type = "", name = "", rval = "";

			for(int i=0; i<size; i++){
				Tree child = root.getChild(i);

				if (RFSMParser.TYPE == child.getType()) {
					type = gets(child);
				}
				else if (RFSMParser.NAME == child.getType()){
					name = gets(child);
				}
				else if (RFSMParser.RVAL == child.getType()) {
					rval = gets(child);
				}
			}
			
			buffer.append(type).append(" ").append(name).append(" = ").append(rval).append(";");
		}
		else {
			int size = root.getChildCount();
			if (size > 0) {
				for(int i=0; i<size; i++){
					Tree child = root.getChild(i);
					buffer.append(gets(child));
				}
			}
			else {
				buffer.append(root.getText());
			}
		}
		// KJH 20101019 e, 수정

		return buffer.toString();
	}
	// KJH 20100512 e, get text
}
