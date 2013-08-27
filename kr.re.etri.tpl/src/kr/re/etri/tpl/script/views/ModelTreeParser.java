package kr.re.etri.tpl.script.views;

import kr.re.etri.tpl.grammar.RFSMParser;
import kr.re.etri.tpl.script.grammar.tree.FuncNode;
import kr.re.etri.tpl.script.grammar.tree.ITPLTree;
import kr.re.etri.tpl.script.grammar.tree.TPLTree;
import kr.re.etri.tpl.script.grammar.tree.VarNode;

import org.antlr.runtime.Token;
import org.antlr.runtime.tree.CommonTree;
import org.apache.log4j.Logger;

public class ModelTreeParser {
	private static Logger logger = Logger.getLogger(ModelTreeParser.class);
	private static ModelTreeParser parser ; 
	private ModelTreeParser(){
	}
	public static ModelTreeParser createParser(){
		if(parser == null){
			parser =  new ModelTreeParser();
			return parser;
		}
		return parser;
	}
	
	public TPLTree parseTplTree(CommonTree root, TPLTree parent,int level){		
		logger.debug(root);
		int size = root.getChildCount();			
		for(int i =0 ; i < size ; i++){
			CommonTree ct = (CommonTree)root.getChild(i);
			Token token = ct.getToken();
			if(token == null){
				logger.info("Token is null.");
				return parent;
			}
			if(token.getType() == RFSMParser.MODEL){
				parent.addChild(model(ct,parent));
			}
			else if(level ==0 && token.getType() == RFSMParser.INCL){
				parent.addChild(include(ct,parent));
			}
		}		
		return parent;
	}	
	

	public TPLTree include(CommonTree root , TPLTree parent){
		logger.debug(root);
		TPLTree node = new TPLTree();
		node.setType(ITPLTree.INCLUDE);
		node.setParent(parent);	
		node.setName(root.getChild(0).getChild(0).getText());
		node.setLine(root.getLine());
		node.setCharPosition(root.getCharPositionInLine());
		
		return node;
	}
	

	public TPLTree model(CommonTree root , TPLTree parent){
		logger.debug(root);
		TPLTree node = new TPLTree();
		node.setType(ITPLTree.MODEL);
		node.setParent(parent);	
		node.setName(root.getChild(0).getChild(0).getChild(0).getText());
		node.setLine(root.getLine());
		node.setCharPosition(root.getCharPositionInLine());
		int size = root.getChildCount();
		for(int i=1; i< size ; i++){
			CommonTree t = (CommonTree)root.getChild(i);
			if(t.getToken().getType() == RFSMParser.VAR){
				node.addChild(var(t,node));
			}
			else if(t.getToken().getType() == RFSMParser.FUNC){
				node.addChild(func(t,node));				
			}
		}		
		return node;
	}
	

	public TPLTree var(CommonTree root , TPLTree parent){
		VarNode node = new VarNode();
		node.setType(ITPLTree.VAR);
		node.setParent(parent);	
		node.setName(root.getChild(2).getChild(0).getChild(0).getText());
		node.setLine(root.getLine());
		node.setCharPosition(root.getCharPositionInLine());
		node.setVMod(root.getChild(0).getChild(0).getText());
		node.setVType(root.getChild(1).getChild(0).getChild(0).getText());
		return node;
	}
	

	public TPLTree func(CommonTree root, TPLTree parent){
		FuncNode node = new FuncNode();
		node.setType(ITPLTree.FUNC);
		node.setParent(parent);	
		int size = root.getChildCount();
		for(int i =0; i <size ; i++){
			CommonTree child = (CommonTree)root.getChild(i);
			if(child.getToken().getText().equals("ACTION")){
				node.setAction(child.getChild(0).getText());
			}
			else if(child.getToken().getText().equals("RTN")){
				node.setRtn(child.getChild(0).getChild(0).getText());
			}
			else if(child.getToken().getText().equals("NAME")){
				node.setName(child.getChild(0).getChild(0).getText());
			}
		}
		node.setLine(root.getLine());
		node.setCharPosition(root.getCharPositionInLine());
		return node;		
	}
	

}
