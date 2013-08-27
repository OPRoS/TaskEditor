package kr.re.etri.tpl.script.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import kr.re.etri.tpl.grammar.RFSMLexer;
import kr.re.etri.tpl.grammar.RFSMParser;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTree;
import org.apache.log4j.Logger;

public class TPLAntlrUtil {
	private static final Logger logger = Logger.getLogger(TPLAntlrUtil.class);
	public static CommonTree makeCommonTree(File f){
		try {
			ANTLRInputStream input = new ANTLRInputStream(new FileInputStream(f));
	
			RFSMLexer lexer = new RFSMLexer(input);
			CommonTokenStream tokens = new CommonTokenStream(lexer);
			
			RFSMParser parser = new RFSMParser(tokens);		
			RFSMParser.rfsm_return ret = parser.rfsm();	
			CommonTree tree = (CommonTree) ret.getTree();			
			return tree;
		} catch (IOException e1) {
			logger.warn("Can not create ANTLRInputStream.");
		} catch (RecognitionException e) {
			logger.warn(e.getMessage());
		}catch(Throwable t){
			logger.warn(t.getMessage());
		}
		return null;
	}
	
	public static CommonTree makeCommonTree(String text){
		try {
			ANTLRInputStream input = new ANTLRInputStream(new ByteArrayInputStream(text.getBytes()));
	
			RFSMLexer lexer = new RFSMLexer(input);
			CommonTokenStream tokens = new CommonTokenStream(lexer);
			RFSMParser parser = new RFSMParser(tokens);		
			RFSMParser.rfsm_return ret = parser.rfsm();	
			CommonTree tree = (CommonTree) ret.getTree();			
			return tree;
		} catch (IOException e1) {
			logger.warn("Can not create ANTLRInputStream.");
		} catch (RecognitionException e) {
			logger.warn(e.getMessage());
		}
		return null;
	}

}
