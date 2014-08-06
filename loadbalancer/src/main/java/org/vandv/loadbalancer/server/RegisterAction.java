package org.vandv.loadbalancer.server;

/**
 * A server's register action.
 * 
 * Created by vinceseguin on 29/07/14.
 */
class RegisterAction extends AbstractServerAction {

    @Override
    public void execute(Server server) {
        this.serverManager.register(server);
    }
}
