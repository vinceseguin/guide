package org.vandv.loadbalancer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.vandv.common.communication.SocketManager;
import org.vandv.loadbalancer.client.ClientRequestHandler;
import org.vandv.loadbalancer.server.ServerRequestHandler;

import java.io.File;
import java.io.IOException;

/**
 * Created by vinceseguin on 29/07/14.
 * Updated by vgentilcore on 06/08/14.
 */
public class Main {

    private static final Logger logger = LogManager.getLogger(Main.class.getName());

    public static void main(String[] args) {

        SocketManager serverSocketManager = new SocketManager(new ServerRequestHandler());
        SocketManager clientSocketManager = new SocketManager(new ClientRequestHandler());
        try {
            serverSocketManager.start(5050);
            clientSocketManager.start(5051);
        } catch (IOException exception) {
            logger.error(exception.getMessage());
        }
    }
}
