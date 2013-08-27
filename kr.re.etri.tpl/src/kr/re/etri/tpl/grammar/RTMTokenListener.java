package kr.re.etri.tpl.grammar;


import java.util.Stack;

import kr.re.etri.tpl.diagram.ModelManager;
import kr.re.etri.tpl.diagram.listener.ITraceListener;
import kr.re.etri.tpl.diagram.util.ItemState;
import kr.re.etri.tpl.script.utils.TPLTreeUtil;
import kr.re.etri.tpl.taskmodel.ActionElement;
import kr.re.etri.tpl.taskmodel.BehaviorElement;
import kr.re.etri.tpl.taskmodel.ConnectorElement;
import kr.re.etri.tpl.taskmodel.Constant;
import kr.re.etri.tpl.taskmodel.Direction;
import kr.re.etri.tpl.taskmodel.EnumElement;
import kr.re.etri.tpl.taskmodel.EnumItemElement;
import kr.re.etri.tpl.taskmodel.Function;
import kr.re.etri.tpl.taskmodel.IncludedElement;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.ModelDiagram;
import kr.re.etri.tpl.taskmodel.ModelElement;
import kr.re.etri.tpl.taskmodel.Parameter;
import kr.re.etri.tpl.taskmodel.StateAction;
import kr.re.etri.tpl.taskmodel.StateActionType;
import kr.re.etri.tpl.taskmodel.StateAttribute;
import kr.re.etri.tpl.taskmodel.StateElement;
import kr.re.etri.tpl.taskmodel.StructBlockElement;
import kr.re.etri.tpl.taskmodel.StructType;
import kr.re.etri.tpl.taskmodel.Symbol;
import kr.re.etri.tpl.taskmodel.TaskElement;
import kr.re.etri.tpl.taskmodel.TaskModelFactory;
import kr.re.etri.tpl.taskmodel.TaskModelPackage;
import kr.re.etri.tpl.taskmodel.WithElement;

import org.antlr.runtime.Parser;
import org.antlr.runtime.ParserRuleReturnScope;
import org.antlr.runtime.Token;
import org.antlr.runtime.tree.CommonErrorNode;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.Tree;

public class RTMTokenListener implements ITraceListener {
	private Stack<StackNode> stack = new Stack<StackNode>();
	private TaskModelFactory factory;
	private StackNode current;
	
	private INotifyListener notifyListener;
	
	protected class StackNode {
		public ItemElement model;
		public int modelType;
		
		public StackNode(int modelType, ItemElement model) {
			this.model = model;
			this.modelType = modelType;
		}
	}

	public RTMTokenListener() {
	}

	public RTMTokenListener(INotifyListener listener) {
		notifyListener = listener;
	}

	public TaskModelFactory getFactory() {
		if(factory == null) {
			factory = ModelManager.getFactory();
		}
		
		return factory;
	}
	
	public ItemElement getCurrentElement() {
		if(current == null) {
			return null;
		}
		return current.model;
	}

	@Override
	public void enterRule(int rule, Parser parser, ParserRuleReturnScope returnScope){
		
		switch(rule) {
		case TaskModelPackage.MODEL_DIAGRAM:
			enterModelDiagram(rule, parser, returnScope);
			break;
		case TaskModelPackage.INCLUDED_ELEMENT:
			enterIncludedElement(rule, parser, returnScope);
			break;
		case TaskModelPackage.ENUM_ELEMENT:
			enterEnumElement(rule, parser, returnScope);
			break;
		case TaskModelPackage.MODEL_ELEMENT:
			enterModelElement(rule, parser, returnScope);
			break;
		case TaskModelPackage.FUNCTION:
			enterFunction(rule, parser, returnScope);
			break;
		case TaskModelPackage.CONSTANT:
			enterConstant(rule, parser, returnScope);
			break;
		case TaskModelPackage.SYMBOL:
			enterSymbol(rule, parser, returnScope);
			break;
		case TaskModelPackage.ACTION_ELEMENT:
			enterActionElement(rule, parser, returnScope);
			break;
		case TaskModelPackage.WORKER_ELEMENT:
			enterWorkerElement(rule, parser, returnScope);
			break;
		case TaskModelPackage.TASK_ELEMENT:
			enterTaskElement(rule, parser, returnScope);
			break;
		case TaskModelPackage.STATE_ELEMENT:
			enterStateElement(rule, parser, returnScope);
			break;
		case TaskModelPackage.STATE_ACTION:
			enterStateAction(rule, parser, returnScope);
			break;
		case TaskModelPackage.ENUM_ITEM_ELEMENT:
			enterEnumItem(rule, parser, returnScope);
			break;
		case TaskModelPackage.PARAMETER:
			enterParameter(rule, parser, returnScope);
			break;
//		case TaskModelPackage.RELATION_SHIP:	// KJH 20101018, transition
//			enterTransition(rule, parser, returnScope);
//			break;
		case TaskModelPackage.CONNECTOR_ELEMENT:	// KJH 20110208, connector
			enterConnector(rule, parser, returnScope);
			break;
		case TaskModelPackage.STRUCT_BLOCK_ELEMENT:	// KJH 20110209, structblock
			enterStructBlock(rule, parser, returnScope);
			break;
//		case TaskModelPackage.EXPAND_TRANS_ELEMENT:
//			enterExpandTrans(rule, parser, returnScope);
//			break;
		case TaskModelPackage.WITH_ELEMENT:
			enterWithElement(rule, parser, returnScope);
			break;
		}
	}

