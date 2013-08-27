package kr.re.etri.tpl.grammar;


import java.util.List;
import java.util.Stack;

import kr.re.etri.tpl.diagram.ModelManager;
import kr.re.etri.tpl.diagram.listener.IErrorListener;
import kr.re.etri.tpl.script.utils.TPLTreeUtil;
import kr.re.etri.tpl.taskmodel.ActionElement;
import kr.re.etri.tpl.taskmodel.BehaviorElement;
import kr.re.etri.tpl.taskmodel.ConnectionElement;
import kr.re.etri.tpl.taskmodel.ConnectorElement;
import kr.re.etri.tpl.taskmodel.Constant;
import kr.re.etri.tpl.taskmodel.EnumElement;
import kr.re.etri.tpl.taskmodel.EnumItemElement;
import kr.re.etri.tpl.taskmodel.ExpandTransElement;
import kr.re.etri.tpl.taskmodel.Function;
import kr.re.etri.tpl.taskmodel.IncludedElement;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.LineEndPoint;
import kr.re.etri.tpl.taskmodel.LineStyle;
import kr.re.etri.tpl.taskmodel.ModelDiagram;
import kr.re.etri.tpl.taskmodel.ModelElement;
import kr.re.etri.tpl.taskmodel.Parameter;
import kr.re.etri.tpl.taskmodel.ReferElement;
import kr.re.etri.tpl.taskmodel.RelationShip;
import kr.re.etri.tpl.taskmodel.StateAction;
import kr.re.etri.tpl.taskmodel.StateActionType;
import kr.re.etri.tpl.taskmodel.StateElement;
import kr.re.etri.tpl.taskmodel.SubDiagram;
import kr.re.etri.tpl.taskmodel.Symbol;
import kr.re.etri.tpl.taskmodel.TaskElement;
import kr.re.etri.tpl.taskmodel.TaskModelFactory;
import kr.re.etri.tpl.taskmodel.TaskModelPackage;
import kr.re.etri.tpl.taskmodel.WithElement;

import org.antlr.runtime.CommonToken;
import org.antlr.runtime.TokenStream;
import org.antlr.runtime.tree.CommonErrorNode;
import org.antlr.runtime.tree.Tree;
import org.eclipse.emf.common.util.EList;

public class RTMTokenVerifier {
	private Stack<StackNode> stack = new Stack<StackNode>();
	private TaskModelFactory factory;
	private Tree parseTree;
	private ModelDiagram model;
	private StackNode current;
	private IErrorListener logListener;
	private TokenStream tokenStream;
	private boolean updateModel;
	private SymbolTable symbolTable = new SymbolTable();
	private ConditionStack conditionStack = new ConditionStack();

	protected class StackNode {
		public ItemElement model;
		public int modelType;
		
		public StackNode(int modelType, ItemElement model) {
			this.model = model;
			this.modelType = modelType;
		}
	}

	public RTMTokenVerifier() {
	}

	public RTMTokenVerifier(Tree parseTree, TokenStream tokenStream, ModelDiagram model, boolean updateModel, IErrorListener logListener) {
		this.parseTree = parseTree;
		this.tokenStream = tokenStream;
		this.model = model;
		this.updateModel = updateModel;
		this.logListener = logListener;
	}

	public TaskModelFactory getFactory() {
		if(factory == null) {
			factory = ModelManager.getFactory();
		}
		
		return factory;
	}

	public static void verify(Tree parseTree, TokenStream tokenStream, ModelDiagram model, boolean updateModel, IErrorListener logListener) {
		RTMTokenVerifier verifier = new RTMTokenVerifier(parseTree, tokenStream, model, updateModel, logListener);
		verifier.verify();
	}

	public void verify() {
		current = new StackNode(TaskModelPackage.MODEL_DIAGRAM, model);
		stack.push(current);

		verifyTrace(parseTree);
		
		stack.pop();
		current = null;
	}

	protected void verifyTrace(Tree tree) {
		int type = tree.getType();
		switch (type) {
		case RFSMParser.INCL:
			verifyIncludedElement(tree);	break;
		case RFSMParser.ENUM:
			verifyEnumElement(tree);	break;
		case RFSMParser.MODEL:
			verifyModelElement(tree);	break;
		case RFSMParser.WORKER2:
			verifyTaskElement(tree);	break;
		case RFSMParser.BEHA:
			verifyBehaviorElement(tree);	break;
		case RFSMParser.CNT:
			verifyConnectorElement(tree);	break;
		// Model Member
		case RFSMParser.VAR:
			processSymbol(tree);	break;
		case RFSMParser.FUNC:
			processFunction(tree);	break;
		// statements
		case RFSMParser.IF:
			processIfStatement(tree);	break;
		case RFSMParser.GOTO:
			processGotoStatement(tree);	break;
		case RFSMParser.IVK:
			processExpandStatement(tree);	break;
		case RFSMParser.STMTCALL:
			processCallStatement(tree);	break;
		case RFSMParser.STMTEXPR:
			processExprStatement(tree);	break;
		case RFSMParser.STMTBLOCK:
			processBlockStatement(tree);	break;
		case RFSMParser.VB:
			processLocalVariable(tree);	break;
		//Exepressions
		case RFSMParser.ASMT:
			verifyExpression(tree);	break;
		case RFSMParser.COND:
			verifyExprCond(tree);	break;
		case RFSMParser.OR:
			verifyExprOr(tree);	break;
		case RFSMParser.AND:
			verifyExprAnd(tree);	break;
		case RFSMParser.NOT:
			verifyExprNot(tree);	break;
		case RFSMParser.BOOL:
			verifyExprBool(tree);	break;
		case RFSMParser.ADD:
			verifyExprAdd(tree);	break;
		case RFSMParser.MUL:
			verifyExprMul(tree);	break;
		case RFSMParser.SIGN:
			verifyExprSign(tree);	break;
		default:
			int childCount = tree.getChildCount();
			for (int i = 0; i < childCount; i++) {
				Tree child = (Tree) tree.getChild(i);
				verifyTrace(child);
			}
		}
		
	}

	protected void verifyModelDiagram(Tree tree) {
	}

	protected void verifyIncludedElement(Tree tree) {
	}

	protected void verifyEnumElement(Tree tree) {
	}

	protected void verifyModelElement(Tree tree) {

		ModelElement modelElmt = null;
		Tree nameTree = null;
		
		int childCount = tree.getChildCount();
		for (int i = 0; i < childCount; i++) {
			Tree subTree = tree.getChild(i);
			if (RFSMParser.NAME == tree.getType()) {
				nameTree = subTree.getChild(0);
				break;
			}
		}
		
		if (nameTree == null) {
			return;
		}
		
		String name = TPLTreeUtil.gets(nameTree);
		if(current.model instanceof ModelDiagram) {
			ModelDiagram modelDiagram = (ModelDiagram)current.model;
			for(ItemElement item : modelDiagram.getItems()) {
				if((item instanceof ModelElement) == false) {
					continue;
				}
				if(name.equals(((ModelElement)item).getName())) {
					modelElmt = (ModelElement)item;
					break;
				}
			}
		}
		else if(current.model instanceof ModelElement) {
			ModelElement parentModel = (ModelElement)current.model;
			for(ItemElement item : parentModel.getModels()) {
				if((item instanceof ModelElement) == false) {
					continue;
				}
				if(name.equals(((ModelElement)item).getName())) {
					modelElmt = (ModelElement)item;
					break;
				}
			}
		}
		
		if(modelElmt == null) {
			if (this.logListener != null) {
				CommonToken tokenStart = (CommonToken)tokenStream.get(nameTree.getTokenStartIndex());
				CommonToken tokenEnd = (CommonToken)tokenStream.get(nameTree.getTokenStopIndex());

				int line = nameTree.getLine();
				int charStart = tokenStart.getStartIndex();
				int charEnd = tokenEnd.getStopIndex()+1;

				this.logListener.error(String.format("Model %s정보를 찾을 수 없습니다.", name), line, charStart, charEnd);
			}
			return;
		}

		current = new StackNode(TaskModelPackage.MODEL_ELEMENT, modelElmt);
		stack.push(current);

		for (int idx = 0; idx < childCount; idx++) {
			Tree subTree = tree.getChild(idx);
			if (RFSMParser.VAR == subTree.getType()) {
				processSymbol(subTree);
			}
			else if (RFSMParser.FUNC == subTree.getType()) {
				processFunction(subTree);
			}
			else if (RFSMParser.MODEL == subTree.getType()) {
				verifyModelElement(subTree);
			}
		}

		stack.pop();
		current = stack.peek();
	}

	protected void processSymbol(Tree tree) {
	}

	protected void processFunction(Tree tree) {
	}

