package org.vandv.vision;

import org.apache.commons.io.IOUtils;
import org.opencv.core.Mat;
import org.vandv.communication.IAction;
import org.vandv.google.MapsApiObjectFactory;

import java.io.OutputStream;
import java.util.List;

/**
 * Created by vinceseguin on 04/08/14.
 */
public class NewDestinationAction implements IAction {

    private VisualRecognitionManager manager;

    public NewDestinationAction(VisualRecognitionManager manager) {
        this.manager = manager;
    }

    @Override
    public void execute(OutputStream out, List<String> lines, byte[] data) throws Exception {
        String address = new String(data);
        Mat image = MapsApiObjectFactory.getStreetViewImage(address);
        Mat histogram = (new HistogramCalculator(image)).calculate();
        Mat features = (new FeatureCalculator(image)).calculate();
        long requestId = this.manager.registerDestination(new Destination(address,image,features,histogram));

        StringBuilder sb = new StringBuilder();
        sb.append("GUIDE_SERVER_CLIENT_RESPONSE");
        sb.append("REQUEST_ID:" + requestId);
        sb.append("DATA_LENGTH:0");
        sb.append("PARAMS_LENGTH:");

        IOUtils.write(sb.toString(), out);
    }
}
