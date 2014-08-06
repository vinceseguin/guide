package org.vandv.loadbalancer;

import java.io.OutputStream;
import java.util.List;

/**
 * Action's interface.
 * 
 * Created by vinceseguin on 29/07/14.
 */
public interface IAction {

	/**
	 * Executes the action.
	 * 
	 * @param out socket's output stream.
	 * @param lines request's text lines.
	 * @throws Exception
	 */
    public void execute(OutputStream out, List<String> lines) throws Exception;
}
