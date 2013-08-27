package kr.re.etri.tpl.script.editors.outline;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import kr.re.etri.tpl.grammar.RFSMParser;
import kr.re.etri.tpl.script.grammar.tree.ActionNode;
import kr.re.etri.tpl.script.grammar.tree.BehaviorNode;
import kr.re.etri.tpl.script.grammar.tree.FuncNode;
import kr.re.etri.tpl.script.grammar.tree.ITPLTree;
import kr.re.etri.tpl.script.grammar.tree.ParamNode;
import kr.re.etri.tpl.script.grammar.tree.StateNode;
import kr.re.etri.tpl.script.grammar.tree.TPLBlock;
import kr.re.etri.tpl.script.grammar.tree.TPLTree;
import kr.re.etri.tpl.script.grammar.tree.TranNode;
import kr.re.etri.tpl.script.grammar.tree.VarNode;
import kr.re.etri.tpl.script.utils.TPLTreeUtil;

import org.antlr.runtime.tree.CommonErrorNode;
import org.antlr.runtime.tree.CommonTree;

public class OutlineTreeParser {
	private static OutlineTreeParser treeParser ;
	
	private OutlineTreeParser(){}

	public static ITPLTree creatTree(CommonTree root){
		if(treeParser == null){
			treeParser = new OutlineTreeParser();
		}

		return treeParser.tpl(root);  
	}
	
	
	// KJH 20100517 s, sort
	public class ComparatorImpl implements Comparator<ITPLTree>{
		@Override
		public int compare(ITPLTree o1, ITPLTree o2) {
			String type1 = o1.getType();
			String type2 = o2.getType();
			return 0;
		}
		
	}
	// KJH 20100517 e, sort
	
	public ITPLTree tpl(CommonTree root){
		TPLTree node = new TPLTree();
		node.setIsRoot(true);
		node.setType("tpl");
		node.setName("root");
		int size = root.getChildCount();

		for(int i = 0 ; i < size ; i++){
			CommonTree ct = (CommonTree)root.getChild(i);
			
			if(ct instanceof CommonErrorNode)
				continue;
			
			int cType = ct.getToken().getType();
			
			if(cType == RFSMParser.BEHA){
				node.addChild(behavior(ct, node));
			}
			else if(cType == RFSMParser.INCL){
				node.addChild(include(ct,node));
			}
			else if(cType == RFSMParser.ENUM){
				node.addChild(enumm(ct,node));
			}
			else if(cType == RFSMParser.MODEL){
				node.addChild(model(ct,node));
			}
			else if(cType == RFSMParser.WORKER){
				node.addChild(task(ct,node));
			}
		}
		return node;
	}	

	public ITPLTree task(CommonTree root , ITPLTree parent){
		TPLBlock node = new TPLBlock();
		node.setType(ITPLTree.WORKER);
		node.setParent(parent);
		node.setBlockStart(root.getLine());
		int size = root.getChildCount();
		
		for(int i=0; i<size; i++){
			CommonTree ct = (CommonTree)root.getChild(i);
			
			if(ct instanceof CommonErrorNode)
				continue;
			
			int cType = ct.getToken().getType();
			
			if(cType == RFSMParser.NAME){
				node.setName(TPLTreeUtil.gets((CommonTree)ct.getChild(0)));
				node.setLine(ct.getLine());
				node.setCharPosition(ct.getCharPositionInLine());
			}
			else if(cType == RFSMParser.DESC){
				
			}
			else if(cType == RFSMParser.TASK){
				TPLTree child = new TPLTree();
				child.setType(ITPLTree.TASK);
				child.setParent(node);
				child.setName(TPLTreeUtil.gets((CommonTree)ct.getChild(0)));
				child.setLine(ct.getLine());
				child.setCharPosition(ct.getCharPositionInLine());
				node.addChild(child);
			}
			else if(cType == RFSMParser.EOL){
				node.setBlockEnd(ct.getLine());
			}
		}
		
//		node.setLine(root.getLine());
//		node.setCharPosition(root.getCharPositionInLine());
		return node;
	}


