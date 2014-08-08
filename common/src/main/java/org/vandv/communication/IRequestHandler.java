package org.vandv.communication;

import java.io.IOException;
import java.net.Socket;

import org.vandv.exceptions.ProtocolFormatException;

/**
 * Request handler's interface.
 * 
 * Created by vinceseguin on 29/07/14.
 */
public interface IRequestHandler {

	/**
	 * Handles a request.
	 * 
	 * @param socket socket used to communicate with the client.
	 * @throws IOException
	 * @throws ProtocolFormatException 
	 */
    public void handleRequest(Socket socket) throws IOException, ProtocolFormatException;
}
