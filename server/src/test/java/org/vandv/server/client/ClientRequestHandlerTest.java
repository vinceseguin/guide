package org.vandv.server.client;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.*;
import org.junit.Assert;
import org.junit.Test;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.vandv.exceptions.ProtocolFormatException;
import org.vandv.server.client.vision.NewDestinationAction;
import org.vandv.server.client.vision.VisualRecognitionAction;
import org.vandv.server.client.vision.VisualRecognitionManager;

/**
 * Created by vinceseguin on 06/08/14.
 * Updated by vgentilcore on 07/08/14.
 */
public class ClientRequestHandlerTest {

	static{ System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }
	
    @Test
    public void createActionRegisterDestinationTest() {
    	ClientRequestHandler requestHandler = new ClientRequestHandler();
    	
    	String data = "Ecole de Technologie Superieure";
    	
    	List<String> lines = new ArrayList<String>();
    	lines.add("GUIDE_SERVER_CLIENT_REQUEST");
    	lines.add("REQUEST_ACTION:VISUAL_RECOGNITION");
    	lines.add("REQUEST_TYPE:REGISTER_DESTINATION");
    	lines.add("REQUEST_ID:0");
    	lines.add(String.format("DATA_LENGTH:%d", data.length()));
    	lines.add(String.format("PARAMS_LENGTH:%d", data.length()));
    	lines.add(data);
    	
    	try {
    		IAction action = requestHandler.createAction(lines);
			Assert.assertTrue(action instanceof NewDestinationAction);
    	} catch (Exception ex) {
		}
    }
    
    @Test
    public void createActionHistogramTest2() {
    	ClientRequestHandler requestHandler = new ClientRequestHandler();
    	
    	String data = "Ecole de Technologie Superieure";
    	
    	List<String> lines = new ArrayList<String>();
    	lines.add("GUIDE_SERVER_CLIENT_REQUEST");
    	lines.add("REQUEST_ACTION:VISUAL_RECOGNITION");
    	lines.add("REQUEST_TYPE:HISTOGRAM");
    	lines.add("REQUEST_ID:0");
    	lines.add(String.format("DATA_LENGTH:%d", data.length()));
    	lines.add(String.format("PARAMS_LENGTH:%d", data.length()));
    	lines.add(data);
    	
    	try {
    		IAction action = requestHandler.createAction(lines);
			Assert.assertTrue(action instanceof VisualRecognitionAction);
    	} catch (Exception ex) {
		}
    }
    
    @Test
    public void createActionNotValidTest3() {
    	ClientRequestHandler requestHandler = new ClientRequestHandler();
    	
    	String data = "Ecole de Technologie Superieure";
    	
    	List<String> lines = new ArrayList<String>();
    	lines.add("GUIDE_SERVER_CLIENT_REQUEST");
    	lines.add("REQUEST_ACTION:NOT_VALID");
    	lines.add("REQUEST_TYPE:REGISTER_DESTINATION");
    	lines.add("REQUEST_ID:0");
    	lines.add(String.format("DATA_LENGTH:%d", data.length()));
    	lines.add(String.format("PARAMS_LENGTH:%d", data.length()));
    	lines.add(data);
    	
    	try {
    		IAction action = requestHandler.createAction(lines);
			Assert.fail();
    	} catch (Exception ex) {
		}
    }
    
