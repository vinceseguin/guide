package org.vandv.loadbalancer.client;

import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.vandv.loadbalancer.IAction;
import org.vandv.loadbalancer.ServerManager;
import org.vandv.loadbalancer.server.Server;
import org.vandv.loadbalancer.server.VisualRecognitionServer;

import static org.mockito.Mockito.*;

public class AbstractClientActionTest {

	@Test
	public void testConnectAction1() {
		OutputStream out = mock(BufferedOutputStream.class);
		
		ServerManager serverManager = ServerManager.getInstance();
		
        Server server = new VisualRecognitionServer(null);
        server.setAddress("192.168.0.1");
        server.setPort(5050);
        
        serverManager.register(server);
        
		IAction action = new ConnectAction();
        List<String> lines = new ArrayList<String>();

        lines.add("GUIDE_CLIENT_LOADBALANCER");
        lines.add("REQUEST-ACTION:REQUEST_SERVER");
        lines.add("REQUEST-TYPE:VISUAL_RECOGNITION");
        try {
            action.execute(out, lines);
        } catch (Exception ex) {
            Assert.fail();
        }
	}
	
	@Test
	public void testConnectAction2() {
		OutputStream out = mock(BufferedOutputStream.class);
        
		IAction action = new ConnectAction();
        List<String> lines = new ArrayList<String>();

        lines.add("GUIDE_CLIENT_LOADBALANCER");
        lines.add("REQUEST-ACTION:REQUEST_SERVER");
        lines.add("REQUEST-TYPE:NOT_VALID");
        try {
            action.execute(out, lines);
            Assert.fail();
        } catch (Exception ex) {
            
        }
	}
	
	@Test
	public void testConnectAction3() {
		OutputStream out = mock(BufferedOutputStream.class);
        
		try {
			Thread.sleep(ServerManager.FLUSH_TIMER_LIMIT * 1000);
		}
		catch (Exception ex) {
			Assert.fail();
		}
		
		IAction action = new ConnectAction();
        List<String> lines = new ArrayList<String>();

        lines.add("GUIDE_CLIENT_LOADBALANCER");
        lines.add("REQUEST-ACTION:REQUEST_SERVER");
        lines.add("REQUEST-TYPE:VISUAL_RECOGNITION");
        try {
            action.execute(out, lines);
            Assert.fail();
        } catch (Exception ex) {
            
        }
	}
}
