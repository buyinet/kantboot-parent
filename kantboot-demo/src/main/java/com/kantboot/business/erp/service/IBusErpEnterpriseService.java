package com.kantboot.business.erp.service;

import com.kantboot.business.erp.domain.entity.BusErpEnterprise;

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

}
