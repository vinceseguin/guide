package org.vandv.vision;

import org.opencv.core.Mat;

/**
 * Created by vinceseguin on 16/07/14.
 */
public class NullRecognition extends ImageRecognition {

    public NullRecognition(Mat obj1, Mat obj2) {
        super(obj1, obj2);
    }

    @Override
    public boolean handleRequest() {
        return successor == null || successor.handleRequest();
    }
}
