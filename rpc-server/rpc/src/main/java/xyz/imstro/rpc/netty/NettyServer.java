package xyz.imstro.rpc.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import xyz.imstro.rpc.Processor;
import xyz.imstro.rpc.potocol.CustomProtocol;
import xyz.imstro.rpc.potocol.codec.CustomDecode;
import xyz.imstro.rpc.potocol.codec.CustomEncode;

/**
 * A netty server, provider the net support
 *
 * @Author: MOSTRO
 */
public class NettyServer {
    private final EventLoopGroup boss;

    private final EventLoopGroup work;

    private final ServerBootstrap bootstrap;

    private final NettyConfig config;

    private final Processor processor;

    public NettyServer(Processor processor) {
        this(null, processor);
    }



    public NettyServer(NettyConfig config,Processor processor) {
        if(config == null){
            this.config = new NettyConfig();
        }else{
            this.config = config;
        }
        this.processor = processor;
        boss = new NioEventLoopGroup(1);
        work = new NioEventLoopGroup(config.getThreadNums());
        bootstrap = new ServerBootstrap();
    }

    public void start(){
        bootstrap.group(boss, work)
                .channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO))
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new CustomDecode())
                                .addLast(new CustomEncode())
                                .addLast(new RpcServerHandler());
                    }});
        try {
            ChannelFuture future = bootstrap.bind(config.getPort()).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            shutdown();
        }

    }

    public void shutdown(){
        boss.shutdownGracefully();
        work.shutdownGracefully();
    }

    private class RpcServerHandler extends SimpleChannelInboundHandler<CustomProtocol> {

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, CustomProtocol msg) throws Exception {
            processor.processor(ctx, msg);
        }
    }

    private class RpcServerInitHandler extends ChannelDuplexHandler {
        @Override
        public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
            super.channelRegistered(ctx);
        }

        @Override
        public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
            super.channelUnregistered(ctx);
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            super.channelActive(ctx);
        }

        @Override
        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
            super.channelInactive(ctx);
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            super.exceptionCaught(ctx, cause);
        }
    }
}
