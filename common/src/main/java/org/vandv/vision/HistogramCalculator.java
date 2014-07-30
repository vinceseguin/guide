package org.vandv.vision;

import org.opencv.core.*;
import org.opencv.features2d.DescriptorExtractor;
import org.opencv.features2d.FeatureDetector;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vinceseguin on 23/07/14.
 */
public class HistogramCalculator {
    private Mat image;

    private static final int H_BINS = 50;
    private static final int S_BINS = 60;
    private static final float H_RANGE_MAX = 180;
    private static final float S_RANGE_MAX = 256;

    public HistogramCalculator(Mat image) {
        this.image = image;
    }

    public Mat calculate() {
        Mat imageHsv = new Mat();

        Imgproc.cvtColor(image, imageHsv, Imgproc.COLOR_BGR2HSV);

        Mat imageHist = new Mat();

        MatOfInt channels = new MatOfInt(0, 1);
        MatOfInt histSize = new MatOfInt(H_BINS, S_BINS);

        MatOfFloat ranges = new MatOfFloat(0f, H_RANGE_MAX,0f, S_RANGE_MAX);

        List<Mat> imageList = new ArrayList<Mat>();
        imageList.add(imageHsv);

        Imgproc.calcHist(imageList, channels, new Mat(), imageHist, histSize, ranges);

        Core.normalize(imageHist, imageHist, 0, 1, Core.NORM_MINMAX, -1, new Mat());

        return imageHist;
    }
}
