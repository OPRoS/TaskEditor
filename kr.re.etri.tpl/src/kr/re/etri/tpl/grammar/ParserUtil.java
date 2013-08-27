package kr.re.etri.tpl.grammar;

import java.io.InputStream;
import java.util.Iterator;

import kr.re.etri.tpl.diagram.listener.IErrorListener;
import kr.re.etri.tpl.diagram.util.MarkerLogger;
import kr.re.etri.tpl.diagram.util.MarkerProvider;
import kr.re.etri.tpl.taskmodel.EnumItemElement;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.ModelDiagram;
import kr.re.etri.tpl.taskmodel.ModelElement;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.Tree;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.util.EList;

/**
 * 로봇 스크립트 파싱 유틸 클래스
 * @author DaeHyung Lee
 *
 */
public class ParserUtil {

	/**
	 * 스크립트 내용을 파싱하여 IncludedElement를 생성한다.
	 * @param stream 스크립트 파일 내용
	 * @return 파싱된 모델
	 */
	public static ModelDiagram parseModel(IFile file, InputStream stream, IErrorListener listener) {

		ScriptParser parser = new ScriptParser(file);
		if (listener instanceof MarkerLogger) {
			((MarkerLogger)listener).clearProblem();
		}
		parser.addErrorListener(listener);

		ModelDiagram modelDiagram = parser.parse(stream);
		return modelDiagram;
	}

	public static ModelDiagram parseModel(IFile file, IErrorListener listener) {

		ScriptParser parser = new ScriptParser(file);
//		MarkerLogger markLogger = new MarkerLogger(file, MarkerLogger.TYPE_SCRIPT);
//		markLogger.clearProblem();
		if (listener instanceof MarkerLogger) {
			((MarkerLogger)listener).clearProblem();
		}
		parser.addErrorListener(listener);
		
		ModelDiagram modelDiagram = parser.parse();
		return modelDiagram;
	}

	/**
	 * 입력받은 IncludedElement의 item 중 같은 이름이 존재하는가를 검사하는 메서드
	 * @param list ItemElement 목록을 가지고 있는 EList 객체
	 * @param clz 검사 대상이 되는 ItemElement를 상속받은 클래스 (예) EnumElement
	 * @param name 존재를 검사할 이름
	 * @return 존재한다면 true, 존재하지 않으면 false
	 */
	@SuppressWarnings("unchecked")
	public static boolean isExistItemName(EList list, Class clz, String name){

		Iterator<ItemElement> iter = list.iterator();
		while(iter.hasNext()){
			Object obj =iter.next();

			if( clz.isAssignableFrom(obj.getClass())){

				ItemElement item = (ItemElement)clz.cast(obj);
				if(item.getName().equals(name))
					return true;
				else
					continue;
			}

		}

		return false;

	}


	/**
	 * 입력받은 IncludedElement의 item 중 같은 이름이 존재하는가를 검사하는 메서드
	 * @param list ItemElement 목록을 가지고 있는 EList 객체
	 * @param name 존재를 검사할 이름
	 * @return 존재한다면 true, 존재하지 않으면 false
	 */
	@SuppressWarnings("unchecked")
	public static boolean isExistItemName(EList list, String name){
		if(name == null || name.length() == 0) {
			return true;
		}

		Iterator<ItemElement> iter = list.iterator();
		while(iter.hasNext()){
			ItemElement item = iter.next();
			if(item.getName() != null && item.getName().equals(name)) {
				return true;
			}
		}

		return false;

	}

	/**
	 * 입력받은 IncludedElement의 item 중 같은 이름이 존재하는가를 검사하는 메서드
	 * @param list ItemElement 목록을 가지고 있는 EList 객체
	 * @param name 존재를 검사할 이름
	 * @return 존재한다면 ItemElement 반환, 존재하지 않으면 null 반환
	 */
	@SuppressWarnings("unchecked")
	public static ItemElement getMatchedItem(EList list, String name){
		if(name == null || name.length() == 0) {
			return null;
		}

		Iterator<ItemElement> iter = list.iterator();
		while(iter.hasNext()){
			ItemElement item = iter.next();
			if(item.getName() != null && item.getName().equals(name)) {
				return item;
			}
		}

		return null;

	}

	/**
	 * 입력받은 IncludedElement의 item 중 같은 이름이 존재하는가를 검사하는 메서드
	 * @param list ModelElement 목록을 가지고 있는 EList 객체
	 * @param clz 검사 대상이 되는 ItemElement를 상속받은 클래스 (예) EnumElement
	 * @param name 존재를 검사할 이름
	 * @param compareLevel 검사 시작 깊이 또는 단계(depth), ModelElement경우
	 *  ModelElement내부에 또 다른 ModelElement가 존재가능 따라서 같은 깊이에 있는 것들만 검사하기 위한 값
	 *  깊이는 최상위가 0이다. 따라서 IncludedElement에서 획득한 ModelElement 또한 0 깊이이다.
	 * @return 존재한다면 true, 존재하지 않으면 false
	 */
	@SuppressWarnings("unchecked")
	public static boolean isExistModelNameOnLevel(EList list,
			String name, int currentLevel, int compareLevel){

		if(currentLevel == compareLevel){
			return isExistItemName(list, ModelElement.class, name);
		}else{

			Iterator<ItemElement> iter = list.iterator();
			while(iter.hasNext()){
				ItemElement childList = iter.next();
				if( childList instanceof ModelElement ){
					ModelElement childModel = (ModelElement)childList;
					boolean result = isExistModelNameOnLevel(childModel.getModels(), name, currentLevel+1, compareLevel);
					if( result )
						return true;
					else
						continue;
				}
			}
			return false;
		}

	}