	public ITPLTree include(CommonTree root , ITPLTree parent){
		TPLBlock node = new TPLBlock();
		node.setType(ITPLTree.INCLUDE);
		node.setParent(parent);
		String name = TPLTreeUtil.gets((CommonTree)root.getChild(0).getChild(0));
		int pos = root.getCharPositionInLine();
		if(name.startsWith("\"")){
			name = name.substring(1, name.length()-1);
			pos ++;
		}
		node.setName(name);
		node.setLine(root.getLine());
		node.setCharPosition(pos);
		node.setBlockStart(root.getLine());
		node.setBlockEnd(root.getChild(0).getChild(0).getLine());

		return node;
	}


	public ITPLTree enumm(CommonTree root , ITPLTree parent){
		TPLBlock node = new TPLBlock();
		node.setType(ITPLTree.ENUM);
		node.setParent(parent);	
		node.setBlockStart(root.getLine());
		int size = root.getChildCount();
		
		for(int i=0; i<size; i++){
			CommonTree ct = (CommonTree)root.getChild(i);
			
			if(ct instanceof CommonErrorNode)
				continue;
			
			int cType = ct.getToken().getType();
			
			if(cType == RFSMParser.NAME){
				node.setName(TPLTreeUtil.gets((CommonTree)ct.getChild(0)));
				node.setLine(ct.getLine());
				node.setCharPosition(ct.getCharPositionInLine());
			}
			else if(cType == RFSMParser.ELEM){
				node.addChild(element(ct, node));
			}
			else if(cType == RFSMParser.EOL){
				node.setBlockEnd(ct.getLine());
			}
		}

//		node.setLine(root.getLine());
//		node.setCharPosition(root.getCharPositionInLine());
		return node;
	}
	
	// KJH 20100514 s, Enum\Element 추가
	public ITPLTree element(CommonTree root, ITPLTree parent){
		TPLTree node = new TPLTree();
		node.setType(ITPLTree.ELEM);
		node.setParent(parent);
		node.setName(TPLTreeUtil.gets((CommonTree)root.getChild(0)));
		node.setLine(root.getLine());
		node.setCharPosition(root.getCharPositionInLine());
		return node;
	}
	// KJH 20100514 e, Enum\Element 추가
	
//	public ITPLTree model(CommonTree root , ITPLTree parent){
//		TPLTree node = new TPLTree();
//		node.setType(ITPLTree.MODEL);
//		node.setParent(parent);	
//		node.setName(TPLTreeUtil.getFirstLeafValue(root));
//		node.setLine(root.getLine());
//		node.setCharPosition(root.getCharPositionInLine());
//		return node;
//	}
	
	public TPLTree model(CommonTree root , TPLTree parent){
		TPLBlock node = new TPLBlock();
		node.setType(ITPLTree.MODEL);
		node.setParent(parent);	
		node.setBlockStart(root.getLine());
		int size = root.getChildCount();
		for(int i=0; i< size ; i++){
			CommonTree ct = (CommonTree)root.getChild(i);
			
			if(ct instanceof CommonErrorNode)
				continue;
			
			int cType = ct.getToken().getType();

			if(cType == RFSMParser.NAME){
				node.setName(TPLTreeUtil.gets((CommonTree)ct.getChild(0)));
				node.setLine(ct.getLine());
				node.setCharPosition(ct.getCharPositionInLine());

			}
			else if(cType == RFSMParser.VAR){
				node.addChild(var(ct,node));
			}
			else if(cType == RFSMParser.FUNC){
				node.addChild(func(ct,node));
			}
			else if(cType == RFSMParser.MODEL){
				node.addChild(model(ct, node));
			}
			else if(cType == RFSMParser.EOL){
				node.setBlockEnd(ct.getLine());
			}
		}		
		return node;
	}
	

	// KJH 20100514 s, model\ 추가
	public TPLTree var(CommonTree root , TPLTree parent){
		VarNode node = new VarNode();
		node.setType(ITPLTree.VAR);
		node.setParent(parent);
		int size = root.getChildCount();
		
		for(int i=0; i<size; i++){
			CommonTree ct = (CommonTree)root.getChild(i);
			
			if(ct instanceof CommonErrorNode)
				continue;
			
			int cType = ct.getToken().getType();

			if(cType == RFSMParser.VMOD){
				node.setVMod(TPLTreeUtil.gets((CommonTree)ct.getChild(0)));
			}
			else if(cType == RFSMParser.TYPE){
				node.setVType(TPLTreeUtil.gets((CommonTree)ct.getChild(0)));
			}
			else if(cType == RFSMParser.NAME){
				node.setName(TPLTreeUtil.gets((CommonTree)ct.getChild(0)));
				node.setLine(ct.getLine());
				node.setCharPosition(ct.getCharPositionInLine());
			}
		}
		
		return node;
	}
	

