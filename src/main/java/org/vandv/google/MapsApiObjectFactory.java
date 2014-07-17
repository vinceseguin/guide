package org.vandv.google;

import org.json.JSONException;
import org.json.JSONObject;
import org.vandv.communication.IAsyncListener;
import org.vandv.communication.JsonHttpRequestExecutor;

import java.net.URLEncoder;

/**
 * Created by vinceseguin on 17/07/14.
 */
public class MapsApiObjectFactory {

    private static final String GEOCODING_API_URL = "https://maps.googleapis.com/maps/api/geocode/json?address=";

    public static void getLocation(String address, IAsyncListener<Location> listener) {
        IAsyncListener<String> httpListener = new IAsyncListener<String>() {
            @Override
            public void receive(String object) {

                try {
                    JSONObject jsonObject = new JSONObject(object);

                    JSONObject location = jsonObject.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location");

                    Location loc = new Location();
                    loc.setLatitude(Double.parseDouble(location.get("lat").toString()));
                    loc.setLongitude(Double.parseDouble(location.get("lng").toString()));
                } catch (JSONException exception) {
                    //TODO: malformed string
                }

            }
        };

        JsonHttpRequestExecutor executor = new JsonHttpRequestExecutor(httpListener);
        executor.execute(GEOCODING_API_URL + URLEncoder.encode(address));
    }
}
