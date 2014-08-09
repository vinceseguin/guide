package org.vandv.common.communication;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import org.vandv.common.exceptions.ProtocolFormatException;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by vinceseguin on 06/08/14.
 */
public class ClientSocketManager {

    private IRequestHandler handler;

    public ClientSocketManager(IRequestHandler handler) {
        this.handler = handler;
    }

    public void start(String address, int port) throws IOException, ProtocolFormatException {
        Socket socket = new Socket(address, port);
        handler.handleRequest(socket);
        socket.close();
    }
}
