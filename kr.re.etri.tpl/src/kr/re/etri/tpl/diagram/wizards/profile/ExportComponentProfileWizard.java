package kr.re.etri.tpl.diagram.wizards.profile;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import kr.re.etri.tpl.diagram.util.TreeItem;
import kr.re.etri.tpl.diagram.util.component.ProfileStrings;
import kr.re.etri.tpl.diagram.wizards.profile.ExportComponentProfilePage.ExportInfo;
import kr.re.etri.tpl.script.grammar.tree2.IFuncNode;
import kr.re.etri.tpl.script.grammar.tree2.IModelNode;
import kr.re.etri.tpl.script.grammar.tree2.IParamNode;
import kr.re.etri.tpl.script.grammar.tree2.IVarNode;
import kr.re.etri.tpl.taskmodel.Function;
import kr.re.etri.tpl.taskmodel.ModelElement;
import kr.re.etri.tpl.taskmodel.Parameter;
import kr.re.etri.tpl.taskmodel.Symbol;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IExportWizard;
import org.eclipse.ui.IWorkbench;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

public class ExportComponentProfileWizard extends Wizard implements
		IExportWizard {
	
	private IStructuredSelection selection;
	
	private ExportComponentProfilePage page;
	
	private ExportComponentProfilePage2 page2;

	public ExportComponentProfileWizard() {
		super();
		setNeedsProgressMonitor(true);
		setWindowTitle("Export Component Profile");
	}

	@Override
	public boolean performFinish() {
		final String containerName = page.getContainerName();
		List<Object> resultList = new ArrayList<Object>();
		for (Object result : page.getResults()) {
			if (result instanceof TreeItem &&
					((TreeItem)result).getData() instanceof ExportInfo) {
				resultList.add(result);
			}
		}
		
		final Object[] results = resultList.toArray();

		IRunnableWithProgress op = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor) throws InvocationTargetException {
				try {
					doFinish(containerName, results, monitor);
				} catch (CoreException e) {
					throw new InvocationTargetException(e);
				} catch (IOException e) {
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
		page = new ExportComponentProfilePage(selection);
		addPage(page);
		page2 = new ExportComponentProfilePage2(selection);
		addPage(page2);
	}

	private void doFinish(String containerName, Object[] results, IProgressMonitor monitor)
	throws CoreException, IOException {
		monitor.beginTask("Creating component profile... ", results.length);
		
		File container = new File(containerName);
		if (container.exists() == false || container.isDirectory() == false) {
			throwCoreException("\"" + containerName + "\" does not exist.");
		}
		
		Element root2 = page2.getResult();

		for (Object result : results) {
			if (result instanceof TreeItem == false) {
				continue;
			}
			TreeItem treeItem = (TreeItem)result;
			if (treeItem.getData() instanceof ExportInfo == false) {
				continue;
			}
			
			ExportInfo info = (ExportInfo)treeItem.getData();
			Object sourceObject = info.getSourceObject();
			
			monitor.setTaskName("Creating " + info.getTargetname() + "...");
			
			// main model
			String fileName = containerName + "\\" + info.getTargetname();
			if (sourceObject instanceof IModelNode) {
				IModelNode model = (IModelNode)sourceObject;
				Element root = null;
				if (info.getLevel() == 1) {
					root = makeComponentProfile(model, results);
					Element child = root.getChild(ProfileStrings.PROPERTIES);
					int index = root.indexOf(child);
					if (index > -1) {
						root.addContent(index, root2.cloneContent());
					}
				}
				else if (info.getLevel() == 2) {
					root = makeServicePortTypeProfile(model);
				}
				
				if (root != null) {
					createFile(fileName, root);
				}
			}
			else if (sourceObject instanceof ModelElement) {
				ModelElement model = (ModelElement)sourceObject;
				Element root = null;
				if (info.getLevel() == 1) {
					root = makeComponentProfile(model, results);
					Element child = root.getChild(ProfileStrings.PROPERTIES);
					int index = root.indexOf(child);
					if (index > -1) {
						root.addContent(index, root2.getChildren());
					}
				}
				else if (info.getLevel() == 2) {
					root = makeServicePortTypeProfile(model);
				}
				
				if (root != null) {
					createFile(fileName, root);
				}
			}
			
			monitor.worked(1);
		}
		monitor.done();
	}
	
	private Element makeComponentProfile(IModelNode model, Object[] objects) {
		if (model == null) {
			return null;
		}
		
		Element root = new Element(ProfileStrings.COMPONENT_PROFILE);
		
		Element child = null;
		root.addContent(new Element(ProfileStrings.NAME).addContent(model.getName()));
		root.addContent(new Element(ProfileStrings.DESCRIPTION).addContent(model.getDesc()));

		root.addContent(child = new Element(ProfileStrings.PROPERTIES));
		
		root.addContent(child = new Element(ProfileStrings.EXPORTS));
		for (IVarNode var : model.getVars()) {
			child.addContent(new Element(ProfileStrings.VAR)
							.setAttribute(ProfileStrings.NAME, var.getName())
							.setAttribute(ProfileStrings.TYPE, var.getVType()));
		}
		
		root.addContent(child = new Element(ProfileStrings.PORTS));
		for (IModelNode subModel : model.getModels()) {
			if (isContains(objects, subModel) == false) {
				continue;
			}
			
			Element c;
			child.addContent(c = new Element(ProfileStrings.SERVICE_PORT));
			c.addContent(new Element(ProfileStrings.NAME).addContent(subModel.getName()));
			c.addContent(new Element(ProfileStrings.TYPE).addContent(subModel.getName()));
			c.addContent(new Element(ProfileStrings.USAGE).addContent("provided"));
			c.addContent(new Element(ProfileStrings.REFERENCE).addContent(subModel.getName()+".xml"));
		}

		return root;
	}
	
	private Element makeServicePortTypeProfile(IModelNode model) {
		if (model == null) {
			return null;
		}
		
		Element root = new Element(ProfileStrings.SERVICE_PORT_TYPE_PROFILE);
		
		Element child;
		root.addContent(child = new Element(ProfileStrings.SERVICE_PORT_TYPE));
		
		child.addContent(new Element(ProfileStrings.TYPE_NAME).addContent(model.getName()));
		
		for (IFuncNode func : model.getFunctions()) {
			Element c;
			child.addContent(c = new Element(ProfileStrings.METHOD)
								.setAttribute(ProfileStrings.NAME, func.getName())
								.setAttribute(ProfileStrings.RETURN_TYPE, func.getRtn())
								.setAttribute(ProfileStrings.CALL_TYPE, "nonblocking"));
			
			int index = 1;
			for (IParamNode param : func.getParams()) {
				Element cc;
				c.addContent(cc = new Element(ProfileStrings.PARAM)
							.setAttribute(ProfileStrings.INDEX, Integer.toString(index++)));
				cc.addContent(new Element(ProfileStrings.TYPE).addContent(param.getDataType()));
				cc.addContent(new Element(ProfileStrings.NAME).addContent(param.getName()));
			}
		}
		
		return root;
	}

	private Element makeComponentProfile(ModelElement model, Object[] objects) {
		if (model == null) {
			return null;
		}
		
		Element root = new Element(ProfileStrings.COMPONENT_PROFILE);
		
		Element child = null;
		root.addContent(new Element(ProfileStrings.NAME).addContent(model.getName()));
		root.addContent(new Element(ProfileStrings.DESCRIPTION).addContent(model.getDescription()));

		root.addContent(child = new Element(ProfileStrings.PROPERTIES));
		
		root.addContent(child = new Element(ProfileStrings.EXPORTS));
		for (Symbol var : model.getSymbols()) {
			child.addContent(new Element(ProfileStrings.VAR)
							.setAttribute(ProfileStrings.NAME, var.getName())
							.setAttribute(ProfileStrings.TYPE, var.getType()));
		}
		
		root.addContent(child = new Element(ProfileStrings.PORTS));
		for (ModelElement subModel : model.getModels()) {
			if (isContains(objects, subModel) == false) {
				continue;
			}
			
			Element c;
			child.addContent(c = new Element(ProfileStrings.SERVICE_PORT));
			c.addContent(new Element(ProfileStrings.NAME).addContent(subModel.getName()));
			c.addContent(new Element(ProfileStrings.TYPE).addContent(subModel.getName()));
			c.addContent(new Element(ProfileStrings.USAGE).addContent("provided"));
			c.addContent(new Element(ProfileStrings.REFERENCE).addContent(subModel.getName()+".xml"));
		}

		return root;
	}
	
	private Element makeServicePortTypeProfile(ModelElement model) {
		if (model == null) {
			return null;
		}
		
		Element root = new Element(ProfileStrings.SERVICE_PORT_TYPE_PROFILE);
		
		Element child;
		root.addContent(child = new Element(ProfileStrings.SERVICE_PORT_TYPE));
		
		child.addContent(new Element(ProfileStrings.TYPE_NAME).addContent(model.getName()));
		
		for (Function func : model.getFunctions()) {
			Element c;
			child.addContent(c = new Element(ProfileStrings.METHOD)
								.setAttribute(ProfileStrings.NAME, func.getName())
								.setAttribute(ProfileStrings.RETURN_TYPE, func.getType())
								.setAttribute(ProfileStrings.CALL_TYPE, "nonblocking"));
			
			int index = 1;
			for (Parameter param : func.getParams()) {
				Element cc;
				c.addContent(cc = new Element(ProfileStrings.PARAM)
							.setAttribute(ProfileStrings.INDEX, Integer.toString(index++)));
				cc.addContent(new Element(ProfileStrings.TYPE).addContent(param.getType()));
				cc.addContent(new Element(ProfileStrings.NAME).addContent(param.getName()));
			}
		}
		
		return root;
	}
	
	private boolean isContains(Object[] arr, Object item) {
		for (Object obj : arr) {
			if (obj instanceof TreeItem) {
				TreeItem treeItem = (TreeItem)obj;
				if (treeItem.getData() instanceof ExportInfo) {
					ExportInfo info = (ExportInfo)treeItem.getData();
					if (item != null &&
							item.equals(info.getSourceObject())) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	protected void createFile(String path, Element root) throws IOException {
		File file = new File(path);
		if (file.exists()) {	// backup
			String bak = file.getPath();
			bak = bak.substring(0, bak.lastIndexOf(".")) + ".bak";
			File bakFile =  new File(bak);
			file.renameTo(bakFile);
		}
		XMLOutputter outp = new XMLOutputter();
		Format form = outp.getFormat();
		form.setEncoding("UTF-8");
		form.setLineSeparator("\r\n");
		form.setIndent("	");
		form.setTextMode(Format.TextMode.TRIM);
		outp.setFormat(form);
		
		Document doc = new Document(root);
		FileWriter writer;
		writer = new FileWriter(path);
		outp.output(doc, writer);
		writer.close();
	}
	
	private void throwCoreException(String message) throws CoreException {
		IStatus status =
			new Status(IStatus.ERROR, "kr.re.etri.tpl", IStatus.OK, message, null);
		throw new CoreException(status);
	}

}
