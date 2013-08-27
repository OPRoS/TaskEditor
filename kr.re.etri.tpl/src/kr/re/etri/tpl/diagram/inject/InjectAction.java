package kr.re.etri.tpl.diagram.inject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import kr.re.etri.tpl.TaskModelPlugin;
import kr.re.etri.tpl.diagram.editors.TPLDiagramEditor;
import kr.re.etri.tpl.script.utils.DiagramResourceUtil;
import kr.re.etri.tpl.taskmodel.IncludedElement;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.ModelDiagram;
import kr.re.etri.tpl.taskmodel.ReferElement;
import kr.re.etri.tpl.taskmodel.SubDiagram;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceImpl;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;

public class InjectAction implements
IWorkbenchWindowActionDelegate{	
	private static Logger logger = Logger.getLogger(InjectAction.class);
	private IWorkbenchWindow m_window;
	private String m_selectedFilePath;
	private List<InjectOperation>  m_operationList= new ArrayList<InjectOperation>();
	
	@Override
	public void dispose() {
	}

	@Override
	public void init(IWorkbenchWindow window) {
		m_window = window;
	}
	
	private TPLDiagramEditor getTPLDiagramEditor(){
		IWorkbenchPage wpage = m_window.getActivePage();
		
		IEditorPart editPart = wpage.getActiveEditor();
		if(!(editPart instanceof TPLDiagramEditor)){
			return null;
		}
		
		return ((TPLDiagramEditor)editPart);
	}
	
	@Override
	public void run(IAction action) {		
		TPLDiagramEditor editor = getTPLDiagramEditor();

		if(editor == null){
			logger.debug("선택된 편집기는 TPLDiagramEditor가 아닙니다.");
			return;
		}		

		ModelDiagram originModel = (ModelDiagram)editor.getModel();
		Resource resource = editor.getResource();
		
		ModelDiagram injectModel = getInjectModel();
		if(injectModel == null){
			logger.debug("InjectModel is null.");
			return ;
		}
//include 파일 처리
		if(!processInclude(injectModel, originModel)){
			return ;
		}
		
		int maxY = findY(originModel.getSubDiagram());
		logger.debug(maxY);
		changeY(injectModel.getSubDiagram(),maxY);
		
		List<ItemElement> injectItems = injectModel.getItems();
		List<ItemElement> originItems = originModel.getItems();
		
		List<ItemElement> tempList = new ArrayList<ItemElement>();
		for(ItemElement item : injectItems){
			item.setParent(originModel);
			tempList.add(item);
		}		
	
		SubDiagram originDiagram = originModel.getSubDiagram();
		List<ReferElement> tempList2 = new ArrayList<ReferElement>();
		List<ReferElement> references=injectModel.getSubDiagram().getItems();
		for(ReferElement elem : references){
			elem.setParent(originDiagram);
			tempList2.add(elem);
		}
		
		if(tempList.size() != tempList2.size()){
			logger.error("ItemElement 와 ReferElement 의 갯수가 다릅니다.");
			return;
		}
		
		int size = tempList.size();
		for(int i =0 ; i < size ; i++){
			originItems.add(tempList.get(i));
			originDiagram.getItems().add(tempList2.get(i));
			try {
				resource.save(Collections.EMPTY_MAP);
			} catch (IOException e) {				
				logger.error("Can not save XMLResourceImpl", e);
			}				
		}
		
		DiagramResourceUtil.projectNavigatorViewRefresh();
		
	}
	
	private ModelDiagram getInjectModel(){
		if(m_selectedFilePath != null){
			m_selectedFilePath = null;
		}
		ModelDiagram rootModel = null;
		Resource resource = new XMLResourceImpl();
		FileDialog fd = new FileDialog(m_window.getShell(), SWT.OPEN);
	    fd.setText("Open");
	    fd.setFilterPath(".");
	    String[] filterExt = {"*.tpd", "*.*"};
	    fd.setFilterExtensions(filterExt);
	    m_selectedFilePath = fd.open();
	    if(m_selectedFilePath == null){
	    	return null;
	    }	    
	    m_selectedFilePath = m_selectedFilePath.replace("\\", "/");
	    logger.debug(m_selectedFilePath);    
		URI uri = URI.createFileURI(m_selectedFilePath);		
		resource.setURI(uri);
		try {
			resource.load(Collections.EMPTY_MAP);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(resource.getContents().size() > 0) {
			rootModel = (ModelDiagram)resource.getContents().get(0);
		}
		return rootModel;
	}

	
	private boolean processInclude(ModelDiagram injectModel, ModelDiagram originModel){
		int index = m_selectedFilePath.lastIndexOf('/');
		logger.debug(index);
		String firstPath = m_selectedFilePath.substring(0, index);
		logger.debug(firstPath);
		index = firstPath.lastIndexOf('/');
		firstPath= firstPath.substring(0,index);
		logger.debug(firstPath);
		List<IncludedElement> injectInclude=injectModel.getIncludeItems();
		List<IncludedElement> originInclude=originModel.getIncludeItems();
		for(IncludedElement elem : injectInclude){
			String path = elem.getIncludePath();
			index = path.lastIndexOf("models");
			String afterPath = path.substring(index, path.length());
			logger.debug(path);
			path = firstPath+path;
			logger.debug(path);
			File f = new File(path);
			if(f.exists()){
				logger.debug("exist");
			}
			else {
				logger.debug("nothing");
			}
			File projectFile = DiagramResourceUtil.getProjectDir();
			String projectPath = projectFile.getPath();
			projectPath = projectPath.replace('\\', '/');
			logger.debug("ProjectFilePath = "+projectPath);
			index = projectPath.lastIndexOf('/');
			String projectName = projectPath.substring(index,projectPath.length());
			String originPath = projectPath+'/'+afterPath;
			
//같은 이름의 파일이 존재한다면 메시지 전송
			File to = new File(originPath);
			if(to.exists()){
				m_operationList.clear();
				Status status = new Status(IStatus.ERROR, TaskModelPlugin.PLUGIN_ID, 0,"Aleady existed Model file.",null);

				ErrorDialog.openError(m_window.getShell(), "Model File Error","" , status);							
				return false;
			}
			logger.debug("IncludePath = "+projectName+'/'+afterPath);
			elem.setIncludePath(projectName+'/'+afterPath);		
			m_operationList.add(new InjectOperation(path,originPath, elem, originInclude));
		}
		
		for(InjectOperation op : m_operationList){
			op.excute();
		}
		return true;
	}
	
	public void copyFile(File from, File to){
		try {
			BufferedReader reader = new BufferedReader(new FileReader(from));
			PrintWriter writer = new PrintWriter(to);
			String line = null;
			while(true){
				line =reader.readLine();
				if(line == null){
					break;
				}
				writer.println(line);
			}
			reader.close();
			writer.close();
		} catch (FileNotFoundException e) {
			logger.error("CopyFile Not Found", e);
		} catch (IOException e) {
			logger.error("", e);
		}
	}
	
	private int findY(SubDiagram diagram){
		int max =0;
		List<ReferElement> elements = diagram.getItems();
		for(ReferElement ele : elements){
			int y = ele.getY()+ele.getHeight();
				
			if(max < y){
				max = y;
			}
		}
		return max;
	}
	
	private void changeY(SubDiagram diagram, int maxY){
		List<ReferElement> elements = diagram.getItems();
		for(ReferElement ele : elements){
			int y = ele.getY();
			ele.setY(y+maxY);
		}
	}
	
	
	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		IWorkbench wb = PlatformUI.getWorkbench();
		IWorkbenchWindow wbw = wb.getActiveWorkbenchWindow();
		IWorkbenchPage wp = wbw.getActivePage();
		if (wp == null) {
			return;
		}
		IEditorPart editPart= wp.getActiveEditor();
		
		if(editPart instanceof TPLDiagramEditor) {
			action.setEnabled(true);
		}
		else {
			action.setEnabled(false);
		}
		
	}

}
