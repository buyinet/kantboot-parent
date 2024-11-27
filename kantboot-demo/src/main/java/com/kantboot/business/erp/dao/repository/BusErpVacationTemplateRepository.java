package com.kantboot.business.erp.dao.repository;

import com.kantboot.business.erp.domain.entity.BusErpVacationTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 请假模板 数据访问层
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
     */
    List<BusErpVacationTemplate> findByEnterpriseIdAndIsDefault(Long enterpriseId, Boolean isDefault);

}
