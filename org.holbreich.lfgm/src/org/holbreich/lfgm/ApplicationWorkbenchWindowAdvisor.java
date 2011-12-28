package org.holbreich.lfgm;

import org.eclipse.swt.graphics.Point;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;

/**
 * This class configures Workbench Window
 * 
 * @author Alexander Holbreich (http://alexander.holbreich.org)
 * @version $Rev: 1 $, ${date}$
 */
public class ApplicationWorkbenchWindowAdvisor extends WorkbenchWindowAdvisor {

	public ApplicationWorkbenchWindowAdvisor(IWorkbenchWindowConfigurer configurer) {
		super(configurer);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void preWindowOpen() {
		IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
		configurer.setInitialSize(new Point(1024, 798));
		configurer.setShowCoolBar(true);
		configurer.setShowStatusLine(true);
		configurer.setShowPerspectiveBar(false);
		configurer.setShowProgressIndicator(true);
	}

}
