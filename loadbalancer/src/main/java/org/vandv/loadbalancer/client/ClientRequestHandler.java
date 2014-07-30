package org.vandv.loadbalancer.client;

import org.apache.commons.io.IOUtils;
import org.vandv.loadbalancer.IRequestHandler;
import org.vandv.loadbalancer.IAction;
import org.vandv.loadbalancer.ServerManager;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

/**
 * Created by vinceseguin on 29/07/14.
 */
public class ClientRequestHandler implements IRequestHandler {

    private static final String CONNECT_ACTION = "CONNECT";

    private ServerManager serverManager;

    public ClientRequestHandler(ServerManager serverManager) {
        this.serverManager = serverManager;
    }

    @Override
    public void handleRequest(Socket socket) throws IOException {

        List<String> lines = IOUtils.readLines(socket.getInputStream());

        try  {
            while (true) {
                IAction action = createAction(lines.get(1));
                action.execute(socket.getOutputStream(), lines);
            }
        } catch (Exception exception) {
            //TODO
        }
    }

    protected IAction createAction(String requestActionLine) throws Exception {
        if (requestActionLine.contains(CONNECT_ACTION)) {
            return new ConnectAction(serverManager);
        } else {
            throw new Exception("The action type is not valid, please refer to the communication protocol.");
        }
    }
}
