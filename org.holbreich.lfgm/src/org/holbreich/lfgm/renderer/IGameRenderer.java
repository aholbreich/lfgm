package org.holbreich.lfgm.renderer;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Canvas;
import org.holbreich.lfgm.model.IGameFieldModel;

/**
 * 
 * Game Renderer.
 *
 * @author Alexander Holbreich (http://alexander.holbreich.org) 
 * @version $Rev: 1 $, ${date}$
 */
public interface IGameRenderer {

	/**
	 * Renders game model wiht given Graphical context {@link GC}.
	 * @param gc G context.
	 */
	void renderGame(GC gc);

	/**
	 * 
	 * @param model
	 */
	void setGameModel(IGameFieldModel model);

	/**
	 * provides canvas.
	 * @param canvas
	 */
	void setMainCanvas(Canvas canvas);

	/**
	 * Translates x, y canvas coordinates to model coordinates.
	 * @param x
	 * @param y
	 * @return 
	 */
	Point reverserTranslate(int x, int y);

}
