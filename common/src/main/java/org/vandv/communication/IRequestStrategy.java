package org.vandv.communication;

/**
 * Created by vinceseguin on 24/07/14.
 */
public interface IRequestStrategy<T> {
    public void executeRequest(RequestExecutor<T> executor, String... params);
}