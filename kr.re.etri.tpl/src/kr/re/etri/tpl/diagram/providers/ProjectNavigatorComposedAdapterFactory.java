package kr.re.etri.tpl.diagram.providers;

import java.util.ArrayList;

import kr.re.etri.tpl.taskmodel.provider.TaskModelItemProviderAdapterFactory;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.edit.provider.resource.ResourceItemProviderAdapterFactory;

public class ProjectNavigatorComposedAdapterFactory {

	private static ComposedAdapterFactory adapterFactory;
	
	public final static ComposedAdapterFactory getAdapterFactory() {
		if(adapterFactory == null) {
			adapterFactory = new ComposedAdapterFactory(createFactoryList());
		}
		
		return adapterFactory;
	}
	
	public final static ArrayList<AdapterFactory> createFactoryList() {
		ArrayList<AdapterFactory> factories = new ArrayList<AdapterFactory>();
		factories.add(new ResourceItemProviderAdapterFactory());
		factories.add(new TaskModelItemProviderAdapterFactory());
		factories.add(new ReflectiveItemProviderAdapterFactory());
		return factories;
	}
}
