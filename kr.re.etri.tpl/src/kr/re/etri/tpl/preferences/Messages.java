package kr.re.etri.tpl.preferences;

import java.util.ResourceBundle;


public class Messages {

	private final static String RESOURCE_BUNDLE= "kr.re.etri.tpl.preferences.Messages";//$NON-NLS-1$
	
	private static ResourceBundle fgResourceBundle = null;
	
	private static boolean notRead = true;

	public Messages() {
	}
	public static ResourceBundle getResourceBundle() {
		if (notRead) {
			notRead = false;
			try {
				fgResourceBundle = ResourceBundle.getBundle(RESOURCE_BUNDLE);
			}
			catch (Exception e) {
			}
		}
		
		return fgResourceBundle;
	}
	public static String getString(String key) {
		try {
			return getResourceBundle().getString(key);
		} catch (Exception e) {
			return "!" + key + "!";//$NON-NLS-2$ //$NON-NLS-1$
		}
	}
}

