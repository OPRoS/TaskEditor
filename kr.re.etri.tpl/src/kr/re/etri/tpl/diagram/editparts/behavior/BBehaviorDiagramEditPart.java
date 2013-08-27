package kr.re.etri.tpl.diagram.editparts.behavior;

import java.util.ArrayList;
import java.util.List;

import kr.re.etri.tpl.diagram.DiagramConfiguration;
import kr.re.etri.tpl.diagram.editors.DiagramResourceManager;
import kr.re.etri.tpl.diagram.editors.TPLDiagramEditor;
import kr.re.etri.tpl.diagram.editparts.behavior.policies.BXYLayoutEditPolicy;
import kr.re.etri.tpl.diagram.properties.sources.ModelDiagramPropertySource;
import kr.re.etri.tpl.diagram.properties.sources.TaskElementPropertySource;
import kr.re.etri.tpl.taskmodel.ActionElement;
import kr.re.etri.tpl.taskmodel.BehaviorElement;
import kr.re.etri.tpl.taskmodel.ConnectorElement;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.ModelDiagram;
import kr.re.etri.tpl.taskmodel.ReferAttribute;
import kr.re.etri.tpl.taskmodel.ReferElement;
import kr.re.etri.tpl.taskmodel.SubDiagram;
import kr.re.etri.tpl.taskmodel.TaskElement;
import kr.re.etri.tpl.taskmodel.TaskModelPackage;

import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.FreeformLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceImpl;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editpolicies.RootComponentEditPolicy;
import org.eclipse.gef.editpolicies.SelectionEditPolicy;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.IPropertySourceProvider;

/**
 * TaskDiagram �𵨰� Figure �� control �ϴ� EditPart Ŭ�����̴�.
 * �� EditPart ���� �����ϴ� Figure �� ���̾�׷� Editor �̴�.
 * 
 * @see BItemElementEditPart
 * 
 * @author sfline
 */
public class BBehaviorDiagramEditPart extends BItemElementEditPart {

	/**
	 * EditPart �� ���� Policy �� �����Ѵ�.
	 * @see org.eclipse.gef.editparts.AbstractEditPart#createEditPolicies()
	 * 
	 * @Override
	 */
	protected void createEditPolicies() {
		super.createEditPolicies();

		// disallows the removal of this edit part from its parent
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new RootComponentEditPolicy());
		// handles constraint changes (e.g. moving and/or resizing) of model elements
		// and creation of new model elements
//		installEditPolicy(EditPolicy.LAYOUT_ROLE,  new TDgmXYLayoutEditPolicy(this));
		
		installEditPolicy(EditPolicy.PRIMARY_DRAG_ROLE,  new BXYLayoutEditPolicy(this));
		
