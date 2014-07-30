package org.vandv.client.communication;

import android.os.AsyncTask;
import org.vandv.communication.IRequestStrategy;
import org.vandv.communication.RequestExecutor;

/**
 * Created by vinceseguin on 23/07/14.
 */
public class AndroidRequestStrategy<T> extends AsyncTask<String, String, T> implements IRequestStrategy<T> {

    private RequestExecutor<T> executor;

    @Override
    protected T doInBackground(String... params) {
        return executor.doInBackground(params[0]);
    }

    @Override
    protected void onPostExecute(T t) {
        super.onPostExecute(t);
        executor.onPostExecute(t);
    }

    @Override
    public void executeRequest(RequestExecutor<T> executor, String... params) {
        this.executor = executor;
        this.execute(params);
    }
}
