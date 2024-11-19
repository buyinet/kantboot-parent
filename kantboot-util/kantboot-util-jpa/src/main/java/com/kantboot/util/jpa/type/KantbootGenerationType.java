package com.kantboot.util.jpa.type;

/**
 * 针对Jpa的id生成策略
 * 通过该类可以获取到不同的id生成策略
 * 该版本只支持雪花算法
 * @author 方某方
 */
public class KantbootGenerationType {
    /**
     * 雪花算法
     */
    public static final String SNOWFLAKE="com.kantboot.util.jpa.id.GenerateSnowflakeId";

}
