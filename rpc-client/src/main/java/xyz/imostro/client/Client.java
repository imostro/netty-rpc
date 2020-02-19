package xyz.imostro.client;

import xyz.imostro.api.IRpcHelloService;
import xyz.imostro.api.IRpcService;
import xyz.imstro.rpc.invoker.RpcInvokerServer;

/**
 * @Author: MOSTRO
 */
public class Client {
    public static void main(String[] args){
        IRpcService service = RpcInvokerServer.create(IRpcService.class);
        int i = service.mult(5, 2);
        int j = service.div(5, 2);
        int k = service.sub(5, 2);
        System.out.println(i + "  " + k + "   " + j);

        IRpcHelloService helloService = RpcInvokerServer.create(IRpcHelloService.class);
        String hello = helloService.hello("mostro");
        System.out.println(hello);
    }
}
