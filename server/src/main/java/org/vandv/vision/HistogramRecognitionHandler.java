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
public class HistogramRecognitionHandler extends ImageRecognitionHandler {


    private static final double HISTOGRAM_THRESHOLD = 0.25;

    @Override
    public boolean handleRequest(Mat image1, Mat image2) {
        return compareHistogram(image1, image2) >= HISTOGRAM_THRESHOLD && successor.handleRequest(image1, image2);
    }

    private Mat calculateHistogram(Mat image) {
        HistogramCalculator calculator = new HistogramCalculator(image);
        return calculator.calculate();
    }

    private double compareHistogram(Mat image1, Mat image2) {
        return Imgproc.compareHist(calculateHistogram(image1), calculateHistogram(image2), Imgproc.CV_COMP_CORREL);
    }
}