package kr.re.etri.tpl.diagram.util.scripts;

import java.util.Hashtable;

import kr.re.etri.tpl.diagram.listener.IErrorListener;
import kr.re.etri.tpl.diagram.util.TPLException;
import kr.re.etri.tpl.diagram.util.TPLUtil;
import kr.re.etri.tpl.taskmodel.Constant;
import kr.re.etri.tpl.taskmodel.Function;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.ModelElement;
import kr.re.etri.tpl.taskmodel.Parameter;
import kr.re.etri.tpl.taskmodel.Symbol;

import org.eclipse.emf.common.util.EList;

public class ModelElementScript extends ScriptGenerator {

	public ModelElementScript(ModelElement model, int indent, IErrorListener logger) {
		super(model, indent, logger);
	}

	protected ModelElement getCastedModel() {
		return (ModelElement)getModel();
	}
	
	/**
	 * ModelElement�� ��ȿ���� �˻��Ѵ�.
	 * @throws TPLException
	 */
	public void validate() throws TPLException {
		EList<Symbol> symList = getCastedModel().getSymbols();
		EList<Constant> constList = getCastedModel().getConstants();
		EList<Function> functionList = getCastedModel().getFunctions();
		EList<ModelElement> modelList = getCastedModel().getModels();

		if(getCastedModel().getName() == null) {
			getLogger().error("Model �̸��� �������� �ʾҽ��ϴ�.", -1, -1, -1);
		}

		// Model�� Symbol�� �˻��Ѵ�.
		Hashtable<String, ItemElement> paramMap = new Hashtable<String, ItemElement>();
		Hashtable<String, ItemElement> nameMap = new Hashtable<String, ItemElement>();
		for(int idx = 0; idx < symList.size(); idx += 1) {
			Symbol item = symList.get(idx);
			if(item.getType() == null) {
				getLogger().error(String.format("Model %s �� Symbol Ÿ���� �������� �ʾҽ��ϴ�.", getCastedModel().getName()), -1, -1, -1);
			}
			if(item.getName() == null) {
				getLogger().error(String.format("Model %s �� Symbol �̸��� �������� �ʾҽ��ϴ�.", getCastedModel().getName()), -1, -1, -1);
			}
			
			if(nameMap.containsKey(item.getName())) {
				getLogger().warning(String.format("Model %s �� Symbol �̸�(%s)�� �ߺ��Ǿ����ϴ�.", getCastedModel().getName(), item.getName()), -1, -1, -1);
			}
			nameMap.put(item.getName(), item);
		}

		// Model�� Constant�� �˻��Ѵ�.
		nameMap.clear();
		for(int idx = 0; idx < constList.size(); idx += 1) {
			Constant constItem = constList.get(idx);
			if(constItem.getType() == null) {
				getLogger().error(String.format("Model %s �� const Ÿ���� �������� �ʾҽ��ϴ�.", getCastedModel().getName()), -1, -1, -1);
			}
			if(constItem.getName() == null) {
				getLogger().error(String.format("Model %s �� const �̸��� �������� �ʾҽ��ϴ�.", getCastedModel().getName()), -1, -1, -1);
			}
			if(constItem.getInitValue() == null) {
				getLogger().error(String.format("Model %s �� const %s�� �ʱ� ���� �������� �ʾҽ��ϴ�.", getCastedModel().getName(), constItem.getName()), -1, -1, -1);
			}

			if(nameMap.containsKey(constItem.getName())) {
				getLogger().warning(String.format("Model %s �� const �̸�(%s)�� �ߺ��Ǿ����ϴ�.", getCastedModel().getName(), constItem.getName()), -1, -1, -1);
			}
			nameMap.put(constItem.getName(), constItem);
		}

		// Model�� FunctioN�� �˻��Ѵ�.
		nameMap.clear();
		for(int idx = 0; idx < functionList.size(); idx += 1) {
			Function funcItem = functionList.get(idx);
			if(funcItem.getType() == null) {
				getLogger().error(String.format("Model %s �� function ��ȯ Ÿ���� �������� �ʾҽ��ϴ�.", getCastedModel().getName()), -1, -1, -1);
			}
			if(funcItem.getName() == null) {
				getLogger().error(String.format("Model %s �� function �̸��� �������� �ʾҽ��ϴ�.", getCastedModel().getName()), -1, -1, -1);
			}

			// Function�� Parameter�� �˻��Ѵ�.
			paramMap.clear();
			EList<Parameter> paramList = funcItem.getParams();
			for(int paramIdx = 0; paramIdx < paramList.size(); paramIdx += 1) {
				Parameter paramItem = paramList.get(paramIdx);
				if(paramItem.getType() == null) {
					getLogger().error(String.format("Function %s.%s ��  parameter Ÿ���� �������� �ʾҽ��ϴ�.", getCastedModel().getName(), funcItem.getName()), -1, -1, -1);
				}
				if(paramItem.getName() == null) {
					getLogger().error(String.format("Function %s.%s ��  parameter �̸��� �������� �ʾҽ��ϴ�.", getCastedModel().getName(), funcItem.getName()), -1, -1, -1);
				}
				
				if(paramMap.containsKey(paramItem.getName())) {
					getLogger().warning(String.format("Function %s.%s �� parameter �̸�(%s)�� �ߺ��˴ϴ�.", getCastedModel().getName(), funcItem.getName(), paramItem.getName()), -1, -1, -1);
				}
				paramMap.put(paramItem.getName(), paramItem);
			}
		}

		// Model�� �ڽ� Model�� �˻��Ѵ�.
		nameMap.clear();
		for(int idx = 0; idx < modelList.size(); idx += 1) {
			ModelElement item = modelList.get(idx);
			
			if(nameMap.containsKey(item.getName())) {
				getLogger().warning(String.format("Model %s �� Model �̸�(%s)�� �ߺ��Ǿ����ϴ�.", getCastedModel().getName(), item.getName()), -1, -1, -1);
			}
			nameMap.put(item.getName(), item);
		}

		for(int idx = 0; idx < modelList.size(); idx += 1) {
			ModelElement item = modelList.get(idx);

			ModelElementScript modelScript = new ModelElementScript(item, getIndent()+1, getLogger());
			modelScript.validate();
		}
	}

