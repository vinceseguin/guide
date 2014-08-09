package org.vandv.client.server;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.opencv.core.Mat;
import org.vandv.common.communication.ClientSocketManager;
import org.vandv.common.communication.IRequestHandler;
import org.vandv.common.exceptions.ProtocolFormatException;

/**
 * Manages the client's new destination requests to the server.
 * 
 * Created by vincegentilcore on 08/08/2014.
 */
public class ClientVisualRecognitionRequestManager implements
		IClientRequestManager {

    private static final Logger logger = LogManager.getLogger(ClientVisualRecognitionRequestManager.class.getName());

	private String destination;
	
	private boolean isFinished;

    private boolean hasMoreImage = true;

	private Queue<Mat> imagesQueue = new LinkedList<Mat>();

	/**
	 * Constructor
	 * @param destination destination's address
	 * @throws IOException
	 * @throws ProtocolFormatException
	 */
	public ClientVisualRecognitionRequestManager(String destination)
			throws IOException, ProtocolFormatException {
		this.destination = destination;
	}

	@Override
	public void sendRequest(String serverIp, int serverPort)
			throws IOException, ProtocolFormatException {
		IRequestHandler handler = new NewDestinationRequestHandler(destination,
				this, serverIp, serverPort);

		ClientSocketManager clientSocketManager = new ClientSocketManager(
				handler);

		clientSocketManager.start(serverIp, serverPort);
	}

	/**
	 * Send a visual recognition request to the server.
	 * @param serverIp server's ip address
	 * @param serverPort server's port
	 * @param requestId request id
	 * @throws IOException
	 * @throws ProtocolFormatException
	 */
	public void sendVisualRecognitionRequest(String serverIp, int serverPort,
			int requestId) throws IOException, ProtocolFormatException {
        while (hasMoreImage) {
            synchronized (imagesQueue) {
                while (imagesQueue.isEmpty()) {
                    try {
                        imagesQueue.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            IRequestHandler handler = new VisualRecognitionRequestHandler(
                    imagesQueue.poll(), requestId, this);

            ClientSocketManager clientSocketManager = new ClientSocketManager(
                    handler);

            clientSocketManager.start(serverIp, serverPort);
        }
	}

	/**
	 * Push a new image on the queue
	 * @param image new image to push on the queue
	 */
	public void pushImage(Mat image) {
		synchronized (imagesQueue) {
			imagesQueue.add(image);
			imagesQueue.notify();
		}
	}
	
	/**
	 * 
	 * @param isFinished
	 */
	public void setFinished(boolean isFinished) {
        logger.trace("FINISHED: " + isFinished);
		this.isFinished = isFinished;
	}

    public void setHasMoreImage(boolean hasMoreImage) {

        logger.trace("NO MORE IMAGE");

        this.hasMoreImage = hasMoreImage;
    }
	/**
	 * 
	 * @return
	 */
	public boolean hasFinished() {
		return isFinished;
	}
}
