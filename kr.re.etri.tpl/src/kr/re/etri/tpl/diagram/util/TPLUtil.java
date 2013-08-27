package kr.re.etri.tpl.diagram.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import kr.re.etri.tpl.diagram.ModelManager;
import kr.re.etri.tpl.diagram.editors.TPLDiagramEditor;
import kr.re.etri.tpl.diagram.listener.IErrorListener;
import kr.re.etri.tpl.taskmodel.ActionElement;
import kr.re.etri.tpl.taskmodel.BehaviorElement;
import kr.re.etri.tpl.taskmodel.ConnectorElement;
import kr.re.etri.tpl.taskmodel.EnumElement;
import kr.re.etri.tpl.taskmodel.IncludedElement;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.ModelDiagram;
import kr.re.etri.tpl.taskmodel.ModelElement;
import kr.re.etri.tpl.taskmodel.ReferElement;
import kr.re.etri.tpl.taskmodel.StateElement;
import kr.re.etri.tpl.taskmodel.SubDiagram;
import kr.re.etri.tpl.taskmodel.TaskElement;
import kr.re.etri.tpl.taskmodel.TaskModelFactory;
import kr.re.etri.tpl.taskmodel.WithElement;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceImpl;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

public class TPLUtil {
	private static Logger logger = Logger.getLogger(TPLUtil.class);
	
	public TPLUtil() {
		// TODO Auto-generated constructor stub
	}

	public static ItemElement getRootModel(ItemElement child) {
		if(child instanceof ReferElement) {
			return TPLUtil.getRootModel(((ReferElement)child).getRealModel());
		}
		ItemElement parent = child.getParent();
		if(parent == null) {
			return child;
		}

		return TPLUtil.getRootModel(parent);
	}
	
	public static List<ItemElement> getAllActionElements(ModelDiagram rootModel) {
		ArrayList<ItemElement> actionList = new ArrayList<ItemElement>();
		for(IncludedElement incItem : rootModel.getIncludeItems()) {
			getAllActionElements(incItem, actionList);
		}
		for(ItemElement childItem : rootModel.getItems()) {
			if(childItem instanceof IncludedElement) {
				getAllActionElements((IncludedElement)childItem, actionList);
			}
			else if(childItem instanceof ActionElement) {
				actionList.add((ActionElement)childItem);
			}
		}

		return actionList;
	}
	
	public static void getAllActionElements(IncludedElement parentItem, List<ItemElement> list) {
		for(ItemElement childItem : parentItem.getItems()) {
			if(childItem instanceof IncludedElement) {
				getAllActionElements((IncludedElement)childItem, list);
			}
			else if(childItem instanceof ActionElement) {
				list.add((ActionElement)childItem);
			}
		}
	}

	public static List<ItemElement> getAllTaskElements(ModelDiagram rootModel) {
		ArrayList<ItemElement> taskList = new ArrayList<ItemElement>();
		for(IncludedElement incItem : rootModel.getIncludeItems()) {
			getAllTaskElements(incItem, taskList);
		}
		for(ItemElement childItem : rootModel.getItems()) {
			if(childItem instanceof IncludedElement) {
				getAllTaskElements((IncludedElement)childItem, taskList);
			}
			else if(childItem instanceof BehaviorElement) {
				taskList.add((BehaviorElement)childItem);
			}
			else if (childItem instanceof ConnectorElement) {	// KJH 20110816
				taskList.add(childItem);
			}
		}

		return taskList;
	}
	
	public static void getAllTaskElements(IncludedElement parentItem, List<ItemElement> list) {
		for(ItemElement childItem : parentItem.getItems()) {
			if(childItem instanceof IncludedElement) {
				getAllTaskElements((IncludedElement)childItem, list);
			}
			else if(childItem instanceof BehaviorElement) {
				list.add((BehaviorElement)childItem);
			}
			else if (childItem instanceof ConnectorElement) {	// KJH 20110816
				list.add(childItem);
			}
		}
	}