	protected void verifyTaskElement(Tree tree) {
		Tree nameTree = null;
		Tree varBlock = null;
		TaskElement task = null;
		
		int childCount = tree.getChildCount();
		for (int i = 0; i < childCount; i++) {
			Tree subTree = tree.getChild(i);
			if (RFSMParser.NAME == subTree.getType()) {
				nameTree = subTree.getChild(0);
			}
			else if (RFSMParser.VB == subTree.getType()) {
				varBlock = subTree;
			}
		}
		
		if (nameTree == null) {
			return;
		}
		
		if (current.model instanceof ModelDiagram) {
			ModelDiagram diagram = (ModelDiagram)current.model;
			for (ItemElement item : diagram.getItems()) {
				if ((item instanceof TaskElement) == false) {
					continue;
				}
				if (item.getName().equals(TPLTreeUtil.gets(nameTree))) {
					task = (TaskElement)item;
					break;
				}
			}
		}
		else {
			return;
		}
		
		if(task == null) {
			if (this.logListener != null) {
				CommonToken tokenStart = (CommonToken)tokenStream.get(nameTree.getTokenStartIndex());
				CommonToken tokenEnd = (CommonToken)tokenStream.get(nameTree.getTokenStopIndex());

				int line = nameTree.getLine();
				int charStart = tokenStart.getStartIndex();
				int charEnd = tokenEnd.getStopIndex()+1;

				this.logListener.error(String.format("Task %s 정보를 찾을 수 없습니다.", nameTree.getText()), line, charStart, charEnd);
			}
			return;
		}
		
		current = new StackNode(TaskModelPackage.WORKER_ELEMENT, task);
		stack.push(current);

		symbolTable.addScope();
		
		if (varBlock != null) {
			for (int i = 0; i < varBlock.getChildCount(); i++) {
				Tree child = varBlock.getChild(i);

				if (RFSMParser.LVAR == child.getType()) {
					processLocalVariable(child);
				}
			}
		}
		
		for (int i = 0; i < childCount; i++) {
			Tree subTree = tree.getChild(i);
			if (RFSMParser.CONSTR == subTree.getType()) {
				verifyStructBlock(subTree);
			}
			else if (RFSMParser.DEST == subTree.getType()) {
				verifyStructBlock(subTree);
			}
			else if (RFSMParser.RUN == subTree.getType()) {
				EList<ItemElement> items = task.getItems();
				if (items.size() > 0 &&
					items.get(0) instanceof WithElement) {
					WithElement with = (WithElement)items.get(0);
					current = new StackNode(TaskModelPackage.WITH_ELEMENT, with);
					stack.push(current);
					
					processRunStatement(subTree);
					getReferElement(with);	// KJH 20110928

					stack.pop();
					current = stack.peek();
				}
			}
		}
		
		getReferElement(task);
		
		symbolTable.removeScope();
		
		stack.pop();
		current = stack.peek();
	}
	
	protected void verifyStructBlock(Tree tree) {
		symbolTable.addScope();

		int childCount = tree.getChildCount();
		for (int i = 0; i < childCount; i++) {
			Tree subTree = tree.getChild(i);
			if (RFSMParser.STMTS == subTree.getType()) {
				processSingleStatement(subTree);
			}
		}
		
		symbolTable.removeScope();

//		if (RFSMParser.RUN == tree.getType()) {	// run block
//			Tree behaTree = getTree(tree, RFSMParser.BEHA);
//			String initTask = "";
//			BehaviorElement behavior = null;
//			if (behaTree != null) {
//				behaTree = getTree(behaTree, RFSMParser.ID);
//				if (behaTree != null) {
//					initTask = TPLTreeUtil.gets(behaTree);
//					behavior = findInitialTask(initTask);
//				}
//			}
//			else {
//				return;
//			}
//
//			if(behavior == null) {
//				if (this.logListener != null) {
//					CommonToken tokenStart = (CommonToken)tokenStream.get(behaTree.getTokenStartIndex());
//					CommonToken tokenEnd = (CommonToken)tokenStream.get(behaTree.getTokenStopIndex());
//
//					int line = behaTree.getLine();
//					int charStart = tokenStart.getStartIndex();
//					int charEnd = tokenEnd.getStopIndex()+1;
//
//					this.logListener.error(String.format("초기 Behavior %s정보를 찾을 수 없습니다.", initTask), line, charStart, charEnd);
//				}
//				return;
//			}
//		}
	}

	protected void verifyBehaviorElement(Tree tree) {
		Tree bhvName = null;
		Tree bhvBlock = null;
		Tree varBlock = null;
		BehaviorElement bhv = null;
		
		int childCount = tree.getChildCount();
		for (int i = 0; i < childCount; i++) {
			Tree subTree = tree.getChild(i);
			if (RFSMParser.TNAME == subTree.getType()) {
				bhvName = subTree.getChild(0);
			}
			else if (RFSMParser.PARMS == subTree.getType()) {
				
			}
			else if (RFSMParser.STATES == subTree.getType()) {
				bhvBlock = subTree;
			}
			else if (RFSMParser.VB == subTree.getType()) {
				varBlock = subTree;
			}
		}
		
		if (bhvName == null) {
			return;
		}
		
		if (current.model instanceof ModelDiagram) {
			ModelDiagram modelDiagram = (ModelDiagram)current.model;
			for(ItemElement item : modelDiagram.getItems()) {
				if((item instanceof BehaviorElement) == false) {
					continue;
				}
				if(item.getName().equals(TPLTreeUtil.gets(bhvName))) {
					bhv = (BehaviorElement)item;
					break;
				}
			}
		}
		else {
			return;
		}

		if(bhv == null) {
			if (this.logListener != null) {
				CommonToken tokenStart = (CommonToken)tokenStream.get(bhvName.getTokenStartIndex());
				CommonToken tokenEnd = (CommonToken)tokenStream.get(bhvName.getTokenStopIndex());

				int line = bhvName.getLine();
				int charStart = tokenStart.getStartIndex();
				int charEnd = tokenEnd.getStopIndex()+1;

				this.logListener.error(String.format("Behavior %s 정보를 찾을 수 없습니다.", bhvName.getText()), line, charStart, charEnd);
			}
			return;
		}

		current = new StackNode(TaskModelPackage.TASK_ELEMENT, bhv);
		stack.push(current);

		symbolTable.addScope();
		
		// Task 파라미터를 Model에서? 트리에서?
		List<Parameter> paramList = bhv.getParams();
		for(Parameter param : paramList) {
			symbolTable.addSymbol(param.getName());
		}

		if (varBlock != null) {
			for (int i = 0; i < varBlock.getChildCount(); i++) {
				Tree child = varBlock.getChild(i);

				if (RFSMParser.LVAR == child.getType()) {
					processLocalVariable(child);
				}
			}
		}
		
		if (bhvBlock != null) {
			for (int i = 0; i < bhvBlock.getChildCount(); i++) {
				Tree childDecl = bhvBlock.getChild(i);

				if(RFSMParser.STATE == childDecl.getType()) {
					verifyStateElement(childDecl);
				}
				else if (RFSMParser.CONSTR == childDecl.getType()) {
					verifyStructBlock(childDecl);
				}
				else if (RFSMParser.DEST == childDecl.getType()) {
					verifyStructBlock(childDecl);
				}
			}
		}
		
		getReferElement(bhv);
		
		symbolTable.removeScope();

		stack.pop();
		current = stack.peek();
	}

	protected void verifyStateElement(Tree tree) {
		Tree stateName = null;
		Tree varBlock = null;
		
		int childCount = tree.getChildCount();
		for (int i = 0; i < childCount; i++) {
			Tree subTree = tree.getChild(i);
			if (RFSMParser.SNAME == subTree.getType()) {
				stateName = subTree.getChild(0);
			}
			else if (RFSMParser.VB == subTree.getType()) {
				varBlock = subTree;
			}
		}
		
		StateElement state = null;
		BehaviorElement task = (BehaviorElement)current.model;
		for(StateElement item : task.getStates()) {
			if(item.getName().equals(TPLTreeUtil.gets(stateName))) {
				state = item;
				break;
			}
		}

		if(state == null) {
			if (this.logListener != null) {
				CommonToken tokenStart = (CommonToken)tokenStream.get(stateName.getTokenStartIndex());
				CommonToken tokenEnd = (CommonToken)tokenStream.get(stateName.getTokenStopIndex());

				int line = stateName.getLine();
				int charStart = tokenStart.getStartIndex();
				int charEnd = tokenEnd.getStopIndex()+1;

				this.logListener.error(String.format("Behavior %s의 State %s정보를 찾을 수 없습니다.", task.getName(), stateName.getText()), line, charStart, charEnd);
			}
			return;
		}
		
		current = new StackNode(TaskModelPackage.STATE_ELEMENT, state);
		stack.push(current);

		symbolTable.addScope();

		if (varBlock != null) {
			for (int i = 0; i < varBlock.getChildCount(); i++) {
				Tree subTree = varBlock.getChild(i);
				if(RFSMParser.LVAR == subTree.getType()) {
					processLocalVariable(subTree);
				}
			}
		}

		for(int i = 0; i < childCount; i++) {
			Tree subTree = tree.getChild(i);
			if(RFSMParser.TRANS == subTree.getType()) {
				verifyTransition(subTree);
			}
			else if(RFSMParser.ACTION == subTree.getType()) {
				verifyStateAction(subTree);
			}
		}
		
		getReferElement(state);
		
		symbolTable.removeScope();

		stack.pop();
		current = stack.peek();
	}

	protected void verifyStateAction(Tree tree) {
		Tree actionName = null;
		Tree statementBlock = null;
		
		int childCount = tree.getChildCount();
		for (int i = 0; i < childCount; i++) {
			Tree subTree = tree.getChild(i);
			if (RFSMParser.AMOD == subTree.getType()) {
				actionName = subTree;
			}
			else if (RFSMParser.STMTS == subTree.getType()) {
				statementBlock = subTree;
			}
		}
		
		if (actionName == null || statementBlock == null) {
			return;
		}
		
		StateActionType actionType = StateActionType.get(TPLTreeUtil.gets(actionName));

		StateAction action = null;
		StateElement state = (StateElement)current.model;
		if(StateActionType.ENTRY == actionType) {
			action = state.getEntry();
		}
		else if(StateActionType.STAY == actionType) {
			action = state.getStay();
		}
		else if(StateActionType.EXIT == actionType) {
			action = state.getExit();
		}
		else {
			return;
		}
		
		current = new StackNode(TaskModelPackage.STATE_ACTION, action);
		stack.push(current);

		symbolTable.addScope();

		processSingleStatement(statementBlock);

		symbolTable.removeScope();

		stack.pop();
		current = stack.peek();
	}
	
