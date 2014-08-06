package org.vandv.communication;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by vinceseguin on 29/07/14.
 */
public interface IRequestHandler {

    public void handleRequest(Socket socket) throws IOException;
}
