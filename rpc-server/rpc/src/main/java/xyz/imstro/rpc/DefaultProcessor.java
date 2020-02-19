package xyz.imstro.rpc;

import io.netty.channel.ChannelHandlerContext;
import xyz.imstro.rpc.context.RpcContext;
import xyz.imstro.rpc.potocol.CustomProtocol;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @Author: MOSTRO
 */
public class DefaultProcessor implements Processor{

    private final RpcContext context;

    public DefaultProcessor(RpcContext context) {
        this.context = context;
    }

    @Override
    public void processor(ChannelHandlerContext ctx, CustomProtocol msg) {
        try {
            String className = msg.getClassName();
            Class<?> clazz = Class.forName(className);
            Method method = clazz.getMethod(msg.getMethodName(), msg.getParameters());
            Object result = method.invoke(context.getRpcObjectMap().get(className), msg.getValues());
            msg.setResult(result);
            ctx.writeAndFlush(msg).sync();
            ctx.channel().close();
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