	protected void verifyConnectorElement(Tree tree) {
		Tree nameTree = null;
		Tree varBlock = null;
		Tree conElems = null;
		ConnectorElement cnt = null;
		
		int childCount = tree.getChildCount();
		for (int i = 0; i < childCount; i++) {
			Tree subTree = tree.getChild(i);
			if (RFSMParser.NAME == subTree.getType()) {
				nameTree = subTree.getChild(0);
			}
			else if (RFSMParser.PARMS == subTree.getType()) {
				
			}
			else if (RFSMParser.ELEMS == subTree.getType()) {
				conElems = subTree;
			}
			else if (RFSMParser.VB == subTree.getType()) {
				varBlock = subTree;
			}
		}
		
		if (nameTree == null) {
			return;
		}
		
		if (current.model instanceof ModelDiagram) {
			ModelDiagram modelDiagram = (ModelDiagram)current.model;
			for(ItemElement item : modelDiagram.getItems()) {
				if((item instanceof ConnectorElement) == false) {
					continue;
				}
				if(item.getName().equals(TPLTreeUtil.gets(nameTree))) {
					cnt = (ConnectorElement)item;
					break;
				}
			}
		}
		
		if (cnt == null) {
			if (this.logListener != null) {
				CommonToken tokenStart = (CommonToken)tokenStream.get(nameTree.getTokenStartIndex());
				CommonToken tokenEnd = (CommonToken)tokenStream.get(nameTree.getTokenStopIndex());

				int line = nameTree.getLine();
				int charStart = tokenStart.getStartIndex();
				int charEnd = tokenEnd.getStopIndex()+1;

				this.logListener.error(String.format("Connector %s 정보를 찾을 수 없습니다.", nameTree.getText()), line, charStart, charEnd);
			}
			return;
		}
		
		current = new StackNode(TaskModelPackage.CONNECTOR_ELEMENT, cnt);
		stack.push(current);

		symbolTable.addScope();
		
		// Connector 파라미터를 Model에서? 트리에서?
		List<Parameter> paramList = cnt.getParams();
		for(Parameter param : paramList) {
			symbolTable.addSymbol(param.getName());
		}

		if (varBlock != null) {
			for (int i = 0; i < varBlock.getChildCount(); i++) {
				Tree child = varBlock.getChild(i);

				if (RFSMParser.LVAR == child.getType()) {
					processLocalVariable(child);
				}
			}
		}
		
		if (conElems != null) {
			for (int i = 0; i < conElems.getChildCount(); i++) {
				Tree subTree = conElems.getChild(i);

				if (RFSMParser.CONSTR == subTree.getType()) {
					verifyStructBlock(subTree);
				}
				else if (RFSMParser.DEST == subTree.getType()) {
					verifyStructBlock(subTree);
				}
				else if (RFSMParser.CON == subTree.getType()) {
					verifyConDecl(subTree);
				}
			}
		}
		
		getReferElement(cnt);
		
		symbolTable.removeScope();

		stack.pop();
		current = stack.peek();
	}
	
	protected void verifyConDecl(Tree tree) {
		Tree withsTree = null;
		int childCount = tree.getChildCount();
		for (int i = 0; i < childCount; i++) {
			Tree subTree = tree.getChild(i);
			if (RFSMParser.WITHS == subTree.getType()) {
				withsTree = subTree;
			}
		}
		
		if (withsTree == null) {
			
		}
		
		if ((current.model instanceof ConnectorElement) == false) {
			return;
		}
		
		ConnectorElement cnt = (ConnectorElement)current.model;
		List<WithElement> withs = cnt.getWiths();
		if (withsTree != null) {
			for (int i = 0; i < withsTree.getChildCount() && i < withs.size(); i++) {
				Tree child = withsTree.getChild(i);
				WithElement with = withs.get(i);
				
				current = new StackNode(TaskModelPackage.WITH_ELEMENT, with);
				stack.push(current);
				
				if (RFSMParser.RUN == child.getType()) {	// KJH 20110905, WITH->RUN
					processRunStatement(child);
				}
				
				if (i > 0) {
					WithElement preWith = withs.get(i-1);
					
					ReferElement refSource = getReferElement(preWith);
					ReferElement refTarget = getReferElement(with);
					
					ConnectionElement conn = createConnection(RelationShip.TRANSITION, refSource, refTarget);
					for (String condition : conditionStack.getCurrentConditions()) {
						conn.getCondition().add(condition);
					}
				}
				else {
					getReferElement(with);	// KJH 20110928
				}
				
				stack.pop();
				current = stack.peek();
			}
		}
	}

	protected void verifyEnumItem(Tree tree) {
		StackNode beforeNode = stack.peek();

		EnumElement parent = (EnumElement)beforeNode.model;
		EnumItemElement child = (EnumItemElement)current.model;

		child.setName(tree.getText());
		
		parent.getEnumItem().add(child);
		child.setParent(parent);
	}

	protected void verifyRelationShip(Tree tree) {

	}

	protected void verifyParameter(Tree tree) {
		StackNode beforeNode = stack.peek();

		ItemElement parent = beforeNode.model;
		Parameter child = (Parameter)current.model;

		child.setType(tree.getChild(0).getText());
		child.setName(tree.getChild(1).getText());

		if(TaskModelPackage.FUNCTION == beforeNode.modelType) {
			((Function)parent).getParams().add(child);
		}
		else if(TaskModelPackage.ACTION_ELEMENT == beforeNode.modelType) {
			((ActionElement)parent).getParams().add(child);
		}
		else if(TaskModelPackage.TASK_ELEMENT == beforeNode.modelType) {
			((BehaviorElement)parent).getParams().add(child);
		}
		else if (TaskModelPackage.CONNECTOR_ELEMENT == beforeNode.modelType) {
			// KJH 20110728, add
			((ConnectorElement)parent).getParams().add(child);
		}
		child.setParent(parent);
	}

	protected void verifyTransition(Tree tree) {
		symbolTable.addScope();

		int tranCount = tree.getChildCount();
		for(int i = 0; i < tranCount; i += 1) {
			Tree stmtTree = tree.getChild(i);
			if(RFSMParser.STMTS == stmtTree.getType()) {
				processSingleStatement(stmtTree);
			}
		}

		symbolTable.removeScope();
	}
	
	protected void processSingleStatement(Tree tree) {
		int sCount = tree.getChildCount();
		for(int sIdx = 0; sIdx < sCount; sIdx += 1) {
			Tree stmt = tree.getChild(sIdx);
	
			if(RFSMParser.STMTBLOCK == stmt.getType()) {
				processBlockStatement(stmt);
			}
			else if(RFSMParser.IF == stmt.getType()) {
				processIfStatement(stmt);
			}
			else if(RFSMParser.GOTO == stmt.getType()) {
				processGotoStatement(stmt);
			}
			else if(RFSMParser.IVK == stmt.getType()) {
				processExpandStatement(stmt);
			}
			else if(RFSMParser.STMTCALL == stmt.getType()) {
				processCallStatement(stmt);
			}
			else if(RFSMParser.STMTEXPR == stmt.getType()) {
				processExprStatement(stmt);
			}
			else if(RFSMParser.LVAR == stmt.getType()) {
				processLocalVariable(stmt);
			}
			else if(RFSMParser.SEQ == stmt.getType()) {
				//
			}
			else if(RFSMParser.PAR == stmt.getType()) {
				//
			}
			else if(RFSMParser.WAIT == stmt.getType()) {
				//
			}
			else if(RFSMParser.SYNCH == stmt.getType()) {
				//
			}
		}
	}
	
	protected void processIfStatement(Tree tree) {
		conditionStack.increaseLevel();

		int childCount = tree.getChildCount();
		for (int i = 0; i < childCount; i++) {
			Tree subTree = tree.getChild(i);
			if (RFSMParser.COND == subTree.getType()) {
				String conditions = TPLTreeUtil.gets(subTree);
				for (String condition : conditions.split("\r\n")) {
					condition = condition.trim();
					if (condition.equals("") == false) {
						conditionStack.addCondition(condition);
					}
				}
//				if (conditions.equals("") == false) {
//					conditionStack.addCondition(conditions);
//				}

				verifyExprCond(subTree);
			}
			else if (RFSMParser.WHENT == subTree.getType()) {
				processBlockStatement(subTree);
			}
			else if (RFSMParser.WHENF == subTree.getType()) {
				conditionStack.increaseLevel();
				processBlockStatement(subTree);
				conditionStack.decreaseLevel();
			}
		}
		
		conditionStack.decreaseLevel();
	}
	
	protected void processExprStatement(Tree blockTree) {
		int sCount = blockTree.getChildCount();
		for(int sIdx = 0; sIdx < sCount; sIdx += 1) {
			Tree stmt = blockTree.getChild(sIdx);
	
			if(RFSMParser.ASMT == stmt.getType()) {
				verifyExpression(stmt);
			}
		}
	}

