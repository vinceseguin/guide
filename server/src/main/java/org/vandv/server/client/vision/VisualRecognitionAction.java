package org.vandv.server.client.vision;

import org.apache.commons.io.IOUtils;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfFloat;
import org.opencv.highgui.Highgui;
import org.vandv.server.client.IAction;

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

        sendResponseToClient(out, requestId, imageRecognition.handleRequest());
    }
    
    /**
     * Create a matrix with the array of data sent by the client.
     * @param data The data sent by the client.
     * @param length The length of byte of the matrix.
     * @param offset The offset in the data array to start from.
     * @return A matrix containing the data.
     */
    private Mat createMatFloat(float[] data) {
        MatOfFloat imgMat = new MatOfFloat();
        imgMat.fromArray(data);

        return Highgui.imdecode(imgMat, Highgui.CV_LOAD_IMAGE_UNCHANGED);
    }
    
    /**
     * Create a matrix with the array of data sent by the client.
     * @param data The data sent by the client.
     * @param length The length of byte of the matrix.
     * @param offset The offset in the data array to start from.
     * @return A matrix containing the data.
     */
    private Mat createMatByte(byte[] data) {
        MatOfByte imgMat = new MatOfByte();
        imgMat.fromArray(data);

        return Highgui.imdecode(imgMat, Highgui.CV_LOAD_IMAGE_UNCHANGED);
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

        byte[] byteRecognized = new byte[] { recognized ? (byte)1 : (byte)0 };

        IOUtils.write(sb.toString().getBytes(), out);
        IOUtils.write(byteRecognized, out);

        out.flush();
    }
}
