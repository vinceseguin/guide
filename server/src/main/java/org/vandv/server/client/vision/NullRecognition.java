package org.vandv.server.client.vision;

import org.opencv.core.Mat;

/**
 * Implements the Null Object pattern (GOF). It only passes the responsibility to the next object.
 * This classes simplifies the exit conditions in the chain of responsability.
 * Part of the chain of responsibility (GOF)
 *
 * Created by vinceseguin on 16/07/14.
 */
public class NullRecognition extends ImageRecognition {

    /**
     * Constructor
     * @param obj1 Matrix representing the first image.
     * @param obj2 Matrix representing the second image.
     */
    public NullRecognition(Mat obj1, Mat obj2) {
        super(obj1, obj2);
    }

    /**
     * Passes the responsibility to his successor.
     * @return <code>true</code> if the two matrices are of
     * the same scene
     * <code>false</code> otherwise
     */
    @Override
    public boolean handleRequest() {
        return successor == null || successor.handleRequest();
    }
}
