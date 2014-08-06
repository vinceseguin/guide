package org.vandv;

import org.vandv.communication.ClientRequestHandler;
import org.vandv.communication.SocketManager;

import java.io.IOException;

/**
 * Created by vinceseguin on 03/08/14.
 */
public class Main {

    public static void main(String[] args) {
        SocketManager serverSocketManager = new SocketManager(new ClientRequestHandler());

        try {
            serverSocketManager.start(5050);
        } catch (IOException exception) {
            //TODO
        }
    }
}
