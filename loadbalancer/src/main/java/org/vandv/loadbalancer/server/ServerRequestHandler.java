package org.vandv.loadbalancer.server;

import org.apache.commons.io.IOUtils;
import org.vandv.loadbalancer.IAction;
import org.vandv.communication.IRequestHandler;
import org.vandv.loadbalancer.ServerManager;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

/**
 * Created by vinceseguin on 29/07/14.
 */
public class ServerRequestHandler implements IRequestHandler {

    private static final String REGISTER_ACTION = "REGISTER";
    private static final String UPDATE_ACTION = "UPDATE";

    private ServerManager serverManager;

    public ServerRequestHandler(ServerManager serverManager) {
        this.serverManager = serverManager;
    }

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

    protected IAction createAction(String requestActionLine) throws Exception {
        if (requestActionLine.contains(REGISTER_ACTION)) {
            return new RegisterAction(this.serverManager);
        } else if (requestActionLine.contains(UPDATE_ACTION)) {
            return new UpdateAction(this.serverManager);
        } else {
            throw new Exception("The action type is not valid, please refer to the communication protocol.");
        }
    }
}
