package org.vandv.server.client.vision;

import org.opencv.core.Mat;

/**
 * Abstract class using a variant of the chain of responsability pattern. (GOF)
 * The implementing classes must determine if two Matrices correspond to
 * the same scene.
 *
 * Created by vinceseguin on 16/07/14.
 */
public abstract class ImageRecognition {
    protected ImageRecognition successor;

    protected Mat obj1;
    protected Mat obj2;

    /**
     * Constructor
     * @param obj1 Matrix representing the first image.
     * @param obj2 Matrix representing the second image.
     */
    public ImageRecognition(Mat obj1, Mat obj2) {
        this.obj1 = obj1;
        this.obj2 = obj2;
    }

    /**
     * Determines if the two matrices are of the same scene.
     * If the logic in this method returns that they are, the
     * responsibility is passed down the chain.
     * @return <code>true</code> if the two matrices are of
     * the same scene
     * <code>false</code> otherwise
     */
    public abstract boolean handleRequest();

    /**
     * Sets the successor in the chain.
     * @param successor the successor in the chain.
     */
    public void setSuccessor(ImageRecognition successor) {
        if (this.successor == null) {
        	this.successor = successor;
        } else {
        	this.successor.setSuccessor(successor);
        }
    }
}
