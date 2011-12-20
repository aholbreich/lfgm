package org.holbreich.lfgm.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

/**
 * Main Game View
 * 
 * @author Alexander Holbreich (http://alexander.holbreich.org)
 * @version $Rev: 1 $, ${date}$
 */
public class MainGameView extends ViewPart implements PaintListener {

	private static final int	height	= 5;

	private static final int	with	= 5;

	private Canvas canvas;
	
	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String	ID	= "org.holbreich.lfgm.views.MainGameView";

	/**
	 * The constructor.
	 */
	public MainGameView() {
	}


	/**
	 * This is a callback that will allow us to create the viewer and initialize it.
	 */
	@Override
	public void createPartControl(Composite parent) {
		
		canvas = new Canvas(parent, SWT.NONE);
		canvas.addPaintListener(this);
		

		// Create the help context id for the viewer's control
		PlatformUI.getWorkbench().getHelpSystem().setHelp(canvas, "org.holbreich.lfgm.viewer");
		
	}
	
	
	@Override
	public void paintControl(PaintEvent e) {
		
		e.gc.drawRectangle(5,5,with,height);
		
	}



	/**
	 * Passing the focus request to the viewer's control.
	 */
	@Override
	public void setFocus() {
		canvas.setFocus();
	}




}
