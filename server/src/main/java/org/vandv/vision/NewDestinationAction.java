package org.vandv.vision;

import org.apache.commons.io.IOUtils;
import org.opencv.core.Mat;
import org.vandv.communication.IAction;
import org.vandv.google.MapsApiObjectFactory;

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
    public void execute(OutputStream out, List<String> lines, byte[] data) throws IOException {
        String address = new String(data);
        Mat image = MapsApiObjectFactory.getStreetViewImage(address);
        Mat histogram = (new HistogramCalculator(image)).calculateHistogram();
        Mat features = (new FeatureCalculator(image)).calculateFeaturePoints();
        long requestId = this.manager.registerDestination(new Destination(features,histogram));

        StringBuilder sb;
        sb = new StringBuilder();
        sb.append("GUIDE_SERVER_CLIENT_RESPONSE");
        sb.append("REQUEST_ID:").append(requestId);
        sb.append("DATA_LENGTH:0");
        sb.append("PARAMS_LENGTH:");

        IOUtils.write(sb.toString(), out);
    }
}
