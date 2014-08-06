package org.vandv.loadbalancer.exceptions;

/**
 * Exception thrown when there's an error in the protocol format.
 */
public class ProtocolFormatException extends Exception {

	public ProtocolFormatException() {
		super("Error in protocol format.");
	}
}
