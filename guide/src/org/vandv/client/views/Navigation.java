package org.vandv.client.views;

import java.io.IOException;

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

import org.apache.http.client.ClientProtocolException;
import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.vandv.R;
import org.vandv.common.communication.IAsyncListener;
import org.vandv.common.google.MapsApiObjectFactory;

/**
 * Created by vinceseguin on 17/07/14.
 */
public class Navigation extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation);
        
        try {
			handleIntent(getIntent());
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        try {
			handleIntent(intent);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    private void handleIntent(Intent intent) throws ClientProtocolException, IOException {
        if(Intent.ACTION_SEARCH.equals(intent.getAction())) {
            IAsyncListener<Mat> imageListener = new IAsyncListener<Mat>() {
                @Override
                public void receive(Mat m) {
                    Bitmap bm = Bitmap.createBitmap(m.cols(), m.rows(),Bitmap.Config.ARGB_8888);
                    Utils.matToBitmap(m, bm);

                    // find the imageview and draw it!
                    ImageView iv = (ImageView) findViewById(R.id.streetview);
                    iv.setImageBitmap(bm);
                }
            };

            //START NAV
            String query = intent.getStringExtra(SearchManager.QUERY);

            //CALCUL HISTOGRAME + FEATURE (IF NECESSARY)
            MapsApiObjectFactory.getStreetViewImage(query);
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
