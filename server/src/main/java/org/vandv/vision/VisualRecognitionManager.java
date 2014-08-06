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

    public static final int TICK_DURATION = 1;
    public static final int FLUSH_TIMER_LIMIT = 60;
    private IdGenerator idGenerator = new IdGenerator();
    private Map<Long, Destination> destinations = new HashMap<Long, Destination>();

    private static volatile VisualRecognitionManager instance = null;

    private VisualRecognitionManager() {

    }

    public final static VisualRecognitionManager getInstance() {
        if(instance == null) {
            synchronized (VisualRecognitionManager.class) {
                if(instance == null) {
                    instance = new VisualRecognitionManager();
                }
            }
        }

        instance.startTicking();
        return instance;
    }

    public long registerDestination(Destination destination) {
        long id = idGenerator.generate();
        this.destinations.put(id, destination);
        return id;
    }

    public Mat getHistogram(long id) {
        destinations.get(id).setTimer(0);
        return destinations.get(id).getHistogram();
    }

    public Mat getFeatures(long id) {
        destinations.get(id).setTimer(0);
        return destinations.get(id).getFeatures();
    }

    public int getCurrentNumberOfRequest() {
        return destinations.size();
    }

    public void startTicking() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(TICK_DURATION * 1000);

                        for (Long key : destinations.keySet()) {
                            Destination destination = destinations.get(key);
                            destination.tick();
                            if (destination.getTimer() >= FLUSH_TIMER_LIMIT) {
                                destinations.remove(key);
                            }
                        }
                    } catch (Exception ex) {

                    }
                }
            }
        }).start();
    }
}
