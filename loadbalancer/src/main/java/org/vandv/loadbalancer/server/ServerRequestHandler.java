package org.vandv.loadbalancer.server;

import org.apache.commons.io.IOUtils;
import org.vandv.loadbalancer.IAction;
import org.vandv.communication.IRequestHandler;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

/**
 * Handler for server's requests
 * 
 * Created by vinceseguin on 29/07/14.
 * Updated by vgentilcore on 06/08/14.
 */
public class ServerRequestHandler implements IRequestHandler {

    private static final String REGISTER_ACTION = "REGISTER";
    private static final String UPDATE_ACTION = "UPDATE";

    @Override
    public void handleRequest(Socket socket) throws IOException {

        List<String> lines = IOUtils.readLines(socket.getInputStream());

        try  {
            IAction action = createAction(lines.get(1));
            action.execute(socket.getOutputStream(), lines);

            socket.close();
        } catch (Exception exception) {
            //TODO
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
        if (requestActionLine.contains(REGISTER_ACTION)) {
            return new RegisterAction();
        } else if (requestActionLine.contains(UPDATE_ACTION)) {
            return new UpdateAction();
        } else {
            throw new Exception("The action type is not valid, please refer to the communication protocol.");
        }
    }
}
