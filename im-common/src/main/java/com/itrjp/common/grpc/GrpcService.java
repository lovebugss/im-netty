package com.itrjp.common.grpc;

import java.lang.annotation.*;

/**
 * TODO
 *
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2022/7/22 17:49
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface GrpcService {
    /**
     * 名称
     *
     * @return
     */
    String client() default "";
}
