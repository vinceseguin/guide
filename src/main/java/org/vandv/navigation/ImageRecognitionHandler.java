package org.vandv.navigation;

import org.opencv.core.Mat;

/**
 * Created by vinceseguin on 16/07/14.
 */
public abstract class ImageRecognitionHandler {
    protected ImageRecognitionHandler successor = new NullRecognitionHandler();

    public void setSuccessor(ImageRecognitionHandler successor) {
        this.successor = successor;
    }

    public abstract boolean handleRequest(Mat image1, Mat image2);
}
