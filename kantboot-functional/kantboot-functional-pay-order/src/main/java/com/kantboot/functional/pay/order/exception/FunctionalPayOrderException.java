package com.kantboot.functional.pay.order.exception;

import com.kantboot.util.rest.exception.BaseException;

/**
 * 支付订单异常
 */
public class FunctionalPayOrderException {

    /**
     * 支付订单未找到
     * payOrderNotFound
     */
    public static final BaseException PAY_ORDER_NOT_FOUND = new BaseException("payOrderNotFound", "支付订单未找到");

    /**
     * 支付订单不是未支付状态
     * payOrderNotUnpaid
     */
    public static final BaseException PAY_ORDER_NOT_UNPAID = new BaseException("payOrderNotUnpaid", "支付订单不是未支付状态");

}