package org.vandv.loadbalancer.server;

import org.vandv.loadbalancer.ServerManager;

/**
 * Created by vinceseguin on 29/07/14.
 */
class RegisterAction extends AbstractServerAction {

    private ServerManager serverManager;

    public RegisterAction(ServerManager serverManager) {
        this.serverManager = serverManager;
    }

    @Override
    public void execute(Server server) {
        this.serverManager.register(server);
    }
}
