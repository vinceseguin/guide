package org.vandv.loadbalancer;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

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
                    } catch (IOException exception) {
                        //TODO
                    }
                }
            }).start();
        }
    }
}
