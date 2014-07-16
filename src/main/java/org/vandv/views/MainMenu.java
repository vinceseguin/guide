package org.vandv.views;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.TypedValue;
import junit.framework.Assert;
import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;
import org.vandv.R;
import org.vandv.navigation.ImageMatcher;
import org.vandv.utils.OpenCVUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;

/**
 * Created by vinceseguin on 03/07/14.
 */
public class MainMenu extends Activity {

    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                {
                    Bitmap bit = BitmapFactory.decodeResource(getResources(), R.drawable.ets1);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bit.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    byte[] imageInByte = stream.toByteArray();
                    Mat etsImage = OpenCVUtils.readImage(imageInByte);

                    Bitmap bit2 = BitmapFactory.decodeResource(getResources(), R.drawable.streetview);
                    ByteArrayOutputStream stream2 = new ByteArrayOutputStream();
                    bit2.compress(Bitmap.CompressFormat.JPEG, 100, stream2);
                    byte[] imageInByte2 = stream2.toByteArray();
                    Mat streetViewImage = OpenCVUtils.readImage(imageInByte2);

                    ImageMatcher imageMatcher = new ImageMatcher(streetViewImage);
                    boolean lala = imageMatcher.compareImage(etsImage);
                } break;
                default:
                {
                    super.onManagerConnected(status);
                } break;
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_menu);
    }

    @Override
    public void onResume()
    {
        super.onResume();

        OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_9, this, mLoaderCallback);
    }
}