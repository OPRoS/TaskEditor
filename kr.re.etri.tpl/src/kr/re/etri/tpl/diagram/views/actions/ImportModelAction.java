package kr.re.etri.tpl.diagram.views.actions;

import java.io.File;
import java.io.IOException;

import kr.re.etri.tpl.diagram.ModelManager;
import kr.re.etri.tpl.diagram.commands.ModelElementCreateCommand;
import kr.re.etri.tpl.diagram.util.ItemState;
import kr.re.etri.tpl.diagram.util.component.CP;
import kr.re.etri.tpl.taskmodel.Direction;
import kr.re.etri.tpl.taskmodel.Function;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.ModelDiagram;
import kr.re.etri.tpl.taskmodel.ModelElement;
import kr.re.etri.tpl.taskmodel.Parameter;
import kr.re.etri.tpl.taskmodel.Symbol;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gef.requests.CreationFactory;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.IWorkbenchGraphicConstants;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

public class ImportModelAction extends Action {
	
	private CommandStack commandStack;
	private Shell shell;
	private ItemElement rootModel;
	private ItemElement parent;
	
	public final static String actionId = "kr.re.etri.tpl.views.NavigatorViewPage.ImportModelAction";
	
	public ImportModelAction(ModelDiagram rootModel, CommandStack cmdStack, Shell shell) {
		this("&Import Model", rootModel, cmdStack, shell);
	}
	
	public ImportModelAction(String text, ModelDiagram rootModel, CommandStack cmdStack, Shell shell) {
		super(text);
		
		this.rootModel = rootModel;
		this.commandStack = cmdStack;
		this.shell = shell;

		ISharedImages sharedImages = PlatformUI.getWorkbench().getSharedImages();
		setImageDescriptor(sharedImages.getImageDescriptor(IWorkbenchGraphicConstants.IMG_ETOOL_IMPORT_WIZ));
	}
	
	public ImportModelAction(String text, ImageDescriptor image) {
		super(text, image);
	}

