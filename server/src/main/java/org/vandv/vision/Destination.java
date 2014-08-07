package org.vandv.vision;

import org.opencv.core.Mat;

/**
 * Class containing the data necessary for the recognition algorithm.
 *
 * Created by vinceseguin on 04/08/14.
 */
public class Destination {
    private Mat features;
    private Mat histogram;
    private int timer = 0;

    /**
     * Constructor
     * @param features Matrix of feature point of the google street image.
     * @param histogram Histogram of the google street image.
     */
    public Destination(Mat features, Mat histogram) {
        this.features = features;
        this.histogram = histogram;
    }

    /**
     * Get the histogram of the google street image.
     * @return The histogram.
     */
    public Mat getHistogram() {
        return histogram;
    }

    /**
     * Get the features of the google street image.
     * @return The features.
     */
    public Mat getFeatures() {
        return features;
    }

    /**
     * Increase the timer according to the TICK_DURATION value
     * defined in the VisualRecognitionManager class.
     */
    public void tick() {
        this.timer += VisualRecognitionManager.TICK_DURATION;
    }

    /**
     * Get the timer.
     * @return The timer
     */
    public int getTimer() {
        return timer;
    }

    /**
     * Set the timer
     * @param timer The timer.
     */
    public void setTimer(int timer) {
        this.timer = timer;
    }
}
