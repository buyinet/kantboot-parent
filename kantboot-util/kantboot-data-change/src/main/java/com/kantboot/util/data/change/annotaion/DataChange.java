package com.kantboot.util.data.change.annotaion;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用来统一记录数据的变化
 * 如果调用了某个方法，那么就会在记录一个UUID，表示这个在key的管理的数据发生了变化
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DataChange {

    String key() default "";

    String[] keys() default {};

}
