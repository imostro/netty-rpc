package xyz.imostro;

import xyz.imstro.rpc.RpcServer;

/**
 * @Author: MOSTRO
 */
public class Server {
    public static void main(String[] args){
        RpcServer.run(Server.class);
    }
}
