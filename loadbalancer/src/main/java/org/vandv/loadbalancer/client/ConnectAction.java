package org.vandv.loadbalancer.client;

import org.vandv.loadbalancer.IAction;
import org.vandv.loadbalancer.server.Server;
import org.vandv.loadbalancer.ServerManager;

import java.io.OutputStream;
import java.util.List;

/**
 * Created by vinceseguin on 30/07/14.
 */
class ConnectAction implements IAction {

    private static final int PROTOCOL_NUMBER_REQUEST_LINE_INDEX = 3;

    private ServerManager serverManager;

    public ConnectAction(ServerManager serverManager) {
        this.serverManager = serverManager;
    }

    @Override
    public void execute(OutputStream out, List<String> lines) throws Exception {
        String requestType = lines.get(PROTOCOL_NUMBER_REQUEST_LINE_INDEX).split(":")[1];
        Server server = serverManager.getNextAvailableServer(requestType);
        serverManager.sendServerInformation(out, server);
    }
}
