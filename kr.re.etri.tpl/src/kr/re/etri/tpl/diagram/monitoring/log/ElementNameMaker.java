package kr.re.etri.tpl.diagram.monitoring.log;

import kr.re.etri.tpl.diagram.editparts.behavior.BBehaviorDiagramEditPart;
import kr.re.etri.tpl.diagram.editparts.behavior.BBehaviorElementEditPart;
import kr.re.etri.tpl.diagram.editparts.behavior.BConnectionElementEditPart;
import kr.re.etri.tpl.diagram.editparts.behavior.BStateActionEditPart;
import kr.re.etri.tpl.diagram.editparts.behavior.BStateElementEditPart;
import kr.re.etri.tpl.diagram.monitoring.controller.MonitoringElement;
import kr.re.etri.tpl.diagram.outline.BehaviorElementTreeEditPart;
import kr.re.etri.tpl.diagram.outline.ConnectionElementTreeEditPart;
import kr.re.etri.tpl.diagram.outline.StateActionTreeEditPart;
import kr.re.etri.tpl.diagram.outline.StateElementTreeEditPart;
import kr.re.etri.tpl.taskmodel.ConnectionElement;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.ModelDiagram;
import kr.re.etri.tpl.taskmodel.SubDiagram;

import org.apache.log4j.Logger;
import org.eclipse.gef.EditPart;

public class ElementNameMaker {
	private static Logger logger = Logger.getLogger(ElementNameMaker.class);
	private static final String TRANSITION ="transition";
	
	public static String getName(MonitoringElement mElement){
		Object element = mElement.getElement();
		String name ="";
		if(element instanceof BBehaviorDiagramEditPart){
			BBehaviorDiagramEditPart part = (BBehaviorDiagramEditPart)element;
			name = getFullName(part.getModel());
		}else if(element instanceof BBehaviorElementEditPart){
			BBehaviorElementEditPart part = (BBehaviorElementEditPart)element;
			name = getFullName(part.getModel());
		}
		else if(element instanceof BStateElementEditPart){
			BStateElementEditPart part = (BStateElementEditPart)element;
			name = getFullName(part.getModel());
		}
		else if(element instanceof BStateActionEditPart){
			BStateActionEditPart part = (BStateActionEditPart)element;
			name = getFullName(part.getModel());
		}
		else if(element instanceof BConnectionElementEditPart){
			BConnectionElementEditPart part = (BConnectionElementEditPart)element;
			EditPart sourPart = part.getSource();
			String name1 = getFullName(sourPart.getModel());
			String name2 = ((ItemElement)part.getModel()).getName();
			name=name1+"."+ElementNameMaker.TRANSITION+"."+name2;
		}
		else if(element instanceof BehaviorElementTreeEditPart){
			BehaviorElementTreeEditPart part = (BehaviorElementTreeEditPart)element;
			name = getFullName(part.getModel());
		}
		else if(element instanceof StateElementTreeEditPart){
			StateElementTreeEditPart part = (StateElementTreeEditPart)element;
			name = getFullName(part.getModel());
		}
		else if(element instanceof StateActionTreeEditPart){
			StateActionTreeEditPart part = (StateActionTreeEditPart)element;
			name = getFullName(part.getModel());
		}
		else if(element instanceof ConnectionElementTreeEditPart){
			ConnectionElementTreeEditPart part = (ConnectionElementTreeEditPart)element;
			ConnectionElement model =(ConnectionElement)part.getModel();
			
			String name1 = getFullName(model.getSource());
			String name2 = ((ItemElement)part.getModel()).getName();
			name=name1+"."+ElementNameMaker.TRANSITION+"."+name2;
		}
		return name;
	}
	
	private static String getFullName(Object model){
		if(model instanceof SubDiagram){
			SubDiagram sd =(SubDiagram)model;
			return getFullName(sd.getParent());
		}
		else if(model instanceof ModelDiagram){
			ItemElement itemEle = (ItemElement)model;
			return itemEle.getName();
//			return "";
		}
		else if(model instanceof ItemElement){
			ItemElement itemEle = (ItemElement)model;
			return getFullName(itemEle.getParent())+"."+itemEle.getName();
		}
		else{
			logger.debug(model);
		}
		return "";
	}
}
