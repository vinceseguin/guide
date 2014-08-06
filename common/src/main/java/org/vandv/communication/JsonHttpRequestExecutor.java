package org.vandv.communication;

import java.io.ByteArrayOutputStream;

/**
 * Created by vinceseguin on 16/07/14.
 */
public class JsonHttpRequestExecutor extends RequestExecutor<String> {

    @Override
    protected String transformByte(ByteArrayOutputStream out) {
        return out.toString();
    }
}
