package kr.re.etri.tpl.preferences;

import kr.re.etri.tpl.TaskModelPlugin;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

public class TPLPreferenceInitializer extends AbstractPreferenceInitializer {

	@Override
	public void initializeDefaultPreferences() {
		IPreferenceStore store = TaskModelPlugin.getDefault().getPreferenceStore();
		store.setDefault(TPLPreferenceConstants.MONITORING_TYPE, TPLPreferenceConstants.MONITORING_TYPE1);
	}

}
