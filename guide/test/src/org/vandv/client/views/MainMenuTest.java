package org.vandv.client.views;

import android.test.ActivityInstrumentationTestCase2;
import org.vandv.client.views.MainMenu;

/**
 * This is a simple framework for a test of an Application.  See
 * {@link android.test.ApplicationTestCase ApplicationTestCase} for more information on
 * how to write and extend Application tests.
 * <p/>
 * To run this test, you can type:
 * adb shell am instrument -w \
 * -e class org.vandv.client.views.MainMenuTest \
 * org.vandv.tests/android.test.InstrumentationTestRunner
 */
public class MainMenuTest extends ActivityInstrumentationTestCase2<MainMenu> {

    public MainMenuTest() {
        super("org.vandv", MainMenu.class);
    }

}
