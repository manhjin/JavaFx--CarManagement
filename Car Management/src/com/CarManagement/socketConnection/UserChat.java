package com.CarManagement.socketConnection;

import java.io.Serializable;
import java.util.function.Consumer;
/*
    create an object userchat as client.
 */
public class UserChat extends NetworkConnection {
    private int port;
    private String ip;
    public UserChat(String ip, int port ,Consumer<Serializable> onReceiveCallback) {
        super(onReceiveCallback);
        this.port = port;
        this.ip = ip;
    }

    /**
     * because it is client so will return false.
     * @return false;
     */
    @Override
    protected boolean isServer() {
        return false;
    }

    /**
     * return customer ip to connect to server
     * @return 127.0.0.1 or localhost
     */
    @Override
    protected String getIP() {
        return ip;
    }

    @Override
    protected int getPort() {
        return port;
    }
}
