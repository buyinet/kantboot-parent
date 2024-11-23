package com.kantboot.business.erp.web.controller;

import com.kantboot.business.erp.domain.entity.BusErpCertificate;
import com.kantboot.business.erp.service.IBusErpCertificateService;
import com.kantboot.util.rest.common.CommonSuccessStateCodeAndMsg;
import com.kantboot.util.rest.result.RestResult;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 证件控制器
 * Certificate controller
 */
@RestController
@RequestMapping("/business-erp-web/erpCertificate")
public class BusErpCertificateController {

    @Resource
    private IBusErpCertificateService service;

    /**
     * 根据企业ID获取证书列表
     * Get the certificate list according to the enterprise ID
     *
     * @param enterpriseId 企业ID
     *                     Enterprise ID
     *
     * @return 证书列表
     */
    @RequestMapping("/getBySelf")
    public RestResult<?> getBySelf(@RequestParam("enterpriseId") Long enterpriseId) {
        return RestResult.success(service.getBySelf(enterpriseId), CommonSuccessStateCodeAndMsg.GET_SUCCESS);
    }

    /**
     * 删除证书
     * Delete certificate
     *
     * @param id 证书ID
     *           Certificate ID
     * @return 删除结果
     *          Delete result
     */
    @RequestMapping("/deleteBySelf")
    public RestResult<?> deleteBySelf(@RequestParam("id") Long id) {
        service.deleteBySelf(id);
        return RestResult.success(null,CommonSuccessStateCodeAndMsg.REMOVE_SUCCESS);
    }

    /**
     * 添加证书
     * Add certificate
     *
     * @param entity 证书实体
     *               Certificate entity
     * @return 添加成功的证书实体
     *         Certificate entity added successfully
     */
    @RequestMapping("/addBySelf")
    public RestResult<?> addBySelf(@RequestBody BusErpCertificate entity) {
        return RestResult.success(service.addBySelf(entity), CommonSuccessStateCodeAndMsg.SAVE_SUCCESS);
    }

    /**
     * 更新证书
     * Update certificate
     *
     * @param entity 证书实体
     *               Certificate entity
     * @return 更新成功的证书实体
     *         Updated certificate entity
     */
    @RequestMapping("/updateBySelf")
    public RestResult<?> updateBySelf(@RequestBody BusErpCertificate entity) {
        return RestResult.success(service.updateBySelf(entity), CommonSuccessStateCodeAndMsg.SAVE_SUCCESS);
    }

    /**
     * 根据ID获取证书
     * Get certificate by ID
     *
     * @param id 证书ID
     *           Certificate ID
     * @return 证书实体
     */
    @RequestMapping("/getById")
    public RestResult<?> getById(@RequestParam("id") Long id) {
        return RestResult.success(service.getById(id), CommonSuccessStateCodeAndMsg.GET_SUCCESS);
    }

    /**
     * 获取所有证书
     * Get all certificates
     *
     * @param enterpriseId 企业ID
     *                     Enterprise ID
     *
     * @return 证书列表
     */
    @RequestMapping("/getAll")
    public RestResult<?> getAll(@RequestParam("enterpriseId") Long enterpriseId) {
        return RestResult.success(service.getAll(enterpriseId), CommonSuccessStateCodeAndMsg.GET_SUCCESS);
    }

}
