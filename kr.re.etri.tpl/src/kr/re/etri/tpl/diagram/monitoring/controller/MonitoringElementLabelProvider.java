package kr.re.etri.tpl.diagram.monitoring.controller;

import kr.re.etri.tpl.TaskModelPlugin;
import kr.re.etri.tpl.diagram.editparts.behavior.BBehaviorDiagramEditPart;
import kr.re.etri.tpl.diagram.editparts.behavior.BBehaviorElementEditPart;
import kr.re.etri.tpl.diagram.editparts.behavior.BConnectionElementEditPart;
import kr.re.etri.tpl.diagram.editparts.behavior.BStateActionEditPart;
import kr.re.etri.tpl.diagram.editparts.behavior.BStateElementEditPart;
import kr.re.etri.tpl.diagram.outline.BehaviorElementTreeEditPart;
import kr.re.etri.tpl.diagram.outline.ConnectionElementTreeEditPart;
import kr.re.etri.tpl.diagram.outline.StateActionTreeEditPart;
import kr.re.etri.tpl.diagram.outline.StateElementTreeEditPart;
import kr.re.etri.tpl.taskmodel.ConnectionElement;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.ModelDiagram;
import kr.re.etri.tpl.taskmodel.StateAction;
import kr.re.etri.tpl.taskmodel.StateActionType;
import kr.re.etri.tpl.taskmodel.SubDiagram;

import org.apache.log4j.Logger;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

public class MonitoringElementLabelProvider extends LabelProvider implements ITableLabelProvider{
	private static Logger logger = Logger.getLogger(MonitoringElementLabelProvider.class);
	private final String BEHAVIOR ="behavior";
	private final String STATE ="state";
	private final String ENTRY ="entry";
	private final String EXIT ="exit";
	private final String DO ="do";
	private final String TRANSITION ="transition";
	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		if(columnIndex == 0){
			if(element instanceof MonitoringElement){
				boolean checked = ((MonitoringElement)element).getChecked();
				if(checked){
					return TaskModelPlugin.getImageDescriptor("/icons/checked.gif").createImage();
				}
				else{
					return TaskModelPlugin.getImageDescriptor("/icons/unchecked.gif").createImage();
				}				
			}
			else{
				logger.info("Unexpected element type : "+element);
			}
		}
		return null;
	}

	@Override
	public String getColumnText(Object monitoringElement, int columnIndex) {	
		logger.debug(monitoringElement);
		if(!(monitoringElement instanceof MonitoringElement)){
			logger.warn("Element is not MonitoringElement");
			return null;
		}
		Object element = ((MonitoringElement)monitoringElement).getElement();
		logger.debug(element);
		switch (columnIndex) {
		case 0:
			return null;
		case 1:
			String name="";
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
				name=name1+"."+this.TRANSITION+"."+name2;
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
				name=name1+"."+this.TRANSITION+"."+name2;
			}
			return name;
		case 2:
			String type ="";
			if(element instanceof BBehaviorElementEditPart){
				type= BEHAVIOR;
			}
			else if(element instanceof BStateElementEditPart){
				type= this.STATE;
			}
			else if(element instanceof BStateActionEditPart){
				BStateActionEditPart part = (BStateActionEditPart)element;
				StateAction sa = (StateAction)part.getCastedModel();
				StateActionType t = sa.getStateActionType();
				if(t == StateActionType.ENTRY){
					type = ENTRY;
				}
				else if(t == StateActionType.EXIT){
					type =EXIT;
				}
				else if(t == StateActionType.STAY){
					type =DO;
				}
			}
			else if(element instanceof BConnectionElementEditPart){
				type= this.TRANSITION;
			}
			else if(element instanceof BehaviorElementTreeEditPart){
				type= BEHAVIOR;
			}
			else if(element instanceof StateElementTreeEditPart){
				type= this.STATE;
			}
			else if(element instanceof StateActionTreeEditPart){
				StateActionTreeEditPart part = (StateActionTreeEditPart)element;
				StateAction sa = (StateAction)part.getModel();
				StateActionType t = sa.getStateActionType();
				if(t == StateActionType.ENTRY){
					type = ENTRY;
				}
				else if(t == StateActionType.EXIT){
					type =EXIT;
				}
				else if(t == StateActionType.STAY){
					type =DO;
				}
			}
			else if(element instanceof ConnectionElementTreeEditPart){
				type= this.TRANSITION;
			}
			return type;
		default:
			logger.info("Unsuppored ColumnIndex.");
			return "";
		}
	}
		
	private String getFullName(Object model){
		logger.debug(model);
		if(model instanceof SubDiagram){
			SubDiagram sd =(SubDiagram)model;
			return getFullName(sd.getParent());
		}
		else if(model instanceof ModelDiagram){
			ItemElement itemEle = (ItemElement)model;
			return itemEle.getName();
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
