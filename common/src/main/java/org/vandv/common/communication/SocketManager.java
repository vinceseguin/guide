package org.vandv.common.communication;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

import org.vandv.common.exceptions.ProtocolFormatException;

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

        ServerSocket serverSocket = new ServerSocket(port);
        while (true) {

            final Socket socket = serverSocket.accept();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        requestHandler.handleRequest(socket);
                        socket.close();
                    } catch (IOException | ProtocolFormatException exception) {
                        //TODO
                    }
                }
            }).start();
        }
    }
}
