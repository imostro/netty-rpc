package xyz.imstro.rpc.invoker;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import xyz.imstro.rpc.potocol.CustomProtocol;
import xyz.imstro.rpc.potocol.codec.CustomDecode;
import xyz.imstro.rpc.potocol.codec.CustomEncode;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Author: MOSTRO
 */
public class RpcInvokerServer {

    public static <T> T create(Class<?> clazz){
        //clazz传进来本身就是interface
        RpcProxy proxy = new RpcProxy(clazz);
        Class<?> [] interfaces = clazz.isInterface() ?
                new Class[]{clazz} :
                clazz.getInterfaces();
        T result = (T) Proxy.newProxyInstance(clazz.getClassLoader(),interfaces,proxy);
        return result;
    }

    private static class RpcProxy implements InvocationHandler {

        private Class<?> targetClass;

        public RpcProxy(Class<?> clazz) {
            this.targetClass = clazz;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args)  throws Throwable {
            //如果传进来是一个已实现的具体类（本次演示略过此逻辑)
            if (Object.class.equals(method.getDeclaringClass())) {
                try {
                    return method.invoke(this, args);
                } catch (Throwable t) {
                    t.printStackTrace();
                }
                //如果传进来的是一个接口（核心)
            } else {
                CustomProtocol protocol = new CustomProtocol();
                protocol.setClassName(targetClass.getName());
                protocol.setMethodName(method.getName());
                protocol.setParameters(method.getParameterTypes());
                protocol.setValues(args);
                return getResultInNet(protocol);
            }
            return null;
        }

        private Object getResultInNet(CustomProtocol protocol){

            NioEventLoopGroup group = new NioEventLoopGroup(1);

            Bootstrap bootstrap = new Bootstrap();
            RpcClientHandler handler = new RpcClientHandler();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new CustomDecode())
                                    .addLast(new CustomEncode())
                                    .addLast(handler);
                        }
                    });
            try {
                ChannelFuture future = bootstrap.connect("127.0.0.1", 8080).sync();

                Channel channel = future.channel();
                channel.writeAndFlush(protocol);
                channel.closeFuture().sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                group.shutdownGracefully();
            }
            Object response = handler.getResponse();
            return response;
        }
    }

    private static class RpcClientHandler extends SimpleChannelInboundHandler<CustomProtocol>{

        private Object response;

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, CustomProtocol msg) throws Exception {
            response = msg.getResult();
        }

        public Object getResponse() {
            return response;
        }
    }

}
