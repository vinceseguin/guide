package org.vandv.loadbalancer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.vandv.loadbalancer.exceptions.NoServerAvailableException;
import org.vandv.loadbalancer.server.Server;

import java.util.*;

/**
 * The load balancer's server manager.
 * 
 * Registers new servers, keeps track of them and dispatch them when a client needs
 * to get a request handled.
 * 
 * Implements the Singleton design pattern.
 * 
 * Created by vinceseguin on 28/07/14.
 * Updated by vgentilcore on 06/08/14.
 */
public class ServerManager {

    private static final Logger logger = LogManager.getLogger(ServerManager.class.getName());

	private static volatile ServerManager instance = null;
	
    public static final int TICK_DURATION = 10;
    public static final int FLUSH_TIMER_LIMIT = 300;

    // We use two lists to manage the servers.
    // PriorityQueue : to quickly access the less loaded server that can handle the desired request.
    // HashMap 		 : to quickly access a server by it's id.
    private Queue<Server> serverPriorityQueue = new PriorityQueue<Server>();
    private Map<String, Server> serverMap = new HashMap<String, Server>();
    
    /**
     * Private constructor (Singleton)
     */
    private ServerManager() {

        this.startTicking();
    }
    
    /**
     * Returns the unique instance of ServerManager (Singleton)
     * 
     * @return the unique instance of ServerManager.
     */
    public final static ServerManager getInstance() {
    	if (instance == null) {
    		synchronized (ServerManager.class) {
				if (instance == null) {
					instance = new ServerManager();
				}
			}
    	}
    	return instance;
    }

    /**
     * Adds the server to the list of available servers.
     * 
     * @param server the new server to be added to the list.
     */
    public void register(Server server) {
        server.setCurrentNumberOfRequest(0);
        server.setCpuLoad(0);
        this.serverPriorityQueue.add(server);
        this.serverMap.put(server.getId(), server);

        logger.error("SERVER REGISTERED: " + server.getAddress() + ":" + server.getPort());
    }

    /**
     * Updates the server's object informations
     * 
     * @param server the server with updated informations.
     */
    public void update(Server server) {
        Server toUpdate = serverMap.get(server.getId());
        remove(toUpdate);
        this.serverPriorityQueue.add(server);
        this.serverMap.put(server.getId(), server);

        logger.error("SERVER UPDATED: " + server.getAddress() + ":" + server.getPort());
    }

    /**
     * Starts a new thread to monitor servers every TICK_DURATION sec.
     * If a server has not pinged us in FLUSH_TIMER_LIMIT sec, it is removed from the list.
     */
    private void startTicking() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(TICK_DURATION * 1000);
                        for (Server server : serverPriorityQueue) {
                            server.tick();
                            if (server.getTimer() >= FLUSH_TIMER_LIMIT) {
                                remove(server);
                                logger.error("CANNOT REACH SERVER: " + server.getAddress() + ":" + server.getPort());
                            }
                        }
                    } catch (Exception ex) {
                    	ex.printStackTrace();
                    }
                }
            }
        }).start();
    }

    /**
     * Removes a server from the list.
     * 
     * @param server the server to remove.
     */
    private synchronized void remove(Server server) {
        this.serverMap.remove(server.getId());
        this.serverPriorityQueue.remove(server);
    }

    /**
     * Returns the next available server. 
     * It should be the less loaded server that is able to handle the requestType.
     * 
     * @param requestType the type of request that needs to be handled.
     * @return the next available server.
     * @throws NoServerAvailableException
     */
    public Server getNextAvailableServer(String requestType) throws NoServerAvailableException {
    	Server nextServer = null;
    	
        for (Server server : this.serverPriorityQueue) {
        	if (server.canHandle(requestType)) {
        		
        		nextServer = server;
        		break;
        	}
        }
        
        if (nextServer == null) {
        	throw new NoServerAvailableException();
        }

        return nextServer;
    }
}
