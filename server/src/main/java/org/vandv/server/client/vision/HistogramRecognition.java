package org.vandv.server.client.vision;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

/**
 * Determine if two Matrices correspond to the same scene using their histograms.
 * Part of the chain of responsibility (GOF)
 * Created by vinceseguin on 16/07/14.
 */
public class HistogramRecognition extends ImageRecognition {

    private static final double HISTOGRAM_THRESHOLD = 0.25;

    /**
     * Constructor
     * @param histogram1 Matrix representing the histogram of the first image.
     * @param histogram2 Matrix representing the histogram of the second image.
     */
    public HistogramRecognition(Mat histogram1, Mat histogram2) {
        super(histogram1, histogram2);
    }

    /**
     * Determines if the two matrices are of the same scene.
     * If the logic in this method returns that they are, the
     * responsibility is passed down the chain.
     * The matrices are histograms in this class.
     * @return <code>true</code> if the two matrices are of
     * the same scene
     * <code>false</code> otherwise
     */
    @Override
    public boolean handleRequest() {
        return compareHistogram(obj1, obj2) >= HISTOGRAM_THRESHOLD && successor.handleRequest();
    }

    /**
     * Comparison of the histogram.
     * @param histogram1 Matrix representing the histogram of the first image.
     * @param histogram2 Matrix representing the histogram of the second image.
     * @return The correlation between the two matrices. Please refer to:
     * http://docs.opencv.org/modules/imgproc/doc/histograms.html?highlight=comparehist#comparehist
     */
    private double compareHistogram(Mat histogram1, Mat histogram2) {
        return Imgproc.compareHist(histogram1, histogram2, Imgproc.CV_COMP_CORREL);
    }
}