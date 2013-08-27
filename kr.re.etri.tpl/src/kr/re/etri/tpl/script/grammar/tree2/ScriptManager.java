package kr.re.etri.tpl.script.grammar.tree2;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import kr.re.etri.tpl.IRTMDefines;
import kr.re.etri.tpl.diagram.util.MarkerProvider;
import kr.re.etri.tpl.grammar.RFSMParser;
import kr.re.etri.tpl.grammar.RTMLexer;
import kr.re.etri.tpl.grammar.RTMParser;
import kr.re.etri.tpl.script.editors.TPLTextEditorInput;
import kr.re.etri.tpl.script.utils.TPLTreeUtil;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonErrorNode;
import org.antlr.runtime.tree.CommonTree;
import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.ide.FileStoreEditorInput;

public class ScriptManager {

	private static ScriptManager manager = new ScriptManager();
	private static Logger logger = Logger.getLogger(ScriptManager.class);
	
	/*
	 * KJH 20100706
	 * key value : IDocument -> Object로 수정 (IFile 이용)
	 * document가 파일 열때마다 주소값이 달라져 같은 파일이더라도 다른 document로 되어,
	 * global include 표현시 해당 파일의 트리구조가 나타나지 않는 문제가 있었음
	 */
	private Map<String, IScriptNode> roots;	// KJH 20110214, key:resource->string
	private IDocument document;
	private Stack<IDocument> stack;
	
	private IProject project;
	

	public static ScriptManager getInstance(){
		return manager;
	}
	
	public ScriptManager(){
		roots = new HashMap<String, IScriptNode>();	// KJH 20110214, key:resource->string
		stack = new Stack<IDocument>();
	}

	public IScriptNode getTree(IFile file, IDocument document) {
		if (file == null) {
			return null;
		}
		
		String filePath = file.getFullPath().toString();
		IScriptNode root = roots.get(filePath);	// KJH 20110214, key:resource->string

		if(root == null){
			if (document == null) {
				document = FileManager.getDocument(file);
			}
			root = createTree(filePath, file, document, -1, -1, -1);	// KJH 20110809
		}
		
		return root;
	}
	
	public IScriptNode getTree(IEditorInput editorInput, IDocument document) {
		String filePath = getFilePath(editorInput);
		IFile file = null;
		if (editorInput instanceof IFileEditorInput) {
			file = ((IFileEditorInput)editorInput).getFile();
		}
		
		IScriptNode root = roots.get(filePath);
		if (root == null) {
			root = createTree(filePath, file, document, -1, -1, -1);
		}
		return root;
	}
	
	public IScriptNode getTree(IEditorInput editorInput, IDocument document, int offset, int length, int moveTo){
		String filePath = getFilePath(editorInput);
		IFile file = null;
		if (editorInput instanceof IFileEditorInput) {
			file = ((IFileEditorInput)editorInput).getFile();
		}
		
		return createTree(filePath, file, document, offset, length, moveTo);
	}
	
	private String getFilePath(IEditorInput editorInput) {
		if (editorInput instanceof IFileEditorInput) {
			IFile file = ((IFileEditorInput)editorInput).getFile();
			if (file != null) {
				return file.getFullPath().toString();
			}
		}
		if (editorInput instanceof FileStoreEditorInput) {
			URI uri = ((FileStoreEditorInput)editorInput).getURI();
			if (uri != null) {
				String str = uri.getPath();
				if (str.startsWith("/")) {
					str = str.substring(1);
				}
				return str;
			}
		}
		if (editorInput instanceof TPLTextEditorInput) {	// KJH 20110915, remote
			IPath path = ((TPLTextEditorInput)editorInput).getPath();
			if (path != null) {
				return "REMOTE:" + path.toString(); 
			}
		}
		
		if (editorInput != null) {
			return editorInput.getName();
		}
		return "";
	}
	
	/*
	 * KJH 20100709, 프로젝트 전체 tpl파일 목록을 구현하는 함수
	 * (non-Javadoc)
	 * @see kr.re.etri.tpl.script.grammar.tree2.IScriptManager#getTPLList(org.eclipse.core.resources.IResource, kr.re.etri.tpl.script.grammar.tree2.IScriptNode)
	 */
	public IScriptNode getTPLList(IResource resource){
		if (resource == null) {	// KJH 20110809
			return null;
		}
		
		IProject project = resource.getProject();
		
		if(!project.exists() || !project.isAccessible())
			return null;

		IScriptNode root = roots.get(project.getFullPath().toString());	// KJH 20110214, key:resource->string
		if(root == null)
			root = new ScriptNode();
		
		List<IFile> members = FileManager.getFiles(project, IRTMDefines.TASK_SCRIPT_EXTENSION_NAME);
		List<IScriptNode> remList = new ArrayList<IScriptNode>();
		remList.addAll(root.getChildren());

		for(IFile member : members){
			String tplName = member.getFullPath().removeFirstSegments(1).toString();
//			String tplName = member.getFullPath().toString();
			if(tplName.startsWith("/"))
				tplName = tplName.substring(1);
			IScriptNode node = getTree(member, null);
			if (node == null) {
				continue;
			}
			
			node.setName(tplName);
			if (node instanceof IScriptRootNode
					&& resource instanceof IFile /* KJH 20111124 */) {
				if(member.equals(resource)) {
					((IScriptRootNode)node).setGlobal(false);
				} else {
					((IScriptRootNode)node).setGlobal(true);
				}
			}

//			if(node != null && node.getChildrenCount() > 0 && !root.contains(node))	// KJH 20110621
			if (!root.contains(node)) {
				root.addChild(node);
			}
			
			if(remList.contains(node)) {
				remList.remove(node);
			}
		}
		
		root.getChildren().removeAll(remList);
		roots.put(project.getFullPath().toString(), root);	// KJH 20110214, key:resource->string
		
		return root;
	}
	
	public IScriptNode getTPLList(IEditorInput editorInput, IDocument document) {
		IScriptNode root = new ScriptNode();
		
		IScriptNode node = getTree(editorInput, document);
		if (node != null) {
			root.addChild(node);
		}
		return root;
	}

	public List<IScriptNode> getTasks(IResource resource) {
		IScriptNode root = getTPLList(resource);
		List<IScriptNode> list = new ArrayList<IScriptNode>();
		
		for (IScriptNode child : root.getChildren()) {
			if (child instanceof IScriptRootNode) {
				list.addAll(getNodes(child, RFSMParser.WORKER2));
			} else if (child instanceof IWorkerNode) {
				list.add(child);
			}
		}
		
		return list;
	}
	
