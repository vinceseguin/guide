package org.vandv.server;

import org.vandv.common.communication.SocketManager;
import org.vandv.server.client.ClientRequestHandler;
import org.vandv.server.loadbalancer.ServerRegistrationManager;

import java.io.IOException;

/**
 * Created by vinceseguin on 03/08/14.
 */
public class Main {

    public static void main(String[] args) {
        SocketManager serverSocketManager = new SocketManager(new ClientRequestHandler());

        try {
            serverSocketManager.start(5050);

            //TODO ADJUST IPS
            ServerRegistrationManager serverRegistrationManager = new ServerRegistrationManager("",123,"",123);
        } catch (IOException exception) {
            //TODO
        }
    }
}
