package org.vandv.loadbalancer;

import org.apache.commons.io.IOUtils;
import org.vandv.loadbalancer.server.Server;

import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

/**
 * Created by vinceseguin on 28/07/14.
 */
public class ServerManager {

    public static final int TICK_DURATION = 10;
    private static final int FLUSH_TIMER_LIMIT = 300;
    private static final int CPU_LOAD_LIMIT = 80;

    private Queue<Server> serverPriorityQueue = new PriorityQueue<Server>();
    private Map<String, Server> serverMap = new HashMap<String, Server>();

    public ServerManager() {
        this.startTicking();
    }

    /**
     * Add the server to the available servers.
     */
    public void register(Server server) {
        server.setCurrentNumberOfRequest(0);
        server.setCpuLoad(0);
        this.serverPriorityQueue.add(server);
        this.serverMap.put(server.getId(), server);
    }

    /**
     * Update the server object information
     *
     * @param server
     */
    public void update(Server server) {
        Server toUpdate = serverMap.get(server.getId());
        remove(toUpdate);
        this.serverPriorityQueue.add(server);
        this.serverMap.put(server.getId(), server);
    }

    public void startTicking() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(TICK_DURATION * 1000);
                    } catch (Exception ex) {
                        for (Server server : serverPriorityQueue) {
                            server.tick();
                            if (server.getTimer() >= FLUSH_TIMER_LIMIT) {
                                remove(server);
                            }
                        }
                    }
                }
            }
        }).start();
    }

    private synchronized void remove(Server server) {
        this.serverMap.remove(server.getId());
        this.serverPriorityQueue.remove(server);
    }

    public Server getNextAvailableServer(String requestType) {

        List<Server> deletedServers = new ArrayList<Server>();

        Server server;
        do {
            server = this.serverPriorityQueue.poll();
            deletedServers.add(server);
        } while (server.getCpuLoad() > CPU_LOAD_LIMIT  && !server.canHandle(requestType));

        for (Server deletedServer : deletedServers) {
            this.serverPriorityQueue.add(deletedServer);
        }

        return server;
    }

    public void sendServerInformation(OutputStream out, Server server) {
        StringBuilder sb = new StringBuilder();
        sb.append("GUIDE_LOADBALANCER_CLIENT\r\n");
        sb.append("SERVER-IP:" + server.getAddress() + "\r\n");
        sb.append("SERVER-PORT:" + server.getPort());

        try {
            IOUtils.write(sb.toString(), out);
            out.flush();
        } catch (IOException exception) {
            //TODO
        }
    }
}
