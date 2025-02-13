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
    public static final BaseException PAY_ORDER_NOT_FOUND = BaseException.of("payOrderNotFound", "The payment order was not found");

    /**
     * 支付订单不是未支付状态
     * payOrderNotUnpaid
     */
    public static final BaseException PAY_ORDER_NOT_UNPAID = BaseException.of("payOrderNotUnpaid", "The payment order is not in the unpaid state");

    /**
     * 订单异常
     */
    public static final BaseException PAY_ORDER_EXCEPTION = BaseException.of("payOrderException", "Payment order exception");

    /**
     * 订单正在进行其它操作
     */
    public static final BaseException PAY_ORDER_OPERATING = BaseException.of("payOrderOperating", "The payment order is being operated");

    /**
     * 该订单已退款
     */
    public static final BaseException PAY_ORDER_REFUNDED = BaseException.of("payOrderRefunded", "The payment order has been refunded");

    /**
     * 该订单正在退款中
     */
    public static final BaseException PAY_ORDER_REFUNDING = BaseException.of("payOrderRefunding", "The payment order is being refunded");

    /**
     * 该订单并未支付完成
     */
    public static final BaseException PAY_ORDER_NOT_PAID = BaseException.of("payOrderNotPaid", "The payment order has not been paid");

    /**
     * 等待退款确认
     */
    public static final BaseException PAY_ORDER_WAIT_REFUND_CHECK = BaseException.of("payOrderWaitRefundCheck", "Waiting for refund confirmation");

}