package org.jboss.reddeer.swt.impl.tree;

import java.util.LinkedList;
import java.util.List;

import org.hamcrest.Matcher;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.swt.api.Tree;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.handler.TreeHandler;
import org.jboss.reddeer.swt.handler.TreeItemHandler;
import org.jboss.reddeer.swt.impl.tree.internal.BasicTreeItem;
import org.jboss.reddeer.swt.reference.ReferencedComposite;
import org.jboss.reddeer.swt.widgets.AbstractWidget;

public abstract class AbstractTree extends AbstractWidget<org.eclipse.swt.widgets.Tree> implements Tree {

	private static final Logger logger = Logger.getLogger(AbstractTree.class);

	private TreeHandler treeHandler = TreeHandler.getInstance();
	private TreeItemHandler treeItemHandler = TreeItemHandler.getInstance();

	protected AbstractTree(ReferencedComposite refComposite, int index, Matcher<?>... matchers){
		super(org.eclipse.swt.widgets.Tree.class, refComposite, index, matchers);
	}
	
	protected AbstractTree(org.eclipse.swt.widgets.Tree tree){
		super(tree);
	}
	
	public List<TreeItem> getAllItems() {
		List<TreeItem> list = new LinkedList<TreeItem>();
		list.addAll(getAllItemsRecursive(getItems()));
		return list;
	}

	public List<TreeItem> getItems() {
		LinkedList<TreeItem> items = new LinkedList<TreeItem>();
		List<org.eclipse.swt.widgets.TreeItem> eclipseItems = treeHandler.getSWTItems(swtWidget);
		for (org.eclipse.swt.widgets.TreeItem swtTreeItem : eclipseItems) {
			items.addLast(new BasicTreeItem(swtTreeItem));
		}
		return items;
	}

	public void selectItems(final TreeItem... treeItems) {
		// Tree items should be logged, however, the names needs to 
		// be retrieved in UI thread so it might be a performance issue
		logger.info("Select specified tree items");
		org.eclipse.swt.widgets.TreeItem[] items = new org.eclipse.swt.widgets.TreeItem[treeItems.length];
		for (int i=0; i < treeItems.length; i++) {
			items[i] = treeItems[i].getSWTWidget();
		}
		treeItemHandler.selectItems(items);
	}

	public void setFocus() {
		treeHandler.setFocus(swtWidget);
	}

	/**
	 * @see Tree#getColumnCount()
	 */
	public int getColumnCount() {
		return treeHandler.getColumnCount(swtWidget);
	}
	
	public List<String> getHeaderColumns() {
		return treeHandler.getHeaderColumns(swtWidget);
	}

	public void unselectAllItems() {
		logger.info("Unselect all tree items");
		treeHandler.unselectAllItems(swtWidget);
	}
	
	private List<TreeItem> getAllItemsRecursive(List<TreeItem> parentItems) {
		List<TreeItem> list = new LinkedList<TreeItem>();

		for (TreeItem item : parentItems) {
			list.add(item);
			list.addAll(getAllItemsRecursive(item.getItems()));
		}
		return list;
	}
}
