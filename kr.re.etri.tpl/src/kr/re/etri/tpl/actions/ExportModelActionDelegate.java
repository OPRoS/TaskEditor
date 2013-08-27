package kr.re.etri.tpl.actions;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import kr.re.etri.tpl.IRTMDefines;
import kr.re.etri.tpl.script.grammar.tree2.IFuncNode;
import kr.re.etri.tpl.script.grammar.tree2.IModelNode;
import kr.re.etri.tpl.script.grammar.tree2.IParamNode;
import kr.re.etri.tpl.script.grammar.tree2.IScriptNode;
import kr.re.etri.tpl.script.grammar.tree2.IScriptRootNode;
import kr.re.etri.tpl.script.grammar.tree2.IVarNode;
import kr.re.etri.tpl.script.grammar.tree2.ScriptManager;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.IWorkbenchGraphicConstants;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

public class ExportModelActionDelegate implements IObjectActionDelegate {

	private static Logger logger = Logger.getLogger(ExportModelActionDelegate.class);
	
	private IWorkbenchPart targetPart;
	private IWorkbenchWindow window;
	private static final String SCRIPT_EXTENSION = IRTMDefines.TASK_SCRIPT_EXTENSION_NAME;
	private static final String XML_EXTENSION = "xml";
	private List<IFile> selectedFiles;
	private Map<String, Element> exportMap;
	
	public ExportModelActionDelegate() {
	}

