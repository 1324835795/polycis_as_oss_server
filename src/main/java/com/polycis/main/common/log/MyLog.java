package com.polycis.main.common.log;

import java.lang.annotation.*;

/**
 * 自定义注解
 * @author weitao
 * @since 2019-05-19
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyLog {
    String describe() default "";
    enum Operation {QUERY, ADD, UPDATE,DELETE,OTHER}
    enum Query{QUERY,FIND,GET,SELECT}
    Operation operation() default Operation.QUERY;
}
