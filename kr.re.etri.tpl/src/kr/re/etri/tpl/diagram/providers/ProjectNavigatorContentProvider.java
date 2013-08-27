package kr.re.etri.tpl.diagram.providers;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;

public class ProjectNavigatorContentProvider extends
	AdapterFactoryContentProvider {

	private static ResourceSetImpl resourceSet = new ResourceSetImpl();
	
	public ProjectNavigatorContentProvider() {
		super(ProjectNavigatorComposedAdapterFactory.getAdapterFactory());
	}
	
	public ProjectNavigatorContentProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	@Override
	public Object[] getElements(Object inputElement) {

		return getChildren(inputElement);
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof IFile)
	    {
	        String path = ((IFile)parentElement).getFullPath().toString();
	        URI uri = URI.createPlatformResourceURI(path, true);
//	        parentElement = resourceSet.getResource(uri, true);
	        parentElement = new ResourceSetImpl().getResource(uri, true);
	    }
	    return super.getChildren(parentElement);
	}

	@Override
	public Object getParent(Object element) {
		if (element instanceof IFile)
	        return ((IResource)element).getParent();
	    return super.getParent(element);
	}

	@Override
	public boolean hasChildren(Object element) {
		if (element instanceof IFile)
	        return true;
	    return super.hasChildren(element);
	}
}