	protected void processBlockStatement(Tree blockTree) {
		symbolTable.addScope();
		
		int sCount = blockTree.getChildCount();
		for(int sIdx = 0; sIdx < sCount; sIdx += 1) {
			Tree stmt = blockTree.getChild(sIdx);
	
			if(RFSMParser.STMTBLOCK == stmt.getType()) {
				processBlockStatement(stmt);
			}
			else if(RFSMParser.IF == stmt.getType()) {
				processIfStatement(stmt);
			}
			else if(RFSMParser.GOTO == stmt.getType()) {
				processGotoStatement(stmt);
			}
			else if(RFSMParser.IVK == stmt.getType()) {
				processExpandStatement(stmt);
			}
			else if(RFSMParser.STMTCALL == stmt.getType()) {
				processCallStatement(stmt);
			}
			else if(RFSMParser.STMTEXPR == stmt.getType()) {
				processExprStatement(stmt);
			}
			else if(RFSMParser.LVAR == stmt.getType()) {
				processLocalVariable(stmt);
			}
			else if(RFSMParser.SEQ == stmt.getType()) {
				//
			}
			else if(RFSMParser.PAR == stmt.getType()) {
				//
			}
			else if(RFSMParser.WAIT == stmt.getType()) {
				//
			}
			else if(RFSMParser.SYNCH == stmt.getType()) {
				//
			}
		}
		
		symbolTable.removeScope();
	}
	
	protected void processRunStatement(Tree tree) {
		symbolTable.addScope();
		
		int childCount = tree.getChildCount();
		for (int i = 0; i < childCount; i++) {
			Tree stmt = tree.getChild(i);
			if (RFSMParser.STMTS == stmt.getType()) {
				processSingleStatement(stmt);
			}
		}
		
		symbolTable.removeScope();
	}

	protected void processGotoStatement(Tree tree) {
		boolean isStay = false;
		Tree targetTree = null;
		int childCount = tree.getChildCount();
		for (int i = 0; i < childCount; i++) {
			Tree subTree = tree.getChild(i);
			if (RFSMParser.STATE == subTree.getType()) {
				targetTree = subTree.getChild(0);
			}
			else if (RFSMParser.STAY == subTree.getType()) {
				isStay = true;
			}
		}

		if (targetTree == null && isStay == false) {
			return;
		}

		if((this.current.model instanceof StateElement) == false) {
			return;
		}
		
		StateElement source = (StateElement)this.current.model;
		StateElement target = null;
		BehaviorElement task = (BehaviorElement)source.getParent();
		
		if (isStay) {
			target = source;
		}
		else {
			String targetName = TPLTreeUtil.gets(targetTree);
			for(StateElement state : task.getStates()) {
				if(targetName.equals(state.getName())) {
					target = state;
					break;
				}
			}
		}
		
		if(target == null) {
			if(this.logListener != null) {
				CommonToken tokenStart = (CommonToken)tokenStream.get(targetTree.getTokenStartIndex());
				CommonToken tokenEnd = (CommonToken)tokenStream.get(targetTree.getTokenStopIndex());

				int line = targetTree.getLine();
				int charStart = tokenStart.getStartIndex();
				int charEnd = tokenEnd.getStopIndex()+1;
				
				this.logListener.error(String.format("State %s 정보를 찾을 수 없습니다.", targetTree.getText()), line, charStart, charEnd);
			}
			
			return;
		}

		if(this.updateModel == false) {
			return;
		}

		ConnectionElement conn;
		ReferElement refSource, refTarget;
		refSource = getReferElement(source);
		refTarget = getReferElement(target);

		conn = createConnection(RelationShip.TRANSITION, refSource, refTarget);
		for (String condition : conditionStack.getCurrentConditions()) {
			conn.getCondition().add(condition);
		}
	}
	
	protected void processExpandStatement(Tree tree) {
		Tree expandTree = null;
		Tree transTree = null;
		int childCount = tree.getChildCount();
		for (int i = 0; i < childCount; i++) {
			Tree subTree = tree.getChild(i);
			if (RFSMParser.BEHA == subTree.getType()) {
				expandTree = subTree.getChild(0);
			}
			else if (RFSMParser.EOE == subTree.getType()) {
				transTree = subTree.getChild(0);
			}
		}

		if (expandTree == null) {
			return;
		}

		if (transTree == null) {
			processExprCall(expandTree);
			return;
		}

		if((this.current.model instanceof StateElement) == false) {
			return;
		}
		
		StateElement source = (StateElement)this.current.model;
		StateElement target = null;
		BehaviorElement behavior = (BehaviorElement)source.getParent();
		ItemElement realSource = source;
		
		String targetName = TPLTreeUtil.gets(transTree);
		for(StateElement state : behavior.getStates()) {
			if(targetName.equals(state.getName())) {
				target = state;
				break;
			}
		}
		
		// expand & trans
		ExpandTransElement et = getFactory().createExpandTransElement();
		et.setName("Expand & Moveto");
		current = new StackNode(TaskModelPackage.EXPAND_TRANS_ELEMENT, et);
		stack.push(current);

		source.getBifurcates().add(et);
		et.setParent(source);
		realSource = et;

		processExprCall(expandTree);

		stack.pop();
		current = stack.peek();
		
		if(target == null) {
			if(this.logListener != null) {
				CommonToken tokenStart = (CommonToken)tokenStream.get(transTree.getTokenStartIndex());
				CommonToken tokenEnd = (CommonToken)tokenStream.get(transTree.getTokenStopIndex());

				int line = transTree.getLine();
				int charStart = tokenStart.getStartIndex();
				int charEnd = tokenEnd.getStopIndex()+1;
				
				this.logListener.error(String.format("State %s 정보를 찾을 수 없습니다.", transTree.getText()), line, charStart, charEnd);
			}
			
			return;
		}

		if(this.updateModel == false) {
			return;
		}

		ConnectionElement conn;
		ReferElement refSource, refTarget;
		refSource = getReferElement(realSource);
		refTarget = getReferElement(target);

		conn = createConnection(RelationShip.TRANSITION, refSource, refTarget);
//		for (String condition : conditionStack.getCurrentConditions()) {
//			conn.getCondition().add(condition);
//		}
	}

	protected void processLocalVariable(Tree tree) {
		Tree nameTree = null;
		
		int childCount = tree.getChildCount();
		for (int i = 0; i < childCount; i++) {
			Tree subTree = tree.getChild(i);
			
			if (RFSMParser.NAME == subTree.getType()) {
				nameTree = subTree.getChild(0);
			}
		}
		
		if (nameTree == null) {
			return;
		}
		
		String name = TPLTreeUtil.gets(nameTree);
		if(symbolTable.hasSymbol(name)) {
			if(this.logListener != null) {
				CommonToken tokenStart = (CommonToken)tokenStream.get(nameTree.getTokenStartIndex());
				CommonToken tokenEnd = (CommonToken)tokenStream.get(nameTree.getTokenStopIndex());

				int line = nameTree.getLine();
				int charStart = tokenStart.getStartIndex();
				int charEnd = tokenEnd.getStopIndex()+1;

				this.logListener.error(String.format("%s 는 이미 정의되었습니다.", nameTree.getText()), line, charStart, charEnd);
			}
			
			return;
		}

		symbolTable.addSymbol(name);
		
		for (int i = 0; i < childCount; i++) {
			Tree subTree = tree.getChild(i);
			if (RFSMParser.RVAL == subTree.getType()) {
				Tree addTree = subTree.getChild(0);
				if (addTree != null && RFSMParser.ADD == addTree.getType()) {
					verifyExprAdd(addTree);
				}
				else {
					if((tree instanceof CommonErrorNode) == false) {
						unknownCase(addTree);
					}
				}
			}
		}
	}
	
	protected void processCallStatement(Tree tree) {
		int childCount = tree.getChildCount();
		for (int i = 0; i < childCount; i++) {
			Tree subTree = tree.getChild(i);
			if (RFSMParser.CALL == subTree.getType()) {
				processExprCall(subTree);
			}
		}
	}