	@Override
	public void exitRule(int rule, Parser parser, ParserRuleReturnScope returnScope){
		
		switch(rule) {
		case TaskModelPackage.MODEL_DIAGRAM:
			exitModelDiagram(rule, parser, returnScope);
			break;
		case TaskModelPackage.INCLUDED_ELEMENT:
			exitIncludedElement(rule, parser, returnScope);
			break;
		case TaskModelPackage.ENUM_ELEMENT:
			exitEnumElement(rule, parser, returnScope);
			break;
		case TaskModelPackage.MODEL_ELEMENT:
			exitModelElement(rule, parser, returnScope);
			break;
		case TaskModelPackage.FUNCTION:
			exitFunction(rule, parser, returnScope);
			break;
		case TaskModelPackage.CONSTANT:
			exitConstant(rule, parser, returnScope);
			break;
		case TaskModelPackage.SYMBOL:
			exitSymbol(rule, parser, returnScope);
			break;
		case TaskModelPackage.ACTION_ELEMENT:
			exitActionElement(rule, parser, returnScope);
			break;
		case TaskModelPackage.WORKER_ELEMENT:
			exitWorkerElement(rule, parser, returnScope);
			break;
		case TaskModelPackage.TASK_ELEMENT:
			exitTaskElement(rule, parser, returnScope);
			break;
		case TaskModelPackage.STATE_ELEMENT:
			exitStateElement(rule, parser, returnScope);
			break;
		case TaskModelPackage.STATE_ACTION:
			exitStateAction(rule, parser, returnScope);
			break;
		case TaskModelPackage.ENUM_ITEM_ELEMENT:
			exitEnumItem(rule, parser, returnScope);
			break;
		case TaskModelPackage.PARAMETER:
			exitParameter(rule, parser, returnScope);
			break;
//		case TaskModelPackage.RELATION_SHIP:	// KJH 20101018, transition
//			exitTransition(rule, parser, returnScope);
//			break;
		case TaskModelPackage.CONNECTOR_ELEMENT:	// KJH 20110208, connector
			exitConnector(rule, parser, returnScope);
			break;
		case TaskModelPackage.STRUCT_BLOCK_ELEMENT:	// KJH 20110209, construct, destruct
			exitStructBlock(rule, parser, returnScope);
			break;
//		case TaskModelPackage.EXPAND_TRANS_ELEMENT:
//			exitExpandTrans(rule, parser, returnScope);
//			break;
		case TaskModelPackage.WITH_ELEMENT:
			exitWithElement(rule, parser, returnScope);
			break;
		}
	}

	@Override
	public void enterSubRule(int subRule, Parser parser, ParserRuleReturnScope returnScope){
//		if (RelationShip.get(subRule) == RelationShip.TRANSITION) {	// KJH 20101018, transition
//			enterSubTransition(subRule, parser, returnScope);
//		}
	}

	@Override
	public void exitSubRule(int subRule, Parser parser, ParserRuleReturnScope returnScope){
//		if (RelationShip.get(subRule) == RelationShip.TRANSITION) {	// KJH 20101018, transition
//			exitSubTransition(subRule, parser, returnScope);
//		}
	}

	protected void enterModelDiagram(int rule, Parser parser, ParserRuleReturnScope returnScope) {
		current = new StackNode(rule, getFactory().createModelDiagram());
		stack.push(current);
	}

	protected void enterIncludedElement(int rule, Parser parser, ParserRuleReturnScope returnScope) {
		current = new StackNode(rule, getFactory().createIncludedElement());
		stack.push(current);
	}

	protected void enterEnumElement(int rule, Parser parser, ParserRuleReturnScope returnScope) {
		current = new StackNode(rule, getFactory().createEnumElement());
		stack.push(current);
	}

	protected void enterModelElement(int rule, Parser parser, ParserRuleReturnScope returnScope) {
		current = new StackNode(rule, getFactory().createModelElement());
		stack.push(current);
	}

	protected void enterFunction(int rule, Parser parser, ParserRuleReturnScope returnScope) {
		current = new StackNode(rule, getFactory().createFunction());
		stack.push(current);
	}

	protected void enterConstant(int rule, Parser parser, ParserRuleReturnScope returnScope) {
		current = new StackNode(rule, getFactory().createConstant());
		stack.push(current);
	}

	protected void enterSymbol(int rule, Parser parser, ParserRuleReturnScope returnScope) {
		current = new StackNode(rule, getFactory().createSymbol());
		stack.push(current);
	}

	protected void enterActionElement(int rule, Parser parser, ParserRuleReturnScope returnScope) {
		current = new StackNode(rule, getFactory().createActionElement());
		stack.push(current);
	}

	protected void enterWorkerElement(int rule, Parser parser, ParserRuleReturnScope returnScope) {
		current = new StackNode(rule, getFactory().createWorkerElement());
		stack.push(current);
	}

	protected void enterTaskElement(int rule, Parser parser, ParserRuleReturnScope returnScope) {
		current = new StackNode(rule, getFactory().createTaskElement());
		stack.push(current);
	}

	protected void enterStateElement(int rule, Parser parser, ParserRuleReturnScope returnScope) {
		current = new StackNode(rule, getFactory().createStateElement());
		stack.push(current);
	}

	protected void enterStateAction(int rule, Parser parser, ParserRuleReturnScope returnScope) {
		current = new StackNode(rule, getFactory().createStateAction());
		stack.push(current);
	}

	protected void enterEnumItem(int rule, Parser parser, ParserRuleReturnScope returnScope) {
		current = new StackNode(rule, getFactory().createEnumItemElement());
		stack.push(current);
	}

	protected void enterParameter(int rule, Parser parser, ParserRuleReturnScope returnScope) {
		current = new StackNode(rule, getFactory().createParameter());
		stack.push(current);
	}

	// KJH 20101018 s, transition
	protected void enterTransition(int rule, Parser parser, ParserRuleReturnScope returnScope) {
		current = new StackNode(rule, getFactory().createConnectionElement());
		stack.push(current);
	}
	// KJH 20101018 e, transition

	// KJH 20110208 s, connector
	protected void enterConnector(int rule, Parser parser, ParserRuleReturnScope returnScope) {
		current = new StackNode(rule, getFactory().createConnectorElement());
		stack.push(current);
	}
	// KJH 20110208 e, connector
	
	// KJH 20110209 s, structblock
	protected void enterStructBlock(int rule, Parser parser, ParserRuleReturnScope returnScope) {
		current = new StackNode(rule, getFactory().createStructBlockElement());
		stack.push(current);
	}
	// KJH 20110209 e, structblock
	
	/**
	 * KJH 20110707, Expand & Trans
	 */
	protected void enterExpandTrans(int rule, Parser parser, ParserRuleReturnScope returnScope) {
		current = new StackNode(rule, getFactory().createExpandTransElement());
		stack.push(current);
	}

	/**
	 * KJH 20110707, WithElement
	 */
	protected void enterWithElement(int rule, Parser parser, ParserRuleReturnScope returnScope) {
		current = new StackNode(rule, getFactory().createWithElement());
		stack.push(current);
	}

	// TODO exit
	protected void exitModelDiagram(int rule, Parser parser, ParserRuleReturnScope returnScope) {
		current = stack.pop();
	}
	
