package org.jboss.reddeer.ui.api.wizard;

import org.jboss.reddeer.jface.wizard.WizardPage;
import org.jboss.reddeer.swt.impl.button.CheckBox;
import org.jboss.reddeer.swt.impl.button.RadioButton;
import org.jboss.reddeer.swt.impl.text.LabeledText;

/**
 * First page of the new RedDeer Test Case wizard
 * @author jrichter
 *
 */
public class NewRedDeerTestWizardPageOne extends WizardPage {
	
	/**
	 * Sets the source folder
	 * @param folder
	 */
	public void setSourceFolder(String folder) {
		setTextToLabeledText("Source folder:", folder);
	}
	
	/**
	 * Sets the target package
	 * @param pkg
	 */
	public void setPackage(String pkg) {
		setTextToLabeledText("Package:", pkg);
	}
	
	/**
	 * Sets the test class name
	 * @param name
	 */
	public void setName(String name) {
		setTextToLabeledText("Name:", name);
	}
	
	/**
	 * Sets the superclass for the new test class
	 * @param klass
	 */
	public void setSuperClass(String klass) {
		setTextToLabeledText("Superclass:", klass);
	}
	
	/**
	 * Sets the class under test 
	 * @param klass
	 */
	public void setClassUnderTest(String klass) {
		setTextToLabeledText("Class under test:", klass);
	}
	
	/**
	 * Sets if the setUp() check box is checked
	 * @param checked true to check, false otherwise
	 */
	public void setSetupMethod(boolean checked) {
		toggleCheckBox("setUp()", checked);
	}
	
	/**
	 * Sets if the setUpBeforeClass() check box is checked
	 * @param checked true to check, false otherwise
	 */
	public void setBeforeClassSetup(boolean checked) {
		toggleCheckBox("setUpBeforeClass()", checked);
	}
	
	/**
	 * Sets if the tearDown() check box is checked
	 * @param checked true to check, false otherwise
	 */
	public void setTearDownMethod(boolean checked) {
		toggleCheckBox("tearDown()", checked);
	}
	
	/**
	 * Sets if the tearDownAfterClass() check box is checked
	 * @param checked true to check, false otherwise
	 */
	public void setAfterClassTearDown(boolean checked) {
		toggleCheckBox("tearDownAfterClass()", checked);
	}
	
	/**
	 * Sets if the constructor check box is checked
	 * @param checked true to check, false otherwise
	 */
	public void setConstructor(boolean checked) {
		toggleCheckBox("constructor", checked);
	}
	
	/**
	 * Sets if the 'New JUnit 3 test' radio button is selected
	 * @param checked true to select, false otherwise
	 */
	public void setJUnit3(boolean select) {
		new RadioButton("New JUnit 3 test").toggle(select);
	}
	
	/**
	 * Sets if the 'New JUnit 4 test' radio button is selected
	 * @param checked true to select, false otherwise
	 */
	public void setJUnit4(boolean select) {
		new RadioButton("New JUnit 4 test").toggle(select);
	}
	
	/**
	 * Sets if the Generate comments check box is checked
	 * @param checked true to check, false otherwise
	 */
	public void setGenerateComments(boolean checked) {
		toggleCheckBox("Generate comments", checked);
	}
	
	private void toggleCheckBox(String text, boolean checked) {
		new CheckBox(text).toggle(checked);
	}
	
	private void setTextToLabeledText(String label, String text) {
		new LabeledText(label).setText(text);
	}
}
