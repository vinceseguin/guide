package org.vandv.client.server;

import java.io.IOException;

import org.vandv.common.exceptions.ProtocolFormatException;

/**
 * Manages the client's requests to the server.
 * 
 * Created by vincegentilcore on 08/08/2014.
 */
public interface IClientRequestManager {
	
	/**
	 * Sends the request to the server
	 * @throws ProtocolFormatException 
	 * @throws IOException 
	 */
	public void sendRequest(String serverIp, int serverPort) throws IOException, ProtocolFormatException;
}
