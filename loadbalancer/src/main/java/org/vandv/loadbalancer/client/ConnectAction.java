package org.vandv.loadbalancer.client;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.vandv.loadbalancer.server.Server;

import java.io.IOException;
import java.io.OutputStream;

/**
 * A client's connect action.
 * 
 * Created by vinceseguin on 30/07/14.
 */
public class ConnectAction extends AbstractClientAction {

    private static final Logger logger = LogManager.getLogger(ConnectAction.class.getName());

	@Override
	public void execute(OutputStream out, Server server) {

        logger.trace(String.format("SENDING SERVER : %s:%d", server.getAddress(), server.getPort()));

        StringBuilder sb = new StringBuilder();
        sb.append("GUIDE_LOADBALANCER_CLIENT\r\n");
        sb.append("SERVER-IP:" + server.getAddress() + "\r\n");
        sb.append("SERVER-PORT:" + server.getPort());

        try {
            IOUtils.write(sb.toString(), out);

            out.flush();
        } catch (IOException exception) {
            logger.error(exception);
        }
	}
}
