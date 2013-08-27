package kr.re.etri.tpl.diagram.util.component;

import java.io.FileWriter;
import java.io.IOException;

import kr.re.etri.tpl.taskmodel.Function;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.ModelElement;
import kr.re.etri.tpl.taskmodel.Symbol;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

public class ExportModel {
	public static void export(String directory, ItemElement item) throws IOException {
		XMLOutputter outp = new XMLOutputter();
		Format form = outp.getFormat();
		form.setEncoding("euc-kr");
		form.setLineSeparator("\r\n");
		form.setIndent("	");
		form.setTextMode(Format.TextMode.TRIM);
		outp.setFormat(form);
		
		Element root = null;
		Document document = null;
		if (item instanceof ModelElement) {
			ModelElement model = (ModelElement)item;
			
			root = new Element(CP.COMPONENT_PROFILE);
			document = new Document(root);
			
			// name
			root.addContent(new Element(CP.NAME).addContent(model.getName()));
			
			// data types
			Element child = new Element(CP.DEFINED_DATA_TYPES);
			root.addContent(child);
			
			for (Symbol symbol : model.getSymbols()) {
				String profile = symbol.getName() + ".xml";
				child.addContent(new Element(CP.DEFINED_DATA_TYPE).addContent(profile));
				
				ExportModel.export(directory, symbol);
			}
			
			// service types
			child = new Element(CP.DEFINED_SERVICE_TYPES);
			root.addContent(child);
			
			for (Function function : model.getFunctions()) {
				String profile = function.getName() + ".xml";
				child.addContent(new Element(CP.DEFINED_SERVICE_TYPE).addContent(profile));
				
				ExportModel.export(directory, function);
			}
		}
		else if (item instanceof Symbol) {
			Symbol symbol = (Symbol)item;
			
		}
		else if (item instanceof Function) {
			Function function = (Function)item;
		}
		
		if (root != null) {
			String fileName = item.getName() + ".xml";
			FileWriter writer = new FileWriter(fileName);

			document = new Document(root);
			outp.output(document, writer);
		}
	}
}