	// KJH 20110908 s
	public List<IScriptNode> getModels(IResource resource) {
		IScriptNode root = getTPLList(resource);
		List<IScriptNode> list = new ArrayList<IScriptNode>();
		
		for (IScriptNode child : root.getChildren()) {
			if (child instanceof IScriptRootNode) {
				list.addAll(getNodes(child, RFSMParser.MODEL));
			} else if (child instanceof IModelNode) {
				list.add(child);
			}
		}
		
		return list;
	}
	
	/*
	 * start와 type이 같은 child node를 구함
	 */
	// KJH 20110207 s, fix
	private IScriptNode getNode(IScriptNode parent, int start, int type){
		for(IScriptNode node : parent.getChildren()){
			if (node == null)	continue;
			if(type == node.getType() && start == node.getStart()) {
				return node;
			}
		}
		return null;
	}	// KJH 20110207 e, fix
	
	// KJH 20110207 s, fix
	private List<IScriptNode> getNodes(IScriptNode parent, int type){
		List<IScriptNode> list = new ArrayList<IScriptNode>();

		for(IScriptNode node : parent.getChildren()){
			if(type == node.getType())
				list.add(node);
		}
		
		return list;
	}	// KJH 20110207 e, fix
	
	private int getOffset(int line, int position){
		if(line <= 0) {
			return -1;
		}
		if (this.document == null) {
			return -1;
		}
		
		int offset;
		try {
			offset = document.getLineOffset(line-1) + position;
		} catch (BadLocationException e) {
			logger.warn("Can not get line offset.", e);
			return -1;
		} catch (NullPointerException e) {
			logger.warn("Current document is null.", e);
			return -1;
		}
		
		return offset;
	}
	
	/*
	 * KJH 20100709, 
	 * (non-Javadoc)
	 * @see kr.re.etri.tpl.script.grammar.tree2.IScriptManager#setActiveNode(kr.re.etri.tpl.script.grammar.tree2.IScriptNode)
	 */
	public void setActiveNode(IScriptNode root){
		for(IScriptNode child : roots.values()) {
			if(child instanceof IScriptRootNode) {
				((IScriptRootNode)child).setGlobal(true);
			}
		}
		if(root instanceof IIncludeNode) {
			((IScriptRootNode)root).setGlobal(false);
		}
	}
	
	public IScriptNode getActiveNode(){
		for(IScriptNode child : roots.values()){
			if(child instanceof IScriptRootNode &&
					((IScriptRootNode)child).isGlobal() == false) {
				return child;
			}
		}
		return null;
	}
	
	private IScriptNode createTree(String filePath, IFile file, IDocument document, int offset, int length, int moveTo){
		IScriptNode root = null;
		
		try {
			InputStream stream = null;
			if (document != null) {
				stream = new ByteArrayInputStream(document.get().getBytes());
			}
			else if (file != null) {
				stream = file.getContents();
			}

			stack.push(this.document);
			this.document = document;
			if (file != null) {	// KJH 20110809
				this.project = file.getProject();
			}

			// KJH 20101015 s,
			CommonTree commonRoot = null;
			if (stream != null && stream.available() > 0) {
				MarkerProvider listener = new MarkerProvider();
				ANTLRInputStream antlrStream = new ANTLRInputStream(stream);
				RTMLexer lexer = new RTMLexer(antlrStream);
				lexer.setErrorListener(listener);
				CommonTokenStream tokens = new CommonTokenStream(lexer);
				RTMParser parser = new RTMParser(tokens);
				parser.setErrorListener(listener);

				RFSMParser.rfsm_return def = parser.rfsm();
				commonRoot = (CommonTree)def.getTree();
			}
			root = roots.get(filePath);
			if (root != null) {
				root.invalidate();	// KJH 20110616
			}
			root = createRoot(commonRoot, root, offset, length, moveTo);
			root.setName(filePath);
			root.revalidate();	// KJH 20110616
			
			if (root != null) {
				roots.put(filePath, root);
			}

			if(root instanceof IScriptRootNode) {
				((IScriptRootNode)root).setFile(file);
			}
			
			this.project = null;
			this.document = (IDocument)stack.pop();

		} catch (IOException e) {
		} catch (RecognitionException e) {
		} catch (CoreException e) {
		}

		return root;
	}
	
	private IScriptNode createRoot(CommonTree root, IScriptNode _node, int start, int length, int moveTo){
		// KJH 20100708, tpl root를 IncludeNode로 수정 (global)
		IScriptRootNode node = null;
		if (_node instanceof IScriptRootNode) {
			node = (IScriptRootNode)_node;
		} else {
			node = new ScriptRootNode();
		}
		
		node.setGlobal(true);
		node.setType(RFSMParser.RTDL);
		node.setName(IScriptNode.ROOT);
		node.setValid(true);	// KJH 20110621

		if(root == null){
			logger.debug("CommonTree is null.");

			node.invalidate();
			return node;
		}

		List<IScriptNode> list = new ArrayList<IScriptNode>();

		int size = root.getChildCount();
		for(int i=0; i<size; i++){
			// CommonTree; child node & token type
			CommonTree ct = (CommonTree)root.getChild(i);
			if(ct instanceof CommonErrorNode)
				continue;
			int cType = ct.getToken().getType();

			// current node's start offset
			int offset = getOffset(ct.getLine(), ct.getCharPositionInLine());
			// In after fixed range;
			if(start > -1 && offset >= start)
				offset -= moveTo;
			IScriptNode child = getNode(node, offset, cType);

			if(cType == RFSMParser.BEHA){
				child = createBehavior(ct, node, child, start, length, moveTo);
				if (!list.contains(child)) {
					list.add(child);
				}
			}
			else if(cType == RFSMParser.INCL){
				child = createInclude(ct, node, child, start, length, moveTo);
				if (!list.contains(child)) {
					list.add(child);
				}
			}
			else if(cType == RFSMParser.ENUM){
				child = createEnumm(ct, node, child, start, length, moveTo);
				if (!list.contains(child)) {
					list.add(child);
				}
			}
			else if(cType == RFSMParser.MODEL){
				child = createModel(ct, node, child, start, length, moveTo);
				if (!list.contains(child)) {
					list.add(child);
				}
			}
			else if(cType == RFSMParser.WORKER2){
				child = createWorker2(ct, node, child, start, length, moveTo);
				if (!list.contains(child)) {
					list.add(child);
				}
			}
			else if(cType == RFSMParser.CNT) {	// KJH 20110130 s, connector
				child = createConnector(ct, node, child, start, length, moveTo);
				if (!list.contains(child)) {
					list.add(child);
				}
			}	// KJH 20110130 e, connector
		}
		
		node.getChildren().clear();
		node.getChildren().addAll(list);

		return node;
	}
	
