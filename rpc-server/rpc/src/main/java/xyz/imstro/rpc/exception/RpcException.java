package xyz.imstro.rpc.exception;

/**
 * @Author: MOSTRO
 */
public class RpcException extends Exception {
    public RpcException(String message) {
        super(message);
    }

    public RpcException(String message, Throwable cause) {
        super(message, cause);
    }
}
