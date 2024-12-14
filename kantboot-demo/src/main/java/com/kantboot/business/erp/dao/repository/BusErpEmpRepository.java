package com.kantboot.business.erp.dao.repository;

import com.kantboot.business.erp.domain.entity.BusErpEmp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BusErpEmpRepository extends JpaRepository<BusErpEmp,Long> {


    @Query("""
    FROM BusErpEmp
    WHERE (:#{#param.id} IS NULL OR id = :#{#param.id})
    AND (:#{#param.enterpriseId} IS NULL OR enterpriseId = :#{#param.enterpriseId})
    AND (:#{#param.departmentId} IS NULL OR departmentId = :#{#param.departmentId})
    AND (:#{#param.name} IS NULL OR name = '' OR name = :#{#param.name})
    """)
    List<BusErpEmp> search(@Param("param") BusErpEmp param);

    /**
     * 根据用户名查询是否存在
     * Query whether the username exists
     */
    Boolean existsByUsernameAndEnterpriseId(String username, Long enterpriseId);

    /**
     * 根据用户名查询员工
     * Query employee by username
     */
    BusErpEmp getByUsernameAndEnterpriseId(String username, Long enterpriseId);

    /**
     * 根据用户账号ID和企业ID获取员工
     * Get employee according to the user account ID and enterprise ID
     *
     * @param enterpriseId 企业ID
     *                     Enterprise ID
     * @return BusErpEmp 员工
     */
    BusErpEmp getByUserAccountIdAndEnterpriseId(Long userAccountId, Long enterpriseId);

}
