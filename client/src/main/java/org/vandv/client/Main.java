package org.vandv.client;

import java.io.IOException;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;
import org.vandv.client.loadbalancer.ClientRegistrationManager;
import org.vandv.client.server.ClientVisualRecognitionRequestManager;
import org.vandv.common.exceptions.ProtocolFormatException;

/**
 * Created by vgentilcore on 08/08/2014.
 */
public class Main {

	private static final String REQUEST_TYPE = "VISUAL_RECOGNITION";
	private static final String LOAD_BALANCER_IP = "10.196.122.21";
	private static final int LOAD_BALANCER_PORT = 5051;

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

			ClientVisualRecognitionRequestManager clientNewDestinationRequestManager = new ClientVisualRecognitionRequestManager(
					DESTINATION);

			clientRegistrationManager
					.registerClient(clientNewDestinationRequestManager);

			int i = MIN_IMAGE_ID;
			
			while (true) {

				try {
					Thread.sleep(1000);

					if (i > MAX_IMAGE_ID || clientNewDestinationRequestManager.hasFinished()) {
						break;
					}

					Mat image = Highgui.imread(Object.class.getClass()
							.getResource(String.format("/images/%d.jpg", i++))
							.getPath());

					clientNewDestinationRequestManager.pushImage(image);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			
			if (clientNewDestinationRequestManager.hasFinished()) {
				System.out.println("SUCCESS!!!");
			}

		} catch (IOException | ProtocolFormatException e) {
			e.printStackTrace();
		}
	}
}
