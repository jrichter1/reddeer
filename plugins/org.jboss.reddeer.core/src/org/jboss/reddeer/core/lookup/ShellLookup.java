package org.jboss.reddeer.core.lookup;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.hamcrest.Matcher;
import org.jboss.reddeer.common.wait.TimePeriod;
import org.jboss.reddeer.common.wait.WaitUntil;
import org.jboss.reddeer.core.condition.ShellIsActive;
import org.jboss.reddeer.core.condition.ShellMatchingMatcherIsAvailable;
import org.jboss.reddeer.common.condition.WaitCondition;
import org.jboss.reddeer.core.matcher.WithTextMatcher;
import org.jboss.reddeer.core.util.Display;
import org.jboss.reddeer.core.util.ResultRunnable;

/**
 * Shell Lookup, this contains routines for ToolBar implementation that have are widely used 
 * and also requires to be executed in UI Thread
 * @author Jiri Peterka, mlabuda@redhat.com
 * 
 */
public class ShellLookup {
	
	private static ShellLookup instance = null;
	
	private ShellLookup(){
		
	}

	/**
	 * Creates and returns instance of Shell Lookup
	 * 
	 * @return ButtonLookup instance
	 */
	public static ShellLookup getInstance() {
		if (instance == null)
			instance = new ShellLookup();
		return instance;
	}
	
	/**
	 * Returns active shell
	 * Waits for shell to become active in case there is no active shell at the moment
	 * If there is no active shell even after waiting has finished then shell with focus is returned
	 * If there still is no active shell, shell with highest index is returned (@link{org.eclipse.swt.widgets.Display.getShells()})
	 * @return
	 */
	public Shell getActiveShell() {
		new WaitUntil(new ShellIsActive(), TimePeriod.SHORT, false);
		Shell activeShell = getCurrentActiveShell();
		// try to find shell with focus
		if (activeShell == null) {
			new WaitUntil(new ShellIsFocused(), TimePeriod.SHORT, false);
			activeShell = getCurrentFocusShell();
		}
		// if still no shell found, last visible shell will be returned
		if (activeShell == null) {
			activeShell = getLastVisibleShell();
		}
		return activeShell;
	}

	/**
	 * Returns current Active Shell without waiting for shell to become active
	 * Can return null
	 * @return
	 */
	public Shell getCurrentActiveShell () {
		return Display.syncExec(new ResultRunnable<Shell>() {
			
			@Override
			public Shell run() {
				Shell s = Display.getDisplay().getActiveShell();
				if(s!=null && s.isVisible()){
					return s;
				}
				return null;
			}
		});
	}
	
	/**
	 * Returns current focused (and visible) Shell
	 * Can return null
	 * @return
	 */
	public Shell getCurrentFocusShell () {
		return Display.syncExec(new ResultRunnable<Shell>() {
			
			@Override
			public Shell run() {
				Shell[] ss = Display.getDisplay().getShells();
				for (Shell shell : ss) {
					if (shell.isFocusControl() && shell.isVisible()) {
						return shell;
					}
				}
				return null;
			}
		});
	}
	
	/**
	 * Returns all visible shells
	 * @return all visible shells as an shell array
	 */
	public Shell[] getShells() {
		
		return Display.syncExec(new ResultRunnable<Shell[]>() {
			
			@Override
			public Shell[] run() {
				List<Shell> visibleShells = new ArrayList<Shell>();
				Shell[] shells = Display.getDisplay().getShells();
				for (Shell s : shells) {
					if (!s.isDisposed() && s.isVisible()) {
						visibleShells.add(s);
					}
				}
				return visibleShells.toArray(new Shell[visibleShells.size()]);
			}
			
		});
	}
	/**
	 * Returns shell matching matcher and wait for it for timePeriod 
	 * @param matcher
	 * @param timePeriod
	 * @return
	 */
	public Shell getShell(final Matcher<String> matcher , TimePeriod timePeriod) {
		if (!timePeriod.equals(TimePeriod.NONE)){
			new WaitUntil(new ShellMatchingMatcherIsAvailable(matcher), timePeriod, false);
		}
		
		return Display.syncExec(new ResultRunnable<Shell>() {
			
			@Override
			public Shell run() {
				Shell[] shell = Display.getDisplay().getShells(); 
				for (int i = 0; i < shell.length; i++) {
					if(matcher.matches(shell[i])) {
						if (shell[i].isVisible()) {
							return shell[i];
						}
					}
				}
				return null;
			}
		});
	}
	/**
	 * Returns shell matching matcher and wait for it for {@link TimePeriod.NORMAL}
	 * @param matcher
	 * @return
	 */
	public Shell getShell(final Matcher<String> matcher) {
		return getShell(matcher , TimePeriod.NORMAL); 
	}
	/**
	 * Returns shell with title and wait for it for timePeriod
	 * @param title
	 * @param timePeriod
	 * @return
	 */
	public Shell getShell(String title , TimePeriod timePeriod) {
		return getShell(new WithTextMatcher(title) , timePeriod);		
	}
	/**
	 * Returns shell with title and wait for it for {@link TimePeriod.NORMAL}
	 * @param title
	 * @return
	 */
	public Shell getShell(String title) {
		return getShell(new WithTextMatcher(title) , TimePeriod.NORMAL);		
	}
	
	private Shell getLastVisibleShell() {
		return Display.syncExec(new ResultRunnable<Shell>() {
			@Override
			public Shell run() {
				Shell[] shells = Display.getDisplay().getShells();
				for (int i = shells.length - 1; i>=0; i--){
					if (shells[i].isVisible()) return shells[i];
				}
				return null;
			}
		});
	}
	/**
	 * Returns active workbench shell
	 * @return
	 */
	public Shell getWorkbenchShell() {
		return Display.syncExec(new ResultRunnable<Shell>() {
			@Override
			public Shell run() {
				return PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
			}
		});
	}
	
	class ShellIsFocused implements WaitCondition{
		
		@Override
		public boolean test() {
			return getCurrentFocusShell() != null;
		}
		
		@Override
		public String description() {
			return "shell is focused";
		}
	}
}