	private IScriptNode createWorker2(CommonTree root, IScriptNode parent, IScriptNode _node,
			int start, int length, int moveTo) {
		IWorkerNode node = null;
		if(_node == null || !(_node instanceof IWorkerNode)){
			node = new WorkerNode();
		} else{
			node = (IWorkerNode)_node;
			
			if(start > -1){
				if(node.getEnd() < start){
					node.avalidate();
					return node;
				}
				else if(node.getStart() >= start+length){
					moveTo(node, moveTo);
					node.avalidate();
					return node;
				}
			}
		}
		
		node.setType(root.getType());
		node.setParent(parent);
		node.setStart(getOffset(root.getLine(), root.getCharPositionInLine()));
		node.setValid(true);	// KJH 20110621
		
		List<IScriptNode> list = new ArrayList<IScriptNode>();
		int size = root.getChildCount();
		for(int i=0; i<size; i++){
			CommonTree ct = (CommonTree)root.getChild(i);
			if(ct instanceof CommonErrorNode)
				continue;
			int cType = ct.getToken().getType();
			
			int offset = getOffset(ct.getLine(), ct.getCharPositionInLine());
			if(start > -1 && offset >= start)
				offset -= moveTo;
			IScriptNode child = getNode(node, offset, cType);
			
			if(cType == RFSMParser.NAME){
				node.setName(TPLTreeUtil.gets(ct.getChild(0)));
				node.setNameStart(getOffset(ct.getLine(), ct.getCharPositionInLine()));
				node.setNameLength(node.getName().length());
			}
			else if(cType == RFSMParser.CONSTR || cType == RFSMParser.DEST){	// initializer, finalize
				// KJH 20110201 s, create Struct
				child = createStruct(ct, node, child, start, length, moveTo);
				if (!list.contains(child)) {
					list.add(child);
				}
				// KJH 20110201 e, create Struct
			}
			else if(cType == RFSMParser.RUN){	// run
				child = createRun(ct, node, child, start, length, moveTo);
				if (!list.contains(child)) {
					list.add(child);
				}
			}
			else if(cType == RFSMParser.EOL){
				node.setEnd(getOffset(ct.getLine(), ct.getCharPositionInLine()) + 1);
			}
		}
		
		node.getChildren().clear();
		node.getChildren().addAll(list);
		
		return node;
	}
	
	// KJH 20110130 s, create IConnectorNode
	private IScriptNode createConnector(CommonTree root, IScriptNode parent, IScriptNode _node,
			int start, int length, int moveTo) {
		IConnectorNode node = null;
		if(_node == null || !(_node instanceof IConnectorNode)){
			node = new ConnectorNode();
		} else{
			node = (IConnectorNode)_node;
			
			if(start > -1){
				if(node.getEnd() < start){
					node.avalidate();
					return node;
				}
				else if(node.getStart() >= start+length){
					moveTo(node, moveTo);
					node.avalidate();
					return node;
				}
			}
		}
		
		node.setType(root.getType());
		node.setParent(parent);
		node.setStart(getOffset(root.getLine(), root.getCharPositionInLine()));
		node.setValid(true);	// KJH 20110621
		
		List<IScriptNode> list = new ArrayList<IScriptNode>();
		int size = root.getChildCount();
		for(int i=0; i<size; i++){
			CommonTree ct = (CommonTree)root.getChild(i);
			if(ct instanceof CommonErrorNode)
				continue;
			int cType = ct.getToken().getType();
			
			if(cType == RFSMParser.NAME){
				node.setName(TPLTreeUtil.gets(ct.getChild(0)));
				node.setNameStart(getOffset(ct.getLine(), ct.getCharPositionInLine()));
				node.setNameLength(node.getName().length());
			}
			else if(cType == RFSMParser.PARMS){
				for(Object c : ct.getChildren()){
					if(c instanceof CommonErrorNode)
						continue;
					
					CommonTree ctt = (CommonTree)c;
					cType = ctt.getToken().getType();

					int offset = getOffset(ctt.getLine(), ctt.getCharPositionInLine());
					if(start > -1 && offset >= start)
						offset -= moveTo;
					IScriptNode child = getNode(node, offset, cType);

					if(cType == RFSMParser.PARM){
						child = createParam(ctt, node, child, start, length, moveTo);
						if (!list.contains(child)) {
							list.add(child);
						}
					}
				}
			}
			else if (cType == RFSMParser.ELEMS) {	// KJH 20110208, elems
				for(Object c : ct.getChildren()){
					if(c instanceof CommonErrorNode)
						continue;
					
					CommonTree ctt = (CommonTree)c;
					cType = ctt.getToken().getType();

					if(cType == RFSMParser.CONSTR || cType == RFSMParser.DEST){	// construct, destruct
						int offset = getOffset(ctt.getLine(), ctt.getCharPositionInLine());
						if(start > -1 && offset >= start)
							offset -= moveTo;
						IScriptNode child = getNode(node, offset, cType);
						
						child = createStruct(ctt, node, child, start, length, moveTo);
						if(!list.contains(child)) {
							list.add(child);
						}
					}
					else if(cType == RFSMParser.CON){	// conexer
						for (Object cc : ctt.getChildren()) {
							if (cc instanceof CommonErrorNode) {
								continue;
							}
							
							CommonTree cttt = (CommonTree)cc;
							cType = cttt.getToken().getType();
							
							if(cType == RFSMParser.CMOD){
								String conMod = TPLTreeUtil.gets(cttt.getChild(0));
								node.setConMod(conMod);
							}
							else if(cType == RFSMParser.SYNMOD){
								node.setSynMod(TPLTreeUtil.gets(cttt.getChild(0)));
							}
							else if(cType == RFSMParser.JTYPE){
								node.setJoinType(TPLTreeUtil.gets(cttt.getChild(0)));
							}
							else if(cType == RFSMParser.WITHS){
								List<IScriptNode> tList = stmtparallel(cttt, node, start, length, moveTo);
								for (IScriptNode child : tList) {
									if (!list.contains(child)) {
										list.add(child);
									}
								}
							}
						}
					}
				}
			}	// KJH 20110208, elems
			else if(cType == RFSMParser.EOL){
				node.setEnd(getOffset(ct.getLine(), ct.getCharPositionInLine()) + 1);
			}
		}
		
		node.getChildren().clear();
		node.getChildren().addAll(list);

		return node;
	}	// KJH 20110130 s, create IConnectorNode
	
