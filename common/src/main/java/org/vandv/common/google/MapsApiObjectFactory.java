package org.vandv.common.google;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;
import org.opencv.core.Mat;
import org.vandv.common.communication.IAsyncListener;
import org.vandv.common.communication.IRequestStrategy;
import org.vandv.common.communication.ImageRequestExecutor;
import org.vandv.common.communication.JsonHttpRequestExecutor;

import java.io.IOException;
import java.net.URLEncoder;

/**
 * Created by vinceseguin on 17/07/14.
 */
public class MapsApiObjectFactory {

    private static final String GEOCODING_API_URL = "https://maps.googleapis.com/maps/api/geocode/json?address=";
    private static final String STREET_VIEW_API_URL = "https://maps.googleapis.com/maps/api/streetview?size=400x400&";

    @Deprecated
    public static void getLocation(String address, final IAsyncListener<Location> listener, IRequestStrategy<String> strategy) {

        IAsyncListener<String> httpListener = new IAsyncListener<String>() {
            @Override
            public void receive(String object) {

                try {
                    JSONObject jsonObject = new JSONObject(object);

                    JSONObject location = jsonObject.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location");

                    Location loc = new Location();
                    loc.setLatitude(Double.parseDouble(location.get("lat").toString()));
                    loc.setLongitude(Double.parseDouble(location.get("lng").toString()));

                    listener.receive(loc);
                } catch (JSONException exception) {
                    //TODO: malformed string
                }
            }
        };

        JsonHttpRequestExecutor executor = new JsonHttpRequestExecutor();
        strategy.executeRequest(executor, GEOCODING_API_URL + URLEncoder.encode(address));
    }

    public static Mat getStreetViewImage(final String address) throws ClientProtocolException, IOException {
        ImageRequestExecutor executor = new ImageRequestExecutor();
        StringBuilder sb = new StringBuilder();
        sb.append(STREET_VIEW_API_URL);
        sb.append("location=" + URLEncoder.encode(address, "UTF-8"));
        return executor.execute(sb.toString());
    }
}
