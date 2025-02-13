package com.kantboot.util.event.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 全局监听
 * Global listening
 *
 * @author 方某方
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface EventOn {

    /**
     * 事件编码
     * Event code
     */
    String value();
}