	protected void exitIncludedElement(int rule, Parser parser, ParserRuleReturnScope returnScope) {
		current = stack.pop();
		if(stack.size() > 0) {
			StackNode beforeNode = stack.peek();
	
			ItemElement parent = beforeNode.model;
			IncludedElement child = (IncludedElement)current.model;
			// KJH 20110929, itemState
			int itemState = child.getItemState();
			if (itemState == 0) {
				itemState |= ItemState.ITEM_CREATED;
				child.setItemState(itemState);
			}

			Tree tree = (Tree)returnScope.getTree();
			if(tree == null || returnScope.stop == null) {
				return;
			}
			if(tree instanceof CommonErrorNode) {
				return;
			}

			// KJH 20101012, 수정
			for (int i=0; i<tree.getChildCount(); i++) {
				CommonTree ct = (CommonTree)tree.getChild(i);
				if (ct instanceof CommonErrorNode) {	// KJH 20110216 s, script error시 ItemElement 추가 안함
					return;
				}	// KJH 20110216 s, script error시 ItemElement 추가 안함
				int ctype = ct.getToken().getType();
				
				switch (ctype) {
				case RFSMParser.FNAME:
					String filePath = TPLTreeUtil.gets(ct);
					filePath = filePath.substring(1, filePath.length()-1);
					child.setIncludePath(filePath);
					break;
				}
			}

			if(TaskModelPackage.MODEL_DIAGRAM == beforeNode.modelType) {
				((ModelDiagram)parent).getIncludeItems().add(child);
			}
			else if(TaskModelPackage.INCLUDED_ELEMENT == beforeNode.modelType) {
				((IncludedElement)parent).getItems().add(child);
			}
			child.setParent(parent);

			if(notifyListener != null) {
				Notify notify = new Notify(parent, Notify.Type.ADD, child, null);
				if(TaskModelPackage.MODEL_DIAGRAM == beforeNode.modelType) {
					notify.setData(TaskModelPackage.MODEL_DIAGRAM__INCLUDE_ITEMS);
				}
				else if(TaskModelPackage.INCLUDED_ELEMENT == beforeNode.modelType) {
					notify.setData(TaskModelPackage.INCLUDED_ELEMENT__ITEMS);
				}

				notifyListener.notifyChanged(notify);
			}
		}
	}

	protected void exitEnumElement(int rule, Parser parser, ParserRuleReturnScope returnScope) {
		current = stack.pop();
		if(stack.size() > 0) {
			StackNode beforeNode = stack.peek();
	
			ModelDiagram parent = (ModelDiagram)beforeNode.model;
			EnumElement child = (EnumElement)current.model;
			// KJH 20110929, itemState
			int itemState = child.getItemState();
			if (itemState == 0) {
				itemState |= ItemState.ITEM_CREATED;
				child.setItemState(itemState);
			}

			Tree tree = (Tree)returnScope.getTree();
			if(tree == null || returnScope.stop == null) {
				return;
			}
			if(tree instanceof CommonErrorNode) {
				return;
			}
			
			// KJH 20101012, 수정
			for (int i=0; i<tree.getChildCount(); i++) {
				CommonTree ct = (CommonTree)tree.getChild(i);
				if (ct instanceof CommonErrorNode) {	// KJH 20110216 s, script error시 ItemElement 추가 안함
					return;
				}	// KJH 20110216 s, script error시 ItemElement 추가 안함
				int ctype = ct.getToken().getType();
				
				switch (ctype) {
				case RFSMParser.NAME:
					child.setName(TPLTreeUtil.gets(ct));
					break;
				}
			}
	
			parent.getItems().add(child);
			child.setParent(parent);

			if(notifyListener != null) {
				Notify notify = new Notify(parent, Notify.Type.ADD, child, null);
				notify.setData(TaskModelPackage.ENUM_ELEMENT__ENUM_ITEM);

				notifyListener.notifyChanged(notify);
			}
		}
	}

	protected void exitModelElement(int rule, Parser parser, ParserRuleReturnScope returnScope) {
		current = stack.pop();
		if(stack.size() > 0) {
			StackNode beforeNode = stack.peek();
	
			ItemElement parent = beforeNode.model;
			ModelElement child = (ModelElement)current.model;
			// KJH 20110929, itemState
			int itemState = child.getItemState();
			if (itemState == 0) {
				itemState |= ItemState.ITEM_CREATED;
				child.setItemState(itemState);
			}

			Tree tree = (Tree)returnScope.getTree();
			if(tree == null || returnScope.stop == null) {
				return;
			}
			if(tree instanceof CommonErrorNode) {
				return;
			}

			// KJH 20101019, 수정
			for (int i=0; i<tree.getChildCount(); i++) {
				CommonTree ct = (CommonTree)tree.getChild(i);
				if (ct instanceof CommonErrorNode) {	// KJH 20110216 s, script error시 ItemElement 추가 안함
					return;
				}	// KJH 20110216 s, script error시 ItemElement 추가 안함
				int ctype = ct.getToken().getType();
				
				switch (ctype) {
				case RFSMParser.NAME:
					child.setName(TPLTreeUtil.gets(ct));
					break;
				}
			}
	
			if(TaskModelPackage.MODEL_DIAGRAM == beforeNode.modelType) {
				((ModelDiagram)parent).getItems().add(child);
			}
			else if(TaskModelPackage.INCLUDED_ELEMENT == beforeNode.modelType) {
				((IncludedElement)parent).getItems().add(child);
			}
			else if(TaskModelPackage.MODEL_ELEMENT == beforeNode.modelType) {
				((ModelElement)parent).getModels().add(child);
			}
			child.setParent(parent);

			if(notifyListener != null) {
				Notify notify = new Notify(parent, Notify.Type.ADD, child, null);
				if(TaskModelPackage.MODEL_DIAGRAM == beforeNode.modelType) {
					notify.setData(TaskModelPackage.MODEL_DIAGRAM__INCLUDE_ITEMS);
				}
				else if(TaskModelPackage.INCLUDED_ELEMENT == beforeNode.modelType) {
					notify.setData(TaskModelPackage.INCLUDED_ELEMENT__ITEMS);
				}
				else if(TaskModelPackage.MODEL_ELEMENT == beforeNode.modelType) {
					notify.setData(TaskModelPackage.MODEL_ELEMENT__MODELS);
				}

				notifyListener.notifyChanged(notify);
			}
		}
	}

