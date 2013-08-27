package kr.re.etri.tpl.actions;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import kr.re.etri.tpl.IRTMDefines;
import kr.re.etri.tpl.diagram.util.TPLUtil;
import kr.re.etri.tpl.grammar.RTMType;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.IWorkbenchGraphicConstants;
import org.eclipse.ui.navigator.CommonViewer;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

public class ImportModelActionDelegate implements IObjectActionDelegate {

	private static Logger logger = Logger.getLogger(ImportModelActionDelegate.class);
	
	private IWorkbenchPart targetPart;
	private IWorkbenchWindow window;
	private CommonViewer viewer;
	private IContainer container;
	private static final String SCRIPT_EXTENSION = IRTMDefines.TASK_SCRIPT_EXTENSION_NAME;
	private static final String XML_EXTENSION = "xml";
	
	private String m_xmlFolderPath;
	private String m_modelName;	// KJH 20110523
	
	
	public ImportModelActionDelegate() {
	}

	@Override
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		this.targetPart = targetPart;
		this.viewer = (CommonViewer)targetPart.getAdapter(CommonViewer.class);
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
		
		if (container == null) {
			return;
		}
		
		// xml파일 선택
		FileDialog dialog = new FileDialog(window.getShell(), SWT.OPEN);
		dialog.setFilterNames(new String[] {"Xml files (*.xml)", "All files (*.*)"});
		dialog.setFilterExtensions(new String[] {"*.xml", "*.*"});
		dialog.setFilterPath(System.getProperty("user.dir"));
		final String fileName = dialog.open();
		
		int idx = fileName.lastIndexOf(File.separator);
		if (idx > -1) {
			m_xmlFolderPath = fileName.substring(0, idx + 1);
		}
		
		IRunnableWithProgress operation = new IRunnableWithProgress() {
			@Override
			public void run(IProgressMonitor monitor) throws InvocationTargetException,
					InterruptedException {
				runWithProgress(container, fileName, monitor);
			}
		}; 
		
