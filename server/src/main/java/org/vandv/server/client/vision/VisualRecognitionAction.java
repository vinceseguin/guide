package org.vandv.server.client.vision;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.vandv.server.client.IAction;
import org.vandv.common.vision.HistogramCalculator;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * This command determining if two images are from the same
 * scene.
 *
 * Created by vinceseguin on 03/08/14.
 */
public class VisualRecognitionAction implements IAction {

    private static final int REQUEST_TYPE_LINE_INDEX = 2;
    private static final int REQUEST_ID_LINE_INDEX = 3;
    private static final int PARAMS_LENGTH_LINE_INDEX = 5;

    private VisualRecognitionManager visualRecognitionManager;

    private static final Logger logger = LogManager.getLogger(VisualRecognitionAction.class.getName());

    /**
     *Constructor
     */
    public VisualRecognitionAction() {
        this.visualRecognitionManager = VisualRecognitionManager.getInstance();
    }

    /**
     * Execute the command.
     * @param out The OutputStream of the socket directing to the client.
     * @param lines The lines defined by the communication protocol.
     * @param data The data send by the client as defined in the protocol.
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void execute(OutputStream out, List<String> lines, char[] data) throws IOException {
        String requestType = lines.get(REQUEST_TYPE_LINE_INDEX).split(":")[1];
        String[] paramsLength = lines.get(PARAMS_LENGTH_LINE_INDEX).split(":")[1].split(",");
        long requestId = Long.parseLong(lines.get(REQUEST_ID_LINE_INDEX).split(":")[1]);

        ImageRecognition imageRecognition = new NullRecognition(null, null);

        int offset = 0;
        int nextParamLength = Integer.parseInt(paramsLength[0]);
        
        String str = new String(data);
        String[] strs = str.replace("[", "").replace("]", "").split(", ");

        if (requestType.contains("HISTOGRAM")) {
        	float[] values = new float[nextParamLength];
            for (int i = offset; i < nextParamLength; i++) {
            	values[i] = Float.parseFloat(strs[i]);
            }
        	
            Mat histogram = createMatFloat(values);
            imageRecognition.setSuccessor(new HistogramRecognition(histogram,
                    visualRecognitionManager.getHistogram(requestId)));
            offset = nextParamLength;
            nextParamLength = Integer.parseInt(paramsLength[1]);
        }

        if (requestType.contains("FEATURE")) {
        	byte[] values = new byte[nextParamLength];
            for (int i = 0, j = offset; i < nextParamLength; i++, j++) {
            	values[i] = Byte.parseByte(strs[j]);
            }
        	
            Mat features = createMatByte(values);
            imageRecognition.setSuccessor(new FeatureRecognition(features,
                    visualRecognitionManager.getFeatures(requestId)));
        }

        imageRecognition.setSuccessor(new NullRecognition(null, null));

        sendResponseToClient(out, requestId, imageRecognition.handleRequest());
    }
    
    /**
     * Create a matrix with the array of data sent by the client.
     * @param data The data sent by the client.
     * @return A matrix containing the data.
     */
    private Mat createMatFloat(float[] data) {
        Mat mat = new Mat(HistogramCalculator.H_BINS, HistogramCalculator.S_BINS, CvType.CV_32F);

        for (int i=0; i<HistogramCalculator.H_BINS; i++) {

            float[] toPutInMatrix = new float[HistogramCalculator.S_BINS];

            System.arraycopy(data, i * HistogramCalculator.S_BINS, toPutInMatrix, 0, HistogramCalculator.S_BINS);
            mat.put(i, 0, toPutInMatrix);

        }

        return mat;
    }
    
    /**
     * Create a matrix with the array of data sent by the client.
     * @param data The data sent by the client.
     * @return A matrix containing the data.
     */
    private Mat createMatByte(byte[] data) {
        Mat mat = new Mat(data.length / 32, 32, CvType.CV_8U);

        for (int i=0; i<data.length / 32; i++) {

            byte[] toPutInMatrix = new byte[32];

            System.arraycopy(data, i * 32, toPutInMatrix, 0, 32);
            mat.put(i, 0, toPutInMatrix);

        }

        return mat;
    }

    /**
     * Send the response to the client who sent the request.
     * @param out The OutputStream of the socket directing to the client.
     * @param requestId The request id sent by the client.
     * @param recognized Whether the scene is detected in the two images.
     * @throws IOException if an I/O error occurs
     */
    private void sendResponseToClient(OutputStream out, long requestId, boolean recognized) throws IOException {
        StringBuilder sb;
        sb = new StringBuilder();
        sb.append("GUIDE_SERVER_CLIENT_RESPONSE\r\n");
        sb.append("REQUEST_ID:").append(requestId).append("\r\n");
        sb.append("DATA_LENGTH:1" + "\r\n").append("PARAMS_LENGTH:1" + "\r\n");

        byte byteRecognized = recognized ? (byte)1 : (byte)0 ;

        sb.append(byteRecognized);

        IOUtils.write(sb.toString().getBytes(), out);

        logger.trace("RECOGNIZED: " + recognized);

        out.flush();
    }
}
