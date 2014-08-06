package org.vandv.vision;

import org.opencv.core.Mat;

/**
 * Created by vinceseguin on 16/07/14.
 */
public abstract class ImageRecognition {
    protected ImageRecognition successor;

    protected Mat obj1;
    protected Mat obj2;

    public ImageRecognition(Mat obj1, Mat obj2) {
        this.obj1 = obj1;
        this.obj2 = obj2;
    }

    public abstract boolean handleRequest();

    public void setSuccessor(ImageRecognition successor) {
        successor.setSuccessor(successor);
    }
}
