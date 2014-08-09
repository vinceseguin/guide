package org.vandv.client.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.vandv.common.communication.IRequestHandler;
import org.vandv.common.exceptions.ProtocolFormatException;

/**
 * Handles a client's new destination request Created by vgentilcore on
 * 08/08/2014.
 */
public class NewDestinationRequestHandler implements IRequestHandler {

	private static final int REQUEST_ID_LINE_INDEX = 1;

	private String destination;

	private ClientVisualRecognitionRequestManager manager;

	private String serverIp;

	private int serverPort;

	/**
	 * Constructor
	 * 
	 * @param destination
	 *            the new destination
	 */
	public NewDestinationRequestHandler(String destination,
			ClientVisualRecognitionRequestManager manager, String serverIp,
			int serverPort) {
		this.destination = destination;
		this.manager = manager;
		this.serverIp = serverIp;
		this.serverPort = serverPort;
	}

	@Override
	public void handleRequest(Socket socket) throws IOException,
			ProtocolFormatException {
		StringBuilder sb = new StringBuilder();
		sb.append("GUIDE_SERVER_CLIENT_REQUEST\r\n");
		sb.append("REQUEST-ACTION:VISUAL_RECOGNITION\r\n");
		sb.append("REQUEST-TYPE:REGISTER_DESTINATION\r\n");
		sb.append("REQUEST_ID:\r\n");
		sb.append(String.format("DATA_LENGTH:%d\r\n", destination.length()));
		sb.append(String.format("PARAMS_LENGTH:%d\r\n", destination.length()));
		sb.append(destination);

		OutputStream out = socket.getOutputStream();
		IOUtils.write(sb.toString(), out);
        out.flush();
        socket.shutdownOutput();

		InputStream is = socket.getInputStream();
		List<String> lines = IOUtils.readLines(is, "UTF-8");

		int requestId = Integer.parseInt(lines.get(REQUEST_ID_LINE_INDEX)
				.split(":")[1]);

		manager.sendVisualRecognitionRequest(serverIp, serverPort, requestId);

	}
}
