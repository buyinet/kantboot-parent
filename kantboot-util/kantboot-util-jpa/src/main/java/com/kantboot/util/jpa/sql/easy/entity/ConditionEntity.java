package com.kantboot.util.jpa.sql.easy.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 关于运算符的实体类
 */
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class ConditionEntity implements Serializable {


    /**
     * 操作符
     * 等于 eq
     */
    public static final String OPERATOR_CODE_EQ = "eq";

    /**
     * like查询 like
     */
    public static final String OPERATOR_CODE_LIKE = "like";

    /**
     * 模糊查询 vague
     */
    public static final String OPERATOR_CODE_VAGUE = "vague";

    /**
     * 大于 gt
     */
    public static final String OPERATOR_CODE_GT = "gt";

    /**
     * 小于 lt
     */
    public static final String OPERATOR_CODE_LT = "lt";

    /**
     * 大于等于 ge
     */
    public static final String OPERATOR_CODE_GE = "ge";

    /**
     * 小于等于 le
     */
    public static final String OPERATOR_CODE_LE = "le";

    /**
     * 开区间查询 openInterval
     */
    public static final String OPERATOR_CODE_OPEN_INTERVAL = "openInterval";

    /**
     * 闭区间查询 closeInterval
     */
    public static final String OPERATOR_CODE_CLOSE_INTERVAL = "closeInterval";


    /**
     * 操作值字段
     */
    private String field;

    /**
     * 操作符
     * 等于 eq
     * like查询 like
     * 模糊查询 vague
     * 大于 gt
     * 小于 lt
     * 大于等于 ge
     * 小于等于 le
     * 开区间查询 openInterval
     * 闭区间查询 closeInterval
     */
    private String operatorCode;


    /**
     * 操作值
     */
    private Object value;


    /**
     * 用区间查询时的开始值
     * 开始区间的值
     */
    private Object startValue;

    /**
     * 用区间查询时的结束值
     * 结束区间的值
     */
    private Object endValue;

}
