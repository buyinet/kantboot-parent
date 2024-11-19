package com.kantboot.business.erp.web.controller;

import com.kantboot.business.erp.domain.dto.BusErpEmpClockSearchDTO;
import com.kantboot.business.erp.service.IBusErpEmpClockService;
import com.kantboot.util.jpa.param.PageParam;
import com.kantboot.util.rest.common.CommonSuccessStateCodeAndMsg;
import com.kantboot.util.rest.result.RestResult;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/business-erp-web/empClock")
public class BusErpEmpClockController {

    @Resource
    private IBusErpEmpClockService service;

    /**
     * 用户自身打卡
     * User self clock in
     * @param type 打卡类型 1:上班打卡 2:下班打卡
     *             Clock in type 1:Clock in 2:Clock out
     */
    @RequestMapping("/clockBySelf")
    public RestResult<?> clock(
            @RequestParam("type") Integer type) {
        return RestResult.success(service.clockBySelf(type),CommonSuccessStateCodeAndMsg.OPERATION_SUCCESS);
    }

    /**
     * 获取用户自身的最近一次打卡记录
     * Get the latest clock in record of the user itself
     */
    @RequestMapping("/getLastClockBySelf")
    public RestResult<?> getLastClockBySelf() {
        return RestResult.success(service.getLastClockBySelf(), CommonSuccessStateCodeAndMsg.GET_SUCCESS);
    }

    @RequestMapping("/searchBySelf")
    public RestResult<?> searchBySelf(@RequestBody PageParam<BusErpEmpClockSearchDTO> param) {
        return RestResult.success(service.searchBySelf(param), CommonSuccessStateCodeAndMsg.GET_SUCCESS);
    }

    /**
     * 根据ID获取打卡记录
     */
    @RequestMapping("/getById")
    public RestResult<?> getById(@RequestParam("id") Long id) {
        return RestResult.success(service.getById(id), CommonSuccessStateCodeAndMsg.GET_SUCCESS);
    }

}
