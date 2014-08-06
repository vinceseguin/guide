package org.vandv.communication;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.IOException;

/**
 * Created by vinceseguin on 06/08/14.
 */
public class ClientSocketManager {

    private IRequestHandler handler;

    public ClientSocketManager(IRequestHandler handler) {
        this.handler = handler;
    }

    public void start(String address, int port) throws IOException {
        SSLSocketFactory sslSocketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        SSLSocket socket = (SSLSocket) sslSocketFactory.createSocket(address, port);
        socket.startHandshake();
        handler.handleRequest(socket);
        socket.close();
    }
}
