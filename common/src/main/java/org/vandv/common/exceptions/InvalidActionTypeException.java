package org.vandv.common.exceptions;

public class InvalidActionTypeException extends Exception {

	public InvalidActionTypeException() {
		super("The action type is not valid, please refer to the communication protocol.");
	}
}
