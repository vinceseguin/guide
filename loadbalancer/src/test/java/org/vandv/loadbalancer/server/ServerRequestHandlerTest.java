package org.vandv.loadbalancer.server;

import org.junit.Assert;
import org.junit.Test;
import org.vandv.loadbalancer.IAction;
import org.vandv.loadbalancer.ServerManager;

import java.util.ArrayList;
import java.util.List;

public class ServerRequestHandlerTest {

    @Test
    public void createActionTest() {
        ServerManager serverManager = new ServerManager();
        ServerRequestHandler requestHandler = new ServerRequestHandler(serverManager);
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
        ServerManager serverManager = new ServerManager();
        ServerRequestHandler requestHandler = new ServerRequestHandler(serverManager);
        List<String> lines = new ArrayList<String>();

        lines.add("GUIDE_SERVER_LOADBALANCER");
        lines.add("REQUEST-ACTION:NEW");
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
        ServerManager serverManager = new ServerManager();
        ServerRequestHandler requestHandler = new ServerRequestHandler(serverManager);
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
}