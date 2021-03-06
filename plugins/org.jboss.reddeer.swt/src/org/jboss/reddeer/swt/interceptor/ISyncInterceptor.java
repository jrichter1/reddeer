package org.jboss.reddeer.swt.interceptor;

/**
 * Display SyncExec interceptor. Serves to intercept user operation before and after each
 * call of Display.syncExec
 * 
 * To make interceptor working add
 
 * @author Jiri Peterka
 * @deprecated since 0.8, use {@link #org.jboss.reddeer.core.interceptor.ISyncInterceptor}
 */
@Deprecated
public interface ISyncInterceptor {

	/**
	 * Method will be executed before syncExec is performed
	 */
	void beforeSyncOp();
	
	/**
	 * Method will be executed after syncExec is performed
	 */
	void afterSyncOp();
}
