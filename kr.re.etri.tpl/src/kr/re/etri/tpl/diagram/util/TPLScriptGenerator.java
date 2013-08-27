package kr.re.etri.tpl.diagram.util;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Hashtable;

import kr.re.etri.tpl.diagram.listener.IErrorListener;
import kr.re.etri.tpl.diagram.util.scripts.ActionElementScript;
import kr.re.etri.tpl.diagram.util.scripts.BehaviorElementScript;
import kr.re.etri.tpl.diagram.util.scripts.ConnectorElementScript;
import kr.re.etri.tpl.diagram.util.scripts.EnumElementScript;
import kr.re.etri.tpl.diagram.util.scripts.IncludedElementScript;
import kr.re.etri.tpl.diagram.util.scripts.ModelElementScript;
import kr.re.etri.tpl.diagram.util.scripts.TaskElementScript;
import kr.re.etri.tpl.taskmodel.ActionElement;
import kr.re.etri.tpl.taskmodel.BehaviorElement;
import kr.re.etri.tpl.taskmodel.ConnectorElement;
import kr.re.etri.tpl.taskmodel.EnumElement;
import kr.re.etri.tpl.taskmodel.IncludedElement;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.ModelDiagram;
import kr.re.etri.tpl.taskmodel.ModelElement;
import kr.re.etri.tpl.taskmodel.TaskElement;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.EList;

public class TPLScriptGenerator {
	private Logger logger = Logger.getLogger(TPLScriptGenerator.class);
	private IErrorListener logListner;
	
	public TPLScriptGenerator(IErrorListener listener) {
		this.logListner = listener;
	}

	public IErrorListener getLogger() {
		return this.logListner;
	}

