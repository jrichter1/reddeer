package org.jboss.reddeer.ui.api.wizard;

import org.jboss.reddeer.jface.wizard.NewWizardDialog;

/**
 * This class represents a new RedDeer Test Case wizard
 * @author jrichter
 *
 */
public class NewRedDeerTestCaseWizard extends NewWizardDialog{

	public static final String NAME = "RedDeer Test"; 
	
	/**
	 * Default constructor
	 */
	public NewRedDeerTestCaseWizard() {
		super(NewRedDeerTestPluginWizard.CATEGORY, NAME);
	}
}
