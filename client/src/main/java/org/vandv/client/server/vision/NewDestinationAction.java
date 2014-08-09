package org.vandv.client.server.vision;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.vandv.client.server.IAction;

/**
 * This command is called when a client starts a new destination
 * process.
 * 
 * Created by vgentilcore on 08/08/2014.
 */
public class NewDestinationAction implements IAction {
	
	@Override
	public void execute(OutputStream out, List<String> lines, char[] data)
			throws IOException {

		
		while (true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			
		}
	}

}
