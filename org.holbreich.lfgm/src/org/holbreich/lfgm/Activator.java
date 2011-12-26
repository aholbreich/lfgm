package org.holbreich.lfgm;

import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.holbreich.lfgm"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;
	
	private static ILog logger;
	
	/**
	 * The constructor
	 */
	public Activator() {
	}


	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		getLog();
	}


	@Override
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path
	 *
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}
	
	/**
	 * 
	 * @param message
	 */
	public static void logError(String message, Throwable e)
	{
		IStatus status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, message,e);
		getLogger().log(status); 
	}
	
	
	/**
	 * 
	 * @param message
	 */
	public static void logInfo(String message)
	{
		IStatus status = new Status(IStatus.INFO, Activator.PLUGIN_ID, message);
		getLogger().log(status); 
	}
	
	/**
	 * 
	 * @param message
	 */
	public static void logWarning(String message)
	{
		IStatus status = new Status(IStatus.WARNING, Activator.PLUGIN_ID, message);
		getLogger().log(status); 
	}


	private static ILog getLogger() {
		if(logger ==null)
		{
			logger = Activator.getDefault().getLog();
			//logger.addLogListener(new LFGMLogListener());
			
		}
		return logger;
	}
	
}
