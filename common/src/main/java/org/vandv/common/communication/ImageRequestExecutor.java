package org.vandv.common.communication;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.highgui.Highgui;

import java.io.ByteArrayOutputStream;

/**
 * Created by vinceseguin on 23/07/14.
 */
public class ImageRequestExecutor extends RequestExecutor<Mat> {

    @Override
    protected Mat transformByte(ByteArrayOutputStream out) {
        byte[] imageBytes = out.toByteArray();
        MatOfByte imgMatByte = new MatOfByte();
        imgMatByte.fromArray(imageBytes);
        return Highgui.imdecode(imgMatByte, Highgui.CV_LOAD_IMAGE_UNCHANGED);
    }
}
