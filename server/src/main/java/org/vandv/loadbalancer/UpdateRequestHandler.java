package org.vandv.loadbalancer;

import com.sun.management.OperatingSystemMXBean;
import org.apache.commons.io.IOUtils;
import org.vandv.communication.IRequestHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.management.ManagementFactory;
import java.net.Socket;

/**
 * Created by vinceseguin on 06/08/14.
 */
public class UpdateRequestHandler implements IRequestHandler {

    private String ip;
    private int port;
    private int numberOfRequest;

    public UpdateRequestHandler(String ip, int port, int numberOfRequest) {
        this.ip = ip;
        this.port = port;
        this.numberOfRequest = numberOfRequest;
    }

    @Override
    public void handleRequest(Socket socket) throws IOException {

        OperatingSystemMXBean operatingSystemMXBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

        StringBuilder sb = new StringBuilder();
        sb.append("GUIDE_SERVER_LOADBALANCER\r\n");
        sb.append("REQUEST-ACTION:UPDATE\r\n");
        sb.append("REQUEST-TYPE:VISUAL_RECOGNITION\r\n");
        sb.append("SERVER-IP:" + this.ip + "\r\n");
        sb.append("SERVER-PORT:" + this.port + "\r\n");
        sb.append("SERVER-NUMBER-OF-REQUEST" + numberOfRequest + "\r\n");
        sb.append("SERVER-CPU-LOAD" + operatingSystemMXBean.getSystemCpuLoad());

        OutputStream out = socket.getOutputStream();

        IOUtils.write(sb.toString(), out);
    }
}
