package org.vandv.vision;

import org.opencv.core.Mat;

/**
 * Created by vinceseguin on 04/08/14.
 */
public class Destination {
    private String address;
    private Mat image;
    private Mat features;
    private Mat histogram;
    private int timer = 0;

    public Mat getHistogram() {
        return histogram;
    }

    public Mat getFeatures() {
        return features;
    }

    public Destination(String address, Mat image, Mat features, Mat histogram) {
        this.address = address;
        this.image = image;
        this.features = features;
        this.histogram = histogram;
    }

    public void tick() {
        this.timer += VisualRecognitionManager.TICK_DURATION;
    }

    public int getTimer() {
        return timer;
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }
}
