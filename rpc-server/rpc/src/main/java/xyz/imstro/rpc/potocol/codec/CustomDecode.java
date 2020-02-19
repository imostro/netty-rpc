package xyz.imstro.rpc.potocol.codec;

import io.netty.handler.codec.serialization.ClassResolver;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;

/**
 * @Author: MOSTRO
 */
public class CustomDecode  extends ObjectDecoder {


    public CustomDecode(){
        this(Integer.MAX_VALUE);
    }

    public CustomDecode(int maxObjectSize) {
        super(maxObjectSize, ClassResolvers.cacheDisabled(null));
    }
}
