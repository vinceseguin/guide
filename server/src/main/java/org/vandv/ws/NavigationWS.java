package org.vandv.ws;

import org.vandv.navigation.NavigationManager;

/**
 * Created by vinceseguin on 23/07/14.
 */
public class NavigationWS {

    public static long startNavigation(String address) {
        return NavigationManager.getInstance().startNavigation(address);
    }
}
