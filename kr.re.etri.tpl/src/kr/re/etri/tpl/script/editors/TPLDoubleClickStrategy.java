package kr.re.etri.tpl.script.editors;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextDoubleClickStrategy;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.swt.custom.StyledText;

public class TPLDoubleClickStrategy implements ITextDoubleClickStrategy {
	protected ITextViewer fText;

	protected StyledText text;	// WJH 100831 추가
	protected int line = 0;	// WJH 100831 추가
	
	public void doubleClicked(ITextViewer part) {
		int pos = part.getSelectedRange().x;

		if (pos < 0)
			return;

		fText = part;

		if (!selectComment(pos)) {
			selectWord(pos);
		}
	}
	protected boolean selectComment(int caretPos) {
		IDocument doc = fText.getDocument();
		int startPos, endPos;

		try {
			int pos = caretPos;
			char c = ' ';

			while (pos >= 0) {
				c = doc.getChar(pos);
				if (c == '\\') {
					pos -= 2;
					continue;
				}
				if (c == Character.LINE_SEPARATOR || c == '\"')
					break;
				--pos;
			}

			if (c != '\"')
				return false;

			startPos = pos;

			pos = caretPos;
			int length = doc.getLength();
			c = ' ';

			while (pos < length) {
				c = doc.getChar(pos);
				if (c == Character.LINE_SEPARATOR || c == '\"')
					break;
				++pos;
			}
			if (c != '\"')
				return false;

			endPos = pos;

			int offset = startPos + 1;
			int len = endPos - offset;
			fText.setSelectedRange(offset, len);
			return true;
		} catch (BadLocationException x) {
		}

		return false;
	}
	protected boolean selectWord(int caretPos) {

		IDocument doc = fText.getDocument();
		int startPos, endPos;

		try {

			int pos = caretPos;
			char c;

			while (pos >= 0) {
				c = doc.getChar(pos);
				if (!Character.isJavaIdentifierPart(c))
					break;
				--pos;
			}

			startPos = pos;

			pos = caretPos;
			int length = doc.getLength();

			while (pos < length) {
				c = doc.getChar(pos);
				if (!Character.isJavaIdentifierPart(c))
					break;
				++pos;
			}

			endPos = pos;
			selectRange(startPos, endPos);
			return true;

		} catch (BadLocationException x) {
		}

		return false;
	}