		installEditPolicy(EditPolicy.SELECTION_FEEDBACK_ROLE, new SelectionEditPolicy(){

			@Override
			protected void hideSelection() {
				// TODO Auto-generated method stub
				
			}

			@Override
			protected void showSelection() {
				// TODO Auto-generated method stub
				
			} 
			
		});
	}
	
	/**
	 * �𵨿� ���� Figure �� �����Ͽ� �����Ѵ�.
	 * @return �𵨿� ���� Figure
	 * 
	 * @Override
	 */
	protected IFigure createFigureForModel()
	{
		IFigure figure;
		figure = new FreeformLayer();
		figure.setBorder(new MarginBorder(3));
		figure.setLayoutManager(new FreeformLayout());
		
		DiagramConfiguration dgmCfg = DiagramConfiguration.getInstance();
		figure.setBackgroundColor(dgmCfg.getItemBGColor(TaskModelPackage.MODEL_DIAGRAM, DiagramConfiguration.NONE));
		
		return figure;
	}

	/**
	 * Object Ÿ���� ���� EditPart �� �� Ÿ������ ĳ�����Ѵ�.
	 * @return ModelDiagram ���� ���� ��(SubDiagram)
	 * 
	 * @Override
	 */
	public SubDiagram getCastedModel() {
		return (SubDiagram) getModel();
	}

	/**
	 * ���� ��(ReferElement)�� ���� ���� ��ȯ�Ѵ�.
	 * @return ItemElement ��
	 */
	public ItemElement getRealModel() {
		SubDiagram refItem = getCastedModel();
		return (ItemElement)refItem.getParent();
	}
	
	/**
	 * �־��� Object �� child �ΰ��� Ȯ���Ѵ�.
	 * @param child child ���θ� Ȯ���� ���
	 * @return
	 */
	public boolean isChild(Object child) {
		EditPart editPart;
		List children = getChildren();

		for (int i = 0; i < children.size(); i++) {
			editPart = (EditPart)children.get(i);
			ItemElement childModel = (ItemElement)editPart.getModel();
			if(child == childModel) {
				return true;
			}
			else if(childModel instanceof ReferElement) {
				ItemElement realItem = (ItemElement)((ReferElement)childModel).getRealModel();
				if(child == realItem) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * ���� �ڽ� �𵨵��� ��ȯ�Ѵ�.
	 * @see org.eclipse.gef.editparts.AbstractEditPart#getModelChildren()
	 * 
	 * @Override
	 */
	protected List getModelChildren() {
		EList<ReferElement> itemList = getCastedModel().getItems(); // return a list of elements
		ArrayList<ItemElement> childList = new ArrayList<ItemElement>();
		for(ReferElement refItem : itemList) {
			ItemElement item = refItem.getRealModel();
			
			// KJH 20100830, external behavior
			if (refItem.getAttribute() == ReferAttribute.EXTERNAL) {
				// KJH 20100902, proxy�� ���(href) ���� �𵨷� ��ȯ
				if (item != null && item.eIsProxy()) {
					URI uri = ((InternalEObject)item).eProxyURI();
					String fragment = uri.fragment();
					String platform = uri.toPlatformString(false);

					Resource resource = DiagramResourceManager.getResource(platform);
					
					// KJH 20101018 s, getResource()���� ó��
//					try {
//						resource.load(Collections.EMPTY_MAP);
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
					// KJH 20101018 e, getResource()���� ó��
					
					// KJH 20101006, script�� ���� ModelDiagram
					
					EList<EObject> contents = resource.getContents();	// KJH 20101018
					if (contents != null && contents.size() > 0) {
						ModelDiagram modelDiagram = (ModelDiagram)contents.get(0);
						String script = modelDiagram.getScript();
						if (script != null) {
							DiagramResourceManager.setResource(script, resource);
						}
					}

					// KJH 20100831, fragment�� �� ���ϴ� ���
					ItemElement external = (ItemElement)resource.getEObject(fragment);
					if (external instanceof BehaviorElement) {
						item = external;
						refItem.setRealModel(external);
						item.getReferences().add(refItem);
					}
				}
			}
			
			if(item instanceof BehaviorElement) {
				childList.add(refItem);
				
				// KJH 20100901,
				EList<ReferElement> referList = item.getReferences();
//				if (referList.contains(refItem))
//					continue;
				
				for (int i = 0; i < referList.size(); i++) {
					ReferElement refer = referList.get(i);

					// KJH 20100902, proxy�� ���(href) ���� �𵨷� ��ȯ
					if (refer.eIsProxy()) {
						URI uri = ((InternalEObject)refer).eProxyURI();
						String fragment = uri.fragment();
						String platform = uri.toPlatformString(false);

						Resource resource = DiagramResourceManager.getResource(platform);
						if (resource == null) {
							resource = new XMLResourceImpl(uri);
							DiagramResourceManager.setResource(platform, resource);
						}
						ItemElement refer2 = (ItemElement)resource.getEObject(fragment);
						if (refer2 instanceof ReferElement && !referList.contains(refer2)) {
							referList.set(i, (ReferElement)refer2);
						}
						else {
							referList.remove(i);
						}
						refer.setParent(null);
						refer.setRealModel(null);
					}
				}
			}
			else if(item instanceof ActionElement) {
				childList.add(refItem);
			}
			// KJH 20101126 s, connector
			else if (item instanceof ConnectorElement) {
				childList.add(refItem);
			}	// KJH 20101126 e, connector
			// KJH 20110413 s, task
			else if (item instanceof TaskElement) {
				childList.add(refItem);
			} // KJH 20110413 e, task
			// KJH 20100830, external behavior
//			else if(refItem.getAttribute() == ReferAttribute.EXTERNAL) {
//				URI uri = ((InternalEObject)item).eProxyURI();
//				String fragment = uri.fragment();
//				String platform = uri.toPlatformString(false);
//				
//				Resource resource = TPLDiagramEditorResourceManager.getResource(platform);
//				if (resource == null) {
//					resource = new XMLResourceImpl(uri);
//					TPLDiagramEditorResourceManager.setResource(platform, resource);
//				}
//				try {
//					resource.load(Collections.EMPTY_MAP);
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//				// KJH 20100831, fragment�� �� ���ϴ� ���
//				ItemElement external = (ItemElement)resource.getEObject(fragment);
//				if (external instanceof BehaviorElement) {
//					BehaviorElement behavior = (BehaviorElement)external;
//					
//					// KJH 20100831, add states
////					for (StateElement state : behavior.getStates()) {
////						ReferElement clone = (state.getReferences() != null && state.getReferences().size() > 0) ?
////								state.getReferences().get(0) : null;
////
////						ReferElement refChild = ModelManager.getFactory().createReferElement();
////
////						if (clone != null) {
////							refChild.setX(clone.getX());
////							refChild.setY(clone.getY());
////							refChild.setWidth(clone.getWidth());
////							refChild.setHeight(clone.getHeight());
////						}
////						else {
////							refChild.setWidth(100);
////							refChild.setHeight(55);
////						}
////						
////						refChild.setAttribute(ReferAttribute.EXTERNAL);
////						refChild.setParent(refItem);
////						refChild.setRealModel(state);
////						state.getReferences().add(refChild);
////						refItem.getItems().add(refChild);
////					}
//					
//					refItem.setRealModel(behavior);
//					childList.add(refItem);
//				}
//			}
		}

		return childList;
	}

	/**
	 * ���� Property �� ����Ǹ� ȣ��ȴ�.
	 * @param notification ���� ���� ����
	 * 
	 * @Override
	 */
	public void notifyChanged(Notification notification) {
		int type = notification.getEventType();
		int featureId = notification.getFeatureID(TaskModelPackage.class);

		switch(type) {
		case Notification.SET:
			this.refresh();
			break;
			
		case Notification.ADD:
			switch(featureId) {
			case TaskModelPackage.SUB_DIAGRAM__ITEMS:
				refreshChildren();
				break;
			case TaskModelPackage.MODEL_DIAGRAM__INCLUDE_ITEMS:
				refreshChildren();
				break;
			default:
				super.notifyChanged(notification);
				break;
			}
			break;
		case Notification.REMOVE:
			switch(featureId) {
//			case TaskModelPackage.MODEL_DIAGRAM__ITEMS:
			case TaskModelPackage.SUB_DIAGRAM__ITEMS:
				if(notification.getOldValue() instanceof TaskElement) {
					DefaultEditDomain editDomain = (DefaultEditDomain)this.getViewer().getEditDomain(); 
					IEditorPart editorPart = editDomain.getEditorPart();
					if(editorPart instanceof TPLDiagramEditor) {
						((TPLDiagramEditor)editorPart).removePage((TaskElement)notification.getOldValue());
					}
				}
				refreshChildren();
				break;
			case TaskModelPackage.MODEL_DIAGRAM__INCLUDE_ITEMS:
//				IncludedElement incItem = (IncludedElement)notification.getOldValue();
//				SubDiagram subDiagram = getCastedModel();
//				List<ReferElement> refList = subDiagram.getItems();
//				ArrayList<ItemElement> remList = new ArrayList<ItemElement>();
//				for(ReferElement refItem : refList) {
//					ItemElement realItem = refItem.getRealModel();
//					if(incItem == RTMUtil.getRootModel(realItem)) {
//						remList.add(refItem);
//					}
//				}
//				
//				refList.removeAll(remList);
				
				refreshChildren();
				break;
			default:
				super.notifyChanged(notification);
				break;
			}
			break;
		default:
			super.notifyChanged(notification);
			break;
		}
	}

	/**
	 * Parameter �� �־��� Ŭ����(�Ǵ� �������̽�)�� ���� Adapter �� �����Ѵ�.
	 * @param key Adapter ��û Ÿ��
	 * @return Parameter �� �־��� Ŭ����(�Ǵ� �������̽�)�� ���� Adapter
	 */
	public Object getAdapter(Class key) {
		if (IPropertySource.class == key) {
			Object model = getRealModel();
			if(model instanceof ModelDiagram) {
				return new ModelDiagramPropertySource((ModelDiagram)model);
			}
			else if(model instanceof TaskElement) {
				return new TaskElementPropertySource((TaskElement)model);
			}
		}
		if (IPropertySourceProvider.class == key) {
			final Object model = getModel();
			if(model instanceof ModelDiagram) {
				return new IPropertySourceProvider() {
					public IPropertySource getPropertySource(Object object) {
						return new ModelDiagramPropertySource((ModelDiagram)model);		
					}
				};
			}
		}
		return super.getAdapter(key);
	}

	/**
	 * Property �� ����Ǹ� ȣ��ȴ�
	 * @param event Property ���� Event
	 * 
	 * @Override
	 */
	public void propertyChange(PropertyChangeEvent event) {		
		super.propertyChange(event);
	}
	
	/**
	 * child ���� Figure ������ ����Ǹ� ȣ��ȴ�.
	 * @param event Figure ���� ���� Event 
	 * 
	 * @Override
	 */
	public void selectionChanged(SelectionChangedEvent event) {
		super.selectionChanged(event);

//		EditPartViewer viewer = getViewer();
//
//		// add the ShortestPathConnectionRouter
//		ScalableFreeformRootEditPart root = (ScalableFreeformRootEditPart)viewer.getRootEditPart();
//		ConnectionLayer connLayer = (ConnectionLayer)root.getLayer(LayerConstants.CONNECTION_LAYER);
//		ShortestPathConnectionRouter shortestPathRouter;
//		shortestPathRouter = (ShortestPathConnectionRouter)connLayer.getConnectionRouter();
//
////		BendpointConnectionRouter bendpointRouter = 
////			new BendpointConnectionRouter();
////		connLayer.setConnectionRouter(bendpointRouter);
//
//		List<IFigure> children = getFigure().getChildren();
//		for(IFigure fig : children) {
//			if(fig instanceof org.eclipse.draw2d.Connection) {
//				shortestPathRouter.invalidate((org.eclipse.draw2d.Connection)fig);
//			}
//		}
////		connLayer.revalidate();
	}
}