	protected void processExprCall(Tree tree) {
		Tree symbolTree = null;
		Tree paramTree = null;
		
		int childCount = tree.getChildCount();
		for (int i = 0; i < childCount; i++) {
			Tree subTree = tree.getChild(i);
			if (RFSMParser.CNAME == subTree.getType()) {
				symbolTree = subTree.getChild(0);
			}
			else if (RFSMParser.CPARAMS == subTree.getType()) {
				paramTree = subTree;
			}
		}
		
		if (symbolTree == null || RFSMParser.ID != symbolTree.getType()) {
			return;
		}
		
		// symbol 토큰의 수가 2개 이상이면 Model의 Function호출
		int tokenCount = symbolTree.getChildCount();
		if(tokenCount > 1) {
			Function func = findFunction(symbolTree, paramTree);
			if(func == null) {
				String symbol = TPLTreeUtil.gets(symbolTree);

				StringBuilder sb_ = new StringBuilder();
				sb_.append(symbol).append("(");
				if (paramTree != null) {
					int paramCount = paramTree.getChildCount();
					for(int idx = 0; idx < paramCount; idx += 1) {
						Tree param = paramTree.getChild(idx);
						String token = getSymbolName(param);
						if(idx > 0) {
							sb_.append(", ");
						}
						sb_.append(token);
					}
				}
				sb_.append(")");

				if(this.logListener != null) {
					CommonToken tokenStart = (CommonToken)tokenStream.get(tree.getTokenStartIndex());
					CommonToken tokenEnd = (CommonToken)tokenStream.get(tree.getTokenStopIndex());

					int line = tree.getLine();
					int charStart = tokenStart.getStartIndex();
					int charEnd = tokenEnd.getStopIndex()+1;
					
					this.logListener.error(String.format("Function %s정보를 찾을 수 없습니다.", sb_.toString()), line, charStart, charEnd);
				}
				
				return;
			}
			
			// 함수 이름과 파라미터 이름이 같은 함수를 찾았음.
			// 파라미터에 대입되는 연산에 심볼들을 검증

//			if(paramTree != null) {
//				int paramCount = paramTree.getChildCount();
//				for(int idx = 0; idx < paramCount; idx++) {
//					Tree param = paramTree.getChild(idx);
//					if(RFSMParser.CPARAM == param.getType()) {
//						Tree expr = param.getChild(0);
//						if (expr == null) {
//							continue;
//						}
//						if (RFSMParser.ASMT == expr.getType()) {
//							verifyExpression(expr);
//						}
//						else {
//							if ((expr instanceof CommonErrorNode) == false) {
//								unknownCase(expr);
//							}
//						}
//					}
//					else {
//						if((param instanceof CommonErrorNode) == false) {
//							unknownCase(param);
//						}
//					}
//				}
//			}
			return;
		}
		
		BehaviorElement task = findTask(symbolTree, paramTree);
		if(task != null) {
//			if(paramTree != null) {
//				int paramCount = paramTree.getChildCount();
//				for(int idx = 0; idx < paramCount; idx += 1) {
//					Tree param = paramTree.getChild(idx);
//					if(RFSMParser.CPARAM == param.getType()) {
//						Tree expr = param.getChild(0);
//						if (expr == null) {
//							continue;
//						}
//						if (RFSMParser.ASMT == expr.getType()) {
//							verifyExpression(expr);
//						}
//						else {
//							if ((expr instanceof CommonErrorNode) == false) {
//								unknownCase(expr);
//							}
//						}
//					}
//					else {
//						if((param instanceof CommonErrorNode) == false) {
//							unknownCase(param);
//						}
//					}
//				}
//			}

			if(this.updateModel == false) {
				return;
			}

			ReferElement refSource, refTarget;
			
			ItemElement source = null;
			if(this.current.model instanceof StateElement) {
				source = this.current.model;
			}
			else if(this.current.model instanceof StateAction) {
				StateAction stateAction = (StateAction)this.current.model;
				source = stateAction.getParent();
			}
			else if (this.current.model instanceof ExpandTransElement) {
				source = this.current.model;
			}
			else if (this.current.model instanceof WithElement) {
				source = this.current.model;
			}
			else if (this.current.model instanceof TaskElement) {
				source = this.current.model;
			}
			else {
				return;
			}
			
			refSource = getReferElement(source);
			refTarget = getReferElement(task);
			
			ConnectionElement conn = createConnection(RelationShip.TASK_CALL, refSource, refTarget);
			for (String condition : conditionStack.getCurrentConditions()) {
				conn.getCondition().add(condition);
			}

			return;
		}
		
		ConnectorElement cnt = findConnector(symbolTree, paramTree);
		if(cnt != null) {
//			if(paramTree != null) {
//				int paramCount = paramTree.getChildCount();
//				for(int idx = 0; idx < paramCount; idx += 1) {
//					Tree param = paramTree.getChild(idx);
//					if(RFSMParser.CPARAM == param.getType()) {
//						Tree expr = param.getChild(0);
//						if (expr == null) {
//							continue;
//						}
//						if (RFSMParser.ASMT == expr.getType()) {
//							verifyExpression(expr);
//						}
//						else {
//							if ((expr instanceof CommonErrorNode) == false) {
//								unknownCase(expr);
//							}
//						}
//					}
//					else {
//						if((param instanceof CommonErrorNode) == false) {
//							unknownCase(param);
//						}
//					}
//				}
//			}
			
			if(this.updateModel == false) {
				return;
			}

			ReferElement refSource, refTarget;
			
			ItemElement source = null;
			if(this.current.model instanceof StateElement) {
				source = this.current.model;
			}
			else if(this.current.model instanceof StateAction) {
				StateAction stateAction = (StateAction)this.current.model;
				source = stateAction.getParent();
			}
			else if (this.current.model instanceof ExpandTransElement) {
				source = this.current.model;
			}
			else if (this.current.model instanceof WithElement) {
				source = this.current.model;
			}
			else if (this.current.model instanceof TaskElement) {
				source = this.current.model;
			}
			else {
				return;
			}
			
			refSource = getReferElement(source);
			refTarget = getReferElement(cnt);
			
			ConnectionElement conn = createConnection(RelationShip.TASK_CALL, refSource, refTarget);
			for (String condition : conditionStack.getCurrentConditions()) {
				conn.getCondition().add(condition);
			}

			return;
		}

		String symbol = tokenStream.toString(symbolTree.getTokenStartIndex(), symbolTree.getTokenStopIndex());

		StringBuilder sb_ = new StringBuilder();
		sb_.append(symbol).append("(");
		if (paramTree != null) {
			int paramCount = paramTree.getChildCount();
			for(int idx = 0; idx < paramCount; idx += 1) {
				Tree param = paramTree.getChild(idx);
				String token = getSymbolName(param);
				if(token.length() == 0) {
					continue;
				}
				if(idx > 0) {
					sb_.append(", ");
				}
				sb_.append(token);
			}
		}
		sb_.append(")");

		if(this.logListener != null) {
			CommonToken tokenStart = (CommonToken)tokenStream.get(tree.getTokenStartIndex());
			CommonToken tokenEnd = (CommonToken)tokenStream.get(tree.getTokenStopIndex());

			int line = tree.getLine();
			int charStart = tokenStart.getStartIndex();
			int charEnd = tokenEnd.getStopIndex()+1;

			this.logListener.error(String.format("Behavior 또는 Connector 호출 %s 정보를 찾을 수 없습니다.", sb_.toString()), line, charStart, charEnd);
		}
	}

	protected void verifyExpression(Tree tree) {
		int childCount = tree.getChildCount();
		for (int i = 0; i < childCount; i++) {
			Tree subTree = tree.getChild(i);
			if (RFSMParser.COND == subTree.getType()) {
				verifyExprCond(subTree);
			}
			else if (RFSMParser.ADD == subTree.getType()) {
				verifyExprAdd(subTree);
			}
			else if (RFSMParser.OP == subTree.getType()) {
				;
			}
			else {
				if ((subTree instanceof CommonErrorNode) == false) {
					unknownCase(subTree);
				}
			}
		}
	}

	protected void verifyExprCond(Tree tree) {
		int childCount = tree.getChildCount();
		for (int i = 0; i < childCount; i++) {
			Tree subTree = tree.getChild(i);
			if (RFSMParser.OR == subTree.getType()) {
				verifyExprOr(subTree);
			}
			else if (RFSMParser.ASMT == subTree.getType()) {
				verifyExpression(subTree);
			}
			else if ("?".equals(subTree.getText()) ||
					":".equals(subTree.getText())) {
				;
			}
			else {
				if ((subTree instanceof CommonErrorNode) == false) {
					unknownCase(subTree);
				}
			}
		}
	}

	protected void verifyExprOr(Tree tree) {
		int childCount = tree.getChildCount();
		for (int i = 0; i < childCount; i++) {
			Tree subTree = tree.getChild(i);
			if (RFSMParser.AND == subTree.getType()) {
				verifyExprAnd(subTree);
			}
			else if (RFSMParser.OP == subTree.getType()) {
				;
			}
			else {
				if ((subTree instanceof CommonErrorNode) == false) {
					unknownCase(subTree);
				}
			}
		}
	}

	protected void verifyExprAnd(Tree tree) {
		int childCount = tree.getChildCount();
		for (int i = 0; i < childCount; i++) {
			Tree subTree = tree.getChild(i);
			if (RFSMParser.NOT == subTree.getType()) {
				verifyExprNot(subTree);
			}
			else if (RFSMParser.OP == subTree.getType()) {
				;
			}
			else {
				if ((subTree instanceof CommonErrorNode) == false) {
					unknownCase(subTree);
				}
			}
		}
	}

	protected void verifyExprNot(Tree tree) {
		int childCount = tree.getChildCount();
		for (int i = 0; i < childCount; i++) {
			Tree subTree = tree.getChild(i);
			if (RFSMParser.BOOL == subTree.getType()) {
				verifyExprBool(subTree);
			}
			else if (RFSMParser.OP == subTree.getType()) {
				;
			}
			else {
				if ((subTree instanceof CommonErrorNode) == false) {
					unknownCase(subTree);
				}
			}
		}
	}

	protected void verifyExprBool(Tree tree) {
		int childCount = tree.getChildCount();
		for (int i = 0; i < childCount; i++) {
			Tree subTree = tree.getChild(i);
			if (RFSMParser.ADD == subTree.getType()) {
				verifyExprAdd(subTree);
			}
			else if (RFSMParser.OP == subTree.getType()) {
				;
			}
			else {
				if ((subTree instanceof CommonErrorNode) == false) {
					unknownCase(subTree);
				}
			}
		}
	}

	protected void verifyExprAdd(Tree tree) {
		int childCount = tree.getChildCount();
		for (int i = 0; i < childCount; i++) {
			Tree subTree = tree.getChild(i);
			if (RFSMParser.MUL == subTree.getType()) {
				verifyExprMul(subTree);
			}
			else if (RFSMParser.OP == subTree.getType()) {
				;
			}
			else {
				if ((subTree instanceof CommonErrorNode) == false) {
					unknownCase(subTree);
				}
			}
		}
	}

	protected void verifyExprMul(Tree tree) {
		int childCount = tree.getChildCount();
		for (int i = 0; i < childCount; i++) {
			Tree subTree = tree.getChild(i);
			if (RFSMParser.SIGN == subTree.getType()) {
				verifyExprSign(subTree);
			}
			else if (RFSMParser.OP == subTree.getType()) {
				;
			}
			else {
				if ((subTree instanceof CommonErrorNode) == false) {
					unknownCase(subTree);
				}
			}
		}
	}

