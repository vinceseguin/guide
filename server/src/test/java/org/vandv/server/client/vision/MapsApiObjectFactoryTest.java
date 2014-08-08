package org.vandv.server.client.vision;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.junit.Assert;
import org.junit.Test;
import org.opencv.core.Core;
import org.vandv.common.google.MapsApiObjectFactory;

public class MapsApiObjectFactoryTest {

    static{ System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }

    @Test
    public void getStreetViewImage() {
        try {
			MapsApiObjectFactory.getStreetViewImage("Ecole de technologie superieure");
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			Assert.fail();
		} catch (IOException e) {
			e.printStackTrace();
			Assert.fail();
		}
    }
}