package com.kantboot.business.erp.web.controller;

import com.kantboot.business.erp.domain.entity.BusErpDepartment;
import com.kantboot.business.erp.service.IBusErpDepartmentService;
import com.kantboot.util.rest.common.CommonSuccessStateCodeAndMsg;
import com.kantboot.util.rest.result.RestResult;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/business-erp-web/erpDepartment")
public class BusErpDepartmentController {

    @Resource
    private IBusErpDepartmentService service;

    /**
     * 获取当前用户的部门
     * Get the department of the current user
     * @return 返回当前用户的部门
     */
    @RequestMapping("/getBySelf")
    public RestResult<?> getBySelf(@RequestParam("enterpriseId") Long enterpriseId) {
        return RestResult.success(service.getBySelf(enterpriseId), CommonSuccessStateCodeAndMsg.GET_SUCCESS);
    }

    /**
     * 删除当前用户的部门
     * Delete the department of the current user
     * @param id 部门ID
     *           Department ID
     * @return 删除结果
     *          Delete result
     */
    @RequestMapping("/deleteBySelf")
    public RestResult<?> deleteBySelf(@RequestParam("id") Long id) {
        service.deleteBySelf(id);
        return RestResult.success(null,CommonSuccessStateCodeAndMsg.REMOVE_SUCCESS);
    }

    /**
     * 添加
     * Add
     *
     * @param entity 部门实体
     *               Department entity
     * @return 添加成功的部门实体
     *         Department entity added successfully
     */
    @RequestMapping("/addBySelf")
    public RestResult<?> addBySelf(@RequestBody BusErpDepartment entity) {
        return RestResult.success(service.addBySelf(entity), CommonSuccessStateCodeAndMsg.SAVE_SUCCESS);
    }

    /**
     * 更新
     * Update
     *
     * @param entity 部门实体
     *               Department entity
     * @return 更新成功的部门实体
     *         Updated department entity
     */
    @RequestMapping("/updateBySelf")
    public RestResult<?> updateBySelf(@RequestBody BusErpDepartment entity) {
        return RestResult.success(service.updateBySelf(entity), CommonSuccessStateCodeAndMsg.SAVE_SUCCESS);
    }

    /**
     * 根据ID获取
     * Get by ID
     *
     * @param id 部门ID
     *           Department ID
     * @return 部门实体
     */
    @RequestMapping("/getById")
    public RestResult<?> getById(@RequestParam("id") Long id) {
        return RestResult.success(service.getById(id), CommonSuccessStateCodeAndMsg.GET_SUCCESS);
    }

}