	private void selectRange(int startPos, int stopPos) {
		int offset = startPos + 1;
		int length = stopPos - offset;
		fText.setSelectedRange(offset, length);
		
//		WJH 100830 S 더블클릭 시 현재 라인 체크
//		if(startPos == stopPos){
////			System.out.println("======================  Double Click1 ============================");
//			IDocument obj = fText.getDocument();
////			TextSelection selection = new TextSelection(fText.getDocument(), startPos, 0);
////			System.out.println(selection.getText());
////			System.out.println("line "+(selection.getStartLine()+1));
//			text = fText.getTextWidget();
//			line  = text.getLineAtOffset(stopPos);
//			IScriptNode active = ScriptManager.getInstance().getActiveNode();
//			String path = "/"+active.getName();
//			TPLScriptEditor editor = ScriptManager.getInstance().getTPLScriptEditor(path);
////			System.out.println("======================  Double Click2 ============================");
//			ArrayList location = editor.scriptData(startPos);
//			String action = "";
//			String state = "";
//			String behavior = "";
//			String tran = "";
//			if(location.size()>0){
//				for(int i=0; i<location.size(); i++){
//					Object loc = location.get(i);
//					if(loc instanceof ActionNode){
//						action = ((ActionNode)loc).getName();
//					}
//					if(loc instanceof WorkerNode){
//						action = ((WorkerNode)loc).getName();
//					}
//					if(loc instanceof TranNode){
//						tran = ((TranNode)loc).getName();
//					}
//					else if(loc instanceof StateNode){
//						state = ((StateNode)loc).getName();
//					}
//					else if(loc instanceof BehaviorNode){
//						behavior = ((BehaviorNode)loc).getName();
//					}
//				}
//			}
//			
//			if((tran.equals("") || action.equals("")) && state.equals("") && behavior.equals(""))
//				return;
//			
//			String str = text.getLine(line).trim();			
//			
//			RegisterData rData = new RegisterData();
////			rData.setTask(ScriptManager.getInstance().getSelectTask().getName());
////			IEditorInput newInput = TPLScriptEditor.getInstance().getEditorInput();
////			IEditorInput newInput2 = TPLScriptEditor.getInstance().getNewInput();
////			rData.setNewInput(newInput);
//			if(str.trim().equals("{")){
//				line++;
//				str = text.getLine(line).trim();
//			}
//			
//			rData.setPath(path);
//			rData.setLine(line);
//			String target = "";
//			if(!behavior.equals("")) {target += behavior; rData.setBehavior(behavior);}
//			if(!state.equals("")) {target += "."+state; rData.setState(state);}
//			if(!action.equals("")) {target += "."+action; rData.setAction(action);}
//			else if(!tran.equals("")) {target += "."+tran; rData.setTran(tran);}
//			rData.setTarget(target);
//			rData.setStr(str);
//			rData.setOffset(offset);
////			System.out.println("======================  Double Click3 ============================");
//			if(DebugBreakpointView.getInstance().getDebugList().size()>0){
//				ArrayList list = DebugBreakpointView.getInstance().getDebugList();
//				for(int i=0; i<list.size(); i++){
//					RegisterData data = (RegisterData)list.get(i);
//					if(data.getPath().equals(rData.getPath())){
//						if(data.getLine() == rData.getLine()){
//							DebugMessageSend.getInstance().debugRemove(rData);
//							try{
//							obj.replace(startPos-str.length(), str.length(), str);
//							saveDebug("c:\\bebug.data");
//							}
//							catch(Exception e){
//								e.printStackTrace();
//							}
//							return;
//						}
//						else{
//							
//						}
//					}					
//				}
//				DebugMessageSend.getInstance().debugRegist(rData);
//			}
//			else{
//				DebugMessageSend.getInstance().debugRegist(rData);
//			}
//			TPLScriptEditor.getInstance().debugLineChange(text, line, str, 1);
//			String url = System.getProperty("user.dir");
//			saveDebug(url+"\\bebug.data");
//		}
//		System.out.println("======================  Double Click4 ============================");
		
		
//		WJH 100830 E 더블클릭 시 현재 라인 체크
	}
	
//	public void saveDebug(String filePath){
//		StringBuffer sb = new StringBuffer();
//		try{
//		FileWriter fw = new FileWriter(new File(filePath));
//		BufferedWriter bw=new BufferedWriter(fw);
//		sb.append("<Breakpoint>\n");
//		addList(DebugBreakpointView.getInstance().getDebugList(), sb);
//		sb.append("</Breakpoint>");
//		bw.write(sb.toString());
//		bw.close();
//		}
//		catch(Exception e){
//			e.printStackTrace();
//		}
//	}
//	
//	public StringBuffer addList(ArrayList list, StringBuffer sb){
//		String t = "\t";
//		if(list.size()>0){
//			for(int i=0; i<list.size(); i++){
//				sb.append(t+"<Line>\n");
//				RegisterData rd = (RegisterData)list.get(i);
//				sb.append(t+t+"<path>");
//				sb.append(rd.getPath());
//				sb.append("</path>\n");
//				sb.append(t+t+"<target>");
//				sb.append(rd.getTarget());
//				sb.append("</target>\n");
//				sb.append(t+t+"<task>");
//				sb.append(rd.getTask());
//				sb.append("</task>\n");
//				sb.append(t+t+"<linenum>");
//				sb.append(rd.getLine());
//				sb.append("</linenum>\n");
//				sb.append(t+t+"<str>");
//				sb.append(rd.getStr());
//				sb.append("</str>\n");
//				sb.append(t+t+"<offset>");
//				sb.append(rd.getOffset());
//				sb.append("</offset>\n");				
//				sb.append(t+"</Line>\n");
//				System.out.println();
//			}
//		}
//		return sb;
//	}
}