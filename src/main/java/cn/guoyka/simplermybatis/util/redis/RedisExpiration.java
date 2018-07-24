package cn.guoyka.simplermybatis.util.redis;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RedisExpiration {
    long value() default 10000L;
}
