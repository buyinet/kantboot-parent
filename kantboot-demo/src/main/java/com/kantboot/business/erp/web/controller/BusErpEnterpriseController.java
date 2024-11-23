package com.kantboot.business.erp.web.controller;

import com.kantboot.business.erp.domain.entity.BusErpEnterprise;
import com.kantboot.business.erp.service.IBusErpEnterpriseService;
import com.kantboot.util.rest.common.CommonSuccessStateCodeAndMsg;
import com.kantboot.util.rest.result.RestResult;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 企业控制器
 * Enterprise controller
 */
@RestController
@RequestMapping("/business-erp-web/erpEnterprise")
public class BusErpEnterpriseController {

    @Resource
    private IBusErpEnterpriseService service;

    /**
     * 添加成功
     * @param entity 企业实体
     * @return 返回添加成功的企业实体
     */
    @RequestMapping("/addBySelf")
    public RestResult<?> addBySelf(@RequestBody BusErpEnterprise entity) {
        return RestResult.success(service.addBySelf(entity), CommonSuccessStateCodeAndMsg.SAVE_SUCCESS);
    }

    /**
     * 更新成功
     * @param entity 企业实体
     * @return 返回更新成功的企业实体
     */
    @RequestMapping("/updateBySelf")
    public RestResult<?> updateBySelf(@RequestBody BusErpEnterprise entity) {
        return RestResult.success(service.updateBySelf(entity), CommonSuccessStateCodeAndMsg.SAVE_SUCCESS);
    }

    /**
     * 获取当前用户的企业
     * Get the enterprise of the current user
     * @return 返回当前用户的企业
     */
    @RequestMapping("/getBySelf")
    public RestResult<?> getBySelf() {
        return RestResult.success(service.getBySelf(), CommonSuccessStateCodeAndMsg.GET_SUCCESS);
    }

    /**
     * 删除当前用户的企业
     * Delete the enterprise of the current user
     * @param id 企业ID
     *           Enterprise ID
     * @return 删除结果
     *          Delete result
     */
    @RequestMapping("/deleteBySelf")
    public RestResult<?> deleteBySelf(@RequestParam("id") Long id) {
        service.deleteBySelf(id);
        return RestResult.success(null,CommonSuccessStateCodeAndMsg.REMOVE_SUCCESS);
    }

    /**
     * 根据ID获取企业
     * Get enterprise by ID
     * @param id 企业ID
     *           Enterprise ID
     */
    @RequestMapping("/getById")
    public RestResult<?> getById(@RequestParam("id") Long id) {
        return RestResult.success(service.getById(id), CommonSuccessStateCodeAndMsg.GET_SUCCESS);
    }

}