	@Override
	public String toString() {
		if(getCastedModel() == null) {
			return "";
		}
		int idx;

		EList<Symbol> symList = getCastedModel().getSymbols();
		EList<Constant> constList = getCastedModel().getConstants();
		EList<Function> functionList = getCastedModel().getFunctions();
		EList<ModelElement> modelList = getCastedModel().getModels();

		StringBuilder sb = new StringBuilder();
		sb.append(TPLUtil.getWs(getIndent())).append("model").append(" ").append(getCastedModel().getName()).append("\r\n");
		sb.append(TPLUtil.getWs(getIndent())).append("{").append("\r\n");

		for(idx = 0; idx < symList.size(); idx += 1) {
			Symbol item = symList.get(idx);
			sb.append(TPLUtil.getWs(getIndent()+1));
			sb.append(item.getDirection()).append(" ");
			sb.append(item.getType()).append(" ");
			sb.append(item.getName()).append(";\r\n");
		}
		if(idx > 0) {
			sb.append("\r\n");
		}

		for(idx = 0; idx < constList.size(); idx += 1) {
			Constant constItem = constList.get(idx);
			sb.append(TPLUtil.getWs(getIndent()+1));
			sb.append("const ");
			sb.append(constItem.getType()).append(" ");
			sb.append(constItem.getName()).append(" = ");
			sb.append(constItem.getInitValue()).append(";\r\n");
		}
		if(idx > 0) {
			sb.append("\r\n");
		}

		for(idx = 0; idx < functionList.size(); idx += 1) {
			Function funcItem = functionList.get(idx);
			sb.append(TPLUtil.getWs(getIndent()+1));
			sb.append(funcItem.getType()).append(" ");
			sb.append(funcItem.getName()).append("(");

			EList<Parameter> paramList = funcItem.getParams();
			for(int paramIdx = 0; paramIdx < paramList.size(); paramIdx += 1) {
				Parameter paramItem = paramList.get(paramIdx);
				if(paramIdx > 0) {
					sb.append(",");
				}
				sb.append(" ").append(paramItem.getType());
				sb.append(" ").append(paramItem.getName());
			}
			sb.append(");").append("\r\n");
		}
		if(idx > 0) {
			sb.append("\r\n");
		}

		for(idx = 0; idx < modelList.size(); idx += 1) {
			ModelElement item = modelList.get(idx);
			
			ModelElementScript modelScript = new ModelElementScript(item, getIndent()+1, getLogger());
			sb.append(modelScript.toString());
			sb.append("\r\n");
		}

		sb.append(TPLUtil.getWs(getIndent())).append("};").append("\r\n");

		return sb.toString();
	}
}
