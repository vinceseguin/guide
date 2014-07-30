package org.vandv.communication;

import java.io.ByteArrayOutputStream;

/**
 * Created by vinceseguin on 16/07/14.
 */
public class JsonHttpRequestExecutor extends RequestExecutor<String> {


    public JsonHttpRequestExecutor(IAsyncListener<String> listener) {
        super(listener);
    }

    @Override
    protected String transformByte(ByteArrayOutputStream out) {
        return out.toString();
    }
}
