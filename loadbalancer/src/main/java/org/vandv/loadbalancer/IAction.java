package org.vandv.loadbalancer;

import java.io.OutputStream;
import java.util.List;

/**
 * Created by vinceseguin on 29/07/14.
 */
public interface IAction {

    public void execute(OutputStream out, List<String> lines) throws Exception;
}
