package com.kantboot.functional.pay.order.web.admin.controller;

import com.kantboot.functional.pay.order.domain.dto.PaySuccessDTO;
import com.kantboot.functional.pay.order.service.IFunctionalPayOrderService;
import com.kantboot.util.rest.common.CommonSuccessStateCodeAndMsg;
import com.kantboot.util.rest.result.RestResult;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/functional-pay-order-web/admin/functionalPayOrder")
public class FunctionalPayOrderControllerOfAdmin {

    @Resource
    private IFunctionalPayOrderService service;

    @RequestMapping("/paySuccess")
    public RestResult<Void> paySuccess(@RequestBody PaySuccessDTO dto) {
        service.payComplete(dto);
        return RestResult.success(null, CommonSuccessStateCodeAndMsg.OPERATION_SUCCESS);
    }

}
