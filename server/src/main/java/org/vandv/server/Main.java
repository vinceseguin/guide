package org.vandv.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.vandv.common.communication.SocketManager;
import org.vandv.common.exceptions.ProtocolFormatException;
import org.vandv.server.client.ClientRequestHandler;
import org.vandv.server.loadbalancer.ServerRegistrationManager;

import java.io.IOException;

/**
 * Created by vinceseguin on 03/08/14.
 */
public class Main {

    private static final Logger logger = LogManager.getLogger(Main.class.getName());

    public static void main(String[] args) {
        SocketManager serverSocketManager = new SocketManager(new ClientRequestHandler());

        try {
            //TODO ADJUST IPS
            ServerRegistrationManager serverRegistrationManager = new ServerRegistrationManager("10.196.122.21",6060,
                    "10.196.122.21",5050);

            serverSocketManager.start(6060);

        } catch (IOException | ProtocolFormatException exception) {
            logger.error(exception);
        }
    }
}
