package kr.re.etri.tpl.diagram.actions;

import java.util.Map;

import org.eclipse.core.runtime.IProduct;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.IAction;
import org.eclipse.osgi.util.NLS;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.LabelRetargetAction;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.internal.IWorkbenchHelpContextIds;
import org.eclipse.ui.internal.WorkbenchMessages;
import org.eclipse.ui.internal.actions.CommandAction;
import org.eclipse.ui.services.IServiceLocator;

public abstract class RTMActionFactory {

    /**
     * Interface for a workbench action.
     */
    public interface IWorkbenchAction extends IAction {
        /**
         * Disposes of this action. Once disposed, this action cannot be used.
         * This operation has no effect if the action has already been
         * disposed.
         */
        public void dispose();
    }
    
    private static class WorkbenchCommandAction extends CommandAction implements
			IWorkbenchAction {
		/**
		 * @param commandIdIn
		 * @param window
		 */
		public WorkbenchCommandAction(String commandIdIn,
				IWorkbenchWindow window) {
			super(window, commandIdIn);
		}
		
		public WorkbenchCommandAction(String commandIdIn, Map parameterMap,
				IServiceLocator serviceLocator) {
			super(serviceLocator, commandIdIn, parameterMap);
		}
	}

    public static final ActionFactory PROPERTIES = new ActionFactory("kr.re.etri.tpl.diagram.contextmenu.action.properties") {//$NON-NLS-1$
        
        public IWorkbenchAction create(IWorkbenchWindow window) {
            if (window == null) {
                throw new IllegalArgumentException();
            }
            LabelRetargetAction action = new LabelRetargetAction(getId(),"&Properties");
            action.setToolTipText("Properties");
            window.getPartService().addPartListener(action);
            action.setActionDefinitionId("kr.re.etri.tpl.diagram.contextmenu.action.properties"); //$NON-NLS-1$
            ISharedImages sharedImages = window.getWorkbench()
                    .getSharedImages();
//            action.setImageDescriptor(sharedImages
//                    .getImageDescriptor(ISharedImages.IMG_TOOL_UNDO));
//            action.setDisabledImageDescriptor(sharedImages
//                    .getImageDescriptor(ISharedImages.IMG_TOOL_UNDO_DISABLED));
            action.setEnabled(true);
            return action;
        }
    };

    /**
	 * Establishes bi-direction connections between the forward and backward
	 * actions of a cycle pair.
	 * <p>
	 * Example usage:
	 * 
	 * <pre>
	 * ActionFactory.IWorkbenchAction nextEditorAction = ActionFactory.NEXT_EDITOR
	 * 		.create(window);
	 * ActionFactory.IWorkbenchAction previousEditorAction = ActionFactory.PREVIOUS_EDITOR
	 * 		.create(window);
	 * ActionFactory.linkCycleActionPair(nextEditorAction, previousEditorAction);
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param next
	 *            the action that moves forward
	 * @param previous
	 *            the action that moves backward
	 */
    public static void linkCycleActionPair(IWorkbenchAction next,
            IWorkbenchAction previous) {
    }

    /**
     * Id of actions created by this action factory.
     */
    private final String actionId;

    /**
     * Creates a new workbench action factory with the given id.
     * 
     * @param actionId
     *            the id of actions created by this action factory
     */
    protected RTMActionFactory(String actionId) {
        this.actionId = actionId;
    }

    /**
     * Creates a new standard action for the given workbench window. The action
     * has an id as specified by the particular factory.
     * <p>
     * Actions automatically register listeners against the workbench window so
     * that they can keep their enablement state up to date. Ordinarily, the
     * window's references to these listeners will be dropped automatically
     * when the window closes. However, if the client needs to get rid of an
     * action while the window is still open, the client must call
     * {@link IWorkbenchAction#dispose dispose}to give the action an
     * opportunity to deregister its listeners and to perform any other
     * cleanup.
     * </p>
     * 
     * @param window
     *            the workbench window
     * @return the workbench action
     */
    public abstract IWorkbenchAction create(IWorkbenchWindow window);

    /**
     * Returns the id of this action factory.
     * 
     * @return the id of actions created by this action factory
     */
    public String getId() {
        return actionId;
    }

}
