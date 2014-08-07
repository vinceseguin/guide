package org.vandv.loadbalancer.client;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.vandv.loadbalancer.IAction;

public class ClientRequestHandlerTest {

	@Test
    public void createActionTest() {
		ClientRequestHandler requestHandler = new ClientRequestHandler();
		
		try {
			IAction action = requestHandler.createAction("REQUEST_ACTION:CONNECT");
			Assert.assertTrue(action instanceof ConnectAction);
		} catch (Exception ex) {
		}
	}
	
	@Test
    public void createActionTest2() {
		ClientRequestHandler requestHandler = new ClientRequestHandler();
		
		try {
			IAction action = requestHandler.createAction("REQUEST_ACTION:NOT_VALID");
			Assert.fail();
		} catch (Exception ex) {
		}
	}
	
	@Test
	public void handleRequestTest() {
		Socket socket = mock(Socket.class);
		OutputStream out = mock(BufferedOutputStream.class);
		
		StringBuilder sb = new StringBuilder();
        sb.append("GUIDE_CLIENT_LOADBALANCER\r\n");
        sb.append("REQUEST-ACTION:CONNECT\r\n");
        sb.append("REQUEST-TYPE:VISUAL_RECOGNITION");
        
    	try {
			when(socket.getInputStream()).thenReturn(IOUtils.toInputStream(sb));
			when(socket.getOutputStream()).thenReturn(out);
		} catch (IOException e) {
			Assert.fail();
		}
    	
    	ClientRequestHandler requestHandler = new ClientRequestHandler();
    	try {
			requestHandler.handleRequest(socket);
		} catch (IOException e) {
			Assert.fail();
		}
	}
}
