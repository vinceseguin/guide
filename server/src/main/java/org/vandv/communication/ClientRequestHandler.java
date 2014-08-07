package org.vandv.communication;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.vandv.vision.NewDestinationAction;
import org.vandv.vision.VisualRecognitionAction;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Handler for client's request
 *
 * Created by vinceseguin on 03/08/14.
 */
public class ClientRequestHandler implements IRequestHandler {

    private static final Logger logger = LogManager.getLogger(ClientRequestHandler.class.getName());

    private static final String VISUAL_RECOGNITION_ACTION = "VISUAL_RECOGNITION";
    private static final String REGISTER_DESTINATION_TYPE = "REGISTER_DESTINATION";
    private static final int REQUEST_ACTION_LINE_INDEX = 1;
    private static final int REQUEST_TYPE_LINE_INDEX = 2;
    private static final int DATA_LENGTH_LINE_INDEX = 3;

    /**
     * Handler the request of the client.
     * @param socket The socket of the client.
     * @throws IOException IOException if an I/O error occurs
     */
    @Override
    public void handleRequest(Socket socket) throws IOException {

        InputStream is = socket.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        List<String> lines = new ArrayList<String>();

        for (int i=0; i<6; i++) {
            lines.add(reader.readLine());
        }

        int dataLength = Integer.parseInt(lines.get(DATA_LENGTH_LINE_INDEX).split(":")[1]);

        byte data[] = new byte[dataLength];
        int numberOfByteRead = is.read(data);

        try  {
            if (numberOfByteRead == dataLength) {
                IAction action = createAction(lines);
                action.execute(socket.getOutputStream(), lines, data);
            }
        } catch (Exception exception) {
            logger.error(exception.getMessage());
        }
    }

    /**
     * Creates a new action to handle a request.
     * @param lines the lines sent by the client
     * @return the new action.
     * @throws Exception The message sent by the client does not respect the protocol.
     */
    protected IAction createAction(List<String> lines) throws Exception {
        String requestActionLine = lines.get(REQUEST_ACTION_LINE_INDEX);
        String requestTypeLine = lines.get(REQUEST_TYPE_LINE_INDEX);

        if (requestActionLine.contains(VISUAL_RECOGNITION_ACTION) && requestTypeLine.contains(REGISTER_DESTINATION_TYPE)) {
            return new NewDestinationAction();
        } else if (requestActionLine.contains(VISUAL_RECOGNITION_ACTION)) {
            return new VisualRecognitionAction();
        } else {
            //TODO: Throw ProtocolFormatException instead
            throw new Exception("The action type is not valid, please refer to the communication protocol.");
        }
    }
}
