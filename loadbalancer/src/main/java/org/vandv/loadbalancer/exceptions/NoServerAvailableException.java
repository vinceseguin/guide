package org.vandv.loadbalancer.exceptions;

/**
 * Exception thrown when there's no available server to handle
 * a request.
 * 
 * Created by vgentilcore on 06/08/14.
 */
public class NoServerAvailableException extends Exception {

	public NoServerAvailableException() {
		super("No server is available to handle the desired request.");
	}
}
