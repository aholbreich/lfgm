package org.holbreich.lfgm.model;


/**
 * Access point to the model. Holds referenc to the model.
 * 
 * @author Alexander Holbreich (http://alexander.holbreich.org)
 * @version $Rev: 1 $, ${date}$
 */
public class ModelHolder {

	private IGameFieldModel	fieldModel;
	private static ModelHolder instance;
	
	private UICOnfiguration config;

	/**
	 * Singleton.
	 * @return ModelHolder.
	 */
	public static ModelHolder getInstance() {
		if(instance==null)
		{
			instance = new ModelHolder();
		}
		return instance;
	}

	/**
	 * 
	 * @param arrayGameField
	 */
	public void setModel(IGameFieldModel arrayGameField) {
		assert arrayGameField !=null;
		this.fieldModel = arrayGameField;
	}
	
	/**
	 * 
	 * @return
	 */
	public IGameFieldModel getModel() {
		return fieldModel;
	}


}
