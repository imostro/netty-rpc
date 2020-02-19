package xyz.imstro.rpc;

import io.netty.channel.ChannelHandlerContext;
import xyz.imstro.rpc.potocol.CustomProtocol;

/**
 * @Author: MOSTRO
 */
public interface Processor {

    /**
     * processor
     *
     * @param ctx channel handler context
     * @param msg custom protocol
     */
    void processor(ChannelHandlerContext ctx, CustomProtocol msg);
}
