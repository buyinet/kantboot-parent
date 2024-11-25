package com.kantboot.business.erp.dao.repository;

import com.kantboot.business.erp.domain.entity.BusErpVacationTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 请假模板 数据访问层
 * Vacation template data access layer
 */
public interface BusErpVacationTemplateRepository
        extends JpaRepository<BusErpVacationTemplate, Long> {
}
