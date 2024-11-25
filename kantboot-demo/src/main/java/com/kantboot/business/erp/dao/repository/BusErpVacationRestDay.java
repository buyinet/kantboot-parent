package com.kantboot.business.erp.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * “假期休息日” 数据访问接口
 * "Vacation rest day" data access interface
 */
public interface BusErpVacationRestDay
    extends JpaRepository<BusErpVacationRestDay, Long> {
}
