package kr.re.etri.tpl.diagram.inject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import kr.re.etri.tpl.script.utils.DiagramResourceUtil;
import kr.re.etri.tpl.taskmodel.IncludedElement;

public class InjectOperation {
	private String m_from;
	private String m_to;
	private IncludedElement m_includedElement;
	private List<IncludedElement> m_originInclude;
	
	public InjectOperation(String from, String to, IncludedElement includedElement,List<IncludedElement> originInclude ){
		m_from = from;
		m_to = to;
		m_includedElement = includedElement;
		m_originInclude = originInclude;
	}
	
	void excute(){
		copyFile(new File(m_from), new File(m_to));
		if(!m_originInclude.contains(m_includedElement)){
			m_originInclude.add(m_includedElement);
		}
	}
	
	private void copyFile(File from, File to){
		try {
			BufferedReader reader = new BufferedReader(new FileReader(from));
			PrintWriter writer = new PrintWriter(to);
			String line = null;
			while(true){
				line =reader.readLine();
				if(line == null){
					break;
				}
				writer.println(line);
			}
			reader.close();
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
