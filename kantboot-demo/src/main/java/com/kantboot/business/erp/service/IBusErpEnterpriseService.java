package com.kantboot.business.erp.service;

import com.kantboot.business.erp.domain.entity.BusErpEnterprise;

import java.util.List;

/**
 * 企业服务
 * Enterprise service
 */
public interface IBusErpEnterpriseService {

    /**
     * 添加
     * Add
     */
    BusErpEnterprise addBySelf(BusErpEnterprise enterprise);

    /**
     * 更新
     * Update
     */
    BusErpEnterprise updateBySelf(BusErpEnterprise enterprise);


    /**
     * 获取自己的企业
     * Get your own business
     */
    List<BusErpEnterprise> getBySelf();

    /**
     * 删除自己的企业
     * Delete your own business
     */
    void deleteBySelf(Long id);

    /**
     * 根据ID获取
     * Get by ID
     */
    BusErpEnterprise getById(Long id);

}
