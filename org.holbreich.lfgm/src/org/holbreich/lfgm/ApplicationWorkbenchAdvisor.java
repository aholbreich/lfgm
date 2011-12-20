package org.holbreich.lfgm;

import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchAdvisor;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;

/**
 * 
 * Standard Workbench Advisor.
 *
 * @author Alexander Holbreich (http://alexander.holbreich.org) 
 * @version $Rev: 1 $, ${date}$
 *
 */
public class ApplicationWorkbenchAdvisor extends WorkbenchAdvisor {

	/**
	 * ID of the initial Perspective.
	 */
	private static final String PERSPECTIVE_ID = "org.holbreich.lfgm.perspective"; //$NON-NLS-1$
	

	/**
	 * Defines Workbench windows Advisor
	 * {@inheritDoc}
	 */
    @Override
	public WorkbenchWindowAdvisor createWorkbenchWindowAdvisor(IWorkbenchWindowConfigurer configurer) {
        return new ApplicationWorkbenchWindowAdvisor(configurer);
    }

	@Override
	public String getInitialWindowPerspectiveId() {
		return PERSPECTIVE_ID;
	}
}
