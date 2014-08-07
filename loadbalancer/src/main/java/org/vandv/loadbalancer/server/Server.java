package org.vandv.loadbalancer.server;

import org.vandv.loadbalancer.ServerManager;

/**
 * Abstraction of a server.
 * Implements the Decorator design pattern.
 * To add capabilities to the server, decorate it with the appropriate
 * children class.
 * 
 * Created by vinceseguin on 28/07/14.
 * Updated by vgentilcore on 06/08/14.
 */
public abstract class Server implements Comparable<Server> {

    private String address;
    private int port;
    private int cpuLoad;
    private int currentNumberOfRequest;
    private int timer = 0;
    protected Server server;
    
    /**
     * Constructor
     * 
     * @param server the server to be decorated.
     */
    public Server(Server server) {
        this.server = server;
    }

    /**
     * Returns true if the server can handle the specified request type, false otherwise.
     * 
     * @param requestType the type of the request to be handled.
     * @return true if the server can handle the specified request type, false otherwise.
     */
    public abstract boolean canHandle(String requestType);
    
    /**
     * Returns the server's timer value.
     * 
     * @return the server's timer value.
     */
    public int getTimer() {
        return timer;
    }

    /**
     * Returns the server's address.
     * 
     * @return the server's address.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Set the server's address.
     * 
     * @param address the server's address.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Returns the server's port.
     * 
     * @return the server's port.
     */
    public int getPort() {
        return port;
    }

    /**
     * Set the server's port.
     * 
     * @param port the server's port.
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * Returns the current number of request handled by the server.
     * 
     * @return the current number of request handled by the server.
     */
    public int getCurrentNumberOfRequest() {
        return currentNumberOfRequest;
    }

    /**
     * Set the current number of request handled by the server.
     * 
     * @param currentNumberOfRequest the current number of request handled by the server.
     */
    public void setCurrentNumberOfRequest(int currentNumberOfRequest) {
        this.currentNumberOfRequest = currentNumberOfRequest;
    }

    /**
     * Set the server's CPU Load.
     * 
     * @param cpuLoad the server's CPU Load.
     */
    public void setCpuLoad(int cpuLoad) {
        this.cpuLoad = cpuLoad;
    }

    /**
     * Returns the server's id.
     * 
     * @return the server's id.
     */
    public String getId() {
        return getAddress() + ":" + getPort();
    }

    /**
     * Simulates a tick on the server. 
     * The timer value is incremented by TICK_DURATION
     */
    public void tick() {
        this.timer += ServerManager.TICK_DURATION;
    }
    
    @Override
    public int compareTo(Server server) {
    	int score1 = this.cpuLoad * 100 + this.currentNumberOfRequest;
    	int score2 = server.cpuLoad * 100 + server.currentNumberOfRequest;
        
    	return score1 - score2;
    }
}
