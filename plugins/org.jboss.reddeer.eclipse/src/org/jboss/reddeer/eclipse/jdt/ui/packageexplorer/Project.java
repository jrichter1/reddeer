package org.jboss.reddeer.eclipse.jdt.ui.packageexplorer;

import org.jboss.reddeer.eclipse.core.resources.AbstractProject;
import org.jboss.reddeer.swt.api.TreeItem;

/**
 * Represents a general project inside Project explorer, Package explorer or Resource
 * Navigator.
 * 
 * @author Vlado Pakan, mlabuda@redhat.com
 * @deprecated since 0.7.0. Use {@link org.jboss.reddeer.eclipse.core.resources.Project} instead.
 * 
 */
@Deprecated
public class Project extends AbstractProject {

	/**
	 * Creates a project represented by specified {@link TreeItem}.
	 * 
	 * @param treeItem
	 *            encapsulated tree item
	 */
	public Project(TreeItem treeItem) {
		super(treeItem);
	}

	@Override
	public String[] getNatureIds() {
		return null;
	}
}
