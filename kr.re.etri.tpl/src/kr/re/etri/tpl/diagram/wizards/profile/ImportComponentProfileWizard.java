package kr.re.etri.tpl.diagram.wizards.profile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import kr.re.etri.tpl.IRTMDefines;
import kr.re.etri.tpl.diagram.ModelManager;
import kr.re.etri.tpl.diagram.util.TPLUtil;
import kr.re.etri.tpl.diagram.util.TreeItem;
import kr.re.etri.tpl.diagram.util.component.ProfileStrings;
import kr.re.etri.tpl.diagram.wizards.profile.ImportComponentProfilePage.ImportInfo;
import kr.re.etri.tpl.grammar.RTMType;
import kr.re.etri.tpl.taskmodel.Function;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.ModelDiagram;
import kr.re.etri.tpl.taskmodel.ModelElement;
import kr.re.etri.tpl.taskmodel.Parameter;
import kr.re.etri.tpl.taskmodel.Symbol;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceImpl;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IImportWizard;
import org.eclipse.ui.IWorkbench;
import org.jdom.Element;

public class ImportComponentProfileWizard extends Wizard implements
		IImportWizard {
	private IStructuredSelection selection;
	private ImportComponentProfilePage page;

	public ImportComponentProfileWizard() {
		super();
		setNeedsProgressMonitor(true);
		setWindowTitle("Import Component Profile");
	}

	@Override
	public boolean performFinish() {
		final String containerName = page.getContainerName();
		final Object[] results = page.getResults();

		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IResource resource = root.findMember(new Path(containerName));
		if (!resource.exists() || !(resource instanceof IContainer)) {
			return false;
		}
		
		IRunnableWithProgress op = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor) throws InvocationTargetException {
				try {
					doFinish(containerName, results, monitor);
				} catch (CoreException e) {
					throw new InvocationTargetException(e);
				} finally {
					monitor.done();
				}
			}
		};
		try {
			getContainer().run(true, false, op);
		} catch (InterruptedException e) {
			return false;
		} catch (InvocationTargetException e) {
			Throwable realException = e.getTargetException();
			MessageDialog.openError(getShell(), "Error", realException.getMessage());
			return false;
		}

		return true;
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.selection = selection;
	}

	@Override
	public void addPages() {
		page = new ImportComponentProfilePage(selection);
		addPage(page);
	}

	private void doFinish(String containerName, Object[] results, IProgressMonitor monitor)
	throws CoreException {
		monitor.beginTask("Creating TPL...", results.length);
		
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IResource resource = root.findMember(new Path(containerName));
		if (!resource.exists() || !(resource instanceof IContainer)) {
			throwCoreException("Container \"" + containerName + "\" does not exist.");
		}
		IContainer container = (IContainer) resource;
		
		for (Object result : results) {
			if (result instanceof TreeItem == false) {
				continue;
			}
			TreeItem treeItem = (TreeItem)result;
			if (treeItem.getData() instanceof ImportInfo == false) {
				continue;
			}
			ImportInfo info = (ImportInfo)treeItem.getData();
			String targetName = info.getTargetname();
			
			String fileName = targetName + "." + IRTMDefines.TASK_DIAGRAM_EXTENSION_NAME; 
			monitor.setTaskName("Creating " + fileName + "...");
			if (info.isCreateTpd()) {
				try {
					String extension = IRTMDefines.TASK_DIAGRAM_EXTENSION_NAME;
					IFile file = container.getFile(new Path(fileName));

					InputStream stream = getInputStream(targetName, extension, treeItem);
					if (stream != null) {
						if (file.exists()) {
							// 파일이 이미 존재한다면 파일을 초기화 시킨다?
							file.setContents(stream, true, true, monitor);
						} else {
							// 파일을 생성한다.
							file.create(stream, true, monitor);
						}
						stream.close();
					}
				} catch (IOException e) {
				}
			}
			
			fileName = targetName + "." + IRTMDefines.TASK_SCRIPT_EXTENSION_NAME; 
			monitor.setTaskName("Creating " + fileName + "...");
			if (info.isCreateTpl()) {
				try {
					String extension = IRTMDefines.TASK_SCRIPT_EXTENSION_NAME;
					IFile file = container.getFile(new Path(fileName));

					InputStream stream = getInputStream(targetName, extension, treeItem);
					if (stream != null) {
						if (file.exists()) {
							// 파일이 이미 존재한다면 파일을 초기화 시킨다?
							file.setContents(stream, true, true, monitor);
						} else {
							// 파일을 생성한다.
							file.create(stream, true, monitor);
						}
						stream.close();
					}
				} catch (IOException e) {
				}
			}
			monitor.worked(1);
		}
		monitor.done();
	}
	
	private InputStream getInputStream(String inputName, String extension, TreeItem treeItem) {
		InputStream stream = null;
		
		if (IRTMDefines.TASK_DIAGRAM_EXTENSION_NAME.equalsIgnoreCase(extension)) {
			ModelDiagram diagram = ModelManager.getFactory().createModelDiagram();
			ItemElement content = createTpdContents(treeItem);
			diagram.setName(inputName);
			diagram.getItems().add(content);
			content.setParent(diagram);
			
			XMLResource resource = new XMLResourceImpl();
			resource.getContents().add(diagram);
		    ByteArrayOutputStream out = new ByteArrayOutputStream();
			try {
				resource.save(out,Collections.EMPTY_MAP);
			} catch (IOException e) {
				e.printStackTrace();
			}
		    stream = new ByteArrayInputStream(out.toByteArray());
		}
		else if (IRTMDefines.TASK_SCRIPT_EXTENSION_NAME.equalsIgnoreCase(extension)) {
			String script = createTplContents(treeItem, 0);
			stream = new ByteArrayInputStream(script.getBytes());
		}
		
		return stream;
	}
	
	private ItemElement createTpdContents(TreeItem treeItem) {
		ImportInfo info = (ImportInfo)treeItem.getData();
		Element element = info.getElement();
		if (info.isCreateTpd() == false) {
			return null;
		}
		
		String eleName = "";
		if (element != null) {
			eleName = element.getName();
		}
		
		if (eleName.equals(ProfileStrings.APPLICATION_PROFILE) ||
				eleName.equals(ProfileStrings.COMPOSITE_COMPONENT_PROFILE)) {
			String modelName = treeItem.getText();
			ModelElement model = ModelManager.getFactory().createModelElement();
			model.setName(modelName);
			
			for (Object obj : treeItem.getChildren()) {
				if (obj instanceof TreeItem == false) {
					continue;
				}
				
				ItemElement subItem = createTpdContents((TreeItem)obj);
				if (subItem instanceof ModelElement) {
					model.getModels().add((ModelElement)subItem);
					subItem.setParent(model);
				}
			}
			
			return model;
		}
		else if (eleName.equals(ProfileStrings.COMPONENT_PROFILE)) {
			String modelName = treeItem.getText();
			ModelElement model = ModelManager.getFactory().createModelElement();
			model.setName(modelName);
			
			if (element != null) {
				Element defined = element.getChild(ProfileStrings.EXPORTS);
				if (defined != null) {
					for (Object child : defined.getChildren(ProfileStrings.VAR)) {
						if (child instanceof Element == false) {
							continue;
						}

						Element childElement = (Element)child;
						String varName = childElement.getAttributeValue("name");
						String varType = childElement.getAttributeValue("type");
						varType = convertType(varType);	// KJH 20110523

						Symbol var = ModelManager.getFactory().createSymbol();
						var.setName(varName);
						var.setType(varType);
						model.getSymbols().add(var);
						var.setParent(model);
					}
				}
			}
			
			for (Object obj : treeItem.getChildren()) {
				if (obj instanceof TreeItem == false) {
					continue;
				}
				
				ItemElement subItem = createTpdContents((TreeItem)obj);
				if (subItem instanceof ModelElement) {
					model.getModels().add((ModelElement)subItem);
					subItem.setParent(model);
				}
			}
			
			return model;
		}
		else if (eleName.equals(ProfileStrings.SERVICE_PORT_TYPE_PROFILE)) {
			String modelName = treeItem.getText();
			ModelElement model = ModelManager.getFactory().createModelElement();
			model.setName(modelName);
			
			if (element != null) {
				Element defined = element.getChild(ProfileStrings.SERVICE_PORT_TYPE);
				for (Object child : defined.getChildren(ProfileStrings.METHOD)) {
					if (child instanceof Element == false) {
						continue;
					}

					Element childElement = (Element)child;
					String methodName = childElement.getAttributeValue(ProfileStrings.NAME);
					String methodType = childElement.getAttributeValue(ProfileStrings.RETURN_TYPE);
					methodType = convertType(methodType);	// KJH 20110523

					Function func = ModelManager.getFactory().createFunction();
					func.setName(methodName);
					func.setType(methodType);
					model.getFunctions().add(func);
					func.setParent(model);

					List params = childElement.getChildren(ProfileStrings.PARAM);
					Object[] arr = params.toArray();
					arr = sort(arr);

					for (int i=0; i<arr.length; i++) {
						if (arr[i] instanceof Element == false) {
							continue;
						}
						Element cchildElement = (Element)arr[i];
						String paramName = cchildElement.getChildText(ProfileStrings.NAME);
						String paramType = cchildElement.getChildText(ProfileStrings.TYPE);
						paramType = convertType(paramType);	// KJH 20110523

						Parameter param = ModelManager.getFactory().createParameter();
						param.setName(paramName);
						param.setType(paramType);
						func.getParams().add(param);
						param.setParent(func);
					}
				}
			}
			
			return model;
		}
		else {
			String modelName = treeItem.getText();
			ModelElement model = ModelManager.getFactory().createModelElement();
			model.setName(modelName);
			return model;
		}
	}
	
	private String createTplContents(TreeItem treeItem, int indent) {
		ImportInfo info = (ImportInfo)treeItem.getData();
		Element element = info.getElement();
		if (info.isCreateTpl() == false) {
			return "";
		}
		
		StringBuffer sb = new StringBuffer();
		String eleName = "";
		if (element != null) {
			eleName = element.getName();
		}
		
		if (eleName.equals(ProfileStrings.APPLICATION_PROFILE) ||
				eleName.equals(ProfileStrings.COMPOSITE_COMPONENT_PROFILE)) {
			String modelName = treeItem.getText();
			boolean isAppended = false;
			
			sb.append(TPLUtil.getWs(indent))
			.append("model ")
			.append(modelName)
			.append(" {\r\n");
			
			for (Object obj : treeItem.getChildren()) {
				if (obj instanceof TreeItem == false) {
					continue;
				}

				if (isAppended) {
					sb.append("\r\n");
					isAppended = false;
				}

				String subStr = createTplContents((TreeItem)obj, indent + 1);
				sb.append(subStr);
				if (subStr.length() > 0) {
					isAppended = true;
				}
			}
			
			sb.append(TPLUtil.getWs(indent))
			.append("};\r\n");
		}
		else if (eleName.equals(ProfileStrings.COMPONENT_PROFILE)) {
			String modelName = treeItem.getText();
			boolean isAppended = false;

			sb.append(TPLUtil.getWs(indent))
			.append("model ")
			.append(modelName)
			.append(" {\r\n");

			if (element != null) {
				Element defined = element.getChild(ProfileStrings.EXPORTS);
				if (defined != null) {
					for (Object child : defined.getChildren(ProfileStrings.VAR)) {
						if (child instanceof Element == false) {
							continue;
						}

						Element childElement = (Element)child;
						String varName = childElement.getAttributeValue("name");
						String varType = childElement.getAttributeValue("type");
						varType = convertType(varType);	// KJH 20110523
						boolean comment = RTMType.get(varType) == null;

						sb.append(comment ? "//" : "")
						.append(TPLUtil.getWs(indent+1))
						.append("in ").append(varType).append(" ")
						.append(varName).append(";\r\n");

						isAppended = true;
					}
				}
			}

			for (Object obj : treeItem.getChildren()) {
				if (obj instanceof TreeItem == false) {
					continue;
				}

				if (isAppended) {
					sb.append("\r\n");
					isAppended = false;
				}

				String subStr = createTplContents((TreeItem)obj, indent + 1);
				sb.append(subStr);
				if (subStr.length() > 0) {
					isAppended = true;
				}
			}
			
			sb.append(TPLUtil.getWs(indent))
			.append("};\r\n");
		}
		else if (eleName.equals(ProfileStrings.SERVICE_PORT_TYPE_PROFILE)) {
			String modelName = treeItem.getText();

			sb.append(TPLUtil.getWs(indent))
			.append("model ")
			.append(modelName)
			.append(" {\r\n");

			if (element != null) {
				Element defined = element.getChild(ProfileStrings.SERVICE_PORT_TYPE);
				for (Object child : defined.getChildren(ProfileStrings.METHOD)) {
					if (child instanceof Element == false) {
						continue;
					}

					boolean comment = false;

					Element childElement = (Element)child;
					String methodName = childElement.getAttributeValue(ProfileStrings.NAME);
					String methodType = childElement.getAttributeValue(ProfileStrings.RETURN_TYPE);
					methodType = convertType(methodType);	// KJH 20110523
					comment |= RTMType.get(methodType) == null;

					StringBuffer sb2 = new StringBuffer();
					sb2.append(TPLUtil.getWs(indent + 1))
					.append(methodType).append(" ")
					.append(methodName).append("(");

					List params = childElement.getChildren(ProfileStrings.PARAM);
					Object[] arr = params.toArray();
					arr = sort(arr);

					for (int i=0; i<arr.length; i++) {
						if (arr[i] instanceof Element == false) {
							continue;
						}
						Element cchildElement = (Element)arr[i];
						String paramName = cchildElement.getChildText(ProfileStrings.NAME);
						String paramType = cchildElement.getChildText(ProfileStrings.TYPE);
						paramType = convertType(paramType);	// KJH 20110523
						comment |= RTMType.get(paramType) == null;

						if (i > 0) {
							sb2.append(", ");
						}
						sb2.append(paramType).append(" ").append(paramName);
					}

					sb2.append(");\r\n");	// end func
					
					sb.append(comment ? "//" : "").append(sb2);
				}
			}
			
			sb.append(TPLUtil.getWs(indent))
			.append("};\r\n");	// end model
		}
		else {
			String modelName = treeItem.getText();
			sb.append(TPLUtil.getWs(indent))
			.append("model ")
			.append(modelName)
			.append(" {\r\n");
		}
		
		return sb.toString();
	}

	private String convertType(String type) {
		int idx = type.lastIndexOf(':');
		if (idx > -1) {
			type = type.substring(idx + 1);
		}
		type = type.toLowerCase();
			
//		if (RTMType.get(type) == null) {
//			return "void";
//		}
		
		return type;
	}
	
	private Object[] sort(Object[] arr) {
		Arrays.sort(arr, new Comparator<Object>() {
			@Override
			public int compare(Object arg0, Object arg1) {
				int o1 = Integer.MAX_VALUE;
				int o2 = Integer.MAX_VALUE;
				if (arg0 instanceof Element) {
					String v = ((Element)arg0).getAttributeValue(ProfileStrings.INDEX);
					if (v != null) {
						o1 = Integer.parseInt(v);
					}
				}
				if (arg1 instanceof Element) {
					String v = ((Element)arg1).getAttributeValue(ProfileStrings.INDEX);
					if (v != null) {
						o2 = Integer.parseInt(v);
					}
				}
					
				return o1 - o2;
			}
		});
		return arr;
	}
	
	private void throwCoreException(String message) throws CoreException {
		IStatus status =
			new Status(IStatus.ERROR, "kr.re.etri.tpl", IStatus.OK, message, null);
		throw new CoreException(status);
	}
}
