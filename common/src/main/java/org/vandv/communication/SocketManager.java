package org.vandv.communication;

import java.io.IOException;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

import org.vandv.exceptions.ProtocolFormatException;

/**
 * Created by vinceseguin on 29/07/14.
 */
public class SocketManager {

    private IRequestHandler requestHandler;

    public SocketManager(IRequestHandler requestHandler) {
        this.requestHandler = requestHandler;
    }

    /**
     * Start the Socket for the Server
     */
    public void start(int port) throws IOException {

        //KeyStore ks = KeyStore.getInstance("guide");

        SSLServerSocketFactory sslServerSocketFactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
        SSLServerSocket sslServerSocket = (SSLServerSocket) sslServerSocketFactory.createServerSocket(port);

        while (true) {

            final SSLSocket sslSocket = (SSLSocket) sslServerSocket.accept();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        requestHandler.handleRequest(sslSocket);
                        sslSocket.close();
                    } catch (IOException | ProtocolFormatException exception) {
                        //TODO
                    }
                }
            }).start();
        }
    }
}
