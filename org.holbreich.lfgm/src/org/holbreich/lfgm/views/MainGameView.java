package org.holbreich.lfgm.views;


import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.holbreich.lfgm.Activator;
import org.holbreich.lfgm.model.ArrayGameField;
import org.holbreich.lfgm.model.IModelChangeListener;
import org.holbreich.lfgm.model.ModelHolder;
import org.holbreich.lfgm.renderer.IGameRenderer;
import org.holbreich.lfgm.renderer.SimpleCanvasRenderer;

/**
 * Main Game View
 * 
 * @author Alexander Holbreich (http://alexander.holbreich.org)
 * @version $Rev: 1 $, ${date}$
 */
public class MainGameView extends ViewPart implements PaintListener, IModelChangeListener, MouseListener {

	private Canvas				canvas;

	private IGameRenderer		renderer;

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String	ID	= "org.holbreich.lfgm.views.MainGameView";

	/**
	 * The constructor.
	 */
	public MainGameView() {
		ModelHolder.getInstance().setModel(new ArrayGameField(167, 113));
		ModelHolder.getInstance().getModel().addListener(this);
	}

	/**
	 * This is a callback that will allow us to create the viewer and initialize it.
	 */
	@Override
	public void createPartControl(Composite parent) {

		canvas = new Canvas(parent, SWT.NONE);
		canvas.addPaintListener(this);
		canvas.addMouseListener(this);

		// Create the help context id for the viewer's control
		PlatformUI.getWorkbench().getHelpSystem().setHelp(canvas, "org.holbreich.lfgm.viewer");

	}

	@Override
	public void paintControl(PaintEvent e) {
		getRenderer().setMainCanvas(canvas);
		getRenderer().renderGame(e.gc);
	}

	/**
	 * @return
	 */
	private IGameRenderer getRenderer() {
		if (renderer == null)
		{
			renderer = new SimpleCanvasRenderer();
			renderer.setGameModel(ModelHolder.getInstance().getModel());
		}
		return renderer;
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	@Override
	public void setFocus() {
		canvas.setFocus();
	}

	@Override
	public void modelChanged() {
		canvas.update();
		
	}

	@Override
	public void mouseDoubleClick(MouseEvent e) {
		// TODO aho  Auto-generated method stub
		
	}

	@Override
	public void mouseDown(MouseEvent e) {
		Activator.getDefault().logInfo("Mouse event "+e.x +" : "+e.y);
		ModelHolder.getInstance().getModel().initNewElementAt(renderer)
		
	}

	@Override
	public void mouseUp(MouseEvent e) {
		// TODO aho  Auto-generated method stub
		
	}


}
