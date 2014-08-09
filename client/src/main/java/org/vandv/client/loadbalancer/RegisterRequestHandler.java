package org.vandv.client.loadbalancer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.vandv.client.server.IClientRequestManager;
import org.vandv.common.communication.IRequestHandler;
import org.vandv.common.exceptions.ProtocolFormatException;

/**
 * Handles registration requests from clients to loadbalancer.
 * 
 * Created by vgentilcore on 08/08/2014.
 */
public class RegisterRequestHandler implements IRequestHandler {

    private static final int SERVER_ADDRESS_LINE_INDEX = 1;
    private static final int SERVER_PORT_LINE_INDEX = 2;
    
	private String requestType;
	private IClientRequestManager manager;
	
	/**
	 * Constructor
	 * @param requestType
	 * @param manager
	 */
    public RegisterRequestHandler(String requestType, IClientRequestManager manager) {
    	this.requestType = requestType;
    	this.manager = manager;
    }

	@Override
	public void handleRequest(Socket socket) throws IOException,
			ProtocolFormatException {
	    StringBuilder sb = new StringBuilder();
	    sb.append("GUIDE_CLIENT_LOADBALANCER\r\n");
	    sb.append("REQUEST-ACTION:CONNECT\r\n");
	    sb.append(String.format("REQUEST-TYPE:%s", requestType));
	
	    OutputStream out = socket.getOutputStream();
	    IOUtils.write(sb.toString(), out);
	    
	    InputStream is = socket.getInputStream();
	    List<String> lines = IOUtils.readLines(is, "UTF-8");
	    
		String serverIp = lines.get(SERVER_ADDRESS_LINE_INDEX).split(":")[1];
		int serverPort = Integer.parseInt(lines.get(SERVER_PORT_LINE_INDEX).split(":")[1]);

		manager.sendRequest(serverIp, serverPort);
	}
}