	protected void verifyExprSign(Tree tree) {
		int childCount = tree.getChildCount();
		for (int i = 0; i < childCount; i++) {
			Tree subTree = tree.getChild(i);
			if (RFSMParser.SYMB == subTree.getType()) {
				verifySymbol(subTree);
			}
			else if (RFSMParser.LITE == subTree.getType()) {
//				verifySymbol(subTree);
			}
			else if (RFSMParser.KEY == subTree.getType()) {
				
			}
			else if (RFSMParser.CALL == subTree.getType()) {
				processExprCall(subTree);
			}
			else if (RFSMParser.EXPRPAR == subTree.getType()) {
				verifyExprPar(subTree);
			}
			else if (RFSMParser.OP == subTree.getType()) {
				;
			}
			else {
				if ((subTree instanceof CommonErrorNode) == false) {
					unknownCase(subTree);
				}
			}
		}
	}
	
	protected void verifyExprPar(Tree tree) {
		int childCount = tree.getChildCount();
		for (int i = 0; i < childCount; i++) {
			Tree subTree = tree.getChild(i);
			if (RFSMParser.ASMT == subTree.getType()) {
				verifyExpression(subTree);
			}
		}
	}
	
	private BehaviorElement findTask(Tree symbolTree, Tree paramTree) {
		String name = TPLTreeUtil.gets(symbolTree);
		if(name == null || name.length() == 0) {
			return null;
		}
		if(model == null) {
			return null;
		}
		
		String token = TPLTreeUtil.gets(symbolTree);

		// 같은 이름의 Task를 찾는다.
//		TaskElement task = null;
		for(ItemElement item : model.getItems()) {
			if(item instanceof BehaviorElement) {
				if(token.equals(item.getName())) {

					// 파리미터가 같은 가 확인한다.
					List<Parameter> paramList = ((BehaviorElement)item).getParams();
					boolean equalsParam = true;
					if (paramTree != null) {
						int paramCount = paramTree.getChildCount();
						// KJH 20110902 s, modify
						if (paramCount == paramList.size()) {
							for(int idx = 0; idx < paramCount; idx += 1) {
								Tree param = paramTree.getChild(idx);
								param = getTree(param, RFSMParser.COND);
								String paramToken = getSymbolName(param);
								Parameter paramItem = paramList.get(idx);
								if(paramToken.equals(paramItem.getName()) == false) {
									equalsParam = false;
									break;
								}
							}
						}	// KJH 20110902 e, modify
						else {
							equalsParam = false;
						}
					}
					else {
						if (paramList.size() > 0) {
							equalsParam = false;
						}
					}
					
					if(equalsParam) {
						return (BehaviorElement)item;
					}
				}
			}
		}

		// Included된 Task가 있는지 찾아본다.
		for(IncludedElement incItem : model.getIncludeItems()) {
			BehaviorElement item = findTask(incItem, symbolTree, paramTree);
			if(item != null){
				return item;
			}
		}

		return null;
	}

	private BehaviorElement findTask(IncludedElement parentItem, Tree symbolTree, Tree paramTree) {
		String name = TPLTreeUtil.gets(symbolTree);
		if(name == null || name.length() == 0) {
			return null;
		}
		
		for(ItemElement item : parentItem.getItems()) {
			if(item instanceof BehaviorElement) {
				if(name.equals(item.getName())) {
					
					// 파리미터가 같은 가 확인한다.
					List<Parameter> paramList = ((BehaviorElement)item).getParams();
					boolean equalsParam = true;
					if (paramTree != null) {
						int paramCount = paramTree.getChildCount();
						// KJH 20110902 s, modify
						if (paramCount == paramList.size()) {
							for(int idx = 0; idx < paramCount; idx++) {
								Tree param = paramTree.getChild(idx);
								param = getTree(param, RFSMParser.COND);
								String paramToken = getSymbolName(param);
								Parameter paramItem = paramList.get(idx);
								if(paramToken.equals(paramItem.getName()) == false) {
									equalsParam = false;
									break;
								}
							}
						}	// KJH 20110902 e, modify
						else {
							equalsParam = false;
						}
					}
					else {
						if (paramList.size() > 0) {
							equalsParam = false;
						}
					}
					
					if(equalsParam) {
						return (BehaviorElement)item;
					}
				}
			}
			else if(item instanceof IncludedElement) {
				BehaviorElement task = findTask((IncludedElement)item, symbolTree, paramTree);
				if(task != null){
					return task;
				}
			}
		}

		return null;
	}

	private BehaviorElement findInitialTask(String taskName) {
		if(model == null) {
			return null;
		}

		// 같은 이름의 Task를 찾는다.
//		TaskElement task = null;
		for(ItemElement item : model.getItems()) {
			if(item instanceof BehaviorElement) {
				if(taskName.equals(item.getName())) {
					// Initial Task가 가능한 TaskElement는 Parameter가 없다.
					if(((BehaviorElement)item).getParams().size() > 0) {
						continue;
					}
					
					return (BehaviorElement)item;
				}
			}
		}

		// Included된 Task가 있는지 찾아본다.
		for(IncludedElement incItem : model.getIncludeItems()) {
			BehaviorElement item = findInitialTask(incItem, taskName);
			if(item != null){
				return item;
			}
		}

		return null;
	}

	private BehaviorElement findInitialTask(IncludedElement parentItem, String taskName) {

		for(ItemElement item : parentItem.getItems()) {
			if(item instanceof BehaviorElement) {
				if(taskName.equals(item.getName())) {
					// Initial Task가 가능한 TaskElement는 Parameter가 없다.
					if(((BehaviorElement)item).getParams().size() > 0) {
						continue;
					}
					
					return (BehaviorElement)item;
				}
			}
			else if(item instanceof IncludedElement) {
				BehaviorElement task = findInitialTask((IncludedElement)item, taskName);
				if(task != null){
					return task;
				}
			}
		}

		return null;
	}

	private ConnectorElement findConnector(Tree symbolTree, Tree paramTree) {
		String name = TPLTreeUtil.gets(symbolTree);
		if(name == null || name.length() == 0) {
			return null;
		}
		if(model == null) {
			return null;
		}
		
		String token = TPLTreeUtil.gets(symbolTree);

		for(ItemElement item : model.getItems()) {
			if(item instanceof ConnectorElement) {
				if(token.equals(item.getName())) {

					// 파리미터가 같은 가 확인한다.
					List<Parameter> paramList = ((ConnectorElement)item).getParams();
					boolean equalsParam = true;
					if (paramTree != null) {
						int paramCount = paramTree.getChildCount();
						// KJH 20110902 s, modify
						if (paramCount == paramList.size()) {
							for(int idx = 0; idx < paramCount; idx += 1) {
								Tree param = paramTree.getChild(idx);
								param = getTree(param, RFSMParser.COND);
								String paramToken = getSymbolName(param);
								Parameter paramItem = paramList.get(idx);
								if(paramToken.equals(paramItem.getName()) == false) {
									equalsParam = false;
									break;
								}
							}
						}	// KJH 20110902 e, modify
						else {
							equalsParam = false;
						}
					}
					else {
						if (paramList.size() > 0) {
							equalsParam = false;
						}
					}
					
					if(equalsParam) {
						return (ConnectorElement)item;
					}
				}
			}
		}

		// Included된 Task가 있는지 찾아본다.
		for(IncludedElement incItem : model.getIncludeItems()) {
			ConnectorElement item = findConnector(incItem, symbolTree, paramTree);
			if(item != null){
				return item;
			}
		}

		return null;
	}

	private ConnectorElement findConnector(IncludedElement parentItem, Tree symbolTree, Tree paramTree) {
		String name = TPLTreeUtil.gets(symbolTree);
		if (name == null || name.length() == 0) {
			return null;
		}
		
		for(ItemElement item : parentItem.getItems()) {
			if(item instanceof ConnectorElement) {
				if(name.equals(item.getName())) {
					
					// 파리미터가 같은 가 확인한다.
					List<Parameter> paramList = ((ConnectorElement)item).getParams();
					boolean equalsParam = true;
					if (paramTree != null) {
						int paramCount = paramTree.getChildCount();
						// KJH 20110902 s, modify
						if (paramCount == paramList.size()) {
							for(int idx = 0; idx < paramCount; idx += 1) {
								Tree param = paramTree.getChild(idx);
								param = getTree(param, RFSMParser.COND);
								String paramToken = getSymbolName(param);
								Parameter paramItem = paramList.get(idx);
								if(paramToken.equals(paramItem.getName()) == false) {
									equalsParam = false;
									break;
								}
							}
						}	// KJH 20110902 e, modify
						else {
							equalsParam = false;
						}
					}
					else {
						if (paramList.size() > 0) {
							equalsParam = false;
						}
					}
					
					if(equalsParam) {
						return (ConnectorElement)item;
					}
				}
			}
			else if(item instanceof IncludedElement) {
				ConnectorElement task = findConnector((IncludedElement)item, symbolTree, paramTree);
				if(task != null){
					return task;
				}
			}
		}

		return null;
	}

