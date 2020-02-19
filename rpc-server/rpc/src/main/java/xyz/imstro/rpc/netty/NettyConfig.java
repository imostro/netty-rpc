package xyz.imstro.rpc.netty;

import lombok.Data;

/**
 * @Author: MOSTRO
 */

public class NettyConfig {

    /**
     * server port.
     */
    private int port = 8080;

    /**
     * child thread number.
     */
    private int threadNums = 4;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getThreadNums() {
        return threadNums;
    }

    public void setThreadNums(int threadNums) {
        if(threadNums >=0){
            this.threadNums = threadNums;
        }
    }
}
