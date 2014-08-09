package org.vandv.server.client.vision;

import org.apache.commons.io.IOUtils;
import org.apache.http.client.ClientProtocolException;
import org.opencv.core.Mat;
import org.vandv.common.google.MapsApiObjectFactory;
import org.vandv.server.client.IAction;
import org.vandv.common.vision.FeatureCalculator;
import org.vandv.common.vision.HistogramCalculator;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * This command is called when a client start a new destination
 * process. This class will go search the google street image for
 * the destination and extract the histogram and the feature points
 * for this image.
 *
 * Created by vinceseguin on 04/08/14.
 */
public class NewDestinationAction implements IAction {

    private VisualRecognitionManager manager;

    /**
     * Constructor
     */
    public NewDestinationAction() {
        this.manager = VisualRecognitionManager.getInstance();
    }

    /**
     * Execute the command.
     * @param out The OutputStream of the socket directing to the client.
     * @param lines The lines defined by the communication protocol.
     * @param data The data send by the client as defined in the protocol.
     * @throws java.io.IOException if an I/O error occurs
     */
    @Override
    public void execute(OutputStream out, List<String> lines, char[] data) throws ClientProtocolException, IOException {
        String address = new String(data);
        Mat image = MapsApiObjectFactory.getStreetViewImage(address);
        Mat histogram = (new HistogramCalculator(image)).calculateHistogram();
        Mat features = (new FeatureCalculator(image)).calculateFeaturePoints();
        long requestId = this.manager.registerDestination(new Destination(features,histogram));

        StringBuilder sb;
        sb = new StringBuilder();
        sb.append("GUIDE_SERVER_CLIENT_RESPONSE\r\n");
        sb.append("REQUEST_ID:").append(requestId).append("\r\n");
        sb.append("DATA_LENGTH:0\r\n");
        sb.append("PARAMS_LENGTH:0");

        IOUtils.write(sb.toString(), out);
    }
}
