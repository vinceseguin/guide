package org.vandv.vision;

import org.apache.commons.io.IOUtils;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.highgui.Highgui;
import org.vandv.communication.IAction;

import java.io.OutputStream;
import java.util.List;

/**
 * Created by vinceseguin on 03/08/14.
 */
public class VisualRecognitionAction implements IAction {

    private static final int REQUEST_TYPE_LINE_INDEX = 2;
    private static final int REQUEST_ID_LINE_INDEX = 3;
    private static final int PARAMS_LENGTH_LINE_INDEX = 5;

    private VisualRecognitionManager visualRecognitionManager;

    public VisualRecognitionAction(VisualRecognitionManager visualRecognitionManager) {
        this.visualRecognitionManager = visualRecognitionManager;
    }

    @Override
    public void execute(OutputStream out, List<String> lines, byte[] data) throws Exception {
        String requestType = lines.get(REQUEST_TYPE_LINE_INDEX).split(":")[1];
        String[] paramsLength = lines.get(PARAMS_LENGTH_LINE_INDEX).split(":")[1].split(",");
        long requestId = Long.parseLong(lines.get(REQUEST_ID_LINE_INDEX).split(":")[1]);

        ImageRecognition imageRecognition = new NullRecognition(null, null);

        int offset = 0;
        int nextParamLength = Integer.parseInt(paramsLength[0]);
        if (requestType.contains("HISTOGRAM")) {
            byte[] histogramArr = new byte[nextParamLength];
            offset = nextParamLength;

            for (int i=0; i<offset; i++) {
                histogramArr[i] = data[i];
            }

            Mat histogram = matFromByte(histogramArr);

            nextParamLength = Integer.parseInt(paramsLength[1]);

            imageRecognition.setSuccessor(new HistogramRecognition(histogram, visualRecognitionManager.getHistogram(requestId)));
        }

        if (requestType.contains("FEATURE")) {
            byte[] featuresArr = new byte[nextParamLength];

            for (int i=0; i<nextParamLength; i++) {
                featuresArr[i] = data[i + offset];
            }

            Mat features = matFromByte(featuresArr);

            imageRecognition.setSuccessor(new FeatureRecognition(features, visualRecognitionManager.getFeatures(requestId)));
        }

        Boolean recognized = imageRecognition.handleRequest();

        StringBuilder sb = new StringBuilder();
        sb.append("GUIDE_SERVER_CLIENT_RESPONSE\r\n");
        sb.append("REQUEST_ID:" + requestId + "\r\n");
        sb.append("DATA_LENGTH:1" + "\r\n");
        sb.append("PARAMS_LENGTH:1" + "\r\n");

        byte[] byteRecognized = new byte[] { recognized ? (byte)1 : (byte)0 };

        IOUtils.write(sb.toString().getBytes(), out);
        IOUtils.write(byteRecognized, out);

        out.flush();
    }

    private Mat matFromByte(byte[] data) {
        MatOfByte imgMatByte = new MatOfByte();
        imgMatByte.fromArray(data);

        return Highgui.imdecode(imgMatByte, Highgui.CV_LOAD_IMAGE_UNCHANGED);
    }
}
