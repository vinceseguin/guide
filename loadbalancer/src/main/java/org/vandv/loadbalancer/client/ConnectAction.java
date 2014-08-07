package org.vandv.loadbalancer.client;

import org.apache.commons.io.IOUtils;
import org.vandv.loadbalancer.server.Server;

import java.io.IOException;
import java.io.OutputStream;

/**
 * A client's connect action.
 * 
 * Created by vinceseguin on 30/07/14.
 */
class ConnectAction extends AbstractClientAction {

	@Override
	public void execute(OutputStream out, Server server) {
        StringBuilder sb = new StringBuilder();
        sb.append("GUIDE_LOADBALANCER_CLIENT\r\n");
        sb.append("SERVER-IP:" + server.getAddress() + "\r\n");
        sb.append("SERVER-PORT:" + server.getPort());

        try {
            IOUtils.write(sb.toString(), out);
            out.flush();
        } catch (IOException exception) {
            //TODO
        }
	}
}