	// KJH 20110130 s, create IRunNode
	private IScriptNode createRun(CommonTree root, IScriptNode parent, IScriptNode _node,
			int start, int length, int moveTo) {
		IStructNode node = null;
		if(_node == null || !(_node instanceof IStructNode)){
			node = new StructNode();
		}
		else{
			node = (IStructNode)_node;
			
			if(start > -1){
				if(node.getEnd() < start){
					node.avalidate();
					return node;
				}
				else if(node.getStart() >= start+length){
					moveTo(node, moveTo);
					node.avalidate();
					return node;
				}
			}
		}
		
		node.setType(root.getType());
		node.setParent(parent);
		node.setStart(getOffset(root.getLine(), root.getCharPositionInLine()));
		node.setValid(true);	// KJH 20110621

		int size = root.getChildCount();
		List<IScriptNode> list = new ArrayList<IScriptNode>();
		for(int i =0 ; i <size ; i++){
			CommonTree ct =(CommonTree)root.getChild(i);
			if(ct instanceof CommonErrorNode)
				continue;
			int cType = ct.getToken().getType();

			int offset = getOffset(ct.getLine(), ct.getCharPositionInLine());
			if(start > -1 && offset >= start)
				offset -= moveTo;
			
			if (cType == RFSMParser.STMTS) {
				String body = TPLTreeUtil.gets(ct);
				node.setBody(body);
				
				List<IScriptNode> tList = stmtblock(ct, node, start, length, moveTo);
				for (IScriptNode child : tList) {
					if (!list.contains(child)) {
						list.add(child);
					}
				}
			}
			else if (cType == RFSMParser.AMOD) {
				node.setName(TPLTreeUtil.gets(ct.getChild(0)));
				node.setNameStart(getOffset(ct.getLine(), ct.getCharPositionInLine()));
				node.setNameLength(node.getName().length());
			}
			else if (cType == RFSMParser.EOL) {
				node.setEnd(getOffset(ct.getLine(), ct.getCharPositionInLine()) + 1);
			}
			else if (cType == RFSMParser.CYCLE) {	// KJH 20110907
				
			}
		}
		
		node.getChildren().clear();
		node.getChildren().addAll(list);

		return node;
	}	// KJH 20110214 s, create IRunNode (task/run)
	
	// KJH 20110130 s, create IConexecNode: task (connector/destruct, task/finalize)
	private IScriptNode createStruct(CommonTree root, IScriptNode parent, IScriptNode _node,
			int start, int length, int moveTo) {
		IStructNode node = null;
		if(_node == null || !(_node instanceof IStructNode)){
			node = new StructNode();
		}
		else{
			node = (IStructNode)_node;
			
			if(start > -1){
				if(node.getEnd() < start){
					node.avalidate();
					return node;
				}
				else if(node.getStart() >= start+length){
					moveTo(node, moveTo);
					node.avalidate();
					return node;
				}
			}
		}
		
		node.setType(root.getType());
		node.setParent(parent);
		node.setStart(getOffset(root.getLine(), root.getCharPositionInLine()));
		node.setValid(true);	// KJH 20110621
		
		int size = root.getChildCount();
		for(int i =0 ; i <size ; i++){
			CommonTree ct =(CommonTree)root.getChild(i);
			if(ct instanceof CommonErrorNode)
				continue;
			int cType = ct.getToken().getType();
			
			if(cType == RFSMParser.AMOD){
				node.setName(TPLTreeUtil.gets(ct.getChild(0)));
				node.setNameStart(getOffset(ct.getLine(), ct.getCharPositionInLine()));
				node.setNameLength(node.getName().length());
			}
			else if(cType == RFSMParser.STMTS){
				node.setBody(TPLTreeUtil.gets(ct));
			}
			else if(cType == RFSMParser.EOL){
				node.setEnd(getOffset(ct.getLine(), ct.getCharPositionInLine()) + 1);
			}
		}
		
		return node;
	}	// KJH 20110130 e, create IDestructNode (connector/destruct, task/finalize)

	private IScriptNode createInclude(CommonTree root, IScriptNode parent, IScriptNode _node,
			int start, int length, int moveTo){
		IIncludeNode node = null;
		if(_node == null || !(_node instanceof IIncludeNode)){
			node = new IncludeNode();
		}
		else{
			node = (IIncludeNode)_node;
			
			if(start > -1){
				if(node.getEnd() < start){
					node.avalidate();
					return node;
				}
				else if(node.getStart() >= start+length){
					moveTo(node, moveTo);
					node.avalidate();
					return node;
				}
			}
		}
		
		node.setType(root.getType());
		node.setParent(parent);
		node.setStart(getOffset(root.getLine(), root.getCharPositionInLine()));
		node.setValid(true);	// KJH 20110621
		
		int size = root.getChildCount();
		for(int i=0; i<size; i++){
			CommonTree ct = (CommonTree)root.getChild(i);
			if(ct instanceof CommonErrorNode)
				continue;
			int cType = ct.getToken().getType();
			
			if(cType == RFSMParser.FNAME){
				String name = TPLTreeUtil.gets(ct.getChild(0));
				int pos = ct.getCharPositionInLine();

				node.setEnd(getOffset(ct.getLine(), pos) + name.length());

				if (name.startsWith("\"")) {
					name = name.substring(1);
					pos ++;
				}
				if (name.endsWith("\"")) {
					name = name.substring(0, name.length()-1);
				}
				node.setName(name);
				node.setNameStart(getOffset(ct.getLine(), pos));
				node.setNameLength(name.length());
				
				// include detail
				String path = name;
				if(!path.startsWith("/")) {
					path = "/" + path;
				}
				
				IResource resource = null;
				if (this.project != null) {
					resource = this.project.findMember(new Path(path));
				}
				else {
					IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
					resource = workspaceRoot.findMember(new Path(path));
				}

				IScriptNode includeRoot = null;
				if(resource instanceof IFile && resource.exists()) {
					includeRoot = getTree((IFile)resource, null);
				}
				
				if (includeRoot instanceof IScriptRootNode) {
					node.setRealNode(includeRoot);
				} else {
					node.setRealNode(null);
				}
			}
		}
		
		return node;
	}

