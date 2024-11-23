package com.kantboot.business.erp.dao.repository;

import com.kantboot.business.erp.domain.entity.BusErpEnterprise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 企业的数据访问层
 * Data access layer of enterprise
 */
public interface BusErpEnterpriseRepository extends JpaRepository<BusErpEnterprise,Long> {

    /**
     * 根据用户ID获取
     */
    List<BusErpEnterprise> getByUserAccountId(Long userAccountId);

}
