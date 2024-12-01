package com.kantboot.business.erp.dao.repository;

import com.kantboot.business.erp.domain.entity.BusErpVacationRestDay;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * “假期休息日” 数据访问接口
 * "Vacation rest day" data access interface
 */
public interface BusErpVacationRestDayRepository
    extends JpaRepository<BusErpVacationRestDay, Long> {

    /**
     * 根据模板ID删除
     * Delete by template ID
     *
     * @param vacationTemplateId 模板ID
     *                           Template ID
     */
    @Modifying
    @Transactional
    @Query("DELETE FROM BusErpVacationRestDay WHERE vacationTemplateId = ?1")
    void deleteByVacationTemplateId(Long vacationTemplateId);

}
