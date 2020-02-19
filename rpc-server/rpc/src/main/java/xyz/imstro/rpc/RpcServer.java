package xyz.imstro.rpc;


import xyz.imstro.rpc.exception.RpcException;

/**
 * @Author: MOSTRO
 */
public class RpcServer {

    private RpcServer(){

    }
    public static void run(Class<?> clazz){
        run(clazz, null);
    }

    public static void run(Class<?> clazz, String[] args){
        try {
            RpcController controller = new RpcController(clazz);
            controller.start();
        } catch (RpcException e) {
            e.printStackTrace();
        }
    }


}
