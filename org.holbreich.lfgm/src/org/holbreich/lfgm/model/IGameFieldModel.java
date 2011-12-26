package org.holbreich.lfgm.model;

/**
 * Common interface for a Life Game field.
 * 
 * @author Alexander Holbreich (http://alexander.holbreich.org)
 * @version $Rev: 1 $, ${date}$
 */
public interface IGameFieldModel {

	/**
	 * @return model with in cells.
	 */
	int getWidth();

	/**
	 * @return model height in cells.
	 */
	int getHeight();

	/**
	 * 
	 */
	void nextTurn();

	/**
	 * 
	 * @return
	 */
	boolean isGameOver();

	
	void addListener(IModelChangeListener mainGameView);

}
