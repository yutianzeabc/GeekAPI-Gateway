package cc.geektip.gateway.sdk.annotation;

import java.lang.annotation.*;

/**
 * @description: Api生产者类注解
 * @author: Fish
 * @date: 2024/6/4
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface ApiProducerClazz {

    /**
     * 接口名称
     */
    String interfaceName() default "";

    /**
     * 接口版本
     */
    String interfaceVersion() default "";

}