	protected void exitFunction(int rule, Parser parser, ParserRuleReturnScope returnScope) {
		current = stack.pop();
		if(stack.size() > 0) {
			StackNode beforeNode = stack.peek();
	
			ModelElement parent = (ModelElement)beforeNode.model;
			Function child = (Function)current.model;
			// KJH 20110929, itemState
			int itemState = child.getItemState();
			if (itemState == 0) {
				itemState |= ItemState.ITEM_CREATED;
				child.setItemState(itemState);
			}

			Tree tree = (Tree)returnScope.getTree();
			if(tree == null || returnScope.stop == null) {
				return;
			}
			if(tree instanceof CommonErrorNode) {
				return;
			}
			
			// KJH 20101019, modify
			for (int i=0; i<tree.getChildCount(); i++) {
				CommonTree ct = (CommonTree)tree.getChild(i);
				if (ct instanceof CommonErrorNode) {	// KJH 20110216 s, script error시 ItemElement 추가 안함
					return;
				}	// KJH 20110216 s, script error시 ItemElement 추가 안함
				int ctype = ct.getToken().getType();
				
				String type = "";
				switch (ctype) {
				case RFSMParser.ACTION:
					if (type.length() > 0)
						type = " " + type;
					type = TPLTreeUtil.gets(ct) + type;
					child.setType("");
					break;
				case RFSMParser.RTN:
					if (type.length() > 0)
						type += " ";
					type += TPLTreeUtil.gets(ct);
					break;
				case RFSMParser.NAME:
					child.setName(TPLTreeUtil.gets(ct));
					break;
				}
				child.setType(type);
			}
			
			parent.getFunctions().add(child);
			child.setParent(parent);

			if(notifyListener != null) {
				Notify notify = new Notify(parent, Notify.Type.ADD, child, null);
				notify.setData(TaskModelPackage.MODEL_ELEMENT__FUNCTIONS);

				notifyListener.notifyChanged(notify);
			}
		}
	}

	protected void exitConstant(int rule, Parser parser, ParserRuleReturnScope returnScope) {
		current = stack.pop();
		if(stack.size() > 0) {
			StackNode beforeNode = stack.peek();
	
			ModelElement parent = (ModelElement)beforeNode.model;
			Constant child = (Constant)current.model;
			// KJH 20110929, itemState
			int itemState = child.getItemState();
			if (itemState == 0) {
				itemState |= ItemState.ITEM_CREATED;
				child.setItemState(itemState);
			}

			Tree tree = (Tree)returnScope.getTree();
			if(tree == null || returnScope.stop == null) {
				return;
			}
			if(tree instanceof CommonErrorNode) {
				return;
			}
			
			// KJH 20101019, 수정
			for (int i=0; i<tree.getChildCount(); i++) {
				CommonTree ct = (CommonTree)tree.getChild(i);
				if (ct instanceof CommonErrorNode) {	// KJH 20110216 s, script error시 ItemElement 추가 안함
					return;
				}	// KJH 20110216 s, script error시 ItemElement 추가 안함
				int ctype = ct.getToken().getType();
				
				switch (ctype) {
				case RFSMParser.NAME:
					child.setName(TPLTreeUtil.gets(ct));
					break;
				case RFSMParser.TYPE:
					child.setType(TPLTreeUtil.gets(ct));
					break;
//				case RFSMParser.INITVALUE:
//					child.setInitValue(TPLTreeUtil.getFirstLeafValue(ct));
//					Tree initValueTree = tree.getChild(4);
//					String initValue = parser.input.toString(initValueTree.getTokenStartIndex(), initValueTree.getTokenStopIndex());
//					child.setInitValue(initValue);
//					break;
				}
			}
	
			parent.getConstants().add(child);
			child.setParent(parent);

			if(notifyListener != null) {
				Notify notify = new Notify(parent, Notify.Type.ADD, child, null);
				notify.setData(TaskModelPackage.MODEL_ELEMENT__CONSTANTS);

				notifyListener.notifyChanged(notify);
			}
		}
	}

	protected void exitSymbol(int rule, Parser parser, ParserRuleReturnScope returnScope) {
		current = stack.pop();
		if(stack.size() > 0) {
			StackNode beforeNode = stack.peek();
	
			ModelElement parent = (ModelElement)beforeNode.model;
			Symbol child = (Symbol)current.model;
			// KJH 20110929, itemState
			int itemState = child.getItemState();
			if (itemState == 0) {
				itemState |= ItemState.ITEM_CREATED;
				child.setItemState(itemState);
			}

			Tree tree = (Tree)returnScope.getTree();
			if(tree == null || returnScope.stop == null) {
				return;
			}
			if(tree instanceof CommonErrorNode) {
				return;
			}
			
			// KJH 20101019, 수정
			for (int i=0; i<tree.getChildCount(); i++) {
				CommonTree ct = (CommonTree)tree.getChild(i);
				if (ct instanceof CommonErrorNode) {	// KJH 20110216 s, script error시 ItemElement 추가 안함
					return;
				}	// KJH 20110216 s, script error시 ItemElement 추가 안함
				int ctype = ct.getToken().getType();
				
				switch (ctype) {
				case RFSMParser.VMOD:
					Direction dir = Direction.get(TPLTreeUtil.gets(ct));
					if (dir != null) {
						child.setDirection(dir);
					}
					break;
				case RFSMParser.NAME:
					child.setName(TPLTreeUtil.gets(ct));
					break;
				case RFSMParser.TYPE:
					child.setType(TPLTreeUtil.gets(ct));
					break;
				}
			}
	
			parent.getSymbols().add(child);
			child.setParent(parent);

			if(notifyListener != null) {
				Notify notify = new Notify(parent, Notify.Type.ADD, child, null);
				notify.setData(TaskModelPackage.MODEL_ELEMENT__SYMBOLS);

				notifyListener.notifyChanged(notify);
			}
		}
	}

	protected void exitActionElement(int rule, Parser parser, ParserRuleReturnScope returnScope) {
		current = stack.pop();
		if(stack.size() > 0) {
			StackNode beforeNode = stack.peek();
	
			ModelDiagram parent = (ModelDiagram)beforeNode.model;
			ActionElement child = (ActionElement)current.model;
			// KJH 20110929, itemState
			int itemState = child.getItemState();
			if (itemState == 0) {
				itemState |= ItemState.ITEM_CREATED;
				child.setItemState(itemState);
			}

			Tree tree = (Tree)returnScope.getTree();
			if(tree == null || returnScope.stop == null) {
				return;
			}
			if(tree instanceof CommonErrorNode) {
				return;
			}
			
			// KJH 20101019, 수정
			for (int i=0; i<tree.getChildCount(); i++) {
				CommonTree ct = (CommonTree)tree.getChild(i);
				if (ct instanceof CommonErrorNode) {	// KJH 20110216 s, script error시 ItemElement 추가 안함
					return;
				}	// KJH 20110216 s, script error시 ItemElement 추가 안함
				int ctype = ct.getToken().getType();
				
				switch (ctype) {
				case RFSMParser.NAME:
					child.setName(TPLTreeUtil.gets(ct));
					break;
				}
			}
	
			parent.getItems().add(child);
			child.setParent(parent);

			if(notifyListener != null) {
				Notify notify = new Notify(parent, Notify.Type.ADD, child, null);
				if(TaskModelPackage.MODEL_DIAGRAM == beforeNode.modelType) {
					notify.setData(TaskModelPackage.MODEL_DIAGRAM__INCLUDE_ITEMS);
				}
				else if(TaskModelPackage.INCLUDED_ELEMENT == beforeNode.modelType) {
					notify.setData(TaskModelPackage.INCLUDED_ELEMENT__ITEMS);
				}

				notifyListener.notifyChanged(notify);
			}
		}
	}

