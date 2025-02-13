package com.kantboot.business.ovo.coin.service;

import com.kantboot.business.ovo.coin.domain.dto.BusOvoCoinRechargeDTO;
import com.kantboot.functional.pay.order.domian.entity.FunctionalPayOrder;

public interface IBusOvoCoinRechargeService {

    /**
     * 生成订单Id
     */
    FunctionalPayOrder generatePayOrder(BusOvoCoinRechargeDTO dto);

}
