package com.kantboot.business.erp.service;

import com.kantboot.business.erp.domain.entity.BusErpVacationTemplate;

import java.util.List;

/**
 * 假期模板服务
 * Vacation template service
 * @author 方某方
 */
public interface IBusErpVacationTemplateService {

    /**
     * 添加假期模板
     * Add vacation template
     *
     * @param template 请假模板
     *                 vacation template
     *
     */
    void addBySelf(BusErpVacationTemplate template);

    /**
     * 修改假期模板
     * Modify vacation template
     *
     * @param template 请假模板
     *                 vacation template
     */
    void updateBySelf(BusErpVacationTemplate template);

    /**
     * 删除假期模板
     * Delete vacation template
     *
     * @param id 假期模板ID
     *           vacation template ID
     */
    void deleteBySelf(Long id);


    /**
     * 根据企业ID获取假期模板
     * Get vacation template by enterprise ID
     *
     * @param enterpriseId 企业ID
     *                     enterprise ID
     */
    List<BusErpVacationTemplate> getByEnterpriseId(Long enterpriseId);

    /**
     * 根据企业获取默认模板
     * Get default template by enterprise
     *
     * @param enterpriseId 企业ID
     *                     enterprise ID
     */
    BusErpVacationTemplate getDefaultByEnterpriseId(Long enterpriseId);

}
