package com.kantboot.business.erp.service;

import com.kantboot.business.erp.domain.entity.BusErpCertificate;

import java.util.List;

/**
 * 证件服务
 * Certificate service
 */
public interface IBusErpCertificateService {

    /**
     * 根据企业ID获取
     */
    List<BusErpCertificate> getBySelf(Long enterpriseId);

    /**
     * 添加
     * @param entity 部门实体
     * @return 返回添加成功的部门实体
     */
    BusErpCertificate addBySelf(BusErpCertificate entity);

    /**
     * 更新
     * @param entity 部门实体
     * @return 返回更新成功的部门实体
     */
    BusErpCertificate updateBySelf(BusErpCertificate entity);

    /**
     * 删除
     * @param id 部门ID
     */
    void deleteBySelf(Long id);

    /**
     * 根据ID获取
     * Get by ID
     *
     * @param id 部门ID
     *           Department ID
     */
    BusErpCertificate getById(Long id);

    /**
     * 获取所有
     * Get all
     *
     * @param enterpriseId 企业ID
     *                     Enterprise ID
     */
    List<BusErpCertificate> getAll(Long enterpriseId);

}