		try {
			window.run(true, false, operation);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void selectionChanged(IAction action, ISelection sel) {
		boolean enable = false;
		
		if (targetPart == null) {
			action.setEnabled(false);
			return;
		}
		
		if (sel instanceof IStructuredSelection) {
			IStructuredSelection selection = (IStructuredSelection)sel;
			Object element = selection.getFirstElement();
			if (element instanceof IContainer) {
				enable = true;
				container = (IContainer)element;
			} else if (element instanceof IFile) {
				enable = true;
				container = ((IFile)element).getParent();
			}
		}
		
		action.setEnabled(enable); 
		
		if (action.getImageDescriptor() == null) {
			ISharedImages sharedImages = PlatformUI.getWorkbench().getSharedImages();
			action.setImageDescriptor(sharedImages.getImageDescriptor(IWorkbenchGraphicConstants.IMG_ETOOL_IMPORT_WIZ));
		}
	}
	
	private void runWithProgress(IContainer container, String xmlPath, IProgressMonitor monitor) {
		Element root = getRootElement(xmlPath);
		if (root == null || !(	root.getName().equals("component_profile") ||
								root.getName().equals("service_port_type_profile"))) {
			return;
		}
		
		// tpl파일 생성
		String script = makeScript(root, 0);
		String fileName = m_modelName;

		String tplPath = String.format("%s.%s", fileName, SCRIPT_EXTENSION);
		IFile file = container.getFile(new Path(tplPath));
		int i = 1;
		while (file.exists()) {	// 중복된 이름있을 경우 넘버링
			tplPath = String.format("%s(%d).%s", fileName, i++, SCRIPT_EXTENSION);
			file = container.getFile(new Path(tplPath));
		}

		try {
			if (!file.exists()) {
				InputStream stream = new ByteArrayInputStream(script.getBytes());
				file.create(stream, true, monitor);
				stream.close();
			}
		} catch (CoreException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * 
	 */
	
	private Element getRootElement(String fileName) {
		SAXBuilder builder = new SAXBuilder();
		Document document = null;
		try {
			document = builder.build(new File(fileName));
		} catch (JDOMException e1) {
			return null;
		} catch (IOException e1) {
			return null;
		}
		
		if (document == null) {
			return null;
		}
		
		return document.getRootElement();
	}
	
	// KJH 20110523 s, modify
	private String makeScript(Element element, int indent) {
		if (element == null) {
			return null;
		}
		
		StringBuffer sb = new StringBuffer();
		
		String name = element.getName();
		if (name == null || name.equals("")) {
			return null;
		}
		
		if (name.equals("component_profile")) {
			m_modelName = element.getChildText("name");

			sb.append(TPLUtil.getWs(indent)).append("model ").append(m_modelName).append(" {\r\n");

			boolean isAppended = false;
			Element defined = element.getChild("monitoring");
			if (defined != null) {
				for (Object obj : defined.getChildren("var")) {
					if (!(obj instanceof Element)) {
						continue;
					}

					String varName = ((Element)obj).getAttributeValue("name");
					String varType = ((Element)obj).getAttributeValue("type");
					varType = convertType(varType);	// KJH 20110523
					
					sb.append(TPLUtil.getWs(indent+1)).append("in ").append(varType).append(" ")
							.append(varName).append(";\r\n");
					
					isAppended = true;
				}
			}
			
			defined = element.getChild("ports");
			if (defined != null) {
				for (Object obj : defined.getChildren("service_port")) {
					if (!(obj instanceof Element)) {
						continue;
					}
					
					String servicePortUsage = ((Element)obj).getChildText("usage");
					if ("provided".equalsIgnoreCase(servicePortUsage) == false) {
						continue;
					}
					
					if (isAppended) {
						sb.append("\r\n");
						isAppended = false;
					}
					
					String servicePortName = ((Element)obj).getChildText("name");
					String servicePortRef = ((Element)obj).getChildText("reference");
					
					if (!servicePortRef.contains(File.separator)) {
						servicePortRef = m_xmlFolderPath + servicePortRef;
					}
					Element root = getRootElement(servicePortRef);
					sb.append(TPLUtil.getWs(indent+1)).append("model ").append(servicePortName).append(" {\r\n");
					sb.append(makeScript(root, indent+2));
					sb.append(TPLUtil.getWs(indent+1)).append("};\r\n");
					
					isAppended = true;
				}
			}
			
			sb.append(TPLUtil.getWs(indent)).append("};\r\n");
		}
		else if (name.equals("service_port_type_profile")) {
			for (Object obj : element.getChildren("service_port_type")) {
				if (!(obj instanceof Element)){
					continue;
				}
				
				Element servicePortType = (Element)obj;

				for (Object obj2 : servicePortType.getChildren("method")) {
					if (!(obj2 instanceof Element)) {
						continue;
					}
					
					Element method = (Element)obj2;
					String methodName = method.getAttributeValue("name");
					String methodType = method.getAttributeValue("return_type");
					methodType = convertType(methodType);	// KJH 20110523
					
					sb.append(TPLUtil.getWs(indent)).append(methodType).append(" ").append(methodName).append("(");

					List params = method.getChildren("param");
					List<Element> sortedParams = new ArrayList<Element>(params.size());

					for (Object obj3 : method.getChildren("param")) {
						if (!(obj3 instanceof Element)) {
							continue;
						}
						
						Element param = (Element)obj3;
						int index = Integer.parseInt(param.getAttributeValue("index")) - 1;
						
						sortedParams.add(index, param);
					}
					
					for (int i=0; i<sortedParams.size(); i++) {
						String paramName = sortedParams.get(i).getChildText("name");
						String paramType = sortedParams.get(i).getChildText("type");
						paramType = convertType(paramType);	// KJH 20110523

						if (i > 0) {
							sb.append(", ");
						}
						sb.append(paramType).append(" ").append(paramName);
					}
					sb.append(");\r\n");
				}
			}
		}
		
		return sb != null ? sb.toString() : "";
	}		// KJH 20110523 s, modify

	/**
	 * @author KJH 20110523
	 */
	private String convertType(String type) {
		if (RTMType.get(type) == null) {
			return "void";
		}
		
		return type;
	}
}
