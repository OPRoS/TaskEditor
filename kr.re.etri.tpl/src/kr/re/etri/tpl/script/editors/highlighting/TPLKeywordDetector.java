package kr.re.etri.tpl.script.editors.highlighting;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.text.rules.IWordDetector;

public class TPLKeywordDetector implements IWordDetector {
//	private List<String> keywords;	
	public TPLKeywordDetector(){
	}	
	@Override
	public boolean isWordPart(char c) {
		// KJH 20100629, (c=='_') 조건 추가
		return (c=='.')||(c=='_')||Character.isLetterOrDigit(c);
//		for(String next : keywords){
//			next = next.substring(1);
//			if(next.indexOf(c) != -1){	
//				return true;
//			}			
//		}
//		return false;
	}

	@Override
	public boolean isWordStart(char c) {
		return (c =='#')||(c =='_')||(c =='@')||Character.isLetterOrDigit(c);
//		for(String next : keywords){
//			char k = next.charAt(0);
//			if(k==c){
//				return true;
//			}
//		}
//		return false;
	}
	
	public static void main(String[] args){
		List<String> keys= new ArrayList<String>();
		keys.add("if");
		keys.add("else");
		
//	//	TPLKeywordDetector dt = new TPLKeywordDetector(keys);
//		
//		System.out.println("i start : "+dt.isWordStart('i'));
//		System.out.println("g start : "+dt.isWordStart('g'));
//		
//		System.out.println("f part : "+dt.isWordPart('f') );
//		System.out.println("z part : "+dt.isWordPart('z') );
		
		
	}
}
