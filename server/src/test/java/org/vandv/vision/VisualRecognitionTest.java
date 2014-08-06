package org.vandv.vision;

import org.junit.Test;
import org.opencv.core.Core;
import org.vandv.google.MapsApiObjectFactory;

import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class VisualRecognitionTest {

    static{ System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }

    @Test
    public void newDestinationTest() {

        OutputStream out = mock(BufferedOutputStream.class);

        MapsApiObjectFactory.getStreetViewImage("Ecole de technologie superieure");

        VisualRecognitionManager manager = new VisualRecognitionManager();
        NewDestinationAction action = new NewDestinationAction(manager);

        List<String> lines = new ArrayList<String>();

        byte[] data = "Ecole de technologie superieure".getBytes();

        try {
            action.execute(out, lines, data);
        } catch (Exception ex) {

        }
    }
}