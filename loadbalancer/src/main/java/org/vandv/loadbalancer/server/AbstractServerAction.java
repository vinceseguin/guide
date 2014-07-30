package org.vandv.loadbalancer.server;

import org.vandv.loadbalancer.IAction;

import java.io.OutputStream;
import java.util.List;

/**
 * Created by vinceseguin on 29/07/14.
 */
public abstract class AbstractServerAction implements IAction {

    private static final int PROTOCOL_REQUEST_TYPE_LINE_INDEX = 2;
    private static final int PROTOCOL_IP_LINE_INDEX = 3;
    private static final int PROTOCOL_PORT_LINE_INDEX = 4;
    private static final int PROTOCOL_NUMBER_REQUEST_LINE_INDEX = 5;
    private static final int PROTOCOL_CPU_LOAD_LINE_INDEX = 6;

    private void templateMethod(List<String> lines) throws Exception {
        Server server = createServer(lines);
        execute(server);
    }

    public abstract void execute(Server server);

    private Server createServer(List<String> lines) throws Exception {
        Server server = null;

        String[] requestTypes = lines.get(PROTOCOL_REQUEST_TYPE_LINE_INDEX).split(":")[1].split(",");

        for (int i=0; i<requestTypes.length; i++) {

            if (requestTypes[i].equals(VisualRecognitionServer.VISUAL_RECOGNITION_STRING)) {
                server = new VisualRecognitionServer(server);
            }
        }

        String address = lines.get(PROTOCOL_IP_LINE_INDEX).split(":")[1];
        int port = Integer.parseInt(lines.get(PROTOCOL_PORT_LINE_INDEX).split(":")[1]);
        server.setAddress(address);
        server.setPort(port);

        if (lines.size() == 5) {
            return server;
        }
        if (lines.size() == 7) {
            int currentNumberOfRequest = Integer.parseInt(lines.get(PROTOCOL_NUMBER_REQUEST_LINE_INDEX).split(":")[1]);
            double cpuLoad = Double.parseDouble(lines.get(PROTOCOL_CPU_LOAD_LINE_INDEX).split(":")[1]);
            server.setCurrentNumberOfRequest(currentNumberOfRequest);
            server.setCpuLoad(cpuLoad);
            return server;
        } else {
            throw new Exception("Error in protocol format.");
        }
    }

    @Override
    public void execute(OutputStream out, List<String> lines) throws Exception {
        this.templateMethod(lines);
    }
}
