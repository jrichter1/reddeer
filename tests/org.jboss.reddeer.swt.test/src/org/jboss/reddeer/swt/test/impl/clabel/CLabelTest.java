package org.jboss.reddeer.swt.test.impl.clabel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Shell;
import org.jboss.reddeer.swt.api.CLabel;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.impl.clabel.DefaultCLabel;
import org.jboss.reddeer.swt.test.SWTLayerTestCase;
import org.jboss.reddeer.swt.util.Display;
import org.junit.Test;
/**
 * Tests CLabel implementation
 * @author vlado pakan
 *
 */
public class CLabelTest extends SWTLayerTestCase {
	
	private static final String CLABEL_PREFIX = "CLabel:";
	
	private static final String CLABEL_TOOLTIP_PREFIX = "CLabel Tooltip Text:";
	
	@Override
	protected void createControls(Shell shell){
		createCLabel(shell, CLABEL_PREFIX + 0, SWT.LEFT, null);
		createCLabel(shell, CLABEL_PREFIX + 1, SWT.CENTER, Display.getDisplay().getSystemImage(SWT.ICON_QUESTION));
		createCLabel(shell, CLABEL_PREFIX + 2, SWT.RIGHT, null);
	}
	
	private org.eclipse.swt.custom.CLabel createCLabel(Shell shell, String text, int align, Image image){
		org.eclipse.swt.custom.CLabel cLabel = new org.eclipse.swt.custom.CLabel(shell, SWT.SHADOW_IN);
		cLabel.setText(text);
		cLabel.setToolTipText(CLabelTest.CLABEL_TOOLTIP_PREFIX + text);
		cLabel.setAlignment(align);
		cLabel.setImage(image);
		return cLabel;
	}
	
	@Test
	public void findCLabelByIndex(){
		CLabel cLabel = new DefaultCLabel(1);
		assertEquals("Wrong cLabel widget was found", CLABEL_PREFIX + 1, cLabel.getText());
	}
	
	@Test
	public void findCLabelByText(){
		CLabel cLabel = new DefaultCLabel(CLabelTest.CLABEL_PREFIX + 1);
		assertEquals("Wrong cLabel widget was found", CLABEL_PREFIX + 1, cLabel.getText());
	}
	
	@Test(expected=SWTLayerException.class)
	public void findNonExistingCLabelByIndex(){
		new DefaultCLabel(11);
	}
	
	@Test(expected=SWTLayerException.class)
	public void findNonExistingCLabelByText(){
		new DefaultCLabel("NON_ESITING_@##$_TEXT");
	}
	
	@Test
	public void getTooltipText(){
		String tooltip = new DefaultCLabel(1).getTooltipText();
		assertEquals("CLabel has wrong tooltip", CLABEL_TOOLTIP_PREFIX + CLABEL_PREFIX + 1, tooltip);
	}
	
	@Test
	public void getAlignment(){
		int alignmemt = new DefaultCLabel(1).getAlignment();
		assertEquals("CLabel has wrong Alignment: ", SWT.CENTER, alignmemt);
	}
	
	@Test
	public void hasImage(){
		assertFalse("Clabel should not have an image", new DefaultCLabel(0).hasImage());
		assertTrue("Clabel should have an image", new DefaultCLabel(1).hasImage());
		assertFalse("Clabel should not have an image", new DefaultCLabel(2).hasImage());
	}
}
