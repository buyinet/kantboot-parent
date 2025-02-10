package com.kantboot.functional.pay.order.service;

import com.kantboot.functional.pay.order.domain.dto.PayOrderGenerateDTO;
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

}