	public TPLTree func(CommonTree root, TPLTree parent){
		FuncNode node = new FuncNode();
		node.setType(ITPLTree.FUNC);
		node.setParent(parent);	
		int size = root.getChildCount();
		for(int i =0; i <size ; i++){
			CommonTree ct = (CommonTree)root.getChild(i);
			
			if(ct instanceof CommonErrorNode)
				continue;
			
			int cType = ct.getToken().getType();

			if(cType == RFSMParser.ACTION){
				node.setAction(ct.getChild(0).getText());
			}
			else if(cType == RFSMParser.RTN){
				node.setRtn(TPLTreeUtil.gets((CommonTree)ct.getChild(0)));
			}
			else if(cType == RFSMParser.NAME){
				node.setName(TPLTreeUtil.gets((CommonTree)ct.getChild(0)));
				node.setLine(ct.getLine());
				node.setCharPosition(ct.getCharPositionInLine());
			}
			else if(cType == RFSMParser.PARM){
				node.addChild(param(ct, node));
			}
		}
		
		return node;		
	}
	
	public ITPLTree param(CommonTree root, ITPLTree parent){
		ParamNode node = new ParamNode();
		node.setType(ITPLTree.PARAM);
		node.setParent(parent);
		int size = root.getChildCount();
		
		for(int i=0; i<size; i++){
			CommonTree ct = (CommonTree)root.getChild(i);
			
			if(ct instanceof CommonErrorNode)
				continue;
			
			int cType = ct.getToken().getType();
			
			if(cType == RFSMParser.TYPE){
				node.setDataType(TPLTreeUtil.gets((CommonTree)ct.getChild(0)));
			}
			else if(cType == RFSMParser.NAME){
				node.setName(TPLTreeUtil.gets((CommonTree)ct.getChild(0)));
				node.setLine(ct.getLine());
				node.setCharPosition(ct.getCharPositionInLine());
			}
		}
		
		return node;
	}
	// KJH 20100514 e, model\ 추가

	public ITPLTree behavior(CommonTree root, ITPLTree parent){
		BehaviorNode node = new BehaviorNode();
		node.setType(ITPLTree.BEHAVIOR);
		node.setParent(parent);
		node.setBlockStart(root.getLine());
		int size = root.getChildCount();

		for(int i =0; i <size ; i++){
			CommonTree ct = (CommonTree)root.getChild(i);
			
			if(ct instanceof CommonErrorNode)
				continue;
			
			int cType = ct.getToken().getType();

			if(cType == RFSMParser.TNAME){
				node.setName(TPLTreeUtil.gets((CommonTree)ct.getChild(0)));
				node.setLine(ct.getLine());
				node.setCharPosition(ct.getCharPositionInLine());
			}
			else if(cType == RFSMParser.STATES){
				for(Object c : ct.getChildren()){
					CommonTree ctt = (CommonTree)c;

					if(ctt.getToken().getType() == RFSMParser.STATE){
						node.addChild(state(ctt, node));
					}
				}				
			}
			// KJH 20100507 s, behavior에 param추가
			else if(cType == RFSMParser.PARMS){
				for(Object c : ct.getChildren()){
					CommonTree ctt = (CommonTree)c;

					if(ct instanceof CommonErrorNode)
						continue;
					
					if(ctt.getToken().getType() == RFSMParser.PARM){
						String name = "";
						String type = "";
						for(Object cc : ctt.getChildren()){
							CommonTree cttt = (CommonTree)cc;

							if(cttt.getToken().getType() == RFSMParser.TYPE)
								type = TPLTreeUtil.gets((CommonTree)cttt.getChild(0));
							else if(cttt.getToken().getType() == RFSMParser.NAME)
								name = TPLTreeUtil.gets((CommonTree)cttt.getChild(0));
						}

						if(name!="" && type!="")
							node.addParam(name, type);
					}
				}
			}
			// KJH 20100507 e, behavior에 param추가
			// KJH 20100518 s, grammer 변경
			else if(cType == RFSMParser.MEM){
				
			}
			else if(cType == RFSMParser.EOL){
				node.setBlockEnd(ct.getLine());
			}
			// KJH 20100518 e, grammer 변경
		}		
		return node;
	}


