package org.vandv.vision;

import org.opencv.core.Mat;

/**
 * Created by vinceseguin on 16/07/14.
 */
public class NullRecognitionHandler extends ImageRecognitionHandler {

    @Override
    public boolean handleRequest(Mat image1, Mat image2) {
        return successor == null || successor.handleRequest(image1, image2);
    }
}
