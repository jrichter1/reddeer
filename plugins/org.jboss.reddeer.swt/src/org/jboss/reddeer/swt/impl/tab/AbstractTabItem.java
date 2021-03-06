package org.jboss.reddeer.swt.impl.tab;

import org.eclipse.swt.SWT;
import org.hamcrest.Matcher;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.swt.api.TabItem;
import org.jboss.reddeer.swt.handler.TabItemHandler;
import org.jboss.reddeer.swt.handler.WidgetHandler;
import org.jboss.reddeer.swt.reference.ReferencedComposite;
import org.jboss.reddeer.swt.widgets.AbstractWidget;

/**
 * Abstract class for all TabItem implementations
 * 
 * @author Andrej Podhradsky
 * @author Vlado Pakan
 * 
 */
public abstract class AbstractTabItem extends AbstractWidget<org.eclipse.swt.widgets.TabItem> implements TabItem {

	private static final Logger logger = Logger.getLogger(AbstractTabItem.class);

	protected org.eclipse.swt.widgets.TabFolder swtParent;
	
	private TabItemHandler tabItemHandler = TabItemHandler.getInstance();

	protected AbstractTabItem(ReferencedComposite refComposite, int index, Matcher<?>... matchers){
		super(org.eclipse.swt.widgets.TabItem.class, refComposite, index, matchers);
		swtParent = TabItemHandler.getInstance().getTabFolder(swtWidget);
	}
	
	/**
	 * See {@link TabItem}
	 */
	@Override
	public void activate() {
		logger.info("Activate " + this.getText());
		tabItemHandler.select(swtWidget);
		tabItemHandler.notifyTabFolder(
			tabItemHandler.createEventForTabItem(swtWidget,SWT.Selection),
			swtParent);
	}
	
	/**
	 * See {@link TabItem}
	 */
	@Override
	public String getText() {
		return WidgetHandler.getInstance().getText(swtWidget);
	}
}
