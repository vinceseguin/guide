package org.vandv.client.views;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ImageView;
import android.widget.SearchView;
import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.vandv.R;
import org.vandv.communication.IAsyncListener;
import org.vandv.google.MapsApiObjectFactory;

/**
 * Created by vinceseguin on 17/07/14.
 */
public class Navigation extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation);
        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if(Intent.ACTION_SEARCH.equals(intent.getAction())) {
            IAsyncListener<Mat> imageListener = new IAsyncListener<Mat>() {
                @Override
                public void receive(Mat m) {
                    Bitmap bm = Bitmap.createBitmap(m.cols(), m.rows(),Bitmap.Config.ARGB_8888);
                    Utils.matToBitmap(m, bm);

                    // find the imageview and draw it!
                    ImageView iv = (ImageView) findViewById(R.id.streeview);
                    iv.setImageBitmap(bm);
                }
            };

            //START NAV
            String query = intent.getStringExtra(SearchManager.QUERY);

            //CALCUL HISTOGRAME + FEATURE (IF NECESSARY)
            MapsApiObjectFactory.getStreetViewImage(query, imageListener);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        return super.onCreateOptionsMenu(menu);
    }
}
