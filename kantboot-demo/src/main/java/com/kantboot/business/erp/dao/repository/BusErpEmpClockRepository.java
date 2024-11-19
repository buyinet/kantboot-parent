package com.kantboot.business.erp.dao.repository;

import com.kantboot.business.erp.domain.dto.BusErpEmpClockSearchDTO;
import com.kantboot.business.erp.domain.entity.BusErpEmpClock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * 员工打卡的数据访问层
 * Data access layer of employee clock
 * @author 方某方
 */
public interface BusErpEmpClockRepository extends JpaRepository<BusErpEmpClock, Long> {

    /**
     * 根据用户账号ID获取最近一次打卡记录
     * Get the latest clock record according to the user account ID
     *
     * @param userAccountId 用户账号ID
     *                      User account ID
     *
     * @return BusErpEmpClockIn 最后一次打卡记录
     *                          The last clock record
     */
    BusErpEmpClock findFirstByUserAccountIdOrderByGmtClockDesc(Long userAccountId);


    @Query("""
    FROM BusErpEmpClock
    WHERE (:#{#param.userAccountId} IS NULL OR userAccountId = :#{#param.userAccountId})
    AND (:#{#param.gmtClockStart} IS NULL OR gmtClock >= :#{#param.gmtClockStart})
    AND (:#{#param.gmtClockEnd} IS NULL OR gmtClock <= :#{#param.gmtClockEnd})
    ORDER BY gmtClock DESC
    """)
    Page<BusErpEmpClock> search(@Param("param") BusErpEmpClockSearchDTO param, Pageable pageable);

}
