package kr.re.etri.tpl.script.debug;

import java.text.MessageFormat;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.ui.IDebugModelPresentation;
import org.eclipse.debug.ui.IValueDetailListener;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.part.FileEditorInput;

public class DebugModelPresentation implements IDebugModelPresentation {

	@Override
	public void computeDetail(IValue value, IValueDetailListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public Image getImage(Object element) {
		return null;
	}

	@Override
	public String getText(Object element) {
		
		if (element instanceof TPLBreakpoint) {
			TPLBreakpoint breakpoint = (TPLBreakpoint)element;
			StringBuilder sb = new StringBuilder();

			IResource resource = breakpoint.getMarker().getResource();
			IResource parent = null;
			if (resource != null) {
				sb.append(resource.getName());
				parent = resource.getParent();
			}
			
			try {
				int lineNumber = breakpoint.getLineNumber();
				sb.append(MessageFormat.format("[line:{0}]", new Object[]{Integer.toString(lineNumber)})); 
			} catch (CoreException e) {
			}
			
			// KJH 20110412 s, breakpoints view에 파일 경로 표시되도록 수정
			if (resource != null) {
				String path = parent.getFullPath().toString();
				if (path.startsWith("/")) {
					path = path.substring(1);
				}
				sb.append("[");
				sb.append(path);
				sb.append("]");
			}	// KJH 20110412 s, breakpoints view에 파일 경로 표시되도록 수정
			
			return sb.toString();
		}
		
		return null;
	}

	@Override
	public void setAttribute(String attribute, Object value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isLabelProperty(Object element, String property) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getEditorId(IEditorInput input, Object element) {
		return "kr.re.etri.tpl.script.editors.TPLEditor";
	}

	@Override
	public IEditorInput getEditorInput(Object element) {
		if (element instanceof IBreakpoint) {
			IBreakpoint breakpoint = ((IBreakpoint)element);
			IResource resource = breakpoint.getMarker().getResource();
			if (resource instanceof IFile) {
				return new FileEditorInput((IFile)resource);
			}
		}
		return null;
	}

}
