package org.jboss.reddeer.jface.preference;

import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.swt.api.Button;
import org.jboss.reddeer.swt.condition.ProgressInformationShellIsActive;
import org.jboss.reddeer.swt.condition.ShellWithTextIsAvailable;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.clabel.DefaultCLabel;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.wait.WaitWhile;

/**
 * Represents a general preference page.
 * 
 * @author Lucia Jelinkova
 * @since 0.6
 */
public abstract class PreferencePage {

	protected final Logger log = Logger.getLogger(this.getClass());

	/**
	 * Default constructor.
	 */
	public PreferencePage() {
	}

	/**
	 * Get name of the given preference page.
	 * 
	 * @return name of preference page
	 */
	public String getName() {
		DefaultCLabel cl = new DefaultCLabel();
		return cl.getText();
	}

	/**
	 * Submit OK button and exit preference shell.
	 * 
	 * @deprecated will be removed in 1.0, the logic for closing preference page has been moved to {@link org.jboss.reddeer.eclipse.jdt.ui.WorkbenchPreferenceDialog#ok()}
	 */
	@Deprecated
	public void ok() {
		String shellText = new DefaultShell().getText();
		Button b = new PushButton("OK");
		log.info("Close Preferences dialog");
		
		b.click();
		new WaitWhile(new ProgressInformationShellIsActive());
		new WaitWhile(new ShellWithTextIsAvailable(shellText));
	}

	/**
	 * Submit Cancel button and exit preference shell.
	 * 
	 *  @deprecated will be removed in 1.0, the logic for closing preference page has been moved to {@link org.jboss.reddeer.eclipse.jdt.ui.WorkbenchPreferenceDialog#cancel()}
	 */
	public void cancel() {
		String shellText = new DefaultShell().getText();
		Button b = new PushButton("Cancel");
		
		log.info("Cancel Preferences dialog");
		b.click();
		new WaitWhile(new ProgressInformationShellIsActive());
		new WaitWhile(new ShellWithTextIsAvailable(shellText));
	}

	/**
	 * Apply preference page changes.
	 */
	public void apply() {
		Button b = new PushButton("Apply");
		log.info("Apply changes in Preferences dialog");
		b.click();
	}

	/**
	 * Restore default preference page settings.
	 */
	public void restoreDefaults() {
		Button b = new PushButton("Restore Defaults");
		log.info("Restore default values in Preferences dialog");
		b.click();
	}
	
}
