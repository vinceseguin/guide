package org.vandv.vision;

import org.junit.Test;
import org.opencv.core.Core;
import org.vandv.google.MapsApiObjectFactory;

public class MapsApiObjectFactoryTest {

    static{ System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }

    @Test
    public void getStreetViewImage() {
        MapsApiObjectFactory.getStreetViewImage("Ecole de technologie superieure");
    }
}