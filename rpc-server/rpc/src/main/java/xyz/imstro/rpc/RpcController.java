package xyz.imstro.rpc;

import xyz.imstro.rpc.context.RpcContext;
import xyz.imstro.rpc.exception.RpcException;
import xyz.imstro.rpc.netty.NettyConfig;
import xyz.imstro.rpc.netty.NettyServer;

/**
 * @Author: MOSTRO
 */
public class RpcController {

    private RpcContext rpcContext;

    private NettyServer server;

    private Processor processor;

    private NettyConfig nettyConfig;

    public RpcController(Class<?> clazz) throws RpcException {
        this.rpcContext = new RpcContext(clazz);
        this.nettyConfig = new NettyConfig();
        this.processor = new DefaultProcessor(rpcContext);
        this.server = new NettyServer(this.nettyConfig, this.processor);
    }

    public void start(){
        start(8080);
    }

    public void start(int port){
        nettyConfig.setPort(port);
        server.start();
    }



}
