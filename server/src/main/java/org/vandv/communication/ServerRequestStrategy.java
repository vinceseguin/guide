package org.vandv.communication;

import java.net.URLEncoder;

/**
 * Created by vinceseguin on 24/07/14.
 */
public class ServerRequestStrategy<T> implements IRequestStrategy<T> {

    private RequestExecutor<T> executor;

    private T doInBackground(String... params) {
        return executor.doInBackground(params[0]);
    }

    private void onPostExecute(T t) {
        executor.onPostExecute(t);
    }

    @Override
    public void executeRequest(RequestExecutor<T> executor, final String... params) {
        this.executor = executor;

        Runnable thread = new Runnable() {
            @Override
            public void run() {

                T obj = doInBackground(params);
                onPostExecute(obj);
            }
        };

        thread.run();
    }

}