	private IScriptNode createEnumm(CommonTree root, IScriptNode parent, IScriptNode _node,
			int start, int length, int moveTo){
		IEnumNode node = null;
		if(_node == null || !(_node instanceof IEnumNode)){
			node = new EnumNode();
		}
		else{
			node = (IEnumNode)_node;
			
			if(start > -1){
				if(node.getEnd() < start){
					node.avalidate();
					return node;
				}
				else if(node.getStart() >= start+length){
					moveTo(node, moveTo);
					node.avalidate();
					return node;
				}
			}
		}

		node.setType(root.getType());
		node.setParent(parent);
		node.setStart(getOffset(root.getLine(), root.getCharPositionInLine()));
		node.setValid(true);	// KJH 20110621
		
		List<IScriptNode> list = new ArrayList<IScriptNode>();
		int size = root.getChildCount();
		for(int i=0; i<size; i++){
			CommonTree ct = (CommonTree)root.getChild(i);
			if(ct instanceof CommonErrorNode)
				continue;
			int cType = ct.getToken().getType();
			
			if(cType == RFSMParser.NAME){
				node.setName(TPLTreeUtil.gets(ct.getChild(0)));
				node.setNameStart(getOffset(ct.getLine(), ct.getCharPositionInLine()));
				node.setNameLength(node.getName().length());
			}
			else if(cType == RFSMParser.ELEM){
				int offset = getOffset(ct.getLine(), ct.getCharPositionInLine());
				if(start > -1 && offset >= start)
					offset -= moveTo;
				IScriptNode child = getNode(node, offset, cType);
				
				child = createElement(ct, node, child, start, length, moveTo);
				if(child != null && !list.contains(child)) {
					list.add(child);
				}
			}
			else if(cType == RFSMParser.EOL){
				node.setEnd(getOffset(ct.getLine(), ct.getCharPositionInLine()) + 1);
			}
		}
		
		node.getChildren().clear();
		node.getChildren().addAll(list);

		return node;
	}
	
	private IScriptNode createElement(CommonTree root, IScriptNode parent, IScriptNode _node,
			int start, int length, int moveTo){
		IEnumElmtNode node = null;
		if(_node == null || !(_node instanceof IEnumElmtNode)){
			node = new EnumElmtNode();
		}
		else{
			node = (IEnumElmtNode)_node;
			
			if(start > -1){
				if(node.getEnd() < start){
					node.avalidate();
					return node;
				}
				else if(node.getStart() >= start+length){
					moveTo(node, moveTo);
					node.avalidate();
					return node;
				}
			}
		}
		
		if(root.getChild(0) instanceof CommonErrorNode){
			return null;
		}
		
		node.setValid(true);	// KJH 20110621
		node.setType(root.getType());
		node.setParent(parent);
		node.setName(TPLTreeUtil.gets(root.getChild(0)));
		node.setNameStart(getOffset(root.getLine(), root.getCharPositionInLine()));
		node.setNameLength(node.getName().length());
		
		node.setStart(node.getNameStart());
		node.setEnd(node.getNameEnd());

		return node;
	}

	private IScriptNode createModel(CommonTree root, IScriptNode parent, IScriptNode _node,
			int start, int length, int moveTo){
		IModelNode node = null;
		if(_node == null || !(_node instanceof IModelNode)){
			node = new ModelNode();
		}
		else{
			node = (IModelNode)_node;
			
			if(start > -1){
				if(node.getEnd() < start){
					node.avalidate();
					return node;
				}
				else if(node.getStart() >= start+length){
					moveTo(node, moveTo);
					node.avalidate();
					return node;
				}
			}
		}
		
		node.setType(root.getType());
		node.setParent(parent);
		node.setStart(getOffset(root.getLine(), root.getCharPositionInLine()));
		node.setValid(true);	// KJH 20110621
		
		List<IScriptNode> list = new ArrayList<IScriptNode>();
		int size = root.getChildCount();
		for(int i=0; i< size ; i++){
			CommonTree ct = (CommonTree)root.getChild(i);
			if(ct instanceof CommonErrorNode)
				continue;
			int cType = ct.getToken().getType();

			int offset = getOffset(ct.getLine(), ct.getCharPositionInLine());
			if(start > -1 && offset >= start)
				offset -= moveTo;
			IScriptNode child = getNode(node, offset, cType);

			if(cType == RFSMParser.NAME){
				node.setName(TPLTreeUtil.gets(ct.getChild(0)));
				node.setNameStart(getOffset(ct.getLine(), ct.getCharPositionInLine()));
				node.setNameLength(node.getName().length());

			}
			else if(cType == RFSMParser.VAR){
				child = createVar(ct, node, child, start, length, moveTo);
				if (!list.contains(child)) {
					list.add(child);
				}
			}
			else if(cType == RFSMParser.FUNC){
				child = createFunc(ct, node, child, start, length, moveTo);
				if (!list.contains(child)) {
					list.add(child);
				}
			}
			else if(cType == RFSMParser.MODEL){
				child = createModel(ct, node, child, start, length, moveTo);
				if (!list.contains(child)) {
					list.add(child);
				}
			}
			else if(cType == RFSMParser.EOL){
				node.setEnd(getOffset(ct.getLine(), ct.getCharPositionInLine()) + 1);
			}
			
		}
		
		node.getChildren().clear();
		node.getChildren().addAll(list);

		return node;
	}
	
	private IScriptNode createVar(CommonTree root, IScriptNode parent, IScriptNode _node,
			int start, int length, int moveTo){
		IVarNode node = null;
		if(_node == null || !(_node instanceof IVarNode)){
			node = new VarNode();
		}
		else{
			node = (IVarNode)_node;
			
			if(start > -1){
				if(node.getEnd() < start){
					node.avalidate();
					return node;
				}
				else if(node.getStart() >= start+length){
					moveTo(node, moveTo);
					node.avalidate();
					return node;
				}
			}
		}
		
		node.setType(root.getType());
		node.setParent(parent);
		node.setStart(getOffset(root.getLine(), root.getCharPositionInLine()));
		node.setValid(true);	// KJH 20110621
		
		int size = root.getChildCount();
		for(int i=0; i< size ; i++){
			CommonTree ct = (CommonTree)root.getChild(i);
			if(ct instanceof CommonErrorNode)
				continue;
			int cType = ct.getToken().getType();
			
			if(cType == RFSMParser.VMOD){
				node.setVMod(TPLTreeUtil.gets(ct.getChild(0)));
			}
			else if(cType == RFSMParser.TYPE){
				node.setVType(TPLTreeUtil.gets(ct.getChild(0)));
			}
			else if(cType == RFSMParser.NAME){
				node.setName(TPLTreeUtil.gets(ct.getChild(0)));
				node.setNameStart(getOffset(ct.getLine(), ct.getCharPositionInLine()));
				node.setNameLength(node.getName().length());
			}
			else if(cType == RFSMParser.EOL){
				node.setEnd(getOffset(ct.getLine(), ct.getCharPositionInLine()) + 1);
			}
		}
		
		return node;
	}