	protected void exitWorkerElement(int rule, Parser parser, ParserRuleReturnScope returnScope) {
		current = stack.pop();
		if(stack.size() > 0) {
			StackNode beforeNode = stack.peek();
	
			ModelDiagram parent = (ModelDiagram)beforeNode.model;
			TaskElement child = (TaskElement)current.model;
			// KJH 20110929, itemState
			int itemState = child.getItemState();
			if (itemState == 0) {
				itemState |= ItemState.ITEM_CREATED;
				child.setItemState(itemState);
			}

			Tree tree = (Tree)returnScope.getTree();
			if(tree == null || returnScope.stop == null) {
				return;
			}
			if(tree instanceof CommonErrorNode) {
				return;
			}

			for (int i=0; i<tree.getChildCount(); i++) {
				CommonTree ct = (CommonTree)tree.getChild(i);
				if (ct instanceof CommonErrorNode) {	// KJH 20110216 s, script error시 ItemElement 추가 안함
					return;
				}	// KJH 20110216 s, script error시 ItemElement 추가 안함
				int tokenType = ct.getToken().getType();
				
				switch (tokenType) {
				case RFSMParser.NAME:
					child.setName(TPLTreeUtil.gets(ct.getChild(0)));
					break;
				case RFSMParser.VB:	// KJH 20110927
					String stmts = TPLTreeUtil.gets(ct);
					for (String stmt : stmts.split("\r\n")) {
						child.getStatements().add(stmt);
					}
					break;
				}
			}
	
			parent.getItems().add(child);
			child.setParent(parent);

			if(notifyListener != null) {
				Notify notify = new Notify(parent, Notify.Type.ADD, child, null);
				if(TaskModelPackage.MODEL_DIAGRAM == beforeNode.modelType) {
					notify.setData(TaskModelPackage.MODEL_DIAGRAM__INCLUDE_ITEMS);
				}
				else if(TaskModelPackage.INCLUDED_ELEMENT == beforeNode.modelType) {
					notify.setData(TaskModelPackage.INCLUDED_ELEMENT__ITEMS);
				}

				notifyListener.notifyChanged(notify);
			}
		}
	}

	protected void exitTaskElement(int rule, Parser parser, ParserRuleReturnScope returnScope) {
		current = stack.pop();
		if(stack.size() > 0) {
			StackNode beforeNode = stack.peek();
	
			ModelDiagram parent = (ModelDiagram)beforeNode.model;
			BehaviorElement child = (BehaviorElement)current.model;
			// KJH 20110929, itemState
			int itemState = child.getItemState();
			if (itemState == 0) {
				itemState |= ItemState.ITEM_CREATED;
				child.setItemState(itemState);
			}

			Tree tree = (Tree)returnScope.getTree();
			if(tree == null || returnScope.stop == null) {
				return;
			}
			if(tree instanceof CommonErrorNode) {
				return;
			}
			
			// KJH 20101019, 수정
			for (int i=0; i<tree.getChildCount(); i++) {
				CommonTree ct = (CommonTree)tree.getChild(i);
				if (ct instanceof CommonErrorNode) {	// KJH 20110216 s, script error시 ItemElement 추가 안함
					return;
				}	// KJH 20110216 s, script error시 ItemElement 추가 안함
				int ctype = ct.getToken().getType();
				
				switch (ctype) {
				case RFSMParser.TNAME:
					child.setName(TPLTreeUtil.gets(ct));
					break;
				case RFSMParser.VB:	// KJH 20110927
					String stmts = TPLTreeUtil.gets(ct);
					for (String stmt : stmts.split("\r\n")) {
						child.getStatements().add(stmt);
					}
					break;
				}
			}
	
			parent.getItems().add(child);
			child.setParent(parent);

			if(notifyListener != null) {
				Notify notify = new Notify(parent, Notify.Type.ADD, child, null);
				if(TaskModelPackage.MODEL_DIAGRAM == beforeNode.modelType) {
					notify.setData(TaskModelPackage.MODEL_DIAGRAM__INCLUDE_ITEMS);
				}
				else if(TaskModelPackage.INCLUDED_ELEMENT == beforeNode.modelType) {
					notify.setData(TaskModelPackage.INCLUDED_ELEMENT__ITEMS);
				}

				notifyListener.notifyChanged(notify);
			}
		}
	}

	protected void exitStateElement(int rule, Parser parser, ParserRuleReturnScope returnScope) {
		current = stack.pop();
		if(stack.size() > 0) {
			StackNode beforeNode = stack.peek();
	
			BehaviorElement parent = (BehaviorElement)beforeNode.model;
			StateElement child = (StateElement)current.model;
			// KJH 20110929, itemState
			int itemState = child.getItemState();
			if (itemState == 0) {
				itemState |= ItemState.ITEM_CREATED;
				child.setItemState(itemState);
			}

			Tree tree = (Tree)returnScope.getTree();
			if(tree == null || returnScope.stop == null) {
				return;
			}
			if(tree instanceof CommonErrorNode) {
				return;
			}
			
			// KJH 20101019, 수정
			for (int i=0; i<tree.getChildCount(); i++) {
				CommonTree ct = (CommonTree)tree.getChild(i);
				if (ct instanceof CommonErrorNode) {	// KJH 20110216 s, script error시 ItemElement 추가 안함
					return;
				}	// KJH 20110216 s, script error시 ItemElement 추가 안함
				int ctype = ct.getToken().getType();
				
				switch (ctype) {
				case RFSMParser.SMOD:
					String smod = TPLTreeUtil.gets(ct);
					// KJH 20111006 s,
					StateAttribute attr;
					if ("initial".equals(smod)) {
						attr = StateAttribute.INITIAL;
					}
					else if ("goal".equals(smod)) {
						attr = StateAttribute.TARGET;
					}
					else {
						attr = StateAttribute.NORMAL;
					}	// KJH 20111006 e,
					
					if (attr != null) {
						child.setAttribute(attr);
						if (StateAttribute.INITIAL == attr) {
							if (parent.getInitialState() != null) {
								
							}
							parent.setInitialState(child);
						}
					}
					break;
				case RFSMParser.SNAME:
					child.setName(TPLTreeUtil.getFirstLeafValue(ct));
					break;
				case RFSMParser.VB:	// KJH 20110927
					String stmts = TPLTreeUtil.gets(ct);
					for (String stmt : stmts.split("\r\n")) {
						child.getStatements().add(stmt);
					}
					break;
				}
			}
			
			parent.getStates().add(child);
			child.setParent(parent);

			if(notifyListener != null) {
				Notify notify = new Notify(parent, Notify.Type.ADD, child, null);
				notify.setData(TaskModelPackage.TASK_ELEMENT__STATES);

				notifyListener.notifyChanged(notify);
			}
		}
	}

