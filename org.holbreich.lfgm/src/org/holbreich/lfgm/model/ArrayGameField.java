package org.holbreich.lfgm.model;

/**
 * 
 * Represents Game Field
 *
 * @author Alexander Holbreich (http://alexander.holbreich.org) 
 * @version $Rev: 1 $, ${date}$
 */
public class ArrayGameField extends AbstractListenerHolder<IModelChangeListener> implements IGameFieldModel{

	private int turns;
	
	private boolean[][] cells;

	/**
	 * Creates new game Field with with and height.
	 * @param width
	 * @param height
	 */
	public ArrayGameField(int width, int height) {
		this.cells = new boolean[height][width];
	}

	@Override
	public int getWidth() {
		return this.cells[0].length;
		
	}

	@Override
	public int getHeight() {
		return this.cells.length;
	}

	@Override
	public void nextTurn() {
		turns++;
		
	}

	@Override
	public boolean isGameOver() {
		return turns>1000;
		
	}


	@Override
	protected void fireOne(IModelChangeListener listener) {
		listener.modelChanged();
	}
	
}
