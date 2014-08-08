package org.vandv.client.loadbalancer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.vandv.common.communication.IRequestHandler;
import org.vandv.common.exceptions.ProtocolFormatException;

/**
 * Handles registration requests from clients to loadbalancer.
 * 
 * Created by vgentilcore on 08/08/2014.
 */
public class RegisterRequestHandler implements IRequestHandler {

    /**
     * Constructor
     */
    public RegisterRequestHandler() {
    }

	@Override
	public void handleRequest(Socket socket) throws IOException, ProtocolFormatException {
        StringBuilder sb = new StringBuilder();
        sb.append("GUIDE_CLIENT_LOADBALANCER\r\n");
        sb.append("REQUEST-ACTION:CONNECT\r\n");
        sb.append("REQUEST-TYPE:VISUAL_RECOGNITION");

        OutputStream out = socket.getOutputStream();
        IOUtils.write(sb.toString(), out);
        
        InputStream is = socket.getInputStream();
        List<String> lines = IOUtils.readLines(is, "UTF-8");
        
        
	}
}
