package org.vandv.client.loadbalancer;

import java.io.IOException;

import org.vandv.common.communication.ClientSocketManager;
import org.vandv.common.communication.IRequestHandler;
import org.vandv.common.exceptions.ProtocolFormatException;

/**
 * Manages the registration of the client to the load balancer.
 * 
 * Created by vgentilcore on 08/08/2014.
 */
public class ClientRegistrationManager {

	private String loadBalancerIp;
	private int loadBalancerPort;

	/**
	 * Constructor
	 * @param loadBalancerIp load balancer's ip address
	 * @param loadBalancerPort load balancer's port
	 * @throws IOException
	 * @throws ProtocolFormatException
	 */
	public ClientRegistrationManager(String loadBalancerIp, int loadBalancerPort)
			throws IOException, ProtocolFormatException {
		this.loadBalancerIp = loadBalancerIp;
		this.loadBalancerPort = loadBalancerPort;

		registerClient();
	}

	/**
	 * Registers the client to the load balancer.
	 * @throws IOException
	 * @throws ProtocolFormatException
	 */
	private void registerClient() throws IOException, ProtocolFormatException {
		IRequestHandler handler = new RegisterRequestHandler();

		ClientSocketManager clientSocketManager = new ClientSocketManager(
				handler);

		clientSocketManager.start(loadBalancerIp, loadBalancerPort);
	}
}
