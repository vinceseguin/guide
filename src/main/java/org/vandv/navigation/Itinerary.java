package org.vandv.navigation;

import java.util.List;

/**
 * Created by vinceseguin on 06/07/14.
 */
public abstract class Itinerary {

    protected Location origin;
    protected Location destination;
    protected String mode;

    public Itinerary() { };

    public Itinerary(Location origin, Location destination) {
        this.origin = origin;
        this.destination = destination;
    }

    public abstract List<Step> getSteps();
}
