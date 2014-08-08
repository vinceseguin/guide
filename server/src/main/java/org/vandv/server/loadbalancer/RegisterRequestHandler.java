package org.vandv.server.loadbalancer;

import org.apache.commons.io.IOUtils;
import org.vandv.communication.IRequestHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by vinceseguin on 06/08/14.
 */
public class RegisterRequestHandler implements IRequestHandler {

    private String ip;
    private int port;

    public RegisterRequestHandler(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    @Override
    public void handleRequest(Socket socket) throws IOException {

        StringBuilder sb = new StringBuilder();
        sb.append("GUIDE_SERVER_LOADBALANCER\r\n");
        sb.append("REQUEST-ACTION:REGISTER\r\n");
        sb.append("REQUEST-TYPE:VISUAL_RECOGNITION\r\n");
        sb.append("SERVER-IP:" + this.ip + "\r\n");
        sb.append("SERVER-PORT:" + this.port);

        OutputStream out = socket.getOutputStream();

        IOUtils.write(sb.toString(), out);
    }
}
