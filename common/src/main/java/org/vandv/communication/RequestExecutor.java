package org.vandv.communication;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by vinceseguin on 23/07/14.
 */
public abstract class RequestExecutor<T> {

    /**
     * http://stackoverflow.com/questions/3505930/make-an-http-request-with-android
     * @param url
     * @return
     * @throws IOException 
     * @throws ClientProtocolException 
     */
    public T execute(String url) throws ClientProtocolException, IOException {
        HttpClient httpclient = new DefaultHttpClient();
        HttpResponse response;
        T object = null;
        response = httpclient.execute(new HttpGet(url));
        StatusLine statusLine = response.getStatusLine();
        if(statusLine.getStatusCode() == HttpStatus.SC_OK){
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            response.getEntity().writeTo(out);
            object = transformByte(out);
            out.close();
        } else{
            //Closes the connection.
            response.getEntity().getContent().close();
            throw new IOException(statusLine.getReasonPhrase());
        }
        return object;
    }

    protected abstract T transformByte(ByteArrayOutputStream out);
}
