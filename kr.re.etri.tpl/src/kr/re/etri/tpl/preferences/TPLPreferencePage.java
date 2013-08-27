package kr.re.etri.tpl.preferences;

import kr.re.etri.tpl.TaskModelPlugin;
import kr.re.etri.tpl.preferences.overlaypage.FieldEditorOverlayPage;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.RadioGroupFieldEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;


public class TPLPreferencePage extends FieldEditorOverlayPage
		implements IWorkbenchPreferencePage {

	private static final String PAGE_ID = "kr.re.etri.tpl.properties.tplpropertypage";
	
	private RadioGroupFieldEditor monitoringType;
	
	public TPLPreferencePage() {
		super(GRID);
	}

	@Override
	protected String getPageId() {
		return PAGE_ID;
	}

	@Override
	public void init(IWorkbench workbench) {
//		setDescription(Messages.getString("TPLPreferencePage.Description"));
	}
	
	@Override
	protected IPreferenceStore doGetPreferenceStore() {
		return TaskModelPlugin.getDefault().getPreferenceStore();
	}


	@Override
	protected void createFieldEditors() {
		Composite parent = getFieldEditorParent();
		
		monitoringType = new RadioGroupFieldEditor(TPLPreferenceConstants.MONITORING_TYPE,
				Messages.getString("TPLPreferencePage.MonitoringType_Label"),
				1,
				new String[][] {
					{Messages.getString("TPLPreferencePage.MonitoringType_Type1"),
					 TPLPreferenceConstants.MONITORING_TYPE1
					},
					{Messages.getString("TPLPreferencePage.MonitoringType_Type2"),
					 TPLPreferenceConstants.MONITORING_TYPE2
					}
				},
				parent);
		
		addField(monitoringType);
	}

}
