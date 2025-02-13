package com.kantboot.functional.pay.order.service;

import com.kantboot.functional.pay.order.domain.dto.PayOrderGenerateDTO;
import com.kantboot.functional.pay.order.domain.dto.PaySuccessDTO;
import com.kantboot.functional.pay.order.domian.entity.FunctionalPayOrder;

public interface IFunctionalPayOrderService {

    /**
     * 生成支付订单
     */
    FunctionalPayOrder generate(PayOrderGenerateDTO dto);

    /**
     * 根据订单ID查询
     */
    FunctionalPayOrder getById(Long id);

    /**
     * 取消订单
     */
    FunctionalPayOrder cancel(Long id);

    /**
     * 支付前校验
     */
    void checkPrePay(Long orderId);

    /**
     * 对应的支付方式已支付
     */
    void paySuccess(PaySuccessDTO dto);


}