	public TPLTree state(CommonTree root, ITPLTree parent){	
		StateNode node = new StateNode();
		node.setType(ITPLTree.STATE);	
		node.setParent(parent);
		node.setBlockStart(root.getLine());
		int size = root.getChildCount();

		for(int i =0 ; i <size ; i++){
			CommonTree ct =(CommonTree)root.getChild(i);
			
			if(ct instanceof CommonErrorNode)
				continue;
			
			int cType = ct.getToken().getType();

			if(cType == RFSMParser.SNAME){
				node.setName(TPLTreeUtil.gets((CommonTree)ct.getChild(0)));
				node.setLine(ct.getLine());
				node.setCharPosition(ct.getCharPositionInLine());
			}

			// KJH 20100331 s, action
			else if(cType == RFSMParser.ACTION){
				node.addChild(action(ct, node));
			}
			// KJH 20100331 e, action
			// KJH 20100408 s, transition
			else if(cType == RFSMParser.TRANS){
				ITPLTree[] trans = transition(ct, node);
				for(int j=0; j<trans.length; j++)
					node.addChild(trans[j]);
			}
			// KJH 20100408 e, transition
			else if(cType == RFSMParser.EOL){
				node.setBlockEnd(ct.getLine());
			}
		}
		return node;
	}

	/*
	 *  KJH 20100331, action
	 */
	public ITPLTree action(CommonTree root, ITPLTree parent){
		ActionNode node = new ActionNode();
		node.setType(ITPLTree.STATEELEM);
		node.setParent(parent);
		int size = root.getChildCount();

		for(int i=0; i<size; i++){
			CommonTree ct = (CommonTree)root.getChild(i);
			
			if(ct instanceof CommonErrorNode)
				continue;
			
			int cType = ct.getToken().getType();

			if(cType == RFSMParser.AMOD){
				CommonTree ctt = (CommonTree)ct.getChild(0);
				node.setName(ctt.getText());
				node.setLine(ctt.getLine());
				node.setCharPosition(ctt.getCharPositionInLine());
			}
			else if(cType == RFSMParser.STMTS){
//				CommonTree ctt = (CommonTree)ct.getChild(0);
//				while(ctt!=null && ctt.getToken().getType() == RFSMParser.STMTS){
//					ctt = (CommonTree)ctt.getChild(0);
//				}
				String body = TPLTreeUtil.gets((CommonTree)ct.getChild(0));
				node.setBody(body);
			}
		}

		return node;
	}

	/*
	 *  KJH 20100408, transition
	 */
	public ITPLTree[] transition(CommonTree root, ITPLTree parent){
		List<ITPLTree> list = new ArrayList<ITPLTree>();
		ITPLTree[] nodes = null;
		int size = root.getChildCount();

		for(int i=0; i<size; i++){
			CommonTree ct = (CommonTree)root.getChild(i);
			
			if(ct instanceof CommonErrorNode)
				continue;
			
			int cType = ct.getToken().getType();

			if(cType == RFSMParser.STMTS){
				list.addAll(Arrays.asList(stmts(ct, parent)));
			}
		}

		// KJH 20100416 s, null이 반환되지 않도록 수정
		nodes = (ITPLTree[])list.toArray(new ITPLTree[list.size()]);
		return nodes!=null ? nodes:new TPLTree[0];
	}

	/*
	 *  KJH 20100510, stmt
	 */
	public ITPLTree[] stmts(CommonTree root, ITPLTree parent){
		List<ITPLTree> list = new ArrayList<ITPLTree>();
		ITPLTree[] nodes = null;
		int size = root.getChildCount();

		for(int i=0; i<size; i++){
			CommonTree ct = (CommonTree)root.getChild(i);
			
			if(ct instanceof CommonErrorNode)
				continue;
			
			int cType = ct.getToken().getType();

			if(cType == RFSMParser.STMTS){
				list.addAll(Arrays.asList(stmts(ct, parent)));
			}
			else if(cType == RFSMParser.STMTBLOCK){
				list.addAll(Arrays.asList(stmts(ct, parent)));
			}
			else if(cType == RFSMParser.IF){
				list.addAll(Arrays.asList(tif(ct, parent)));
			}
			else if(cType == RFSMParser.GOTO){
				list.add(tgoto(ct, parent));
			}
			else if(cType == RFSMParser.IVK){
				list.add(tgoto(ct, parent));
			}
			else if(cType == RFSMParser.PAR){
				list.addAll(Arrays.asList(parallel(ct, parent)));
			}
		}

		// KJH 20100416 s, null이 반환되지 않도록 수정
		nodes = (ITPLTree[])list.toArray(new ITPLTree[list.size()]);
		return nodes!=null ? nodes:new ITPLTree[0];
	}

