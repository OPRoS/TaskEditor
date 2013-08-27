package kr.re.etri.tpl.diagram.editors.actions;

import kr.re.etri.tpl.diagram.commands.SetValueCommand;
import kr.re.etri.tpl.diagram.dialogs.BehaviorSelectionDialog;
import kr.re.etri.tpl.diagram.properties.sources.BehaviorElementPropertySource;
import kr.re.etri.tpl.diagram.properties.sources.ModelDiagramPropertySource;
import kr.re.etri.tpl.diagram.properties.sources.ReferElementPropertySource;
import kr.re.etri.tpl.diagram.util.PropertyContainer;
import kr.re.etri.tpl.diagram.util.TPLUtil;
import kr.re.etri.tpl.taskmodel.BehaviorElement;
import kr.re.etri.tpl.taskmodel.IncludedElement;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.ModelDiagram;
import kr.re.etri.tpl.taskmodel.ReferElement;
import kr.re.etri.tpl.taskmodel.TaskModelPackage;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.EList;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;

public class ExternalBehaviorSetAction extends Action {

	public static final String actionId = "kr.re.etri.tpl.diagram.editors.actions.ExternalBehaviorSetAction";
	
	private DefaultEditDomain editDomain;
	private ReferElement referElement;
	private IProject project;
	
	public ExternalBehaviorSetAction(DefaultEditDomain editDomain) {
		this(editDomain, "&Reselect Behavior");
	}

	public ExternalBehaviorSetAction(DefaultEditDomain editDomain, String text) {
		super(text);
		this.editDomain = editDomain;
	}

	public ExternalBehaviorSetAction(DefaultEditDomain editDomain, String text, ImageDescriptor image) {
		super(text, image);
		this.editDomain = editDomain;
	}

	public ExternalBehaviorSetAction(DefaultEditDomain editDomain, String text, int style) {
		super(text, style);
		this.editDomain = editDomain;
	}

	
	@Override
	public void run() {
		IEditorPart editor = editDomain.getEditorPart();
		Shell shell = editor.getSite().getShell();
		CommandStack commandStack = editDomain.getCommandStack();
		
		if (shell == null) {
			return;
		}

		BehaviorElement oldValue = (BehaviorElement)referElement.getRealModel();
		BehaviorElement newValue = null;
		BehaviorSelectionDialog dialog = new BehaviorSelectionDialog(shell, editor);
		dialog.setValue(oldValue);

		// KJH 20100903 s, 다이얼로그 cancel 선택시에도 실행되는 문제 수정
		if (IDialogConstants.OK_ID == dialog.open()) {
			newValue = (BehaviorElement)dialog.getValue();
			IncludedElement incElement = dialog.getIncludedElement();

			if (newValue != null && !newValue.equals(oldValue)) {
				// KJH 20100906, CompoundCommand 이용하여 세개의 command가 동시에 실행되도록 함
				CompoundCommand commands = new CompoundCommand();
				
				SetValueCommand command = new SetValueCommand("set external behavior");
				command.setPropertyValue(newValue);
				command.setPropertyId(TaskModelPackage.REFER_ELEMENT__REAL_MODEL);
				command.setTarget(new ReferElementPropertySource(referElement));
				commands.add(command);

				// KJH 20100903 s, reference add
				PropertyContainer prop;
				prop = new PropertyContainer(PropertyContainer.ADD, TaskModelPackage.TASK_ELEMENT__REFERENCES,
						referElement);
				command = new SetValueCommand("add referelement");
				command.setPropertyValue(prop);
				command.setPropertyId(prop.getFeatureId());
				command.setTarget(new BehaviorElementPropertySource(newValue));
				commands.add(command);
				
				prop = new PropertyContainer(PropertyContainer.REMOVE, TaskModelPackage.TASK_ELEMENT__REFERENCES,
						referElement);
				command = new SetValueCommand("remove referelement");
				command.setPropertyValue(prop);
				command.setPropertyId(prop.getFeatureId());
				command.setTarget(new BehaviorElementPropertySource(oldValue));
				commands.add(command);
				
				// KJH 20101012 s, include
				ItemElement parent = referElement.getParent();
				while (parent != null && !(parent instanceof ModelDiagram)) {
					parent = parent.getParent();
				}
				
				if (parent instanceof ModelDiagram) {
					ModelDiagram modelDiagram = (ModelDiagram)parent;
					
					String path = incElement.getIncludePath();

					boolean isExist = false;
					EList<IncludedElement> incList = modelDiagram.getIncludeItems();
					for (IncludedElement incElmt : incList) {
						if (TPLUtil.isExists(incElmt, path)) {
							isExist = true;
							break;
						}
					}

					if (!isExist) {
						prop = new PropertyContainer(PropertyContainer.ADD,
								TaskModelPackage.MODEL_DIAGRAM__INCLUDE_ITEMS, incElement);
						command = new SetValueCommand("add include item");
						command.setPropertyValue(prop);
						command.setPropertyId(prop.getFeatureId());
						command.setTarget(new ModelDiagramPropertySource(modelDiagram));
						commands.add(command);
					}
				}
				// KJH 20101012 e, include

				commandStack.execute(commands);
			}
		}
	}

	@Override
	public String getId() {
		return actionId;
	}

	public ReferElement getReferElement() {
		return referElement;
	}

	public void setReferElement(ReferElement referElement) {
		this.referElement = referElement;
	}

	public IProject getProject() {
		return project;
	}

	public void setProject(IProject project) {
		this.project = project;
	}

}
