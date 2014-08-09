package org.vandv.client;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;
import org.vandv.client.loadbalancer.ClientRegistrationManager;
import org.vandv.client.server.ClientVisualRecognitionRequestManager;
import org.vandv.common.exceptions.ProtocolFormatException;

/**
 * Created by vgentilcore on 08/08/2014.
 */
public class Main {

    private static final Logger logger = LogManager.getLogger(Main.class.getName());

	private static final String REQUEST_TYPE = "VISUAL_RECOGNITION";
	private static final String LOAD_BALANCER_IP = "10.196.122.21";
	private static final int LOAD_BALANCER_PORT = 5050;

	private static final int MIN_IMAGE_ID = 1;
	private static final int MAX_IMAGE_ID = 39;
	
	private static final String DESTINATION = "Ecole de technologie superieure";

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

	public static void main(String[] args) {
		try {
			ClientRegistrationManager clientRegistrationManager = new ClientRegistrationManager(
					LOAD_BALANCER_IP, LOAD_BALANCER_PORT, REQUEST_TYPE);

			final ClientVisualRecognitionRequestManager clientNewDestinationRequestManager = new ClientVisualRecognitionRequestManager(
					DESTINATION);


            new Thread(new Runnable() {
                @Override
                public void run() {

                    int i = MIN_IMAGE_ID;

                    while (true) {
                        try {
                            Thread.sleep(1000);

                            if (i > MAX_IMAGE_ID || clientNewDestinationRequestManager.hasFinished()) {
                                break;
                            }

                            logger.trace("SENDING: " + i);

                            Mat image = Highgui.imread(Object.class.getClass()
                                    .getResource(String.format("/images/%d.jpg", i++))
                                    .getPath());

                            Mat resizedImage = new Mat();
                            Size sz = new Size(500,281);
                            Imgproc.resize(image, resizedImage, sz);

                            clientNewDestinationRequestManager.pushImage(resizedImage);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }

                    clientNewDestinationRequestManager.setHasMoreImage(false);
                }
            }).start();

            clientRegistrationManager
                    .registerClient(clientNewDestinationRequestManager);

		} catch (IOException | ProtocolFormatException e) {
			e.printStackTrace();
		}
	}
}
