package org.vandv.loadbalancer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.vandv.common.communication.SocketManager;

import java.io.IOException;

/**
 * Created by vinceseguin on 29/07/14.
 * Updated by vgentilcore on 06/08/14.
 */
public class Main {

    private static final Logger logger = LogManager.getLogger(Main.class.getName());

    public static void main(String[] args) {

        SocketManager serverSocketManager = new SocketManager(new LoadBalancerRequestHandler());

        try {
            serverSocketManager.start(5050);
        } catch (IOException exception) {
            logger.error(exception.getMessage());
        }
    }
}
