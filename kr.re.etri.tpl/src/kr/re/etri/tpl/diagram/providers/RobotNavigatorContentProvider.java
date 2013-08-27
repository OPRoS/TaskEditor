package kr.re.etri.tpl.diagram.providers;

import kr.re.etri.tpl.diagram.util.TreeItem;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.IViewSite;

public class RobotNavigatorContentProvider implements
		IStructuredContentProvider, ITreeContentProvider {

	private IViewSite m_site;
	private TreeItem m_invisibleItem;
	
	public RobotNavigatorContentProvider(IViewSite site)
	{
		m_site = site;
	}
	@Override
	public Object[] getElements(Object inputElement) {
		if(inputElement.equals(m_site)) {
			if(m_invisibleItem == null) { 
				initialize();
			}

			return m_invisibleItem.getChildren();
		}

		return getChildren(inputElement);
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// TODO Auto-generated method stub

	}

	@Override
	public Object[] getChildren(Object parentElement) {
		if(parentElement == null) {
			return null;
		}

		if(parentElement instanceof TreeItem) {
			TreeItem item = (TreeItem)parentElement;
			return item.getChildren();
		}

		return null;
	}

	@Override
	public Object getParent(Object element) {
		if(element == null) {
			return null;
		}

		if(element instanceof TreeItem) {
			TreeItem item = (TreeItem)element;
			return item.getParent();
		}

		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		if(element == null) {
			return false;
		}

		if(element instanceof TreeItem) {
			TreeItem item = (TreeItem)element;
			return item.hasChildren();
		}

		return false;
	}

	private void initialize()
	{
		TreeItem itemL1, itemL2;
		m_invisibleItem = new TreeItem("Root");
		itemL1 = m_invisibleItem.addChild("Level 1 - 1");
		itemL2 = itemL1.addChild("Level 2 - 1");
		itemL2 = itemL1.addChild("Level 2 - 2");
		itemL2 = itemL1.addChild("Level 2 - 3");

		itemL1 = m_invisibleItem.addChild("Level 1 - 2");
		itemL2 = itemL1.addChild("Level 2 - 1");
		itemL2 = itemL1.addChild("Level 2 - 2");
		itemL2 = itemL1.addChild("Level 2 - 3");
	}
}
