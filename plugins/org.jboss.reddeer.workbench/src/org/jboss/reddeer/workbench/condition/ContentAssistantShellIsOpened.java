package org.jboss.reddeer.workbench.condition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.swt.widgets.Shell;
import org.jboss.reddeer.swt.api.Table;
import org.jboss.reddeer.swt.condition.WaitCondition;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.handler.ShellHandler;
import org.jboss.reddeer.swt.impl.table.DefaultTable;
import org.jboss.reddeer.swt.lookup.ShellLookup;

/**
 * Checks if content assistand shell is open.
 * @author rawagner
 *
 */
public class ContentAssistantShellIsOpened implements WaitCondition {

    private Shell[] previousShells;
    private Table table;

    /**
     * Default constructor.
     * @param previousShells shells which have been opened
     * before calling action to open content assistant
     */
    public ContentAssistantShellIsOpened(final Shell[] previousShells) {
        this.previousShells = previousShells;
    }

    @Override
    public final boolean test() {
        List<Shell> s1List = new ArrayList<Shell>(Arrays.asList(previousShells));
        List<Shell> s2List = new ArrayList<Shell>(Arrays.asList(ShellLookup
                .getInstance().getShells()));
        s2List.removeAll(s1List);
        // shell with javadoc can be displayed also
        if (s2List.size() == 1 || s2List.size() == 2) {
            for (Shell s : s2List) {
                ShellHandler.getInstance().setFocus(s);
                try {
                    table = new DefaultTable();
                    return true;
                } catch (SWTLayerException ex) {
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * Returns content assistant table.
     * @return content assistant table
     */
    public final Table getContentAssistTable() {
        return table;
    }

    @Override
    public final String description() {
        return "ContentAssistant shell is opened";
    }

}
