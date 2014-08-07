package org.vandv.loadbalancer.client;

import org.apache.commons.io.IOUtils;
import org.vandv.loadbalancer.IAction;
import org.vandv.communication.IRequestHandler;
import org.vandv.exceptions.InvalidActionTypeException;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

/**
 * Handler for client's requests
 * 
 * Created by vinceseguin on 29/07/14.
 * Updated by vgentilcore on 06/08/14.
 */
public class ClientRequestHandler implements IRequestHandler {

    private static final String CONNECT_ACTION = "CONNECT";

    @Override
    public void handleRequest(Socket socket) throws IOException {

        List<String> lines = IOUtils.readLines(socket.getInputStream());

        try  {
            IAction action = createAction(lines.get(1));
            action.execute(socket.getOutputStream(), lines);
        } catch (Exception exception) {
        	// exception.printStackTrace();
        }
    }

    /**
     * Creates a new action to handle a request.
     * 
     * @param requestActionLine the request action identifier.
     * @return the new action.
     * @throws Exception
     */
    protected IAction createAction(String requestActionLine) throws Exception {
        if (requestActionLine.contains(CONNECT_ACTION)) {
            return new ConnectAction();
        } else {
            throw new InvalidActionTypeException();
        }
    }
}
