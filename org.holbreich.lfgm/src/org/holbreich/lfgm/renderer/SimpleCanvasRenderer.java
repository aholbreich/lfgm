package org.holbreich.lfgm.renderer;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Canvas;
import org.holbreich.lfgm.model.IGameFieldModel;

/**
 * 
 * First Game renderer
 *
 * @author Alexander Holbreich (http://alexander.holbreich.org) 
 * @version $Rev: 1 $, ${date}$
 */
public class SimpleCanvasRenderer implements IGameRenderer {

	private static final int	height	= 5;

	private static final int	with	= 5;

	
	
	private IGameFieldModel model;

	private Canvas	canvas;
	
	@Override
	public void renderGame(GC gc) {
		if(model == null)
		{
			throw new IllegalStateException("Model can't be null");
		}
		
	//	gc.setAdvanced(true);
		
		// Create the image to fill the canvas
        Image image = new Image(canvas.getDisplay(), canvas.getBounds());
        GC gcImage = new GC(image); // Set up the offscreen gc
		
		for(int x=0; x<model.getWidth();x++)
		{
			for(int y=0; y<model.getHeight();y++)
			{
				gcImage.drawRectangle((with+1)*x, (1+height)*y, with, height);
			}
		}
		  // Draw the offscreen buffer to the screen
        gc.setAdvanced(true);
		gc.drawImage(image, 0, 0);
        // Clean up
        image.dispose();
        gcImage.dispose();
	}

	@Override
	public void setGameModel(IGameFieldModel model) {
		this.model =model;
	}

	@Override
	public void setMainCanvas(Canvas canvas) {
		this.canvas = canvas;
		
	}

}
