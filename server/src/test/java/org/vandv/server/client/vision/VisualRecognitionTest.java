package org.vandv.server.client.vision;

import org.apache.http.client.ClientProtocolException;
import org.junit.Assert;
import org.junit.Test;
import org.opencv.core.Core;
import org.vandv.google.MapsApiObjectFactory;
import org.vandv.server.client.vision.NewDestinationAction;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class VisualRecognitionTest {

    static{ System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }

    @Test
    public void newDestinationTest() {

        OutputStream out = mock(BufferedOutputStream.class);

        try {
			MapsApiObjectFactory.getStreetViewImage("Ecole de technologie superieure");
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			Assert.fail();
		} catch (IOException e) {
			e.printStackTrace();
			Assert.fail();
		}

        NewDestinationAction action = new NewDestinationAction();

        List<String> lines = new ArrayList<String>();
        
        String str = "Ecole de technologie superieure";

        try {
            action.execute(out, lines, str.toCharArray());
        } catch (Exception ex) {
        	Assert.fail();
        }
    }
}