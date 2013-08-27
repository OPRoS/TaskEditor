package kr.re.etri.tpl.diagram.editors;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceImpl;

/**
 * proxy 모델들에 대한 경로 처리 때문
 * @author KJH 20110928
 *
 */
public class DiagramResourceManager {
	private static final Map<String, Resource> resourceMap = new HashMap<String, Resource>();
	
	public static Resource getResource(String string) {
		Resource result = resourceMap.get(string);
		if (result == null) {
			URI uri = URI.createPlatformResourceURI(string, false);
			result = new XMLResourceImpl(uri);
			
			if (!result.isLoaded()) {
				try {
					result.load(Collections.EMPTY_MAP);
					resourceMap.put(string, result);
				} catch (IOException e) {
				}
			}
		}

		return result;
	}
	
	public static Resource getResource(IFile file) {
		return getResource(file.getFullPath().toString());
	}
	
	public static void setResource(String string, Resource resource) {
		if (resource == null) {
			resourceMap.remove(string);
		}
		else {
			resourceMap.put(string, resource);
		}
	}
	
	public static void setResource(IFile file, Resource resource) {
		setResource(file.getFullPath().toString(), resource);
	}
	
}
