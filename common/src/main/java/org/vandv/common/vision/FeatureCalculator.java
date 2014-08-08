package org.vandv.common.vision;

import org.opencv.core.Mat;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.features2d.DescriptorExtractor;
import org.opencv.features2d.FeatureDetector;

/**
 * This class calculates the feature points for a given image.
 * Created by vinceseguin on 23/07/14.
 */
public class FeatureCalculator {

    private Mat image;

    /**
     * Constructor
     * @param image The opencv matrix containing the image.
     */
    public FeatureCalculator(Mat image) {
        this.image = image;
    }

    /**
     * Calculate the feature points of the image.
     * @return The feature points.
     */
    public Mat calculateFeaturePoints() {
        FeatureDetector fd = FeatureDetector.create(FeatureDetector.FAST);

        MatOfKeyPoint keyPoints = new MatOfKeyPoint();
        fd.detect(image, keyPoints);

        DescriptorExtractor de = DescriptorExtractor.create(DescriptorExtractor.BRIEF);

        Mat descriptors = new Mat();
        de.compute(image, keyPoints, descriptors);

        return descriptors;
    }
}