	private ActionElement findAction(Tree symbolTree, Tree paramTree) {
		String name = symbolTree.getText();
		if("EXPRSYMBOL".equals(name) == false) {
			return null;
		}
		
		if(model == null) {
			return null;
		}

		Tree tokenTree = symbolTree.getChild(0);
		String token = tokenTree.getText();

		for(ItemElement item : model.getItems()) {
			if(item instanceof ActionElement) {
				if(token.equals(item.getName())) {
					// 파리미터가 같은 가 확인한다.
					List<Parameter> paramList = ((ActionElement)item).getParams();
					int paramCount = paramTree.getChildCount();
					
					if(paramList.size() == paramCount) {
						boolean equalsParam = true;
						for(int idx = 0; idx < paramCount; idx += 1) {
							Tree param = paramTree.getChild(idx);
							param = getTree(param, RFSMParser.COND);
							String paramToken = getSymbolName(param);
							if(paramToken == null || paramToken.length() == 0) {
								equalsParam = false;
								break;
							}
							Parameter paramItem = paramList.get(idx);
							if(paramToken.equals(paramItem.getName()) == false) {
								equalsParam = false;
								break;
							}
						}
						
						if(equalsParam) {
							return (ActionElement)item;
						}
					}
				}
			}
		}
		
		for(IncludedElement incItem : model.getIncludeItems()) {
			ActionElement action = findAction(incItem, symbolTree, paramTree);
			if(action != null){
				return action;
			}
		}

		return null;
	}

	private ActionElement findAction(IncludedElement parentItem, Tree symbolTree, Tree paramTree) {
		String name = symbolTree.getText();
		if("EXPRSYMBOL".equals(name) == false) {
			return null;
		}
		Tree tokenTree = symbolTree.getChild(0);
		String token = tokenTree.getText();

		for(ItemElement item : parentItem.getItems()) {
			if(item instanceof ActionElement) {
				if(token.equals(item.getName())) {
					// 파리미터가 같은 가 확인한다.
					List<Parameter> paramList = ((ActionElement)item).getParams();
					int paramCount = paramTree.getChildCount();
					boolean equalsParam = true;
					for(int idx = 0; idx < paramCount; idx += 1) {
						Tree param = paramTree.getChild(idx);
						param = getTree(param, RFSMParser.COND);
						String paramToken = getSymbolName(param);
						if(paramToken == null || paramToken.length() == 0) {
							equalsParam = false;
							break;
						}
						Parameter paramItem = paramList.get(idx);
						if(paramToken.equals(paramItem.getName()) == false) {
							equalsParam = false;
							break;
						}
					}
					
					if(equalsParam) {
						return (ActionElement)item;
					}
				}
			}
			else if(item instanceof IncludedElement) {
				ActionElement action = findAction((IncludedElement)item, symbolTree, paramTree);
				if(action != null){
					return action;
				}
			}
		}

		return null;
	}

	private Function findFunction(Tree symbolTree, Tree paramTree) {
		Tree tokenTree = symbolTree.getChild(0);
		String token = TPLTreeUtil.gets(tokenTree);
		ModelElement modelElmt = findModelElement(token);
		if(modelElmt == null && this.logListener != null) {
			CommonToken tokenStart = (CommonToken)tokenStream.get(symbolTree.getTokenStartIndex());
			CommonToken tokenEnd = (CommonToken)tokenStream.get(symbolTree.getTokenStopIndex());

			int line = symbolTree.getLine();
			int charStart = tokenStart.getStartIndex();
			int charEnd = tokenEnd.getStopIndex()+1;

			this.logListener.error(String.format("Model %s정보를 찾을 수 없습니다.", token), line, charStart, charEnd);
//			Throwable t = new Throwable();
//			StackTraceElement[] traceElmt = t.getStackTrace();
//			this.logListener.error(traceElmt[0].toString());
			return null;
		}

		int tokenCount = symbolTree.getChildCount();
		for(int idx = 1; idx < (tokenCount-1); idx++) {
			tokenTree = symbolTree.getChild(idx);
			token = tokenTree.getText();
			if (RFSMParser.Identifier != tokenTree.getType()) {
				continue;
			}
			modelElmt = findModelElement(modelElmt, token);
			if(modelElmt == null && this.logListener != null) {
				CommonToken tokenStart = (CommonToken)tokenStream.get(symbolTree.getTokenStartIndex());
				CommonToken tokenEnd = (CommonToken)tokenStream.get(symbolTree.getTokenStopIndex());

				int line = symbolTree.getLine();
				int charStart = tokenStart.getStartIndex();
				int charEnd = tokenEnd.getStopIndex()+1;

				this.logListener.error(String.format("Model %s정보를 찾을 수 없습니다.", token), line, charStart, charEnd);
//				Throwable t = new Throwable();
//				StackTraceElement[] traceElmt = t.getStackTrace();
//				this.logListener.error(traceElmt[0].toString());
				return null;
			}
		}
		
		tokenTree = symbolTree.getChild(tokenCount-1);
		token = tokenTree.getText();
		Function func = findFunction(modelElmt, token, paramTree);
		
		return func;
	}

	private Function findFunction(ModelElement parentModel, String funcName, Tree paramTree) {
		if(funcName == null || funcName.length() == 0) {
			return null;
		}

		Function func = null;
		for(Function item : parentModel.getFunctions()) {
			if(funcName.equals(item.getName())) {
				func = item;
				break;
			}
		}
		
		if(func == null) {
			return null;
		}

		List<Parameter> paramList = func.getParams();
		if (paramTree != null) {
			int paramCount = paramTree.getChildCount();
			// KJH 20110902 s, modify
			if (paramCount == paramList.size()) {
				for(int idx = 0; idx < paramCount; idx += 1) {
					Tree param = paramTree.getChild(idx);
					param = getTree(param, RFSMParser.COND);
					String token = getSymbolName(param);
					Parameter paramItem = paramList.get(idx);
					if(token.equals(paramItem.getName()) == false) {
						return null;
					}
				}
			}	// KJH 20110902 e, modify
			else {
				return null;
			}
		}
		else {
			if (paramList.size() > 0) {
				return null;
			}
		}

		return func;
	}

	private EnumElement findEnumElement(String elmtName) {
		if(elmtName == null || elmtName.length() == 0) {
			return null;
		}
		
		if(model == null) {
			return null;
		}

		for(ItemElement item : model.getItems()) {
			if(item instanceof EnumElement) {
				if(elmtName.equals(item.getName())) {
					return (EnumElement)item;
				}
			}
		}

		for(IncludedElement incItem : model.getIncludeItems()) {
			EnumElement enumElmt = findEnumElement(incItem, elmtName);
			if(enumElmt != null){
				return enumElmt;
			}
		}

		return null;
	}

	private EnumElement findEnumElement(IncludedElement parentItem, String elmtName) {
		if(elmtName == null || elmtName.length() == 0) {
			return null;
		}
		
		for(ItemElement item : parentItem.getItems()) {
			if(item instanceof EnumElement) {
				if(elmtName.equals(item.getName())) {
					return (EnumElement)item;
				}
			}
			else if(item instanceof IncludedElement) {
				EnumElement enumElmt = findEnumElement((IncludedElement)item, elmtName);
				if(enumElmt != null){
					return enumElmt;
				}
			}
		}

		return null;
	}

	private ModelElement findModelElement(String modelName) {
		if(modelName == null || modelName.length() == 0) {
			return null;
		}
		
		if(model == null) {
			return null;
		}

		for(ItemElement item : model.getItems()) {
			if(item instanceof ModelElement) {
				if(modelName.equals(item.getName())) {
					return (ModelElement)item;
				}
			}
		}

		for(IncludedElement incItem : model.getIncludeItems()) {
			ModelElement modelElmt = findModelElement(incItem, modelName);
			if(modelElmt != null){
				return modelElmt;
			}
		}

		return null;
	}

	private ModelElement findModelElement(IncludedElement parentItem, String modelName) {
		if(modelName == null || modelName.length() == 0) {
			return null;
		}
		
		for(ItemElement item : parentItem.getItems()) {
			if(item instanceof ModelElement) {
				if(modelName.equals(item.getName())) {
					return (ModelElement)item;
				}
			}
		}
		
		for(ItemElement item : parentItem.getItems()) {
			if(item instanceof IncludedElement) {
				ModelElement modelElmt = findModelElement((IncludedElement)item, modelName);
				if(modelElmt != null){
					return modelElmt;
				}
			}
		}

		return null;
	}

	private ModelElement findModelElement(ModelElement parentModel, String modelName) {
		if(modelName == null || modelName.length() == 0) {
			return null;
		}

		for(ModelElement childModel : parentModel.getModels()) {
			if(modelName.equals(childModel.getName())) {
				return childModel;
			}
		}

		return null;
	}

	private String getSymbolName(Tree paramTree) {
		return TPLTreeUtil.gets(paramTree);
	}
	
	protected void verifySymbol(Tree tree) {
		Tree subTree = tree.getChild(0);
		if (RFSMParser.ID == subTree.getType()) {
			verifyIdentifier(subTree);
		}
	}
	
