package com.aoji.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author yangsaixing
 * @description 自定义json注解
 * @date Created in 上午10:44 2017/11/17
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface JSON {
    Class<?> type();
    String include() default "";
    String filter() default "";
}
