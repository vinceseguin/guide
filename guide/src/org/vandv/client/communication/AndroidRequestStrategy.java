package org.vandv.client.communication;

import java.io.IOException;

import android.os.AsyncTask;
import org.vandv.common.communication.IRequestStrategy;
import org.vandv.common.communication.RequestExecutor;
import org.apache.http.client.ClientProtocolException;
/**
 * Created by vinceseguin on 23/07/14.
 */
@Deprecated
public class AndroidRequestStrategy<T> extends AsyncTask<String, String, T> implements IRequestStrategy<T> {

    private RequestExecutor<T> executor;

    @Override
    protected T doInBackground(String... params) {
        try {
			return executor.execute(params[0]);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
    }

    @Override
    protected void onPostExecute(T t) {
        super.onPostExecute(t);
        // executor.onPostExecute(t);
    }
    
    public void executeRequest(RequestExecutor<T> executor, String... params) {
        this.executor = executor;
        this.execute(params);
    }
}