	private IScriptNode createFunc(CommonTree root, IScriptNode parent, IScriptNode _node,
			int start, int length, int moveTo){
		IFuncNode node = null;
		if(_node == null || !(_node instanceof IFuncNode)){
			node = new FuncNode();
		}
		else{
			node  = (IFuncNode)_node;
			
			if(start > -1){
				if(node.getEnd() < start){
					node.avalidate();
					return node;
				}
				else if(node.getStart() >= start+length){
					moveTo(node, moveTo);
					node.avalidate();
					return node;
				}
			}
		}
		
		node.setType(root.getType());
		node.setParent(parent);
		node.setStart(getOffset(root.getLine(), root.getCharPositionInLine()));
		node.setAction(false);	// KJH 20110620
		node.setValid(true);	// KJH 20110621
		
		List<IScriptNode> list = new ArrayList<IScriptNode>();
		int size = root.getChildCount();
		for(int i=0; i< size ; i++){
			CommonTree ct = (CommonTree)root.getChild(i);
			if(ct instanceof CommonErrorNode)
				continue;
			int cType = ct.getToken().getType();

			if(cType == RFSMParser.ACTION){
				node.setAction(true);
			}
			else if(cType == RFSMParser.RTN){
				node.setRtn(TPLTreeUtil.gets(ct.getChild(0)));
			}
			else if(cType == RFSMParser.NAME){
				node.setName(TPLTreeUtil.gets(ct.getChild(0)));
				node.setNameStart(getOffset(ct.getLine(), ct.getCharPositionInLine()));
				node.setNameLength(node.getName().length());
			}
			else if(cType == RFSMParser.PARM){
				int offset = getOffset(ct.getLine(), ct.getCharPositionInLine());
				if(start > -1 && offset >= start)
					offset -= moveTo;
				IScriptNode child = getNode(node, offset, cType);

				child = createParam(ct, node, child, start, length, moveTo);
				if (!list.contains(child)) {
					list.add(child);
				}
			}
			else if(cType == RFSMParser.EOL){
				node.setEnd(getOffset(ct.getLine(), ct.getCharPositionInLine()) + 1);
			}
		}
		
		node.getChildren().clear();
		node.getChildren().addAll(list);

		return node;
	}

	private IScriptNode createParam(CommonTree root, IScriptNode parent, IScriptNode _node,
			int start, int length, int moveTo){
		IParamNode node = null;
		if(_node == null || !(_node instanceof IParamNode)){
			node = new ParamNode();
		}
		else{
			node = (IParamNode)_node;
			
			if(start > -1){
				if(node.getEnd() < start){
					node.avalidate();
					return node;
				}
				else if(node.getStart() >= start+length){
					moveTo(node, moveTo);
					node.avalidate();
					return node;
				}
			}
		}
		
		node.setType(root.getType());
		node.setParent(parent);
		node.setStart(getOffset(root.getLine(), root.getCharPositionInLine()));
		node.setValid(true);	// KJH 20110621
		
		int size = root.getChildCount();
		for(int i=0; i<size; i++){
			CommonTree ct = (CommonTree)root.getChild(i);
			if(ct instanceof CommonErrorNode)
				continue;
			int cType = ct.getToken().getType();

			if(cType == RFSMParser.TYPE){
				node.setDataType(TPLTreeUtil.gets(ct.getChild(0)));
			}
			else if(cType == RFSMParser.NAME){
				node.setName(TPLTreeUtil.gets(ct.getChild(0)));
				node.setNameStart(getOffset(ct.getLine(), ct.getCharPositionInLine()));
				node.setNameLength(node.getName().length());
				node.setEnd(node.getNameEnd());
			}
		}
		
		return node;
	}

	private IScriptNode createBehavior(CommonTree root, IScriptNode parent, IScriptNode _node,
			int start, int length, int moveTo){
		IBehaviorNode node = null;
		if(_node == null || !(_node instanceof IBehaviorNode)){
			node = new BehaviorNode();
		}
		else{
			node = (IBehaviorNode)_node;
			
			if(start > -1){
				if(node.getEnd() < start){
					node.avalidate();
					return node;
				}
				else if(node.getStart() >= start+length){
					moveTo(node, moveTo);
					node.avalidate();
					return node;
				}
			}
		}

		node.setType(root.getType());
		node.setParent(parent);
		node.setStart(getOffset(root.getLine(), root.getCharPositionInLine()));
		node.setMission(false);	// KJH 20110620
		node.setValid(true);	// KJH 20110621
		
		List<IScriptNode> list = new ArrayList<IScriptNode>();
		int size = root.getChildCount();
		for(int i=0; i<size; i++){
			CommonTree ct = (CommonTree)root.getChild(i);
			if(ct instanceof CommonErrorNode)
				continue;
			int cType = ct.getToken().getType();
			
			if(cType == RFSMParser.TNAME){
				node.setName(TPLTreeUtil.gets(ct.getChild(0)));
				node.setNameStart(getOffset(ct.getLine(), ct.getCharPositionInLine()));
				node.setNameLength(node.getName().length());
			}
			else if(cType == RFSMParser.STATES){
				if(ct.getChildren() != null){
					for(Object c : ct.getChildren()){
						if(c instanceof CommonErrorNode)
							continue;

						CommonTree ctt = (CommonTree)c;
						cType = ctt.getToken().getType();

						if(cType == RFSMParser.STATE){
							int offset = getOffset(ctt.getLine(), ctt.getCharPositionInLine());
							if(start > -1 && offset >= start)
								offset -= moveTo;
							IScriptNode child = getNode(node, offset, cType);

							child = createState(ctt, node, child, start, length, moveTo);
							if (!list.contains(child)) {
								list.add(child);
							}
						}
						else if (cType == RFSMParser.CONSTR || cType == RFSMParser.DEST) {
							int offset = getOffset(ctt.getLine(), ctt.getCharPositionInLine());
							if(start > -1 && offset >= start)
								offset -= moveTo;
							IScriptNode child = getNode(node, offset, cType);

							child = createStruct(ctt, node, child, start, length, moveTo);
							if (!list.contains(child)) {
								list.add(child);
							}
						}
					}				
				}
			}
			else if(cType == RFSMParser.PARMS){
				if (ct.getChildren() != null) {
					for(Object c : ct.getChildren()){
						if(c instanceof CommonErrorNode)
							continue;

						CommonTree ctt = (CommonTree)c;
						cType = ctt.getToken().getType();

						if(cType == RFSMParser.PARM){
							int offset = getOffset(ctt.getLine(), ctt.getCharPositionInLine());
							if(start > -1 && offset >= start)
								offset -= moveTo;
							IScriptNode child = getNode(node, offset, cType);

							child = createParam(ctt, node, child, start, length, moveTo);
							if (!list.contains(child)) {
								list.add(child);
							}
						}
					}
				}
			}
			else if(cType == RFSMParser.MEM){
				node.setMission(true);
			}
			else if(cType == RFSMParser.EOL){
				node.setEnd(getOffset(ct.getLine(), ct.getCharPositionInLine()) + 1);
			}
		}

		node.getChildren().clear();
		node.getChildren().addAll(list);

		return node;
	}
	