    @Test
    public void handleRequestRegisterDestinationTest() {
		Socket socket = mock(Socket.class);
		OutputStream out = mock(BufferedOutputStream.class);
		
    	String data = "Ecole de Technologie Superieure";
    	
    	StringBuilder sb = new StringBuilder();
    	sb.append("GUIDE_SERVER_CLIENT_REQUEST\r\n");
    	sb.append("REQUEST_ACTION:VISUAL_RECOGNITION\r\n");
    	sb.append("REQUEST_TYPE:REGISTER_DESTINATION\r\n");
    	sb.append("REQUEST_ID:0\r\n");
    	sb.append(String.format("DATA_LENGTH:%d\r\n", data.length()));
    	sb.append(String.format("PARAMS_LENGTH:%d\r\n", data.length()));
    	sb.append(data);
    	
    	try {
			when(socket.getInputStream()).thenReturn(IOUtils.toInputStream(sb));
			when(socket.getOutputStream()).thenReturn(out);
		} catch (IOException e) {
			Assert.fail();
		}
    	
    	ClientRequestHandler requestHandler = new ClientRequestHandler();
    	try {
			requestHandler.handleRequest(socket);
		} catch (IOException | ProtocolFormatException e) {
			Assert.fail();
		}
    }
    
    @Test
    public void handleRequestRegisterDestinationNotValidTest2() {
		Socket socket = mock(Socket.class);
		OutputStream out = mock(BufferedOutputStream.class);
		
    	String data = "Ecole de Technologie Superieure";
    	
    	StringBuilder sb = new StringBuilder();
    	sb.append("GUIDE_SERVER_CLIENT_REQUEST\r\n");
    	sb.append("REQUEST_ACTION:VISUAL_RECOGNITION\r\n");
    	sb.append("REQUEST_TYPE:REGISTER_DESTINATION\r\n");
    	sb.append("REQUEST_ID:0\r\n");
    	sb.append(String.format("DATA_LENGTH:%d\r\n", data.length() + 1));
    	sb.append(String.format("PARAMS_LENGTH:%d\r\n", data.length() + 1));
    	sb.append(data);
    	
    	try {
			when(socket.getInputStream()).thenReturn(IOUtils.toInputStream(sb));
			when(socket.getOutputStream()).thenReturn(out);
		} catch (IOException e) {
			Assert.fail();
		}
    	
    	ClientRequestHandler requestHandler = new ClientRequestHandler();
    	try {
			requestHandler.handleRequest(socket);
			Assert.fail();
		} catch (IOException | ProtocolFormatException e) {
		}
    }
    
    @Test
    public void handleRequestHistogramFeatureTest() {
		Socket socket = mock(Socket.class);
		OutputStream out = mock(BufferedOutputStream.class);
		
    	VisualRecognitionManager manager = VisualRecognitionManager.getInstance();
    	
    	int id = 1;
    	
    	Mat histogram = manager.getHistogram(id);
    	float[] histogramBuffer = new float[(int) (histogram.total() * histogram.channels())];
    	histogram.get(0, 0, histogramBuffer);
    	
    	Mat features = manager.getFeatures(id);
    	byte[] featuresBuffer = new byte[(int) (features.total() * features.channels())];
    	features.get(0, 0, featuresBuffer);

    	String data = Arrays.toString(histogramBuffer) + ", " + Arrays.toString(featuresBuffer);
    	
        StringBuilder sb = new StringBuilder();
    	sb.append("GUIDE_SERVER_CLIENT_REQUEST\r\n");
    	sb.append("REQUEST_ACTION:VISUAL_RECOGNITION\r\n");
    	sb.append("REQUEST_TYPE:HISTOGRAM_FEATURE\r\n");
    	sb.append(String.format("REQUEST_ID:%d\r\n", id));
    	sb.append(String.format("DATA_LENGTH:%d\r\n", data.length()));
    	sb.append(String.format("PARAMS_LENGTH:%d,%d\r\n", histogramBuffer.length, featuresBuffer.length));
    	sb.append(data);
    	
    	try {
			when(socket.getInputStream()).thenReturn(IOUtils.toInputStream(sb));
			when(socket.getOutputStream()).thenReturn(out);
		} catch (IOException e) {
			Assert.fail();
		}
    	
    	ClientRequestHandler requestHandler = new ClientRequestHandler();
    	try {
			requestHandler.handleRequest(socket);
		} catch (IOException | ProtocolFormatException e) {
			Assert.fail();
		}
    }
}
