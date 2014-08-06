package org.vandv.vision;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.imgproc.Imgproc;
import org.vandv.vision.HistogramCalculator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vinceseguin on 16/07/14.
 */
public class HistogramRecognition extends ImageRecognition {


    private static final double HISTOGRAM_THRESHOLD = 0.25;

    public HistogramRecognition(Mat histogram1, Mat histogram2) {
        super(histogram1, histogram2);
    }

    @Override
    public boolean handleRequest() {
        return compareHistogram(obj1, obj2) >= HISTOGRAM_THRESHOLD && successor.handleRequest();
    }

    private double compareHistogram(Mat histogram1, Mat histogram2) {
        return Imgproc.compareHist(histogram1, histogram2, Imgproc.CV_COMP_CORREL);
    }
}