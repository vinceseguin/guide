package org.vandv.server.client.vision;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.opencv.core.Mat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The manager of the visual recognition request.
 *
 * Created by vinceseguin on 04/08/14.
 */
public class VisualRecognitionManager {

    private static final Logger logger = LogManager.getLogger(VisualRecognitionManager.class.getName());
    public static final int TICK_DURATION = 10;
    public static final int FLUSH_TIMER_LIMIT = 60;
    private IdGenerator idGenerator = new IdGenerator();
    private Map<Long, Destination> destinations = new HashMap<Long, Destination>();

    private static volatile VisualRecognitionManager instance = null;

    /**
     * Private constructor for the singleton pattern.
     */
    private VisualRecognitionManager() {

    }

    /**
     * Creates the single instance
     * @return the instance
     */
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

    /**
     * Register a new destination
     * @param destination the destination
     * @return the request id the client will have to use for the future
     */
    public long registerDestination(Destination destination) {
        logger.trace(String.format("REGISTERING DESTINATION"));
        long id = idGenerator.generate();
        this.destinations.put(id, destination);
        return id;
    }

    /**
     * Get the feature points for a destination.
     * @param id
     * @return
     */
    public Mat getHistogram(long id) {
        destinations.get(id).setTimer(0);
        return destinations.get(id).getHistogram();
    }

    /**
     * Get the feature points for a destination.
     * @param id the request id
     * @return the feature points
     */
    public Mat getFeatures(long id) {
        destinations.get(id).setTimer(0);
        return destinations.get(id).getFeatures();
    }

    /**
     * Get the current number of request for this server.
     * @return the number of request
     */
    public int getCurrentNumberOfRequest() {
        return destinations.size();
    }

    /**
     * Starts a new thread to monitor servers every TICK_DURATION sec.
     * If a client has not pinged us in FLUSH_TIMER_LIMIT sec, it is removed from the list.
     */
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
                                logger.trace("REMOVED DESTINATION");
                            }
                        }
                    } catch (Exception ex) {

                    }
                }
            }
        }).start();
    }
}
