package org.jboss.reddeer.ui.api.wizard;

import org.jboss.reddeer.jface.wizard.NewWizardDialog;

/**
 * This class represents a new RedDeer Test Plug-in wizard
 * @author jrichter
 *
 */
public class NewRedDeerTestPluginWizard extends NewWizardDialog {

	public static final String CATEGORY="RedDeer";
	public static final String NAME="RedDeer Test Plug-in";
	
	/**
	 * Default constructor
	 */
	public NewRedDeerTestPluginWizard() {
		super(CATEGORY, NAME);
	}
}
