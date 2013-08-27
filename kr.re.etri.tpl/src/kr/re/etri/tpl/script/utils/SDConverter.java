package kr.re.etri.tpl.script.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.re.etri.tpl.IRTMDefines;
import kr.re.etri.tpl.diagram.editors.DiagramResourceManager;
import kr.re.etri.tpl.script.grammar.tree2.FileManager;
import kr.re.etri.tpl.script.grammar.tree2.IBehaviorNode;
import kr.re.etri.tpl.script.grammar.tree2.IScriptNode;
import kr.re.etri.tpl.script.grammar.tree2.ScriptManager;
import kr.re.etri.tpl.taskmodel.BehaviorElement;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.ModelDiagram;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

public class SDConverter {
	private static Map<IScriptNode, ItemElement> behaviorMap;
	
	// 다이어그램의 ItemElement와 해당 스크립트 파일의 IScriptNode를 짝짓는 함수
	public static void init(IProject project) {
		if (behaviorMap != null) {
			behaviorMap.clear();
		}
		else {
			behaviorMap = new HashMap<IScriptNode, ItemElement>();
		}
		
		List<IFile> diagrams = FileManager.getFiles(project, IRTMDefines.TASK_DIAGRAM_EXTENSION_NAME);
		
		for (IFile diagram : diagrams) {
			Resource xmlResource = DiagramResourceManager.getResource(diagram);
			// KJH 20101018 s, getResource()에서 처리
//			try {
//				xmlResource.load(Collections.EMPTY_MAP);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
			// KJH 20101018 e, getResource()에서 처리
			
			EList<EObject> contents = xmlResource.getContents();	// KJH 20101018
			if (contents != null && contents.size() > 0) {
				ModelDiagram modelDiagram = (ModelDiagram)contents.get(0);
				String scriptPath = modelDiagram.getScript();
				if (scriptPath == null || scriptPath.equals(""))
					continue;
				
				IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
				IResource script = root.findMember(scriptPath);
				IScriptNode scriptNode = ScriptManager.getInstance().getTree(
						(script instanceof IFile) ? (IFile)script : null, null);
				
				if (modelDiagram != null && scriptNode != null) {
					for (ItemElement item : modelDiagram.getItems()) {
						for (IScriptNode childNode : scriptNode.getChildren()) {
							if (item instanceof BehaviorElement && 
									childNode instanceof IBehaviorNode &&
									item.getName().equals(childNode.getName())) {
								behaviorMap.put(childNode, item);
								break;
							}
						}
					}
				}
			}
		}
	}
	
	public static void put(IScriptNode scriptNode, ItemElement itemElement) {
		if (behaviorMap == null)
			behaviorMap = new HashMap<IScriptNode, ItemElement>();
		behaviorMap.put(scriptNode, itemElement);
	}
	
	public static ItemElement convertToDiagram(IScriptNode scriptNode) {
		if (behaviorMap != null) {
			return behaviorMap.get(scriptNode);
		}
		return null;
	}
	
	public static IScriptNode convertToScript(ItemElement itemElement) {
		if (behaviorMap != null && behaviorMap.containsValue(itemElement)) {
			for (IScriptNode scriptNode : behaviorMap.keySet()) {
				if (behaviorMap.get(scriptNode).equals(itemElement)) {
					return scriptNode;
				}
			}
		}
		return null;
	}
}
