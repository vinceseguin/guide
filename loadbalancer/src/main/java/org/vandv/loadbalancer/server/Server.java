package org.vandv.loadbalancer.server;

import org.vandv.loadbalancer.ServerManager;

/**
 * Created by vinceseguin on 28/07/14.
 */
public abstract class Server implements Comparable<Server> {

    private String address;
    private int port;
    private double cpuLoad;
    private int currentNumberOfRequest;
    private int timer = 0;
    protected Server server;

    public Server(Server server) {
        this.server = server;
    }

    public int getTimer() {
        return timer;
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getCurrentNumberOfRequest() {
        return currentNumberOfRequest;
    }

    public void setCurrentNumberOfRequest(int currentNumberOfRequest) {
        this.currentNumberOfRequest = currentNumberOfRequest;
    }

    public double getCpuLoad() {
        return cpuLoad;
    }

    public void setCpuLoad(double cpuLoad) {
        this.cpuLoad = cpuLoad;
    }

    @Override
    public int compareTo(Server server) {
        return this.currentNumberOfRequest - server.getCurrentNumberOfRequest();
    }

    public String getId() {
        return getAddress() + ":" + getPort();
    }

    public void tick() {
        this.timer += ServerManager.TICK_DURATION;
    }

    public abstract boolean canHandle(String requestType);
}
