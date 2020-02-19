package xyz.imstro.rpc.potocol.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.serialization.ObjectEncoder;

import java.io.Serializable;

/**
 * @Author: MOSTRO
 */
public class CustomEncode extends ObjectEncoder {

    @Override
    protected void encode(ChannelHandlerContext ctx, Serializable msg, ByteBuf out) throws Exception {
        super.encode(ctx, msg, out);
    }
}
