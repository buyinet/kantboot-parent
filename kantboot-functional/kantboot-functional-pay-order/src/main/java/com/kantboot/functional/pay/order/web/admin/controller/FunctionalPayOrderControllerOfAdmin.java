package com.kantboot.functional.pay.order.web.admin.controller;

import com.kantboot.functional.pay.order.service.IFunctionalPayOrderService;
import com.kantboot.util.rest.common.CommonSuccessStateCodeAndMsg;
import com.kantboot.util.rest.result.RestResult;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/functional-pay-order-web/admin/functionalPayOrder")
public class FunctionalPayOrderControllerOfAdmin {

    @Resource
    private IFunctionalPayOrderService service;

    @RequestMapping("/paySuccess")
    public RestResult<Void> paySuccess(
            @RequestParam("payOrderId") Long payOrderId,
            @RequestParam("payMethodCode") String payMethodCode,
            @RequestParam("payMethodAdditionalInfo") String payMethodAdditionalInfo) {
        service.paySuccess(payOrderId, payMethodCode, payMethodAdditionalInfo);
        return RestResult.success(null, CommonSuccessStateCodeAndMsg.OPERATION_SUCCESS);
    }

}