	protected void exitStateAction(int rule, Parser parser, ParserRuleReturnScope returnScope) {
		current = stack.pop();
		if(stack.size() > 0) {
			StackNode beforeNode = stack.peek();

			StateElement parent = (StateElement)beforeNode.model;
			StateAction child = (StateAction)current.model;
			// KJH 20110929, itemState
			int itemState = child.getItemState();
			if (itemState == 0) {
				itemState |= ItemState.ITEM_CREATED;
				child.setItemState(itemState);
			}

			Tree tree = (Tree)returnScope.getTree();
			if(tree == null || returnScope.stop == null) {
				return;
			}
			if(tree instanceof CommonErrorNode) {
				return;
			}

			// KJH 20101019, 수정
			StateActionType actionType = null;
			for (int i=0; i<tree.getChildCount(); i++) {
				CommonTree ct = (CommonTree)tree.getChild(i);
				if (ct instanceof CommonErrorNode) {	// KJH 20110216 s, script error시 ItemElement 추가 안함
					return;
				}	// KJH 20110216 s, script error시 ItemElement 추가 안함
				int ctype = ct.getToken().getType();
				
				switch (ctype) {
				case RFSMParser.AMOD:
					actionType = StateActionType.get(TPLTreeUtil.gets(ct));
					if (actionType != null) {
						child.setStateActionType(actionType);
						child.setName(actionType.toString());
					}
					break;
				case RFSMParser.STMTS:
					String statements = TPLTreeUtil.gets(ct);
					for (String statement : statements.split("\r\n")) {
						statement = statement.trim();
						if (statement.equals("{") || statement.equals("}"))
							continue;
						child.getStatements().add(statement);
					}
					break;
				}
			}

			if(actionType == StateActionType.ENTRY) {
				parent.setEntry(child);
			}
			else if(actionType == StateActionType.STAY) {
				parent.setStay(child);
			}
			else if(actionType == StateActionType.EXIT) {
				parent.setExit(child);
			}
			child.setParent(parent);

			if(notifyListener != null) {
				Notify notify = new Notify(parent, Notify.Type.ADD, child, null);
				if(actionType == StateActionType.ENTRY) {
					notify.setData(TaskModelPackage.STATE_ELEMENT__ENTRY);
				}
				else if(actionType == StateActionType.STAY) {
					notify.setData(TaskModelPackage.STATE_ELEMENT__STAY);
				}
				else if(actionType == StateActionType.EXIT) {
					notify.setData(TaskModelPackage.STATE_ELEMENT__EXIT);
				}

				notifyListener.notifyChanged(notify);
			}
		}
	}

	protected void exitEnumItem(int rule, Parser parser, ParserRuleReturnScope returnScope) {
		current = stack.pop();
		if(stack.size() > 0) {
			StackNode beforeNode = stack.peek();
	
			EnumElement parent = (EnumElement)beforeNode.model;
			EnumItemElement child = (EnumItemElement)current.model;
			// KJH 20110929, itemState
			int itemState = child.getItemState();
			if (itemState == 0) {
				itemState |= ItemState.ITEM_CREATED;
				child.setItemState(itemState);
			}

			Tree tree = (Tree)returnScope.getTree();
			if(tree == null || returnScope.stop == null) {
				return;
			}
			if(tree instanceof CommonErrorNode) {
				return;
			}

			// KJH 20101018, 수정
			for (int i=0; i<tree.getChildCount(); i++) {
				child = ModelManager.getFactory().createEnumItemElement();
				child.setName(TPLTreeUtil.gets(tree.getChild(i)));
				child.setValue(i);
				
				parent.getEnumItem().add(child);
				child.setParent(parent);
			}
			
			if(notifyListener != null) {
				Notify notify = new Notify(parent, Notify.Type.ADD, child, null);
				if(TaskModelPackage.MODEL_DIAGRAM == beforeNode.modelType) {
					notify.setData(TaskModelPackage.MODEL_DIAGRAM__ITEMS);
				}
				else if(TaskModelPackage.INCLUDED_ELEMENT == beforeNode.modelType) {
					notify.setData(TaskModelPackage.INCLUDED_ELEMENT__ITEMS);
				}

				notifyListener.notifyChanged(notify);
			}
		}
	}

	protected void exitParameter(int rule, Parser parser, ParserRuleReturnScope returnScope) {
		current = stack.pop();
		if(stack.size() > 0) {
			StackNode beforeNode = stack.peek();
	
			ItemElement parent = beforeNode.model;
			Parameter child = (Parameter)current.model;
			// KJH 20110929, itemState
			int itemState = child.getItemState();
			if (itemState == 0) {
				itemState |= ItemState.ITEM_CREATED;
				child.setItemState(itemState);
			}

			Tree tree = (Tree)returnScope.getTree();
			if(tree == null || returnScope.stop == null) {
				return;
			}
			if(tree instanceof CommonErrorNode) {
				return;
			}
			
			// KJH 20101019, 수정
			for (int i=0; i<tree.getChildCount(); i++) {
				CommonTree ct = (CommonTree)tree.getChild(i);
				if (ct instanceof CommonErrorNode) {	// KJH 20110216 s, script error시 ItemElement 추가 안함
					return;
				}	// KJH 20110216 s, script error시 ItemElement 추가 안함
				int ctype = ct.getToken().getType();
				
				switch (ctype) {
				case RFSMParser.TYPE:
					child.setType(TPLTreeUtil.gets(ct));
					break;
				case RFSMParser.NAME:
					child.setName(TPLTreeUtil.gets(ct));
					break;
				}
			}
	
			if(parent instanceof Function) {
				((Function)parent).getParams().add(child);
			}
			else if(parent instanceof ActionElement) {
				((ActionElement)parent).getParams().add(child);
			}
			else if(parent instanceof BehaviorElement) {
				((BehaviorElement)parent).getParams().add(child);
			}
			else if (parent instanceof ConnectorElement) {
				// KJH 20110728, add
				((ConnectorElement)parent).getParams().add(child);
			}
			child.setParent(parent);

			if(notifyListener != null) {
				Notify notify = new Notify(parent, Notify.Type.ADD, child, null);
				if(TaskModelPackage.FUNCTION == beforeNode.modelType) {
					notify.setData(TaskModelPackage.FUNCTION__PARAMS);
				}
				else if(TaskModelPackage.ACTION_ELEMENT == beforeNode.modelType) {
					notify.setData(TaskModelPackage.ACTION_ELEMENT__PARAMS);
				}
				else if(TaskModelPackage.TASK_ELEMENT == beforeNode.modelType) {
					notify.setData(TaskModelPackage.TASK_ELEMENT__PARAMS);
				}
				else if (TaskModelPackage.CONNECTOR_ELEMENT == beforeNode.modelType) {
					// KJH 20110728, add
					notify.setData(TaskModelPackage.CONNECTOR_ELEMENT__PARAMS);
				}

				notifyListener.notifyChanged(notify);
			}
		}
	}
	