	// KJH 20100825 s, get all behavior element in project
	public static List<ItemElement> getAllTaskElements(IResource resource) {
		List<ItemElement> taskList = new ArrayList<ItemElement>();

		try {
			if (resource instanceof IContainer) {
				for (IResource cResource : ((IContainer)resource).members()) {
					taskList.addAll(getAllTaskElements(cResource));
				}
			}
			else if (resource instanceof IFile) {
				IFile file = (IFile)resource;
				XMLResource xmlResource = new XMLResourceImpl();
				xmlResource.load(file.getContents(), Collections.EMPTY_MAP);
				if (xmlResource.getContents() != null && xmlResource.getContents().size() > 0) {
					ModelDiagram modelDiagram = (ModelDiagram)xmlResource.getContents().get(0);
					taskList.addAll(getAllTaskElements(modelDiagram));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (CoreException e) {
			e.printStackTrace();
		}

		return taskList;
	}
	
	public static IncludedElement convertTo(ModelDiagram model) {
		logger.debug(model);
		TaskModelFactory factory = ModelManager.getFactory();
		IncludedElement incItem = factory.createIncludedElement();
	
		incItem.setName(model.getName());
		
		incItem.setDescription(model.getDescription());
		
		SubDiagram subDiagram = model.getSubDiagram();
		if(subDiagram != null) {
			subDiagram.setParent(incItem);
			incItem.setSubDiagram(subDiagram);
		}

		incItem.setItemState(ItemState.ITEM_CREATED);

		EList<IncludedElement> incluededChildren = model.getIncludeItems();
		List<ItemElement> incChildren = incItem.getItems();
		
		for(IncludedElement incChild : incluededChildren) {
			incChild.setParent(incItem);
		}
		incChildren.addAll(incluededChildren);
		
		List<ItemElement> children = model.getItems();
		for(ItemElement child : children) {
			child.setParent(incItem);
		}
		incChildren.addAll(children);
		
		return incItem;
	}

	public static ModelDiagram convertTo(IncludedElement incModel) {
		TaskModelFactory factory = ModelManager.getFactory();
		ModelDiagram modelItem = factory.createModelDiagram();
		
		modelItem.setName(incModel.getName());
		
		modelItem.setDescription(incModel.getDescription());
		
		SubDiagram subDiagram = incModel.getSubDiagram();
		if(subDiagram != null) {
			subDiagram.setParent(modelItem);
			modelItem.setSubDiagram(subDiagram);
		}

		modelItem.setItemState(ItemState.ITEM_CREATED);
		List<ItemElement> incChildren = incModel.getItems();
		List<ItemElement> itemChildren = modelItem.getItems();
		EList<IncludedElement> incluededChildren = modelItem.getIncludeItems();
		
		for(ItemElement child : incChildren) {
			child.setParent(modelItem);
			
			if(child instanceof IncludedElement) {
				incluededChildren.add((IncludedElement)child);
			}
			else {
				itemChildren.add(child);
			}
		}

		return modelItem;
	}

	public static void mergeModel(ModelDiagram source, ModelDiagram target) {
		SubDiagram srcSubDiagram = source.getSubDiagram();
		SubDiagram tgtSubDiagram = target.getSubDiagram();
		
		if(srcSubDiagram != null) {
			if(tgtSubDiagram == null) {
				TaskModelFactory factory = ModelManager.getFactory();
				tgtSubDiagram = factory.createSubDiagram();
				tgtSubDiagram.setParent(target);
				target.setSubDiagram(tgtSubDiagram);
			}
			List<ReferElement> srcRefList = srcSubDiagram.getItems();
			for(ReferElement refItem : srcRefList) {
				refItem.setParent(tgtSubDiagram);
			}
			tgtSubDiagram.getItems().addAll(srcRefList);
		}

		List<ItemElement> children = source.getItems();
		for(ItemElement child : children) {
			child.setParent(target);
		}
		target.getItems().addAll(children);

		EList<IncludedElement> incluededChildren = source.getIncludeItems();
		for(IncludedElement child : incluededChildren) {
			child.setParent(target);
		}
		target.getIncludeItems().addAll(incluededChildren);
	}

	public static void mergeModel(ModelDiagram source, IncludedElement target) {
		SubDiagram srcSubDiagram = source.getSubDiagram();
		SubDiagram tgtSubDiagram = target.getSubDiagram();
		
		if(srcSubDiagram != null) {
			if(tgtSubDiagram == null) {
				TaskModelFactory factory = ModelManager.getFactory();
				tgtSubDiagram = factory.createSubDiagram();
				tgtSubDiagram.setParent(target);
				target.setSubDiagram(tgtSubDiagram);
			}
			List<ReferElement> srcRefList = srcSubDiagram.getItems();
			for(ReferElement refItem : srcRefList) {
				refItem.setParent(tgtSubDiagram);
			}
			tgtSubDiagram.getItems().addAll(srcRefList);
		}

		List<ItemElement> children = source.getItems();
		for(ItemElement child : children) {
			child.setParent(target);
		}
		target.getItems().addAll(children);
		
		EList<IncludedElement> incluededChildren = source.getIncludeItems();
		for(IncludedElement child : incluededChildren) {
			child.setParent(target);
		}
		target.getItems().addAll(incluededChildren);
	}
	
	public static String getModelPath(ItemElement child) {
		String path, fragment;

		if(child instanceof ReferElement) {
			fragment = TPLUtil.getModelPath(((ReferElement)child).getRealModel());
			return fragment;
		}
		else if(child instanceof IncludedElement) {
			path = ((IncludedElement)child).getIncludePath();
		}
		else {
			path = child.getName();
		}
		ItemElement parent = child.getParent();
		if(parent == null) {
			return path;
		}

		fragment = TPLUtil.getModelPath(parent);
		return String.format("%s.%s", fragment, path);
	}
	
	public static void sortDependency(EList<ItemElement> itemList) {
		ECollections.sort(itemList, new Comparator<ItemElement>() {
			@Override
			public int compare(ItemElement o1, ItemElement o2) {
				if(o1 instanceof IncludedElement) {
					if(o2 instanceof EnumElement) return -1;
					if(o2 instanceof ModelElement) return -1;
					if(o2 instanceof ActionElement) return -1;
					if(o2 instanceof BehaviorElement) return -1;
					if(o2 instanceof ConnectorElement) return -1;	// KJH 20110525
					if(o2 instanceof TaskElement) return -1;
				}
				else if(o1 instanceof EnumElement) {
					if(o2 instanceof IncludedElement) return 1;
					if(o2 instanceof ModelElement) return -1;
					if(o2 instanceof ActionElement) return -1;
					if(o2 instanceof BehaviorElement) return -1;
					if(o2 instanceof ConnectorElement) return -1;	// KJH 20110525
					if(o2 instanceof TaskElement) return -1;
				}
				else if(o1 instanceof ModelElement) {
					if(o2 instanceof IncludedElement) return 1;
					if(o2 instanceof EnumElement) return 1;
					if(o2 instanceof ActionElement) return -1;
					if(o2 instanceof BehaviorElement) return -1;
					if(o2 instanceof ConnectorElement) return -1;	// KJH 20110525
					if(o2 instanceof TaskElement) return -1;
				}
				else if(o1 instanceof ActionElement) {
					if(o2 instanceof IncludedElement) return 1;
					if(o2 instanceof EnumElement) return 1;
					if(o2 instanceof ModelElement) return 1;
					if(o2 instanceof BehaviorElement) return -1;
					if(o2 instanceof ConnectorElement) return -1;	// KJH 20110525
					if(o2 instanceof TaskElement) return -1;
				}
				else if(o1 instanceof BehaviorElement) {
					if(o2 instanceof IncludedElement) return 1;
					if(o2 instanceof EnumElement) return 1;
					if(o2 instanceof ModelElement) return 1;
					if(o2 instanceof ActionElement) return 1;
					if(o2 instanceof ConnectorElement) return -1;	// KJH 20110525
					if(o2 instanceof TaskElement) return -1;
				}
				else if(o1 instanceof ConnectorElement) {	// KJH 20110525 s,
					if(o2 instanceof IncludedElement) return 1;
					if(o2 instanceof EnumElement) return 1;
					if(o2 instanceof ModelElement) return 1;
					if(o2 instanceof ActionElement) return 1;
					if(o2 instanceof BehaviorElement) return 1;
					if(o2 instanceof TaskElement) return -1;
				}	// KJH 20110525 e,
				else if(o1 instanceof TaskElement) {
					if(o2 instanceof IncludedElement) return 1;
					if(o2 instanceof EnumElement) return 1;
					if(o2 instanceof ModelElement) return 1;
					if(o2 instanceof ActionElement) return 1;
					if(o2 instanceof BehaviorElement) return 1;
					if(o2 instanceof ConnectorElement) return 1;	// KJH 20110525
				}
				else {
					return -1;
				}
				
				if(o1.getName() == null) {
					return -1;
				}
				return o1.getName().compareTo(o2.getName());
			}
		});
	}
	
	public static void sortList(EList<ItemElement> itemList) {
		ECollections.sort(itemList, new Comparator<ItemElement>() {
			@Override
			public int compare(ItemElement o1, ItemElement o2) {
				if(o1 instanceof IncludedElement) {
					if(o2 instanceof TaskElement) return -1;
					if(o2 instanceof BehaviorElement) return -1;
					if(o2 instanceof StateElement) return -1;
					if(o2 instanceof ConnectorElement) return -1;	// KJH 20110525
					if(o2 instanceof WithElement) return -1;	// KJH 20110525
					if(o2 instanceof ActionElement) return -1;
				}
				if(o1 instanceof TaskElement) {
					if(o2 instanceof IncludedElement) return 1;
					if(o2 instanceof BehaviorElement) return -1;
					if(o2 instanceof StateElement) return -1;
					if(o2 instanceof ConnectorElement) return -1;	// KJH 20110525
					if(o2 instanceof WithElement) return -1;	// KJH 20110525
					if(o2 instanceof ActionElement) return -1;
				}
				if(o1 instanceof BehaviorElement) {
					if(o2 instanceof IncludedElement) return 1;
					if(o2 instanceof TaskElement) return 1;
					if(o2 instanceof StateElement) return -1;
					if(o2 instanceof ConnectorElement) return -1;	// KJH 20110525
					if(o2 instanceof WithElement) return -1;	// KJH 20110525
					if(o2 instanceof ActionElement) return -1;
				}
				if(o1 instanceof ConnectorElement) {	// KJH 20110525 s,
					if(o2 instanceof IncludedElement) return 1;
					if(o2 instanceof TaskElement) return 1;
					if(o2 instanceof BehaviorElement) return 1;
					if(o2 instanceof StateElement) return -1;
					if(o2 instanceof WithElement) return -1;	// KJH 20110525
					if(o2 instanceof ActionElement) return -1;
				}	// KJH 20110525 e,
				if(o1 instanceof ActionElement) {
					if(o2 instanceof IncludedElement) return 1;
					if(o2 instanceof TaskElement) return 1;
					if(o2 instanceof BehaviorElement) return 1;
					if(o2 instanceof StateElement) return -1;
					if(o2 instanceof ConnectorElement) return 1;	// KJH 20110525
					if(o2 instanceof WithElement) return -1;	// KJH 20110525
				}
				if(o1 instanceof StateElement) {
					if(o2 instanceof IncludedElement) return 1;
					if(o2 instanceof TaskElement) return 1;
					if(o2 instanceof BehaviorElement) return 1;
					if(o2 instanceof ConnectorElement) return 1;	// KJH 20110525
					if(o2 instanceof WithElement) return -1;	// KJH 20110525
					if(o2 instanceof ActionElement) return 1;
				}
				if(o1 instanceof WithElement) {	// KJH 20110525 s,
					if(o2 instanceof IncludedElement) return 1;
					if(o2 instanceof TaskElement) return 1;
					if(o2 instanceof BehaviorElement) return 1;
					if(o2 instanceof StateElement) return 1;
					if(o2 instanceof ConnectorElement) return 1;
					if(o2 instanceof ActionElement) return 1;
				}	// KJH 20110525 e,
				
				if(o1.getName() == null) {
					return -1;
				}
				return o1.getName().compareTo(o2.getName());
			}
		});
	}
	
	public static void sortList(List<ItemElement> itemList) {
		Collections.sort(itemList, new Comparator<ItemElement>() {
			@Override
			public int compare(ItemElement o1, ItemElement o2) {
				if(o1 instanceof IncludedElement) {
					if(o2 instanceof TaskElement) return -1;
					if(o2 instanceof BehaviorElement) return -1;
					if(o2 instanceof StateElement) return -1;
					if(o2 instanceof ConnectorElement) return -1;	// KJH 20110525
					if(o2 instanceof WithElement) return -1;	// KJH 20110525
					if(o2 instanceof ActionElement) return -1;
				}
				if(o1 instanceof TaskElement) {
					if(o2 instanceof IncludedElement) return 1;
					if(o2 instanceof BehaviorElement) return -1;
					if(o2 instanceof StateElement) return -1;
					if(o2 instanceof ConnectorElement) return -1;	// KJH 20110525
					if(o2 instanceof WithElement) return -1;	// KJH 20110525
					if(o2 instanceof ActionElement) return -1;
				}
				if(o1 instanceof BehaviorElement) {
					if(o2 instanceof IncludedElement) return 1;
					if(o2 instanceof TaskElement) return 1;
					if(o2 instanceof StateElement) return -1;
					if(o2 instanceof ConnectorElement) return -1;	// KJH 20110525
					if(o2 instanceof WithElement) return -1;	// KJH 20110525
					if(o2 instanceof ActionElement) return -1;
				}
				if(o1 instanceof ConnectorElement) {	// KJH 20110525 s,
					if(o2 instanceof IncludedElement) return 1;
					if(o2 instanceof TaskElement) return 1;
					if(o2 instanceof BehaviorElement) return 1;
					if(o2 instanceof StateElement) return -1;
					if(o2 instanceof WithElement) return -1;	// KJH 20110525
					if(o2 instanceof ActionElement) return -1;
				}	// KJH 20110525 e,
				if(o1 instanceof ActionElement) {
					if(o2 instanceof IncludedElement) return 1;
					if(o2 instanceof TaskElement) return 1;
					if(o2 instanceof BehaviorElement) return 1;
					if(o2 instanceof StateElement) return -1;
					if(o2 instanceof ConnectorElement) return 1;	// KJH 20110525
					if(o2 instanceof WithElement) return -1;	// KJH 20110525
				}
				if(o1 instanceof StateElement) {
					if(o2 instanceof IncludedElement) return 1;
					if(o2 instanceof TaskElement) return 1;
					if(o2 instanceof BehaviorElement) return 1;
					if(o2 instanceof ConnectorElement) return 1;	// KJH 20110525
					if(o2 instanceof WithElement) return -1;	// KJH 20110525
					if(o2 instanceof ActionElement) return 1;
				}
				if(o1 instanceof WithElement) {	// KJH 20110525 s,
					if(o2 instanceof IncludedElement) return 1;
					if(o2 instanceof TaskElement) return 1;
					if(o2 instanceof BehaviorElement) return 1;
					if(o2 instanceof StateElement) return 1;
					if(o2 instanceof ConnectorElement) return 1;
					if(o2 instanceof ActionElement) return 1;
				}	// KJH 20110525 e,
				
				if(o1.getName() == null) {
					return -1;
				}
				return o1.getName().compareTo(o2.getName());
			}
		});
	}
	
	public static String getWs(int indent) {
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < indent; i += 1) {
			sb.append("\t");
		}
		
		return sb.toString();
	}

	public static IErrorListener getLogger() {
		IErrorListener errorReporter = null;

		IWorkbench wb = PlatformUI.getWorkbench();
		IWorkbenchWindow wbw = wb.getActiveWorkbenchWindow();
		IWorkbenchPage wp = wbw.getActivePage();
		IEditorPart editPart= wp.getActiveEditor();

		if(editPart instanceof TPLDiagramEditor) {
			errorReporter = ((TPLDiagramEditor)editPart).getLogger();
		}
		
		return errorReporter;
	}
	
	public static boolean isExists(IncludedElement includeElmt, String path) {
		if(path == null || path.length() == 0) {
			return false;
		}

		if(path.equals(includeElmt.getIncludePath())) {
			return true;
		}

		EList<ItemElement> itemList = includeElmt.getItems();
		for(ItemElement item : itemList) {
			if(item instanceof IncludedElement) {
				if(isExists((IncludedElement)item, path)) {
					return true;
				}
			}
		}

		return false;
	}
}