	@Override
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		this.targetPart = targetPart;
		this.window = targetPart.getSite().getWorkbenchWindow();
	}

	@Override
	public void run(IAction action) {
		if (window == null) {
			window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		}
		
		if (window == null) {
			return;
		}
		
		if (selectedFiles == null || selectedFiles.size() == 0) {
			logger.debug("Model does not exist");
			return;
		}
		
		DirectoryDialog dialog = new DirectoryDialog(window.getShell());
		dialog.setMessage("Select a directory :");
		String container;
//		container = selectedFiles.get(0).getLocation().toOSString();
//		container = container.substring(0, container.lastIndexOf(File.separator));
//		dialog.setFilterPath(container);
		container = dialog.open();

		if (container == null || container.equals("")) {
			logger.error("Directory is empty");
			return;
		}
		
		if (!container.endsWith(File.separator)) {
			container += File.separator;
		}
		
		IDocument document = null;
		IScriptNode scriptNode = null;
		
		if (exportMap == null) {
			exportMap = new HashMap<String, Element>();
		}
		exportMap.clear();

		for (IFile selectedFile : selectedFiles) {
			scriptNode = ScriptManager.getInstance().getTree(selectedFile, null);

			if (scriptNode instanceof IScriptRootNode) {
				IScriptRootNode rootNode = (IScriptRootNode)scriptNode;
				List<IModelNode> models = rootNode.getModels();
				
				for (IModelNode model : models) {
					String key = makeFileName(model.getName());
					Element profile = makeComponentProfile(model);
					
					exportMap.put(key, profile);
					
					for (IModelNode subModel : model.getModels()) {
						key = makeFileName(subModel.getName());
						profile = makeServicePortTypeProfile(subModel);
						
						exportMap.put(key, profile);
					}
				}
			}
		}
		
		Iterator<String> iter = exportMap.keySet().iterator();
		while (iter.hasNext()) {
			String key = iter.next();
			export(container + key, exportMap.get(key));
		}
	}

	@Override
	public void selectionChanged(IAction action, ISelection sel) {
		if (targetPart == null) {
			action.setEnabled(false);
			return;
		}
		
		if (selectedFiles == null) {
			selectedFiles = new ArrayList<IFile>();
		}
		selectedFiles.clear();
		
		if (sel instanceof IStructuredSelection) {
			IStructuredSelection selection = (IStructuredSelection)sel;

			// KJH 20110525 s, 다중선택 지원
			Iterator iter = selection.iterator();
			while (iter.hasNext()) {
				Object element = iter.next();
				if (element instanceof IResource) {
					getSelectedFiles((IResource)element, selectedFiles);
				}
			}	// KJH 20110525 e, 다중선택 지원
		}
		
		action.setEnabled(selectedFiles.size() > 0);
		
		if (action.getImageDescriptor() == null) {
			ISharedImages sharedImages = PlatformUI.getWorkbench().getSharedImages();
			action.setImageDescriptor(sharedImages.getImageDescriptor(IWorkbenchGraphicConstants.IMG_ETOOL_EXPORT_WIZ));

		}
	}
	
	/*
	 * 
	 */
	
	// KJH 20101119 s, xml파일 이름 구하는 기능
	private String makeFileName(String name) {
		String key = String.format("%s.%s", name, XML_EXTENSION);
		int i = 1;
		while (exportMap.containsKey(key)) {	// 같은이름에 대해 model(1).xml
			key = String.format("%s(%d).%s", name, i++, XML_EXTENSION);
		}
		
		return key;
	}	// KJH 20101119 e, xml파일 이름 구하는 기능
	
	private void getSelectedFiles(IResource resource, List<IFile> selectedFiles) {
		if (resource instanceof IFile) {
			IFile file = (IFile)resource;
			if (SCRIPT_EXTENSION.equals(file.getFileExtension())
					&& !selectedFiles.contains(file)) {
				selectedFiles.add(file);
			}
		} else if (resource instanceof IContainer) {
			IContainer container = (IContainer)resource;
			try {
				for (IResource member : container.members()) {
					getSelectedFiles(member, selectedFiles);
				}
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
	}
	
	// KJH 20110524 s, component_profile 생성부 수정
	private Element makeComponentProfile(IModelNode model) {
		if (model == null) {
			return null;
		}
		
		Element root = new Element("component_profile");
		
		Element child = null;
		root.addContent(new Element("name").addContent(model.getName()));
		root.addContent(new Element("description").addContent(model.getDesc()));

		root.addContent(child = new Element("execution_environment"));
		child.addContent(new Element("os")
						.setAttribute("name", "NoNameOS")
						.setAttribute("version", "NoVersion"));
		child.addContent(new Element("cpu"));
		child.addContent(new Element("library_type"));
		child.addContent(new Element("library_name"));
		child.addContent(new Element("impl_language"));
		child.addContent(new Element("compiler"));
		
		root.addContent(child = new Element("execution_semantics"));
		child.addContent(new Element("type"));
		child.addContent(new Element("period"));
		child.addContent(new Element("priority"));
		child.addContent(new Element("creation_type"));
		child.addContent(new Element("lifecycle_type"));
		
		root.addContent(child = new Element("properties"));
		
		root.addContent(child = new Element("monitoring"));
		for (IVarNode var : model.getVars()) {
			child.addContent(new Element("var")
							.setAttribute("name", var.getName())
							.setAttribute("type", var.getVType()));
		}
		
		root.addContent(child = new Element("ports"));
		for (IModelNode subModel : model.getModels()) {
			Element c;
			child.addContent(c = new Element("service_port"));
			c.addContent(new Element("name").addContent(subModel.getName()));
			c.addContent(new Element("type").addContent(subModel.getName()));
			c.addContent(new Element("usage").addContent("provided"));
			c.addContent(new Element("reference").addContent(subModel.getName()+".xml"));
		}

		return root;
	}	// KJH 20110524 e, component_profile 생성부 수정
	
	private Element makeServicePortTypeProfile(IModelNode model) {
		if (model == null) {
			return null;
		}
		
		Element root = new Element("service_port_type_profile");
		
		Element child;
		root.addContent(child = new Element("service_port_type"));
		
		child.addContent(new Element("type_name").addContent(model.getName()));
		
		for (IFuncNode func : model.getFunctions()) {
			Element c;
			child.addContent(c = new Element("method")
								.setAttribute("name", func.getName())
								.setAttribute("return_type", func.getRtn())
								.setAttribute("call_type", "nonblocking"));
			
			int index = 1;
			for (IParamNode param : func.getParams()) {
				Element cc;
				c.addContent(cc = new Element("param")
							.setAttribute("index", Integer.toString(index++)));
				cc.addContent(new Element("type").addContent(param.getDataType()));
				cc.addContent(new Element("name").addContent(param.getName()));
			}
		}
		
		return root;
	}
	
	private void export(String path, Element root) {
		if (root == null) {
			return;
		}
		
		File file = new File(path);
		if (file.exists()) {	// backup
			String bak = file.getPath();
			bak = bak.substring(0, bak.lastIndexOf(".")) + ".bak";
			File bakFile =  new File(bak);
			file.renameTo(bakFile);
		}
		XMLOutputter outp = new XMLOutputter();
		Format form = outp.getFormat();
		form.setEncoding("euc-kr");
		form.setLineSeparator("\r\n");
		form.setIndent("	");
		form.setTextMode(Format.TextMode.TRIM);
		outp.setFormat(form);
		
		Document doc = new Document(root);
		FileWriter writer;
		try {
			writer = new FileWriter(path);
			outp.output(doc, writer);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
