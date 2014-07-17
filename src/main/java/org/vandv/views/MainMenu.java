package org.vandv.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import org.vandv.R;

/**
 * Created by vinceseguin on 03/07/14.
 */
public class MainMenu extends Activity {

    /*private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
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

                    IAsyncListener<Location> listener = new IAsyncListener<Location>() {
                        @Override
                        public void receive(Location object) {
                            Assert.assertEquals(object.getLatitude(), 45.495403);
                            Assert.assertEquals(object.getLongitude(), -73.56303199999999);
                        }
                    };

                    MapsApiObjectFactory.getLocation("Ecole de technologie superieure", listener);

                    //ImageMatcher imageMatcher = new ImageMatcher(streetViewImage);
                    //boolean lala = imageMatcher.compareImage(etsImage);
                } break;
                default:
                {
                    super.onManagerConnected(status);
                } break;
            }
        }
    };*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_menu);
    }

    public void startNavigationActivity(View view) {
        Intent intent = new Intent(this, Navigation.class);
        startActivity(intent);
    }

    /*@Override
    public void onResume()
    {
        super.onResume();

        OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_9, this, mLoaderCallback);
    }*/
}