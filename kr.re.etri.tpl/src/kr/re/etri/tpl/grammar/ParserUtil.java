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
 * �κ� ��ũ��Ʈ �Ľ� ��ƿ Ŭ����
 * @author DaeHyung Lee
 *
 */
public class ParserUtil {

	/**
	 * ��ũ��Ʈ ������ �Ľ��Ͽ� IncludedElement�� �����Ѵ�.
	 * @param stream ��ũ��Ʈ ���� ����
	 * @return �Ľ̵� ��
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
	 * �Է¹��� IncludedElement�� item �� ���� �̸��� �����ϴ°��� �˻��ϴ� �޼���
	 * @param list ItemElement ����� ������ �ִ� EList ��ü
	 * @param clz �˻� ����� �Ǵ� ItemElement�� ��ӹ��� Ŭ���� (��) EnumElement
	 * @param name ���縦 �˻��� �̸�
	 * @return �����Ѵٸ� true, �������� ������ false
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
	 * �Է¹��� IncludedElement�� item �� ���� �̸��� �����ϴ°��� �˻��ϴ� �޼���
	 * @param list ItemElement ����� ������ �ִ� EList ��ü
	 * @param name ���縦 �˻��� �̸�
	 * @return �����Ѵٸ� true, �������� ������ false
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
	 * �Է¹��� IncludedElement�� item �� ���� �̸��� �����ϴ°��� �˻��ϴ� �޼���
	 * @param list ItemElement ����� ������ �ִ� EList ��ü
	 * @param name ���縦 �˻��� �̸�
	 * @return �����Ѵٸ� ItemElement ��ȯ, �������� ������ null ��ȯ
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
	 * �Է¹��� IncludedElement�� item �� ���� �̸��� �����ϴ°��� �˻��ϴ� �޼���
	 * @param list ModelElement ����� ������ �ִ� EList ��ü
	 * @param clz �˻� ����� �Ǵ� ItemElement�� ��ӹ��� Ŭ���� (��) EnumElement
	 * @param name ���縦 �˻��� �̸�
	 * @param compareLevel �˻� ���� ���� �Ǵ� �ܰ�(depth), ModelElement���
	 *  ModelElement���ο� �� �ٸ� ModelElement�� ���簡�� ���� ���� ���̿� �ִ� �͵鸸 �˻��ϱ� ���� ��
	 *  ���̴� �ֻ����� 0�̴�. ���� IncludedElement���� ȹ���� ModelElement ���� 0 �����̴�.
	 * @return �����Ѵٸ� true, �������� ������ false
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
	 * �־��� EnumItemElement ����Ʈ�� Enum �̸��� �ִ��� �˻��ϴ� �޼���
	 * @param list EnumItemElement ����Ʈ
	 * @param name ã�����ϴ� �̸�
	 * @return �����ϸ� true, �������� ������ false
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
