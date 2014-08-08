package org.vandv.common.vision;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

/**
 * This class calculates the histogram for a given image.
 *
 * Created by vinceseguin on 23/07/14.
 */
public class HistogramCalculator {
    private Mat image;

    public static final int H_BINS = 50;
    public static final int S_BINS = 60;
    private static final float H_RANGE_MAX = 180;
    private static final float S_RANGE_MAX = 256;

    /**
     * Constructor
     * @param image The opencv matrix containing the image.
     */
    public HistogramCalculator(Mat image) {
        this.image = image;
    }

    /**
     * Calculate the histogram
     * @return The histogram.
     */
    public Mat calculateHistogram() {
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