	public ImportModelAction(String text, int style) {
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
	
	public ItemElement getParent() {
		if (parent == null)
			return rootModel;
		return parent;
	}

	public void setParent(ItemElement parent) {
		this.parent = parent;
	}


	@Override
	public String getId() {
		return actionId;
	}

	@Override
	public void run() {
		FileDialog dialog = new FileDialog(shell, SWT.OPEN | SWT.APPLICATION_MODAL);
		dialog.setFilterNames(new String[] {"Xml files (*.xml)", "All files (*.*)"});
		dialog.setFilterExtensions(new String[] {"*.xml", "*.*"});
		dialog.setFilterPath(System.getProperty("user.dir"));
		String fileName = dialog.open();

		SAXBuilder builder = new SAXBuilder();
//		builder.setValidation(true);
//		builder.setIgnoringElementContentWhitespace(true);
		Document document = null;
		try {
			document = builder.build(new File(fileName));
		} catch (JDOMException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		if (document == null)
			return;
		
		final Element rootElement = document.getRootElement();
		String rootName = rootElement.getName();
		if (!isEquals(rootName, CP.COMPONENT_PROFILE)) {
			return;
		}
		
		CreateRequest request = new CreateRequest();
		request.setLocation(new Point(10, 10));
		request.setFactory(new CreationFactory() {
			public Object getNewObject() {
				ModelElement model = ModelManager.getFactory().createModelElement();
				
				String modelName = rootElement.getChildText(CP.NAME);

				Element child = rootElement.getChild(CP.DEFINED_DATA_TYPES);
				if (child != null) {
					for (Object object : child.getChildren(CP.DEFINED_DATA_TYPE)) {
						addSubModel(model, object);
					}
				}

				child = rootElement.getChild(CP.DEFINED_SERVICE_TYPES);
				if (child != null) {
					for (Object object : child.getChildren(CP.DEFINED_SERVICE_TYPE)) {
						addFunction(model, object);
					}
				}
				
				model.setName(modelName);
				model.setItemState(ItemState.ITEM_CREATED);
				return model;
			}
			public Object getObjectType() { return null; }
		});

		ModelElementCreateCommand cmd = new ModelElementCreateCommand(getParent(), request);
		CommandStack cmdStack = getCommandStack();

		cmdStack.execute(cmd);
	}

	private void addSubModel(ModelElement model, Object object) {
		if (object instanceof Element) {
			Element element = (Element)object;
			File file = new File(element.getText());
			if (file == null || !file.exists())
				return;
			
			try {
				SAXBuilder builder = new SAXBuilder();
//				builder.setValidation(true);
//				builder.setIgnoringElementContentWhitespace(true);
				Document document = builder.build(file);
				Element rootElement = document.getRootElement();
				
				if (!isEquals(rootElement.getName(), CP.DATA_TYPE))
					return;
				
				Element child = rootElement.getChild(CP.STRUCT);
				String modelName = child.getAttributeValue(CP.NAME);
				
				ModelElement subModel = ModelManager.getFactory().createModelElement();
				subModel.setName(modelName);
				subModel.setItemState(ItemState.ITEM_CREATED);
				subModel.setParent(model);
				model.getModels().add(subModel);
				
				for (Object item : child.getChildren(CP.ITEM)) {
					if (item instanceof Element) {
						child = (Element)item;
						
						String symbolType = child.getAttributeValue(CP.TYPE);
						String symbolName = child.getText();

						Symbol symbol = ModelManager.getFactory().createSymbol();
						symbol.setName(symbolName);
						symbol.setType(symbolType);
						symbol.setDirection(Direction.IN);	// KJH 20100929
						symbol.setItemState(ItemState.ITEM_CREATED);
						symbol.setParent(subModel);
						subModel.getSymbols().add(symbol);
					}
				}
			} catch (JDOMException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void addSymbol(ModelElement model, Object object) {
		if (object instanceof Element) {
			Element element = (Element)object;
			File file = new File(element.getText());
			if (file == null || !file.exists())
				return;
			
			try {
				SAXBuilder builder = new SAXBuilder();
//				builder.setValidation(true);
//				builder.setIgnoringElementContentWhitespace(true);
				Document document = builder.build(file);
				Element rootElement = document.getRootElement();
				
				if (!isEquals(rootElement.getName(), CP.DATA_TYPE))
					return;
				
				Element child = rootElement.getChild(CP.STRUCT);
				String symbolName = child.getAttributeValue(CP.NAME);
				child = child.getChild(CP.ITEM);
				String symbolType = child.getAttributeValue(CP.TYPE);
				
				Symbol symbol = ModelManager.getFactory().createSymbol();
				symbol.setName(symbolName);
				symbol.setType(symbolType);
				symbol.setDirection(Direction.IN);	// KJH 20100929
				symbol.setItemState(ItemState.ITEM_CREATED);
				symbol.setParent(model);
				model.getSymbols().add(symbol);
				
			} catch (JDOMException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void addFunction(ModelElement model, Object object) {
		if (object instanceof Element) {
			Element element = (Element)object;
			File file = new File(element.getText());
			if (file == null || !file.exists())
				return;
			
			try {
				SAXBuilder builder = new SAXBuilder();
//				builder.setValidation(true);
//				builder.setIgnoringElementContentWhitespace(true);
				Document document = builder.build(file);
				Element rootElement = document.getRootElement();
				
				if (!isEquals(rootElement.getName(), CP.SERVICE_PORT_TYPE_PROFILE))
					return;
				
				Element child = rootElement.getChild(CP.SERVICE_PORT_TYPE).getChild(CP.METHOD);
				String funcName = child.getAttributeValue(CP.NAME);
				String funcType = child.getAttributeValue(CP.RETURN_TYPE);
				
				Function func = ModelManager.getFactory().createFunction();
				func.setName(funcName);
				func.setType(funcType);
				func.setItemState(ItemState.ITEM_CREATED);
				func.setParent(model);
				model.getFunctions().add(func);
				
				for (Object obj : child.getChildren(CP.PARAM)) {
					if (obj instanceof Element) {
						child = (Element)obj;
						String paramName = child.getChildText(CP.NAME);
						String paramType = child.getChildText(CP.TYPE);
						
						Parameter param = ModelManager.getFactory().createParameter();
						param.setName(paramName);
						param.setType(paramType);
						param.setItemState(ItemState.ITEM_CREATED);
						param.setParent(func);
						func.getParams().add(param);
					}
				}
			} catch (JDOMException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private boolean isEquals(String s1, String s2) {
		if (s1 == null)
			return false;
		
		return s1.equalsIgnoreCase(s2);
	}
}