	// KJH 20101018 s, transition
	protected void exitTransition(int rule, Parser parser, ParserRuleReturnScope returnScope) {
		current = stack.pop();
		if (stack.size() > 0) {
//			StackNode beforeNode = stack.peek();
//			ItemElement parent = beforeNode.model;
//			ConnectionElement child = (ConnectionElement)current.model;
	
			Tree tree = (Tree)returnScope.getTree();
			if(tree == null || returnScope.stop == null) {
				return;
			}
			if(tree instanceof CommonErrorNode) {
				return;
			}
			
		}
	}
	// KJH 20101018 e, transition
	
	// KJH 20110208 s, connector
	protected void exitConnector(int rule, Parser parser, ParserRuleReturnScope returnScope) {
		current = stack.pop();
		if (stack.size() > 0) {
			StackNode beforeNode = stack.peek();
			
			ModelDiagram parent = (ModelDiagram)beforeNode.model;
			ConnectorElement child = (ConnectorElement)current.model;
			// KJH 20110929, itemState
			int itemState = child.getItemState();
			if (itemState == 0) {
				itemState |= ItemState.ITEM_CREATED;
				child.setItemState(itemState);
			}

			Tree tree = (Tree)returnScope.getTree();
			if(tree == null || returnScope.stop == null) {
				return;
			}
			if(tree instanceof CommonErrorNode) {
				return;
			}
			
			for (int i=0; i<tree.getChildCount(); i++) {
				CommonTree ct = (CommonTree)tree.getChild(i);
				if (ct instanceof CommonErrorNode) {	// KJH 20110216 s, script error시 ItemElement 추가 안함
					return;
				}	// KJH 20110216 s, script error시 ItemElement 추가 안함
				int ctype = ct.getToken().getType();
				
				switch (ctype) {
				case RFSMParser.NAME:
					child.setName(TPLTreeUtil.gets(ct));
					break;
				case RFSMParser.VB:	// KJH 20110927
					String stmts = TPLTreeUtil.gets(ct);
					for (String stmt : stmts.split("\r\n")) {
						child.getStatements().add(stmt);
					}
					break;
				}
			}
	
			parent.getItems().add(child);
			child.setParent(parent);

			if(notifyListener != null) {
				Notify notify = new Notify(parent, Notify.Type.ADD, child, null);
				if(TaskModelPackage.MODEL_DIAGRAM == beforeNode.modelType) {
					notify.setData(TaskModelPackage.MODEL_DIAGRAM__INCLUDE_ITEMS);
				}
				else if(TaskModelPackage.INCLUDED_ELEMENT == beforeNode.modelType) {
					notify.setData(TaskModelPackage.INCLUDED_ELEMENT__ITEMS);
				}

				notifyListener.notifyChanged(notify);
			}
		}
	}
	// KJH 20110208 e, connector

	// KJH 20110209 s, structblock
	protected void exitStructBlock(int rule, Parser parser, ParserRuleReturnScope returnScope) {
		current = stack.pop();
		if (stack.size() > 0) {
			StackNode beforeNode = stack.peek();

			ItemElement parent = beforeNode.model;
			StructBlockElement child = (StructBlockElement)current.model;
			// KJH 20110929, itemState
			int itemState = child.getItemState();
			if (itemState == 0) {
				itemState |= ItemState.ITEM_CREATED;
				child.setItemState(itemState);
			}

			Tree tree = (Tree)returnScope.getTree();
			if(tree == null || returnScope.stop == null) {
				return;
			}
			if(tree instanceof CommonErrorNode) {
				return;
			}

			// KJH 20101019, 수정
			StructType structType = null;
			// KJH 20111004 s, FIX, initializer, finalizer에 대해 StructType을 CONSTR/DEST로 통일하여 에러남
			if (RFSMParser.CONSTR == tree.getType()) {
				structType = StructType.CONSTRUCT;
			}
			else if (RFSMParser.DEST == tree.getType()) {
				structType = StructType.DESTRUCT;
			}
			else {
				structType = StructType.RUN;
			}	// KJH 20111004 e, FIX
			
			for (int i=0; i<tree.getChildCount(); i++) {
				CommonTree ct = (CommonTree)tree.getChild(i);
				if (ct instanceof CommonErrorNode) {	// KJH 20110216 s, script error시 ItemElement 추가 안함
					return;
				}	// KJH 20110216 s, script error시 ItemElement 추가 안함
				int ctype = ct.getToken().getType();
				
				switch (ctype) {
				case RFSMParser.AMOD:
					child.setStructType(structType);
					child.setName(TPLTreeUtil.gets(ct));
					break;
				case RFSMParser.STMTS:
					String statements = TPLTreeUtil.gets(ct);
					for (String statement : statements.split("\r\n")) {
						statement = statement.trim();
						if (statement.equals("{") || statement.equals("}"))
							continue;
						child.getStatements().add(statement);
					}
					break;
				}
			}

			if (parent instanceof TaskElement) {
				if (structType == StructType.CONSTRUCT) {
					((TaskElement)parent).setInitialize(child);
				}
				else if (structType == StructType.DESTRUCT) {
					((TaskElement)parent).setFinalize(child);
				}
				else if (structType == StructType.RUN) {
					((TaskElement)parent).setRun(child);
				}
			}
			else if (parent instanceof ConnectorElement) {
				if (structType == StructType.CONSTRUCT) {
					((ConnectorElement)parent).setConstruct(child);
				}
				else if (structType == StructType.DESTRUCT) {
					((ConnectorElement)parent).setDestruct(child);
				}
			}
			// KJH 20110216 s,
			else if (parent instanceof BehaviorElement) {
				if (structType == StructType.CONSTRUCT) {
					((BehaviorElement)parent).setConstruct(child);
				}
				else if (structType == StructType.DESTRUCT) {
					((BehaviorElement)parent).setDestruct(child);
				} 
			}	// KJH 20110216 e,
			child.setParent(parent);

			if(notifyListener != null) {
				Notify notify = new Notify(parent, Notify.Type.ADD, child, null);
				if (parent instanceof TaskElement) {
					if (structType == StructType.CONSTRUCT) {
						notify.setData(TaskModelPackage.WORKER_ELEMENT__INITIALIZE);
					}
					else if (structType == StructType.DESTRUCT) {
						notify.setData(TaskModelPackage.WORKER_ELEMENT__FINALIZE);
					}
//					else if (structType == StructType.RUN) {
//						notify.setData(TaskModelPackage.WORKER_ELEMENT__RUN);
//					}
				}
				else if (parent instanceof ConnectorElement) {
					if (structType == StructType.CONSTRUCT) {
						notify.setData(TaskModelPackage.CONNECTOR_ELEMENT__CONSTRUCT);
					}
					else if (structType == StructType.DESTRUCT) {
						notify.setData(TaskModelPackage.CONNECTOR_ELEMENT__DESTRUCT);
					}
				}
				else if (parent instanceof BehaviorElement) {
					if (structType == StructType.CONSTRUCT) {
						notify.setData(TaskModelPackage.TASK_ELEMENT__CONSTRUCT);
					}
					else if (structType == StructType.DESTRUCT) {
						notify.setData(TaskModelPackage.TASK_ELEMENT__DESTRUCT);
					}
				}

				notifyListener.notifyChanged(notify);
			}
		}
	}
	// KJH 20110209 e, structblock
	
