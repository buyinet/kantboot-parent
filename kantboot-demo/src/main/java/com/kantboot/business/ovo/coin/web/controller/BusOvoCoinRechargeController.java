package com.kantboot.business.ovo.coin.web.controller;

import com.kantboot.business.ovo.coin.domain.dto.BusOvoCoinRechargeDTO;
import com.kantboot.business.ovo.coin.service.IBusOvoCoinRechargeService;
import com.kantboot.functional.pay.order.domian.entity.FunctionalPayOrder;
import com.kantboot.util.rest.common.CommonSuccessStateCodeAndMsg;
import com.kantboot.util.rest.result.RestResult;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bus-ovo-coin-web/ovoCoinRecharge")
public class BusOvoCoinRechargeController {

    @Resource
    private IBusOvoCoinRechargeService busOvoCoinRechargeService;

    @RequestMapping("/generatePayOrder")
    public RestResult<FunctionalPayOrder> generatePayOrder(@RequestBody BusOvoCoinRechargeDTO dto) {
        return RestResult.success(busOvoCoinRechargeService.generatePayOrder(dto), CommonSuccessStateCodeAndMsg.OPERATION_SUCCESS);
    }


}