	/**
	 * 주어진 EnumItemElement 리스트에 Enum 이름이 있는지 검사하는 메서드
	 * @param list EnumItemElement 리스트
	 * @param name 찾으려하는 이름
	 * @return 존재하면 true, 존재하지 않으면 false
	 */
	public static boolean isExistEnumItemElementName(EList<EnumItemElement> list, String name){

		for(EnumItemElement eItemElm: list){

			if( eItemElm.getName().equals(name)){
				return true;
			}
		}
		return false;
	}

	public boolean checkExpression(ModelDiagram model, String expression, IErrorListener listener) throws RecognitionException {

		expression = expression.trim();
		if(expression.length() == 0) {
			return true;
		}

		ANTLRStringStream antlrStream = new ANTLRStringStream(expression);

		RTMLexer lexer = new RTMLexer(antlrStream);

		MarkerProvider logListener = new MarkerProvider();
		logListener.addErrorListener(listener);

		lexer.setErrorListener(logListener);

		RFSMParser.expr_return def;

		CommonTokenStream tokens = new CommonTokenStream(lexer);
		RTMParser parser = new RTMParser(tokens);

		parser.setErrorListener(logListener);

		RTMTokenListener rtmTracer = new RTMTokenListener();
		parser.addTraceListener(rtmTracer);
		def = parser.expr();
		parser.removeTraceListener(rtmTracer);

		RTMTokenVerifier.verify((Tree)def.getTree(), tokens, model, true, logListener);
		logListener.removeErrorListener(listener);

		return logListener.hasError();
	}

	public boolean checkStayAction(ModelDiagram model, String expression, IErrorListener listener) throws RecognitionException {

		expression = expression.trim();
		if(expression.length() == 0) {
			return true;
		}
		ANTLRStringStream antlrStream = new ANTLRStringStream(expression);

		RTMLexer lexer = new RTMLexer(antlrStream);
		lexer.setErrorListener(listener);

		RTMParser.stmt_return def;

		CommonTokenStream tokens = new CommonTokenStream(lexer);
		RTMParser parser = new RTMParser(tokens);
		parser.setErrorListener(listener);

		RTMTokenListener rtmTracer = new RTMTokenListener();
		parser.addTraceListener(rtmTracer);
		def = parser.stmt();
		parser.removeTraceListener(rtmTracer);

		MarkerProvider logListener = new MarkerProvider();
		logListener.addErrorListener(listener);
		RTMTokenVerifier.verify((Tree)def.getTree(), tokens, model, true, logListener);
		logListener.removeErrorListener(listener);

		return logListener.hasError() == false;
	}

	public boolean checkCallExprsssion(ModelDiagram model, String expression, IErrorListener listener) throws RecognitionException {

		expression = expression.trim();
		if(expression.length() == 0) {
			return true;
		}

//		RTMErrorReporter messageReporter = new RTMErrorReporter();
//		messageReporter.addLogListener(listener);

		ANTLRStringStream antlrStream = new ANTLRStringStream(expression);

		RTMLexer lexer = new RTMLexer(antlrStream);
//		lexer.setMessageReporter(messageReporter);
		lexer.setErrorListener(listener);

		RTMParser.stmt_return def;

		CommonTokenStream tokens = new CommonTokenStream(lexer);
		RTMParser parser = new RTMParser(tokens);
//		parser.setMessageReporter(messageReporter);
		parser.setErrorListener(listener);

		RTMTokenListener rtmTracer = new RTMTokenListener();
		parser.addTraceListener(rtmTracer);
		def = parser.stmt();
		parser.removeTraceListener(rtmTracer);
//		messageReporter.removeLogListener(listener);

		MarkerProvider logListener = new MarkerProvider();
		logListener.addErrorListener(listener);
		RTMTokenVerifier.verify((Tree)def.getTree(), tokens, model, true, logListener);
		logListener.removeErrorListener(listener);

		return logListener.hasError();
	}
	
	// KJH 20110210 s, add
	public boolean checkStructBlock(ModelDiagram model, String expression, IErrorListener listener) throws RecognitionException {

		expression = expression.trim();
		if(expression.length() == 0) {
			return true;
		}
		ANTLRStringStream antlrStream = new ANTLRStringStream(expression);

		RTMLexer lexer = new RTMLexer(antlrStream);
		lexer.setErrorListener(listener);

		RTMParser.stmt_return def;

		CommonTokenStream tokens = new CommonTokenStream(lexer);
		RTMParser parser = new RTMParser(tokens);
		parser.setErrorListener(listener);

		RTMTokenListener rtmTracer = new RTMTokenListener();
		parser.addTraceListener(rtmTracer);
		def = parser.stmt();
		parser.removeTraceListener(rtmTracer);

		MarkerProvider logListener = new MarkerProvider();
		logListener.addErrorListener(listener);
		RTMTokenVerifier.verify((Tree)def.getTree(), tokens, model, true, logListener);
		logListener.removeErrorListener(listener);

		return logListener.hasError() == false;
	}
	// KJH 20110210 e, add
}
