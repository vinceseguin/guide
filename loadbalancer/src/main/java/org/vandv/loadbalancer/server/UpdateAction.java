package org.vandv.loadbalancer.server;

/**
 * A server's update action.
 * 
 * Created by vinceseguin on 29/07/14.
 */
public class UpdateAction extends AbstractServerAction {

    @Override
    public void execute(Server server) {
        this.serverManager.update(server);
    }
}
