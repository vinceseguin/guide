package org.vandv.loadbalancer.server;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.vandv.loadbalancer.IAction;
import org.vandv.loadbalancer.LoadBalancerRequestHandler;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class ServerRequestHandlerTest {

    @Test
    public void createActionTest() {
        LoadBalancerRequestHandler requestHandler = new LoadBalancerRequestHandler();
        List<String> lines = new ArrayList<String>();

        lines.add("GUIDE_SERVER_LOADBALANCER");
        lines.add("REQUEST-ACTION:UPDATE");
        lines.add("REQUEST-TYPE:VISUAL_RECOGNITION");
        lines.add("SERVER-IP:192.168.0.1");
        lines.add("SERVER-PORT:5050");
        lines.add("SERVER-NUMBER-OF-REQUEST:10");

        try {
            IAction action = requestHandler.createAction(lines.get(1));
            Assert.assertTrue(action instanceof UpdateAction);
        } catch (Exception ex) {
        }
    }

    @Test
    public void createActionTest2() {
        LoadBalancerRequestHandler requestHandler = new LoadBalancerRequestHandler();
        List<String> lines = new ArrayList<String>();

        lines.add("GUIDE_SERVER_LOADBALANCER");
        lines.add("REQUEST-ACTION:REGISTER");
        lines.add("REQUEST-TYPE:VISUAL_RECOGNITION");
        lines.add("SERVER-IP:192.168.0.1");
        lines.add("SERVER-PORT:5050");

        try {
            IAction action = requestHandler.createAction(lines.get(1));
            Assert.assertTrue(action instanceof RegisterAction);
        } catch (Exception ex) {
        }
    }

    @Test
    public void createActionTest3() {
        LoadBalancerRequestHandler requestHandler = new LoadBalancerRequestHandler();
        List<String> lines = new ArrayList<String>();

        lines.add("GUIDE_SERVER_LOADBALANCER");
        lines.add("REQUEST-ACTION:NOT_VALID");
        lines.add("REQUEST-TYPE:VISUAL_RECOGNITION");
        lines.add("SERVER-IP:192.168.0.1");
        lines.add("SERVER-PORT:5050");

        try {
            IAction action = requestHandler.createAction(lines.get(1));
            Assert.fail();
        } catch (Exception ex) {

        }
    }
    
    @Test
    public void handleRequestTest() {
    	Socket socket = mock(Socket.class);
    	
        StringBuilder sb = new StringBuilder();
        sb.append("GUIDE_SERVER_LOADBALANCER\r\n");
        sb.append("REQUEST-ACTION:REGISTER\r\n");
        sb.append("REQUEST-TYPE:VISUAL_RECOGNITION\r\n");
        sb.append("SERVER-IP:192.168.0.1\r\n");
        sb.append("SERVER-PORT:5050");
    	
    	try {
			when(socket.getInputStream()).thenReturn(IOUtils.toInputStream(sb));
		} catch (IOException e) {
			Assert.fail();
		}

        LoadBalancerRequestHandler requestHandler = new LoadBalancerRequestHandler();
    	try {
			requestHandler.handleRequest(socket);
		} catch (Exception e) {
			Assert.fail();
		}
    }
}