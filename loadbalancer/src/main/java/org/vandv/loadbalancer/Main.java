package org.vandv.loadbalancer;

import org.vandv.communication.SocketManager;
import org.vandv.loadbalancer.client.ClientRequestHandler;
import org.vandv.loadbalancer.server.ServerRequestHandler;

import java.io.IOException;

/**
 * Created by vinceseguin on 29/07/14.
 */
public class Main {

    public static void main(String[] args) {
        ServerManager serverManager = new ServerManager();
        SocketManager serverSocketManager = new SocketManager(new ServerRequestHandler(serverManager));
        SocketManager clientSocketManager = new SocketManager(new ClientRequestHandler(serverManager));
        try {
            serverSocketManager.start(5050);
            clientSocketManager.start(5051);
        } catch (IOException exception) {
            //TODO
        }
    }
}
