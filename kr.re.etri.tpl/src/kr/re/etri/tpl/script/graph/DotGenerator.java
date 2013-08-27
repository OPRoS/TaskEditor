package kr.re.etri.tpl.script.graph;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import kr.re.etri.tpl.grammar.RFSMParser;
import kr.re.etri.tpl.script.grammar.tree.ITPLTree;
import kr.re.etri.tpl.script.utils.TPLAntlrUtil;
import kr.re.etri.tpl.script.utils.TPLLogger;
import kr.re.etri.tpl.script.utils.TPLTreeUtil;

import org.antlr.runtime.tree.CommonTree;

public class DotGenerator {
	private static DotGenerator generator;
	private List<CommonTree> treeList;
	private TPL tpl;
	private File prjDir;
	private List<String> restTrans;
	private Queue<Behavior> behaviorQ;
	
	private DotGenerator(){}
	
	public static DotGenerator getInstance(){
		if(generator == null){
			generator = new DotGenerator();
		}
		return generator;
	}
	
	public void generate(File root){
		tpl = new TPL();
		treeList = new ArrayList<CommonTree>();
		prjDir = root;
		behaviorQ = new ConcurrentLinkedQueue<Behavior>();
		if(prjDir == null){
			TPLLogger.logWarning("DotGenerator.generate()\t: Not Found project directory.");
			return;
		}		
		traverse(prjDir);		
		int size = treeList.size();
		for(int i =0; i< size ; i++){
			traverse(treeList.get(i));
		}
		File f = new File(prjDir,"dot");
		if(!f.exists()){
			f.mkdir();
		}
		prjDir = f;
		makeDotFile();		
	}
	private void printRest(List<String> rest, PrintWriter out){
		int size = rest.size();
		for(int i = 0 ; i< size ; i++){
			out.println(rest.get(i));
		}		
	}
	private void makeDotFile(){
		int size = tpl.getTaskListSize();
		for(int i =0; i < size ; i++){
			Task t = tpl.getTask(i);
			makeDotFile(t);
		}
	}
	private void makeDotFile(Task t){
		File f = new File(prjDir,t.getName()+".dot");
		if(!f.exists()){
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		restTrans = new ArrayList<String>();
		try {
			PrintWriter out = new PrintWriter(new FileOutputStream(f));
			out.println("digraph "+t.getName()+"{");
			out.println("compound=true;");
			GoTo gt =t.getStartBehavior();
			if(gt != null){
				makeDotFile(t,gt,out);
			}	
			printRest(restTrans,out);
			
			out.println("}");
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}			
	}
	private void makeDotFile(Task t, GoTo gt, PrintWriter out){
		if(gt.getType().equals(ITPLTree.BEHAVIOR)){
			out.println("subgraph cluster"+gt.getTo().trim()+"{");
			out.println("label=\""+gt.getTo()+"\"");
			Behavior bh = tpl.getBehavior(gt.getTo());
			makeDotFile(bh, out);
			out.println("}");
			
			while(!behaviorQ.isEmpty()){
				Behavior bbhh = behaviorQ.poll();
				out.println("subgraph cluster"+bbhh.getName().trim()+"{");
				out.println("label=\""+bbhh.getName()+"\"");
				makeDotFile(bbhh, out);
				out.println("}");				
			}
		}		
	}
	
	private final String TO = "->";
	private void makeDotFile(Behavior bh, PrintWriter out){
		bh.setPrinted(true);
		int size = bh.getStateListSize();
		for(int i =0 ; i < size ; i++){
			State s = bh.get(i);
			int gsize = s.getGoToListSize();
			for(int j =0; j <gsize ; j++){
				GoTo gt = s.getGoTo(j);
				if(gt.getType().equals(ITPLTree.STATE)){
					if(gt.getTo().equals("stay")){
						out.println(s.getName() +TO + s.getName()+";");
					}else{
						out.println(s.getName() +TO + gt.getTo()+";");
					}
				}
				else if(gt.getType().equals(ITPLTree.BEHAVIOR)){
					Behavior bbhh =tpl.getBehavior(gt.getTo());
					if(bbhh.getStateListSize() <= 0){
						continue;
					}
					State te= bbhh.get(0);						
					restTrans.add(s.getName()+TO+te.getName() + "[lhead= cluster"+gt.getTo().trim()+"];");
					if(!bbhh.isPrinted()){
						behaviorQ.add(bbhh);
					}
				}
				else{
					throw new UnsupportedOperationException("DotGenerator.makeDotFile() : GoTo type must be Behavior or State.");
				}
			}			
		}
	}
	
	private void traverse(File f){
		if(!f.exists()){
			return;
		}
		if(!f.isDirectory()){
			return;
		}
		
		File[] files = f.listFiles();
		
		for(int i =0; i< files.length ; i++){
			if(files[i].isDirectory()){
				traverse(files[i]);
			}
			else if(files[i].isFile()){
				if(!files[i].getName().endsWith(".tpl")){
					continue;
				}
				if(treeList == null){
					TPLLogger.logWarning("DotGenerator.traverse()\t: TreeList is null. ");
					return;
				}
				treeList.add(TPLAntlrUtil.makeCommonTree(files[i]));
			}
		}
	}
	
	private void traverse(CommonTree root){
		int size = root.getChildCount();
		for(int i = 0 ; i < size ; i++){
			CommonTree ct = (CommonTree)root.getChild(i);
			if(ct.getToken().getType() == RFSMParser.TASK){
				Task t = new Task();
				traverse(ct,t);
				tpl.addTask(t);
			}
			else if(ct.getToken().getType() == RFSMParser.BEHA){
				Behavior bv = new Behavior();
				traverse(ct,bv);
				tpl.addBehavior(bv);
			}
		}		
	}
	
	private void traverse(CommonTree root, Task t){
		int size = root.getChildCount();
		for(int i =0; i <size ; i++){
			CommonTree ct = (CommonTree)root.getChild(i);
			if(ct.getToken().getType() == RFSMParser.NAME){
				t.setName(ct.getChild(0).getChild(0).getText());
			}
			else if(ct.getToken().getType() == RFSMParser.STMTS){
				traverse(ct, t);
			}
			else if(ct.getToken().getType() == RFSMParser.GOTO){
				GoTo gt = new GoTo();
				traverse(ct , gt);		
				t.setStartBehavior(gt);
			}
		}
	}
	
	private void traverse(CommonTree root, GoTo gt){
		int size = root.getChildCount();
		for(int i =0; i < size ; i++){
			CommonTree ct =(CommonTree)root.getChild(i);
			if(ct.getToken().getType() == RFSMParser.STATE){
				gt.setType(ITPLTree.STATE);
				gt.setTo(TPLTreeUtil.getFirstLeafValue(ct));
			}
			else if(ct.getToken().getType() == RFSMParser.CALL){
				gt.setType(ITPLTree.BEHAVIOR);
				gt.setTo(TPLTreeUtil.getFirstLeafValue(ct));
			}else if(ct.getToken().getType() == RFSMParser.STAY){
				gt.setType(ITPLTree.STATE);
				gt.setTo(TPLTreeUtil.getFirstLeafValue(ct));
			}
		}
	}
	
	private void traverse(CommonTree root, Behavior bhv){
		int size = root.getChildCount();
		for(int i =0; i <size ; i++){
			CommonTree ct =(CommonTree)root.getChild(i);
			if(ct.getToken().getType() == RFSMParser.STATE){
				State s = new State();
				traverse(ct,s);
				bhv.add(s);
			}
			else if(ct.getToken().getType() == RFSMParser.TNAME){
				bhv.setName(TPLTreeUtil.getFirstLeafValue(ct));
			}else if(ct.getToken().getType() == RFSMParser.STATES){
				traverse(ct, bhv);
			}
		}
	}
	
	private void traverse(CommonTree root, State s){
		int size = root.getChildCount();
		for(int i =0; i <size ; i++){
			CommonTree ct =(CommonTree)root.getChild(i);
			if(ct.getToken().getType() == RFSMParser.SMOD){
				s.setMode(TPLTreeUtil.getFirstLeafValue(ct));
			}
			else if(ct.getToken().getType() == RFSMParser.SNAME ){
				s.setName(TPLTreeUtil.getFirstLeafValue(ct));			
			}
			else if(ct.getToken().getType() == RFSMParser.TRANS){
				traverse(ct, s,RFSMParser.TRANS);
			}			
		}
	}
	
	private void traverse(CommonTree root, State s , int type){
		if(type != RFSMParser.TRANS){
			return;
		}
		int size = root.getChildCount();
		for(int i =0; i <size ; i++){
			CommonTree ct =(CommonTree)root.getChild(i);
			
			if(ct.getToken().getType() == RFSMParser.GOTO){
				GoTo gt = new GoTo();
				traverse(ct,gt);
				s.addGoTo(gt);				
			}
			else if(ct.getChildCount() > 0){
				traverse(ct, s,RFSMParser.TRANS);
			}
		}		
	}
	
	public static void main(String args[]){
		DotGenerator dotGen = DotGenerator.getInstance();
		File f  =new File("D:\\eclipse\\tpl\\kr.re.etri.tpl");
		dotGen.generate(f);
	}
	
}
