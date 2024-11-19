package com.kantboot.business.erp.dao.repository;

import com.kantboot.business.erp.domain.entity.BusErpEnterprise;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 公司的数据访问层
 * Company data access layer
 */
public interface BusErpEnterpriseRepository extends JpaRepository<BusErpEnterprise,Long> {
}