	public void generateModel(ModelDiagram model, OutputStream oStream) throws TPLException {
		Hashtable<String, ItemElement> nameMap = new Hashtable<String, ItemElement>();
		for(ItemElement item : model.getItems()) {
			if(item instanceof EnumElement) {
				if(nameMap.containsKey(item.getName())) {
					getLogger().warning(String.format("Enum %s 의 이름이 중복되었습니다.", item.getName()), -1, -1, -1);
				}
				nameMap.put(item.getName(), item);
			}
			else if(item instanceof ModelElement) {
				if(nameMap.containsKey(item.getName())) {
					getLogger().warning(String.format("Model %s 의 이름이 중복되었습니다.", item.getName()), -1, -1, -1);
				}
				nameMap.put(item.getName(), item);
			}
			else if(item instanceof BehaviorElement) {
				if(nameMap.containsKey(item.getName())) {
					getLogger().warning(String.format("Task %s 의 이름이 중복되었습니다.", item.getName()), -1, -1, -1);
				}
				nameMap.put(item.getName(), item);
			}
			else if(item instanceof TaskElement) {
				if(nameMap.containsKey(item.getName())) {
					getLogger().warning(String.format("Action %s 의 이름이 중복되었습니다.", item.getName()), -1, -1, -1);
				}
				nameMap.put(item.getName(), item);
			}
		}
		
		if(getLogger().hasError()) {
			return;
		}

		StringBuilder sb = new StringBuilder();
		for(IncludedElement incItem : model.getIncludeItems()) {
			IncludedElementScript incScript = new IncludedElementScript(incItem, 0, this.getLogger());
			incScript.validate();
			sb.append(incScript.toString());
			sb.append("\r\n");
		}
		sb.append("\r\n");

		EList<ItemElement> itemList = model.getItems();
		TPLUtil.sortDependency(itemList);
		for(ItemElement item : model.getItems()) {
			if(item instanceof EnumElement) {
				EnumElementScript enumScript = new EnumElementScript((EnumElement)item, 0, this.getLogger());
				enumScript.validate();
				sb.append(enumScript.toString());
				sb.append("\r\n");
			}
			else if(item instanceof ModelElement) {
				ModelElementScript modelScript = new ModelElementScript((ModelElement)item, 0, this.getLogger());
				modelScript.validate();
				sb.append(modelScript.toString());
				sb.append("\r\n");
			}
		}
		try {
			oStream.write(sb.toString().getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void generateAction(ModelDiagram model, OutputStream oStream) throws TPLException {
		Hashtable<String, ItemElement> nameMap = new Hashtable<String, ItemElement>();
		for(ItemElement item : model.getItems()) {
			if(item instanceof ActionElement) {
				if(nameMap.containsKey(item.getName())) {
					getLogger().warning(String.format("Action %s 의 이름이 중복되었습니다.", item.getName()), -1, -1, -1);
				}
				nameMap.put(item.getName(), item);
			}
		}
		
		if(getLogger().hasError()) {
			return;
		}

		StringBuilder sb = new StringBuilder();
		for(IncludedElement incItem : model.getIncludeItems()) {
			IncludedElementScript incScript = new IncludedElementScript(incItem, 0, this.getLogger());
			incScript.validate();
			sb.append(incScript.toString());
			sb.append("\r\n");
		}
		sb.append("\r\n");

		EList<ItemElement> itemList = model.getItems();
		TPLUtil.sortDependency(itemList);
		for(ItemElement item : model.getItems()) {
			if(item instanceof ActionElement) {
				ActionElementScript actionScript = new ActionElementScript((ActionElement)item, 0, this.getLogger());
				actionScript.validate();
				sb.append(actionScript.toString());
				sb.append("\r\n");
			}
		}
		try {
			oStream.write(sb.toString().getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void generateTask(ModelDiagram model, OutputStream oStream) throws TPLException {
		Hashtable<String, ItemElement> nameMap = new Hashtable<String, ItemElement>();
		for(ItemElement item : model.getItems()) {
			if(item instanceof BehaviorElement) {
				if(nameMap.containsKey(item.getName())) {
					getLogger().warning(String.format("Task %s 의 이름이 중복되었습니다.", item.getName()), -1, -1, -1);
				}
				nameMap.put(item.getName(), item);
			}
		}
		
		if(getLogger().hasError()) {
			return;
		}

		StringBuilder sb = new StringBuilder();
		for(IncludedElement incItem : model.getIncludeItems()) {
			IncludedElementScript incScript = new IncludedElementScript(incItem, 0, this.getLogger());
			incScript.validate();
			sb.append(incScript.toString());
			sb.append("\r\n");
		}
		sb.append("\r\n");

		EList<ItemElement> itemList = model.getItems();
		TPLUtil.sortDependency(itemList);
		for(ItemElement item : model.getItems()) {
			if(item instanceof BehaviorElement) {
				BehaviorElementScript taskScript = new BehaviorElementScript((BehaviorElement)item, 0, this.getLogger());
				taskScript.validate();
				sb.append(taskScript.toString());
				sb.append("\r\n");
			}
		}
		try {
			oStream.write(sb.toString().getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void generateWorker(ModelDiagram model, OutputStream oStream) throws TPLException {
		Hashtable<String, ItemElement> nameMap = new Hashtable<String, ItemElement>();
		for(ItemElement item : model.getItems()) {
			if(item instanceof TaskElement) {
				if(nameMap.containsKey(item.getName())) {
					getLogger().warning(String.format("Task %s 의 이름이 중복되었습니다.", item.getName()), -1, -1, -1);
				}
				nameMap.put(item.getName(), item);
			}
		}
		
		if(getLogger().hasError()) {
			return;
		}

		StringBuilder sb = new StringBuilder();
		for(IncludedElement incItem : model.getIncludeItems()) {
			IncludedElementScript incScript = new IncludedElementScript(incItem, 0, this.getLogger());
			incScript.validate();
			sb.append(incScript.toString());
			sb.append("\r\n");
		}
		sb.append("\r\n");

		EList<ItemElement> itemList = model.getItems();
		TPLUtil.sortDependency(itemList);
		for(ItemElement item : model.getItems()) {
			if(item instanceof TaskElement) {
				TaskElementScript workerScript = new TaskElementScript((TaskElement)item, 0, this.getLogger());
				workerScript.validate();
				sb.append(workerScript.toString());
				sb.append("\r\n");
			}
		}
		try {
			oStream.write(sb.toString().getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void generateAllInOne(ModelDiagram model, OutputStream oStream) throws TPLException {
		Hashtable<String, ItemElement> nameMap = new Hashtable<String, ItemElement>();

		nameMap.clear();
		for(ItemElement item : model.getItems()) {
			if(item instanceof ModelElement) {
				if(nameMap.containsKey(item.getName())) {
					getLogger().warning(String.format("Model %s 의 이름이 중복되었습니다.", item.getName()), -1, -1, -1);
				}
				
				nameMap.put(item.getName(), item);
			}
		}

		nameMap.clear();
		for(ItemElement item : model.getItems()) {
			if(item instanceof ActionElement) {
				if(nameMap.containsKey(item.getName())) {
					getLogger().warning(String.format("Action %s 의 이름이 중복되었습니다.", item.getName()), -1, -1, -1);
				}
				nameMap.put(item.getName(), item);
			}
		}

		nameMap.clear();
		for(ItemElement item : model.getItems()) {
			if(item instanceof BehaviorElement) {
				if(nameMap.containsKey(item.getName())) {
					getLogger().warning(String.format("Behavior %s 의 이름이 중복되었습니다.", item.getName()), -1, -1, -1);
				}
				nameMap.put(item.getName(), item);
			}
		}

		nameMap.clear();
		for(ItemElement item : model.getItems()) {
			if(item instanceof TaskElement) {
				if(nameMap.containsKey(item.getName())) {
					getLogger().warning(String.format("Task %s 의 이름이 중복되었습니다.", item.getName()), -1, -1, -1);
				}
				nameMap.put(item.getName(), item);
			}
		}

		// KJH 20110525 s, connector 이름 중복
		nameMap.clear();
		for(ItemElement item : model.getItems()) {
			if(item instanceof ConnectorElement) {
				if(nameMap.containsKey(item.getName())) {
					getLogger().warning(String.format("Connector %s 의 이름이 중복되었습니다.", item.getName()), -1, -1, -1);
				}
				nameMap.put(item.getName(), item);
			}
		}	// KJH 20110525 e, connector 이름 중복

		if(getLogger().hasError()) {
			return;
		}

		Class beforeClass = null;
		StringBuilder sb = new StringBuilder();
//		sb.append("\r\n");
		
		for(IncludedElement incItem : model.getIncludeItems()) {
			IncludedElementScript incScript = new IncludedElementScript(incItem, 0, this.getLogger());
			incScript.validate();
			sb.append(incScript.toString());
			sb.append("\r\n");
			
			beforeClass = incItem.getClass();
		}

		EList<ItemElement> itemList = model.getItems();
		TPLUtil.sortDependency(itemList);
		for(ItemElement item : model.getItems()) {
			
			if(beforeClass != null) {
				if(beforeClass != item.getClass()) {
					sb.append("\r\n");
				}
			}
			beforeClass = item.getClass();

			if(item instanceof EnumElement) {
				EnumElementScript enumScript = new EnumElementScript((EnumElement)item, 0, this.getLogger());
				enumScript.validate();
				if(getLogger().hasError()) {
					return;
				}
				sb.append(enumScript.toString());
				sb.append("\r\n");
			}
			else if(item instanceof ModelElement) {
				ModelElementScript modelScript = new ModelElementScript((ModelElement)item, 0, this.getLogger());
				modelScript.validate();
				if(getLogger().hasError()) {
					return;
				}
				sb.append(modelScript.toString());
				sb.append("\r\n");
			}
			else if(item instanceof ActionElement) {
				ActionElementScript actionScript = new ActionElementScript((ActionElement)item, 0, this.getLogger());
				
				actionScript.validate();
				if(getLogger().hasError()) {
					return;
				}
				sb.append(actionScript.toString());
				sb.append("\r\n");
			}
			else if(item instanceof BehaviorElement) {
				BehaviorElementScript behaviorScript = new BehaviorElementScript((BehaviorElement)item, 0, this.getLogger());
				behaviorScript.validate();
				if(getLogger().hasError()) {
					return;
				}
				sb.append(behaviorScript.toString());
				sb.append("\r\n");
			}
			else if(item instanceof TaskElement) {
				TaskElementScript taskScript = new TaskElementScript((TaskElement)item, 0, this.getLogger());
				taskScript.validate();
				if(getLogger().hasError()) {
					return;
				}
				sb.append(taskScript.toString());
				sb.append("\r\n");
			}
			// KJH 20101203 s, ConnectorElement
			else if(item instanceof ConnectorElement) {
				ConnectorElementScript connectorScript = new ConnectorElementScript((ConnectorElement)item, 0, this.getLogger());
				connectorScript.validate();
				if (getLogger().hasError()) {
					return;
				}
				sb.append(connectorScript.toString());
				sb.append("\r\n");
			}	// KJH 20101203 e, ConnectorElement
		}
		try {
			oStream.write(sb.toString().getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
