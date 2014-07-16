package org.vandv.utils;

/**
 * Created by vinceseguin on 13/07/14.
 */

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.highgui.Highgui;

public final class OpenCVUtils {

    private OpenCVUtils() {}

    public static Mat readImage(byte[] imgArr) {

        MatOfByte imgMatByte = new MatOfByte();
        imgMatByte.fromArray(imgArr);

        return Highgui.imdecode(imgMatByte, Highgui.CV_LOAD_IMAGE_UNCHANGED);
    }
}
