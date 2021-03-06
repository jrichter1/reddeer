package org.jboss.reddeer.eclipse.test.wst.common.project.facet.ui;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import org.jboss.reddeer.eclipse.jdt.ui.WorkbenchPreferenceDialog;
import org.jboss.reddeer.eclipse.jdt.ui.packageexplorer.PackageExplorer;
import org.jboss.reddeer.eclipse.test.wst.server.ui.TestServerRuntime;
import org.jboss.reddeer.eclipse.test.wst.server.ui.view.ServersViewTestCase;
import org.jboss.reddeer.eclipse.ui.wizards.datatransfer.ExternalProjectImportWizardDialog;
import org.jboss.reddeer.eclipse.ui.wizards.datatransfer.WizardProjectsImportPage;
import org.jboss.reddeer.eclipse.utils.DeleteUtils;
import org.jboss.reddeer.eclipse.wst.common.project.facet.ui.RuntimesPropertyPage;
import org.jboss.reddeer.eclipse.wst.server.ui.RuntimePreferencePage;
import org.jboss.reddeer.eclipse.wst.server.ui.wizard.NewRuntimeWizardDialog;
import org.jboss.reddeer.eclipse.wst.server.ui.wizard.NewRuntimeWizardPage;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.swt.api.Shell;
import org.jboss.reddeer.swt.condition.ShellWithTextIsActive;
import org.jboss.reddeer.swt.condition.ShellWithTextIsAvailable;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.handler.ShellHandler;
import org.jboss.reddeer.swt.handler.WidgetHandler;
import org.jboss.reddeer.swt.impl.button.OkButton;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.wait.WaitUntil;
import org.jboss.reddeer.swt.wait.WaitWhile;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class RuntimesPropertyPageTest {

	private static final String PROJECT = "server-project";
	
	private RuntimesPropertyPage propertyPage;
	
	@Before
	public void createProject(){
		ExternalProjectImportWizardDialog wizard  = new ExternalProjectImportWizardDialog();
		wizard.open();

		WizardProjectsImportPage wizardPage = wizard.getFirstPage();
		wizardPage.setArchiveFile(ServersViewTestCase.ZIP_FILE.getAbsolutePath());
		wizardPage.selectProjects(PROJECT);

		wizard.finish();
		
		PackageExplorer packageExplorer = new PackageExplorer();
		packageExplorer.open();
		propertyPage = new RuntimesPropertyPage(packageExplorer.getProject(PROJECT));
	}
	
	@Before
	public void createRuntime(){
		WorkbenchPreferenceDialog preferencesDialog = new WorkbenchPreferenceDialog();
		RuntimePreferencePage runtimePreference = new RuntimePreferencePage();

		preferencesDialog.open();
		preferencesDialog.select(runtimePreference);
		
		NewRuntimeWizardDialog dialog = runtimePreference.addRuntime();
		NewRuntimeWizardPage page = dialog.getFirstPage();
		page.selectType(TestServerRuntime.CATEGORY, TestServerRuntime.NAME);
		dialog.finish();
		
		preferencesDialog.ok();
	}
	
	@After
	public void cleanup(){
		Shell shell = null;
		try {
			shell = new DefaultShell(propertyPage.getPageTitle());
			shell.close();
		} catch (SWTLayerException e){
			// not found, no action needed
		}
		
		DeleteUtils.forceProjectDeletion(new PackageExplorer().getProject(PROJECT),true);
		
		WorkbenchPreferenceDialog preferencesDialog = new WorkbenchPreferenceDialog();
		RuntimePreferencePage runtimePreference = new RuntimePreferencePage();

		preferencesDialog.open();
		preferencesDialog.select(runtimePreference);
		
		runtimePreference.removeAllRuntimes();
		preferencesDialog.cancel();
	}
	
	@Test
	public void selectRuntime() {
		propertyPage.open();
		propertyPage.selectRuntime(TestServerRuntime.NAME);
		final String parentShellText = WidgetHandler.getInstance().getText(
				ShellHandler.getInstance().getParentShell(new DefaultShell(propertyPage.getPageTitle()).getSWTWidget()));
		new OkButton().click();
		new WaitWhile(new ShellWithTextIsAvailable(propertyPage.getPageTitle())); 
		new WaitUntil(new ShellWithTextIsActive(parentShellText));
		
		propertyPage.open();		
 		assertThat(propertyPage.getSelectedRuntimes().get(0), is(TestServerRuntime.NAME));
 		
 		new OkButton().click();
 	}
}
