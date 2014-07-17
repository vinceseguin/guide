package org.vandv.communication;

/**
 * Created by vinceseguin on 17/07/14.
 */
public interface IAsyncListener<T> {
    public void receive(T object);
}
