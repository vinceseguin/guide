package org.vandv.client.loadbalancer;

import java.io.IOException;

import org.opencv.core.Core;
import org.vandv.common.communication.ClientSocketManager;
import org.vandv.common.communication.IRequestHandler;
import org.vandv.common.exceptions.ProtocolFormatException;
import org.vandv.client.server.IClientRequestManager;

/**
 * Manages the registration of the client to the load balancer.
 * 
 * Created by vgentilcore on 08/08/2014.
 */
public class ClientRegistrationManager {

	private String loadBalancerIp;
	private int loadBalancerPort;
	private String requestType;

	/**
	 * Constructor
	 * @param loadBalancerIp load balancer's ip address
	 * @param loadBalancerPort load balancer's port
	 * @throws IOException
	 * @throws ProtocolFormatException
	 */
	public ClientRegistrationManager(String loadBalancerIp, int loadBalancerPort, String requestType)
			throws IOException, ProtocolFormatException {
		this.loadBalancerIp = loadBalancerIp;
		this.loadBalancerPort = loadBalancerPort;
		this.requestType = requestType;
	}

	/**
	 * Registers the client to the load balancer.
	 * @throws IOException
	 * @throws ProtocolFormatException
	 */
	public void registerClient(IClientRequestManager manager) throws IOException, ProtocolFormatException {
		IRequestHandler handler = new RegisterRequestHandler(requestType, manager);

		ClientSocketManager clientSocketManager = new ClientSocketManager(
				handler);

		clientSocketManager.start(loadBalancerIp, loadBalancerPort);
	}
}