	private IScriptNode createState(CommonTree root, IScriptNode parent, IScriptNode _node,
			int start, int length, int moveTo){
		IStateNode node = null;
		if(_node == null || !(_node instanceof IStateNode)){
			node = new StateNode();
		}
		else{
			node = (IStateNode)_node;
			
			if(start > -1){
				if(node.getEnd() < start){
					node.avalidate();
					return node;
				}
				else if(node.getStart() >= start+length){
					moveTo(node, moveTo);
					node.avalidate();
					return node;
				}
			}
		}
		
		node.setType(root.getType());
		node.setParent(parent);
		node.setStart(getOffset(root.getLine(), root.getCharPositionInLine()));
		node.setValid(true);	// KJH 20110621
		
		List<IScriptNode> list = new ArrayList<IScriptNode>();
		
		int size = root.getChildCount();
		for(int i =0 ; i <size ; i++){
			CommonTree ct =(CommonTree)root.getChild(i);
			if(ct instanceof CommonErrorNode)
				continue;
			int cType = ct.getToken().getType();

			int offset = getOffset(ct.getLine(), ct.getCharPositionInLine());
			if(start > -1 && offset >= start)
				offset -= moveTo;
			IScriptNode child = getNode(node, offset, cType);

			if(cType == RFSMParser.SMOD){
				node.setModifier(TPLTreeUtil.gets(ct.getChild(0)));
			}
			else if(cType == RFSMParser.SNAME){
				node.setName(TPLTreeUtil.gets(ct.getChild(0)));
				node.setNameStart(getOffset(ct.getLine(), ct.getCharPositionInLine()));
				node.setNameLength(node.getName().length());
			}
			else if(cType == RFSMParser.ACTION){
				child = createAction(ct, node, child, start, length, moveTo);
				if (!list.contains(child)) {
					list.add(child);
				}
			}
			else if(cType == RFSMParser.TRANS){
				List<IScriptNode> tList = stmtblock(ct, node, start, length, moveTo);
				for (int j=0; j<tList.size(); j++) {
					child = tList.get(j);
					if (!list.contains(child)) {
						list.add(child);
					}
				}
			}
			// KJH 20100408 e, transition
			else if(cType == RFSMParser.EOL){
				node.setEnd(getOffset(ct.getLine(), ct.getCharPositionInLine()) + 1);
			}
		}
		
		node.getChildren().clear();
		node.getChildren().addAll(list);

		return node;
	}

	private IScriptNode createAction(CommonTree root, IScriptNode parent, IScriptNode _node,
			int start, int length, int moveTo){
		IStateActionNode node = null;
		if(_node == null || !(_node instanceof IStateActionNode)){
			node = new StateActionNode();
		}
		else{
			node = (IStateActionNode)_node;
			
			if(start > -1){
				if(node.getEnd() < start){
					node.avalidate();
					return node;
				}
				else if(node.getStart() >= start+length){
					moveTo(node, moveTo);
					node.avalidate();
					return node;
				}
			}
		}
		
		node.setType(root.getType());
		node.setParent(parent);
		node.setStart(getOffset(root.getLine(), root.getCharPositionInLine()));
		node.setValid(true);	// KJH 20110621
		
		int size = root.getChildCount();
		for(int i =0 ; i <size ; i++){
			CommonTree ct =(CommonTree)root.getChild(i);
			if(ct instanceof CommonErrorNode)
				continue;
			int cType = ct.getToken().getType();
			
			if(cType == RFSMParser.AMOD){
				node.setName(TPLTreeUtil.gets(ct.getChild(0)));
				node.setNameStart(getOffset(ct.getLine(), ct.getCharPositionInLine()));
				node.setNameLength(node.getName().length());
			}
			else if(cType == RFSMParser.STMTS){
				String body = TPLTreeUtil.gets(ct);
				node.setBody(body);
			}
			// KJH 20101029 s, RFSM.g actionBody에 EOL 추가
			else if(cType == RFSMParser.EOL){
				node.setEnd(getOffset(ct.getLine(), ct.getCharPositionInLine()) + 1);
			}
			// KJH 20101029 e, RFSM.g actionBody에 EOL 추가
		}
		
		return node;
	}

	private List<IScriptNode> stmtblock(CommonTree root, IScriptNode parent,
			int start, int length, int moveTo){
		List<IScriptNode> list = new ArrayList<IScriptNode>();
		
		int size = root.getChildCount();
		for(int i=0; i<size; i++){
			CommonTree ct = (CommonTree)root.getChild(i);
			if(ct instanceof CommonErrorNode)
				continue;
			int cType = ct.getToken().getType();

			if(cType == RFSMParser.STMTS) {
				list.addAll(stmtblock(ct, parent, start, length, moveTo));
			}
			else if(cType == RFSMParser.STMTBLOCK){
				list.addAll(stmtblock(ct, parent, start, length, moveTo));
			}
			else if(cType == RFSMParser.PAR){
				list.addAll(stmtparallel(ct, parent, start, length, moveTo));
			}
			else if(cType == RFSMParser.IF) {	// KJH 20101029 s,
				list.addAll(stmtif(ct, parent, start, length, moveTo));
			}
			else if(cType == RFSMParser.GOTO || cType == RFSMParser.IVK){
				int offset = getOffset(ct.getLine(), ct.getCharPositionInLine());
				if(start > -1 && offset >= start)
					offset -= moveTo;
				IScriptNode child = getNode(parent, offset, cType);
				
				for(IScriptNode element : getNodes(parent, cType)){
					// KJH 20101029, getNameStart() -> getStart
					if(element != null && element.getStart() == offset){
						child = element;
						break;
					}
				}

				child = createTrans(ct, parent, child, start, length, moveTo);
				if(!list.contains(child))
					list.add(child);
			}
		}

		// KJH 20100416 s, null이 반환되지 않도록 수정
		return list;
	}