	private void verifyIdentifier(Tree tree) {
		int childCount = tree.getChildCount();
		
		if(childCount == 0) {
			return;
		}
		// symbolCount == 1 인 경우는 로컬 변수(파라미터 변수 포함), Action, Task
		if(childCount == 1) {
			Tree token = tree.getChild(0);
			// 우선 로컬 변수 테이블에 있는거 검사한다.

			if(this.symbolTable.hasSymbol(token.getText())) {
				return;
			}
			
			if(this.logListener != null) {
				CommonToken tokenStart = (CommonToken)tokenStream.get(token.getTokenStartIndex());
				CommonToken tokenEnd = (CommonToken)tokenStream.get(token.getTokenStopIndex());

				int line = token.getLine();
				int charStart = tokenStart.getStartIndex();
				int charEnd = tokenEnd.getStopIndex()+1;

				this.logListener.error(String.format("Symbol %s 정보를 찾을 수 없습니다.", token.getText()), line, charStart, charEnd);
				return;
			}

			this.symbolTable.increaseError();
		}
		else {
			// Enum 또는 Model의 Elememt인가 검사한다.
			ModelElement modelElmt = null;
			Tree token = tree.getChild(0);
			String tokenString = token.getText();
			modelElmt = this.findModelElement(tokenString);

			if(modelElmt != null) {
				for(int idx = 1; idx < (childCount - 1); idx += 1) {
					token = tree.getChild(idx);
					if (RFSMParser.Identifier == token.getType()) { 
						tokenString = token.getText();
						modelElmt = this.findModelElement(modelElmt, tokenString);
					}
				}
				
				token = tree.getChild(childCount - 1);
				if (RFSMParser.Identifier == token.getType()) {
					tokenString = token.getText();
					List<Constant> constList = modelElmt.getConstants();
					for(Constant constItem : constList) {
						if(tokenString.equals(constItem.getName())) {
							return;
						}
					}
					List<Symbol> symList = modelElmt.getSymbols();
					for(Symbol symItem : symList) {
						if(tokenString.equals(symItem.getName())) {
							return;
						}
					}
					List<Function> funcList = modelElmt.getFunctions();
					for(Function funcItem : funcList) {
						if(tokenString.equals(funcItem.getName())) {
							// ????????? Call이어야 하는데
							return;
						}
					}
				}
			}

			EnumElement enumElement = null;
			enumElement = this.findEnumElement(tokenString);

			if(enumElement != null) {
				token = null;
				for(int idx = 1; idx < childCount; idx += 1) {
					token = tree.getChild(idx);
					if (RFSMParser.Identifier == token.getType()) {
						break;
					}
				}
				
				if (token != null) {
					tokenString = token.getText();

					List<EnumItemElement> itemList = enumElement.getEnumItem();
					for(EnumItemElement itemElmt : itemList) {
						if(tokenString.equals(itemElmt.getName())) {
							return;
						}
					}
				}
			}

			if(this.logListener != null) {
				String lineString = tokenStream.toString(tree.getTokenStartIndex(), tree.getTokenStopIndex());
				CommonToken tokenStart = (CommonToken)tokenStream.get(tree.getTokenStartIndex());
				CommonToken tokenEnd = (CommonToken)tokenStream.get(tree.getTokenStopIndex());

				int line = tree.getLine();
				int charStart = tokenStart.getStartIndex();
				int charEnd = tokenEnd.getStopIndex()+1;

				this.logListener.error(String.format("Symbol %s 정보를 찾을 수 없습니다.", lineString), line, charStart, charEnd);
				
			}
		}
	}
	
	private ReferElement createReferElement(ItemElement item) {
		if(item == null) {
			return null;
		}
		ReferElement refItem = this.getFactory().createReferElement();
		refItem.setRealModel(item);
		item.getReferences().add(refItem);
		
		refItem.setVisible(true);
		refItem.setCollapsed(true);
		refItem.setX(5);
		refItem.setY(5);
		refItem.setX2(5);
		refItem.setY2(5);
		if(item instanceof BehaviorElement) {
			refItem.setWidth(120);
			refItem.setHeight(50);
			refItem.setWidth2(300);
			refItem.setHeight2(200);
		}
		else if (item instanceof ConnectorElement) {
			refItem.setWidth(120);
			refItem.setHeight(50);
			refItem.setWidth2(200);
			refItem.setHeight2(150);
		}
		else if (item instanceof StateElement) {
			refItem.setWidth(100);
			refItem.setHeight(55);
			refItem.setWidth2(100);
			refItem.setHeight2(55);
		}
		else if (item instanceof WithElement) {
			refItem.setWidth(20);
			refItem.setHeight(20);
			refItem.setWidth2(20);
			refItem.setHeight2(20);
		}
		else if (item instanceof ExpandTransElement) {
			refItem.setWidth(15);
			refItem.setHeight(15);
			refItem.setWidth2(15);
			refItem.setHeight2(15);
		}
		else if (item instanceof TaskElement) {
			refItem.setWidth(120);
			refItem.setHeight(50);
			refItem.setWidth2(150);
			refItem.setHeight2(100);
		}
		else {
			refItem.setWidth(80);
			refItem.setHeight(40);
			refItem.setWidth2(80);
			refItem.setHeight2(40);
		}
		
		return refItem;
	}

	private ConnectionElement createConnection(RelationShip relation, ReferElement source, ReferElement target) {
		ConnectionElement conn = this.getFactory().createConnectionElement();
		
		// connection name
		int num = source.getSourceConnections().size() + 1;
		if (source.getRealModel() instanceof ExpandTransElement) {
			ItemElement parent = source.getParent();
			ItemElement realParent = source.getRealModel().getParent();
			num = 1;
			if (parent instanceof ReferElement) {
				num += ((ReferElement)parent).getSourceConnections().size();
			}
			if (realParent instanceof StateElement) {
				num += ((StateElement)realParent).getBifurcates().size() - 1;
			}
		}
		conn.setName(Integer.toString(num));
		
		conn.setRelationship(relation);

		ReferElement conSource = source;
		if (RelationShip.TRANSITION != relation) {
			ItemElement realModel = source.getRealModel();
			while (realModel instanceof BehaviorElement == false &&
					realModel instanceof ConnectorElement == false &&
					realModel instanceof TaskElement == false) {
				conSource = (ReferElement)conSource.getParent();
				realModel = conSource.getRealModel();
			}
//			if(realModel instanceof StateElement) {
//				conSource = (ReferElement)source.getParent();
//			}
//			else if (realModel instanceof WithElement) {
//				conSource = (ReferElement)source.getParent();
//			}
//			else if (realModel instanceof ExpandTransElement) {
//				conSource = (ReferElement)((ReferElement)source.getParent()).getParent();
//			}
		}
		conSource.getSourceConnections().add(conn);
		conn.setSource(conSource);
		conn.setSource2(source);

		target.getTargetConnections().add(conn);
		conn.setTarget(target);
		conn.setTarget2(target);

		if(RelationShip.TASK_CALL == relation) {
			conn.setLineStyle(LineStyle.LINE_SOLID);
			if (conSource.equals(source) == false &&	/* KJH 20111006 */
					(conSource.getRealModel() instanceof TaskElement ||
					conSource.getRealModel() instanceof BehaviorElement ||
					conSource.getRealModel() instanceof ConnectorElement)) {
				conn.setSourceEndPoint(LineEndPoint.OPENED_CIRCLE);
			} else {
				conn.setSourceEndPoint(LineEndPoint.NONE);
			}
			conn.setTargetEndPoint(LineEndPoint.CLOSED_TRIANGLE);
		}
		else if(RelationShip.ACTION_CALL == relation) {
			conn.setLineStyle(LineStyle.LINE_DASH);
			conn.setSourceEndPoint(LineEndPoint.NONE);
			conn.setTargetEndPoint(LineEndPoint.OPENED_ARROW);
		}
		else if(RelationShip.TRANSITION == relation) {
			if (conSource.getRealModel() instanceof WithElement) {	// KJH 20110721 e, with사이의 transition은 dash
				conn.setLineStyle(LineStyle.LINE_DASH);
			} else {
				conn.setLineStyle(LineStyle.LINE_SOLID);
			}
			conn.setSourceEndPoint(LineEndPoint.NONE);
			conn.setTargetEndPoint(LineEndPoint.OPENED_ARROW);
			conn.setVisible(false);
		}

		return conn;		
	}

	private SubDiagram getSubDiagram(ItemElement item) {
		if(item == null) {
			return null;
		}
		
		SubDiagram subDiagram = item.getSubDiagram();
		if(subDiagram == null) {
			subDiagram = this.getFactory().createSubDiagram();
			subDiagram.setParent(item);
			item.setSubDiagram(subDiagram);
		}
		
		return subDiagram;
	}

	private ReferElement getReferElement(ItemElement item) {
		if(item == null) {
			return null;
		}
		
		ReferElement refItem = null;
		List<ReferElement> refList = item.getReferences();
		if(refList.size() == 0) {
			refItem = createReferElement(item);
			ItemElement parent = item.getParent();
			if(parent instanceof ModelDiagram) {
				SubDiagram modelDiagram = getSubDiagram(this.model);
				modelDiagram.getItems().add(refItem);
				refItem.setParent(modelDiagram);
			}
			else {
				ReferElement refParent = getReferElement(parent);
				refParent.getItems().add(refItem);
				refItem.setParent(refParent);
			}
		}
		else {
			refItem = refList.get(0);
		}
		
		return refItem;
	}
	
	private void unknownCase(Tree tree) {
		if(this.logListener != null) {
			String lineString = tokenStream.toString(tree.getTokenStartIndex(), tree.getTokenStopIndex());
			CommonToken tokenStart = (CommonToken)tokenStream.get(tree.getTokenStartIndex());
			CommonToken tokenEnd = (CommonToken)tokenStream.get(tree.getTokenStopIndex());

			int line = tree.getLine();
			int charStart = tokenStart.getStartIndex();
			int charEnd = tokenEnd.getStopIndex()+1;

			this.logListener.error(String.format("예상치 못한 토큰 : %s", lineString), line, charStart, charEnd);
		}
	}
	
	private Tree getTree(Tree parentTree, int type) {
		Tree result = null;
		for (int i = 0; i < parentTree.getChildCount(); i++) {
			Tree subTree = parentTree.getChild(i);
			if (type == subTree.getType()) {
				return subTree;
			}
			else {
				result = getTree(subTree, type);
			}
			
			if (result != null) {
				break;
			}
		}
		return result;
	}
}
