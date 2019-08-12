package me.solby.xconfig.annotation;

import me.solby.xconfig.annotation.enums.LimitTypeEnum;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * me.solby.xboot.config.annotation
 *
 * @author majhdk
 * @date 2019-08-11
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Order(Ordered.HIGHEST_PRECEDENCE)
public @interface RequestLimit {

    /**
     * 限制类型，默认接口
     *
     * @return
     */
    LimitTypeEnum type() default LimitTypeEnum.INTERFACE;

    /**
     * 调用次数
     *
     * @return
     */
    int count() default 0;

    /**
     * 接口时间限制
     *
     * @return
     */
    int time() default 1;

    /**
     * 时间单位，默认秒
     *
     * @return
     */
    TimeUnit unit() default TimeUnit.SECONDS;

}
