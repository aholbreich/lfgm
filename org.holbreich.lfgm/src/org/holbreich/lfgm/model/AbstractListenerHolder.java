package org.holbreich.lfgm.model;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.Marshaller.Listener;

/**
 * 
 * Abstract {@link Listener} holder.
 *
 * @author Alexander Holbreich (http://alexander.holbreich.org) 
 * @version $Rev: 1 $, ${date}$
 * @param <T> Listener type.
 */
public abstract class AbstractListenerHolder<T> {

	private List<T> listeners = new LinkedList<T>();
	
	/**
	 * 
	 * @param listener
	 */
	public void addListener(T listener)
	{
		assert listener!=null;
		this.listeners.add(listener);
	}
	
	/**
	 * 
	 * @param listener
	 */
	public void removeListener(T listener)
	{
		this.listeners.remove(listener);
	}
	
	protected void fireEvent()
	{
		for(T listener :listeners)
		{
			fireOne(listener);
		}
	}

	/**
	 * 
	 * @param listener
	 */
	 protected abstract void fireOne(T listener);
	
}
