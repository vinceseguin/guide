package org.vandv.server.loadbalancer;

import org.vandv.common.communication.ClientSocketManager;
import org.vandv.common.communication.IRequestHandler;
import org.vandv.common.exceptions.ProtocolFormatException;
import org.vandv.server.client.vision.VisualRecognitionManager;

import java.io.IOException;

/**
 * Created by vinceseguin on 06/08/14.
 */
public class ServerRegistrationManager {

    private static final int TICK_DURATION = 100;
    private VisualRecognitionManager visualRecognitionManager;
    private String myIp;
    private int myPort;
    private String loadBalancerIp;
    private int loadBalancerPort;

    public ServerRegistrationManager(String myIp, int myPort,
                                     String loadBalancerIp, int loadBalancerPort) {
        this.visualRecognitionManager = VisualRecognitionManager.getInstance();
        this.myIp = myIp;
        this.myPort = myPort;
        this.loadBalancerIp = loadBalancerIp;
        this.loadBalancerPort = loadBalancerPort;

        registerServer();
        updateInfo();
    }

    public void registerServer() {
        IRequestHandler handler = new RegisterRequestHandler(myIp, myPort);

        ClientSocketManager clientSocketManager = new ClientSocketManager(handler);

        try {
            clientSocketManager.start(loadBalancerIp, loadBalancerPort);
        } catch (IOException | ProtocolFormatException ex) {
            //TODO
        }
    }

    public void updateInfo() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(TICK_DURATION * 1000);

                        IRequestHandler handler = new UpdateRequestHandler(myIp, myPort,
                                visualRecognitionManager.getCurrentNumberOfRequest());

                        ClientSocketManager clientSocketManager = new ClientSocketManager(handler);

                        clientSocketManager.start(loadBalancerIp, loadBalancerPort);
                    } catch (Exception ex) {
                        //TODO
                    }
                }
            }
        }).start();
    }
}
