package org.vandv.loadbalancer.server;

/**
 * Created by vinceseguin on 30/07/14.
 */
public class VisualRecognitionServer extends Server {

    public static final String VISUAL_RECOGNITION_STRING = "VISUAL_RECOGNITION";

    public VisualRecognitionServer(Server server) {
        super(server);
    }

    @Override
    public boolean canHandle(String requestType) {

        if (requestType.equals(VISUAL_RECOGNITION_STRING)) {
            return true;
        } else {
            return this.server != null && this.server.canHandle(requestType);
        }
    }
}
