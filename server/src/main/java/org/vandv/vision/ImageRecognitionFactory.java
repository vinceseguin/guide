package org.vandv.vision;

/**
 * Created by vinceseguin on 16/07/14.
 */
public class ImageRecognitionFactory {

    public ImageRecognitionHandler createImageRecognitionHandler() {
        HistogramRecognitionHandler histogramRecognitionHandler = new HistogramRecognitionHandler();
        FeatureRecognitionHandler featureRecognitionHandler = new FeatureRecognitionHandler();

        histogramRecognitionHandler.setSuccessor(featureRecognitionHandler);

        return histogramRecognitionHandler;
    }
}
