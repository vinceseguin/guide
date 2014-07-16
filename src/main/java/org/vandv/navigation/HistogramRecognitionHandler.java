package org.vandv.navigation;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vinceseguin on 16/07/14.
 */
public class HistogramRecognitionHandler extends ImageRecognitionHandler {

    private static final int H_BINS = 50;
    private static final int S_BINS = 60;
    private static final float H_RANGE_MAX = 180;
    private static final float S_RANGE_MAX = 256;
    private static final double HISTOGRAM_THRESHOLD = 0.25;

    @Override
    public boolean handleRequest(Mat image1, Mat image2) {

        return compareHistogram(image1, image2) >= HISTOGRAM_THRESHOLD && successor.handleRequest(image1, image2);
    }

    private Mat calculateHistogram(Mat image) {
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

    private double compareHistogram(Mat image1, Mat image2) {
        return Imgproc.compareHist(calculateHistogram(image1), calculateHistogram(image2), Imgproc.CV_COMP_CORREL);
    }
}
