package org.vandv.vision;

import org.apache.commons.io.IOUtils;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.highgui.Highgui;
import org.vandv.communication.IAction;

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
    public void execute(OutputStream out, List<String> lines, byte[] data) throws IOException {
        String requestType = lines.get(REQUEST_TYPE_LINE_INDEX).split(":")[1];
        String[] paramsLength = lines.get(PARAMS_LENGTH_LINE_INDEX).split(":")[1].split(",");
        long requestId = Long.parseLong(lines.get(REQUEST_ID_LINE_INDEX).split(":")[1]);

        ImageRecognition imageRecognition = new NullRecognition(null, null);

        int offset = 0;
        int nextParamLength = Integer.parseInt(paramsLength[0]);

        if (requestType.contains("HISTOGRAM")) {
            offset = nextParamLength;
            Mat histogram = createMat(data, nextParamLength, offset);
            nextParamLength = Integer.parseInt(paramsLength[1]);
            imageRecognition.setSuccessor(new HistogramRecognition(histogram,
                    visualRecognitionManager.getHistogram(requestId)));
        }

        if (requestType.contains("FEATURE")) {
            Mat features = createMat(data, nextParamLength, offset);
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
    private Mat createMat(byte[] data, int length, int offset) {
        byte[] arr = new byte[length];

        System.arraycopy(data, offset, arr, 0, length);
        MatOfByte imgMatByte = new MatOfByte();
        imgMatByte.fromArray(arr);

        return Highgui.imdecode(imgMatByte, Highgui.CV_LOAD_IMAGE_UNCHANGED);
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