	/**
	 * KJH 20110707, Expand & Trans
	 */
	protected void exitExpandTrans(int rule, Parser parser, ParserRuleReturnScope returnScope) {
		current = stack.pop();
		if (stack.size() > 0) {
//			StackNode beforeNode = stack.peek();
		}
	}
	
	/**
	 * KJH 20110707, WithElement 
	 */
	protected void exitWithElement(int rule, Parser parser, ParserRuleReturnScope returnScope) {
		current = stack.pop();
		if (stack.size() > 0) {
			StackNode beforeNode = stack.peek();
			
			ItemElement parent = (ItemElement)beforeNode.model;
			WithElement child = (WithElement)current.model;
			// KJH 20110929, itemState
			int itemState = child.getItemState();
			if (itemState == 0) {
				itemState |= ItemState.ITEM_CREATED;
				child.setItemState(itemState);
			}

			Tree tree = (Tree)returnScope.getTree();
			if(tree == null || returnScope.stop == null) {
				return;
			}
			if(tree instanceof CommonErrorNode) {
				return;
			}
			
			for (int i=0; i<tree.getChildCount(); i++) {
				CommonTree ct = (CommonTree)tree.getChild(i);
				if (ct instanceof CommonErrorNode) {
					return;
				}
				int ctype = ct.getToken().getType();
				
				switch (ctype) {
				case RFSMParser.AMOD:
					child.setName(TPLTreeUtil.gets(ct));
					break;
				case RFSMParser.CYCLE:	// KJH 20110928
					int value = Integer.parseInt(TPLTreeUtil.gets(ct));
					child.setCycle(value);
				}
			}
			
			// KJH 20110901 s, parent가 connector, task에 대한 처리
			if (parent instanceof ConnectorElement) {
				((ConnectorElement)parent).getWiths().add(child);
			}
			else if (parent instanceof TaskElement) {
				((TaskElement)parent).getItems().add(child);
			}
			child.setParent(parent);

			if(notifyListener != null) {
				Notify notify = new Notify(parent, Notify.Type.ADD, child, null);
				if (parent instanceof ConnectorElement) {
					notify.setData(TaskModelPackage.CONNECTOR_ELEMENT__WITHS);
					notifyListener.notifyChanged(notify);
				}
				else if (parent instanceof TaskElement) {
					notify.setData(TaskModelPackage.WORKER_ELEMENT__ITEMS);
					notifyListener.notifyChanged(notify);
				}
			}
			// KJH 20110901 e, parent가 connector, task에 대한 처리
		}
	}

	// KJH 20101018 s, transition
	protected void enterSubTransition(int rule, Parser parser, ParserRuleReturnScope returnScope) {
		
	}
	// KJH 20101018 e, transition

	// KJH 20101018 s, transition
	protected void exitSubTransition(int rule, Parser parser, ParserRuleReturnScope returnScope) {

		if (stack.size() > 0) {
//			StackNode beforeNode = stack.peek();
//			ItemElement parent = beforeNode.model;
//			ConnectionElement child = (ConnectionElement)current.model;
	
			Tree tree = (Tree)returnScope.getTree();
			if(tree == null || returnScope.stop == null) {
				return;
			}
			if(tree instanceof CommonErrorNode) {
				return;
			}
			
		}
	}
	// KJH 20101018 e, transition

//	private static void printTree(int level, Tree tree) {
//		Tree child = null;
//		String format = String.format("%%0$%dc%%s [%d](%d:%d)", level * 2 + 1,
//				level, tree.getLine(), tree.getCharPositionInLine());
//		System.out.println(String.format(format, ' ', tree.toString()));
//		int childNum = tree.getChildCount();
//		for (int i = 0; i < childNum; i++) {
//			child = (Tree) tree.getChild(i);
//			printTree(level + 1, child);
//		}
//	}
//
//	private static void printTrace(Token[] trace) {
//		if(trace == null || trace.length == 0) {			
//			return;
//		}
//		for (int i = 0; i < trace.length; i++) {
//			if(i > 0) {
//				System.out.print('.');
//			}
//			
//			if(trace[i] == null) {
//				continue;
//			}
//			System.out.print(((Token)trace[i]).getText());
//		}
//		
//	}

	public Token[] getTokenTrace(Tree tree) {
		Token []parentTrace = null;
		if(tree != null) {
			parentTrace = getTokenTrace(tree.getParent());
		}
		else {
			parentTrace = new Token[0];
			return parentTrace;
		}
		
		Token []trace = new Token[parentTrace.length + 1];
		System.arraycopy(parentTrace, 0, trace, 0, parentTrace.length);
		trace[parentTrace.length] = ((CommonTree)tree).getToken();
		
		return trace;
	}
}