	// KJH 20100414 s, if
	private List<IScriptNode> stmtif(CommonTree root, IScriptNode parent,
			int start, int length, int moveTo){
		List<IScriptNode> tList = new ArrayList<IScriptNode>();
		List<IScriptNode> fList = new ArrayList<IScriptNode>();
		
		int offset = getOffset(root.getLine(), root.getCharPositionInLine());
		if(start > -1 && offset >= start)
			offset -= moveTo;
		
		String cond = "";
		int size = root.getChildCount();
		for(int i=0; i<size; i++){
			CommonTree ct = (CommonTree)root.getChild(i);
			if(ct instanceof CommonErrorNode)
				continue;
			int cType = ct.getToken().getType();

			if(cType == RFSMParser.COND){	// 조건
				cond = TPLTreeUtil.gets(ct.getChild(0));
			}
			else if(cType == RFSMParser.WHENT){	// 참일 경우
				tList.addAll(stmtblock(ct, parent, start, length, moveTo));	// KJH 20101029
			}
			else if(cType == RFSMParser.WHENF){	// 거짓일 경우
				fList.addAll(stmtblock(ct, parent, start, length, moveTo));	// KJH 20101029
			}
		}

		for(IScriptNode node : tList){
			if(node instanceof ITranNode){
				((ITranNode)node).setCond(cond);
//				node.setStart(offset);	// KJH 20101029
			}
		}
		
		tList.addAll(fList);
		fList.clear();
		
		return tList;
	}
	// KJH 20100414 e, if

	// KJH 20100414 s, goto
	private IScriptNode createTrans(CommonTree root, IScriptNode parent, IScriptNode _node,
			int start, int length, int moveTo){
		ITranNode node = null;
		if(_node == null || !(_node instanceof ITranNode))
			node = new TranNode();
		else{
			node = (ITranNode)_node;
			
			if(start > -1){
				if(node.getEnd() < start){
					node.avalidate();
					return node;
				}
				else if(node.getStart() >= start+length){
					moveTo(node, moveTo);
					node.avalidate();
					return node;
				}
			}
		}
		
		node.setType(root.getType());
		node.setParent(parent);
		node.setStart(getOffset(root.getLine(), root.getCharPositionInLine()));	// KJH 20101029
		node.setValid(true);

		int size = root.getChildCount();
		for(int i=0; i<size; i++){
			CommonTree ct = (CommonTree)root.getChild(i);
			if(ct instanceof CommonErrorNode)
				continue;
			int cType = ct.getToken().getType();

			if(cType == RFSMParser.BEHA){	// target이 behavior인 경우
				String name = TPLTreeUtil.gets(ct.getChild(0));
				int index = name.lastIndexOf("()");
				if(index > -1)
					name = name.substring(0, index);

				node.setName(IScriptNode.EXPAND);	// KJH 20110214, Call for Behavior -> Expand (script)
				node.setTarget(name);
				node.setNameStart(getOffset(ct.getLine(), ct.getCharPositionInLine()));
				node.setNameLength(name.length());
			}
			else if(cType == RFSMParser.STATE){	// target이 state인 경우
				String name = TPLTreeUtil.gets(ct.getChild(0));
				node.setName(IScriptNode.TRANS);
				node.setTarget(name);
				node.setNameStart(getOffset(ct.getLine(), ct.getCharPositionInLine()));
				node.setNameLength(name.length());
			}
			else if(cType == RFSMParser.STAY){
				String name = TPLTreeUtil.gets(ct.getChild(0));
				if(IScriptNode.STAY.equals(name))
					name = parent.getParent().getName();	// KJH 20110216, State->Transition->goto
				node.setName(IScriptNode.TRANS);
				node.setTarget(name);
				node.setNameStart(getOffset(ct.getLine(), ct.getCharPositionInLine()));
				node.setNameLength(name.length());
			}
			else if(cType == RFSMParser.EOL){
				node.setEnd(getOffset(ct.getLine(), ct.getCharPositionInLine()) + 1);	// KJH 20101029
			}
			else if (cType == RFSMParser.EOE) {
				ITranNode newNode = new TranNode();
				String target = TPLTreeUtil.gets(ct.getChild(0));
				newNode.setName(IScriptNode.TRANS);
				newNode.setTarget(target);
				newNode.setNameStart(getOffset(ct.getLine(), ct.getCharPositionInLine()));
				newNode.setNameLength(target.length());
				node.getParent().addChild(newNode);
			}
			else if (cType == RFSMParser.EOB) {
				
			}
		}
		
		return node;
	}
	// KJH 20100414 e, goto 

	// KJH 20100416 s, parallel
	private List<IScriptNode> stmtparallel(CommonTree root, IScriptNode parent,
			int start, int length, int moveTo){
		List<IScriptNode> list = new ArrayList<IScriptNode>();

		int size = root.getChildCount();
		for(int i=0; i<size; i++){
			CommonTree ct = (CommonTree)root.getChild(i);
			if(ct instanceof CommonErrorNode)
				continue;
			int cType = ct.getToken().getType();

			if(cType == RFSMParser.RUN){
				int offset = getOffset(ct.getLine(), ct.getCharPositionInLine());
				if(start > -1 && offset >= start)
					offset -= moveTo;
				IScriptNode child = getNode(parent, offset, cType);
				
//				list.addAll(stmtblock(ct, parent, start, length, moveTo));
				list.add(createRun(ct, parent, child, start, length, moveTo));
			}
		}

		return list;
	}
	
	private void moveTo(IScriptNode node, int offset){
		if(node != null){
			node.setStart(node.getStart() + offset);
			node.setEnd(node.getEnd() + offset);
			node.setNameStart(node.getNameStart() + offset);
			node.setNameEnd(node.getNameEnd() + offset);
		}
		
		for(IScriptNode child : node.getChildren()){
			moveTo(child, offset);
		}
			
	}

}
