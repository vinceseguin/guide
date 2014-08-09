package org.vandv.loadbalancer;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.vandv.common.communication.IRequestHandler;
import org.vandv.common.exceptions.InvalidActionTypeException;
import org.vandv.common.exceptions.ProtocolFormatException;
import org.vandv.loadbalancer.client.ConnectAction;
import org.vandv.loadbalancer.server.RegisterAction;
import org.vandv.loadbalancer.server.UpdateAction;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

/**
 * Created by vinceseguin on 09/08/14.
 */
public class LoadBalancerRequestHandler implements IRequestHandler {

    private static final String REGISTER_ACTION = "REGISTER";
    private static final String UPDATE_ACTION = "UPDATE";
    private static final String CONNECT_ACTION = "CONNECT";

    private static final Logger logger = LogManager.getLogger(LoadBalancerRequestHandler.class.getName());

    @Override
    public void handleRequest(Socket socket) throws IOException, ProtocolFormatException {
        List<String> lines = IOUtils.readLines(socket.getInputStream());

        try  {
            IAction action = createAction(lines.get(1));
            action.execute(socket.getOutputStream(), lines);
        } catch (Exception exception) {
            logger.error(exception);
        }
    }

    /**
     * Creates a new action to handle a request.
     *
     * @param requestActionLine the request action identifier.
     * @return the new action.
     * @throws Exception
     */
    public IAction createAction(String requestActionLine) throws Exception {
        if (requestActionLine.contains(REGISTER_ACTION)) {
            return new RegisterAction();
        } else if (requestActionLine.contains(UPDATE_ACTION)) {
            return new UpdateAction();
        } else if (requestActionLine.contains(CONNECT_ACTION)) {
            return new ConnectAction();
        } else {
            throw new InvalidActionTypeException();
        }
    }
}
