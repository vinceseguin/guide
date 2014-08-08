package org.vandv.loadbalancer.server;

import org.vandv.common.exceptions.ProtocolFormatException;
import org.vandv.loadbalancer.IAction;
import org.vandv.loadbalancer.ServerManager;

import java.io.OutputStream;
import java.util.List;

/**
 * Abstraction of a server action.
 * 
 * Created by vinceseguin on 29/07/14.
 * Updated by vgentilcore on 06/08/14.
 */
public abstract class AbstractServerAction implements IAction {

    private static final int PROTOCOL_REQUEST_TYPE_LINE_INDEX = 2;
    private static final int PROTOCOL_IP_LINE_INDEX = 3;
    private static final int PROTOCOL_PORT_LINE_INDEX = 4;
    private static final int PROTOCOL_NUMBER_REQUEST_LINE_INDEX = 5;
    private static final int PROTOCOL_CPU_LOAD_LINE_INDEX = 6;
    private static final int PROTOCOL_NUMBER_OF_LINES_REGISTER = 5;
    private static final int PROTOCOL_NUMBER_OF_LINES_UPDATE = 7;

    protected ServerManager serverManager;
    
    /**
     * Constructor.
     */
    public AbstractServerAction() {
    	this.serverManager = ServerManager.getInstance();
    }
    
    /**
     * Execute the server's action.
     * 
     * @param server the server to handle.
     */
    public abstract void execute(Server server);
    
    /**
     * Template method to execute a server's action.
     * 
     * @param lines request's text lines.
     * @throws Exception
     */
    private void templateMethod(List<String> lines) throws ProtocolFormatException {
        Server server = createServer(lines);
        execute(server);
    }

    /**
     * Creates a new server instance.
     * 
     * @param lines request's text lines.
     * @return the new server instance.
     * @throws ProtocolFormatException
     */
    private Server createServer(List<String> lines) throws ProtocolFormatException {
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

        if (lines.size() == PROTOCOL_NUMBER_OF_LINES_REGISTER) {
            return server;
        }
        if (lines.size() == PROTOCOL_NUMBER_OF_LINES_UPDATE ) {
            int currentNumberOfRequest = Integer.parseInt(lines.get(PROTOCOL_NUMBER_REQUEST_LINE_INDEX).split(":")[1]);
            int cpuLoad = Integer.parseInt(lines.get(PROTOCOL_CPU_LOAD_LINE_INDEX).split(":")[1]);
            server.setCurrentNumberOfRequest(currentNumberOfRequest);
            server.setCpuLoad(cpuLoad);
            return server;
        } else {
            throw new ProtocolFormatException();
        }
    }

    @Override
    public void execute(OutputStream out, List<String> lines) throws Exception {
        this.templateMethod(lines);
    }
}
