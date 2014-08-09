package org.vandv.client.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.opencv.core.Mat;
import org.vandv.common.communication.IRequestHandler;
import org.vandv.common.exceptions.ProtocolFormatException;
import org.vandv.common.vision.FeatureCalculator;
import org.vandv.common.vision.HistogramCalculator;

public class VisualRecognitionRequestHandler implements IRequestHandler {

	private static final int DATA_LINE_INDEX = 4;
	
	private Mat image;
	private int requestId;
	private ClientVisualRecognitionRequestManager manager;

	public VisualRecognitionRequestHandler(Mat image, int requestId, ClientVisualRecognitionRequestManager manager) {
		this.image = image;
		this.requestId = requestId;
		this.manager = manager;
	}
	
	@Override
	public void handleRequest(Socket socket) throws IOException,
			ProtocolFormatException {
		
    	Mat histogram = new HistogramCalculator(image).calculateHistogram();
    	float[] histogramBuffer = new float[(int) (histogram.total() * histogram.channels())];
    	histogram.get(0, 0, histogramBuffer);
    	
    	Mat features = new FeatureCalculator(image).calculateFeaturePoints();
    	byte[] featuresBuffer = new byte[(int) (features.total() * features.channels())];
    	features.get(0, 0, featuresBuffer);

    	String data = Arrays.toString(histogramBuffer) + ", " + Arrays.toString(featuresBuffer);
    	
        StringBuilder sb = new StringBuilder();
    	sb.append("GUIDE_SERVER_CLIENT_REQUEST\r\n");
    	sb.append("REQUEST_ACTION:VISUAL_RECOGNITION\r\n");
    	sb.append("REQUEST_TYPE:HISTOGRAM_FEATURE\r\n");
    	sb.append(String.format("REQUEST_ID:%d\r\n", requestId));
    	sb.append(String.format("DATA_LENGTH:%d\r\n", data.length()));
    	sb.append(String.format("PARAMS_LENGTH:%d,%d\r\n", histogramBuffer.length, featuresBuffer.length));
    	sb.append(data);
    	
	    OutputStream out = socket.getOutputStream();
	    IOUtils.write(sb.toString(), out);
	    out.flush();
        socket.shutdownOutput();

	    InputStream is = socket.getInputStream();
	    List<String> lines = IOUtils.readLines(is, "UTF-8");
	    
	    byte requestId = Byte.parseByte(lines.get(DATA_LINE_INDEX));
	    manager.setFinished(requestId == 1);
	}
}
