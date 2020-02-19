package xyz.imostro.api;

import xyz.imstro.rpc.annotation.RpcService;

@RpcService
public class RpcHelloServiceImpl implements IRpcHelloService {

    @Override
    public String hello(String name) {
        return "Hello " + name + "!";  
    }  
  
}  
