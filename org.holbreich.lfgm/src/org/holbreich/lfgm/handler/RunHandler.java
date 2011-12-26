package org.holbreich.lfgm.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.widgets.Display;
import org.holbreich.lfgm.model.ModelHolder;

/**
 * 
 * TODO insert type comment
 *
 * @author Alexander Holbreich (http://alexander.holbreich.org) 
 * @version $Rev: 1 $, ${date}$
 */
public class RunHandler extends AbstractHandler {



	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		//Leaving UI thread
		new Thread(){

			@Override
			public void run() {
				
				while(!ModelHolder.getInstance().getModel().isGameOver())
				{
					ModelHolder.getInstance().getModel().nextTurn();
					Display.getCurrent().asyncExec(new Runnable() {
						
						@Override
						public void run() {
							
							
						}
					});
				}
			}
			
		}.run();
		return null;
	}

}
