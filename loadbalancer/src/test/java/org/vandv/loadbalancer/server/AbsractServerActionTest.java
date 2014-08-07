package org.vandv.loadbalancer.server;

import org.junit.Assert;
import org.junit.Test;
import org.vandv.loadbalancer.IAction;

import java.util.ArrayList;
import java.util.List;

public class AbsractServerActionTest {

    @Test
    public void testNewAction() {
        IAction action = new RegisterAction();
        List<String> lines = new ArrayList<String>();

        lines.add("GUIDE_SERVER_LOADBALANCER");
        lines.add("REQUEST-ACTION:REGISTER");
        lines.add("REQUEST-TYPE:VISUAL_RECOGNITION");
        lines.add("SERVER-IP:192.168.0.1");
        lines.add("SERVER-PORT:5050");
        try {
            action.execute(null, lines);
        } catch (Exception ex) {
            Assert.fail();
        }
    }
    
    @Test
    public void testNewAction2() {
        IAction action = new RegisterAction();
        List<String> lines = new ArrayList<String>();

        lines.add("GUIDE_SERVER_LOADBALANCER");
        lines.add("REQUEST-ACTION:REGISTER");
        lines.add("REQUEST-TYPE:NOT_VALID");
        lines.add("SERVER-IP:192.168.0.1");
        lines.add("SERVER-PORT:5050");
        try {
            action.execute(null, lines);
            Assert.fail();
        } catch (Exception ex) {
        }
    }

    @Test
    public void testUpdateExecute() {
        IAction action = new UpdateAction();
        List<String> lines = new ArrayList<String>();

        lines.add("GUIDE_SERVER_LOADBALANCER");
        lines.add("REQUEST-ACTION:UPDATE");
        lines.add("REQUEST-TYPE:VISUAL_RECOGNITION");
        lines.add("SERVER-IP:192.168.0.1");

        try {
            action.execute(null, lines);
            Assert.fail();
        } catch (Exception ex) {
        }
    }

    @Test
    public void testUpdateExecute2() {
        IAction action = new RegisterAction();
        List<String> lines = new ArrayList<String>();

        lines.add("GUIDE_SERVER_LOADBALANCER");
        lines.add("REQUEST-ACTION:REGISTER");
        lines.add("REQUEST-TYPE:VISUAL_RECOGNITION");
        lines.add("SERVER-IP:192.168.0.1");
        lines.add("SERVER-PORT:5050");

        try {
            action.execute(null, lines);
        } catch (Exception ex) {
            Assert.fail();
        }

        action = new UpdateAction();
        lines = new ArrayList<String>();

        lines.add("GUIDE_SERVER_LOADBALANCER");
        lines.add("REQUEST-ACTION:UPDATE");
        lines.add("REQUEST-TYPE:VISUAL_RECOGNITION");
        lines.add("SERVER-IP:192.168.0.1");
        lines.add("SERVER-PORT:5050");
        lines.add("SERVER-NUMBER-OF-REQUEST:10");
        lines.add("SERVER-CPU-LOAD:40");

        try {
            action.execute(null, lines);
        } catch (Exception ex) {
            Assert.fail();
        }
    }

    @Test
    public void testInvalidExecute() {
        IAction action = new UpdateAction();
        List<String> lines = new ArrayList<String>();

        lines.add("GUIDE_SERVER_LOADBALANCER");
        lines.add("REQUEST-ACTION:UPDATE");
        lines.add("REQUEST-TYPE:VISUAL_RECOGNITION");
        lines.add("SERVER-IP:192.168.0.1");
        lines.add("SERVER-PORT:5050");
        lines.add("SERVER-NUMBER-OF-REQUEST:10");

        try {
            action.execute(null, lines);
            Assert.fail();
        } catch (Exception ex) {
        }
    }
}