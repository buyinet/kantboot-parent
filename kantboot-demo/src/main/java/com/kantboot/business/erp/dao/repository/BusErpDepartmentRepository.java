package com.kantboot.business.erp.dao.repository;

import com.kantboot.business.erp.domain.entity.BusErpDepartment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BusErpDepartmentRepository
        extends JpaRepository<BusErpDepartment,Long> {

    /**
     * 根据企业ID获取
     * Get by enterprise ID
     */
    List<BusErpDepartment> getByEnterpriseId(Long enterpriseId);

}
