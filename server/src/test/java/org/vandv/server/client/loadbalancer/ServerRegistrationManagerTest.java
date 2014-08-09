package org.vandv.server.client.loadbalancer;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.sun.corba.se.spi.activation.ServerManager;
import org.junit.Assert;
import org.junit.Test;
import org.vandv.common.communication.IRequestHandler;
import org.vandv.common.exceptions.ProtocolFormatException;
import org.vandv.server.loadbalancer.RegisterRequestHandler;
import org.vandv.server.loadbalancer.ServerRegistrationManager;
import org.vandv.server.loadbalancer.UpdateRequestHandler;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by vinceseguin on 08/08/14.
 */
public class ServerRegistrationManagerTest {

    @Test
    public void testRegisterRequestHandler() {
        Socket socket = mock(Socket.class);
        OutputStream out = mock(BufferedOutputStream.class);

        try {
            when(socket.getOutputStream()).thenReturn(out);
        } catch (IOException e) {
            Assert.fail();
        }

        IRequestHandler handler = new RegisterRequestHandler("FE80:0000:0000:0000:0202:B3FF:FE1E:8329", 5051);

        try {
            handler.handleRequest(socket);
        } catch (IOException | ProtocolFormatException ex) {
            Assert.fail();
        }
    }

    @Test
    public void testUpdateRequestHandler() {
        Socket socket = mock(Socket.class);
        OutputStream out = mock(BufferedOutputStream.class);

        try {
            when(socket.getOutputStream()).thenReturn(out);
        } catch (IOException e) {
            Assert.fail();
        }

        IRequestHandler handler = new UpdateRequestHandler("FE80:0000:0000:0000:0202:B3FF:FE1E:8329", 5051, 69);

        try {
            handler.handleRequest(socket);
        } catch (IOException | ProtocolFormatException ex) {
            Assert.fail();
        }
    }

    /*@Test
    public void testRegistrationServerManager() {
        try {
            ServerRegistrationManager sm = new ServerRegistrationManager("FE80:0000:0000:0000:0202:B3FF:FE1E:8329",
                    5051, "FE80:0000:0000:0000:0202:B3FF:FE1E:8329", 5052);
        } catch (Exception e) {

        }
    }*/
}
