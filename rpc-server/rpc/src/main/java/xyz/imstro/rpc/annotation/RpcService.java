package xyz.imstro.rpc.annotation;

import java.lang.annotation.*;

/**
 * @Author: MOSTRO
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RpcService {

    String value() default "";
}
