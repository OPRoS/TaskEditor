package kr.re.etri.tpl.script.editors;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.eclipse.core.resources.IEncodedStorage;
import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IPersistableElement;
import org.eclipse.ui.IStorageEditorInput;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.model.IWorkbenchAdapter;

/**
 * 
 * @author KJH 20110818
 *
 */
public class TPLTextEditorInput implements IStorageEditorInput {
	
	private class WorkbenchAdapter implements IWorkbenchAdapter {

		@Override
		public Object[] getChildren(Object o) {
			return null;
		}

		@Override
		public ImageDescriptor getImageDescriptor(Object object) {
			return TPLTextEditorInput.this.getImageDescriptor();
		}

		@Override
		public String getLabel(Object o) {
			return TPLTextEditorInput.this.getName();
		}

		@Override
		public Object getParent(Object o) {
			return null;
		}
	}
	
	private class Storage implements IEncodedStorage {

		@Override
		public InputStream getContents() throws CoreException {
			return new ByteArrayInputStream(content.getBytes());
		}

		@Override
		public IPath getFullPath() {
			return TPLTextEditorInput.this.getPath();
		}

		@Override
		public String getName() {
			return TPLTextEditorInput.this.getName();
		}

		@Override
		public boolean isReadOnly() {
			return false;
		}

		@Override
		public Object getAdapter(Class adapter) {
			return Platform.getAdapterManager().getAdapter(this, adapter);
		}

		@Override
		public String getCharset() throws CoreException {
			return null;
		}
		
	}
	
	private String name;
	private String content;
	private IPath path;
	private WorkbenchAdapter workbenchAdapter;
	private IStorage storage;

	public TPLTextEditorInput(final String name) {
		this.name = name;
		this.path = new Path(name);
		this.content = "";
		this.workbenchAdapter = new WorkbenchAdapter();
		this.storage = new Storage();
	}
	
	public TPLTextEditorInput(final String name, final IPath path) {
		this.name = name;
		this.path = path;
		this.content = "";
		this.workbenchAdapter = new WorkbenchAdapter();
		this.storage = new Storage();
	}
	
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public IPath getPath() {
		return path;
	}
	
	public void setPath(IPath path) {
		this.path = path;
	}
	
	@Override
	public boolean exists() {
		return content != null && name.length() > 0 && path != null;
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		try {
			IEditorDescriptor desc = IDE.getEditorDescriptor(name);
			return desc.getImageDescriptor();
		} catch (PartInitException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public IPersistableElement getPersistable() {
		return null;
	}

	@Override
	public String getToolTipText() {
		return path.toString();
	}

	@Override
	public Object getAdapter(Class adapter) {
		if (IWorkbenchAdapter.class.equals(adapter)) {
			return workbenchAdapter;
		}
		return Platform.getAdapterManager().getAdapter(this, adapter);
	}

	@Override
	public IStorage getStorage() throws CoreException {
		return storage;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof TPLTextEditorInput) {
			TPLTextEditorInput other = (TPLTextEditorInput)obj;
			if (path.equals(other.path)) {
				return true;
			}
		}
		return super.equals(obj);
	}

}
