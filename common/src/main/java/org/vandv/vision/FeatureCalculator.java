package org.vandv.vision;

import org.opencv.core.Mat;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.features2d.DescriptorExtractor;
import org.opencv.features2d.FeatureDetector;

/**
 * Created by vinceseguin on 23/07/14.
 */
public class FeatureCalculator {

    private Mat image;

    public FeatureCalculator(Mat image) {
        this.image = image;
    }

    public Mat calculate() {
        FeatureDetector fd = FeatureDetector.create(FeatureDetector.FAST);

        MatOfKeyPoint keyPoints = new MatOfKeyPoint();
        fd.detect(image, keyPoints);

        DescriptorExtractor de = DescriptorExtractor.create(DescriptorExtractor.BRIEF);

        Mat descriptors = new Mat();
        de.compute(image, keyPoints, descriptors);

        return descriptors;
    }
}
