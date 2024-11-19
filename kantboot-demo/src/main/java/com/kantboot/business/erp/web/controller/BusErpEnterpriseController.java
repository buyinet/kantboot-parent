package com.kantboot.business.erp.web.controller;

import com.kantboot.business.erp.domain.entity.BusErpEnterprise;
import com.kantboot.business.erp.service.IBusErpEnterpriseService;
import com.kantboot.util.rest.common.CommonSuccessStateCodeAndMsg;
import com.kantboot.util.rest.result.RestResult;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/business-erp-web/erpEnterprise")
public class BusErpEnterpriseController {

    @Resource
    private IBusErpEnterpriseService service;

    @RequestMapping("/addBySelf")
    public RestResult<?> addBySelf(@RequestBody BusErpEnterprise entity) {
        return RestResult.success(service.addBySelf(entity), CommonSuccessStateCodeAndMsg.SAVE_SUCCESS);
    }

}
