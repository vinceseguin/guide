package org.vandv.communication;

import org.junit.Assert;
import org.junit.Test;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.vandv.google.Location;
import org.vandv.google.MapsApiObjectFactory;

public class MapsApiObjectFactoryTest {

    static{ System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }

    @Test
    public void testGetLocation() {
        MapsApiObjectFactory.getLocation("Ecole de technologie superieure", new IAsyncListener<Location>() {
            @Override
            public void receive(Location object) {
                Assert.assertNotNull(object);
            }
        }, new ServerRequestStrategy<String>());
    }

    @Test
    public void getStreetViewImage() {
        MapsApiObjectFactory.getStreetViewImage("Ecole de technologie superieure", new IAsyncListener<Mat>() {
            @Override
            public void receive(Mat object) {
                Assert.assertNotNull(object);
            }
        }, new ServerRequestStrategy<org.opencv.core.Mat>());
    }
}