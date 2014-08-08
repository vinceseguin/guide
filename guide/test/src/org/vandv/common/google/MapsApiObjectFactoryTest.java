package org.vandv.common.google;

import android.test.InstrumentationTestCase;
import org.vandv.common.communication.IAsyncListener;

/**
 * Created by vinceseguin on 27/07/14.
 */
public class MapsApiObjectFactoryTest extends InstrumentationTestCase {

    public void getLocationTest() {
        MapsApiObjectFactory.getLocation("Ecole de technologie superieure", new IAsyncListener<Location>() {
            @Override
            public void receive(Location object) {

            }
        }, null);
    }
}
