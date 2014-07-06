package org.vandv.navigation;

import java.util.List;

/**
 * Created by vinceseguin on 06/07/14.
 */
public class BusItinerary extends Itinerary {

    public BusItinerary(Location origin, Location destination) {
        super(origin, destination);
        this.mode = "transit";
    }

    @Override
    public List<Step> getSteps() {
        //TODO
        return null;
    }
}
