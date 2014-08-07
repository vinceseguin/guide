package org.vandv.loadbalancer.client;

import java.io.OutputStream;
import java.util.List;

import org.vandv.loadbalancer.IAction;
import org.vandv.loadbalancer.ServerManager;
import org.vandv.loadbalancer.server.Server;

/**
 * Abstraction of a client action.
 * 
 * Created by vgentilcore on 06/08/14.
 */
public abstract class AbstractClientAction implements IAction {

    private static final int PROTOCOL_NUMBER_REQUEST_LINE_INDEX = 2;
	
	protected ServerManager serverManager;
	
	/**
	 * Constructor
	 */
	public AbstractClientAction() {
		this.serverManager = ServerManager.getInstance();
	}
	
    /**
     * Execute the client's action.
     * 
     * @param out socket's output stream.
     * @param server the server to handle client's request.
     */
    public abstract void execute(OutputStream out, Server server);
	
	/**
	 * Template method to execute a client's action.
	 * 
	 * @param out socket's output stream.
	 * @param lines request's text lines.
	 * @throws Exception
	 */
    private void templateMethod(OutputStream out, List<String> lines) throws Exception {
        String requestType = lines.get(PROTOCOL_NUMBER_REQUEST_LINE_INDEX).split(":")[1];
        Server server = serverManager.getNextAvailableServer(requestType);
        server.setCurrentNumberOfRequest(server.getCurrentNumberOfRequest() + 1);
        
    	execute(out, server);
    }
	
	@Override
	public void execute(OutputStream out, List<String> lines) throws Exception {
		this.templateMethod(out, lines);
	}
}
