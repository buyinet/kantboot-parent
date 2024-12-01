package com.kantboot.business.erp.dao.repository;

import com.kantboot.business.erp.domain.entity.BusErpVacationTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 假期模板 数据访问层
 * Vacation template data access layer
 */
public interface BusErpVacationTemplateRepository
        extends JpaRepository<BusErpVacationTemplate, Long> {

    /**
     * 根据企业ID和是否默认查询
     * Query by enterprise ID and whether it is default
     * @param enterpriseId 企业ID
     *                     Enterprise ID
     * @param isDefault 是否默认
     *                  Whether it is default
     *
     * @return 假期模板列表
     *        Vacation template list
     */
    List<BusErpVacationTemplate> findByEnterpriseIdAndIsDefault(Long enterpriseId, Boolean isDefault);

    /**
     * 根据企业ID查询
     * Query by enterprise ID
     * @param enterpriseId 企业ID
     *                     Enterprise ID
     * @return 假期模板列表
     *         Vacation template list
     */
    List<BusErpVacationTemplate> findByEnterpriseId(Long enterpriseId);

}
