package kr.re.etri.tpl.grammar;

import kr.re.etri.tpl.diagram.ModelManager;
import kr.re.etri.tpl.taskmodel.BehaviorElement;
import kr.re.etri.tpl.taskmodel.ConnectorElement;
import kr.re.etri.tpl.taskmodel.IncludedElement;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.ModelDiagram;
import kr.re.etri.tpl.taskmodel.StateAction;
import kr.re.etri.tpl.taskmodel.StateActionType;
import kr.re.etri.tpl.taskmodel.StateElement;
import kr.re.etri.tpl.taskmodel.StructBlockElement;
import kr.re.etri.tpl.taskmodel.StructType;
import kr.re.etri.tpl.taskmodel.TaskElement;

import org.eclipse.emf.common.util.EList;

public class ItemElementVerify {

	private ItemElementVerify() {
	}
	
	public static void verify(ItemElement element) {
		ItemElementVerify verify = new ItemElementVerify();
		verify.fillEmptyElement(element);
	}
	
	private void fillEmptyElement(ItemElement element) {
		if (element instanceof ModelDiagram) {
			ModelDiagram model = (ModelDiagram)element;
			EList<ItemElement> items = model.getItems();
			for (int i = 0; i < items.size(); i++) {
				ItemElement item = items.get(i);
				fillEmptyElement(item);
			}
			EList<IncludedElement> incItems = model.getIncludeItems();
			for (int i = 0; i < incItems.size(); i++) {
				ItemElement item = incItems.get(i);
				fillEmptyElement(item);
			}
		}
		else if (element instanceof BehaviorElement) {
			BehaviorElement behavior = (BehaviorElement)element;
			if (behavior.getConstruct() == null) {
				StructBlockElement value = ModelManager.getFactory().createStructBlockElement();
				value.setStructType(StructType.CONSTRUCT);
				value.setName("construct");
				value.setParent(behavior);
				behavior.setConstruct(value);
			}
			if (behavior.getDestruct() == null) {
				StructBlockElement value = ModelManager.getFactory().createStructBlockElement();
				value.setStructType(StructType.DESTRUCT);
				value.setName("destruct");
				value.setParent(behavior);
				behavior.setDestruct(value);
			}
			
			EList<StateElement> states = behavior.getStates();
			for (int i = 0; i < states.size(); i++) {
				StateElement state = states.get(i);
				fillEmptyElement(state);
			}
		}
		else if (element instanceof ConnectorElement) {
			ConnectorElement connector = (ConnectorElement)element;
			if (connector.getConstruct() == null) {
				StructBlockElement value = ModelManager.getFactory().createStructBlockElement();
				value.setStructType(StructType.CONSTRUCT);
				value.setName("construct");
				value.setParent(connector);
				connector.setConstruct(value);
			}
			if (connector.getDestruct() == null) {
				StructBlockElement value = ModelManager.getFactory().createStructBlockElement();
				value.setStructType(StructType.DESTRUCT);
				value.setName("destruct");
				value.setParent(connector);
				connector.setDestruct(value);
			}
		}
		else if (element instanceof TaskElement) {
			TaskElement task = (TaskElement)element;
			if (task.getInitialize() == null) {
				StructBlockElement value = ModelManager.getFactory().createStructBlockElement();
				value.setStructType(StructType.CONSTRUCT);
				value.setName("initializer");
				value.setParent(task);
				task.setInitialize(value);
			}
			if (task.getFinalize() == null) {
				StructBlockElement value = ModelManager.getFactory().createStructBlockElement();
				value.setStructType(StructType.DESTRUCT);
				value.setName("finalizer");
				value.setParent(task);
				task.setFinalize(value);
			}
		}
		else if (element instanceof StateElement) {
			StateElement state = (StateElement)element;
			if (state.getEntry() == null) {
				StateAction action = ModelManager.getFactory().createStateAction();
				action.setStateActionType(StateActionType.ENTRY);
				action.setName("entry");
				action.setParent(state);
				state.setEntry(action);
			}
			if (state.getStay() == null) {
				StateAction action = ModelManager.getFactory().createStateAction();
				action.setStateActionType(StateActionType.STAY);
				action.setName("stay");
				action.setParent(state);
				state.setStay(action);
			}
			if (state.getExit() == null) {
				StateAction action = ModelManager.getFactory().createStateAction();
				action.setStateActionType(StateActionType.EXIT);
				action.setName("exit");
				action.setParent(state);
				state.setExit(action);
			}
		}
	}
}
