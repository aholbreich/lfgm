package org.holbreich.lfgm.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.holbreich.lfgm.model.ModelHolder;

/**
 * 
 * Handles this.
 *
 * @author Alexander Holbreich (http://alexander.holbreich.org) 
 * @version $Rev: 1 $, ${date}$
 */
public class RunHandler extends AbstractHandler {



	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		
		//Leaving UI thread
		Job gameRunJob =new Job("running "){

			@Override
			protected IStatus run(IProgressMonitor monitor) {
				
				while(!ModelHolder.getInstance().getModel().isGameOver())
					{
						ModelHolder.getInstance().getModel().nextTurn();
						
					}
				return Status.OK_STATUS;
			
			}};
			gameRunJob.schedule();
		
//
//			@Override
//			public void run() {
//				
//				while(!ModelHolder.getInstance().getModel().isGameOver())
//				{
//					ModelHolder.getInstance().getModel().nextTurn();
//					try {
//						this.sleep(1);
//					}
//					catch (InterruptedException e) {
//						// TODO aho  Auto-generated catch block
//						e.printStackTrace();
//					}
//				}
//			}
//			
//		}.run();
		return null;
	}

}
