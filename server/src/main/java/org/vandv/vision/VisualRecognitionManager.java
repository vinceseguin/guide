package org.vandv.vision;

import org.opencv.core.Mat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by vinceseguin on 04/08/14.
 */
public class VisualRecognitionManager {

    private IdGenerator idGenerator = new IdGenerator();
    private Map<Long, Destination> destinations = new HashMap<Long, Destination>();

    public long registerDestination(Destination destination) {
        long id = idGenerator.generate();
        this.destinations.put(id, destination);
        return id;
    }

    public Mat getHistogram(long id) {
        return destinations.get(id).getHistogram();
    }

    public Mat getFeatures(long id) {
        return destinations.get(id).getFeatures();
    }
}
