package org.vandv.vision;

import org.opencv.core.Mat;

/**
 * Created by vinceseguin on 04/08/14.
 */
public class Destination {
    private String address;
    private Mat image;
    private Mat features;

    public Mat getHistogram() {
        return histogram;
    }

    public Mat getFeatures() {
        return features;
    }

    private Mat histogram;

    public Destination(String address, Mat image, Mat features, Mat histogram) {
        this.address = address;
        this.image = image;
        this.features = features;
        this.histogram = histogram;
    }
}