	// KJH 20100414 s, if
	public ITPLTree[] tif(CommonTree root, ITPLTree parent){
		List<ITPLTree> list = new ArrayList<ITPLTree>();
		ITPLTree[] nodes = null;
		int size = root.getChildCount();
		String cond = "";

		for(int i=0; i<size; i++){
			CommonTree ct = (CommonTree)root.getChild(i);
			
			if(ct instanceof CommonErrorNode)
				continue;
			
			int cType = ct.getToken().getType();

			if(cType == RFSMParser.COND){	// 조건
				cond = TPLTreeUtil.gets((CommonTree)ct.getChild(0));
			}
			else if(cType == RFSMParser.WHENT){	// 참일 경우
				nodes = stmts(ct, parent);

				for(ITPLTree node : nodes){
					if(node instanceof TranNode)
						((TranNode)node).setCond(cond);
				}

				list.addAll(Arrays.asList(nodes));
			}
			else if(cType == RFSMParser.WHENF){	// 거짓일 경우
				list.addAll(Arrays.asList(stmts(ct, parent)));
			}
		}

		nodes = list.toArray(new TPLTree[list.size()]);
		return nodes;
	}
	// KJH 20100414 e, if

	// KJH 20100414 s, goto
	public ITPLTree tgoto(CommonTree root, ITPLTree parent){
		TranNode node = new TranNode();
		node.setParent(parent);
		int size = root.getChildCount();

		for(int i=0; i<size; i++){
			CommonTree ct = (CommonTree)root.getChild(i);
			
			if(ct instanceof CommonErrorNode)
				continue;
			
			int cType = ct.getToken().getType();

			if(cType == RFSMParser.BEHA){	// target이 behavior인 경우
				node.setType(ITPLTree.EXPAND);
				node.setLine(ct.getLine());
				node.setCharPosition(ct.getCharPositionInLine());
				
				String name = TPLTreeUtil.gets((CommonTree)ct.getChild(0));
				int index = name.lastIndexOf("()");
				if(index > -1)
					name = name.substring(0, index);
				node.setName("Call for Behavior (" + name + ")");
				break;
			}
			else if(cType == RFSMParser.STATE){	// target이 state인 경우
				node.setType(ITPLTree.GOTO);
				node.setLine(ct.getLine());
				node.setCharPosition(ct.getCharPositionInLine());
				String name = TPLTreeUtil.gets((CommonTree)ct.getChild(0));
				node.setName("Transition (" + name + ")");
				break;
			}
			else if(cType == RFSMParser.STAY){
				node.setType(ITPLTree.GOTO);
				node.setLine(ct.getLine());
				node.setCharPosition(ct.getCharPositionInLine());
				String name = TPLTreeUtil.gets((CommonTree)ct.getChild(0));
				node.setName("Transition (" + name + ")");
				break;
			}
		}

		return node;
	}
	// KJH 20100414 e, goto 

	// KJH 20100416 s, parallel
	public ITPLTree[] parallel(CommonTree root, ITPLTree parent){
		List<ITPLTree> list = new ArrayList<ITPLTree>();
		TPLTree[] nodes = null;
		int size = root.getChildCount();

		for(int i=0; i<size; i++){
			CommonTree ct = (CommonTree)root.getChild(i);
			
			if(ct instanceof CommonErrorNode)
				continue;
			
			int cType = ct.getToken().getType();

			if(cType == RFSMParser.WITH){
				list.addAll(Arrays.asList(transition(ct, parent)));
			}
		}

		nodes = list.toArray(new TPLTree[list.size()]);
		return nodes;
	}
	// KJH 20100416 e, parallel


}
