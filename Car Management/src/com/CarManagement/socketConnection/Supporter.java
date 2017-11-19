package com.CarManagement.socketConnection;

import java.io.Serializable;
import java.util.function.Consumer;

/**
 * Create an object supporter as Server
 */
public class Supporter extends NetworkConnection{
    private int port;

    /**
     * Create an constructor to call port and receive the information.
     * @param port
     * @param onReceiveCallback
     */
    public Supporter(int port,Consumer<Serializable> onReceiveCallback) {
        super(onReceiveCallback);
        this.port = port;

    }

    /**
     * return true when its server, i going to use this to check its server or not
     * @return true
     */
    @Override
    protected boolean isServer() {
        return true;
    }

    /**
     * server dont need an ip so it will return null;
     * @return
     */
    @Override
    protected String getIP() {
        return null;
    }

    /**
     * return port what i choose is 11111;
     * @return
     */
    @Override
    protected int getPort() {
        return port;
    }
}
