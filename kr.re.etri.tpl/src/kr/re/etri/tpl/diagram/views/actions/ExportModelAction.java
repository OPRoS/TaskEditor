package kr.re.etri.tpl.diagram.views.actions;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import kr.re.etri.tpl.diagram.util.component.ProfileStrings;
import kr.re.etri.tpl.taskmodel.Function;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.ModelDiagram;
import kr.re.etri.tpl.taskmodel.ModelElement;
import kr.re.etri.tpl.taskmodel.Parameter;
import kr.re.etri.tpl.taskmodel.Symbol;

import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.IWorkbenchGraphicConstants;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

public class ExportModelAction extends Action {
	
	private CommandStack commandStack;
	private Shell shell;
	private ItemElement rootModel;
	private ModelElement modelElement;

	public final static String actionId = "kr.re.etri.tpl.views.NavigatorViewPage.ExportModelAction";
	
	public ExportModelAction(ModelDiagram rootModel, CommandStack cmdStack, Shell shell) {
		this("Exp&ort Mode", rootModel, cmdStack, shell);
	}
	
	public ExportModelAction(String text, ModelDiagram rootModel, CommandStack cmdStack, Shell shell) {
		super(text);

		this.rootModel = rootModel;
		this.commandStack = cmdStack;
		this.shell = shell;

		ISharedImages sharedImages = PlatformUI.getWorkbench().getSharedImages();
		setImageDescriptor(sharedImages.getImageDescriptor(IWorkbenchGraphicConstants.IMG_ETOOL_EXPORT_WIZ));
	}
	
	public ExportModelAction(String text, ImageDescriptor image) {
		super(text, image);
	}

	public ExportModelAction(String text, int style) {
		super(text, style);
	}
	
	
	public CommandStack getCommandStack() {
		return commandStack;
	}

	public void setCommandStack(CommandStack commandStack) {
		this.commandStack = commandStack;
	}

	public Shell getShell() {
		return shell;
	}

	public void setShell(Shell shell) {
		this.shell = shell;
	}
	
	public ModelElement getModelElement() {
		return modelElement;
	}

	public void setModelElement(ModelElement modelElement) {
		this.modelElement = modelElement;
	}

	
	@Override
	public String getId() {
		return actionId;
	}

	@Override
	public void run() {
		DirectoryDialog dialog = new DirectoryDialog(shell);
		String directory = dialog.open();
		if (directory == null || directory.equals(""))
			return;
		
		if (!directory.endsWith(File.separator)) {
			directory = directory + File.separator;
		}
		
		Element root = new Element(ProfileStrings.COMPONENT_PROFILE);
			
		// name
		root.addContent(new Element(ProfileStrings.NAME).addContent(modelElement.getName()));
			
		// data types
		Element child = new Element(ProfileStrings.DEFINED_DATA_TYPES);
		root.addContent(child);

		for (ModelElement subModel : modelElement.getModels()) {
			if (subModel.getName().equals(modelElement.getName())) {
				continue;
			}
			
			String path = subModel.getName() + ".xml";
			child.addContent(new Element(ProfileStrings.DEFINED_DATA_TYPE).addContent(path));

			Element subRoot = new Element(ProfileStrings.DATA_TYPE);
			Element subChild = new Element(ProfileStrings.STRUCT).setAttribute(ProfileStrings.NAME, subModel.getName());
			subRoot.addContent(subChild);
			
			for (Symbol symbol : subModel.getSymbols()) {
				subChild.addContent(new Element(ProfileStrings.ITEM).addContent(symbol.getName())
						.setAttribute(ProfileStrings.TYPE, symbol.getType()));
			}
			
			export(directory + path, subRoot);
		}

//		for (Symbol symbol : model.getSymbols()) {
//		}
			
		// service types
		child = new Element(ProfileStrings.DEFINED_SERVICE_TYPES);
		root.addContent(child);

		if (modelElement.getFunctions().size() > 0) {
			String typeName = modelElement.getName() + "_Method";
			String path = typeName + ".xml";	// KJH 20101004, 임의
			child.addContent(new Element(ProfileStrings.DEFINED_SERVICE_TYPE).addContent(path));
			
			Element subRoot = new Element(ProfileStrings.SERVICE_PORT_TYPE_PROFILE);
			Element subChild = new Element(ProfileStrings.SERVICE_PORT_TYPE);
			subRoot.addContent(subChild);
			
			// type_name
			subChild.addContent(new Element(ProfileStrings.TYPE_NAME).addContent(typeName));
			
			for (Function func : modelElement.getFunctions()) {
				Element method = new Element(ProfileStrings.METHOD);
				method.setAttribute(ProfileStrings.NAME, func.getName());
				method.setAttribute(ProfileStrings.RETURN_TYPE, func.getType());
				method.setAttribute(ProfileStrings.CALL_TYPE, "blocking");	// KJH 20101004, 임의
				
				int i = 1;
				for (Parameter param : func.getParams()) {
					Element par = new Element(ProfileStrings.PARAM).setAttribute(ProfileStrings.INDEX, Integer.toString(i++));
					par.addContent(new Element(ProfileStrings.NAME).addContent(param.getName()));
					par.addContent(new Element(ProfileStrings.TYPE).addContent(param.getType()));
					method.addContent(par);		// KJH 20101116
				}
				
				subChild.addContent(method);	// KJH 20101116
			}
			
			export(directory + path, subRoot);
		}
		
		export(directory + modelElement.getName() + ".xml", root);
	}

	public void export(String path, Element root) {
		if (root == null) {
			return;
		}
		
		File file = new File(path);
		if (file.exists()) {
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
